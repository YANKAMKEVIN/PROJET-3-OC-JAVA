package com.example.mareu.ui.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.utils.ViewModelFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity {

    private static final int DELAY_MILLISECONDS = 1000;
    private ActivityAddMeetingBinding binding;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    AddMeetingViewModel mViewModel;
    @Nullable
    private Integer pickedStartHour;
    @Nullable
    private Integer pickedStartMinute;

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
            String newParticipant = Objects.requireNonNull(binding.addMeetingInputParticipantText.getText()).toString().trim();
            mViewModel.addParticipant(newParticipant);
            binding.addMeetingInputParticipantText.setText("");
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
            configureTimePickers((timePicker, hourOfDay, mMinute) -> {
                pickedStartHour = hourOfDay;
                pickedStartMinute = mMinute;
                binding.addMeetingTimePickerText.setText(String.format(Locale.FRANCE, "%02d:%02d", hourOfDay, mMinute));
                selectedTime = LocalTime.parse(binding.addMeetingTimePickerText.getText());
                Log.d("Kevin",""+selectedTime);
            });
        });
    }

    private void configureTimePickers(
            @NonNull TimePickerDialog.OnTimeSetListener listener) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listener, hour, minute, true);
        if (pickedStartHour != null && pickedStartMinute != null) {
            timePickerDialog.updateTime(pickedStartHour, pickedStartMinute);
        }
        timePickerDialog.show();
    }

    private void setupDatePicker() {
        binding.addMeetingDatePickerButton.setOnClickListener(view -> {
            Locale.setDefault(Locale.FRANCE);

            final Calendar now = Calendar.getInstance();
            int mYear = now.get(Calendar.YEAR);
            int mMonth = now.get(Calendar.MONTH);
            int mDay = now.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.YEAR, year);
                        String date = formatDate(dayOfMonth, monthOfYear + 1, year);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        selectedDate = LocalDate.parse(date,formatter);
                        binding.addMeetingDatePickerText.setText(date);
                    }, mYear, mMonth, mDay);
            dpd.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
            dpd.getDatePicker().setMinDate(now.getTimeInMillis());
            dpd.show();
        });
    }

    private String formatDate(int dayOfMonth, int month, int year) {
        return String.format(Locale.FRANCE, "%02d/%02d/%04d", dayOfMonth, month, year);
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