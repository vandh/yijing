package org.boc.rule;

import org.boc.rule.Expression;

import java.util.List;
import java.util.Iterator;
/**
 * Describe class ExpressionStatement here.
 *
 *
 * Created: Sat Aug 14 22:26:01 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class ExpressionStatement extends Expression {
  Expression expr;
  public ExpressionStatement(Expression expr)
  {
    this.expr = expr;
  }
  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    return expr.getExpressionString() + ";" ;
  }

  //-------------------------------------
  /**
   *
   * Evaluates the expression in the given context
   **/
  public Object evaluate (VariableResolver pResolver,
			  FunctionMapper functions,
			  Logger pLogger)
    throws ELException
  {
    return expr.evaluate(pResolver, functions, pLogger);
  }
  //-------------------------------------

  public void travel(ExpressionVisitor visitor)
    throws ELException
  { 
    expr.travel(visitor);
    visitor.visit(";");
  }  
}
