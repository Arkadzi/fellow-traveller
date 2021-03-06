package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Place;

import java.util.Collections;
import java.util.List;

import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.adapters.items.EmptyPlace;
import us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder;

import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.FROM;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.TO;
import static us.fellowtraveller.presentation.adapters.viewholders.TripPointHolder.WAY;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointAdapter extends ItemTouchAdapter<TripPoint, TripPointHolder> implements TripPointHolder.OnClearClickListener {
    private LayoutInflater inflater;
    private TripPoint from;
    private TripPoint to;
    private OnPointClickListener pointClickListener;
    private Context context;

    public TripPointAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
        TripPointHolder tripPointHolder = new TripPointHolder(context, parent, viewType);
        tripPointHolder.itemView.setOnClickListener(v -> pointClickListener.onClick(viewType, tripPointHolder.getAdapterPosition()));
        tripPointHolder.setOnClearClickListener(this);
        switch (viewType) {
            case FROM:


        }
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

    public void addPlace(TripPoint place) {
        getItems().add(getItemCount() - 1, place);
        notifyItemInserted(getItemCount() - 2);
    }

    public void setFrom(@Nullable TripPoint from) {
        this.from = from;
        notifyItemChanged(0);
    }

    public void setTo(@Nullable TripPoint to) {
        this.to = to;
        notifyItemChanged(getItemCount() - 1);
    }

    @Nullable
    public TripPoint getFrom() {
        return from;
    }

    @Nullable
    public TripPoint getTo() {
        return to;
    }

    public void setPointClickListener(OnPointClickListener pointClickListener) {
        this.pointClickListener = pointClickListener;
    }

    public List<TripPoint> getWayPoints() {
        return getItems().subList(1, getItemCount() - 1);
    }

    public void setPlace(TripPoint tripPoint, int tag) {
        getItems().set(tag, tripPoint);
        notifyItemChanged(tag);
    }

    @Override
    public void onClick(TripPointHolder holder, int viewType) {
        switch (viewType) {
            case FROM:
                from = null;
                notifyItemChanged(0);
                break;
            case TO:
                to = null;
                notifyItemChanged(getItemCount() - 1);
                break;
            case WAY:
                int index = holder.getAdapterPosition();
                getItems().remove(index);
                notifyItemRemoved(index);
                break;
        }
        if (onItemInteractListener != null) {
            onItemInteractListener.onItemInteract();
        }

    }

    public interface OnPointClickListener {
        void onClick(int viewType, int position);
    }
}
