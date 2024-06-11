package com.example.myapplication;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProxyGenerator {
  public static void main(String[] args) {
//    generateProxyClass(ApiService.class);
  }

  public static void generateProxyClass(Context context, Class<?> proxyInterface) {
    String proxyClassName = "Proxy" + proxyInterface.getSimpleName();
    byte[] classFile = ProxyGenerator.generateProxyClass(proxyClassName, new Class<?>[]{proxyInterface});


    File proxyClassName1 = new File(context.getExternalCacheDir(), proxyClassName);
    try (FileOutputStream fos = new FileOutputStream(proxyClassName1 + ".class")) {
      fos.write(classFile);
      System.out.println("Proxy class file written to " + proxyClassName1 + ".class");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static byte[] generateProxyClass(String proxyClassName, Class<?>[] interfaces) {
    try {
      Class<?> proxyGeneratorClass = Class.forName("sun.misc.ProxyGenerator");
      return (byte[]) proxyGeneratorClass
        .getDeclaredMethod("generateProxyClass", String.class, Class[].class)
        .invoke(null, proxyClassName, interfaces);
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate proxy class", e);
    }
  }
}
