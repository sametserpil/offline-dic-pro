package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.adapters.DailyPharaseAdapter;
import com.samet.offlinedic.pro.model.Category;
import com.samet.offlinedic.pro.model.DailyPharase;
import com.samet.offlinedic.pro.model.DataHolder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;


public class DailyPharasesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MaterialBetterSpinner categorySpinner;
    private ListView pharasesListView;

    public DailyPharasesFragment() {
        // Required empty public constructor
    }

    public static DailyPharasesFragment newInstance() {
        return new DailyPharasesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_pharases, container, false);
        pharasesListView = (ListView) view.findViewById(R.id.pharases_list_view);
        categorySpinner = (MaterialBetterSpinner) view.findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories, android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemClickListener(this);

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
    public void onClick(View view) {

    }

    private List<DailyPharase> getPharasesForCategory(Category category) {
        List<DailyPharase> pharases = new ArrayList<>();
        for (DailyPharase dp : DataHolder.getInstance().dailyPharases) {
            if (dp.getCategory().equals(category)) {
                pharases.add(dp);
            }
        }
        return pharases;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.GREETINGS), getContext()));
                break;
            case 1:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.INTRODUCTIONS), getContext()));
                break;
            case 2:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.HELPING), getContext()));
                break;
            case 3:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.GENERAL), getContext()));
                break;
            case 4:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.SHOPPING), getContext()));
                break;
            case 5:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.ADDRESS), getContext()));
                break;
            case 6:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.FOOD), getContext()));
                break;
            case 7:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.TRAVEL), getContext()));
                break;
            case 8:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.ACCOMODATION), getContext()));
                break;
            case 9:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.TELEPHONE), getContext()));
                break;
            case 10:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.FRIEND), getContext()));
                break;
            case 11:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.HEALTH), getContext()));
                break;
            case 12:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.BUSINESS), getContext()));
                break;
            case 13:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.MONEY), getContext()));
                break;
            case 14:
                pharasesListView.setAdapter(new DailyPharaseAdapter(getPharasesForCategory(Category.EDUCATION), getContext()));
                break;
            default:
                break;
        }
    }
}
