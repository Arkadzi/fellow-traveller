package us.fellowtraveller.domain.model;

/**
 * Created by arkadii on 3/18/17.
 */
public class Car {
    private String id;
    private String title;
    private int capacity;
    private int year;
    private String imageUrl;

    public Car(String id, String title, int capacity, int year, String imageUrl) {
        this.id = id;
        this.title = title;
        this.capacity = capacity;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
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
}
