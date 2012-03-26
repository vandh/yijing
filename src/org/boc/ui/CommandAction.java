package org.boc.ui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CommandAction
    extends AbstractAction {
  ActionListener listener = null;
  public CommandAction(String text, Icon icon,
                       String description, char accelerator,
                       java.awt.event.ActionListener listener) {
    super(text, icon);
    if (accelerator >= 'A') {
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator,
          Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
    putValue(SHORT_DESCRIPTION, description);
    this.listener = listener;
  }

  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    listener.actionPerformed(e);
  }
}