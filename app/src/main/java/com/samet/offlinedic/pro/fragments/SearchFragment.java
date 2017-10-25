package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.Direction;


public class SearchFragment extends Fragment implements FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, FloatingSearchView.OnMenuItemClickListener {

    private FloatingSearchView searchView;

    private TextView meaningTextView;

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
        meaningTextView = (TextView) view.findViewById(R.id.meaning_text_view);
        searchView = view.findViewById(R.id.floating_search_view);
        searchView.setOnQueryChangeListener(this);
        searchView.setOnSearchListener(this);
        searchView.setOnMenuItemClickListener(this);
        if (DataHolder.getInstance().direction.equals(Direction.EN2TR)) {
            searchView.setSearchHint(getString(R.string.search_en));
        } else {
            searchView.setSearchHint(getString(R.string.search_tr));
        }
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
        if (newQuery.length() < 2) {
            searchView.clearSuggestions();
            return;
        }
        meaningTextView.setText("");
        if (DataHolder.getInstance().direction.equals(Direction.EN2TR)) {
            searchView.swapSuggestions(DataHolder.getInstance().dbHelper.getSuggestionsEN2TR(newQuery));
        } else {
            searchView.swapSuggestions(DataHolder.getInstance().dbHelper.getSuggestionsTR2EN(newQuery));
        }

        searchView.bringToFront();
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        onSearchAction(searchSuggestion.getBody());
    }

    @Override
    public void onSearchAction(String currentQuery) {
        searchView.clearSearchFocus();
        if (DataHolder.getInstance().direction.equals(Direction.EN2TR)) {
            meaningTextView.setText(Html.fromHtml(DataHolder.getInstance().dbHelper.getMeaningEN2TR(currentQuery)));
        } else {
            meaningTextView.setText(Html.fromHtml(DataHolder.getInstance().dbHelper.getMeaningTR2EN(currentQuery)));
        }

    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.english:
                Toast.makeText(getContext(), getString(R.string.direction_changed), Toast.LENGTH_SHORT).show();
                searchView.setSearchText("");
                searchView.setSearchHint(getString(R.string.search_en));
                meaningTextView.setText("");
                DataHolder.getInstance().direction = Direction.EN2TR;
                writeTranslationDirection(Direction.EN2TR);
                break;
            case R.id.turkish:
                Toast.makeText(getContext(), getString(R.string.direction_changed), Toast.LENGTH_SHORT).show();
                searchView.setSearchText("");
                searchView.setSearchHint(getString(R.string.search_tr));
                meaningTextView.setText("");
                DataHolder.getInstance().direction = Direction.TR2EN;
                writeTranslationDirection(Direction.TR2EN);
                break;
        }
    }

    private void writeTranslationDirection(Direction direction) {

        SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.direction), direction.getName());
        editor.apply();
    }
}
