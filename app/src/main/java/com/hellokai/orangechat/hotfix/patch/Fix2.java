package com.hellokai.orangechat.hotfix.patch;

import android.util.Log;

public class Fix2 {
    public static int testCrash() {
        Log.i("hellokaiaries", "testCrash start2");
//        int result = 1 / 0; //fix
        int result = 10;
        Log.i("hellokaiaries", "testCrash end result:" + result);
        return result;
    }
}
