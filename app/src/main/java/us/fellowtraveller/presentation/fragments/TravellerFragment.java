package us.fellowtraveller.presentation.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.data.Constants;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.adapters.RouteAdapter;
import us.fellowtraveller.presentation.presenter.DriverPresenter;
import us.fellowtraveller.presentation.presenter.TravellerPresenter;
import us.fellowtraveller.presentation.utils.ActivityUtils;
import us.fellowtraveller.presentation.utils.ScreenNavigator;
import us.fellowtraveller.presentation.view.DriverRouteView;
import us.fellowtraveller.presentation.view.TravellerRouteView;

public class TravellerFragment extends Fragment implements TravellerRouteView {
    public static final String TAG = "traveller_fragment";
    public static final int ADD_ROUTE_REQUEST_CODE = 101;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private RouteAdapter adapter;
    @Inject
    TravellerPresenter presenter;

    public static TravellerFragment newInstance() {

        Bundle args = new Bundle();

        TravellerFragment fragment = new TravellerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getApp(getActivity()).getUserComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traveller, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RouteAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onCreate(this);
        if (savedInstanceState == null) {
            presenter.onStart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ROUTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Route route = ActivityUtils.restore(data.getExtras(), Constants.Intents.EXTRA_ROUTE);
            adapter.addRoute(route);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @OnClick(R.id.fab_add_route)
    public void onClick() {
        presenter.onAddRouteButtonClick();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
        refreshLayout.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void renderRoutes(List<Route> routes) {
        if (routes.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        }
        adapter.setData(routes);
    }

    @Override
    public void navigateToSearchRouteScreen() {
        ScreenNavigator.startSearchRouteScreen(this, getActivity(), ADD_ROUTE_REQUEST_CODE);
    }
}
