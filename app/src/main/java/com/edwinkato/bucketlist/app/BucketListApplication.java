package com.edwinkato.bucketlist.app;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by edwinkato on 11/20/17.
 */

public class BucketListApplication extends Application {

    private static BucketListApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
        }
    }

    public static Context getInstance() {
        return mInstance;
    }
}
