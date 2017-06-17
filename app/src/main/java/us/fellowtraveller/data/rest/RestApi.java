package us.fellowtraveller.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;
import us.fellowtraveller.R;
import us.fellowtraveller.domain.DeleteCarException;
import us.fellowtraveller.domain.DeleteRouteException;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.presentation.utils.FilesUtils;

/**
 * Created by arkadii on 3/5/17.
 */

public class RestApi {
    public static final String BEARER = "Bearer ";
    private RetrofitApi api;
    private Context context;
    private Account account;

    public RestApi(RetrofitApi api, Context context, Account account) {
        this.api = api;
        this.context = context;
        this.account = account;
    }

    public Observable<AccountUser> signIn(AccountUser user) {
        return api.signIn(user).map(userResponse -> {
            if (!userResponse.isSuccessful())
                throw Exceptions.propagate(new HttpException(userResponse));
            return userResponse;
        }).flatMap(userResult -> Observable.from(userResult.headers().values("Authorization")).first()
                        .map(token -> token.substring(BEARER.length())),
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
        }).flatMap(userResult -> Observable.from(userResult.headers().values("Authorization")).first()
                        .map(token -> token.substring(BEARER.length())),
                (userResult, token) -> {
                    AccountUser body = userResult.body();
                    body.setToken(token);
                    return body;
                });
    }

    public Observable<User> getUserInfo(String userId) {
        return api.getUserInfo(userId);
    }

    public Observable<Photo> editPhoto(String filePath) {
        return Observable.create(createMultipart(filePath)).flatMap(part -> api.editPhoto(part));
    }

    public Observable<AccountUser> editProfile(User user) {
        return api.editProfile(user);
    }

    public Observable<Car> addCar(Car car, String filePath) {
        if (filePath != null) {
            return api.addCar(car).flatMap(new Func1<Car, Observable<Photo>>() {
                @Override
                public Observable<Photo> call(Car car) {
                    return addCarPhoto(car, filePath);
                }
            }, (newCar, photo) -> {
                newCar.setImageUrl(photo.getUrl());
                return newCar;
            });
        } else {
            return api.addCar(car);
        }
    }

    public Observable<Photo> addCarPhoto(Car car, String filePath) {
        return Observable.create(createMultipart(filePath)).flatMap(part -> api.addCarPhoto(car.getId(), part));
    }

    @NonNull
    private Observable.OnSubscribe<MultipartBody.Part> createMultipart(final String filePath) {
        return subscriber -> {
            File tmpFile;
            try {
                double max = Math.min(500, 0.8 * Math.sqrt(Runtime.getRuntime().freeMemory() / 4));
                tmpFile = FilesUtils.saveTempFile(context, filePath, (int) max);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), tmpFile);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", tmpFile.getName(), requestFile);
                subscriber.onNext(part);
                FilesUtils.deleteFile(tmpFile);
                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(new Throwable("file loading error"));
            }
        };
    }

    public Observable<Car> deleteCar(Car car) {
        return api.deleteCar(car.getId()).map(response -> {
            if (response.isSuccessful()) {
                return car;
            }
            throw new DeleteCarException();
        });
    }

    public Observable<RouteResult> getRoute(LatLng origin, LatLng destination, List<LatLng> items) {
        List<String> waypoints = new ArrayList<>();
        for (LatLng latLng : items) {
            waypoints.add(String.format("via:%s,%s", latLng.latitude, latLng.longitude));
        }
        return api.getRoute(
                String.format("%s,%s", origin.latitude, origin.longitude),
                String.format("%s,%s", destination.latitude, destination.longitude),
                waypoints.isEmpty() ? null : TextUtils.join("|", waypoints),
                context.getString(R.string.google_maps_key)
        );
    }

    public Observable<Route> addRoute(Route route) {
        return api.addRoute(route)/*.map(userResponse -> {
            if (!userResponse.isSuccessful())
                throw Exceptions.propagate(new HttpException(userResponse));
            return route;
        })*/;
    }

    public Observable<List<Route>> getAllOwnerRoutes(String ownerId) {
        return api.getAllOwnerRoutes(ownerId);
    }

    public Observable<List<Route>> getAllSubscriberRoutes() {
        return api.getAllSubscriberRoutes();
    }

    public Observable<List<Route>> findRoutes(LatLng origin, LatLng destination, double radiusOrigin, double radiusDestination, long when) {
        return api.findRoutes(origin.latitude, origin.longitude, destination.latitude, destination.longitude, radiusOrigin / 111, radiusDestination / 111, when)
                .flatMapIterable(l -> l)
                .filter(r -> !account.user().getId().equals(r.getOwner()))
                .toList();
    }

    public Observable<Route> deleteRoute(Route route) {
        return api.deleteRoute(route.getId()).map(response -> {
            if (response.isSuccessful()) {
                return route;
            }
            throw new DeleteRouteException();
        });
    }

    public Observable<String> subscribe(String pointId) {
        return api.subscribe(pointId).map(voidResponse ->  {
//            if (voidResponse.isSuccessful()) {
                return pointId;
//            }
//            throw new SubscribeException();
        });
    }


    public Observable<String> unsubscribe(String pointId) {
        return api.unsubscribe(pointId).map(voidResponse -> pointId);
    }
}
