package com.example.mareu.add;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.TestUtils.waitFor;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.mareu.MainActivity;
import com.example.mareu.R;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddMeetingTest {
    private int year;
    private int month;
    private int day;
    private int hour;

    private static int ITEMS_COUNT = 10;

    private static final String MEETING_TITLE = "Réunion test";
    private static final String MEETING_SUBJECT = "Description de réunion test";
    private static final String ROOM = "Salle 3";
    private static final String PARTICIPANT_MAIL = "john@lamzone.fr";
    private static final String INVALID_PARTICIPANT_MAIL = "jôhИ@do€..fr";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR) + 1;
    }
    @Test
    public void onFillEveryFieldCorrectly_clickCreateBtn_shouldAddNewMeeting() {
        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(ITEMS_COUNT)));

        onView(isRoot()).perform(waitFor(5000));


        onView(withId(R.id.main_fab_add)).perform(click());

        onView(withId(R.id.add_meeting_time_picker_button)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(22,0));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.add_meeting_date_picker_button)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month, day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.add_meeting_input_participant_text)).perform(replaceText(PARTICIPANT_MAIL));
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        onView(withId(R.id.add_meeting_location_tiet)).perform(click());
        onView(withText(ROOM)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.add_meeting_name_tiet)).perform(replaceText(MEETING_TITLE));

        onView(withId(R.id.ad_meeting_subject_tiet)).perform(replaceText(MEETING_SUBJECT));

        pressBack();

        onView(withId(R.id.add_meeting_button)).perform(click());
        pressBack();
        onView(isRoot()).perform(waitFor(10000));

        onView(withId(R.id.list_meetings_rv)).check(matches(hasChildCount(ITEMS_COUNT )));
    }

    @Test
    public void onAddNoInfo_shouldSubmitButtonBeDisabled() {
        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.add_meeting_button)).check(matches(not(isEnabled())));
        onView(withId(R.id.activity_add_meeting)).check(matches(isDisplayed()));
    }


    @Test
    public void onFillEveryFieldCorrectly_whenFilled_shouldDisplayChosenInfo() {
        onView(withId(R.id.main_fab_add)).perform(click());

        onView(withId(R.id.add_meeting_date_picker_button)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2024, 11, 10));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_date_picker_text)).check(matches(withText("10/11/2024")));

        onView(withId(R.id.add_meeting_time_picker_button)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(13, 0));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_meeting_time_picker_text)).check(matches(withText("13:00")));


        onView(withId(R.id.add_meeting_location_tiet)).perform(click());
        onView(withText(ROOM)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.add_meeting_location_tiet)).check(matches(withText(ROOM)));

        onView(withId(R.id.add_meeting_name_tiet)).perform(replaceText(MEETING_TITLE));
        onView(withId(R.id.add_meeting_name_tiet)).check(matches(withText(MEETING_TITLE)));

        onView(withId(R.id.ad_meeting_subject_tiet)).perform(replaceText(MEETING_SUBJECT));
        onView(withId(R.id.ad_meeting_subject_tiet)).check(matches(withText(MEETING_SUBJECT)));

        pressBack();

        onView(withId(R.id.add_meeting_input_participant_text)).perform(replaceText(PARTICIPANT_MAIL));
        onView(withId(R.id.add_meeting_participants_button)).perform(click());
        onView(allOf(withText(PARTICIPANT_MAIL), withParent(allOf(withId(R.id.add_meeting_participants_list),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))),
                isDisplayed()));
    }
/*

    @Test
    public void onAddingInvalidMail_shouldDisplayError() {
        onView(withId(R.id.main_fab_add)).perform(click());

        onView(withId(R.id.participants_input)).perform(replaceText(INVALID_PARTICIPANT_MAIL));
        onView(withId(R.id.add_participant_fab)).perform(click());
        onView(withId(R.id.participants_layout)).check(matches(hasDescendant(
                withText(R.string.invalid_email_input))));
    }*/
}
