package com.kalistdev.spelling.training;

import android.os.Bundle;
import android.content.ContentValues;
import com.kalistdev.spelling.database.Test;
import com.kalistdev.spelling.database.Helper;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class TestActivity extends TrainingActivity {

    /** Id test. */
    private int mIdTestLevel;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdTestLevel = getIntent().getIntExtra("idTest", 0);
        final Helper helper   = new Helper(this);
        final Test test = helper.getTest(mIdTestLevel);

        setIsTest(true);
        setWords(helper.getWordsOfTest(test.getRange()));
        getProgressBar().setMax(getWords().size());
        setFinishAction(new Action() {
            @Override
            public void action() {
                actionFinish(helper, test);
            }
        });

    }

    private void actionFinish(final Helper helper,
                              final Test test) {
        ContentValues contentValues = new ContentValues();
        if (getWrongAnswerCount() > 3) {
            String message
                    = "Тест не пройден!"
                    + "\nВы допустили "
                    + getWrongAnswerCount()
                    + " ошибок."
                    + "Проведите работу"
                    + " над ошибками.";
            setUserMessage(message);
            contentValues.put("failure", test.getFailure() + 1);

        } else {
            String message
                    = "Тест пройден!"
                    + "\nВы допустили "
                    + getWrongAnswerCount()
                    + " ошибок.";
            setUserMessage(message);
            contentValues.put("success", test.getSuccess() + 1);
        }

        helper.getDatabase().update(
                "Tests",
                contentValues,
                "_id = ?",
                new String[]{Integer.toString(mIdTestLevel)});
        helper.close();
    }
}
