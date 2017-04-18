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

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.subscribers.SimpleSubscriberListener;
import us.fellowtraveller.domain.usecase.GetRouteUseCase;
import us.fellowtraveller.presentation.adapters.ItemTouchAdapter;
import us.fellowtraveller.presentation.adapters.TripPointAdapter;
import us.fellowtraveller.presentation.adapters.view_handlers.SimpleItemTouchHelperCallback;
import us.fellowtraveller.presentation.dialogs.CreateRouteDialog;
import us.fellowtraveller.presentation.dialogs.DatePickDialogFragment;
import us.fellowtraveller.presentation.dialogs.TimePickerDialog;
import us.fellowtraveller.presentation.utils.LocationUtils;
import us.fellowtraveller.presentation.utils.Messages;

import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.FROM;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.TO;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.WAY;

public class CreateRouteActivity extends ProgressActivity implements ItemTouchAdapter.OnItemInteractListener,
        TripPointAdapter.OnPointClickListener, CreateRouteDialog.MapDialogListener,
        DatePickDialogFragment.DatePickerListener, TimePickerDialog.TimePickerListener {
    public static final int MINUTE_MILLIS = 1000 * 60;
    public static final int HOUR_MILLIS = MINUTE_MILLIS * 60;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.btn_add_point)
    FloatingActionButton fabAddPoint;
    private TripPointAdapter adapter;
    private int pointType;
    private TripPoint place;
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
            updateSnackbar();
        }
    };
    private Snackbar snackbar;

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
        snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), R.string.question_build_route, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_build_route, v -> makeQuery());
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LocationUtils.onActivityResult(requestCode)) {
            Place place = LocationUtils.fetchPlace(this, resultCode, data);
            if (place != null) {
                String address = place.getAddress() != null ? place.getAddress().toString() : "";
                String name = place.getName() != null ? place.getName().toString() : "";
                this.place = new TripPoint(address, name, place.getLatLng(), 0);
                if (pointType == FROM) {
                    DatePickDialogFragment.showDateTime(getSupportFragmentManager());
                } else {
                    TimePickerDialog.show(getSupportFragmentManager(), 0);
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


    private void updatePlace(TripPoint tripPoint, int tag) {
        switch (pointType) {
            case FROM:
                adapter.setFrom(tripPoint);
                break;
            case TO:
                adapter.setTo(tripPoint);
                break;
            case WAY:
                if (tag == 0) {
                    adapter.addPlace(tripPoint);
                } else {
                    adapter.setPlace(tripPoint, tag);
                }
                break;
        }
        updateSnackbar();
    }

    private void updateSnackbar() {
        if (adapter.getFrom() != null && adapter.getTo() != null) {
            if (!snackbar.isShown()) {
                snackbar.show();
            }
        } else if (snackbar.isShown()) {
            snackbar.dismiss();
        }
    }


    private void makeQuery() {
        TripPoint from = adapter.getFrom();
        TripPoint to = adapter.getTo();
        if (from != null && to != null) {
            List<TripPoint> wayPoints = adapter.getWayPoints();

            getRouteUseCase.setCoords(from, to, wayPoints);
            getRouteUseCase.execute(getSubscriber());
        }
    }

    @NonNull
    private BaseProgressSubscriber<List<Point>> getSubscriber() {
        return new BaseProgressSubscriber<List<Point>>(routeListener) {
            @Override
            public void onNext(List<Point> response) {
                super.onNext(response);
                CreateRouteDialog.show(getSupportFragmentManager(), CreateRouteActivity.this, response);
            }
        };
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
        updateSnackbar();
    }

    @Override
    public void onClick(int viewType, int position) {
        pointType = viewType;
        if ((viewType == FROM && adapter.getFrom() == null)
                || (viewType == TO && adapter.getTo() == null)) {
            LocationUtils.requestLocation(this);
        } else if (viewType == FROM) {
            place = adapter.getFrom();
            DatePickDialogFragment.showDateTime(getSupportFragmentManager());
        } else if (viewType == TO) {
            place = adapter.getTo();
            TimePickerDialog.show(getSupportFragmentManager(), 0);
        } else {
            place = adapter.getItem(position);
            TimePickerDialog.show(getSupportFragmentManager(), position);
        }
    }

    @Override
    public void onCreated() {

    }

    @Override
    public void onDismiss() {
        updateSnackbar();
    }

    @Override
    public void onDatePicked(long date) {
        place.setDatetime(date);
        updatePlace(place, 0);
    }

    @Override
    public void onTimePicked(int hour, int minute, int tag) {
        place.setDatetime(minute * MINUTE_MILLIS + hour * HOUR_MILLIS);
        updatePlace(place, tag);
    }
}
