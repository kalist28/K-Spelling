package com.kalistdev.spelling.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.kalistdev.spelling.R;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import com.kalistdev.spelling.database.Helper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kalistdev.spelling.main.adapters.TestAdapter;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ListTestFragment extends Fragment {

    @Override
    public final View onCreateView(final @NonNull LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_levels, container, false);
    }

    @Override
    public final void onViewCreated(final @NonNull View view,
                              final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Helper helper = new Helper(getContext());
        recyclerView.setAdapter(new TestAdapter(helper.getTests()));
    }
}
