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
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by coraliephanord on 3/5/15.
 */


public class SocialHelper {
    public ParseGeoPoint geoPoint;

    public void share(final Activity activity,Snap snap) {
        SnapParseObj snapParseObj = new SnapParseObj();
        snapParseObj.setId(snap.getId());
        snapParseObj.setUser(snap.getUser());
        snapParseObj.setType(snap.getType());
        snapParseObj.setDateTime(snap.getDateTime());
        if (snap.getTag() != null ) {
            snapParseObj.setTag(snap.getTag());
        }
        if (snap.getPhoto() != null ){
            ParseFile photo = new ParseFile(snap.getPhoto());
            snapParseObj.setPhoto(photo);
        }
        if (snap.getNote() != null ) {
            snapParseObj.setNote(snap.getNote());
        }
        if (snap.getRecording() != null ) {
            ParseFile audio = new ParseFile(snap.getRecording());
            snapParseObj.setRecording(audio);
        }
        if (snap.getLocation() != null) {
            ParseGeoPoint geoPoint = new ParseGeoPoint(snap.getLocation().getLatitude(), snap.getLocation().getLongitude());
            snapParseObj.setGeoLocation(geoPoint);
        }
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setWriteAccess(ParseUser.getCurrentUser(),true);
        snapParseObj.setACL(acl);
        snapParseObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
               activity.finish();
            }
        });
    }

    public void commentToSnap(SnapParseObj snap, String comment) throws JSONException {
        JSONArray comments = snap.getComments();
        comments.put(comments.length(),comment);
        snap.put("comments",comments);
        snap.saveInBackground();
    }
    public  void likeSnap(SnapParseObj snap) {
        int likes = snap.getLikes() + 1;
        snap.put("likes",likes);
        snap.saveInBackground();
    }
    public void deleteSnap(SnapParseObj snap) {
        snap.deleteInBackground();
    }


}
