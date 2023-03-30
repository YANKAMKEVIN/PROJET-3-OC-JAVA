package com.example.mareu.main;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.TestUtils.withRecyclerView;
import static org.hamcrest.CoreMatchers.equalTo;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.mareu.MainActivity;
import com.example.mareu.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    // This is fixed
    private static int ITEMS_COUNT = 10;
    private static final String ROOM_SIX = "Salle 6";
    private static final String MEETING_SIX_NAME = "REUNION F";
    private static final String MEETING_SIX_HOUR = "14:00";

    private int year;
    private int month;
    private int day;


    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
    }


    @Test
    public void isRecyclerViewVisible_onAppLaunch() {
        onView(withId(R.id.list_meetings_rv)).check(matches(isDisplayed()));
    }

   @Test
    public void meetingRecyclerView_shouldHaveAtLeastOneItem() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void onDeleteOneListItem_shouldRemoveOneItem() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(ITEMS_COUNT)));
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(ITEMS_COUNT - 1)));
    }

    @Test
    public void onDeleteAllListItems_shouldDisplayNoMeeting() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(10)));

        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());
        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.item_list_delete_button))
                .perform(click());

        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(0)));

    }

    @Test
    public void onClickFab_ShouldStartCreateActivity() {
        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.activity_add_meeting)).check(matches(isDisplayed()));
    }

    @Test
    public void onFilterRoom4_shouldDisplayFilteredMeetings() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(10)));
        onView(withId(R.id.menu_filter)).check(matches(isDisplayed())).perform(click());

        onView(withText(R.string.filter_by_location)).perform(click());
        onView(withText(ROOM_SIX)).perform(click());
        onView(withId(R.id.list_meetings_rv)).perform(scrollToPosition(0));

        onView(withRecyclerView(R.id.list_meetings_rv)
                .atPositionOnView(0, R.id.meeting_name_hour_location))
                .check(matches(withText(MEETING_SIX_NAME+" - "+MEETING_SIX_HOUR+" - "+ROOM_SIX)));

        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(1)));
    }

    @Test
    public void onFilterDate_shouldDisplayFilteredMeetings() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(10)));
        onView(withId(R.id.menu_filter)).check(matches(isDisplayed())).perform(click());

        onView(withText(R.string.filter_by_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withId(android.R.id.button1)).perform(doubleClick());

        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(1)));
    }

   @Test
    public void onFilterDate_resetFilter_shouldResetFilter() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(10)));
        onView(withId(R.id.menu_filter)).check(matches(isDisplayed())).perform(click());

        onView(withText(R.string.filter_by_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year,month,day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(1)));

        onView(withId(R.id.menu_filter)).check(matches(isDisplayed())).perform(click());
        onView(withText(R.string.remove_filters)).perform(click());

        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(10)));
    }

}
