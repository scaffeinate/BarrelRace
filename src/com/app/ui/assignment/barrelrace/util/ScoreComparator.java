package com.app.ui.assignment.barrelrace.util;

import java.util.Comparator;

import com.app.ui.assignment.barrelrace.objects.Score;

/**
* @author Revanth Kumar
* @netid rxa142230
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module ScoreComparator
*/

/*To Compare two Objects*/
public class ScoreComparator implements Comparator<Score>{

    @Override
    public int compare(Score s1, Score s2) {
        // TODO Auto-generated method stub
        return Long.compare(s1.getScoreTime(), s2.getScoreTime());
    }

}
