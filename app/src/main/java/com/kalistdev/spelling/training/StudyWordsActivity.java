package com.kalistdev.spelling.training;

import android.os.Bundle;
import com.kalistdev.spelling.database.Level;
import com.kalistdev.spelling.database.Helper;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class StudyWordsActivity extends TrainingActivity {

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int idLevel = getIntent().getIntExtra("lvl", 0);
        Helper helper   = new Helper(this);
        Level level     = helper.getLevels().get(idLevel);

        setIsTest(false);
        setWords(helper.getWordsOfLevel(idLevel));
        setScored(level.getPrice());
        getProgressBar().setMax(level.getCountWords());
        helper.close();
    }
}
