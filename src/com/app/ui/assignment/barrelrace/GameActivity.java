package com.app.ui.assignment.barrelrace;

import com.app.ui.assignment.barrelrace.views.BarrelRaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameActivity extends Activity implements OnTouchListener {

    private BarrelRaceView barrelRaceView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        barrelRaceView = new BarrelRaceView(this);
        barrelRaceView.setOnTouchListener(this);
        setContentView(barrelRaceView);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        barrelRaceView.resume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        barrelRaceView.pause();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
