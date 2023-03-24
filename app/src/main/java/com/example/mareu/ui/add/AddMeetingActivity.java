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
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.utils.ViewModelFactory;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity {

    private static final int DELAY_MILLISECONDS = 1000;
    private ActivityAddMeetingBinding binding;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    AddMeetingViewModel mViewModel;

    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);
        setupParticipantsObserver();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setupLocationAdapter();
        setupDatePicker();
        setupTimePicker();
        setupTextWatchers();


        binding.addMeetingParticipantsButton.setOnClickListener(view -> {
            String newParticipant = Objects.requireNonNull(binding.addMeetingAboutMeTiet.getText()).toString().trim();
            mViewModel.addParticipant(newParticipant);
            binding.addMeetingAboutMeTiet.setText("");
        });

        binding.refreshMeetingParticipantsButton.setOnClickListener(v -> {
            mViewModel.resetParticipantsList();
            binding.addMeetingParticipantsList.setText("");
        });

        mViewModel.getErrorMessageLiveData().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                mViewModel.resetErrorMessage();
            }
        });
    }

    private void setupTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUI();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        binding.addMeetingLocationTiet.addTextChangedListener(watcher);
        binding.adMeetingSubjectTiet.addTextChangedListener(watcher);
        binding.addMeetingDatePickerText.addTextChangedListener(watcher);
        binding.addMeetingTimePickerText.addTextChangedListener(watcher);
        binding.addMeetingParticipantsList.addTextChangedListener(watcher);
        binding.addMeetingNameTiet.addTextChangedListener(watcher);
    }

    private void setupTimePicker() {
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
    }

    private void setupDatePicker() {
        binding.addMeetingDatePickerButton.setOnClickListener(view -> {
            // Construisez la contrainte pour n'autoriser que les dates à partir d'aujourd'hui
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
            constraintsBuilder.setValidator(DateValidatorPointForward.now());

            // Créez le sélecteur de date avec la contrainte
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraintsBuilder.build())
                    .build();

            datePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                long selectedDateInMillis = selection;
                selectedDate = Instant.ofEpochMilli(selectedDateInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
                binding.addMeetingDatePickerText.setText(datePicker.getHeaderText());
            });
        });
    }

    private void setupLocationAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.rooms));
        binding.addMeetingLocationTiet.setAdapter(adapter);
    }

    private void updateUI() {
        String location = binding.addMeetingLocationTiet.getText().toString();
        String subject = binding.adMeetingSubjectTiet.getText().toString();
        String meetingName = binding.addMeetingNameTiet.getText().toString();
        String datePickerText = binding.addMeetingDatePickerText.getText().toString();
        String timePickerText = binding.addMeetingTimePickerText.getText().toString();
        String participantsList = binding.addMeetingParticipantsList.getText().toString();
        boolean allFieldsFilled = mViewModel.areAllFieldsFilled(meetingName, location, subject, datePickerText, timePickerText, participantsList);
        binding.addMeetingButton.setEnabled(allFieldsFilled);

        binding.addMeetingButton.setOnClickListener(view -> {
            mViewModel.createAndAddMeeting(meetingName, selectedDate, selectedTime, location, subject, Objects.requireNonNull(mViewModel.getParticipantsLiveData().getValue()),
                    () -> {
                        if (TextUtils.isEmpty(mViewModel.getErrorMessageLiveData().getValue())) {
                            // Afficher la ProgressBar
                            binding.progressBar.setVisibility(View.VISIBLE);
                            // Afficher un message Toast
                            Toast.makeText(getApplicationContext(), "Réunion enregistrée", Toast.LENGTH_SHORT).show();

                            // Cacher la ProgressBar et retourner à l'activité précédente après un délai de 1 seconde
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                binding.progressBar.setVisibility(View.GONE);
                                finish();
                            }, DELAY_MILLISECONDS); // 1000 millisecondes (1 seconde)
                        }
                    });
        });
    }


    private void setupParticipantsObserver() {
        mViewModel.getParticipantsLiveData().observe(this, participants -> {
            if (participants.size() > 0) {
                String participantsText = "Liste des participants: \n" + TextUtils.join("\n", participants);
                binding.addMeetingParticipantsList.setText(participantsText);
            }
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