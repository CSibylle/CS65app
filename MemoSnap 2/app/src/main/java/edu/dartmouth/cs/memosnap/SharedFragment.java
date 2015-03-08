package edu.dartmouth.cs.memosnap;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseQueryAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class SharedFragment extends ListFragment {
    private ParseQueryAdapter<SnapParseObj> mainAdapter;
    private SharedImagesAdapter sharedImagesAdapter;
    private SharedNotesAdapter sharedNotesAdapter;
    private SharedAudioAdapter sharedAudioAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shared, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.snap_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.snap_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener mListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String typePicked = parent.getItemAtPosition(pos).toString();
                Toast.makeText(parent.getContext(),
                        "On Item Select : \n" + typePicked,
                        Toast.LENGTH_LONG).show();
                if (typePicked == "Images" ){
                    showImages();
                }
                if (typePicked == "Notes" ){
                    showNotes();
                }
                if (typePicked == "Audio" ){
                    showAudio();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(mListener);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lv = getListView();
        lv.setClickable(false);

        mainAdapter = new ParseQueryAdapter<SnapParseObj>(this.getActivity(), SnapParseObj.class);
        mainAdapter.setTextKey("title");
        mainAdapter.setImageKey("photo");

        // Subclass of ParseQueryAdapter
        sharedImagesAdapter = new SharedImagesAdapter(this.getActivity());
        sharedNotesAdapter = new SharedNotesAdapter(this.getActivity());
        sharedAudioAdapter = new SharedAudioAdapter(this.getActivity());

        // Default view is all meals
        setListAdapter(sharedImagesAdapter);

        AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int pos, long id) {
                String typePicked = parent.getItemAtPosition(pos).toString();
                Toast.makeText(parent.getContext(),
                        "On Item Select : \n" + typePicked,
                        Toast.LENGTH_LONG).show();
            }
        };
        lv.setOnItemClickListener(mListener);
    }

    private void updateMealList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }
    private void showImages() {
        sharedImagesAdapter.loadObjects();
        setListAdapter(sharedImagesAdapter);
    }
    private void showNotes() {
        Log.e("Notes","NOTES LISTVIEW");
        sharedNotesAdapter.loadObjects();
        setListAdapter(sharedNotesAdapter);
    }
    private void showAudio() {
        sharedAudioAdapter.loadObjects();
        setListAdapter(sharedAudioAdapter);
    }
}
