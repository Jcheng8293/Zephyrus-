package com.example.zephyrus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;

public class JournalActivity extends AppCompatActivity {

    String[] journal = new String[0];
    TarotDeck deck = new TarotDeck();

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

        // Grabs past reading information
        try {
            journal = deck.readFromJournal(getApplicationContext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Grabs card names
        String[] cardNames = new String[journal.length/2];
        for (int i = 0; i < cardNames.length; i++) {
            int[] cardIDs = new int[3];
            cardNames[i] = getCardNames(journal, i);
        }

        GridLayout llMain = findViewById(R.id.journalLayout);
        for (int i = 0; i < journal.length/2; i++) {

            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setTextColor(Color.WHITE);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setBackgroundResource(R.drawable.bg_rounded);
            textView.setText(journal[i*2] + "\n" + cardNames[i]);
            llMain.addView(textView, llMain.getLayoutParams());

        }
    }

    private String getCardNames(String[] journal, int index) {
        String[] temp = journal[index*2+1].split(" ");
        int[] cardIDs = new int[temp.length];
        for (int i = 0; i < cardIDs.length; i++) {
            cardIDs[i] = (int) Integer.parseInt(temp[i].trim());
        }
        String output = TarotCard.readNewTarotCardById(getApplicationContext(), cardIDs[0]).getCardName() + "\n";
        if (cardIDs.length > 1) {
            output += TarotCard.readNewTarotCardById(getApplicationContext(), cardIDs[1]).getCardName() + "\n";
        }
        if (cardIDs.length > 2) {
            output += TarotCard.readNewTarotCardById(getApplicationContext(), cardIDs[2]).getCardName();
        }
        return output;
    }
}