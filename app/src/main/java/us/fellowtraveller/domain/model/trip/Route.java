package us.fellowtraveller.domain.model.trip;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadius on 4/17/17.
 */

public class Route implements Serializable {
    private String id;
    private String title;
    private int seats;
    private float price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
}
