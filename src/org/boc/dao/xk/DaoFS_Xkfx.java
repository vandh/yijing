package org.boc.dao.xk;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.Xuank;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.util.Messages;

public class DaoFS_Xkfx {
  private DaoYiJingMain daoyj;
  private DaoPublic pub;
  private DaoCalendar daoc ;
  private StringBuffer str;
  private int[][] gInfo;

  public DaoFS_Xkfx() {
    daoyj = new DaoYiJingMain();
    pub = new DaoPublic();
    daoc = new DaoCalendar();
    str = new StringBuffer();
    gInfo = new int[10][13];
  }

  public String getHead(int y3, int iJy,int sx,int[] n4, int[] mz,
                        int[] syfx, boolean[] yyjy) {
    try{
      this.getAllOfGong(y3, iJy, sx, syfx, yyjy);
      //for(int i=0; i<gInfo.length; i++) {
      //  for(int j=0; j<gInfo[0].length; j++) {
      //    System.out.print(gInfo[i][j]+"  ");
      //  }
      //  System.out.println("\r\n");
      //}

      int yun = getYun(y3, iJy);
      String yy39 = getYuan(yun);

      String str = " 信息：建于";
      str += yy39 + "，" + Xuank.s24name[sx] + "，地运" + getDiYun(sx, yun) + "年。";
      str += "\r\n     ";

      str += "格局：" + this._getGeju();
      if (gInfo[0][10] > 0)
        str += "；城门在" + Xuank.s24name2[gInfo[0][9]];
      if (gInfo[0][10] > 0 && gInfo[0][12] > 0)
        str += Xuank.s24name2[gInfo[0][11]];
      if (gInfo[0][10] == 0 && gInfo[0][12] > 0)
        str += "；城门在" + Xuank.s24name2[gInfo[0][11]];

      str += this._getHs();
      str += this._getSbg();
      str += this._getFfl(true, true);
      str += this._getFfl(true, false);
      str += this._getFfl(false, true);
      str += this._getFfl(false, false);
      int dj = this.getDj();
      if (dj == 9)
        str += "；离宫打劫局";
      else if (dj == 1)
        str += "；坎宫打劫局";
      else if (dj == 3)
        str += "；三般巧卦";
      str += "。";

      return str;
    }catch(Exception e) {
      e.printStackTrace();
      Messages.error("DaoFs_Xkfx("+e+")");
    }
    return null;
  }

  public String pp(int y3, int iJy,int sx,int[] n4, int[] mz,
                   int[] syfx, boolean[] yyjy) {
    int yun = getYun(y3, iJy);
    String s = "";
    str.delete(0, str.length());
    String kg = "    ";
    String[] _desc = getDesc();

    out2("4A                           9A                           2A");
    out1("┏━━━━━━━━┳━━━━━━━━┳━━━━━━━━┓");
    out1("┃41┃91┃21┃"+kg+_desc[1]);
    out1("┃42┃92┃22┃"+kg+_desc[2]);
    out1("┃43┃93┃23┃"+kg+_desc[3]);
    out1("┃44┃94┃24┃"+kg+_desc[4]);
    out1("┃45┃95┃25┃"+kg+_desc[5]);
    out1("┃46┃96┃26┃"+kg+_desc[6]);
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫"+kg+_desc[7]);
    out1("┃31┃51┃71┃"+kg+_desc[8]);
    out1("┃32┃52┃72┃"+kg+_desc[9]);
    out2("3A┃33┃53┃73┃7A"+"  "+_desc[10]);
    out1("┃34┃54┃74┃"+kg+_desc[11]);
    out1("┃35┃55┃75┃"+kg+_desc[12]);
    out1("┃36┃56┃76┃"+kg+_desc[13]);
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫"+kg+_desc[14]);
    out1("┃81┃11┃61┃"+kg+_desc[15]);
    out1("┃82┃12┃62┃"+kg+_desc[16]);
    out1("┃83┃13┃63┃"+kg+_desc[17]);
    out1("┃84┃14┃64┃"+kg+_desc[18]);
    out1("┃85┃15┃65┃"+kg+_desc[19]);
    out1("┃86┃16┃66┃"+kg+_desc[20]);
    out1("┗━━━━━━━━┻━━━━━━━━┻━━━━━━━━┛"+kg+_desc[21]);
    out2("8A                           1A                           6A"+"  "+_desc[22]);

    for(int i=23; i<_desc.length; i++) {
      if(_desc[i]==null || _desc[i].trim().equals("")) {
        break;
      }else{
        out1(daoyj.getRepeats(" ",56)+kg+_desc[i]);
      }

    }

    s = str.toString();

    //1. 排地盘
    for(int i = 1; i<=9; i++) {
      if(i==5)
        s = s.replaceAll("55",format("中五"));
      else
        s = s.replaceAll(i+"5",format(QiMen.dpjg[i]));
    }
    //2. 排年月日时刻飞星盘，此处只排当前时间
    for(int i = 1; i<=9; i++) {
      s = s.replaceAll(i+"4",format(gInfo[i][4] + "-" + gInfo[i][5]+"  "+
                                    gInfo[i][6] + "-" + gInfo[i][7]));
    }

    //3. 排生气 煞 旺 死 排运盘 九星 九神
    for(int i = 1; i<=9; i++) {
      s = s.replaceAll(i+"3",format(Xuank.jxws2[gInfo[i][8]]+"  "+
                                    Xuank.jiux[gInfo[i][1]]));
    }

    //4. 排山盘 水盘
    for(int i = 1; i<=9; i++) {
      s = s.replaceAll(i+"2",format(gInfo[i][2]+"  "+gInfo[i][3]));
    }

    //5. 排城门
    int[] zygong = this.getZygua(sx);
    int[] zycm = this.getZycm(sx,yun);
    for(int i=1; i<zygong.length; i++) {
      if(zygong[i]>0 && zycm[2*i]>0)
        s = s.replaceAll(zygong[i]+"1",
                         format(Xuank.s24name2[zycm[2*i-1]]
                                +Xuank.cmfh+
                                Xuank.jiux4[zycm[2*i]]));
    }

    //6. 加上箭头
    int jt = getGongByS24(sx);
    for(int i=0; i<=9; i++) {
      if((10-jt) == i) {
        s = s.replaceAll("" + i + "A", Xuank.fuhao[jt][2]);
        s = s.replaceAll("" + jt + "A", Xuank.fuhao[jt][1]);
      }
    }
    for(int i=0; i<=9; i++) {
      for(int j=0; j<=6; j++) {
        s = s.replaceAll(""+i+"A", "  ");
      }
    }

    //7. 替换所有的数字
    for(int i=0; i<=9; i++) {
      for(int j=0; j<=6; j++) {
        s = s.replaceAll(""+i+j, format(""));
      }
    }

    return s;
  }

