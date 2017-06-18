package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class PostCommentUseCase extends UseCase<Comment> {
    private final Repository repository;
    private Comment comment;

    @Inject
    public PostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    protected Observable<Comment> getUseCaseObservable() {
        return repository.postComment(comment);
    }
}
