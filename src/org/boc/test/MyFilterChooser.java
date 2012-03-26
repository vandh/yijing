package org.boc.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MyFilterChooser extends JFrame {
public MyFilterChooser( ) {
  super("Filter Test Frame");
  setSize(350, 200);
  setDefaultCloseOperation(EXIT_ON_CLOSE);

  Container c = getContentPane( );
  c.setLayout(new FlowLayout( ));

  JButton openButton = new JButton("Open");
  final JLabel statusbar = new JLabel("Output of your selection will go here");

  openButton.addActionListener(new ActionListener( ) {
    public void actionPerformed(ActionEvent ae) {
      String[] pics = new String[] {"gif", "jpg", "tif"};
      String[] audios = new String[] {"au", "aiff", "wav"};
      JFileChooser chooser = new JFileChooser( );
      chooser.addChoosableFileFilter(new SimpleFileFilter(pics,
                                     "Images (*.gif, *.jpg, *.tif)"));
      chooser.addChoosableFileFilter(new SimpleFileFilter(".MOV"));
      chooser.addChoosableFileFilter(new SimpleFileFilter(audios,
                                     "Sounds (*.aiff, *.au, *.wav)"));
      int option = chooser.showOpenDialog(MyFilterChooser.this);
      if (option == JFileChooser.APPROVE_OPTION) {
        if (chooser.getSelectedFile( )!=null)
          statusbar.setText("You chose " + chooser.getSelectedFile( ).getName( ));
      }
      else {
        statusbar.setText("You canceled.");
      }
    }
  });

  c.add(openButton);
  c.add(statusbar);
  setVisible(true);
}

public static void main(String args[]) {
  MyFilterChooser mfc = new MyFilterChooser( );
}
}
