package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.Date;

public class RecoveryActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public int hoursSlept, wakeUpHour;

    private TextView sleepScoreTV;

    private Resources resources;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        sleepScoreTV = findViewById(R.id.tv_sleep_score);

        sharedPreferences = getSharedPreferences("RecoveryData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean newDay = sharedPreferences.getBoolean("New day", true);

        int oldDate = sharedPreferences.getInt("Last day", 0);

        if (oldDate != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
            newDay = true;
            oldDate = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            editor.putInt("Last day", oldDate);
            editor.apply();
        }

        resources = getResources();

        if (!newDay) {
            ChangeFragments();
            wakeUpHour = sharedPreferences.getInt("Wake up hour", 0);
            hoursSlept = sharedPreferences.getInt("Hours slept", 0);
        }

        CheckForRating();
    }

    public void ChangeFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recovery_fragment_container, MainRecoveryFragment.class, null)
                .setReorderingAllowed(true)
                .commit();
        editor.putBoolean("New day", false);
        editor.apply();
    }

    public void SaveHours() {
        editor.putInt("Wake up hour", wakeUpHour);
        editor.putInt("Hours slept", hoursSlept);
        SaveNight(hoursSlept);
        CheckForRating();
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

        // Old, only works with dates - long averageHoursSlept = totalHours / 7;
        long averageHoursSlept = totalTime / nightsCount;

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
