package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.Route;

/**
 * Created by arkadius on 4/19/17.
 */

public class RouteViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_title)
    TextView tvTitle;

    public RouteViewHolder(LayoutInflater inflater, ViewGroup viewGroup) {
        super(inflater.inflate(R.layout.item_route, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(Route route) {
        tvTitle.setText(route.getTitle());
    }
}
