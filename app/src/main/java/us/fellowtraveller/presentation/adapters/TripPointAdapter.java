package us.fellowtraveller.presentation.adapters;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Place;

import java.util.Collections;
import java.util.List;

import us.fellowtraveller.presentation.adapters.items.EmptyPlace;
import us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder;

import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.FROM;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.TO;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.WAY;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointAdapter extends ItemTouchAdapter<Place, TripPointHolder> {
    private LayoutInflater inflater;
    private Place from;
    private Place to;
    private OnPointClickListener pointClickListener;

    public TripPointAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        getItems().add(new EmptyPlace());
        getItems().add(new EmptyPlace());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FROM;
        } else if (position == getItemCount() - 1) {
            return TO;
        } else {
            return WAY;
        }
    }

    @Override
    public TripPointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TripPointHolder tripPointHolder = new TripPointHolder(inflater, parent, viewType);
        tripPointHolder.itemView.setOnClickListener(v -> pointClickListener.onClick(viewType));
        return tripPointHolder;
    }

    @Override
    public void onBindViewHolder(TripPointHolder holder, int position) {
        if (position == 0) {
            holder.bind(from);
        } else if (position == getItemCount() - 1) {
            holder.bind(to);
        } else {
            holder.bind(getItem(position));
        }
    }

    public void addPlace(Place place) {
        getItems().add(getItemCount() - 1,  place);
        notifyItemInserted(getItemCount() - 2);
    }

    public void setFrom(@Nullable Place from) {
        this.from = from;
        notifyItemChanged(0);
    }

    public void setTo(@Nullable Place to) {
        this.to = to;
        notifyItemChanged(getItemCount() - 1);
    }

    @Nullable
    public Place getFrom() {
        return from;
    }

    @Nullable
    public Place getTo() {
        return to;
    }

    public void setPointClickListener(OnPointClickListener pointClickListener) {
        this.pointClickListener = pointClickListener;
    }

    public List<Place> getWayPoints() {
        return getItems().subList(1, getItemCount() - 1);
    }

    public interface OnPointClickListener {
        void onClick(int viewType);
    }
}