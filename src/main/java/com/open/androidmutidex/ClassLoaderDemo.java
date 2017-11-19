package com.open.androidmutidex;

public class ClassLoaderDemo {
    public static void main(String[] args){
        Class mainClass = ClassLoaderDemo.class;
        ClassLoader mainLoadder = mainClass.getClassLoader();
        System.out.println("mainloader is Name: " + mainLoadder.toString());
    }
}
