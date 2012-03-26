package org.boc.rule;

import org.boc.rule.Expression;

import java.util.List;
import java.util.Iterator;
/**
 * Describe class ComponundStatement here.
 *
 *
 * Created: Sat Aug 14 22:26:01 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class CompoundStatement extends Expression {
  List exprs;
  public CompoundStatement(List exprs)
  {
    this.exprs = exprs;
  }
  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    boolean first = true;
    String ret = "";
    for ( Iterator it = exprs.iterator(); it.hasNext(); ) {
      Expression expr = (Expression)it.next();
      if ( first ) {
	ret = expr.getExpressionString();
	first = false;
      }
      else {
	ret += "; "+ expr.getExpressionString();
      }
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
    Object ret = null;
    for ( Iterator it = this.exprs.iterator(); it.hasNext(); ) {
      Expression expr = (Expression)it.next();
      if ( expr == null ) {
	continue;
      }
      ret = expr.evaluate(pResolver, functions, pLogger);
    }
    return ret;
  }
  //-------------------------------------

  public void travel(ExpressionVisitor visitor)
    throws ELException
  { 
    visitor.visit("{");
    for ( Iterator it = this.exprs.iterator(); it.hasNext(); ) {
      Expression expr = (Expression)it.next();
      if ( expr == null ) {
	continue;
      }
      visitor.visit(expr);
     }
    visitor.visit("}");
  }  
}
