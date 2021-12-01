package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpreadsActivity extends AnimatedActivity {

    int[] theDeck = new int[78];

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.LEFT, Gravity.RIGHT);
        setContentView(R.layout.activity_spreads);

        /****
         * Do when page loads
         ****/
        Context context = getApplicationContext();
        initialize(context);

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
                default:
                    break;
            }
            return false;
        });
    }

    /***
     * In class methods for initializing the theDeck int array variable
     */

    // Initializes the deck
    private void initialize(Context context) {
        File filePath = context.getFilesDir();
        String fileName = "deck.txt";
        File deckFile = new File(filePath, fileName);

        // First time running the app
        if (!deckFile.canWrite()) {
            firstTime(deckFile);
        }

        // Has ran the app once
        else {
            readFile(deckFile);
        }

        // Checks for duplicate cards
        checkCards();
    }

    // In theory, this should never fail, but if it does, shit...
    private void checkCards() {
        for (int i = 0; i < theDeck.length; i++) {

            // For odd (NORMAL) cards
            if (theDeck[i] % 2 == 1) {
                for (int j = 0; j < theDeck.length; j++) {

                    // Checks for REVERSED versions of the same card and the same exact card
                    if (theDeck[i] + 1 == theDeck[j] || (theDeck[i] == theDeck[j] && i != j)) {
                        doNotCallMe();
                    }
                }
            }
            else {
                for (int j = 0; j < theDeck.length; j++) {

                    // Checks for NORMAL versions of the same card and the same exact card
                    if (theDeck[i] - 1 == theDeck[j] || (theDeck[i] == theDeck[j] && i != j)) {
                        doNotCallMe();
                    }
                }
            }
        }
    }

    // If something goes wrong, this will be called and render the buttons useless
    // THIS SHOULD NEVER BE CALLED
    private void doNotCallMe() {
        Button button = findViewById(R.id.past_present_future_button);
        button.setText("Something went wrong");
    }

    // Reads "deck.txt" and initializes the cardIDs array
    private void readFile(File deckFile) {
        try {
            int length = (int) deckFile.length();
            byte[] bytes = new byte[length];

            FileInputStream fis = new FileInputStream(deckFile);
            fis.read(bytes);
            String content = new String(bytes);
            stringToDeck(content);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create and save the base deck
    // This Should only run once, ever
    private void firstTime(File deckFile) {
        defaultDeck();
        try {
            FileOutputStream fos = new FileOutputStream(deckFile);
            String string = deckToString();
            fos.write(string.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create the base deck of cardIDs
    private void defaultDeck() {
        for (int i = 0; i < theDeck.length; i++) {
            theDeck[i] = i * 2 + 1;
        }
    }

    // Convert String to Array
    private void stringToDeck(String content) {
        String temp[] = content.split(" ");
        for (int i = 0; i < 78; i++) {
            theDeck[i] = (int) Integer.parseInt(temp[i].trim());
        }
    }

    // Convert Array to String
    // Yes, there is a built-in method for it, but this somewhat makes it more
    // clean in my opinion at least.
    private String deckToString() {
        StringBuilder output = new StringBuilder();
        for (int j : theDeck) {
            output.append(j).append(" ");
        }
        return output.toString();
    }

    /*****
     * Goes to Three Card Spreads Activity
     ****/
    public void toThreeCardSpread(View v) {
            Intent intent = new Intent(SpreadsActivity.this, ThreeCardSpread.class);
            intent.putExtra("deck", theDeck);
            startActivity(intent);
    }
}