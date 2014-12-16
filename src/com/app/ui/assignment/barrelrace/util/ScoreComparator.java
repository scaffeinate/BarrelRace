package com.app.ui.assignment.barrelrace.util;

import java.util.Comparator;

import com.app.ui.assignment.barrelrace.objects.Score;

/**
* @author Revanth Kumar
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
