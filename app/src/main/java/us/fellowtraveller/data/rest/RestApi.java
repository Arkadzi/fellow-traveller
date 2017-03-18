package us.fellowtraveller.data.rest;

import java.util.concurrent.TimeUnit;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.exceptions.Exceptions;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public class RestApi {
    private RetrofitApi api;

    public RestApi(RetrofitApi api) {
        this.api = api;
    }

    public Observable<AccountUser> signIn(AccountUser user) {
        return api.signIn(user).map(userResponse -> {
            if (!userResponse.isSuccessful())
                throw Exceptions.propagate(new HttpException(userResponse));
            return userResponse;
        }).flatMap(userResult -> Observable.from(userResult.headers().values("Authorization")).first(),
                (userResult, token) -> {
                    AccountUser body = userResult.body();
                    body.setToken(token);
                    return body;
                });
    }


    public Observable<AccountUser> signUp(AccountUser user) {
        return api.signUp(user).map(userResponse -> {
            if (!userResponse.isSuccessful())
                throw Exceptions.propagate(new HttpException(userResponse));
            return userResponse;
        }).flatMap(userResult -> Observable.from(userResult.headers().values("Authorization")).first(),
                (userResult, token) -> {
                    AccountUser body = userResult.body();
                    body.setToken(token);
                    return body;
                });
    }

    public Observable<User> getUserInfo(String userId) {

        User user = new User();
        user.setLastName("Умник");
        user.setEmail("clever.boy@ukr.net");
        user.setFirstName("Малолетний");
        user.setSsoId("228");
        user.setId(userId);
        user.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/07/fa/03/07fa03be6676ca10e088449075d460f6.jpg");
        return Observable.just(user).delay(2, TimeUnit.SECONDS);
    }
}
