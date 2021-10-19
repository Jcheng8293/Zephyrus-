package com.example.zephyrus;
import org.junit.Test;
import static org.junit.Assert.*;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
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
}
