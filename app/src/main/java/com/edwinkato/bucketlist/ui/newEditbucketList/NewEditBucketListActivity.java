package com.edwinkato.bucketlist.ui.newEditbucketList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.edwinkato.bucketlist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewEditBucketListActivity extends AppCompatActivity {

    @BindView(R.id.action_cancel)
    ImageView actionCancel;

    @BindView(R.id.action_save_bucket_list)
    ImageView actionSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_bucket_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.action_cancel)
    public void closeActivity(){
        finish();
    }

    @OnClick(R.id.action_save_bucket_list)
    public void saveBucketList(){
        save();
        finish();
    }

    private void save() {

    }
}
