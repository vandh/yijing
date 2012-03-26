package org.boc.event;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

import java.util.Collection;

import org.boc.util.Public;
import org.boc.util.VO;
import org.boc.ui.*;

public class MyJtabedMouseLisener implements MouseListener{
      public void mouseClicked(MouseEvent e) {
        JTabbedPane pane = (JTabbedPane) e.getSource();
        if (e.getClickCount() == 1) {
          int index2 = pane.getSelectedIndex();
          //名字即文件id名
          String fileId = pane.getName();
          if(Public.getValueIndex(fileId)<0)
            return;
          //第一个tab页的名字
          String title1 = Public.tabTitle[Public.getValueIndex(fileId)][1];
          String tag = pane.getTitleAt(index2);
          /**
           * 如果是列表页面
           * 由fileId亦可得到此对象BasicJTabbedPane
           * 由对象得到rowId
           * 由rowId得到其父目录
           * 从文件fileId中得到所有父目录下的值
           */
          if(tag.equals(title1)) {
            BasicJTabbedPane panel = (BasicJTabbedPane)TreePanel.mapBaseTabPane.get(fileId);
            String rowId = panel.getRowId();
            //System.out.println(fileId+":"+rowId);
            String parent = TreePanel.getParent(rowId);
            if(parent==null)
              parent = Public.getRootKey(fileId);
            Collection<VO> coll = Public.getObjectFromFile(fileId);
            panel.updTableInfo(coll);
            return;
          }
          return;
        }
//        	else if (e.getClickCount() == 2) { 	//双击不需要关闭
//          int index2 = pane.getSelectedIndex(); //得到当前标签页的下标
//          String tag = pane.getTitleAt(index2);
//          pane.removeTabAt(index2);
//          //将变量置回来
//          Public.setKeyValue(Public.mapKeyIsOpen, Public.getRootValue(tag), false);
//          return;
//        }
      }

    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
  }
