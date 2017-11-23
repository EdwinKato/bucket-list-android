package com.edwinkato.bucketlist.ui.newEditbucketList;

import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edwinkato.bucketlist.R;
import com.edwinkato.bucketlist.data.model.BucketList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pchmn.materialchips.ChipView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewEditBucketListActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static String TAG = NewEditBucketListActivity.class.getSimpleName();
    private DatabaseReference mDatabase;
    private Validator validator;
    protected boolean validated;
    Boolean mIsEdit = false;
    private String[] arraySpinner = new String[] { "Pending"};
    LayoutInflater layoutInflater;
    HashSet<String> selectedTags = new HashSet<>();

    @BindView(R.id.action_cancel)
    ImageView actionCancel;

    @BindView(R.id.action_save_bucket_list)
    ImageView actionSave;

    @NotEmpty
    @BindView(R.id.name)
    EditText textName;

    @NotEmpty
    @BindView(R.id.description)
    EditText textDescription;

    @NotEmpty
    @BindView(R.id.edit_bucket_list)
    TextView activityTitle;

    @BindView(R.id.new_bucket_list_linear_layout)
    LinearLayout linearLayout;

    @BindView(R.id.action_save_and_close)
    Button saveAndClose;

    @BindView(R.id.select_status)
    Spinner selectStatus;

    @BindView(R.id.chips_layout)
    LinearLayout chipsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_bucket_list);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        layoutInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mIsEdit= false;
            } else {
                mIsEdit=true;
//                isEdit= extras.getBoolean("IS_EDIT");
            }
        } else {
//            isEdit= (Boolean) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        if (mIsEdit) {
            activityTitle.setText(R.string.edit_bucket_list);
            this.arraySpinner = new String[] {
                    "Pending", "Completed"
            };
            selectStatus.setEnabled(true);
        } else {
            selectStatus.setEnabled(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        selectStatus.setAdapter(adapter);
        setUpHorizontalScrollView();
    }

    @OnClick(R.id.action_cancel)
    public void closeActivity(){
        finish();
    }

    @OnClick(R.id.action_save_bucket_list)
    public void saveBucketList(){
        save();
    }

    @OnClick(R.id.action_save_and_close)
    public void saveAndClose(){
        save();
        if (!validated) {
            return;
        }
        finish();
    }

    private void save() {
        if (validator != null)
            validator.validate();
        if (!validated) {
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = textName.getText().toString();
        String description = textDescription.getText().toString();
        onAddBucketList(new BucketList(uid, name, description));
    }

    protected void onAddBucketList(BucketList bucketList) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("posts").push().setValue(bucketList, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference reference) {
                if (error != null) {
                    Log.e(TAG, "Failed to write bucket list to database", error.toException());
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Error, bucket list has not been saved!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Bucket list saved successfully", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        validated = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setUpHorizontalScrollView() {
        for (int i = 0; i < 5; i++) {
            View view = layoutInflater.inflate(R.layout.scroll_view_item, linearLayout, false);
            final ChipView chip = (ChipView) view.findViewById(R.id.chip);
            chip.setLabel("Chipo");
            chip.setOnDeleteClicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, chip.getLabel() + ": delete clicked", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });

            chip.setOnChipClicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedTags.contains(chip.getLabel())) {
                        selectedTags.remove(chip.getLabel());
                        chip.setHasAvatarIcon(false);
//                        chip.setDeletable(true);
                        return;
                    }
                    selectedTags.add(chip.getLabel());
                    chip.setAvatarIcon(getResources().getDrawable(R.mipmap.ic_checkmark));
//                    chip.setDeletable(false);
                }
            });
            chipsLayout.addView(view);

        }
    }
}
