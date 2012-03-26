package org.boc.event.qm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenGeju1;
import org.boc.ui.Main;
import org.boc.ui.PopPanel;
import org.boc.ui.qm.QiMenFrame;
import org.boc.util.Public;
import org.boc.xml.XmlProc;

public class FloatMouseListener implements ActionListener{
	private QiMenFrame frame; 
	public FloatMouseListener(JComponent frame) {
		this.frame = (QiMenFrame)frame;
	}
	public void actionPerformed(ActionEvent e) {
		//Messages.info(e.getActionCommand());
		String cmd = e.getActionCommand();
		if(cmd.equals("flush")) {
			flush();
			return;
		}
		if(cmd.equals("io")) {
			QiMen2.IO = !QiMen2.IO;
			io();
			return;
		}
		if(cmd.equals("go")) {
			QiMen2.YANG = !QiMen2.YANG;
			changeQimen();
			return;
		}else if(cmd.equals("save")) {
			changeQimen(true);
			return;
		}else if(cmd.equals("zf")) {			
			frame.setZhuanFei(QiMen2.ZF=!QiMen2.ZF);
			Main.setStatusInfo(Public.QM);  //更新状态信息提示
			changeQimen();
			return;
		}else if(cmd.equals("rb")) {
			frame.setCaiYun(QiMen2.RB=!QiMen2.RB);
			Main.setStatusInfo(Public.QM);  //更新状态信息提示
			changeQimen();
			return;
		}else if(cmd.equals("td")) {
			frame.setXiaozhifu(QiMen2.TD=!QiMen2.TD);
			Main.setStatusInfo(Public.QM);  //更新状态信息提示
			changeQimen();
			return;
		}else if(cmd.equals("kg")) {
			frame.setJigong(QiMen2.KG=!QiMen2.KG);
			Main.setStatusInfo(Public.QM);  //更新状态信息提示
			changeQimen();
			return;
		}else if(cmd.equals("tip")) {
			QiMen2.TIP = !QiMen2.TIP;
			if(!QiMen2.TIP)
				frame.getResultPane().getTextPane().removeMouseMotionListener(frame.getMouseMotionListener());
			else
				frame.getResultPane().getTextPane().addMouseMotionListener(frame.getMouseMotionListener());
			return;
		}
		
		if(cmd.equals("sj")) 
			QiMen2.SJ = !QiMen2.SJ;
		else if(cmd.equals("xchm")) 
			QiMen2.XCHM = !QiMen2.XCHM;		
		else if(cmd.equals("ma")) 
			QiMen2.MA = !QiMen2.MA;
		else if(cmd.equals("jtxms")) 
			QiMen2.JTXMS = !QiMen2.JTXMS;		
		else if(cmd.equals("huo")) 
			QiMen2.HUO = !QiMen2.HUO;		
		else if(cmd.equals("jxge")) 
			QiMen2.JXGE = !QiMen2.JXGE;			
		else if(cmd.equals("sgky")) 
			QiMen2.SGKY = !QiMen2.SGKY;		
		else if(cmd.equals("wsxq")) 
			QiMen2.WSXQ = !QiMen2.WSXQ;	
		else if(cmd.equals("head")) 
			QiMen2.HEAD = !QiMen2.HEAD;
		else if(cmd.equals("all")) 
			QiMen2.ALL = !QiMen2.ALL;	
		else if(cmd.equals("reset")) 
			QiMen2.XMHW = 0;		
		else if(cmd.equals("xmhw")) 
			QiMen2.XMHW = ++QiMen2.XMHW > 5 ? 0 : QiMen2.XMHW;	
		else if(cmd.equals("bian")) {
			QiMen2.XMHW = 100;
			QimenGeju1.getRandomInvoke();
		}
		
		if(cmd.equals("head")) {
			//不管有无显示pop提示，都应相应调整双击帮助框的位置
			PopPanel pop = QmClickListener.getPop();
			PopPanel pop2 = QmClickListener.getPop2();
			if(pop2!=null) pop2.setVisible(false);
			//不管有没有显示，也需要调整位置，否则就不准确了
			if(pop!=null) 
				pop.setLocation(pop.getX(), QiMen2.HEAD  ? 
							pop.getY()+QmClickListener.y_nohead:pop.getY()-QmClickListener.y_nohead);
		}else if(cmd.equals("all")) {
			PopPanel pop = QmClickListener.getPop();
			PopPanel pop2 = QmClickListener.getPop2();
			if(pop2!=null) pop2.setVisible(false);
			if(pop!=null) {  //皆真皆假者不需要动，只有一真一假才动
				if((QiMen2.HEAD && !QiMen2.ALL) || (!QiMen2.HEAD && QiMen2.ALL))
				pop.setLocation(pop.getX(), QiMen2.ALL  ? 
							pop.getY()+QmClickListener.y_nohead:pop.getY()-QmClickListener.y_nohead);
			}
		}
		
		if(!cmd.equals("all") && !cmd.equals("xmwh")) {
			QiMen2.ALL=false;  //只不要是ALL，则ALL要设为假，否则影响判断
		}
		else if(cmd.equals("all")){  //ALL为假，则其余要全部为假，All为真，其余要全部为真
			QiMen2.XCHM = QiMen2.ALL;
			QiMen2.HUO = QiMen2.ALL;
			QiMen2.SJ = QiMen2.ALL;
			QiMen2.JXGE = QiMen2.ALL;
			QiMen2.JTXMS = QiMen2.ALL;
			QiMen2.SGKY = QiMen2.ALL;
			QiMen2.WSXQ = QiMen2.ALL;
			QiMen2.MA = QiMen2.ALL;	
			QiMen2.HEAD = QiMen2.ALL;
		}				
		
		changeQimen();		
	}
	
	private void flush() {
		XmlProc.loadFromXmlFile();		
	}
	
	private void io() {
		if(QiMen2.IO) XmlProc.loadFromSysFile();
		else XmlProc.loadFromXmlFile();
	}
	
	private void changeQimen() {
		changeQimen(false);		
	}
	private void changeQimen(boolean issave) {
		QiMenFrame qmframe = (QiMenFrame)frame;
		qmframe.pan(issave);	
		qmframe.getResultPane().getTextPane().roll20();
	}

}
