package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NutritionActivity extends AppCompatActivity {

    private float caloriesToday, caloriesNeeded;
    private Resources resources;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        resources = getResources();

        SharedPreferences nutritionPreferences = getSharedPreferences("Nutrition Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor nutritionEditor = nutritionPreferences.edit();

        caloriesNeeded = nutritionPreferences.getFloat("Calories needed", 0);
        caloriesToday = nutritionPreferences.getFloat("Calories today", 0);

        TextView tvCaloriesToday = findViewById(R.id.tv_calories_today);
        SetCaloriesText(tvCaloriesToday);

        Button addCaloriesButton = findViewById(R.id.button_add_calories);
        addCaloriesButton.setOnClickListener(view -> {
            caloriesToday += 500;
            nutritionEditor.putFloat("Calories today", caloriesToday);
            nutritionEditor.apply();
            SetCaloriesText(tvCaloriesToday);
        });

        Button resetCaloriesButton = findViewById(R.id.button_reset_calories);
        resetCaloriesButton.setOnClickListener(view -> {
            caloriesToday = 0;
            nutritionEditor.putFloat("Calories today", caloriesToday);
            nutritionEditor.apply();
            SetCaloriesText(tvCaloriesToday);
        });
    }

    private void SetCaloriesText(TextView tvCalories) {
        tvCalories.setText(resources.getString(R.string.calories_today).concat(String.valueOf(Math.round(caloriesToday))).concat("/").concat(String.valueOf(Math.round(caloriesNeeded))));
        float caloriesRatio = caloriesToday / caloriesNeeded;
        if (caloriesRatio <= .85f) {
            tvCalories.setBackgroundColor(resources.getColor(R.color.nutrition_far));
        } else if (caloriesRatio > .9f && caloriesRatio <= 1) {
            tvCalories.setBackgroundColor(resources.getColor(R.color.nutrition_close));
        } else {
            tvCalories.setBackgroundColor(resources.getColor(R.color.nutrition_passed));
        }
    }
}
