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

public class DeleteCarUseCase extends UseCase<Car> {

    private final Repository repository;
    private Car car;

    @Inject
    public DeleteCarUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    protected Observable<Car> getUseCaseObservable() {
        return repository.deleteCar(car);
    }
}
