package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.WordSuggestion;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener {

    private FloatingSearchView searchView;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.floating_search_view);
        searchView.setOnQueryChangeListener(this);
        searchView.setOnSearchListener(this);
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

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        List<SearchSuggestion> newSuggestions = new ArrayList<>();
        newSuggestions.add(new WordSuggestion("samet"));
        newSuggestions.add(new WordSuggestion("asdasd"));
        searchView.swapSuggestions(newSuggestions);
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        Toast.makeText(getContext(), searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchAction(String currentQuery) {
        Toast.makeText(getContext(), currentQuery, Toast.LENGTH_SHORT).show();
    }
}
