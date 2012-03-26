package org.boc.util;

public class VoXuanKong extends VO{
  private int build;
  private int syjy;
  private int fxyear;
  private int fxmonth;
  private int fxday;
  private int fxhour;
  private int fxminute;
  private int dm;
  private int wf;
  private int cf;
  private int cesuo;
  private boolean isjian;
  private boolean isfxyun;
  private int sx;
  private int fxng;
  private int fxnz;
  private int fxyg;
  private int fxyz;
  private int fxrg;
  private int fxrz;
  private int fxsg;
  private int fxsz;

  public VoXuanKong(int build, int iJy, int sx, int[] fangx,
                    int[] mz, int[] syfx, boolean[] b, boolean bl) {
    this.build  = build;
    this.syjy = iJy;
    this.sx = sx;

    this.dm = fangx[1];
    this.wf = fangx[2];
    this.cf = fangx[3];
    this.cesuo = fangx[4];

    this.setNg(mz[1]);
    this.setNz(mz[2]);
    this.setYg(mz[3]);
    this.setYz(mz[4]);
    this.setRg(mz[5]);
    this.setRz(mz[6]);
    this.setSg(mz[7]);
    this.setSz(mz[8]);

    this.fxng = syfx[1];
    this.fxnz = syfx[2];
    this.fxyg = syfx[3];
    this.fxyz = syfx[4];
    this.fxrg = syfx[5];
    this.fxrz = syfx[6];
    this.fxsg = syfx[7];
    this.fxsz = syfx[8];

    this.setIsYin(b[1]);
    this.isjian=b[2];
    this.setIsYun(b[3]);
    this.isfxyun = b[4];
  }

  public VoXuanKong(int build, int iJy, int sx, int[] fangx,
                    int[] mz, int[] syfx, boolean[] b) {
    this.build  = build;
    this.syjy = iJy;
    this.sx = sx;

    this.dm = fangx[1];
    this.wf = fangx[2];
    this.cf = fangx[3];
    this.cesuo = fangx[4];

    this.setYear(mz[1]);
    this.setMonth(mz[2]);
    this.setDay(mz[3]);
    this.setHour(mz[4]);
    this.setMinute(mz[5]);
    this.setSecond(mz[6]);

    this.fxyear = syfx[1];
    this.fxmonth = syfx[2];
    this.fxday = syfx[3];
    this.fxhour = syfx[4];
    this.fxminute = syfx[5];

    this.setIsYin(b[1]);
    this.isjian=b[2];
    this.setIsYun(b[3]);
    this.isfxyun = b[4];
  }

  public int getBuild() {
    return build;
  }
  public void setBuild(int build) {
    this.build = build;
  }
  public int getSyjy() {
    return syjy;
  }
  public void setSyjy(int syjy) {
    this.syjy = syjy;
  }
  public int getFxyear() {
    return fxyear;
  }
  public void setFxyear(int fxyear) {
    this.fxyear = fxyear;
  }
  public int getFxmonth() {
    return fxmonth;
  }
  public void setFxmonth(int fxmonth) {
    this.fxmonth = fxmonth;
  }
  public int getFxday() {
    return fxday;
  }
  public void setFxday(int fxday) {
    this.fxday = fxday;
  }
  public int getFxhour() {
    return fxhour;
  }
  public void setFxhour(int fxhour) {
    this.fxhour = fxhour;
  }
  public int getFxminute() {
    return fxminute;
  }
  public void setFxminute(int fxminute) {
    this.fxminute = fxminute;
  }
  public int getDm() {
    return dm;
  }
  public void setDm(int dm) {
    this.dm = dm;
  }
  public int getWf() {
    return wf;
  }
  public void setWf(int wf) {
    this.wf = wf;
  }
  public int getCf() {
    return cf;
  }
  public void setCf(int cf) {
    this.cf = cf;
  }
  public int getCesuo() {
    return cesuo;
  }
  public void setCesuo(int cesuo) {
    this.cesuo = cesuo;
  }
  public boolean isIsjian() {
    return isjian;
  }
  public void setIsjian(boolean isjian) {
    this.isjian = isjian;
  }
  public boolean isIsfxyun() {
    return isfxyun;
  }
  public void setIsfxyun(boolean isfxyun) {
    this.isfxyun = isfxyun;
  }
  public int getSx() {
    return sx;
  }
  public void setSx(int sx) {
    this.sx = sx;
  }
  public int getFxng() {
    return fxng;
  }
  public void setFxng(int fxng) {
    this.fxng = fxng;
  }
  public int getFxnz() {
    return fxnz;
  }
  public void setFxnz(int fxnz) {
    this.fxnz = fxnz;
  }
  public int getFxyg() {
    return fxyg;
  }
  public void setFxyg(int fxyg) {
    this.fxyg = fxyg;
  }
  public int getFxyz() {
    return fxyz;
  }
  public void setFxyz(int fxyz) {
    this.fxyz = fxyz;
  }
  public int getFxrg() {
    return fxrg;
  }
  public void setFxrg(int fxrg) {
    this.fxrg = fxrg;
  }
  public int getFxrz() {
    return fxrz;
  }
  public void setFxrz(int fxrz) {
    this.fxrz = fxrz;
  }
  public int getFxsg() {
    return fxsg;
  }
  public void setFxsg(int fxsg) {
    this.fxsg = fxsg;
  }
  public int getFxsz() {
    return fxsz;
  }
  public void setFxsz(int fxsz) {
    this.fxsz = fxsz;
  }
}
