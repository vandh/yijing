package org.boc.rule;

import java.lang.StringBuffer;

import org.boc.rule.ExpressionEvaluatorImpl;
import org.boc.rule.ExpressionVisitor;
import org.boc.rule.Literal;
import org.boc.rule.NamedValue;

import java.lang.String;

/**
 * Describe class HtmlFormatter here.
 *
 *
 * Created: Tue Nov 23 16:14:21 2004
 *
 * @author <a href="mailto:unidevel@yahoo.com.cn">Jerry</a>
 * @version 1.0
 */
public class HtmlFormatter {

  /**
   * Creates a new <code>HtmlFormatter</code> instance.
   *
   */
  public HtmlFormatter() {

  }

  public String format(String script)
    throws ELException
  {
    ExpressionEvaluatorImpl evaluator = new ExpressionEvaluatorImpl();
    HtmlVisitor visitor = new HtmlVisitor();
    evaluator.travel(script, visitor);
    return visitor.toString();
  }

  public void setNewUrl(String url)
  {
  }

  public void setEditUrl(String url)
  {
  }

  class HtmlVisitor
    extends ExpressionVisitor
  {
    StringBuffer script = new StringBuffer();
    public void visit(Literal constant)
    {
      script.append("<a class=\"const\" href=\"\">");
      script.append(constant.getValue());
      script.append("</a>");
    }

    public void visit(NamedValue variable)
    {
      script.append("<a class=\"vairable\" href=\"\">");
      script.append(variable.getExpressionString());
      script.append("</a> ");
    }

    public void visit(String keyword)
    {
      //script.append(" <a class=\"keyword\">");
      //script.append(" ");
      script.append(keyword);
      script.append(" ");
      //script.append("</a> ");
    }

    public void visitFunction(String name)
    {
      script.append(name);
    }

    public String toString()
    {
      return script.toString();
    }
  }
}
