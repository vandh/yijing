package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.YiJing;
import org.boc.db.qm.QimenCaiyun;
import org.boc.db.qm.QimenGeju1;
import org.boc.db.qm.QimenGongzuo;
import org.boc.db.qm.QimenHunyin;
import org.boc.db.qm.QimenJibing;
import org.boc.db.qm.QimenMingyun;
import org.boc.db.qm.QimenPublic;
import org.boc.db.qm.QimenXuexi;
import org.boc.ui.ResultPanel;

public class DelQiMenMain {
  private StringBuffer str;
  private PrintWriter pw;
  
  private DaoSiZhuMain dao;
  private DaoYiJingMain daoY;
  private DaoQiMen daoq;
  
  private QimenPublic pub;
  private QimenMingyun minyun;
  private QimenCaiyun caiyun;
  private QimenHunyin hunyin;
  private QimenJibing jibing;
  private QimenXuexi xuexi;
  private QimenGeju1 buju;  
  private QimenGongzuo work;
  
  private int jigong ;  //设置中五宫寄何宫 ,由QiMenFrame中赋值,qm.setJigong(iJigong); 

  public DelQiMenMain() {
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    dao = new DaoSiZhuMain();
    daoq = new DaoQiMen();
    daoq.setJigong(jigong);
    daoY = new DaoYiJingMain();
    
    pub = new QimenPublic(daoq,dao);
    minyun = new QimenMingyun(pub);
    caiyun = new QimenCaiyun(daoq, pub);
    hunyin = new QimenHunyin(daoq, pub);
    jibing = new QimenJibing(pub);
    xuexi = new QimenXuexi(daoq, pub,dao);
    buju = new QimenGeju1(daoq, pub);
    work = new QimenGongzuo(daoq, pub);
  }
  
  public DaoQiMen getDaoQiMen() {
  	return this.daoq;
  }
  public QimenPublic getQimenPublic() {
  	return this.pub;
  }
  public DaoSiZhuMain getDaoSiZhuMain() {
  	return this.dao;
  }
  
  /**
   * 得到工作就业的信息，由时间排盘
   */
  public String getWork(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
    //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //输出工作就业信息
    work.getWork(str, mzText, ysNum, isMan);
  	
  	return str.toString();
  }
  
  /**
   * 得到工作就业信息，由干支排盘
   * @return
   */
  public String getWork(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
  //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //输出工作就业信息
  	work.getWork(str, mzText, ysNum, isMan);
  	
  	
  	return str.toString();
  }
  
  /**
   * 得到学习考试的信息，由时间排盘
   * @param mzText : 为命主信息，形式如1997;1998;或者1,1;2,2;
   * @param ysNum: 用神序号，年干1，月干2，日干3，时干4
   */
  public String getXuexi(String mzText, int ysNum, int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
    //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //输出学习考试信息
    xuexi.getXuexi(str,mzText, ysNum);
  	
  	return str.toString();
  }
  
  /**
   * 得到学习考试信息，由干支排盘
   * @param mzText : 为命主信息，形式如1997;1998;或者1,1;2,2;
   * @param ysNum: 用神序号，年干1，月干2，日干3，时干4
   * @return
   */
  public String getXuexi(String mzText, int ysNum, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
  //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //输出学习考试信息
  	xuexi.getXuexi(str,mzText, ysNum);
  	
  	
  	return str.toString();
  }
  
  /**
   * 得到格局1的信息
   * iZfs： 小值符，1为天盘，2为地盘
   */
  public String getGeju1(ResultPanel rp, int y, int m, int d, int h,int mi,
      int ysnum, boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju, String mzText) {
  	str.delete(0,str.length());
  	pub.setBoy(isMan);
  	pub.setZf(iszf==1);  //转盘为1为true
  	pub.setTd(iZfs==1);  //小值符使，默认随天盘1为true
  	pub.setCy(iscy==2);  //置闰或拆补，默认拆补2为true
  	pub.setJigong(jigong);  	
  	pub.setYongshen(ysnum);
  	pub.setNianming(mzText);
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
    //输出干净的局
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");

    //输出命运流年信息，此处用，是因为5宫比较特殊，要这个参数
    buju.getGeju(rp, iszf, str);
  	
  	return null;
  }
  
  /**
   * 得到格局信息，由干支排盘
   * @return
   */
  public String getGeju1(ResultPanel rp, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
  		int ysnum, boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju, String mzText) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
  //输出干净的局
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");
    
  //输出命运流年信息
  	buju.getGeju(rp, iszf, str);
  	
  	
  	//return str.toString();
  	return null;
  }
  