  /**
   * 分析
   */
  public String analyse(int y3, int iJy,int iwx,int[] n4, int[] mz,
                               int[] syfx, boolean[] yyyun) {
    return "";
  }
  /**
   * 择日
   */
  public String getZrHead(int[] syfx, boolean[] yyyun) {
    String s = "";
    //123456年月日干支，78为卯旬，9为何宫，10为原日干，
    //11,12,13,14为该月哪4天,15-17为原阳历年月日，18-20为原阴历年月日
    int[] wtj = this.wtjZr(syfx,yyyun[4],yyyun[1]);
    s += "天元乌兔经：";

    s+="\r\n    阳历："+wtj[15]+"-"+wtj[16]+"-"+wtj[17]+" "+syfx[4]+":"+syfx[5]+":"+syfx[6];
    s+="\r\n    阴历："+wtj[18]+"年"+wtj[19]+"月初"+wtj[20]+" "+syfx[4]+":"+syfx[5]+":"+syfx[6];
    s+="\r\n    朔月："+YiJing.TIANGANNAME[wtj[1]]+YiJing.DIZINAME[wtj[2]]+" "+
        YiJing.TIANGANNAME[wtj[3]]+YiJing.DIZINAME[wtj[4]]+" "+
        YiJing.TIANGANNAME[wtj[5]]+YiJing.DIZINAME[wtj[6]];
    s+="\r\n    起宫："+YiJing.TIANGANNAME[wtj[7]]+YiJing.DIZINAME[wtj[8]]+
        (wtj[22]==1?"顺数":"逆数")+"，"+YiJing.DIZINAME[wtj[21]]+
        "上即"+YiJing.DIZINAME[wtj[6]]+"，自"+QiMen.dpjg[wtj[9]]+"宫起初一。";
    s+="\r\n    吉日：本月太阳吉日有"+
        (wtj[11]<=0?"":"、初"+wtj[11])+
        "、初"+wtj[12]+
        (wtj[13]>30?"":"、初"+wtj[13])+
        (wtj[14]>30?"":"、初"+wtj[14])+"日。";
    String[] scname = {"","子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
    int[] sc = this.wtjZs(wtj[10]);
    int gong = 0; //第8宫为何宫
    if(sc[1]==1) {
        gong = (8-sc[0]+9) % 9 == 0 ? 9 : (8-sc[0]+9) % 9;
      }else{
        gong = (sc[0] - 8+9) % 9 == 0 ? 9 : (sc[0] - 8+9) % 9;
      }
    s+="\r\n    良辰：本日太阳时有"+
        scname[1+gong]+(1+gong+9>12?"":","+scname[(1+gong+9)])+"时。";

    return s;
  }

  public String wtjPP(int[] syfx, boolean yun, boolean yy) {
    String s = "";
    str.delete(0, str.length());
    //123456年月日干支，78为卯旬，9为何宫，1011121314为该月哪4天
    int[] wtj = this.wtjZr(syfx,yun,yy);

    out1("┏━━━━━━━━┳━━━━━━━━┳━━━━━━━━┓");
    out1("┃41┃91┃21┃");
    out1("┃42┃92┃22┃");
    out1("┃403┃903┃203┃");
    out1("┃44┃94┃24┃");
    out1("┃45┃95┃25┃");
    out1("┃46┃96┃26┃");
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫");
    out1("┃31┃51┃71┃");
    out1("┃32┃52┃72┃");
    out1("┃303┃503┃703┃");
    out1("┃34┃54┃74┃");
    out1("┃35┃55┃75┃");
    out1("┃36┃56┃76┃");
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫");
    out1("┃81┃11┃61┃");
    out1("┃82┃12┃62┃");
    out1("┃803┃103┃603┃");
    out1("┃84┃14┃64┃");
    out1("┃85┃15┃65┃");
    out1("┃86┃16┃66┃");
    out1("┗━━━━━━━━┻━━━━━━━━┻━━━━━━━━┛");
    s = str.toString();

    //排神盘
    String[] s9 = {"","水星","太阴星","木星","计都星","土星","罗侯星","金星","太阳星","火星"};
    for(int i = 1; i<=9; i++) {
      s = s.replaceAll(i+"4",format(s9[i]));
    }

    //一卦有何支
    for(int i = 1; i<=9; i++) {
      s = s.replaceAll(i+"6",format(QiMen.guaOfZi2[i]));
    }

    //排地盘
    for(int i = 1; i<=9; i++) {
      if(i==5)
        continue;
      s = s.replaceAll(i+"5",format(QiMen.dpjg[i]));
    }

    //排时辰
    int[] sc = wtjZs(wtj[10]);
    int gong = 0;
    String syl = null;
    int yl=1;
    String[] scname = {"","子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
    for(int i = 0; i<=8; i++) {
      if(sc[1]==1) {
        gong = (sc[0] + i) % 9 == 0 ? 9 : (sc[0] + i) % 9; //1 2 3
      }else{
        gong = (sc[0] - i+9) % 9 == 0 ? 9 : (sc[0] - i+9) % 9; //3 2 1
      }
      syl = scname[yl]+(yl+9>12?"":","+scname[(yl+9)]);
      s = s.replaceAll(gong+"2",format(syl));
      yl++;
    }

    //在太阳宫加上星标志
    s = s.replaceAll("81", format(Xuank.cmfh));

    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        s = s.replaceAll(""+i+j, format(""));
      }
    }

    //排人盘即初几
    gong = 0;
    syl = null;
    yl=1;
    for(int i = 0; i<=8; i++) {
      gong = (wtj[9]+i)%9==0?9:(wtj[9]+i)%9;
      syl = yl+","+(yl+9)+","+(yl+18)+(yl+27>30?"":","+(yl+27));
      s = s.replaceAll(gong+"03",format(syl));
      yl++;
    }

    return s;

  }

