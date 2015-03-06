package edu.dartmouth.cs.memosnap;

import android.app.Application;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseGeoPoint;

/**
 * Created by coraliephanord on 3/5/15.
 */


public class SocialHelper {
    public ParseGeoPoint geoPoint;

    public void share() {
        /*
        Location location = (currentLocation == null) ? lastLocation : currentLocation;
        geoPoint = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        updateMyWall();*/
    }

    public void updateMyWall() {

    }
}
