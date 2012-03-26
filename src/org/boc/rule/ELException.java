package org.boc.rule;

import java.lang.Exception;

/**
 * Describe class ELException here.
 *
 *
 * Created: Sat Aug 14 00:19:13 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class ELException extends Exception {

  /**
   * Creates a new <code>ELException</code> instance.
   *
   */
  public ELException() {

  }

  public ELException(String message)
  {
    super(message);
  }
  public ELException(Throwable parent)
  {
  }
  public ELException(String message, Throwable parent)
  {
    super(message);
  }
}
