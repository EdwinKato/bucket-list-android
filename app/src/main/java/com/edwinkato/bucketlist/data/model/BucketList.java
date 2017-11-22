package com.edwinkato.bucketlist.data.model;

/**
 * Created by edwinkato on 10/18/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BucketList {
    private String mUid;
    private String mName;
    private String mDescription;

    public BucketList() {}  // Needed for Firebase

    public BucketList(String uid, String name, String description) {
        this.mUid = uid;
        this.mName = name;
        this.mDescription = description;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
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
