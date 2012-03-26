package org.boc.calendar.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;

import org.boc.util.Public;

/**
 * 显示当前时间
 */
public class TimesLabel extends JLabel implements Runnable {
	private static final long serialVersionUID = -4029471797281693798L;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Thread t;
	private boolean run_continue = false;
	
	public void addNotify() {
		super.addNotify();
		this.run_continue = true;
		if (this.t == null) {
			this.t = new Thread(this);
			this.t.start();
		}
	}
	public void removeNotify() {
		this.run_continue = false;
		if (this.t != null)
			this.t = null;
		super.removeNotify();
	}
	
	public void run() {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Calendar cls = Public.getCalendarForBeijing();
		while (this.run_continue) {						
			this.setText(sdf.format(cls.getTime()));			
			try { Thread.sleep(1000); }
			catch (InterruptedException e) { }
		}
	}

}
