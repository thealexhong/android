/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.record;

import android.content.Context;

import com.github.thealexhong.practice2.R;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Manages saving/loading/recording entries of the database
 */
public enum DatabaseManager {
    INSTANCE;

    private PersonDatabase mSession;

    /**
     * Constructor
     */
    private DatabaseManager() {
        mSession = PersonDatabase.clearDatabase();
    }

    /**
     * Accessors
     */
    public PersonDatabase getDatabase() { return mSession; }
    public boolean hasUnsavedChanges() { return mSession.hasChanges(); }

    /**
     * Record an entry
     * @param context current app context
     * @param personRecord personRecord object
     * @throws DatabaseException for invalid PersonRecord objects
     */
    public void addPersonRecord(Context context, PersonRecord personRecord) throws DatabaseException {
        if (!personRecord.isValid()) {
            throw new DatabaseException(context.getString(R.string.msg_addInvalid));
        }
        mSession.addPersonRecord(personRecord);
    }

    /**
     * Clears database
     * @param context current app context
     */
    public void newDatabase() {
        mSession = PersonDatabase.clearDatabase();
    }

    /**
     * Saves database
     * @param context current app context
     * @param filename filename of the database to save
     * @throws DatabaseException for invalid filename
     */
    public void saveDatabase(Context context, String filename) throws DatabaseException {
        saveDatabase(context, filename, mSession);
    }

    /**
     * Saves database
     * @param context current app context
     * @param filename filename of the database to save
     * @param database database to save
     * @throws DatabaseException for invalid filename
     */
    public void saveDatabase(Context context, String filename, PersonDatabase database) throws DatabaseException {
        if (Strings.isNullOrEmpty(filename)) {
            throw new DatabaseException(context.getString(R.string.msg_fileInvalid));
        }
        try {
            database.storeDatabase(filename);
            writeToFile(context, filename, database.toJSON());
        }
        catch (IOException e) {
            throw new DatabaseException(context.getResources().getString(R.string.msg_writeError), e);
        }
    }

    /**
     * Load existing database
     * @param context current app context
     * @param filename existing file name
     * @return exisiting database
     * @throws DatabaseException for read errors
     */
    public PersonDatabase loadDatabase(Context context, String filename) throws DatabaseException {
        PersonDatabase database = null;
        if (Strings.isNullOrEmpty(filename)) {
            throw new DatabaseException(context.getString(R.string.msg_fileInvalid));
        }
        try {
            database = PersonDatabase.fromJSON(readFromFile(context, filename));
        }
        catch (IOException e) {
            throw new DatabaseException(context.getResources().getString(R.string.msg_readError), e);
        }
        return database;
    }

    /**
     * Simple File output method
     * @param context current app context
     * @param filename desired filename
     * @param data data to be written
     * @throws IOException for write errors
     */
    public static void writeToFile(Context context, String filename, String data) throws IOException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(new File(context.getFilesDir(), filename)));
            pw.write(data);
            pw.flush();
        }
        finally {
            if(pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Simple file input method
     * @param context current app context
     * @param filename exisiting filename
     * @return a line in the file
     * @throws IOException for read errors
     */
    public static String readFromFile(Context context, String filename) throws IOException {
        BufferedReader br = null;
        String data = null;
        try {
            br = new BufferedReader(new FileReader(new File(context.getFilesDir(), filename)));
            String str = br.readLine();
            if (str != null) {
                data = str;
            }
        }
        finally {
            if (br != null) {
                br.close();
            }
        }
        return data;
    }



    /**
     * Retrieves a list of exisiting databases
     * @param context current app context
     * @return list of exisiting databases
     * @throws DatabaseException for read errors
     */
    public List<Displayable> getStoredDatabaseFiles(Context context) throws DatabaseException {
        File filesDir = context.getFilesDir();
        List<Displayable> containers = Lists.newArrayList();

        for (String filename : filesDir.list()) {
            try {
                containers.add(PersonDatabase.fromJSON(readFromFile(context, filename)));
            }
            catch (IOException e) {
                throw new DatabaseException(context.getResources().getString(R.string.msg_readError), e);
            }
        }
        return ImmutableList.copyOf(containers);
    }
}