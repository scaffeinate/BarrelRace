package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements OnTouchListener {

    public class OurView extends SurfaceView implements Runnable {

        Thread thread = null;
        SurfaceHolder holder;
        boolean isThread = false;
        
        public OurView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            holder = getHolder();
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(isThread == true) {
                if(!holder.getSurface().isValid()) {
                    continue;
                }
                
                Canvas c = holder.lockCanvas();
                c.drawARGB(255, 150, 250, 100);
                
                Fence f1 = new Fence();
                f1.draw(20, 20, getWidth()-20, 20, c);
                
                Fence f2 = new Fence();
                f2.draw(20, 20, 20, getHeight()-100, c);
                
                Fence f3 = new Fence();
                f3.draw(getWidth()-20, 20, getWidth()-20, getHeight()-100, c);
                
                Fence f4 = new Fence();
                f4.draw(20, getHeight()-100, (getWidth()/2)-50, getHeight()-100, c);
                
                Fence f5 = new Fence();
                f5.draw(getWidth()-20, getHeight()-100, (getWidth()/2)+50, getHeight()-100, c);
                
                Horse h = new Horse();
                h.draw(c.getWidth()/2, c.getHeight() - 50, 20, c);
                Barrel b1 = new Barrel();
                b1.draw(getWidth()/2, getHeight()-225, 25, c);
                Barrel b2 = new Barrel();
                b2.draw(getWidth() - 200, 125, 25, c);
                Barrel b3 = new Barrel();
                b3.draw(200, 125, 25, c);
                holder.unlockCanvasAndPost(c);
            }
        }
        
        public void pause() {
            isThread = false;
            while(true) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
            
            thread = null;
        }
        
        public void resume() {
            isThread = true;
            thread = new Thread(this);
            thread.start();
        }
        
    }

    OurView v;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        v = new OurView(this);
        v.setOnTouchListener(this);
        setContentView(v);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        v.resume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        v.pause();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
