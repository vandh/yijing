package org.boc.rule;

/**
 * Describe class ExpressionVisitor here.
 *
 *
 * Created: Mon Nov 22 23:57:57 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class ExpressionVisitor {

  /**
   * Creates a new <code>ExpressionVisitor</code> instance.
   *
   */
  public ExpressionVisitor() {

  }

  public void visit(Expression expression) { }
  public void visit(ExpressionString expression) { }
  public void visit(ValueSuffix object) { }
  public void visit(BinaryOperator operation) { }
  public void visit(UnaryOperator operation) { }
  public void visit(Literal constant) { }
  public void visit(NamedValue variable) { } 
  public void visit(String keywork) { }
  public void visitFunction(String name) { }
}
