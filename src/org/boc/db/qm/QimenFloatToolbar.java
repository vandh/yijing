package org.boc.db.qm;

import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.boc.event.qm.FloatMouseListener;
import org.boc.ui.UIPublic;

public class QimenFloatToolbar {
	private FloatMouseListener flistener;
	
	public QimenFloatToolbar(JComponent frame) {
		flistener = new FloatMouseListener(frame);
	}
	public JToolBar getFloatToolbar() {
    JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    toolBar.add(getImageButton("go", "go","阳盘/阴盘",flistener));
    toolBar.add(getImageButton("save", "save1","保存当前内容",flistener));
    toolBar.add(getImageButton("zf", "zf","转盘/飞盘",flistener));
    toolBar.add(getImageButton("rb", "rb","置闰/拆补",flistener));
    toolBar.add(getImageButton("td", "td","小值符随天盘/地盘",flistener));
    toolBar.add(getImageButton("kg", "kg","中宫永寄坤宫/阴坤阳艮",flistener));
    //toolBar.add(new JLabel(" "));    
    toolBar.add(getImageButton("head","m1","隐藏/显示:起局头部",flistener));
    toolBar.add(getImageButton("ma","m2","隐藏/显示:驿马空亡宫旺衰",flistener));    
    toolBar.add(getImageButton("jxge","m3","隐藏/显示：吉格凶格",flistener));
    toolBar.add(getImageButton("sgky", "m4","隐藏/显示：十干克应",flistener));
    toolBar.add(getImageButton("wsxq","m5","隐藏/显示：旺衰休囚",flistener)); 
    toolBar.add(getImageButton("xchm","m6","隐藏/显示：刑冲合墓桃",flistener)); 
    toolBar.add(getImageButton("jtxms","m7","隐藏/显示：退星隐门地盘神",flistener)); 
    toolBar.add(getImageButton("huo","m8","隐藏/显示：暗干活干纳音",flistener)); 
    toolBar.add(getImageButton("sj", "m9","隐藏/显示：十二神将",flistener));    
    toolBar.add(getImageButton("all", "m0","隐藏/显示：所有",flistener));
    //toolBar.add(new JLabel(" "));
    toolBar.add(getImageButton("reset","o0","各就各位",flistener));
    toolBar.add(getImageButton("xmhw","o1","星门神跳来跳去",flistener));
    toolBar.add(getImageButton("bian","o2","孙悟空72变",flistener));
    //toolBar.add(new JLabel(" "));
    toolBar.add(getImageButton("tip","tip","关闭/显示提示信息",flistener));
    toolBar.add(getImageButton("io","io","全内置/全外置",flistener));
    toolBar.add(getImageButton("flush","flush","重新载入规则、格局、提示等定制文件",flistener));
    
    toolBar.setFloatable(true);
    toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    return toolBar;
  }
	/**
	 * 工具栏的按钮
	 */
	public JButton getImageButton(String cmdname, String imganme, 
			String tip,ActionListener listener) {
		return UIPublic.getCustomImageButton(cmdname, imganme, tip, listener, 18, 14);
	}
}