//  /**
//   * 得到疾病的信息，由时间排盘
//   */
//  public String getJibing(int y, int m, int d, int h,int mi,
//      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
//      int iszf, int iscy,  int iZfs,int ju) {
//  	str.delete(0,str.length());
//  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
//  	
//    //输出时间信息
//    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
//    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
//    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
//    str.append("\r\n");
//    str.append("\r\n");
//    
//    //输出干净的局
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");
//
//    //输出命运信息
//    jibing.getBing(str,mzText,ysnum,isMan);
//  	
//  	return str.toString();
//  }
//  
//  /**
//   * 得到疾病信息，由干支排盘
//   * @return
//   */
//  public String getJibing(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
//      boolean yun, int sheng ,int shi,boolean isMan,
//      int iszf, int iscy, int iZfs,int ju) {
//  	str.delete(0,str.length());
//  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
//  	
//    //输出时间信息
//    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
//    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
//    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
//    str.append("\r\n");
//    str.append("\r\n");
//    
//  //输出干净的局
//    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
//    str.append("\r\n");
//    str.append("\r\n");
//    
//  //输出命运信息
//  	jibing.getJibing(str);
//  	
//  	
//  	return str.toString();
//  }
  
  /**
   * 得到财运的信息，由时间排盘
   */
  public String getCaiyun(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
    //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //输出财运信息
    caiyun.getCaiyun(str, mzText, ysNum, isMan, iszf);
  	
  	return str.toString();
  }
  /**
   * 得到财运信息，由干支排盘
   * @return
   */
  public String getCaiyun(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
  //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //输出命运信息
  	caiyun.getCaiyun(str, mzText, ysNum, isMan, iszf);
  	
  	
  	return str.toString();
  }
  
  /**
   * 得到婚姻的信息，由时间排盘
   */
  public String getHunyin(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
    //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //输出命运信息
    hunyin.getHunyin(str, mzText, ysNum, isMan);
  	
  	return str.toString();
  }
  /**
   * 得到婚姻信息，由干支排盘
   * @return
   */
  public String getHunyin(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
  //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //输出命运信息
  	hunyin.getHunyin(str, mzText, ysNum, isMan);
  	
  	
  	return str.toString();
  }
  
  /**
   * 得到命运的信息，由时间排盘
   */
  public String getMingYun(String mzText, int ysNum,int y, int m, int d, int h,int mi,
      boolean type, boolean yun, int sheng ,int shi, boolean isMan,
      int iszf, int iscy,  int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead1(iszf, iscy, iZfs, ju));  //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
    //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");

    //输出命运信息
  	minyun.getLife(str, mzText, ysNum, isMan, y);
  	
  	return str.toString();
  }
  /**
   * 得到命运信息，由干支排盘
   * @return
   */
  public String getMingYun(String mzText, int ysNum,int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
      boolean yun, int sheng ,int shi,boolean isMan,
      int iszf, int iscy, int iZfs,int ju) {
  	str.delete(0,str.length());
  	daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
  	
    //输出时间信息
    dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(daoq.getHead2(iszf, iscy, iZfs, ju)); //输出时间头部
    str.append("\r\n");
    str.append("\r\n");
    
  //输出干净的局
    str.append(daoq.getBody0(iszf, iscy, iZfs, ju));
    str.append("\r\n");
    str.append("\r\n");
    
  //输出命运信息
  	minyun.getLife(str, mzText, ysNum, isMan);
  	
  	
  	return str.toString();
  }

  /**
   * 由时间排盘
   * @param type 是阴历还阳历时间，true为阴历
   * @param yun  是闰月还是非闰月，true为闰月
   * @param isMan 是否男女
   * @param iszf 转盘奇门还是飞盘奇门　
   * @param iscy 超接置闰法还是拆补无闰法
   * @param ju 直接输入阳遁阴遁几局
   * @return
   */
  public String getQiMenInfo(int y, int m, int d, int h,int mi,
                              boolean type, boolean yun, int sheng ,int shi, boolean isMan,
                              int iszf, int iscy,  int iZfs,int ju) {
    str.delete(0,str.length());
    daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
    
    //输出时间信息
    dao.getDateOut(y, m, d, h, mi, type, yun, isMan, sheng, shi);
    getQiMenOut(iszf,iscy, iZfs,ju,str,true);

    //Debug.out(str.toString());
    return str.toString();
  }

  /**
   * 由八字排盘
   * @return
   */
  public String getQiMenInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                                boolean yun, int sheng ,int shi,boolean isMan,
                                int iszf, int iscy, int iZfs,int ju) {
      str.delete(0,str.length());
      daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
      
      //输出时间信息
      dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan);
      getQiMenOut(iszf,iscy, iZfs,ju,str,false);

      //Debug.out(str.toString());
      return str.toString();
  }

  /**
   * 输出排盘信息
   */
  private void getQiMenOut(int iszf, int iscy,  int iZfs,int ju,
                           StringBuffer str, boolean b) {
    str.append("\r\n");
    daoq.setJigong(jigong);  //必须设置，因初始化只一次，一旦更改了不会生效
    
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    if(b) {
      str.append(daoq.getHead1(iszf, iscy, iZfs, ju));
      str.append("\r\n");
      str.append(daoq.getBody1(iszf, iscy, iZfs, ju));
    }else{
      str.append(daoq.getHead2(iszf, iscy, iZfs, ju));
      str.append("\r\n");
      str.append(daoq.getBody1(iszf, iscy, iZfs, ju));
    }

  }
  
  //设置中五宫寄何宫
  public void setJigong(int jigong) {
  	this.jigong = jigong;
  }
  public int getJigong() {
  	return this.jigong;
  }

  public static void main(String[] args) {
    DelQiMenMain qm = new DelQiMenMain();
    //qm.getQiMenInfo(1977,3,23,6,34,true,true,-1,-1,true,1,1,0);
    //qm.getQiMenInfo(1934,1,23,6,34,false,true,-1,-1,true,1,1,0);
    //qm.getQiMenInfo(2005,11,2,20,18,false,true,-1,-1,true,1,1,1,0);
    //qm.getQiMenInfo(1933,5,23,12,18,true,true,-1,-1,true,1,1,0);
    //qm.getQiMenInfo(2005,11,17,11,28,false,true,-1,-1,true,1,2,1,0);
    qm.getQiMenInfo(2004,2,1,11,28,false,true,-1,-1,true,1,2,1,0);
    //qm.getQiMenInfo(1,1,3,3,6,6,2,2,true,-1,-1,true,1,2,1,5);
  }
}
