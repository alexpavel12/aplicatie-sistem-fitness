package com.fitnesssystem.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

        ImageButton button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(view -> {
            if (!canContinue)
                return;

            if (currentFragmentIndex > 4) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
}
