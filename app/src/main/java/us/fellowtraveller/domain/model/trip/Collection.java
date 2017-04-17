package us.fellowtraveller.domain.model.trip;

import java.io.Serializable;

/**
 * Created by arkadius on 4/17/17.
 */

public class Collection implements Serializable {
    private long datetime;

    public Collection(long datetime) {
        this.datetime = datetime;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
