package com.fitnesssystem.fitnessapp;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainRecoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainRecoveryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainRecoveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainRecoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainRecoveryFragment newInstance(String param1, String param2) {
        MainRecoveryFragment fragment = new MainRecoveryFragment();
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
        View parentHolder = inflater.inflate(R.layout.fragment_main_recovery, container, false);

        Resources resources = getResources();
        RecoveryActivity recoveryActivity = (RecoveryActivity) getActivity();

        TextView lastNightTV = parentHolder.findViewById(R.id.tv_last_night);
        TextView wakeUpHourTV = parentHolder.findViewById(R.id.tv_wake_up_hour);
        ImageView lastNightRatingIV = parentHolder.findViewById(R.id.iv_last_night_rating);
        ImageView wakeUpHourRatingIV = parentHolder.findViewById(R.id.iv_wake_up_hour_rating);

        lastNightTV.setText(resources.getString(R.string.last_night).concat(String.valueOf(Objects.requireNonNull(recoveryActivity).hoursSlept)).concat(" "));
        wakeUpHourTV.setText(resources.getString(R.string.wake_up_hour).concat(String.valueOf(Objects.requireNonNull(recoveryActivity).wakeUpHour)).concat(" "));

        if (Objects.requireNonNull(recoveryActivity).hoursSlept > 7) {
            lastNightRatingIV.setImageResource(R.drawable.checkmark);
        } else {
            lastNightRatingIV.setImageResource(R.drawable.x);
        }

        if (Objects.requireNonNull(recoveryActivity).wakeUpHour > 7) {
            wakeUpHourRatingIV.setImageResource(R.drawable.checkmark);
        } else {
            wakeUpHourRatingIV.setImageResource(R.drawable.x);
        }

        return parentHolder;
    }
}