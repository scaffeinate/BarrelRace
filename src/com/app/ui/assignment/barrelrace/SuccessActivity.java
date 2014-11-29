package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends Activity implements OnClickListener {

    private Button buttonHome;
    private TextView textViewTime;
    private Long timeElapsed = 0L;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        
        buttonHome = (Button) findViewById(R.id.buttonHome);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        buttonHome.setOnClickListener(this);
        
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
