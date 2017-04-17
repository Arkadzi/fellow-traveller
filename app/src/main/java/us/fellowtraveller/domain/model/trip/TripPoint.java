package us.fellowtraveller.domain.model.trip;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by arkadius on 4/16/17.
 */

public class TripPoint implements Serializable {
    @Nullable
    private String address;
    @Nullable
    private String name;
    private LatLng latLng;
    private long datetime;

    public TripPoint(@Nullable String address, @Nullable String name, LatLng latLng, long datetime) {
        this.address = address;
        this.name = name;
        this.latLng = latLng;
        this.datetime = datetime;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getAddress() {
        if (address == null) {
            return "";
        }
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        String address = getAddress();
        String name = getName();
        /*if (!address.isEmpty() && !name.isEmpty()) {
            return String.format("%s, %s", name, address);
        } else */if (!address.isEmpty()) {
            return address;
        } else if (!name.isEmpty()) {
            return name;
        } else {
            return String.format("(%s, %s)", latLng.latitude, latLng.longitude);
        }
    }

    public TripPoint copy() {
        return new TripPoint(address, name, latLng, datetime);
    }
}
