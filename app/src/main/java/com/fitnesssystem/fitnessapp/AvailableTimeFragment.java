package com.fitnesssystem.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailableTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableTimeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvailableTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvailableTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvailableTimeFragment newInstance(String param1, String param2) {
        AvailableTimeFragment fragment = new AvailableTimeFragment();
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
        View parentHolder = inflater.inflate(R.layout.fragment_available_time, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button[] daysButtons = {parentHolder.findViewById(R.id.button_two_days), parentHolder.findViewById(R.id.button_three_days),
                                parentHolder.findViewById(R.id.button_four_days), parentHolder.findViewById(R.id.button_five_days)};

        for (int i = 0, daysButtonsLength = daysButtons.length; i < daysButtonsLength; i++) {
            Button button = daysButtons[i];
            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (Button deselectedButton : daysButtons) {
                        deselectedButton.setSelected(false);
                    }
                    button.setSelected(true);

                    editor.putString("Available Time", String.valueOf(finalI + 2) + " days/week");
                    editor.commit();
                }
            });
        }

        return parentHolder;
    }
}