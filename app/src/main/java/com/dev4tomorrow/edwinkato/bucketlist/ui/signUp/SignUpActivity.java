package com.dev4tomorrow.edwinkato.bucketlist.ui.signUp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev4tomorrow.edwinkato.bucketlist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    public final String TAG = "SIGN_UP_ACTIVITY";
    private FirebaseAuth mAuth;

    @BindView(R.id.sign_up_form) View mSignUpFormView;
    @BindView(R.id.sign_up_progress) View mProgressView;
    @BindView(R.id.register_button) Button mRegisterButton;
    @BindView(R.id.password) EditText mPasswordField;
    @BindView(R.id.confirm_password) EditText mConfirmPasswordField;
    @BindView(R.id.email) AutoCompleteTextView mEmailField;
    @BindView(R.id.error_message) TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        // Initialise the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.register_button) void submit() {
        if (this.isEmpty(mEmailField) || this.isEmpty(mPasswordField) || this.isEmpty(mConfirmPasswordField)) {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText(R.string.missing_fields);
            return;
        }
        if (this.doPasswordsMatch(mPasswordField, mConfirmPasswordField)) {
            this.createAccount(mEmailField.getText().toString().trim(), mPasswordField.getText().toString().trim());
            mErrorMessage.setVisibility(View.GONE);
        } else {
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText(R.string.un_matching_passwords);
        }
    }

    private void createAccount(String email, String password) {
        showProgress(true);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "User account successfully created");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "User account not created");
                        }
                        showProgress(false);
                    }
                });
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private boolean doPasswordsMatch(EditText password, EditText confirmPassword) {
        return password.getText().toString().trim().equals(confirmPassword.getText().toString().trim());
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
