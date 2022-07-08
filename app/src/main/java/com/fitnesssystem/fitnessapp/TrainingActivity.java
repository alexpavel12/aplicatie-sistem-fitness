package com.fitnesssystem.fitnessapp;

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

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        ConstraintLayout popupWorkout = findViewById(R.id.view_start_workout);

        Button startWorkout = findViewById(R.id.button_start_workout);
        startWorkout.setOnClickListener(view -> {
            layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_start_workout, null);

            popupWindow = new PopupWindow(container, 600, 600, true);
            popupWindow.showAtLocation(popupWorkout, Gravity.CENTER, 0, 0);
        });
    }
}
