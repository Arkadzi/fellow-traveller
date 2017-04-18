package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.presentation.utils.CircleTransform;

/**
 * Created by arkadii on 3/18/17.
 */
public class CarViewHolder2 extends RecyclerView.ViewHolder {

    private final TextView tvCarTitle;
    private final TextView tvSeats;
    private final RatingBar carRating;
    private final String seatsHint;

    public CarViewHolder2(LayoutInflater layoutInflater, ViewGroup parent) {
        super(layoutInflater.inflate(R.layout.item_car2, parent, false));
        tvCarTitle = (TextView) itemView.findViewById(R.id.tv_car_title);
        TextView tvStateHint = (TextView) itemView.findViewById(R.id.tv_state_hint);
        tvSeats = (TextView) itemView.findViewById(R.id.tv_seats);
        carRating = (RatingBar) itemView.findViewById(R.id.car_rating);
        carRating.setMax(Car.MAX_RATING);
        tvStateHint.setText(tvSeats.getContext().getString(R.string.hint_condition) + ":");
        seatsHint = tvSeats.getContext().getString(R.string.hint_capacity);
        carRating.setNumStars(Car.MAX_RATING);
    }

    public void bind(Car car) {
        tvCarTitle.setText(car.getTitle());
        carRating.setRating(car.getCondition());
        tvSeats.setText(String.format("%s: %s", seatsHint, car.getCapacity()));
    }
}
