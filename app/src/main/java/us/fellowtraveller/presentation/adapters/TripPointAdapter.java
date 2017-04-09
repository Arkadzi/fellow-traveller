package us.fellowtraveller.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointAdapter extends ItemTouchAdapter<TripPoint, TripPointHolder> {
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
}
