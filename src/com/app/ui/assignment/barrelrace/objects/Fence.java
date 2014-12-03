package com.app.ui.assignment.barrelrace.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.app.ui.assignment.barrelrace.R;

/**
* @author Sudharsanan Muralidharan
* @netid sxm149130
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.022
* @description Barrel Race Game for Android
* @module Fence: Fence View Object
*/

/*Fence View Class*/
public class Fence {
    
    private Paint mPaint;
    private float startX, startY, stopX, stopY;
    
    /*Constructor*/
    public Fence(Context context, float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.fence_color));
        mPaint.setStrokeWidth(5);
    }

    /*Draw Circle*/
    public void draw(Canvas c) {
        c.drawLine(startX, startY, stopX, stopY, mPaint);
    }
    
    /*Getters and Setters*/
    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getStopX() {
        return stopX;
    }

    public void setStopX(float stopX) {
        this.stopX = stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public void setStopY(float stopY) {
        this.stopY = stopY;
    }
}
