package com.samet.offlinedic.pro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.PharasalVerb;

/**
 * Created by samet on 23.10.2017.
 */

public class PharasalVerbsAdapter extends BaseExpandableListAdapter {
    private Context _context;

    public PharasalVerbsAdapter(Context context) {
        this._context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return DataHolder.getInstance().pharasalVerbs.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final PharasalVerb pharasalVerb = (PharasalVerb) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.pharasal_verb_list_view_child, null);
        }

        TextView example = (TextView) convertView
                .findViewById(R.id.example);
        TextView meaning = (TextView) convertView
                .findViewById(R.id.meaning);

        example.setText(_context.getString(R.string.example, pharasalVerb.getExample()));
        meaning.setText(_context.getString(R.string.meaning, pharasalVerb.getMeaning()));

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return DataHolder.getInstance().pharasalVerbs.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return DataHolder.getInstance().pharasalVerbs.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        PharasalVerb pharasalVerb = (PharasalVerb) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.irregular_verb_list_view_header, null);
        }

        TextView wordTV = (TextView) convertView
                .findViewById(R.id.list_item_header_text_view);
        wordTV.setText(pharasalVerb.getWord());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
