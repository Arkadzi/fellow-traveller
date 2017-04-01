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
public class CarViewHolder extends RecyclerView.ViewHolder {

    private final ImageView ivImageView;
    private final TextView tvCarTitle;
    private final TextView tvYear;
    private final TextView tvStateHint;
    private final RatingBar carRating;
    private final String yearManufactireHint;

    public CarViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        super(layoutInflater.inflate(R.layout.item_car, parent, false));
        ivImageView = (ImageView) itemView.findViewById(R.id.iv_car_photo);
        tvYear = (TextView) itemView.findViewById(R.id.tv_year);
        yearManufactireHint = tvYear.getContext().getString(R.string.hint_year_of_manufacture);
        tvCarTitle = (TextView) itemView.findViewById(R.id.tv_car_title);
        tvStateHint = (TextView) itemView.findViewById(R.id.tv_state_hint);
        carRating = (RatingBar) itemView.findViewById(R.id.car_rating);
        carRating.setMax(Car.MAX_RATING);
        tvStateHint.setText(tvYear.getContext().getString(R.string.hint_condition) + ":");
        carRating.setNumStars(Car.MAX_RATING);
    }

    public void bind(Car car) {
        Picasso.with(ivImageView.getContext())
                .load(car.getImageUrl())
                .transform(new CircleTransform())
                .into(ivImageView);
        tvCarTitle.setText(car.getTitle());
        tvYear.setText(String.format("%s: %s",yearManufactireHint, car.getYear()));
        carRating.setRating(car.getCondition());
    }
}
