package com.hwang.health_tracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExcerciseDao {

    @Query("SELECT * FROM exercise")
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise WHERE id=:id")
    Exercise getById(long id);

    @Insert
    void add(Exercise exercise);
}
