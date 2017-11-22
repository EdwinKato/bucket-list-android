package com.edwinkato.bucketlist.ui.bucketLists;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.edwinkato.bucketlist.R;
import com.edwinkato.bucketlist.ui.LoginActivity;
import com.edwinkato.bucketlist.ui.profile.ProfileActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BucketListsActivity extends AppCompatActivity {

    public static final String TAG = "BUCKET_LISTS_ACTIVITY";

    private FirebaseUser user;
    @BindView(R.id.account_picture) ImageView accountIcon;
    @BindView(R.id.add_icon) ImageView addIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_lists);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.account_picture)
    public void goToProfile(){
        Intent intent = new Intent(BucketListsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(BucketListsActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
