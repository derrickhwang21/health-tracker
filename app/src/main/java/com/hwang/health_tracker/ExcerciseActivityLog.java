package com.hwang.health_tracker;

import androidx.room.Room;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class ExcerciseActivityLog extends AppCompatActivity {
    AppDatabase database;

    Date currentTime = Calendar.getInstance().getTime();



    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        setContentView(R.layout.activity_exercise_log);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        createTestExercise();
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, database.excerciseDao().getAll());
        ListView listView = (ListView) findViewById(R.id.excerciseListView);
        listView.setAdapter(adapter);



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
        database.excerciseDao().add(userInputExerciseObject);

        // clear edit field text
        UserInputExerciseName.setText("");
        userInputExerciseSets.setText("");
        userInputExerciseReps.setText("");
        userInputExerciseDescription.setText("");

        // renders listview
        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, database.excerciseDao().getAll());
        ListView listView = (ListView) findViewById(R.id.excerciseListView);
        listView.setAdapter(adapter);

        }




}
