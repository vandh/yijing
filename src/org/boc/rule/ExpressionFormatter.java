package org.boc.rule;

/**
 * Describe interface ExpressionFormatter here.
 *
 *
 * Created: Thu Dec  9 17:36:21 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class ExpressionFormatter {
  int lineno = 0;
  public void start()
  {
    System.out.print((++lineno)+".");
  }
  public void finish()
  {
  }
  public void keyword(String name)
  {
    System.out.print(name);
  }
  public void variable(String name)
  {
    System.out.print(name);
  }
  public void function(String name)
  {
    System.out.print(name);
  }
  public void newline(String name)
  {
     System.out.print(name + (++lineno)+".");
  }
  public void space(String name)
  {
    System.out.print(name);
  }
  public void property(String name)
  {
    System.out.print(name);
  }
  public void constant(String name)
  {
    System.out.print(name);
  }
}
