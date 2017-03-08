package us.fellowtraveller.data;

import rx.Observable;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public class RepositoryImpl implements Repository {
    private RestApi restApi;
    private Account account;


    public RepositoryImpl(RestApi restApi, Account account) {
        this.restApi = restApi;
        this.account = account;
    }

    @Override
    public Observable<User> signIn(User user) {
        return restApi.signIn(user).doOnNext(user1 -> account.save(user1));
    }

    @Override
    public Observable<User> signUp(User user) {
        return restApi.signUp(user).doOnNext(user1 -> account.save(user1));
    }
}
