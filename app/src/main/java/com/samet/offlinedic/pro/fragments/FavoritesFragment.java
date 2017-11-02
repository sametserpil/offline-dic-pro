package com.samet.offlinedic.pro.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


public class FavoritesFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView favoritesListView;

    public FavoritesFragment() {
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        DataHolder.getInstance().dbHelper.readFavorites();
        favoritesListView = view.findViewById(R.id.favorites_list_view);
        favoritesListView.setAdapter(new FavHisAdapter(getContext(), DataHolder.getInstance().favorites));
        favoritesListView.setOnItemClickListener(this);
        favoritesListView.setOnItemLongClickListener(this);
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
        intent.putExtra(getString(R.string.word), DataHolder.getInstance().favorites.get(i).getWord());
        intent.putExtra(getString(R.string.lang), DataHolder.getInstance().favorites.get(i).getDirection().getName());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.are_you_sure))
                .setContentText(getString(R.string.fav_will_be_deleted))
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
                                .setContentText(getString(R.string.fav_deleted))
                                .setConfirmText(getString(R.string.ok))
                                .setConfirmClickListener(null)
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        deleteFavorite(i);
                    }
                })
                .show();
        return true;
    }

    private void deleteFavorite(int index) {
        String word = DataHolder.getInstance().favorites.get(index).getWord();
        DataHolder.getInstance().favorites.remove(index);
        DataHolder.getInstance().dbHelper.removeFavorite(word);
        favoritesListView.setAdapter(new FavHisAdapter(getContext(), DataHolder.getInstance().favorites));
    }
}
