package us.fellowtraveller.domain.model;

import java.io.Serializable;

/**
 * Created by arkadii on 3/18/17.
 */
public class Car implements Serializable {
    public static final int MAX_RATING = 5;
    private String id;
    private String title;
    private int capacity;
    private int year;
    private String imageUrl;
    private int condition;

    public Car(String id, String title, int capacity, int year, int condition, String imageUrl) {
        this.id = id;
        this.title = title;
        this.capacity = capacity;
        this.year = year;
        this.condition = condition;
        this.imageUrl = imageUrl;
    }

    public Car(String title, int capacity, int year, int condition) {
        this(null, title, capacity, year, condition, null);
    }

    public String getId() {
        return id;
    }

    public int getCondition() {
        return condition;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
