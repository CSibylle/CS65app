package edu.dartmouth.cs.memosnap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by coraliephanord on 3/7/15.
 */
public class SharedAudioAdapter extends ParseQueryAdapter<SnapParseObj> {

    public SharedAudioAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<SnapParseObj>() {
            public ParseQuery<SnapParseObj> create() {
                // Here we can configure a ParseQuery to display
                // only top-rated meals.
                ParseQuery query = new ParseQuery("SnapParseObj");
                query.whereContains("type", "audio");
                query.orderByDescending("date");
                return query;
            }
        });
    }

    @Override
    public View getItemView(SnapParseObj snap, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.shared_image_grid, null);
        }

        super.getItemView(snap , v, parent);

        ParseImageView audioImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile audioFile = snap.getRecording();
        Drawable audioIcon = parent.getResources().getDrawable(R.drawable.mic_small);
        if (audioFile != null) {
            audioImage.setImageDrawable(audioIcon);
            audioImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {}
            });
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.snap_name);
        titleTextView.setText(snap.getName());
        TextView ratingTextView = (TextView) v
                .findViewById(R.id.likes);
        ratingTextView.setText(snap.getLikes());
        return v;
    }

}