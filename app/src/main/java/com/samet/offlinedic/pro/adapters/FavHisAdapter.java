package com.samet.offlinedic.pro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.FavHis;

import java.util.List;

/**
 * Created by samet on 26.10.2017.
 */

public class FavHisAdapter extends BaseAdapter {

    private Context _context;

    private List<FavHis> elements;

    public FavHisAdapter(Context _context, List<FavHis> elements) {
        this._context = _context;
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int i) {
        return elements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final FavHis element = (FavHis) getItem(i);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.fav_his_list_item, null);
        }

        TextView word = view.findViewById(R.id.favhis_text_view);

        word.setText(element.getWord());

        return view;
    }
}
