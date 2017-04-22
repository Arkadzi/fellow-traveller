package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.trip.Route;
import us.fellowtraveller.presentation.view.DriverRouteView;

/**
 * Created by arkadius on 4/19/17.
 */

public interface DriverPresenter extends Presenter<DriverRouteView> {
    void onStart();

    void onAddRouteButtonClick();

    void onShowProfileButtonClick();

    void onRefresh();

    void onDelete(Route data);
}
