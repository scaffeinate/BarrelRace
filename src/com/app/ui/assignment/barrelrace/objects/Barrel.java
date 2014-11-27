package com.app.ui.assignment.barrelrace.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.app.ui.assignment.barrelrace.R;

public class Barrel {

    private Paint mPaint;
    private float x, y, radius;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Barrel(Context context, float x, float y, float radius) {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.barrel_color));
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    public void draw(Canvas c) {
        c.drawCircle(x, y, radius, mPaint);
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }
}
