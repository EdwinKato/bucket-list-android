package com.edwinkato.bucketlist.ui.bucketLists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.edwinkato.bucketlist.R;
import com.edwinkato.bucketlist.ui.LoginActivity;
import com.edwinkato.bucketlist.ui.newEditbucketList.NewEditBucketListActivity;
import com.edwinkato.bucketlist.ui.profile.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BucketListsActivity extends AppCompatActivity {

    public static final String TAG = "BUCKET_LISTS_ACTIVITY";

    private FirebaseUser user;
    @BindView(R.id.action_profile) ImageView accountIcon;
    @BindView(R.id.action_add_bucket_list) ImageView addIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_lists);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.action_profile)
    public void goToProfile(){
        Intent intent = new Intent(BucketListsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_add_bucket_list)
    public void goToNewEditBucketList(){
        Intent intent = new Intent(BucketListsActivity.this, NewEditBucketListActivity.class);
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
