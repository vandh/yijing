package org.boc.dao;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Public;

public class DaoCalendar {

  /**
   * 由年月日时得到四柱
   * 0 是否闰月1闰0非
   * 1-8 四柱
   * 9-11 阳历年月日
   * 12-14取的农历年月日，都是不按节气定的
   * 15-17 按节气的
   * 18 星期几
   * @param yyyyy
   * @param mm
   * @param dd
   * @param hh
   * @param type 阴历为真，阳历为假
   * @param yun 只有为阴历时此参数才有效
   * @return
   */
  public int[] getSiZhu(int yyyy, int mm, int dd, int hh, int mi,
                        boolean isyun, boolean type) {
    int gYearn = 0, gMonthn = 0, gDayn = 0, gHour = hh*100+mi;  //阴历论节气的
    int gYearp = 0, gMonthp = 0, gDayp = 0;                     //阳历
    int gYearn1 = 0, gMonthn1 = 0, gDayn1 = 0;                  //阴历不论节气的
    int gYun = 0, gDays = 0;
    int gYearg = 0, gYearz = 0, gMonthg = 0, gMonthz = 0, gDayg = 0, gDayz = 0, gHourg = 0, gHourz = 0;

    int days = 0;
    int _m = mm;
    int initDN = 0;
    int initMN = 0;
    int initYN = 0;
    int daysPerMN = 0;

    int initDP = 0;
    int initMP = 0;
    int initYP = 0;
    int daysPerMP = 0;

    //如果是阴历
    //    如果该月=闰月&&yue==true || 该月>闰月此月都要加1
    int yunYue = getYunYue(yyyy);
    if(mm != yunYue)
      isyun = false;
    if(type)
      if((isyun && mm==yunYue) || (mm > yunYue && yunYue!=0))
        _m++;

    initDN = Calendar.IDAYN;
    initMN = Calendar.IMONTH;
    initYN = Calendar.IYEAR;
    initDP = Calendar.IDAYP;
    initMP = Calendar.IMONTH;
    initYP = Calendar.IYEAR;

    while (yyyy != (type ? initYN : initYP) ||
           _m != (type ? initMN : initMP) ||
           dd != (type ? initDN : initDP)) {
      initDN++;
      initDP++;
      days++;

      //按顺序取，阴或阳的第13个月可能为0
      //如果阴历取到了闰月，则取后两位数
      daysPerMN = getYinDays(initYN, initMN);
      if(daysPerMN > 32)
        daysPerMN -= daysPerMN/100 * 100;
      daysPerMP = getYangDays(initYP, initMP);

      if (initDN > daysPerMN ) {
         initDN = 1;
         initMN++;
      }
      //防止最大天与最大月同时满足
      if(initMN == 13) {
        daysPerMN = getYinDays(initYN, initMN);
        if (daysPerMN > 32)
          daysPerMN -= daysPerMN / 100 * 100;
        if (daysPerMN == 0) {
          initMN++;
        }
      }

      //只有12个月，如果大于13，将在下一轮循环加一年，而天数又加了1
      if (initMN > 13) {
        initMN = 1;
        initYN++;
      }

      if (initDP > daysPerMP ) {
         initDP = 1;
         initMP++;
      }

      //只有12个月，如果大于13，将在下一轮循环加一年，而天数又加了1
      if (initMP > 12) {
        initMP = 1;
        initYP++;
      }
    }

    if(type) {
      gYearn = yyyy; //可以是initYN
      gMonthn = mm; //不能是initMN，因为如果是闰月或大于闰月可能要减1，复杂
      gDayn = dd; //可以是initDN
      gYun = isyun?1:0;
    }else {
      gYearn = initYN;
      gMonthn = initMN;
      gDayn = initDN;

      //如果是闰月或者大于闰月，都要月份减一
      gYun = Calendar.yinli[initYN - Calendar.IYEAR][initMN] > 32 ? 1:0;
      if(gYun == 1) {
        gMonthn--;
      } else if(getYunYue(initYN) != 0 && initMN > getYunYue(initYN)) {
        gMonthn--;
      }
    }
    gYearp = initYP;
    gMonthp = initMP;
    gDayp = initDP;
    gDays = days;

    //这里才能确定最终的农历年
    gYearn1 = gYearn;
    //这里才能确定最终的农历月
    gMonthn1 = gMonthn;
    gDayn1 = gDayn;

    int y = gYearn;
    int m = gMonthn;  //这样检测速度最快
    int actTime = gMonthn*1000000+gDayn*10000+gHour;
    int yueOfJieqi = Calendar.jieqi[gYearn - Calendar.IYEAR][m]/1000000;
    int jie = Calendar.jieqi[gYearn - Calendar.IYEAR][m];
    int breaker1 = 0;
    int breaker2 = 0;
    boolean bYun = (gYun == 1 ? true:false);

    while(true) {
      if ( (actTime >= jie && !bYun && !isYunYue(y, m)) ||
          (actTime >= jie && !bYun && gMonthn != yueOfJieqi && isYunYue(y, m)) ||
          (actTime >= jie && bYun) ||
          (actTime < jie && bYun && gMonthn == yueOfJieqi && !isYunYue(y, m)) ||
          (actTime < jie && Math.abs(gMonthn - yueOfJieqi) > 5)) {
       if(breaker2 > 0) {
         gMonthn = m;
         gYearn = y;
         break;
       }
       //如果该节的月份小于阴历月，则终止，可能是另一年了
       if(yueOfJieqi < gMonthn && breaker1 != 0) {
         if(--m == 0) {
           gMonthn = 12;
           gYearn = --y;
         }else{
           gMonthn = m;
           gYearn = y;
         }
         break;
       }
        m++;
        if (m == 13) {
          m = 1;
          y++;
        }
        breaker1++;
      }else{
        if(breaker1 > 0) {
          if(--m == 0) {
            gMonthn = 12;
            gYearn = --y;
          }else{
            gMonthn = m;
            gYearn = y;
          }
          break;
        }
        if((yueOfJieqi > gMonthn && breaker2!=0) ||
            yueOfJieqi > gMonthn && breaker2==0 && yueOfJieqi - gMonthn>=6) {
          gMonthn = m;
          gYearn = y;
          break;
        }
        gMonthn = --m;
        if (m == 0) {
          m = 12;
          y--;
        }
        breaker2++;
      }
      yueOfJieqi = Calendar.jieqi[y - Calendar.IYEAR][m] / 1000000;
      jie = Calendar.jieqi[y - Calendar.IYEAR][m];
    }

    //此处才排年干
    int year = gYearn - Calendar.IYEAR;
    gYearg = (year+7) % 10 == 0 ? 10 : (year+7) % 10;
    gYearz = (year+7) % 12 == 0 ? 12 : (year+7) % 12;

    //排月干，初始天干，而正月起寅非起子
    int yg = SiZhu.yueByNian[gYearg];
    gMonthz = (gMonthn + 2) % 12 == 0 ? 12 : (gMonthn + 2) % 12;
    int yuezhu = gMonthz;
    if (yuezhu < YiJing.YIN)
      yuezhu += 12;
    gMonthg = (yg + yuezhu - 3) % 10 == 0 ? 10 : (yg + yuezhu - 3) % 10;

    //日柱
    gDayg = (gDays+7)%10==0 ? 10: (gDays+7)%10;
    gDayz = (gDays+5)%12==0 ? 12: (gDays+5)%12;

    //时柱
    int sg = SiZhu.shiByRi[gDayg];
    int i=1;
    for(; i<=12; i++) {
      if(SiZhu.HOURNUM[i] > gHour && i==1) {
        break;  //今天子时
      }else if(SiZhu.HOURNUM[i] <= gHour && i==12) {
        i=1;    //第二天子时
        gDayg = (gDayg+1)%10==0?10:(gDayg+1)%10;
        gDayz = (gDayz+1)%12==0?12:(gDayz+1)%12;
        sg = SiZhu.shiByRi[gDayg];
        break;
      }else if(SiZhu.HOURNUM[i] <= gHour && SiZhu.HOURNUM[i+1] > gHour) {
        i++;   //今日时刻
        break;
      }
    }
    gHourz = i;
    gHourg = (gHourz+sg-1)%10==0 ? 10 : (gHourz+sg-1)%10;

    int week = 0;
    if(days > 0 )
      week = (days+Calendar.IWEEK)%7==0 ? 7:(days+Calendar.IWEEK)%7;

    return new int[]{gYun,
        gYearg, gYearz, gMonthg, gMonthz, gDayg, gDayz, gHourg, gHourz,
        gYearp, gMonthp, gDayp,
        gYearn1, gMonthn1, gDayn1,
        gYearn, gMonthn, gDayn, week};
  }

