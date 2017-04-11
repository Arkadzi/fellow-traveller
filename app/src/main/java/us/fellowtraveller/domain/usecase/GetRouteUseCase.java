package us.fellowtraveller.domain.usecase;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.RouteResult;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadius on 4/10/17.
 */

public class GetRouteUseCase extends UseCase<RouteResult> {

    private final Repository repository;
    private LatLng origin;
    private LatLng destination;
    private List<Place> items;

    @Inject
    public GetRouteUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setCoords(LatLng origin, LatLng destination, List<Place> items) {
        this.items = items;
        stopExecution();
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    protected Observable<RouteResult> getUseCaseObservable() {
        return repository.getRoute(origin, destination, items);
    }

}
