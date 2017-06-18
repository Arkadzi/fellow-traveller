package us.fellowtraveller.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.adapters.viewholders.AddCarHolder;
import us.fellowtraveller.presentation.adapters.viewholders.CarViewHolder;
import us.fellowtraveller.presentation.adapters.viewholders.UserProfileViewHolder;
import us.fellowtraveller.presentation.utils.ScreenNavigator;

/**
 * Created by arkadii on 3/18/17.
 */
public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int USER_PROFILE = 1;
    private static final int USER_CAR = 2;
    private static final int ADD_CAR = 3;
    private final LayoutInflater layoutInflater;
    private Activity activity;
    private final boolean canModifyCars;
    private final View.OnClickListener addCarButtonClickListener;
    @Nullable
    private OnCarClickListener onCarClickListener;
    @Nullable
    private User user;

    public ProfileAdapter(Activity activity, boolean canModifyCars, View.OnClickListener addCarButtonClickListener) {
        layoutInflater = activity.getLayoutInflater();
        this.activity = activity;
        this.canModifyCars = canModifyCars;
        this.addCarButtonClickListener = addCarButtonClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? USER_PROFILE
                : (canModifyCars || !hasCars()) && position == getItemCount() - 1 ? ADD_CAR
                : USER_CAR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case USER_CAR:
                CarViewHolder carViewHolder = new CarViewHolder(layoutInflater, parent);
                if (canModifyCars) {
                    carViewHolder.itemView.setOnLongClickListener(view -> {
                        int adapterPosition = carViewHolder.getAdapterPosition();
                        if (user != null) {
                            Car car = user.getCars().get(adapterPosition - 1);
                            if (onCarClickListener != null) {
                                onCarClickListener.onCarClick(car);
                            }
                        }
                        return false;
                    });
                }
                return carViewHolder;
            case USER_PROFILE:
                UserProfileViewHolder viewHolder = new UserProfileViewHolder(layoutInflater, parent);
                return viewHolder;
            case ADD_CAR:
                return new AddCarHolder(layoutInflater, parent);
        }
        return null;
    }

    public void setOnCarClickListener(@Nullable OnCarClickListener onCarClickListener) {
        this.onCarClickListener = onCarClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserProfileViewHolder) {
            UserProfileViewHolder viewHolder = (UserProfileViewHolder) holder;
            viewHolder.bind(user);
            viewHolder.feedbackView.setOnClickListener(v -> ScreenNavigator.startCommentsScreen(activity, user));
        } else if (holder instanceof CarViewHolder) {
            if (user != null) {
                ((CarViewHolder) holder).bind(user.getCars().get(position - 1));
            }

        } else if (holder instanceof AddCarHolder) {
            ((AddCarHolder) holder).bind(addCarButtonClickListener, hasCars(), canModifyCars);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof AddCarHolder) {
            ((AddCarHolder) holder).unbind();
        } else if (holder instanceof UserProfileViewHolder) {
            ((UserProfileViewHolder) holder).feedbackView.setOnClickListener(null);
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return (user != null ? user.getCars().size() + 1 + (canModifyCars || !hasCars() ? 1 : 0) : 0);
    }

    private boolean hasCars() {
        return user != null && !user.getCars().isEmpty();
    }

    public void setUser(@Nullable User user) {
        this.user = user;
        notifyDataSetChanged();
    }

    public interface OnCarClickListener {
        void onCarClick(Car car);
    }
}
