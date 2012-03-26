package org.boc.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.boc.ui.qm.AboutFrame;
import org.boc.util.Exec;
import org.boc.util.Helper;
import org.boc.util.Messages;
import org.boc.util.Public;

public class Main1
    extends JFrame {
  private static final int WIDTH = 650;
  private static final int HIGHT = 650;
  private static final int TOOLBARW = 18;
  private static final int TOOLBARH = 24;
  private static final int STATUSTIPW = 800;
  private static final int STATUSTIPH = 14;
  private static final int STATUSTIMEW = 100;
  private static final int STATUSTIMEH = 14;
  private static final String SYS_SIZHU = "四柱"; //主面板中的四柱TAP页
  public static final boolean zc = true; //false为无

  private Collection popups;  //当前的窗口
  private JToolBar jToolBar; //工具栏
  private JMenu fileMenu; //一级菜单文件
  private JMenu jMenuSansi; //三式
  private JMenu jMenuShenshu; //五大神数
  private JMenu jMenuSiliu; //四柱六爻
  private JMenu jMenuFengshui; //风水
  private JMenu jMenuQita; //其它
  private JMenu jMenuPreference; //一级菜单设置
  private JMenu jMenuOther; //一级菜单其它

  private CommandAction cmdOpen; //二级菜单备份
  private CommandAction cmdClose; //二级菜单恢复
  private CommandAction cmdQuit; //二级菜单退出

  private CommandAction cmdYijing; //二级菜单易经
  private CommandAction cmdSizhu; //二级菜单四柱
  private CommandAction cmdFengshuiBz; //二级菜单八宅派风水
  private CommandAction cmdFengshuiXk; //二级菜单玄空派风水
  private CommandAction cmdFengshuiSh; //二级菜单三合派风水
  private CommandAction cmdFengshuiSy; //二级菜单飞星派风水
  private CommandAction cmdQimen; //二级菜单奇门遁甲
  private CommandAction cmdLiuren; //二级菜单六壬术
  private CommandAction cmdTaiyi; //二级菜单太乙正宗
  private CommandAction cmdZiwei; //二级菜单紫微斗数
  private CommandAction cmdTieban; //二级菜单铁板神数
  private CommandAction cmdNanji; //二级菜单南极神数
  private CommandAction cmdBeiji; //二级菜单北极神数
  private CommandAction cmdMeihua; //二级菜单邵子神数
  private CommandAction cmdZhuge; //二级菜单诸葛神数
  private CommandAction cmdXingming; //二级菜单姓名预测
  private CommandAction cmdCezi; //二级菜单测字
  private CommandAction cmdChouqian; //二级菜单抽签
  private CommandAction cmdMianxiang; //二级菜单面相
  private CommandAction cmdShouxiang; //二级菜单手相
  private CommandAction cmdGuxiang; //二级菜单骨相
  private CommandAction cmdTuibei; //二级菜单推背图
  private CommandAction cmdShudou; //二级菜单数斗
  private CommandAction cmdJiemeng; //二级菜单周公解梦
  private CommandAction cmdZhengzhao; //二级菜单征兆
  private CommandAction cmdFofa; //二级菜单佛法咒语
  private CommandAction cmdXuexing; //二级菜单血型
  private CommandAction cmdXingzuo; //二级菜单星座
  private CommandAction cmdShuzi; //二级菜单数字
  private CommandAction cmdChenggu; //二级菜单称骨

  private CommandAction cmdHelp; //二级菜单帮助
  private CommandAction cmdAbout; //二级菜单关于

  private JMenuBar jMenuBar; //菜单栏
  private JPanel jContentPane; //背景大面板
  private javax.swing.Timer timer;
  private JPanel jPanelStatus; //状态栏
  private JLabel jLabelStatusTime; //状态栏中时间标签
  private JPanel jPanelStatusTime; //状态栏中时间容器
  private JPanel jPanelStatusTip; //状态栏提示容器
  private JLabel jLabelStatusTip; //状态栏提示标签
  private JLayeredPane jPanelMain; //主面板
  private JPanel siZhuPanel; //四柱面板
  private CardLayout layout;

  private void initialize() {
    popups = new ArrayList();
    //getSystemSetting().loadSetting();
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //设置缺省关闭按钮
    setSize();
    //0. 这里加入标题
    this.setTitle("中国古代预测学");
    //1. 设置母面板
    this.setContentPane(getJContentPane());
    //2. 设置菜单栏,不需要加入面板中
    this.setJMenuBar(getJMenuBars());
    //3. 设置工具栏，在最上面
    jContentPane.add(getJToolBar(), BorderLayout.NORTH);
    //4. 设置中间的主面板
    jContentPane.add(getJPanelMain(), BorderLayout.CENTER);
    //5. 设置最下面的状态栏面板，包括信息提示与时间显示
    jContentPane.add(getJPanelStatus(), BorderLayout.SOUTH);

    refreshStatus();
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if(!zc)
//        JOptionPane.showMessageDialog(getThis(),
//                                        Public.info,Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);
//        systemExit();
        	AboutFrame.show(0);
      }
    });

    timer = new javax.swing.Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doTimer();
      }
    });
    timer.start();
  }

  private class ChmThread extends Thread {
    private final String cmd ;
    ChmThread(String cmd) {
      this.cmd = cmd;
      start();
    }

    public void run() {
      try {
        sleep(100);
      }
      catch (InterruptedException ex) {
      }
      Exec.exec(cmd);
    }
  }
  public void openHelpIE() {
    try {
      new ChmThread("C:/Program Files/Internet Explorer/IEXPLORE.EXE blog.sina.com.cn/u/2479027277");
    } catch(Throwable t) {  }
  }

  public void openHelpChm() {
    String filename = Public.helpdoc;    
    String hhExe = null;
    String osName = null;
    try{
      osName = System.getProperty("os.name");
      if (osName.equals("Windows NT")) {
        hhExe = "C:/WINNT/system32/cmd.exe /c ";
      }else if(osName.equals("Windows 2000")) {
        hhExe = "C:/WINNT/system32/cmd.exe /c ";
      }else{
        hhExe = "C:/WINDOWS/system32/cmd.exe /c ";
      }
    }catch(Exception e) {
      Messages.error("Main1:openHelpChm() 189行：打开帮助文件的执行命令hh.exe在"+osName+"中没有找到");
    }

    try {
      //filename = Public.HOME+"help/"+Properties.helpdoc;
      //filename = getClass().getResource("/help/"+Properties.helpdoc).getPath();
      //filename = filename.substring(1);
      String cmd = hhExe+filename;
      //Messages.info(cmd);
      //Exec.exec(cmd);
      new ChmThread(cmd);
    } catch(Throwable t) {
      try{
        Exec.exec("C:/windows/hh.exe "+filename);
      }catch(Throwable t1) {
        Messages.error("打开帮助文件时执行命令[" + filename + "]失败！" + t1);
      }
    }
  }

  public static FileManagerFrame fManager;
  public void openFileManager() {
    if(fManager==null)
      fManager = new FileManagerFrame();
    fManager.setVisible(true);
  }

  	private static Helper parser ;
	private static JyjJTree tree ;
	private static DefaultMutableTreeNode rootNode;
	private static DefaultTreeModel model;
	public JyjJTree getMyTree() {
		return tree;
	}
	/**
	 * 新方法好用，10000次不出错
	 */
	public void OpenIntoTree(String name) {
		if(parser==null) parser = new Helper();
		if(tree==null) tree = TreePanel.getTree();  //得到这棵树，第一次只是空的，后期会从qm.xml和ty.xml中加载
		DefaultMutableTreeNode leadSelection = TreePanel.getLeadSelection();
		if (leadSelection == null) {//如果没有选择，则选择根节点<始皇字典></始皇字典>
			leadSelection = (DefaultMutableTreeNode) tree.getModel().getRoot();
			TreePanel.setLeadSelection(leadSelection);
		}
		if (rootNode == null) //将<始皇字典></始皇字典>赋值给rootNode
			rootNode = (DefaultMutableTreeNode) leadSelection.getRoot();
		//（name=qm)，得到qm.xml文件，第一次不存在，直接new一个"奇门”节点返回来
		model = parser.parse((String) Public.mapFile.get(name));  

		//预测技术是否已经装载到了树，如果没有装载
		if (!((Boolean) Public.mapKeyIsLoaded.get(name)).booleanValue()) {
			//将奇门模型树添加到始皇字典根节点中
			rootNode.add((DefaultMutableTreeNode) model.getRoot());
			tree.expandRow(0);
			//tree.expandPath(new TreePath(((DefaultTreeModel)tree.getModel()).getPathToRoot()));
			((DefaultTreeModel) tree.getModel()).reload(rootNode);
			//更新qm状态为已加载
			Public.mapKeyIsLoaded.remove(name);
			Public.mapKeyIsLoaded.put(name, new Boolean(true));
			//System.out.println("add " + name);
		} else {  //如果已经装载
			Enumeration e = rootNode.children();
			DefaultMutableTreeNode theNode = null;
			while (e.hasMoreElements()) {
				theNode = (DefaultMutableTreeNode) e.nextElement();
				if (theNode.toString().indexOf(Public.getRootKey(name)) != -1) {
					rootNode.remove(theNode);
					((DefaultTreeModel) tree.getModel()).reload(rootNode);
					Public.mapKeyIsLoaded.remove(name);
					Public.mapKeyIsLoaded.put(name, new Boolean(false));
					//System.out.println("remove " + name);
					break;
				}
			}
		}
	}

  //原方法注释了
  public void OpenIntoTree2(String name) {
    Helper parser = new Helper();
    JyjJTree tree = TreePanel.getTree();
    //可直接new 一个rootNdoe
    DefaultMutableTreeNode leadSelection = TreePanel.getLeadSelection();
    if(leadSelection==null){
      //JOptionPane.showMessageDialog(tree, "请选择列表的根节点！");
      //return;
      leadSelection = (DefaultMutableTreeNode)tree.getModel().getRoot();
      TreePanel.setLeadSelection(leadSelection);
    }
    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)leadSelection.getRoot();
    DefaultTreeModel model = parser.parse( (String) Public.mapFile.get(name));

    if (!((Boolean)Public.mapKeyIsLoaded.get(name)).booleanValue()) {
      rootNode.add( (DefaultMutableTreeNode) model.getRoot());
      ( (DefaultTreeModel) tree.getModel()).reload(rootNode);
      Public.mapKeyIsLoaded.put(name,new Boolean(true));
    }
    else {
      Enumeration e = rootNode.children();
      DefaultMutableTreeNode theNode = null;
      while(e.hasMoreElements()) {
        theNode = (DefaultMutableTreeNode)e.nextElement();
        if(theNode.toString().indexOf(Public.getRootKey(name))!=-1){
          rootNode.remove(theNode);
          ( (DefaultTreeModel) tree.getModel()).reload(rootNode);
          Public.mapKeyIsLoaded.put(name,new Boolean(false));
          break;
        }
      }
    }
  }

  public JInternalFrame getCurrentFrame() {
    for (Iterator it = popups.iterator(); it.hasNext(); ) {
      JInternalFrame currentFrame = (JInternalFrame) it.next();
      if (currentFrame.isSelected()) {
        return currentFrame;
      }
    }
    return null;
  }

  private void setSize() {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(WIDTH, HIGHT);
    int width = this.getWidth();
    int height = this.getHeight();

    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    this.setBounds(x, y, width, height);

  }

  public void refreshStatus() {
    this.getBackupAction().setEnabled(true);
    this.getRecoveryAction().setEnabled(true);
    this.setStatusText("成功启动！");
  }

  public void systemExit() {
  	if(Main.fout!=null)
  		Main.fout.close();
  	if(Main.ferr!=null)
  		Main.ferr.close();
  	
    //if(!Properties.zc)
//        JOptionPane.showMessageDialog(getThis(),
//                                       Public.info,Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);
  	AboutFrame.show(0);
    //System.exit(0);
  }

  public void doTimer() {
    java.util.Date now = new java.util.Date();
    int h = now.getHours();
    int mi = now.getMinutes();
    int ss = now.getSeconds();
    String d = " " +
        (now.getHours() < 10 ? "0" + h : "" + now.getHours());
    d += ":" +
        (now.getMinutes() < 10 ? "0" + mi : "" + now.getMinutes());
    d += ":" +
        (now.getSeconds() < 10 ? "0" + ss : "" + now.getSeconds());
    jLabelStatusTime.setText(d);

    if(mi%3==0 && ss==30)
      if(!zc)
//        JOptionPane.showMessageDialog(getThis(),
//                                        Public.info, Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);

    if(mi%15==0  && ss == 25)
      if(!zc)
        this.systemExit();
  }

  /**
   * 设置背景大面板
   * @return
   */
  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = (JPanel)this.getContentPane();
      jContentPane.setLayout(new BorderLayout());
    }
    return jContentPane;
  }

  /**
   * 工具栏，嵌入到大面板的北部
   * @return
   */
  public JToolBar getJToolBar() {
    if (jToolBar == null) {
      jToolBar = new JToolBar();
      jToolBar.setPreferredSize(new Dimension(TOOLBARW, TOOLBARH));
      jToolBar.add(this.getBackupAction());
      jToolBar.add(this.getRecoveryAction());
      jToolBar.addSeparator();
      //jToolBar.add(getTaiyiAction());
      jToolBar.add(getQimenAction());
      jToolBar.add(getLiurenAction());
      jToolBar.addSeparator();
      jToolBar.add(getZiweiAction());
      jToolBar.add(getTiebanAction());
      jToolBar.addSeparator();
      jToolBar.add(getSizhuAction());
      jToolBar.add(getYijingAction());
      jToolBar.addSeparator();
      jToolBar.add(getFengshuiBzAction());
      jToolBar.add(getFengshuiXkAction());
      //jToolBar.add(getFengshuiShAction());
      //jToolBar.add(getFengshuiSyAction());
      jToolBar.addSeparator();
      jToolBar.add(getXingmingAction());
      //jToolBar.add(getJiemengAction());
      jToolBar.add(getZhugeAction());
      jToolBar.add(getChengguAction());
      jToolBar.addSeparator();
      jToolBar.add(getAboutAction());
      jToolBar.add(getHelpAction());
      jToolBar.addSeparator();
      jToolBar.add(getQuitAction());

    }
    return jToolBar;
  }

  /**
   * 得到主面板
   * @return
   */
  private JLayeredPane getJPanelMain() {
    if (jPanelMain == null) {
      jPanelMain = new JDesktopPane();
    }
    return jPanelMain;
  }

  /**
   * 得到状态栏的面板，包括提示信息与时间信息
   * @return
   */
  private JPanel getJPanelStatus() {
    if (jPanelStatus == null) {
      jPanelStatus = new JPanel();
      //jPanelStatus.setLayout(new BorderLayout());
      jPanelStatus.setLayout(new BoxLayout(jPanelStatus, BoxLayout.X_AXIS));
      jPanelStatus.setPreferredSize(new Dimension(500, 18));
      jPanelStatus.add(getJPanelStatusTip(), null);
      jPanelStatus.add(getJPanelStatusTime(), null);
    }
    return jPanelStatus;
  }

  /**
   * 得到状态栏提示信息面板
   * @return
   */
  private JPanel getJPanelStatusTip() {
    if (jPanelStatusTip == null) {
      jLabelStatusTip = new JLabel();
      jPanelStatusTip = new JPanel();
      jPanelStatusTip.setLayout(new BorderLayout());
      jPanelStatusTip.setBorder(BorderFactory.createBevelBorder(BevelBorder.
          LOWERED));
      jPanelStatusTip.setPreferredSize(new Dimension(STATUSTIPW, STATUSTIPH));
      jLabelStatusTip.setText("");
      jLabelStatusTip.setFont(new Font("宋体", Font.PLAIN, 12));
      jPanelStatusTip.add(jLabelStatusTip, BorderLayout.NORTH);
    }
    return jPanelStatusTip;
  }

  /**
   * 得到状态栏的时间面板
   * @return
   */
  private JPanel getJPanelStatusTime() {
    if (jPanelStatusTime == null) {
      jLabelStatusTime = new JLabel();
      jPanelStatusTime = new JPanel();
      jPanelStatusTime.setLayout(new BorderLayout());
      jPanelStatusTime.setBorder(BorderFactory.createBevelBorder(BevelBorder.
          LOWERED));
      jPanelStatusTime.setPreferredSize(new Dimension(STATUSTIMEW, STATUSTIMEH));
      jLabelStatusTime.setText("");
      jPanelStatusTime.add(jLabelStatusTime, BorderLayout.NORTH);
    }
    return jPanelStatusTime;
  }

  /**
   * 得到四柱页面
   * @return
   */
  public JPanel getSiZhu() {
    if (siZhuPanel == null) {
      siZhuPanel = new JPanel();
    }
    return siZhuPanel;
  }

  /**
   * 菜单栏
   * @return
   */
  private JMenuBar getJMenuBars() {
    if (jMenuBar == null) {
      jMenuBar = new JMenuBar();
      jMenuBar.add(getJMenuFile());
      jMenuBar.add(getJMenuSansi());
      jMenuBar.add(getJMenuShenshu());
      jMenuBar.add(getJMenuSiliu());
      jMenuBar.add(getJMenuFengshui());
      jMenuBar.add(getJMenuQita());
      jMenuBar.add(getJMenuPreference());
      jMenuBar.add(getJMenuOther());
    }
    return jMenuBar;
  }

  /**
   * 文件菜单
   * @return
   */
  private JMenu getJMenuFile() {
    if (fileMenu == null) {
      fileMenu = new JMenu();
      fileMenu.setText("W文件");
      fileMenu.setFont(new Font("宋体", Font.PLAIN, 12));
      fileMenu.setMnemonic(KeyEvent.VK_S);
      fileMenu.add(this.getBackupAction());
      fileMenu.add(this.getRecoveryAction());
      fileMenu.addSeparator();
      fileMenu.add(getQuitAction());
    }
    return fileMenu;
  }

  /**
   * 预测方法菜单
   * @return
   */
  private JMenu getJMenuSansi() {
    if (jMenuSansi == null) {
      jMenuSansi = new JMenu();
      jMenuSansi.setText("S三式");
      jMenuSansi.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuSansi.setMnemonic(KeyEvent.VK_S);
      jMenuSansi.add(getQimenAction());
      jMenuSansi.add(getLiurenAction());
      jMenuSansi.add(getTaiyiAction());
    }
    return jMenuSansi;
  }

  private JMenu getJMenuShenshu() {
    if (jMenuShenshu == null) {
      jMenuShenshu = new JMenu();
      jMenuShenshu.setText("W神数");
      jMenuShenshu.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuShenshu.setMnemonic(KeyEvent.VK_S);
      jMenuShenshu.add(getZiweiAction());
      jMenuShenshu.add(getTiebanAction());
      jMenuShenshu.add(getNanjiAction());
      jMenuShenshu.add(getBeijiAction());
      jMenuShenshu.add(getMeihuaAction());
    }
    return jMenuShenshu;
  }

  private JMenu getJMenuFengshui() {
    if (jMenuFengshui == null) {
      jMenuFengshui = new JMenu();
      jMenuFengshui.setText("F风水");
      jMenuFengshui.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuFengshui.setMnemonic(KeyEvent.VK_S);
      jMenuFengshui.add(getFengshuiBzAction());
      jMenuFengshui.add(getFengshuiXkAction());
      jMenuFengshui.add(getFengshuiShAction());
      jMenuFengshui.add(getFengshuiSyAction());
    }
    return jMenuFengshui;
  }

  private JMenu getJMenuSiliu() {
    if (jMenuSiliu == null) {
      jMenuSiliu = new JMenu();
      jMenuSiliu.setText("L四六");
      jMenuSiliu.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuSiliu.setMnemonic(KeyEvent.VK_S);
      jMenuSiliu.add(getSizhuAction());
      jMenuSiliu.add(getYijingAction());
    }
    return jMenuSiliu;
  }

  private JMenu getJMenuQita() {
    if (jMenuQita == null) {
      jMenuQita = new JMenu();
      jMenuQita.setText("Z杂占");
      jMenuQita.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuQita.setMnemonic(KeyEvent.VK_S);
      jMenuQita.add(getXingmingAction());
      jMenuQita.add(getJiemengAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getZhugeAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getChengguAction());
      jMenuQita.add(getShuziAction());
      jMenuQita.add(getCeziAction());
      jMenuQita.add(getChouqianAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getMianxiangAction());
      jMenuQita.add(getShouxiangAction());
      jMenuQita.add(getGuxiangAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getXuexingAction());
      jMenuQita.add(getXingzuoAction());
      jMenuQita.addSeparator();
      jMenuQita.add(getTuibeiAction());
      jMenuQita.add(getShudouAction());
      jMenuQita.add(getZhengzhaoAction());
      jMenuQita.add(getFofaAction());
    }
    return jMenuQita;
  }


  /**
   * 得到首选项菜单
   * @return
   */
  private JMenu getJMenuPreference() {
    if (jMenuPreference == null) {
      jMenuPreference = new JMenu();
      jMenuPreference.setText("P设置");
      jMenuPreference.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuPreference.setMnemonic(KeyEvent.VK_S);
      jMenuPreference.addSeparator();
    }
    return jMenuPreference;
  }

  /**
   * 得到其它菜单
   * @return
   */
  private JMenu getJMenuOther() {
    if (jMenuOther == null) {
      jMenuOther = new JMenu();
      jMenuOther.setText("O其它");
      jMenuOther.setFont(new Font("宋体", Font.PLAIN, 12));
      jMenuOther.setMnemonic(KeyEvent.VK_S);
      jMenuOther.add(getHelpAction());
      jMenuOther.addSeparator();
      jMenuOther.add(getAboutAction());
    }
    return jMenuOther;
  }

  private CommandAction getRecoveryAction() {
    if (cmdOpen == null) {
      cmdOpen = new CommandAction("恢复",
                                  new ImageIcon(getClass().getResource("/images/open.gif")),
                                  "恢复原来备份的预测数据", 'O',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          openFileManager();
        }
      });
    }
    return cmdOpen;
  }

  private CommandAction getBackupAction() {
    if (cmdClose == null) {
      cmdClose = new CommandAction("备份", new ImageIcon(getClass().getResource("/images/close.gif")),
                                  "备份历史预测数据", 'C',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          openFileManager();
        }
      });
    }
    return cmdClose;
  }

  private CommandAction getQuitAction() {
    if (cmdQuit == null) {
      cmdQuit = new CommandAction("退出", new ImageIcon(getClass().getResource("/images/exit.gif")),
                                  "退出主程序", 'Q',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          systemExit();
        }
      });
    }
    return cmdQuit;
  }

  private CommandAction getHelpAction() {
    if (cmdHelp == null) {
      cmdHelp = new CommandAction("帮助", new ImageIcon(getClass().getResource("/images/help.gif")),
                                  "帮助文件", 'H',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //openHelpChm();
        	openHelpIE();
        }
      });
    }
    return cmdHelp;
  }

  private CommandAction getAboutAction() {
    if (cmdAbout == null) {
      cmdAbout = new CommandAction("关于", new ImageIcon(getClass().getResource("/images/about.gif")),
                                  "关于版本及作者", 'A',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
//          JOptionPane.showMessageDialog(getThis(),
//                                        Public.info,Public.infoTitle,
//                                        JOptionPane.INFORMATION_MESSAGE);
        	AboutFrame.show(1);
        }
      });
    }
    return cmdAbout;
  }

  protected Component getThis() {
    return this;
  }


  private CommandAction getYijingAction() {
    if (cmdYijing == null) {
      cmdYijing = new CommandAction("六爻", new ImageIcon(getClass().getResource("/images/m10.gif")),
                                  "六爻", 'B',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("ly");
        }
      });
    }
    return cmdYijing;
  }

  private CommandAction getSizhuAction() {
    if (cmdSizhu == null) {
      cmdSizhu = new CommandAction("四柱", new ImageIcon(getClass().getResource("/images/m9.gif")),
                                  "四柱", 'D',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("sz");
        }
      });
    }
    return cmdSizhu;
  }

  private CommandAction getFengshuiBzAction() {
    if (cmdFengshuiBz == null) {
      cmdFengshuiBz = new CommandAction("八宅明镜", new ImageIcon(getClass().getResource("/images/m11.gif")),
                                  "八宅派风水", 'E',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("bz");
        }
      });
    }
    return cmdFengshuiBz;
  }

  private CommandAction getFengshuiXkAction() {
    if (cmdFengshuiXk == null) {
      cmdFengshuiXk = new CommandAction("玄空飞星", new ImageIcon(getClass().getResource("/images/m12.gif")),
                                  "玄空飞星派风水", 'E',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("xk");
        }
      });
    }
    return cmdFengshuiXk;
  }

  private CommandAction getFengshuiShAction() {
    if (cmdFengshuiSh == null) {
      cmdFengshuiSh = new CommandAction("三合派", new ImageIcon(getClass().getResource("/images/m13.gif")),
                                  "三合派风水", 'E',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("sh");
        }
      });
    }
    return cmdFengshuiSh;
  }

  private CommandAction getFengshuiSyAction() {
      if (cmdFengshuiSy == null) {
        cmdFengshuiSy = new CommandAction("三元派", new ImageIcon(getClass().getResource("/images/m14.gif")),
                                    "三元派风水", 'E',
                                    new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            OpenIntoTree("sy");
          }
        });
      }
      return cmdFengshuiSy;
    }

  CommandAction getQimenAction() {
    if (cmdQimen == null) {
      cmdQimen = new CommandAction("奇门", new ImageIcon(getClass().getResource("/images/m1.gif")),
                                  "奇门遁甲", 'F',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("qm");
        }
      });
    }
    return cmdQimen;
  }

  private CommandAction getLiurenAction() {
    if (cmdLiuren == null) {
      cmdLiuren = new CommandAction("六壬", new ImageIcon(getClass().getResource("/images/m2.gif")),
                                  "六壬", 'G',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("lr");
        }
      });
    }
    return cmdLiuren;
  }

  private CommandAction getTaiyiAction() {
    if (cmdTaiyi == null) {
      cmdTaiyi = new CommandAction("太乙", new ImageIcon(getClass().getResource("/images/m3.gif")),
                                  "太乙正宗", 'I',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("ty");
        }
      });
    }
    return cmdTaiyi;
  }

  private CommandAction getZiweiAction() {
    if (cmdZiwei == null) {
      cmdZiwei = new CommandAction("紫微斗数", new ImageIcon(getClass().getResource("/images/m4.gif")),
                                  "紫微斗数", 'J',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("zw");
        }
      });
    }
    return cmdZiwei;
  }

  private CommandAction getTiebanAction() {
    if (cmdTieban == null) {
      cmdTieban = new CommandAction("铁板神数", new ImageIcon(getClass().getResource("/images/m5.gif")),
                                  "铁板神数", 'K',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("tb");
        }
      });
    }
    return cmdTieban;
  }

  private CommandAction getNanjiAction() {
    if (cmdNanji == null) {
      cmdNanji = new CommandAction("南极神数", new ImageIcon(getClass().getResource("/images/m6.gif")),
                                  "南极神数", 'K',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("nj");
        }
      });
    }
    return cmdNanji;
  }

  private CommandAction getBeijiAction() {
    if (cmdBeiji == null) {
      cmdBeiji = new CommandAction("北极神数", new ImageIcon(getClass().getResource("/images/m7.gif")),
                                  "北极神数", 'K',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("bj");
        }
      });
    }
    return cmdBeiji;
  }

  private CommandAction getMeihuaAction() {
    if (cmdMeihua == null) {
      cmdMeihua = new CommandAction("邵子神数", new ImageIcon(getClass().getResource("/images/m8.gif")),
                                  "邵子神数", 'S',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //OpenIntoTree("sz");
        }
      });
    }
    return cmdMeihua;
  }

  private CommandAction getZhugeAction() {
    if (cmdZhuge == null) {
      cmdZhuge = new CommandAction("鬼谷分定", new ImageIcon(getClass().getResource("/images/o3.gif")),
                                  "鬼谷子分定经", 'L',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("gg");
        }
      });
    }
    return cmdZhuge;
  }

  private CommandAction getXingmingAction() {
    if (cmdXingming == null) {
      cmdXingming = new CommandAction("姓名预测", new ImageIcon(getClass().getResource("/images/o1.gif")),
                                  "姓名预测", 'M',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("xm");
        }
      });
    }
    return cmdXingming;
  }

  private CommandAction getCeziAction() {
    if (cmdCezi == null) {
      cmdCezi = new CommandAction("测字", new ImageIcon(getClass().getResource("/images/o6.gif")),
                                  "测字", 'N',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdCezi;
  }

  private CommandAction getChouqianAction() {
    if (cmdChouqian == null) {
      cmdChouqian = new CommandAction("抽签", new ImageIcon(getClass().getResource("/images/o7.gif")),
                                  "抽签", 'P',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdChouqian;
  }

  private CommandAction getMianxiangAction() {
    if (cmdMianxiang == null) {
      cmdMianxiang = new CommandAction("面相", new ImageIcon(getClass().getResource("/images/o8.gif")),
                                  "面相", 'R',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdMianxiang;
  }

  private CommandAction getShouxiangAction() {
    if (cmdShouxiang == null) {
      cmdShouxiang = new CommandAction("手相", new ImageIcon(getClass().getResource("/images/o9.gif")),
                                  "手相", 'S',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdShouxiang;
  }

  private CommandAction getGuxiangAction() {
    if (cmdGuxiang == null) {
      cmdGuxiang = new CommandAction("骨相", new ImageIcon(getClass().getResource("/images/o10.gif")),
                                  "骨相", 'T',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdGuxiang;
  }

  private CommandAction getTuibeiAction() {
    if (cmdTuibei == null) {
      cmdTuibei = new CommandAction("推背图", new ImageIcon(getClass().getResource("/images/o13.gif")),
                                  "推背图", 'U',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdTuibei;
  }

  private CommandAction getShudouAction() {
    if (cmdShudou == null) {
      cmdShudou = new CommandAction("数斗", new ImageIcon(getClass().getResource("/images/o14.gif")),
                                  "数斗", 'V',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdShudou;
  }

  private CommandAction getJiemengAction() {
    if (cmdJiemeng == null) {
      cmdJiemeng = new CommandAction("周公解梦", new ImageIcon(getClass().getResource("/images/o2.gif")),
                                  "周公解梦", 'W',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdJiemeng;
  }

  private CommandAction getZhengzhaoAction() {
    if (cmdZhengzhao == null) {
      cmdZhengzhao = new CommandAction("征兆", new ImageIcon(getClass().getResource("/images/o15.gif")),
                                  "征兆", 'X',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdZhengzhao;
  }

  private CommandAction getFofaAction() {
    if (cmdFofa == null) {
      cmdFofa = new CommandAction("佛法咒语", new ImageIcon(getClass().getResource("/images/o16.gif")),
                                  "佛法咒语", 'Y',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdFofa;
  }

  private CommandAction getXuexingAction() {
    if (cmdXuexing == null) {
      cmdXuexing = new CommandAction("血型", new ImageIcon(getClass().getResource("/images/o11.gif")),
                                  "血型", 'Z',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdXuexing;
  }

  private CommandAction getXingzuoAction() {
    if (cmdXingzuo == null) {
      cmdXingzuo = new CommandAction("星座", new ImageIcon(getClass().getResource("/images/o12.gif")),
                                  "星座", '1',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdXingzuo;
  }

  private CommandAction getShuziAction() {
    if (cmdShuzi == null) {
      cmdShuzi = new CommandAction("数字", new ImageIcon(getClass().getResource("/images/o5.gif")),
                                  "数字", '2',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //refreshStatus();
        }
      });
    }
    return cmdShuzi;
  }

  private CommandAction getChengguAction() {
    if (cmdChenggu == null) {
      cmdChenggu = new CommandAction("称骨", new ImageIcon(getClass().getResource("/images/o4.gif")),
                                  "袁天罡称骨", '3',
                                  new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          OpenIntoTree("cg");
        }
      });
    }
    return cmdChenggu;
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

  public void setStatusText(String text) {
    jLabelStatusTip.setText(" " + text);
  }

//  public void showSplash() {
//    SplashWindow splash = null;
//    splash = new SplashWindow(500);
//    //int rand = (int)(Math.random()*10);
//    //if(rand >5) rand = rand/2;
//    //splash.showSplash("/images/logo"+rand+".gif");
//    splash.showSplash("/images/logo.gif");
//  }

  public Main1() {
    super();
    initialize();
  }

  public static void main(String[] args) {
    initGlobalFontSetting(new java.awt.Font("宋体", java.awt.Font.PLAIN, 12));
    Main1 application = new Main1();
    application.setStatusText("启动中....");
    //application.showSplash();
    application.show();
    application.setStatusText(Public.status);

    //int rand = (int)(Math.random()*10);
    //if(rand >5) rand = rand/2;
    //System.err.println(rand);
  }

}
