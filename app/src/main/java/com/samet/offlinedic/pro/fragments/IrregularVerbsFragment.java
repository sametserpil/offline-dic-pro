package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.adapters.IrregularVerbsAdapter;

public class IrregularVerbsFragment extends Fragment {

    private IrregularVerbsAdapter listAdapter;
    private ExpandableListView expListView;

    public IrregularVerbsFragment() {
    }

    public static IrregularVerbsFragment newInstance() {
        return new IrregularVerbsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_irregular_verbs, container, false);

        expListView = view.findViewById(R.id.irregular_verbs_listview);
        listAdapter = new IrregularVerbsAdapter(getContext());

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
