package com.hwang.health_tracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Exercise.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExcerciseDao excerciseDao();
}
