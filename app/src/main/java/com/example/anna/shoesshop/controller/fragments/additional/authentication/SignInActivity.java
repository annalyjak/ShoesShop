package com.example.anna.shoesshop.controller.fragments.additional.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.firebase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/**
 * Start activity - when user is already logged - starts MainMenuActivity
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Log.i("LOGOWANIE", "Inicjulę widok");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        Button mSignInButton = findViewById(R.id.buttonSignIn);
        Button mSignUpButton = findViewById(R.id.buttonSignUp);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);

        Log.i("LOGOWANIE", "ZAINICJOWANY");
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            Log.i("LOGOWANIE", "ZALOGOWANY BYŁ JUŻ");
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                    hideProgressDialog();

                    if (task.isSuccessful()) {
                        onAuthSuccess(Objects.requireNonNull(task.getResult()).getUser());
                    } else {
                        Toast.makeText(SignInActivity.this, "Sign In Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                    hideProgressDialog();

                    if (task.isSuccessful()) {
                        onAuthSuccess(Objects.requireNonNull(task.getResult()).getUser());
                    } else {
                        Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = SignInUtils.usernameFromEmail(Objects.requireNonNull(user.getEmail()));

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        Log.i("LOGOWANIE", "Rozpoczynam główną aktywność:");
        startActivity(new Intent(SignInActivity.this, MainMenuActivity.class));
        finish();
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignIn) {
            signIn();
        } else if (i == R.id.buttonSignUp) {
            signUp();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}