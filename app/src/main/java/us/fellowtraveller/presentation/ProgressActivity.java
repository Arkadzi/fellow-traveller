package us.fellowtraveller.presentation;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import us.fellowtraveller.presentation.dialogs.ProgressDialogFragment;
import us.fellowtraveller.presentation.presenter.ProgressView;

/**
 * Created by arkadii on 3/7/17.
 */

public class ProgressActivity extends AppCompatActivity implements ProgressView {

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
