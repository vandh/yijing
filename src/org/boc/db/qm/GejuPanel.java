package org.boc.db.qm;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.Messages;

public class GejuPanel extends ResultPanel{
  private MyTextPane textPane;
  private JScrollPane jScrollPane;

  public GejuPanel() {
    super();
    this.setLayout(new BorderLayout());
    this.add(getJScrollTextArea(), BorderLayout.CENTER);
  }

  public MyTextPane getTextPane() {
  	return textPane;
  }
  
  /**
   * 更新显示内容
   * @param str String
   */
  public void updResult(String str) {
  	if(str==null) {
  	//以下分别是显示第一行，不用每次都滚动到最后一行显示
      textPane.setCaretPosition(0);
      textPane.setSelectionStart(0);
      textPane.setSelectionEnd(0);
      return;
  	}
  	
  	Document doc = textPane.getDocument();
  	SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, Color.BLACK);
		
  	try {
  		doc.remove(0, doc.getLength());
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			Messages.error(e.getMessage());
			e.printStackTrace();
		}
		//以下分别是显示第一行，不用每次都滚动到最后一行显示
    textPane.setCaretPosition(0);
    textPane.setSelectionStart(0);
    textPane.setSelectionEnd(0);
  }

  /**
     * 得到滚动的文本显示框
     * @return
     */
    private JScrollPane getJScrollTextArea() {
    	textPane = new MyTextPane();      
      jScrollPane = new JScrollPane(textPane);
      return jScrollPane;
    }

}