  /**
   * 按天元乌兔经择时
   * 0-何宫 1-1顺10逆
   */
   private int[] wtjZs(int rg) {
     if(rg==YiJing.JIA || rg==YiJing.JI) {
       return new int[]{1,1};
     }else if(rg==YiJing.YI || rg==YiJing.GENG) {
       return new int[]{7,0};
     }else if(rg==YiJing.BING || rg==YiJing.XIN) {
       return new int[]{3,0};
     }else if(rg==YiJing.DING || rg==YiJing.REN) {
       return new int[]{9,1};
     }else{
       return new int[]{5,1};
     }
   }

  /**
   * 按天元乌兔经择日
   * 如果传2005.2.1公历，农历是2005.1.11，但干支是2004.12，但取农历2005.1.1是最接近的
   * @param int[] 12345678年月日干支，78为卯旬，9为何宫，10为原日干，
   * 11,12,13,14为该月哪4天,15-17为原阳历年月日，18-20为原阴历年月日
   * 21何支对应何宫，22该卯之顺逆
   */
  private int[] wtjZr(int[] sj, boolean yun, boolean isyy) {
    int[] sizhu1 = daoc.getSiZhu(sj[1],sj[2],sj[3],sj[4],1,yun,isyy);  //当前年月日的阴历
    int[] sizhu = daoc.getSiZhu(sizhu1[12],sizhu1[13],1,1,1,yun,true);  //当月阴历第1天
    int[] x = getWumao(sizhu[5],sizhu[6]);
    int z = sizhu[6];  //当前日支
    int sn = 0; //顺1逆0
    if(x[0]==YiJing.YI || x[0]==YiJing.XIN) {
      z = (1-z+YiJing.MAO+12)%12==0?12:(1-z+YiJing.MAO+12)%12;
    }else{
      sn = 1;
      z = (z-1+YiJing.MAO)%12==0?12:(z-1+YiJing.MAO)%12;
    }
    int gua = QiMen.ziOfGua[z];
    int cha = (8-gua+9)%9==0?9:(8-gua+9);
    if(cha==9) {
      return new int[] {0,sizhu[1],sizhu[2],sizhu[3],sizhu[4],sizhu[5],sizhu[6],
          x[0],x[1],gua,sizhu1[6],1, 10, 19, 28,
          sizhu1[9],sizhu1[10],sizhu1[11],sizhu1[12],sizhu1[13],sizhu1[14],
          z,sn};
    }
    else {
      return new int[]{0,sizhu[1],sizhu[2],sizhu[3],sizhu[4],sizhu[5],sizhu[6],
          x[0], x[1], gua, sizhu1[6],1 + cha -9, 10 + cha-9, 19 + cha-9, 19+cha,
          sizhu1[9],sizhu1[10],sizhu1[11],sizhu1[12],sizhu1[13],sizhu1[14],
          z,sn};
    }
  }

