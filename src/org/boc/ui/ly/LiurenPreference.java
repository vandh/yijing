package org.boc.ui.ly;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.boc.db.ly.Liuyao;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.xml.XmlProc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * RB: 置闰还是拆补，默认是拆补法为false
 * XMHW: 0是神星门，1.神门星
 * 如果加了选项，还必须改改 XmlProc::loadFromOnFile()方法
 */
public class LiurenPreference extends JDialog {
	String[] sgroups = {"ma","jxge","wsxq", "tip", "tool", "input",
			"head", "io", "left","calendar", "now"};
	String[] groupName = {"日柱空亡","吉格凶格","旺衰休囚","提示信息",	"  工具栏","高级面板",
												"格局头部","内置定制","  目录树","现代传统"};
	String[][] radioName = new String[][]{
			{"显示","隐藏"},{"显示","隐藏"},{"显示","隐藏"},{"显示","隐藏"},
			{"显示","隐藏"},{"显示","隐藏"},{"显示","隐藏"},{"内置","定制"},
			{"显示","隐藏"},{"现代","传统"}};
	boolean[] bgroups = {Liuyao.MA,Liuyao.JXGE,Liuyao.WSXQ,Liuyao.TIP,
			Liuyao.TOOL,Liuyao.INPUT,Liuyao.HEAD,Liuyao.IO,Liuyao.LEFT>10,
			Liuyao.NOW};
	
	ButtonGroup[] groups = new ButtonGroup[sgroups.length];
	
	public LiurenPreference() {
		Box box1 = new Box(BoxLayout.Y_AXIS);
		Box box2 = new Box(BoxLayout.X_AXIS);
		for(int i=0; i<groups.length; i++) {
			groups[i] = new ButtonGroup();
			box2.add(create(groupName[i], radioName[i][0],radioName[i][1], groups[i], bgroups[i]));
			
			if(i%2==1) {
				box2.setAlignmentX(LEFT_ALIGNMENT);				
				box1.add(box2, BorderLayout.CENTER);
				box2 = new Box(BoxLayout.X_AXIS);
			}else{
				box2.add(new JLabel("      |      "));
				box2.setAlignmentX(LEFT_ALIGNMENT);		
				if(i==groups.length-1) box1.add(box2, BorderLayout.CENTER);
			}			
		}
		
		Box box4 = new Box(BoxLayout.X_AXIS);
		JButton save = new JButton("确定");
		save.addActionListener(new ActionListener() {				
			public void actionPerformed(ActionEvent e) {
				savePref();
			}
		});
		box4.add(save);
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box1.setAlignmentX(CENTER_ALIGNMENT);
		box.add(box1,BorderLayout.CENTER);
		box.add(box4,BorderLayout.CENTER);

    this.getContentPane().add(box,BorderLayout.CENTER);
    this.setUndecorated(true);  
    this.setSize(600, 400);  
    this.setLocationRelativeTo(null);  
    this.setAlwaysOnTop(true);
    //this.setVisible(true);
    this.pack();
	}
	
	private File getFile() {
  	return new File(Public.LYQDSZ);
  }
	
	private void savePref(){
		XmlProc xml = XmlProc.getInstance();
		try {
			Document doc = xml.createNewDocument();
			Element root = doc.createElement("root");
			for(int i=0; i<groups.length; i++) {
				String val = groups[i].getSelection().getActionCommand().equals("show")?"1":"0";
				Element e = xml.createElement(doc, root,sgroups[i]);
				e.setTextContent(val);
			}
			doc.appendChild(root);
			xml.save2File(doc, getFile());
		} catch (Exception e) {
			e.printStackTrace();
			this.setVisible(false);
			Messages.error(e.toString());			
			this.setVisible(true);
		}
		this.setVisible(false);
	}
	
	private Box create(String g1,String n1, String n2,  ButtonGroup group, boolean b) {
		String v1="show", v2="hide";
		return create(g1,n1,n2,v1,v2,group,b);
	}
	private Box create(String g1,  String n1, String n2, String v1, String v2, ButtonGroup group, boolean b) {
		Box box = new Box(BoxLayout.X_AXIS);
		JRadioButton r1 = new JRadioButton(n1, b);
		JRadioButton r2 = new JRadioButton(n2, !b); //男 ，女
		r1.setActionCommand(v1);
    r2.setActionCommand(v2);
    box.add(new JLabel(g1+"："));  //"性别："
    group.add(r1);
    group.add(r2);  
    box.add(r1);
    box.add(r2);     
    box.setAlignmentX(LEFT_ALIGNMENT);
    return box;
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			this.setVisible(false);
		}
	}
}
