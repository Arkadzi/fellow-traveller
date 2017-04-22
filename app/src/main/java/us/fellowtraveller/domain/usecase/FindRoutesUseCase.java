package us.fellowtraveller.domain.usecase;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadius on 4/10/17.
 */

public class FindRoutesUseCase extends UseCase<List<Route>> {
    private LatLng origin;
    private LatLng destination;
    private double radiusOrigin;
    private double radiusDestination;
    private long when;
    private final Repository repository;

    @Inject
    public FindRoutesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setData(LatLng origin,
            LatLng destination,
            double radiusOrigin,
            double radiusDestination,
            long when) {
        stopExecution();
        this.origin = origin;
        this.destination = destination;
        this.radiusOrigin = radiusOrigin;
        this.radiusDestination = radiusDestination;
        this.when = when;
    }

    @Override
    protected Observable<List<Route>> getUseCaseObservable() {
        return repository.findRoutes(origin, destination, radiusOrigin, radiusDestination, when);
    }

}
