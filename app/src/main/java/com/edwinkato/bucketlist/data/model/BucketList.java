package com.edwinkato.bucketlist.data.model;

/**
 * Created by edwinkato on 10/18/17.
 */

public class BucketList {
    private int mUid;
    private String mName;
    private String mDescription;

    public BucketList() {}  // Needed for Firebase

    public BucketList(int id, String name, String description) {
        this.mUid = id;
        this.mName = name;
        this.mDescription = description;
    }

    public int getUid() {
        return mUid;
    }

    public void setUid(int uid) {
        this.mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }
}
