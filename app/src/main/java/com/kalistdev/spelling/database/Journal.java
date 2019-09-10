package com.kalistdev.spelling.database;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Journal {

    /** Id of the word in database. */
    private int mId;

    /** Contains the ID of a specific word. */
    private int mIdWord;

    /**
     * Function to set value of field.
     * @param id        - position in journal.
     * @param idWord    - id of the word in the database.
     */
    Journal(final int id,
            final int idWord) {
        this.mId     = id;
        this.mIdWord = idWord;
    }

    /**
     * Function to get value of field {@link Journal#mId}.
     * @return returns Id of the word in database.
     */
    public long getlId() {
        return mId;
    }

    /**
     * Function to get value of field {@link Journal#mIdWord}.
     * @return the ID of the word.
     */
    public long getIdWord() {
        return mIdWord;
    }
}
