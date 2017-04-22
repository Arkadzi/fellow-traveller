package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.adapters.viewholders.RouteViewHolder;

/**
 * Created by arkadius on 4/19/17.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Route> data = new ArrayList<>();
    private Comparator<Route> routeComparator = (o1, o2) -> {
        if (o2.getTime() - o1.getTime() > 0) return 1;
        return -1;
    };
    @Nullable
    private OnRouteClickListener routeClickListener;
    @Nullable
    private OnRouteLongClickListener routeLongClickListener;

    public RouteAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RouteViewHolder routeViewHolder = new RouteViewHolder(layoutInflater, parent);
        routeViewHolder.itemView.setOnClickListener(v -> {
            if (routeClickListener != null) {
                routeClickListener.onRouteClick(data.get(routeViewHolder.getAdapterPosition()));
            }
        });
        routeViewHolder.itemView.setOnLongClickListener(v -> {
            if (routeLongClickListener != null) {
                int adapterPosition = routeViewHolder.getAdapterPosition();
                routeLongClickListener.onRouteClick(adapterPosition, data.get(adapterPosition));
            }
            return false;
        });
        return routeViewHolder;
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    public void setData(List<Route> data) {
        this.data.clear();
        this.data.addAll(data);
        Collections.sort(data, routeComparator);
        notifyDataSetChanged();
    }

    public void addRoute(Route route) {
        data.add(route);
        Collections.sort(data, routeComparator);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setRouteClickListener(@Nullable OnRouteClickListener routeClickListener) {
        this.routeClickListener = routeClickListener;
    }

    public void setRouteLongClickListener(@Nullable OnRouteLongClickListener routeLongClickListener) {
        this.routeLongClickListener = routeLongClickListener;
    }

    public void remove(Route response) {
        for (int i = 0; i < data.size(); i++) {
            if (response.getId().equals(data.get(i).getId())) {
                data.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public interface OnRouteClickListener {
        void onRouteClick(Route route);
    }

    public interface OnRouteLongClickListener {
        void onRouteClick(int position, Route route);
    }
}