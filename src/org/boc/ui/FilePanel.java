package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.boc.util.CustomFileFilter;
import org.boc.util.FileUtilities;
import org.boc.util.Messages;
import org.boc.util.Public;

public class FilePanel
    extends JPanel
    implements  ActionListener {
  private JyjButton bBackup;
  private JyjButton bRecovery;
  private JCheckBox delOldBack;
  private JCheckBox delOldRecover;

  public FilePanel() {
    super();
    init();
  }

  public void init() {
    bBackup = new JyjButton(
      new ImageIcon(getClass().getResource("/images/close.gif")), "备份", this, "backup");
    bRecovery = new JyjButton(
      new ImageIcon(getClass().getResource("/images/open.gif")), "恢复", this, "recovery");
    this.setLayout(new BorderLayout());
    this.setFont(Public.getFont());

    Box box1 = new Box(BoxLayout.X_AXIS);
    box1.add(new JLabel("1. 文件备份：    "));
    box1.add(bBackup);
    delOldBack = new JCheckBox("删除原历史数据", false);
    box1.add(new JLabel("      "));
    box1.add(delOldBack);

    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("2. 文件恢复：    "));
    box2.add(bRecovery);
    delOldRecover = new JCheckBox("删除原备份文件", false);
    box2.add(new JLabel("      "));
    box2.add(delOldRecover);

    Box box3 = new Box(BoxLayout.Y_AXIS);
    box3.add(box1);
    box3.add(box2);
    box3.add(new JLabel(" "));

    this.add(new JLabel(" "),BorderLayout.NORTH);
    this.add(box3,BorderLayout.CENTER);
    this.add(new JLabel(" "),BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("backup")) {
      try {
          this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
          //如果历史目录为空，则直接返回
          if(!jarOrZipFiles(Public.HOME+Public.DATA)) {
            Messages.info("历史数据目录为空，不需要备份！");
            return;
          }
          this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      catch (Exception ex) {
        Messages.error("FilePanel()备份历史数据失败: " + ex);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }

    }
    else{
      try {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //Messages.info(name);
        readFromJarOrZip();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
      catch (Exception ex) {
        Messages.error("FilePanel()恢复文件失败: " + ex);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      }
    }
  }

  /**
   * 将指定目录下的文件打成jar包
   * 最后保存到指定的文件夹下面
   */
  public boolean jarOrZipFiles(String path) {
    File[] files = getFiles(path, null);
    if(files==null || files.length<1)
      return false;

    String name = null;
    CustomFileFilter zipfileFilter[] = {
        new CustomFileFilter("归档文件(*.jar)", FileUtilities.JAR),
        new CustomFileFilter("压缩文件(*.zip)", FileUtilities.ZIP)};
    try{
      name = Messages.chooseFile(zipfileFilter, true);
      if (name != null) {
        //true如果文件存在则提示覆盖，false文件存在也不提示覆盖，因为是恢复
        if (name.substring(name.lastIndexOf(".")+1).equals("zip")) {
          ZipOutputStream zipOutput = new ZipOutputStream(new
              BufferedOutputStream(new FileOutputStream(name)));
          zipOutput.setMethod(ZipOutputStream.DEFLATED);
          for (int i = 0; i < files.length; i++) {
            ZipEntry entry = new ZipEntry(files[i].getName());
            entry.setMethod(ZipOutputStream.DEFLATED);
            zipOutput.putNextEntry(entry);
            zipOutput.write(getBytesFromFile(files[i]));
          }
          if (zipOutput != null) {
            zipOutput.closeEntry();
            zipOutput.close();
          }
        }else if(name.substring(name.lastIndexOf(".")+1).equals("jar")) {
          JarOutputStream jarOutput = new JarOutputStream(new
              BufferedOutputStream(new FileOutputStream(name)));
          jarOutput.setMethod(JarOutputStream.DEFLATED);
          for (int i = 0; i < files.length; i++) {
            ZipEntry entry = new ZipEntry( files[i].getName());
            entry.setMethod(JarOutputStream.DEFLATED);
            jarOutput.putNextEntry(entry);
            jarOutput.write(getBytesFromFile(files[i]));
          }
          if (jarOutput != null) {
            jarOutput.closeEntry();
            jarOutput.close();
          }
        }
        if(this.delOldBack.isSelected()) {
            int tip = Messages.question("真的要删除原历史数据吗？");
            //Messages.info(String.valueOf(tip)); 0为确定 1为反悔
            if(tip==0) {
              File f = new File(Public.HOME+Public.DATA);
              File[] fs = f.listFiles();
              for(int i=0; i<fs.length; i++) {
                fs[i].delete();
              }
            }
          }

        Messages.error("备份历史数据成功啦！ ");
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      Messages.error("JxtDumpPanelListener : erreur SQL : " + ex);
    }
    return true;
  }

  /**
   * 解压jar包的文件内容
   * 最后保存到指定的文件夹下面
   */
  public void UnJarOrZipFile(String path) {
    byte[] bs ;
    String name = null;
    final int BUFFER = 2048;
    CustomFileFilter zipfileFilter[] = {
        new CustomFileFilter("归档文件(*.jar)", FileUtilities.JAR),
        new CustomFileFilter("压缩文件(*.zip)", FileUtilities.ZIP)};
    try{
      name = Messages.chooseFile(zipfileFilter, false);
      if (name != null) {
        if (name.substring(name.lastIndexOf(".") + 1).equals("zip")) {
          BufferedOutputStream dest = null;
          FileInputStream fis = new FileInputStream(name);
          ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
          ZipEntry entry;
          while ( (entry = zis.getNextEntry()) != null) {
            //System.out.println("Extracting: " +entry);
            int count;
            byte data[] = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(path+File.separator+entry.getName());
            dest = new BufferedOutputStream(fos, BUFFER);
            while ( (count = zis.read(data, 0, BUFFER)) != -1) {
              dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
          }
          if (zis != null) {
            zis.closeEntry();
            zis.close();
          }
        }
        else if (name.substring(name.lastIndexOf(".") + 1).equals("jar")) {
          BufferedOutputStream dest = null;
          FileInputStream fis = new FileInputStream(name);
          JarInputStream zis = new JarInputStream(new BufferedInputStream(fis));
          JarEntry entry;
          while ( (entry = zis.getNextJarEntry()) != null) {
            //System.out.println("Extracting: " +entry);
            int count;
            byte data[] = new byte[BUFFER];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(path+File.separator+entry.getName());
                      dest = new BufferedOutputStream(fos, BUFFER);
                      while ( (count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                      }
                      dest.flush();
                      dest.close();
                    }
                    if (zis != null) {
                      zis.closeEntry();
                      zis.close();
                    }

        }
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      Messages.error("解压缩文件失败: " + ex);
    }
  }

  /**
   * 读取jar包的文件内容，追加到原文件的后面
   * 最后保存到指定的文件夹下面
   */
  public void readFromJarOrZip() {
    String path = Public.HOME + Public.DATA;
    File[] files = getFiles(path, null);

    final int BUFFER = 2048;
    String name = null;
    CustomFileFilter zipfileFilter[] = {
        new CustomFileFilter("归档文件(*.jar)", FileUtilities.JAR),
        new CustomFileFilter("压缩文件(*.zip)", FileUtilities.ZIP)};
    try{
      name = Messages.chooseFile(zipfileFilter, false);
      if (name != null) {
        if (name.substring(name.lastIndexOf(".")+1).equals("zip")) {
          BufferedOutputStream dest = null;
          ZipInputStream zis =
              new ZipInputStream(new BufferedInputStream(new FileInputStream(name)));
          ZipEntry entry ;
          while ( (entry = zis.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];
            //如果原目录下有文件，则追加到文件末尾，否则新建
            //Messages.info(path + File.separator+entry.getName());
            FileOutputStream fos = new FileOutputStream(new File(path + File.separator+entry.getName()), false);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ( (count = zis.read(data, 0, BUFFER)) != -1) {
              dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
          }
          if (zis != null) {
            zis.closeEntry();
            zis.close();
          }
        }else if(name.substring(name.lastIndexOf(".")+1).equals("jar")) {
          BufferedOutputStream dest = null;
          JarInputStream zis =
              new JarInputStream(new BufferedInputStream(new FileInputStream(name)));
          ZipEntry entry ;
          while ( (entry = zis.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[BUFFER];
            //如果原目录下有文件，则追加到文件末尾，否则新建
            FileOutputStream fos = new FileOutputStream(new File(path + File.separator+entry.getName()), false);
            dest = new BufferedOutputStream(fos, BUFFER);
            while ( (count = zis.read(data, 0, BUFFER)) != -1) {
              dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
          }
          if (zis != null) {
            zis.closeEntry();
            zis.close();
          }
        }
        if(this.delOldRecover.isSelected()) {
          File f = new File(name);
          int tip = Messages.question("真的要删除备份的历史数据吗？");
          //Messages.info(String.valueOf(tip)); 0为确定 1为反悔
          if(tip==0) {
            f.delete();
          }
        }
        Messages.error("恢复历史数据到现有数据库成功啦！ ");
      }
    }catch (Exception ex) {
      ex.printStackTrace();
      Messages.error("恢复历史数据到现有数据库失败: " + ex);
    }
  }

  /**
   * 得到某个目录下的所有文件对象
   * 可以过滤得到某类特定的带扩展名的或包含某名字的文件
   */
  public String[] getFilesAndDir(String path, final String sFilter) {
    if (path == null)
      return null;
    File dir = new File(path);
    String filename;
    String[] children;
    FilenameFilter filter;

    if (sFilter != null) {
      filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.indexOf(sFilter) != -1;
        }
      };
      children = dir.list(filter);
    }
    else {
      children = dir.list();
    }

    return children;
  }

  /**
   * 得到某个目录下的所有文件对象
   * 可以过滤得到某类特定的带扩展名的或包含某名字的文件
   */
  public File[] getFiles(String path, final String sFilter) {
    if (path == null)
      return null;
    File dir = new File(path);
    String filename;
    File[] children;
    FilenameFilter filter;

    if (sFilter != null) {
      filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
          return name.indexOf(sFilter) != -1;
        }
      };
      children = dir.listFiles(filter);
    }
    else {
      children = dir.listFiles();
    }

    return children;
  }


  /**
   * 读取文件在字节数组中
   * @param file File
   * @throws IOException
   * @return byte[]
   */
  public byte[] getBytesFromFile(File file) throws IOException {
    InputStream is = new FileInputStream(file);
    long length = file.length();
    if (length > Integer.MAX_VALUE) {
      Messages.info("文件太长啦！");
    }
    byte[] bytes = new byte[ (int) length];
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length &&
           (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }
    if (offset < bytes.length) {
      Messages.info("读取文件出错啦！");
    }
    is.close();
    return bytes;
  }



  public static void main(String[] args) {
  }


}
