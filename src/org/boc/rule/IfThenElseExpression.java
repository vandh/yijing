package org.boc.rule;

import java.util.List;
import java.util.Iterator;
/**
 * Describe class IfThenElseExpression here.
 *
 *
 * Created: Sat Aug 14 10:15:39 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class IfThenElseExpression extends Expression {
  Expression cond = null;
  //  List then = null, other = null;
  Expression then = null, other = null;

  /**
   * Creates a new <code>IfThenElseExpression</code> instance.
   *
   */
  public IfThenElseExpression(Expression cond,
			      Expression then,
			      Expression other) {
    this.cond = cond;
    this.then = then;
    this.other = other;
  }

  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    String expr = "if "+ cond.getExpressionString() + " then " + then.getExpressionString();
    if ( other != null ) {
      expr += other.getExpressionString();
    }
    return expr;
  }

  public String getExpressionString(List exprs)
  {
    String ret = "";
    for (Iterator it = exprs.iterator(); it.hasNext(); ) {
      Expression expr = (Expression) it.next();
      ret += expr.getExpressionString();
    }
    return ret;
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
    Object ret = cond.evaluate(pResolver, functions, pLogger);
    if ( ret instanceof Boolean ) {
      if ( ((Boolean)ret).booleanValue() ) {
	ret = then.evaluate(pResolver, functions, pLogger);
      }
      else if ( other != null ) {
	ret = other.evaluate(pResolver, functions, pLogger);
      }
      else {
	ret = null;
      }
    }
    else {
      if ( ret != null ) {
	ret = then.evaluate(pResolver, functions, pLogger);
      }
      else if ( other != null ) {
	ret = other.evaluate(pResolver, functions, pLogger);
      }
      else {
	ret = null;
      }
    }
    return ret;
  }

  //-------------------------------------
  public Object evaluate (List exprs,
			  VariableResolver pResolver,
			  FunctionMapper functions,
			  Logger pLogger)
    throws ELException
  {
    Object ret = null;
    for ( Iterator it = exprs.iterator(); it.hasNext(); ) {
      Expression expr = (Expression)it.next();
      ret = expr.evaluate(pResolver, functions, pLogger);
    }
    return ret;
  }

  public void travel(ExpressionVisitor visitor)
    throws ELException
  {
    visitor.visit("如果");
    this.cond.travel(visitor);
    visitor.visit("那么");
    this.then.travel(visitor);
    if ( this.other != null ) {
      visitor.visit("否则");
      this.other.travel(visitor);
    }
  }
}
