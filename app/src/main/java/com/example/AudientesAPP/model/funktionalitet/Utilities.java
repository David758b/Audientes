package com.example.AudientesAPP.model.funktionalitet;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

public class Utilities {

    /**
     * Metode til at få procenten af progress
     * @param currentDuration
     * @param totalDuration
     * @return percentage som en int value
     */
    public int getProgressPercentage(long currentDuration, long totalDuration){
        double percentage;

        long currentSec = (int) (currentDuration / 1000);
        long totalSec = (int) (totalDuration / 1000);

        //beregning af procent
        percentage = (((double)currentSec)/totalSec)*100;

        return (int) percentage;
    }

    /**
     *  Metode til at ændre progress til timer(engelsk)
     * @param progress
     * @param totalDuration
     * @return nuværende duration i milisec
     */
    public int progressToTimer(int progress, int totalDuration){
        int currentDuration;
        totalDuration = (int) (totalDuration/1000);
        currentDuration = (int) ((((double)progress)/100)* totalDuration);

        //i millisekunder
        return currentDuration * 1000;
    }


    @SuppressLint("DefaultLocale")
    public String convertFormat(long duration){
        return String.format("%02d:%02d"
                , TimeUnit.MILLISECONDS.toMinutes(duration)
                , TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
