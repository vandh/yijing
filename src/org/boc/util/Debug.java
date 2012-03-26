package org.boc.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class Debug {
  public static void err(String o){
    if(Public.DEBUGSWITCH)
      System.err.println(o.toString());
  }

  public static void err(int o){
    if(Public.DEBUGSWITCH)
      System.err.println(o);
  }

  public static void err(Object o){
    if(Public.DEBUGSWITCH)
      System.err.println(o.toString());
  }

  public static void out(String o){
    if(Public.DEBUGSWITCH)
      System.out.println(o.toString());
  }

  public static void out(int o){
    if(Public.DEBUGSWITCH)
      System.out.println(o);
  }

  public static void out(Object o){
    if(Public.DEBUGSWITCH)
      System.out.println(o.toString());
  }

  private static String getClassRootPath(String file) {
    URL url = null;
    try {
      url = (new Debug()).getClass().getClassLoader().getResource(file);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    String path = null;
    if(url==null)
      err("读取日志文件出错！");
    else
        path = url.getPath();
    return path;
  }

  public static void log(String text) {
    if(!Public.DEBUGSWITCH)
      return;
    String file = Public.LOGFILE;
    try {
      FileWriter theFile = new FileWriter(getClassRootPath(file),true);
      PrintWriter out = new PrintWriter(theFile);
      out.println(text);
      out.close();
    }
    catch (UnsupportedEncodingException e) {
    }
    catch (IOException e) {
    }
  }


}
