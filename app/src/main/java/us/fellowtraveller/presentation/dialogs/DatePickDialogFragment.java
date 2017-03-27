package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import us.fellowtraveller.R;

/**
 * Created by arkadii on 3/19/17.
 */

public class DatePickDialogFragment extends DialogFragment {

    public static final String ARG_DATE = "date";
    public static final String TAG = "date_pick_dialog";
    private DatePicker datePicker;

    public static DatePickDialogFragment newInstance(long initDate) {
        Bundle args = new Bundle();
        args.putLong(ARG_DATE, initDate);
        DatePickDialogFragment fragment = new DatePickDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        Calendar instance = Calendar.getInstance();
        long timeMillis = getArguments().getLong(ARG_DATE);
        if (timeMillis <= 0) {
            instance.setTime(new Date());
        } else {
            instance.setTime(new Date(timeMillis));
        }
        datePicker.updateDate(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.hint_choose_date)
                .setPositiveButton(R.string.action_ok, (dialogInterface, i) -> onDatePicked())
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    private void onDatePicked() {
        Calendar instance = Calendar.getInstance();
        instance.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 0, 0, 0);
        long date = instance.getTime().getTime();

        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof DatePickerListener) {
            ((DatePickerListener) parentFragment).onDatePicked(date);
        } else if (getActivity() instanceof DatePickerListener) {
            ((DatePickerListener) getActivity()).onDatePicked(date);
        }
    }

    public interface DatePickerListener {
        void onDatePicked(long date);
    }
}
