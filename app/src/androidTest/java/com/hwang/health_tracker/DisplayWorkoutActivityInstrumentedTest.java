package com.hwang.health_tracker;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
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



  @Test
  public void buttonIsEnabled() {
    onView(withId(R.id.button)).check(matches(isEnabled()));
    onView(withId(R.id.btn_start)).check(matches(isEnabled()));
    onView(withId(R.id.btn_stop)).check(matches(isEnabled()));
    onView(withId(R.id.btn_reset)).check(matches(isEnabled()));
  }

  @Test
  public void buttonIsDisplayed() {
    onView(withId(R.id.button)).check(matches(isDisplayed()));
    onView(withId(R.id.btn_start)).check(matches(isDisplayed()));
    onView(withId(R.id.btn_stop)).check(matches(isDisplayed()));
    onView(withId(R.id.btn_reset)).check(matches(isDisplayed()));
  }

  @Test
  public void buttonIsCompletelyDisplayed() {
    onView(withId(R.id.button)).check(matches(isCompletelyDisplayed()));
    onView(withId(R.id.btn_start)).check(matches(isCompletelyDisplayed()));
    onView(withId(R.id.btn_stop)).check(matches(isCompletelyDisplayed()));
    onView(withId(R.id.btn_reset)).check(matches(isCompletelyDisplayed()));
  }

  @Test
  public void buttonIsNotSelectable() {
    onView(withId(R.id.button)).check(matches(not(isSelected())));
    onView(withId(R.id.btn_start)).check(matches(not(isSelected())));
    onView(withId(R.id.btn_stop)).check(matches(not(isSelected())));
    onView(withId(R.id.btn_reset)).check(matches(not(isSelected())));
  }

  @Test
  public void buttonIsClickable() {
    onView(withId(R.id.button)).check(matches(isClickable()));
    onView(withId(R.id.btn_start)).check(matches(isClickable()));
    onView(withId(R.id.btn_stop)).check(matches(isClickable()));
    onView(withId(R.id.btn_reset)).check(matches(isClickable()));
  }
}
