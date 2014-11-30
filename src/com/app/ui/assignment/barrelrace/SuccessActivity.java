package com.app.ui.assignment.barrelrace;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.ui.assignment.barrelrace.objects.Score;
import com.app.ui.assignment.barrelrace.util.FileUtil;

public class SuccessActivity extends Activity implements OnClickListener {

    private Button buttonHome;
    private TextView textViewTime;
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
        buttonHome.setOnClickListener(this);
        
        fileUtil = new FileUtil();
        scoresList = new ArrayList<Score>();
        strBuilder = new StringBuilder();
        
        try {
            timeElapsed = getIntent().getLongExtra("timeElapsed", 0L);
            int timeDiff = (int) (timeElapsed/1000);
            int minutes = timeDiff/60;
            int seconds = timeDiff % 60;
            int milliseconds = (int) (timeElapsed % 1000);
   
            textViewTime.setText(minutes + ":" + String.format("%02d", seconds) 
                    + ":" + String.format("%03d", milliseconds));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        scoresList = fileUtil.fetchHighScores();
        
        scoresList.add(new Score("Test", timeElapsed));
        
        Collections.sort(scoresList, Score.scoreComparator);
        
        for(int i=0;i<10;i++) {
            strBuilder.append(scoresList.get(i).getName());
            strBuilder.append(":");
            strBuilder.append(scoresList.get(i).getScoreTime());
            strBuilder.append(System.lineSeparator());
        }
        
        fileUtil.writeToFile(strBuilder.toString());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
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
