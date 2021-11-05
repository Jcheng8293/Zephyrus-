package com.example.zephyrus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ThreeCardSpread extends AppCompatActivity {

    int[] deck = (int[]) getIntent().getSerializableExtra("deck");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_card_spread);

    }
}