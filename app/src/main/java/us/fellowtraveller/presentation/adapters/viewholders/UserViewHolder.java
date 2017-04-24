package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.utils.CircleTransform;

/**
 * Created by arkadius on 4/24/17.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    private final int imageSize;
    @Bind(R.id.iv_user_photo)
    ImageView ivUserPhoto;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;

    public UserViewHolder(LayoutInflater inflater, ViewGroup viewGroup) {
        super(inflater.inflate(R.layout.item_user, viewGroup, false));
        ButterKnife.bind(this, itemView);
        imageSize = (int) itemView.getResources().getDimension(R.dimen.car_image_size);
    }

    public void bind(User user) {
        tvUserName.setText(user.getFullName());
        Picasso.with(ivUserPhoto.getContext())
                .load(user.getImageUrl())
                .resize(imageSize, 0)
                .transform(new CircleTransform())
                .into(ivUserPhoto);
    }
}
