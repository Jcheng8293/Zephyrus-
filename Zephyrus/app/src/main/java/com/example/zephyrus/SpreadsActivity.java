package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpreadsActivity extends AppCompatActivity {


    private BottomNavigationView navigation;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spreads);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.mobile_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.calendarActivity:
                    Intent a = new Intent(SpreadsActivity.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreadsActivity:
                    break;
                case R.id.cardListActivity:
                    Intent b = new Intent(SpreadsActivity.this, CardListActivity.class);
                    startActivity(b);
                    break;
                case R.id.journalActivity:
                    Intent c = new Intent(SpreadsActivity.this, JournalActivity.class);
                    startActivity(c);
                    break;
            }
            return false;
        }});
    }
}