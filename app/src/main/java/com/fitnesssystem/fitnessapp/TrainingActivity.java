package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TrainingActivity extends AppCompatActivity {

    private Resources resources;
    private Button startWorkout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean workoutStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        resources = getResources();

        sharedPreferences = getSharedPreferences("Training", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        workoutStarted = sharedPreferences.getBoolean("Workout started", false);

        startWorkout = findViewById(R.id.button_start_workout);
        startWorkout.setText(workoutStarted ? resources.getString(R.string.continue_workout) : resources.getString(R.string.start));
        startWorkout.setOnClickListener(view -> {
            startActivity(new Intent(this, WorkoutActivity.class));
            /*StartWorkoutPopup startWorkoutPopup = new StartWorkoutPopup();
            startWorkoutPopup.show(getSupportFragmentManager(), "workout popup");
            editor.putBoolean("Workout started", true);
            editor.apply();*/
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //workoutStarted = sharedPreferences.getBoolean("Workout started", false);
        //startWorkout.setText(workoutStarted ? resources.getString(R.string.continue_workout) : resources.getString(R.string.start));
    }
}
