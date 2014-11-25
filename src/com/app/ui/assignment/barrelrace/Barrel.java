package com.app.ui.assignment.barrelrace;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Barrel {

    Paint mPaint;
    
    public Barrel() {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.BLUE);
    }
    
    public void draw(int x, int y, int radius, Canvas c) {
        c.drawCircle(x, y, radius, mPaint);
    }
}
