package com.samet.offlinedic.pro.database;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.samet.offlinedic.pro.R;
import com.samet.offlinedic.pro.model.Category;
import com.samet.offlinedic.pro.model.DailyPharase;
import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.Direction;
import com.samet.offlinedic.pro.model.FavHis;
import com.samet.offlinedic.pro.model.IrregularVerb;
import com.samet.offlinedic.pro.model.PharasalVerb;
import com.samet.offlinedic.pro.model.WordSuggestion;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class DBHelper extends SQLiteOpenHelper implements TextToSpeech.OnInitListener {

    final static String DB_NAME = "main.1.com.samet.offlinedic.pro.obb";
    final static String DB_PATH = System.getenv("EXTERNAL_STORAGE") + "/Android/obb/com.samet.offlinedic.pro/";
    private final Context _context;
    private final String KEY_WORD = "word";
    private final String KEY_WORD_EXTENDED = "word_extended";
    private final String KEY_MEANING = "meaning";
    private final String KEY_LANG = "lang";
    private final String FIELD_SUGGESTION = "word";
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
    private SQLiteDatabase myDataBase;
    private Bitmap speaker;
    private TextToSpeech tts;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this._context = context;
        try {
            SQLiteDatabase.loadLibs(_context); //first init the db libraries with the context
            myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, "getDatabasePath();", null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            Toast.makeText(_context, _context.getString(R.string.failed_to_open_db), Toast.LENGTH_LONG).show();

        }
        readIrregularVerbs();
        readPharasalVerbs();
        readDailyPharases();
        readFavorites();
        readHistory();
        speaker = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_volume_up_black_24dp);
        tts = new TextToSpeech(_context, this);
        tts.setLanguage(Locale.US);
    }

    public static boolean checkDatabase() {
        String dbFile = DB_PATH + DB_NAME;
        return new File(dbFile).exists();
    }

    private void readIrregularVerbs() {
        new AsyncTask<Void, Void, List<IrregularVerb>>() {
            @Override
            protected List<IrregularVerb> doInBackground(Void... voids) {
                List<IrregularVerb> irregularVerbs = new ArrayList<>();

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
                List<PharasalVerb> pharasalVerbs = new ArrayList<>();

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
                List<DailyPharase> dailyPharases = new ArrayList<>();

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
        final Cursor cursor = myDataBase.query(EN2TR_TABLE, columns, KEY_WORD + "=\""
                + query + "\" collate nocase", null, null, null, null);
        String meaning;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                final String word = cursor.getString(0);
                meaning = word + "ab" + "\n\n" + cursor.getString(1).replaceAll("\\\\n", "\n") + "\n";
                SpannableString spannableMeaning = new SpannableString(meaning);
                spannableMeaning.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, word.length(), 0);
                final Drawable speakerDrawable = new BitmapDrawable(_context.getResources(), speaker);
                speakerDrawable.setBounds(50, 0, 130, 80);
                ImageSpan imageSpan = new ImageSpan(speakerDrawable, ImageSpan.ALIGN_BOTTOM);
                spannableMeaning.setSpan(imageSpan, word.length(), word.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        speak(word);
                    }

                };
                spannableMeaning.setSpan(clickableSpan, word.length(), word.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableMeaning.setSpan(new ForegroundColorSpan(Color.parseColor("#3F51B5")), word.length() + 2, meaning.length(), 0);
                spannableStringBuilder.append(spannableMeaning);
            }
            cursor.close();
            return spannableStringBuilder;
        }

        return null;
    }

    public Spanned getMeaningTR2EN(String query) {
        String[] columns = new String[]{KEY_WORD_EXTENDED, KEY_MEANING};
        final Cursor cursor = myDataBase.query(TR2EN_TABLE, columns, KEY_WORD + "=\""
                + query + "\" collate nocase", null, null, null, null);
        String meaning;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                final String word = cursor.getString(0);
                final String meaningText = cursor.getString(1).replaceAll("\\\\n", "\n");
                meaning = word + "ab" + "\n\n" + cursor.getString(1).replaceAll("\\\\n", "\n") + "\n";
                SpannableString spannableMeaning = new SpannableString(meaning);
                spannableMeaning.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), 0, word.length(), 0);
                final Drawable speakerDrawable = new BitmapDrawable(_context.getResources(), speaker);
                speakerDrawable.setBounds(50, 0, 130, 80);
                ImageSpan imageSpan = new ImageSpan(speakerDrawable, ImageSpan.ALIGN_BOTTOM);
                spannableMeaning.setSpan(imageSpan, word.length(), word.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        speak(meaningText);
                    }

                };
                spannableMeaning.setSpan(clickableSpan, word.length(), word.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableMeaning.setSpan(new ForegroundColorSpan(Color.parseColor("#3F51B5")), word.length() + 2, meaning.length(), 0);
                spannableStringBuilder.append(spannableMeaning);
            }
            cursor.close();
            return spannableStringBuilder;
        }

        return null;
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
        Collections.reverse(favorites);
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

    public void removeFavorite(String word) {
        myDataBase.delete(FAVORITES_TABLE, "word='" + word + "'", null);
    }

    public void removeHistory(String word) {
        myDataBase.delete(HISTORY_TABLE, "word='" + word + "'", null);
    }

    public void clearHistory() {
        myDataBase.delete(HISTORY_TABLE, "1=1", null);
    }

    @Override
    public void onInit(int i) {
        Log.i(_context.getString(R.string.app_name), "Initialized tts");
    }

    private void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    public void killTTS() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
