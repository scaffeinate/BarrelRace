package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

import com.app.ui.assignment.barrelrace.views.BarrelRaceView;

public class GameActivity extends Activity {

    private BarrelRaceView barrelRaceView;
    private TextView textViewTime;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        setContentView(R.layout.activity_game);
        /*barrelRaceView = new BarrelRaceView(this, point.x, point.y);*/
        barrelRaceView = (BarrelRaceView) findViewById(R.id.barrelRaceView);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        barrelRaceView.initialize(this, point.x, point.y, textViewTime);
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
