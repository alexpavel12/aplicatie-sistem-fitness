package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NutritionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        Resources resources = getResources();

        SharedPreferences nutritionPreferences = getSharedPreferences("Nutrition Data", Context.MODE_PRIVATE);

        float caloriesNeeded = nutritionPreferences.getFloat("Calories needed", 0);

        TextView tvCaloriesNeeded = findViewById(R.id.tv_calories_needed);
        tvCaloriesNeeded.setText(resources.getString(R.string.calories_needed).concat(String.valueOf(Math.round(caloriesNeeded))));
    }
}