  private int[] getWumao(int rg, int rz) {
    int x = rz-YiJing.MAO<0?rz+12-YiJing.MAO:rz-YiJing.MAO;
    int g = (rg-x+10)%10==0?10:(rg-x+10)%10;
    return new int[]{g,YiJing.MAO};
  }


  /**
   * 1为父母三般卦 2为连珠三般卦 0不是
   */
  private String _getSbg() {
    String str = "";
    int index;
    int[] hs = new int[10];
    int j = 0;
    String _s = "";
    int k = 0;
    String sbgname = null;

    for(index=1; index<=9; index++) {
      if(k==0)
        k = getSbg(index);
      if (getSbg(index) > 0)
        hs[j++] = index;
    }

    if(k==1) sbgname = "父母三般卦";
    else if(k==2) sbgname = "连珠三般卦";

    if(j >= 9) {
      str = "；满盘"+sbgname;
    }else if(j>0){
      for(int i=0; i<hs.length; i++) {
        if(hs[i]>0)
            _s += Xuank.jiux4[hs[i]]+"、";
      }
      _s = _s.substring(0, _s.length() -1);
      str = "；第"+_s+"宫"+sbgname;
    }

    return str;
  }

  private String _getFfl(boolean sx,boolean ff) {
    String str = "";
    int index;
    int[] hs = new int[10];
    int j = 0;
    String _s = "";
    String _sxff = sx?"山星":"向星";
    _sxff += ff?"反呤":"伏呤";

    for(index=1; index<=9; index++) {
      if(index==5)
        continue;
      if (this.isFfl(sx,ff,index))
        hs[j++] = index;
    }
    if(j >= 8) {
      str = "；满盘"+_sxff;
    }else if(j>0){
      for(int i=0; i<hs.length; i++) {
        if(hs[i]>0)
            _s += Xuank.jiux4[hs[i]]+"、";
      }
      _s = _s.substring(0, _s.length() -1);
      str = "；第"+_s+"宫"+_sxff;
    }

    return str;
  }

  private String _getHs() {
    String str = "";
    int index;
    int[] hs = new int[10];
    int j = 0;
    String _s = "";
    for(index=1; index<=9; index++) {
      if (isHs(index))
        hs[j++] = index;
    }
    if(j >= 9) {
      str = "；满盘合十";
    }else if(j>0){
      for(int i=0; i<hs.length; i++) {
        if(hs[i]>0)
            _s += Xuank.jiux4[hs[i]]+"、";
      }
      _s = _s.substring(0, _s.length() -1);
      str = "；第"+_s+"宫合十";
    }

    return str;
  }


  /**
   * 大的格局
   */
  private String _getGeju() {
    if(this.isWswx())
      return "旺山旺向";
    if(this.isSxzx())
      return "双星在向";
    if(this.isSxzz())
      return "双星在坐";
    if(this.isSsxs())
      return "上山下水";
    if(this.isRq(true))
      return "山星入囚";
    if(this.isRq(false))
      return "向星入囚";

    return "";
  }

