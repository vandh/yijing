package org.boc.ui.ly;

import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.boc.event.ly.LyFloatMouseListener;
import org.boc.ui.UIPublic;

public class LiuyaoFloatToolbar {
	private LyFloatMouseListener flistener;
	
	public LiuyaoFloatToolbar(JComponent frame) {
		flistener = new LyFloatMouseListener(frame);
	}
	public JToolBar getFloatToolbar() {
    JToolBar toolBar = new JToolBar(SwingConstants.VERTICAL);
    toolBar.add(getImageButton("go", "go","六爻/梅花",flistener));
    toolBar.add(getImageButton("save", "save1","保存当前内容",flistener));
    toolBar.add(getImageButton("now", "td","传经/现代",flistener));
    toolBar.add(new JLabel(" "));    
    toolBar.add(getImageButton("head","m1","隐藏/显示:起局头部",flistener));
    toolBar.add(getImageButton("tg","m2","隐藏/显示:天干",flistener));    
    toolBar.add(getImageButton("tg","m3","隐藏/显示:地支",flistener));
    toolBar.add(getImageButton("wh","m4","隐藏/显示：五行",flistener));
    toolBar.add(getImageButton("wh","m5","隐藏/显示：六亲",flistener));
    toolBar.add(getImageButton("wh","m6","隐藏/显示：六神",flistener));
    toolBar.add(getImageButton("ma","m7","隐藏/显示:神煞空亡",flistener));    
    toolBar.add(getImageButton("jxge","m8","隐藏/显示：旺衰休囚",flistener));
    toolBar.add(getImageButton("wsxq","m9","隐藏/显示：卦名",flistener));    
    toolBar.add(getImageButton("all", "m0","隐藏/显示：所有",flistener));
    toolBar.add(new JLabel(" "));
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
