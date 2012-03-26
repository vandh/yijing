package org.boc.util;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.io.File;

public class Messages {
  /** Show an information message contains <code> msg String</code>*/
	private static HtmlMultiLineControl html = new HtmlMultiLineControl();
  public static void info(String msg) {
    info(msg,null);
  }
  public static void info(String msg, JDialog dialog) {
    JLabel jmsg = new JLabel(msg);
    jmsg.setFont(Public.getFont());
    JOptionPane.showMessageDialog(null, jmsg,
                                  "始皇预测 - 温馨提示",
                                  JOptionPane.INFORMATION_MESSAGE);
    if(dialog!=null) dialog.setVisible(true);
  }

  /** Show an error message contains <code> msg String</code>*/
  public static void error(String msg) {
    //System.err.println(msg);
    JLabel jmsg = new JLabel(html.CovertDestionString(procMsg(msg),3));
    jmsg.setFont(Public.getFont());
    JOptionPane.showMessageDialog(null, jmsg,
                                  "始皇预测 - 出错啦",
                                  JOptionPane.ERROR_MESSAGE);
  }
  
  private static String procMsg(String msg) {
  	int LENGTH = 60;
  	StringBuffer sb = new StringBuffer(msg==null?"":msg);
  	int len = sb.length()/LENGTH;  //60 20,40
  	for(int i=1; i<len; i++) {
  		sb.insert(LENGTH*i+(i-1)*2, "\r\n");
  	}
  	return sb.toString();
  }

  /** Show a question message contains <code> msg String</code>
   *  @return int JOptionPane response.
   */
  public static int question(String msg) {  	  	
    JLabel jmsg = new JLabel(html.CovertDestionString(procMsg(msg),3));
    jmsg.setFont(Public.getFont());
    return JOptionPane.showConfirmDialog(null, jmsg,
                                         "始皇预测 - 有问题哟",
                                         JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE);
  }

  /** Show a warning message contains <code> msg String</code>*/
  public static void warning(String msg) {
    JLabel jmsg = new JLabel(msg);
    jmsg.setFont(Public.getFont());
    JOptionPane.showMessageDialog(null, jmsg,
                                  "始皇预测 - 注意啦",
                                  JOptionPane.WARNING_MESSAGE);
  }

  public static String input(String msg) {
    JLabel jmsg = new JLabel(msg);
    jmsg.setFont(Public.getFont());
    return (String) JOptionPane.showInputDialog(null, msg,
                                                "始皇预测",
                                                JOptionPane.QUESTION_MESSAGE);

  }

  public static String chooseFile(CustomFileFilter fileFilter) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileFilter(fileFilter);
    //fileChooser.setAccessory(new TextFilePreview(fileChooser));

    boolean canWrite = true;
    while (true) {
      canWrite = true;
      int ret = fileChooser.showSaveDialog(null);
      if (ret == JFileChooser.APPROVE_OPTION) {
        StringBuffer name = new StringBuffer();

        try {
          name.append(fileChooser.getSelectedFile().getCanonicalPath());
        }
        catch (Exception ex) {
          Messages.error("Messages.chooseFile : " + ex);
        }

        if (name.toString().toLowerCase().lastIndexOf("." +
            fileFilter.getExtension().toLowerCase()) <= 0)
          name.append("." + fileFilter.getExtension().toLowerCase());
        //如果文件存在则覆盖
        if ((new File(name.toString())).exists()) {
          int retOw = question("文件已经存在，覆盖吗？");
          if (retOw == JOptionPane.NO_OPTION)
            canWrite = false;
        }

        if (canWrite) {
          return name.toString();
        }
      }
      else
        return null;
    }

  }

  public static String chooseFile(CustomFileFilter fileFilter[], boolean bl) {

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);

    for (int i = 0; i < fileFilter.length; i++)
      fileChooser.setFileFilter(fileFilter[i]);

    boolean canWrite = true;
    while (true) {
      canWrite = true;
      int ret = fileChooser.showSaveDialog(null);
      if (ret == JFileChooser.APPROVE_OPTION) {
        StringBuffer name = new StringBuffer();
        String extension = ( (CustomFileFilter) fileChooser.getFileFilter()).
            getExtension();

        try {
          name.append(fileChooser.getSelectedFile().getCanonicalPath());
        }
        catch (Exception ex) {
          Messages.error("Messages.chooseFile : " + ex);
        }

        if (name.toString().toLowerCase().lastIndexOf("." +
            extension.toLowerCase()) <= 0)
          name.append("." + extension.toLowerCase());

        if(bl) {
          if ( (new File(name.toString())).exists()) {
            int retOw = question("文件已经存在，覆盖吗？");
            if (retOw == JOptionPane.NO_OPTION)
              canWrite = false;
          }
        }

        if (canWrite) {
          return name.toString();
        }
      }
      else
        return null;
    }
  }
  
  public static void main(String[] args) {
  	int LENGTH = 20;
  	StringBuffer sb = new StringBuffer("01234567890123456789012345678901234567890123456789012345678901234567890123456789");
  	int len = sb.length()/LENGTH;  //60 20,40
  	for(int i=1; i<len; i++) {
  		sb = sb.insert(LENGTH*i+(i-1)*2, "\r\n");
  	}
  	System.out.println(sb);
	}
}
