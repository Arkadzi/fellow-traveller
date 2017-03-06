package us.fellowtraveller.presentation;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.dialogs.ProgressDialogFragment;
import us.fellowtraveller.presentation.presenter.SignInPresenter;
import us.fellowtraveller.presentation.view.SignInView;

public class SignInActivity extends AppCompatActivity implements SignInView {
    @Bind(R.id.et_login)
    EditText etLogin;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Inject
    SignInPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        Application.getApp(this).getUserComponent().inject(this);
        presenter.onCreate(this);

    }

    @OnClick(R.id.btn_sign_in)
    void onSignInClick() {
        String ssoId = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        presenter.onSignInButtonClick(new User(ssoId, password));
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onSignIn(User user) {

    }

    @Override
    public void showProgress() {
        DialogFragment dialog = (DialogFragment) getSupportFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog == null) {
            dialog = ProgressDialogFragment.newInstance();
            dialog.show(getSupportFragmentManager(), ProgressDialogFragment.TAG);
        }
    }

    @Override
    public void hideProgress() {
        DialogFragment dialog = (DialogFragment) getSupportFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
