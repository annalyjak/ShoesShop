package com.example.anna.shoesshop.controller.fragments.additional.authentication;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.firebase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class LoginFragment extends Fragment {

//    public static final int LOG_OUT_VIEW = 1;
//    public static final int LOG_IN_VIEW = 2;
//    private int viewType = LOG_OUT_VIEW;

    /**
     * Firebase auth
     */
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView nameOfUser;
//    private View mProgressView;
    private View mLoginFormView;
    private View mLogOutView;
    private ProgressDialog mProgressDialog;

    private View focusView = null;

    public LoginFragment() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            setLogOutView();
            nameOfUser.setText(currentUser.getEmail());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailView = view.findViewById(R.id.email);

        mPasswordView = view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        nameOfUser = view.findViewById(R.id.user_name);

        Button mEmailSignInButton = view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view1 -> attemptLogin());

        Button signOutButton = view.findViewById(R.id.log_out_button);
        signOutButton.setOnClickListener(view1 -> singOut());

        mLoginFormView = view.findViewById(R.id.login_form);
//        mProgressView = view.findViewById(R.id.login_progress);
        mLogOutView = view.findViewById(R.id.log_out_form);
        return view;
    }

    private void setLogOutView() {
        mLoginFormView.setVisibility(View.GONE);
        mLogOutView.setVisibility(View.VISIBLE);
//        viewType = LOG_IN_VIEW;
    }

    private void setLogInView() {
        mLoginFormView.setVisibility(View.VISIBLE);
        mLogOutView.setVisibility(View.GONE);
//        viewType = LOG_OUT_VIEW;
    }

    private boolean validateForm(String email, String password) {
        boolean cancel = false;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !SignInUtils.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!SignInUtils.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        return cancel;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        resetErrors();

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        focusView = null;
        boolean cancel = validateForm(email, password);

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            signIn(email, password);
        }
    }

    private void resetErrors() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = SignInUtils.usernameFromEmail(Objects.requireNonNull(user.getEmail()));
        writeNewUser(user.getUid(), username, user.getEmail());

        setLogOutView();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
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

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    private void signIn(String email, String password) {
        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    Log.d("LOGOWANIE", "signIn:onComplete:" + task.isSuccessful());
                    hideProgressDialog();

                    if (task.isSuccessful()) {
                        onAuthSuccess(Objects.requireNonNull(task.getResult()).getUser());
                    } else {
                        Toast.makeText(getContext(), "Sign In Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void singOut() {
        mAuth.signOut();
        setLogInView();
    }

}
