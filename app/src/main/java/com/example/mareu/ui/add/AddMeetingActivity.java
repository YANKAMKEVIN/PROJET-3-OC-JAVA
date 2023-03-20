package com.example.mareu.ui.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity {

    private ActivityAddMeetingBinding binding;
    private LocalDate selectedDate;
    private LocalTime selectedTime;

    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AddMeetingViewModel mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);
        setupParticipantsObserver(mViewModel);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.salles));
        binding.addMeetingLocationTiet.setAdapter(adapter);

        binding.addMeetingDatePickerButton.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                long selectedDateInMillis = selection;
                selectedDate = Instant.ofEpochMilli(selectedDateInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                binding.addMeetingDatePickerText.setText(datePicker.getHeaderText());
            });
        });

        binding.addMeetingTimePickerButton.setOnClickListener(view -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Set Start Time")
                    .build();
            timePicker.show(getSupportFragmentManager(), "Material_Time_Picker");
            timePicker.addOnPositiveButtonClickListener(view1 -> {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                selectedTime = LocalTime.of(hour, minute);
                binding.addMeetingTimePickerText.setText(String.format("%02d:%02d", hour, minute));
            });
        });
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUI(mViewModel);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        binding.addMeetingParticipantsButton.setOnClickListener(view -> {
            String newParticipant = Objects.requireNonNull(binding.addMeetingAboutMeTiet.getText()).toString().trim();
            mViewModel.addParticipant(newParticipant);
            binding.addMeetingAboutMeTiet.setText("");
        });

        binding.addMeetingLocationTiet.addTextChangedListener(watcher);
        binding.adMeetingSubjectTiet.addTextChangedListener(watcher);
        binding.addMeetingDatePickerText.addTextChangedListener(watcher);
        binding.addMeetingTimePickerText.addTextChangedListener(watcher);
        binding.addMeetingParticipantsList.addTextChangedListener(watcher);
        binding.addMeetingNameTiet.addTextChangedListener(watcher);

        mViewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                mViewModel.resetErrorMessage();
            }
        });
    }

    private void updateUI(AddMeetingViewModel mViewModel) {
        String location = binding.addMeetingLocationTiet.getText().toString();
        String subject = binding.adMeetingSubjectTiet.getText().toString();
        String meetingName = binding.addMeetingNameTiet.getText().toString();
        String datePickerText = binding.addMeetingDatePickerText.getText().toString();
        String timePickerText = binding.addMeetingTimePickerText.getText().toString();
        String participantsList = binding.addMeetingParticipantsList.getText().toString();
        boolean allFieldsFilled = mViewModel.areAllFieldsFilled(location, subject, meetingName, datePickerText, timePickerText, participantsList);

        if (allFieldsFilled) {
            binding.addMeetingButton.setEnabled(true);
            binding.addMeetingButton.setOnClickListener(view -> {
                // Affichez la ProgressBar
                binding.progressBar.setVisibility(View.VISIBLE);

                // Enregistrez la réunion et utilisez le rappel pour gérer la suite
                mViewModel.onAddButtonClicked(meetingName,
                        selectedTime,
                        selectedDate,
                        location,
                        subject,
                        (List<String>) Objects.requireNonNull(mViewModel.getParticipantsLiveData().getValue()),
                        () -> {
                            // Affichez un message Toast
                            Toast.makeText(getApplicationContext(), "Enregistrement de la réunion", Toast.LENGTH_SHORT).show();

                            // Attendez une seconde avant de cacher la ProgressBar et de revenir à l'activité précédente
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                // Cachez la ProgressBar
                                binding.progressBar.setVisibility(View.GONE);

                                // Revenez à l'activité précédente
                                finish();
                            }, 1000); // 1000 milliseconds (1 second)
                        });
            });
        } else {
            binding.addMeetingButton.setEnabled(false);
        }
    }

    private void setupParticipantsObserver(AddMeetingViewModel mViewModel) {
        mViewModel.getParticipantsLiveData().observe(this, participants -> {
            String participantsText = "Liste des participants: \n" + TextUtils.join("\n", participants);
            binding.addMeetingParticipantsList.setText(participantsText);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Gère la navigation en arrière en appelant onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

