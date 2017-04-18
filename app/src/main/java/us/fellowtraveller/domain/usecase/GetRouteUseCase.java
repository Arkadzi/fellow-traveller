package us.fellowtraveller.domain.usecase;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadius on 4/10/17.
 */

@UserScope
public class GetRouteUseCase extends UseCase<List<Point>> {

    private final Repository repository;
    private TripPoint origin;
    private TripPoint destination;
    private List<TripPoint> items;

    @Inject
    public GetRouteUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setCoords(TripPoint origin, TripPoint destination, List<TripPoint> items) {
        stopExecution();
        this.items = items;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    protected Observable<List<Point>> getUseCaseObservable() {
        return repository.getRoute(origin, destination, items);
    }

}
