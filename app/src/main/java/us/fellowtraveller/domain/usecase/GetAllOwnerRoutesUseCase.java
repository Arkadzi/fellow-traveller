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

public class GetAllOwnerRoutesUseCase extends UseCase<List<Route>> {
    private String ownerId;
    private final Repository repository;

    @Inject
    public GetAllOwnerRoutesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    protected Observable<List<Route>> getUseCaseObservable() {
        return repository.getAllOwnerRoutes(ownerId);
    }

}
