package com.app.ui.assignment.barrelrace.objects;

import java.util.Comparator;

/**
* @author Revanth Kumar
* @netid rxa142230
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module Score: Score Bean Object
*/

public class Score {
    
    private long scoreTime;
    private String name;
    
    public Score(String name, long scoreTime) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.scoreTime = scoreTime;
    }

    public long getScoreTime() {
        return scoreTime;
    }
    
    public void setScoreTime(long scoreTime) {
        this.scoreTime = scoreTime;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public static Comparator<Score> scoreComparator =
            new Comparator<Score>() {
        public int compare(Score s1 , Score s2) {
            long l1 = s1.getScoreTime();
            long l2 = s2.getScoreTime();
            return (int) (l2-l1);
        }
    };

}
