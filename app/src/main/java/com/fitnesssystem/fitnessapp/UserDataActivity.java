package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class UserDataActivity extends AppCompatActivity {

    public boolean canContinue;

    private int currentFragmentIndex;
    private String nextClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        SharedPreferences startConfig = getSharedPreferences("StartConfig", MODE_PRIVATE);
        SharedPreferences.Editor startConfigEditor = startConfig.edit();
        if (startConfig.getBoolean("started", false)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        currentFragmentIndex = 0;
        nextClass = "com.fitnesssystem.fitnessapp.AgeFragment";

        ImageButton buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(view -> {
            if (!canContinue)
                return;

            if (currentFragmentIndex > 4) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                CalculateCalories();
                startConfigEditor.putBoolean("started", true);
                startConfigEditor.apply();
                finish();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            try {
                fragmentManager.beginTransaction()
                        .replace(R.id.user_data_fragment_container, (Class<? extends Fragment>) Class.forName(nextClass), null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            IncrementIndex();
        });
    }

    private void IncrementIndex() {
        currentFragmentIndex++;
        switch (currentFragmentIndex) {
            case 1:
                nextClass = "com.fitnesssystem.fitnessapp.WeightHeightFragment";
                break;
            case 2:
                nextClass = "com.fitnesssystem.fitnessapp.ObjectivesFragment";
                break;
            case 3:
                nextClass = "com.fitnesssystem.fitnessapp.AvailableTimeFragment";
                break;
            case 4:
                nextClass = "com.fitnesssystem.fitnessapp.AvailableEquipmentFragment";
                break;
        }
        canContinue = false;
    }

    private void CalculateCalories() {
        SharedPreferences nutritionPreferences = getSharedPreferences("Nutrition Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor nutritionEditor = nutritionPreferences.edit();

        SharedPreferences userData = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        float weight = userData.getFloat("Weight", 0);
        float height = userData.getFloat("Height", 0);
        int age = userData.getInt("Age", 0);
        int workingOutDays = userData.getInt("Working out days", 0);
        String gender = userData.getString("Gender", null);
        String objective = userData.getString("Objective", null);

        float bmr;
        if (gender.equals("Male")) {
            bmr = 66.47f + (13.75f * weight) + (5.003f * height) - (6.755f * age);
        } else {
            bmr = 655.1f + (9.563f * weight) + (1.850f * height) - (4.676f * age);
        }
        float amr = CalculateAMR(bmr, workingOutDays);

        if (objective.equals("Fat loss")) {
            float caloriesNeeded = amr - 500;
            nutritionEditor.putFloat("Calories needed", caloriesNeeded);
        } else if (objective.equals("Muscle mass")) {
            float caloriesNeeded = amr + 200;
            nutritionEditor.putFloat("Calories needed", caloriesNeeded);
        }
        nutritionEditor.apply();
    }

    private float CalculateAMR(float BMR, int workingOutDays) {
        if (isBetween(workingOutDays, 1, 3)) {
            return BMR * 1.375f;
        } else if (isBetween(workingOutDays, 3, 5)) {
            return BMR * 1.355f;
        } else if (isBetween(workingOutDays, 6, 7)) {
            return BMR * 1.725f;
        } else return 0;
    }

    private boolean isBetween(long n, int lower, int upper) {
        return lower <= n && n <= upper;
    }
}
