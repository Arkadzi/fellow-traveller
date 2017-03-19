package us.fellowtraveller.presentation.app.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by arkadii on 3/19/17.
 */

public class DateTextView extends TextView {

    public static final String ARG_SUPER_STATE = "super_state";
    public static final String ARG_DATE = "date";
    public static final int DATE_UNSPECIFIED = -1;
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.getDefault());

    private long date = DATE_UNSPECIFIED;

    public DateTextView(Context context) {
        super(context);
    }

    public DateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
        if (date >= 0) {
            setText(dateFormat.format(new Date(date)));
        } else {
            setText("");
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_SUPER_STATE, super.onSaveInstanceState());
        bundle.putLong(ARG_DATE, date);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            setDate(((Bundle) state).getLong(ARG_DATE, DATE_UNSPECIFIED));
            state = ((Bundle) state).getParcelable(ARG_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }
}
