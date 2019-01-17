package com.hwang.health_tracker;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ExcerciseActivityLog extends AppCompatActivity {
    AppDatabase database;



    String[] nameArray;
    int[] setArray;
    int[] repArray;
    String[] descriptionArray;
    String[] timesArray;
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

//    public void createExercise(ExerciseAdapter adapter) {
//        EditText UserInputExerciseName = findViewById(R.id.exercise_name);
//        EditText userInputExerciseSets = findViewById(R.id.exercise_sets);
//        EditText userInputExerciseReps = findViewById(R.id.exercise_reps);
//        EditText userInputExerciseDescription = findViewById(R.id.exercise_description);
//
//        Exercise userInputExerciseObject = new Exercise(UserInputExerciseName.getText().toString(), Integer.parseInt(userInputExerciseSets.getText().toString()), Integer.parseInt(userInputExerciseReps.getText().toString()), userInputExerciseDescription.getText().toString(), currentTime.toString());
//
//        adapter.add(userInputExerciseObject);
//
//        UserInputExerciseName.setText("");
//        userInputExerciseSets.setText("");
//        userInputExerciseReps.setText("");
//        userInputExerciseDescription.setText("");
//
//        ListView listView = (ListView) findViewById(R.id.excerciseListView);
//        listView.setAdapter(adapter);

        public void createExercise(View v) {

        EditText UserInputExerciseName = findViewById(R.id.exercise_name);
        EditText userInputExerciseSets = findViewById(R.id.exercise_sets);
        EditText userInputExerciseReps = findViewById(R.id.exercise_reps);
        EditText userInputExerciseDescription = findViewById(R.id.exercise_description);

        Exercise userInputExerciseObject = new Exercise(UserInputExerciseName.getText().toString(), Integer.parseInt(userInputExerciseSets.getText().toString()), Integer.parseInt(userInputExerciseReps.getText().toString()), userInputExerciseDescription.getText().toString(), currentTime.toString());

            ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, database.excerciseDao().getAll());
            ListView listView = (ListView) findViewById(R.id.excerciseListView);
            listView.setAdapter(adapter);

//        UserInputExerciseName.setText("");
//        userInputExerciseSets.setText("");
//        userInputExerciseReps.setText("");
//        userInputExerciseDescription.setText("");
//
//        nameArray = new String[exercises.size()];
//        setArray = new int[exercises.size()];
//        repArray = new int[exercises.size()];
//        descriptionArray = new String[exercises.size()];
//        timesArray = new String[exercises.size()];
//
//        for (int i = 0; i < exercises.size(); i++){
//            String name = exercises.get(i).title;
//            int sets = exercises.get(i).sets;
//            int reps = exercises.get(i).reps;
//            String descriptions = exercises.get(i).description;
//            String times = exercises.get(i).timestamp;
//
//            nameArray[i] = name;
//            setArray[i] = sets;
//            repArray[i] = reps;
//            descriptionArray[i] = descriptions;
//            timesArray[i] = times;
        }




}
