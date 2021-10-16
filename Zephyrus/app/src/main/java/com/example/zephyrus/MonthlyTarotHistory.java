package com.example.zephyrus;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/*
* This class is used for storing and accessing the history of which tarot cards a user has flipped
* in a given month. Every modification of the data yields an immediate change in the file that
* each object is associated with.
*
*
* */
public class MonthlyTarotHistory implements Serializable {
    private final int month; // [0,11]
    private final int year;  // A.D.
    private final HashMap<Integer, TarotCard> daysThatTheUserSelectedCardThisMonth; // dayNumber -> tarotCardForThatDay
    private MonthlyTarotHistory(Context applicationContext, int month, int year)
    {
        if(month < 0 || month > 11)
            throw new IllegalArgumentException("month must be in range [0, 12), but was given '" + month + "'");

        this.month = month;
        this.year = year;
        this.daysThatTheUserSelectedCardThisMonth = new HashMap<>();
        saveHistoryToFile(applicationContext);
    }
    /*
    * This function returns a Tarot Card history object. If this is the first time that a user has
    * accessed the app for this month, it creates a new TarotCardHistory object. If they have already
    * accessed it this month, it reads it in from a file.
    * The parameter 'applicationContext' should be the result of the function getApplicationContext().
    * */
    public static MonthlyTarotHistory TarotHistoryForMonthAndYear(Context applicationContext, int month, int year)
    {
        if(historyFileForMonthAndYearAlreadyExists(month, year))
            return readTarotHistoryFromFile(applicationContext, month, year);
        return new MonthlyTarotHistory(applicationContext, month, year);
    }
    public int getMonth()
    {
        return month;
    }
    public int getYear()
    {
        return year;
    }
    public void addTarotCardForDay(Context applicationContext, TarotCard card, int day)
    {
        daysThatTheUserSelectedCardThisMonth.put(day, card);
        saveHistoryToFile(applicationContext);
    }
    public boolean userFlippedCardOnDay(int day)
    {
        return daysThatTheUserSelectedCardThisMonth.containsKey(day);
    }
    public TarotCard getTarotCardForDay(int day)
    {
        return daysThatTheUserSelectedCardThisMonth.get(day);
    }
    private void saveHistoryToFile(Context applicationContext)
    {
        File applicationFilesDir = applicationContext.getFilesDir();
        String filename = makeFilenameFromMonthAndYear();
        File cardHistoryFile = new File(applicationFilesDir, filename);
        try {
            FileOutputStream f_out = new FileOutputStream(cardHistoryFile);
            ObjectOutputStream oos = new ObjectOutputStream(f_out);
            oos.writeObject(this);
        }
        catch(IOException e)
        {
            Toast.makeText(applicationContext, "Something went wrong while saving monthly history.", Toast.LENGTH_LONG).show();
        }
    }
    private String makeFilenameFromMonthAndYear()
    {
        return makeFilenameFromMonthAndYear(this.month, this.year);
    }
    private static String makeFilenameFromMonthAndYear(int month, int year)
    {
        String monthPaddedWithZeros = String.format("%2s", ""+month).replace(' ', '0');
        return "MonthlyHistory-" + monthPaddedWithZeros + "-" + year + ".ser";
    }
    private static boolean historyFileForMonthAndYearAlreadyExists(int month, int year)
    {
        String filename = makeFilenameFromMonthAndYear(month, year);
        return new File(filename).exists();
    }
    private static MonthlyTarotHistory readTarotHistoryFromFile(Context applicationContext, int month, int year)
    {
        String filename = makeFilenameFromMonthAndYear(month, year);
        File historyFile = new File(filename);
        MonthlyTarotHistory h = null;
        try {
            FileInputStream f_in = new FileInputStream(historyFile);
            ObjectInputStream ois = new ObjectInputStream(f_in);
            h = (MonthlyTarotHistory) ois.readObject();
        }catch (IOException | ClassNotFoundException e)
        {
            Toast.makeText(applicationContext, "Something went wrong while reading monthly history.", Toast.LENGTH_LONG).show();
        }
        return h;
    }

}
