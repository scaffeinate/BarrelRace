package com.app.ui.assignment.barrelrace.util;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.ui.assignment.barrelrace.R;
import com.app.ui.assignment.barrelrace.objects.Score;

/**
* @author Sudharsanan Muralidharan
* @netid sxm149130
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.022
* @description Barrel Race Game for Android
* @module CustomAdapter: BaseAdapter for HighScores List
*/

/*BaseAdapter for the High scores List*/
@SuppressLint("InflateParams")
public class CustomAdapter extends BaseAdapter {

    private ArrayList<Score> scoresList;
    private LayoutInflater inflater;
    private TimerUtil timerUtil;
    
    /*Constructor*/
    public CustomAdapter(Activity activity, ArrayList<Score> scoresList) {
        this.scoresList = scoresList;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        timerUtil = new TimerUtil();
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return scoresList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return scoresList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*ViewHolder Class*/
    public static class ViewHolder {
        public TextView textViewName, textViewScore;
    }
    
    /*getView of each listItem*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        final ViewHolder holder;
        
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_item, null); //Inflate Item with Custom layout
            holder = new ViewHolder();
            //Initialize Views
            holder.textViewName = (TextView) vi
                    .findViewById(R.id.textViewName);
            holder.textViewScore = (TextView) vi.findViewById(R.id.textViewScore);
            
            vi.setTag(holder); //Set Tag to holder for each ListItem view
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        
        /*Set Values to Views*/
        holder.textViewName.setText(scoresList.get(position).getName());
        holder.textViewScore.setText(timerUtil.formatTime(scoresList.get(position).getScoreTime()));
        
        return vi;
    }
}
