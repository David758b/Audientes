package com.example.AudientesAPP.model.funktionalitet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.ActivityChooserView;

import java.util.concurrent.TimeUnit;

public class Utilities {
    // TODO: Vi bør nok referer utilities gennem modelViewController istedet for direkte i UI'en

    /**
     * Metode til at få procenten af progress
     * @param currentDuration
     * @param totalDuration
     * @return percentage som en int value
     */
    public static int getProgressPercentage(long currentDuration, long totalDuration){
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
    public static int progressToTimer(int progress, int totalDuration){
        int currentDuration;
        totalDuration = (int) (totalDuration/1000);
        currentDuration = (int) ((((double)progress)/100)* totalDuration);

        //i millisekunder
        return currentDuration * 1000;
    }


    @SuppressLint("DefaultLocale")
    public static String convertFormat(long durationMilliSec){
//        return String.format("%02d:%02d"
//                , TimeUnit.MILLISECONDS.toMinutes(duration)
//                , TimeUnit.MILLISECONDS.toSeconds(duration) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        String finalString = "";
        String secondString = "";

        int hours = (int) (durationMilliSec / (1000 * 60 * 60));
        int minutes = (int) (durationMilliSec % (1000 * 60 * 60)) / (1000*60);
        int seconds = (int) ((durationMilliSec % (1000 * 60 * 60)) % (1000*60)) / 1000;
        if (hours > 0){
            finalString = hours + ":";
        }
        if(seconds < 10){
            secondString = "0" + seconds;
        } else {
            secondString = "" + seconds;
        }
        return finalString = finalString + minutes + ":" + secondString;

    }

    public static void hideKeyboard(View v, Activity activity) {
        // Fjerner keyboard fra skærmen
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
