package us.fellowtraveller.presentation.presenter;

import java.util.List;

import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.view.CreateRouteView;

/**
 * Created by arkadius on 4/18/17.
 */

public interface CreateRoutePresenter extends Presenter<CreateRouteView> {
    void onRouteBuildClick(TripPoint origin, TripPoint destination, List<TripPoint> items);
    void onCreateRouteClick(Route route);
}
