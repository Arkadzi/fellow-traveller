package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import us.fellowtraveller.R;
import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadius on 6/18/17.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {
    ImageView ivAuthorPhoto;
    RatingBar ratingBar;
    TextView tvDate;
    TextView tvAuthor;
    TextView tvText;
    final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.getDefault());

    public CommentViewHolder(LayoutInflater inflater, ViewGroup viewGroup) {
        super(inflater.inflate(R.layout.item_comment, viewGroup, false));
        ivAuthorPhoto = (ImageView) itemView.findViewById(R.id.c_author_photo);
        ratingBar = (RatingBar) itemView.findViewById(R.id.rb_rating_value);
        tvAuthor = (TextView) itemView.findViewById(R.id.c_author_nick);
        tvDate = (TextView) itemView.findViewById(R.id.c_date);
        tvText = (TextView) itemView.findViewById(R.id.tv_text);

    }

    public void bind(Comment comment) {
        tvDate.setText(dateFormat.format(new Date(comment.getDatetime())));
        User sender = comment.getSender();
        tvAuthor.setText(sender.getFullName());
        tvText.setText(comment.getText());
        Picasso.with(ivAuthorPhoto.getContext())
                .load(sender.getImageUrl())
                .resize(100, 0)
                .into(ivAuthorPhoto);
        ratingBar.setRating(comment.getRating());
    }
}