  /**
   * 得到时辰飞星
   */
  private int[] getHourX(int y, int m, int d, int h, boolean isYun, boolean yytype) {
    int[] rs = new int[5];
    int sfx = 0;
    int[] rfxs = {0, 1,4,7, 1,4,7, 1,4,7, 1,4,7};
    int[] rfxn = {0, 9,6,3, 9,6,3, 9,6,3, 9,6,3};

    int jq[] = pub.getJieQi(y, m, d, h, 0, isYun, yytype);
    int jqname = jq[1];
    int[] nyrfx = this.getDayX(y,m,d,isYun,yytype);
    int[] sizhu = daoc.getSiZhu(y,m,d,h,0,isYun,yytype);
    int rz = sizhu[6];
    int ss = sizhu[8] - 1;

    //冬至 一阳生，顺行
    if((jqname >= 22 && jqname <=24) || (jqname>=1 && jqname <=9)) {
      sfx = (ss+rfxs[rz]) % 9 == 0 ? 9 : (ss+rfxs[rz]) % 9;
    }else{
      //夏至一阴生，逆行
      sfx = (rfxn[rz] - ss + 90) % 9 == 0 ? 9 : (rfxn[rz] - ss + 90) % 9;
    }

    rs[1] = nyrfx[1];
    rs[2] = nyrfx[2];
    rs[3] = nyrfx[3];
    rs[4] = sfx;

    return rs;
  }

  /**
   * 得到流日飞星
   * 22,23,24,1节气 甲子 = 一白
   * 2，3，4，5， = 7
   * 6，7 8 9 = 4
   * 10 11 12 13  = 9
   * 14 15 16 17  = 3
   * 18 19 20 21 =6
   * 旬支-干：0，10，8，6，4，2
   */
  private int[] getDayX(int y, int m, int d, boolean isYun, boolean yytype) {
    int[] rs = new int[4];
    int[] jqfx = {0, 1, 7,7,7,7, 4,4,4,4, 9,9,9,9, 3,3,3,3, 6,6,6,6, 1,1,1}; //各节气甲子日的飞星
    int[] xun = {0, 0, 50,0,40,0,30,0,20,0,10};

    int jq[] = pub.getJieQi(y, m, d, 12, 0, isYun, yytype);
    int jqname = jq[1];

    int[] sizhu = daoc.getSiZhu(y,m,d,12,0,isYun,yytype);
    int[] nyfx = getMonthX(sizhu[15],sizhu[4]);
    int rg = sizhu[5];
    int rz = sizhu[6];
    int ts = xun[(rz-rg+12)%12] + rg - YiJing.JIA; //得到距离甲子的天数

    int rfx = 0;
    //冬至 一阳生，顺行
    if((jqname >= 22 && jqname <=24) || (jqname>=1 && jqname <=9)) {
      rfx = (ts+jqfx[jqname]) % 9 == 0 ? 9 : (ts+jqfx[jqname]) % 9;
    }else{
    //夏至一阴生，逆行
      rfx = (jqfx[jqname] - ts + 90) % 9 == 0 ? 9 : (jqfx[jqname] - ts + 90) % 9;
    }

    rs[1] = nyfx[1];
    rs[2] = nyfx[2];
    rs[3] = rfx;

    return rs;
  }

  /**
   * 得到流月飞星
   * @param year 论节气的年
   * @param month 是月的地支
   */
  private int[] getMonthX(int year, int month) {
    int nz = (year-Calendar.IYEAR+Calendar.IYEARZ)%12==0?12:(year-Calendar.IYEAR+Calendar.IYEARZ)%12;
    int[] nyx = {0, 8,5,2,8,  5,2,8,5, 2,8,5,2};
    int yf = month>2?month-2:month+10;

    return new int[]{0, getYearX(year),(nyx[nz]+1-yf+9)%9==0?9:(nyx[nz]+1-yf+9)%9};
  }

  /**
   * 得到流年飞星
   * @param year 年份，以论节气年为准，2004.2.1=2004正月十一=2003年地支
   */
  private int getYearX(int year) {
    if(year>=1864)
      return (10 - ((year-1864)%9) + 9000)%9==0?9:(10 - ((year-1864)%9) + 9000)%9;
    else
      return (1865-year)%9==0?9:(1865-year)%9;
  }

  /**
   * 在八宫图中央输出天地人神盘
   * @param o
   * @return
   */
  private String format(String o) {
    if(o==null)
      o="";
    if(o.trim().equals("null"))
       o="";

    int len = o.getBytes().length;
    int hlen = (16-len)/2;
    return (daoyj.getRepeats(" ",hlen)+o+daoyj.getRepeats(" ",(16-len-hlen)));
  }

  /**
   * 输出九宫图
   * @param o
   */
  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  private void out2(Object o) {
    str.append("  "+o.toString()+"\r\n");
  }

  /**
   * 由年份得到三元九运中的何运
   * @return
   */
  private int getYuanYun(int year) {
    return ((year - 1864)/20 + 1);
  }

  /**
   * 由几运(或几星)和几宫，顺排还是逆排得到何星
   */
  private int getGongfx(int yun, int gong, boolean bl) {
    if(bl)
      return Xuank.jiux3[(gong+yun-5+9)%9==0?9:(gong+yun-5+9)%9];
    else
      return Xuank.jiux3[(5+90-gong+yun)%9==0?9:(5+90-gong+yun)%9];
  }

