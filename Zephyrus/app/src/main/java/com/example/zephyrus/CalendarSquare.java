package com.example.zephyrus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarSquare extends AppCompatButton implements View.OnClickListener {
    private final Integer dayOfMonth;
    private static final Paint dayOfMonthTextPaint = makeDayOfMonthTextPaint();
    private static Calendar cal = Calendar.getInstance();
    private final Drawable tarotCardImage;
    private static String[] day_Name = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public CalendarSquare(Context context, Integer dayOfMonth, Drawable tarotCardImage) {
        super(context);
        this.dayOfMonth = dayOfMonth;
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        int current_month = cal.get(Calendar.MONTH);
        setOnClickListener(this);
        if (dayOfMonth == null)
            this.setBackgroundColor(getResources().getColor(R.color.CalendarUnusedSquare));
        else if (dayOfMonth == current_day)
            this.setBackgroundColor(Color.rgb(250, 250, 250)); // Sets the tile for the current day of the week as white
        else
            this.setBackgroundColor(getResources().getColor(R.color.CalendarUsedSquare));
        this.tarotCardImage = tarotCardImage;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size); // make it square
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public static Paint makeDayOfMonthTextPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public String dateSuffix(int day) {
        String num = Integer.toString(day);
        String end = num.substring(num.length() - 1);
        if (num.substring(0,1).equals("1")) {
            return "th";
        } else {
            switch (end) {
                case "1":
                    return "st";
                case "2":
                    return "nd";
                case "3":
                    return "rd";
                default:
                    return "th";
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        // just pass it and a ref to self along to parent activity
        ((CalendarActivity) getContext()).calendarSquareClickedCallback(this);
        // Going to add a method that will list the current day depending upon the calendar date
        Toast.makeText(getContext(), "CurrentDay" + ": " + dayOfMonth + dateSuffix(dayOfMonth), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(this.dayOfMonth != null)
        {
            int height, width;
            height = width = getHeight();
            int centerY = height / 2;
            int centerX = width / 2;
            if(this.tarotCardImage != null)
            {
                double cardImageAspectRatio = (double) tarotCardImage.getIntrinsicWidth() / tarotCardImage.getIntrinsicHeight();
                double cardImageWidth = width / 2;
                double cardImageHeight = cardImageWidth / cardImageAspectRatio;
                tarotCardImage.setBounds((int)(centerX - cardImageWidth/2), (int)(centerY - cardImageHeight/2),
                                         (int)(centerX + cardImageWidth/2), (int)(centerY + cardImageHeight/2));
                tarotCardImage.draw(canvas);
            }

            float textHeight = height / 5.0f;
            dayOfMonthTextPaint.setTextSize(textHeight);
            canvas.drawText("" + this.dayOfMonth, 5, textHeight + 1, dayOfMonthTextPaint);
        }
    }
}
