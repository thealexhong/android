/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.thealexhong.practice2.R;
import com.github.thealexhong.practice2.record.DatabaseException;
import com.github.thealexhong.practice2.record.DatabaseManager;
import com.github.thealexhong.practice2.record.PersonRecord;

public class EntryFragment extends MyFragment {
    private static final String TAG = EntryFragment.class.getSimpleName();
    private EditText mNameField, mAgeField;
    private Spinner mFoodField;
    private DatabaseManager mDatabaseManager = DatabaseManager.INSTANCE;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entry, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
    }

    private void setView() {
        mNameField = (EditText) getActivity().findViewById(R.id.edit_name);
        mAgeField = (EditText) getActivity().findViewById(R.id.edit_age);
        mFoodField = (Spinner) getActivity().findViewById(R.id.array_food);

        Button btn_add = (Button) getActivity().findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) { addEntry(); }
        });

        Button btn_done = (Button) getActivity().findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) { getPrevFragment(); }
        });
    }

    private void addEntry() {
        try {
            String name = mNameField.getText().toString();
            String ageStr = mAgeField.getText().toString();
            String food = (String) mFoodField.getSelectedItem();
            Integer age;
            try {
                age = Integer.parseInt(ageStr);
            }
            catch(NumberFormatException nfe) {
                Log.e(TAG, "Unable to parse Integer", nfe);
                String err = getResources().getString(R.string.msg_addInvalidE);
                showNotification(String.format(err, nfe.getMessage()));
                return;
            }
            PersonRecord personRecord = new PersonRecord(name, age, food);
            mDatabaseManager.addPersonRecord(getActivity(), personRecord);
            showNotification(getResources().getString(R.string.msg_addSuccess));
            newEntry();
        }
        catch (DatabaseException e) {
            Log.e(TAG, "Unable to save entry", e);
            String err = getResources().getString(R.string.msg_addInvalidE);
            showNotification(String.format(err, e.getMessage()));
        }
    }

    private void newEntry() {
        mNameField.setText("");
        mAgeField.setText("");
        mFoodField.setSelection(0);
    }
}
