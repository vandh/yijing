package org.boc.ui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.boc.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class FileManagerFrame extends JFrame{
  private JPanel jContentPane; //背景大面板

  public FileManagerFrame () {
    this.setTitle("文件管理");
    this.setFont(Public.getFont());
    this.setContentPane(getJContentPane());
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setSize(300, 150);
    jContentPane.add(new FilePanel(), BorderLayout.CENTER);

    CommandAction action = new CommandAction("退出", null, "", ' ', new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //System.exit(0);
        //jContentPane.setVisible(false);
        Main1.fManager.setVisible(false);
      }
    });
    JButton button = new JButton(action);
    jContentPane.add(button, BorderLayout.SOUTH);

    UIUtilities.center(this);
    //jContentPane.setVisible(true);
  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = (JPanel)this.getContentPane();
      jContentPane.setLayout(new BorderLayout());
    }
    return jContentPane;
  }


  public static void main(String[] args) {
    FileManagerFrame mainFrame = new FileManagerFrame( );
    //mainFrame.init();
  }

}
