package com.example.zephyrus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DailyTarot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_tarot);
        TarotCard card = (TarotCard) getIntent().getSerializableExtra("TarotCard");

        TextView leftMeaningTextView = findViewById(R.id.leftMeaningTextView);
        TextView centerMeaningTextView = findViewById(R.id.centerMeaningTextView);
        TextView rightMeaningTextView = findViewById(R.id.rightMeaningTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView tarotCardImageView = findViewById(R.id.tarotCardImageView);

        String[] captions = card.getCaptions();
        leftMeaningTextView.setText(captions[0]);
        centerMeaningTextView.setText(captions[1]);
        rightMeaningTextView.setText(captions[2]);
        descriptionTextView.setText(card.getDescription());
        tarotCardImageView.setImageDrawable(card.getImage());


        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.spreads_navigation);

        navigation.setSelectedItemId(R.id.spreads_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(DailyTarot.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(DailyTarot.this, SpreadsActivity.class);
                    startActivity(b);
                    break;
                case R.id.cardList_nav:
                    Intent c = new Intent(DailyTarot.this, CardListActivity.class);
                    startActivity(c);
                    break;
                case R.id.journal_nav:
                    Intent d = new Intent(DailyTarot.this, JournalActivity.class);
                    startActivity(d);
                    break;
                default:
                    break;
            }
            return false;
        });

    }
}