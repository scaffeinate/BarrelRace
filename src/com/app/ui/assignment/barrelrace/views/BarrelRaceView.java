package com.app.ui.assignment.barrelrace.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

import com.app.ui.assignment.barrelrace.FailureActivity;
import com.app.ui.assignment.barrelrace.R;
import com.app.ui.assignment.barrelrace.SuccessActivity;
import com.app.ui.assignment.barrelrace.objects.Barrel;
import com.app.ui.assignment.barrelrace.objects.Fence;
import com.app.ui.assignment.barrelrace.objects.Horse;
import com.app.ui.assignment.barrelrace.util.TimerUtil;

/**
* @author Sudharsanan Muralidharan
* @netid sxm149130
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.022
* @description Barrel Race Game for Android
* @module BarrelRaceView: View for Game and GameLoop Thread
*/

/* BarrelRaceView: Main Game View extends SurfaceView, 
 * implements Runnable for Thread, SensorEvents, Callback
 * */
public class BarrelRaceView extends SurfaceView implements Runnable, SensorEventListener, Callback {

    private SensorManager sensorManager;
    
    //Main Game Thread
    Thread t = null;
    SurfaceHolder holder;
    
    /*Flag to maintain the thread state*/
    boolean isThreadRunning = true;
    
    private Context context;
    
    /*Game View Objects*/
    private Canvas canvas;
    private Fence fence1, fence2, fence3, fence4, fence5;
    private Horse horse;
    private Barrel barrel1, barrel2, barrel3;
    
    /*X and Y positions of the horse Object*/
    private float x,y;
    /*Height and Width of the Screen/Canvas*/
    private float height, width;
    /*Acceleration from the Sensor data*/
    private float accelX, accelY;
    /*X and Y positions of the three barrels*/
    private float barrel1X, barrel1Y, barrel2X, barrel2Y, barrel3X, barrel3Y;
    /*Entry point for the horse*/
    private float gateGap;
    /*Define margins and start & stop of X and Y for fence positions*/
    private float leftMargin, rightMargin, topMargin, bottomMargin;
    private float fence1StartX, fence1StartY, fence1StopX, fence1StopY;
    private float fence2StartX, fence2StartY, fence2StopX, fence2StopY;
    private float fence3StartX, fence3StartY, fence3StopX, fence3StopY;
    private float fence4StartX, fence4StartY, fence4StopX, fence4StopY;
    private float fence5StartX, fence5StartY, fence5StopX, fence5StopY;
    /*Horse and Barrel Radius*/
    private float horseRadius, barrelRadius;
    /*Start Position of the Horse*/
    private float[] horseStartPosition;
    
    /*Multiple to move the horse faster, accelX and accelY times this value*/
    private float accelRate;
    /*Various Flags to use in Game Logic*/
    private boolean hasEntered = false;
    private boolean isGameFinished = false;
    private boolean isPenaltyReduced = false;
    private boolean isTimerStarted = false;
    private boolean barrel1Circled, barrel2Circled, barrel3Circled;
    private boolean isSoundEnabled = false;
    /*Game Difficulty from SharedPreferences*/
    private String gameDifficulty;
    private Paint mPaint;
    
    /*synchorized(Object) to send*/
    private Object TIMER_LOCK = new Object();
    
    /*Timer values*/
    private long startTime = 0L, timeDiffMil = 0L;
    
    /*Initialize objects*/
    private Vibrator vibrator;
    private MediaPlayer bMedia, fMedia;
    private TextView textViewTime;
    private Handler handler;
    private TimerUtil timerUtil;
    
    private SharedPreferences sharedPreferences;
    
