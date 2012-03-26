package org.boc.dao;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.Liuren;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;


public class DaoLiuren {
  private DaoYiJingMain daoyj;
  private DaoCalendar daoc;
  private StringBuffer str;
  private DaoPublic pub;
  private int[][] gInfo;
  private String gGeju;

  public DaoLiuren() {
    daoyj = new DaoYiJingMain();
    daoc = new DaoCalendar();
    str = new StringBuffer();
    pub = new DaoPublic();
    /**
     * 00-月将 01-昼1夜0 02-贵人
     * 11-18: 年月日时干支
     * 20-25: 性别0女1男，年命干，支、行年干、行支
     * 31-312:各地盘之天盘
     * 41-48: 四课地天盘 49-412：四课天将
     * 51-53: 三传干支 54-56:三传天将 57-59:三传遁干 510-512:六亲
     * 61-612:十二天将
     */
    gInfo = new int[10][13];
    gGeju = "";
  }

  /**
   * 地盘 12地支
   * 天盘 12地支
   * 神盘 10神
   */
  public String getBody() {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;
    String _s1 = "";

    //吉凶格
    String _s[] = new String[50];//getJiXiongOfGong();
    for(int i=1; i<_s.length; i++) {
      if(_s[i]==null) _s[i]="";
    }

    out1("    C16  C17  C18  C19"+kg+_s[index+1]);
    out1("    B16  B17  B18  B19"+kg+_s[index+2]);
    out1("C15  B15          B20  C20"+kg+_s[index+3]);
    out1("C14  B14          B21  C21"+kg+_s[index+4]);
    out1("    B13  B12  B11  B22"+kg+_s[index+5]);
    out1("    C13  C12  C11  C22"+kg+_s[index+6]);
    out1("                  "+kg+_s[index+7]);
    out1("    D22  D21  D20  D19"+kg+_s[index+8]);
    out1("    D18  D16  D14  D12"+kg+_s[index+9]);
    out1("    D17  D15  D13  D11"+kg+_s[index+10]);
    out1("                  "+kg+_s[index+11]);
    out1("  E1  F1  G1  H1"+kg+_s[index+12]);
    out1("  E2  F2  G2  H2"+kg+_s[index+13]);
    out1("  E3  F3  G3  H3"+kg+_s[index+14]);
    out1("                  "+kg+_s[index+15]);
    //将多余的吉凶格显示在此
    for(int i=index+16; i<50; i++) {
      if(_s[i]==null || _s[i].trim().equals("")) {
        break;
      }else{
        out1(daoyj.getRepeats(" ",22)+kg+_s[i]);
      }
    }

    _str = str.toString();

    //排局天盘
    for(int i=1; i<=12; i++) {
      _str = _str.replaceAll("B"+(i+10), YiJing.DIZINAME[gInfo[3][i]]);
    }
    //排局天将
    for(int i=1; i<=12; i++) {
      _str = _str.replaceAll("C"+(i+10), Liuren.tjname[gInfo[6][i]]);
    }
    //四课天将
    for(int i=1; i<=4; i++) {
      _str = _str.replaceAll("D"+(i+18), Liuren.tjname[gInfo[4][i+8]]);
    }
    //四课干支
    for(int i=1; i<=8; i++) {
      if(i==1)
        _s1 = YiJing.TIANGANNAME[gInfo[4][1]];
      else
        _s1 = YiJing.DIZINAME[gInfo[4][i]];
      _str = _str.replaceAll("D"+(i+10), _s1);
    }
    //三传六亲
    for(int i=1; i<=3; i++) {
      _str = _str.replaceAll("E"+i, YiJing.LIUQINNAME[gInfo[5][i+9]]);
    }
    //三传遁干
    for(int i=1; i<=3; i++) {
      _str = _str.replaceAll("F"+i, YiJing.TIANGANNAME[gInfo[5][i+6]]==null?"空":YiJing.TIANGANNAME[gInfo[5][i+6]]);
    }
    //三传干支
    for(int i=1; i<=3; i++) {
      _str = _str.replaceAll("G"+i, YiJing.DIZINAME[gInfo[5][i]]);
    }
    //三传天将
    for(int i=1; i<=3; i++) {
      _str = _str.replaceAll("H"+i, Liuren.tjname[gInfo[5][i+3]]);
    }
    //清掉内存中的垃圾信息
    clear();

    return _str;
  }

  private void clear() {
    for(int i=0; i<gInfo.length;i++) {
      for(int j=0;j<gInfo[i].length; j++) {
        gInfo[i][j] = 0;
      }
    }
    gGeju = "";
    str.delete(0,str.length());
  }

