package com.example.zephyrus;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    // Do NOT use this to calculate the # of days in a month, use the method daysInMonth()
    private static int[] _daysInMonth = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static final int DAYS_IN_WEEK = 7;

    private static String[] monthNames = new String[]{"January", "February", "March",
                                                      "April", "May", "June", "July",
                                                      "August", "September", "October",
                                                      "November", "December"};

    final int horizontalBorderCalendarMargin = 20;
    final int verticalBorderCalendarMargin = 20;
    final int marginBetweenAdjacentCalenderCells = 10;
    final int halfMarginBetweenAdjacentCalenderCells = marginBetweenAdjacentCalenderCells / 2;
    MonthlyTarotHistory thisMonthsTarotHistory;
    public static int daysInMonth(int month, int year)
    {
        if(month == 1) // if February, add 1 if it's a leap year
            return _daysInMonth[month] + (year % 4 == 0 ? 1 : 0);
        return _daysInMonth[month];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /****
         * Bottom Navigation Bar Code
         ****/
        BottomNavigationView navigation = findViewById(R.id.calender_navigation);

        navigation.setSelectedItemId(R.id.calendar_nav);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.calendar_nav:
                    break;
                case R.id.spreads_nav:
                    Intent a = new Intent(CalendarActivity.this, SpreadsActivity.class);
                    startActivity(a);
                    break;
                case R.id.cardList_nav:
                    Intent b = new Intent(CalendarActivity.this, CardListActivity.class);
                    startActivity(b);
                    break;
                case R.id.journal_nav:
                    Intent c = new Intent(CalendarActivity.this, JournalActivity.class);
                    startActivity(c);
                    break;
                default:
                    break;
            }
            return false;
        });

        /****
         * Calender Code
         ****/

        Date today = Calendar.getInstance().getTime();
        int currentMonth = today.getMonth();
        int currentYear = today.getYear();
        thisMonthsTarotHistory = MonthlyTarotHistory.TarotHistoryForMonthAndYear(
                getApplicationContext(), currentMonth, currentYear);

        // Toast.makeText(this, "hist count = " + thisMonthsTarotHistory.numCardsFlippedInMonth(), Toast.LENGTH_LONG).show();

        // sets the month label
        TextView monthLabel = findViewById(R.id.monthNameTextView);
        monthLabel.setText(monthNames[currentMonth]);


        // date calculations to find the number of rows and the starting cell
        int daysInCurrentMonth = daysInMonth(today.getMonth(), today.getYear());
        int weekDay = today.getDay(); // 0=Sunday, 1=Monday, ... 6=Saturday
        int dayOfMonth = today.getDate();
        Date firstDateOfThisMonth = new Date(today.getYear(), today.getMonth(), 1);
        int firstWeekdayOfMonth = firstDateOfThisMonth.getDay();

        TableLayout calendarCellLayout = findViewById(R.id.calendarTableLayout);
        TableRow.LayoutParams rowLayoutParams = new TableRow.LayoutParams();
        rowLayoutParams.weight = 1; // make all rows have the same height

        int numRows = (int) Math.ceil((daysInCurrentMonth + firstWeekdayOfMonth) / 7.0);
        for(int row = 0; row < numRows; row++)
        {
            TableRow calendarRow = new TableRow(this);
            calendarRow.setLayoutParams(rowLayoutParams);
            for(int col = 0; col < DAYS_IN_WEEK; col++)
            {
                int cellNumber = row * 7 + col;
                int calendarSquaresDayOfMonth = cellNumber + 1 - firstWeekdayOfMonth;
                CalendarSquare calendarSquare = null;
                // if it's a square in the calendar that isn't associated with an actual day
                if (calendarSquaresDayOfMonth <= 0 || calendarSquaresDayOfMonth > daysInCurrentMonth) {
                    calendarSquare = new CalendarSquare(this, null, null);
                }
                else {
                    if(thisMonthsTarotHistory.userFlippedCardOnDay(calendarSquaresDayOfMonth))
                    {
                        TarotCard daysCard = null;
                        try {
                            daysCard = TarotCard.readNewTarotCardByID(getApplicationContext(),
                                    thisMonthsTarotHistory.getTarotCardIDForDay(calendarSquaresDayOfMonth));
                        }
                        catch(Exception e) {}
                        calendarSquare = new CalendarSquare(this, calendarSquaresDayOfMonth,
                                daysCard.getImage());
                    }
                    else {
                        try {
                            calendarSquare = new CalendarSquare(this, calendarSquaresDayOfMonth,
                                    getDrawableImage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                TableRow.LayoutParams calendarSquareLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                // these two lines make every cell have the same width
                calendarSquareLayoutParams.width = 0;
                calendarSquareLayoutParams.weight = 0.000001f;

                if(col == 0) {
                    calendarSquareLayoutParams.leftMargin = horizontalBorderCalendarMargin;
                }
                else {
                    calendarSquareLayoutParams.leftMargin = halfMarginBetweenAdjacentCalenderCells;
                }
                if(col == DAYS_IN_WEEK - 1) {
                    calendarSquareLayoutParams.rightMargin = horizontalBorderCalendarMargin;
                }
                else {
                    calendarSquareLayoutParams.rightMargin = halfMarginBetweenAdjacentCalenderCells;
                }
                if(row == 0) {
                    calendarSquareLayoutParams.topMargin = verticalBorderCalendarMargin;
                }
                else {
                    calendarSquareLayoutParams.topMargin = halfMarginBetweenAdjacentCalenderCells;
                }
                if(row == numRows - 1) {
                    calendarSquareLayoutParams.bottomMargin = verticalBorderCalendarMargin;
                }
                else {
                    calendarSquareLayoutParams.bottomMargin = halfMarginBetweenAdjacentCalenderCells;
                }
                calendarSquare.setLayoutParams(calendarSquareLayoutParams);
                calendarRow.addView(calendarSquare);
            }
            calendarCellLayout.addView(calendarRow);
        }
    }

    private Drawable getDrawableImage() throws IOException {
        Drawable image;
        InputStream file = getAssets().open("Tarot_Back_Idea_1.png");
        image = Drawable.createFromStream(file, null);
        return image;
    }

    public void calendarSquareClickedCallback(CalendarSquare calendarSquare)
    {
        Integer selectedDay = calendarSquare.getDayOfMonth();
        if(selectedDay == null)
            return;
        // Toast.makeText(this, "dayOfMonth = " + calendarSquare.getDayOfMonth(), Toast.LENGTH_SHORT).show();
        if(!thisMonthsTarotHistory.userFlippedCardOnDay(selectedDay))
        {
            int randomUnseenCardID = thisMonthsTarotHistory.randomCardIDOfUnseenCard();
            thisMonthsTarotHistory.addTarotCardForDay(getApplicationContext(), randomUnseenCardID, selectedDay);
        }
        int cardID = thisMonthsTarotHistory.getTarotCardIDForDay(selectedDay);
        Intent cardFactsIntent = new Intent(this, CardFacts.class);
        cardFactsIntent.putExtra("TarotCardID", cardID);
        startActivity(cardFactsIntent);
    }
}
