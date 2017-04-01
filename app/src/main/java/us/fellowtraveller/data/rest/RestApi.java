package us.fellowtraveller.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
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

//        User user = new User();
//        user.setLastName("Statham");
//        user.setEmail("jason.statham@gmail.com");
//        user.setFirstName("Jason");
//        user.setSsoId("228");
//        user.setId(userId);
//        user.setRating(4.5f);
//        user.setCommentsCount(250);
//        user.setTripCount(111);
//        Calendar instance = Calendar.getInstance();
//        instance.set(1967, 6, 26, 0, 0, 0);
//        user.setBirthday(instance.getTime().getTime());
//        user.setImageUrl("http://i.telegraph.co.uk/multimedia/archive/01691/jason-statham-460_1691091a.jpg");
//        ArrayList<Car> cars = new ArrayList<>();
//        cars.add(new Car("1", "Nissan Skyline", 3, 2000, 4, "https://s-media-cache-ak0.pinimg.com/originals/a5/40/c3/a540c37dab57c8a77b2caaf323493684.jpg"));
//        cars.add(new Car("2", "Четкая жига", 3, 1976, 2, "http://censoru.net/uploads/posts/2015-03/1426786529_000_14.jpg"));
//        user.setCars(cars);
//        return Observable.just(user)/*.delay(2, TimeUnit.SECONDS)*/;
        return api.signIn(account.user()).map(Response::body);
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
        return api.deleteCar(car.getId()).map(response -> car);
    }
}