    /*Constructor*/
    public BarrelRaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        /*Get the Holder and addCallback on start*/
        getHolder().addCallback(this);
    }

    /*initialize(Context, int, int TextView) to initialize various objects, timer textviews, get height and width*/
    public void initialize(Context context, int width, int height, TextView textViewTime) {
        this.context = context;
        this.height = height;
        this.width = width;
        holder = getHolder();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        initalizeCoordinates(); //Initialize Object Coordinates
        initializeObjects(); //Initialize Game View Objects
        initializeMedia(); //Initialize Media and Vibrator
        initializeGamePrefs(); //Fetch Game Preferences from SharedPreferences
        this.textViewTime = textViewTime;
    }

    /*Fetch Game Preferences from SharedPreferences*/
    private void initializeGamePrefs() {
        // TODO Auto-generated method stub
        sharedPreferences = context.getSharedPreferences("GAME_PREFS", Context.MODE_PRIVATE);
        isSoundEnabled = sharedPreferences.getBoolean("sound", true);
        gameDifficulty = sharedPreferences.getString("difficulty", "normal");
        
        /*Check game difficulty from SharedPreferences to set accelRate*/ 
        if(gameDifficulty.equals("easy")) {
            accelRate = 1.45F;
        } else if(gameDifficulty.equals("hard")) {
            accelRate = 2.80F;
        } else {
            accelRate = 1.70F;
        }
    }

    /*Start Timer when start button is pressed*/
    public void startTimer() {
        // TODO Auto-generated method stub
        startTime = SystemClock.uptimeMillis(); //Get Start Time and store it
        handler = new Handler(); //Handler to update TextView
        timerUtil = new TimerUtil();
        handler.postDelayed(updateTimer, 0); //Start Handler to update timer textview
        isTimerStarted = true; //Set Timer started to true
    }

    /*Initialize Media and Vibrator*/
    private void initializeMedia() {
        // TODO Auto-generated method stub
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        bMedia = MediaPlayer.create(context, R.raw.barrel_hit);
        fMedia = MediaPlayer.create(context, R.raw.fence_hit);
    }

    //Initialize Object Coordinates
    private void initalizeCoordinates() {
        // TODO Auto-generated method stub

        /*Set horseStartPosition as [x,y]*/
        horseStartPosition = new float[] { width/2, height-50 };
        
        x = horseStartPosition[0];
        y = horseStartPosition[1];
        
        /*Calculate horse and BarrelRadius using width of Screen*/
        horseRadius = width/45;
        barrelRadius = width/45; 
        
        /*Calculate gateGap based on the horseRadius*/ 
        gateGap = (float) (horseRadius * 2.5);
        
        /*Margins for the fences*/
        leftMargin = rightMargin = width/7;
        topMargin = height/10;
        bottomMargin = height/7;
        
        /*Set X and Y coordinates for barrels 1,2,3*/
        barrel1X = (width/2);
        barrel1Y = (float) (height-height/2.75);
        
        barrel2X = (3*width)/4-rightMargin/2;
        barrel2Y = height/4;
        
        barrel3X = (width/4)+leftMargin/2;
        barrel3Y = height/4;
        
        /*Set fence1 StartX, StartY, StopX and StopY*/
        fence1StartX = leftMargin;
        fence1StartY = topMargin;
        fence1StopX = width-rightMargin;
        fence1StopY = topMargin;
        
        /*Set fence2 StartX, StartY, StopX and StopY*/
        fence2StartX = leftMargin;
        fence2StartY = topMargin;
        fence2StopX = leftMargin;
        fence2StopY = height-bottomMargin;
        
        /*Set fence3 StartX, StartY, StopX and StopY*/
        fence3StartX = width-rightMargin;
        fence3StartY = topMargin;
        fence3StopX = width-rightMargin;
        fence3StopY = height-bottomMargin;
        
        /*Set fence4 StartX, StartY, StopX and StopY*/
        fence4StartX = leftMargin;
        fence4StartY = height-bottomMargin;
        fence4StopX = (width/2)-gateGap/2;
        fence4StopY = height-bottomMargin;
        
        /*Set fence5 StartX, StartY, StopX and StopY*/
        fence5StartX = width-rightMargin;
        fence5StartY = height-bottomMargin;
        fence5StopX = (width/2)+gateGap/2;
        fence5StopY = height-bottomMargin;
        
    }

    /*Initialize the various Game View objects*/
    private void initializeObjects() {
        // TODO Auto-generated method stub
        
        horse = new Horse(context);
        
        /*Draw fences based on (x1,y1) and (x2,y2) points*/
        fence1 = new Fence(context, fence1StartX, fence1StartY, fence1StopX, fence1StopY);
        fence2 = new Fence(context, fence2StartX, fence2StartY, fence2StopX, fence2StopY);
        fence3 = new Fence(context, fence3StartX, fence3StartY, fence3StopX, fence3StopY);
        fence4 = new Fence(context, fence4StartX, fence4StartY, fence4StopX, fence4StopY);
        fence5 = new Fence(context, fence5StartX, fence5StartY, fence5StopX, fence5StopY);
        
        /*Draw barrels as circles based on (x,y,r)*/
        barrel1 = new Barrel(context, barrel1X, barrel1Y, barrelRadius);
        barrel2 = new Barrel(context, barrel2X, barrel2Y, barrelRadius);
        barrel3 = new Barrel(context, barrel3X, barrel3Y, barrelRadius);
        
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.barrel_circled_color));
    }
    
    /*Thread run()*/ 
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(isThreadRunning) {
            /*Check if holder Surface is valid*/
            if(holder.getSurface().isValid()) {
                /*Get Lock on Canvas for drawing*/
                canvas = holder.lockCanvas();
                /*Set Canvas Background*/
                canvas.drawColor(context.getResources().getColor(R.color.bg_color));
                
                /*Draw Fences*/
                fence1.draw(canvas);
                fence2.draw(canvas);
                fence3.draw(canvas);
                fence4.draw(canvas);
                fence5.draw(canvas);
                
                /*Draw Barrels*/
                barrel1.draw(canvas);
                barrel2.draw(canvas);
                barrel3.draw(canvas);
                
                /*Change X and Y of the Horse*/
                x += accelX;
                y += accelY;
                
                /*Don't Let the horse go down the screen, Set base value*/
                if(y > horseStartPosition[1]) {
                    y = horseStartPosition[1];
                }
                
                /*Draw horse*/
                horse.draw(x, y, horseRadius-5, canvas);
                
                /*Check if the horse has entered the area*/
                if(y <= height-bottomMargin+20-horseRadius) {
                    hasEntered = true;
                } else {
                    hasEntered = false;
                }
                
                /*Check collision with the barrels*/
                if(collides()) {
                    handleCollision();
                }
                
                /*Check Collision with the Fences*/
                if(collidesFence()) {
                    handleCollisionWithFence();
                } else {
                    isPenaltyReduced = false;
                }
                
                /*Check if horse has circled barrel1*/
                if(checkCircleBarrel(barrel1)) {
                    barrel1Circled = true;
                    barrel1.setmPaint(mPaint);
                }
                
                /*Check if horse has circled barrel2*/
                if(checkCircleBarrel(barrel2)) {
                    barrel2Circled = true;
                    barrel2.setmPaint(mPaint);
                }
                
                /*Check if horse has circled barrel3*/
                if(checkCircleBarrel(barrel3)) {
                    barrel3Circled = true;
                    barrel3.setmPaint(mPaint);
                }
                
                /*If all barrels are circled, stop Thread, Go to success activity */
                if(barrel1Circled &&  
                        barrel2Circled && barrel3Circled) {
                    /*Set thread state to false*/
                    isThreadRunning = false;
                    /*Interrupt thread*/
                    t.interrupt();
                    if(!isGameFinished) {
                        /*Start SuccessActivity, send elapsed time with Intent*/
                        Intent toSuccessActivity = new Intent(context, SuccessActivity.class);
                        toSuccessActivity.putExtra("timeElapsed", SystemClock.uptimeMillis()-startTime);
                        context.startActivity(toSuccessActivity);
                        ((Activity) context).finish();
                        //Set GameFinished to true
                        isGameFinished = true;
                    }
                }
                /*Unlock the canvas*/
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    /*Handle when Horse Collides with the Fence Object*/
    private void handleCollisionWithFence() {
        // TODO Auto-generated method stub
        if(!isPenaltyReduced) {
            if(isSoundEnabled) {
                /*If Sound is Enabled in Preferences play sound on collision*/
                fMedia.start();
            }
            if(vibrator.hasVibrator()) {
                /*If device has vibrator vibrate for 100ms*/
                vibrator.vibrate(100);
            }
            
            /*Reduce startTime by 5000ms to add penalty*/
            synchronized (TIMER_LOCK) {
                startTime -= 5000;
            }
            /*Set penalty reduced to true*/
            isPenaltyReduced = true;
        }
    }

    /*Check if the horse circled the Barrel object passed*/
    private boolean checkCircleBarrel(Barrel barrel) {
        // TODO Auto-generated method stub
        
        /*Check if horse has visited the bottomRightQuadrant
        If yes then set the flag to true*/
        if(x >= (barrel.getX()+barrelRadius+10) 
                && y >= (barrel.getY()+barrelRadius+10)) {
            barrel.setRightBottomQuad(true);
        }
        
        /*Check if horse has visited the topRightQuadrant
        If yes then set the flag to true*/
        if(x >= (barrel.getX()+barrelRadius+10) 
                && y <= (barrel.getY()-barrelRadius-10)) {
            barrel.setRightTopQuad(true);
        }
        
        /*Check if horse has visited the topLeftQuadrant
        If yes then set the flag to true*/
        if(x <= (barrel.getX()-barrelRadius-10) 
                && y <= (barrel.getY()-barrelRadius-10)) {
            barrel.setLeftTopQuad(true);
        }
        
        /*Check if horse has visited the bottomLeftQuadrant
        If yes then set the flag to true*/
        if(x <= (barrel.getX()-barrelRadius-10) 
                && y >= (barrel.getY()+barrelRadius+10)) {
            barrel.setLeftBottomQuad(true);
        }
        
        /*If the horse has entered the area and all barrels are circle 
         * return true*/
        return hasEntered && barrel.isCircled();
    }

    /*Check Collision with Fences*/
    private boolean collidesFence() {
        // TODO Auto-generated method stub
        
        boolean collidesFence = false;
        
        /*Check if it collides with left Fence, 
        if true then readjust horse*/
        if(x <= (horseRadius+fence2StartX)) {
            x = fence2StartX+horseRadius;
            collidesFence = true;
        }  
        
        /*Check if it collides with right Fence, 
        if true then readjust horse*/
        if(x >= (fence3StartX-horseRadius)) {
            x = fence3StartX-horseRadius;
            collidesFence = true;
        } 
        
        /*Check if it collides with top Fence, 
        if true then readjust horse*/
        if(y <= (horseRadius+fence1StartY)) {
            y = horseRadius+fence1StartY;
            collidesFence = true;
        }  
        
        /*Check if it collides with bottom Fence,
         * Check if the horse is not within the gateGap entry point
         * Check if horse is inside the area 
        if true then readjust horse*/
        if(y >= fence4StartY-horseRadius) {
            /*Check if the horse is not within the gateGap entry point*/
            if(x >= fence4StopX+10 && x <= fence5StopX-10) {
                collidesFence = false;
            } else {
                if(hasEntered) {
                    /*Readjust horse if inside area*/
                    y = fence4StartY-horseRadius;
                    collidesFence = true;
                } else {
                    /*If outside Readjust horse to initial value*/
                    y = horseStartPosition[1];
                    collidesFence = false;
                }
            }
        } 
        
        return collidesFence;
    }

    /*Handle collision with the Barrel*/
    private void handleCollision() {
        // TODO Auto-generated method stub
        
        /*Stop the Game by setting game state to false and interrupt thread */
        isThreadRunning = false;
        t.interrupt();
        
        if(!isGameFinished) {
            if(isSoundEnabled) {
                /*Play sound if sound is enabled in preferences*/
                bMedia.start();
            }
            if(vibrator.hasVibrator()) {
                /*Vibrate if device has a vibrator*/
                vibrator.vibrate(100);
            }
            /*Go to failure activity*/
            Intent toFailureActivity = new Intent(context, FailureActivity.class);
            context.startActivity(toFailureActivity);
            ((Activity) context).finish();
            /*Set Game finished to true*/
            isGameFinished = true;
        }
    }

    /*Check if horse collides with the barrel*/
    private boolean collides() {
        // TODO Auto-generated method stub
        
        /*If horse collides readjust the position of the horse*/
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

    /*Give a rebound to the horse on barrel collision*/
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

    /*Check for the criteria of two circles: horse and barrel colliding
    Condition to check:
    pow(x1-x2, 2) + pow(y1-y2, 2) <= pow(r1+r2, 2)*/
    private boolean checkCriteria(Barrel barrel) {
        return (Math.pow((x - barrel.getX()), 2) + Math.pow((y - barrel.getY()), 2)) 
                <= Math.pow((horseRadius + barrelRadius), 2);
    }
    
    /*Pause the Game Thread on Game Activity pause*/
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
    
    /*Resume Game Thread on Game Activity Resume*/
    public void resume() {
        isThreadRunning = true;
        t = new Thread(this);
        t.start();
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

    /*updateTimer runnable to update the values in textview*/
    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            timeDiffMil = (SystemClock.uptimeMillis() - startTime);
            
            /*Format time and set it to textview*/
            textViewTime.setText(timerUtil.formatTime(timeDiffMil));
            
            handler.postDelayed(this, 0);
        }
        
    };

    /*Check if the Motion Sensor has a change in X,Y or Z position*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        /*Check if the timer has been started*/
        if(isTimerStarted) {
            /*get X and Y acceleration from Sensor and multiply with accelRate*/
            accelX = (float) (event.values[1] * accelRate);
            accelY = (float) (event.values[0] * (accelRate * 1.5));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }
}
