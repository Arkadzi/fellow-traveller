package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.presentation.view.ViewRouteView;

/**
 * Created by arkadius on 4/22/17.
 */

public interface ViewRoutePresenter extends Presenter<ViewRouteView> {
    void onStart(String userId);
}
