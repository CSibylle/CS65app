package edu.dartmouth.cs.memosnap;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;

public class SavedShotsFragment extends Fragment {
    private SnapDBHelper dbHelper;
    CursorAdapter adapter;
    GridView grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_saved_shots, container, false);
        grid = (GridView) view.findViewById(R.id.gridview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new SnapDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Only get the photos
        final Cursor cursor = db.rawQuery("SELECT * FROM snaps WHERE type = 'Camera'", null);

        adapter = new PhotoCursorAdapter(getActivity(), cursor);

        grid.setAdapter(adapter);

        AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), SaveActivity.class);
                intent.putExtra("id", cursor.getLong(0));
                intent.putExtra("History", "Camera");

                startActivity(intent);
                getActivity().finish();
            }
        };
        grid.setOnItemClickListener(mListener);
        db.close();


    }
}
