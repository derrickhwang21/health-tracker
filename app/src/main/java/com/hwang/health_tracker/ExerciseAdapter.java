package com.hwang.health_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


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

        // Lookup view for data population
        TextView exerciseName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView exerciseSets = (TextView) convertView.findViewById(R.id.textViewSets);
        TextView exerciseReps = (TextView) convertView.findViewById(R.id.textViewReps);
        TextView exerciseDescriptions = (TextView) convertView.findViewById(R.id.textViewDescription);
        TextView exerciseTimes = (TextView) convertView.findViewById(R.id.textViewTime);
        TextView exerciseLongitude = (TextView) convertView.findViewById(R.id.textViewLongitude);
        TextView exercuseLatitude = (TextView) convertView.findViewById(R.id.textViewLatitude);
        // Populate the data into the template view using the data object
        exerciseName.setText(exercise.title);
        exerciseSets.setText(exercise.sets);
        exerciseReps.setText(exercise.reps);
        exerciseDescriptions.setText(exercise.description);
        exerciseTimes.setText(exercise.timestamp);
        exerciseLongitude.setText(Double.toString(exercise.longitude));
        exerciseTimes.setText(Double.toString(exercise.latitude));
        // Return the completed view to render on screen
        return convertView;
    }
}
