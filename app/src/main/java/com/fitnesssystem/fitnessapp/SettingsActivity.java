package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources resources = getResources();
        String pullUpBar = resources.getString(R.string.pull_up_bar);
        String dumbbells = resources.getString(R.string.dumbbells);
        String barbell = resources.getString(R.string.barbell);
        String gym = resources.getString(R.string.gym);
        String noEquipment = resources.getString(R.string.no_equipment);

        String availableEquipment = "";

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        TextView userGenderTV = findViewById(R.id.tv_user_gender);
        userGenderTV.setText(sharedPreferences.getString("Gender", "not_assigned"));

        TextView userAgeTV = findViewById(R.id.tv_user_age);
        userAgeTV.setText(String.valueOf(sharedPreferences.getInt("Age", 0)));

        TextView userWeightTV = findViewById(R.id.tv_user_weight);
        userWeightTV.setText(String.valueOf(sharedPreferences.getFloat("Weight", 0)));

        TextView userHeightTV = findViewById(R.id.tv_user_height);
        userHeightTV.setText(String.valueOf(sharedPreferences.getFloat("Height", 0)));

        TextView userObjectiveTV = findViewById(R.id.tv_user_objective);
        userObjectiveTV.setText(sharedPreferences.getString("Objective", "not_assigned"));

        TextView userAvailableTimeTV = findViewById(R.id.tv_user_available_time);
        userAvailableTimeTV.setText(sharedPreferences.getString("Available time", "not_assigned"));

        TextView userAvailableEquipmentTv = findViewById(R.id.tv_user_available_equipment);
        if (sharedPreferences.contains("Pull up bar")) {
            availableEquipment = pullUpBar;
        }
        if (sharedPreferences.contains("Dumbbells")) {
            if (availableEquipment.length() > 0) {
                availableEquipment += ", ";
            }
            availableEquipment += dumbbells;
        }
        if (sharedPreferences.contains("Barbell")) {
            if (availableEquipment.length() > 0) {
                availableEquipment += ", ";
            }
            availableEquipment += barbell;

        }
        if (sharedPreferences.contains("Gym")) {
            availableEquipment = gym;
        }
        if (sharedPreferences.contains("No equipment")) {
            availableEquipment = noEquipment;
        }

        userAvailableEquipmentTv.setText(availableEquipment);
    }
}
