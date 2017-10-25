package com.samet.offlinedic.pro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.samet.offlinedic.pro.model.Category;
import com.samet.offlinedic.pro.model.DailyPharase;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.IrregularVerb;
import com.samet.offlinedic.pro.model.PharasalVerb;
import com.samet.offlinedic.pro.model.WordSuggestion;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    // The Android's default system path of your application database.
    private static final String DB_PATH = System.getenv("EXTERNAL_STORAGE") + "/sozluk-db/";
    private static final String KEY_WORD = "word";
    private static final String KEY_WORD_EXTENDED = "word_extended";
    private static final String KEY_MEANING = "meaning";
    private static final String FIELD_SUGGESTION = "word";
    private static String DB_NAME = "yeni.db";
    private static String TR2EN_TABLE = "TR";
    private static String EN2TR_TABLE = "EN";
    private final String IRREGULAR_VERBS_TABLE = "IRREGULAR_VERBS";
    private final String[] IRREGULAR_VERBS_COLS = new String[]{"word", "past_simple", "past_participle", "third_person_singular", "present_participle_gerund", "meaning"};
    private final String PHARASAL_VERBS_TABLE = "PHARASAL_VERBS";
    private final String[] PHARASAL_VERBS_COLS = new String[]{"word", "meaning", "example"};
    private final String DAILY_PHARASES_TABLE = "DAILY_PHARASES";
    private final String[] DAILY_PHARASES_COLS = new String[]{"pharase", "meaning", "category"};
    private final SQLiteDatabase myDataBase;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        readIrregularVerbs();
        readPharasalVerbs();
        readDailyPharases();
    }


    private void readIrregularVerbs() {
        new AsyncTask<Void, Void, List<IrregularVerb>>() {
            @Override
            protected List<IrregularVerb> doInBackground(Void... voids) {
                List<IrregularVerb> irregularVerbs = new ArrayList<IrregularVerb>();

                Cursor cursor = myDataBase.query(IRREGULAR_VERBS_TABLE, IRREGULAR_VERBS_COLS, null, null, null, null, null);
                if (cursor != null) {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        irregularVerbs.add(new IrregularVerb(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                    }
                    cursor.close();
                }
                return irregularVerbs;
            }

            @Override
            protected void onPostExecute(List<IrregularVerb> list) {
                super.onPostExecute(list);
                DataHolder.getInstance().irregularVerbs = list;
            }

        }.execute();
    }

    private void readPharasalVerbs() {
        new AsyncTask<Void, Void, List<PharasalVerb>>() {
            @Override
            protected List<PharasalVerb> doInBackground(Void... voids) {
                List<PharasalVerb> pharasalVerbs = new ArrayList<PharasalVerb>();

                Cursor cursor = myDataBase.query(PHARASAL_VERBS_TABLE, PHARASAL_VERBS_COLS, null, null, null, null, null);
                if (cursor != null) {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        pharasalVerbs.add(new PharasalVerb(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                    }
                    cursor.close();
                }
                return pharasalVerbs;
            }

            @Override
            protected void onPostExecute(List<PharasalVerb> list) {
                super.onPostExecute(list);
                DataHolder.getInstance().pharasalVerbs = list;
            }

        }.execute();
    }

    private void readDailyPharases() {
        new AsyncTask<Void, Void, List<DailyPharase>>() {
            @Override
            protected List<DailyPharase> doInBackground(Void... voids) {
                List<DailyPharase> dailyPharases = new ArrayList<DailyPharase>();

                Cursor cursor = myDataBase.query(DAILY_PHARASES_TABLE, DAILY_PHARASES_COLS, null, null, null, null, null);
                if (cursor != null) {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        dailyPharases.add(new DailyPharase(cursor.getString(0), cursor.getString(1), Category.valueOf(cursor.getString(2))));
                    }
                    cursor.close();
                }
                return dailyPharases;
            }

            @Override
            protected void onPostExecute(List<DailyPharase> list) {
                super.onPostExecute(list);
                DataHolder.getInstance().dailyPharases = list;
            }

        }.execute();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String getMeaningEN2TR(String query) {
        String[] columns = new String[]{KEY_WORD_EXTENDED, KEY_MEANING};
        Cursor cursor = myDataBase.query(EN2TR_TABLE, columns, KEY_WORD + "=\""
                + query + "\" collate nocase", null, null, null, null);
        String meaning = "";
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                meaning += "<font color=#FF4081>" + cursor.getString(0) + "</font><br><br><font color=#3F51B5>" + cursor.getString(1).replaceAll("\\\\n", "<br>") + "</font><br>";
            }
            cursor.close();
            return meaning;
        }

        return null;
    }

    public String getMeaningTR2EN(String query) {
        String[] columns = new String[]{KEY_WORD_EXTENDED, KEY_MEANING};
        Cursor cursor = myDataBase.query(TR2EN_TABLE, columns, KEY_WORD + "=\""
                + query + "\" collate nocase", null, null, null, null);
        String meaning = "";
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                meaning += "<font color=#FF4081>" + cursor.getString(0) + "</font><br><br><font color=#3F51B5>" + cursor.getString(1).replaceAll("\\\\n", "<br>") + "</font><br>";
            }
            cursor.close();
            return meaning;
        }

        return null;
    }

    public List<SearchSuggestion> getSuggestionsEN2TR(String s) {
        Cursor cursor = myDataBase.query(true, EN2TR_TABLE, new String[]{FIELD_SUGGESTION},
                FIELD_SUGGESTION + " LIKE \"" + s + "%\"", null, null, null, FIELD_SUGGESTION, "20");

        List<SearchSuggestion> suggestions = new ArrayList<>();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                suggestions.add(new WordSuggestion(cursor.getString(0)));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return suggestions;
    }


    public List<SearchSuggestion> getSuggestionsTR2EN(String s) {
        Cursor cursor = myDataBase.query(true, TR2EN_TABLE, new String[]{FIELD_SUGGESTION},
                FIELD_SUGGESTION + " LIKE \"" + s + "%\"", null, null, null, FIELD_SUGGESTION, "20");

        List<SearchSuggestion> suggestions = new ArrayList<>();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                suggestions.add(new WordSuggestion(cursor.getString(0)));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return suggestions;
    }


    public long addEnglishWord(String word, String meaning) {
        if (word.equals("") || meaning.equals(""))
            return 0;
        ContentValues cv = new ContentValues();
        cv.put(KEY_WORD, word);
        cv.put(KEY_MEANING, meaning);
        return myDataBase.insert(EN2TR_TABLE, null, cv);
    }

    public long addTurkishWord(String word, String meaning) {
        if (word.equals("") || meaning.equals(""))
            return 0;
        ContentValues cv = new ContentValues();
        cv.put(KEY_WORD, word);
        cv.put(KEY_MEANING, meaning);
        return myDataBase.insert(TR2EN_TABLE, null, cv);
    }


}
