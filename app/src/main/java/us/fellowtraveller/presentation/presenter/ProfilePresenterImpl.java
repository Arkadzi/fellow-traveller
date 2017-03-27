package us.fellowtraveller.presentation.presenter;

import android.support.annotation.NonNull;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.DeleteCarUseCase;
import us.fellowtraveller.domain.usecase.UserInfoUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.ProfileView;

/**
 * Created by arkadii on 3/18/17.
 */
public class ProfilePresenterImpl extends ProgressPresenter<ProfileView> implements ProfilePresenter {
    private final UserInfoUseCase useCase;
    private final DeleteCarUseCase deleteCarUseCase;

    public ProfilePresenterImpl(Messages messages, UserInfoUseCase useCase, DeleteCarUseCase deleteCarUseCase) {
        super(messages);
        this.useCase = useCase;
        this.deleteCarUseCase = deleteCarUseCase;
    }

    @Override
    public void onUserRequested(String userId) {
        if (!useCase.isWorking()) {
            useCase.setUserId(userId);
            useCase.execute(getSubscriber());
        }
    }

    @Override
    public void onCarDelete(Car car) {
        deleteCarUseCase.setCar(car);
        deleteCarUseCase.execute(new BaseProgressSubscriber<Car>(this) {
            @Override
            public void onNext(Car response) {
                super.onNext(response);
                ProfileView view = getView();
                if (view != null) {
                    view.onCarDeleted(response);
                }
            }
        });
    }

    @NonNull
    private BaseProgressSubscriber<User> getSubscriber() {
        return new BaseProgressSubscriber<User>(this) {
            @Override
            public void onNext(User response) {
                super.onNext(response);
                ProfileView view = getView();
                if (view != null) {
                    view.renderUser(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ProfileView view = getView();
                if (view != null) {
                    view.renderError();
                }
            }
        };
    }
}
