package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import org.boc.util.Messages;

public class SplashWindow
    extends JWindow {
  private int duration = 1000;
  private javax.swing.JPanel jContentPane = null;
  public static void main(String[] args) {
    SplashWindow splash = new SplashWindow();
    //System.err.println(System.getProperty("user.dir"));
    splash.showSplash("images/splash.jpg");
    System.exit(0);
  }

  public SplashWindow(int duration) {
    super();
    this.duration = duration;
    initialize();
  }

  /**
   * This is the default constructor
   */
  public SplashWindow() {
    super();
    initialize();
  }

  /**
   * This method initializes this
   *
   * @return void
   */
  private void initialize() {
    this.setSize(100, 100);
    this.setContentPane(getJContentPane());
  }

  /**
   * This method initializes jContentPane
   *
   * @return javax.swing.JPanel
   */
  private javax.swing.JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new javax.swing.JPanel();
      jContentPane.setLayout(new java.awt.BorderLayout());
    }
    return jContentPane;
  }

  String splashFile = null;
  public void showSplash(String picfile) {
//    File f = new File(picfile);
//    if (!f.exists())
//      return;
    ImageIcon image;
    //try {
      //image = new ImageIcon(f.toURL());
    	image = new ImageIcon(getClass().getResource(picfile));
//    }
//    catch (MalformedURLException ex) {
//      Messages.error("SplashWindow("+ex+")");
//      return;
//    }
    JPanel content = (JPanel) getContentPane();
    content.setBackground(Color.GRAY);
    int width = image.getIconWidth();
    int height = image.getIconHeight();
    JLabel label = new JLabel(image);
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    this.setBounds(x, y, width + 10, height + 10);
    content.add(label, BorderLayout.CENTER);
    this.setVisible(true);
    try {
      Thread.sleep(this.duration);
    }
    catch (Exception e) {}
    this.setVisible(false);
  }

  public SplashWindow getThis() {
    return this;
  }
}
