package us.fellowtraveller.presentation.fragments;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import us.fellowtraveller.presentation.dialogs.ProgressDialogFragment;
import us.fellowtraveller.presentation.view.ProgressView;

/**
 * Created by arkadius on 4/22/17.
 */

public class ProgressFragment extends Fragment implements ProgressView {

    @Override
    public void showProgress() {
        DialogFragment dialog = (DialogFragment) getFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog == null) {
            dialog = ProgressDialogFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(dialog, ProgressDialogFragment.TAG)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void hideProgress() {
        DialogFragment dialog = (DialogFragment) getFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.TAG);
        if (dialog != null) {
            getFragmentManager().beginTransaction()
                    .remove(dialog)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
