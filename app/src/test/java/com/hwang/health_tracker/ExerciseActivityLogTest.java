package com.hwang.health_tracker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.room.Room;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExerciseActivityLogTest {

    @Rule
    public ActivityTestRule<ExcerciseActivityLog> mActivityRule =
            new ActivityTestRule(MyClass.class);

    AppDatabase database;
    Exercise exercise;

    @Before
    public void setUpDataBase(){
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }


    @Test
    public void createExercise_AddNewExerciseObject_ReturnsTrue(){

    }
}
