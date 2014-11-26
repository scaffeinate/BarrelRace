package com.app.ui.assignment.barrelrace;

import android.graphics.Bitmap;
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
    
    public void draw(Bitmap barrel, int x, int y, Canvas c) {
        /*c.drawCircle(x, y, radius, mPaint);*/
        c.drawBitmap(barrel, x, y, null);
    }
}
