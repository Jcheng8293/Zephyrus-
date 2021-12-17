package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

public class ThreeCardSpread extends AnimatedActivity {

    int[] readingCards;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.RIGHT, Gravity.RIGHT);
        setContentView(R.layout.activity_three_card_spread);

        Context context = getApplicationContext();
        TarotDeck cardIDs = new TarotDeck(context, "deck.txt");
        readingCards = cardIDs.pickNumberOfCards(3);

        ImageView image1 = findViewById(R.id.cardImageView1);
        ImageView image2 = findViewById(R.id.cardImageView2);
        ImageView image3 = findViewById(R.id.cardImageView3);

        TarotCard tarotCard1 = TarotCard.readNewTarotCardById(context, readingCards[0]);
        TarotCard tarotCard2 = TarotCard.readNewTarotCardById(context, readingCards[1]);
        TarotCard tarotCard3 = TarotCard.readNewTarotCardById(context, readingCards[2]);

        TextView text1 = findViewById((R.id.cardName1));
        TextView text2 = findViewById((R.id.cardName2));
        TextView text3 = findViewById((R.id.cardName3));

        image1.setImageDrawable(tarotCard1.getImage());
        image2.setImageDrawable(tarotCard2.getImage());
        image3.setImageDrawable(tarotCard3.getImage());

        EasyFlipView view1 = findViewById(R.id.card1);
        EasyFlipView view2 = findViewById(R.id.card2);
        EasyFlipView view3 = findViewById(R.id.card3);

        view1.setOnFlipListener((easyFlipView, newCurrentSide) -> text1.setText(tarotCard1.getCardName()));

        view2.setOnFlipListener((easyFlipView, newCurrentSide) -> text2.setText(tarotCard2.getCardName()));

        view3.setOnFlipListener((easyFlipView, newCurrentSide) -> text3.setText(tarotCard3.getCardName()));

        cardIDs.shuffleCall();
        cardIDs.saveToJournal(context, "Past Present Future", readingCards);
    }
}