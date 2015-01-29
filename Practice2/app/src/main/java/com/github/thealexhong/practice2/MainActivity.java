/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.thealexhong.practice2.fragment.MenuFragment;
import com.github.thealexhong.practice2.record.DatabaseManager;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new MenuFragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            final DatabaseManager mDatabaseManager = DatabaseManager.INSTANCE;
            if (mDatabaseManager.hasUnsavedChanges())
            {
                new AlertDialog.Builder(this)
                        .setTitle("Unsaved changes")
                        .setMessage(getResources().getString(R.string.dialog_unsaved2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabaseManager.newDatabase();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment())
                                        .addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else {
                mDatabaseManager.newDatabase();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment())
                        .addToBackStack(null).commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
