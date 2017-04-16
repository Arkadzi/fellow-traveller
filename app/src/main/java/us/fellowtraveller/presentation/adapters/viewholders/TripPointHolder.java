package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.text.SimpleDateFormat;
import java.util.Date;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.trip.TripPoint;

/**
 * Created by arkadius on 4/9/17.
 */

public class TripPointHolder extends MovableViewHolder {
    public static final int FROM = 1;
    public static final int TO = 2;
    public static final int WAY = 0;
    private final int type;
    private final TextView tvAddress;
    private final TextView tvTime;
    private SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy, H:mm");

    public TripPointHolder(LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        super(inflater.inflate(R.layout.trip_point_item, viewGroup, false));
        type = viewType;
        tvAddress = ((TextView) itemView.findViewById(R.id.tv_point_address));
        tvTime = ((TextView) itemView.findViewById(R.id.tv_point_time));
        itemView.findViewById(R.id.from_point).setVisibility(viewType == FROM ? View.VISIBLE : View.INVISIBLE);
        itemView.findViewById(R.id.to_point).setVisibility(viewType == TO ? View.VISIBLE : View.INVISIBLE);
        itemView.findViewById(R.id.way_point).setVisibility(viewType == WAY ? View.VISIBLE : View.INVISIBLE);

        switch (type) {
            case FROM:
                tvAddress.setHint(R.string.hint_from);
                break;
            case TO:
                tvAddress.setHint(R.string.hint_to);
                break;
        }
    }

    public void bind(TripPoint item) {
        if (item != null) {
            tvAddress.setText(item.toString());
            tvTime.setText(format.format(new Date(item.getDatetime())));
            tvTime.setVisibility(View.VISIBLE);
        } else {
            tvAddress.setText("");
            tvTime.setVisibility(View.GONE);
            tvTime.setText("");
        }
    }

    @Override
    public boolean isMovable() {
        return type == WAY;
    }
}
