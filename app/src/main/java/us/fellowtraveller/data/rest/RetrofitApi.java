package us.fellowtraveller.data.rest;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.SimpleResponse;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.RouteResult;

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

    @DELETE("profile/cars/{id}")
    Observable<Response<Void>> deleteCar(@Path("id") String id);

    @GET("https://maps.googleapis.com/maps/api/directions/json")
    Observable<RouteResult> getRoute(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("waypoints") String waypoints,
            @Query("key") String key
    );

    @POST("map/routes")
    Observable<Route/*Response<Void>*/> addRoute(@Body Route route);

    @GET("map/routes")
    Observable<List<Route>> getAllOwnerRoutes(@Query("owner") String ownerId);

    @GET("map/subscribes")
    Observable<List<Route>> getAllSubscriberRoutes();

    @PUT("map/subscribes/{collectionDataId}")
    Observable<Response<Void>> subscribe(@Path("collectionDataId") String id);


    @DELETE("map/subscribes/{collectionDataId}")
    Observable<Response<Void>> unsubscribe(@Path("collectionDataId") String id);

    @GET("map/routes/search")
    Observable<List<Route>> findRoutes(
            @Query("latitude1") double latitude1, @Query("longitude1") double longitude1,
            @Query("latitude2") double latitude2, @Query("longitude2") double longitude2,
            @Query("radius1") double radiusOrigin, @Query("radius2") double radiusDestination,
            @Query("time") long when
    );

    @DELETE("map/routes/{id}")
    Observable<Response<Void>> deleteRoute(@Path("id") String id);


    @GET("users")
    Observable<User> getUserInfo(@Query("id") String userId);
}
