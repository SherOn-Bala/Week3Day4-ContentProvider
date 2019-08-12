package ca.judacribz.week3day4_contentprovider.master_detail_flow;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.judacribz.week3day4_contentprovider.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokeFragment extends Fragment {


    public PokeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poke, container, false);
    }

}
