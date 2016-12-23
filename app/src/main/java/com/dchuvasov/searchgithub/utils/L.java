package com.dchuvasov.searchgithub.utils;

import android.util.Log;

import com.dchuvasov.searchgithub.BuildConfig;

/**
 * Created by denis_chuvasov on 19.12.16.
 */

public class L {
    private L(){

    }

    public static void d(String tag, String message) {
        if(BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
