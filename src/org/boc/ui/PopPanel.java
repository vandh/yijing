package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.boc.ui.MyTextPane;

public class PopPanel extends JDialog {
	private static final long serialVersionUID = 2685642063387323018L;
	private MyTextPane text;
	JDialog dialog;
	
	public PopPanel(int w, int h, JDialog dialog) {
		this( w, h);
		this.dialog = dialog;
		//System.out.println(dialog);
	}
	public PopPanel() {
		this(0,0);
	}
	public PopPanel(int w, int h) {
		Box box = new Box(BoxLayout.Y_AXIS);
    text = new MyTextPane();        
    JScrollPane jScrollPane = new JScrollPane(text);        
    box.add(jScrollPane, BorderLayout.CENTER);   
    this.getContentPane().add(box,BorderLayout.CENTER);
    //this.setUndecorated(true);  
    this.setSize(w, h);  
    this.setLocationRelativeTo(null);  
    
//    this.addWindowFocusListener(new WindowFocusListener(){
//    	public void windowGainedFocus(WindowEvent e){}
//    	public void windowLostFocus(WindowEvent e){
//    	    e.getWindow().toFront();
//    	}
//    	});
    this.setAlwaysOnTop(true);
    //this.setVisible(true); 
	}
	
	public void setAll(String title, int w, int h) {
		this.setTitle(title);
		this.setSize(w,h);
	}
	
	public void hidFloatWindow() {
		this.setVisible(false);
	}
	
	public MyTextPane getTextpane() {
		return text;
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if(e.getID()==WindowEvent.WINDOW_CLOSING){
			JDialog d = (JDialog)e.getComponent();
			d.setVisible(false);
			if(dialog!=null) {				
				dialog.setVisible(true);
			}
		}
	}
	
	public static void main(String[] args) {
		//PopPanel.showFloatWindow(500,600);
	}
}
