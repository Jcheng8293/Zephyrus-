package com.example.zephyrus;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardListActivity extends AppCompatActivity {

    private TarotCard.State displayCardsInState = TarotCard.State.NORMAL;
    private TarotCard[] tarotCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.cardlist_navigation);

        navigation.setSelectedItemId(R.id.cardList_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    Intent a = new Intent(CardListActivity.this, CalendarActivity.class);
                    startActivity(a);
                    break;
                case R.id.spreads_nav:
                    Intent b = new Intent(CardListActivity.this, SpreadsActivity.class);
                    startActivity(b);
                    break;
                case R.id.cardList_nav:
                    break;
                case R.id.journal_nav:
                    Intent c = new Intent(CardListActivity.this, JournalActivity.class);
                    startActivity(c);
                    break;
                default:
                    break;
            }
            return false;
        });
        /***
         * Dynamically add images
         ***/
        this.tarotCards = readAllTarotCards();
        setCardButtonsImages();
        setReverseButtonTitle();
        /*
        try {
            int imageNum = 1;
            final String string = "SampleTarotCards/card";
            final String fileType = ".png";
            Drawable image;
            for (int i = R.id.B00; i <= R.id.B77; i++) {
                Button button = findViewById(i);
                image = Drawable.createFromStream(getAssets().open(string + imageNum + fileType), null);
                button.setForeground(image);
                imageNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        /*
         * Reversed Dem Cards
         */
        Button button = findViewById(R.id.reverseMe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardListActivity.this.displayCardsInState = TarotCard.flipState(CardListActivity.this.displayCardsInState);
                setCardButtonsImages();
                setReverseButtonTitle();
                /*
                for (int i = R.id.B00; i <= R.id.B77; i++)
                {
                    Button b = findViewById(i);
                    if(isShowingReversedCards)
                    {
                        b.setText
                    }
                    if (b.getRotation() == 0) {
                        b.setText("REVERSED");
                        b.setRotation(180);
                    }
                    else {
                        b.setText("NORMAL");
                        b.setRotation(0);
                    }
                }
                 */
            }
        });
    }

    public void setReverseButtonTitle()
    {
        Button reverseButton = findViewById(R.id.reverseMe);
        if(this.displayCardsInState == TarotCard.State.REVERSED)
            reverseButton.setText("REVERSED");
        else if(this.displayCardsInState == TarotCard.State.NORMAL)
            reverseButton.setText("NORMAL");
    }

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
                while(tarotCards[currentCardIndex].getState() != this.displayCardsInState) {
                    currentCardIndex++;
                    if(tarotCards[currentCardIndex] == null)
                        System.out.println("null at index " + currentCardIndex);
                }
                b.setForeground(tarotCards[currentCardIndex].getImage());
            }
        }
        /*

        for(int i = R.id.B00; i <= R.id.B77; i++, currentCardIndex++)
        {
            Button b = findViewById(i);
            b.setForeground(tarotCards[currentCardIndex].getImage());
        }
         */
    }
    public int getCardIDFromViewID(int viewID)
    {
        int currentCardIndex = 0;
        for(int i = R.id.B00; i != viewID; i++, currentCardIndex++)
        {
            while(tarotCards[currentCardIndex].getState() != this.displayCardsInState)
                currentCardIndex++;
        }
        while(tarotCards[currentCardIndex].getState() != this.displayCardsInState)
            currentCardIndex++;
        return tarotCards[currentCardIndex].getCardID();
    }
    private TarotCard[] readAllTarotCards()
    {
        Context context = getApplication().getApplicationContext();
        TarotCard[] cards = new TarotCard[TarotCard.NUM_TAROT_CARDS];
        for (int cardID = 0; cardID < TarotCard.NUM_TAROT_CARDS; cardID++)
            cards[cardID] = TarotCard.readNewTarotCardByID(context, cardID);
        return cards;
    }
    public void cardButtonIsClicked(View v)
    {
        /*
        int cardID = v.getId() - R.id.B00; // assumes all the IDs are sequential
        Button button = findViewById(R.id.B00);
        if (button.getRotation() == 180) {
            cardFactsIntent.putExtra("State", TarotCard.State.REVERSED);
        }
        else {
            cardFactsIntent.putExtra("State", TarotCard.State.NORMAL);
        }
         */
        int cardID = getCardIDFromViewID(v.getId());
        Intent cardFactsIntent = new Intent(this, CardFacts.class);
        cardFactsIntent.putExtra("TarotCardID", cardID);
        startActivity(cardFactsIntent);
    }
}