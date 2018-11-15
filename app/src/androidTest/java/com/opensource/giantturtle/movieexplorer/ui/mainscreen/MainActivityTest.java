package com.opensource.giantturtle.movieexplorer.ui.mainscreen;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.opensource.giantturtle.movieexplorer.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class MainActivityTest {

    @Rule
    public ActivityTestRule <MainActivity> activity = new ActivityTestRule<MainActivity>(MainActivity.class);

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
    public void clickOnFirstItem() {
        ViewInteraction recyclerViewInteraction = onView(
                allOf(withId(R.id.recycler_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0),
                                        1),
                                0)));
        recyclerViewInteraction.perform(actionOnItemAtPosition(0, click()));
    }

    @Test
    public void viewCodeDetails() {
        RecyclerView recyclerView = activity.getActivity().recyclerView;
        int itemCount = recyclerView.getAdapter().getItemCount();
        ViewInteraction recyclerViewInteraction = onView(
                allOf(withId(R.id.recycler_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0),
                                        1),
                                0)));
        recyclerViewInteraction.perform(actionOnItemAtPosition(itemCount/2, click()));

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction viewCodeDetails = onView(
                allOf(withId(R.id.view_code_btn_details), withText(R.string.view_code_button),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                allOf(withId(android.R.id.content),
                                                        childAtPosition(
                                                                withId(R.id.action_bar_root),
                                                                1)),
                                                0),
                                        1),
                                2),
                        isDisplayed()));
        viewCodeDetails.perform(click());
    }

    @Test
    public void clickOnLastItem() {
        RecyclerView recyclerView = activity.getActivity().recyclerView;
        int itemCount = recyclerView.getAdapter().getItemCount();
        onView(withId(R.id.recycler_view)).perform(scrollToPosition(itemCount-1));
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(itemCount-1, click()));
    }

    @Test
    public void scrollToBottom_ShowMoreDisplayed() {
        RecyclerView recyclerView = activity.getActivity().recyclerView;
        int itemCount = recyclerView.getAdapter().getItemCount();
        onView(withId(R.id.recycler_view)).perform(scrollToPosition(itemCount-1));

        ViewInteraction buttonShowMore = onView(
                allOf(withId(R.id.show_more_button),
                        childAtPosition(
                                allOf(withId(R.id.entire_view),
                                        childAtPosition(
                                                allOf(withId(R.id.card_view_entire_row),
                                                        childAtPosition(
                                                                allOf(withId(R.id.recycler_view),
                                                                        childAtPosition(
                                                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                                                0)),
                                                                2)),
                                                0)),
                                3),
                        isDisplayed()));
        buttonShowMore.check(matches(isDisplayed()));
    }

    @Test
    public void scrollToBottom_ClickShowMore() {
        onView(withId(R.id.recycler_view)).perform(scrollTo(hasDescendant(allOf(withId(R.id.show_more_button),withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))));

        ViewInteraction showMoreButton = onView(
                allOf(withId(R.id.show_more_button),
                        childAtPosition(
                                allOf(withId(R.id.entire_view),
                                        childAtPosition(
                                                allOf(withId(R.id.card_view_entire_row),
                                                        childAtPosition(
                                                                allOf(withId(R.id.recycler_view),
                                                                        childAtPosition(
                                                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                                                0)),
                                                                2)),
                                                0)),
                                3),
                        isDisplayed()));
        showMoreButton.perform(new MyViewAction());
    }


    private static class MyViewAction implements ViewAction {

        @Override
        public Matcher<View> getConstraints() {
            return ViewMatchers.isEnabled(); // no constraints, they are checked above
        }

        @Override
        public String getDescription() {
            return "click plus button";
        }

        @Override
        public void perform(UiController uiController, View view) {
            view.performClick();
        }
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
