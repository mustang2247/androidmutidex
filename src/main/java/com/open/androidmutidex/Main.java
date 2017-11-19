package com.open.androidmutidex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


        //第三步 通过getParent方法， 获取mainLoader中对parent字段
        ClassLoader parentLoader = mainLoadder.getParent();
        System.out.println("prentLoader is Name:  " + parentLoader.toString());

        //第四步 打印ExtClassLoader对加载路径
        URL[] murlsExt = ((URLClassLoader) parentLoader).getURLs();
        print(murlsExt);

        //第五步 通过getParent方法，获取ParentLoader中对parent字段 null
        ClassLoader parentLoaderTwo = parentLoader.getParent();
        System.out.println("parentLoaderTwo is Name:  " + parentLoaderTwo);

        //第六步 打印BootstrapClassLoader对加载路径
        try {
            Class launcherClass = Class.forName("sun.misc.Launcher");
            Method methodGetClassPath = launcherClass.getDeclaredMethod("getBootstrapClassPath", null);
            if (methodGetClassPath != null){
                methodGetClassPath.setAccessible(true);

                Object mObj = methodGetClassPath.invoke(null, null);
                if (mObj != null){
                    Method methodGetURLs = mObj.getClass().getDeclaredMethod("getURLs", null);
                    if (methodGetURLs != null){
                        methodGetURLs.setAccessible(true);
                        URL[] murlBoot = (URL[]) methodGetURLs.invoke(mObj, null);
                        print(murlBoot);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
