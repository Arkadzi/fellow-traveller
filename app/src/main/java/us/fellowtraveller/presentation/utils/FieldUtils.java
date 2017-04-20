package us.fellowtraveller.presentation.utils;

import android.content.Context;
import android.widget.EditText;

import us.fellowtraveller.R;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;

/**
 * Created by arkadii on 3/12/17.
 */

public class FieldUtils {
    public static String getNonEmptyText(EditText editText) {
        String fieldText = editText.getText().toString();
        if (fieldText.isEmpty()) {
            Context context = editText.getContext();
            String string = context.getString(R.string.error_empty_field);
            editText.setError(string);
            throw new BadFieldDataException(editText);
        } else {
            return fieldText;
        }
    }

    public static int getInt(EditText editText) {
        String fieldText = editText.getText().toString();
        if (fieldText.isEmpty()) {
            Context context = editText.getContext();
            String string = context.getString(R.string.error_empty_field);
            editText.setError(string);
            throw new BadFieldDataException(editText);
        } else {
            try {
                return Integer.parseInt(fieldText);
            }catch (RuntimeException e) {
                Context context = editText.getContext();
                String string = context.getString(R.string.error_not_a_number);
                editText.setError(string);
                throw new BadFieldDataException(editText);
            }
        }
    }

    public static float getFloat(EditText editText) {
        String fieldText = editText.getText().toString();
        if (fieldText.isEmpty()) {
            Context context = editText.getContext();
            String string = context.getString(R.string.error_empty_field);
            editText.setError(string);
            throw new BadFieldDataException(editText);
        } else {
            try {
                return Float.parseFloat(fieldText);
            }catch (RuntimeException e) {
                Context context = editText.getContext();
                String string = context.getString(R.string.error_not_a_number);
                editText.setError(string);
                throw new BadFieldDataException(editText);
            }
        }
    }
}
