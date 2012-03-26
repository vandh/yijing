package org.boc.util;

public class VoLiuYao  extends VO implements java.io.Serializable{
  private int qgfs;		//起卦方式
  private int ys;			//用神
  private int up;			//上卦
  private int down;		//下卦
  private String dy;  //动爻数组以,分隔
  private String mzhu;//命主

  public VoLiuYao(int qgfs, int yongshen, int upGua,int downGua,
                  String acts,int[] sj,
                  int year,int month,int day,int hour,int minute,
                  boolean isYin, boolean isYun,
                  String mzhu, int prov, int city) {
  	this.mzhu = mzhu;
  	this.setIsheng(prov);
    this.setIshi(city);
    this.qgfs = qgfs;
    this.up=upGua;
    this.down=downGua;
    this.ys=yongshen;
    this.setYear(year);
    this.setMonth(month);
    this.setDay(day);
    this.setHour(hour);
    this.setMinute(minute);
    this.setIsYin(isYin);
    this.setIsYun(isYun);
    this.setDy(acts);
    setNg(sj[1]);
    setNz(sj[2]);
    setYg(sj[3]);
    setYz(sj[4]);
    setRg(sj[5]);
    setRz(sj[6]);
    setSg(sj[7]);
    setSz(sj[8]);
  }

  public String getMzhu() {
		return mzhu;
	}

	public void setMzhu(String mzhu) {
		this.mzhu = mzhu;
	}

	public int getQgfs() {
    return qgfs;
  }
  public void setQgfs(int qgfs) {
    this.qgfs = qgfs;
  }
  public int getYs() {
    return ys;
  }
  public void setYs(int ys) {
    this.ys = ys;
  }
  public int getUp() {
    return up;
  }
  public void setUp(int up) {
    this.up = up;
  }
  public int getDown() {
    return down;
  }
  public void setDown(int down) {
    this.down = down;
  }
  public String getDy() {
    return dy;
  }
  public void setDy(String dy) {
    this.dy = dy;
  }
}
