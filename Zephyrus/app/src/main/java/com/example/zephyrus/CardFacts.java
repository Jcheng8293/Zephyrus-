package com.example.zephyrus;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CardFacts extends AnimatedActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.RIGHT, Gravity.RIGHT);
        setContentView(R.layout.card_facts);

        int tarotCardID = (int) getIntent().getSerializableExtra("TarotCardID");
        // TarotCard.State state = (TarotCard.State) getIntent().getSerializableExtra("State");
        TarotCard card = null;
        try {
            card = TarotCard.readNewTarotCardByID(getApplicationContext(), tarotCardID);
        } catch (Exception e) {
        }

        //TextView topMeaningTextView = findViewById(R.id.topMeaningTextView);
        //TextView centerMeaningTextView = findViewById(R.id.centerMeaningTextView);
        //TextView bottomMeaningTextView = findViewById(R.id.bottomMeaningTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView tarotCardImageView = findViewById(R.id.tarotCardImageView);

        String[] captions = card.getCaptions();
        String[] description = card.getDescription();
        /*topMeaningTextView.setText(captions[0]);
        centerMeaningTextView.setText(captions[1]);
        bottomMeaningTextView.setText(captions[2]);*/
        if (TarotCard.State.NORMAL == card.getState()) {
            descriptionTextView.setText(description[0]);
        } else {
            descriptionTextView.setText(description[1]);
        }

        /*
        if (TarotCard.State.REVERSED == state) {
            tarotCardImageView.setRotation(180);
        }
        */
        tarotCardImageView.setImageDrawable(card.getImage());

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
                default:
                    break;
            }
            return false;
        });
        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplication().getBaseContext(), "B1", Toast.LENGTH_SHORT).show();
                TextView txt_change = findViewById(R.id.descriptionTextView);
                txt_change.setText(captions[0]);
            }
        });
        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplication().getBaseContext(), "B2", Toast.LENGTH_SHORT).show();
                TextView txt_change = findViewById(R.id.descriptionTextView);
                txt_change.setText(captions[1]);
            }
        });
        Button b3 = findViewById(R.id.buttonDescription);
        TarotCard finalCard = card;
        b3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplication().getBaseContext(), "Description", Toast.LENGTH_SHORT).show();
                TextView txt_change = findViewById(R.id.descriptionTextView);
                if (TarotCard.State.NORMAL == finalCard.getState()) {
                    txt_change.setText(description[0]);
                } else {
                    txt_change.setText(description[1]);
                }
            }
        });
        Button b4 = findViewById(R.id.button3);
        b4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplication().getBaseContext(), "B3", Toast.LENGTH_SHORT).show();
                TextView txt_change = findViewById(R.id.descriptionTextView);
                txt_change.setText(captions[2]);
            }
        });
    }
}