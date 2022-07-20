package com.fitnesssystem.fitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class StartWorkoutPopup extends AppCompatDialogFragment {

    private EditText RestSetsET, RestExercisesEt;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_start_workout, null);

        builder.setView(view)
                .setTitle("Start workout")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        float setsRestTime = Float.parseFloat(RestSetsET.getText().toString());
                        float exercisesRestTime = Float.parseFloat(RestExercisesEt.getText().toString());
                        Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                        intent.putExtra("SETS", setsRestTime);
                        intent.putExtra("EXERCISES", exercisesRestTime);
                        startActivity(intent);
                    }
                });

        RestSetsET = view.findViewById(R.id.et_rest_sets);
        RestExercisesEt = view.findViewById(R.id.et_rest_exercises);

        return builder.create();
    }
}
