package us.fellowtraveller.data.rest;

import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public interface RetrofitApi {

    @POST("signin")
    Observable<Response<Void>> signIn(@Body User user);
    @POST("signup")
    Observable<Result<User>> signUp(@Body User user);
}
