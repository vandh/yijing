package org.boc.util;

public class VoBaZhai extends VO{
  private int yearGirl;
  private int[] iHjs;
  private int iWx;
  private int iDmw;
  private int iDmx;
  private int iGxw;
  private int iJzw;
  private int iWf;
  private int iFmw;
  private int iCw;
  private int iCfw;
  private int iZw;
  private int iZx;
  public VoBaZhai(int yearBoy,int yearGirl, int iFz,
                  int[] iHjs, int iWx,int iDmw, int iDmx, int iGxw, int iJzw,
                  int iWf, int iFmw,int iCw,  int iCfw, int iZw, int iZx) {
    this.setYear(yearBoy);
    this.setIsBoy(iFz==1);

    this.yearGirl = yearGirl;
    this.iHjs = iHjs;
    this.iWx = iWx;
    this.iDmw = iDmw;
    this.iDmx = iDmx;
    this.iGxw = iGxw;
    this.iJzw = iJzw;
    this.iWf = iWf;
    this.iFmw = iFmw;
    this.iCw = iCw;
    this.iCfw = iCfw;
    this.iZw = iZw;
    this.iZx = iZx;
  }
  public int getYearGirl() {
    return yearGirl;
  }
  public void setYearGirl(int yearGirl) {
    this.yearGirl = yearGirl;
  }
  public int[] getIHjs() {
    return iHjs;
  }
  public void setIHjs(int[] iHjs) {
    this.iHjs = iHjs;
  }
  public int getIWx() {
    return iWx;
  }
  public void setIWx(int iWx) {
    this.iWx = iWx;
  }
  public int getIDmw() {
    return iDmw;
  }
  public void setIDmw(int iDmw) {
    this.iDmw = iDmw;
  }
  public int getIDmx() {
    return iDmx;
  }
  public void setIDmx(int iDmx) {
    this.iDmx = iDmx;
  }
  public int getIGxw() {
    return iGxw;
  }
  public void setIGxw(int iGxw) {
    this.iGxw = iGxw;
  }
  public int getIJzw() {
    return iJzw;
  }
  public void setIJzw(int iJzw) {
    this.iJzw = iJzw;
  }
  public int getIWf() {
    return iWf;
  }
  public void setIWf(int iWf) {
    this.iWf = iWf;
  }
  public int getIFmw() {
    return iFmw;
  }
  public void setIFmw(int iFmw) {
    this.iFmw = iFmw;
  }
  public int getICw() {
    return iCw;
  }
  public void setICw(int iCw) {
    this.iCw = iCw;
  }
  public int getICfw() {
    return iCfw;
  }
  public void setICfw(int iCfw) {
    this.iCfw = iCfw;
  }
  public int getIZw() {
    return iZw;
  }
  public void setIZw(int iZw) {
    this.iZw = iZw;
  }
  public int getIZx() {
    return iZx;
  }
  public void setIZx(int iZx) {
    this.iZx = iZx;
  }
}
