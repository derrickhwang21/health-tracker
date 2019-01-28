package com.hwang.health_tracker;

import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcerciseActivityLog extends AppCompatActivity {
  AppDatabase database;
  ArrayAdapter<Exercise> adapter;
  Date currentTime = Calendar.getInstance().getTime();
  ListView listView;
  FusedLocationProviderClient mFusedLocationClient;
  private final int PERMISSION_ID = 0;
  Location lastLocation;
  protected String latitude;
  protected String longitude;



    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        showUsername();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getUserLocation();
        createTestExercise();
        listView = findViewById(R.id.excerciseListView);
        getAllExerciseObjects();

    }

    public void createTestExercise(){
        // Test database population
        if (database.excerciseDao().getAll().isEmpty()) {
            // test data
            database.excerciseDao().add(new Exercise("Burpees", 1, 10, "All Day!", currentTime.toString()));
            database.excerciseDao().add(new Exercise("Pushup", 1, 10, "All Day!", currentTime.toString()));

        }
    }

        public void createExercise(View v) {

        // grabs text from edittext view
        EditText UserInputExerciseName = findViewById(R.id.exercise_name);
        EditText userInputExerciseSets = findViewById(R.id.exercise_sets);
        EditText userInputExerciseReps = findViewById(R.id.exercise_reps);
        EditText userInputExerciseDescription = findViewById(R.id.exercise_description);

        // create Exercise object
        Exercise userInputExerciseObject = new Exercise(UserInputExerciseName.getText().toString(), Integer.parseInt(userInputExerciseSets.getText().toString()), Integer.parseInt(userInputExerciseReps.getText().toString()), userInputExerciseDescription.getText().toString(), currentTime.toString());
        if(lastLocation != null){
          userInputExerciseObject.longitude = lastLocation.getLongitude();
          userInputExerciseObject.latitude = lastLocation.getLatitude();
        }

        // add new exercise object to database
        sendToServer(userInputExerciseObject.title, Integer.toString(userInputExerciseObject.sets), Integer.toString(userInputExerciseObject.reps), userInputExerciseObject.description, userInputExerciseObject.timestamp, Double.toString(userInputExerciseObject.longitude), Double.toString(userInputExerciseObject.latitude));
        database.excerciseDao().add(userInputExerciseObject);

        // clear edit field text
        UserInputExerciseName.setText("");
        userInputExerciseSets.setText("");
        userInputExerciseReps.setText("");
        userInputExerciseDescription.setText("");

        // renders listview
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, database.excerciseDao().getAll());
        listView.setAdapter(adapter);
        }


   //retrieve request from server database
    private void getAllExerciseObjects() {

      adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, database.excerciseDao().getAll());

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        String url =" https://young-temple-12802.herokuapp.com/exercises";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Exercise> exerciseObjects = new ArrayList<>();
                        for (int i = 0; i < response.length(); i ++) {
                            try {
                              Exercise exerciseObject = new Exercise();
                              JSONObject jsonExercise =  response.getJSONObject(i);
                              exerciseObject.title = jsonExercise.getString("title");
                              exerciseObject.sets = jsonExercise.getInt("sets");
                              exerciseObject.reps = jsonExercise.getInt("reps");
                              exerciseObject.description = jsonExercise.getString("description");
                              exerciseObject.timestamp = jsonExercise.getString("timestamp");
                              exerciseObject.longitude = jsonExercise.getDouble("longitude");
                              exerciseObject.latitude = jsonExercise.getDouble("latitude");
                              exerciseObjects.add(exerciseObject);
                            } catch (JSONException error) {
                                error.printStackTrace();
                            }
                        }
                        adapter.addAll(exerciseObjects);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonArrayRequest);
        listView.setAdapter(adapter);
    }

    // request to server database
    private void sendToServer(final String title, final String sets, final String reps, final String description, final String timestamp, final String longitude, final String latitude) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://young-temple-12802.herokuapp.com/exercises";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getAllExerciseObjects();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
          @Override
          protected Map<String, String> getParams()
          {
            Map<String, String> params = new HashMap<String, String>();
            params.put("title", title);
            params.put("sets", sets);
            params.put("reps", reps);
            params.put("description", description);
            params.put("timestamp", timestamp);
            params.put("longitude", longitude);
            params.put("latitude", latitude);
            return params;
          }
        };
        queue.add(stringRequest);
    }

  // get the users location
  public void getUserLocation() {
    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
              PERMISSION_ID);
    } else {
      mFusedLocationClient.getLastLocation()
              .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                  if (location != null) {
                    Log.d("location", "Latitude is: " + location.getLatitude());
                    Log.d("location", "Longitude is: " + location.getLongitude());
                    lastLocation = location;
                  } else {
                    Log.i("Location", "Location Unknown");
                  }
                }
              });
    }
  }

  // location permission request
  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_ID: {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getUserLocation();
        } else {
          // permission denied
          latitude = "Unknown";
          longitude = "Unknown";
        }
        return;
      }
    }
  }

// show shared preference for username
  public void showUsername() {
    Context context = this;
    SharedPreferences sharedPref = context.getSharedPreferences(
            getString(R.string.username), Context.MODE_PRIVATE);
    String username = sharedPref.getString(getString(R.string.username), "Enter Username on homepage");
    TextView userData = findViewById(R.id.username);
    userData.setText("Hello, " + username);
  }

}
