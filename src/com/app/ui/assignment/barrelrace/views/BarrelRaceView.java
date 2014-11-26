package com.app.ui.assignment.barrelrace.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.app.ui.assignment.barrelrace.R;
import com.app.ui.assignment.barrelrace.objects.Barrel;
import com.app.ui.assignment.barrelrace.objects.Fence;
import com.app.ui.assignment.barrelrace.objects.Horse;

public class BarrelRaceView extends SurfaceView implements Runnable, OnTouchListener {

    Thread t = null;
    SurfaceHolder holder;
    boolean isThreadRunning = true;
    private Context context;
    private Canvas canvas;
    private Fence fence1, fence2, fence3, fence4, fence5;
    private Horse horse;
    private Barrel barrel1, barrel2, barrel3;
    
    private Bitmap barrelBitmap;
    private float x,y, barrel1X, barrel1Y, barrel2X, barrel2Y, barrel3X, barrel3Y;
    boolean isTouched = false;
    
    public BarrelRaceView(Context context, int width, int height) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        x = width/2;
        y = height - 50;
        holder = getHolder();
        setOnTouchListener(this);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(isThreadRunning) {
            if(!holder.getSurface().isValid()) {
                continue;
            }
            
            canvas = holder.lockCanvas();
            canvas.drawColor(context.getResources().getColor(R.color.bg_color));
            
            fence1 = new Fence(context);
            fence2 = new Fence(context);
            fence3 = new Fence(context);
            fence4 = new Fence(context);
            fence5 = new Fence(context);
            
            barrelBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_barrel);
            
            barrel1 = new Barrel();
            barrel2 = new Barrel();
            barrel3 = new Barrel();
            
            horse = new Horse();

            barrel1X = (getWidth()/2);
            barrel1Y = getHeight()-225;
            
            barrel2X = (getWidth()/2) + 250;
            barrel2Y = 125;
            
            barrel3X = (getWidth()/2) - 250;
            barrel3Y = 125;
            
            fence1.draw(30, 30, getWidth()-30, 30, canvas);
            fence2.draw(30, 30, 30, getHeight()-80, canvas);
            fence3.draw(getWidth()-30, 30, getWidth()-30, getHeight()-80, canvas);
            fence4.draw(30, getHeight()-80, (getWidth()/2)-50, getHeight()-80, canvas);
            fence5.draw(getWidth()-30, getHeight()-80, (getWidth()/2)+50, getHeight()-80, canvas);
            
            barrel1.draw(barrelBitmap, barrel1X, barrel1Y, canvas);
            barrel2.draw(barrelBitmap, barrel2X, barrel2Y, canvas);
            barrel3.draw(barrelBitmap, barrel3X, barrel3Y, canvas);
            
            horse.draw(x, y, 20, canvas);
            
            if((Math.pow((x - barrel1X), 2) + Math.pow((y - barrel1Y), 2)) <= Math.pow(45, 2)) {
                Log.i("awesome", "Collision Detected Barrel 1");
            } else if((Math.pow((x - barrel2X), 2) + Math.pow((y - barrel2Y), 2)) <= Math.pow(45, 2)) {
                Log.i("awesome", "Collision Detected Barrel 2");
            } else if((Math.pow((x - barrel3X), 2) + Math.pow((y - barrel3Y), 2)) <= Math.pow(45, 2)) {
                Log.i("awesome", "Collision Detected Barrel 3");
            }
            
            holder.unlockCanvasAndPost(canvas);
        }
    }
    
    public void pause() {
        isThreadRunning = false;
        while(true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            break;
        }
        
        t = null;
    }
    
    public void resume() {
        isThreadRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        
        x = event.getX();
        y = event.getY();
        
        switch(event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            isTouched = true;
            break;
        case MotionEvent.ACTION_UP:
            isTouched = false;
            break;
        case MotionEvent.ACTION_MOVE:
            isTouched = true;
            break;
        }
        return true;
    }

}
