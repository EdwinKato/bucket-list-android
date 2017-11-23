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

    public Tag(String nName, String mUid) {
        this.nName = nName;
        this.mUid = mUid;
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

    @Override
    public String toString() {
        return "Tag{" +
                "nName='" + nName + '\'' +
                ", mUid='" + mUid + '\'' +
                '}';
    }
}
