package us.fellowtraveller.domain.usecase;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadius on 4/10/17.
 */

public class GetPointSubscribersUseCase extends UseCase<List<User>> {
    private List<String> subscribers;
    private final Repository repository;

    @Inject
    public GetPointSubscribersUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    protected Observable<List<User>> getUseCaseObservable() {
        return repository.getSubscribers(subscribers);
    }

}
