package us.fellowtraveller.presentation.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

public class DriverFragment extends Fragment {
    public static final String TAG = "driver_fragment";
    private static final int CREATE_ROUTE_REQUEST_CODE = 111;

    public static DriverFragment newInstance() {

        Bundle args = new Bundle();

        DriverFragment fragment = new DriverFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver, container, false);
        ButterKnife.bind(this, view);
        updateList();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREATE_ROUTE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    updateList();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick(R.id.fab_add_route)
    public void onClick() {
        ScreenNavigator.startCreateRouteScreen(this, getActivity(), CREATE_ROUTE_REQUEST_CODE);
    }

    private void updateList() {
        //TODO update list
    }
}
