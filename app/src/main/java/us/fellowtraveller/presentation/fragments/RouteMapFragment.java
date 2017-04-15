package us.fellowtraveller.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.presentation.utils.LocationUtils;

/**
 * Created by arkadius on 4/10/17.
 */

public class RouteMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    public static final String ARG_POLYLINES = "polylines";
    @Nullable
    private GoogleMap googleMap;


    public static RouteMapFragment newInstance(ArrayList<String> polylines) {

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_POLYLINES, polylines);

        RouteMapFragment fragment = new RouteMapFragment();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        drawPolylines(getArguments().getStringArrayList(ARG_POLYLINES));
    }

    public void drawPolylines(List<String> polylines) {
        if (googleMap != null) {
            googleMap.clear();
            LatLngBounds.Builder builder = LatLngBounds.builder();
            PolylineOptions polylineOptions = new PolylineOptions();

            for (String polyline : polylines) {
                List<LatLng> iterable = LocationUtils.decodePolyline(polyline);
                for (LatLng latLng : iterable) {
                    builder.include(latLng);
                }
                polylineOptions.addAll(iterable);
            }
            googleMap.addPolyline(polylineOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        }
    }
}
