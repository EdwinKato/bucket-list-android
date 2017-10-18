package com.dev4tomorrow.edwinkato.bucketlist.model;

/**
 * Created by edwinkato on 10/18/17.
 */

public class BucketList {
    int id;
    String title;
    String description;

    public BucketList(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
