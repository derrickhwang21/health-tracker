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
    int setList[] = {1,2,3,4,5,6};
    String[] nameArray1;
    int[] setArray;
//    Integer[] setsArray;




    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();


        if(database.excerciseDao().getAll().isEmpty()){

            for(int i = 0; i < exerciseNameList.length; i++) {
                Date currentTime = Calendar.getInstance().getTime();
                database.excerciseDao().add(new Exercise(exerciseNameList[i], setArray[i], 10, "burpees all day!", currentTime.toString()));

            }
        }




        List<Exercise> exercises = database.excerciseDao().getAll();
        nameArray1 = new String[exercises.size()];
        setArray = new int[exercises.size()];




        for (int i = 0; i < exercises.size(); i++){
            String name = exercises.get(i).title;
            int sets = exercises.get(i).sets;

            nameArray1[i] = name;
            setArray[i] = sets;
        }

//        String[] nameArray = exercises.get(i).title.toArray(new String[exercises.size()]);

        listView = (ListView) findViewById(R.id.excerciseListView);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), nameArray1, setArray);
        listView.setAdapter(customAdapter);

    }
}
