package com.hwang.health_tracker;

import android.content.Context;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class ExerciseInstrumentedTest {
    private ExcerciseDao exerciseDao;
    private AppDatabase exerciseDatabase;

    @Before
    public void createDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        exerciseDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        exerciseDao = exerciseDatabase.excerciseDao();

    }

    @After
    public void closeDb() throws IOException{
        exerciseDatabase.close();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.hwang.health_tracker", appContext.getPackageName());
    }


    @Test
    public void writeExerciseAndReadInList() throws Exception{
        Date currentTime = Calendar.getInstance().getTime();

        Exercise exerciseTestObject1 = new Exercise("Burpees", 1, 10, "All Day!", currentTime.toString());
        Exercise exerciseTestObject2 = new Exercise("Pushup", 1, 10, "All Day!", currentTime.toString());
        Exercise exerciseTestObject3 = new Exercise("Pushup", 1, 0, "!", currentTime.toString());


        exerciseDao.add(exerciseTestObject1);
        exerciseDao.add(exerciseTestObject2);
        exerciseDao.add(exerciseTestObject3);

        Exercise exerciseTestObjectById1 = exerciseDao.getById(1);
        Exercise exerciseTestObjectById2 = exerciseDao.getById(2);
        Exercise exerciseTestObjectById3 = exerciseDao.getById(3);


        assertEquals(exerciseTestObject1.toString(), exerciseTestObjectById1.toString());
        assertEquals(exerciseTestObject2.toString(), exerciseTestObjectById2.toString());
        assertEquals(exerciseTestObject3.toString(), exerciseTestObjectById3.toString());

    }


}
