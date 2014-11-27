package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FailureActivity extends Activity implements OnClickListener {

    private Button buttonTryAgain, buttonHome;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        
        buttonTryAgain = (Button) findViewById(R.id.buttonTryAgain);
        buttonHome = (Button) findViewById(R.id.buttonHome);
    
        buttonTryAgain.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonTryAgain:
            Intent toGameActivity = new Intent(this, GameActivity.class);
            startActivity(toGameActivity);
            break;
        case R.id.buttonHome:
            Intent toMain = new Intent(this, MainActivity.class);
            startActivity(toMain);
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
