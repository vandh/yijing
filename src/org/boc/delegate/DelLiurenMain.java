package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.DaoLiuren;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.sz.DaoSiZhuMain;

public class DelLiurenMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoSiZhuMain dao;
  private DaoYiJingMain daoY;
  private DaoLiuren daolr;

  public DelLiurenMain() {
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    dao = new DaoSiZhuMain();
    daolr = new DaoLiuren();
    daoY = new DaoYiJingMain();
  }

  /**
   * 由时间起课
   */
  public String getInfo(int born, int y, int m, int d, int h,int mi,int ss,
                        boolean isYin,boolean isBoy, boolean isYun,
                        int iGr, int iZy, int iDg, int iYj) {
    str.delete(0,str.length());
    str.append("\r\n");
    //输出头部信息
    str.append(daolr.getHead(born, y, m, d, h, mi, ss,
                  isYin, isBoy, isYun, iGr,iZy,iDg,iYj));
    str.append("\r\n");
    str.append(daolr.getBody());

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字起课
   * @return
   */
  public String getInfo(int bg, int bz, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                        boolean isYin,boolean isBoy, boolean isYun,
                        int iGr, int iZy, int iDg, int iYj) {
      str.delete(0,str.length());
      str.append("\r\n");
      //输出头部信息
      str.append(daolr.getHead(bg, bz, ng,nz,yg,yz,rg,rz,sg,sz,
                               isYin, isBoy, isYun, iGr,iZy,iDg,iYj));
      str.append("\r\n");
      str.append(daolr.getBody());

      //Debug.out(str.toString());
      return str.toString();
  }

  /**
   * 输出排盘信息
   */
  private void getOut(StringBuffer str) {

  }


  public static void main(String[] args) {
    DelLiurenMain lr = new DelLiurenMain();
    //lr.getInfo(1977,2005,12,10,9,54,30,false,true,true,0,0,0);
    //lr.getInfo(4,6,2,10,2,10,4,2,2,8,false,true,false,0,0,0,3); //反呤格
    //lr.getInfo(4,6,2,10,2,10,7,1,2,11,false,true,false,0,0,0,10);   //涉害格
    //lr.getInfo(4,6,2,10,2,10,2,10,2,10,false,true,false,1,1,1,1);
    lr.getInfo(1977,2005,12,23,22,54,30,false,true,false,0,0,0,1);
  }
}
