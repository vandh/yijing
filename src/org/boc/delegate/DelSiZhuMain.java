package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.sz.DaoSiZhuHunYin;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.dao.sz.DaoSiZhuWangShuai;
import org.boc.dao.sz.DaoSiZhuXingGe;
import org.boc.dao.sz.DaoSiZhuYongShen;
import org.boc.db.Calendar;
import org.boc.db.ChengGu;
import org.boc.db.GuiGu;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Messages;

public class DelSiZhuMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoYiJingMain daoY;
  private DaoCalendar daoC;
  private DaoSiZhuMain dao;
  private DaoSiZhuWangShuai dao2;
  private DaoSiZhuYongShen daoys;
  private DaoSiZhuXingGe daox;
  private DaoSiZhuHunYin daoh;

  public DelSiZhuMain() {
    dao = new DaoSiZhuMain();
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    daoY = new DaoYiJingMain();
    daoC = new DaoCalendar();
    dao2 = new DaoSiZhuWangShuai();
    daoys = new DaoSiZhuYongShen();
    daox = new DaoSiZhuXingGe();
    daoh = new DaoSiZhuHunYin();
  }

  public String getMingYunForHtml(
      int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    String s = "";
    s += getMingYun(y,m,d,h,mi,type,yun,sheng,shi,isMan);
    s += getGlobleInfo(y, m, d, h,mi, type, yun, sheng ,shi, isMan);
    s += getXingGeInfo(y, m, d, h,mi, type, yun, sheng ,shi, isMan);
    s += getHunYinInfo(y, m, d, h,mi, type, yun, sheng ,shi, isMan);

    return s;
  }

  public String getMingYunForHtml(
      int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, boolean isMan) {
    String s = "";
    s += getMingYun(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);
    s += getGlobleInfo(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);
    s += getXingGeInfo(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);
    s += getHunYinInfo(ng,nz,yg,yz,rg,rz,sg,sz,yun,isMan);

    return s;
  }


  /**
   * 输出命运
   * @param y 年
   * @param m 月
   * @param d 日
   * @param h 时
   * @param mi 分
   * @param type 是阴历还阳历时间，true为阴历
   * @param yun  是闰月还是非闰月，true为闰月
   * @param isMan 是否男女
   * @param str
   */
  public String getMingYun(
      int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    try{
      str.delete(0,str.length());
      //输出时间信息
      dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
      printOut();
    }catch(Exception ex) {
      Messages.error("DelSiZhuMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * 由八字推算命运
   */
  public String getMingYun(
      int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, boolean isMan) {
    try{
      str.delete(0,str.length());
      //输出时间信息
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      printOut();
    }catch(Exception ex) {
      Messages.error("DelSiZhuMain("+ex+")");
    }

    return str.toString();
  }

  /**
   * 由时间推算其格局信息
   * @return
   */
  public String getGlobleInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getGeJuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字推算其格局信息
   * @return
   */
  public String getGlobleInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                              boolean yun, boolean isMan) {
    str.delete(0,str.length());
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
    getGeJuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由时间推算其性格身高等内在外在的信息
   * @return
   */
  public String getXingGeInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //输出时间信息
    str.append("\r\n");
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getXingGeOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字推算其性格身高等内在外在的信息
   * @return
   */
  public String getXingGeInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //输出时间信息
      str.append("\r\n");
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getXingGeOut();

      //Debug.out(str.toString());
      return str.toString();
  }

  /**
   * 由时间推算其婚姻的基本信息
   * @return
   */
  public String getHunYinInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //输出时间信息
    str.append("\r\n");
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getHunYinOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字推算其婚姻的基本信息
   * @return
   */
  public String getHunYinInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //输出时间信息
      str.append("\r\n");
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getHunYinOut();

      //Debug.out(str.toString());
      return str.toString();
    }

  /**
   * 由时间推算称骨
   * @return
   */
  public String getChengGuInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getChengGuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字推算推算称骨
   * @return
   */
  public String getChengGuInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //输出时间信息
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getChengGuOut();

      //Debug.out(str.toString());
      return str.toString();
    }

    /**
   * 由时间推算称骨
   * @return
   */
  public String getGuiGuInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
    str.delete(0,str.length());
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi, str);
    getGuiGuOut();

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字推算推算称骨
   * @return
   */
  public String getGuiGuInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, boolean isMan) {
      str.delete(0,str.length());
      //输出时间信息
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
      getGuiGuOut();

      //Debug.out(str.toString());
      return str.toString();
    }


  /**
   * 打印出所有的基本信息如大运，小运，流年等
   * @return
   * @throws java.lang.Exception
   */
  private void printOut() throws Exception{
    //输四柱与命宫胎元
    dao.getSiZhuTaiMingOut(str);
    //排大运
    dao.getDaYunOut(str);
    //年命纳音、胎元、命宫小运流年成七柱
    dao.getQiZhuOut(str);

    //Debug.out(str.toString());
    //return str.toString();
  }

  /**
   * 基本格局论断
   ($日干|壬)($日干五行|水)生于($节气|立春)($天数|二十六)日，正当($司令干$司令五行|甲木)真神司令。
   ($日干五行|金)生($四季|春)月，($月令简单描述|秋水通源)
   (%取格)。柱中（$五行数量|1木4水）
   for($干透神|食或伤)透干，($是否通根|地支丑戌通根),($天覆地载)，($是否合化),($进气退气),($强寡),
      ($是否坐禄刃长生), ($是否冲), ($是否得生), ($是否受克), ($是否旺衰)
   for($支藏神|食或伤)深藏，($是否合化),($进气退气),($强寡),($是否会或局),
      ($是否坐禄), ($是否坐刃), ($是否冲), ($是否得生), ($是否受克), ($是否旺衰)
   ($日干),($是否通根|地支丑戌通根),($天覆地载)，($是否合化),($进气退气),($强寡),
      ($是否坐禄刃长生), ($是否冲), ($是否得生), ($是否受克), ($是否旺衰)
   ($是否中和偏枯)，($源流),($通关),($清浊),($震兑坎离)
   ($由强弱模式取用神|身轻杀旺，取比劫为用)
   ($由旺衰似何取用)
   ($用神真假),($用神之病),($喜神之病),($忌神之病),($闲神)
   ($应行何大运),($各阶段喜忌)，($配诗)
   */
  private void getGeJuOut() {
    int jiehourishu = daoC.getDiffDatesForSiLing();
    int silinggan = Calendar.SILING[SiZhu.yz][jiehourishu];

    str.append("\n");
    //1. 简单描述
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("日干"+YiJing.TIANGANNAME[SiZhu.rg]+YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.rg]]);
    str.append("生于"+Calendar.JIEQINAME[SiZhu.yz]+"后"+jiehourishu+"日，");
    str.append("正当"+YiJing.TIANGANNAME[silinggan]+YiJing.WUXINGNAME[YiJing.TIANGANWH[silinggan]]+"真神司令。");
    str.append("正所谓"+YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.rg]]+"生"+Calendar.SIJI[SiZhu.yz]+","+dao2.sijiDesc());
    //2. 五行打分
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("究其旺衰：");
    str.append(dao2.getWuXingCent());
    //3. 格局
    str.append("\r\n");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("推其格局：");
    str.append(dao2.getGeJu());
    //4.用神
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("用神参考：");
    str.append(daoys.analyseYongShen());
    //5.运程
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("运程喜忌：");
    str.append(daoys.analyseYunCheng());
    //5.个例赏析
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("个例赏析：");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("原局："+YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+
               " "+YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+
               " "+YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+
               " "+YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+
               "    木："+SiZhu.muCent +
               "  火："+SiZhu.huoCent +
               "  土："+SiZhu.tuCent +
               "  金："+SiZhu.jinCent +
               "  水："+SiZhu.shuiCent );
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoys.analyseSameBaZi());

    //Debug.out(str.toString());
    //return str.toString();
  }

  /**
   * 输出性格身高等信息
   */
  private void getXingGeOut() {
    //身高
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("身高参考：");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daox.getXingGeOut());

  }

  /**
   * 输出婚姻的相关信息
   */
  private void getHunYinOut() {
    //身高
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("婚期参考：");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoh.getSiZhuHunYin());
  }

  /**
   * 输出称骨的相关信息
   */
  private void getChengGuOut() {
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    ChengGu cg = new ChengGu();
    int izhong = cg.yz[SiZhu.ng][SiZhu.nz]+cg.mz[Calendar.MONTHN1]+cg.dz[Calendar.DAYN1]+cg.hz[SiZhu.sz];
    int jin = izhong/10;
    int liang = izhong%10;
    String zhong = "骨重为："+jin+"两"+liang+"钱。";
    str.append("袁天罡称骨：");
    str.append(zhong);
    str.append("\r\n");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(cg.desc[izhong]);
  }

  /**
   * 输出鬼谷分定经
   */
  private void getGuiGuOut() {
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    GuiGu gg = new GuiGu();
    str.append("四柱："+YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+"      "
               +YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+"      "
               +YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+"      "
               +YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"      ");
    str.append("\r\n");
    str.append("\r\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(gg.jing[SiZhu.ng][SiZhu.sg]);
  }




  public static void main(String[] args) {
    DelSiZhuMain del = new DelSiZhuMain();
    System.out.println(del.getMingYunForHtml(1977,5,10,6,30,false,true,0,6,true)); //阴阳 闰 男女
    //System.out.println(del.getMingYunForHtml(1,1,1,1,1,1,1,1,true,true)); //阴阳 闰 男女
  }
}
