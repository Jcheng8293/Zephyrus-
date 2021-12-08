package com.example.zephyrus;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TarotDeck {

    /****
     * All spread functions will use this class to:
     * shuffle, save, load, and create files needed to make
     * the spread functions work.
     * It is also used to save to journal
     */
    private ArrayList<Integer> cardIDs = new ArrayList<>();
    private File arbitraryFile;

    TarotDeck(Context context, String filename) {
        initialize(context, filename);
    }

    TarotDeck() { }

    // Initializes the deck
    private void initialize(Context context, String filename) {
        File filePath = context.getFilesDir();
        arbitraryFile = new File(filePath, filename);

        String content = readFile();

        // First time running the app
        if (!arbitraryFile.canWrite() || content.equals("")) {
            firstTime();
        }

        // Has ran the app once
        else {
            notFirstTime(content);
        }

    }

    // In theory, this should never fail, but if it does, shit...
    public boolean checkCards() {
        for (int i = 0; i < cardIDs.size(); i++) {

            // For odd (NORMAL) cards
            if (cardIDs.get(i) % 2 == 0) {
                for (int j = 0; j < cardIDs.size(); j++) {

                    // Checks for REVERSED versions of the same card and the same exact card
                    if (cardIDs.get(i) + 1 == cardIDs.get(j) || (cardIDs.get(i) == cardIDs.get(j) && i != j)) {
                        return false;
                    }
                }
            } else {
                for (int j = 0; j < cardIDs.size(); j++) {

                    // Checks for NORMAL versions of the same card and the same exact card
                    if (cardIDs.get(i) - 1 == cardIDs.get(j) || (cardIDs.get(i) == cardIDs.get(j) && i != j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // This should run after running "firstTime()" once
    private void notFirstTime(String content) {
        stringToDeck(content);
    }

    // Reads "deck.txt" and initializes the cardIDs array
    private String readFile() {
        int length = (int) arbitraryFile.length();
        byte[] bytes = new byte[length];
        FileInputStream fis;

        try {
            fis = new FileInputStream(arbitraryFile);
            fis.read(bytes);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }

    // Create and save the base deck
    // This Should only run once, ever
    private void firstTime() {
        String string = defaultDeck();
        try {
            FileOutputStream fos = new FileOutputStream(arbitraryFile);
            fos.write(string.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create the base deck of cardIDs
    private String defaultDeck() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 78; i++) {
            temp.append(i * 2).append(" ");
        }
        return temp.toString();
    }

    // Convert String to Array
    private void stringToDeck(String content) {
        String[] temp = content.split(" ");
        for (String s : temp) {
            cardIDs.add((int) Integer.parseInt(s.trim()));
        }
    }

    // Convert Array to String
    // Yes, there is a built-in method for it, but this somewhat makes it more
    // clean in my opinion at least.
    private String deckToString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < cardIDs.size(); i++) {
            output.append(cardIDs.get(i)).append(" ");
        }
        return output.toString();
    }

    // The Master Method
    // Does all the shuffling and picks the top # of cards (3)
    public void shuffleCall() {
        uglyShuffle();
        regShuffle();
        regShuffle();
        regShuffle();
        overwrite();
    }

    // Shuffle where you spreads cards around the table and put them back together
    // I hate this shuffle, so messy...
    private void uglyShuffle() {
        ArrayList<Integer> tempDeck = new ArrayList<>();
        for (int i = 0; i < 78; i++) {
            tempDeck.add(cardIDs.remove((int) Math.floor(Math.random() * cardIDs.size())));
        }
        cardIDs = tempDeck;
    }

    // Regular shuffle that we all know how to do but suck at
    private void regShuffle() {
        ArrayList<Integer> tempDeck = new ArrayList<>();
        ArrayList<Integer> firstHalf = new ArrayList<>();
        ArrayList<Integer> otherHalf = new ArrayList<>();
        int mainIndex = 0;
        int firstIndex = 0;
        int otherIndex = 0;

        for (int i = 0; i < 39; i++) {
            firstHalf.add(cardIDs.get(i));
            otherHalf.add(cardIDs.get(i + 39));
        }

        if ((int) Math.round(Math.random()) == 0) {
            firstHalf = reverse(firstHalf);
        } else {
            otherHalf = reverse(otherHalf);
        }

        while (firstIndex != firstHalf.size() || otherIndex != otherHalf.size()) {
            if (Math.round(Math.random()) == 0 && mainIndex != 78 && firstHalf.size() != 0) {
                tempDeck.add(firstHalf.remove(0));
                mainIndex++;
            } else if (Math.round(Math.random()) == 1 && mainIndex != 78 && otherHalf.size() != 0) {
                tempDeck.add(otherHalf.remove(0));
                mainIndex++;
            }
        }
        cardIDs = tempDeck;
    }

    private ArrayList<Integer> reverse(ArrayList<Integer> half) {
        int temp;
        for (int i = 0; i < half.size(); i++) {
            if (half.get(i) % 2 == 0) {
                temp = half.remove(i);
                half.add(i, temp - 1);
            } else {
                temp = half.remove(i);
                half.add(i, temp + 1);
            }
        }
        return half;
    }

    private void overwrite() {
        try {
            FileOutputStream fos = new FileOutputStream(arbitraryFile);
            String string = deckToString();
            fos.write(string.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] pickNumberOfCards(int number) {
        int[] cards = new int[number];

        for (int i = 0; i < number; i++) {
            cards[i] = cardIDs.remove(cardIDs.size() - 1);
        }
        for (int i = 0; i < number; i++) {
            cardIDs.add((int) (Math.floor(Math.random() * cardIDs.size())), cards[i]);
        }

        return cards;
    }

    // Saves reading
    public void saveToJournal(Context context, String readingType, int[] cardNumbers) {
        File filePath = context.getFilesDir();
        String fileName = "Readings.txt";
        arbitraryFile = new File(filePath, fileName);

        String info = "";
        if (cardNumbers.length == 3) {
            info = getCurrentTime() + "\n" + readingType + "\n" + cardNumbers[0] + " " + cardNumbers[1] + " " + cardNumbers[2] + "\n";
        }
        else {
            info = getCurrentTime() + "\n" + readingType + "\n" + cardNumbers[0] + "\n";
        }
        // Reads Journal File not deck file
        info += readFile();
        try {
            FileOutputStream fos = new FileOutputStream(arbitraryFile);
            fos.write(info.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads Past Readings
    public String[] readFromJournal(Context context) throws FileNotFoundException {
        File filePath = context.getFilesDir();
        String[] output = new String[0];
        String[] temp;
        int size = 1;
        int index = 0;
        arbitraryFile = new File(filePath, "Readings.txt");
        Scanner scanner = new Scanner(arbitraryFile);

        while (scanner.hasNextLine()) {
            temp = output;
            output = new String[size+=2];
            if (temp.length != 0) {
                for (int i = 0; i < temp.length; i++) {
                    output[i] = temp[i];
                }
            }
            output[index] = "  " + scanner.nextLine() + "  \n" + scanner.nextLine();
            index++;
            output[index] = scanner.nextLine();
            index++;
        }
        return output;
    }

    // Returns time in Year/Month/Day Hour:Minute:Second
    private String getCurrentTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss z");
        return sdf.format(new Date());
    }

}
