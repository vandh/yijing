package org.boc.event.qm;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.ui.qm.QiMenFrame;
import org.boc.ui.qm.Tip;

public class TipMouseMotionAdaption extends MouseMotionAdapter{
	private Tip tip;
	private QiMenFrame frame;
	//private MyTextPane textPane;  //显示信息的文本框
	
	private static JDialog jdTip ;  //提示对话框
	private JLabel lblText;  //提示显示的文本标签
	
	private int w; //调整提示框的x坐标
	private int h; //调整提示框的y坐标
	
	public static JDialog getTipwindow() {
		return jdTip;
	}
	public TipMouseMotionAdaption(MyTextPane textPane,BasicJPanel bframe) {
		this.frame = (QiMenFrame)bframe;
		tip = new Tip(textPane, frame);
		w = 30;
		h = 135;
				
		lblText = new JLabel();
		lblText.setBorder(BorderFactory.createEtchedBorder());
		jdTip = new JDialog();
		jdTip.setUndecorated(true);
		jdTip.add(lblText);	
		jdTip.addMouseListener(new MouseListener() {			
			public void mouseReleased(MouseEvent e) {				
			}			
			public void mousePressed(MouseEvent e) {				
			}			
			public void mouseExited(MouseEvent e) {				
			}			
			public void mouseEntered(MouseEvent e) {
				((JDialog)e.getComponent()).dispose();
			}			
			public void mouseClicked(MouseEvent e) {
				((JDialog)e.getComponent()).dispose();
			}
		});
	}
	
	
	
	//鼠标移动事件，会提示
	public void mouseMoved(MouseEvent e) {
		int x=e.getX(), y = e.getY();
		if(x>=89 && x<=199 && y>=21 && y<=32) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			return;
		}
		
		String text = tip.getToolTip(e);
		if (text != null) {
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			//textPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			//textPane.setToolTipText(text);
			//ToolTipManager.sharedInstance().setDismissDelay(600000);// 设置为5秒			
			mytooltip(e.getPoint(), text);
		} else {
			jdTip.dispose();		 //隐藏提示
			e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			//textPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			//textPane.setToolTipText(null);			
		}
	}
	/**
	 * 创建一个显示的对话框紧凑显示
	 * p为当前鼠标的位置，text为要显示的文本
	 * @param p
	 * @param text
	 */
	public void mytooltip(Point p, String text) {		
		lblText.setText(text);
		jdTip.pack();  //紧凑显示
		//System.out.println("w="+w+":h="+h);
		jdTip.setLocation(p.x+w,p.y+h);  //显示位置
		jdTip.setAlwaysOnTop(true); //总是前端显示
		jdTip.setVisible(true);
	}
	
	@Override
	protected void finalize() throws Throwable {		
		super.finalize();
		jdTip = null;
		lblText = null;
		tip = null;
	}
}
