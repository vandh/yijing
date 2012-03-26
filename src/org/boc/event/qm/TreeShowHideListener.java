package org.boc.event.qm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSplitPane;

import org.boc.calendar.ui.CalendarForm;
import org.boc.db.qm.QiMen2;
import org.boc.ui.PopPanel;

public class TreeShowHideListener implements PropertyChangeListener {
//	private JSplitPane split;
	
	public void propertyChange(PropertyChangeEvent evt) {
		//不管pop有没有显示，也需要调整位置，否则就不准确了
		PopPanel pop = QmClickListener.getPop();		
		PopPanel pop2 = QmClickListener.getPop2();
		if(pop2!=null) pop2.setVisible(false);
		
		CalendarForm cal = QmClickListener.getCalendarForm();		
		int leftW = ((JSplitPane)evt.getSource()).getDividerLocation();
		if(pop!=null) {
			pop.setSize(pop.getWidth()-leftW + QiMen2.LEFT, pop.getHeight());
			pop.setLocation(pop.getX()+leftW - QiMen2.LEFT, pop.getY());
			pop2.setLocation(pop2.getX()+leftW - QiMen2.LEFT, pop2.getY());
		}
		if(cal!=null) {
			cal.setLocation(cal.getX()+leftW - QiMen2.LEFT, cal.getY());
		}
		QiMen2.LEFT = leftW; //判断左边是否显示还是隐藏了	
		//pop.repaint();
	}
}
