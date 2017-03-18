package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/18/17.
 */

public class UserProfileViewHolder extends RecyclerView.ViewHolder {
    ImageView ivProfilePhoto;
    TextView tvRatingCount;
    TextView tvTripCount;
    TextView tvCommentCount;
    TextView tvEmail;
    TextView tvGender;
    TextView tvPhone;
    Button btnAddCar;


    public UserProfileViewHolder(LayoutInflater inflater, ViewGroup viewGroup) {
        super(inflater.inflate(R.layout.item_user_about, viewGroup, false));
        ivProfilePhoto = (ImageView) itemView.findViewById(R.id.iv_profile_photo);
        tvRatingCount = (TextView) itemView.findViewById(R.id.tv_rating_count);
        tvTripCount = (TextView) itemView.findViewById(R.id.tv_trip_count);
        tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
        tvGender = (TextView) itemView.findViewById(R.id.tv_gender);
        tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
        btnAddCar = (Button) itemView.findViewById(R.id.btn_add_car);
    }

    public void bind(User user, View.OnClickListener onClickListener) {
        tvPhone.setText(user.getSsoId());
        tvEmail.setText(user.getEmail());
        tvCommentCount.setText(String.format("%s", user.getCommentsCount()));
        tvTripCount.setText(String.format("%s", user.getTripCount()));
        if (user.getRating() == (int) user.getRating()) {
            tvRatingCount.setText(String.format("%s", (int) user.getRating()));
        } else {
            tvRatingCount.setText(String.format(Locale.ENGLISH, "%.1f", user.getRating()));
        }
        tvGender.setText(User.GENDER_FEMALE.equals(user.getGender())
                ? R.string.hint_female : R.string.hint_male);
        btnAddCar.setOnClickListener(onClickListener);
        btnAddCar.setVisibility(user.getCars().isEmpty() ? View.VISIBLE : View.GONE);
    }

    public void unbind() {
        btnAddCar.setOnClickListener(null);
    }
}
