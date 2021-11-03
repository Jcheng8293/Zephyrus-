package com.example.zephyrus;

import android.content.Intent;
import android.os.Bundle;
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
         * Dynamically name buttons
         ***/


        try {
            InputStream file = getAssets().open("tarot_cards_spaced.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            for (int i = R.id.B00; i <= R.id.B77; i++) {
                Button button = findViewById(i);

                // Unknown reason... leave it alone
                if (i == R.id.B73) {
                    reader.readLine();
                }
                String line = reader.readLine();
                button.setText(line);
                for (int j = 0; j < 7; j++) {
                    reader.readLine();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}