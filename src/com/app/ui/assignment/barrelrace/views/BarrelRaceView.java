package com.app.ui.assignment.barrelrace.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.app.ui.assignment.barrelrace.FailureActivity;
import com.app.ui.assignment.barrelrace.R;
import com.app.ui.assignment.barrelrace.SuccessActivity;
import com.app.ui.assignment.barrelrace.objects.Barrel;
import com.app.ui.assignment.barrelrace.objects.Fence;
import com.app.ui.assignment.barrelrace.objects.Horse;

public class BarrelRaceView extends SurfaceView implements Runnable, OnTouchListener, SensorEventListener, Callback {

    private SensorManager sensorManager;
    
    Thread t = null;
    SurfaceHolder holder;
    boolean isThreadRunning = true;
    private Context context;
    private Canvas canvas;
    private Fence fence1, fence2, fence3, fence4, fence5;
    private Horse horse;
    private Barrel barrel1, barrel2, barrel3;
    
    private float x,y;
    private float height, width;
    private float accelX, accelY;
    private float barrel1X, barrel1Y, barrel2X, barrel2Y, barrel3X, barrel3Y;
    private float fence1StartX, fence1StartY, fence1StopX, fence1StopY;
    private float fence2StartX, fence2StartY, fence2StopX, fence2StopY;
    private float fence3StartX, fence3StartY, fence3StopX, fence3StopY;
    private float fence4StartX, fence4StartY, fence4StopX, fence4StopY;
    private float fence5StartX, fence5StartY, fence5StopX, fence5StopY;
    private float horseRadius, barrelRadius;
    private boolean hasEntered = false;
    private boolean isGameFinished = false;
    private boolean isPenaltyReduced = false;
    private Object TIMER_LOCK = new Object();
    
    private long startTime = 0L, timeDiffMil = 0L;
    
    private Vibrator vibrator;
    private MediaPlayer bMedia, fMedia;
    private TextView textViewTime;
    private Handler handler;
    
