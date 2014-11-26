package com.app.ui.assignment.barrelrace.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Horse {
    
    Paint mPaint;
    
    public Horse() {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.RED);
    }
    
    public void draw(int x, int y, int radius, Canvas c) {
        c.drawCircle(x, y, radius, mPaint);
    }
}