  /**
   * 由时间得到头部
   * @param sj 0是否闰月 1-8四柱 9-11阳历年月日 12-14取的农历年月日，都是不按节气定的
   * 15-17 按节气的 18 星期几
   * @param sheng,shi -1为不调真太阳时
   */
  public String getShiJian(int[] sj,int h, int mi,int sheng,int shi, String born, boolean sex) {
    StringBuilder sb = new StringBuilder();
    int sunTrue = 0;
    boolean isSunTrue = false;
    if(sheng>=0 && shi>=0) {
      isSunTrue = true;
    }else{
      isSunTrue = false;
    }

    if(isSunTrue) {
      sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
      sunTrue += Calendar.zpsc[sj[10]][sj[11]];
    }

    sb.append("阳历："+sj[9]+"年"+sj[10]+"月"+sj[11]+"日 "+Calendar.WEEKNAME[sj[18]]);
    sb.append("\n    农历："+sj[12]+"年");
    sb.append(sj[0]==1 ? "闰":"");
    sb.append(Public.DAXIAO[sj[13]]+"月初"+Public.DAXIAO[sj[14]]+" "+h+":"+mi);
    int h1 = (h*60*60+mi*60+sunTrue)/60/60;
    int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
    int hour2 = h1*100+mi1;
    if(isSunTrue) {
    	sb.append("\n"+new DaoYiJingMain().getRepeats(" ", YiJing.INTER[0]));
    	sb.append("阴历真太阳时：" + sj[12] + "年" );
    	sb.append(sj[0]==1 ? "闰" : "");
    	sb.append(sj[13] + "月初" + sj[14] + " " + h1 + ":" + mi1) ;
    }
    
//    int iborn = 0;
//    try{
//    	iborn = Integer.valueOf(born);
//    }catch(Exception e) {}
//    if(iborn!=0 && iborn > Calendar.IYEAR && iborn <Calendar.MAXYEAR) {
//      int bg = (iborn - Calendar.IYEAR + Calendar.IYEARG) % 10 == 0 ? 10 :
//          (iborn - Calendar.IYEAR + Calendar.IYEARG) % 10;
//      int bz = (iborn - Calendar.IYEAR + Calendar.IYEARZ) % 12 == 0 ? 12 :
//          (iborn - Calendar.IYEAR + Calendar.IYEARZ) % 12;
//      sb.append("\n    年命：");
//      sb.append(born + "年生，" + (sex ? "乾造" : "坤造"));
//      sb.append("，" + YiJing.TIANGANNAME[bg] + YiJing.DIZINAME[bz]);
//    }else if(born.split(",").length>1) { //如果是干支形式
//    	int bg = Integer.valueOf(born.split(",")[0]);
//    	int bz = Integer.valueOf(born.split(",")[1]);
//    	sb.append("\n    年命：");
//      sb.append(sex ? "乾造" : "坤造");
//      sb.append("，" + YiJing.TIANGANNAME[bg] + YiJing.DIZINAME[bz]);
//    }

    return sb.toString();
  }

