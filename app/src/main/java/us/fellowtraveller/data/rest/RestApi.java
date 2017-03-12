package us.fellowtraveller.data.rest;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.exceptions.Exceptions;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public class RestApi {
    private RetrofitApi api;

    public RestApi(RetrofitApi api) {
        this.api = api;
    }

    public Observable<User> signIn(User user) {
        return api.signIn(user).map(userResponse -> {
            if (!userResponse.isSuccessful())
                throw Exceptions.propagate(new HttpException(userResponse));
            return userResponse;
        }).flatMap(userResult -> Observable.from(userResult.headers().values("Authorization")).first(),
                (userResult, token) -> {
                    User body = userResult.body();
                    body.setToken(token);
                    return body;
                });
    }


    public Observable<User> signUp(User user) {
        return api.signUp(user).map(userResponse -> {
            if (!userResponse.isSuccessful())
                throw Exceptions.propagate(new HttpException(userResponse));
            return userResponse;
        }).flatMap(userResult -> Observable.from(userResult.headers().values("Authorization")).first(),
                (userResult, token) -> {
                    User body = userResult.body();
                    body.setToken(token);
                    return body;
                });
    }
}
