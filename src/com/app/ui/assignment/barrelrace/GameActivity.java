package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.ui.assignment.barrelrace.views.BarrelRaceView;

/**
* @author Sudharsanan Muralidharan
* @netid sxm149130
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.022
* @description Barrel Race Game for Android
* @module GameActivity: Main Game Activity
*/

public class GameActivity extends Activity implements OnClickListener {

    private BarrelRaceView barrelRaceView;
    private TextView textViewTime;
    private Button buttonStart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        setContentView(R.layout.activity_game);
        barrelRaceView = (BarrelRaceView) findViewById(R.id.barrelRaceView);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        barrelRaceView.initialize(this, point.x, point.y, textViewTime);
    
        buttonStart.setOnClickListener(this);
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonStart:
            barrelRaceView.startTimer();
            break;
        }
    }
    
}