  /**
   * 得到指定的阴历或阳历日期与初始日期相差天数、四柱、星期几, 子时都不加1天了
   * @param type 阴历为真，阳历为假
   * @param yuen 只有为阴历时此参数才有效
   */
  public void calculate(int y, int m, int d, int h,int mi, boolean type, boolean yun,int sheng ,int shi) {
    transYinYangDate(y,m,d,type,yun);
    getWeek();
    //Calendar.HOUR = h*100+mi;
    //得到真太阳时间,秒; 得到阳历月的修正时间
    int sunTrue = 0;
    Calendar.HOUR = h*100+mi;
    //如果有了真太阳时，必须将其加为全局变量，以便恢复时使用；simon(2011-12-14)
    if(sheng>=0 && shi>=0) {
      Calendar.isSunTrue = true;
      Calendar.PROVINCE = sheng;
      Calendar.CITY = shi;
    }else {
    	Calendar.isSunTrue = false;
    }
//    if(sheng<=0 && shi<=0 && Calendar.isSunTrue && Calendar.PROVINCE!=-1 && Calendar.CITY!=-1){
//      sheng = Calendar.PROVINCE;
//      shi = Calendar.CITY;
//    }

    if(Calendar.isSunTrue) {
      sunTrue = (Calendar.jingdu[sheng][shi] - 120 * 60) * 4;
      sunTrue += Calendar.zpsc[Calendar.MONTHP][Calendar.DAYP];
    }

    SiZhu.yinli = "阴历北京时间："+Calendar.YEARN+"年"+(Calendar.YUN ? "闰":"") +
                  Calendar.MONTHN+"月初"+Calendar.DAYN+" "+h+":"+mi+" "+
                  Calendar.WEEKNAME[Calendar.WEEK];
    int h1 = (h*60*60+mi*60+sunTrue)/60/60;
    int mi1 = (h*60*60+mi*60+sunTrue)/60 - h1*60;
    Calendar.HOUR2 = h1*100+mi1;
    if(Calendar.isSunTrue) {
      SiZhu.yinli += "\n"+new DaoYiJingMain().getRepeats(" ", YiJing.INTER[0])+
          "阴历真太阳时：" + Calendar.YEARN + "年" +
          (Calendar.YUN ? "闰" : "") +
          Calendar.MONTHN + "月初" + Calendar.DAYN + " " + h1 + ":" + mi1 + " " +
          Calendar.WEEKNAME[Calendar.WEEK];
    }

    Calendar.YEARN1 = Calendar.YEARN;
    Calendar.MONTHN1 = Calendar.MONTHN;
    Calendar.DAYN1 = Calendar.DAYN;
    SiZhu.yangli = "阳历时间："+Calendar.YEARP+"--"+Calendar.MONTHP+"--"+Calendar.DAYP;
    //先推月的节，再排年干支、后排月干支,年干支在月中排
    getMonthGanZi();

    getRiZhu();
    //如果是>23:00则是第二天了，故日干支在时柱中排，但不处理子时+1天的情况，没必要
    getShiZhu();
  }


