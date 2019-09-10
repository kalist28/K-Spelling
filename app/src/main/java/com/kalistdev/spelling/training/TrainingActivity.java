package com.kalistdev.spelling.training;

import java.util.List;
import java.util.Arrays;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.TextView;
import com.kalistdev.spelling.R;
import kr.co.prnd.StepProgressBar;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import com.google.android.gms.ads.AdView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import com.google.android.gms.ads.AdRequest;
import com.kalistdev.spelling.database.Word;
import com.kalistdev.spelling.database.Helper;
import androidx.appcompat.app.AppCompatActivity;
import com.kalistdev.spelling.database.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
@SuppressLint("Registered")
public class TrainingActivity extends AppCompatActivity {

    /** Contains all word. */
    private List<Word> mWords;

    /** Displays the word  in caps. */
    private TextView wordView;

    /** Floating button switch words. */
    private FloatingActionButton floatingButton;

    /** Step progress bar. */
    private StepProgressBar mProgressBar;

    /** Contains true answer. */
    private String trueAnswer;

    /** Message for the user. */
    private String mUserMessage;

    /** Number of all words. */
    private int wordCount;

    /** Number of wrong answer. */
    private int wrongAnswerCount;

    /** Prize for successful completion. */
    private int mScored;

    /** Logical response variable. */
    private boolean isAnswer;

    /** Logical variable. */
    private boolean mIsTest;

    /** Action with correct answer. */
    private Action mAction;

    /** Action before the finish. */
    private Action mFinishAction;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        mWords          = new ArrayList<>();
        mProgressBar    = findViewById(R.id.progress);
        wordView        = findViewById(R.id.word);
        floatingButton  = findViewById(R.id.floating_button);
        wordCount       = 0;

        initializeAdBanner();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected final void onStart() {
        super.onStart();

        if (mWords.size() == 0 || mProgressBar.getMax() == 0) {
            showFinishDialog();
        } else {
            setInfo();
        }

        floatingButton.setVisibility(View.GONE);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(final View view) {
                step();
                floatingButton.setVisibility(View.GONE);
            }
        });
    }

    /** Sets the information on the canvas. */
    @SuppressLint("RestrictedApi")
    private void setInfo() {
        List<Button> list = new ArrayList<>();
        list.add((Button) findViewById(R.id.btn_one));
        list.add((Button) findViewById(R.id.btn_two));
        list.add((Button) findViewById(R.id.btn_three));
        list.add((Button) findViewById(R.id.btn_fore));

        List<String> options
                = new ArrayList<>(Arrays
                .asList(mWords.get(wordCount)
                        .getOptions().split(",")));

        trueAnswer = options.get(0);
        wordView.setText(options.get(0).toUpperCase());

        int countButton = options.size();
        for (int i = 0; i < list.size(); i++) {
            final Button btn = list.get(i);
            btn.setVisibility(View.VISIBLE);
            if (i < countButton) {
                int randomId = (int) (Math.random() * ((options.size())));
                btn.setBackground(ContextCompat
                        .getDrawable(this, R.drawable.button_shape));
                btn.setText(options.get(randomId));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        checkAnswer(btn);
                    }
                });
                options.remove(randomId);
            } else {
                btn.setVisibility(View.GONE);
            }
        }
    }

    /** Checks the answer to truthfulness.
     *
     * @param button - press the button.
     */
    @SuppressLint("RestrictedApi")
    private void checkAnswer(final Button button) {
        if (trueAnswer.contentEquals(button.getText())) {
            button
                    .setBackground(ContextCompat
                            .getDrawable(this, R.drawable.button_shape_true));
            isAnswer = true;
            try {
                mAction.action();
            } catch (Exception e) {
                System.out.println("No action assigned");
            }
        } else {
            button
                    .setBackground(ContextCompat
                            .getDrawable(this, R.drawable.button_shape_false));
            if (!isAnswer) {
                Helper helper = new Helper(this);
                int idWord = mWords.get(wordCount).getId();
                helper.addFieldJournal(idWord);
                helper.close();
                wrongAnswerCount++;
                isAnswer = true;
            }
        }
        System.out.println(wrongAnswerCount);
        if (mIsTest) {
            step();
        } else {
            floatingButton.setVisibility(View.VISIBLE);
        }
    }

    /** Step in the training. */
    private void step() {
        isAnswer = false;
        wordCount++;
        mProgressBar.setStep(wordCount);
        if (wordCount != mWords.size()) {
            setInfo();
        } else {
            showFinishDialog();
        }
    }

    /** When the training is completed, the dialog box shows the result. */
    private void showFinishDialog() {
        if (mFinishAction != null) {
            mFinishAction.action();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final UserData userData
                = new UserData(getSharedPreferences("save", MODE_PRIVATE));
        userData.load();
        builder.setTitle("Результат")
                .setMessage(getUserMessage())
                .setPositiveButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                                final int i) {
                                finish();
                                userData.addScored(getScore());
                                userData.save();
                            }
                        })
                .setCancelable(false)
                .show();
    }

    /** Get score number.
     * @return calculation score.
     */
    private int getScore() {
        final int threeWord = 3;
        if (wrongAnswerCount > threeWord) {
            return wordCount - wrongAnswerCount;
        } else {
            return mScored;
        }
    }

    /** Initialize Ad Banner. */
    private void initializeAdBanner() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /** Function to get value of field.
     * @param words - list words.
     */
    public void setWords(final List<Word> words) {
        this.mWords = words;
    }

    /** /** Function to get value of field.
     * @param scored - user scored.
     */
    public void setScored(final int scored) {
        this.mScored = scored;
    }

    /** /** Function to get value of field.
     * @param isTest - it is a test or not
     */
    public void setIsTest(final boolean isTest) {
        this.mIsTest = isTest;
    }

    /** /** Function to get value of field.
     * @param action - user action on correct answer.
     */
    public void setAction(final Action action) {
        this.mAction = action;
    }

    /** /** Function to get value of field.
     * @param finishAction - user action in the end training.
     */
    public void setFinishAction(final Action finishAction) {
        this.mFinishAction = finishAction;
    }

    /** /** Function to get value of field.
     * @param userMessage - user message in the end training.
     */
    public void setUserMessage(final String userMessage) {
        this.mUserMessage = userMessage;
    }

    /**
     * Function to get value of field {@link TrainingActivity#mWords}.
     * @return list words.
     */
    public List<Word> getWords() {
        return mWords;
    }

    /**
     * Function to get value of field {@link TrainingActivity#mProgressBar}.
     * @return step progress bar.
     */
    public StepProgressBar getProgressBar() {
        return mProgressBar;
    }

    /**
     * Function to get value of field {@link TrainingActivity#wordCount}.
     * @return count word.
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Function to get value of field {@link TrainingActivity#wrongAnswerCount}.
     * @return count wrong answer.
     */
    public int getWrongAnswerCount() {
        return wrongAnswerCount;
    }

    /**
     * Function to get value of field {@link TrainingActivity#mUserMessage}.
     * @return user message in the end.
     */
    public String getUserMessage() {
        if (mUserMessage == null) {
            return "Ты ответил правильно на "
                    + (mWords.size() - wrongAnswerCount)
                    + " и "
                    + "заработал "
                    + getScore();
        } else {
            return mUserMessage;
        }
    }
}
