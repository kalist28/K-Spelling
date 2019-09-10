package com.kalistdev.spelling.main.adapters;

import java.util.List;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;
import com.kalistdev.spelling.R;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import androidx.cardview.widget.CardView;
import com.kalistdev.spelling.database.Level;
import androidx.recyclerview.widget.RecyclerView;
import com.kalistdev.spelling.database.UserData;
import com.kalistdev.spelling.training.StudyWordsActivity;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class LevelAdapter
        extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    /** Contains of all levels. */
    private List<Level> mLevels;

    /** @see UserData - object*/
    private UserData mUserData;

    /**
     * Constructor - initialize object.
     * @param levels    - list levels.
     * @param context   - app context.
     */
    public LevelAdapter(final Context context,
                        final List<Level> levels) {
        this.mLevels    = levels;
        this.mUserData  = new UserData(context
                .getSharedPreferences("save", Context.MODE_PRIVATE));
        mUserData.load();
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(final @NonNull ViewGroup parent,
                                               final int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_element_level,
                         parent,
                        false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public final void onBindViewHolder(final @NonNull ViewHolder holder,
                                       final int position) {
        final Level level = mLevels.get(position);
        holder.name.setText(position + 1 + " Уровень");
        int wordCount = level.getMaxValue() - level.getMinValue() + 1;
        String addInfo = " Всего слов:" + wordCount
                + "\n Допуск: " + level.getScoredCount()
                + " очков";
        holder.addInfo.setText(addInfo);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (mUserData.getScored() >= level.getScoredCount()) {
                    goTraining(holder, position);
                } else {
                    showDialog(holder.card.getContext());
                }
            }
        });

    }

    /** Start training activity.
     *
     * @param holder    - {@link ViewHolder} object.
     * @param position  - id of the level.
     */
    private void goTraining(final ViewHolder holder,
                            final int position) {
        Intent intent
                = new Intent(holder.card.getContext(),
                    StudyWordsActivity.class);
        intent.putExtra("lvl", position);
        holder.card.getContext().startActivity(intent);
    }

    /** When the training is completed, the dialog box shows the result.
     *
     * @param context - app context.
     */
    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Предупреждение!")
                .setMessage(context.getString(R.string.warning))
                .setPositiveButton("Закрыть",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface,
                                        final int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    @Override
    public final int getItemCount() {
        return mLevels.size();
    }


    /**
     * Describes the variables of the canvas.
     * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /** Card view object. */
        private CardView card;

        /** Text view name of the level. */
        private TextView name;

        /** Text view add info about level. */
        private TextView addInfo;

        /**
         * Constructor - initialize object.
         * @param v - canvas view object.
         */
        ViewHolder(final @NonNull View v) {
            super(v);
            card    = v.findViewById(R.id.card);
            name    = v.findViewById(R.id.level_name);
            addInfo = v.findViewById(R.id.add_info);
        }
    }

}
