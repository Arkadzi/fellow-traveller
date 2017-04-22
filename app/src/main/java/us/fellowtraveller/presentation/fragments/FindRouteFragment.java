package us.fellowtraveller.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.data.Constants;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.activities.CreateRouteActivity;
import us.fellowtraveller.presentation.dialogs.CreateRouteDialog;
import us.fellowtraveller.presentation.dialogs.DatePickDialogFragment;
import us.fellowtraveller.presentation.presenter.FindRoutePresenter;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.LocationUtils;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;
import us.fellowtraveller.presentation.view.FindRouteView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by arkadius on 4/22/17.
 */

public class FindRouteFragment extends ProgressFragment implements FindRouteView,
        DatePickDialogFragment.DatePickerListener, View.OnClickListener {
    private static int FROM = 1;
    private static int TO = 2;
    @Bind(R.id.et_when)
    EditText etWhen;
    @Bind(R.id.et_from)
    EditText etFrom;
    @Bind(R.id.et_to)
    EditText etTo;
    private int pointType;
    @Bind(R.id.btn_find)
    Button btnFind;
    @Inject
    FindRoutePresenter presenter;
    private final SimpleDateFormat formatFirst = new SimpleDateFormat("d MMM yyyy, H:mm");

    private TripPoint from;
    private TripPoint to;
    private long when;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getApp(getActivity()).getUserComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter.onCreate(this);
    }

    @Override
    public void onDestroyView() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void initViews(View view) {
        ButterKnife.bind(this, view);
        etFrom.setOnClickListener(this);
        etFrom.setFocusable(false);
        etFrom.setClickable(true);
        etTo.setOnClickListener(this);
        etTo.setFocusable(false);
        etTo.setClickable(true);
        etWhen.setOnClickListener(this);
        etWhen.setFocusable(false);
        etWhen.setClickable(true);
        btnFind.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LocationUtils.onActivityResult(requestCode)) {
            Place place = LocationUtils.fetchPlace(getActivity(), resultCode, data);
            if (place != null) {
                String address = place.getAddress() != null ? place.getAddress().toString() : "";
                String name = place.getName() != null ? place.getName().toString() : "";
                if (pointType == FROM) {
                    from = new TripPoint(address, name, place.getLatLng(), 0);
                    etFrom.setText(from.toString());
                } else if (pointType == TO) {
                    to = new TripPoint(address, name, place.getLatLng(), 0);
                    etTo.setText(to.toString());
                }
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), R.string.error_unknown, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationUtils.onPermissionRequested(this, requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onClick(View view) {
        if (view == etFrom) {
            pointType = FROM;
            LocationUtils.requestLocation(this);
        } else if (view == etTo) {
            pointType = TO;
            LocationUtils.requestLocation(this);
        } else if (view == etWhen) {
            DatePickDialogFragment.showDateTime(getChildFragmentManager());
        } else if (view == btnFind) {
            try {
                FieldUtils.getNonEmptyText(etWhen);
                FieldUtils.getNonEmptyText(etTo);
                FieldUtils.getNonEmptyText(etFrom);
                presenter.onRouteBuildClick(from, to, when);
            } catch (BadFieldDataException e) {}
        }
    }

    @Override
    public void onDatePicked(long date) {
        etWhen.setText(formatFirst.format(new Date(date)));
        when = date;
    }

    @Override
    public void onRouteCreated(Route response) {
        showMessage(getString(R.string.message_route_built));
        Intent data = new Intent();
//        Scree
    }
}

