package us.fellowtraveller.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.fellowtraveller.R;

public class TravellerFragment extends Fragment {
public static final String TAG = "traveller_fragment";

    public static TravellerFragment newInstance() {

        Bundle args = new Bundle();

        TravellerFragment fragment = new TravellerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_traveller, container, false);
    }

}
