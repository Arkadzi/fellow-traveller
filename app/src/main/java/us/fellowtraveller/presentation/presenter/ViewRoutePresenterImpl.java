package us.fellowtraveller.presentation.presenter;

import java.util.List;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.GetPointSubscribersUseCase;
import us.fellowtraveller.domain.usecase.SubscribeUseCase;
import us.fellowtraveller.domain.usecase.UnsubscribeUseCase;
import us.fellowtraveller.domain.usecase.UserInfoUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.ViewRouteView;

/**
 * Created by arkadius on 4/22/17.
 */

public class ViewRoutePresenterImpl extends ProgressPresenter<ViewRouteView> implements ViewRoutePresenter {
    private final UserInfoUseCase userInfoUseCase;
    private final SubscribeUseCase subscribeUseCase;
    private final UnsubscribeUseCase unsubscribeUseCase;
    private GetPointSubscribersUseCase getPointSubscribersUseCase;

    public ViewRoutePresenterImpl(Messages messages,
                                  UserInfoUseCase userInfoUseCase,
                                  SubscribeUseCase subscribeUseCase,
                                  UnsubscribeUseCase unsubscribeUseCase,
                                  GetPointSubscribersUseCase getPointSubscribersUseCase) {
        super(messages);
        this.userInfoUseCase = userInfoUseCase;
        this.subscribeUseCase = subscribeUseCase;
        this.unsubscribeUseCase = unsubscribeUseCase;
        this.getPointSubscribersUseCase = getPointSubscribersUseCase;
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

    @Override
    public void onSubscribe(String pointId) {
        subscribeUseCase.setPointId(pointId);
        subscribeUseCase.execute(new BaseProgressSubscriber<String>(this) {
            @Override
            public void onNext(String response) {
                super.onNext(response);
                ViewRouteView view = getView();
                if (view != null) {
                    view.onSubscribed();
                }
            }
        });
    }

    @Override
    public void onUnsubscribe(String pointId) {
        unsubscribeUseCase.setPointId(pointId);
        unsubscribeUseCase.execute(new BaseProgressSubscriber<String>(this) {
            @Override
            public void onNext(String response) {
                super.onNext(response);
                ViewRouteView view = getView();
                if (view != null) {
                    view.onUnsubscribed();
                }
            }
        });
    }

    @Override
    public void onMarkerClick(List<String> subscribers) {
        getPointSubscribersUseCase.setSubscribers(subscribers);
        getPointSubscribersUseCase.execute(new BaseProgressSubscriber<List<User>>(this) {
            @Override
            public void onNext(List<User> response) {
                super.onNext(response);
                ViewRouteView view = getView();
                if (view != null) {
                    view.showSubscribers(response);
                }
            }
        });

    }
}
