package us.fellowtraveller.presentation.presenter;

import java.util.List;

import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.domain.subscribers.BaseProgressSubscriber;
import us.fellowtraveller.domain.usecase.FindRoutesUseCase;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.FindRouteView;

/**
 * Created by arkadius on 4/22/17.
 */

public class FindRoutePresenterImpl extends ProgressPresenter<FindRouteView> implements FindRoutePresenter {
    private FindRoutesUseCase findRoutesUseCase;
    public FindRoutePresenterImpl(Messages messages, FindRoutesUseCase findRoutesUseCase) {
        super(messages);
        this.findRoutesUseCase = findRoutesUseCase;
    }

    @Override
    public void onRouteBuildClick(TripPoint origin, TripPoint destination, double originRadius, double destinationRadius, long when) {
        findRoutesUseCase.setData(origin.getLatLng(), destination.getLatLng(),
                originRadius, destinationRadius, when);
        findRoutesUseCase.execute(new BaseProgressSubscriber<List<Route>>(this) {
            @Override
            public void onNext(List<Route> response) {
                super.onNext(response);
                FindRouteView view = getView();
                if (view != null) {
                    view.onRoutesFound(response);
                }
            }
        });
    }
}
