package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
* @author Vasu Irneni
* @netid vxi140330
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module FailureActivity: Once Game Fails
*/

/*Activity Launched when Game Fails to be completed successfully*/
public class FailureActivity extends Activity implements OnClickListener {

    private Button buttonTryAgain, buttonHome;
    private TextView textViewTitle;
    private Typeface titleFont;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        buttonTryAgain = (Button) findViewById(R.id.buttonTryAgain);
        buttonHome = (Button) findViewById(R.id.buttonHome);
    
        /*Set Custom Typeface to the title*/
        titleFont = Typeface.createFromAsset(getAssets(), "fonts/title_font.ttf");
        textViewTitle.setTypeface(titleFont);
        
        buttonTryAgain.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
    }

    /*Handle button Onclick*/ 
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonTryAgain:
            Intent toGameActivity = new Intent(this, GameActivity.class);
            startActivity(toGameActivity);
            break;
        case R.id.buttonHome:
            finish();
            break;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
