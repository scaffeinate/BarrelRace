package com.app.ui.assignment.barrelrace.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;

import com.app.ui.assignment.barrelrace.objects.Score;

public class FileUtil {
    private FileOutputStream fileOutputStream;
    private BufferedReader bufferedReader;
    
    public boolean writeToFile(String stringToWrite) {
        boolean success = false;

        if(isWritableToStorage()) {
            File fileToWrite = new File(Environment.getExternalStorageDirectory(), "highscores.txt"); 
            try {
                if(!fileToWrite.exists()) {
                    fileToWrite.createNewFile();
                }
                fileOutputStream = new FileOutputStream(fileToWrite);
                fileOutputStream.write(stringToWrite.getBytes());
                fileOutputStream.close();
                success = true;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return success;
    }
    
    /*Read from File*/
    public ArrayList<Score> fetchHighScores() {
        String str;

        ArrayList<Score> resultArray = new ArrayList<Score>();
        if(isWritableToStorage()) {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "highscores.txt");
                if(!file.exists()){
                    file.createNewFile();
                }
                bufferedReader = new BufferedReader(new FileReader(file));
                while((str = bufferedReader.readLine()) != null){
                    String[] tempStr = str.split(":");
                    resultArray.add(new Score(tempStr[0], Long.parseLong(tempStr[1])));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultArray;
    }
    
    /*Check Writable to storage*/
    private boolean isWritableToStorage() {
        String storageState = Environment.getExternalStorageState();

        if(storageState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }

        return false;
    }
}
