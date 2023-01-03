/*
 * ****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2022
 * Instructor: Prof. Brian King
 *
 * Name: Team 8
 * Section: Section 2
 * Date: 11/29/22
 * Time: 9:00 PM
 *
 * Project: csci205FinalProject
 * Package: org.csci205Team08.model
 * Class: StopwatchTest
 *
 * Description: Tests the functionality of the StopWatch class
 *
 * ****************************************
 */

package org.csci205Team08.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests {@link Stopwatch}
 * It tests only methods related to formatting the time.
 */
class StopwatchTest {

    /**
     * A test to make sure that formatConciseTime method in Stopwatch class is working
     * converts the seconds into HH:MM:SS format
     */
    @Test
    void formatConciseTime() {
        String test1 = Stopwatch.formatConciseTime(6000);
        String test2 = Stopwatch.formatConciseTime(1000);
        String test3 = Stopwatch.formatConciseTime(40);

        assertEquals("1:40:00", test1);
        assertEquals("16:40", test2);
        assertEquals("00:40", test3);

    }

    /**
     * A test to make sure that formatDetailedTime method in Stopwatch class is working
     * returns a string where it tells you how many hours, minutes and seconds that the time took
     */
    @Test
    void formatDetailedTime() {
        String test1 = Stopwatch.formatDetailedTime(5500);
        String test2 = Stopwatch.formatDetailedTime(798);
        String test3 = Stopwatch.formatDetailedTime(37);

        assertEquals("1 hours 31 minutes 40 seconds", test1);
        assertEquals("13 minutes 18 seconds", test2);
        assertEquals("37 seconds", test3);

    }
}