package com.hwang.health_tracker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTest {

  @Rule
  public ActivityTestRule<MainActivity> displayMainActivityTestRule =
          new ActivityTestRule<>(MainActivity.class);


  @Test
  public void testOnCreateButton() {

    onView(allOf(withId(R.id.buttonLoadPicture), withText("Load Picture")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.btn_workout), withText("Go to Workout")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.button3), withText("Take A Picture")))
            .check(matches(isDisplayed()));
    onView(allOf(withId(R.id.button2), withText("Exercise Log")))
            .check(matches(isDisplayed()));
  }

  @Test
  public void buttonIsEnabled() {
    onView(withId(R.id.buttonLoadPicture)).check(matches(isEnabled()));
    onView(withId(R.id.btn_workout)).check(matches(isEnabled()));
    onView(withId(R.id.button3)).check(matches(isEnabled()));
    onView(withId(R.id.button2)).check(matches(isEnabled()));
  }

  @Test
  public void buttonIsDisplayed() {
    onView(withId(R.id.buttonLoadPicture)).check(matches(isDisplayed()));
    onView(withId(R.id.btn_workout)).check(matches(isDisplayed()));
    onView(withId(R.id.button3)).check(matches(isDisplayed()));
    onView(withId(R.id.button2)).check(matches(isDisplayed()));
  }

  @Test
  public void buttonIsCompletelyDisplayed() {
    onView(withId(R.id.buttonLoadPicture)).check(matches(isCompletelyDisplayed()));
    onView(withId(R.id.btn_workout)).check(matches(isCompletelyDisplayed()));
    onView(withId(R.id.button3)).check(matches(isCompletelyDisplayed()));
    onView(withId(R.id.button2)).check(matches(isCompletelyDisplayed()));
  }

  @Test
  public void buttonIsNotSelectable() {
    onView(withId(R.id.buttonLoadPicture)).check(matches(not(isSelected())));
    onView(withId(R.id.btn_workout)).check(matches(not(isSelected())));
    onView(withId(R.id.button3)).check(matches(not(isSelected())));
    onView(withId(R.id.button2)).check(matches(not(isSelected())));
  }

  @Test
  public void buttonIsClickable() {
    onView(withId(R.id.buttonLoadPicture)).check(matches(isClickable()));
    onView(withId(R.id.btn_workout)).check(matches(isClickable()));
    onView(withId(R.id.button3)).check(matches(isClickable()));
    onView(withId(R.id.button2)).check(matches(isClickable()));
  }

  @Test
  public void testMenuItem(){
    openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

    onView(withText("5 seconds")).check(matches(isDisplayed()));
    onView(withText("10 seconds")).check(matches(isDisplayed()));
    onView(withText("30 seconds")).check(matches(isDisplayed()));
  }
}
