package edu.dartmouth.cs.memosnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.Arrays;

public class SignUpActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            logIn();
        }
    }

    public void logIn()
    {
        ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                SignUpActivity.this);
        Intent parseLoginIntent = loginBuilder.setAppLogo(R.drawable.snaplogoicon)
                .setParseLoginEnabled(true)
                .setParseLoginEmailAsUsername(true)
                .setFacebookLoginEnabled(true)
                .setFacebookLoginPermissions(Arrays.asList("public_profile", "user_friends"))
                .setTwitterLoginEnabled(true)
                .build();
        startActivityForResult(parseLoginIntent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == MemoSnapApplication.LOGIN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Intent i= new Intent(SignUpActivity.this,MainActivity.class);
                i.putExtra("User", ParseUser.getCurrentUser().getString("name"));
                startActivity(i);
            }
        }
    }
}
