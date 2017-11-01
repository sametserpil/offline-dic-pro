package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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


@SuppressWarnings("deprecation")
public class SearchFragment extends Fragment implements FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, FloatingSearchView.OnMenuItemClickListener, View.OnClickListener {

    private FloatingSearchView searchView;

    private TextView meaningTextView;

    private FloatingActionButton favoritesButton;

    private String lastSearched;

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
        favoritesButton = view.findViewById(R.id.addToFavButton);
        meaningTextView = view.findViewById(R.id.meaning_text_view);
        searchView = view.findViewById(R.id.floating_search_view);
        searchView.setOnQueryChangeListener(this);
        searchView.setOnSearchListener(this);
        searchView.setOnMenuItemClickListener(this);
        favoritesButton.setOnClickListener(this);
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
        lastSearched = currentQuery;
        searchView.clearSearchFocus();
        searchView.clearQuery();
        DataHolder.getInstance().dbHelper.addToHistory(lastSearched, DataHolder.getInstance().direction);
        if (DataHolder.getInstance().direction.equals(Direction.EN2TR)) {
            meaningTextView.setText(DataHolder.getInstance().dbHelper.getMeaningEN2TR(currentQuery));

        } else {
            meaningTextView.setText(DataHolder.getInstance().dbHelper.getMeaningTR2EN(currentQuery));
        }
    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.english:
                changeDirectionToEn();
                break;
            case R.id.turkish:
                changeDirectionToTr();
                break;
        }
    }

    private void changeDirectionToTr() {
        Toast.makeText(getContext(), getString(R.string.direction_changed), Toast.LENGTH_SHORT).show();
        searchView.setSearchText("");
        searchView.setSearchHint(getString(R.string.search_tr));
        meaningTextView.setText("");
        DataHolder.getInstance().direction = Direction.TR2EN;
        writeTranslationDirection(Direction.TR2EN);
    }

    private void changeDirectionToEn() {
        Toast.makeText(getContext(), getString(R.string.direction_changed), Toast.LENGTH_SHORT).show();
        searchView.setSearchText("");
        searchView.setSearchHint(getString(R.string.search_en));
        meaningTextView.setText("");
        DataHolder.getInstance().direction = Direction.EN2TR;
        writeTranslationDirection(Direction.EN2TR);
    }

    private void writeTranslationDirection(Direction direction) {
        SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.direction), direction.getName());
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addToFavButton:
                addToFavorites();
                break;
        }
    }

    private void addToFavorites() {
        long result = DataHolder.getInstance().dbHelper.addToFavorites(lastSearched, DataHolder.getInstance().direction);
        if (result >= 0) {
            Toast.makeText(getContext(), getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
        } else if (result == -2) {
            Toast.makeText(getContext(), getString(R.string.favorite_already_exists), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.failed_to_add_favorite), Toast.LENGTH_SHORT).show();
        }
    }
}
