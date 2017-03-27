package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.AccountUser;
import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class AddCarUseCase extends UseCase<Car> {

    private final Repository repository;
    private Car car;
    private String pictureFilePath;

    @Inject
    public AddCarUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setData(Car car, String pictureFilePath) {
        this.car = car;
        this.pictureFilePath = pictureFilePath;
    }

    @Override
    protected Observable<Car> getUseCaseObservable() {
        return repository.addCar(car, pictureFilePath);
    }
}
