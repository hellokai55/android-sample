package com.hellokai.orangechat;

import android.util.Log;



public class Test {

    public void test(Method2 method2) {
        Log.i("hellokaio", "method2 first" + method2);
//        method2 = new Method2("modified",2);
        method2.name = "modified";
        Log.i("hellokaio", "method2 after" + method2);
    }
}
