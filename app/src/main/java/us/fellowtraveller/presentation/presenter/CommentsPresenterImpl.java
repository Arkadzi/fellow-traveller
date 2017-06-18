package us.fellowtraveller.presentation.presenter;

import java.util.List;

import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.GetCommentsUseCase;
import us.fellowtraveller.domain.usecase.PostCommentUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.CommentsView;

/**
 * Created by arkadius on 6/17/17.
 */

public class CommentsPresenterImpl extends ProgressPresenter<CommentsView> implements CommentsPresenter {
    private GetCommentsUseCase getCommentsUseCase;
    private PostCommentUseCase postCommentUseCase;
    private BaseProgressSubscriber.ProgressSubscriberListener dialogProgressListener = new BaseProgressSubscriber.ProgressSubscriberListener() {
        @Override
        public void onError(Throwable t) {
            CommentsView view = getView();
            if (view != null) {
                view.hideDialogProgress();
                showMessage(getMessages().getError(t));
                view.close();
            }
        }

        @Override
        public void onCompleted() {
            CommentsView view = getView();
            if (view != null) {
                view.hideDialogProgress();
            }
        }

        @Override
        public void onStartLoading() {
            CommentsView view = getView();
            if (view != null) {
                view.showDialogProgress();
            }
        }
    };

    public CommentsPresenterImpl(Messages messages, GetCommentsUseCase getCommentsUseCase, PostCommentUseCase postCommentUseCase) {
        super(messages);
        this.getCommentsUseCase = getCommentsUseCase;
        this.postCommentUseCase = postCommentUseCase;
    }

    @Override
    public void onCreate(CommentsView view) {
        super.onCreate(view);
        if (getCommentsUseCase.isWorking()) {
            getCommentsUseCase.execute(getCommentsSubscriber());
        }
        if (postCommentUseCase.isWorking()) {
            postCommentUseCase.execute(getPostCommentSubscriber());
        }
    }

    @Override
    public void onStart(User user) {
        getCommentsUseCase.setData(user.getId());
        getCommentsUseCase.execute(getCommentsSubscriber());
    }

    @Override
    public void onRelease() {
        getCommentsUseCase.unsubscribe();
        postCommentUseCase.unsubscribe();
        super.onRelease();
    }

    @Override
    public void onSendButtonClick(Comment comment) {
        postCommentUseCase.setComment(comment);
        postCommentUseCase.execute(getPostCommentSubscriber());
    }

    private BaseProgressSubscriber<List<Comment>> getCommentsSubscriber() {
        return new BaseProgressSubscriber<List<Comment>>(this) {
            @Override
            public void onNext(List<Comment> response) {
                super.onNext(response);
                CommentsView view = getView();
                if (view != null) {
                    view.renderComments(response);
                }
            }
        };
    }

    private BaseProgressSubscriber<Comment> getPostCommentSubscriber() {
        return new BaseProgressSubscriber<Comment>(dialogProgressListener) {
            @Override
            public void onNext(Comment response) {
                super.onNext(response);
                CommentsView view = getView();
                if (view != null) {
                    view.addComment(response);
                }
            }
        };
    }
}
