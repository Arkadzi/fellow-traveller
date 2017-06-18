package us.fellowtraveller.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.presentation.adapters.viewholders.CommentViewHolder;

/**
 * Created by arkadius on 6/18/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private LayoutInflater inflater;
    private final List<Comment> data = new ArrayList<>();

    public CommentsAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<Comment> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(Comment comment) {
        this.data.add(comment);
        notifyItemInserted(this.data.size() - 1);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
