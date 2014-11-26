package com.app.ui.assignment.barrelrace.views;

import com.app.ui.assignment.barrelrace.R;
import com.app.ui.assignment.barrelrace.R.color;
import com.app.ui.assignment.barrelrace.R.drawable;
import com.app.ui.assignment.barrelrace.objects.Barrel;
import com.app.ui.assignment.barrelrace.objects.Fence;
import com.app.ui.assignment.barrelrace.objects.Horse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BarrelRaceView extends SurfaceView implements Runnable {

    Thread t = null;
    SurfaceHolder holder;
    boolean isThreadRunning = true;
    private Context context;
    private Canvas canvas;
    private Fence fence1, fence2, fence3, fence4, fence5;
    private Horse horse;
    private Barrel barrel1, barrel2, barrel3;
    
    private Bitmap barrelBitmap;
    
    public BarrelRaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        holder = getHolder();
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
            
            fence1.draw(20, 20, getWidth()-20, 20, canvas);
            fence2.draw(20, 20, 20, getHeight()-80, canvas);
            fence3.draw(getWidth()-20, 20, getWidth()-20, getHeight()-80, canvas);
            fence4.draw(20, getHeight()-80, (getWidth()/2)-50, getHeight()-80, canvas);
            fence5.draw(getWidth()-20, getHeight()-80, (getWidth()/2)+50, getHeight()-80, canvas);
            
            barrel1.draw(barrelBitmap, getWidth()/2-35, getHeight()-245, canvas);
            barrel2.draw(barrelBitmap, getWidth() - 235, 100, canvas);
            barrel3.draw(barrelBitmap, 165, 100, canvas);
            
            horse.draw(canvas.getWidth()/2, canvas.getHeight() - 50, 20, canvas);
            
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

}
