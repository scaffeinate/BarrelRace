package com.app.ui.assignment.barrelrace.objects;


/**
* @author Revanth Kumar
* @netid rxa142230
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module Score: Score Bean Object
*/

/*Score Bean Class*/
public class Score {
    
    private long scoreTime;
    private String name;
    
    /*Constructor*/
    public Score(String name, long scoreTime) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.scoreTime = scoreTime;
    }

    /*Getters and Setters*/
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
}
