package us.fellowtraveller.domain.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkadii on 3/6/17.
 */

public class User implements Serializable {
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    private String id;
    private String ssoId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String imageUrl;
    private String about;
    private List<Car> cars;
    @Nullable
    private Long birthday;
    @Nullable
    private Float rating;
    @Nullable
    private Integer commentsCount;
    @Nullable
    private Integer tripCount;

    public User() {
    }

    public List<Car> getCars() {
        if (cars == null) {
            return new ArrayList<>();
        }
        return cars;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setRating(@Nullable Float rating) {
        this.rating = rating;
    }

    public void setCommentsCount(@Nullable Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Nullable
    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public void setTripCount(@Nullable Integer tripCount) {
        this.tripCount = tripCount;
    }

    public float getRating() {
        if (rating == null) {
            return 0;
        }
        return rating;
    }

    public int getTripCount() {
        if (tripCount == null) {
            return 0;
        }
        return tripCount;
    }

    public int getCommentsCount() {
        if (commentsCount == null) {
            return 0;
        }
        return commentsCount;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        User user = (User) o;

        return id != null && id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
