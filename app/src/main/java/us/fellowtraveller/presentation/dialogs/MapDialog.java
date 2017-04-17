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
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.fragments.RouteMapFragment;
import us.fellowtraveller.presentation.utils.LocationUtils;

/**
 * Created by arkadius on 4/15/17.
 */

public class MapDialog extends DialogFragment {

    public static final String TAG = "map_dialog";
    @Nullable
    private MapDialogListener mapDialogListener;
    private ArrayList<Point> points;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_seats_count)
    EditText etSeatsCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(R.layout.map_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getChildFragmentManager().findFragmentById(R.id.map_container) == null) {

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, RouteMapFragment.newInstance(points))
                    .commit();
        }
    }

    @OnClick(R.id.btn_ok)
    void onOkClick() {

    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
        if (mapDialogListener != null) {
            mapDialogListener.onDismiss();
        }
    }

    public void setPoints(List<Point> points) {
        this.points = new ArrayList<>(points);
    }

    public static void show(FragmentManager fragmentManager,
                            MapDialogListener listener,
                            List<Point> points) {
        MapDialog mapDialog = new MapDialog();

        mapDialog.setMapDialogListener(listener);
        mapDialog.setPoints(points);
        mapDialog.show(fragmentManager, TAG);
    }

    public void setMapDialogListener(@Nullable MapDialogListener mapDialogListener) {
        this.mapDialogListener = mapDialogListener;
    }

    public interface MapDialogListener {
        void onCreated();

        void onDismiss();
    }
}
