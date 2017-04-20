package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.presentation.view.TravellerRouteView;

/**
 * Created by arkadius on 4/19/17.
 */

public interface TravellerPresenter extends Presenter<TravellerRouteView> {
    void onStart();
    void onAddRouteButtonClick();
    void onRefresh();
}
