package com.hwang.health_tracker;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcerciseActivityLog extends AppCompatActivity {
    AppDatabase database;
    ListView listView;
    String exerciseNameList[] = {"Burpees", "Pushup"};
    int setList[] = {1, 2};
    int repList[] = {10, 12};
    String descriptionList[] = {"All day", "You got this"};


    String[] nameArray;
    int[] setArray;
    int[] repArray;
    String[] descriptionArray;
    String[] timesArray;

    String TAG = "log test string";
    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        if(database.excerciseDao().getAll().isEmpty()){
            for(int i = 0; i < exerciseNameList.length; i++) {
                Date currentTime = Calendar.getInstance().getTime();
                database.excerciseDao().add(new Exercise(exerciseNameList[i], setList[i], repList[i], descriptionList[i], currentTime.toString()));
//                database.excerciseDao().add(new Exercise(exerciseNameList[i], setList[i], repList[i], descriptionList[i], currentTime[i]));
                Log.d(TAG, "Line 41: ");
            }
        }
        Log.d(TAG, "Line 44: ");

        // grabbing list data from room to array
        List<Exercise> exercises = database.excerciseDao().getAll();
        nameArray = new String[exercises.size()];
        setArray = new int[exercises.size()];
        repArray = new int[exercises.size()];
        descriptionArray = new String[exercises.size()];
        timesArray = new String[exercises.size()];

        for (int i = 0; i < exercises.size(); i++){
            String name = exercises.get(i).title;
            int sets = exercises.get(i).sets;
            int reps = exercises.get(i).reps;
            String descriptions = exercises.get(i).description;
            String times = exercises.get(i).timestamp;

            nameArray[i] = name;
            setArray[i] = sets;
            repArray[i] = reps;
            descriptionArray[i] = descriptions;
            timesArray[i] = times;

        }

        listView = (ListView) findViewById(R.id.excerciseListView);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), nameArray, setArray, repArray, descriptionArray, timesArray);
        listView.setAdapter(customAdapter);

    }
}
