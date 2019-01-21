package com.hwang.health_tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class DisplayWorkoutActivityInstrumentedTest {

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
        onView(withId(R.id.button)).perform(click());

        onView(withText("Button Clicked first time")).
                        inRoot(withDecorView(not(displayWorkoutActivityTestRule.getActivity().getWindow().getDecorView()))).
                        check(matches(isDisplayed()));
            }


}
