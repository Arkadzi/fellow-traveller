package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.NumberPicker;

import us.fellowtraveller.R;

/**
 * Created by arkadii on 3/26/17.
 */

public class TimePickerDialog extends DialogFragment {

    public static final String TAG = "time_picker";
    public static final String ARG_TAG = "tag";
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;

    public static TimePickerDialog newInstance(int tag) {

        Bundle args = new Bundle();
        args.putInt(ARG_TAG, tag);
        TimePickerDialog fragment = new TimePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }


    public static void show(FragmentManager fragmentManager, int tag) {
        TimePickerDialog fragment = newInstance(tag);
        fragmentManager.beginTransaction()
                .add(fragment, TAG)
                .commitAllowingStateLoss();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time_picker, null);
        hourPicker = (NumberPicker) view.findViewById(R.id.hour_picker);
        minutePicker = (NumberPicker) view.findViewById(R.id.minute_picker);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(30);
        String[] minutes = new String[11];
        for (int i = 0; i < minutes.length; i++) {
            minutes[i] = String.valueOf(i * 5 + 5);
        }
        minutePicker.setDisplayedValues(minutes);
        minutePicker.setMaxValue(10);
        minutePicker.setMinValue(0);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.hint_choose_time_to_get)
                .setPositiveButton(R.string.action_ok, (dialogInterface, i) -> onNumberPicked())
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    private void onNumberPicked() {
        int hour = hourPicker.getValue();
        int minute = 5 * minutePicker.getValue() + 5;
        int tag = getArguments().getInt(ARG_TAG);
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof TimePickerListener) {
            ((TimePickerListener) parentFragment).onTimePicked(hour, minute, tag);
        } else if (getActivity() instanceof TimePickerListener) {
            ((TimePickerListener) getActivity()).onTimePicked(hour, minute, tag);
        }
    }

    public interface TimePickerListener {
        void onTimePicked(int hour, int minute, int tag);
    }
}
