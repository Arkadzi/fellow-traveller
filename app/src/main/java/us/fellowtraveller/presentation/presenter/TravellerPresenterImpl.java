package us.fellowtraveller.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.GetAllOwnerRoutesUseCase;
import us.fellowtraveller.domain.usecase.GetAllSubscriberRoutesUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.TravellerRouteView;

/**
 * Created by arkadius on 4/19/17.
 */

public class TravellerPresenterImpl extends ProgressPresenter<TravellerRouteView> implements TravellerPresenter {

    private final GetAllSubscriberRoutesUseCase getAllRoutesUseCase;
    private final Account account;

    public TravellerPresenterImpl(Messages messages, GetAllSubscriberRoutesUseCase getAllRoutesUseCase, Account account) {
        super(messages);
        this.getAllRoutesUseCase = getAllRoutesUseCase;
        this.account = account;
    }

    @Override
    public void onStart() {
        if (!getAllRoutesUseCase.isWorking()) {
            getAllRoutesUseCase.execute(getSubscriber());
        }
    }

    @Override
    public void onRelease() {
        getAllRoutesUseCase.stopExecution();
        super.onRelease();
    }

    @NonNull
    private BaseProgressSubscriber<List<Route>> getSubscriber() {
        return new BaseProgressSubscriber<List<Route>>(this) {
            @Override
            public void onNext(List<Route> response) {
                super.onNext(response);
                TravellerRouteView view = getView();
                if (view != null) {
                    view.renderRoutes(response);
                }
            }
        };
    }

    @Override
    public void onAddRouteButtonClick() {
        TravellerRouteView view = getView();
        if (view != null) {
            view.navigateToSearchRouteScreen();
        }
    }

    @Override
    public void onRefresh() {
        getAllRoutesUseCase.stopExecution();
        getAllRoutesUseCase.execute(getSubscriber());
    }
}
