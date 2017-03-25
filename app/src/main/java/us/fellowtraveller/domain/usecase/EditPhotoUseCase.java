package us.fellowtraveller.domain.usecase;

import javax.inject.Inject;

import rx.Observable;
import us.fellowtraveller.domain.Repository;
import us.fellowtraveller.domain.model.Photo;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.schedulers.ObserveOn;
import us.fellowtraveller.domain.schedulers.SubscribeOn;

/**
 * Created by arkadii on 3/6/17.
 */

public class EditPhotoUseCase extends UseCase<Photo> {

    private final Repository repository;
    private String filePath;

    @Inject
    public EditPhotoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, Repository repository) {
        super(subscribeOn, observeOn);
        this.repository = repository;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Observable<Photo> getUseCaseObservable() {
        return repository.editPhoto(filePath);
    }
}
