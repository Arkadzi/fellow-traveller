package us.fellowtraveller.domain.usecase;

import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Comment;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadius on 4/10/17.
 */

public class GetCommentsUseCase extends UseCase<List<Comment>> {
    private final Repository repository;
    private String recipientId;

    @Inject
    public GetCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setData(String recipientId) {
        this.recipientId = recipientId;
    }

    @Override
    protected Observable<List<Comment>> getUseCaseObservable() {
        return repository.getComments(recipientId);
    }

}
