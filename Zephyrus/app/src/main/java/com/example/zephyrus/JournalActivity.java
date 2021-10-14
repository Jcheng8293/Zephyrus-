package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class JournalActivity extends AppCompatActivity {


    private BottomNavigationView navigation;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.mobile_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.calendarActivity:
                    Intent a = new Intent(JournalActivity.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreadsActivity:
                    Intent b = new Intent(JournalActivity.this, SpreadsActivity.class);
                    startActivity(b);
                    break;
                case R.id.cardListActivity:
                    Intent c = new Intent(JournalActivity.this, CardListActivity.class);
                    startActivity(c);
                    break;
                case R.id.journalActivity:
                    break;
            }
            return false;
        }});
    }
}