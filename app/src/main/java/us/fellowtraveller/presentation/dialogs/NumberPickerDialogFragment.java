package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.NumberPicker;

import us.fellowtraveller.R;

/**
 * Created by arkadii on 3/26/17.
 */

public class NumberPickerDialogFragment extends DialogFragment {

    public static final String ARG_MIN = "min";
    public static final String ARG_MAX = "max";
    public static final String TAG = "number_picker";
    private NumberPicker numberPicker;

    public static NumberPickerDialogFragment newInstance(int min, int max) {

        Bundle args = new Bundle();
        args.putInt(ARG_MIN, min);
        args.putInt(ARG_MAX, max);
        NumberPickerDialogFragment fragment = new NumberPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_number_picker, null);
        numberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
        numberPicker.setMinValue(getArguments().getInt(ARG_MIN));
        numberPicker.setMaxValue(getArguments().getInt(ARG_MAX));
        numberPicker.setValue(numberPicker.getMaxValue());
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.hint_choose_date)
                .setPositiveButton(R.string.action_ok, (dialogInterface, i) -> onNumberPicked())
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    private void onNumberPicked() {
        int number = numberPicker.getValue();
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof NumberPickerListener) {
            ((NumberPickerListener) parentFragment).onNumberPicked(number);
        } else if (getActivity() instanceof NumberPickerListener) {
            ((NumberPickerListener) getActivity()).onNumberPicked(number);
        }
    }

    public interface NumberPickerListener {
        void onNumberPicked(int number);
    }
}