  /**
   * 得到时柱
   */
  public void getShiZhu() {
    SiZhu.sg = SiZhu.shiByRi[SiZhu.rg];
    getShiZi();
    SiZhu.sg = (SiZhu.sz+SiZhu.sg-1)%10==0 ? 10 : (SiZhu.sz+SiZhu.sg-1)%10;
  }


  /**
   * 得到日柱
   */
  public void getRiZhu() {
    //if(Calendar.HOUR >= 2300)
    //  Calendar.DAYS++;
    SiZhu.rg = (Calendar.DAYS+7)%10==0 ? 10: (Calendar.DAYS+7)%10;
    SiZhu.rz = (Calendar.DAYS+5)%12==0 ? 12: (Calendar.DAYS+5)%12;
  }

  /**
   * 得到时柱的地支
   * Calendar.HOUR为原北京时间，Calendar.HOUR2为真太阳时
   */
  public void getShiZi() {
    int i=1;
    for(; i<=12; i++) {
      if(SiZhu.HOURNUM[i] > Calendar.HOUR2 && i==1) {
        break;
      }else if(SiZhu.HOURNUM[i] <= Calendar.HOUR2 && i==12) {
        i=1;
        SiZhu.rg = (SiZhu.rg+1)%10==0?10:(SiZhu.rg+1)%10;
        SiZhu.rz = (SiZhu.rz+1)%12==0?12:(SiZhu.rz+1)%12;
        SiZhu.sg = SiZhu.shiByRi[SiZhu.rg];
        break;
      }else if(SiZhu.HOURNUM[i] <= Calendar.HOUR2 && SiZhu.HOURNUM[i+1] > Calendar.HOUR2) {
        i++;
        break;
      }
    }
    SiZhu.sz = i;
  }

