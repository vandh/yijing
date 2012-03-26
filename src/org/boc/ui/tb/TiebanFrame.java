package org.boc.ui.tb;

import org.boc.delegate.DelTieBanMain;
import org.boc.ui.sz.SiZhuFrame;
import org.boc.util.Public;

public class TiebanFrame
    extends SiZhuFrame {
  private DelTieBanMain tb;
  private int isheng=0, ishi=0;
  public TiebanFrame() {
    super();
    //重新设置根目录 铁板是4
    tb = new DelTieBanMain();
    this.setRoot(Public.valueRoot[4]);
  }

  /**
   * 重载父方法，输出排盘信息
   */
  public String do1() {
    if (vo == null)return "";
    if (vo.getYear() == 0)
      return tb.getMingYun(ng,nz,yg,yz,rg,rz,sg,sz,isYun,isBoy);
    return tb.getMingYun(year,month,day,hour,minute,isYin, isYun, isheng, ishi, isBoy);
  }

  public String do2() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do3() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do4() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do5() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do6() {return "\r\n    开发中......，请等待下一版本完善！";}
  public String do7() {return "\r\n    开发中......，请等待下一版本完善！";}

}
