package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.adapters.FavHisAdapter;
import com.samet.offlinedic.pro.model.DataHolder;

public class HistoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView historyListView;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        DataHolder.getInstance().dbHelper.readHistory();
        historyListView = view.findViewById(R.id.history_list_view);
        historyListView.setAdapter(new FavHisAdapter(getContext(), DataHolder.getInstance().history));
        historyListView.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), DataHolder.getInstance().history.get(i).getWord(), Toast.LENGTH_SHORT).show();
    }
}
