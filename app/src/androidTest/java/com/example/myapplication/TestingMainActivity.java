package com.example.myapplication;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import android.app.Activity;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestingMainActivity {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void checkHomView() {
//        onData(allOf(is(instanceOf(String.class)), is("task 1"))).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Home")));
    }

    @Test
    public void settingsButton() {
        onView(withId(R.id.settingsButton)).perform(click());
    }

    @Test
    public void recyclerTest(){
        onView(withId(R.id.tasksRecyclerView)).perform(click());
    }

    @Test
    public void adapterTest(){
        onView(allOf(withId(R.id.taskTitleViewFragment), withText("task 1"))).perform(click());
    }
}
