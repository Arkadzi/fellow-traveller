package us.fellowtraveller.presentation.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.data.Constants;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.dialogs.UserListDialogFragment;
import us.fellowtraveller.presentation.fragments.RouteMapFragment;
import us.fellowtraveller.presentation.presenter.ViewRoutePresenter;
import us.fellowtraveller.presentation.utils.CircleTransform;
import us.fellowtraveller.presentation.utils.ResourceUtils;
import us.fellowtraveller.presentation.utils.ScreenNavigator;
import us.fellowtraveller.presentation.view.ViewRouteView;

public class RouteActivity extends ProgressActivity implements ViewRouteView, RouteMapFragment.InfoWindowClickListener {
    @Bind(R.id.route_title)
    TextView tvRouteTitle;
    @Bind(R.id.route_date)
    TextView tvRouteDate;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_seats)
    TextView tvSeats;
    @Bind(R.id.driver_view)
    View driverView;
    @Bind(R.id.iv_driver_photo)
    ImageView ivDriverPhoto;
    @Bind(R.id.tv_driver_name)
    TextView tvDriverName;
    @Bind(R.id.tv_year)
    TextView tvYear;
    @Bind(R.id.car_view)
    View carView;
    @Bind(R.id.iv_car_photo)
    ImageView ivCarPhoto;
    @Bind(R.id.tv_car_title)
    TextView tvCarTitle;
    @Bind(R.id.tv_state_hint)
    TextView tvState;
    @Bind(R.id.car_rating)
    RatingBar carRating;
    @Bind(R.id.btn_subscribe)
    Button btnSubscribe;
    @Bind(R.id.btn_unsubscribe)
    Button btnUnsubscribe;
    @Inject
    ViewRoutePresenter presenter;
    @Inject
    Account account;
    private final SimpleDateFormat formatFirst = new SimpleDateFormat("d MMM yyyy, H:mm");
    private Route route;
    private User user;
    private String subscriptionPointId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        route = (Route) getIntent().getSerializableExtra(Constants.Intents.EXTRA_ROUTE);
        Application.getApp(this).getUserComponent().inject(this);
        bindView(route);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        presenter.onCreate(this);
        presenter.onStart(route.getOwner());
    }

    private void bindView(Route route) {
        ButterKnife.bind(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RouteMapFragment.newInstance(route.getPoints()))
                    .commit();
        }
        driverView.setOnClickListener(v -> {
            if (user != null) {
                ScreenNavigator.startProfileScreen(RouteActivity.this, user);
            }
        });
        float price = route.getPrice();
        btnSubscribe.setOnClickListener(v -> presenter.onSubscribe(subscriptionPointId));
        btnUnsubscribe.setOnClickListener(v -> presenter.onUnsubscribe(subscriptionPointId));
        tvPrice.setText(price == (int) price ? String.valueOf((int) price) : String.format(Locale.ENGLISH, "%.2f", price));
        tvSeats.setText(String.valueOf(route.getSeats()));
        tvState.setText(getString(R.string.hint_condition) + ":");
        tvRouteTitle.setText(route.getTitle());
        tvRouteDate.setText(formatFirst.format(new Date(route.getTime())));

    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void renderUser(User user) {
        this.user = user;
        int dimension = (int) getResources().getDimension(R.dimen.car_image_size);
        driverView.setVisibility(View.VISIBLE);
        tvDriverName.setText(user.getFullName());
        String imageUrl = user.getImageUrl();
        Picasso.with(this)
                .load(imageUrl)
                .resize(dimension, 0)
                .transform(new CircleTransform())
                .into(ivDriverPhoto);
        if (!account.user().getId().equals(user.getId())) {
            subscriptionPointId = account.user().getSubscriptionPointId(route);
            boolean subscribed = subscriptionPointId != null;
            if(!subscribed) {
                subscriptionPointId = route.getStartPointId();
            }
            btnSubscribe.setVisibility(subscribed ? View.GONE : View.VISIBLE);
            btnUnsubscribe.setVisibility(subscribed ? View.VISIBLE : View.GONE);
        }

        Car car = user.getCar(route.getCar());
        if (car != null) {
            carView.setVisibility(View.VISIBLE);
            tvYear.setText(getString(R.string.hint_year_of_manufacture) + ": " + car.getYear());
            tvCarTitle.setText(car.getTitle());
            carRating.setMax(Car.MAX_RATING);
            carRating.setNumStars(Car.MAX_RATING);
            carRating.setRating(car.getCondition());

            Picasso.with(this)
                    .load(car.getImageUrl())
                    .resize(dimension, 0)
                    .transform(new CircleTransform())
                    .into(ivCarPhoto);
        }
    }

    @Override
    public void onSubscribed() {
        route.subscribe(subscriptionPointId, user.getId());
        btnSubscribe.setVisibility(View.GONE);
        btnUnsubscribe.setVisibility(View.VISIBLE);
        Intent data = new Intent();
        data.putExtra(Constants.Intents.EXTRA_ROUTE, route);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onUnsubscribed() {
        route.unsubscribe(subscriptionPointId, user.getId());
        btnSubscribe.setVisibility(View.VISIBLE);
        btnUnsubscribe.setVisibility(View.GONE);
        Intent data = new Intent();
        data.putExtra(Constants.Intents.EXTRA_ROUTE, route);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void showSubscribers(List<User> response) {
        UserListDialogFragment.newInstance(response)
                .show(getSupportFragmentManager(), UserListDialogFragment.TAG);
    }

    @Override
    public void onClick(List<String> subscribers) {
        presenter.onMarkerClick(subscribers);
    }
}
