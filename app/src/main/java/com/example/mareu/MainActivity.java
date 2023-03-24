package com.example.mareu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.ui.add.AddMeetingActivity;
import com.example.mareu.ui.list.DateFilterEvent;
import com.example.mareu.ui.list.LocationFilterEvent;
import com.example.mareu.ui.list.ResetFilterEvent;

import org.greenrobot.eventbus.EventBus;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.mainFabAdd.setOnClickListener(v -> startActivity(AddMeetingActivity.navigate(this)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meeting_filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_filter_by_location:
                showLocationFilterDialog();
                return true;
            case R.id.action_filter_by_date:
                showDateFilterDialog();
                return true;
            case R.id.action_reset_filter:
                resetFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetFilterDialog() {
        EventBus.getDefault().post(new ResetFilterEvent());
    }

    private void showLocationFilterDialog() {
        // Show a dialog or dropdown list to allow the user to select a location to filter the list of meetings
        // Then update the list of meetings based on the selected location
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.app_name);
        builder.setItems(R.array.rooms, (dialog, which) -> {
            String[] locations = getResources().getStringArray(R.array.rooms);
            String selectedLocation = locations[which];
            EventBus.getDefault().post(new LocationFilterEvent(selectedLocation));
        });
        builder.show();
    }

    private void showDateFilterDialog() {
        // Show a dialog to allow the user to select a date to filter the list of meetings
        // Then update the list of meetings based on the selected date
        LocalDate today = LocalDate.now();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
            EventBus.getDefault().post(new DateFilterEvent(selectedDate));
        }, today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        dialog.show();
    }
}