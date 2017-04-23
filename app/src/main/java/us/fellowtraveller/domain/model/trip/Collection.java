package us.fellowtraveller.domain.model.trip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadius on 4/17/17.
 */

public class Collection implements Serializable {
    private long datetime;
    private String id;
    private List<String> subscribers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getSubscribers() {
        if (subscribers != null) {
            return subscribers;
        }
        return new ArrayList<>();
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }

    public Collection(long datetime) {
        this.datetime = datetime;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public void addSubscriber(String userId) {
        if (subscribers == null) {
            subscribers = new ArrayList<>();
        }
        subscribers.add(userId);
    }

    public void removeSubscriber(String userId) {
        if (subscribers != null) {
            subscribers.remove(userId);
        }
    }
}
