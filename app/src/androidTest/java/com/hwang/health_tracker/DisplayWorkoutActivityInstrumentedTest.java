package com.hwang.health_tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplayWorkoutActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<DisplayWorkout> displayWorkoutActivityTestRule =
            new ActivityTestRule(DisplayWorkout.class);


    @Test
    public void testOnCreate() {

        onView(withId(R.id.button))
                .check(matches(withText("Start Exercise")));
        onView(withId(R.id.btn_start))
                .check(matches(withText("Start")));
        onView(withId(R.id.btn_stop))
                .check(matches(withText("Stop")));
        onView(withId(R.id.btn_reset))
                .check(matches(withText("Reset")));
    }
    @Test
    public void testFingerExercise() {

        //Test that things change when button is clicked
        onView(withId(R.id.button)).perform(click());

        onView(withText("Button Clicked first time")).
                        inRoot(withDecorView(not(displayWorkoutActivityTestRule.getActivity().getWindow().getDecorView()))).
                        check(matches(isDisplayed()));
            }


}
