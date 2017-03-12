package us.fellowtraveller.presentation.utils.exceptions;

import android.widget.EditText;

/**
 * Created by arkadii on 3/12/17.
 */

public class BadFieldDataException extends RuntimeException {
    EditText editText;

    public BadFieldDataException(EditText editText) {
        this.editText = editText;
    }
}
