package us.fellowtraveller.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadius on 4/10/17.
 */

public class GetAllSubscriberRoutesUseCase extends UseCase<List<Route>> {
    private String subscriberId;
    private final Repository repository;

    @Inject
    public GetAllSubscriberRoutesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    protected Observable<List<Route>> getUseCaseObservable() {
        return repository.getAllSubscriberRoutes(subscriberId);
    }

}
