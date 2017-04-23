package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class UnsubscribeUseCase extends UseCase<String> {

    private final Repository repository;
    private String pointId;

    @Inject
    public UnsubscribeUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setPointId(String pointId) {
        stopExecution();
        this.pointId = pointId;
    }

    @Override
    protected Observable<String> getUseCaseObservable() {
        return repository.unsubscribe(pointId);
    }
}
