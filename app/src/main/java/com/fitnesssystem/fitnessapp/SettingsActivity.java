package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        TextView userGenderTV = findViewById(R.id.tv_user_gender);
        userGenderTV.setText(sharedPreferences.getString("Gender", "not_assigned"));

        TextView userAgeTV = findViewById(R.id.tv_user_age);
        userAgeTV.setText(String.valueOf(sharedPreferences.getInt("Age", 0)));
    }
}
