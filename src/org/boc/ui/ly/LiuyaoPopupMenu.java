package org.boc.ui.ly;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.ly.Liuyao;
import org.boc.delegate.DelYiJingMain;
import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.ui.PopPanel;
import org.boc.ui.PublicFoumulas;
import org.boc.util.Messages;
import org.boc.util.PrintWriter;

public class LiuyaoPopupMenu extends JPopupMenu 
	implements MouseListener, ActionListener {
	private PopPanel pop;
	PrintWriter pw;
	LiuYaoFrame frame;
	LiuyaoPublic pub;
	DaoYiJingMain daoly;
	DaoCalendar daocal;
	
	JMenu koujueItem; 
	JMenu analyse;
	JMenuItem cutItem, copyItem, pasteItem, deleteItem, selectAllItem, printItem;
	JMenuItem preference;
	JMenuItem swsjb2;  //十天干生旺死绝表
	JMenuItem nsqy2;   //年上起月法
	JMenuItem rsqs2;	  //日上起时法
	JMenuItem yima2; 		//驿马
	JMenuItem jz602;		//六十甲子纳音表
	
	JMenuItem zsmy2;
	JMenuItem jqmy2;
	JMenuItem swbd2;
	JMenuItem qxks2;
	JMenuItem gzjy2;
	JMenuItem jyqc2;
	JMenuItem lahy2;
	JMenuItem rtjb2;
	JMenuItem cxcg2;
	JMenuItem tsqx2;
	JMenuItem dlfs2;
	JMenuItem xrzs2;
	JMenuItem hyfw2;
	JMenuItem tyjs2;
	
	JMenuItem tool2;
	JMenuItem input2;
	
	public void setFrame(BasicJPanel frame) {
		this.frame = (LiuYaoFrame)frame;
		DelYiJingMain delly = this.frame.getDelYiJingMain();
		pub = delly.getLiuyaoPublic();
		daoly = delly.getDaoYiJingMain();
		daocal = delly.getDaoCalendar();
	}
	
	private static LiuyaoPopupMenu sharedInstance = null;
	public static LiuyaoPopupMenu getSharedInstance() { // 单例模式
		if (sharedInstance == null) {
			sharedInstance = new LiuyaoPopupMenu();			
		}
		return sharedInstance;
	}
	
	public static void LiuyaoPopupMenu(JTextComponent c) {
		if (c instanceof JTextPane && !(c instanceof JPasswordField)) {
			c.addMouseListener(LiuyaoPopupMenu.getSharedInstance());
		}
	}
	public static void uninstallFromComponent(JTextComponent c) {
		if (c instanceof JTextPane && !(c instanceof JPasswordField)) {
			c.removeMouseListener(getSharedInstance());
		}
	}

	public LiuyaoPopupMenu() {
		pop = new PopPanel();
		pw = new PrintWriter();		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		analyse = new JMenu("智能推算");
		zsmy2 = new JMenuItem("终生命运");
		jqmy2 = new JMenuItem("近段运气");
		swbd2 = new JMenuItem("失物被盗");
		lahy2 = new JMenuItem("恋爱婚姻");
		qxks2 = new JMenuItem("求学考试");
		gzjy2 = new JMenuItem("工作就业");
		jyqc2 = new JMenuItem("经营求财");
		cxcg2 = new JMenuItem("出行出国");
		rtjb2 = new JMenuItem("人体疾病");
		tsqx2 = new JMenuItem("天时气象");
		dlfs2 = new JMenuItem("地理风水");
		xrzs2 = new JMenuItem("行人走失");
		hyfw2 = new JMenuItem("怀孕分娩");
		tyjs2 = new JMenuItem("体育竞赛");
		analyse.add(zsmy2);
		analyse.add(jqmy2);
		analyse.add(swbd2);
		analyse.add(lahy2);
		analyse.add(qxks2);
		analyse.add(gzjy2);
		analyse.add(jyqc2);
		analyse.add(cxcg2);
		analyse.add(rtjb2);
		analyse.add(tsqx2);
		analyse.add(dlfs2);
		analyse.add(xrzs2);
		analyse.add(hyfw2);
		analyse.add(tyjs2);
		
		koujueItem = new JMenu("常用口决");		
		swsjb2 = new JMenuItem("生旺死绝表");		
		jz602 = new JMenuItem("六十甲子纳音表");		
		nsqy2 = new JMenuItem("年上起月法");
		rsqs2 = new JMenuItem("日上起时法");
		yima2 = new JMenuItem("驿马");
		
		koujueItem.add(jz602);  //甲子纳音表
		koujueItem.add(swsjb2);	//生旺死绝表		
		koujueItem.add(nsqy2);
		koujueItem.add(rsqs2);		
		koujueItem.add(yima2);
		addSeparator();
		
		add(analyse);		
		add(koujueItem);
		addSeparator();
		
		add(tool2 = new JMenuItem(Liuyao.TOOL?"隐藏工具栏":"显示工具栏 "));
		add(input2 = new JMenuItem(Liuyao.INPUT?"隐藏高级面板":"显示高级面板 "));
		add(preference = new JMenuItem("启动设置"));
		addSeparator();
		
		add(cutItem = new JMenuItem("剪切 "));
		add(copyItem = new JMenuItem("复制"));
		add(pasteItem = new JMenuItem("粘贴"));
		add(deleteItem = new JMenuItem("删除"));
		add(selectAllItem = new JMenuItem("全选"));
		add(printItem = new JMenuItem("打印   (R)"));

		tool2.addActionListener(this);
		input2.addActionListener(this);
		preference.addActionListener(this);
		
		cutItem.addActionListener(this);
		copyItem.addActionListener(this);
		pasteItem.addActionListener(this);
		deleteItem.addActionListener(this);
		selectAllItem.addActionListener(this);
		printItem.addActionListener(this);
		
		swsjb2.addActionListener(this);
		nsqy2.addActionListener(this);
		rsqs2.addActionListener(this);
		yima2.addActionListener(this);
		jz602.addActionListener(this);
		
		zsmy2.addActionListener(this);
		jqmy2.addActionListener(this);
		swbd2.addActionListener(this);
		lahy2.addActionListener(this);
		qxks2.addActionListener(this);
		gzjy2.addActionListener(this);
		jyqc2.addActionListener(this);
		cxcg2.addActionListener(this);
		rtjb2.addActionListener(this);
		tsqx2.addActionListener(this);
		dlfs2.addActionListener(this);
		xrzs2.addActionListener(this);
		hyfw2.addActionListener(this);
		tyjs2.addActionListener(this);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger() && e.getSource() instanceof JTextPane) { // e.isPopupTrigger()鼠标右键
			JTextPane textfield = (JTextPane) e.getSource();
			if (Boolean.TRUE.equals(textfield.getClientProperty("DisablePopupMenu"))) {
				return;
			}
			textfield.requestFocusInWindow();
			this.show(textfield, e.getX(), e.getY()); // 获得组件及位置
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void show(Component invoker, int x, int y) {
		JTextComponent tc = (JTextComponent) invoker;
		String sel = tc.getSelectedText();

		boolean selected = sel != null && !sel.equals("");
		boolean enableAndEditable = tc.isEnabled() && tc.isEditable();
		// 禁用与启用
		cutItem.setEnabled(selected && enableAndEditable);
		copyItem.setEnabled(selected && tc.isEnabled());
		deleteItem.setEnabled(selected && enableAndEditable);
		pasteItem.setEnabled(enableAndEditable);
		selectAllItem.setEnabled(tc.isEnabled());

		super.show(invoker, x, y);
	}

	public void actionPerformed(ActionEvent e){
		JTextComponent tc = (JTextComponent) getInvoker();
		String sel = tc.getSelectedText();
		try{
			if (e.getSource() == cutItem) {
				tc.cut();
			} else if (e.getSource() == copyItem) {
				tc.copy();
			} else if (e.getSource() == pasteItem) {
				tc.paste();
			} else if (e.getSource() == selectAllItem) {
				tc.selectAll();
			} else if (e.getSource() == deleteItem) {
				Document doc = tc.getDocument();
				int start = tc.getSelectionStart();
				int end = tc.getSelectionEnd();
	
				try {
					Position p0 = doc.createPosition(start);
					Position p1 = doc.createPosition(end);
	
					if ((p0 != null) && (p1 != null) && (p0.getOffset() != p1.getOffset())) {
						doc.remove(p0.getOffset(), p1.getOffset() - p0.getOffset());
					}
				} catch (BadLocationException be) {
				}
			}	else if (e.getSource() == printItem) {
				try {
					tc.print(null);
				} catch (Exception e1) {
					Messages.info("该功能目前不支持！");
					return;
				}
			} else if (e.getSource() == tool2)	tool();
			else if (e.getSource() == input2)	input();
			else if (e.getSource() == preference)	preference();
			
			else if (e.getSource() == swsjb2)	swsjb();
			else if(e.getSource() == jz602) jz60();
			else if(e.getSource() == nsqy2)	nsqy();
			else if(e.getSource() == rsqs2)	rsqs();
			else if(e.getSource() == yima2)	yima();
			
			else {
				if(e.getSource() == zsmy2) zsmy();
				else if(e.getSource() == jqmy2) jqmy();
				else if(e.getSource() == swbd2) swbd();
				else if(e.getSource() == lahy2) lahy();
				else if(e.getSource() == qxks2) qxks();
				else if(e.getSource() == gzjy2) gzjy();
				else if(e.getSource() == jyqc2) jyqc();
				else if(e.getSource() == cxcg2) cxcg();
				else if(e.getSource() == rtjb2) rtjb();
				else if(e.getSource() == tsqx2) tsqx();
				else if(e.getSource() == dlfs2) dlfs();
				else if(e.getSource() == xrzs2) xrzs();
				else if(e.getSource() == hyfw2) hyfw();
				else if(e.getSource() == tyjs2) tyjs();
			}
		}
		catch(Exception ee) {
			ee.printStackTrace();
			Messages.error(ee.getMessage());
			return;
		}
	}
	
	//
	private void swsjb() throws Exception{
		pop.setAll("十天干生旺死绝表",WIDTH1-150,HEIGHT1-100);
		MyTextPane text = pop.getTextpane();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);		
		PublicFoumulas.printShiTianganSWSJB(pw);	  
	  text.roll20();
	  pop.setVisible(true);
	}
	
	//年上起月法
	private void nsqy() throws Exception {
		pop.setAll("年上起月法",360,200);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		pw.setDocument(doc);
		doc.remove(0, doc.getLength());
		PublicFoumulas.printNsqyf(pw);
		
		pop.setVisible(true);
	}
	//十干克应
	private void sgky() throws Exception{
		pop.setAll("十干克应表",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printSgkyb(pw);
		text.roll20();
		pop.setVisible(true);
	}

	private void jz60() throws Exception{
		pop.setAll("六十甲子纳音表",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printNayin(pw);
		text.roll20();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		pop.setVisible(true);
	}

	//日上起时法
	private void rsqs() throws Exception{
		pop.setAll("日上起时法",300,200);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printRsqsf(pw);
	  pop.setVisible(true);
	}
	//驿马
	private void yima() throws Exception{
		pop.setAll("驿马",200,250);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
		PublicFoumulas.printYima(pw);
		pop.setVisible(true);
	}
	
	public static final int WIDTH1 = 550;
	public static final int HEIGHT1 = 550;
	//终生命运
	private void zsmy() throws BadLocationException {
		pop.setAll("终生命运",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 终生命运\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.ZSMY);
			if(rs==null) return;
			pw.sblue(rs);

		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//近段运气
	private void jqmy() throws BadLocationException {
		pop.setAll("近段运气",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();				
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);		
			pw.bred("                 近段运气\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.JDYQ);
			if(rs==null) return;
			pw.sblue(rs);
	
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//失物被盗
	private void swbd() throws BadLocationException {
		pop.setAll("失物被盗",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 失物被盗\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.SWBD);
			if(rs==null) return;
			pw.sblue(rs);

		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//恋爱婚姻
	private void lahy() throws BadLocationException {	
		pop.setAll("恋爱婚姻",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);
		
			pw.bred("                 恋爱婚姻\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.LAHY);
			if(rs==null) return;
			pw.sblue(rs);
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//求学考试
	private void qxks() throws BadLocationException {
		pop.setAll("求学考试",500,570);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 求学考试\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.QXKS);
			if(rs==null) return;
			pw.sblue(rs);

		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//工作就业
	private void gzjy() throws BadLocationException {
		pop.setAll("工作就业",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 工作就业\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.GZJY);
			if(rs==null) return;
			pw.sblue(rs);
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//经营求财
	private void jyqc() throws BadLocationException {
		pop.setAll("经营求财",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 经营求财\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.JYQC);
			if(rs==null) return;
			pw.sblue(rs);
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//出行出国
	private void cxcg() throws BadLocationException {
		pop.setAll("出行出国",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 出行出国\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.CXCG);
			if(rs==null) return;
			pw.sblue(rs);
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//人体疾病
	private void rtjb() throws BadLocationException {
		pop.setAll("人体疾病",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();		
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 人体疾病\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.RTJB);
			if(rs==null) return;
			pw.sblue(rs);
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//天时气象
	private void tsqx() throws BadLocationException {
		pop.setAll("天时气象",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 天时气象\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.TSQX);
			if(rs==null) return;
			pw.sblue(rs);
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//地理风水
	private void dlfs()  throws BadLocationException {
		pop.setAll("地理风水",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 地理风水\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.DLFS);
			if(rs==null) return;
			pw.sblue(rs);			
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//行人走失
	private void xrzs()  throws BadLocationException {
		pop.setAll("行人走失",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 行人走失\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.XRZS);
			if(rs==null) return;
			pw.sblue(rs);			
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//怀孕分娩
	private void hyfw() throws BadLocationException  {
		pop.setAll("怀孕分娩",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 怀孕分娩\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.HYFW);
			if(rs==null) return;
			pw.sblue(rs);			
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	//体育竞赛
	private void tyjs() throws BadLocationException {
		pop.setAll("体育竞赛",WIDTH1,HEIGHT1);
		MyTextPane text = pop.getTextpane();
		Document doc = text.getDocument();
		doc.remove(0, doc.getLength());
		pw.setDocument(doc);

			pw.bred("                 体育竞赛\r\n");
			String rs = frame.getInputpanel().runRule(Liuyao.TYJS);
			if(rs==null) return;
			pw.sblue(rs);			
		
		text.roll20();  //滚到第一行
		pop.setVisible(true);
	}
	
	//显示、隐藏工具栏
	private void tool() {
		if(Liuyao.TOOL = !Liuyao.TOOL) {
			frame.addTool(frame.getToolbar());
			tool2.setText("隐藏工具栏");
		}else{
			frame.delTool(frame.getToolbar());
			tool2.setText("显示工具栏");
		}
	}
	//显示界面的选项设置
	LiurenPreference pref;
	private void preference() {
		if(pref==null) pref = new LiurenPreference();
		pref.setVisible(true);
	}
	
	//显示、隐藏高级面板
	private void input() {
		if(Liuyao.INPUT = !Liuyao.INPUT) {
			frame.addInput(frame.getInputToolbar());
			input2.setText("隐藏高级面板");
		}else{
			frame.delInput(frame.getInputToolbar());
			input2.setText("显示高级面板");
		}
	}
}
