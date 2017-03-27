package us.fellowtraveller.presentation.activities;

import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import us.fellowtraveller.presentation.dialogs.ProgressDialogFragment;
import us.fellowtraveller.presentation.view.ProgressView;

/**
 * Created by arkadii on 3/7/17.
 */

public class ProgressActivity extends BaseActivity implements ProgressView {

    @Override
    public void showProgress() {
        DialogFragment dialog = (DialogFragment) getSupportFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog == null) {
            dialog = ProgressDialogFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(dialog, ProgressDialogFragment.TAG)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void hideProgress() {
        DialogFragment dialog = (DialogFragment) getSupportFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(dialog)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
