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
//      Intent intent = new Intent();
//      String expected = "Start Exercise";
//      intent.putExtra("text", expected);
//      Instrumentation.ActivityResult result =
//              new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
//
//      intending(toPackage("com.hwang.health_tracker")).respondWith(result);

      Intents.assertNoUnverifiedIntents();

//        onView(allOf(withId(R.id.button), withText("Start Exercise")))
//                .check(matches(isDisplayed()));
//        onView(allOf(withId(R.id.btn_start), withText("Start")))
//                .check(matches(isDisplayed()));
//        onView(allOf(withId(R.id.btn_stop), withText("Stop")))
//                .check(matches(isDisplayed()));
//        onView(allOf(withId(R.id.btn_reset), withText("Reset")))
//                .check(matches(isDisplayed()));


//        onView(withId(R.id.btn_start))
//                .check(matches(withText("Start")));
//        onView(withId(R.id.btn_stop))
//                .check(matches(withText("Stop")));
//        onView(withId(R.id.btn_reset))
//                .check(matches(withText("Reset")));
    }
//    @Test
//    public void testFingerExercise() {
//
//        //Test that things change when button is clicked
//        onView(withId(R.id.button)).perform(click());
//
//        onView(withText("Button Clicked first time")).
//                        inRoot(withDecorView(not(displayWorkoutActivityTestRule.getActivity().getWindow().getDecorView()))).
//                        check(matches(isDisplayed()));
//            }
//

}
