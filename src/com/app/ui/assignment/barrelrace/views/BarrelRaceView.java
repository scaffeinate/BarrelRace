package com.app.ui.assignment.barrelrace.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class BarrelRaceView extends SurfaceView implements Runnable, SensorEventListener, Callback {

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
    private float gateGap;
    private float leftMargin, rightMargin, topMargin, bottomMargin;
    private float fence1StartX, fence1StartY, fence1StopX, fence1StopY;
    private float fence2StartX, fence2StartY, fence2StopX, fence2StopY;
    private float fence3StartX, fence3StartY, fence3StopX, fence3StopY;
    private float fence4StartX, fence4StartY, fence4StopX, fence4StopY;
    private float fence5StartX, fence5StartY, fence5StopX, fence5StopY;
    private float horseRadius, barrelRadius;
    private float[] horseStartPosition;
    private float accelRate;
    private boolean hasEntered = false;
    private boolean isGameFinished = false;
    private boolean isPenaltyReduced = false;
    private boolean isTimerStarted = false;
    private boolean barrel1Circled, barrel2Circled, barrel3Circled;
    private boolean isSoundEnabled = false;
    private String gameDifficulty;
    private Paint mPaint;
    private Object TIMER_LOCK = new Object();
    
    private long startTime = 0L, timeDiffMil = 0L;
    
    private Vibrator vibrator;
    private MediaPlayer bMedia, fMedia;
    private TextView textViewTime;
    private Handler handler;
    private TimerUtil timerUtil;
    
    private SharedPreferences sharedPreferences;
    
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
        initializeGamePrefs();
        this.textViewTime = textViewTime;
    }

    private void initializeGamePrefs() {
        // TODO Auto-generated method stub
        sharedPreferences = context.getSharedPreferences("GAME_PREFS", Context.MODE_PRIVATE);
        isSoundEnabled = sharedPreferences.getBoolean("sound", true);
        gameDifficulty = sharedPreferences.getString("difficulty", "normal");
        
        if(gameDifficulty.equals("easy")) {
            accelRate = 1.45F;
        } else if(gameDifficulty.equals("hard")) {
            accelRate = 2.80F;
        } else {
            accelRate = 1.70F;
        }
    }

    public void startTimer() {
        // TODO Auto-generated method stub
        startTime = SystemClock.uptimeMillis();
        handler = new Handler();
        timerUtil = new TimerUtil();
        handler.postDelayed(updateTimer, 0);
        isTimerStarted = true;
    }

    private void initializeMedia() {
        // TODO Auto-generated method stub
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        bMedia = MediaPlayer.create(context, R.raw.barrel_hit);
        fMedia = MediaPlayer.create(context, R.raw.fence_hit);
    }

    private void initalizeCoordinates() {
        // TODO Auto-generated method stub
        
        horseStartPosition = new float[] { width/2, height-50 };
        
        x = horseStartPosition[0];
        y = horseStartPosition[1];
        
        horseRadius = width/45;
        barrelRadius = width/45; 
        
        gateGap = (float) (horseRadius * 2.5);
        
        leftMargin = rightMargin = width/7;
        topMargin = height/10;
        bottomMargin = height/7;
        
        barrel1X = (width/2);
        barrel1Y = (float) (height-height/2.75);
        
        barrel2X = (3*width)/4-rightMargin/2;
        barrel2Y = height/4;
        
        barrel3X = (width/4)+leftMargin/2;
        barrel3Y = height/4;
        
        fence1StartX = leftMargin;
        fence1StartY = topMargin;
        fence1StopX = width-rightMargin;
        fence1StopY = topMargin;
        
        fence2StartX = leftMargin;
        fence2StartY = topMargin;
        fence2StopX = leftMargin;
        fence2StopY = height-bottomMargin;
        
        fence3StartX = width-rightMargin;
        fence3StartY = topMargin;
        fence3StopX = width-rightMargin;
        fence3StopY = height-bottomMargin;
        
        fence4StartX = leftMargin;
        fence4StartY = height-bottomMargin;
        fence4StopX = (width/2)-gateGap/2;
        fence4StopY = height-bottomMargin;
        
        fence5StartX = width-rightMargin;
        fence5StartY = height-bottomMargin;
        fence5StopX = (width/2)+gateGap/2;
        fence5StopY = height-bottomMargin;
        
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
        
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(isThreadRunning) {
            if(holder.getSurface().isValid()) {
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
                
                if(y > horseStartPosition[1]) {
                    y = horseStartPosition[1];
                }
                
                horse.draw(x, y, horseRadius-5, canvas);
                
                if(y <= height-bottomMargin+20-horseRadius) {
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
                
                if(checkCircleBarrel(barrel1)) {
                    barrel1Circled = true;
                    barrel1.setmPaint(mPaint);
                }
                
                if(checkCircleBarrel(barrel2)) {
                    barrel2Circled = true;
                    barrel2.setmPaint(mPaint);
                }
                
                if(checkCircleBarrel(barrel3)) {
                    barrel3Circled = true;
                    barrel3.setmPaint(mPaint);
                }
                
                if(barrel1Circled &&  
                        barrel2Circled && barrel3Circled) {
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
    }
    
    private void handleCollisionWithFence() {
        // TODO Auto-generated method stub
        if(!isPenaltyReduced) {
            if(isSoundEnabled) {
                fMedia.start();
            }
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
        
        if(x <= (horseRadius+fence2StartX)) {
            x = fence2StartX+horseRadius;
            collidesFence = true;
        }  
        
        if(x >= (fence3StartX-horseRadius)) {
            x = fence3StartX-horseRadius;
            collidesFence = true;
        } 
        
        if(y <= (horseRadius+fence1StartY)) {
            y = horseRadius+fence1StartY;
            collidesFence = true;
        }  
        
        if(y >= fence4StartY-horseRadius) {
            if(x >= fence4StopX+10 && x <= fence5StopX-10) {
                collidesFence = false;
            } else {
                if(hasEntered) {
                    y = fence4StartY-horseRadius;
                    collidesFence = true;
                } else {
                    y = horseStartPosition[1];
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
            if(isSoundEnabled) {
                bMedia.start();
            }
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
            
            textViewTime.setText(timerUtil.formatTime(timeDiffMil));
        
            handler.postDelayed(this, 0);
        }
        
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if(isTimerStarted) {
            accelX = (float) (event.values[1] * accelRate);
            accelY = (float) (event.values[0] * (accelRate * 1.5));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        
    }
    
}
