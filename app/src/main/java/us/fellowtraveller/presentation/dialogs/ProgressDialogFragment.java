package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import us.fellowtraveller.R;


/**
 * Created by sebastian on 07.07.16.
 */
public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG = "progress_dialog";

    public static DialogFragment newInstance() {
        DialogFragment fragment = new ProgressDialogFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_progress);
        return builder.create();
    }
}
