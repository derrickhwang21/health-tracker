package com.hwang.health_tracker;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ExcerciseActivityLog extends AppCompatActivity {
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").allowMainThreadQueries().build();

        if(database.excerciseDao().getById(0) == null){
            Date currentTime = Calendar.getInstance().getTime();
            database.excerciseDao().add(new Exercise("Burpees", 3, 10, "burpees all day!", currentTime.toString()));
        }

        TextView textView = findViewById(R.id.excerciseLog);
        Exercise exercise = database.excerciseDao().getById(0);
        textView.setText(exercise.title);
    }
}
