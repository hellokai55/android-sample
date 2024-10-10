package com.hellokai.orangechat;

import androidx.annotation.NonNull;

public class Method2 {

    //name
    public String name;
    //age
    public int age;

    public Method2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "Method2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}