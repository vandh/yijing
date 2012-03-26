package org.boc.dao.ly;

import org.boc.dao.DaoCalendar;
import org.boc.db.Calendar;
import org.boc.db.YiJing;

public class LiuyaoPublic {
	private DaoYiJingMain daoly;
  private DaoCalendar daocal;
  
	public LiuyaoPublic(DaoYiJingMain daoly, DaoCalendar daocal) {
		this.daoly = daoly;
		this.daocal = daocal;
	}
	public DaoYiJingMain getDaoYiJingMain() {
		return daoly;
	}
	public DaoCalendar getDaoCalendar() {
		return daocal;
	}
	
	//将字符串转化成动爻数组
	public int[] getActs(String sact) {
		if(sact==null || sact.trim().equals("")) return null;
		
		int[] chgs;
		String[] s = sact.split(",");
    int j=0;
    
    for(int i=0; i<s.length; i++) {
      if(s[i]==null || s[i].trim().equals("0") || "".equals(s[i].trim()))
        continue;
      j++;
    }    
    chgs = new int[j];
    j = 0;
    for(int i=0; i<s.length; i++) {
      if(s[i]==null || s[i].trim().equals("0") || "".equals(s[i].trim()))
        continue;
      chgs[j++] = Integer.valueOf(s[i]).intValue();
    }
    
    return chgs;
	}
	
	/**
	 * 分析命主的格式，得到年命的干与支
	 * 命主格式一般为：1977或1,1二种形式，在婚姻中存在2个等多个的情况
	 * @return
	 */
	public int[] getMZhu(String mingzhu) {
		if(mingzhu==null || "".equals(mingzhu.trim()))
			return new int[]{0,0};
		
		//去掉空格及前后|
		String[] kg = {"\\|",";",",","\\","\\/","-","%","$","@","*"};
		mingzhu = mingzhu.trim();
		for(int i =0; i<kg.length; i++ ) {
			if(mingzhu.startsWith(kg[i])) mingzhu = mingzhu.substring(1);
			if(mingzhu.endsWith(kg[i])) mingzhu = mingzhu.substring(0, mingzhu.length()-1);
		}
		
		String[] yeararry = mingzhu.split("\\|");  //得到多个年份或年干支		
		int year = 0;
		int[] yearganzi = new int[2*yeararry.length];
		int j = 0;
		
		for(int i=0; i<yeararry.length; i++) {
			String[] douhao = yeararry[i].split(",");		
			if(douhao.length==1) {
				year = Integer.valueOf(yeararry[i]);
				int[] ngz = this.getYearGanzi(year); 
				yearganzi[j++] = ngz[0];
				yearganzi[j++] = ngz[1];
			}else {			
				yearganzi[j++] = Integer.valueOf(douhao[0]);
				yearganzi[j++] = Integer.valueOf(douhao[1]);
			}
		}
		return yearganzi;
	}
	
	/**
   * 由年份得到年干支
   */
  public int[] getYearGanzi(int year) {
  	int bg = (year - Calendar.IYEAR + Calendar.IYEARG) % 10 == 0 ? 10 :
      (year - Calendar.IYEAR + Calendar.IYEARG) % 10;
  	int bz = (year - Calendar.IYEAR + Calendar.IYEARZ) % 12 == 0 ? 12 :
      (year - Calendar.IYEAR + Calendar.IYEARZ) % 12;
  	return new int[]{bg, bz};
  }
  
  /**
   * 得到旬空
   */
  public String[] getXunKongs(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    int zi1 = xk/100;
    int zi2 = xk - zi1*100;

    String[] str = new String[2];
    str[0] = YiJing.DIZINAME[zi1];
    str[1] = YiJing.DIZINAME[zi2];
    return str;
  }
  public int[] getXunKongi(int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    return new int[]{xk/100, xk%100};
  }
  
  private boolean isboy;
  public boolean isBoy() {
  	return isboy;
  }
  public void setBoy(boolean isboy){
  	this.isboy = isboy;
  }
}
