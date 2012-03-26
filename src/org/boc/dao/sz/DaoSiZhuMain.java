package org.boc.dao.sz;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuMain {
  private DaoCalendar daoC;
  private DaoYiJingMain daoY;

  public DaoSiZhuMain() {
    daoC = new DaoCalendar();
    daoY = new DaoYiJingMain();
  }

  public DaoCalendar getDaoCalendar() {
  	return daoC;
  }
  /**
   * 排小运
   * @param str
   */
  public void getQiZhuOut(StringBuffer str) {
    //排小运
    getXiaoYun();
    //得到7柱，即四柱+大运+小运+流年
    getQiZhu();
    //排7柱天干十神
    str.append("\n");
    str.append(SiZhu.XYNAME);
    str.append("\n");

    for(int i=SiZhu.LNBEGIN; i<SiZhu.QIZHU.length; i++) {
      str.append(daoY.getRepeats(" ", YiJing.INTER[4]));
      for(int j=1; j<SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          str.append(daoY.getRepeats(" ", YiJing.INTER[4] + 4));
        }else{
          str.append(getTGShen(SiZhu.QIZHU[i][j][1]) +
                     daoY.getRepeats(" ", YiJing.INTER[4] + 2));
        }
      }
      str.append("\n");
      //排7柱干支
      String ll = "";
      if(i<10)
        ll = "0"+i+"岁：";
      else
        ll = i + "岁：";
      str.append(ll+daoY.getRepeats(" ", YiJing.INTER[4]-ll.getBytes().length));
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] > 0) {
          str.append(YiJing.TIANGANNAME[SiZhu.QIZHU[i][j][1]] +
                     YiJing.DIZINAME[SiZhu.QIZHU[i][j][2]] +
                     daoY.getRepeats(" ", YiJing.INTER[4]));
        }else{
          str.append(daoY.getRepeats(" ", YiJing.INTER[4]+4));
        }
      }
      str.append("\n");
      //排7柱地支循藏、循藏十神
      int[] zcg ;
      String zc, zs;
      String _zcOut="", _zsOut="";
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          _zcOut += pCenter(_zcOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
          _zsOut += pCenter(_zsOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
        }else{
          zc = "";
          zs = "";
          zcg = SiZhu.DZXUNCANG[SiZhu.QIZHU[i][j][2]];
          for (int k = 0; k < zcg.length; k++) {
            zc += YiJing.TIANGANNAME[zcg[k]];
            zs += getTGShen(zcg[k]);
          }
          _zcOut += pCenter(_zcOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), zc);
          _zsOut += pCenter(_zsOut.getBytes().length,
                            YiJing.INTER[4] * j + 2 + 4 * (j - 1), zs);
        }
      }
      str.append(_zcOut);
      str.append("\n");
      str.append(_zsOut);
      str.append("\n");
      //输出7柱各天干在各支中的旺衰
      String gws="", _s="";
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
        }else{
          gws = SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.nz]];
          gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.yz]];
          gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.rz]];
          gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.sz]];
          if(j>=5)
            gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.QIZHU[i][j][1]][SiZhu.QIZHU[i][j][2]]];
          _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), gws);
        }
      }
      str.append(_s);
      str.append("\n");
      //输出7柱各支的神煞
      String tgShen ,dzShen;
      String _sShen ="";
      for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
        if(SiZhu.QIZHU[i][j][1] == 0) {
          _sShen += pCenter(_sShen.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
        }else{
          tgShen = getShenSha1(SiZhu.QIZHU[i][j][1], true, j);
          dzShen = getShenSha1(SiZhu.QIZHU[i][j][2], false, j);
          if(j<5) {
            tgShen += getShenSha2(j, true);
            dzShen += getShenSha2(j, false);
          }else{
            tgShen += getShenSha3(SiZhu.QIZHU[i][j][1], SiZhu.QIZHU[i][j][2], true);
            dzShen += getShenSha3(SiZhu.QIZHU[i][j][1], SiZhu.QIZHU[i][j][2], false);
          }
          _sShen += pCenter(_sShen.getBytes().length, YiJing.INTER[4] * j + 2 + 4 * (j - 1), tgShen + "|" + dzShen);
        }
      }
      str.append(_sShen);
      str.append("\n");
      //输出各柱的纳音
    String nayin = "";
    String _sn = "",_ss = "";
    for (int j = 1; j < SiZhu.QIZHU[1].length; j++) {
      if(j==1) _ss = "年柱：";
        if(j==2) _ss = "月柱：";
          if(j==3) _ss = "日柱：";
            if(j==4) _ss = "时柱：";
              if(j==5) _ss = "大运：";
              if(j==6) _ss = "小运：";
                if(j==7) _ss = "流年：";
      if(SiZhu.QIZHU[i][j][1] == 0) {
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * j + 2 + 4 * (j - 1), "");
      }else{
        nayin = _ss + SiZhu.NAYIN[SiZhu.QIZHU[i][j][1]][SiZhu.QIZHU[i][j][2]];
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * j + 2 + 4 * (j - 1), nayin);
      }
    }
    str.append(_sn);
    str.append("\n");
    str.append("\n");
    }
  }


  /**
   * 排大运
   * @param str
   */
  public void getDaYunOut(StringBuffer str) {
    //得到大运
    getDaYun();
    //排大运天干十神
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[4]));
    for(int i=1; i<=8; i++) {
      str.append(getTGShen(SiZhu.DAYUN[i][1]) +
                 daoY.getRepeats(" ", YiJing.INTER[4] + 2));
    }
    str.append("\n");
    //排大运干支
    str.append(SiZhu.DYNAME+daoY.getRepeats(" ", YiJing.INTER[4]-SiZhu.DYNAME.getBytes().length));
    for(int i=1; i<=8; i++) {
      str.append(YiJing.TIANGANNAME[SiZhu.DAYUN[i][1]] +
                 YiJing.DIZINAME[SiZhu.DAYUN[i][2]] +
                 daoY.getRepeats(" ", YiJing.INTER[4]));
    }
    str.append("\n");
    //排地支循藏、排循藏十神
    int[] zcg ;
    String zc, zs;
    String _zcOut="", _zsOut="";
    for(int i=1; i<=8; i++) {
      zc="";
      zs="";
      zcg = SiZhu.DZXUNCANG[SiZhu.DAYUN[i][2]];
      for (int j = 0; j < zcg.length; j++) {
        zc += YiJing.TIANGANNAME[zcg[j]];
        zs += getTGShen(zcg[j]);
      }
      _zcOut += pCenter(_zcOut.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), zc);
      _zsOut += pCenter(_zsOut.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), zs);
    }
    str.append(_zcOut);
    str.append("\n");
    str.append(_zsOut);
    str.append("\n");
    //输出各天干在各支中的旺衰
    String gws="", _s="";
    for(int i=1; i<=8; i++) {
      gws = SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.nz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.yz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.rz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.sz]];
      gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.DAYUN[i][1]][SiZhu.DAYUN[i][2]]];
      _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), gws);
    }
    str.append(_s);
    str.append("\n");
    //输出各支的神煞
    String tgShen ,dzShen;
    String _sShen ="";
    for(int i=1; i<=8; i++) {
      tgShen = getShenSha1(SiZhu.DAYUN[i][1], true, i);
      tgShen += getShenSha3(SiZhu.DAYUN[i][1],SiZhu.DAYUN[i][2], true);
      dzShen = getShenSha1(SiZhu.DAYUN[i][2], false, i);
      dzShen += getShenSha3(SiZhu.DAYUN[i][1],SiZhu.DAYUN[i][2], false);
      _sShen += pCenter(_sShen.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), tgShen+"|"+dzShen);
    }
    str.append(_sShen);
    str.append("\n");
    //得到起运的天数,三天折合一岁计，即一天折合四个月，一小时折合五天
    //如果是直接录入八字，则不用排起运天数
    if(Calendar.YEARN>0 && Calendar.MONTHN>0 && Calendar.DAYN>0 && Calendar.HOUR>0) {
      int days = getQiYunSui();
      int nian = days / 3;
      int yue = days % 3 * 4;
      String _s1 = "", _s2 = "";
      for (int i = 1; i <= 8; i++) {
        if (yue > 0)
          _s1 = nian + (i - 1) * 10 + "岁" + yue + "月";
        else
          _s1 = nian + (i - 1) * 10 + "岁";
        _s2 +=
            pCenter(_s2.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1),
                    _s1);
      }
      str.append(_s2);
      str.append("\n");
    }
    //输出各柱的纳音
    String nayin = "";
    String _sn = "",_ss = "";
    for(int i=1; i<SiZhu.DAYUN.length; i++) {
      if(SiZhu.DAYUN[i][1] == 0) {
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        nayin = _ss + SiZhu.NAYIN[SiZhu.DAYUN[i][1]][SiZhu.DAYUN[i][2]];
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), nayin);
      }
    }
    str.append(_sn);
    str.append("\n");

  }

  /**
   * 得到起运天数
   */
  public int getQiYunSui() {
    int days = daoC.getDiffDates();
    SiZhu.QIYUNSUI = days / 3;
    return days;
  }

  /**
   * 排四柱及命宫胎元
   * @param str
   */
  public void getSiZhuTaiMingOut(StringBuffer str) {
    //得到六柱
    int[][] liu = getSixZhu();
    //排六柱天干十神
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[4]));
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        str.append(daoY.getRepeats(" ", YiJing.INTER[4] + 4));
      }else{
        if(i==3)
          str.append(getTGShen(0) +
                     daoY.getRepeats(" ", YiJing.INTER[4] + 2));
        else
          str.append(getTGShen(liu[i][1]) +
                     daoY.getRepeats(" ", YiJing.INTER[4] + 2));
      }
    }
    str.append("\n");
    //排六柱干支
    str.append(SiZhu.SZNAME+daoY.getRepeats(" ", YiJing.INTER[4]-SiZhu.DYNAME.getBytes().length));
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        str.append(daoY.getRepeats(" ", YiJing.INTER[4]+4));
      }else{
        str.append(YiJing.TIANGANNAME[liu[i][1]] +
                   YiJing.DIZINAME[liu[i][2]] +
                   daoY.getRepeats(" ", YiJing.INTER[4]));
      }
    }
    str.append("\n");
    //排地支循藏、排循藏十神
    int[] zcg ;
    String zc, zs;
    String _zcOut="", _zsOut="";
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        _zcOut += pCenter(_zcOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
        _zsOut += pCenter(_zsOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        zc = "";
        zs = "";
        zcg = SiZhu.DZXUNCANG[liu[i][2]];
        for (int j = 0; j < zcg.length; j++) {
          zc += YiJing.TIANGANNAME[zcg[j]];
          zs += getTGShen(zcg[j]);
        }
        _zcOut += pCenter(_zcOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), zc);
        _zsOut += pCenter(_zsOut.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), zs);
      }
    }
    str.append(_zcOut);
    str.append("\n");
    str.append(_zsOut);
    str.append("\n");
    //输出各天干在各支中的旺衰
    String gws="", _s="";
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        gws = SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.nz]];
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.yz]];
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.rz]];
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][SiZhu.sz]];
        if(i>=5)
        gws += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[liu[i][1]][liu[i][2]]];
        _s += pCenter(_s.getBytes().length, YiJing.INTER[4] * i + 2 + 4 * (i - 1), gws);
      }
    }
    str.append(_s);
    str.append("\n");
    //输出各支的神煞
    String tgShen ,dzShen;
    String _sShen ="";
    for(int i=1; i<liu.length; i++) {
      if(liu[i][1] == 0) {
        _sShen += pCenter(_sShen.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        tgShen = getShenSha1(liu[i][1], true, i);
        tgShen += getShenSha2(i, true);
        dzShen = getShenSha1(liu[i][2], false, i);
        dzShen += getShenSha2(i, false);
        _sShen += pCenter(_sShen.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), tgShen + "|" + dzShen);
      }
    }
    str.append(_sShen);
    str.append("\n");
    //输出各柱的纳音
    String nayin = "";
    String _sn = "",_ss = "";
    for(int i=1; i<liu.length; i++) {
      if(i==1) _ss = "年柱：";
        if(i==2) _ss = "月柱：";
          if(i==3) _ss = "日柱：";
            if(i==4) _ss = "时柱：";
              if(i==6) _ss = "胎元：";
                if(i==7) _ss = "命宫：";
      if(liu[i][1] == 0) {
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), "");
      }else{
        nayin = _ss + SiZhu.NAYIN[liu[i][1]][liu[i][2]];
        _sn += pCenter(_sn.getBytes().length,
                    YiJing.INTER[4] * i + 2 + 4 * (i - 1), nayin);
      }
    }
    str.append(_sn);
    str.append("\n");


  }

  /**
   * 输出阴阳历
   * @param type 阴历为真，阳历为假
   * @param yuen 只有为阴历时此参数才有效
   * @param isMan 是男人还是女人
   */
  public void getDateOut(int y, int m, int d, int h, int mi,
                         boolean type, boolean yun, boolean isMan,
                         int sheng, int shi,
                         StringBuffer str) {
    daoC.calculate(y, m, d, h, mi, type, yun, sheng, shi);
    getTaiYuan();
    getMingGong();
    SiZhu.ISMAN = isMan;
    SiZhu.sex = isMan ? "乾造：" : "坤造：";
    //SiZhu.sex += "("+SiZhu.NAYIN[SiZhu.ng][SiZhu.nz]+")";
    String nayin = SiZhu.NAYIN[SiZhu.ng][SiZhu.nz];
    if(nayin.indexOf("金")!=-1) SiZhu.NAYININT = 1;
      if(nayin.indexOf("木")!=-1) SiZhu.NAYININT = 2;
        if(nayin.indexOf("水")!=-1) SiZhu.NAYININT = 3;
          if(nayin.indexOf("火")!=-1) SiZhu.NAYININT = 4;
            if(nayin.indexOf("土")!=-1) SiZhu.NAYININT = 5;
    str.append(SiZhu.sex);
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(SiZhu.yinli);
    str.append("\n");
    str.append(daoY.getRepeats(" ", YiJing.INTER[0]));
    str.append(SiZhu.yangli);
    str.append("\n");
  }

  /**
   * 输出阴阳历
   * @param type 阴历为真，阳历为假
   * @param yuen 只有为阴历时此参数才有效
   * @param isMan 是男人还是女人
   */
  public void getDateOut(int y, int m, int d, int h, int mi,
                         boolean type, boolean yun, boolean isMan,
                         int sheng, int shi) {
    daoC.calculate(y, m, d, h, mi, type, yun, sheng, shi);
    SiZhu.ISMAN = isMan;
  }


  /**
   * 输出阴阳历
   * @param type 阴历为真，阳历为假
   * @param yuen 只有为阴历时此参数才有效
   * @param isMan 是男人还是女人
   */
  public void getDateOut(int ng, int nz, int yg, int yz, int rg, int rz,
                         int sg, int sz, boolean yun, boolean isMan,
                         StringBuffer str) {
    Calendar.YUN = yun;
    SiZhu.ISMAN = isMan;
    SiZhu.sex = isMan ? "乾造：" : "坤造：";
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;
    getTaiYuan();
    getMingGong();
    String nayin = SiZhu.NAYIN[SiZhu.ng][SiZhu.nz];
    if(nayin.indexOf("金")!=-1) SiZhu.NAYININT = 1;
      if(nayin.indexOf("木")!=-1) SiZhu.NAYININT = 2;
        if(nayin.indexOf("水")!=-1) SiZhu.NAYININT = 3;
          if(nayin.indexOf("火")!=-1) SiZhu.NAYININT = 4;
            if(nayin.indexOf("土")!=-1) SiZhu.NAYININT = 5;
    str.append(SiZhu.sex);
    str.append("\n");
  }

  /**
   * 输出阴阳历
   * @param type 阴历为真，阳历为假
   * @param yuen 只有为阴历时此参数才有效
   * @param isMan 是男人还是女人
   */
  public void getDateOut(int ng, int nz, int yg, int yz, int rg, int rz,
                         int sg, int sz, boolean yun, boolean isMan) {
    Calendar.YUN = yun;
    SiZhu.ISMAN = isMan;
    SiZhu.sex = isMan ? "乾造：" : "坤造：";
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;
  }

  /**
   * 居中输出
   * @return
   */
  private String pCenter(int left, int center, String str) {
    int len = str.getBytes().length;
    return daoY.getRepeats(" ",center-left-len/2)+str;
  }

  /**
   * 以干或支返回其神煞，必须去掉同一支出现两个相同的神煞
   * 1为阳男阴女，2为阴女阳男
   * @param gz 天干或地支
   * @param type true为干、false为支
   * @param local 干或支的位置
   */
  private String getShenSha1(int gz, boolean type, int local) {
    String str = "";
    int sex = getSexChar();

    if(type) {
      if(SiZhu.TIANDE[1][SiZhu.yz][gz]) str += "天"+SiZhu.SPLIT;
      if(SiZhu.YUEDE[SiZhu.yz][gz]) str += "月"+SiZhu.SPLIT;
      if(SiZhu.DEXIU[SiZhu.yz][1][gz]) str += "德"+SiZhu.SPLIT;
      if(SiZhu.DEXIU[SiZhu.yz][2][gz]) str += "秀"+SiZhu.SPLIT;
      return str;
    }else{
      if(SiZhu.TIANYI[SiZhu.rg][gz]) str += "乙"+SiZhu.SPLIT;
      if(SiZhu.TAIJI[SiZhu.rg][gz]) str += "极"+SiZhu.SPLIT;
      if(SiZhu.TIANDE[2][SiZhu.yz][gz]) str += "天"+SiZhu.SPLIT;
      if(SiZhu.FUXING[SiZhu.ng][gz]) str += "福"+SiZhu.SPLIT;
      if(SiZhu.FUXING[SiZhu.rg][gz] && str.indexOf("福")==-1) str += "福"+SiZhu.SPLIT;
      if(SiZhu.WENCANG[SiZhu.ng][gz]) str += "昌"+SiZhu.SPLIT;
      if(SiZhu.WENCANG[SiZhu.rg][gz] && str.indexOf("昌")==-1) str += "昌"+SiZhu.SPLIT;
      if(SiZhu.GUOYIN[SiZhu.ng][gz]) str += "印"+SiZhu.SPLIT;
      if(SiZhu.WENCANG[SiZhu.rg][gz] && str.indexOf("印")==-1) str += "印"+SiZhu.SPLIT;
      if(SiZhu.YIMA[SiZhu.nz][gz]) str += "马"+SiZhu.SPLIT;
      if(SiZhu.YIMA[SiZhu.rz][gz] && str.indexOf("马")==-1) str += "马"+SiZhu.SPLIT;
      if(SiZhu.HUAGAI[SiZhu.nz][gz] && local!=1) str += "华"+SiZhu.SPLIT;
      if(SiZhu.HUAGAI[SiZhu.rz][gz] && str.indexOf("华")==-1 && local!=3) str += "华"+SiZhu.SPLIT;
      if(SiZhu.JIANG[SiZhu.nz][gz] && local!=1) str += "将"+SiZhu.SPLIT;
      if(SiZhu.JIANG[SiZhu.rz][gz] && str.indexOf("将")==-1 && local!=3) str += "将"+SiZhu.SPLIT;
      if(SiZhu.JINYU[SiZhu.rg][gz]) str += "舆"+SiZhu.SPLIT;
      if(SiZhu.TIANYI1[SiZhu.yz][gz]) str += "医"+SiZhu.SPLIT;
      if(SiZhu.LUSHEN[SiZhu.rg][gz]) str += "禄"+SiZhu.SPLIT;
      if(SiZhu.YANGREN[SiZhu.rg][gz]) str += "刃"+SiZhu.SPLIT;
      if(SiZhu.WANGSHEN[SiZhu.nz][gz]) str += "亡";
      if(SiZhu.WANGSHEN[SiZhu.rz][gz] && str.indexOf("亡")==-1) str += "亡"+SiZhu.SPLIT;
      if(SiZhu.JIESHA[SiZhu.nz][gz]) str += "劫"+SiZhu.SPLIT;
      if(SiZhu.JIESHA[SiZhu.rz][gz] && str.indexOf("劫")==-1) str += "劫"+SiZhu.SPLIT;
      if(SiZhu.ZAISHA[SiZhu.nz][gz]) str += "灾"+SiZhu.SPLIT;
      if(SiZhu.GOUSHA[sex][SiZhu.nz][gz]) str += "勾"+SiZhu.SPLIT;
      if(SiZhu.JIAOSHA[sex][SiZhu.nz][gz]) str += "绞"+SiZhu.SPLIT;
      if(SiZhu.TIANLUO[SiZhu.NAYININT][1][gz][1]) str += "罗"+SiZhu.SPLIT;
      if(SiZhu.TIANLUO[SiZhu.NAYININT][2][gz][1]) str += "网"+SiZhu.SPLIT;
      if(SiZhu.YUANCHEN[sex][SiZhu.nz][gz]) str += "元"+SiZhu.SPLIT;
      if(daoY.isXunKong(gz,SiZhu.rg,SiZhu.rz)) str += "空"+SiZhu.SPLIT;
      if(SiZhu.XIANCI[SiZhu.nz][gz]) str += "花"+SiZhu.SPLIT;
      if(SiZhu.XIANCI[SiZhu.rz][gz] && str.indexOf("花")==-1) str += "花"+SiZhu.SPLIT;
    }

    return str;
  }

  /**
   * 返回其神煞,专为四柱定做
   * @param zhu 1为年柱、2月柱、3日柱、4时柱
   * @param type true为干、false为支
   */
  private String getShenSha2(int zhu, boolean type) {
    String str = "";

    if(zhu==1) {
      if(type) {
        if (SiZhu.SHANJI[SiZhu.ng][SiZhu.yg][SiZhu.rg]) {
          if (str.indexOf("奇") == -1)
            str += "奇"+SiZhu.SPLIT;
        }
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.ng][SiZhu.nz])
          str += "堂"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.ng][SiZhu.nz])
          str += "馆"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.ng][SiZhu.nz] &&
            str.indexOf("馆") == -1)
          str += "馆"+SiZhu.SPLIT;

      }else{
        if (SiZhu.SHANJI[SiZhu.nz][SiZhu.yz][SiZhu.rz]) {
          if (str.indexOf("奇") == -1)
            str += "奇"+SiZhu.SPLIT;
        }
        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.nz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.nz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.nz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.nz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.nz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.nz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
      }
    }else if(zhu==2) {
      if(type) {
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.yg][SiZhu.yz])
          str += "堂"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.yg][SiZhu.yz])
          str += "馆"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.yg][SiZhu.yz] &&
            str.indexOf("馆") == -1)
          str += "馆"+SiZhu.SPLIT;

      }else{
        if (SiZhu.SHANJI[SiZhu.yz][SiZhu.rz][SiZhu.sz]) {
          if (str.indexOf("奇") == -1)
            str += "奇"+SiZhu.SPLIT;
        }
        if (SiZhu.SHANJI[SiZhu.yg][SiZhu.rg][SiZhu.sg]) {
          if (str.indexOf("奇") == -1)
            str += "奇"+SiZhu.SPLIT;
        }
        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.nz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.nz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.yz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.yz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.yz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.yz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.GCGX[1][SiZhu.nz][SiZhu.yz])
          str += "孤"+SiZhu.SPLIT;
        if (SiZhu.GCGX[2][SiZhu.nz][SiZhu.yz])
          str += "寡"+SiZhu.SPLIT;
      }
    }else if(zhu==3) {
      if(type) {
        if (SiZhu.KUIGANG[SiZhu.rg][SiZhu.rz])
          str += "魁"+SiZhu.SPLIT;
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.rg][SiZhu.rz])
          str += "堂"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.rg][SiZhu.rz])
          str += "馆"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.rg][SiZhu.rz] &&
          str.indexOf("馆") == -1)
        str += "馆"+SiZhu.SPLIT;
        if (SiZhu.JINSHEN[SiZhu.rg][SiZhu.rz])
          str += "金"+SiZhu.SPLIT;
        if (SiZhu.GONGLU[SiZhu.rg][SiZhu.rz][SiZhu.sg][SiZhu.sz])
          str += "拱"+SiZhu.SPLIT;
        if (SiZhu.TIANSE[SiZhu.yz][SiZhu.rg][SiZhu.rz])
          str += "赦"+SiZhu.SPLIT;
        if (SiZhu.DABAI[SiZhu.rg][SiZhu.rz])
          str += "败"+SiZhu.SPLIT;
        if (SiZhu.GULUAN[SiZhu.rg][SiZhu.rz] && SiZhu.GULUAN[SiZhu.sg][SiZhu.sz])
          str += "鸾"+SiZhu.SPLIT;
        if (SiZhu.YYCACUO[SiZhu.rg][SiZhu.rz])
          str += "错"+SiZhu.SPLIT;
        if (SiZhu.SIFEI[SiZhu.yz][SiZhu.rg][SiZhu.rz])
          str += "废"+SiZhu.SPLIT;

      }else{

        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.rz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.rz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.nz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.nz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.rz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.rz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.GCGX[1][SiZhu.nz][SiZhu.rz])
          str += "孤"+SiZhu.SPLIT;
        if (SiZhu.GCGX[2][SiZhu.nz][SiZhu.rz])
          str += "寡"+SiZhu.SPLIT;
      }
    }else if(zhu==4) {
      if(type) {
        if (SiZhu.XUETANG[SiZhu.NAYININT][SiZhu.sg][SiZhu.sz])
          str += "堂"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.ng][SiZhu.sg][SiZhu.sz])
          str += "馆"+SiZhu.SPLIT;
        if (SiZhu.CIGUAN[SiZhu.rg][SiZhu.sg][SiZhu.sz] &&
            str.indexOf("馆") == -1)
          str += "馆"+SiZhu.SPLIT;
        if (SiZhu.JINSHEN[SiZhu.rg][SiZhu.rz])
          str += "金"+SiZhu.SPLIT;
        if (SiZhu.GONGLU[SiZhu.rg][SiZhu.rz][SiZhu.sg][SiZhu.sz])
          str += "拱"+SiZhu.SPLIT;
        if (SiZhu.GULUAN[SiZhu.rg][SiZhu.rz] && SiZhu.GULUAN[SiZhu.sg][SiZhu.sz])
          str += "鸾"+SiZhu.SPLIT;
      }else{
        if (SiZhu.TIANLUO[0][1][SiZhu.yz][SiZhu.sz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.yz][SiZhu.sz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.rz][SiZhu.sz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.rz][SiZhu.sz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][1][SiZhu.sz][SiZhu.nz] && str.indexOf("罗")==-1)
          str += "罗"+SiZhu.SPLIT;
        if (SiZhu.TIANLUO[0][2][SiZhu.sz][SiZhu.nz] && str.indexOf("网")==-1)
          str += "网"+SiZhu.SPLIT;
        if (SiZhu.GCGX[1][SiZhu.nz][SiZhu.sz])
          str += "孤"+SiZhu.SPLIT;
        if (SiZhu.GCGX[2][SiZhu.nz][SiZhu.sz])
          str += "寡"+SiZhu.SPLIT;
      }
    }
    return str;
  }

  /**
   * 返回其神煞,专为大运或小运柱定做，但用不上
   * @param zhu 1为年柱、2月柱、3日柱、4时柱
   */
  private String getShenSha3(int g, int z, boolean type) {
    String str = "";

    if(type) {
      if (SiZhu.JINSHEN[g][z])
        str += "金"+SiZhu.SPLIT;
      if (SiZhu.TIANSE[SiZhu.yz][g][z])
        str += "赦"+SiZhu.SPLIT;
      if (SiZhu.DABAI[g][z])
        str += "败"+SiZhu.SPLIT;
      if (SiZhu.YYCACUO[g][z])
        str += "错"+SiZhu.SPLIT;
      if (SiZhu.SIFEI[SiZhu.yz][g][z])
        str += "废"+SiZhu.SPLIT;
      if (SiZhu.KUIGANG[g][z])
        str += "魁"+SiZhu.SPLIT;
      if (SiZhu.XUETANG[SiZhu.NAYININT][g][z])
        str += "堂"+SiZhu.SPLIT;
      if (SiZhu.CIGUAN[SiZhu.ng][g][z])
        str += "馆"+SiZhu.SPLIT;
      if (SiZhu.CIGUAN[SiZhu.rg][g][z] &&
          str.indexOf("馆") == -1)
        str += "馆"+SiZhu.SPLIT;
    }else{
      if (SiZhu.TIANLUO[0][1][z][SiZhu.sz] && str.indexOf("罗")==-1)
        str += "罗"+SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.sz] && str.indexOf("网")==-1)
        str += "网" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][1][z][SiZhu.nz] && str.indexOf("罗")==-1)
        str += "罗" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.nz] && str.indexOf("网")==-1)
        str += "网" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][1][z][SiZhu.yz] && str.indexOf("罗")==-1)
        str += "罗" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.yz] && str.indexOf("网")==-1)
        str += "网" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][1][z][SiZhu.rz] && str.indexOf("罗")==-1)
        str += "罗" + SiZhu.SPLIT;
      if (SiZhu.TIANLUO[0][2][z][SiZhu.rz] && str.indexOf("网")==-1)
        str += "网" + SiZhu.SPLIT;
      if (SiZhu.GCGX[1][SiZhu.nz][z])
        str += "孤"+SiZhu.SPLIT;
      if (SiZhu.GCGX[2][SiZhu.nz][z])
        str += "寡"+SiZhu.SPLIT;
    }
    return str;
  }


  /**
   * 得到1为阳男阴女、2为阴男阳女
   * @return
   */
  public int getSexChar() {
    int zyy = SiZhu.ZIYINYANG[SiZhu.nz];
    int sex = 0;

    if((zyy == 1 && SiZhu.ISMAN) || (zyy == 0 && !SiZhu.ISMAN)) {
      sex = 1;
    }else {
      sex = 2;
    }
    return sex;
  }

  /**
   * 排大运
   */
  public void getDaYun() {
    int sex = getSexChar();
    int j=0;
    int g = SiZhu.yg+10;
    int z = SiZhu.yz+12;

    if(sex==1) {
      while(++j<=8) {
        SiZhu.DAYUN[j][1] = (++g) % 10 == 0 ? 10 : g % 10;
        SiZhu.DAYUN[j][2] = (++z) % 12 == 0 ? 12 : z % 12;
      }
    }else{
      while(++j<=8) {
        SiZhu.DAYUN[j][1] = (--g) % 10 == 0 ? 10 : g % 10;
        SiZhu.DAYUN[j][2] = (--z) % 12 == 0 ? 12 : z % 12;
      }
    }
  }

  /**
   * 排小运
   */
  public void getXiaoYun() {
    int sex = getSexChar();
    int j=0;
    int g = SiZhu.sg+100;
    int z = SiZhu.sz+120;

    if(sex==1) {
      while(++j<=SiZhu.LNEND-1) {
        SiZhu.XIAOYUN[j][1] = (++g) % 10 == 0 ? 10 : g % 10;
        SiZhu.XIAOYUN[j][2] = (++z) % 12 == 0 ? 12 : z % 12;
      }
    }else{
      while(++j<=SiZhu.LNEND-1) {
        SiZhu.XIAOYUN[j][1] = (--g) % 10 == 0 ? 10 : g % 10;
        SiZhu.XIAOYUN[j][2] = (--z) % 12 == 0 ? 12 : z % 12;
      }
    }
  }

  /**
   * 排七柱，细批流年
   * 年柱 月柱 日柱 时柱 大运 小运 流年
   * 小运为虚岁，大运以起运岁为准
   */
  public void getQiZhu() {
    int dy = 0;

    for(int i=1; i<SiZhu.LNEND; i++) {
      SiZhu.QIZHU[i][1][1] = SiZhu.ng;
      SiZhu.QIZHU[i][1][2] = SiZhu.nz;

      SiZhu.QIZHU[i][2][1] = SiZhu.yg;
      SiZhu.QIZHU[i][2][2] = SiZhu.yz;

      SiZhu.QIZHU[i][3][1] = SiZhu.rg;
      SiZhu.QIZHU[i][3][2] = SiZhu.rz;

      SiZhu.QIZHU[i][4][1] = SiZhu.sg;
      SiZhu.QIZHU[i][4][2] = SiZhu.sz;

      if(i -1 >= SiZhu.QIYUNSUI) {
        dy = ((i-1-SiZhu.QIYUNSUI)/10)+1;
        SiZhu.QIZHU[i][5][1] = SiZhu.DAYUN[dy][1];
        SiZhu.QIZHU[i][5][2] = SiZhu.DAYUN[dy][2];
      }else{
        SiZhu.QIZHU[i][5][1] = 0;
        SiZhu.QIZHU[i][5][2] = 0;
      }

      SiZhu.QIZHU[i][6][1] = SiZhu.XIAOYUN[i][1];
      SiZhu.QIZHU[i][6][2] = SiZhu.XIAOYUN[i][2];;

      SiZhu.QIZHU[i][7][1] = (SiZhu.ng + i - 1) % 10 == 0 ? 10 : (SiZhu.ng + i - 1) % 10;
      SiZhu.QIZHU[i][7][2] = (SiZhu.nz + i - 1) % 12 == 0 ? 12 : (SiZhu.nz + i - 1) % 12;
    }
  }

  /**
   * 排六柱
   * 年柱 月柱 日柱 时柱 胎元 命宫
   * 小运为虚岁，大运以起运岁为准
   */
  public int[][] getSixZhu() {
    int[][] liuzhu = new int[8][3];

    liuzhu[1][1] = SiZhu.ng;
    liuzhu[1][2] = SiZhu.nz;

    liuzhu[2][1] = SiZhu.yg;
    liuzhu[2][2] = SiZhu.yz;

    liuzhu[3][1] = SiZhu.rg;
    liuzhu[3][2] = SiZhu.rz;

    liuzhu[4][1] = SiZhu.sg;
    liuzhu[4][2] = SiZhu.sz;

    liuzhu[6][1] = SiZhu.tyg;
    liuzhu[6][2] = SiZhu.tyz;

    liuzhu[7][1] = SiZhu.mgg;
    liuzhu[7][2] = SiZhu.mgz;

    return liuzhu;
  }

  /**
   * 得到天干神
   * @param g
   */
  public String getTGShen(int g) {
    return SiZhu.SHISHEN[SiZhu.TGSHISHEN[SiZhu.rg][g]];
  }

  /**
   * 得到胎元
   */
  public void getTaiYuan() {
    SiZhu.tyg = (SiZhu.yg+1)%10 == 0 ? 10 : (SiZhu.yg+1)%10;
    SiZhu.tyz = (SiZhu.yz+3)%12 == 0 ? 12 : (SiZhu.yz+3)%12;
  }

  /**
   * 得到命宫
   * 起地支 <=4为4-y, >4为4-y+12
   */
  public void getMingGong() {
    //命宫从子逆推
    int[] mg = {0,1,12,11,10,9,8,7,6,5,4,3,2,1,0};
    //得到生月(应是农历的节气月份),将生时落在生月上顺数至卯
    if(SiZhu.yz >= 3) SiZhu.mgz = mg[SiZhu.yz - 2];
    if(SiZhu.yz < 3) SiZhu.mgz = mg[SiZhu.yz + 10];
    if(SiZhu.sz <= 4)
      SiZhu.mgz += 4 - SiZhu.sz;
    else
      SiZhu.mgz += 16 - SiZhu.sz;
    SiZhu.mgz = SiZhu.mgz%12==0?12:SiZhu.mgz%12;
    //得到命宫干
    int z = SiZhu.mgz;
    int g = SiZhu.yueByNian[SiZhu.ng];
    if(z < YiJing.YIN)
      z += 12;
    SiZhu.mgg = (g+z-3)%10 == 0 ? 10 : (g+z-3)%10;
  }

}
