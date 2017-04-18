package us.fellowtraveller.presentation.view;

import java.util.List;

import us.fellowtraveller.domain.model.Car;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.Route;

/**
 * Created by arkadius on 4/18/17.
 */

public interface CreateRouteView extends ProgressView {
    void onRouteBuilt(List<Point> response, List<Car> cars);

    void onRouteCreated(Route response);
}
