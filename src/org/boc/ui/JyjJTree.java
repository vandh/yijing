package org.boc.ui;

import javax.swing.*;
import javax.swing.tree.*;

import org.boc.dao.DaoPublic;
import org.boc.util.*;

public class JyjJTree extends JTree {

	public JyjJTree(DefaultTreeModel defaultTreeModel) {
		super(defaultTreeModel);
		this.setFont(Public.getFont());
		this.putClientProperty("JTree.lineStyle", "Angled");
	}
	public JyjJTree(Object[] objs) {
		super(objs);
		this.setFont(Public.getFont());
		this.putClientProperty("JTree.lineStyle", "Angled");
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("VSX Test");
//		Helper parser = new Helper();
//		JyjJTree tree = new JyjJTree(parser.parse("conf/tree.xml"));
		JyjJTree tree = new JyjJTree(new String[]{Public.TREEROOT});
		frame.getContentPane().add(new JScrollPane(tree));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);
		frame.setVisible(true);
	}
}
