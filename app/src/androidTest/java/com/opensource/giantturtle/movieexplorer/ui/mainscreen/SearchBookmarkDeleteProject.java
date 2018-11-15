package com.opensource.giantturtle.movieexplorer.ui.mainscreen;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.opensource.giantturtle.movieexplorer.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchBookmarkDeleteProject {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void closeIfFirstRun(){
        //simulate first loading
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //click OK in disclaimer if it is first run
        try {
            ViewInteraction okButton = onView(
                    allOf(withId(android.R.id.button3), withText(R.string.ok_app_info_dialog),
                            childAtPosition(
                                    childAtPosition(
                                            allOf(withId(R.id.buttonPanel),
                                                    childAtPosition(
                                                            allOf(withId(R.id.parentPanel),
                                                                    childAtPosition(
                                                                            withId(android.R.id.content),
                                                                            0)),
                                                            3)),
                                            0),
                                    0)));
            okButton.perform(ViewActions.scrollTo(), click());
        } catch (NoMatchingViewException e){
            e.printStackTrace();
        }
    }

    @Test
    public void searchBookmarkDelete() {

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search_toolbar), withContentDescription(R.string.search_title),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.toolbar),
                                                childAtPosition(
                                                        childAtPosition(
                                                                withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                                                0),
                                                        0)),
                                        4),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                allOf(withId(R.id.search_edit_frame),
                                                        childAtPosition(
                                                                allOf(withId(R.id.search_bar),
                                                                        childAtPosition(
                                                                                withId(R.id.search_toolbar),
                                                                                0)),
                                                                2)),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("GithubClient"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text), withText("GithubClient"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                allOf(withId(R.id.search_edit_frame),
                                                        childAtPosition(
                                                                allOf(withId(R.id.search_bar),
                                                                        childAtPosition(
                                                                                withId(R.id.search_toolbar),
                                                                                0)),
                                                                2)),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0),
                                        1),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.action_button), withText(R.string.bookmark_button_text),
                        childAtPosition(
                                allOf(withId(R.id.entire_view),
                                        childAtPosition(
                                                allOf(withId(R.id.card_view_entire_row),
                                                        childAtPosition(
                                                                allOf(withId(R.id.recycler_view),
                                                                        childAtPosition(
                                                                                withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                                                                0)),
                                                                0)),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Collapse"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                childAtPosition(
                                                        childAtPosition(
                                                                withId(R.id.drawer_layout),
                                                                0),
                                                        0),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                childAtPosition(
                                                        childAtPosition(
                                                                withId(R.id.drawer_layout),
                                                                0),
                                                        0),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        allOf(withId(R.id.nav_view),
                                                childAtPosition(
                                                        allOf(withId(R.id.drawer_layout),
                                                                childAtPosition(
                                                                        withId(android.R.id.content),
                                                                        0)),
                                                        1)),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.action_button), withText(R.string.delete_button_text),
                        childAtPosition(
                                allOf(withId(R.id.entire_view),
                                        childAtPosition(
                                                allOf(withId(R.id.card_view_entire_row),
                                                        childAtPosition(
                                                                allOf(withId(R.id.recycler_view_save),
                                                                        childAtPosition(
                                                                                withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                                                                0)),
                                                                0)),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                childAtPosition(
                                                        childAtPosition(
                                                                withId(android.R.id.content),
                                                                0),
                                                        0),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
