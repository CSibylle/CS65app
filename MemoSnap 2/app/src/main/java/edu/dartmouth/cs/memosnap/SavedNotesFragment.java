package edu.dartmouth.cs.memosnap;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;


public class SavedNotesFragment extends ListFragment {
    private SnapDBHelper dbHelper;
    CursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_notes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new SnapDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Only get the notes
        final Cursor cursor = db.rawQuery("SELECT * FROM snaps WHERE type = 'Note'", null);

        ListView lv = getListView();

        adapter = new NoteCursorAdapter(getActivity(), cursor);
        lv.setAdapter(adapter);

        AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), NoteActivity.class);
                intent.putExtra("id", cursor.getLong(0));
                intent.putExtra("History", "HistoryActivity");

                startActivity(intent);
                getActivity().finish();
            }
        };

        lv.setOnItemClickListener(mListener);
    }
}
