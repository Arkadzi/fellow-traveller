package us.fellowtraveller.presentation.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.presentation.fragments.DriverFragment;
import us.fellowtraveller.presentation.fragments.TravellerFragment;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

public class MainActivity extends BaseActivity {

    @Bind(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_traveller:
                    setFragment(TravellerFragment.TAG);
                    return true;
                case R.id.navigation_driver:
                    setFragment(DriverFragment.TAG);
                    return true;
            }
            return false;
        });
        if (savedInstanceState == null) {
            setFragment(TravellerFragment.TAG);
        }
    }

    private void setFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            switch (tag) {
                case TravellerFragment.TAG:
                    fragment = TravellerFragment.newInstance();
                    break;
                case DriverFragment.TAG:
                    fragment = DriverFragment.newInstance();
                    break;
            }
            if (fragment != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, tag)
                        .commit();
            }
        }

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_profile:
                Account account = Application.getApp(this).getUserComponent().account();
                ScreenNavigator.startProfileScreen(this, account.user());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
