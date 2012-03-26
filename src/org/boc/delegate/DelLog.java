package org.boc.delegate;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DelLog {
  private static final String MAN_FILE = "man.log";
  private static final String MANPATH = "database";
  private static final String ERR_FILE = "error.log";
  private static final String LOGPATH = "log";

  public static PrintWriter getLogObject() {
    File f = new File(LOGPATH, ERR_FILE);
    OutputStream errStream;
    try {
      Date now = new Date();
      if (f.exists() && f.isFile() && f.length() > (2 * 1024 * 1024)) {
        File f1 = new File(LOGPATH,
                           "backup" + now.getYear() + "_" + now.getMonth() +
                           "_" +
                           now.getDay() + ERR_FILE);
        f.renameTo(f1);
        f = new File(LOGPATH, ERR_FILE);
      }
    }
    catch (Exception e) {}
    try {
      if (!f.exists())
        f.createNewFile();
      errStream = new FileOutputStream(f, true);
    }
    catch (IOException e) {
    	//e.printStackTrace();
      errStream = System.err;
    }

    return new PrintWriter(errStream);
  }

  public static PrintWriter getDBObject() {
    File f = new File(MANPATH, MAN_FILE);
    OutputStream manStream;
    try {
      Date now = new Date();
      if (f.exists() && f.isFile() && f.length() > (2 * 1024 * 1024)) {
        File f1 = new File(LOGPATH,
                           "backup" + now.getYear() + "_" + now.getMonth() +
                           "_" +
                           now.getDay() + MAN_FILE);
        f.renameTo(f1);
        f = new File(MANPATH, MAN_FILE);
      }
    }
    catch (Exception e) {}
    try {
      if (!f.exists())
        f.createNewFile();
      manStream = new FileOutputStream(f, true);
    }
    catch (IOException e) {
    	//e.printStackTrace();
      manStream = System.err;
    }

    return new PrintWriter(manStream);
  }

  public synchronized static void error(PrintWriter errStream, String s) {
    if (errStream != null) {
      errStream.println("[ERROR] [" + new Date() + "] " + s);
      errStream.flush();
    }
  }

  public synchronized static void error(PrintWriter errStream, String s, Throwable e) {
    if (errStream != null) {
      errStream.println("[ERROR] [" + new Date() + "] " + e.getMessage());
      e.printStackTrace(errStream);
      errStream.flush();
    }
  }

}