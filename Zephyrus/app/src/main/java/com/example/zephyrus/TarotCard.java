package com.example.zephyrus;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class TarotCard extends Activity implements Serializable
{
    public static enum State {NORMAL, REVERSED};

    private String name;
    private final Drawable tarotCardImage;
    private final String[] captions; // the things that go in the bubbles below the card, idk what they're called
    private final String description;
    private State state;

    public TarotCard(File imageFile, String name, String[] captions, String description, State state)
    {
        this(getDrawableFromFile(imageFile), name, captions, description, state);
    }

    public TarotCard(Drawable image, String name, String[] captions, String description, State state)
    {
        if(name == null)
            throw new IllegalArgumentException("name cannot be null");
        if(description == null)
            throw new IllegalArgumentException("description cannot be null");
        if(captions == null)
            throw new IllegalArgumentException("captions cannot be null");
        if(captions.length != 3)
            throw new IllegalArgumentException("The number of captions must be equal to 3, not " + captions.length);

        this.name = name;
        this.state = state;
        this.captions = captions;
        this.description = description;
        this.tarotCardImage = image;
    }

    private static Drawable getDrawableFromFile(File imageFile)
    {
        if(imageFile == null)
            throw new IllegalArgumentException("imageFile cannot be null");
        if(!imageFile.exists())
            throw new IllegalArgumentException("imageFile '" + imageFile.getAbsolutePath() + "' does not exist");

        return Drawable.createFromPath(imageFile.getAbsolutePath());
    }

    public Drawable getImage() { return this.tarotCardImage; }

    public String[] getCaptions() { return this.captions; }

    public String getDescription() { return this.description; }

    public State getState() { return this.state; }

    public void setState(State state)
    {
        this.state = state;
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
