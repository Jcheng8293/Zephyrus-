package com.example.zephyrus;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CardListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.cardlist_navigation);

        navigation.setSelectedItemId(R.id.cardList_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(CardListActivity.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(CardListActivity.this, SpreadsActivity.class);
                    startActivity(b);
                    break;
                case R.id.cardList_nav:
                    break;
                case R.id.journal_nav:
                    Intent c = new Intent(CardListActivity.this, JournalActivity.class);
                    startActivity(c);
                    break;
                default:
                    break;
            }
            return false;
        });

        /***
         * Dynamically add images
         ***/

        try {
            int imageNum = 1;
            final String string = "SampleTarotCards/card";
            final String fileType = ".png";
            Drawable image;
            for (int i = R.id.B00; i <= R.id.B77; i++) {
                Button button = findViewById(i);
                image = Drawable.createFromStream(getAssets().open(string + imageNum + fileType), null);
                button.setForeground(image);
                imageNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Reversed Dem Cards
         */
        Button button = findViewById(R.id.reverseMe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = R.id.B00; i <= R.id.B77; i++) {
                    Button b = findViewById(i);
                    if (b.getRotation() == 0) {
                        b.setText("REVERSED");
                        b.setRotation(180);
                    }
                    else {
                        b.setText("NORMAL");
                        b.setRotation(0);
                    }
                }
            }
        });
    }

    public void cardButtonIsClicked(View v)
    {
        Intent cardFactsIntent = new Intent(this, CardFacts.class);
        int cardID = v.getId() - R.id.B00; // assumes all the IDs are sequential
        Button button = findViewById(R.id.B00);
        if (button.getRotation() == 180) {
            cardFactsIntent.putExtra("State", TarotCard.State.REVERSED);
        }
        else {
            cardFactsIntent.putExtra("State", TarotCard.State.NORMAL);
        }
        cardFactsIntent.putExtra("TarotCardID", cardID);
        startActivity(cardFactsIntent);
    }
}