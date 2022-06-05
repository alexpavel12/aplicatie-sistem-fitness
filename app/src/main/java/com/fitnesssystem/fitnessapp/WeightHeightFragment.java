package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeightHeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightHeightFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserDataActivity userDataActivity;

    private boolean weightCompleted, heightCompleted;

    public WeightHeightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeightHeightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeightHeightFragment newInstance(String param1, String param2) {
        WeightHeightFragment fragment = new WeightHeightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentHolder = inflater.inflate(R.layout.fragment_weight_height, container, false);

        userDataActivity = (UserDataActivity) getActivity();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText weightET = parentHolder.findViewById(R.id.et_weight);
        EditText heightET = parentHolder.findViewById(R.id.et_height);

        weightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (weightET.getText().toString().trim().length() > 0) {
                    editor.putFloat("Weight", Float.parseFloat(weightET.getText().toString()));
                    editor.commit();

                    weightCompleted = true;
                } else {
                    weightCompleted = false;
                }

                if (weightCompleted && heightCompleted) userDataActivity.canContinue = true;
            }
        });

        heightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (heightET.getText().toString().trim().length() > 0) {
                    editor.putFloat("Height", Float.parseFloat(heightET.getText().toString()));
                    editor.commit();

                    heightCompleted = true;
                } else {
                    heightCompleted = false;
                }

                if (weightCompleted && heightCompleted) userDataActivity.canContinue = true;
            }
        });

        return parentHolder;
    }
}