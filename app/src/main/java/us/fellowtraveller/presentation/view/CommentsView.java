package us.fellowtraveller.presentation.view;

import java.util.List;

import us.fellowtraveller.domain.model.Comment;

/**
 * Created by arkadius on 6/17/17.
 */

public interface CommentsView extends ProgressView {
    void renderComments(List<Comment> comments);

    void hideDialogProgress();

    void showDialogProgress();

    void close();

    void addComment(Comment comment);
}
