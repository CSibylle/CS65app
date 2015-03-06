package edu.dartmouth.cs.memosnap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

/**
 * Created by coraliephanord on 3/5/15.
 */


public class SocialHelper {
    public ParseGeoPoint geoPoint;

    public void share(final Activity activity,Snap snap) {

        //given current location convert to geo so i can send to cloud
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        snap.setACL(acl);

        snap.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
               activity.finish();
            }
        });

    }

    public void updateMyWall() {

    }
}
