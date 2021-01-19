package com.example.AudientesAPP.UI;


import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.AudientesAPP.R;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Library_Main_EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void library_Main_EspressoTest() {
        ViewInteraction textView = onView(
                allOf(withText("CATEGORIES"),
                        withParent(allOf(withContentDescription("Categories"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("CATEGORIES")));

        ViewInteraction textView2 = onView(
                allOf(withText("SOUNDS"),
                        withParent(allOf(withContentDescription("Sounds"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("SOUNDS")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.icon),
                        withParent(allOf(withId(R.id.preset_nav), withContentDescription("Presets"),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.hearingTest_nav), withContentDescription("Hearingtest"),
                        withParent(withParent(withId(R.id.bottom_navigation))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.largeLabel), withText("Library"),
                        withParent(withParent(allOf(withId(R.id.library_nav), withContentDescription("Library")))),
                        isDisplayed()));
        textView3.check(matches(withText("Library")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.playbar_playbutton),
                        withParent(withParent(withId(R.id.playBar))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));
    }
}
