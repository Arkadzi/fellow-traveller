package us.fellowtraveller.presentation.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.trip.RouteEntity;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.subscribers.SimpleSubscriberListener;
import us.fellowtraveller.domain.usecase.GetRouteUseCase;
import us.fellowtraveller.presentation.adapters.ItemTouchAdapter;
import us.fellowtraveller.presentation.adapters.TripPointAdapter;
import us.fellowtraveller.presentation.adapters.view_handlers.SimpleItemTouchHelperCallback;
import us.fellowtraveller.presentation.fragments.RouteMapFragment;
import us.fellowtraveller.presentation.utils.LocationUtils;
import us.fellowtraveller.presentation.utils.Messages;

import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.FROM;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.TO;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.WAY;

public class CreateRouteActivity extends ProgressActivity implements ItemTouchAdapter.OnItemInteractListener, TripPointAdapter.OnPointClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
//    @Bind(R.id.item_from)
//    TextView itemFrom;
//    @Bind(R.id.item_to)
//    TextView itemTo;
    private TripPointAdapter adapter;
    private int pointType;
    @Inject
    GetRouteUseCase getRouteUseCase;
    @Inject
    Messages messages;
//    private Place placeFrom;
//    private Place placeTo;
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
        }
    };
    private float pointItemHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        initViews();
        Application.getApp(this).getUserComponent().inject(this);
    }

    private void initViews() {
        ButterKnife.bind(this);
        pointItemHeight = getResources().getDimension(R.dimen.trip_point_item_height);
        adapter = new TripPointAdapter(getLayoutInflater());
        adapter.setOnItemInteractListener(this);
        adapter.setPointClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getSupportFragmentManager().findFragmentById(R.id.map_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map_container, RouteMapFragment.newInstance())
                    .commit();
        }

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LocationUtils.onActivityResult(requestCode)) {
            Place place = LocationUtils.fetchPlace(this, resultCode, data);
            if (place != null) {
                updatePlace(place);
            } else if (resultCode == RESULT_OK) {
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

    private void updatePlace(Place place) {
        switch (pointType) {
            case FROM:
                adapter.setFrom(place);
                break;
            case TO:
                adapter.setTo(place);
                break;
            case WAY:
                adapter.addPlace(place);
                break;
        }
        updateListHeight();
        makeQuery();
    }

    private void updateListHeight() {
//        int count = adapter.getItemCount();
//        if (count > 3) count = 3;
//
//        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
//        params.height=(int) (pointItemHeight * count);
//        recyclerView.setLayoutParams(params);
    }

    private void makeQuery() {
        Place from = adapter.getFrom();
        Place to = adapter.getTo();
        if (from != null && to != null) {
            getRouteUseCase.setCoords(from.getLatLng(), to.getLatLng(), adapter.getWayPoints());
            getRouteUseCase.execute(new BaseProgressSubscriber<RouteResult>(routeListener) {
                @Override
                public void onNext(RouteResult response) {
                    super.onNext(response);
                    RouteMapFragment mapFragment = (RouteMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);
                    List<RouteEntity> routes = response.routes;
                    if (!routes.isEmpty()) {
                        RouteEntity routeEntity = routes.get(0);
                        List<String> polylines = routeEntity.getPolylines();
                        mapFragment.drawPolylines(polylines);
                    } else {
                        mapFragment.clearMap();
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
        updateListHeight();
        for (Place place : adapter.getItems()) {
            Log.e("place", place.getName() + " " + place.getAddress());
        }
        makeQuery();
    }

    @Override
    public void onClick(int viewType) {
        if (viewType != WAY) {
            pointType = viewType;
            LocationUtils.requestLocation(this);
        }
    }
}
