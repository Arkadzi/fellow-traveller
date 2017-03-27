package us.fellowtraveller.presentation.app.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by arkadii on 3/19/17.
 */

public class NumberTextView extends TextView {

    public static final String ARG_SUPER_STATE = "super_state";
    public static final String ARG_NUMBER = "number";
    public static final int NUMBER_UNSPECIFIED = -1;

    private int number = NUMBER_UNSPECIFIED;

    public NumberTextView(Context context) {
        super(context);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if (number != NUMBER_UNSPECIFIED) {
            setText(String.valueOf(number));
        } else {
            setText("");
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(ARG_NUMBER, number);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            setNumber(((Bundle) state).getInt(ARG_NUMBER, NUMBER_UNSPECIFIED));
            state = ((Bundle) state).getParcelable(ARG_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }
}
