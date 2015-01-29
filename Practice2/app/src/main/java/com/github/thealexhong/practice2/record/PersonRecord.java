/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.record;

import android.content.Context;

import com.github.thealexhong.practice2.R;
import com.google.common.base.Strings;

/**
 * PersonRecord object
 */
public class PersonRecord implements Displayable {
    private String mName, mFood;
    private Integer mAge;

    /**
     * PersonRecord constructor
     * @param name name
     * @param age  age
     * @param food favourite food item
     */
    public PersonRecord(String name, Integer age, String food) {
        mName = name;
        mAge = age;
        mFood = food;
    }
    public PersonRecord(String name, Integer age) {
        mName = name;
        mAge = age;
    }
    public PersonRecord(String name, String food) {
        mName = name;
        mFood = food;
    }
    public PersonRecord(String name) {
        mName = name;
    }
    public PersonRecord() {}

    /**
     * Accessors
     */
    public String getName() { return mName; }
    public Integer getAge() { return mAge; }
    public String getFood() { return mFood; }

    /**
     * Checks if record is valid
     * @return {@code true} if PersonRecord object is valid
     *         {@code false} otherwise
     */
    public boolean isValid() {
        return !Strings.isNullOrEmpty(mName) &&
               !Strings.isNullOrEmpty(mFood) &&
                mAge != null &&
                mAge >= 0;
    }

    @Override
    public String getHeader(Context context) {
        return mName;
    }

    @Override
    public String getDetail(Context context) {
        return String.format(context.getResources().getString(R.string.view_personDetail),
                mAge, mFood);
    }
}
