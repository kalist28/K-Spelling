package com.kalistdev.spelling.main.adapters;

import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.TextView;
import android.content.Context;
import com.kalistdev.spelling.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.kalistdev.spelling.database.Test;
import androidx.recyclerview.widget.RecyclerView;
import com.kalistdev.spelling.training.TestActivity;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    /** List of the test. */
    private List<Test> mTests;

    /**
     * Constructor - initialize object.
     * @param tests - list of the test.
     */
    public TestAdapter(final List<Test> tests) {
        this.mTests     = tests;
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(final @NonNull ViewGroup parent,
                                         final int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_element_test,
                        parent,
                        false);
        return new ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final @NonNull ViewHolder holder,
                                 final int position) {
        final Test t = mTests.get(position);
        final Context context = holder.card.getContext();
        final String addInfo
                = t.getInfo()
                + "\nУспешно "
                + t.getSuccess()
                + " из "
                + (t.getSuccess() + t.getFailure());

        holder.name.setText(t.getName());
        holder.addInfo.setText(addInfo);
        if (t.getSuccess() > 0) {
            holder.assessment.setText("пройдено");
            int color
                    = context.getResources()
                    .getColor(R.color.colorAnswerTrue);
            holder.assessment.setTextColor(color);
        } else {
            holder.assessment.setText("не пройдено");
            int color
                    = context.getResources()
                    .getColor(R.color.colorAnswerFalse);
            holder.assessment.setTextColor(color);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("idTest", t.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public final int getItemCount() {
        return mTests.size();
    }

    /**
     * ViewHolder, card description.
     * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /** Card object. */
        private CardView card;

        /** Name view. */
        private TextView name;

        /** Assessment view. */
        private TextView assessment;

        /** Add information view. */
        private TextView addInfo;

        /**
         * Constructor - initialize object.
         * @param v - view card.
         */
        ViewHolder(final View v) {
            super(v);
            card        = v.findViewById(R.id.card);
            name        = v.findViewById(R.id.test_name);
            assessment  = v.findViewById(R.id.test_assessment);
            addInfo     = v.findViewById(R.id.test_add_info);
        }
    }
}
