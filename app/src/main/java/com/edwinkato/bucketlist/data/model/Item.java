package com.edwinkato.bucketlist.data.model;

/**
 * Created by edwinkato on 10/18/17.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item {
    private int id;
    private String name;
    private String description;

    public Item() {}  // Needed for Firebase

    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
