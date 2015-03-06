package edu.dartmouth.cs.memosnap;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Model class defining the properties of a "snap."
 *
 * Created by Devin on 3/3/2015.
 */

@ParseClassName("Snap")
public class Snap extends ParseObject{
    Long id;

    Long user;

    String name;
    String type;
    String dateTime;
    String tag;
    byte[] photo;
    String note;
    // recording;

    // location;

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

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser value) {
        put("user", value);
    }

    public ParseGeoPoint getGeoLocation() {
        return getParseGeoPoint("location");
    }

    public void setGeoLocation(ParseGeoPoint value) {
        put("location", value);
    }

    public static ParseQuery<Snap> getQuery() {
        return ParseQuery.getQuery(Snap.class);
    }

}
