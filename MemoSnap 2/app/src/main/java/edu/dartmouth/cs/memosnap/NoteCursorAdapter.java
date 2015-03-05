package edu.dartmouth.cs.memosnap;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Devin on 3/4/2015.
 */
public class NoteCursorAdapter extends CursorAdapter {
    private Context context;

    public NoteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.entry_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView1 = (TextView) view.findViewById(R.id.snap_name);
        TextView textView2 = (TextView) view.findViewById(R.id.snap_info);

        textView1.setText(cursor.getString(1));
        textView2.setText(cursor.getString(3));
    }
}
