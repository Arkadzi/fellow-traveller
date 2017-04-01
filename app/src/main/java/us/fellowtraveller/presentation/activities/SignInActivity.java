package us.fellowtraveller.presentation.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.presenter.SignInPresenter;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.ScreenNavigator;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;
import us.fellowtraveller.presentation.view.SignInView;

public class SignInActivity extends ProgressActivity implements SignInView {
    public static final int REQUEST_CODE_READ_SMS = 1010;
    @Bind(R.id.et_login)
    EditText etLogin;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Inject
    SignInPresenter presenter;
    public static final String main_data[] = {
            "data1", "is_primary", "data3", "data2", "data1", "is_primary", "photo_uri", "mimetype"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setTitle(R.string.action_sign_in);
        ButterKnife.bind(this);
        Application.getApp(this).getUserComponent().inject(this);
        presenter.onCreate(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            setPhoneNumber();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_READ_SMS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_SMS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setPhoneNumber();
        }
    }

    private void setPhoneNumber() {
//        etLogin.post(() -> {
            TelephonyManager tMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            etLogin.setText(tMgr.getLine1Number());
//        });
    }

    @OnClick(R.id.btn_sign_in)
    void onSignInClick() {
        try {
            String ssoId = FieldUtils.getNonEmptyText(etLogin);
            String password = FieldUtils.getNonEmptyText(etPassword);
            presenter.onSignInButtonClick(new AccountUser(ssoId, password));
        } catch (BadFieldDataException e) {
        }
    }

    @OnClick(R.id.text_sign_up)
    void onSignUpClick() {
        ScreenNavigator.startSignUpScreen(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onSignIn(User user) {
        ScreenNavigator.startMainScreen(this, true);
    }

}
