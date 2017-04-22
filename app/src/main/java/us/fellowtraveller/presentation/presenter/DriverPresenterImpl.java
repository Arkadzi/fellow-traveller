package us.fellowtraveller.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.DeleteRouteUseCase;
import us.fellowtraveller.domain.usecase.GetAllOwnerRoutesUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.DriverRouteView;

/**
 * Created by arkadius on 4/19/17.
 */

public class DriverPresenterImpl extends ProgressPresenter<DriverRouteView> implements DriverPresenter {

    private final GetAllOwnerRoutesUseCase getAllRoutesUseCase;
    private final DeleteRouteUseCase deleteRouteUseCase;
    private final Account account;

    public DriverPresenterImpl(Messages messages, GetAllOwnerRoutesUseCase getAllRoutesUseCase, DeleteRouteUseCase deleteRouteUseCase, Account account) {
        super(messages);
        this.getAllRoutesUseCase = getAllRoutesUseCase;
        this.deleteRouteUseCase = deleteRouteUseCase;
        this.account = account;
    }

    @Override
    public void onStart() {
        if (!getAllRoutesUseCase.isWorking()) {
            getAllRoutesUseCase.setOwnerId(account.user().getId());
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
                DriverRouteView view = getView();
                if (view != null) {
                    view.renderRoutes(response);
                }
            }
        };
    }

    @Override
    public void onAddRouteButtonClick() {
        DriverRouteView view = getView();
        if (view != null) {
            if (!account.user().getCars().isEmpty()) {
                view.navigateToCreateRouteScreen();
            } else {
                view.showNoCarsButton();
            }
        }
    }

    @Override
    public void onShowProfileButtonClick() {
        DriverRouteView view = getView();
        if (view != null) {
            view.showProfile(account.user());
        }
    }

    @Override
    public void onRefresh() {
        getAllRoutesUseCase.stopExecution();
        getAllRoutesUseCase.execute(getSubscriber());
    }

    @Override
    public void onDelete(Route data) {
        deleteRouteUseCase.setRoute(data);
        deleteRouteUseCase.execute(new BaseProgressSubscriber<Route>(this) {
            @Override
            public void onNext(Route response) {
                super.onNext(response);
                DriverRouteView view = getView();
                if (view != null) {
                    view.onRouteDeleted(response);
                }
            }
        });
    }
}
