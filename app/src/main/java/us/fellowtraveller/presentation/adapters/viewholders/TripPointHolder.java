package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import us.fellowtraveller.R;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointHolder extends RecyclerView.ViewHolder {

    private final TextView tvAddress;

    public TripPointHolder(LayoutInflater inflater, ViewGroup viewGroup) {
        super(inflater.inflate(R.layout.trip_point_item, viewGroup, false));
        tvAddress = ((TextView) itemView.findViewById(R.id.tv_point_address));
    }

    public void bind(Place item) {
        tvAddress.setText(item.getName() + "/" + item.getAddress());
    }
}
