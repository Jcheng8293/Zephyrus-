package com.example.zephyrus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AnimatedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.RIGHT, Gravity.LEFT);
        setContentView(R.layout.activity_settings);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.settings_navigation);
        navigation.setSelectedItemId(R.id.settings_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Bundle animationBundle = getAnimationOptions(SettingsActivity.this).toBundle();

            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(SettingsActivity.this, CalendarActivity.class);
                    startActivity(a, animationBundle);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(SettingsActivity.this, SpreadsActivity.class);
                    startActivity(b, animationBundle);
                    break;
                case R.id.cardList_nav:
                    Intent c = new Intent(SettingsActivity.this, CardListActivity.class);
                    startActivity(c, animationBundle);
                    break;
                case R.id.journal_nav:
                    Intent d = new Intent(SettingsActivity.this, JournalActivity.class);
                    startActivity(d, animationBundle);
                    break;
                case R.id.settings_nav:
                    break;
                default:
                    break;
            }
            return false;
        });
    }
}
