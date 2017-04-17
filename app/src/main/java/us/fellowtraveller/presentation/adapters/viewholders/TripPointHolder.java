package us.fellowtraveller.presentation.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
    private final String hintTrip;
    private final ImageView ivClear;
    private SimpleDateFormat formatFirst = new SimpleDateFormat("d MMM yyyy, H:mm");
    @Nullable
    private OnClearClickListener onClearClickListener;
//    private SimpleDateFormat formatTime = new SimpleDateFormat("H:mm");

    public TripPointHolder(Context context, ViewGroup viewGroup, int viewType) {
        super(LayoutInflater.from(context).inflate(R.layout.trip_point_item, viewGroup, false));
        type = viewType;
//        formatTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        tvAddress = ((TextView) itemView.findViewById(R.id.tv_point_address));
        tvTime = ((TextView) itemView.findViewById(R.id.tv_point_time));
        ivClear = ((ImageView) itemView.findViewById(R.id.iv_clear));
        ivClear.setOnClickListener(v -> {
            if (onClearClickListener != null) {
                onClearClickListener.onClick(this, type);
            }
        });
        itemView.findViewById(R.id.from_point).setVisibility(viewType == FROM ? View.VISIBLE : View.INVISIBLE);
        itemView.findViewById(R.id.to_point).setVisibility(viewType == TO ? View.VISIBLE : View.INVISIBLE);
        itemView.findViewById(R.id.way_point).setVisibility(viewType == WAY ? View.VISIBLE : View.INVISIBLE);
        hintTrip = context.getString(R.string.hint_reach_for);
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
        ivClear.setVisibility(type == WAY || item != null ? View.VISIBLE : View.GONE);

        if (item != null) {
            tvAddress.setText(item.toString());
            Date date = new Date(item.getDatetime());
            long minutesTotal = item.getDatetime() / (1000 * 60);
            tvTime.setText(type == FROM ? formatFirst.format(date) : String.format("%s %d:%02d", hintTrip, minutesTotal / 60, minutesTotal % 60));
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

    public void setOnClearClickListener(@Nullable OnClearClickListener onClearClickListener) {
        this.onClearClickListener = onClearClickListener;
    }

    public interface OnClearClickListener {
        void onClick(TripPointHolder holder, int viewType);
    }
}
