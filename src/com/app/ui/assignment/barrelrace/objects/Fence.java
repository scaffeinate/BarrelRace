package com.app.ui.assignment.barrelrace.objects;

import com.app.ui.assignment.barrelrace.R;
import com.app.ui.assignment.barrelrace.R.color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Fence {
    
    Paint mPaint;
    
    public Fence(Context c) {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(c.getResources().getColor(R.color.fence_color));
        mPaint.setStrokeWidth(10);
    }
    
    public void draw(int startX, int startY, int stopX, int stopY, Canvas c) {
        c.drawLine(startX, startY, stopX, stopY, mPaint);
    }
}
