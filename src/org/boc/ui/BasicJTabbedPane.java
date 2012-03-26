package org.boc.ui;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import org.boc.event.*;
import org.boc.util.*;
import org.boc.model.DataTableModel;

/**
 * 在树上每次双击，判断属于何种技术，如果没有会新增一个，有的会调用updPageInfo更新页面数据
 * 首先是Main2.jTabbedPane1，此中内嵌若干BasicJTabbedPane面板，一个面板一个大的Tab如奇门、六壬 1.
 * BasicJTabbedPane面板含一个tab容器jTabbedPane2
 * ，内置三个面板(信息列表/详细信息/排盘/...),即有三个小tab页,和其它循环输出的tab页 1.1 JPanel
 * jPanel2:信息列表pane，列表只new一个，有改变只更新表单的model； 1.2 BasicJPanel
 * bPane:详细页面改变时，动态的删除，再new一个如SiZhuFrame插入进来
 * 此面板保存时，调用重载的do1()等方法，取new时的fileId,rowId,parent。因为一面板一个此对象，故不需要工厂。
 * 此面板内容更新时，调用init()方法，如果parent为null，则取由文件id与rowid得到对象的parent。 1.3
 * ResultPanel:其它输出信息，多个则循环new ResultPanel，改变时只更新文本框
 */
public class BasicJTabbedPane extends JPanel {
	private BorderLayout borderLayout1;
	private BorderLayout borderLayout3;
	public static final String NUM4=".";

	private JTabbedPane jTabbedPane2; // 一个tab容器，包含信息列表jPanel2、详细信息bPane、排盘、财运等各个小pane
	private JPanel jPanel2; // 信息列表的面板
	private JyjJTable myTable; // jPanel2中的列表
	private BasicJPanel bPane; // 所有预测详细窗口的面板,SiZhuFrame、QiMenFrame都继承了它
	private Map<String,ResultPanel> mapRsPane; // 排盘及显示输出结果的面板

	private MyJtabedChangedLisener myTabedChange;
	private MyJtabedMouseLisener myTabedMouse;

	private String fileId;
	private String rowId;
	private String parentNode;

	public BasicJTabbedPane(String fileId, String rowId, String parentNode) {
		try {
			this.fileId = fileId;
			this.rowId = rowId;
			this.parentNode = parentNode;

			/**
			 * 点击时由此名字得到文件id
			 */
			jTabbedPane2 = new JTabbedPane();
			jTabbedPane2.setName(fileId);
			borderLayout1 = new BorderLayout();
			borderLayout3 = new BorderLayout();

			jPanel2 = new JPanel();
			myTable = new JyjJTable(fileId);
			// myTable = new QueryTable(null);
			myTabedChange = new MyJtabedChangedLisener();
			myTabedMouse = new MyJtabedMouseLisener();

			bPane = (BasicJPanel) Class.forName((String) Public.mapClass.get(fileId)).newInstance();
			bPane.init(fileId, rowId, parentNode);
			
			// 将后边的输出面板放在一个map中
			mapRsPane = new HashMap<String,ResultPanel>(); 
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
			Messages.error("BasicJTabbedPane(" + ex + ")");
		}
	}

	/**
	 * 初始化面板，是所有预测术的基本tab面板，内嵌n小tab页
	 */
	void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		jPanel2.setLayout(borderLayout3);
		JScrollPane jsp = new JScrollPane(myTable);

		jPanel2.add(jsp, BorderLayout.CENTER);

		this.add(jTabbedPane2, BorderLayout.CENTER);
		jTabbedPane2.addChangeListener(myTabedChange);
		jTabbedPane2.addMouseListener(myTabedMouse);
		jTabbedPane2.add(jPanel2, Public.tabTitle[Public.getValueIndex(fileId)][1]);
		jTabbedPane2.add(bPane, Public.tabTitle[Public.getValueIndex(fileId)][2]);
		// 输出面板循环，从第三个开始，按序数从1开始对应放入map中
		int valIdx = Public.getValueIndex(fileId);
		for (int i = 3; i < Public.tabTitle[valIdx].length; i++) {
			ResultPanel rsPane = new ResultPanel();
			mapRsPane.put(String.valueOf(i - 2), rsPane);
			jTabbedPane2
					.add(rsPane, Public.tabTitle[Public.getValueIndex(fileId)][i]);
		}
		jTabbedPane2.setSelectedIndex(1);
		bPane.setFather(this); // 设置当前面板为第二块面板的父面板
	}

	public String getFieId() {
		return fileId;
	}
	public String getRowId() {
		return rowId;
	}
	public String getParentNode() {
		return parentNode;
	}

	/**
	 * 得到第二块面板即详细信息
	 */
	public BasicJPanel getXxxxPanel() {
		return bPane;
	}

	/**
	 * 得到第index块输出面板即排盘/信息/婚姻等信息
	 */
	public ResultPanel getResultPanel(int index) {
		return (ResultPanel) mapRsPane.get(String.valueOf(index));
	}

	/**
	 * 单击第index块面板，更新输出信息
	 * 
	 * @param str
	 *          String
	 */
	public void updResultPanel(int index, String str) {
		getResultPanel(index).updResult(str);
	}

	/**
	 * 双击列表时，更新第二个页面的详细信息
	 */
	public void updTableInfo(Collection<VO> coll) {
		myTable.updateInfo(fileId, coll);
	}

	/**
	 * 双击树的子叶子事件时，更新第二个页面的详细信息
	 * 原方法去掉第二块面板再添加，现为直接更新第二块面板的内容
	 */
	public void updPageInfo(String fileId, String rowId, String parentNode) {
		this.fileId = fileId;
		this.rowId = rowId;
		this.parentNode = parentNode;
		jTabbedPane2.setSelectedIndex(1);
		BasicJPanel theframe = (BasicJPanel)(jTabbedPane2.getSelectedComponent());
		theframe.init(fileId, rowId, parentNode);
	}

}
