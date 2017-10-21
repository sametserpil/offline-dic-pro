package com.samet.offlinedic.pro.model;

import com.samet.offlinedic.pro.database.DBHelper;

import java.util.List;

/**
 * Created by samet on 21.10.2017.
 */

public class DataHolder {
    private static final DataHolder ourInstance = new DataHolder();


    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public List<IrregularVerb> irregularVerbs;

    public List<PharasalVerb> pharasalVerbs;

    public List<DailyPharase> dailyPharases;

    public DBHelper dbHelper;
}
