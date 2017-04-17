package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.fragments.RouteMapFragment;
import us.fellowtraveller.presentation.utils.LocationUtils;

/**
 * Created by arkadius on 4/15/17.
 */

public class MapDialog extends DialogFragment {

    public static final String TAG = "map_dialog";
    public static final String ARG_POLYLINES = "polylines";
    public static final String ARG_WAYPOINTS = "waypoints";
    public static final String ARG_FROM = "from";
    public static final String ARG_TO = "to";
    @Nullable
    private MapDialogListener mapDialogListener;
    private MapView mapView;
    private GoogleMap googleMap;

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        return new AlertDialog.Builder(getActivity())
//                .setView(view)
//                .setPositiveButton(R.string.action_next, (dialog, which) -> {
//                    if (mapDialogListener != null) {
//                        mapDialogListener.onNext();
//                    }
//                })
//                .setNegativeButton(R.string.action_proceed_edit, null)
//                .create();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(R.layout.map_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getChildFragmentManager().findFragmentById(R.id.map_container) == null) {
            ArrayList<String> polylines = getArguments().getStringArrayList(ARG_POLYLINES);
            ArrayList<TripPoint> waypoints = (ArrayList<TripPoint>) getArguments().getSerializable(ARG_WAYPOINTS);
            TripPoint from = (TripPoint) getArguments().getSerializable(ARG_FROM);
            TripPoint to = (TripPoint) getArguments().getSerializable(ARG_TO);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, RouteMapFragment.newInstance(polylines, waypoints, from, to))
                    .commit();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapDialogListener != null) {
            mapDialogListener.onDismiss();
        }
    }

    public static void show(FragmentManager fragmentManager, MapDialogListener listener,
                            ArrayList<String> polylines, ArrayList<TripPoint> wayPoints,
                            TripPoint from, TripPoint to) {
        MapDialog mapDialog = new MapDialog();

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_POLYLINES, polylines);
        args.putSerializable(ARG_WAYPOINTS, wayPoints);
        args.putSerializable(ARG_FROM, from);
        args.putSerializable(ARG_TO, to);

        mapDialog.setArguments(args);
        mapDialog.setMapDialogListener(listener);
        mapDialog.show(fragmentManager, TAG);
    }

    public void setMapDialogListener(@Nullable MapDialogListener mapDialogListener) {
        this.mapDialogListener = mapDialogListener;
    }

    public interface MapDialogListener {
        void onNext();
        void onDismiss();
    }
}
