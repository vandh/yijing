package org.boc.rule;

import java.lang.reflect.Method;

public interface FunctionMapper {
  Method resolveFunction(String prefix, String localName);
}
