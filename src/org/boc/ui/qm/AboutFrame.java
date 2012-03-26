package org.boc.ui.qm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Document;

import org.boc.ui.MyTextPane;
import org.boc.ui.PopPanel;
import org.boc.util.PrintWriter;
import org.boc.util.Public;

public class AboutFrame extends JDialog{
	private final int w = 460, h = 290;
	private static AboutFrame about;
	private PopPanel panel;
	private int code = 0;  //0为退出，其它为隐藏
	PrintWriter pw = new PrintWriter();
	
	public static void show(int code) {
		if(about==null){
			about = new AboutFrame();
		}

		about.code = code;				
		about.setAlwaysOnTop(true);
		about.setVisible(true); 
	}

	/**
	 * 始皇预测专业版
	 *   弘扬易学文化，促进世界和谐！
	 * 技术支持：
	 *   博客=http://blog.sina.com.cn/u/2479027277
	 *   QQ=52288572; EMAIL=vandh@163.com; Tel=18975642299
	 * 资金捐助：
	 *   北京招商银行；帐户：6225880118535856；姓名：王先红;
	 */
	private AboutFrame() {				
		JPanel pane = new JPanel();
		JLabel f2;
		Box box = new Box(BoxLayout.Y_AXIS);
		
		JLabel newline = new JLabel("   ");
		newline.setFont(new Font("宋体",Font.PLAIN,6));
		
		JLabel t1 = new JLabel("  始皇预测专业版:");
		t1.setForeground(Color.BLUE);
		t1.setFont(new Font("宋体",Font.BOLD,18)); //设置字体
		box.add(new JLabel("   "));
		box.add(t1);      
    JLabel t2 = new JLabel("        弘扬易学文化，促进世界和谐！");
    t2.setForeground(Color.BLACK);
    t2.setFont(new Font("宋体",Font.BOLD,14)); //设置字体
    box.add(newline);
    box.add(t2); 
    
    t2 = new JLabel("        永久免费，严禁用于商业用途！");
    t2.setForeground(Color.BLACK);
    t2.setFont(new Font("宋体",Font.BOLD,14)); //设置字体
    box.add(newline);
    box.add(t2);
    
    t1 = new JLabel("  技术支持：");
		t1.setForeground(Color.BLUE);
		t1.setFont(new Font("宋体",Font.BOLD,18)); //设置字体
		box.add(new JLabel("   "));
		box.add(t1);      
    f2 = new JLabel("        博客=http://blog.sina.com.cn/u/2479027277");
    f2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    //f2.addMouseListener(copy);
    f2.setForeground(Color.BLACK);
    f2.setFont(new Font("宋体",Font.BOLD,14)); //设置字体
    box.add(newline);
    box.add(f2); 
    f2 = new JLabel("        QQ群:218536197 mail:vandh@163.com");
    f2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    //f2.addMouseListener(copy);
    f2.setForeground(Color.BLACK);
    f2.setFont(new Font("宋体",Font.BOLD,14)); //设置字体
    box.add(newline);
    box.add(f2); 
    
    t1 = new JLabel("  资金捐助：");
		t1.setForeground(Color.BLUE);
		t1.setFont(new Font("宋体",Font.BOLD,18)); //设置字体
		box.add(new JLabel("   "));
		box.add(t1);      
		f2 = new JLabel("        北京招商银行  帐户：6225880118535856 姓名：王先红 ");
		f2.setBorder(BorderFactory.createEmptyBorder());
		f2.setBackground(Color.CYAN);
		//f2.addMouseListener(copy);
		f2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		f2.setForeground(Color.BLACK);
		f2.setFont(new Font("宋体",Font.BOLD,14)); //设置字体
    box.add(newline);
    box.add(f2); 
    
    JButton b2 = new JButton("捐助名单");
    b2.setForeground(Color.RED);
    b2.setFont(new Font("宋体",Font.BOLD,20)); //设置字体
    b2.setAlignmentX(CENTER_ALIGNMENT);
    b2.addMouseListener(new MyMouseAdapter());
    box.add(new JLabel("   "));
    
    pane.setEnabled(true);
    pane.add(box,BorderLayout.NORTH);
    pane.add(b2,BorderLayout.CENTER);
    this.setContentPane(pane);    
    this.setTitle(Public.title); 
    this.setSize(w,h);      
    //this.setModal(false);
    this.setLocationRelativeTo(null);  
    this.setResizable(false);        
    //this.setUndecorated(true);
    //this.setDefaultLookAndFeelDecorated(true);
	}
	
	class MyMouseAdapter extends MouseAdapter {
  	public void mouseClicked(MouseEvent e) {
  		if(panel==null) 
  			newList();
  		about.setVisible(false);  	
  		panel.setVisible(true);
  	}
	}
	
	private void newList() {
		panel = new PopPanel(QimenPopupMenu.WIDTH1,QimenPopupMenu.HEIGHT1, about);
		panel.setTitle("捐助列表");
		MyTextPane text = panel.getTextpane();		
		Document doc = text.getDocument();
		pw.setDocument(doc);
		try{
  		pw.bred("                 捐助列表");
  		pw.newLine();
  		pw.newLine();
  		pw.print("  开户行： 北京招商银行",PrintWriter.SBLUE,false);
  		pw.print("         帐户：6225880118535856",PrintWriter.SBLUE,false);
  		pw.println("          姓名：王先红",PrintWriter.SBLUE,false);
  		pw.println("  博客地址： http://blog.sina.com.cn/u/2479027277",PrintWriter.SBLUE,false);
  		pw.print("  QQ： 52288572",PrintWriter.SBLUE,false);
  		pw.print("           mail=vandh@163.com",PrintWriter.SBLUE,false);
  		pw.print("           Tel=18975642299",PrintWriter.SBLUE,false);
  		pw.newLine();
  		pw.newLine();
  		pw.sred("   ------------------------------------------------------------------------------------");
  		pw.newLine();
  		pw.mblue("  捐助单位或个人  捐助时间                 捐助描述         ");
  		pw.newLine();
  		pw.sred("   ------------------------------------------------------------------------------------");
  		pw.newLine();
  		pw.sred("    Phoebe        2011             布局格式、各项设置等诸多建议，资料提供，软件测试等；");
  		pw.newLine();
  		pw.sred("    目击者        2012             布局格式、各项设置等诸多建议，资料录入及提供，软件测试等；");
  		pw.newLine();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
		text.roll20();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); 
		//panel.setUndecorated(true);
	}
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			if(about.code==0) System.exit(0);			
			else {
				JDialog d = (JDialog)e.getComponent();
				d.setVisible(false);
			}
		}
	}
}
