package com.app.ui.assignment.barrelrace.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
* @author Sudharsanan Muralidharan
* @netid sxm149130
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.022
* @description Barrel Race Game for Android
* @module Horse: Horse View Object
*/

public class Horse {
    
    Paint mPaint;
    
    public Horse() {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.RED);
    }
    
    public void draw(float x, float y, float horseRadius, Canvas c) {
        c.drawCircle(x, y, horseRadius, mPaint);
    }
}
