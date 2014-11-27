package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.app.ui.assignment.barrelrace.views.BarrelRaceView;

public class GameActivity extends Activity {

    private BarrelRaceView barrelRaceView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        barrelRaceView = new BarrelRaceView(this, point.x, point.y);
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
    
}
