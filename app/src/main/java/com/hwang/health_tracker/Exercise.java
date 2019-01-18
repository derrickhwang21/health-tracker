package com.hwang.health_tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String title;
    public int sets;
    public int reps;
    public String description;
    public String timestamp;

    public Exercise(String title, int sets, int reps, String description, String timestamp){
        this.title = title;
        this.sets = sets;
        this.reps = reps;
        this.description = description;
        this.timestamp = timestamp;
    }

//    public Exercise(){}

    public String toString(){
        return "Exercise name: " + title + " Sets: " + sets + " Reps: " + reps + " Description: " + description + " Time: " + timestamp;
    }


}
