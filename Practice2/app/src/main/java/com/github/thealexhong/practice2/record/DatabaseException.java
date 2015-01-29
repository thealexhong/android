/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.record;

/**
 * Custom exception class for database managing
 */
public class DatabaseException extends Exception {
    public DatabaseException(String msg) { super (msg); }
    public DatabaseException(String msg, Throwable e) { super(msg, e); }
}
