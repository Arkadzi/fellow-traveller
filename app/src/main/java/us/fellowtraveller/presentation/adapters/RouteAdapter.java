package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    public RouteAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RouteViewHolder(layoutInflater, parent);
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
}