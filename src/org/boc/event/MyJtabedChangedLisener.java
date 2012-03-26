package org.boc.event;

import javax.swing.event.ChangeListener;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import org.boc.util.Public;
import org.boc.ui.*;

public class MyJtabedChangedLisener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {    	
      JTabbedPane pane = (JTabbedPane) e.getSource();
      if (pane.getSelectedComponent() != null) {
        int index = pane.getSelectedIndex();
        
        //如果是树的双击事件引志的所有的的tab更新，则第3、4、5...就不用更新了
//        if(BasicJTabbedPane.isTreeDbclick && index>2) {
//        	BasicJTabbedPane.isTreeDbclick = false;
//        	return ; 
//        }
        //System.out.println(pane.getTitleAt(idx));
        //第三个tab页的名字
        String fileId = pane.getName();
        if(Public.getValueIndex(fileId)<0)
          return;
        //面板从0开始，基本是第三块及以后的面板适用此方法
        String title1 = Public.tabTitle[Public.getValueIndex(fileId)][index+1];
        String tag = pane.getTitleAt(index);
        if(tag.equals(title1)) {
          BasicJTabbedPane panel = (BasicJTabbedPane)TreePanel.mapBaseTabPane.get(fileId);
          switch(index+1) {
            case 3:  //得到第三块面板，其后面的面板均调用第二块面板的do方法，因QiMenFrame是第二块面板，第345等不需要单独的面板了，就是一个JPanel
            	//panel.getXxxxPanel().setResultPane(panel.getResultPanel(index-1));
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do1()); //             
              //getResultPanel(index).updResult(str);
              //panel.getXxxxPanel().do0(panel.getResultPanel(index-1));       
              break;
            case 4:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do2());
              break;
            case 5:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do3());
              break;
            case 6:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do4());
              break;
            case 7:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do5());
              break;
            case 8:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do6());
              break;
            case 9:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do7());
              break;
            case 10:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do8());
              break;
            case 11:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do9());
              break;
            case 12:
              panel.updResultPanel(index - 1, panel.getXxxxPanel().do10());
              break;
            default:
              break;
          }
        }
      }
    }
  }