  public String getHead(int born, int y, int m, int d, int h,int mi,int ss,
                        boolean isYin,boolean isBoy, boolean isYun,
                        int iGr, int iZy, int iDg, int iYj){
    str.delete(0,str.length());
    String _s = "";
    String _s1 = "";
    //得到所有变量
    getGinfo(born,y,m,d,h,mi,ss,isYin,isBoy,isYun,iGr,iZy,iDg,iYj);

    //0 是否闰月  1-8 四柱 9-11 阳历年月日 12-14取的农历年不按节气 15-17按节气的
    int[] s = daoc.getSiZhu(y,m,d,h,mi,isYun,isYin);
    int[] jq = pub.getJieQi(y,m,d,h,mi, isYun, isYin);

    _s = this._getFs(iGr,iZy,iDg,iYj,s[8]);
    str.append("    方式："+_s+"\r\n");
    _s = s[9]+"-"+s[10]+"-"+s[11]+" "+h+":"+mi+":"+ss;
    _s1 = s[12]+"年"+s[13]+(s[0]>0 ? "闰":"") +"月"+"初"+s[14]+" "+h+":"+mi+":"+ss;
    str.append("    公历："+_s+"\r\n");
    str.append("    阴历："+_s1+"\r\n");

    _s = YiJing.TIANGANNAME[s[1]]+YiJing.DIZINAME[s[2]]+" "+
         YiJing.TIANGANNAME[s[3]]+YiJing.DIZINAME[s[4]]+" "+
         YiJing.TIANGANNAME[s[5]]+YiJing.DIZINAME[s[6]]+" "+
         YiJing.TIANGANNAME[s[7]]+YiJing.DIZINAME[s[8]];
    str.append("    干支："+_s+"\r\n");

    _s = pub.getXunKongOut(s[1],s[2])[0]+pub.getXunKongOut(s[1],s[2])[1]+ " "+
         pub.getXunKongOut(s[3],s[4])[0]+pub.getXunKongOut(s[3],s[4])[1]+ " "+
         pub.getXunKongOut(s[5],s[6])[0]+pub.getXunKongOut(s[5],s[6])[1]+ " "+
         pub.getXunKongOut(s[7],s[8])[0]+pub.getXunKongOut(s[7],s[8])[1];
    str.append("    旬空："+_s+"\r\n");

    _s = QiMen.JIEQI24[jq[1]]+" "+jq[2]+"年"+
                 (pub.isYunJieqi(jq[2], jq[1]) ? "闰":"") +
                 jq[3]+"月初"+ jq[4]+"日"+jq[5]+"时"+jq[6]+"分";
    str.append("    上节："+_s+"\r\n");

    _s = QiMen.JIEQI24[jq[7]]+" "+jq[8]+"年"+
                 (pub.isYunJieqi(jq[8], jq[7]) ? "闰":"") +
                 jq[9]+"月初"+jq[10]+"日"+jq[11]+"时"+jq[12]+"分";
    str.append("    下节："+_s+"\r\n");

    int bg = (born-Calendar.IYEAR+Calendar.IYEARG)%10==0?10:(born-Calendar.IYEAR+Calendar.IYEARG)%10;
    int bz = (born-Calendar.IYEAR+Calendar.IYEARZ)%12==0?12:(born-Calendar.IYEAR+Calendar.IYEARZ)%12;
    _s = born+"年生，性别"+(isBoy?"男":"女")+"，生年干支";
    _s += YiJing.TIANGANNAME[bg]+YiJing.DIZINAME[bz];
    str.append("    年命："+_s+"\r\n");

    int[] xn = this.getXingnian(born, isBoy);
    str.append("    行年：现"+xn[0]+"年，虚岁"+xn[1]+"，1岁起"+
               YiJing.TIANGANNAME[xn[2]]+YiJing.DIZINAME[xn[3]]+"，行年"+
               YiJing.TIANGANNAME[xn[4]]+YiJing.DIZINAME[xn[5]]+"\r\n");

    _s = YiJing.TIANGANNAME[s[5]]+YiJing.DIZINAME[s[6]]+"日"+
         YiJing.DIZINAME[s[8]]+"时";
     if(iYj>1)
       _s += "自选月将"+YiJing.DIZINAME[gInfo[0][0]]+"，"+gGeju;
     else
       _s += YiJing.DIZINAME[gInfo[0][0]]+"将，"+gGeju;
    str.append("    格局："+_s+"\r\n");

    return str.toString();
  }

  public String getHead(int bg, int bz, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                        boolean isYin,boolean isBoy, boolean isYun,
                        int iGr, int iZy, int iDg, int iYj){
    str.delete(0,str.length());
    String _s = "";
    //得到所有变量
    getGinfo(bg,bz,ng,nz,yg,yz,rg,rz,sg,sz,isYin,isBoy,isYun,iGr,iZy,iDg,iYj,0);
    _s = this._getFs(iGr,iZy,iDg,iYj,sz);
    str.append("    方式："+_s+"\r\n");

    _s = YiJing.TIANGANNAME[ng]+YiJing.DIZINAME[nz]+" "+
         YiJing.TIANGANNAME[yg]+YiJing.DIZINAME[yz]+" "+
         YiJing.TIANGANNAME[rg]+YiJing.DIZINAME[rz]+" "+
         YiJing.TIANGANNAME[sg]+YiJing.DIZINAME[sz];
    str.append("    干支："+_s+"\r\n");

    _s = pub.getXunKongOut(ng,nz)[0]+pub.getXunKongOut(ng,nz)[1]+ " "+
         pub.getXunKongOut(yg,yz)[0]+pub.getXunKongOut(yg,yz)[1]+ " "+
         pub.getXunKongOut(rg,rz)[0]+pub.getXunKongOut(rg,rz)[1]+ " "+
         pub.getXunKongOut(sg,sz)[0]+pub.getXunKongOut(sg,sz)[1];
    str.append("    旬空："+_s+"\r\n");

    int born = pub.getYear(bg,bz);
    _s = "可能"+born+"年生，性别"+(isBoy?"男":"女")+"，生年干支";
    _s += YiJing.TIANGANNAME[bg]+YiJing.DIZINAME[bz];
    str.append("    年命："+_s+"\r\n");

    int[] xn = this.getXingnian(born, isBoy);
    str.append("    行年：现"+xn[0]+"年，虚岁"+xn[1]+"，1岁起"+
               YiJing.TIANGANNAME[xn[2]]+YiJing.DIZINAME[xn[3]]+"，行年"+
               YiJing.TIANGANNAME[xn[4]]+YiJing.DIZINAME[xn[5]]+"\r\n");

    _s = YiJing.TIANGANNAME[rg]+YiJing.DIZINAME[rz]+"日"+
         YiJing.DIZINAME[sz]+"时";
     if(iYj>1)
       _s += "自选月将"+YiJing.DIZINAME[gInfo[0][0]]+"，"+gGeju;
     else
       _s += YiJing.DIZINAME[gInfo[0][0]]+"将，"+gGeju;
    str.append("    格局："+_s+"\r\n");

    return str.toString();
  }

