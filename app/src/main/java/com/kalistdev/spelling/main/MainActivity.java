package com.kalistdev.spelling.main;

import android.view.Menu;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;
import android.content.Context;
import com.kalistdev.spelling.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.kalistdev.spelling.database.UserData;
import androidx.fragment.app.FragmentTransaction;
import com.kalistdev.spelling.training.CorrectionMistakesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Ka-spelling Application
 *
 * This file is part of the Ka-spelling package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** Bottom navigation view. */
    private final BottomNavigationView.OnNavigationItemSelectedListener
            navigationListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(final @NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_tests:
                    openFragment(new ListTestFragment());
                    return true;
                case R.id.navigation_levels:
                    openFragment(new ListLevelFragment());
                    return true;
                case R.id.navigation_correction_mistakes:
                    startActivity(new Intent(getApplicationContext(),
                            CorrectionMistakesActivity.class));
                    return true;
                default:
                    return false;
            }
        }
    };

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationListener);
        navView.setSelectedItemId(R.id.navigation_levels);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final @NonNull MenuItem item) {
        if (item.getItemId() == R.id.info) {
            UserData userData
                    = new UserData(getSharedPreferences(
                            "save",
                            Context.MODE_PRIVATE));
            userData.load();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Информация")
                    .setMessage("Набранные очки: "
                            + userData.getScored() + "\n"
                            + getString(R.string.help_info)
                            + "\n\nAppVersion 0.0"
                            + ".1 " + "Beta")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog,
                                            final int i) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Open fragment on app canvas.
     *
     * @param fragment - fragment object.
     */
    private void openFragment(final Fragment fragment) {
        FragmentTransaction transaction
                = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment).commit();
    }
}
