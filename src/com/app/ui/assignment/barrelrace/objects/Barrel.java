package com.app.ui.assignment.barrelrace.objects;

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
    
    public void draw(Bitmap barrel, float x, float y, Canvas c) {
        c.drawCircle(x, y, 25, mPaint);
        /*c.drawBitmap(barrel, x, y, null);*/
    }
}
