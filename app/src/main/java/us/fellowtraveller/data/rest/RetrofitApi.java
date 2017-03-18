package us.fellowtraveller.data.rest;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import us.fellowtraveller.domain.model.AccountUser;

/**
 * Created by arkadii on 3/5/17.
 */

public interface RetrofitApi {

    @POST("signin")
    Observable<Response<AccountUser>> signIn(@Body AccountUser user);

    @POST("signup")
    Observable<Response<AccountUser>> signUp(@Body AccountUser user);
}
