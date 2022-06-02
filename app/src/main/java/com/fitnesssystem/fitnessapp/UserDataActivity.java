package com.fitnesssystem.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UserDataActivity extends AppCompatActivity {

    private int currentFragmentIndex;
    private String nextClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        currentFragmentIndex = 0;
        nextClass = "com.fitnesssystem.fitnessapp.AgeFragment";

        ImageButton button_next = findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragmentIndex > 2){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
            }
        });
    }

    private void IncrementIndex() {
        currentFragmentIndex++;
        switch (currentFragmentIndex) {
            case 1: nextClass = "com.fitnesssystem.fitnessapp.WeightHeightFragment";
            break;
            case 2: nextClass = "com.fitnesssystem.fitnessapp.AvailableEquipmentFragment";
            break;
        }
    }
}
