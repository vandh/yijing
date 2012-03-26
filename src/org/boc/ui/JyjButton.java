package org.boc.ui;

import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import org.boc.util.Public;

public class JyjButton
    extends JButton {
  public JyjButton() {
    super();
    this.setHorizontalTextPosition(SwingConstants.CENTER);
    this.setFont(Public.getFont());
    apparence();
  }

  public JyjButton(String text) {
    super(text);
    this.setToolTipText(text);
    this.setFont(Public.getFont());
    this.setHorizontalTextPosition(SwingConstants.CENTER);
    apparence();
  }

  public JyjButton(String text, ActionListener listener, String actionCommand) {
    super(text);
    this.setFont(Public.getFont());
    this.setHorizontalTextPosition(SwingConstants.CENTER);
    this.addActionListener(listener);
    this.setActionCommand(actionCommand);
    apparence();
  }

  public JyjButton(ImageIcon icon, String text, ActionListener listener,
                   String actionCommand) {
    //super(text);
    this.setToolTipText(text);
    this.setIcon(icon);
    this.setFont(Public.getFont());
    this.setHorizontalTextPosition(SwingConstants.CENTER);
    this.addActionListener(listener);
    this.setActionCommand(actionCommand);
    this.setFocusPainted(false);
    apparence();
  }

  private void apparence() {
    this.setMargin(new Insets(3, 3, 3, 3));
  }

}
