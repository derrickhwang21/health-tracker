package com.hwang.health_tracker;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PictureActivityInstrumentedTest {

  @Rule
  public ActivityTestRule<PictureActivity> pictureActivityTestRule =
          new ActivityTestRule<>(PictureActivity.class);

  @Test
  public void testSavedToExternalStorage(){
    onView(allOf(withId(R.id.btn_takepicture), withText("TAKE PICTURE")))
            .check(matches(isDisplayed()));
    onView(withId(R.id.btn_takepicture)).perform(click());
    onView(withText("Saved:/storage/emulated/0/pic.jpg")).
            inRoot(withDecorView(not(pictureActivityTestRule.getActivity().getWindow().getDecorView()))).
            check(matches(isDisplayed()));
  }


}
