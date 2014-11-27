package com.app.ui.assignment.barrelrace.views;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Vibrator;
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
    
    private float x,y;
    private float barrel1X, barrel1Y, barrel2X, barrel2Y, barrel3X, barrel3Y;
    private float fence1StartX, fence1StartY, fence1StopX, fence1StopY;
    private float fence2StartX, fence2StartY, fence2StopX, fence2StopY;
    private float fence3StartX, fence3StartY, fence3StopX, fence3StopY;
    private float fence4StartX, fence4StartY, fence4StopX, fence4StopY;
    private float fence5StartX, fence5StartY, fence5StopX, fence5StopY;
    private float horseRadius, barrelRadius;
    boolean isTouched = false;
    
    private Vibrator vibrator;
    private MediaPlayer bMedia;
    
    public BarrelRaceView(Context context, int width, int height) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        initalizeCoordinates(width, height);
        initializeObject();
        holder = getHolder();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        bMedia = MediaPlayer.create(context, R.raw.barrel_hit);
        setOnTouchListener(this);
    }

    private void initalizeCoordinates(float width, float height) {
        // TODO Auto-generated method stub
        x = width/2;
        y = height-50;
        horseRadius = 25;
        barrelRadius = 25; 
        
        barrel1X = (width/2);
        barrel1Y = height-225;
        
        barrel2X = (width/2)+250;
        barrel2Y = 125;
        
        barrel3X = (width/2)-250;
        barrel3Y = 125;
        
        fence1StartX = 30;
        fence1StartY = 30;
        fence1StopX = width-30;
        fence1StopY = 30;
        
        fence2StartX = 30;
        fence2StartY = 30;
        fence2StopX = 30;
        fence2StopY = height-80;
        
        fence3StartX = width-30;
        fence3StartY = 30;
        fence3StopX = width-30;
        fence3StopY = height-80;
        
        fence4StartX = 30;
        fence4StartY = height-80;
        fence4StopX = (width/2)-50;
        fence4StopY = height-80;
        
        fence5StartX = width-30;
        fence5StartY = height-80;
        fence5StopX = (width/2)+50;
        fence5StopY = height-80;
        
    }

    private void initializeObject() {
        // TODO Auto-generated method stub
        
        horse = new Horse();
        
        fence1 = new Fence(context, fence1StartX, fence1StartY, fence1StopX, fence1StopY);
        fence2 = new Fence(context, fence2StartX, fence2StartY, fence2StopX, fence2StopY);
        fence3 = new Fence(context, fence3StartX, fence3StartY, fence3StopX, fence3StopY);
        fence4 = new Fence(context, fence4StartX, fence4StartY, fence4StopX, fence4StopY);
        fence5 = new Fence(context, fence5StartX, fence5StartY, fence5StopX, fence5StopY);
        
        barrel1 = new Barrel(context, barrel1X, barrel1Y, barrelRadius);
        barrel2 = new Barrel(context, barrel2X, barrel2Y, barrelRadius);
        barrel3 = new Barrel(context, barrel3X, barrel3Y, barrelRadius);
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
            
            fence1.draw(canvas);
            fence2.draw(canvas);
            fence3.draw(canvas);
            fence4.draw(canvas);
            fence5.draw(canvas);
            
            barrel1.draw(canvas);
            barrel2.draw(canvas);
            barrel3.draw(canvas);
            
            horse.draw(x, y, 20, canvas);
            
            if(collides()) {
                handleCollision();
            }
            
            holder.unlockCanvasAndPost(canvas);
        }
    }
    
    private void handleCollision() {
        // TODO Auto-generated method stub
        bMedia.start();
        if(vibrator.hasVibrator()) {
            vibrator.vibrate(50);
        }   
    }

    private boolean collides() {
        // TODO Auto-generated method stub
        
        if(checkCriteria(barrel1)) {
            readjustHorse(barrel1);
        } else if(checkCriteria(barrel2)) {
            readjustHorse(barrel2);
        } else if(checkCriteria(barrel3)) {
            readjustHorse(barrel3);
        } else {
            return false;
        }
        
        return true;
    }

    private void readjustHorse(Barrel barrel) {
        // TODO Auto-generated method stub
        if(x > barrel.getX()) {
            x = x + 1f;
        } else {
            x = x - 1f;
        }
        
        if(y > barrel.getY()) {
            y = y + 1f;
        } else {
            y = y - 1f;
        }
        
    }

    private boolean checkCriteria(Barrel barrel) {
        return (Math.pow((x - barrel.getX()), 2) + Math.pow((y - barrel.getY()), 2)) 
                <= Math.pow((horseRadius + barrelRadius), 2);
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
