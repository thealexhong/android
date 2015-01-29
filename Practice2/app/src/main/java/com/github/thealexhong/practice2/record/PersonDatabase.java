/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.record;

import android.content.Context;

import com.github.thealexhong.practice2.R;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDatabase implements Displayable {

    private static final String DEFAULT_FILENAME = "untitled.pr";
    private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance();
    private String mDate = DATE_FORMAT.format(new Date());
    private String mFilename = DEFAULT_FILENAME;
    private List<PersonRecord> mPersonRecordList = new ArrayList<>();
    private boolean mHasChanged = false;

    public PersonDatabase() {}

    /**
     * Accessors
     */
    public int size() { return mPersonRecordList.size(); }
    public boolean hasChanges() { return mHasChanged; }
    public String getFilename() { return mFilename; }

    /**
     * Clears database
     * @return New empty database
     */
    public static PersonDatabase clearDatabase() { return new PersonDatabase(); }

    /**
     * Adds a new record to database
     * @param personRecord
     */
    public void addPersonRecord(PersonRecord personRecord) {
        mHasChanged = true;
        mPersonRecordList.add(personRecord);
    }

    /**
     * Prepares list of records in the database for display
     * @return List of records
     */
    public List<Displayable> getPersonRecordList() {
        return ImmutableList.<Displayable> copyOf(mPersonRecordList);
    }

    /**
     * Prepares for saving
     * @param filename desired filename
     */
    public void storeDatabase(String filename) {
        mHasChanged = false;
        mFilename = filename;
        mDate = DATE_FORMAT.format(new Date());
    }

    /**
     * Convert {@code PersonDatabase} to JSON format
     * @return JSON representing the database
     */
    public String toJSON() {
        return new Gson().toJson(this);
    }

    /**
     * Convert JSON format String to readable database format
     * @param json    JSON representing a database
     * @return database parsed from JSON
     */
    public static PersonDatabase fromJSON(String json) {
        return new Gson().fromJson(json, PersonDatabase.class);
    }

    @Override
    public String getHeader(Context context) {
        return String.format(context.getResources().getString(R.string.view_myList), mFilename);
    }

    @Override
    public String getDetail(Context context) {
        String detail = context.getResources().getString(R.string.view_databaseDetail);
        return String.format(detail, mDate, mPersonRecordList.size());
    }
}

