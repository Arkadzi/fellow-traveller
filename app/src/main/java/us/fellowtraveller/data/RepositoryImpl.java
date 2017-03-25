package us.fellowtraveller.data;

import rx.Observable;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Photo;
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
    public Observable<AccountUser> signIn(AccountUser user) {
        return restApi.signIn(user).doOnNext(user1 -> account.save(user1));
    }

    @Override
    public Observable<AccountUser> signUp(AccountUser user) {
        return restApi.signUp(user).doOnNext(user1 -> account.save(user1));
    }

    @Override
    public Observable<User> getUserInfo(String userId) {
        return restApi.getUserInfo(userId);
    }

    @Override
    public Observable<AccountUser> editProfile(User user) {
        return restApi.editProfile(user).map(accountUser -> {
            account.editProfile(accountUser);
            return accountUser;
        });
    }

    @Override
    public Observable<Photo> editPhoto(String filePath) {
        return restApi.editPhoto(filePath).map(photo -> {
            account.savePhoto(photo.getUrl());
            return photo;
        });
    }
}
