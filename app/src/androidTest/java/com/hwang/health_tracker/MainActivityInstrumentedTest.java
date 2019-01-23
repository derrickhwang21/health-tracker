package com.hwang.health_tracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTest {

  @Rule
  public ActivityTestRule<MainActivity> displayMainActivityTestRule =
          new ActivityTestRule<>(MainActivity.class);


  @Test
  public void testOnCreate() {

    onView(allOf(withId(R.id.buttonLoadPicture), withText("Load Picture")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.btn_workout), withText("Go To Workout")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.btn_takepicture), withText("Take A Picture")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.button2), withText("Exercise Log")))
            .check(matches(isDisplayed()));


  }
}
