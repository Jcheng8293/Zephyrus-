package com.example.zephyrus;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;

public class TarotCard {
    private Drawable tarotCardImage;
    private String[] captions; // the things that go in the bubbles below the card, idk what they're called
    private String description;

    public TarotCard(File imageFile, String[] captions, String description)
    {
        if(description == null)
            throw new IllegalArgumentException("description cannot be null");
        if(captions == null)
            throw new IllegalArgumentException("captions cannot be null");
        if(captions.size() == 0)
            throw new IllegalArgumentException("The number of captions must be > 0");
        if(captions.length != 3)
            throw new IllegalArgumentException("The number of captions must be equal to 3, not " + captions.length);
        if(imageFile == null)
            throw new IllegalArgumentException("imageFile cannot be null");
        if(!imageFile.exists())
            throw new IllegalArgumentException("imageFile '" + imageFile.getAbsolutePath() + "' does not exist");

        this.captions = captions;
        this.description = description;
        this.tarotCardImage = Drawable.createFromPath(imageFile.getAbsolutePath());
    }

    public Drawable getImage()
    {
        return this.tarotCardImage;
    }

    public ArrayList<String> getCaptions()
    public String[] getCaptions()
    {
        return this.captions;
    }

    public String getDescription()
    {
        return this.description;
    }
}
