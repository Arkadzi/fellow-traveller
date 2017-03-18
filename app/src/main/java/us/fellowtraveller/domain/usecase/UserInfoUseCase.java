package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class UserInfoUseCase extends UseCase<User> {

    private final Repository repository;
    private String userId;

    @Inject
    public UserInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    protected Observable<User> getUseCaseObservable() {
        return repository.getUserInfo(userId);
    }
}
