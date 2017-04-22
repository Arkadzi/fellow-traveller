package us.fellowtraveller.presentation.presenter;

import us.fellowtraveller.domain.model.trip.TripPoint;
import us.fellowtraveller.presentation.utils.Messages;
import us.fellowtraveller.presentation.view.FindRouteView;

/**
 * Created by arkadius on 4/22/17.
 */

public class FindRoutePresenterImpl extends ProgressPresenter<FindRouteView> implements FindRoutePresenter {
    public FindRoutePresenterImpl(Messages messages) {
        super(messages);
    }

    @Override
    public void onRouteBuildClick(TripPoint origin, TripPoint destination, long when) {

    }
}
