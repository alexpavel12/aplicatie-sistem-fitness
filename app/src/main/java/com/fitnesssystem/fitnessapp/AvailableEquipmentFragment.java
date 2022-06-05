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
 * Use the {@link AvailableEquipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableEquipmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserDataActivity userDataActivity;

    private int selectedButtonsCount;

    public AvailableEquipmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvailableEquipmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvailableEquipmentFragment newInstance(String param1, String param2) {
        AvailableEquipmentFragment fragment = new AvailableEquipmentFragment();
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
        View parentHolder = inflater.inflate(R.layout.fragment_available_equipment, container, false);

        userDataActivity = (UserDataActivity) getActivity();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button pullUpBarButton = parentHolder.findViewById(R.id.button_pull_up_bar);
        Button dumbbellsButton = parentHolder.findViewById(R.id.button_dumbbells);
        Button barbellButton = parentHolder.findViewById(R.id.button_barbell);
        Button gymButton = parentHolder.findViewById(R.id.button_gym);
        Button noEquipmentButton = parentHolder.findViewById(R.id.button_no_equipment);

        Button[] nonMutuallyExclusiveButtons = {pullUpBarButton, dumbbellsButton, barbellButton};

        pullUpBarButton.setOnClickListener(view -> {
            Select(pullUpBarButton, gymButton, noEquipmentButton, editor, "Pull up bar");
        });

        dumbbellsButton.setOnClickListener(view -> {
            Select(dumbbellsButton, gymButton, noEquipmentButton, editor, "Dumbbells");
        });

        barbellButton.setOnClickListener(view -> {
            Select(barbellButton, gymButton, noEquipmentButton, editor, "Barbell");
        });

        gymButton.setOnClickListener(view -> {
            gymButton.setSelected(true);

            DeSelectNonMutuallyExclusiveButtons(nonMutuallyExclusiveButtons, editor);

            if (noEquipmentButton.isSelected()) {
                noEquipmentButton.setSelected(false);
                editor.remove("No equipment");
            }

            selectedButtonsCount = 1;

            editor.putBoolean("Gym", true);
            editor.commit();

            userDataActivity.canContinue = true;
        });

        noEquipmentButton.setOnClickListener(view -> {
            noEquipmentButton.setSelected(true);

            DeSelectNonMutuallyExclusiveButtons(nonMutuallyExclusiveButtons, editor);

            if (gymButton.isSelected()) {
                gymButton.setSelected(false);
                editor.remove("Gym");
            }

            selectedButtonsCount = 1;

            editor.putBoolean("No equipment", true);
            editor.commit();

            userDataActivity.canContinue = true;
        });

        return parentHolder;
    }

    private void Select(Button selectedButton, Button gymButton, Button noEquipmentButton, SharedPreferences.Editor editor, String equipment) {
        if (selectedButton.isSelected()) {
            selectedButton.setSelected(false);
            selectedButtonsCount--;
            editor.remove(equipment);
        } else {
            selectedButton.setSelected(true);
            selectedButtonsCount++;
            editor.putBoolean(equipment, true);
        }
        if (gymButton.isSelected()) {
            gymButton.setSelected(false);
            selectedButtonsCount--;
            editor.remove("Gym");
        }
        if (noEquipmentButton.isSelected()) {
            noEquipmentButton.setSelected(false);
            selectedButtonsCount--;
            editor.remove("No equipment");
        }
        userDataActivity.canContinue = selectedButtonsCount > 0;
        editor.commit();
    }

    private void DeSelectNonMutuallyExclusiveButtons(Button[] nonMutuallyExclusiveButtons, SharedPreferences.Editor editor) {
        for (Button button : nonMutuallyExclusiveButtons) {
            if (button.isSelected()) {
                button.setSelected(false);
                selectedButtonsCount--;
            }
            editor.remove("Pull up bar");
            editor.remove("Dumbbells");
            editor.remove("Barbell");
            editor.commit();
        }
    }
}