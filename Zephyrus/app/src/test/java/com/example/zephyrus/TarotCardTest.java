package com.example.zephyrus;
import static org.junit.Assert.assertEquals;

import android.graphics.drawable.Drawable;

import org.junit.Test;

import java.util.ArrayList;

public class TarotCardTest
{
    @Test
    public void testReverse()
    {
        TarotCard card1 = new TarotCard((Drawable) null, new String[]{"caption1", "caption2", "caption3"}, "Card1", TarotCard.State.NORMAL);
        TarotCard card2 = new TarotCard((Drawable) null, new String[]{"caption1", "caption2", "caption3"}, "Card1", TarotCard.State.REVERSED);
        TarotCard card3 = new TarotCard((Drawable) null, new String[]{"caption1", "caption2", "caption3"}, "Card1", TarotCard.State.NORMAL);
        TarotCard card4 = new TarotCard((Drawable) null, new String[]{"caption1", "caption2", "caption3"}, "Card1", TarotCard.State.REVERSED);
        ArrayList<TarotCard> deck = new ArrayList<>();
        deck.add(card1);
        deck.add(card2);
        deck.add(card3);
        deck.add(card4);
        TarotCard.reverse(deck);
        assertEquals(TarotCard.State.REVERSED, deck.get(0).getState());
        assertEquals(TarotCard.State.NORMAL, deck.get(1).getState());
        assertEquals(TarotCard.State.REVERSED, deck.get(2).getState());
        assertEquals(TarotCard.State.NORMAL, deck.get(3).getState());
    }

    /***
     * Midterm Testing (Top one was already there)
     *****/
    @Test
    public void testShuffle1() {
        ArrayList<TarotCard> deck = new ArrayList<>();
        for (int i = 0; i < 78; i++) {
            deck.add(new TarotCard((Drawable) null, new String[]{"A", "B", "C",}, "Descript", TarotCard.State.NORMAL));
        }
        deck = TarotCard.shuffle(deck);

        /**
         * Explanation: Sometimes the card on the left is first,
         * sometimes the right is first. Code accounts for it
         **/
        // Inside Shuffle
        if (deck.get(0).getState() == TarotCard.State.NORMAL) {
            assertEquals(TarotCard.State.NORMAL, deck.get(0).getState());
            assertEquals(TarotCard.State.REVERSED, deck.get(1).getState());
            assertEquals(TarotCard.State.NORMAL, deck.get(2).getState());
            assertEquals(TarotCard.State.REVERSED, deck.get(3).getState());
        }
        // Outside Shuffle
        if (deck.get(0).getState() == TarotCard.State.REVERSED) {
            assertEquals(TarotCard.State.REVERSED, deck.get(0).getState());
            assertEquals(TarotCard.State.NORMAL, deck.get(1).getState());
            assertEquals(TarotCard.State.REVERSED, deck.get(2).getState());
            assertEquals(TarotCard.State.NORMAL, deck.get(3).getState());
        }
    }
}
