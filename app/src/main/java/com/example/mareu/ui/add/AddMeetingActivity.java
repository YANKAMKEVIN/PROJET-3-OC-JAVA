package com.example.mareu.ui.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

//TODO UTILISER DES IMAGEBUTTON A LA PLACE DES BOUTONS, UNE IMAGE D'Un CALENDRIER POUR LA DATE ET l'HORALOGE POUR LE TEMPS
//TODO AJOUTER UNE LISTE DE SALE POUR QUE LE USER CHOISISSE DIRECTEMENT UNE SALE EXISTANTE et RELIER CHAQUE SALLE A UNE COULEUR
//TODO POUR LA LISTE DES PARTICIPANTS SOIT AVOIR UNE LISTE PREETABLI DES PARTICIPANTS POSSIBLES SOIT AFFICHE LES PARTICIPANTS APRES UN ENTER....
//TODO MESSAGE D'ERREUR POUR SIGNALER QU'UN CHAMP N'A PAS ETE REMPLI

public class AddMeetingActivity extends AppCompatActivity {

    private ActivityAddMeetingBinding binding;

    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Start Time")
                .build();

        String[] choix = {"Salle 1", "Salle 2", "Salle 3", "Salle 4","Salle 5", "Salle 6", "Salle 7", "Salle 8","Salle 9", "Salle 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, choix);
        binding.addMeetingLocationTiet.setAdapter(adapter);

        binding.addMeetingDatePickerButton.setOnClickListener(view13 -> {
            datePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
            datePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener) selection -> binding.addMeetingDatePickerText.setText(datePicker.getHeaderText()));
        });

       binding.addMeetingTimePickerButton.setOnClickListener(view12 -> {
           timePicker.show(getSupportFragmentManager(),"Material_Time_Picker");
           timePicker.addOnPositiveButtonClickListener(view1 -> binding.addMeetingTimePickerText.setText(String.join(":", String.valueOf(timePicker.getHour()), String.valueOf(timePicker.getMinute()))));
       });
    }
}