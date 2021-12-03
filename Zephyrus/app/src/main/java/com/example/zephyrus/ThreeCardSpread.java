package com.example.zephyrus;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import java.util.Arrays;

public class ThreeCardSpread extends AnimatedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.RIGHT, Gravity.RIGHT);
        setContentView(R.layout.activity_three_card_spread);

        Context context = getApplicationContext();
        TarotDeck cardIDs = new TarotDeck(context);
        cardIDs.shuffleCall();
        int[] readingCards = cardIDs.pickNumberOfCards(3);

        ImageView image1 = findViewById(R.id.cardImageView1);
        ImageView image2 = findViewById(R.id.cardImageView2);
        ImageView image3 = findViewById(R.id.cardImageView3);

        TarotCard card1 = TarotCard.readNewTarotCardByID(context, readingCards[0]);
        TarotCard card2 = TarotCard.readNewTarotCardByID(context, readingCards[1]);
        TarotCard card3 = TarotCard.readNewTarotCardByID(context, readingCards[2]);

        image1.setImageDrawable(card1.getImage());
        image2.setImageDrawable(card2.getImage());
        image3.setImageDrawable(card3.getImage());

        cardIDs.saveToJournal(context);
    }
}