package us.fellowtraveller.presentation.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.RouteResult;

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
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
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

    public static List<LatLng> decodePolyline(String encodedPath) {
        int len = encodedPath.length();

        List<LatLng> path = new ArrayList<LatLng>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new LatLng(lat * 1e-5, lng * 1e-5));
        }

        return path;
    }

    private static Bitmap convertViewToBitmap(View v) {
        if (v.getMeasuredHeight() <= 0) {
            int specWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            v.measure(specWidth, specWidth);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }

    public static Bitmap generateMarker(Context context, @DrawableRes int backgroundId, String text) {
        ViewGroup marker = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.marker, null);
        marker.setBackgroundResource(backgroundId);
        ((TextView) marker.getChildAt(0)).setText(text);
        Bitmap bitmap = convertViewToBitmap(marker);
//        BitmapDrawable bmpDrawable = new BitmapDrawable(context.getResources(), bitmap);
//        bmpDrawable.setBounds(0, 0, bmpDrawable.getIntrinsicWidth(), bmpDrawable.getIntrinsicHeight());
        return bitmap;
    }

    public static List<LatLng> decodePolylines(List<String> polylines) {
        List<LatLng> result = new ArrayList<>();
        for (String polyline : polylines) {
            result.addAll(decodePolyline(polyline));
        }
        return result;

    }
}

