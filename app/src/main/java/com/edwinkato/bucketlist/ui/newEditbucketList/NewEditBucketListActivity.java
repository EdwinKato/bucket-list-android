package com.edwinkato.bucketlist.ui.newEditbucketList;

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

import com.afollestad.materialdialogs.MaterialDialog;
import com.edwinkato.bucketlist.R;
import com.edwinkato.bucketlist.data.model.BucketList;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pchmn.materialchips.ChipView;

import java.util.HashSet;
import java.util.Iterator;
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
    HashSet<String> availableTags = new HashSet<>();
    private MaterialDialog dialog;

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

    @BindView(R.id.action_add_tag)
    Button addTagButton;


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

    @OnClick(R.id.action_add_tag)
    public void addTag(){
        dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_tags_input, true)
                .positiveText("Done")
                .show();

        Button addTag = (Button)dialog.findViewById(R.id.add_tag);
        final FlexboxLayout tagsDisplayLayout = (FlexboxLayout)dialog.findViewById(R.id.tags_display_linear_layout);

        populateExistingTags(tagsDisplayLayout);
        final EditText tagText = (EditText)dialog.findViewById(R.id.tag_text);

        addTag.setOnClickListener((view) -> {
            String text = tagText.getText().toString().trim();
            if (!text.equals("")) {
                availableTags.add(text);
                ChipView chip = createChip(text);
                ChipView inActivityChip = createChip(text);
                tagsDisplayLayout.addView(chip);
                chipsLayout.addView(inActivityChip);
                tagText.setText("");
            }
        });

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
        Iterator iterator = availableTags.iterator();
        while (iterator.hasNext()){
            View view = layoutInflater.inflate(R.layout.scroll_view_item, linearLayout, false);
            ChipView chip = view.findViewById(R.id.chip);
            chip.setLabel(iterator.next().toString());

            chip.setOnDeleteClicked((deleteView) -> {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, chip.getLabel() + ": delete clicked", Snackbar.LENGTH_LONG);
                snackbar.show();
            });

            chip.setOnChipClicked((clickView) -> {
                if (selectedTags.contains(chip.getLabel())) {
                    selectedTags.remove(chip.getLabel());
                    chip.setHasAvatarIcon(false);
                } else{
                    selectedTags.add(chip.getLabel());
                    chip.setAvatarIcon(getResources().getDrawable(R.mipmap.ic_checkmark));
                }
            });
            chipsLayout.addView(view);
        }
    }

    private void populateExistingTags(FlexboxLayout layout) {
        Iterator iterator = availableTags.iterator();
        while (iterator.hasNext()){
            ChipView chip = createChip(iterator.next().toString());
            layout.addView(chip);
        }
    }

    private ChipView createChip(String tag){
        ChipView chip = new ChipView(this);
        chip.setLabel(tag);
        chip.setChipBackgroundColor(getResources().getColor(R.color.colorPrimary));
        chip.setLabelColor(getResources().getColor(R.color.white));
        chip.setDeleteIconColor(getResources().getColor(R.color.white));;
        chip.setPadding(2,2,2,2);
        chip.setDeletable(true);
        chip.setOnDeleteClicked((view) -> {
            availableTags.remove(chip.getLabel());
            chip.setVisibility(View.GONE);
        });
        return chip;
    }
}