    public BarrelRaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        getHolder().addCallback(this);
    }

    public void initialize(Context context, int width, int height, TextView textViewTime) {
        this.context = context;
        this.height = height;
        this.width = width;
        holder = getHolder();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        initalizeCoordinates();
        initializeObjects();
        initializeMedia();
        initializeTimer();
        this.textViewTime = textViewTime;
        setOnTouchListener(this);
    }

    private void initializeTimer() {
        // TODO Auto-generated method stub
        startTime = SystemClock.uptimeMillis();
        handler = new Handler();
        handler.postDelayed(updateTimer, 0);
    }

    private void initializeMedia() {
        // TODO Auto-generated method stub
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        bMedia = MediaPlayer.create(context, R.raw.barrel_hit);
        fMedia = MediaPlayer.create(context, R.raw.fence_hit);
    }

    private void initalizeCoordinates() {
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

    private void initializeObjects() {
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
            
            x += accelX;
            y += accelY;
            
            if(y > height-50) {
                y = height-50;
            }
            
            horse.draw(x, y, 20, canvas);
            
            if(y <= canvas.getHeight()-60-horseRadius) {
                hasEntered = true;
            } else {
                hasEntered = false;
            }
            
            if(collides()) {
                handleCollision();
            }
            
            if(collidesFence()) {
                handleCollisionWithFence();
            } else {
                isPenaltyReduced = false;
            }
            
            if(checkCircleBarrel(barrel1) && checkCircleBarrel(barrel2) 
                    && checkCircleBarrel(barrel3)) {
                isThreadRunning = false;
                t.interrupt();
                if(!isGameFinished) {
                    Intent toSuccessActivity = new Intent(context, SuccessActivity.class);
                    toSuccessActivity.putExtra("timeElapsed", SystemClock.uptimeMillis()-startTime);
                    context.startActivity(toSuccessActivity);
                    ((Activity) context).finish();
                    isGameFinished = true;
                }
            }
            
            holder.unlockCanvasAndPost(canvas);
        }
    }
    
    private void handleCollisionWithFence() {
        // TODO Auto-generated method stub
        if(!isPenaltyReduced) {
            fMedia.start();
            if(vibrator.hasVibrator()) {
                vibrator.vibrate(100);
            }
            synchronized (TIMER_LOCK) {
                startTime -= 5000;
            }
            isPenaltyReduced = true;
        }
    }

    private boolean checkCircleBarrel(Barrel barrel) {
        // TODO Auto-generated method stub
        
        if(x >= (barrel.getX()+barrelRadius+10) 
                && y >= (barrel.getY()+barrelRadius+10)) {
            barrel.setRightBottomQuad(true);
        }
        
        if(x >= (barrel.getX()+barrelRadius+10) 
                && y <= (barrel.getY()-barrelRadius-10)) {
            barrel.setRightTopQuad(true);
        }
        
        if(x <= (barrel.getX()-barrelRadius-10) 
                && y <= (barrel.getY()-barrelRadius-10)) {
            barrel.setLeftTopQuad(true);
        }
        
        if(x <= (barrel.getX()-barrelRadius-10) 
                && y >= (barrel.getY()+barrelRadius+10)) {
            barrel.setLeftBottomQuad(true);
        }
        
        return hasEntered && barrel.isCircled();
    }

    private boolean collidesFence() {
        // TODO Auto-generated method stub
        
        boolean collidesFence = false;
        
        if(x <= (horseRadius+30)) {
            x = 30+horseRadius;
            collidesFence = true;
        }  
        
        if(x >= (canvas.getWidth()-30-horseRadius)) {
            x = canvas.getWidth()-30-horseRadius;
            collidesFence = true;
        } 
        
        if(y <= (horseRadius+30)) {
            y = 30+horseRadius;
            collidesFence = true;
        }  
        
        if(y >= canvas.getHeight()-80-horseRadius) {
            if(x >= (canvas.getWidth()/2)-25-horseRadius && x <= ((canvas.getWidth()/2)+25+horseRadius)) {
                collidesFence = false;
            } else {
                if(hasEntered) {
                    y = canvas.getHeight()-80-horseRadius;
                    collidesFence = true;
                } else {
                    y = canvas.getHeight()-50;
                    collidesFence = false;
                }
            }
        } 
        
        return collidesFence;
    }

    private void handleCollision() {
        // TODO Auto-generated method stub
        
        isThreadRunning = false;
        t.interrupt();
        
        if(!isGameFinished) {
            bMedia.start();
            if(vibrator.hasVibrator()) {
                vibrator.vibrate(100);
            }
            Intent toFailureActivity = new Intent(context, FailureActivity.class);
            context.startActivity(toFailureActivity);
            ((Activity) context).finish();
            isGameFinished = true;
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
       
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        
    }

    private Runnable updateTimer = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            timeDiffMil = (SystemClock.uptimeMillis() - startTime);
            
            int timeDiff = (int) (timeDiffMil/1000);
            int minutes = timeDiff/60;
            int seconds = timeDiff % 60;
            int milliseconds = (int) (timeDiffMil % 1000);
        
            textViewTime.setText(minutes + ":" + String.format("%02d", seconds) 
                    + ":" + String.format("%03d", milliseconds));
        
            handler.postDelayed(this, 0);
        }
        
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        /*x = event.values[0]*350;
        y = event.values[1]*150;*/
        /*x = (float) (Math.signum(event.values[0]) * Math.abs(event.values[0]) * (1 - 0.5 * Math.abs(event.values[2]) / 9.8));
        y = (float) (Math.signum(event.values[1]) * Math.abs(event.values[1]) * (1 - 0.5 * Math.abs(event.values[2]) / 9.8));
    
        x = x*250;
        y=y*250;*/
        
        accelX = (float) (event.values[1] * 1.2);
        accelY = (float) (event.values[0] * 1.5);
        
    
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }
    
}
