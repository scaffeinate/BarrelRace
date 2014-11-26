package com.app.ui.assignment.barrelrace.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.app.ui.assignment.barrelrace.R;

public class Barrel {

    Paint mPaint;
    
    public Barrel(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.barrel_color));
    }
    
    public void draw(float x, float y, float radius, Canvas c) {
        c.drawCircle(x, y, radius, mPaint);
    }
}
