package us.fellowtraveller.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.utils.LocationUtils;

/**
 * Created by arkadius on 4/10/17.
 */

public class RouteMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    public static final String ARG_POLYLINES = "polylines";
    public static final String ARG_WAYPOINTS = "waypoints";
    private static final String ARG_FROM = "from";
    private static final String ARG_TO = "to";
    @Nullable
    private GoogleMap googleMap;


    public static RouteMapFragment newInstance(ArrayList<String> polylines, ArrayList<TripPoint> wayPoints, TripPoint from, TripPoint to) {

        Bundle args = new Bundle();

        args.putStringArrayList(ARG_POLYLINES, polylines);
        args.putSerializable(ARG_WAYPOINTS, wayPoints);
        args.putSerializable(ARG_FROM, from);
        args.putSerializable(ARG_TO, to);

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
//        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        drawPolylines();
    }

    public void drawPolylines() {
        if (googleMap != null) {
            googleMap.clear();
            LatLngBounds.Builder builder = LatLngBounds.builder();
            PolylineOptions polylineOptions = new PolylineOptions();
            ArrayList<String> polylines = getArguments().getStringArrayList(ARG_POLYLINES);
            for (String polyline : polylines) {
                List<LatLng> iterable = LocationUtils.decodePolyline(polyline);
                for (LatLng latLng : iterable) {
                    builder.include(latLng);
                }
                polylineOptions.addAll(iterable);
            }
            googleMap.addPolyline(polylineOptions);
            TripPoint from = (TripPoint) getArguments().getSerializable(ARG_FROM);
            TripPoint to = (TripPoint) getArguments().getSerializable(ARG_TO);
            ArrayList<TripPoint> waypoints = (ArrayList<TripPoint>) getArguments().getSerializable(ARG_WAYPOINTS);
            googleMap.addMarker(getMarkerOption(from, 1, R.drawable.point_marker_from1));
            for (int i = 0; i < waypoints.size(); i++) {
                googleMap.addMarker(getMarkerOption(waypoints.get(i), i + 2, R.drawable.point_marker_from));
            }

            googleMap.addMarker(getMarkerOption(to, waypoints.size() + 2, R.drawable.point_marker_from1));


            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        }
    }

    @NonNull
    private MarkerOptions getMarkerOption(TripPoint point, int i, @DrawableRes int markerBackground) {
        return new MarkerOptions()
                .position(point.getLatLng())
                .icon(BitmapDescriptorFactory
                        .fromBitmap(LocationUtils
                                .generateMarker(getActivity(), markerBackground, String.valueOf(i))));
    }
}
