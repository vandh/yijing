package org.boc.event.qm;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import org.boc.calendar.ui.CalendarForm;
import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenPublic;
import org.boc.ui.MyTextPane;
import org.boc.ui.PopPanel;
import org.boc.ui.qm.QiMenFrame;
import org.boc.ui.qm.QimenPopupMenu;
import org.boc.ui.qm.QmCalendarForm;
import org.boc.ui.qm.Tip;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.PrintWriter;

public class QmClickListener implements MouseListener{
	private QiMenFrame frame;
	private PrintWriter pw ;
	private QimenPublic pub;
	private DaoQiMen daoqm;	
	
	public static final int w0 = 633;  //初始宽度，无树
	public static final int h0 = 140;  //初始高度，无头
	public static final int y_nohead = 30;   //有头部时的，y增加30
	private static PopPanel pop ;					//双击宫后的弹出框
	private static PopPanel pop2;					//双击大字体或水红色字体后的提示弹出框
	private static QmCalendarForm calendar;	//双击四柱后的日期面板
	private StyledDocument styledDoc;
	private MyTextPane textPane;
	private String bigFont ;	//大字体，为八门、九星、八神、天干
	private int pinkGong;			//水红色字体，为宫的序号
	private HtmlMultiLineControl html = new HtmlMultiLineControl();
	
	public static PopPanel getPop() {
		return pop;
	}
	public static PopPanel getPop2() {
		return pop2;
	}
	public static CalendarForm getCalendarForm() {
		return calendar;
	}
	
	public QmClickListener(JPanel frame2) {
		this.frame = (QiMenFrame)frame2;
		pub = frame.getDelQiMenMain().getQimenPublic();
		daoqm = pub.getDaoQiMen();
		
		pw = new PrintWriter();
		pop = new PopPanel(QimenPopupMenu.WIDTH1+175-QiMen2.LEFT, QimenPopupMenu.HEIGHT1-97);
		pop.setLocation(w0 + QiMen2.LEFT, h0 + (QiMen2.HEAD ? y_nohead : 0));  
		pop.setUndecorated(true);  		
		pop2 = new PopPanel();		
		pop2.setLocation(w0 + QiMen2.LEFT, h0 + (QiMen2.HEAD ? y_nohead : 0));
		pop2.setUndecorated(true);
		
		calendar = new QmCalendarForm();
		calendar.setParent(frame);
		calendar.setAlwaysOnTop(true);		
		calendar.setLocation(635+QiMen2.LEFT,170);
//		calendar.setTitle(null);
//		calendar.setUndecorated(true);
		calendar.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	}
	
//	public void click1(MouseEvent e) {
//	//89,21~199,32之间双击，则弹出日期框
//		
//	}
	
	public void click2(MouseEvent e) {
		int x=e.getX(), y = e.getY();
		//1. 双击显示了时间，则各弹出框均关闭
		if(x>=89 && x<=199 && y>=21 && y<=32) {		
			if(calendar.isVisible()) calendar.setVisible(false);
			else {								
				TipMouseMotionAdaption.getTipwindow().setVisible(false);
				frame.update2(1);  //更新时间面板，true为显示
			}
			if(pop.isVisible())	pop.setVisible(false);
			if(pop2.isVisible())	pop2.setVisible(false);
			return;
		}
		
		//2. 双击了大字体或水红色字体，如果均不是，则关闭
		if(textPane==null) textPane = frame.getResultPane().getTextPane();
		int pos = textPane.viewToModel(e.getPoint());	
		if(isBigfont(e , pos)) { 
			//System.out.println(bigFont);
			if(pop.isVisible()) pop.setVisible(false);
			showPop2(getDesc(bigFont),e);
			return;
		}else if(isPinkfont(pos)) { 
			if(pop.isVisible()) pop.setVisible(false);
			showPop2(QiMen2.GUA[pinkGong][1],e);			
			return;
		}else{
			if(pop2.isVisible()) pop2.setVisible(false);
		}
		
		//3. 双击宫中信息显示提示框pop，如果不在宫内，则关闭pop
		this.showPop(e);
	}
	
