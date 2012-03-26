package org.boc.util;

import java.util.*;
import java.io.*;

public class GoodWindowsExec {
  public static void run(String args) {
    ////if (args.length < 1) {
    //  System.out.println("USAGE: java GoodWindowsExec <cmd>");
    //  System.exit(1);
    //}

    try {
      String osName = System.getProperty("os.name");
      String[] cmd = new String[3];

      if (osName.equals("Windows NT")) {
        cmd[0] = "cmd.exe";
        cmd[1] = "/C";
        cmd[2] = args; //args[0];
      }
      else if (osName.equals("Windows 95")) {
        cmd[0] = "command.com";
        cmd[1] = "/C";
        cmd[2] = args; //args[0];
      }else if(osName.equals("Windows 2000")) {
        cmd[0] = "cmd.exe";
        cmd[1] = "/C";
        cmd[2] = args; //args[0];
      }

      Runtime rt = Runtime.getRuntime();
      //System.out.println("Execing " + cmd[0] + " " + cmd[1]  + " " + cmd[2]);
      Process proc = rt.exec(cmd);
      // any error message?
      StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

      // any output?
      StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal = proc.waitFor();
      //Normally, an exit value of 0 indicates success;
      //any nonzero value indicates an error.
      //The meaning of these exit values depends on the particular operating system.
      //A Win32 error with a value of 2 is a "file not found" error
      Messages.error("ExitValue: " + exitVal);
    }
    catch (Throwable t) {
      Messages.error("Ö´ÐÐÃüÁî³ö´í:"+t.toString());
    }
  }
}
