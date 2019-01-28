package com.hwang.health_tracker;

import androidx.room.Room;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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



    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();
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

        // grabds text from edittext view
        EditText UserInputExerciseName = findViewById(R.id.exercise_name);
        EditText userInputExerciseSets = findViewById(R.id.exercise_sets);
        EditText userInputExerciseReps = findViewById(R.id.exercise_reps);
        EditText userInputExerciseDescription = findViewById(R.id.exercise_description);

        // create Exercise object
        Exercise userInputExerciseObject = new Exercise(UserInputExerciseName.getText().toString(), Integer.parseInt(userInputExerciseSets.getText().toString()), Integer.parseInt(userInputExerciseReps.getText().toString()), userInputExerciseDescription.getText().toString(), currentTime.toString());

        // add new exercise object to database
        sendToServer(userInputExerciseObject.title, Integer.toString(userInputExerciseObject.sets), Integer.toString(userInputExerciseObject.reps), userInputExerciseObject.description, userInputExerciseObject.timestamp);
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

    private void sendToServer(final String title, final String sets, final String reps, final String description, final String timestamp) {

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
            return params;
          }
        };
        queue.add(stringRequest);
    }


}
