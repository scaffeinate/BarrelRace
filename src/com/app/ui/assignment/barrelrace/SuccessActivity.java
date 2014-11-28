package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SuccessActivity extends Activity implements OnClickListener {

    private Button buttonHome;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        
        buttonHome = (Button) findViewById(R.id.buttonHome);
        
        buttonHome.setOnClickListener(this);
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
