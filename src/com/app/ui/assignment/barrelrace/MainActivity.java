package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private Button buttonPlay, buttonScores, buttonSettings;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonScores = (Button) findViewById(R.id.buttonScores);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);
        
        buttonPlay.setOnClickListener(this);
        buttonScores.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonPlay:
            Intent toGameActivity = new Intent(this, GameActivity.class);
            startActivity(toGameActivity);
            break;
        case R.id.buttonScores:
            break;
        case R.id.buttonSettings:
            break;
        }
    }
}
