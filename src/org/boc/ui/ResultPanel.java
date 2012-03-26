package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.boc.ui.qm.Tip;
import org.boc.util.Messages;

public class ResultPanel extends JPanel{
  private MyTextPane textPane;  //显示信息的文本框
  private JScrollPane jScrollPane;  //滚动条  
  private BasicJPanel frame;  //如QimenFrame/SizhuFrame等
  private Tip tip; 
  private BorderLayout layout;

  public ResultPanel() {
    super();
    layout = new BorderLayout();
    textPane = new MyTextPane();    
    this.setLayout(layout);
    this.add(getJScrollTextArea(), layout.CENTER);
  }
  
  public void addFloatToolbar(JToolBar toolBar) {
    this.add(toolBar, layout.WEST);
    this.updateUI();
  }
  public void delFloatToolbar(JToolBar toolBar) {
  	this.remove(toolBar);
  	this.validate();
  	this.updateUI();
  }
  public void addInputPanel(JToolBar inanel) {
    this.add(inanel, layout.EAST);
    this.updateUI();
  }
  public void delInputPanel(JToolBar inanel) {
  	this.remove(inanel);
  	this.validate();
  	this.updateUI();
  }
  
  public MyTextPane getTextPane() {
  	return textPane;
  }
  public void setTextPane(MyTextPane textPane) {
  	this.textPane = textPane;
  }
  
  /**
   * 更新显示内容
   * @param str String
   */
  public void updResult(String str) {
  	if(str==null) {
  	//以下分别是显示第一行，不用每次都滚动到最后一行显示
      textPane.roll20();
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
    textPane.roll20();
  }

  /**
     * 得到滚动的文本显示框
     * @return
     */
    private JScrollPane getJScrollTextArea() {    	    	    
      jScrollPane = new JScrollPane(textPane);
      return jScrollPane;
    }
//    /**
//     * 年、月、日、时用蓝色字体，值符、值使门用红色字体
//     * @param doc
//     * @throws BadLocationException
//     */
//    private void updateString(Document doc) throws BadLocationException {    	  	
//    	String FLAG = "┃";
//    	String[] blueStr = {"年","月","日","时"};
//    	String[] redStr = {"符",""};
//    	String[] yellowString = {"旺","相","休","囚","死","废"};
//    	
//    	SimpleAttributeSet redStyle = new SimpleAttributeSet();
//  		StyleConstants.setForeground(redStyle, Color.RED);  	
//  		//StyleConstants.setBold(redStyle, true);
//  		//StyleConstants.setFontSize(redStyle, 18);
//  		SimpleAttributeSet blueStyle = new SimpleAttributeSet();
//  		StyleConstants.setForeground(blueStyle, Color.BLUE);
//  		SimpleAttributeSet yelStyle = new SimpleAttributeSet();
//  		StyleConstants.setForeground(yelStyle, Color.PINK);  //MAGENTA ORANGE
//  		//StyleConstants.setBackground(yelStyle, Color.lightGray);
//  		
//  		String str = doc.getText(0, doc.getLength());
//  		if(str==null || str.trim().length()==0) return;
//  		int fromIndex = str.indexOf(FLAG);  //从|开始找需要替换的字符串
//  		//得到值使门
//  		int izhishi = str.indexOf("值使",0);
//  		redStr[1] = str.substring(izhishi+3,izhishi+4);
//  		//将年月日时变成蓝色
//  		for(int i=0; i<blueStr.length; i++) {
//  			int start = str.indexOf(blueStr[i]+" ", fromIndex);
//  			if(start<0) break;
//  			doc.remove(start, 2);
//  			doc.insertString(start, blueStr[i]+" ", blueStyle);
//  		}
//  		//将直符值使变成红色
//  		for(int i=0; i<redStr.length; i++) {
//  			int start = str.indexOf(" "+redStr[i]+" ", fromIndex);
//  			if(start<0) break;
//  			doc.remove(start, 3);
//  			doc.insertString(start, " "+redStr[i]+" ", redStyle);
//  		}
//  		//将各宫旺衰标识成绿色
//  		for(int i=0; i<yellowString.length; i++) {
//  			int start = str.indexOf(FLAG+yellowString[i]+" ", 0); //
//  			if(start<0) break;
//  			doc.remove(start+1, 2);
//  			doc.insertString(start+1, yellowString[i]+" ", yelStyle);
//  			while((start = str.indexOf(FLAG+yellowString[i]+" ", start+5))!=-1) {
//  				doc.remove(start+1, 2);
//    			doc.insertString(start+1, yellowString[i]+" ", yelStyle);
//  			}
//  		}
//    }


}
