package us.fellowtraveller.data;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import rx.Observable;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.RouteResult;

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

    @Override
    public Observable<Car> addCar(Car car, String pictureFilePath) {
        return restApi.addCar(car, pictureFilePath);
    }

    @Override
    public Observable<Car> deleteCar(Car car) {
        return restApi.deleteCar(car);
    }

    @Override
    public Observable<RouteResult> getRoute(LatLng origin, LatLng destination, List<Place> items) {
        return restApi.getRoute(origin, destination, items);
    }
}
