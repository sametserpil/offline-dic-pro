package com.samet.offlinedic.pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.samet.offlinedic.pro.model.DataHolder;
import com.samet.offlinedic.pro.model.Direction;

public class FavHisMeaningViewer extends AppCompatActivity {

    TextView meaningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_his_meaning_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.dictionary);
        meaningTextView = findViewById(R.id.favhis_meaning_text_view);
        meaningTextView.setMovementMethod(LinkMovementMethod.getInstance());
        Intent myIntent = getIntent();
        String word = myIntent.getStringExtra(getString(R.string.word));
        String lang = myIntent.getStringExtra(getString(R.string.lang));

        setMeaning(word, lang);
    }

    private void setMeaning(String word, String lang) {
        if (lang.equals(Direction.TR2EN.getName())) {
            meaningTextView.setText(DataHolder.getInstance().dbHelper.getMeaningTR2EN(word));
        } else {
            meaningTextView.setText(DataHolder.getInstance().dbHelper.getMeaningEN2TR(word));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
