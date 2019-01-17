package com.hwang.health_tracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Exercise.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExcerciseDao excerciseDao();
}
