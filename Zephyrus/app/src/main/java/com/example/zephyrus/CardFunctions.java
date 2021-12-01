package com.example.zephyrus;

public class CardFunctions {

    /****
     * All spread functions will use this class to:
     * shuffle, save, load, and create files needed to make
     * the spread functions work.
     */
    private int[] cardIDs;

    CardFunctions(int[] cardIDs) {
        this.cardIDs = cardIDs;
    }

    // The Master Method
    // Does all the shuffling and picks the top # of cards (3)
    public int[] shuffleCall() {
        uglyShuffle();
        regShuffle();
        regShuffle();
        regShuffle();
        return cardIDs;
    }

    // Shuffle where you spreads cards around the table and put them back together
    // I hate this shuffle, so messy...
    private void uglyShuffle() {
        int[] tempDeck = new int[78];
        for (int i = 0; i < cardIDs.length; i++) {
            tempDeck[i] = cardIDs[(int) Math.round(Math.random() * (78-i))];
        }
        cardIDs = tempDeck;
    }

    // Regular shuffle that we all know how to do but suck at
    private void regShuffle() {
        int[] firstHalf = new int[39];
        int[] otherHalf = new int[39];
        for (int i = 0; i < firstHalf.length; i++ ) {
            firstHalf[i] = cardIDs[i];
            otherHalf[i] = cardIDs[i+39];
        }
    }
}
