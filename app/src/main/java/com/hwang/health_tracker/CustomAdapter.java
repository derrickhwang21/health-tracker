package com.hwang.health_tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String exerciseNameList[];
    int exerciseSetList[];
    int exerciseRepsList[];
    String exerciseDescriptionList[];
    String exerciseTimeList[];
    LayoutInflater layInflater;

    public CustomAdapter(Context applicationContext, String[] exerciseNameList, int[] exerciseSetList, int[] exerciseRepsList, String[] exerciseDescriptionList, String[] exerciseTimeList ){
        this.context = applicationContext;
        this.exerciseNameList = exerciseNameList;
        this.exerciseSetList = exerciseSetList;
        this.exerciseRepsList = exerciseRepsList;
        this.exerciseDescriptionList = exerciseDescriptionList;
        this.exerciseTimeList = exerciseTimeList;

        layInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount(){
        return exerciseNameList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view = layInflater.inflate(R.layout.activity_exercise_list_view, null);
        TextView exerciseName = (TextView) view.findViewById(R.id.textViewName);
        TextView exerciseSets = (TextView) view.findViewById(R.id.textViewSets);
        TextView exerciseReps = (TextView) view.findViewById(R.id.textViewReps);
        TextView exerciseDescriptions = (TextView) view.findViewById(R.id.textViewDescription);
        TextView exerciseTimes = (TextView) view.findViewById(R.id.textViewTime);
        exerciseName.setText(exerciseNameList[i]);
        exerciseSets.setText(exerciseSetList[i]);
        exerciseReps.setText(exerciseRepsList[i]);
        exerciseDescriptions.setText(exerciseDescriptionList[i]);
        exerciseTimes.setText(exerciseTimeList[i]);
        return view;
    }
}
