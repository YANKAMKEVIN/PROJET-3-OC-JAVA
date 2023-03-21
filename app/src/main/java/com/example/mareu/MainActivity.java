package com.example.mareu;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.ui.add.AddMeetingActivity;
import com.example.mareu.ui.list.LocationFilterEvent;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
                showFilterDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFilterDialog() {
        // Show a dialog or dropdown list to allow the user to select a location to filter the list of meetings
        // Then update the list of meetings based on the selected location
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setItems(R.array.salles, (dialog, which) -> {
            String[] locations = getResources().getStringArray(R.array.salles);
            String selectedLocation = locations[which];
            EventBus.getDefault().post(new LocationFilterEvent(selectedLocation));
        });
        builder.show();
    }
}