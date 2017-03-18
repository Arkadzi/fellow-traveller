package us.fellowtraveller.presentation.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
