package edu.dartmouth.cs.memosnap;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

public class MemoSnapApplication extends Application {

    // Key for saving the search distance preference
    private static final String KEY_SEARCH_DISTANCE = "searchDistance";

    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

    private static SharedPreferences preferences;

    //private static ConfigHelper configHelper;

    private static SocialHelper socialHelper;

    public static final int LOGIN_REQUEST = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        SnapParseObj.registerSubclass(SnapParseObj.class);

        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));

        ParseUser.enableAutomaticUser();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}



