package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import us.fellowtraveller.R;
import us.fellowtraveller.presentation.utils.ImageUtils;

/**
 * Created by arkadii on 3/28/17.
 */

public class ChooseImageDialogFragment extends DialogFragment {
    public static final String TAG = "choose_image_dialog";

    public static ChooseImageDialogFragment newInstance() {

        Bundle args = new Bundle();

        ChooseImageDialogFragment fragment = new ChooseImageDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_choose_picture, null);

        view.findViewById(R.id.item_camera).setOnClickListener(v -> {
            ImageUtils.requestPhoto(getActivity());
            dismiss();
        });
        view.findViewById(R.id.item_gallery).setOnClickListener(v -> {
            ImageUtils.requestImage(getActivity());
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
