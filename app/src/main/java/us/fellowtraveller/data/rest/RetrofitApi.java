package us.fellowtraveller.data.rest;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;

/**
 * Created by arkadii on 3/5/17.
 */

public interface RetrofitApi {

    @POST("signin")
    Observable<Response<AccountUser>> signIn(@Body AccountUser user);

    @POST("signup")
    Observable<Response<AccountUser>> signUp(@Body AccountUser user);

    @Multipart
    @POST("profile/photo")
    Observable<Photo> editPhoto(@Part MultipartBody.Part part);

    @PATCH("profile")
    Observable<AccountUser> editProfile(@Body User user);

    @POST("profile/cars")
    Observable<Car> addCar(@Body Car car);

    @Multipart
    @POST("profile/cars/{id}/photo")
    Observable<Photo> addCarPhoto(@Path("id") String id, @Part MultipartBody.Part part);
}
