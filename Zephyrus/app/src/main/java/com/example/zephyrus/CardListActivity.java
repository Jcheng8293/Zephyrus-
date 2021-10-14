package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CardListActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        /*****
         * Bottom Navigation Bar Code
         *****/
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

    }
}