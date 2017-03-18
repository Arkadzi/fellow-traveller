package us.fellowtraveller.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.data.di.UserComponent;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.adapters.ProfileAdapter;
import us.fellowtraveller.presentation.presenter.ProfilePresenter;
import us.fellowtraveller.presentation.utils.ActivityUtils;
import us.fellowtraveller.presentation.utils.ResourceUtils;
import us.fellowtraveller.presentation.utils.ScreenNavigator;
import us.fellowtraveller.presentation.view.ProfileView;

/**
 * Created by arkadii on 3/18/17.
 */

public class ProfileActivity extends ProgressActivity implements ProfileView {
    public static final String ARG_USER = "user";
    @Bind(R.id.iv_profile_photo)
    ImageView ivProfilePhoto;
    @Bind(R.id.rv_profile)
    RecyclerView rvProfile;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.fab_edit_profile)
    FloatingActionButton fabEditProfile;
    @Bind(R.id.btn_action_retry)
    Button btnRetry;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;

    @Inject
    ProfilePresenter presenter;
    @Inject
    Account account;
    private ProfileAdapter adapter;
    private LinearLayoutManager layoutManager;
    private User user;
    private boolean isAccountUser;
    private int appBarElevation;
    private int appBarHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        appBarHeight = (int) getResources().getDimension(R.dimen.profile_app_bar_height);
        ButterKnife.bind(this);
        user = fetchUser(savedInstanceState);
        UserComponent userComponent = Application.getApp(this).getUserComponent();
        userComponent.inject(this);
        isAccountUser = user.equals(account.user());
        appBarElevation = ResourceUtils.dpToPx(this, 8);
        initViews();
        presenter.onCreate(this);
        if (savedInstanceState == null && isAccountUser) {
            presenter.onUserRequested(user.getId());
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void showProgress() {
        super.showProgress();
        btnRetry.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ARG_USER, user);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void renderUser(User user) {
        this.user = user;
        coordinatorLayout.setVisibility(View.VISIBLE);
        collapsingToolbarLayout.setTitle(user.getFullName());
        Picasso.with(this).load(user.getImageUrl()).into(ivProfilePhoto);
        adapter.setUser(user);
        updateScrollFlags();
    }

    @Override
    public void renderError() {
        coordinatorLayout.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private User fetchUser(@Nullable Bundle savedInstanceState) {
        User user = ActivityUtils.restore(savedInstanceState, ARG_USER);
        if (user == null) {
            user = ActivityUtils.restore(getIntent().getExtras(), ARG_USER);
        }
        return user;
    }

    @Override
    protected void onStart() {
        super.onStart();
        renderUser(user);
    }

    private void initViews() {
        fabEditProfile.setOnClickListener(view -> ScreenNavigator.startEditProfileScreen(ProfileActivity.this));
        layoutManager = new LinearLayoutManager(this);
        rvProfile.setLayoutManager(layoutManager);
        adapter = new ProfileAdapter(this, isAccountUser, view -> ScreenNavigator.startAddCarScreen(ProfileActivity.this));
        rvProfile.setAdapter(adapter);
        btnRetry.setOnClickListener(view -> presenter.onUserRequested(this.user.getId()));
        fabEditProfile.setVisibility(isAccountUser ? View.VISIBLE : View.GONE);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            int totalScrollRange = appBarLayout1.getTotalScrollRange();
            ViewCompat.setElevation(appBarLayout1, totalScrollRange > 0 && verticalOffset == -totalScrollRange ? appBarElevation : 0);
        });
    }

    public void updateScrollFlags() {
        rvProfile.post(() -> {
            int height = appBarHeight;
            for (int i = 0; i < layoutManager.getChildCount(); i++) {
                height += layoutManager.getChildAt(i).getHeight();
            }
            boolean isListFullyVisible = getResources().getDisplayMetrics().heightPixels >= height;
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
            layoutParams.setScrollFlags(isListFullyVisible ? 0 : AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            rvProfile.setOverScrollMode(isListFullyVisible ? RecyclerView.OVER_SCROLL_NEVER : RecyclerView.OVER_SCROLL_IF_CONTENT_SCROLLS);

        });
    }
}
