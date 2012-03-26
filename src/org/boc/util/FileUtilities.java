package org.boc.util;

import java.io.File;

public class FileUtilities {

  public static String CSV = "csv";
  public static String GZIP = "tgz";
  public static String HTML = "html";
  public static String JAR = "jar";
  public static String SQL = "sql";
  public static String XLS = "xls";
  public static String XML = "xml";
  public static String DAT = "dat";
  public static String ZIP = "zip";

  /** Return extension from a filename */
  public static String getExtension(File f) {
    String extension = null;
    String fileName = f.getName();
    int index = fileName.lastIndexOf('.');
    if (index > 0 && index < fileName.length())
      extension = fileName.substring(index + 1).toLowerCase();

    return extension;
  }
}
