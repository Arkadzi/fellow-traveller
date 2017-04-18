package us.fellowtraveller.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import us.fellowtraveller.domain.model.Account;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.subscribers.SimpleSubscriberListener;
import us.fellowtraveller.domain.usecase.AddRouteUseCase;
import us.fellowtraveller.domain.usecase.GetRouteUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.CreateRouteView;

/**
 * Created by arkadius on 4/18/17.
 */

public class CreateRoutePresenterImpl extends ProgressPresenter<CreateRouteView> implements CreateRoutePresenter {

    private final AddRouteUseCase addRouteUseCase;
    private final GetRouteUseCase buildRouteUseCase;
    private final Account account;

    @Inject
    public CreateRoutePresenterImpl(Messages messages, AddRouteUseCase addRouteUseCase, GetRouteUseCase buildRouteUseCase, Account account) {
        super(messages);
        this.addRouteUseCase = addRouteUseCase;
        this.buildRouteUseCase = buildRouteUseCase;
        this.account = account;
    }

    @Override
    public void onRelease() {
        addRouteUseCase.stopExecution();
        buildRouteUseCase.stopExecution();
        super.onRelease();
    }

    private Subscriber<List<Point>> getBuildRouteSubscriber() {
        return new BaseProgressSubscriber<List<Point>>(this) {
            @Override
            public void onNext(List<Point> response) {
                super.onNext(response);
                CreateRouteView view = getView();
                if (view != null) {
                    view.onRouteBuilt(response, account.user().getCars());
                }
            }
        };
    }

    @Override
    public void onRouteBuildClick(TripPoint origin, TripPoint destination, List<TripPoint> items) {
        buildRouteUseCase.setCoords(origin, destination, items);
        buildRouteUseCase.execute(getBuildRouteSubscriber());
    }

    @Override
    public void onCreateRouteClick(Route route) {
        addRouteUseCase.setRoute(route);
        addRouteUseCase.execute(getCreateRouteSubscriber());
    }

    @NonNull
    private BaseProgressSubscriber<Route> getCreateRouteSubscriber() {
        return new BaseProgressSubscriber<Route>(this) {
            @Override
            public void onNext(Route response) {
                super.onNext(response);
                CreateRouteView view = getView();
                if (view != null) {
                    view.onRouteCreated(response);
                }
            }
        };
    }
}
