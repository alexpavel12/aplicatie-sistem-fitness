package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.Date;

public class RecoveryActivity extends AppCompatActivity {

    private Date startSleep;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public int hoursSlept, wakeUpHour;

    private TextView sleepScoreTV;

    private Resources resources;

    private boolean isSleeping;

    private final String[] nightKey = {"Night1", "Night2", "Night3", "Night4", "Night5", "Night6", "Night7"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        sleepScoreTV = findViewById(R.id.tv_sleep_score);

        sharedPreferences = getSharedPreferences("RecoveryData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean newDay = sharedPreferences.getBoolean("New day", false);

        startSleep = new Date(sharedPreferences.getLong("Start Time", 0));
        isSleeping = sharedPreferences.getBoolean("Is sleeping", false);

        resources = getResources();

        if (newDay) {
            ChangeFragments();
            wakeUpHour = sharedPreferences.getInt("Wake up hour", 0);
            hoursSlept = sharedPreferences.getInt("Hours slept", 0);
        }

        CheckForRating();

        /*Button sleepButton = findViewById(R.id.button_sleep);

        sleepButton.setText(isSleeping ? resources.getString(R.string.stop_sleep) : resources.getString(R.string.start_sleep));

        sleepButton.setOnClickListener(view -> {
            if (!isSleeping) {
                sleepButton.setText(resources.getString(R.string.stop_sleep));
                startSleep = Calendar.getInstance().getTime();
                isSleeping = true;
                editor.putLong("Start Time", startSleep.getTime());
                editor.putBoolean("Is sleeping", true);
                editor.apply();
            } else {
                sleepButton.setText(resources.getString(R.string.start_sleep));
                Date endSleep = Calendar.getInstance().getTime();

                long difference = endSleep.getTime() - startSleep.getTime();
                long seconds = difference / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                //TextView timeSleptTV = findViewById(R.id.tv_time_slept);
                //timeSleptTV.setText("Time slept: " + hours + " : " + minutes % 60);

                isSleeping = false;
                editor.putBoolean("Is sleeping", false);
                editor.apply();

                SaveNight(difference);
            }
        });*/
    }

    public void ChangeFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recovery_fragment_container, MainRecoveryFragment.class, null)
                .setReorderingAllowed(true)
                .commit();
        editor.putBoolean("New day", true);
        editor.apply();
    }

    public void SaveHours() {
        editor.putInt("Wake up hour", wakeUpHour);
        editor.putInt("Hours slept", hoursSlept);
        editor.apply();
    }

    private void SaveNight(long time) {
        int currentNight = sharedPreferences.getInt("Current Night", 1);
        editor.putLong("Night " + currentNight, time);
        editor.putInt("Current Night", ++currentNight);
        editor.apply();
    }

    private void CheckForRating() {
        int nightsCount = 0;
        for (int i = 0; i < 7; i++) {
            if (sharedPreferences.contains("Night " + (i + 1))) {
                nightsCount++;
            }
        }
        if (nightsCount < 7) {
            return;
        }
        long totalTime = 0;
        for (int i = 0; i < 7; i++) {
            totalTime += sharedPreferences.getLong("Night " + (i + 1), 0);
        }

        long totalSeconds = totalTime / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalRemainingMinutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;

        long averageHoursSlept = totalHours / 7;

        CalculateSleepRating(averageHoursSlept);
    }

    private void CalculateSleepRating(long hours) {
        if (isBetween(hours, 0, 3)) {
            sleepScoreTV.setText(resources.getString(R.string.very_bad));
            sleepScoreTV.setBackgroundColor(ContextCompat.getColor(this, R.color.recovery_very_bad));
        } else if (isBetween(hours, 3, 5)) {
            sleepScoreTV.setText(resources.getString(R.string.bad));
            sleepScoreTV.setBackgroundColor(ContextCompat.getColor(this, R.color.recovery_bad));
        } else if (isBetween(hours, 5, 6)) {
            sleepScoreTV.setText(resources.getString(R.string.decent));
            sleepScoreTV.setBackgroundColor(ContextCompat.getColor(this, R.color.recovery_decent));
        } else if (isBetween(hours, 6, 7)) {
            sleepScoreTV.setText(resources.getString(R.string.good));
            sleepScoreTV.setBackgroundColor(ContextCompat.getColor(this, R.color.recovery_good));
        } else if (isBetween(hours, 7, 12)) {
            sleepScoreTV.setText(resources.getString(R.string.very_good));
            sleepScoreTV.setBackgroundColor(ContextCompat.getColor(this, R.color.recovery_very_good));
        }
    }

    private boolean isBetween(long n, int lower, int upper) {
        return lower <= n && n <= upper;
    }
}
