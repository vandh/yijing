package org.boc.util;

public class VoLiuRen extends VO{
  private int bg;
  private int bz;
  private int iGr;
  private int iZy;
  private int iDg;
  private int iYj;
  private int iyb;

  public VoLiuRen(int bg, int bz, int yg, int yz, int mg,
                  int mz, int dg, int dz, int hg, int hz,
                  boolean isYin,boolean isBoy,boolean isYun,
                  int iGr,int iZy,int iDg,int iYj) {
    this.bg=bg;
    this.bz=bz;
    this.setNg(yg);
    this.setNz(yz);
    this.setYg(mg);
    this.setYz(mz);
    this.setRg(dg);
    this.setRz(dz);
    this.setSg(hg);
    this.setSz(hz);
    this.setIsYin(isYin);
    this.setIsBoy(isBoy);
    this.setIsYun(isYun);
    this.iGr=iGr;
    this.iZy=iZy;
    this.iDg=iDg;
    this.iYj = iYj;
  }

  public VoLiuRen(int iyb, int iyy, int imm, int idd,
                  int ihh, int imi, int iss,
                  boolean isYin, boolean isBoy, boolean isYun,
                  int iGr,int iZy,int iDg,int iYj) {
    this.iyb=iyb;
    this.setYear(iyy);
    this.setMonth(imm);
    this.setDay(idd);
    this.setHour(ihh);
    this.setMinute(imi);
    this.setSecond(iss);
    this.setIsYin(isYin);
    this.setIsBoy(isBoy);
    this.setIsYun(isYun);
    this.iGr=iGr;
    this.iZy=iZy;
    this.iDg=iDg;
    this.iYj = iYj;
  }

  public int getBg() {
    return bg;
  }
  public void setBg(int bg) {
    this.bg = bg;
  }
  public int getBz() {
    return bz;
  }
  public void setBz(int bz) {
    this.bz = bz;
  }

  public int getIGr() {
    return iGr;
  }
  public void setIGr(int iGr) {
    this.iGr = iGr;
  }
  public int getIZy() {
    return iZy;
  }
  public void setIZy(int iZy) {
    this.iZy = iZy;
  }
  public int getIDg() {
    return iDg;
  }
  public void setIDg(int iDg) {
    this.iDg = iDg;
  }
  public int getIYj() {
    return iYj;
  }
  public void setIYj(int iYj) {
    this.iYj = iYj;
  }
  public int getIyb() {
    return iyb;
  }
  public void setIyb(int iyb) {
    this.iyb = iyb;
  }
}
