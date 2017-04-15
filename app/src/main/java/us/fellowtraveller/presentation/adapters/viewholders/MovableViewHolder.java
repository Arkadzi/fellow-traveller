package us.fellowtraveller.presentation.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by arkadius on 4/15/17.
 */

public abstract class MovableViewHolder extends RecyclerView.ViewHolder {
    public MovableViewHolder(View itemView) {
        super(itemView);
    }

    public abstract boolean isMovable();
}
