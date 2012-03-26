package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;

import org.boc.db.qm.QiMen2;
import org.boc.help.HelpThread;
import org.boc.ui.qm.AboutFrame;
import org.boc.util.Messages;
import org.boc.util.Public;
import org.boc.xml.XmlProc;

public final class Main extends JFrame {
  private Main1 main ;
  private Main2 main2;  
  private JPanel jContentPane; //背景大面板
  private JPanel jPanelStatus; //状态栏
  private JPanel jPanelStatusTip; //状态栏提示容器
  private JLabel jLabelStatusTip; //状态栏提示标签
  private static JLabel jLabelInfo; //状态栏中起局信息标签
  private JPanel jPanelInfo; //状态栏中时间容器
  private javax.swing.Timer timer;
  private static final int WIDTH = 800;
  private static final int HIGHT = 500;
  private static Main jyjframe ;
  public static PrintStream fout; 
  public static PrintStream ferr;

  private Main() {
    main = new Main1();
    main2 = new Main2();
  }

  public static Main getInstance() {
    if(jyjframe==null)
      jyjframe = new Main();
    return jyjframe;
  }

  public void init() {
  	JFrame.setDefaultLookAndFeelDecorated(true);
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //设置缺省关闭按钮
    setSize();

    this.setContentPane(getJContentPane());
    this.setJMenuBar(main.getJMenuBar());
    this.getContentPane().add(main.getJToolBar(),BorderLayout.NORTH);
    this.getContentPane().add(main2, BorderLayout.CENTER);
    this.getContentPane().add(getJPanelStatus(), BorderLayout.SOUTH);

    this.setTitle(Public.title);
    this.setIconImage((new ImageIcon(getClass().getResource("/images/title.gif"))).getImage());
    //refreshStatus();

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        systemExit();
      }
    });
  }

  public static void setStatusInfo(int type) {
  	if(type==Public.QM)
  	jLabelInfo.setText("                "+
    		(QiMen2.ZF?"转盘":"飞盘")+"      "+
    		(QiMen2.RB?"置闰法":"拆补法")+"     "+
    		(QiMen2.TD?"小值符随天盘":"小值符随地盘")+"     "+
    		(QiMen2.KG?"永寄坤宫":"阴坤阳艮"));      
  	else if(type==Public.LY)
  		jLabelInfo.setText("                ");
  	else
  		jLabelInfo.setText("                ");
  }

  private void setSize() {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(WIDTH, HIGHT);
    int width = this.getWidth();
    int height = this.getHeight();
    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    this.setBounds(x, y, width, height);
    //this.setBounds(0, 0, screen.width, screen.height);
  }

  private JPanel getJPanelStatus() {
    if (jPanelStatus == null) {
      jPanelStatus = new JPanel();
      jPanelStatus.setLayout(new BoxLayout(jPanelStatus, BoxLayout.X_AXIS));
      jPanelStatus.setPreferredSize(new Dimension(900, 30));
      jPanelStatus.add(getStatusInfo(), null);  //一个显示当前起局的状况
      jPanelStatus.add(getJPanelStatusTip(), null); //一个显示版权的面板
    }
    return jPanelStatus;
  }
  
  /**
   * 得到状态栏的信息面板
   * @return
   */
  private JPanel getStatusInfo() {
    if (jPanelInfo == null) {
      jLabelInfo = new JLabel();
      jPanelInfo = new JPanel();
      jPanelInfo.setLayout(new BorderLayout());
      jPanelInfo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      jPanelInfo.setPreferredSize(new Dimension(800, 30));
      jLabelInfo.setFont(new Font("TimesRoman", Font.PLAIN, 12));
      //jLabelInfo.setForeground(Color.BLUE);  
      jPanelInfo.add(jLabelInfo, BorderLayout.CENTER);
    }
    return jPanelInfo;
  }
  
  /**
   * 得到状态栏版权面板
   * @return
   */
  private JPanel getJPanelStatusTip() {
    if (jPanelStatusTip == null) {
      jLabelStatusTip = new JLabel();  //显示版权信息
      jPanelStatusTip = new JPanel();	 //任务栏面板，加入jLabelStatusTip
      jPanelStatusTip.setLayout(new BorderLayout());
      jPanelStatusTip.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      jPanelStatusTip.setPreferredSize(new Dimension(100, 30));
      jLabelStatusTip.setFont(new Font("TimesRoman", Font.PLAIN, 12));
      //jLabelStatusTip.setForeground(Color.BLUE);
      //jLabelStatusTip.setAlignmentY(CENTER_ALIGNMENT);
      jPanelStatusTip.add(jLabelStatusTip, BorderLayout.CENTER);
    }
    return jPanelStatusTip;
  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = (JPanel)this.getContentPane();
      jContentPane.setLayout(new BorderLayout());
    }
    return jContentPane;
  }

  protected Component getThis() {
    return this;
  }

  public static void initGlobalFontSetting(Font fnt) {
    FontUIResource fontRes = new FontUIResource(fnt);
    for (Enumeration keys = UIManager.getDefaults().keys();
         keys.hasMoreElements(); ) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value instanceof FontUIResource)
        UIManager.put(key, fontRes);
    }
  }

  public void setStatusText() {
    jLabelStatusTip.setText("  " + Public.status);
  }

  public void systemExit() {
  	if(fout!=null)
  		fout.close();
  	if(ferr!=null)
  		ferr.close();
  	
//  JOptionPane.showMessageDialog(getThis(),Public.info,Public.infoTitle,JOptionPane.INFORMATION_MESSAGE);
//  System.exit(0);
  	AboutFrame.show(0);

  }

  public void showSplash() {
    SplashWindow splash = null;
    splash = new SplashWindow(Public.SPLASH);
    splash.showSplash("/images/logo.gif");
  }

  /** Memory managment */
  protected void finalize() throws Throwable {
    super.finalize();
    main = null;
    main2 = null;
  }
  //不需要预先建，没有直接缺省。最后保存时就有了
  private static void writeDefaultData() {
  	File f = new File(Public.HOME + Public.DATA);
  	if(!f.exists()) f.mkdirs();
  	f = new File(Public.HOME + Public.LOG);
  	if(!f.exists()) f.mkdirs();
//    File test ;
//    for (int i = 0; i < Public.valueRoot.length; i++) {
//    	File datafile = new File(Public.HOME + Public.DATA);
//    	if(!datafile.exists()) 
//    		datafile.mkdir();
//    	
//      test = new File(Public.HOME + Public.DATA + File.separator + Public.valueRoot[i]+".dat");
//      if (!test.exists()) {
//        Public.writeObject2File(Public.valueRoot[i], null);
//      }
//    }
  }

  //将jar包目录下的文件拷贝到用户目录下
  private static void copy2Home(String dir, String fname)  throws Exception{
    InputStream in;
    FileOutputStream out;
    File test ;

    String filepath = Public.HOME + dir + File.separator+fname;
    test = new File(filepath);
    if (!test.exists()) {
      in = test.getClass().getResourceAsStream("/" + dir + "/" + fname);
      out = new FileOutputStream(filepath);
      int bRead = -1;
      while ( (bRead = in.read()) != -1)
        out.write(bRead);
      out.close();
    }
  }
  
  /**
   * 缺省打开奇门树上的当前时间节点
   * @param type 缺省打开的Public.valueRoot[] qm/ly，缺省双击第一个
   * @param leafNode  当前时间
   * @param parentNodeName  奇门遁甲
   */
  public void openDefault(String[] types) {
  	for(String type : types) 
  		main.OpenIntoTree(type);  //在树上打开几个缺省的预测技术   
  	String type = types[0];
  	
  	String typename = Public.mapRootValueKey.get(type);
    //模拟双击事件，在右边打开一个面板
    JTabbedPane rightJTabbedPane = Main2.getRightTabbedPane();
    //第一次打开xx.xml时，缺省选择“现在时间”这个节点
    BasicJTabbedPane basePane = new BasicJTabbedPane(type,Public.NOW,null);
    new HelpThread(basePane).start();  //启动一个多线程
    TreePanel.mapBaseTabPane.put(type, basePane);
    rightJTabbedPane.add(basePane, typename);
    TreePanel.setCurView(rightJTabbedPane,typename);  //选中双击时对应的预测面板
    Public.setKeyValue(Public.mapKeyIsOpen, type, true);
  }

  /**
   * 本写入用户目录，并拷贝所有配置文件
   * 去掉用户目录直接保存在当前目录下，simon 2011-10-10
   * @param args
   */
  public static void main(String[] args) {
  	Long t1 = System.currentTimeMillis();
  	
  	XmlProc.loadFromOnFile();  //加载启动文件设置选项
    try {
      //检查用户目录
      //checkUserDirectory();
      //写入缺省的数据文件
      writeDefaultData();
    }
    catch (Exception e) {
      e.printStackTrace();
      //System.err.println("JyiFrame.main() : Error : " + e);
      System.exit(0);
    }
    try {
      try {
      	if(Public.DEBUGSWITCH) {
      		ferr = new PrintStream(new FileOutputStream(Public.HOME + "log/jyijing.err"), true); 
      		System.setErr(ferr);
      	}
      }
      catch (Exception e) {
      	if(!Public.DEBUGSWITCH) 
      		System.err.println("Jxtray.main() : Output Error redirection : " + e);
      }
      try {
      	if(Public.DEBUGSWITCH) {
      		fout = new PrintStream(new FileOutputStream(Public.HOME + "log/jyijing.out"), true);
      		System.setOut(fout);
      	}
      }
      catch (Exception e) {
        System.err.println("Jxtray.main() : Output redirection : " + e);
      }
    }catch (Exception e) {
      Messages.error("出错啦，没能装载属性文件(" + e + ")");
      System.err.println("出错啦，没能装载属性文件(" + e + ")");
      System.exit(0);
    }

    initGlobalFontSetting(Public.getFont());
    Main frame = Main.getInstance();
    frame.init();
    //frame.showSplash();  //播放启动画面    
    //frame.setSize();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setStatusText();
    frame.setStatusInfo(Public.QM);
    frame.setVisible(true);
    long t2 = System.currentTimeMillis();
    //System.out.println("1. 启动到出现框架页面时间："+(t2-t1)+"ms");    
    frame.openDefault(new String[]{"ly","qm"});    
    //frame.openDefault(new String[]{"qm","ly"});
    //System.out.println("2. 双击树叶子现现局时间："+(System.currentTimeMillis()-t2)+"ms");    
    if(!QiMen2.IO)
    	XmlProc.loadFromXmlFile();    
  }

}
