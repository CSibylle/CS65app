package edu.dartmouth.cs.memosnap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

/**
 * Created by Devin on 3/4/2015.
 */
public class PhotoCursorAdapter extends CursorAdapter {

    public PhotoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.photo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView image = (ImageView) view.findViewById(R.id.photo);
        image.setLayoutParams(new GridView.LayoutParams(500, 500));

        byte[] byteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("photo"));
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            Bitmap bmp = BitmapFactory.decodeStream(bis);
            image.setImageBitmap(bmp);
        } catch (Exception ex) {
        }
    }
}
