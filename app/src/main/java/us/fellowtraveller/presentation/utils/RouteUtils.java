package us.fellowtraveller.presentation.utils;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import us.fellowtraveller.domain.model.trip.Collection;
import us.fellowtraveller.domain.model.trip.Point;
import us.fellowtraveller.domain.model.trip.TripPoint;

/**
 * Created by arkadius on 4/17/17.
 */

public class RouteUtils {

    public static List<Point> from(List<TripPoint> gatheringPoints, List<LatLng> correctingPoints,
                                   TripPoint from, TripPoint to) {
        List<Point> points = new ArrayList<>();
        gatheringPoints = getTimedTripPoints(gatheringPoints, from, to);
        from = gatheringPoints.get(0);
        to = gatheringPoints.get(gatheringPoints.size() - 1);

        points.add(new Point(from.getLatLng().latitude, from.getLatLng().longitude, new Collection(from.getDatetime())));
        for (LatLng correctingPoint : correctingPoints) {
            points.add(new Point(correctingPoint.latitude, correctingPoint.longitude, null));
        }
        for (int i = 1; i < gatheringPoints.size() - 1; i++) {
            TripPoint gatheringPoint = gatheringPoints.get(i);
            LatLng latLng = gatheringPoint.getLatLng();
            int position = getInsertPosition(latLng, points);
            Collection collection = new Collection(gatheringPoint.getDatetime());
            Point point = new Point(latLng.latitude, latLng.longitude, collection);
            points.add(position, point);
        }
        points.add(new Point(to.getLatLng().latitude, to.getLatLng().longitude, new Collection(to.getDatetime())));

        return points;
    }

    @NonNull
    private static List<TripPoint> getTimedTripPoints(List<TripPoint> gatheringPoints, TripPoint from, TripPoint to) {
        List<TripPoint> timedTripPoints = new ArrayList<>();
        timedTripPoints.add(from.copy());
        for (TripPoint gatheringPoint : gatheringPoints) {
            TripPoint copy = gatheringPoint.copy();
            copy.setDatetime(timedTripPoints.get(timedTripPoints.size() - 1).getDatetime() + copy.getDatetime());
            timedTripPoints.add(copy);
        }
        TripPoint copy = to.copy();
        copy.setDatetime(timedTripPoints.get(timedTripPoints.size() - 1).getDatetime() + copy.getDatetime());
        timedTripPoints.add(copy);
        return timedTripPoints;
    }

    private static int getInsertPosition(LatLng latLng, List<Point> points) {
        int result = 0;
        float minDistance = Float.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            float[] distance = new float[1];
            Location.distanceBetween(
                    latLng.latitude,
                    latLng.longitude,
                    point.getLatitude(),
                    point.getLongitude(),
                    distance);
            if (distance[0] < minDistance) {
                result = i;
                minDistance = distance[0];
            }
        }
        if (result == 0) {
            return 1;
        } else if (result == points.size() - 1) {
            return points.size() - 2;
        } else {
            float[] distancePrev = new float[1];
            float[] distanceNext = new float[1];
            Point prev = points.get(result - 1);
            Point next = points.get(result + 1);
            Location.distanceBetween(
                    latLng.latitude,
                    latLng.longitude,
                    prev.getLatitude(),
                    prev.getLongitude(),
                    distancePrev);
            Location.distanceBetween(
                    latLng.latitude,
                    latLng.longitude,
                    next.getLatitude(),
                    next.getLongitude(),
                    distanceNext);
            if (distanceNext[0] < distancePrev[0]) result += 1;
            return result;
        }
    }
}
