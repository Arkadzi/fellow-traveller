package us.fellowtraveller.domain.model.trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadius on 4/10/17.
 */

public class RouteEntity {
    public List<Leg> legs;

    public List<String> getPolylines() {
        ArrayList<String> result = new ArrayList<>();
        if (!legs.isEmpty()) {
            for (Step leg : legs.get(0).steps) {
                result.add(leg.polyline.points);
            }
        }
        return result;
    }
}
