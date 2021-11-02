package com.example.zephyrus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ThreeCardSpread extends AppCompatActivity {

    ArrayList<TarotCard> deck = new ArrayList<>();
    FileInputStream file;
    Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_card_spread);


        // Initialize Deck

        // Initialize Deck for the First Time
        try {
            InputStream input = getAssets().open("tarot_cards_spaced.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            FileOutputStream writer = context.openFileOutput("tarot_deck.txt", Context.MODE_PRIVATE);

             for (int i = 0; i < 78; i++) {
                 String name = reader.readLine() + "\n";
                 String[] captions = new String[6];

                 for (int j = 0; j < 6; j++) {
                     captions[j] = reader.readLine();
                 }

                 reader.readLine();
                 deck.add(new TarotCard((Drawable) null, name, captions, "Empty", TarotCard.State.NORMAL));
                 writer.write(name.getBytes());
                 writer.write("Normal\n".getBytes());

                 System.out.println(name);
             }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}