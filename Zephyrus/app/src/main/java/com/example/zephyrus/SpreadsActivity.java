package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SpreadsActivity extends AppCompatActivity {

    int[] deck = new int[78];

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
    }

    /*****
     * Three Card Spreads
     ****/
    public void toThreeCardSpread(View v) throws IOException {
            deck = initialize();
            Intent intent = new Intent(SpreadsActivity.this, ThreeCardSpread.class);
            intent.putExtra("deck", deck);
            startActivity(intent);
    }

    private int[] initialize() throws IOException {
        Context appContext = getApplicationContext();
        File applicationFilesDir = appContext.getFilesDir();
        String filename = "deck.txt";
        FileInputStream fIn = new FileInputStream(new File(applicationFilesDir, filename));
        BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
        File deckFile = new File(applicationFilesDir, filename);
        int[] deck = new int[78];

        // Initializes deck via File
        if (!reader.readLine().equals("")) {

            for (int i = 0; i < deck.length; i++) {
                try {
                    deck[i] = Integer.parseInt(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Initializes deck for the first time
        else {
            FileOutputStream fOut = new FileOutputStream(deckFile);
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(fOut));

            for (int i = 0; i < TarotCard.NUM_TAROT_CARDS; i++) {
                write.write(i);
                deck[i] = i * 2;
            }
        }
        return deck;
    }

}