  /**
   * 得到月的干支
   * 先取当月的节大于的情况，小于则相反
   *    1. 非闰月且大于该非闰月
   *    2. 非闰月大于该闰月且月份不相等
   *    3. 闰月大于此月
   *    4. 闰月小于非闰月且月份相同
   *    5. 小于此月且月份相差6月以上
   */
  public void getMonthGanZi() {
    int yun = 0;
    int y = Calendar.YEARN;
    int m = Calendar.MONTHN;  //这样检测速度最快
    int actTime = Calendar.MONTHN*1000000+Calendar.DAYN*10000+Calendar.HOUR;
    int yueOfJieqi = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR][m]/1000000;
    int jie = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR][m];
    int breaker1 = 0;
    int breaker2 = 0;

    while(true) {
      if ( (actTime >= jie && !Calendar.YUN && !isYunYue(y, m)) ||
          (actTime >= jie && !Calendar.YUN && Calendar.MONTHN != yueOfJieqi && isYunYue(y, m)) ||
          (actTime >= jie && Calendar.YUN) ||
          (actTime < jie && Calendar.YUN && Calendar.MONTHN == yueOfJieqi && !isYunYue(y, m)) ||
          (actTime < jie && Math.abs(Calendar.MONTHN - yueOfJieqi) > 5)) {
       if(breaker2 > 0) {
         Calendar.MONTHN = m;
         Calendar.YEARN = y;
         break;
       }
       //如果该节的月份小于阴历月，则终止，可能是另一年了
       if(yueOfJieqi < Calendar.MONTHN && breaker1 != 0) {
         if(--m == 0) {
           Calendar.MONTHN = 12;
           Calendar.YEARN = --y;
         }else{
           Calendar.MONTHN = m;
           Calendar.YEARN = y;
         }
         break;
       }
        m++;
        if (m == 13) {
          m = 1;
          y++;
        }
        breaker1++;
      }else{
        if(breaker1 > 0) {
          if(--m == 0) {
            Calendar.MONTHN = 12;
            Calendar.YEARN = --y;
          }else{
            Calendar.MONTHN = m;
            Calendar.YEARN = y;
          }
          break;
        }
        if((yueOfJieqi > Calendar.MONTHN && breaker2!=0) ||
            yueOfJieqi > Calendar.MONTHN && breaker2==0 && yueOfJieqi-Calendar.MONTHN>=6) {
          Calendar.MONTHN = m;
          Calendar.YEARN = y;
          break;
        }
        Calendar.MONTHN = --m;
        if (m == 0) {
          m = 12;
          y--;
        }
        breaker2++;
      }
      yueOfJieqi = Calendar.jieqi[y - Calendar.IYEAR][m] / 1000000;
      jie = Calendar.jieqi[y - Calendar.IYEAR][m];
    }

    /**
     * 得到节气的年月日
     * 如果98.1农历生，则其节气小的一定是98年1月的节气，绝不可能跑到97.12月去
     * 能推到99.12月生的人，肯定下一节气是2000年的1月
     * @since simon 2011-10-11 修正节气计算错误
     * 如果2007.1.14月生，其最接近的小于它的节气是2007.1.2的雨水，但其月份并不是Calendar.jieqi[77][1]而是[77][2]
     * 节气的年份也不一定就是2007年，所以纠正此bug
     */
    int jie1 = 0;
    /////////////////////////////////////////////////////////////
    Calendar.MINJIEN = Calendar.YEARN1;
    Calendar.MINJIEY = Calendar.jieqi[y - Calendar.IYEAR][Calendar.MONTHN]/1000000;    
    Calendar.MINJIER = (Calendar.jieqi[y - Calendar.IYEAR][Calendar.MONTHN] - Calendar.MINJIEY*1000000)/10000;
    //简单算法，从当年第一个节气开始检查，月份如果>=10月，肯定是前一年的；一直找到一个介于前后节气之间的，取小的节气
    //如果是闰月则需要再往后找
    //如果比当前开头的节气小或比当前结尾的节气大，还需取上一年或下一年，太复杂，没必要实现了
