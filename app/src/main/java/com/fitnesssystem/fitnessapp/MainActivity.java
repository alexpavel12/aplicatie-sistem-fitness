package com.fitnesssystem.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();

        SharedPreferences trainingPreferences = getSharedPreferences("Training Data", Context.MODE_PRIVATE);

        boolean workoutReady = trainingPreferences.getBoolean("Workout ready", false);

        ImageButton settingsButton = findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        });

        Button trainingButton = findViewById(R.id.button_training);
        trainingButton.setText(workoutReady ? resources.getString(R.string.start_workout) : resources.getString(R.string.training));
        trainingButton.setOnClickListener(view -> {
            startActivity(new Intent(this, TrainingActivity.class));
        });

        Button recoveryButton = findViewById(R.id.button_recovery);
        recoveryButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RecoveryActivity.class));
        });
    }
}