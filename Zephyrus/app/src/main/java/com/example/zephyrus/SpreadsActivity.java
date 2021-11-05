package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpreadsActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spreads);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.spreads_navigation);

        navigation.setSelectedItemId(R.id.spreads_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(SpreadsActivity.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreads_nav:
                    break;
                case R.id.cardList_nav:
                    Intent b = new Intent(SpreadsActivity.this, CardListActivity.class);
                    startActivity(b);
                    break;
                case R.id.journal_nav:
                    Intent c = new Intent(SpreadsActivity.this, JournalActivity.class);
                    startActivity(c);
                    break;
                default:
                    break;
            }
            return false;
        });

        /*****
         * Three Card Spreads
         ****/
        Button button1 = findViewById(R.id.past_present_future_button);
        Button button2 = findViewById(R.id.filler_button);

        // Past Present Future
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpreadsActivity.this, ThreeCardSpread.class);
                // intent.putExtra("deck", deck);
                startActivity(intent);
            }
        }
        );

        // Unknown
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpreadsActivity.this, ThreeCardSpread.class);
                // intent,putExtra("deck", deck);
                startActivity(intent);
            }
        }
        );

    }

}