package us.fellowtraveller.presentation.adapters.view_handlers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import us.fellowtraveller.presentation.adapters.ItemTouchAdapter;
import us.fellowtraveller.presentation.adapters.viewholders.MovableViewHolder;

/**
 * Created by arkadius on 4/9/17.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (isMoveable(viewHolder)) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        return 0;
    }

    private boolean isMoveable(RecyclerView.ViewHolder viewHolder) {
        return !(viewHolder instanceof MovableViewHolder) || ((MovableViewHolder) viewHolder).isMovable();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        if (isMoveable(target)) {
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
