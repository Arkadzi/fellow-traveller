package us.fellowtraveller.presentation.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;
import java.util.List;

import us.fellowtraveller.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by arkadius on 4/9/17.
 */

public class LocationUtils {
    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 2010;
    private static final int REQUEST_CODE_LOCATION = 2020;

    private static boolean hasLocationPermissions(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED;
    }

    public static void requestLocation(Activity activity) {
        if (hasLocationPermissions(activity)) {
            startIntent(activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSIONS);
        }
    }

    public static boolean onPermissionRequested(Activity activity, int requestCode) {
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS) {
            if (hasLocationPermissions(activity)) {
                startIntent(activity);
            } else {
                Toast.makeText(activity, R.string.warn_user_denied_location_permission, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }

    private static void startIntent(Activity activity) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            activity.startActivityForResult(builder.build(activity), REQUEST_CODE_LOCATION);
        } catch (GooglePlayServicesNotAvailableException |GooglePlayServicesRepairableException  e) {
            Toast.makeText(activity, R.string.error_play_services, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean onActivityResult(int requestCode) {
        return requestCode == REQUEST_CODE_LOCATION;
    }

    @Nullable
    public static Place fetchPlace(Activity activity, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            return PlacePicker.getPlace(activity, data);
        }
        return null;
    }

    @Nullable
    public static Address getAddress(Context context, double latitude, double longitude) {
        try {
            List<Address> addresses = new Geocoder(context).getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

