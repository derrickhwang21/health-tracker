package com.hwang.health_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {
    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises){
        super(context, 0, exercises);
    }
// credit: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        // Get the data item for this position
        Exercise exercise = getItem(position);
// Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_list_view, parent, false);
        }

//        Button btButton = (Button) convertView.findViewById(R.id.button_save);
//        btButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Date currentTime = Calendar.getInstance().getTime();
//                EditText UserInputExerciseName = view.findViewById(R.id.exercise_name);
//                EditText userInputExerciseSets = view.findViewById(R.id.exercise_sets);
//                EditText userInputExerciseReps = view.findViewById(R.id.exercise_reps);
//                EditText userInputExerciseDescription = view.findViewById(R.id.exercise_description);
//
//                Exercise userInputExerciseObject = new Exercise(UserInputExerciseName.getText().toString(), Integer.parseInt(userInputExerciseSets.getText().toString()), Integer.parseInt(userInputExerciseReps.getText().toString()), userInputExerciseDescription.getText().toString(), currentTime.toString());
//
//
//
//                view.set (userInputExerciseObject);
//
//                UserInputExerciseName.setText("");
//                userInputExerciseSets.setText("");
//                userInputExerciseReps.setText("");
//                userInputExerciseDescription.setText("");
//            }
//        });


        // Lookup view for data population
        TextView exerciseName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView exerciseSets = (TextView) convertView.findViewById(R.id.textViewSets);
        TextView exerciseReps = (TextView) convertView.findViewById(R.id.textViewReps);
        TextView exerciseDescriptions = (TextView) convertView.findViewById(R.id.textViewDescription);
        TextView exerciseTimes = (TextView) convertView.findViewById(R.id.textViewTime);
        // Populate the data into the template view using the data object
        exerciseName.setText(exercise.title);
        exerciseSets.setText(exercise.sets);
        exerciseReps.setText(exercise.reps);
        exerciseDescriptions.setText(exercise.description);
        exerciseTimes.setText(exercise.timestamp);
        // Return the completed view to render on screen
        return convertView;
    }
}
