package com.app.ui.assignment.barrelrace.util;

/**
* @author Vasu Irneni
* @netid vxi140330
* @since 11/25/2014
* @purpose Homework Assignment 4 - Barrel Race Game CS 6301.015
* @description Barrel Race Game for Android
* @module TimerUtil
*/

/*Format Time based on the elapsedTime value*/
public class TimerUtil {
        
    public String formatTime(long timeElapsed) {
        int timeDiff = (int) (timeElapsed/1000);
        int minutes = timeDiff/60;
        int seconds = timeDiff % 60;
        int milliseconds = (int) (timeElapsed % 1000);

        return minutes + ":" + String.format("%02d", seconds) 
                + ":" + String.format("%03d", milliseconds);
    }
}
