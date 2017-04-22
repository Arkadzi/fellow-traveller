package us.fellowtraveller.presentation.presenter;

import java.util.List;

import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.view.CreateRouteView;
import us.fellowtraveller.presentation.view.FindRouteView;

/**
 * Created by arkadius on 4/18/17.
 */

public interface FindRoutePresenter extends Presenter<FindRouteView> {
    void onRouteBuildClick(TripPoint origin, TripPoint destination, long when);
}
