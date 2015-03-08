package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;


public class ProfileSettingsActivity extends Activity {
    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private TextView userNameTextView;
    private Button logInOutButton;
    private ParseUser currentUser;
    private ImageView profilePic;
    private ProfilePictureView userProfilePictureView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        getActionBar().setDisplayShowHomeEnabled(true);

        emailTextView = (TextView) findViewById(R.id.editEmail);
        nameTextView = (TextView) findViewById(R.id.editName);
        logInOutButton = (Button) findViewById(R.id.log_in_out_button);
        userNameTextView = (TextView) findViewById(R.id.editUserName);
        profilePic = (ImageView) findViewById(R.id.imageProfile);
        userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
        userProfilePictureView.setCropped(true);
        //titleTextView.setText(R.string.profile_title_logged_in);

        ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(this, "Instrument");
        adapter.setImageKey("photo");


        logInOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            ProfileSettingsActivity.this);
                    startActivityForResult(loginBuilder.build(), MemoSnapApplication.LOGIN_REQUEST);
                }
            }
        });
    }
            //String j =(String) b.get("User");

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            try {
                showProfileLoggedIn();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showProfileLoggedOut();
        }
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() throws IOException, JSONException {
        //getTwitterInfo(currentUser.getString("name"));
        makeMeRequest(ParseFacebookUtils.getSession());
        //titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getEmail());

        String fullName = currentUser.getString("name");
        if (fullName != null) {
            nameTextView.setText(fullName);
        }
        String userName = currentUser.getUsername();
        if (userName != null) {
            userNameTextView.setText(userName);
        }
        else {userNameTextView.setText(fullName);}
        logInOutButton.setText(R.string.profile_logout_button_label);
    }

    //get facebook info if user for fb
    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {

                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                                userProfilePictureView.setVisibility(View.VISIBLE);
                                profilePic.setVisibility(View.INVISIBLE);
                                // Set the id for the ProfilePictureView
                                // view that in turn displays the profile picture.
                                userProfilePictureView.setProfileId(user.getId());
                                // Set the Textview's text to the user's name.
                                userNameTextView.setText(user.getFirstName());
                            }
                        }
                        if (response.getError() != null) {
                            // nadaa
                        }
                    }
                });
        request.executeAsync();
    }
    //get twitter info if user twitter
    private void getTwitterInfo(String handle) throws IOException, JSONException {
        HttpClient client = new DefaultHttpClient();
        HttpGet verifyGet = new HttpGet(
                "https://api.twitter.com/1.1/users/show.json?screen_name=" + handle);
        ParseTwitterUtils.getTwitter().signRequest(verifyGet);
        HttpResponse response = client.execute(verifyGet);
        InputStream is = response.getEntity().getContent();
        JSONObject responseJson = new JSONObject(IOUtils.toString(is));
        String url = responseJson.getString("profile_image_url");

        final AQuery androidQuery = new AQuery(this);
        //fetch and set the image from internet, cache with file and memory
        androidQuery.id(R.id.imageProfile).image(url);
    }


    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        //titleTextView.setText(R.string.profile_title_logged_out);
        userProfilePictureView.setVisibility(View.INVISIBLE);
        emailTextView.setText("");
        nameTextView.setText("");
        userNameTextView.setText("");
        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.default_profile));
        logInOutButton.setText(R.string.profile_login_button_label);
    }

    public void logOut(View v)
    {
        ParseUser.logOut();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent i= new Intent(ProfileSettingsActivity.this,MainActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_social) {
            Intent intent = new Intent(this, SocialActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
