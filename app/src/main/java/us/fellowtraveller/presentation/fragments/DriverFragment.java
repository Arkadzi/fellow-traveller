package us.fellowtraveller.presentation.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

public class DriverFragment extends Fragment {
    public static final String TAG = "driver_fragment";
    private static final int CREATE_ROUTE_REQUEST_CODE = 111;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private Account user;

    public static DriverFragment newInstance() {

        Bundle args = new Bundle();

        DriverFragment fragment = new DriverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = Application.getApp(getActivity()).getUserComponent().account();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver, container, false);
        ButterKnife.bind(this, view);

        snackbar = Snackbar.make(coordinatorLayout, R.string.warn_no_cars, Snackbar.LENGTH_LONG)
                .setAction(R.string.profile, v -> ScreenNavigator.startProfileScreen(getActivity(), user.user()));
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
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @OnClick(R.id.fab_add_route)
    public void onClick() {
        if (!user.user().getCars().isEmpty()) {
            ScreenNavigator.startCreateRouteScreen(this, getActivity(), CREATE_ROUTE_REQUEST_CODE);
        } else if (!snackbar.isShown()) {
            snackbar.show();
        }
    }

    private void updateList() {
        //TODO update list
    }
}