  /**
   * 由24山得到属何卦，即何宫，以后天八卦数为准
   * @param shan 即24山之序号
   */
  private int getGongByS24(int shan) {
    return Xuank.s24Ofgua[shan];
  }

  /**
   * 由24山向星得到其山星和水星
   * @param shan 即24山之序号
   */
  private int[] getSXfx(int shan,int yun, boolean jian) {
    int zg = getGongByS24(shan);
    int xg = 10 - zg;
    int zuo = getGongfx(yun, zg, true);
    int xiang = getGongfx(yun, xg, true);
    int zuo1 = 0, xiang1 = 0;

    if(jian) {
      zuo1 = zuo;
      xiang1 = xiang;
      if(zuo!=5)
        zuo = Xuank.s24jianx[Xuank.gua8yl[zuo][Xuank.s24yl[shan]]];
      if(xiang!=5)
        xiang = Xuank.s24jianx[Xuank.gua8yl[xiang][Xuank.s24yl[shan]]];
    }

    return new int[]{0,zuo,xiang, zuo1, xiang1};
  }

  /**
   * 由24山向星得到其所飞临之宫对应的无龙阴阳
   * 因辰山戌向之元龙属性皆一样，故不求戌了
   * 因24山之山向阴阳属性一样，故不用求对山
   * @param shan 即24山之序号
   */
  private boolean[] getSXfxYY(int shan,int yun, boolean jian) {
    int[] sxfx = getSXfx(shan,yun,jian);
    return this._getSXfxYY(shan,yun,jian,sxfx);
  }

  /**
   * 山向飞星顺逆依落宫飞星所在后天八卦之同位元龙阴阳定，如为5则按山向取阴阳
   * 城门遇5，则不依山向阴阳，而依城门所在宫同位元龙阴阳
   */
  private boolean[] _getSXfxYY(int shan,int yun, boolean jian,
                               int[] sxfx) {
    boolean syy = false;
    boolean xyy = false;
    //山向遇5以山向论阴阳，兼向遇替后为5以原山向论阴阳
    //城门遇落宫为5则以落宫同位元龙论阴阳
    if(sxfx[3]==5 || sxfx[1]==5) {
      syy = Xuank.s24yy[shan];
    }else{
      syy = Xuank.s24yy[Xuank.gua8yl[sxfx[1]][Xuank.s24yl[shan]]];
      if(jian)
        syy = Xuank.s24yy[Xuank.gua8yl[sxfx[3]][Xuank.s24yl[shan]]];
    }
    if(sxfx[4]==5 || sxfx[2]==5) {
      xyy = Xuank.s24yy[shan];
    }else{
      xyy = Xuank.s24yy[Xuank.gua8yl[sxfx[2]][Xuank.s24yl[shan]]];
      if(jian)
        xyy = Xuank.s24yy[Xuank.gua8yl[sxfx[4]][Xuank.s24yl[shan]]];
    }

    return new boolean[]{false,syy,xyy};
  }


  /**
   * 得到该卦的左右卦
   */
  private int[] getZygua(int shan) {
    int xgong = 10 - getGongByS24(shan);
    int len = Xuank.guaCx.length;
    int cx = 0;

    for(cx=0; cx<len; cx++) {
      if(xgong==Xuank.guaCx[cx])
        break;
    }

    return new int[]{0,
        Xuank.guaCx[cx-1<0?len-1:cx-1],
        Xuank.guaCx[cx+1>=len?0:cx+1]};
  }

  /**
   * 得到左右城门
   * //丑山未向 1运 左城门5入中，依未阴逆飞则错误，依坐宫离之地元龙丙阳正确
   //壬山丙向 6运 左城门5入中，依丙阳顺飞则正确，依坐宫巽之地元龙辰阴错误
   * 1 = 1,2
   * 2 = 3,4
   * 2x-1, 2x
   */
  private int[] getZycm(int shan, int yun) {
    int[] zygong = this.getZygua(shan);
    int[] rs = new int[5];

    for(int i=1; i<zygong.length; i++) {
      rs[i] = getGongfx(yun, zygong[i],true);
    }
    boolean[] yy = new boolean[3]; //this._getSXfxYY(shan,yun,false,rs);
    //城门遇落宫为5则以落宫同位元龙论阴阳
    if(rs[1]==5) {
      yy[1] = Xuank.s24yy[Xuank.gua8yl[zygong[1]][Xuank.s24yl[shan]]];
    }else{
      yy[1] = Xuank.s24yy[Xuank.gua8yl[rs[1]][Xuank.s24yl[shan]]];
    }
    if(rs[2]==5) {
      yy[2] = Xuank.s24yy[Xuank.gua8yl[zygong[2]][Xuank.s24yl[shan]]];
    }else{
      yy[2] = Xuank.s24yy[Xuank.gua8yl[rs[2]][Xuank.s24yl[shan]]];
    }

    int cmy1 = this.getGongfx(rs[1],zygong[1],yy[1]);
    int cmy2 = this.getGongfx(rs[2],zygong[2],yy[2]);

    int cms1 = Xuank.gua8yl[zygong[1]][Xuank.s24yl[shan]];
    int cms2 = Xuank.gua8yl[zygong[2]][Xuank.s24yl[shan]];

    return new int[]{0,cms1,(cmy1>=yun && cmy1<=yun+2)?cmy1:0,
        cms2, (cmy2>=yun && cmy2<=yun+2)?cmy2:0};
  }

