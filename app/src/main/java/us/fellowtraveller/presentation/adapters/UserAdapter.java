package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.adapters.viewholders.UserViewHolder;

/**
 * Created by arkadius on 4/24/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final LayoutInflater inflater;
    private Context context;
    private final List<User> users = new ArrayList<>();
    @Nullable
    private OnClickListener listener;

    public UserAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserViewHolder userViewHolder = new UserViewHolder(inflater, parent);
        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(users.get(userViewHolder.getAdapterPosition()));
                }
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setListener(@Nullable OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick(User user);
    }
}
