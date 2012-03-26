package org.boc.rule;

import java.lang.reflect.Method;

import org.boc.rule.ELException;
import org.boc.rule.Expression;
import org.boc.rule.FunctionMapper;
import org.boc.rule.VariableResolver;

/**
 * Describe class LetExpression here.
 *
 *
 * Created: Fri Aug 13 14:14:09 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class LetExpression extends Expression {

  NamedValue id;
  Expression expr;
  /**
   * Creates a new <code>LetExpression</code> instance.
   *
   */
  public LetExpression(NamedValue id, Expression expr)
  {
    this.id = id;
    this.expr = expr;
  }

  public String getExpressionString ()
  {
    return this.id + " = " + expr.getExpressionString() + ";";
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
    Object value = this.expr.evaluate(pResolver, functions, pLogger);

    pResolver.setVariable(this.id.getName(), value);
    return value;
  }

  public void travel(ExpressionVisitor visitor)
    throws ELException
  { 
    visitor.visit(this.id);
    visitor.visit("Ϊ");
    this.expr.travel(visitor);
  }  
}
