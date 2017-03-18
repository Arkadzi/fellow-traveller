package us.fellowtraveller.presentation.presenter;

import android.support.annotation.NonNull;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.UserInfoUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.ProfileView;

/**
 * Created by arkadii on 3/18/17.
 */
public class ProfilePresenterImpl extends ProgressPresenter<ProfileView> implements ProfilePresenter {
    private final UserInfoUseCase useCase;

    public ProfilePresenterImpl(Messages messages, UserInfoUseCase useCase) {
        super(messages);
        this.useCase = useCase;
    }

    @Override
    public void onUserRequested(String userId) {
        if (!useCase.isWorking()) {
            useCase.setUserId(userId);
            useCase.execute(getSubscriber());
        }
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