  /**
   * 由建造年份或元运返回准确元运
   */
  private int getYun(int y3, int iJy){
    int yun = 0;
    if(iJy!=0)
      yun = iJy;
    else
      yun = this.getYuanYun(y3);

    return yun;
  }

  /**
   * 由运得到是何元
   */
  private String getYuan(int yun) {
    if(yun>=1 && yun<=3)
      return "上元"+Xuank.jiux4[yun]+"运";
    if(yun>=4 && yun<=6)
      return "中元"+Xuank.jiux4[yun]+"运";
    if(yun>=7 && yun<=9)
      return "下元"+Xuank.jiux4[yun]+"运";

    return "";
  }

  /**
   * 得到地运，山向定，则地运不依元运而变
   */
  private int getDiYun(int shan, int yun) {
    int xx = this.getSXfx(shan,yun,false)[2];
    return ((xx+9-yun)%9 * 20);
  }

  /**
   * 每宫的全部信息
   * 00运 01山卦 02向卦 03山星 04向星 05山阴阳 06向阴阳 07向左卦 08向右卦
   * 09左城门 010右城门
   * 9>=i>=1：i1-运星 i2-山星 i3-水星 i4-年星 i5-月星 i6-日星 i7-辰星
   * i8-旺生煞死退
   */
  private void getAllOfGong(int y3, int iJy,int sx,
                           int[] syfx, boolean[] yyjy) {
    //运盘 排旺生煞死退
    int yun = getYun(y3, iJy);

    gInfo[0][0] = yun;

    for(int i = 1; i<=9; i++) {
      gInfo[i][1] = getGongfx(yun,i,true);
      gInfo[i][8] = Xuank.jxws[i];
    }

    //排山盘 水盘
    int sxgua = this.getGongByS24(sx);
    gInfo[0][1] = sxgua;
    gInfo[0][2] = 10 - sxgua;
    int[] sxfx = this.getSXfx(sx,yun,yyjy[2]);
    gInfo[0][3] = sxfx[1];
    gInfo[0][4] = sxfx[2];
    boolean[] sxfxyy = this.getSXfxYY(sx,yun,yyjy[2]);
    gInfo[0][5] = sxfxyy[1]?1:0;
    gInfo[0][6] = sxfxyy[2]?1:0;
    for(int i = 1; i<=9; i++) {
      gInfo[i][2] = getGongfx(sxfx[1],i,sxfxyy[1]);
      gInfo[i][3] = getGongfx(sxfx[2],i,sxfxyy[2]);
    }
    int[] zygua = this.getZygua(sx);
    int[] zycm  = this.getZycm(sx,yun);
    gInfo[0][7] = zygua[1];
    gInfo[0][8] = zygua[2];
    gInfo[0][9] =  zycm[1];
    gInfo[0][10] = zycm[2];
    gInfo[0][11] =  zycm[3];
    gInfo[0][12] = zycm[4];

    //排年月日时刻飞星盘，前方不传则排当前，否则排传来时间
    if(syfx==null || syfx[1]<=0) {
      java.util.Date d = new java.util.Date();
      int y = d.getYear() + 1900;
      int m = d.getMonth() + 1;
      int day = d.getDate();
      int h = d.getHours();
      int mi = d.getMinutes();
      syfx = getHourX(y, m, day, h, false, false);
    }else{
      syfx = getHourX(syfx[1],syfx[2],syfx[3],syfx[4],yyjy[1],yyjy[4]);
    }
    for(int i = 1; i<=9; i++) {
      gInfo[i][4] = getGongfx(syfx[1],i,true);
      gInfo[i][5] = getGongfx(syfx[2],i,true);
      gInfo[i][6] = getGongfx(syfx[3],i,true);
      gInfo[i][7] = getGongfx(syfx[4],i,true);
    }
  }

  /**
   * 旺山旺向
   */
  private boolean isWswx() {
    return (gInfo[gInfo[0][1]][2] == gInfo[0][0] &&
            gInfo[gInfo[0][2]][3] == gInfo[0][0]);
  }

  /**
   * 双星在向
   */
  private boolean isSxzx() {
    return (gInfo[gInfo[0][2]][2] == gInfo[0][0] &&
            gInfo[gInfo[0][2]][3] == gInfo[0][0]);
  }

