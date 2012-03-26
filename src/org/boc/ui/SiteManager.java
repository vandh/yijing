package org.boc.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class SiteManager
    extends JFrame {

  JLayeredPane desktop;
  Vector popups = new Vector();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();

  public SiteManager() {
    super("Web Site Manager");
    setSize(450, 250);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Container contentPane = getContentPane();

    JToolBar jtb = new JToolBar();
    jtb.add(new CopyAction(this));
    jtb.add(new CopyAction(this));
    jtb.add(new CopyAction(this));
    contentPane.add(jtb, BorderLayout.NORTH);

    // Add our LayeredPane object for the internal frames.
    desktop = new JDesktopPane();
    contentPane.add(desktop, BorderLayout.CENTER);
    addSiteFrame("Sample");
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    SiteManager mgr = new SiteManager();
    mgr.setVisible(true);
  }

  public void addSiteFrame(String name) {
    SiteFrame sf = new SiteFrame(name, this);
    popups.addElement(sf);
    desktop.add(sf, new Integer(2)); // Keep sites on top for now.
    sf.setVisible(true);
  }

  public void addPageFrame(String name) {
    PageFrame pf = new PageFrame(name, this);
    desktop.add(pf, new Integer(1));
    pf.setVisible(true);
    pf.setIconifiable(true);
    popups.addElement(pf);
  }

  public JInternalFrame getCurrentFrame() {
    for (int i = 0; i < popups.size(); i++) {
      JInternalFrame currentFrame = (JInternalFrame) popups.elementAt(i);
      if (currentFrame.isSelected()) {
        return currentFrame;
      }
    }
    return null;
  }

  class CopyAction
      extends AbstractAction {
    SiteManager manager;

    public CopyAction(SiteManager sm) {
      super("", new ImageIcon("copy.gif"));
      manager = sm;
    }

    public void actionPerformed(ActionEvent ae) {
      JInternalFrame currentFrame = manager.getCurrentFrame();
      if (currentFrame == null) {
        return;
      }
      // Can't cut or paste sites
      if (currentFrame instanceof SiteFrame) {
        return;
      }
      ( (PageFrame) currentFrame).copyText();
    }
  }

  class SiteFrame
      extends JInternalFrame {

    JList nameList;
    SiteManager parent;
    // Hardcode the pages of our "site" to keep things simple.
    String[] pages = {
        "index.html", "page1.html", "page2.html"};

    public SiteFrame(String name, SiteManager sm) {
      super("Site: " + name, true, true, true);
      parent = sm;
      setBounds(50, 50, 250, 100);

      nameList = new JList(pages);
      nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      nameList.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent lse) {
          // We know this is the list, so pop up the page.
          if (!lse.getValueIsAdjusting()) {
            parent.addPageFrame( (String) nameList.getSelectedValue());
          }
        }
      });
      Container contentPane = getContentPane();
      contentPane.add(nameList, BorderLayout.CENTER);
    }
  }

  class PageFrame
      extends JInternalFrame {

    SiteManager parent;
    String filename;
    JTextArea ta;

    public PageFrame(String name, SiteManager sm) {
      super("Page: " + name, true, true, true, true);
      parent = sm;
      setBounds(50, 50, 300, 150);

      // Use the JFrame's content pane to store our desktop.
      Container contentPane = getContentPane();

      // Create a text area to display the contents of our file and put it in a
      // scrollable pane so we can get to all of it.
      ta = new JTextArea();
      JScrollPane jsp = new JScrollPane(ta);
      contentPane.add(jsp, BorderLayout.CENTER);

      // Add a "File->Save" option to the menu bar for this frame.
      JMenuBar jmb = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      JMenuItem saveItem = new JMenuItem("Save");
      saveItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          saveContent();
        }
      });
      fileMenu.add(saveItem);
      jmb.add(fileMenu);
      setJMenuBar(jmb);

      // Now get the content, based on the filename that was passed in.
      filename = name;
      loadContent();
    }

    public void loadContent() {
      try {
        FileReader fr = new FileReader(filename);
        ta.read(fr, null);
        fr.close();
      }
      catch (Exception e) {
        System.err.println("Could not load page: " + filename);
      }
    }

    public void saveContent() {
      try {
        FileWriter fw = new FileWriter(filename);
        ta.write(fw);
        fw.close();
      }
      catch (Exception e) {
        System.err.println("Could not save page: " + filename);
      }
    }

    public void cutText() {
      ta.cut();
    }

    public void copyText() {
      ta.copy();
    }

    public void pasteText() {
      ta.paste();
    }

  }
  private void jbInit() throws Exception {
    jMenu1.setText("q");
    jMenuItem1.setText("x");
    jMenuItem2.setText("a");
    jMenuItem3.setText("b");
    jMenuBar1.add(jMenu1);
    jMenu1.add(jMenuItem1);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem2);
    jMenu1.add(jMenuItem3);
  }

}