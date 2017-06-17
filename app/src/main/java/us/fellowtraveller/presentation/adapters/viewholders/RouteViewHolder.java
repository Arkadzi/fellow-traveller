package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.Route;

/**
 * Created by arkadius on 4/19/17.
 */

public class RouteViewHolder extends RecyclerView.ViewHolder {
    private final SimpleDateFormat formatFirst = new SimpleDateFormat("d MMM yyyy, H:mm");
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_seats)
    TextView tvSeats;
    @Bind(R.id.tv_price_hint)
    TextView tvPriceHint;
    private String userId;

    public RouteViewHolder(LayoutInflater inflater, ViewGroup viewGroup, String userId) {
        super(inflater.inflate(R.layout.item_route, viewGroup, false));
        this.userId = userId;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Route route) {
        tvTitle.setText(route.getTitle());
        tvDate.setText(formatFirst.format(new Date(route.getTime())));
        float price = route.getPrice();
        tvPrice.setText(price == (int) price ? String.valueOf((int) price) : String.format(Locale.ENGLISH, "%.2f", price));
        tvSeats.setText(String.valueOf(route.getSeatsAvailable()));
        tvPriceHint.setText(route.getOwner().equals(userId) && !route.isMarshrutka() ? R.string.hint_price_per_km : R.string.hint_price);

    }
}
