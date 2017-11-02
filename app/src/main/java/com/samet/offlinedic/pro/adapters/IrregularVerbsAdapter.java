package com.samet.offlinedic.pro.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.IrregularVerb;

/**
 * Created by samet on 21.10.2017.
 */

public class IrregularVerbsAdapter extends BaseExpandableListAdapter {
    private Context _context;

    public IrregularVerbsAdapter(Context context) {
        this._context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return DataHolder.getInstance().irregularVerbs.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final IrregularVerb irregularVerb = (IrregularVerb) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.irregular_verb_list_view_child, null);
        }

        TextView pastSimple = (TextView) convertView
                .findViewById(R.id.past_simple);
        TextView pastParticiple = (TextView) convertView
                .findViewById(R.id.past_participle);
        TextView thirdPersonSingular = (TextView) convertView
                .findViewById(R.id.third_person_singular);
        TextView presentSimpleGerund = (TextView) convertView
                .findViewById(R.id.present_simple_gerund);
        TextView meaning = (TextView) convertView
                .findViewById(R.id.meaning);

        SpannableString ps = new SpannableString(_context.getString(R.string.past_simple, irregularVerb.getPastSimple()));
        ps.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, 13, 0);
        pastSimple.setText(ps);

        SpannableString pp = new SpannableString(_context.getString(R.string.past_participle, irregularVerb.getPastParticiple()));
        pp.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, 17, 0);
        pastParticiple.setText(pp);

        SpannableString tps = new SpannableString(_context.getString(R.string.third_person_singular, irregularVerb.getThirdPersonSingular()));
        tps.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, 23, 0);
        thirdPersonSingular.setText(tps);

        SpannableString psg = new SpannableString(_context.getString(R.string.present_simple_gerund, irregularVerb.getPresentParticipleGerund()));
        psg.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, 27, 0);
        presentSimpleGerund.setText(psg);

        SpannableString mng = new SpannableString(_context.getString(R.string.meaning, irregularVerb.getMeaning()));
        mng.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, 9, 0);
        meaning.setText(mng);


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return DataHolder.getInstance().irregularVerbs.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return DataHolder.getInstance().irregularVerbs.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        IrregularVerb irregularVerb = (IrregularVerb) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.irregular_verb_list_view_header, null);
        }

        TextView wordTV = (TextView) convertView
                .findViewById(R.id.list_item_header_text_view);
        wordTV.setText(irregularVerb.getWord());
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
