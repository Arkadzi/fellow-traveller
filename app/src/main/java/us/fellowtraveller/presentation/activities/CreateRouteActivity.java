package us.fellowtraveller.presentation.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.trip.RouteEntity;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.subscribers.SimpleSubscriberListener;
import us.fellowtraveller.domain.usecase.GetRouteUseCase;
import us.fellowtraveller.presentation.adapters.ItemTouchAdapter;
import us.fellowtraveller.presentation.adapters.TripPointAdapter;
import us.fellowtraveller.presentation.adapters.view_handlers.SimpleItemTouchHelperCallback;
import us.fellowtraveller.presentation.dialogs.DatePickDialogFragment;
import us.fellowtraveller.presentation.dialogs.MapDialog;
import us.fellowtraveller.presentation.dialogs.TimePickerDialog;
import us.fellowtraveller.presentation.utils.LocationUtils;
import us.fellowtraveller.presentation.utils.Messages;

import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.FROM;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.TO;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.WAY;

public class CreateRouteActivity extends ProgressActivity implements ItemTouchAdapter.OnItemInteractListener,
        TripPointAdapter.OnPointClickListener, MapDialog.MapDialogListener,
        DatePickDialogFragment.DatePickerListener, TimePickerDialog.TimePickerListener {
    public static final int MINUTE_MILLIS = 1000 * 60;
    public static final int HOUR_MILLIS = MINUTE_MILLIS * 60;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.btn_add_point)
    FloatingActionButton fabAddPoint;
    private TripPointAdapter adapter;
    private int pointType;
    private Place place;
    @Inject
    GetRouteUseCase getRouteUseCase;
    @Inject
    Messages messages;
    private SimpleSubscriberListener routeListener = new SimpleSubscriberListener() {
        @Override
        public void onStartLoading() {
            super.onStartLoading();
            showProgress();
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            hideProgress();
        }

        @Override
        public void onError(Throwable t) {
            super.onError(t);
            hideProgress();
            showMessage(messages.getError(t));
            showSnackbar();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        initViews();
        Application.getApp(this).getUserComponent().inject(this);
    }

    private void initViews() {
        ButterKnife.bind(this);
        adapter = new TripPointAdapter(this);
        adapter.setOnItemInteractListener(this);
        adapter.setPointClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LocationUtils.onActivityResult(requestCode)) {
            Place place = LocationUtils.fetchPlace(this, resultCode, data);
            if (place != null) {
                this.place = place;
                if (pointType == FROM) {
                    DatePickDialogFragment.showDateTime(getSupportFragmentManager());
                } else {
                    TimePickerDialog.show(getSupportFragmentManager());
                }
            } else if (resultCode == RESULT_OK) {
                this.place = null;
                Toast.makeText(this, R.string.error_unknown, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationUtils.onPermissionRequested(this, requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void updatePlace(Place place, long time) {
        TripPoint tripPoint = new TripPoint(place.getAddress().toString(), place.getName().toString(), place.getLatLng(), time);
        switch (pointType) {
            case FROM:
                adapter.setFrom(tripPoint);
                break;
            case TO:
                adapter.setTo(tripPoint);
                break;
            case WAY:
                adapter.addPlace(tripPoint);
                break;
        }
        showSnackbar();
    }

    private void showSnackbar() {
        if (adapter.getFrom() != null && adapter.getTo() != null) {
            Snackbar.make(findViewById(R.id.coordinator_layout), R.string.question_build_route, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.action_build_route, v -> makeQuery())
                    .show();
        }
    }


    private void makeQuery() {
        TripPoint from = adapter.getFrom();
        TripPoint to = adapter.getTo();
        if (from != null && to != null) {
            List<TripPoint> wayPoints = adapter.getWayPoints();
            List<LatLng> latLngs = new ArrayList<>();
            for (TripPoint wayPoint : wayPoints) {
                latLngs.add(wayPoint.getLatLng());
            }
            getRouteUseCase.setCoords(from.getLatLng(), to.getLatLng(), latLngs);
            getRouteUseCase.execute(new BaseProgressSubscriber<RouteResult>(routeListener) {
                @Override
                public void onNext(RouteResult response) {
                    super.onNext(response);
                    List<RouteEntity> routes = response.routes;
                    if (!routes.isEmpty()) {
                        RouteEntity routeEntity = routes.get(0);
                        ArrayList<String> polylines = new ArrayList<>(routeEntity.getPolylines());
                        MapDialog.show(getSupportFragmentManager(), CreateRouteActivity.this, polylines);
                    } else {
                        showMessage(getString(R.string.error_unable_to_build_route));
                    }
                }
            });
        }
    }

    @OnClick(R.id.btn_add_point)
    void onClick(View view) {
        pointType = WAY;
        LocationUtils.requestLocation(this);
    }

    @Override
    protected void onDestroy() {
        if (getRouteUseCase.isWorking()) {
            getRouteUseCase.unsubscribe();
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onItemInteract() {
        for (TripPoint place : adapter.getItems()) {
            Log.e("place", place.getName() + " " + place.getAddress());
        }
//        showSnackbar();
    }

    @Override
    public void onClick(int viewType) {
        if (viewType != WAY) {
            pointType = viewType;
            LocationUtils.requestLocation(this);
        }
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onDismiss() {
        showSnackbar();
    }

    @Override
    public void onDatePicked(long date) {
        updatePlace(place, date);
    }

    @Override
    public void onTimePicked(int hour, int minute) {
        updatePlace(place, minute * MINUTE_MILLIS + hour * HOUR_MILLIS);
    }
}
