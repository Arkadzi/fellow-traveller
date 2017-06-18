package us.fellowtraveller.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.fellowtraveller.R;
import us.fellowtraveller.app.Application;
import us.fellowtraveller.data.Constants;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.adapters.CommentsAdapter;
import us.fellowtraveller.presentation.dialogs.CommentDialogFragment;
import us.fellowtraveller.presentation.dialogs.ProgressDialogFragment;
import us.fellowtraveller.presentation.presenter.CommentsPresenter;
import us.fellowtraveller.presentation.view.CommentsView;

/**
 * Created by arkadius on 6/17/17.
 */

public class CommentsActivity extends BaseActivity implements CommentsView, CommentDialogFragment.CommentCreatedListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.tv_empty)
    View emptyView;
    @Bind(R.id.fab_create_comment)
    FloatingActionButton fabCreateComment;
    @Inject
    CommentsPresenter presenter;
    @Inject
    Account account;
    private CommentsAdapter adapter;
    private User user;

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        user = (User) getIntent().getSerializableExtra(Constants.Intents.EXTRA_USER);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(user.getFullName());
        }
        ButterKnife.bind(this);
        Application.getApp(this).getUserComponent().inject(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentsAdapter(this);
        recyclerView.setAdapter(adapter);
        presenter.onCreate(this);
        presenter.onStart(user);
        fabCreateComment.setVisibility(account.user().getId().equals(user.getId()) ? View.GONE : View.VISIBLE);
        fabCreateComment.setOnClickListener(v -> CommentDialogFragment.newInstance()
                .show(getSupportFragmentManager(), CommentDialogFragment.TAG));
    }

    @Override
    protected void onDestroy() {
        presenter.onRelease();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void renderComments(List<Comment> comments) {
        adapter.setData(comments);
        if (comments.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideDialogProgress() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(ProgressDialogFragment.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void showDialogProgress() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) == null) {
            fragmentManager.beginTransaction()
                    .add(ProgressDialogFragment.newInstance(), ProgressDialogFragment.TAG)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void addComment(Comment comment) {
        adapter.addData(comment);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onCommentCreated(String text, boolean isDriver, int rating) {
        Comment comment = new Comment("title",
                System.currentTimeMillis(),
                isDriver, text, rating,
                account.user(), user);
        presenter.onSendButtonClick(comment);
    }
}