  /**
   * 得到行年 0为当前年 1为虚岁 23为行年干支
   * 男从生年旬首起数三位为一岁，在地盘上顺数数至现年岁数为本年行年（虚岁）。
   */
  private int[] getXingnian(int born, boolean isBoy) {
    int bg = (born-Calendar.IYEAR+Calendar.IYEARG)%10==0?10:(born-Calendar.IYEAR+Calendar.IYEARG)%10;
    int bz = (born-Calendar.IYEAR+Calendar.IYEARZ)%12==0?12:(born-Calendar.IYEAR+Calendar.IYEARZ)%12;
    int g1 = 1;
    int z1 = pub.getXunShou(bg,bz);
    int g2 = 0,z2 = 0;
    int y = new java.util.Date().getYear()+1900;
    int xs = y+1 - born;
    if(isBoy) {
      g2 = (g1+2)%10==0?10:(g1+2)%10;
      z2 = (z1+2)%12 == 0?12:(z1+2)%12;
      g1 = (g1+2+xs-1)%10==0?10:(g1+2+xs-1)%10;
      z1 = (z1+2+xs-1)%12 == 0?12:(z1+2+xs-1)%12;
    }else{
      g2 = (g1+8)%10==0?10:(g1+8)%10;
      z2 = (z1+8)%12==0?12:(z1+8)%12;
      g1 = (g1+8-(xs-1)+100)%10==0?10:(g1+8-(xs-1)+100)%10;
      z1 = (z1+8-(xs-1)+120)%12==0?10:(z1+8-(xs-1)+120)%12;
    }

    return new int[]{y,xs,g2,z2,g1,z1};
  }
  private int[] getXingnian(int g, int z, boolean isBoy) {
    return getXingnian(pub.getYear(g,z),isBoy);
  }

