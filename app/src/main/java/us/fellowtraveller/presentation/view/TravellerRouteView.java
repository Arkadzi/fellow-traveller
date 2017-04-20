package us.fellowtraveller.presentation.view;

import java.util.List;

import us.fellowtraveller.domain.model.User;
import us.fellowtraveller.domain.model.trip.Route;

/**
 * Created by arkadius on 4/19/17.
 */

public interface TravellerRouteView extends ProgressView {
    void renderRoutes(List<Route> routes);

    void navigateToSearchRouteScreen();
}
