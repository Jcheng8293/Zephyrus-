package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpreadsActivity extends AnimatedActivity {


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.LEFT, Gravity.RIGHT);
        setContentView(R.layout.activity_spreads);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.spreads_navigation);
        navigation.setSelectedItemId(R.id.spreads_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Bundle animationBundle = getAnimationOptions(SpreadsActivity.this).toBundle();
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(SpreadsActivity.this, CalendarActivity.class);
                    startActivity(a, animationBundle);
                    break;
                case R.id.spreads_nav:
                    break;
                case R.id.cardList_nav:
                    Intent b = new Intent(SpreadsActivity.this, CardListActivity.class);
                    startActivity(b, animationBundle);
                    break;
                case R.id.journal_nav:
                    Intent c = new Intent(SpreadsActivity.this, JournalActivity.class);
                    startActivity(c, animationBundle);
                    break;
                case R.id.settings_nav:
                    Intent d = new Intent(SpreadsActivity.this, SettingsActivity.class);
                    startActivity(d, animationBundle);
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    /*****
     * Goes to Three Card Spreads Activity
     ****/
    public void toThreeCardSpread(View v) {
            Intent intent = new Intent(SpreadsActivity.this, ThreeCardSpread.class);
            startActivity(intent);
    }
    /*****
     * Goes to Single Card Spreads Activity
     ****/
    public void toSingleCard(View v) {
        Intent intent = new Intent(SpreadsActivity.this, SingleCard.class);
        startActivity(intent);
    }
}