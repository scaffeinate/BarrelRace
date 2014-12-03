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
* @author Revanth Kumar, Vasu Irneni
* @netid rxa142230, vxi140330
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module MainActivty: Launcher Activity
*/

/*Launcher Activity*/
public class MainActivity extends Activity implements OnClickListener {

    private Button buttonPlay, buttonScores, buttonSettings;
    private TextView textViewTitle;
    private Typeface titleFont;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonScores = (Button) findViewById(R.id.buttonScores);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        
        /*Set Custom Typeface to the TitleText*/
        titleFont = Typeface.createFromAsset(getAssets(), "fonts/title_font.ttf");
        textViewTitle.setTypeface(titleFont);
        
        /*Handle button Clicks*/
        buttonPlay.setOnClickListener(this);
        buttonScores.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
    }

    /*On Button click start activities*/
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonPlay:
            Intent toGameActivity = new Intent(this, GameActivity.class);
            startActivity(toGameActivity);
            break;
        case R.id.buttonScores:
            Intent toViewScoresActivity = new Intent(this, ViewScoresActivity.class);
            startActivity(toViewScoresActivity);
            break;
        case R.id.buttonSettings:
            Intent toSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(toSettingsActivity);
            break;
        }
    }
}
