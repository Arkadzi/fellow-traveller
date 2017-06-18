package us.fellowtraveller.domain.model;

import java.io.Serializable;

/**
 * Created by arkadius on 6/17/17.
 */

public class Comment implements Serializable {
    private String title;
    private long datetime;
    private boolean forDriver;
    private String text;
    private int rating;
    private User sender;
    private User recipient;


    public Comment(String title, long datetime, boolean forDriver, String text, int rating, User sender, User recipient) {
        this.title = title;
        this.datetime = datetime;
        this.forDriver = forDriver;
        this.text = text;
        this.rating = rating;
        this.sender = new User();
        this.sender.setId(sender.getId());
        this.sender.setFirstName(sender.getFirstName());
        this.sender.setLastName(sender.getLastName());
        this.sender.setImageUrl(sender.getImageUrl());
        this.recipient = new User();
        this.recipient.setId(recipient.getId());
        this.recipient.setFirstName(recipient.getFirstName());
        this.recipient.setLastName(recipient.getLastName());
        this.recipient.setImageUrl(recipient.getImageUrl());
    }

    public String getTitle() {
        return title;
    }

    public long getDatetime() {
        return datetime;
    }

    public boolean isForDriver() {
        return forDriver;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }
}
