package com.example.mareu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mareu.databinding.ActivityMainBinding;
import com.example.mareu.ui.add.AddMeetingActivity;

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
}