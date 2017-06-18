package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.presentation.view.CommentsView;

/**
 * Created by arkadius on 6/17/17.
 */

public interface CommentsPresenter extends Presenter<CommentsView> {
    void onStart(User user);

    void onSendButtonClick(Comment comment);
}
