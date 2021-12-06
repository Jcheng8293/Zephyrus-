package com.example.zephyrus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

/** The TarotCard class contains all the required information about an individual tarot card. * */
public class TarotCard implements Serializable {
    /**
     * The state of the card, could be either NORMAL or REVERSED. *
     */
    public enum State {
        NORMAL,
        REVERSED
    }

    ;

    private final int cardId;
    private final String name;
    private final Drawable tarotCardImage;
    private final String[] captions; // the things that go in the bubbles below the card
    private final String description;
    private State state;
    public static final int NUM_TAROT_CARDS = 156;

    /**
     * Returns a TarotCard object with a given ID. 'context' should be the result of the call
     * getApplicationContext(). *
     */
    public static TarotCard readNewTarotCardById(Context context, int cardId) {
        if (cardId < 0 || cardId >= NUM_TAROT_CARDS) {
            throw new IllegalArgumentException(
                    "cardId must be [0, " + NUM_TAROT_CARDS + "), but was given " + cardId);
        }

        InputStream inStream = null;
        try {
            inStream = context.getAssets().open("tarot_cards_info.txt");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Scanner textScanner = new Scanner(inStream);
        int cardPairsToSkip = cardId / 2;
        for (int i = 0; i < cardPairsToSkip; i++) {
            for (int j = 0; j < 7; j++) {
                textScanner.nextLine();
            }
        }
        String cardName = textScanner.nextLine();
        State cardState = cardId % 2 == 0 ? State.NORMAL : State.REVERSED;
        if (cardState == State.REVERSED) {
            cardName = cardName + " Reversed";
            for (int j = 0; j < 3; j++) textScanner.nextLine();
        }
        String[] captions = new String[3];
        for (int k = 0; k < 3; k++) captions[k] = textScanner.nextLine();

        InputStream descriptionStream = null;
        try {
            descriptionStream = context.getAssets().open("tarot_descriptions.txt");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Scanner descriptionScanner = new Scanner(descriptionStream);
        for (int i = 0; i < cardId; i++) {
            descriptionScanner.nextLine();
            descriptionScanner.nextLine();
        }
        descriptionScanner.nextLine();
        String description = descriptionScanner.nextLine();

        String imageFilename = "SampleTarotCards/" + (cardId / 2 + 1) + ".jpg";
        return new TarotCard(
                cardId, context, imageFilename, cardName, captions, description, cardState);
    }

    private TarotCard(
            int cardId,
            Context context,
            String imageFilePath,
            String name,
            String[] captions,
            String description,
            State state) {
        this(
                cardId,
                context,
                getDrawableFromFilePath(context, imageFilePath),
                name,
                captions,
                description,
                state);
    }

    private TarotCard(
            int cardId,
            Context context,
            Drawable image,
            String name,
            String[] captions,
            String description,
            State state) {
        if (description == null) throw new IllegalArgumentException("description cannot be null");

        if (captions == null) throw new IllegalArgumentException("captions cannot be null");
        if (captions.length != 3)
            throw new IllegalArgumentException(
                    "The number of captions must be equal to 3, not " + captions.length);

        this.cardId = cardId;
        this.name = name;
        this.state = state;
        this.captions = captions;
        this.description = description;

        if (this.cardId % 2 == 0) this.tarotCardImage = rotateCardImage(context, image);
        else this.tarotCardImage = image;
    }

    private static Drawable rotateCardImage(Context context, Drawable image) {
        Bitmap bitmap = drawableToBitmap(image);
        Matrix rotationMatrix = new Matrix();
        rotationMatrix.postRotate(180);
        Bitmap rotatedBitmap =
                Bitmap.createBitmap(
                        bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotationMatrix, true);
        return new BitmapDrawable(context.getResources(), rotatedBitmap);
    }

    public TarotCard getReverseOfCard(Context context) throws Exception {
        int reverseCardsID = this.getCardID();
        if (this.state == State.NORMAL) reverseCardsID++;
        else reverseCardsID--;
        return TarotCard.readNewTarotCardById(context, reverseCardsID);
    }

    private static Drawable getDrawableFromFilePath(Context context, String imageFilePath) {
        InputStream ims = null;
        try {
            ims = context.getAssets().open(imageFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return Drawable.createFromStream(ims, null);
    }

    public Drawable getImage() {
        return this.tarotCardImage;
    }

    public String[] getCaptions() {
        return this.captions;
    }

    public String getDescription() {
        return this.description;
    }

    public State getState() {
        return this.state;
    }

    public int getCardID() {
        return this.cardId;
    }

    public String getCardName() {
        return this.name;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static State flipState(State state) {
        if (state == State.NORMAL) return State.REVERSED;
        else return State.NORMAL;
    }

    public boolean equals(Object o) {
        if (o instanceof TarotCard) {
            return this.cardId == ((TarotCard) o).cardId;
        }
        return false;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap =
                    Bitmap.createBitmap(
                            1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap =
                    Bitmap.createBitmap(
                            drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}

