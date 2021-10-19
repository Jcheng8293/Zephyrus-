package com.example.zephyrus;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalendarUnitTests
{
    @Test
    public void testDaysInMonth()
    {
        // use https://calendar.google.com/calendar/ to find some test data
        assertEquals(28, CalendarActivity.daysInMonth(1, 1999)); // test non-leap year
        assertEquals(29, CalendarActivity.daysInMonth(1, 2000)); // test leap year
        assertEquals(31, CalendarActivity.daysInMonth(6, 2021)); // test two non-February months
        assertEquals(30, CalendarActivity.daysInMonth(3, 2018));
    }

}
