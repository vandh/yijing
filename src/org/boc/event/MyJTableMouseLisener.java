package org.boc.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTabbedPane;

import org.boc.ui.*;
import org.boc.model.DataTableModel;
import org.boc.util.Public;

public class MyJTableMouseLisener
    implements MouseListener {
  public void mouseClicked(MouseEvent me) {
    if (me.getClickCount() == 2) {
      JyjJTable tabble = (JyjJTable) me.getSource();
      DataTableModel model = (DataTableModel) tabble.getModel();
      int h = tabble.getRowHeight();
      int y = me.getY();
      int row = y / h;
      String pk = (String) model.getValueAt(row, 0);
      //System.out.println("您双击了："+pk);

      //由pk值查文件？
      String fileId = model.getFileId();
      int index = Public.getValueIndex(fileId);
      String title = Public.tabTitle[index][2];
      JTabbedPane rightJTabbedPane = Main2.getRightTabbedPane();
      BasicJTabbedPane basePane = (BasicJTabbedPane) TreePanel.mapBaseTabPane.get(fileId);
      basePane.updPageInfo(fileId, pk, null);
      TreePanel.setCurView(rightJTabbedPane, title);
      TreePanel.mapBaseTabPane.put(fileId, basePane);

    }
  }

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {}

  public void mouseReleased(MouseEvent e) {}
}
