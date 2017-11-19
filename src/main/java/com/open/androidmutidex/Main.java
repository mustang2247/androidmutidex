package com.open.androidmutidex;

import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String[] args){
        // 第一步 获取main 对类加载器
        Class mainClass = Main.class;
        ClassLoader mainLoadder = mainClass.getClassLoader();
        System.out.println("mainloader is Name: " + mainLoadder.toString());

        //第二步 打印AppClassLoader对加载路径
        URL[] murls = ((URLClassLoader) mainLoadder).getURLs();
        print(murls);
    }

    /**
     * 打印url数组
     * @param murls
     */
    private static void print(URL[] murls) {
        for (URL url : murls){
            System.out.println("url: " + url);
        }
    }
}
