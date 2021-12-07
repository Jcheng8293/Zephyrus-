package com.example.zephyrus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardListActivity extends AnimatedActivity {

    private TarotCard[] tarotCards;

    private int displayCardTypeIndex = 0;
    private static final TarotCardReaderConfig.TarotCardTypes[] displayOrder =
            {TarotCardReaderConfig.TarotCardTypes.NORMAL,
                    TarotCardReaderConfig.TarotCardTypes.REVERSED,
                    TarotCardReaderConfig.TarotCardTypes.MAJOR_ARCANA,
                    TarotCardReaderConfig.TarotCardTypes.WANDS,
                    TarotCardReaderConfig.TarotCardTypes.CUPS,
                    TarotCardReaderConfig.TarotCardTypes.SWORDS,
                    TarotCardReaderConfig.TarotCardTypes.PENTACLES};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setActivityTransitionAnimationDirection(Gravity.RIGHT, Gravity.LEFT);
        setContentView(R.layout.activity_card_list);


        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.cardlist_navigation);
        navigation.setSelectedItemId(R.id.cardList_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Bundle animationBundle = getAnimationOptions(CardListActivity.this).toBundle();
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(CardListActivity.this, CalendarActivity.class);
                    startActivity(a, animationBundle);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(CardListActivity.this, SpreadsActivity.class);
                    startActivity(b, animationBundle);
                    break;
                case R.id.cardList_nav:
                    break;
                case R.id.journal_nav:
                    Intent c = new Intent(CardListActivity.this, JournalActivity.class);
                    startActivity(c, animationBundle);
                    break;
                default:
                    break;
            }
            return false;
        });
        /***
         * Dynamically add images
         ***/

        displayCardType();

        Button button = findViewById(R.id.reverseMe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCardTypeIndex = (displayCardTypeIndex + 1) % displayOrder.length;
                displayCardType();
            }
        });
    }

    private void displayCardType()
    {
        setButtonTitle();
        TarotCardReaderConfig.TarotCardTypes currentCardType = getCurrentCardType();
        tarotCards = TarotCard.getCardsByType(getApplicationContext(), currentCardType);
        setCardButtonsImages();
    }

    public TarotCardReaderConfig.TarotCardTypes getCurrentCardType()
    {
        return displayOrder[displayCardTypeIndex];
    }

    public void setButtonTitle()
    {
        Button filterButton = findViewById(R.id.reverseMe);
        TarotCardReaderConfig.TarotCardTypes currentCardType = getCurrentCardType();
        filterButton.setText(TarotCardReaderConfig.getNameOfCardType(currentCardType));
    }

    /*
    public void setReverseButtonTitle()
    {
        Button reverseButton = findViewById(R.id.reverseMe);
        if(this.displayCardsInState == TarotCard.State.REVERSED)
            reverseButton.setText("REVERSED");
        else if(this.displayCardsInState == TarotCard.State.NORMAL)
            reverseButton.setText("NORMAL");
    }
    */

    public void setCardButtonsImages()
    {
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        int currentCardIndex = 0;
        for (int i = 0; i < gridLayout.getChildCount(); i++, currentCardIndex++)
        {
            View v = gridLayout.getChildAt(i);
            if(v instanceof Button)
            {
                Button b = (Button) v;
                String buttonIDStr = b.getResources().getResourceName(b.getId());
                Pattern p = Pattern.compile("/B\\d\\d$");
                Matcher m = p.matcher(buttonIDStr);
                if(!m.find()) {
                    continue;
                }

                if(currentCardIndex >= tarotCards.length)
                    b.setVisibility(View.GONE);
                else
                    b.setForeground(tarotCards[currentCardIndex].getImage());
            }
        }

    }
    public int getCardIDFromViewID(int viewID)
    {
        int cardIndex = viewID - R.id.B00;
        return tarotCards[cardIndex].getCardID();
    }
    /*
    private TarotCard[] readAllTarotCards()
    {
        Context context = getApplicationContext();
        TarotCard[] cards = new TarotCard[TarotCard.NUM_TAROT_CARDS];
        for (int cardID = 0; cardID < TarotCard.NUM_TAROT_CARDS; cardID++)
            cards[cardID] = TarotCard.readNewTarotCardById(context, cardID);
        return cards;
    }
     */
    public void cardButtonIsClicked(View v)
    {
        int cardID = getCardIDFromViewID(v.getId());
        Intent cardFactsIntent = new Intent(this, CardFacts.class);
        cardFactsIntent.putExtra("TarotCardID", cardID);
        startActivity(cardFactsIntent);
    }
}