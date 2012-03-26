package org.boc.rule;

public interface VariableResolver {
  public Object resolveVariable (String pName)
    throws ELException;
  public void setVariable(String name, Object value)
    throws ELException;
}
