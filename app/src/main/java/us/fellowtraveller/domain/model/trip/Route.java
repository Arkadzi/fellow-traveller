package us.fellowtraveller.domain.model.trip;

import java.io.Serializable;
import java.util.List;

/**
 * Created by arkadius on 4/17/17.
 */

public class Route implements Serializable {
    private String id;
    private String title;
    private int seats;
    private Float price;
    private Float priceForRoute;
    private String car;
    private String owner;
    private List<Point> points;

    public long getTime() {
        if (points != null && !points.isEmpty()) {
            Collection collectionData = points.get(0).getCollectionData();
            if (collectionData != null) {
                return collectionData.getDatetime();
            }
        }
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMarshrutka() {
        return priceForRoute != null;
    }

    public float getPrice() {
        return isMarshrutka() ? priceForRoute : price;
    }

    public void setPrice(float price, boolean isMarshrutka) {
        if (isMarshrutka) {
            this.priceForRoute = price;
        } else {
            this.price = price;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeats() {
        return seats;
    }

    public int getSeatsAvailable() {
        int seats = this.seats;
        for (Point point : points) {
            if (point.getCollectionData() != null) {
                seats -= point.getCollectionData().getSubscribers().size();
            }
        }
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getStartPointId() {
        try {
            return points.get(0).getCollectionData().getId();
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void subscribe(String subscriptionPointId, String userId) {
        if (points != null) {
            for (Point point : points) {
                Collection collectionData = point.getCollectionData();
                if (collectionData != null && subscriptionPointId.equals(collectionData.getId())) {
                    collectionData.addSubscriber(userId);
                    break;
                }
            }
        }
    }

    public void unsubscribe(String subscriptionPointId, String userId) {
        if (points != null) {
            for (Point point : points) {
                Collection collectionData = point.getCollectionData();
                if (collectionData != null && subscriptionPointId.equals(collectionData.getId())) {
                    collectionData.removeSubscriber(userId);
                    break;
                }
            }
        }
    }
}
