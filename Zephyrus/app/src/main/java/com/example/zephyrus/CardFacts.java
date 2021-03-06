package com.example.zephyrus;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CardFacts extends AnimatedActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.RIGHT, Gravity.RIGHT);
        setContentView(R.layout.card_facts);

        int tarotCardID = (int) getIntent().getSerializableExtra("TarotCardID");
        TarotCard card = null;
        try {
            card = TarotCard.readNewTarotCardById(getApplicationContext(), tarotCardID);
        }
        catch (Exception e){}

        TextView topMeaningTextView = findViewById(R.id.topMeaningTextView);
        TextView centerMeaningTextView = findViewById(R.id.centerMeaningTextView);
        TextView bottomMeaningTextView = findViewById(R.id.bottomMeaningTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView tarotCardImageView = findViewById(R.id.cardImageView_);

        String[] captions = card.getCaptions();
        String description = card.getDescription();
        topMeaningTextView.setText(captions[0]);
        centerMeaningTextView.setText(captions[1]);
        bottomMeaningTextView.setText(captions[2]);
        descriptionTextView.setText(description);
        tarotCardImageView.setImageDrawable(card.getImage());

        /*
        if (TarotCard.State.REVERSED == state) {
            tarotCardImageView.setRotation(180);
        }
        */


        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.card_facts_navigation);
        navigation.setSelectedItemId(R.id.cardList_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Bundle animationBundle = getAnimationOptions(CardFacts.this).toBundle();

            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(CardFacts.this, CalendarActivity.class);
                    startActivity(a, animationBundle);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(CardFacts.this, SpreadsActivity.class);
                    startActivity(b, animationBundle);
                    break;
                case R.id.cardList_nav:
                    Intent c = new Intent(CardFacts.this, CardListActivity.class);
                    startActivity(c, animationBundle);
                    break;
                case R.id.journal_nav:
                    Intent d = new Intent(CardFacts.this, JournalActivity.class);
                    startActivity(d, animationBundle);
                    break;
                case R.id.settings_nav:
                    Intent e = new Intent(CardFacts.this, SettingsActivity.class);
                    startActivity(e, animationBundle);
                    break;
                default:
                    break;
            }
            return false;
        });

    }
}