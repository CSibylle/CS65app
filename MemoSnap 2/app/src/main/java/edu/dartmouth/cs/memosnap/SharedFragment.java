package edu.dartmouth.cs.memosnap;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SharedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SharedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false);
    }
}
