package com.example.zephyrus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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
    private final String[] description;
    private State state;
    public static final int NUM_TAROT_CARDS = 156;

    // 'context' should be the result of the call getApplicationContext()
    public static TarotCard readNewTarotCardByID(Context context, int cardID)
    {
        if(cardID < 0 || cardID >= NUM_TAROT_CARDS)
            throw new IllegalArgumentException("cardID must be [0, " + NUM_TAROT_CARDS +"), but was given " + cardID);

        InputStream in_stream = null;
        try {
            in_stream = context.getAssets().open("tarot_cards_info.txt");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

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
            cardName += "Reverse " + cardName;
            for(int j = 0; j < 3; j++)
                textScanner.nextLine();
        }
        String[] captions = new String[3];
        for(int k = 0; k < 3; k++)
            captions[k] = textScanner.nextLine();

        String imageFilename = "SampleTarotCards/card" + (cardID/2 + 1) + ".png";
        return new TarotCard(cardID, context, imageFilename, cardName, captions, new String[]{"There are no descriptions for now."}, cardState);
    }

    private TarotCard(int cardID, Context context, String imageFilePath, String name, String[] captions, String[] description, State state)
    {
        this(cardID, context, getDrawableFromFilePath(context, imageFilePath), name, captions, description, state);
    }

    private TarotCard(int cardID, Context context, Drawable image, String name, String[] captions, String[] description, State state)
    {
        if(description == null)
            throw new IllegalArgumentException("description cannot be null");

        if(description.length == 1)
            description = new String[]{description[0], description[0]}; // just now for debugging, copy normal and reverse descriptions

        if(captions == null)
            throw new IllegalArgumentException("captions cannot be null");
        if(captions.length != 3)
            throw new IllegalArgumentException("The number of captions must be equal to 3, not " + captions.length);

        this.cardID = cardID;
        this.name = name;
        this.state = state;
        this.captions = captions;
        this.description = description;

        if(state == State.REVERSED)
            this.tarotCardImage = rotateCardImage(context, image);
        else
            this.tarotCardImage = image;

    }

    private static Drawable rotateCardImage(Context context, Drawable image)
    {
        Bitmap bitmap = drawableToBitmap(image);
        Matrix rotationMatrix = new Matrix();
        rotationMatrix.postRotate(180);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotationMatrix, true);
        return new BitmapDrawable(context.getResources(), rotatedBitmap);
    }

    public TarotCard getReverseOfCard(Context context) throws Exception
    {
        int reverseCardsID = this.getCardID();
        if(this.state == State.NORMAL)
            reverseCardsID++;
        else
            reverseCardsID--;
        return TarotCard.readNewTarotCardByID(context, reverseCardsID);
    }

    private static Drawable getDrawableFromFilePath(Context context, String imageFilePath)
    {
        InputStream ims = null;
        try {
            ims = context.getAssets().open(imageFilePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        return Drawable.createFromStream(ims, null);
    }

    public Drawable getImage() { return this.tarotCardImage; }
    public String[] getCaptions() { return this.captions; }
    public String[] getDescription() { return this.description; }
    public State getState() { return this.state; }
    public int getCardID() { return this.cardID; }
    public String getCardName() { return this.name; }
    public void setState(State state) { this.state = state; }

    public static State flipState(State state)
    {
        if(state == State.NORMAL)
            return State.REVERSED;
        else
            return State.NORMAL;
    }

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

    private static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
