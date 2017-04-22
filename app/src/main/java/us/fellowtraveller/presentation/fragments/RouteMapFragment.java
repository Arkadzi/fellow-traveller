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
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.presentation.utils.LocationUtils;

/**
 * Created by arkadius on 4/10/17.
 */

public class RouteMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    public static final String ARG_POINTS = "points";
    @Nullable
    private GoogleMap googleMap;


    public static RouteMapFragment newInstance(List<Point> points) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_POINTS, new ArrayList<>(points));

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
            ArrayList<Point> points = (ArrayList<Point>) getArguments().getSerializable(ARG_POINTS);
            int index = 0;
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                builder.include(latLng);
                polylineOptions.add(latLng);
                if (point.getCollectionData() != null) {
                    googleMap.addMarker(getMarkerOption(point, ++index,
                            (i > 0 && i < points.size() - 1)
                                    ? R.drawable.point_marker_from
                                    : R.drawable.point_marker_from1));
                }
            }
            googleMap.addPolyline(polylineOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        }
    }

    @NonNull
    private MarkerOptions getMarkerOption(Point point, int i, @DrawableRes int markerBackground) {
        return new MarkerOptions()
                .position(point.getPosition())
                .icon(BitmapDescriptorFactory
                        .fromBitmap(LocationUtils
                                .generateMarker(getActivity(), markerBackground, String.valueOf(i))));
    }
}
