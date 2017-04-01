package us.fellowtraveller.presentation.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.RadioGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.presenter.SignUpPresenter;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.ScreenNavigator;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;
import us.fellowtraveller.presentation.view.SignUpView;

public class SignUpActivity extends ProgressActivity implements SignUpView {
    @Bind(R.id.et_login)
    EditText etLogin;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_first_name)
    EditText etFirstName;
    @Bind(R.id.et_last_name)
    EditText etLastName;
    @Bind(R.id.rg_gender)
    RadioGroup rgGender;
    @Inject
    SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle(R.string.action_sign_up);
        ButterKnife.bind(this);
        Application.getApp(this).getUserComponent().inject(this);
        presenter.onCreate(this);
        rgGender.check(R.id.rb_male);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            setPhoneNumber();
        }
    }

    private void setPhoneNumber() {
//        etLogin.post(() -> {
            TelephonyManager tMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            etLogin.setText(tMgr.getLine1Number());
//        });
    }


    @OnClick(R.id.btn_sign_up)
    void onSignUpClick() {
        try {
            String ssoId = FieldUtils.getNonEmptyText(etLogin);
            String password = FieldUtils.getNonEmptyText(etPassword);
            String firstName = FieldUtils.getNonEmptyText(etFirstName);
            String lastName = FieldUtils.getNonEmptyText(etLastName);
            String email = FieldUtils.getNonEmptyText(etEmail);
            String gender = rgGender.getCheckedRadioButtonId() == R.id.rb_male
                    ? User.GENDER_MALE
                    : User.GENDER_FEMALE;

            presenter.onSignUpButtonClick(new AccountUser(ssoId, password, firstName, lastName, email, gender));
        } catch (BadFieldDataException e) {}
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onSignUp(User user) {
        ScreenNavigator.startMainScreen(this, true);
    }
}
