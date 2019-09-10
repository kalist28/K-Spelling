package com.kalistdev.spelling.database;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Level {

    /** First word ID for level. */
    private static final int MIN_VALUE = 0;

    /** Last word ID for level. */
    private static final int MAX_VALUE = 1;

    /** Id of the word in database. */
    private int mId;

    /** Range of words from the General dictionary for a certain level. */
    private String mRange;

    /** Number of access points. */
    private int mScore;

    /** Contains the number of points score.  */
    private int mPrice;

    /**
     * Constructor - initialize object.
     * @param id        - id level.
     * @param range     - word limit (example 2-6).
     * @param score     - number of access points.
     * @param price     - the prize for the user.
     */
    Level(final int id,
          final String range,
          final int score,
          final int price) {
        this.mId    = id;
        this.mRange = range;
        this.mScore = score;
        this.mPrice = price;
    }

    /**
     * Function to get value of field {@link Level#mId}.
     * @return returns Id of the word in database.
     */
    public long getlId() {
        return mId;
    }


    /**
     * Function to get value of field {@link Level#mRange}.
     * @return the ID of the last word in the dictionary for the level.
     */
     public int getMaxValue() {
         String[] mas = mRange.split("-");
         return Integer.parseInt(mas[MAX_VALUE]);
    }

    /**
     * Function to get value of field {@link Level#mRange}.
     * @return the ID of the first word in the dictionary for the level.
     */
    public int getMinValue() {
        String[] mas = mRange.split("-");
        return Integer.parseInt(mas[MIN_VALUE]);
    }

    /**
     * Function to get value of field.
     * @return returns answer choice.
     */
    public int getCountWords() {
        return getMaxValue() - getMinValue() + 1;
    }

    /**
     * Function to get value of field {@link Level#mScore}.
     * @return returns score count.
     */
    public int getScoredCount() {
        return mScore;
    }

    /**
     * Function to get value of field {@link Level#mPrice}.
     * @return returns score count.
     */
    public int getPrice() {
        return mPrice;
    }
}
