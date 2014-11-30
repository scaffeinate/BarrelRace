package com.app.ui.assignment.barrelrace.util;

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
