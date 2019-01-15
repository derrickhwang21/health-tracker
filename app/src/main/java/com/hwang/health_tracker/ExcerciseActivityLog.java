package com.hwang.health_tracker;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcerciseActivityLog extends AppCompatActivity {
    AppDatabase database;
    ListView listView;
    String exerciseNameList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    String[] nameArray1;


    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();


        Date currentTime = Calendar.getInstance().getTime();
        database.excerciseDao().add(new Exercise(exerciseNameList[1], 3, 10, "burpees all day!", currentTime.toString()));
        database.excerciseDao().add(new Exercise(exerciseNameList[2], 3, 10, "burpees all day!", currentTime.toString()));
        database.excerciseDao().add(new Exercise(exerciseNameList[3], 3, 10, "burpees all day!", currentTime.toString()));
        database.excerciseDao().add(new Exercise(exerciseNameList[4], 3, 10, "burpees all day!", currentTime.toString()));

//        if(database.excerciseDao().getAll().isEmpty()){
//            Date currentTime = Calendar.getInstance().getTime();
//            for(int i = 0; i < nameArray1.length; i++) {



//            }
//        }

//        TextView textView = findViewById(R.id.excerciseLog);


        List<Exercise> exercises = database.excerciseDao().getAll();
        nameArray1 = new String[exercises.size()];
        int sizeOfExercises = exercises.size();


        Log.d(getClass().getName(), "Size: " + sizeOfExercises);
        for (int i = 0; i < exercises.size(); i++){
            String name = exercises.get(i).title;
            Log.d(name, "onCreate: ");
            nameArray1[i] = name;
        }

//        String[] nameArray = exercises.get(i).title.toArray(new String[exercises.size()]);

        listView = (ListView) findViewById(R.id.excerciseListView);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), nameArray1);
        listView.setAdapter(customAdapter);

    }
}
