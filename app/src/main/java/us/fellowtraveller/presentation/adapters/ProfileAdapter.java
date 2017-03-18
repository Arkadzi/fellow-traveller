package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.activities.ProfileActivity;
import us.fellowtraveller.presentation.adapters.viewholders.UserProfileViewHolder;

/**
 * Created by arkadii on 3/18/17.
 */
public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int USER_PROFILE = 1;
    private static final int USER_CAR = 2;
    private final LayoutInflater layoutInflater;
    private final View.OnClickListener addCarButtonClickListener;
    @Nullable
    private User user;

    public ProfileAdapter(Context context, View.OnClickListener addCarButtonClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.addCarButtonClickListener = addCarButtonClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? USER_PROFILE : USER_CAR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case USER_CAR:
                return null;
            case USER_PROFILE:
                return new UserProfileViewHolder(layoutInflater, parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserProfileViewHolder) {
            ((UserProfileViewHolder) holder).bind(user, addCarButtonClickListener);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof UserProfileViewHolder) {
            ((UserProfileViewHolder) holder).unbind();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return (user != null ? user.getCars().size() + 1 : 0);
    }

    public void setUser(@Nullable User user) {
        this.user = user;
        notifyDataSetChanged();
    }
}
