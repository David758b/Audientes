package com.example.AudientesAPP.model.funktionalitet;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesTest {

    @Test
    public void getProgressPercentage() {
        Utilities utilities = new Utilities();
        long currentDuration = 10000;
        long TotalDuration = 100000;

        int progressPercentage = utilities.getProgressPercentage(currentDuration, TotalDuration);

        assertEquals(10, progressPercentage);
    }

    @Test
    public void progressToTimer() {
        Utilities utilities = new Utilities();
        int progress = 20;
        int totalDuration = 10000;
        int currentDuration = utilities.progressToTimer(progress, totalDuration);
        assertEquals(2000, currentDuration);
    }

    @Test
    public void convertFormat() {
        Utilities utilities = new Utilities();

        long duration = 10000;
        String time = utilities.convertFormat(duration);

        assertEquals("0:10", time);

        duration = 5000000;
        assertEquals("1:23:20",utilities.convertFormat(duration));
    }

}