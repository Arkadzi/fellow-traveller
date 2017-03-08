package us.fellowtraveller.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.presenter.SignUpPresenter;
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
        ButterKnife.bind(this);
        Application.getApp(this).getUserComponent().inject(this);
        presenter.onCreate(this);
        rgGender.check(R.id.rb_male);
        etLogin.post(() -> {
            TelephonyManager tMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            etLogin.setText(tMgr.getLine1Number());
        });
    }


    @OnClick(R.id.btn_sign_up)
    void onSignUpClick() {
        String ssoId = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String gender = rgGender.getCheckedRadioButtonId() == R.id.rb_male
                ? User.GENDER_MALE
                : User.GENDER_FEMALE;

        presenter.onSignUpButtonClick(new User(ssoId, password, firstName, lastName,email, gender));
    }



    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onSignUp(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
