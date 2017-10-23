package com.samet.offlinedic.pro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.DailyPharase;

import java.util.List;

/**
 * Created by samet on 23.10.2017.
 */

public class DailyPharaseAdapter extends BaseAdapter {

    private List<DailyPharase> pharaseList;
    private Context _context;

    public DailyPharaseAdapter(List<DailyPharase> pharaseList, Context _context) {
        this.pharaseList = pharaseList;
        this._context = _context;
    }

    @Override
    public int getCount() {
        return pharaseList.size();
    }

    @Override
    public Object getItem(int i) {
        return pharaseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final DailyPharase dailyPharase = (DailyPharase) getItem(i);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.daily_pharase_list_item, null);
        }

        TextView meaning = (TextView) view.findViewById(R.id.meaning);
        TextView pharase = (TextView) view.findViewById(R.id.pharase);

        pharase.setText(dailyPharase.getPhrase());
        meaning.setText(dailyPharase.getMeaning());

        return view;
    }
}
