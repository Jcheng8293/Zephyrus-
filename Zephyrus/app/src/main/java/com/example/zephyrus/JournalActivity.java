package com.example.zephyrus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class JournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.journal_navigation);

        navigation.setSelectedItemId(R.id.journal_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(JournalActivity.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(JournalActivity.this, SpreadsActivity.class);
                    startActivity(b);
                    break;
                case R.id.cardList_nav:
                    Intent c = new Intent(JournalActivity.this, CardListActivity.class);
                    startActivity(c);
                    break;
                case R.id.journal_nav:
                    break;
                default:
                    break;
            }
            return false;
        });
    }
}