package com.example.zephyrus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class ThreeCardSpread extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_card_spread);

        TextView textViews = findViewById((R.id.textView3));
        Button buttons = findViewById(R.id.button23);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = "";
                try {
                    InputStream inputSteam = (getAssets().open("tarot_deck.txt"));
                    int size = inputSteam.available();
                    byte[] buffer = new byte[size];
                    inputSteam.read(buffer);
                    string = new String(buffer);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                textViews.setText(string); }
        }
        );
    }
}