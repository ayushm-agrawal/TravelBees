package com.manali.travelbees;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPhotos extends Fragment {


    private ProgressBar mProgressBar;

    public FragmentPhotos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_photos, container, false);

        mProgressBar = (ProgressBar)view.findViewById(R.id.photosProgressBar);
        mProgressBar.setVisibility(View.GONE);
        return null;
    }

}
