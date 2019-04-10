package com.aorise.study;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.AutoCompleteTextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Tuliyuan.
 * Date: 2019/3/20.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Context mTargetContext;
    private AutoCompleteTextView mAutoCompleteTextView;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        mTargetContext = InstrumentationRegistry.getTargetContext();
        onView(withId(R.id.auto)).perform(typeText("what are u nong sha lie"),closeSoftKeyboard());
    }
    @Test
    public void getAutoText(){
        onView(withId(R.id.auto)).check(matches(withText("what are u nong sha lie")));
    }

}
