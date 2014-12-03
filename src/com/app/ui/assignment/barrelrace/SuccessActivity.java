package com.app.ui.assignment.barrelrace;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ui.assignment.barrelrace.objects.Score;
import com.app.ui.assignment.barrelrace.util.FileUtil;
import com.app.ui.assignment.barrelrace.util.ScoreComparator;
import com.app.ui.assignment.barrelrace.util.TimerUtil;

/**
* @author Vasu Irneni
* @netid vxi140330
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module SuccessActivity: Once Game Finishes Successfully
*/

public class SuccessActivity extends Activity implements OnClickListener {

    private Button buttonHome;
    private TextView textViewTime;
    private EditText editTextName;
    private Long timeElapsed = 0L;
    
    private FileUtil fileUtil;
    private ArrayList<Score> scoresList;
    private StringBuilder strBuilder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        
        buttonHome = (Button) findViewById(R.id.buttonHome);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonHome.setOnClickListener(this);
        
        fileUtil = new FileUtil();
        scoresList = new ArrayList<Score>();
        strBuilder = new StringBuilder();
        
        try {
            timeElapsed = getIntent().getLongExtra("timeElapsed", 0L);
            textViewTime.setText(new TimerUtil().formatTime(timeElapsed));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonHome:
            if(editTextName.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Enter Name to continue", Toast.LENGTH_SHORT).show();
            } else {
                scoresList = fileUtil.fetchHighScores();
                scoresList.add(new Score(editTextName.getText().toString(), timeElapsed));
                
                Collections.sort(scoresList, new ScoreComparator());
                
                int size = 10;
                
                if(scoresList.size() < 10) {
                    size = scoresList.size();
                }
                
                for(int i=0;i<size;i++) {
                    strBuilder.append(scoresList.get(i).getName());
                    strBuilder.append(":");
                    strBuilder.append(scoresList.get(i).getScoreTime());
                    strBuilder.append(System.lineSeparator());
                }
                
                fileUtil.writeToFile(strBuilder.toString());
                finish(); 
            }
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
