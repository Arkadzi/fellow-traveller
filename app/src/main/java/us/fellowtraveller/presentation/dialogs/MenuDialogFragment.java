package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.io.Serializable;

import rx.Observable;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.Car;

/**
 * Created by arkadii on 3/27/17.
 */

public class MenuDialogFragment<T extends Serializable> extends DialogFragment {

    public static final String ARG_ITEMS = "items";
    public static final String TAG = "menu_dialog";
    public static final String ARG_DATA = "data";

    public static <T extends Serializable> MenuDialogFragment<T> newInstance(String[] items, T data) {

        Bundle args = new Bundle();
        args.putStringArray(ARG_ITEMS, items);
        args.putSerializable(ARG_DATA, data);
        MenuDialogFragment<T> fragment = new MenuDialogFragment<>();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setItems(getArguments().getStringArray(ARG_ITEMS), (dialogInterface, i) -> onMenuClicked(i))
                .create();
    }

    private void onMenuClicked(int i) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof OnMenuClickListener) {
            ((OnMenuClickListener<T>) parentFragment).onMenuClick(i, (T) getArguments().getSerializable(ARG_DATA));
        } else if (getActivity() instanceof OnMenuClickListener) {
            ((OnMenuClickListener<T>) getActivity()).onMenuClick(i, (T) getArguments().getSerializable(ARG_DATA));
        }
    }

    public interface OnMenuClickListener<T extends Serializable> {
        void onMenuClick(int position, T data);
    }
}
