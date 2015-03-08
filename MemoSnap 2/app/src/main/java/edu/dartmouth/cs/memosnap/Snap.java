package edu.dartmouth.cs.memosnap;

import android.location.Location;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Model class defining the properties of a "snap."
 *
 * Created by Devin on 3/3/2015.
 */

@ParseClassName("Snap")
public class Snap {
    Long id;

    String user = ParseUser.getCurrentUser().toString();

    String name;
    String type;
    String dateTime;
    String tag;
    byte[] photo;
    String note;
    byte[] recording;
    Location location;
    double latitude;
    double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getUser() {return user;}

    public void setUser(String value) {
        this.user = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public ParseGeoPoint getGeoLocation() {
        return new ParseGeoPoint(location.getLatitude(), location.getLongitude());
    }

    //public void setGeoLocation() {;}

    public static ParseQuery<Snap> getQuery() {
        return ParseQuery.getQuery(Snap.class);
    }

    public byte[] getRecording() {return recording;}

    public void setRecording(byte[] recording) {
        this.recording = recording;
    }

}
