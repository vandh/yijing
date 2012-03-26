package org.boc.calendar.ui;

import java.awt.Component;
import java.awt.Dimension;

/**
 * 提供一些 UI 相关方法
 */
public class WindowView {
	/**
	 * 将指定窗体移动到屏幕中央
	 * @param win 指定窗体
	 */
	public static void moveToScreenCenter(Component win) {
		Dimension de = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		win.setLocation((de.width - win.getWidth()) / 2, (de.height
				- win.getHeight() - 40) / 2);
		
	}

}
