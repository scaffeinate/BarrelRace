package com.app.ui.assignment.barrelrace;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ui.assignment.barrelrace.objects.Score;
import com.app.ui.assignment.barrelrace.util.CustomAdapter;
import com.app.ui.assignment.barrelrace.util.FileUtil;

/**
* @author Revanth Kumar
* @description Barrel Race Game for Android
* @module ViewScoresActivity: View High Scores
*/

/*View High Scores Activity*/
public class ViewScoresActivity extends Activity {

    private ListView listViewScores;
    private ProgressBar progressBar;
    private TextView textViewNoScores;
    private TextView textViewTitle;
    private Typeface titleFont;
    private ArrayList<Score> scoresList;
    private CustomAdapter adapter;
    private FileUtil fileUtil;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        listViewScores = (ListView) findViewById(R.id.listViewScores);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textViewNoScores = (TextView) findViewById(R.id.textViewNoScores);
        
        /*Set Custom Typeface to the title text*/
        titleFont = Typeface.createFromAsset(getAssets(), "fonts/title_font.ttf");
        textViewTitle.setTypeface(titleFont);
        
        scoresList = new ArrayList<Score>();
        fileUtil = new FileUtil();
    
        /*Fetch from file in an AsyncTask*/
        new FetchScoresTask().execute();
    }

    /*AsyncTask to fetch values from file*/
    private class FetchScoresTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            /*Fetch High scores and set it to CustomAdapter*/
            scoresList = fileUtil.fetchHighScores();
            adapter = new CustomAdapter(ViewScoresActivity.this, scoresList);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            /*Hide ProgressBar*/
            progressBar.setVisibility(View.GONE);
            /*If no scores are available*/
            if(scoresList.size() == 0) {
                Toast.makeText(getApplicationContext(), "No Scores Available", Toast.LENGTH_SHORT).show();
                textViewNoScores.setVisibility(View.VISIBLE);
            } else {
                /*Set list adapter*/
                listViewScores.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
