package us.fellowtraveller.presentation.view;

import java.util.List;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;

/**
 * Created by arkadius on 4/18/17.
 */

public interface FindRouteView extends ProgressView {
    void onRouteCreated(Route response);
}
