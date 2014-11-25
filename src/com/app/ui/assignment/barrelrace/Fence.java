package com.app.ui.assignment.barrelrace;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Fence {
    
    Paint mPaint;
    
    public Fence() {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10);
    }
    
    public void draw(int startX, int startY, int stopX, int stopY, Canvas c) {
        c.drawLine(startX, startY, stopX, stopY, mPaint);
    }
}
