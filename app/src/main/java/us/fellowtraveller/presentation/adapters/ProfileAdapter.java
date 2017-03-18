package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.adapters.viewholders.AddCarHolder;
import us.fellowtraveller.presentation.adapters.viewholders.CarViewHolder;
import us.fellowtraveller.presentation.adapters.viewholders.UserProfileViewHolder;

/**
 * Created by arkadii on 3/18/17.
 */
public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int USER_PROFILE = 1;
    private static final int USER_CAR = 2;
    private static final int ADD_CAR = 3;
    private final LayoutInflater layoutInflater;
    private final boolean canAddCar;
    private final View.OnClickListener addCarButtonClickListener;
    @Nullable
    private User user;

    public ProfileAdapter(Context context, boolean canAddCar, View.OnClickListener addCarButtonClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.canAddCar = canAddCar;
        this.addCarButtonClickListener = addCarButtonClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? USER_PROFILE
                : (canAddCar || !hasCars()) && position == getItemCount() - 1 ? ADD_CAR
                : USER_CAR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case USER_CAR:
                return new CarViewHolder(layoutInflater, parent);
            case USER_PROFILE:
                return new UserProfileViewHolder(layoutInflater, parent);
            case ADD_CAR:
                return new AddCarHolder(layoutInflater, parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserProfileViewHolder) {
            ((UserProfileViewHolder) holder).bind(user);
        } else if (holder instanceof CarViewHolder) {
            ((CarViewHolder) holder).bind(user.getCars().get(position - 1));
        } else if (holder instanceof AddCarHolder) {
            ((AddCarHolder) holder).bind(addCarButtonClickListener, hasCars(), canAddCar);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof AddCarHolder) {
            ((AddCarHolder) holder).unbind();
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return (user != null ? user.getCars().size() + 1 + (canAddCar || !hasCars() ? 1 : 0) : 0);
    }

    private boolean hasCars() {
        return user != null && !user.getCars().isEmpty();
    }

    public void setUser(@Nullable User user) {
        this.user = user;
        notifyDataSetChanged();
    }
}
