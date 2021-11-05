package com.example.zephyrus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

public class CalendarSquare extends AppCompatButton implements View.OnClickListener
{
    private final Integer dayOfMonth;
    private static final Paint dayOfMonthTextPaint = makeDayOfMonthTextPaint();
    private final Drawable tarotCardImage;
    public CalendarSquare(Context context, Integer dayOfMonth, Drawable tarotCardImage)
    {
        super(context);
        this.dayOfMonth = dayOfMonth;
        setOnClickListener(this);
        if(dayOfMonth == null)
            this.setBackgroundColor(getResources().getColor(R.color.CalendarUnusedSquare));
        else
            this.setBackgroundColor(getResources().getColor(R.color.CalendarUsedSquare));
        this.tarotCardImage = tarotCardImage;
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size); // make it square
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public static Paint makeDayOfMonthTextPaint()
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public void onClick(View view)
    {
        // just pass it and a ref to self along to parent activity
        ((CalendarActivity) getContext()).calendarSquareClickedCallback(this);
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
