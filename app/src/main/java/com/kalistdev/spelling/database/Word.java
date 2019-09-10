package com.kalistdev.spelling.database;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Word {

    /** Id of the word in database. */
    private int mId;

    /** Answer choice. */
    private String mOptions;

    /** Type of the word. */
    private int mIdWordJournal;

    /** Type of the word. */
    private int mType;

    public Word() {
    }

    /**
     * Constructor - initialize object.
     * @param options       - answer choice.
     * @param id            - Id of the word in database.
     * @param type          - mType of the word.
     */
    Word(final int id,
         final String options,
         final int type) {
        this.mOptions = options;
        this.mId = id;
        this.mType = type;
    }

    /**
     * Function to get value of field {@link Word#mId}.
     * @return returns Id of the word in database.
     */
    public int getId() {
        return mId;
    }


    /**
     * Function to get value of field {@link Word#mOptions}.
     * @return returns answer choice.
     */
    public String getOptions() {
        return mOptions;
    }

    /**
     * Function to get value of field {@link Word#mType}.
     * @return returns mType of the word.
     */
    public int getType() {
        return mType;
    }

    /**
     * Function to get value of field {@link Word#mIdWordJournal}.
     * @return returns id word in journal.
     */
    public int getIdWordJournal() {
        return mIdWordJournal;
    }

    /**
     *  Function to set value of field.
     * @param idWordJournal - id word in journal.
     */
    public void setIdWordJournal(final int idWordJournal) {
        this.mIdWordJournal = idWordJournal;
    }
}
