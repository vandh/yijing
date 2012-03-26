package org.boc.util;

import java.util.*;
import java.io.*;

// class StreamGobbler omitted for brevity

public class Exec {
  public static void exec(String cmd) {
    try {
      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec(cmd);

      // any error message?
      StreamGobbler errorGobbler = new
          StreamGobbler(proc.getErrorStream(), "ERR");

      // any output?
      StreamGobbler outputGobbler = new
          StreamGobbler(proc.getInputStream(), "OUT");

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal = proc.waitFor();
      //Messages.info("ExitValue: " + exitVal);
    }
    catch (Throwable t) {
      Messages.error("Ö´ÐÐÃüÁî["+cmd+"]³ö´í£¬"+t);
    }
  }
}
