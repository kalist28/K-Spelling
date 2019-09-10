package com.kalistdev.spelling.training;

import android.os.Bundle;
import com.kalistdev.spelling.R;
import com.kalistdev.spelling.database.Helper;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class CorrectionMistakesActivity extends TrainingActivity {

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper helper = new Helper(this);
        setWords(helper.getWordsInJournal());
        setAction(new Action() {
            @Override
            public void action() {
                Helper helper = new Helper(getApplicationContext());
                helper.deleteFieldJournal(getWords()
                        .get(getWordCount())
                        .getIdWordJournal());
                helper.close();
            }
        });
        final int max = getWords().size() != 0 ? getWords().size() : 1;
        setTitle("Работа над ошибками");
        getProgressBar().setMax(max);
        setUserMessage(getString(R.string.no_error));
        helper.close();
    }

}
