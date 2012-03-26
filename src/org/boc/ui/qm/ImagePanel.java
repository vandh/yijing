package org.boc.ui.qm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private String imagePath; // 图片路径
	private ImageIcon icon;
	private Dimension theSize = new Dimension(20, 20);
	private String id; //标识的id
	private String name ;  //名称
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImagePanel(String imgPath) {
		this.imagePath = imgPath;
	}

//	public void setImage(String imgpath) {
//		icon = new ImageIcon(getClass().getResource(imgpath));
//		this.repaint();
//	}
//
//	public Image getImage() {
//		return this.image;
//	}

	public void paintComponent(Graphics g) {
		if(imagePath==null) return;
		icon = new ImageIcon(getClass().getResource(imagePath));
		g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth(), icon.getIconHeight(), null);
	}

	public Dimension getPreferredSize() {
		return this.theSize;
	}
}