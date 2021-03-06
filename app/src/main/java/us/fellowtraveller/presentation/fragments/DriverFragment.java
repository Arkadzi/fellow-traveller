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
import us.fellowtraveller.presentation.dialogs.MenuDialogFragment;
import us.fellowtraveller.presentation.presenter.DriverPresenter;
import us.fellowtraveller.presentation.utils.ActivityUtils;
import us.fellowtraveller.presentation.utils.ScreenNavigator;
import us.fellowtraveller.presentation.view.DriverRouteView;

public class DriverFragment extends Fragment implements DriverRouteView, MenuDialogFragment.OnMenuClickListener<Route> {
    public static final String TAG = "driver_fragment";
    public static final int ADD_ROUTE_REQUEST_CODE = 101;
    public static final int VIEW_ROUTE_REQUEST_CODE = 102;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private Snackbar snackbar;
    private RouteAdapter adapter;
    @Inject
    DriverPresenter presenter;

    public static DriverFragment newInstance() {

        Bundle args = new Bundle();

        DriverFragment fragment = new DriverFragment();
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
        View view = inflater.inflate(R.layout.fragment_driver, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RouteAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setRouteClickListener(route -> ScreenNavigator.startRouteScreen(this, getActivity(), route, VIEW_ROUTE_REQUEST_CODE));
        adapter.setRouteLongClickListener((position, route) -> {
            MenuDialogFragment.newInstance(new String[]{getString(R.string.action_delete)}, route)
                    .show(getChildFragmentManager(), MenuDialogFragment.TAG);
        });
        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        snackbar = Snackbar.make(coordinatorLayout, R.string.warn_no_cars, Snackbar.LENGTH_LONG)
                .setAction(R.string.profile, v -> presenter.onShowProfileButtonClick());
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
            tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
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
        tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void renderRoutes(List<Route> routes) {
        adapter.setData(routes);
    }

    @Override
    public void navigateToCreateRouteScreen() {
        ScreenNavigator.startCreateRouteScreen(this, getActivity(), ADD_ROUTE_REQUEST_CODE);
    }

    @Override
    public void showNoCarsButton() {
        if (!snackbar.isShown()) {
            snackbar.show();
        }
    }

    @Override
    public void showProfile(User user) {
        ScreenNavigator.startProfileScreen(getActivity(), user);
    }

    @Override
    public void onRouteDeleted(Route response) {
        adapter.remove(response);
    }

    @Override
    public void onMenuClick(int position, Route data) {
        if (position == 0) {
            presenter.onDelete(data);
        }
    }
}
