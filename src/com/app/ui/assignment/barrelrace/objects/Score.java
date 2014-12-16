package com.app.ui.assignment.barrelrace.objects;


/**
* @author Revanth Kumar
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
