package com.samet.offlinedic.pro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.samet.offlinedic.pro.model.Category;
import com.samet.offlinedic.pro.model.DailyPharase;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.Direction;
import com.samet.offlinedic.pro.model.FavHis;
import com.samet.offlinedic.pro.model.IrregularVerb;
import com.samet.offlinedic.pro.model.PharasalVerb;
import com.samet.offlinedic.pro.model.WordSuggestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    // The Android's default system path of your application database.
    private final String DB_PATH = System.getenv("EXTERNAL_STORAGE") + "/sozluk-db/";
    private final String KEY_WORD = "word";
    private final String KEY_WORD_EXTENDED = "word_extended";
    private final String KEY_MEANING = "meaning";
    private final String KEY_LANG = "lang";
    private final String FIELD_SUGGESTION = "word";
    private final static String DB_NAME = "yeni.db";
    private final String TR2EN_TABLE = "TR";
    private final String EN2TR_TABLE = "EN";
    private final String HISTORY_TABLE = "HISTORY";
    private final String FAVORITES_TABLE = "FAVORITES";
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
        readFavorites();
        readHistory();
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

    public Spanned getMeaningEN2TR(String query) {
        String[] columns = new String[]{KEY_WORD_EXTENDED, KEY_MEANING};
        Cursor cursor = myDataBase.query(EN2TR_TABLE, columns, KEY_WORD + "=\""
                + query + "\" collate nocase", null, null, null, null);
        String meaning = "";
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                meaning += "<font color=#FF4081>" + cursor.getString(0) + "</font><br><br><font color=#3F51B5>" + cursor.getString(1).replaceAll("\\\\n", "<br>") + "</font><br>";
            }
            cursor.close();
            return formatMeaningText(meaning);
        }

        return null;
    }

    public Spanned getMeaningTR2EN(String query) {
        String[] columns = new String[]{KEY_WORD_EXTENDED, KEY_MEANING};
        Cursor cursor = myDataBase.query(TR2EN_TABLE, columns, KEY_WORD + "=\""
                + query + "\" collate nocase", null, null, null, null);
        String meaning = "";
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                meaning += "<font color=#FF4081>" + cursor.getString(0) + "</font><br><br><font color=#3F51B5>" + cursor.getString(1).replaceAll("\\\\n", "<br>") + "</font><br>";
            }
            cursor.close();
            return formatMeaningText(meaning);
        }

        return null;
    }

    private Spanned formatMeaningText(String query) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(query, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(query);
        }
    }

    public List<SearchSuggestion> getSuggestionsEN2TR(String query) {
        Cursor cursor = myDataBase.query(true, EN2TR_TABLE, new String[]{FIELD_SUGGESTION},
                FIELD_SUGGESTION + " LIKE \"" + query + "%\"", null, null, null, FIELD_SUGGESTION, "20");

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


    public List<SearchSuggestion> getSuggestionsTR2EN(String query) {
        Cursor cursor = myDataBase.query(true, TR2EN_TABLE, new String[]{FIELD_SUGGESTION},
                FIELD_SUGGESTION + " LIKE \"" + query + "%\"", null, null, null, FIELD_SUGGESTION, "20");

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

    public long addToFavorites(String word, Direction direction) {
        if (word.equals(""))
            return -1;
        if (favoriteAlreadyExists(word)) {
            return -2;
        }
        ContentValues cv = new ContentValues();
        cv.put(KEY_WORD, word);
        cv.put(KEY_LANG, direction.getName());
        return myDataBase.insert(FAVORITES_TABLE, null, cv);
    }

    private boolean favoriteAlreadyExists(String word) {
        for (FavHis fav : DataHolder.getInstance().favorites) {
            if (fav.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }

    public void readFavorites() {
        List<FavHis> favorites = new ArrayList<>();
        String[] columns = new String[]{KEY_WORD, KEY_LANG};
        Cursor cursor = myDataBase.query(FAVORITES_TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                favorites.add(new FavHis(cursor.getString(0), Direction.valueOf(cursor.getString(1))));
            }
            cursor.close();
        }
        DataHolder.getInstance().favorites = favorites;
    }

    public long addToHistory(String word, Direction direction) {
        if (word.equals(""))
            return -1;
        ContentValues cv = new ContentValues();
        cv.put(KEY_WORD, word);
        cv.put(KEY_LANG, direction.getName());
        return myDataBase.insert(HISTORY_TABLE, null, cv);
    }


    public void readHistory() {
        List<FavHis> history = new ArrayList<>();
        String[] columns = new String[]{KEY_WORD, KEY_LANG};
        Cursor cursor = myDataBase.query(HISTORY_TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                history.add(new FavHis(cursor.getString(0), Direction.valueOf(cursor.getString(1))));
            }
            cursor.close();
        }
        Collections.reverse(history);
        DataHolder.getInstance().history = history;
    }

}