	/**
	 * 是否大字体，显示八门、九星、八神、天干信息
	 */
	public boolean isBigfont(MouseEvent e,int pos) {		
		if (styledDoc == null) {
			styledDoc = (StyledDocument) textPane.getDocument();
		}
		((AbstractDocument) styledDoc).readLock();
		AttributeSet as = null;
		try {
			as = styledDoc.getCharacterElement(pos).getAttributes();
		} finally {
			((AbstractDocument) styledDoc).readUnlock();
		}
		Enumeration en = as.getAttributeNames();
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					try {
						bigFont = styledDoc.getText(pos, 1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					return true;
				}
				break;
			}
		}
		return false;
	}
	/**
	 * 是否是水红色字体，显示九宫信息
	 */
	public boolean isPinkfont(int pos) {
		for(int i = 1; i<Tip.REDWORD.length; i++) {
			if(Tip.REDWORD[i]==pos) {
				pinkGong = i;
				return true;
			}
		}
		return false;
	}
	
	public String getDesc(String key) {
		// 1. 是否是天盘干或地盘干
		for (int i = 1; i <= 10; i++) {
			if (key.equals(YiJing.TIANGANNAME[i])) {
				return QiMen2.GAN[i][1];
			}
		}
		// 2.是否是九星
		for (int i = 0; i < QiMen.jx1.length; i++) {
			if (key.equals(QiMen.jx1[i])) {
				return QiMen2.XING[i][1];
			}
		}
		// 3. 是否是八门
		for (int i = 0; i < QiMen.bm1.length; i++) {
			if (key.equals(QiMen.bm1[i])) {
				return QiMen2.MEN[i][1];
			}
		}
		//4. 是否是八神
		for (int i = 0; i < QiMen.bs1.length; i++) {
			if (key.equals(QiMen.bs1[i])) {
				return QiMen2.SHEN[i][1];
			}
		}
		return null;
	}

	Timer mouseTimer ;
	public void mouseClicked(MouseEvent e) {	
//		final MouseEvent e2 = e;
//		if (e.getClickCount() == 1) {
//			mouseTimer = new Timer(350, new ActionListener() {
//				public void actionPerformed(ActionEvent evt) {
//					click1(e2);
//					mouseTimer.stop();
//				}
//			});
//			mouseTimer.restart();
//		} else  if (e.getClickCount() == 2 && mouseTimer.isRunning()) {
//			mouseTimer.stop();
//			//System.out.println("Double");
//			click2(e);
//		}
		if (e.getClickCount() == 2 ) click2(e);
	}
	/**
	 * 只需要x1,y1,其它数字就可以推出来了
	 * 4宫： 11,66~199,199
	 * 9宫：   202,66  ~  391,199
	 * 2宫： 393,66  ~   583, 199
	 * 3宫： 11,215   ~   199,349
	 * 5宫：  202，215 ～     391，  349
	 * 7宫：  393， 215  ～      583,  349
	 * 8宫： 10,366  ~    199, 499
	 * 1宫： 202, 366 ~   391, 499
	 * 6宫： 393， 366  ～        583， 499 
	 * @param e
	 * @return
	 */
	private final int x1=10, x2=x1+190,x3=x2+2,x4=x3+190,x5=x4+2,x6=x5+190;
	private final int y1=66, y2=y1+133,y3=y2+16, y4=y3+133, y5=y4+16, y6=y5+133;
	private int getGong(MouseEvent e) {
		int x = e.getX(); 
		int y = e.getY();
		if(x>=x1 && x<=x2 && y>=y1 && y<=y2) return 4;
		else if(x>=x3 && x<=x4 && y>=y1 && y<=y2) return 9;
		else if(x>=x5 && x<=x6 && y>=y1 && y<=y2) return 2;
		else if(x>=x1 && x<=x2 && y>=y3 && y<=y4) return 3;
		else if(x>=x3 && x<=x4 && y>=y3 && y<=y4) return 5;
		else if(x>=x5 && x<=x6 && y>=y3 && y<=y4) return 7;
		else if(x>=x1 && x<=x2 && y>=y5 && y<=y6) return 8;
		else if(x>=x3 && x<=x4 && y>=y5 && y<=y6) return 1;
		else if(x>=x5 && x<=x6 && y>=y5 && y<=y6) return 6;
		else
			return 0;
	}
	private final String NLINE =  "\r\n           ";
	private final String NLINE2 = "\r\n                 ";
	private final String NLINE3 =  "\r\n";
	private void showPop(MouseEvent e) {			
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		pw.setDocument(doc);		
		int gong = getGong(e);		
		if(gong==0) {
			if(pop.isVisible()) pop.setVisible(false);
			if(calendar!=null) calendar.setVisible(false);
			return;
		}
		
		String title = QiMen.dpjg[gong]+"宫参考信息";
		pop.setTitle(title);
		int men = daoqm.gInt[3][1][gong];  //门
		int shen = daoqm.gInt[1][1][gong]; //神
		int x = daoqm.gInt[2][1][gong];    //星
		int[] dzs = pub.getDiziOfGong(gong);  //九宫所藏地支
		int[] tpgs = pub.getTpjy(gong);
		int[] dpgs = pub.getDpjy(gong); 
		
//		System.out.println("gong="+gong+";men="+men+";xing="+x+";shen="+shen+"\r\n天盘干="+
//				tpgs[0]+"，"+tpgs[1]+"；地盘干="+dpgs[0]+","+dpgs[1]);
		try{
			doc.remove(0, doc.getLength());
  		pw.bblack("                     "+title);
  		pw.newLine();
  		pw.newLine();
//  		加上年月日时节气标题
//  		pw.sblue("    "+daoqm.getHead1(frame.getZhuanFei(), frame.getCaiYun(), 
//  				frame.getXiaozhifu(), frame.getJu()));
//  		pw.newLine();
//  		pw.newLine();
  		
  		pw.println("一、吉格凶格：",PrintWriter.MRED,true);
  		List<Integer> ges = pub.getGejus(gong);
  		if(ges!=null && ges.size()>0) {
	  		for(int ge : ges) {
	  			if(ge==0) continue;
	  			//System.out.println("ge="+ge);
	  			pw.print("  "+QiMen.gGejuDesc[ge][0]+": ",PrintWriter.MBLUE,true);
	  			pw.print(QiMen.gGejuDesc[ge][1].replaceAll(QiMen.HH, NLINE),PrintWriter.SBLACK,false);
	  			pw.newLine();
	  		}
  		}  		
  		pw.newLine();
  		
  		pw.println("二、十干克应：",PrintWriter.MRED,true);  
  		int[] kys1 = pub.getShiganKeying(gong);
  		int[] kys = new int[kys1.length+4];	//暗干克应
  		int k = 0;
  		for(; k<kys1.length; k++) kys[k] = kys1[k];
//  		int[] dpgs2 = {};
//  		if(gong==5) { //中五宫所寄之宫的地盘干
//  			int jg = gong==5?daoqm.getTpJigong() : gong;  //中五宫在天盘所寄之宫
//  			dpgs2 = pub.getDpjy(jg);
//  		}
//  		kys[++k] = QiMen.gan_gan[pub.getAngan(gong)][gong==5?dpgs2[0] : dpgs[0]];
//  		kys[++k] = QiMen.gan_gan[pub.getAngan(gong)][gong==5?dpgs2[1] : dpgs[1]];
  		kys[++k] = QiMen.gan_gan[pub.getAngan(gong)][dpgs[0]];
  		kys[++k] = QiMen.gan_gan[pub.getAngan(gong)][dpgs[1]];
  		
  		
  		for(int ky : kys) {
  			if(ky==0) continue;
  			pw.print("  "+QiMen.gGejuDesc[ky][0]+": ",PrintWriter.MBLUE,true);  			
  			pw.print(QiMen.gGejuDesc[ky][1].replaceAll(QiMen.HH, NLINE),PrintWriter.SBLACK,false);
  			pw.newLine();
  		}  		
  		pw.newLine();
  		
  		//如果是九星伏呤，则八门克应与意象要避免重复
  		if(pub.isXingFu()) dpgs[0] = dpgs[1] = 0;
  		pw.println("三、八门克应：",PrintWriter.MRED,true);  		 
  		int[] mky = {QiMen.men_men[men][QiMen.dp_mengong_mc[gong]],
  				QiMen.shen_men[shen][QiMen.dp_menxing_mc[daoqm.gInt[3][1][gong]]],
  				QiMen.men_gan[men][tpgs[0]],QiMen.men_gan[men][tpgs[1]],
  				QiMen.men_gan[men][dpgs[0]],QiMen.men_gan[men][dpgs[1]]};
  		for(int ky : mky) {
  			if(ky==0) continue;
  			pw.print("  "+QiMen.gGejuDesc[ky][0]+": ",PrintWriter.MBLUE,true);  			
  			pw.print(QiMen.gGejuDesc[ky][1].replaceAll(QiMen.HH, NLINE),PrintWriter.SBLACK,false);
  			pw.newLine();
  		}  		
  		pw.newLine();
  		
  		pw.println("四、九星克应：",PrintWriter.MRED,true);  		
  		int ky = QiMen.xing_men[x][men];
  		if(ky!=0) {
	  		pw.print("  "+QiMen.gGejuDesc[ky][0]+": ",PrintWriter.MBLUE,true);  			
				pw.print(QiMen.gGejuDesc[ky][1].replaceAll(QiMen.HH, NLINE),PrintWriter.SBLACK,false);
				pw.newLine();
  		}
			pw.print("  天"+QiMen.jx1[x==0?5:x]+"值"+YiJing.DIZINAME[SiZhu.sz]+"时: ",PrintWriter.MBLUE,true);  			
			pw.print(QiMen2.JXZS[x==0?5:x][SiZhu.sz].replaceAll(QiMen.HH, NLINE),PrintWriter.SBLACK,false);
			pw.newLine();
			pw.newLine();
 		
			pw.println("五、万物意象：",PrintWriter.MRED,true);					
			String[] yxs = {QiMen2.GUA[gong][0],QiMen2.MEN[men][0],QiMen2.XING[x==0?5:x][0],
					QiMen2.SHEN[shen][0],QiMen2.GAN[tpgs[0]][0],QiMen2.GAN[tpgs[1]][0],
					QiMen2.GAN[dpgs[0]][0],QiMen2.GAN[dpgs[1]][0],
					QiMen2.ZI[dzs[0]][0],QiMen2.ZI[dzs[1]][0]};
			String[] yxs2 = {QiMen2.GUA[gong][1],QiMen2.MEN[men][1],QiMen2.XING[x==0?5:x][1],
					QiMen2.SHEN[shen][1],QiMen2.GAN[tpgs[0]][1],QiMen2.GAN[tpgs[1]][1],
					QiMen2.GAN[dpgs[0]][1],QiMen2.GAN[dpgs[1]][1],
					QiMen2.ZI[dzs[0]][1],QiMen2.ZI[dzs[1]][1]};
  		for(int i=0; i<yxs.length; i++) {
  			if(yxs[i]==null) continue;
  			pw.print("  "+yxs[i]+": ",PrintWriter.MBLUE,true); 
  			pw.print(NLINE,PrintWriter.SBLACK,false);
  			pw.print(yxs2[i].replaceAll(QiMen.HH, NLINE),PrintWriter.SBLACK,false);
  			pw.newLine();
  		}  		
  		pw.newLine();
  		
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		text.roll20();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));		
		pop.setVisible(true);
	}
	
	private void showPop2(String desc, MouseEvent e) {
		if(desc==null) return;
		desc = desc.trim().replaceAll(QiMen2.HH, NLINE3);
		MyTextPane text = pop2.getTextpane();	
		Document doc = text.getDocument();				
		pw.setDocument(doc);
		try {
			doc.remove(0, doc.getLength());
			pw.print(desc,pw.MBLUE,false);
			//必须是JLabel才可以，JTextPane不行
			//text.setText(html.CovertDestionString(desc.replaceAll("<BR>", "\r\n")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//pop2.setLocation(e.getX()+60,e.getY()-100);
		pop2.pack(); 
		pop2.setVisible(true);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
