package org.boc.ui.gg;

import org.boc.delegate.DelSiZhuMain;
import org.boc.ui.sz.SiZhuFrame;
import org.boc.util.Public;

public class GuiGuFrame
    extends SiZhuFrame {
  private DelSiZhuMain dels ;
  private int isheng=0, ishi=0;
  public GuiGuFrame() {
  	
    super();
    //重新设置根目录
    dels = new DelSiZhuMain();
    this.setRoot(Public.valueRoot[11]);
  }

  /**
   * 重载父方法，输出排盘信息
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return dels.getGuiGuInfo(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return dels.getGuiGuInfo(year,month,day,hour,minute,isYin,isYun,isheng,ishi,isBoy);
  }

  public String do2() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do3() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do4() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do5() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do6() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do7() {return "\r\n    开发中......，请等待下一版本完善！";}

}
