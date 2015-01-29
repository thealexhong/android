/*
 * Copyright (C) 2015 Alexander Hong
 */

package com.github.thealexhong.practice2.record;

import android.content.Context;

/**
 * Interface for displaying record information on screen
 */
public interface Displayable {
    String getHeader(Context context);
    String getDetail(Context context);
}
