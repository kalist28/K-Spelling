package com.kalistdev.spelling.database;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Test {

    /** Id test. */
    private int mId;

    /** Correct answer. */
    private int mSuccess;

    /** Wrong answer. */
    private int mFailure;

    /** Name test. */
    private String mName;

    /** Test description. */
    private String mInfo;

    /** Type and range words. */
    private String mRange;

    /**
     * Constructor - initialize object.
     * @param id        - id test.
     * @param success   - correct answer.
     * @param failure   - wrong answer.
     * @param name      - name test.
     * @param info      - test description.
     * @param range     - type and range words.
     */
     Test(final int id,
                final int success,
                final int failure,
                final String name,
                final String info,
                final String range) {
        this.mId            = id;
        this.mSuccess       = success;
        this.mFailure       = failure;
        this.mName          = name;
        this.mInfo          = info;
        this.mRange         = range;
    }

    /**
     * Function to get value of field {@link Test#mId}.
     * @return id test.
     */
    public int getId() {
        return mId;
    }

    /**
     * Function to get value of field {@link Test#mSuccess}.
     * @return correct answer.
     */
    public int getSuccess() {
        return mSuccess;
    }

    /**
     * Function to get value of field {@link Test#mFailure}.
     * @return wrong answer.
     */
    public int getFailure() {
        return mFailure;
    }

    /**
     * Function to get value of field {@link Test#mName}.
     * @return name test.
     */
    public String getName() {
        return mName;
    }

    /**
     * Function to get value of field {@link Test#mInfo}.
     * @return test description.
     */
    public String getInfo() {
        return mInfo;
    }

    /**
     * Function to get value of field {@link Test#mRange}.
     * @return type and range words.
     */
    public String getRange() {
        return mRange;
    }
}
