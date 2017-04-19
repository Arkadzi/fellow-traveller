package us.fellowtraveller.domain.model.trip;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by arkadius on 4/17/17.
 */

public class Point implements Serializable {
    private double latitude;
    private double longitude;
    private Collection collectionData;

    public Point(double latitude, double longitude, Collection collection) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.collectionData = collection;
    }

    public LatLng getPosition(){
        return new LatLng(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Point{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", collection=" + collectionData +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Collection getCollectionData() {
        return collectionData;
    }

    public void setCollectionData(Collection collection) {
        this.collectionData = collection;
    }
}
