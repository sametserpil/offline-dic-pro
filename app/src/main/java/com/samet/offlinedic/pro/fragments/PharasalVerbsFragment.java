package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.adapters.PharasalVerbsAdapter;

public class PharasalVerbsFragment extends Fragment {

    private PharasalVerbsAdapter listAdapter;
    private ExpandableListView expListView;

    public PharasalVerbsFragment() {
    }

    public static PharasalVerbsFragment newInstance() {
        return new PharasalVerbsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pharasal_verbs, container, false);
        expListView = view.findViewById(R.id.pharasal_verbs_listview);
        listAdapter = new PharasalVerbsAdapter(getContext());

        // setting list adapter
        expListView.setAdapter(listAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
