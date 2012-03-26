package org.boc.event;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 可以让指定的Component通过鼠标拖动来移动Window的便利类
 */
public class WindowDraggerUtil {
	private Component fWindow;  //父窗口，一般是JFrame
	private Component fComponent;  //要移动的对象窗口，一般是JWindow或JPanel
	private int dX;
	private int dY;

	/**
	 * 让指定的Component通过鼠标拖动在Window中移动
	 */
	public WindowDraggerUtil(Component window, Component component) {
		fWindow = window;
		fComponent = component;
		fComponent.addMouseListener(createMouseListener());
		fComponent.addMouseMotionListener(createMouseMotionListener());
	}

	private MouseListener createMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point clickPoint = new Point(e.getPoint());
				SwingUtilities.convertPointToScreen(clickPoint, fComponent);
				dX = clickPoint.x - fWindow.getX();
				dY = clickPoint.y - fWindow.getY();
			}
		};
	}

	private MouseMotionAdapter createMouseMotionListener() {
		return new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point dragPoint = new Point(e.getPoint());
				SwingUtilities.convertPointToScreen(dragPoint, fComponent);
				fWindow.setLocation(dragPoint.x - dX, dragPoint.y - dY);
			}
		};
	}
}
