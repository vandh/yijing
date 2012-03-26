package org.boc.util;

public class VoQiMen extends VO implements java.io.Serializable{
  private int iZf;
  private int iZy;
  private int iZfs;
  private int yydunNum;
  private String mingzhu;  //ÃüÖ÷
  private int yongshen;    //ÓÃÉñ

  public VoQiMen() {
  }

  public VoQiMen(String mingzhu, int yongshen, int year, int month, int day, int hour, int minute,
                 boolean isBoy, boolean isYin, boolean isYun,
                 int isheng, int ishi,int iZf,int iZy,int iZfs, int yydunNum,
                 String memo) {
  	this.setMingzhu(mingzhu);
  	this.setYongshen(yongshen);
  	
    this.setYear(year);
    this.setMonth(month);
    this.setDay(day);
    this.setHour(hour);
    this.setMinute(minute);
    this.setIsYin(isYin);
    this.setIsBoy(isBoy);
    this.setIsYun(isYun);
    this.setIsheng(isheng);
    this.setIshi(ishi);
    this.iZf = iZf;
    this.iZy = iZy;
    this.iZfs = iZfs;
    this.yydunNum = yydunNum;
    this.setMemo(memo);
  }

  public VoQiMen(String mingzhu, int yongshen, int ng, int nz, int yg, int yz, int rg, int rz, int sg, int sz,
                 boolean isBoy, boolean isYun,
                 int isheng, int ishi,int iZf,int iZy,int iZfs, int yydunNum,
                 String memo) {
  	this.setMingzhu(mingzhu);
  	this.setYongshen(yongshen);
  	
    this.setNg(ng);
    this.setNz(nz);
    this.setYg(yg);
    this.setYz(yz);
    this.setRg(rg);
    this.setRz(rz);
    this.setSg(sg);
    this.setSz(sz);
    this.setIsBoy(isBoy);
    this.setIsYun(isYun);
    this.setIsheng(isheng);
    this.setIshi(ishi);
    this.iZf = iZf;
    this.iZy = iZy;
    this.iZfs = iZfs;
    this.yydunNum = yydunNum;
    this.setMemo(memo);
  }

  public int getIZf() {
    return iZf;
  }
  public void setIZf(int iZf) {
    this.iZf = iZf;
  }
  public int getIZy() {
    return iZy;
  }
  public void setIZy(int iZy) {
    this.iZy = iZy;
  }
  public int getIZfs() {
    return iZfs;
  }
  public void setIZfs(int iZfs) {
    this.iZfs = iZfs;
  }
  public int getYydunNum() {
    return yydunNum;
  }
  public void setYydunNum(int yydunNum) {
    this.yydunNum = yydunNum;
  }

	public String getMingzhu() {
		return mingzhu;
	}

	public void setMingzhu(String mingzhu) {
		this.mingzhu = mingzhu;
	}

	public int getYongshen() {
		return yongshen;
	}

	public void setYongshen(int yongshen) {
		this.yongshen = yongshen;
	}
  
}
