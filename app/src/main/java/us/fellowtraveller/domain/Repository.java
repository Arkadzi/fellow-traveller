package us.fellowtraveller.domain;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import rx.Observable;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.domain.model.trip.TripPoint;

/**
 * Created by arkadii on 3/5/17.
 */

public interface Repository {

    Observable<AccountUser> signIn(AccountUser user);

    Observable<AccountUser> signUp(AccountUser user);

    Observable<User> getUserInfo(String userId);

    Observable<AccountUser> editProfile(User user);

    Observable<Photo> editPhoto(String filePath);

    Observable<Car> addCar(Car car, String pictureFilePath);

    Observable<Car> deleteCar(Car car);

    Observable<List<Point>> getRoute(TripPoint origin, TripPoint destination, List<TripPoint> items);

    Observable<Route> addRoute(Route route);

    Observable<List<Route>> getAllOwnerRoutes(String ownerId);

    Observable<List<Route>> getAllSubscriberRoutes();

    Observable<List<Route>> findRoutes(LatLng origin, LatLng destination, double radiusOrigin, double radiusDestination, long when);

    Observable<Route> deleteRoute(Route route);

    Observable<String> subscribe(String pointId);

    Observable<String> unsubscribe(String pointId);
}
