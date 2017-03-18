package us.fellowtraveller.presentation.utils;

import android.content.Context;

/**
 * Created by arkadii on 3/18/17.
 */
public class ResourceUtils {
    public static int dpToPx(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp);
    }
}
