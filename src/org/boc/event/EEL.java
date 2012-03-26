package org.boc.event;

import java.io.*;
import java.util.*;
import java.beans.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import org.boc.model.DataTableModel;
import org.boc.ui.JyjJTable;

public class EEL
    implements
    ActionListener,
    AdjustmentListener,
    AWTEventListener,
    ComponentListener,
    ContainerListener,
    FocusListener,
    InputMethodListener,
    ItemListener,
    KeyListener,
    MouseListener,
    MouseMotionListener,
    TextListener,
    WindowListener,
    // but wait! there's more!
    AncestorListener,
    CaretListener,
    CellEditorListener,
    ChangeListener,
    DocumentListener,
    HyperlinkListener,
    InternalFrameListener,
    ListDataListener,
    ListSelectionListener,
    MenuDragMouseListener,
    MenuListener,
    MouseInputListener,
    PopupMenuListener,
    TableColumnModelListener,
    TableModelListener,
    TreeExpansionListener,
    TreeModelListener,
    TreeSelectionListener,
    TreeWillExpandListener,
    UndoableEditListener,
    PropertyChangeListener,
    VetoableChangeListener
// And even more...stop the madness!
// DnD support should go in here, too...
{
  private static LinkedList loggers = new LinkedList();

  private JTextArea guiStream = new JTextArea();
  private JFrame guiDisplay = new JFrame("Event Logger");
  private static EEL eel;

  private boolean reportErrors = false;
  private boolean toErr = false;
  private boolean toOut = false;
  private boolean toGui = false;

  public static EEL getInstance(boolean b) {
    if (eel == null) {
      eel = new EEL(false);
    }
    return eel;
  }

  private EEL(boolean b) {
    if (b) {
      // Don't let anyone else instantiate this class
      addErr();
      setReportErrors(true);

      guiDisplay.setSize(400, 600);
      guiDisplay.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

      guiStream.setEditable(false);

      JButton clearB, closeB;
      JPanel buttonPane = new JPanel();
      clearB = new JButton("Clear");
      closeB = new JButton("Close");
      buttonPane.add(clearB);
      buttonPane.add(closeB);

      guiDisplay.getContentPane().add(new JScrollPane(guiStream),
                                      BorderLayout.CENTER);
      guiDisplay.getContentPane().add(buttonPane, BorderLayout.SOUTH);

      clearB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          guiStream.setText("");
        }
      });

      closeB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          guiDisplay.setVisible(false);
        }
      });
    }
  }

  /**
   * Turn on error reporting for this class.  All errors will be sent
   * to STDERR.
   */
  public void setReportErrors(boolean showErrors) {
    reportErrors = showErrors;
  }

  /**
   * Add STDERR to the list of log streams.
   */
  public void addErr() {
    toErr = true;
  }

  /**
   * Add STDOUT to the list of log streams.
   */
  public void addOut() {
    toOut = true;
  }

  /**
   * Add a graphical window to the list of log streams.
   */
  public void addGui() {
    toGui = true;
  }

  /**
   * Remove STDERR from the list of log streams.
   */
  public void removeErr() {
    toErr = false;
  }

  /**
   * Remove STDOUT from the list of log streams.
   */
  public void removeOut() {
    toOut = false;
  }

  /**
   * Remove the graphical window from the list of log streams.
   */
  public void removeGui() {
    toGui = false;
  }

  /**
   * Add specified PrintWriter to list of log streams.
   */
  public void addStream(PrintWriter pw) {
  }

  /**
   * Create a PrintWriter on the given filename and then add
   * it to the list of log streams.
   */
  public void addStream(String logname) {
    try {
      addStream(new PrintWriter(new FileWriter(logname)));
    }
    catch (Exception e) {
      if (reportErrors) {
        System.err.println("Could not create writer for " + logname);
      }
    }
  }

  /**
   * Put the graphical logger window on the screen.  Note that this does
   * not affect the status of the gui stream logging.  You should call
   * addGui() to ensure this logger will receive copies of the events
   * along with the other loggers.
   */
  public void showDialog() {
    if (eel.guiDisplay != null) {
      eel.guiDisplay.setVisible(true);
      if (eel.guiDisplay.getState() == Frame.ICONIFIED) {
        eel.guiDisplay.setState(Frame.NORMAL);
      }
    }
  }

  /**
   * Hide the graphical logger.  Note that this does not affect the status
   * of the gui stream logging.  I.e. this does not call removeGui()
   * automatically.  This method merely removes the window from the screen.
   * Logging can still occur to the text area of the window.  This may be
   * desirable, but if not, you should call removeGui() yourself just before
   * hiding the window.
   */
  public void hideDialog() {
    if ( (eel.guiDisplay != null) && (eel.guiDisplay.isShowing())) {
      eel.guiDisplay.setVisible(false);
    }
  }

  public void log(String msg) {
      System.out.println(msg);
  }

  // ActionListener methods
  public void actionPerformed(ActionEvent ae) {
    log("ActionPerformed: " + ae);
  }

  // AdjusmetListener methods
  public void adjustmentValueChanged(AdjustmentEvent ae) {
    log("ValueAdjusted: " + ae);
  }

  // AWTEventListener methods
  public void eventDispatched(AWTEvent ae) {
    log("EventDispatched: " + ae);
  }

  // ComponentListener methods
  public void componentHidden(ComponentEvent ce) {
    log("ComponentHidden: " + ce);
  }

  public void componentMoved(ComponentEvent ce) {
    log("ComponentMoved: " + ce);
  }

  public void componentResized(ComponentEvent ce) {
    log("ComponentResized: " + ce);
  }

  public void componentShown(ComponentEvent ce) {
    log("ComponentShown: " + ce);
  }

  // ContainerListener methods
  public void componentAdded(ContainerEvent ce) {
    log("ComponentAdded: " + ce);
  }

  public void componentRemoved(ContainerEvent ce) {
    log("ComponentRemoved: " + ce);
  }

  // FocusListener methods
  public void focusGained(FocusEvent fe) {
    log("FocusGained: " + fe);
  }

  public void focusLost(FocusEvent fe) {
    log("FocusLost: " + fe);
  }

  // HierarchyBoundsListener methods [[new to 1.4?]]
  // HierarchyListener methods [[new to 1.4?]]

  // InputMethodListener methods
  public void caretPositionChanged(InputMethodEvent ime) {
    log("CaretPosition: " + ime);
  }

  public void inputMethodTextChanged(InputMethodEvent ime) {
    log("InputMethodText: " + ime);
  }

  // ItemListener methods
  public void itemStateChanged(ItemEvent ie) {
    log("ItemStateChanged: " + ie);
  }

  // KeyListener methods
  public void keyPressed(KeyEvent ke) {
    log("KeyPressed: " + ke);
  }

  public void keyReleased(KeyEvent ke) {
    log("KeyReleased: " + ke);
  }

  public void keyTyped(KeyEvent ke) {
    log("KeyTyped: " + ke);
  }

  // MouseListener methods
  public void mouseEntered(MouseEvent me) {
    //log("MouseEntered: " + me);
  }

  public void mouseExited(MouseEvent me) {
    //log("MouseExited: " + me);
  }

  public void mousePressed(MouseEvent me) {
    //log("MousePressed: " + me);
  }

  public void mouseReleased(MouseEvent me) {
    //log("MouseReleased: " + me);
  }

  public void mouseClicked(MouseEvent me) {
    if(me.getClickCount()==2) {
      JyjJTable tabble = (JyjJTable) me.getSource();
      DataTableModel model = (DataTableModel) tabble.getModel();
      int h = tabble.getRowHeight();
      int y = me.getY();
      int row = y / h ;
      String pk = (String)model.getValueAt(row, 0);
    }
  }

  // MouseMotionListener methods
  public void mouseMoved(MouseEvent me) {
    //log("MouseMoved: " + me);
  }

  public void mouseDragged(MouseEvent me) {
    log("MouseDragged: " + me);
  }

  // MouseWheelListener methods [[new to 1.4?]]

  // TextListener methods
  public void textValueChanged(TextEvent te) {
    log("TextChanged: " + te);
  }

  // WindowListener methods
  public void windowClosing(WindowEvent we) {
    log("WindowClosing: " + we);
  }

  public void windowClosed(WindowEvent we) {
    log("WindowClosed: " + we);
  }

  public void windowOpened(WindowEvent we) {
    log("WindowOpened: " + we);
  }

  public void windowIconified(WindowEvent we) {
    log("WindowIconified: " + we);
  }

  public void windowDeiconified(WindowEvent we) {
    log("WindowDeiconified: " + we);
  }

  public void windowActivated(WindowEvent we) {
    log("WindowActivated: " + we);
  }

  public void windowDeactivated(WindowEvent we) {
    log("WindowDeactivated: " + we);
  }

  // WindowFocusListener methods [[new to 1.4?]]
  // WindowStateListener methods [[new to 1.4?]]

  // *** SWING EVENTS ***
  // AncestorListener methods
  public void ancestorAdded(AncestorEvent ae) {
    log("AncestorAdded: " + ae);
  }

  public void ancestorMoved(AncestorEvent ae) {
    log("AncestorMoved: " + ae);
  }

  public void ancestorRemoved(AncestorEvent ae) {
    log("AncestorRemoved: " + ae);
  }

  // CaretListener methods
  public void caretUpdate(CaretEvent ce) {
    log("CaretUpdated: " + ce);
  }

  // CellEditorListener methods
  public void editingCanceled(ChangeEvent ce) {
    log("EditingCanceled: " + ce);
  }

  public void editingStopped(ChangeEvent ce) {
    log("EditingStopped: " + ce);
  }

  // ChangeListener methods
  public void stateChanged(ChangeEvent ce) {
    log("StateChanged: " + ce);
  }

  // DocumentListener methods
  public void changedUpdate(DocumentEvent de) {
    log("ChangedUpdate: " + de);
  }

  public void insertUpdate(DocumentEvent de) {
    log("InsertUpdate: " + de);
  }

  public void removeUpdate(DocumentEvent de) {
    log("RemoveUpdate: " + de);
  }

  // HyperlinkListener methods
  public void hyperlinkUpdate(HyperlinkEvent he) {
    log("HyperlinkUpdate: " + he);
  }

  // InternalFrameListener methods
  public void internalFrameActivated(InternalFrameEvent ife) {
    log("InternalFrameActivated: " + ife);
  }

  public void internalFrameClosed(InternalFrameEvent ife) {
    log("InternalFrameClosed: " + ife);
  }

  public void internalFrameClosing(InternalFrameEvent ife) {
    log("InternalFrameClosing: " + ife);
  }

  public void internalFrameDeactivated(InternalFrameEvent ife) {
    log("InternalFrameDeactivated: " + ife);
  }

  public void internalFrameDeiconified(InternalFrameEvent ife) {
    log("InternalFrameDeiconified: " + ife);
  }

  public void internalFrameIconified(InternalFrameEvent ife) {
    log("InternalFrameIconified: " + ife);
  }

  public void internalFrameOpened(InternalFrameEvent ife) {
    log("InternalFrameOpened: " + ife);
  }

  // ListDataListener methods
  public void contentsChanged(ListDataEvent lde) {
    log("ContentsChanged: " + lde);
  }

  public void intervalAdded(ListDataEvent lde) {
    log("IntervalAdded: " + lde);
  }

  public void intervalRemoved(ListDataEvent lde) {
    log("IntervalRemoved: " + lde);
  }

  // ListSelectionListener methods
  public void valueChanged(ListSelectionEvent lse) {
    log("ListValueChanged: " + lse);
  }

  // MenuDragMouseListener methods
  public void menuDragMouseDragged(MenuDragMouseEvent mdme) {
    log("MenuMouseDragged: " + mdme);
  }

  public void menuDragMouseEntered(MenuDragMouseEvent mdme) {
    log("MenuMouseEntered: " + mdme);
  }

  public void menuDragMouseExited(MenuDragMouseEvent mdme) {
    log("MenuMouseExited: " + mdme);
  }

  public void menuDragMouseReleased(MenuDragMouseEvent mdme) {
    log("MenuMouseReleased: " + mdme);
  }

  // MenuKeyListener methods
  public void menuKeyPressed(MenuKeyEvent mke) {
    log("MenuKeyPressed: " + mke);
  }

  public void menuKeyReleased(MenuKeyEvent mke) {
    log("MenuKeyReleased: " + mke);
  }

  public void menuKeyTyped(MenuKeyEvent mke) {
    log("MenuKeyTyped: " + mke);
  }

  // MenuListener methods
  public void menuCanceled(MenuEvent me) {
    log("MenuCanceled: " + me);
  }

  public void menuDeselected(MenuEvent me) {
    log("MenuDeselected: " + me);
  }

  public void menuSelected(MenuEvent me) {
    //log("MenuSelected: " + me);
    System.out.println("µ¥»÷:" + me.toString());
  }

  // MouseInputListener methods
  // This is actually a convenience listener that combines the
  // MouseListener and MouseMotionListener methods into one spot.
  // We've written those methods, so we have nothing extra to do
  // here...

  // PopupMenuListener methods
  public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
    log("PopupWillBeInvisible: " + pme);
  }

  public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
    log("PopupWillBeVisible: " + pme);
  }

  public void popupMenuCanceled(PopupMenuEvent pme) {
    log("PopupCanceled: " + pme);
  }

  // TableColumnModelListener methods
  public void columnAdded(TableColumnModelEvent tcme) {
    log("ColumnAdded: " + tcme);
  }

  public void columnMarginChanged(ChangeEvent ce) {
    log("ColumnMarginChanged: " + ce);
  }

  public void columnMoved(TableColumnModelEvent tcme) {
    log("ColumnMoved: " + tcme);
  }

  public void columnRemoved(TableColumnModelEvent tcme) {
    log("ColumnRemoved: " + tcme);
  }

  public void columnSelectionChanged(ListSelectionEvent lse) {
    log("ColumnSelectionChanged: " + lse);
  }

  // TableModelListener methods
  public void tableChanged(TableModelEvent tme) {
    //log("TableChanged: " + tme);
  }

  // TreeExpansionListener methods
  public void treeCollapsed(TreeExpansionEvent tee) {
    log("TreeCollapsed: " + tee);
  }

  public void treeExpanded(TreeExpansionEvent tee) {
    log("TreeExpanded: " + tee);
  }

  // TreeModelListener methods
  public void treeNodesChanged(TreeModelEvent tme) {
    log("TreeNodesChanged: " + tme);
  }

  public void treeNodesInserted(TreeModelEvent tme) {
    log("TreeNodesInserted: " + tme);
  }

  public void treeNodesRemoved(TreeModelEvent tme) {
    log("TreeNodesRemoved: " + tme);
  }

  public void treeStructureChanged(TreeModelEvent tme) {
    log("TreeStructureChanged: " + tme);
  }

  // TreeSelectionListener methods
  public void valueChanged(TreeSelectionEvent tse) {
    //log("TreeValueChanged: " + tse);
    System.out.println("TreeValueChanged: " + tse);
  }

  // TreeWillExpandListener methods
  public void treeWillCollapse(TreeExpansionEvent tee) {
    log("TreeWillCollapse: " + tee);
  }

  public void treeWillExpand(TreeExpansionEvent tee) {
    log("TreeWillExpand: " + tee);
  }

  // UndoableEditListener methods
  public void undoableEditHappened(UndoableEditEvent uee) {
    log("UndoableEditHappened: " + uee);
  }

  //*** JAVABEANS LISTENERS ***
   // PropertyChangeListener methods
   public void propertyChange(PropertyChangeEvent pce) {
     log("PropertyChange: " + pce);
   }

  // VetoableChangeListener methods
  public void vetoableChange(PropertyChangeEvent pce) {
    log("VetoableChange: " + pce);
  }
}
