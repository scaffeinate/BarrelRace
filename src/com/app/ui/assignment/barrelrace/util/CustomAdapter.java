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

@SuppressLint("InflateParams")
public class CustomAdapter extends BaseAdapter {

    private ArrayList<Score> scoresList;
    private LayoutInflater inflater;
    
    public CustomAdapter(Activity activity, ArrayList<Score> scoresList) {
        this.scoresList = scoresList;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static class ViewHolder {
        public TextView textViewName, textViewScore;
    }
    
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
        holder.textViewScore.setText(String.valueOf(scoresList.get(position).getScoreTime()));
        
        return vi;
    }
}
