package com.edwinkato.bucketlist.data.model;

/**
 * Created by edwinkato on 11/23/17.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Tag {
    String nName;
    String mUid;

    public Tag() {}

    public Tag(String nName, String mUid, int bucketListCount) {
        this.nName = nName;
        this.mUid = mUid;
        this.bucketListCount = bucketListCount;
    }

    public int getBucketListCount() {
        return bucketListCount;
    }

    public void setBucketListCount(int bucketListCount) {
        this.bucketListCount = bucketListCount;
    }

    int bucketListCount;

    public Tag(String nName) {
        this.nName = nName;
    }

    public String getName() {
        return nName;
    }

    public void setName(String name) {
        this.nName = name;
    }

    public String getID() {
        return mUid;
    }

    public void setID(String uid) {
        this.mUid = uid;
    }
}
