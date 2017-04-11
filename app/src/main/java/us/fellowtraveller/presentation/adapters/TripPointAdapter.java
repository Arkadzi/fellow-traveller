package us.fellowtraveller.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Place;

import us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointAdapter extends ItemTouchAdapter<Place, TripPointHolder> {
    private LayoutInflater inflater;

    public TripPointAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public TripPointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TripPointHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(TripPointHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void addPlace(Place place) {
        getItems().add(place);
        notifyItemInserted(getItemCount() - 1);
    }
}
