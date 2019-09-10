package com.kalistdev.spelling.database;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.database.Cursor;
import android.content.Context;
import java.io.FileOutputStream;
import android.content.ContentValues;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Helper extends SQLiteOpenHelper {

    /** Name mDatabase in /assets. */
    private static final String DB_NAME = "words_db.db";

    /** Database version. */
    private static final int DB_VERSION = 3;

    /** The path to the database. */
    private static String mDbPath = "";

    /** App context. */
    private final Context mContext;

    /** Database object. */
    private SQLiteDatabase mDatabase;

    /**
     * Constructor - initialize object.
     * @param context - app context.
     */
    public Helper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        final byte deviceVersion = 17;
        if (android.os.Build.VERSION.SDK_INT >= deviceVersion) {
            mDbPath = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            mDbPath = context.getFilesDir().getPath()
                    + context.getPackageName()
                    + "/databases/";
        }

        this.mContext = context;

        copyDataBase();
        mDatabase = getWritableDatabase();
        this.getWritableDatabase();
    }

    /** Checking the existence of the database.
     * @return - exists.
     */
    private boolean checkDataBase() {
        File dbFile = new File(mDbPath + DB_NAME);
        return dbFile.exists();
    }

    /** Copy database in android catalog. */
    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    /** Copy process.
     * @throws IOException .
     */
     private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(mDbPath + DB_NAME);
        final int mByte = 1024;
        byte[] mBuffer = new byte[mByte];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {

    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion) {
        copyDataBase();
    }

    /**
     * Function to get value of field.
     * @return database object.
     */
    public final SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    /**
     * Function to get value of field.
     * @return cursor of the words.
     */
    public List<Word> getWords() {
        Cursor cursor = mDatabase.rawQuery("select * from Words ", null);
        cursor.moveToFirst();
        return getListWords(cursor);
    }

    /**
     * Function to get value of field.
     * @param cursor - cursor words.
     * @return list words.
     */
    private List<Word> getListWords(final Cursor cursor) {
        List<Word> words = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            words.add(new Word(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }

    /**
     * Function to get value of field.
     * @return list levels.
     */
    public List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("select * from Levels", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            levels.add(new Level(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3)));
            cursor.moveToNext();
        }

        cursor.close();
        return levels;
    }

    /**
     * Function to get value of field.
     * @param idLevel - id of the level.
     * @return list words of the level.
     */
    public final List<Word> getWordsOfLevel(final int idLevel) {
        Level level     = getLevels().get(idLevel);
        String query    = "select * from Words where _id between "
                + level.getMinValue()
                + " and "
                + level.getMaxValue()
                + ";";
        Cursor cursor = mDatabase.rawQuery(query, null);
        return getListWords(cursor);
    }

    /**
     * Function to get value of field.
     * @param rangeValue - range.
     * @return list words of the range.
     */
    private List<Word> getWordsOfRange(final String rangeValue) {
        String[] range = rangeValue.split("-");
        String query    = "select * from Words where _id between "
                + range[0]
                + " and "
                + range[1]
                + ";";
        Cursor cursor = mDatabase.rawQuery(query, null);
        return getListWords(cursor);
    }

    /**
     * Function to get value of field.
     * @return list words of the journal.
     */
    public List<Word> getWordsInJournal() {
        List<Word> words = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(getIdWordsInJournal(), null);
        String[] ids = getPositionWordsInJournal().split(",");
        int count = 0;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = new Word(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2));
            word.setIdWordJournal(Integer.parseInt(ids[count]));
            words.add(word);
            cursor.moveToNext();
            count++;
        }
        cursor.close();
        return words;
    }

    /**
     * Function to get value of field.
     * @return id words in journal.
     */
    private String getIdWordsInJournal() {
        List<Journal> journals = getFieldsJournal();
        StringBuilder builder = new StringBuilder();
        builder.append("select * from Words where _id in (");

        for (int i = 0; i < journals.size(); i++) {
            Journal j = journals.get(i);
            builder.append(j.getIdWord());
            if (i != journals.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(')');
        return builder.toString();
    }

    /**
     * Function to get value of field.
     * @return position words in journal.
     */
    private String getPositionWordsInJournal() {
        List<Journal> journals = getFieldsJournal();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < journals.size(); i++) {
            Journal j = journals.get(i);
            builder.append(j.getIdWord());
            if (i != journals.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    /**
     * Function to get value of field.
     * @return logging.
     */
    private List<Journal> getFieldsJournal() {
        List<Journal> journals = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from Journal ", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            journals.add(new Journal(
                    cursor.getInt(0),
                    cursor.getInt(1)));
            cursor.moveToNext();
        }

        cursor.close();
        return journals;
    }

    /** Add field in journal.
     * @param idWord - id of the word.
     */
    public void addFieldJournal(final int idWord) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_word", idWord);
        mDatabase.insert("Journal", null, contentValues);
    }

    /** Delete field of the journal.
     * @param idWord - id of the word.
     */
    @SuppressLint("Recycle")
    public void deleteFieldJournal(final int idWord) {
        mDatabase.delete("Journal",
                "id_word = ?",
                new String[]{String.valueOf(idWord)});
    }

    /**
     * Function to get value of field.
     * @return list tests.
     */
    public List<Test> getTests() {
        List<Test> tests = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from Tests ", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tests.add(new Test(
                    cursor.getInt(0),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)));
            cursor.moveToNext();
        }

        cursor.close();
        return tests;
    }

    /**
     * Function to get value of field.
     * @param idTest - id of the test.
     * @return test by a id.
     */
    public Test getTest(final int idTest) {
        Cursor cursor
                = mDatabase
                .rawQuery("select * from Tests where _id = " + idTest,
                null);
        cursor.moveToFirst();

        Test test = new Test(
                cursor.getInt(0),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));

        cursor.close();
        return test;
    }

    /**
     * Function to get value of field.
     * @param rangeInfo - type and range info.
     * @return position words in test.
     */
    public List<Word> getWordsOfTest(final String rangeInfo) {
        final int getType   = 0;
        final int getRange  = 1;
        final int typeOne   = 1;
        final int typeTwo   = 2;

        String[] range = rangeInfo.split("_");
        switch (Integer.parseInt(range[getType])) {
            case typeOne:
                int type = Integer.parseInt(range[getRange]);
                Cursor cursor
                        = mDatabase
                        .rawQuery("select * from Words where type = " + type,
                        null);
                return getListWords(cursor);
            case typeTwo:
                return getWordsOfRange(range[getRange]);

            default:
                return null;
        }
    }
}