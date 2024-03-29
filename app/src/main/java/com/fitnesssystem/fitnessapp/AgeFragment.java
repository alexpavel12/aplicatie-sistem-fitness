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

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserDataActivity userDataActivity;

    public AgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgeFragment newInstance(String param1, String param2) {
        AgeFragment fragment = new AgeFragment();
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
        View parentHolder = inflater.inflate(R.layout.fragment_age, container, false);
        // Inflate the layout for this fragment

        userDataActivity = (UserDataActivity) getActivity();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        EditText ageET = parentHolder.findViewById(R.id.et_age);
        ageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ageET.getText().toString().trim().length() > 0) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Age", Integer.parseInt(ageET.getText().toString()));
                    editor.apply();

                    userDataActivity.canContinue = true;
                }
            }
        });
        return parentHolder;
    }
}