  private String _getFs(int iGr, int iZy, int iDg,int iYj, int sz) {
    String _s = null;

    if(iGr == 0)
      _s = "贵人在甲羊戊庚牛，";
    else if(iGr==1)
      _s = "贵人在甲戊兼牛羊，";
    else
      _s = "贵人在甲戊庚日丑，";
    _s += YiJing.DIZINAME[sz]+"时";
    if(iZy == 0)
      _s += "为旦为阳贵，";
    else
      _s += "为暮为阴贵，";
    if(iDg==0)
      _s += "日旬遁干";
    else
      _s += "时旬遁干";
    if(iYj==0)
      _s += "，以节气法定月将";
    else if(iYj==1)
      _s += "，以超接法定月将";
    else
      _s += "，自选月将";

    return _s;
  }

  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  /**
   * 得到全局变量
   */
  private void getGinfo(int born, int y, int m, int d, int h,int mi,int ss,
                        boolean isYin,boolean isBoy, boolean isYun,
                        int iGr, int iZy, int iDg, int iYj) {
    int[] jq = pub.getJieQi(y,m,d,h,mi, isYun, isYin);
    //自定义月将
    if(iYj>1)
      gInfo[0][0] = iYj - 1;
    else
      gInfo[0][0] = getYueJiang(jq[1]);

    int[] s = daoc.getSiZhu(y,m,d,h,mi,isYun,isYin);
    int bg = (born-Calendar.IYEAR+Calendar.IYEARG)%10==0?10:(born-Calendar.IYEAR+Calendar.IYEARG)%10;
    int bz = (born-Calendar.IYEAR+Calendar.IYEARZ)%12==0?12:(born-Calendar.IYEAR+Calendar.IYEARZ)%12;
    getGinfo(bg, bz, s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],
                        isYin,isBoy, isYun,
                        iGr, iZy, iDg, iYj, born);
  }

  private int _getGuiren(int zy, int gr) {
    int s = 0;
    if(zy==0) {
      if (gr == 0)
        s = Liuren.gui01[gInfo[1][5]];
      else if (gr == 1)
        s = Liuren.gui11[gInfo[1][5]];
      else if (gr == 2)
        s = Liuren.gui21[gInfo[1][5]];
    }else{
      if (gr == 0)
        s = Liuren.gui00[gInfo[1][5]];
      else if (gr == 1)
        s = Liuren.gui10[gInfo[1][5]];
      else if (gr == 2)
        s = Liuren.gui20[gInfo[1][5]];
    }
    return s;
  }
  /**
   * 得到全局变量
   */
  private void getGinfo(int bg, int bz, int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
                        boolean isYin,boolean isBoy, boolean isYun,
                        int iGr, int iZy, int iDg, int iYj, int born) {
    //月将，顺逆，贵人
    if(gInfo[0][0] <= 0) {
      if(iYj>1)
        gInfo[0][0] = iYj - 1;
      else
        gInfo[0][0] = getYueJiang(yz<3?(yz+10)*2:2*(yz-2));
    }
    gInfo[0][1] = iZy==0?1:0;

    //起课时间
    gInfo[1][1] = ng;
    gInfo[1][2] = nz;
    gInfo[1][3] = yg;
    gInfo[1][4] = yz;
    gInfo[1][5] = rg;
    gInfo[1][6] = rz;
    gInfo[1][7] = sg;
    gInfo[1][8] = sz;

    //贵人
    gInfo[0][2] = _getGuiren(iZy, iGr);

    //性别、年命、行年
    if(born<=0)
      born = pub.getYear(bg,bz);
    int[] xn = this.getXingnian(born, isBoy);
    gInfo[2][0] = isBoy?1:0;
    gInfo[2][1] = bg;
    gInfo[2][2] = bz;
    gInfo[2][3] = xn[4];
    gInfo[2][4] = xn[5];
    //12天盘
    for(int i=1; i<=12; i++) {
      gInfo[3][i] = getTp(i,sz,gInfo[0][0]);
    }

    //十二天将
    for(int i=1; i<=12; i++) {
      gInfo[6][i] = getTianJiang(i);
    }

    //四课天地盘
    gInfo[4][1] = rg;
    gInfo[4][2] = getTp(Liuren.gan2g[rg], sz, gInfo[0][0]);
    gInfo[4][3] = gInfo[4][2];
    gInfo[4][4] = gInfo[3][gInfo[4][3]];
    gInfo[4][5] = rz;
    gInfo[4][6] = gInfo[3][rz];
    gInfo[4][7] = gInfo[4][6];
    gInfo[4][8] = gInfo[3][gInfo[4][7]];
    //四课天将
    gInfo[4][9]  = getTianJiang(_getDp(gInfo[4][2]));
    gInfo[4][10] = getTianJiang(_getDp(gInfo[4][4]));
    gInfo[4][11] = getTianJiang(_getDp(gInfo[4][6]));
    gInfo[4][12] = getTianJiang(_getDp(gInfo[4][8]));

    //三传
    if(gGeju.indexOf("格")==-1)
      getzs();
    if(gGeju.indexOf("格")==-1)
      getys();
    if(gGeju.indexOf("格")==-1)
      getby();
    if(gGeju.indexOf("格")==-1)
      getsh();
    if(gGeju.indexOf("格")==-1)
      getyk();
    if(gGeju.indexOf("格")==-1)
      getmx();
    if(gGeju.indexOf("格")==-1)
      getbz();
    if(gGeju.indexOf("格")==-1)
      getbaz();
    if(gGeju.indexOf("格")==-1)
      getful();
    if(gGeju.indexOf("格")==-1)
      getfl();

      /*System.out.print(YiJing.DIZINAME[gInfo[4][8]]+YiJing.DIZINAME[gInfo[4][7]]+"  "+
                       YiJing.DIZINAME[gInfo[4][6]]+YiJing.DIZINAME[gInfo[4][5]]+"  "+
                       YiJing.DIZINAME[gInfo[4][4]]+YiJing.DIZINAME[gInfo[4][3]]+"  "+
                       YiJing.DIZINAME[gInfo[4][2]]+YiJing.TIANGANNAME[gInfo[4][1]]+"  ");

      System.out.println(YiJing.DIZINAME[gInfo[5][1]]+"  "+YiJing.DIZINAME[gInfo[5][2]]+"  "+
                         YiJing.DIZINAME[gInfo[5][3]]);
*/
    //三传天将
    gInfo[5][4]  = getTianJiang(_getDp(gInfo[5][1]));
    gInfo[5][5]  = getTianJiang(_getDp(gInfo[5][2]));
    gInfo[5][6]  = getTianJiang(_getDp(gInfo[5][3]));
    //三传遁干 如果为0则为空亡之地支
    gInfo[5][7] = _getDungan(iDg, gInfo[5][1]);
    gInfo[5][8] = _getDungan(iDg, gInfo[5][2]);
    gInfo[5][9] = _getDungan(iDg, gInfo[5][3]);
    //三传六亲
    gInfo[5][10] = _getLiuqin(gInfo[5][1]);
    gInfo[5][11] = _getLiuqin(gInfo[5][2]);
    gInfo[5][12] = _getLiuqin(gInfo[5][3]);
  }
  /**
   * 得到三传遁干
   */
  private int _getDungan(int dg, int tpz) {
    //不用得到该天盘的地支，三传支即地支
    int[] kwz = new int[2];
    int dp = 0; //旬支之地盘，不分阴阳顺布十天干，跳过空亡地支
    if(dg==0) {
      //日旬遁，取天盘之干顺挨为三传之干，注释为取天盘之地盘干
      //dp = _getDp(gInfo[1][6]);
      dp = gInfo[1][6];
      kwz = pub.getXunKong(gInfo[1][5],gInfo[1][6]);
      if(_iskw(kwz,tpz)) return 0;
    }else{
      //时旬遁
      //dp = _getDp(gInfo[1][8]);
      dp = gInfo[1][8];
      kwz = pub.getXunKong(gInfo[1][7],gInfo[1][8]);
      if(_iskw(kwz,tpz)) return 0;
    }

    int x = (tpz-dp)<0?(tpz-dp+12):tpz-dp;
    if(_isCross(dp, tpz, kwz))
      return (x+gInfo[1][5]+10-2)%10==0?10:(x+gInfo[1][5]+10-2)%10;
    else
      return (x+gInfo[1][5]+10)%10==0?10:(x+gInfo[1][5]+10)%10;

  }

  /**
   * 是否经过空亡之支，一律顺行
   */
  private boolean _isCross(int from, int to, int[] kwz) {
    for(int i=from; i!=to; i++) {
      i = i%12;
      if(i==kwz[0] || i==kwz[1])
        return true;
    }
    return false;
  }

  /**
   * 是否空亡之支
   */
  private boolean _iskw(int[] zs, int z) {
    return (zs[0]==z || zs[1]==z);
  }
  /**
   * 得到三传六亲 日干与三传地支
   * 12345: "兄弟","子孙","妻财","官鬼","父母"
   */
  private int _getLiuqin(int z) {
    if(YiJing.WXDANKE[YiJing.TIANGANWH[gInfo[1][5]]][YiJing.DIZIWH[z]]==1)
      return 3;
    if(YiJing.WXDANKE[YiJing.DIZIWH[z]][YiJing.TIANGANWH[gInfo[1][5]]]==1)
      return 4;
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[gInfo[1][5]]][YiJing.DIZIWH[z]]==1)
      return 2;
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[z]][YiJing.TIANGANWH[gInfo[1][5]]]==1)
      return 5;
    else
      return 1;
  }
  /**
   * 得到各宫十二天将序号
   */
  private int getTianJiang(int gong) {
    int dp = _getDp(gInfo[0][2]); //得贵人在地盘何宫
    if(gInfo[0][1]==0) {
      //阴顺
      return (gong-dp+1+12)%12==0?12:(gong-dp+1+12)%12;
    }else{
      //阳逆
      return (dp-gong+1+12)%12==0?12:(dp-gong+1+12)%12;
    }
  }

  /**
   * 得到月将
   */
  private int getYueJiang(int i) {
    return Liuren.yuej[i];
  }

  /**
   * 由地盘地支得到天盘
   */
  private int getTp(int dz, int sz, int yj) {
    return (dz - sz + yj + 12)%12==0?12:(dz - sz + yj + 12)%12;
  }

  /**
   * 1. 得到贼上 0为多少克，1234为克之干
   */
  private int[] getzs() {
    int how = 0;
    String s = "，贼上格；";
    int cc = 0;
    int[] zs = new int[5];
    String _s1 = "";

    if(YiJing.WXDANKE[YiJing.TIANGANWH[gInfo[4][1]]][YiJing.DIZIWH[gInfo[4][2]]]==1) {
      how++;
      zs[how] = gInfo[4][2];
      cc = gInfo[4][2];
      _s1 = YiJing.TIANGANNAME[gInfo[4][1]]+"克"+YiJing.DIZINAME[gInfo[4][2]] + s;
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][3]]][YiJing.DIZIWH[gInfo[4][4]]]==1) {
      how++;
      zs[how] = gInfo[4][4];
      cc = gInfo[4][4];
      _s1 = YiJing.DIZINAME[gInfo[4][3]]+"克"+YiJing.DIZINAME[gInfo[4][4]] + s;
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][5]]][YiJing.DIZIWH[gInfo[4][6]]]==1) {
      how++;
      zs[how] = gInfo[4][6];
      cc = gInfo[4][6];
      _s1 = YiJing.DIZINAME[gInfo[4][5]]+"克"+YiJing.DIZINAME[gInfo[4][6]] + s;
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][7]]][YiJing.DIZIWH[gInfo[4][8]]]==1) {
      how++;
      zs[how] = gInfo[4][8];
      cc = gInfo[4][8];
      _s1 = YiJing.DIZINAME[gInfo[4][7]]+"克"+YiJing.DIZINAME[gInfo[4][8]] + s;
    }

    if(how==1) {
      gInfo[5][1] = cc;
      gInfo[5][2] = getTp(gInfo[5][1], gInfo[1][8], gInfo[0][0]);
      gInfo[5][3] = getTp(gInfo[5][2], gInfo[1][8], gInfo[0][0]);
      gGeju = _s1;
    }

    zs[0] = how;
    return zs;
  }

  /**
   * 1. 得到元首
   */
  private int[] getys() {
    int how = 0;
    String s = "，元首格；";
    int cc = 0;
    int[] ys = new int[5];
    String _s = "";

    if(getzs()[0]==0) {
      if (YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][2]]][YiJing.TIANGANWH[gInfo[4][1]]] == 1) {
        how++;
        cc = gInfo[4][2];
        ys[how] = cc;
        _s = YiJing.DIZINAME[gInfo[4][2]]+"克"+YiJing.TIANGANNAME[gInfo[4][1]] + s;
      }
      if (YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][4]]][YiJing.DIZIWH[gInfo[4][3]]] == 1) {
        how++;
        cc = gInfo[4][4];
        ys[how] = cc;
        _s = YiJing.DIZINAME[gInfo[4][4]] + "克" + YiJing.DIZINAME[gInfo[4][3]] + s;
      }
      if (YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][6]]][YiJing.DIZIWH[gInfo[4][5]]] == 1) {
        how++;
        cc = gInfo[4][6];
        ys[how] = cc;
        _s = YiJing.DIZINAME[gInfo[4][6]] + "克" + YiJing.DIZINAME[gInfo[4][5]] + s;
      }
      if (YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][8]]][YiJing.DIZIWH[gInfo[4][7]]] == 1) {
        how++;
        cc = gInfo[4][8];
        ys[how] = cc;
        _s = YiJing.DIZINAME[gInfo[4][8]] + "克" + YiJing.DIZINAME[gInfo[4][7]] + s;
      }

      if (how == 1) {
        gInfo[5][1] = cc;
        gInfo[5][2] = getTp(gInfo[5][1], gInfo[1][8], gInfo[0][0]);
        gInfo[5][3] = getTp(gInfo[5][2], gInfo[1][8], gInfo[0][0]);
        gGeju = _s;
      }
    }

    ys[0] = how;
    return ys;
  }


  /**
   * 2. 得到比用
   */
  private int[] getby() {
    int[] zs = getzs();
    int[] ys = getys();
    int[] by = new int[zs.length];
    int how = 0;
    int cc = 0;

    if(zs[0] >= 2) {
      for(int i=1; i<zs.length; i++) {
        if(zs[i] == 0)
          continue;
        if(Liuren.zyy[zs[i]]==Liuren.gyy[gInfo[1][5]]) {
          how++;
          cc = zs[i];
          by[how] = cc;
        }
      }
    }else if(zs[0]==0 && ys[0] >= 2) {
      for(int i=1; i<ys.length; i++) {
        if(ys[i] == 0)
          continue;
        if(Liuren.zyy[ys[i]]==Liuren.gyy[gInfo[1][5]]) {
          how++;
          cc = ys[i];
          by[how] = cc;
        }
      }
    }

    if (how == 1) {
      gInfo[5][1] = cc;
      gInfo[5][2] = getTp(gInfo[5][1], gInfo[1][8], gInfo[0][0]);
      gInfo[5][3] = getTp(gInfo[5][2], gInfo[1][8], gInfo[0][0]);
      gGeju = YiJing.DIZINAME[cc]+"同日干，比用格";
    }
    by[0] = how;
    return by;
  }

  /**
   * 3. 得到涉害
   */
  private void getsh() {
    int[] zs = getzs();
    int[] ys = getys();
    int[] by = getby();
    int index = 0;
    int cc = 0;
    String[][] rs = new String[4][2];

    if(by[0]>=2) {
      //取多少克,zs/ys来判断谁上谁下，而by判断到底有几对与日干比。如3对贼上只有2对比
      if(zs[0]>=2) {
        for(int i=1; i<by.length; i++) {
          if(by[i]==0) continue;
          rs[index++] = _getHowKe(_getTwoth(by[i]),by[i],false); //贼上为下主克，上受克，下支如为干，已转换为支
        }
      }else if(zs[0]==0 && ys[0] >= 2) {
        for(int i=1; i<by.length; i++) {
          if(by[i]==0) continue;
          rs[index++] = _getHowKe(by[i],_getTwoth(by[i]),true); //元首为上主克下受克
        }
      }
      //比较克多克少
      int max = 0;     //最大值是多少
      int value = 0;
      String _s1 = ""; //多少克的描述
      int howmax = 0; //共有多少个最大值
      int maxzi = 0;  //取克数最多的地支
      int dpz = 0; //地盘

      for(int i=0; i<rs.length; i++) {
        if(rs[i][1]==null || rs[i][1].equals("null"))
          continue;
        try{
          value = Integer.valueOf(rs[i][0]).intValue();
        }catch(Exception e) {value=0;}
        if(value > max) {
          max = value;
          maxzi = Integer.valueOf(rs[i][2]).intValue();
        }
        _s1 += rs[i][1]+"，";
      }
      for(int i=0; i<rs.length; i++) {
        try{
          value = Integer.valueOf(rs[i][0]).intValue();
        }catch(Exception e) {value=0;}
        if(value == max) {
          howmax++;
        }
      }
      if(howmax>1) {
        for(int i=0; i<rs.length; i++) {
          try{
            value = Integer.valueOf(rs[i][0]).intValue();
          }catch(Exception e) {value=0;}
          if(value == max) {
            maxzi = Integer.valueOf(rs[i][2]).intValue();
            dpz = _getDp(maxzi);
            if(_which(dpz) == 1) {
              gGeju = _s1+"克相等，"+YiJing.DIZINAME[maxzi]+"之地盘"+
                  YiJing.DIZINAME[dpz]+"在四孟，为涉害之见机格";
              cc = maxzi ;
              break;
            }
          }
        }
        if(cc == 0) {
          for (int i = 0; i < rs.length; i++) {
            try{
              value = Integer.valueOf(rs[i][0]).intValue();
            }catch(Exception e) {value=0;}
            if (value == max) {
              maxzi = Integer.valueOf(rs[i][2]).intValue();
              dpz = _getDp(maxzi);
              if (_which(dpz) == 2) {
                gGeju = _s1 + "克相等，" + YiJing.DIZINAME[maxzi] + "之地盘" +
                    YiJing.DIZINAME[dpz] + "在四仲，为涉害之察微格";
                cc = maxzi;
                break;
              }
            }
          }
        }
        if(cc == 0) {
          if (Liuren.gyy[gInfo[1][5]] == 1) {
            cc = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
            gGeju = _s1 + "克相等，无孟无仲，取阳日干上之" +
                 YiJing.DIZINAME[cc]+ "发用，为涉害之复等格";
          }
          else {
            cc =gInfo[3][gInfo[1][6]];
            gGeju = _s1 + "克相等，无孟无仲，取阴日干支上之" +
                YiJing.DIZINAME[cc] + "发用，为涉害之缀瑕格";
          }
        }
      }else{
        cc = maxzi;
        gGeju = _s1+YiJing.DIZINAME[maxzi]+"克多，涉害格";
      }
      gInfo[5][1] = cc;
      gInfo[5][2] = getTp(gInfo[5][1], gInfo[1][8], gInfo[0][0]);
      gInfo[5][3] = getTp(gInfo[5][2], gInfo[1][8], gInfo[0][0]);
    }
  }

  /**
   * 由地盘判断是哪三类 1为四孟 2为四仲 3为四季
   */
  private int _which(int dp) {
    int rs = 0;
    if(dp==YiJing.YIN || dp==YiJing.SHEN || dp==YiJing.SI || dp==YiJing.HAI)
      rs = 1;
    else if(dp==YiJing.ZI || dp==YiJing.WUZ || dp==YiJing.MAO || dp==YiJing.YOU)
      rs = 2;
    else
      rs = 3;
    return rs;
  }
  /**
   * 由天盘取地盘
   */
  private int _getDp(int tp) {
    for(int i=1; i<=12; i++) {
      if(gInfo[3][i]==tp)
        return i;
    }
    return 0;
  }
  /**
   * 由上支得到贼上或元首的下支，因不论是上克下，还是下克上，初传均取上支
   * 如果i==1，则是日干，要换算成地支
   */
  private int _getTwoth(int z) {
    int zz = 0;
    for(int i=1; i<=8; i++) {
      if(gInfo[4][i]==z) {
        if(i==2)
          zz = Liuren.gan2g[gInfo[4][i - 1]];
        else if(i%2==0) {
          zz = gInfo[4][i - 1];
          break;
        }
      }
    }
    return zz;
  }
  /**
   * 由上下支得到有多少克，z1为克支，z2为受克
   * 元首由下支循环至本家，贼上取上支至本家，因相克且均换成地支，不存在相等
   */
  private String[] _getHowKe(int z1, int z2,boolean isYs) {
    int ke = 0;
    int cang = 0;
    String rs = "";

    rs += YiJing.DIZINAME[z1]+"有";
    for(int i=z2; i!=z1; i++) {
      i = i>12?i%12:i;
      if(YiJing.WXDANKE[YiJing.DIZIWH[z1]][YiJing.DIZIWH[i]]==1) {
        ke++;
        rs += "-"+YiJing.DIZINAME[z2];
      }
      cang = Liuren.gong2g[i];
      if(cang>100) {
        if(YiJing.WXDANKE[YiJing.DIZIWH[z1]][YiJing.TIANGANWH[cang/100]]==1) {
          ke++;
          rs += "-"+YiJing.TIANGANNAME[cang/100];
        }
        if(YiJing.WXDANKE[YiJing.DIZIWH[z1]][YiJing.TIANGANWH[cang%100]]==1) {
          ke++;
          rs += "-"+YiJing.TIANGANNAME[cang%100];
        }
      }else{
        if(YiJing.WXDANKE[YiJing.DIZIWH[z1]][YiJing.TIANGANWH[cang]]==1) {
          ke++;
          rs += "-"+YiJing.TIANGANNAME[cang];
        }
      }
    }
    rs += ke+"重克";
    if(isYs)
      return new String[]{String.valueOf(ke),rs,String.valueOf(z1)};
    else
      return new String[]{String.valueOf(ke),rs,String.valueOf(z2)};
  }
  /**
   * 4. 得到遥克
   */
  private int getyk() {
    int[] zs = getzs();
    int[] ys = getys();
    int[] kes = new int[4];
    int cc = 0;
    int index = 0;

    if(zs[0]==ys[0] && zs[0]==0 && !getbaz() && !getful() && !getfl()) {
      for(int i=2; i<=8; i=i+2) {
        if(YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][i]]][YiJing.TIANGANWH[gInfo[1][5]]]==1) {
          kes[index++] = gInfo[4][i];
        }
      }
      if(index==0) {
        for(int i=2; i<=8; i=i+2) {
          if(YiJing.WXDANKE[YiJing.DIZIWH[gInfo[4][i]]][YiJing.TIANGANWH[gInfo[1][5]]]==1) {
            kes[index++] = gInfo[4][i];
          }
        }
      }
    }
    if(index>=1) {
      if (index == 1) {
        gInfo[5][1] = kes[0];
        gInfo[5][2] = getTp(gInfo[5][1], gInfo[1][8], gInfo[0][0]);
        gInfo[5][3] = getTp(gInfo[5][2], gInfo[1][8], gInfo[0][0]);
        gGeju = "无贼上元首，取" + YiJing.DIZINAME[gInfo[5][1]] + "发用，遥克格";
      }
      else {
        for (int i = 0; i < kes.length; i++) {
          if (Liuren.gyy[gInfo[1][5]] == Liuren.zyy[kes[i]]) {
            gInfo[5][1] = kes[i];
            gInfo[5][2] = getTp(gInfo[5][1], gInfo[1][8], gInfo[0][0]);
            gInfo[5][3] = getTp(gInfo[5][2], gInfo[1][8], gInfo[0][0]);
            gGeju = "无贼上元首，取" + YiJing.DIZINAME[gInfo[5][1]] + "发用，遥克格";
            break;
          }
        }
      }
    }
    return index;
  }

  /**
   * 5. 得到昴星
   */
  private void getmx() {
    if(getzs()[0]==0 &&
       getys()[0]==0 &&
       _getHowMany() == 0 &&
       getyk()==0 && !getfl()) {
      gGeju = "无贼上元首遥克，昴星格";
      if(Liuren.gyy[gInfo[1][5]]==1) {
        gInfo[5][1] = gInfo[3][YiJing.YOU];
        gInfo[5][2] = gInfo[3][gInfo[1][6]];
        gInfo[5][3] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
      }else{
        gInfo[5][1] = _getDp(YiJing.YOU);
        gInfo[5][3] = gInfo[3][gInfo[1][6]];
        gInfo[5][2] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
      }
    }
  }

  /**
   * 6. 得到别责
   */
  private void getbz() {
    if(getzs()[0]==0 && getys()[0]==0 && _getHowMany()==1
       && getyk()==0 && !getfl()) {
      gGeju = "无贼上元首遥克且两课不备，别责格";
      if(Liuren.gyy[gInfo[1][5]]==1) {
        gInfo[5][1] = gInfo[3][Liuren.gan2g[Liuren.g6h[gInfo[1][5]]]];
        gInfo[5][2] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
        gInfo[5][3] = gInfo[5][2];
      }else{
        gInfo[5][1] = Liuren.zadd4[gInfo[1][6]];
        gInfo[5][2] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
        gInfo[5][3] = gInfo[5][2];
      }
    }

  }
  /**
   * 得到几对相同的
   */
  private int _getHowMany() {
    int how = 0;
    //41可能为干要转化为支
    int z = Liuren.gan2g[gInfo[4][1]];
    if((z==gInfo[4][3] && gInfo[4][2]==gInfo[4][4]) ||
       (z==gInfo[4][5] && gInfo[4][2]==gInfo[4][6]) ||
       (z==gInfo[4][7] && gInfo[4][2]==gInfo[4][8]))
      how++;
    if((gInfo[4][3]==gInfo[4][5] && gInfo[4][4]==gInfo[4][6]) ||
       (gInfo[4][3]==gInfo[4][7] && gInfo[4][4]==gInfo[4][8]))
      how++;
    if((gInfo[4][5]==gInfo[4][7] && gInfo[4][6]==gInfo[4][8]))
      how++;
    return how;
  }
  /**
   * 7. 得到八专
   */
  private boolean getbaz() {
    int z = 0;
    if(getzs()[0]==0 && getys()[0]==0 && is2Same() && !getfl()) {
      gGeju = "无贼上元首且只有两课，八专格";
      if(Liuren.gyy[gInfo[1][5]]==1) {
        z = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
        gInfo[5][1] = (z+2)%12==0?12:(z+2)%12;
        gInfo[5][2] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
        gInfo[5][3] = gInfo[5][2];
      }else{
        z = gInfo[4][8];
        gInfo[5][1] = (z-2+12)%12==0?12:(z-2+12);
        gInfo[5][2] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
        gInfo[5][3] = gInfo[5][2];
      }
      return true;
    }
    return false;
  }
  /**
   * 是否2对相同
   */
  private boolean is2Same() {
    //41可能为干要转化为支
    int z = Liuren.gan2g[gInfo[4][1]];
    if((z==gInfo[4][3] && gInfo[4][2]==gInfo[4][4] &&
       gInfo[4][5]==gInfo[4][7] && gInfo[4][6]==gInfo[4][8]) ||
       (z==gInfo[4][5] && gInfo[4][2]==gInfo[4][6] &&
       gInfo[4][3]==gInfo[4][7] && gInfo[4][4]==gInfo[4][8]) ||
       (z==gInfo[4][7] && gInfo[4][2]==gInfo[4][8] &&
        gInfo[4][5]==gInfo[4][3] && gInfo[4][6]==gInfo[4][4]))
      return true;
    return false;
  }


  /**
   * 8. 得到伏吟
   */
  private boolean getful() {
    int z = 0;
    if(getzs()[0]==0 && getys()[0]==0 ) {
      if(gInfo[3][1]==1 && gInfo[3][12]==12) {
        gGeju = "无贼上元首且天地同位，伏吟格";
        if(Liuren.gyy[gInfo[1][5]]==1) {
          z = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
          gInfo[5][1] = z;
          gInfo[5][2] = Liuren.xing3[z];
          gInfo[5][3] = Liuren.xing3[gInfo[5][2]];
        }else{
          z = gInfo[3][gInfo[1][6]];
          gInfo[5][1] = z;
          gInfo[5][2] = Liuren.xing3[z];
          gInfo[5][3] = Liuren.xing3[gInfo[5][2]];
        }
        return true;
      }
    }
    return false;
  }

  /**
   * 9. 得到返吟
   */
  private boolean getfl() {
    if(getzs()[0]==0 && getys()[0]==0 ) {
      if (gInfo[3][1] == 7 && gInfo[3][12] == 6) {
        gGeju = "无贼上元首且天地相冲，反吟格";
        if(gInfo[1][6]==YiJing.CHOU)
          gInfo[5][1] = YiJing.HAI;
        if(gInfo[1][6]==YiJing.WEI)
          gInfo[5][1] = YiJing.SI;
        gInfo[5][2] = gInfo[3][gInfo[1][6]];
        gInfo[5][3] = gInfo[3][Liuren.gan2g[gInfo[1][5]]];
      }
    }
    return false;
  }


  public static void main(String[] args) {
    DaoLiuren lr = new DaoLiuren();
  }
}
