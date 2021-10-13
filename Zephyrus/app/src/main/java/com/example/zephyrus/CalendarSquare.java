package com.example.zephyrus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

public class CalendarSquare extends AppCompatButton implements View.OnClickListener
{
    private final Integer dayOfMonth;
    private static final Paint dayOfMonthTextPaint = makeDayOfMonthTextPaint();
    public CalendarSquare(Context context, Integer dayOfMonth)
    {
        super(context);
        this.dayOfMonth = dayOfMonth;
        setOnClickListener(this);
        if(dayOfMonth == null)
            this.setBackgroundColor(Color.rgb(255, 0, 0));
        else
            this.setBackgroundColor(Color.rgb(0, 255, 0));
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size); // make it square
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
        Toast.makeText(getContext(), "dayOfMonth = " + dayOfMonth, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(this.dayOfMonth != null)
        {
            float textHeight = getHeight() / 5.0f;
            dayOfMonthTextPaint.setTextSize(textHeight);
            canvas.drawText("" + this.dayOfMonth, 5, textHeight + 1, dayOfMonthTextPaint);
        }
    }
}
