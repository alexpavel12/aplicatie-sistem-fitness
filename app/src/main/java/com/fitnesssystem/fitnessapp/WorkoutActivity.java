package com.fitnesssystem.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity {

    public float setsRestTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        TextView testTV = findViewById(R.id.tv_test);
        testTV.setText(String.valueOf(setsRestTime));
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
        super.onBackPressed();
    }
}
