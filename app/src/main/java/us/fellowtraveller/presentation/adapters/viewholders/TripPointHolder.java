package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.TripPoint;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointHolder extends RecyclerView.ViewHolder {
    public TripPointHolder(LayoutInflater inflater, ViewGroup viewGroup) {
        super(inflater.inflate(R.layout.trip_point_item, viewGroup, false));
    }

    public void bind(TripPoint item) {

    }
}
