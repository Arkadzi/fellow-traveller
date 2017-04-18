package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.data.di.scope.UserScope;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class AddRouteUseCase extends UseCase<Route> {
    private final Repository repository;
    private Route route;
    private String pictureFilePath;

    @Inject
    public AddRouteUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    protected Observable<Route> getUseCaseObservable() {
        return repository.addRoute(route);
    }
}
