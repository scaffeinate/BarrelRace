package com.app.ui.assignment.barrelrace;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
* @author Sudharsanan Muralidharan
* @description Barrel Race Game for Android
* @module SettingsActivity: App Preferences Activity
*/

/*Game  Preferences are handled in this Activity*/
public class SettingsActivity extends Activity implements OnClickListener {

    private RadioGroup radioGroupDiff, radioGroupSound;
    private RadioButton radioDiffButton, radioSoundButton;
    private TextView textViewTitle;
    private Typeface titleFont;
    private Button buttonSave;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        /*SharedPreferences Initialization*/
        sharedPreferences = getSharedPreferences("GAME_PREFS", Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        radioGroupDiff = (RadioGroup) findViewById(R.id.radioGroupDiff);
        radioGroupSound = (RadioGroup) findViewById(R.id.radioGroupSound);
        
        buttonSave = (Button) findViewById(R.id.buttonSave);
        
        /*Set Custom Typeface to the title*/
        titleFont = Typeface.createFromAsset(getAssets(), "fonts/title_font.ttf");
        textViewTitle.setTypeface(titleFont);
        
        buttonSave.setOnClickListener(this);
        
        /*Check for already present values
         * If present update the views*/
        String difficulty = sharedPreferences.getString("difficulty", "normal");
        
        if(difficulty.equals("easy")) {
            radioDiffButton = (RadioButton) findViewById(R.id.radioButtonEasy);
            radioDiffButton.setChecked(true);
        } else if(difficulty.equals("hard")) {
            radioDiffButton = (RadioButton) findViewById(R.id.radioButtonHard);
            radioDiffButton.setChecked(true);
        }
        
        boolean sound = sharedPreferences.getBoolean("sound", true);
        if(!sound) {
            radioSoundButton = (RadioButton) findViewById(R.id.radioButtonSoundOff);
            radioSoundButton.setChecked(true);
        }
    }

    /*Handle button onCick*/
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
        case R.id.buttonSave:
            
            radioDiffButton = (RadioButton) findViewById(radioGroupDiff.getCheckedRadioButtonId());
            radioSoundButton = (RadioButton) findViewById(radioGroupSound.getCheckedRadioButtonId());
            
            /*Write the changes to Shared Preferences*/
            sharedEditor.putString("difficulty", radioDiffButton.getText().toString().toLowerCase());
            if(radioSoundButton.getText().toString().toLowerCase().equals("on")) {
                sharedEditor.putBoolean("sound", true);
            } else {
                sharedEditor.putBoolean("sound", false);
            }
            
            sharedEditor.commit();
            Toast.makeText(getApplicationContext(), "Successfully saved your preferences", Toast.LENGTH_SHORT).show();
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
