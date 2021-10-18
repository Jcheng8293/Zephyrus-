package com.example.zephyrus;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class TarotCard implements Serializable
{
    private final Drawable tarotCardImage;
    private final String[] captions; // the things that go in the bubbles below the card, idk what they're called
    private final String description;
    private int state; // 0 is regular, 1 is reverse

    public TarotCard(File imageFile, String[] captions, String description, int state)
    {
        if(description == null)
            throw new IllegalArgumentException("description cannot be null");
        if(captions == null)
            throw new IllegalArgumentException("captions cannot be null");
        if(captions.length != 3)
            throw new IllegalArgumentException("The number of captions must be equal to 3, not " + captions.length);
        if(imageFile == null)
            throw new IllegalArgumentException("imageFile cannot be null");
        if(!imageFile.exists())
            throw new IllegalArgumentException("imageFile '" + imageFile.getAbsolutePath() + "' does not exist");

        this.state = 0;
        this.captions = captions;
        this.description = description;
        this.tarotCardImage = Drawable.createFromPath(imageFile.getAbsolutePath());
    }

    public Drawable getImage()
    {
        return this.tarotCardImage;
    }

    public String[] getCaptions()
    {
        return this.captions;
    }

    public String getDescription()
    {
        return this.description;
    }

    public int getState() { return this.state; }

    public void setState(int state) { this.state = state; }


    public ArrayList<TarotCard> shuffle(ArrayList<TarotCard> deck) {
        ArrayList<TarotCard> temp = new ArrayList<>();

        for (int i = 38; i <= deck.size(); i++) {
            temp.add(deck.remove(i)); }

        int random = (int) Math.round(Math.random());

        if (random == 0) { reverse(deck); }
        else { reverse(temp); }

        ArrayList<TarotCard> newDeck = new ArrayList<>();

        for (int i = 0; deck.size() != 0 && temp.size() != 0; i++) {
            newDeck.add(deck.remove(i));
            newDeck.add(temp.remove(i));
        }

        return newDeck;
    }

    public void reverse(ArrayList<TarotCard> deck) {

        for (int i = 0; i <= deck.size(); i++) {
            if (deck.get(i).getState() == 0) {
                deck.get(i).setState(1);
            }
            else {
                deck.get(i).setState(0);
            }
        }
    }
}
