package us.fellowtraveller.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import us.fellowtraveller.data.rest.RestApi;
import us.fellowtraveller.domain.BuildRouteException;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.utils.LocationUtils;
import us.fellowtraveller.presentation.utils.RouteUtils;

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
    public Observable<List<Point>> getRoute(TripPoint origin, TripPoint destination, List<TripPoint> wayPoints) {
        List<LatLng> latLngs = new ArrayList<>();
        for (TripPoint wayPoint : wayPoints) {
            latLngs.add(wayPoint.getLatLng());
        }
        return restApi.getRoute(origin.getLatLng(), destination.getLatLng(), latLngs)
                .map(routeResult -> {
                    if (!routeResult.routes.isEmpty()) {
                        List<LatLng> correctingPoints = LocationUtils.decodePolylines(
                                routeResult.routes.get(0).getPolylines()
                        );
                        return RouteUtils.from(wayPoints, correctingPoints, origin, destination);
                    } else {
                        throw new BuildRouteException();
                    }
                });
    }

    @Override
    public Observable<Route> addRoute(Route route) {
        return restApi.addRoute(route);
    }
}