//    int jieqiyear = Calendar.YEARN1;
//    for(int i=1; i<=24; i++) {
//    	if(i<=2 && Calendar.jieqi[y - Calendar.IYEAR][i]/1000000>=10) 
//    		jieqiyear = Calendar.YEARN1 - 1;
//    	else 
//    		jieqiyear = Calendar.YEARN1;
//    	if(jieqiyear*100000000+Calendar.jieqi[y - Calendar.IYEAR][i] < Calendar.YEARN1*1000000+Calendar.MONTHN1*1000000+Calendar.DAYN1*10000 &&
//    			jieqiyear*100000000+Calendar.jieqi[y - Calendar.IYEAR][i] < Calendar.YEARN1*1000000+Calendar.MONTHN1*1000000+Calendar.DAYN1*10000)
//    		
//    		
//    }
    ////////////////////////////////////////////////////////////
    if(Calendar.MONTHN<12) {
      jie1 = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR][Calendar.MONTHN+1];
      Calendar.MAXJIEN = Calendar.YEARN;
    }
    else {
      jie1 = Calendar.jieqi[Calendar.YEARN - Calendar.IYEAR + 1][1];
      if(jie1/1000000 > 5) {
        Calendar.MAXJIEN = Calendar.YEARN;
      }else {
        Calendar.MAXJIEN = Calendar.YEARN + 1;
      }
    }
    Calendar.MAXJIEY = jie1/1000000;
    Calendar.MAXJIER = (jie1 - Calendar.MAXJIEY * 1000000)/10000;

    //此处才排年干
    getYearGanZi();
    //初始天干，而正月起寅非起子
    int yg = SiZhu.yueByNian[SiZhu.ng];
    SiZhu.yz = (Calendar.MONTHN+2)%12 == 0 ? 12 : (Calendar.MONTHN+2)%12;
    int yuezhu = SiZhu.yz;
    if(yuezhu < YiJing.YIN)
      yuezhu += 12;
    SiZhu.yg = (yg+yuezhu-3)%10 == 0 ? 10 : (yg+yuezhu-3)%10;
  }

  /**
   * 得到年的干支
   * 1930 庚午
   */
  public void getYearGanZi() {
    int year = Calendar.YEARN - Calendar.IYEAR;
    SiZhu.ng = (year+7) % 10 == 0 ? 10 : (year+7) % 10;
    SiZhu.nz = (year+7) % 12 == 0 ? 12 : (year+7) % 12;
  }

  /**
   * 得到星期几，由相差天数
   * @param days
   * @return
   */
  public int getWeek() {
    if(Calendar.DAYS == 0 )
      Calendar.WEEK = 0;
    Calendar.WEEK = (Calendar.DAYS+Calendar.IWEEK)%7==0 ? 7:(Calendar.DAYS+Calendar.IWEEK)%7;
    return Calendar.WEEK;
  }

  /**
   * 得到指定的阴历或阳历日期与初始日期相差天数
   * 得到其相应的阴历与阳历
   * @param type 阴历为真，阳历为假
   * @param yuen 只有为阴历时此参数才有效
   */
  public void transYinYangDate(int y, int m, int d, boolean type, boolean yun) {
  	//System.out.println(y+"; m="+m+"; d="+d+";type="+type+"; yun="+yun);
    int days = 0;
    int _m = m;
    int initDN = 0;
    int initMN = 0;
    int initYN = 0;
    int daysPerMN = 0;

    int initDP = 0;
    int initMP = 0;
    int initYP = 0;
    int daysPerMP = 0;
    
    int yunYue = getYunYue(y);  //得到该年哪月是闰月
    if(m != yunYue)
      yun = false;
    if(type)  //如果是阴历，该月=闰月&&yue==true || 该月>闰月此月都要加1。 因为置闰多了一月
      if((yun && m==yunYue) || (m > yunYue && yunYue!=0))
        _m++;

    initDN = Calendar.IDAYN;
    initMN = Calendar.IMONTH;
    initYN = Calendar.IYEAR;
    initDP = Calendar.IDAYP;
    initMP = Calendar.IMONTH;
    initYP = Calendar.IYEAR;

    while (y != (type ? initYN : initYP) ||
           _m != (type ? initMN : initMP) ||
           d != (type ? initDN : initDP)) {
      initDN++;		//阴历在数加1
      initDP++;		//阳历天数加1
      days++;			//总天数加1

      daysPerMN = getYinDays(initYN, initMN);   //阴历每月天数      
      if (initDN > daysPerMN ) {  //如果阴历天数比该月天数大了，则从1重新累计，阴历月份加1
         initDN = 1;
         initMN++;
      }           
      if(initMN == 13) {  //防止最大天与最大月同时满足，有闰月的话第13个月是存在的，无闰月则是最后一月了
        daysPerMN = getYinDays(initYN, initMN);
        if (daysPerMN == 0) {
          initMN++;
        }
      }      
      if (initMN > 13) {	//最多只有13个月，如果大于13，则年份加1，而月份重置1
        initMN = 1;
        initYN++;
      }
      
      daysPerMP = getYangDays(initYP, initMP);	//阳历每月天数
      if (initDP > daysPerMP ) { //累计数>每月天数，则初始天数重置为1，阳历月份加1
         initDP = 1;
         initMP++;
      }      
      if (initMP > 12) {  //阳历只有12个月，如果大于13，将在下一轮循环加一年，而天数又重置为1
        initMP = 1;
        initYP++;
      }
    }

    if(type) {
      Calendar.YEARN = y; //可以是initYN
      Calendar.MONTHN = m; //不能是initMN，因为如果是闰月或大于闰月可能要减1，复杂
      Calendar.DAYN = d; //可以是initDN
      Calendar.YUN = yun;
    }else {
      Calendar.YEARN = initYN;
      Calendar.MONTHN = initMN;
      Calendar.DAYN = initDN;

      //如果是闰月或者大于闰月，都要月份减一
      Calendar.YUN = Calendar.yinli[initYN - Calendar.IYEAR][initMN] > 32;
      if(Calendar.YUN) {
        Calendar.MONTHN--;
      } else if(getYunYue(initYN) != 0 && initMN > getYunYue(initYN)) {
        Calendar.MONTHN--;
      }
    }

    Calendar.YEARP = initYP;
    Calendar.MONTHP = initMP;
    Calendar.DAYP = initDP;

    Calendar.DAYS = days;
  }

  /**
   * 得到指定的阴历日期与节气相差天数
   */
  public int getDiffDates() {
    if(Calendar.YEARN1==Calendar.IYEAR &&
       Calendar.MONTHN1 == Calendar.IMONTH &&
       Calendar.DAYN1 == Calendar.IDAYN)
      return 30;
    int days = 0;
    int _m = Calendar.MONTHN1;
    int _d = 0, _y = 0;
    int iy = 0;
    int im = 0;
    int id = 0;
    int daysPerMonth = 0;

    //如果是阴历
    //    如果该月=闰月&&yue==true || 该月>闰月此月都要加1
    int yunYue = getYunYue(Calendar.YEARN1);
    if((Calendar.YUN && Calendar.MONTHN1==yunYue) || (Calendar.MONTHN1 > yunYue && yunYue!=0))
        _m++;

    //阳男
    DaoSiZhuMain dao = new DaoSiZhuMain();
    if(dao.getSexChar() == 1) {
      iy = Calendar.YEARN1;
      im = _m;  //如果闰月且大于闰月，必须加1，因为取月天数时无参数的
      id = Calendar.DAYN1;

      _y = Calendar.MAXJIEN;
      _m = Calendar.MAXJIEY;
      _d = Calendar.MAXJIER;
      //必须加1，因为取月天数时无参数的
      //_m=闰月 ，月份-1>的节气的月份=闰月则是
      //_m>闰月的
      int yy = getYunYue(_y);
      if((_m == yy && Calendar.MINJIEY == yy) || (_m > yy && yy>0))  ++_m;
    }else{
      //阴男
      _y = Calendar.YEARN1;
      //_m已经初始化赋值了
      _d = Calendar.DAYN1;

      iy = Calendar.MINJIEN;
      im = Calendar.MINJIEY;
      id = Calendar.MINJIER;
      int yy = getYunYue(iy);
      if((im == yy && Calendar.MAXJIEY > yy) || (im > yy && yy>0))  ++im;
    }

    while (_y != iy || _m != im || _d != id) {
      id++;
      days++;

      //按顺序取，阴或阳的第13个月可能为0
      //如果阴历取到了闰月，则取后两位数
      daysPerMonth = getYinDays(iy, im);
      if(daysPerMonth > 32)
        daysPerMonth -= daysPerMonth/100 * 100;

      if (id > daysPerMonth ) {
         id = 1;
         im++;
      }
      //防止最大天与最大月同时满足
      if(im == 13) {
        daysPerMonth = getYinDays(iy, im);
        if (daysPerMonth > 32)
          daysPerMonth -= daysPerMonth / 100 * 100;
        if (daysPerMonth == 0) {
          im++;
        }
      }

      //只有12个月，如果大于13，将在下一轮循环加一年，而天数又加了1
      if (im > 13) {
        im = 1;
        iy++;
      }
    }
    //System.err.println("days = "+days);
    return days;
  }

  /**
   * 得到指定的阴历日期与节气相差天数，为某月某干司令用
   */
  public int getDiffDatesForSiLing() {
     if(Calendar.YEARN <= Calendar.IYEAR &&
       Calendar.MONTHN <= Calendar.IMONTH &&
       Calendar.DAYN <= Calendar.IDAYN)
      return 1;
    int days = 0;
    int _m = Calendar.MONTHN1;
    int _d = 0, _y = 0;
    int iy = 0;
    int im = 0;
    int id = 0;
    int daysPerMonth = 0;

    //如果是阴历
    //    如果该月=闰月&&yue==true || 该月>闰月此月都要加1
    int yunYue = getYunYue(Calendar.YEARN1);
    if((Calendar.YUN && Calendar.MONTHN1==yunYue) || (Calendar.MONTHN1 > yunYue && yunYue!=0))
        _m++;

    DaoSiZhuMain dao = new DaoSiZhuMain();

    _y = Calendar.YEARN1;
    //_m已经初始化赋值了
    _d = Calendar.DAYN1;

    iy = Calendar.MINJIEN;
    im = Calendar.MINJIEY;
    id = Calendar.MINJIER;
    int yy = getYunYue(iy);
    if ( (im == yy && Calendar.MAXJIEY > yy) || (im > yy && yy > 0))
      ++im;

    while (_y != iy || _m != im || _d != id) {
    	if(id>=30 && im>=12 && iy>=Calendar.MAXYEAR) {
    		return 10;  //算法太复杂，如果实在没找到，返回缺省天数10天
    	}
      id++;
      days++;

      //按顺序取，阴或阳的第13个月可能为0
      //如果阴历取到了闰月，则取后两位数
      daysPerMonth = getYinDays(iy, im);
      if(daysPerMonth > 32)
        daysPerMonth -= daysPerMonth/100 * 100;

      if (id > daysPerMonth ) {
         id = 1;
         im++;
      }
      //防止最大天与最大月同时满足
      if(im == 13) {
        daysPerMonth = getYinDays(iy, im);
        if (daysPerMonth > 32)
          daysPerMonth -= daysPerMonth / 100 * 100;
        if (daysPerMonth == 0) {
          im++;
        }
      }

      //只有12个月，如果大于13，将在下一轮循环加一年，而天数又加了1
      if (im > 13) {
        im = 1;
        iy++;
      }
    }
    //System.err.println("days = "+days);
    return days;
  }

  /**
   * 得到指定年的月的阴历天数，无参数闰月，按顺序取一共13个月/年
   */
  public int getYinDays(int y, int m) {
    //对应于直接输八字的无年月日时
    if(y<Calendar.IYEAR) return 29;
    //System.out.print(y);
    //simon 注释掉下面二行，2011-11-16
    //if(y - Calendar.IYEAR>=81)
    //System.out.println((y - Calendar.IYEAR)+",m="+m);
    
    int days = Calendar.yinli[y - Calendar.IYEAR][m];
    if(days>32)  //如果是429，则为429-400
      days -= days/100 * 100;

    return days;
  }

  /**
   * 得到指定年的月的阴历天数，有参数闰月
   * @param yun 真为闰月，非
   */
  public int getYinDays(int y, int m, boolean yun) {
    int yunYue = getYunYue(y);
    int days = 0;

    if(yunYue == 0) {
      return Calendar.yinli[y - Calendar.IYEAR][m];
    }

    if(m < yunYue || (!yun && m==yunYue)) {
      days = Calendar.yinli[y - Calendar.IYEAR][m];
    }else if((yun && m==yunYue)) {
      days = Calendar.yinli[y - Calendar.IYEAR][++m];
      days -= days/100 * 100;
    } else {
      days = Calendar.yinli[y - Calendar.IYEAR][++m];
    }

    return days;
  }

  /**
   * 判断当前年的当前月(不是实际月份，只是序号)是否是闰月
   * @param <any>
   * @return
   */
  public boolean isYunYue(int y, int m) {
    int days = Calendar.yinli[y - Calendar.IYEAR][m];
    return days>32;
  }

  /**
   * 得到该年的闰月
   */
  public int getYunYue(int y){
    if(y==0) return y;
    int[] yue = Calendar.yinli[y - Calendar.IYEAR];
    int j = 0;
    for(int i = 1; i<13; i++) {
      if(yue[i] > 32) {
        j = yue[i]/100;
        break;
      }
    }
    return j;
  }

  /**
   * 得到指定年的月的阳历天数
   */
  public int getYangDays(int y, int m) {
    int[] mday = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 0};
    int x = y%4==0 && (y%100!=0 || y%400==0) ? 29:28;
    return m == 2 ? x : mday[m];
  }

  public static void main(String[] args) throws Exception{
    DaoCalendar d = new DaoCalendar();
    long t1 = System.currentTimeMillis();
    //d.calculate(1977,3,23,6,31,true,true,0,0);
    //Debug.out(SiZhu.yinli+"\r\n"+SiZhu.yangli);
    //int[] gz = d.getSiZhu(2001,4,25,11,30,false,false);
//    int[] gz = d.getSiZhu(2004,2,1,0,30,true,false);
    
    d.transYinYangDate(2010,12,30,false, true);
    d.transYinYangDate(2013,1,30,false, true);
//    System.out.println("八字： "+(gz[0]==1?"闰  ":"  ")+YiJing.TIANGANNAME[gz[1]] + YiJing.DIZINAME[gz[2]] +
//                       "\t"+ YiJing.TIANGANNAME[gz[3]] + YiJing.DIZINAME[gz[4]] +
//                       "\t"+ YiJing.TIANGANNAME[gz[5]] + YiJing.DIZINAME[gz[6]] +
//                       "\t"+ YiJing.TIANGANNAME[gz[7]] + YiJing.DIZINAME[gz[8]] );

    System.out.println("耗时："+(System.currentTimeMillis()-t1)+"ms");
  }
}
