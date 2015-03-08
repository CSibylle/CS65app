package edu.dartmouth.cs.memosnap;

import android.location.Location;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Model class defining the properties of a "snap."
 *
 * Created by Devin on 3/3/2015.
 */

@ParseClassName("Snap")
public class SnapParseObj extends ParseObject {
    public SnapParseObj(){

    }

    public Long getId() {
        return getLong("id");
    }

    public void setId(Long id) {
        put("id", id);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type",type);
    }

    public String getDateTime() {
        return getString("dateTime");
    }

    public void setDateTime(String dateTime) {
        put("dateTime", dateTime);
    }

    public String getTag() {
        return getString("tag");
    }

    public void setTag(String tag) {
        put("tag",tag);
    }

    public String getNote() {
        return getString("note");
    }

    public void setNote(String note) {put("note",note);}

    public ParseFile getPhoto() {return getParseFile("photo");}

    public void setPhoto(ParseFile photo) {
        put("photo",photo);
    }

    public String getUser() {return getString("user");}

    public void setUser(String user) {
        put("user",user);
    }

    public ParseGeoPoint getGeoLocation() {return getParseGeoPoint("locationSnapped");}
    public void setGeoLocation(ParseGeoPoint geoLocation) {
        put("locationSnapped", geoLocation);
    }

    public ParseFile getRecording() {return getParseFile("audio");}

    public void setRecording(ParseFile recording) {
        put("audio",recording);
    }

    public JSONArray getComments() {return getJSONArray("comments");}

    public void setComments(JSONArray comments) {
        put("comments",comments);
    }
    public int getLikes() {return getInt("likes");}

}
