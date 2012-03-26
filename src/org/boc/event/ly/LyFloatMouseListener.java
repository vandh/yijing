package org.boc.event.ly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.boc.db.ly.Liuyao;
import org.boc.ui.PopPanel;
import org.boc.ui.ly.LiuYaoFrame;
import org.boc.xml.XmlProc;

public class LyFloatMouseListener implements ActionListener{
	private LiuYaoFrame frame; 
	public LyFloatMouseListener(JComponent frame) {
		this.frame = (LiuYaoFrame)frame;
	}
	public void actionPerformed(ActionEvent e) {
		//Messages.info(e.getActionCommand());
		String cmd = e.getActionCommand();
		if(cmd.equals("flush")) {
			flush();
			return;
		}else if(cmd.equals("io")) {
			Liuyao.IO = !Liuyao.IO;
			io();
			return;
		}else if(cmd.equals("now")) {
			Liuyao.NOW = !Liuyao.NOW;
			change();
			return;
		}else if(cmd.equals("save")) {
			frame.save();
			return;
		}else if(cmd.equals("tip")) {
			Liuyao.TIP = !Liuyao.TIP;
			if(!Liuyao.TIP)
				frame.getResultPane().getTextPane().removeMouseMotionListener(frame.getMouseMotionListener());
			else
				frame.getResultPane().getTextPane().addMouseMotionListener(frame.getMouseMotionListener());
			return;
		}else if(cmd.equals("ma")) 
			Liuyao.MA = !Liuyao.MA;
		else if(cmd.equals("jxge")) 
			Liuyao.JXGE = !Liuyao.JXGE;			
		else if(cmd.equals("wsxq")) 
			Liuyao.WSXQ = !Liuyao.WSXQ;	
		else if(cmd.equals("head")) 
			Liuyao.HEAD = !Liuyao.HEAD;
		else if(cmd.equals("all")) 
			Liuyao.ALL = !Liuyao.ALL;	
		
		if(cmd.equals("head")) {
			//不管有无显示pop提示，都应相应调整双击帮助框的位置
			PopPanel pop = LyClickListener.getPop();
			PopPanel pop2 = LyClickListener.getPop2();
			if(pop2!=null) pop2.setVisible(false);
			//不管有没有显示，也需要调整位置，否则就不准确了
			if(pop!=null) 
				pop.setLocation(pop.getX(), Liuyao.HEAD  ? 
							pop.getY()+LyClickListener.y_nohead:pop.getY()-LyClickListener.y_nohead);
		}else if(cmd.equals("all")) {
			PopPanel pop = LyClickListener.getPop();
			PopPanel pop2 = LyClickListener.getPop2();
			if(pop2!=null) pop2.setVisible(false);
			if(pop!=null) {  //皆真皆假者不需要动，只有一真一假才动
				if((Liuyao.HEAD && !Liuyao.ALL) || (!Liuyao.HEAD && Liuyao.ALL))
				pop.setLocation(pop.getX(), Liuyao.ALL  ? 
							pop.getY()+LyClickListener.y_nohead:pop.getY()-LyClickListener.y_nohead);
			}
		}
		
		if(!cmd.equals("all")) {
			Liuyao.ALL=false;  //只不要是ALL，则ALL要设为假，否则影响判断
		}
		else if(cmd.equals("all")){  //ALL为假，则其余要全部为假，All为真，其余要全部为真
			Liuyao.JXGE = Liuyao.ALL;
			Liuyao.WSXQ = Liuyao.ALL;
			Liuyao.MA = Liuyao.ALL;	
			Liuyao.HEAD = Liuyao.ALL;
		}				
		
		change();		
	}
	
	private void flush() {
		XmlProc.loadFromXmlFile();		
	}
	
	private void io() {
		if(Liuyao.IO) XmlProc.loadFromSysFile();
		else XmlProc.loadFromXmlFile();
	}
	
	private void change() {
		change(false);		
	}
	private void change(boolean issave) {
		frame.pan();	
		frame.getResultPane().getTextPane().roll20();
	}

}
