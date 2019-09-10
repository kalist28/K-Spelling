package com.kalistdev.spelling.database;

import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class UserData {

    /** Special tag to save in Shared Preferences. */
    private static final String SAVE_TAG = "save";

    /** User Name. */
    private String mUserName;

    /** Number of completed levels. */
    private int mCompletedLevels;

    /** Number of points mScored. */
    private int mScored;

    /** SharedPreferences object. */
    private SharedPreferences mSharedPreferences;

    /**
     * Constructor - initialize object.
     * @param sharedPreferences - app mSharedPreferences object.
     */
    public UserData(final SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    /**
     * Initialize object.
     * @param name          - user Name.
     * @param completed     - number of completed levels.
     * @param scoredCount   - number of points mScored.
     */
    private void initialize(final String name,
                            final int completed,
                            final int scoredCount) {
        this.mUserName = name;
        this.mCompletedLevels = completed;
        this.mScored = scoredCount;
    }

    /** Load information. */
    public void load() {
        String info = mSharedPreferences.getString(SAVE_TAG, "null");
        if (!info.equals("null")) {
            SaveInfo sv = new Gson().fromJson(info, SaveInfo.class);
            initialize(sv.userName, sv.completedLevels, sv.scored);
        }
    }

    /** Save information. */
    @SuppressLint("CommitPrefEdits")
    public void save() {
        Editor editor   = mSharedPreferences.edit();
        SaveInfo sv     = new SaveInfo(mUserName, mCompletedLevels, mScored);
        String info     = new Gson().toJson(sv);

        editor.putString(SAVE_TAG, info).apply();
    }

    /**
     * Function to get value of field {@link UserData#mUserName}.
     * @return returns word.
     */
    public String getUserName() {
        return mUserName;
    }

    /** Function to set value of field {@link UserData#mUserName}.
     * @param userName - user name;
     */
    public void setUserName(final String userName) {
        this.mUserName = userName;
    }

    /**
     * Function to get value of field {@link UserData#mCompletedLevels}.
     * @return returns pass the level.
     */
    public int getCompletedLevels() {
        return mCompletedLevels;
    }

    /** Function to set value of field {@link UserData#mCompletedLevels}.
     * @param completedLevels - completed levels;
     */
    public void setCompletedLevels(final int completedLevels) {
        this.mCompletedLevels = completedLevels;
    }

    /** Function to add value of field {@link UserData#mCompletedLevels}. */
    public void appCompletedLevels() {
        this.mCompletedLevels++;
    }

    /**
     * Function to get value of field {@link UserData#mScored}.
     * @return returns mScored.
     */
    public int getScored() {
        return mScored;
    }

    /** Function to set value of field {@link UserData#mScored}.
     * @param scored - full scored.
     */
    public void setScored(final int scored) {
        this.mScored = scored;
    }

    /** Function to add value of field {@link UserData#mScored}.
     * @param scored - number of scored.
     */
    public void addScored(final int scored) {
        this.mScored += scored;
    }

    /** Example class for creating a json object save. */
    private static final class SaveInfo {
        /** User Name. */
        private String userName;

        /** Number of completed levels. */
        private int completedLevels;

        /** Number of points mScored. */
        private int scored;

        /**
         * Constructor - initialize object.
         * @param name  - user Name.
         * @param cl    - number of completed levels.
         * @param sc    - number of points mScored.
         */
        SaveInfo(final String name,
                 final int cl,
                 final int sc) {
            this.userName           = name;
            this.completedLevels    = cl;
            this.scored             = sc;
        }
    }
}
