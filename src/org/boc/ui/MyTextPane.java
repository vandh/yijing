package org.boc.ui;

import java.awt.Dimension;

import javax.swing.JTextPane;
/**
 * JTextPane不支持不自动换行，想要此功能，必须重载，然后指定其宽度即可
 * @author Administrator
 *
 */
public class MyTextPane extends JTextPane {
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	public void setSize(Dimension d) {
		d.width = 5000; //行的宽度需要你计算文本中最宽的一行是多少
		super.setSize(d);
	}
	
	public void roll20() {
		this.setCaretPosition(0);
		this.setSelectionStart(0);
		this.setSelectionEnd(0);
	}
}
