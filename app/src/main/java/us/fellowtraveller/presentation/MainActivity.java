package us.fellowtraveller.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.Account;

public class MainActivity extends AppCompatActivity {
    @Inject
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Application.getApp(this).getUserComponent().inject(this);
        if (!account.isAuthorized()) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }
}
