package us.fellowtraveller.presentation.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by arkadii on 3/18/17.
 */

public class ActivityUtils {

    public static <T> T restore(@Nullable Bundle bundle, String key) {
        if (bundle != null) {
            return (T) bundle.getSerializable(key);
        }
        return null;
    }

    public static <T> T restore(@Nullable Bundle bundle, String key, T defaultValue) {
        if (bundle != null) {
            T serializable = (T) bundle.getSerializable(key);
            if (serializable != null) {
                return serializable;
            }
        }
        return defaultValue;
    }
}
