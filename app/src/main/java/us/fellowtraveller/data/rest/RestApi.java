package us.fellowtraveller.data.rest;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
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
        return api.signIn(user).flatMap(userResult -> {
            Log.e("response", String.valueOf(userResult));
            return Observable.from(userResult.headers().values("Authorization"))
                    .first();
        }, (userResult, token) -> {
            User body = userResult.body();
            body.setToken(token);
            return body;
            /*User user1 = new User();
            user1.setToken(token);
            return user1;*/
        }/*(userResult, token) -> {
//            User body = userResult.response().body();
//            body.setToken(token);
            return new User();
        }*/);
    }


    public Observable<User> signUp(User user) {
        return api.signUp(user).flatMap(userResult -> {
            Log.e("response", String.valueOf(userResult.body()));
            return Observable.from(userResult.headers().values("Authorization"))
                    .first();
        },
        (userResult, token) -> {
            User body = userResult.body();
            body.setToken(token);
            return body;
        });
    }
}
