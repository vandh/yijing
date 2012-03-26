package org.boc.ui;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.boc.db.qm.QiMen2;
import org.boc.event.qm.QmClickListener;
import org.boc.event.qm.TipMouseMotionAdaption;
import org.boc.ui.qm.QimenPopupMenu;
import org.boc.util.HtmlMultiLineControl;

/**
 * 第二块面板是QimenFrame，第三块以后的面板是一个JPanel，是在BasicJTabbedPane中循环new的，但会调用第二块面板的do1方法，
     * 面板3是直接更新ResultPane，而4、5、6等是返回值以后更新面板的，与这面板3不同
 */
public abstract class BasicJPanel extends JPanel{
	private BasicJTabbedPane father; //创建它的父面板,以便取到所有的第三块及以后的面板
  public JTextArea jTextArea;
  public JScrollPane jScrollPane;  
  protected HtmlMultiLineControl html = new HtmlMultiLineControl();
  public MouseListener clickListner;
  public MouseMotionListener mouseMotionListener;
  /**
   * 对象保存在文件时的所属的根节点，以防止与文件名不同时改代码
   * 目前与文件id一致
   */
  private String root;
  public String getRoot() {
    return root;
  }
  public void setRoot(String root) {
    this.root = root;
  }
  public void setFather(BasicJTabbedPane pane) {
  	this.father = pane;
  }
  public BasicJTabbedPane getFather() {
  	return this.father;
  }
  /**
   * 初始化方法，必须重载
   * @param fileId String  文件id
   * @param rowId String   该行标识，即树的节点名称
   * @param parentNode String  该节点的父节点
   */
  public abstract void init(String fileId, String rowId, String parentNode);

  /**
   * 各类输出按钮的方法，如果实现了必须重载
   * 有排盘、终身卦、婚姻，六亲、性格、病凶等
   * @return String
   */
  //public String do0(ResultPanel pane) {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do1() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do2() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do3() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do4() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do5() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do6() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do7() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do8() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do9() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do10() {return "\r\n    开发中......，请等待下一版本完善！";}

  /**
   * 得到人员的其它信息
   * 如附注，单位， 职务， 工作地址，居住地址
   * 邮政编码， 电话， 手机， email, QQ， MSN
   * @return
   */
  public Box getOtherInfoPane(JButton saveB, String memo) {
    Box box = new Box(BoxLayout.Y_AXIS);

    box.add(new JLabel("  "));
    box.add(new JLabel("  "));
    box.add(new JLabel("  "));

    Box p1 = new Box(BoxLayout.X_AXIS);
    p1.add(new JLabel("附注信息：                                           "));
    p1.add(saveB);
    box.add(p1);
    jTextArea = new JTextArea();
    jTextArea.setLineWrap(false);
    jTextArea.setText(memo);
  //以下分别是显示第一行，不用每次都滚动到最后一行显示
    jTextArea.setCaretPosition(0);
    jTextArea.setSelectionStart(0);
    jTextArea.setSelectionEnd(0);
    
    //jTextArea.setForeground(java.awt.Color.white);
    //jTextArea.setRows(20);
    //jTextArea.setColumns(100);
    jScrollPane = new JScrollPane(jTextArea);
    box.add(jScrollPane);

    return box;
  }

  public JTextArea getJTextArea() {
    return this.jTextArea;
  }

  //设置要更新的面板
  private ResultPanel resultPane ; 
  public void setResultPane(ResultPanel rp) {
  	this.resultPane = rp;
  }
  //得到面板时，顺便添加鼠标移动事件
  public ResultPanel getResultPane() {   
  	return resultPane;
  }
  
  public MouseMotionListener getMouseMotionListener() {
  	return mouseMotionListener;
  }
  
	//加入/删除工具栏
	public void addTool(JToolBar toolbar) {
		this.getResultPane().addFloatToolbar(toolbar);  //在结果面板西侧加入一个浮动工具栏
	}
	public void delTool(JToolBar toolbar) {
		this.getResultPane().delFloatToolbar(toolbar);  //在结果面板西侧删除一个浮动工具栏
	}
	//加入/删除高级面板
	public void addInput(JToolBar inputToolBar) {
		this.getResultPane().addInputPanel(inputToolBar);  //在结果面板西侧加入一个浮动工具栏
	}
	public void delInput(JToolBar inputToolBar) {
		this.getResultPane().delInputPanel(inputToolBar);  //在结果面板西侧删除一个浮动工具栏
	}
}
