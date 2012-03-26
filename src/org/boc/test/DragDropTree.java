package org.boc.test;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.util.*;

public class DragDropTree
    implements MouseMotionListener, MouseListener, DragGestureListener,
    DragSourceListener {

  private JTree tree1 = null;
  private JTree tree2 = null;
  Object lastNode;
  Vector vet = new Vector();
  TreeSelectionModel selectionModel = tree1.getSelectionModel();
  Vector vetnew = new Vector();
  private boolean drag = false;

  public DragDropTree(JTree t1, JTree t2) {
    tree1 = t1;
    tree2 = t2;
    DragSource dragSource = DragSource.getDefaultDragSource();
    dragSource.createDefaultDragGestureRecognizer(
        tree1, // component where drag originates
        DnDConstants.ACTION_COPY_OR_MOVE, // actions
        this); // drag gesture recognizer
    dragSource.createDefaultDragGestureRecognizer(
        tree2, // component where drag originates
        DnDConstants.ACTION_COPY_OR_MOVE, // actions
        this); // drag gesture recognizer
    tree1.addMouseMotionListener(this);
    tree1.addMouseListener(this);
    tree2.addMouseMotionListener(this);
    tree2.addMouseListener(this);
  }

  public void mouseEntered(MouseEvent e) {

    JTree selecttree = (JTree) e.getSource();
    TreePath path = selecttree.getPathForLocation(e.getX(), e.getY());
    if (path != null) {
      if (drag & vet != null) {
        drag = false;
        String str = path.getPathComponent(1).toString();

        MutableTreeNode parent,
            node = (MutableTreeNode) path.getLastPathComponent();
        if (node.isLeaf())
          parent = (MutableTreeNode) node.getParent();
        else
          parent = node;
        int index = parent.getIndex(node) + 1;
        System.out.println("now node is in " + index + "level");

        DefaultTreeModel model = (DefaultTreeModel) tree2.getModel();

        int j = 0;
        while (j < vet.size()) {
          String ss = vet.elementAt(j).toString();
          MutableTreeNode newnode = new DefaultMutableTreeNode(ss);
          model.insertNodeInto(newnode, parent, index);
          j++;
        }
        vet = null;
        vet = new Vector();
      }
      else;

    }
    else;

  }

  public void mouseDragged(MouseEvent e) {
    drag = true;
    if (selectionModel != null) selectionModel.clearSelection();

  }

  public void mouseMoved(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {

    lastNode = null;
    if (e.getClickCount() == 1) {
      TreePath path = tree1.getPathForLocation(e.getX(), e.getY());

      if (path != null) {
        TreeNode node = (TreeNode) path.getLastPathComponent();
        if (node.isLeaf()) {
          lastNode = (MutableTreeNode) path.getLastPathComponent();
          TreeSelectionModel selectionModel = tree1.getSelectionModel();
          int a = selectionModel.getSelectionCount();
          System.out.println("node is :" + a);
          if (a > 1) {

            if (vetnew != null) {
              System.out.println(vetnew.size() + "have so much path");
              for (int n = 0; n < vetnew.size(); n++) {
                TreePath p = (TreePath) vetnew.get(n);
                System.out.println(p);
                if (p == path) path = null;
              }
            }
            if (path != null)

            {
              vet.addElement(lastNode);
              vetnew.addElement(path);
            }
          }
          else {
            if (vet != null) {
              vet.removeAllElements();
              vet.addElement(lastNode);
            }
            if (vetnew != null) {
              vetnew.removeAllElements();
              vetnew.addElement(path);
            }
          }

        }
        else
          //JOptionPane.showMessageDialog(this, "please select a leaf node!");
          System.out.println("please select a leaf node!");
      }
    }

  }

  public void mouseReleased(MouseEvent e) {}

  public void dragGestureRecognized(DragGestureEvent e) {
    // drag anything ...
    e.startDrag(DragSource.DefaultCopyDrop, // cursor
                new StringSelection("drag well"), // transferable
                this); // drag source listener
    drag = true;
  }

  public void dragDropEnd(DragSourceDropEvent e) {}

  public void dragEnter(DragSourceDragEvent e) {}

  public void dragExit(DragSourceEvent e) {}

  public void dragOver(DragSourceDragEvent e) {}

  public void dropActionChanged(DragSourceDragEvent e) {}
}
