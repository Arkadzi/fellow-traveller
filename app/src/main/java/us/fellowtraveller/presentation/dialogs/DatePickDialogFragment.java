package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import us.fellowtraveller.R;

/**
 * Created by arkadii on 3/19/17.
 */

public class DatePickDialogFragment extends DialogFragment {

    public static final int MODE_DATE = 0;
    public static final int MODE_DATE_TIME = 1;
    public static final String TAG = "date_pick_dialog";
    private static final String ARG_MODE = "mode";
    private int mode;
    private DatePicker datePicker;
    private TimePicker timePicker;

    public static DatePickDialogFragment newDateTimeInstance() {
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, MODE_DATE_TIME);
        DatePickDialogFragment fragment = new DatePickDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DatePickDialogFragment newDateInstance() {
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, MODE_DATE);
        DatePickDialogFragment fragment = new DatePickDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        Calendar instance = Calendar.getInstance();
        if (isDateTimeMode()) {
            datePicker.setMinDate(instance.getTimeInMillis());
        }
        datePicker.setVisibility(View.VISIBLE);
        timePicker.setVisibility(View.GONE);
        datePicker.updateDate(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.hint_choose_date)
                .setPositiveButton(R.string.action_ok, null)
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(i -> {
            if (isDateTimeMode() && timePicker.getVisibility() == View.GONE) {
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
            } else {
                onDatePicked();
                dismiss();
            }
        });
    }

    private boolean isDateTimeMode() {
        return getArguments().getInt(ARG_MODE) == MODE_DATE_TIME;
    }

    private void onDatePicked() {
        Calendar instance = Calendar.getInstance();
        instance.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), getHour(), getMinute(), 0);
        long date = instance.getTime().getTime();

        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof DatePickerListener) {
            ((DatePickerListener) parentFragment).onDatePicked(date);
        } else if (getActivity() instanceof DatePickerListener) {
            ((DatePickerListener) getActivity()).onDatePicked(date);
        }
    }

    private int getHour() {
        if (isDateTimeMode()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return timePicker.getHour();
            } else {
                return timePicker.getCurrentHour();
            }
        }
        return 0;
    }

    private int getMinute() {
        if (isDateTimeMode()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return timePicker.getMinute();
            } else {
                return timePicker.getCurrentMinute();
            }
        }
        return 0;
    }

    public static void showDateTime(FragmentManager fragmentManager) {
        DatePickDialogFragment fragment = newDateTimeInstance();
        fragmentManager.beginTransaction()
                .add(fragment, TAG)
                .commitAllowingStateLoss();
    }

    public interface DatePickerListener {
        void onDatePicked(long date);
    }
}
