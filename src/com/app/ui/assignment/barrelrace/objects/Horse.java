package com.app.ui.assignment.barrelrace.objects;

import com.app.ui.assignment.barrelrace.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
* @author Sudharsanan Muralidharan
* @description Barrel Race Game for Android
* @module Horse: Horse View Object
*/

/*Horse View Class*/
public class Horse {
    
    Paint mPaint;
    
    /*Constructor*/
    public Horse(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.horse_color));
    }
    
    /*Draw Horse*/
    public void draw(float x, float y, float horseRadius, Canvas c) {
        c.drawCircle(x, y, horseRadius, mPaint);
    }
}
