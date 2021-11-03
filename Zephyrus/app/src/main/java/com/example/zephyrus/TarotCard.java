package com.example.zephyrus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TarotCard implements Serializable
{
    public enum State {NORMAL, REVERSED}
    private final int cardID;
    private final String name;
    private final Drawable tarotCardImage;
    private final String[] captions; // the things that go in the bubbles below the card, idk what they're called
    private final String description;
    private State state;
    private static final int NUM_TAROT_CARDS = 78;

    // 'context' should be the result of the call getApplicationContext()
    public static TarotCard readNewTarotCardByID(Context context, int cardID) throws Exception
    {
        if(cardID < 0 || cardID >= NUM_TAROT_CARDS)
            throw new IllegalArgumentException("cardID must be [0, " + NUM_TAROT_CARDS +"), but was given " + cardID);

        InputStream in_stream = context.getAssets().open("tarot_cards_info.txt");

        Scanner textScanner = new Scanner(in_stream);
        int cardPairsToSkip = cardID / 2;
        for(int i = 0; i < cardPairsToSkip; i++)
        {
            for(int j = 0; j < 7; j++)
                textScanner.nextLine();
        }
        String cardName = textScanner.nextLine();
        State cardState = cardID % 2 == 0 ? State.NORMAL : State.REVERSED;
        if(cardState == State.REVERSED)
        {
            cardName += " (Reversed)";
            for(int j = 0; j < 3; j++)
                textScanner.nextLine();
        }
        String[] captions =  new String[3];
        for(int k = 0; k < 3; k++)
            captions[k] = textScanner.nextLine();

        String imageFilename = "SampleTarotCards/card" + (cardID + 1) + ".png";
        // Toast.makeText(context, "Card #" + cardID + " = " + cardName, Toast.LENGTH_LONG).show();
        return new TarotCard(cardID, context, imageFilename, cardName, captions, "There are no descriptions for now.", cardState);
    }

    private TarotCard(int cardID, Context context, String imageFilePath, String name, String[] captions, String description, State state) throws Exception
    {
        this(cardID, getDrawableFromFilePath(context, imageFilePath), name, captions, description, state);
    }

    private TarotCard(int cardID, Drawable image, String name, String[] captions, String description, State state)
    {
        if(description == null)
            throw new IllegalArgumentException("description cannot be null");
        if(captions == null)
            throw new IllegalArgumentException("captions cannot be null");
        if(captions.length != 3)
            throw new IllegalArgumentException("The number of captions must be equal to 3, not " + captions.length);

        this.cardID = cardID;
        this.name = name;
        this.state = state;
        this.captions = captions;
        this.description = description;
        this.tarotCardImage = image;
    }

    private static Drawable getDrawableFromFilePath(Context context, String imageFilePath) throws Exception
    {
        InputStream ims = context.getAssets().open(imageFilePath);
        return Drawable.createFromStream(ims, null);
    }

    public Drawable getImage() { return this.tarotCardImage; }
    public String[] getCaptions() { return this.captions; }
    public String getDescription() { return this.description; }
    public State getState() { return this.state; }
    public int getCardID() { return this.cardID; }
    public String getCardName() { return this.name; }
    public void setState(State state) { this.state = state; }

    public boolean equals(Object o)
    {
        if(o instanceof TarotCard)
        {
            return this.cardID == ((TarotCard)o).cardID;
        }
        return false;
    }

    public static ArrayList<TarotCard> shuffle(ArrayList<TarotCard> deck) {
        ArrayList<TarotCard> temp = new ArrayList<>();

        for (int i = 39; i < deck.size(); ) {
            temp.add(deck.remove(i));
        }

        int random = (int) Math.round(Math.random());
        if (random == 0) { reverse(deck); }
        else { reverse(temp); }

        ArrayList<TarotCard> newDeck = new ArrayList<>();

        while (deck.size() != 0 && temp.size() != 0) {
            newDeck.add(deck.remove(0));
            newDeck.add(temp.remove(0));
        }
        return newDeck;
    }

    public static void reverse(ArrayList<TarotCard> deck)
    {
        for (int i = 0; i < deck.size(); i++) {
            TarotCard card = deck.get(i);
            if(card.state == State.NORMAL)
                card.state = State.REVERSED;
            else
                card.state = State.NORMAL;
        }
    }
}
