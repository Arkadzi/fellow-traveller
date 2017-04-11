package us.fellowtraveller.presentation.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadius on 4/9/17.
 */

public abstract class ItemTouchAdapter<D, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    private final List<D> items = new ArrayList<>();
    @Nullable
    private OnItemInteractListener onItemInteractListener;

    public void onItemDismiss(int position) {
        items.remove(position);

        notifyItemRemoved(position);
        if (onItemInteractListener != null) {
            onItemInteractListener.onItemInteract();
        }
    }

    public void setOnItemInteractListener(@Nullable OnItemInteractListener onItemInteractListener) {
        this.onItemInteractListener = onItemInteractListener;
    }

    public void onItemMove(int fromPosition, int toPosition) {
        D prev = items.remove(fromPosition);
        items.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
        if (onItemInteractListener != null) {
            onItemInteractListener.onItemInteract();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public D getItem(int index) {
        return getItems().get(index);
    }

    public List<D> getItems() {
        return items;
    }

    public interface OnItemInteractListener {
        void onItemInteract();
    }
}