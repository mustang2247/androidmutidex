package com.open.androidmutidex;

import java.util.ArrayList;

public class ClassLoaderDemo {

    static {
        System.out.println("ClassLoaderDemo is load!");
    }

    public static void main(String[] args){
        Class mainClass = ClassLoaderDemo.class;
        ClassLoader mainLoadder = mainClass.getClassLoader();
        System.out.println("mainloader is Name: " + mainLoadder.toString());

        Class listClass = ArrayList.class;
        ClassLoader listLoader = listClass.getClassLoader();

        System.out.println("listLoader is Name: " + listLoader.toString());
    }
}
