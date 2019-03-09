package com.iustin.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,View.OnKeyListener{

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginSignupButton;
    TextView loginModeTextView;

    Boolean loginModeActive = false;

    public void redirectIfLoggedIn(){
        if(
                ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
            startActivity(intent);
        }
    }


    public void signupLogin(View view){

        if (loginModeActive) {
            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if( e == null){
                        Log.i("Info","User logged in");
                        redirectIfLoggedIn();
                    }else {
                        String message = e.getMessage();
                        if(message.toLowerCase().contains("java")){
                            message = e.getMessage().substring(e.getMessage().indexOf(" "));
                        }
                        Toast.makeText(MainActivity.this,message , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {

            ParseUser user = new ParseUser();

            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Info", "User signup");
                        redirectIfLoggedIn();
                    } else {
                        String message = e.getMessage();
                        if(message.toLowerCase().contains("java")){
                            message = e.getMessage().substring(e.getMessage().indexOf(" "));
                        }
                        Toast.makeText(MainActivity.this,message , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginModeTextView) {
            if(loginModeActive){
            loginModeActive = false;
            loginSignupButton.setText("Sign up");
            loginModeTextView.setText("Or, login");
        }else {
            loginModeActive = true;
            loginSignupButton.setText("Login");
            loginModeTextView.setText("Or, sign up");
        }
        }else if(view.getId() == R.id.constraintLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            signupLogin(view);
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login menu");
        usernameEditText = findViewById(R.id.usernameEditTex);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginSignupButton = findViewById(R.id.signupLoginButton);
        loginModeTextView = findViewById(R.id.loginModeTextView);
        loginModeTextView.setOnClickListener(this);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(this);

        redirectIfLoggedIn();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }


}

