package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class SignUpUseCase extends UseCase<User> {

    private final Repository repository;
    private User user;

    @Inject
    public SignUpUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected Observable<User> getUseCaseObservable() {
        return repository.signUp(user);
    }
}
