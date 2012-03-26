package org.boc.ui;

import java.awt.*;
import java.util.*;
import java.io.File;

import org.boc.event.EEL;

import javax.swing.*;

import org.boc.model.DataTableModel;
import org.boc.util.VO;
import org.boc.event.MyJTableMouseLisener;
import javax.swing.table.TableCellRenderer;

public class JyjJTable
    extends JTable {
  private MyJTableMouseLisener eel ;
  private String titles[] = new String[] {"姓名", "性别", "出生省市", "出生日期", "预测时间" };

  public JyjJTable(String fileId) {
    super();
    DataTableModel model = new DataTableModel(fileId);
    this.setModel(model);
    //this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //this.setColumnSelectionAllowed(true);
    eel = new MyJTableMouseLisener();
    this.addMouseListener(eel);
    this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    //this.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  public JyjJTable(DataTableModel model) {
    super(model);
    //this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //this.setColumnSelectionAllowed(true);
    eel = new MyJTableMouseLisener();
    this.addMouseListener(eel);
    this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    //this.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer());
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  /**
   * 系统早有此方法，可直接调用
   */
  //public DataTableModel getModel() {
  //  return this.model;
  //}

  public JyjJTable(Object[][] stats, String[] titles) {
    super(stats, titles);
    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    this.setColumnSelectionAllowed(true);
  }

  public void updateInfo(String fileId,Collection<VO> coll) {
    ((DataTableModel)(this.getModel())).setDatas(fileId,coll);
  }

  class ColorRenderer extends JLabel implements TableCellRenderer {
    private String value;
    public ColorRenderer() {
    }

    public Component getTableCellRendererComponent(JTable table, Object color,
        boolean isSelected, boolean hasFocus, int row, int column) {
      //String value = (String) table.getValueAt(row, column);
      //this.setIcon(new ImageIcon(getClass().getResource("/images/open1.gif")));
      //this.setHorizontalAlignment(JLabel.CENTER);
      return this;
    }
  }


  public static void main(String args[]) {

  }
}
