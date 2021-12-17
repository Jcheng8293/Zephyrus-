package com.example.zephyrus;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wajahatkarim3.easyflipview.EasyFlipView;

public class SingleCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_card);

        Context context = getApplicationContext();
        TarotDeck cardIDs = new TarotDeck(context, "deck.txt");
        int[] readingCards = cardIDs.pickNumberOfCards(1);

        ImageView image1 = findViewById(R.id.cardImageView4);

        TarotCard tarotCard1 = TarotCard.readNewTarotCardById(context, readingCards[0]);

        TextView text1 = findViewById((R.id.cardName4));

        image1.setImageDrawable(tarotCard1.getImage());

        EasyFlipView view1 = findViewById(R.id.card4);

        view1.setOnFlipListener((easyFlipView, newCurrentSide) -> text1.setText("  " + tarotCard1.getCardName() + "  "));

        cardIDs.shuffleCall();
        cardIDs.saveToJournal(context, "Yes or No", readingCards);
    }
}