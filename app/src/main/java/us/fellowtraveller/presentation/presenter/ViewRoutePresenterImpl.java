package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.UserInfoUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.ViewRouteView;

/**
 * Created by arkadius on 4/22/17.
 */

public class ViewRoutePresenterImpl extends ProgressPresenter<ViewRouteView> implements ViewRoutePresenter {
    private final UserInfoUseCase userInfoUseCase;

    public ViewRoutePresenterImpl(Messages messages, UserInfoUseCase userInfoUseCase) {
        super(messages);
        this.userInfoUseCase = userInfoUseCase;
    }

    @Override
    public void onStart(String userId) {
        userInfoUseCase.setUserId(userId);
        userInfoUseCase.execute(new BaseProgressSubscriber<User>(this) {
            @Override
            public void onNext(User response) {
                super.onNext(response);
                ViewRouteView view = getView();
                if (view != null) {
                    view.renderUser(response);
                }
            }
        });
    }
}
