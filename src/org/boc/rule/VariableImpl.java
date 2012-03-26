package org.boc.rule;

/**
 * Describe class VariableImpl here.
 *
 *
 * Created: Sat Aug 14 09:29:10 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class VariableImpl 
  implements Variable
{
  public VariableImpl()
  {
  }
  Object value;
  public void set(Object value)
  {
    this.value = value;
  }
  public Object get()
  {
    return this.value;
  }
}
