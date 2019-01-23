package com.hwang.health_tracker;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplayWorkoutActivityInstrumentedTest {


  //    @Rule
//    public IntentsTestRule<DisplayWorkout> displayWorkoutActivityTestRule =
//            new IntentsTestRule<>(DisplayWorkout.class);
  @Rule
  public ActivityTestRule<DisplayWorkout> displayWorkoutActivityTestRule =
          new ActivityTestRule<>(DisplayWorkout.class);


  @Test
  public void testOnCreate() {

    onView(allOf(withId(R.id.button), withText("Start Exercise")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.btn_start), withText("Start")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.btn_stop), withText("Stop")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.btn_reset), withText("Reset")))
            .check(matches(isDisplayed()));


  }

  @Test
  public void testFingerExercise() {

    //Test that things change when button is clicked
    for (int i = 1; i < 30; i++) {
      int buttonClick = 0;
      onView(withId(R.id.button)).perform(click());
      if (i == 1) {
        onView(withText("Button Clicked first time")).
                inRoot(withDecorView(not(displayWorkoutActivityTestRule.getActivity().getWindow().getDecorView()))).
                check(matches(isDisplayed()));
      }
      if (i == 5){
        onView(withText("Button clicked count is 5"));
      }
      if (i == 29){
        onView(withText("Button clicked count is 29"));
      }

    }
  }
}
