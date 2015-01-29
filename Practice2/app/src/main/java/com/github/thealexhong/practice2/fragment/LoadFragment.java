/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.github.thealexhong.practice2.R;
import com.github.thealexhong.practice2.record.DatabaseException;
import com.github.thealexhong.practice2.record.DatabaseManager;
import com.github.thealexhong.practice2.record.Displayable;
import com.github.thealexhong.practice2.record.EntryAdapter;
import com.github.thealexhong.practice2.record.PersonDatabase;

import java.util.List;

public class LoadFragment extends MyFragment {

    private static final String TAG = LoadFragment.class.getSimpleName();

    private final DatabaseManager mDatabaseManager = DatabaseManager.INSTANCE;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
    }

    private void setView() {
        loadStoredEntryLists();
        Button btn_back = (Button) getActivity().findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrevFragment();
            }
        });
    }

    private void loadStoredEntryLists() {
        try {
            Log.d(TAG, "Loading stored entry list");
            List<Displayable> list = mDatabaseManager.getStoredDatabaseFiles(getActivity());

            final ListView listView = (ListView) getActivity().findViewById(R.id.list);
            EntryAdapter adapter = new EntryAdapter(getActivity(), list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    loadSelectedDatabase(listView, position);
                }
            });
        }
        catch (DatabaseException e) {
            Log.e(TAG, "Database files unable to load", e);
            String err = getResources().getString(R.string.msg_databaseInvalidLoad);
            showNotification(String.format(err, e.getMessage()));
        }
    }

    private void loadSelectedDatabase(ListView listView, int position) {
        PersonDatabase database = (PersonDatabase) listView.getItemAtPosition(position);
        try {
            PersonDatabase loadedDatabase = mDatabaseManager.loadDatabase(getActivity(), database.getFilename());
            String msg = getResources().getString(R.string.msg_databaseSuccessLoad);
            Bundle bundle = new Bundle();
            bundle.putString("bundle_id", loadedDatabase.toJSON());
            ViewFragment fragment = new ViewFragment();
            fragment.setArguments(bundle);
            showNotification(String.format(msg, database.getFilename()));
            swapFragment(fragment);
        }
        catch (DatabaseException e) {
            Log.e(TAG, "Selected database unable to load", e);
            String err = getResources().getString(R.string.msg_databaseInvalidLoad);
            showNotification(String.format(err, e.getMessage()));
        }
    }
}
