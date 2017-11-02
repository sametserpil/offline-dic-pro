package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.samet.offlinedic.pro.FavHisMeaningViewer;
import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.adapters.FavHisAdapter;
import com.samet.offlinedic.pro.model.DataHolder;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HistoryFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

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
        historyListView.setOnItemLongClickListener(this);
        FloatingActionButton clearHistoryButton = view.findViewById(R.id.clear_history_button);
        clearHistoryButton.setOnClickListener(this);
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
        Intent intent = new Intent(getContext(), FavHisMeaningViewer.class);
        intent.putExtra(getString(R.string.word), DataHolder.getInstance().history.get(i).getWord());
        intent.putExtra(getString(R.string.lang), DataHolder.getInstance().history.get(i).getDirection().getName());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.are_you_sure))
                .setContentText(getString(R.string.history_will_be_deleted))
                .setConfirmText(getString(R.string.yes_delete))
                .setCancelText(getString(R.string.no))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText(getString(R.string.deleted))
                                .setContentText(getString(R.string.history_deleted))
                                .setConfirmText(getString(R.string.ok))
                                .setConfirmClickListener(null)
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        deleteHistory(i);
                    }
                })
                .show();
        return true;
    }

    private void deleteHistory(int index) {
        String word = DataHolder.getInstance().history.get(index).getWord();
        DataHolder.getInstance().history.remove(index);
        DataHolder.getInstance().dbHelper.removeHistory(word);
        historyListView.setAdapter(new FavHisAdapter(getContext(), DataHolder.getInstance().history));
    }

    @Override
    public void onClick(View view) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.are_you_sure))
                .setContentText(getString(R.string.all_history_will_be_deleted))
                .setConfirmText(getString(R.string.yes_delete))
                .setCancelText(getString(R.string.no))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText(getString(R.string.deleted))
                                .setContentText(getString(R.string.history_cleared))
                                .setConfirmText(getString(R.string.ok))
                                .setConfirmClickListener(null)
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        clearHistory();
                    }
                })
                .show();
    }

    private void clearHistory() {
        DataHolder.getInstance().history.clear();
        DataHolder.getInstance().dbHelper.clearHistory();
        historyListView.setAdapter(new FavHisAdapter(getContext(), DataHolder.getInstance().history));
    }
}