  /**
   * 双星在坐
   */
  private boolean isSxzz() {
    return (gInfo[gInfo[0][1]][2] == gInfo[0][0] &&
            gInfo[gInfo[0][1]][3] == gInfo[0][0]);
  }

  /**
   * 上山下水
   */
  private boolean isSsxs() {
    return (gInfo[gInfo[0][1]][3] == gInfo[0][0] &&
            gInfo[gInfo[0][2]][2] == gInfo[0][0]);
  }

  /**
   * 是否打劫
   * @param args 0没有 9为离宫打劫局936 1坎宫打劫局147 3三般巧卦258
   */
  private int getDj() {
    if(isDj(2,5,8)) return 3;
    if(isDj(3,6,9)) return 9;
    if(isDj(1,4,7)) return 1;

    return 0;
  }

  private boolean isDj(int g1,int g2,int g3) {
    if((pub.xyzMaking(gInfo[g1][2],gInfo[g2][2],gInfo[g3][2],1,7,12) &&
        pub.xyzMaking(gInfo[g1][3],gInfo[g2][3],gInfo[g3][3],1,7,12)) ||
       (pub.xyzMaking(gInfo[g1][2],gInfo[g2][2],gInfo[g3][2],2,8,15) &&
        pub.xyzMaking(gInfo[g1][3],gInfo[g2][3],gInfo[g3][3],2,8,15)) ||
       (pub.xyzMaking(gInfo[g1][2],gInfo[g2][2],gInfo[g3][2],3,9,18) &&
        pub.xyzMaking(gInfo[g1][3],gInfo[g2][3],gInfo[g3][3],3,9,18))) {
      return true;
    }
    return false;
  }

  /**
   * 合十大吉，可化解上山下水格局
   */
  private boolean isHs(int gong) {
    return (gInfo[gong][1]+gInfo[gong][2]==10 ||
            gInfo[gong][1]+gInfo[gong][0]==10 ||
            gInfo[gong][0]+gInfo[gong][2]==10);
  }

  /**
   * 是否山星或向星入囚
   * @param sx true为山星
   */
  private boolean isRq(boolean sx) {
    if(sx)
      return gInfo[5][2] == gInfo[0][0];
    else
      return gInfo[5][3] == gInfo[0][0];
  }

  /**
   * 是否反伏呤，风水多不利，但逢三般卦打劫，能逢凶化吉
   * 当运星伏呤增强力量好，祸星则力更强凶
   * @param sx true为山星
   * @param ff true为反呤
   */
  private boolean isFfl(boolean sx, boolean ff, int gong) {
    if(sx) {
      if(ff) {
        return gInfo[gong][2]==10 - gong;
      }else{
        return gInfo[gong][2]==gong;
      }
    }else{
      if(ff) {
        return gInfo[gong][3]==10 - gong;
      }else{
        return gInfo[gong][3]==gong;
      }
    }
  }

  /**
   * 是否形成三般卦
   * @return 1为父母三般卦 2为连珠三般卦 0不是
   */
  private int getSbg(int g) {
    if(pub.xyzMaking(gInfo[g][1],gInfo[g][2],gInfo[g][3],1,7,12) ||
       pub.xyzMaking(gInfo[g][1],gInfo[g][2],gInfo[g][3],2,8,15) ||
       pub.xyzMaking(gInfo[g][1],gInfo[g][2],gInfo[g][3],3,9,18)) {
      return 1;
    }

    int max = pub.getMax(gInfo[g][1],gInfo[g][2],gInfo[g][3]);
    int min = pub.getMin(gInfo[g][1],gInfo[g][2],gInfo[g][3]);
    int mid = (gInfo[g][1]+gInfo[g][2]+gInfo[g][3]) - max - min ;
    if((mid-min==1 && max-mid==1) ||
       (max==9 && min==1 && (mid==2 || mid==8))) {
      return 2;
    }
    return 0;
  }

  /**
   * 得到总的各宫的描述
   * @return
   */
  private String[] getDesc() {
    String[] rs = new String[50];
    int sn = 1;
    for(int i=1; i<=9; i++) {
      if(i==5)
        rs[sn++] = "中五宫：";
      else
        rs[sn++] = QiMen.dpjg[i]+"宫：";
      //流年九星吉凶
      rs[sn++] = "    "+Xuank.x2g[gInfo[i][4]][i];
      //山向组合吉凶
      rs[sn++] = "    "+Xuank.s2s[gInfo[i][2]][gInfo[i][3]];
    }

    return rs;
  }

  public static void main(String[] args) {
    DaoFS_Xkfx dao = new DaoFS_Xkfx();
    int dy = dao.getDiYun(2,5);
    System.out.println(dy);
  }
}
