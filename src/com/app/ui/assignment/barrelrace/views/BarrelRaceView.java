package com.app.ui.assignment.barrelrace.views;

import android.content.Context;
import android.graphics.Canvas;
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
    
    private float x,y, barrel1X, barrel1Y, barrel2X, barrel2Y, barrel3X, barrel3Y;
    private float horseRadius, barrelRadius;
    boolean isTouched = false;
    
    public BarrelRaceView(Context context, int width, int height) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        x = width/2;
        y = height - 50;
        horseRadius = 25;
        barrelRadius = 25;
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
            
            barrel1 = new Barrel(context);
            barrel2 = new Barrel(context);
            barrel3 = new Barrel(context);
            
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
            
            barrel1.draw(barrel1X, barrel1Y, barrelRadius, canvas);
            barrel2.draw(barrel2X, barrel2Y, barrelRadius, canvas);
            barrel3.draw(barrel3X, barrel3Y, barrelRadius, canvas);
            
            horse.draw(x, y, 20, canvas);
            
            if(checkCollision()) {
                handleCollision();
            }
            
            holder.unlockCanvasAndPost(canvas);
        }
    }
    
    private void handleCollision() {
        // TODO Auto-generated method stub
    }

    private boolean checkCollision() {
        // TODO Auto-generated method stub
        
        boolean collidesBarrel1 = (Math.pow((x - barrel1X), 2) + Math.pow((y - barrel1Y), 2)) 
                <= Math.pow((horseRadius + barrelRadius), 2);  
        boolean collidesBarrel2 = (Math.pow((x - barrel2X), 2) + Math.pow((y - barrel2Y), 2)) 
                <= Math.pow((horseRadius + barrelRadius), 2);
        boolean collidesBarrel3 = (Math.pow((x - barrel3X), 2) + Math.pow((y - barrel3Y), 2)) 
                <= Math.pow((horseRadius + barrelRadius), 2); 
        
        if(collidesBarrel1) {
            readjustHorse(barrel1X, barrel1Y);
            return true;
        } else if(collidesBarrel2) {
            readjustHorse(barrel2X, barrel2Y);
            return true;
        } else if(collidesBarrel3) {
            readjustHorse(barrel3X, barrel3Y);
            return true;
        }
        
        return false;
    }

    private void readjustHorse(float bX, float bY) {
        // TODO Auto-generated method stub
        if(x > bX) {
            x = x + 1f;
        } else {
            x = x - 1f;
        }
        
        if(y > bY) {
            y = y + 1f;
        } else {
            y = y - 1f;
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
