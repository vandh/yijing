package org.boc.dao.bz;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.BaZhai;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;

public class DaoFS_BaZhai {
  private DaoYiJingMain daoyj;
  private StringBuffer str;

  public DaoFS_BaZhai() {
    daoyj = new DaoYiJingMain();
    str = new StringBuffer();
  }

  /**
   * 住宅综断
   * @param year
   * @param sex
   * @param wx
   * @return
   */
  public String analyse(int year, int year2, boolean sex, int[] hj,
                        int wuxiang, int dmw, int dmx, int cesuo, int jiuzhai,
                             int woshi, int fangmen,  int chuang,
                             int chufang, int zaowei, int zaoxiang)  {
    str.delete(0, str.length());

    str.append("\r\n 1. 外峦头：");
    if(hj!=null)
      for(int i=0; i<hj.length; i++) {
        if(hj[i]==0)
          continue;
        str.append("\r\n    " + BaZhai.hj1[hj[i]]);
      }
    str.append("\r\n");

    str.append("\r\n 2. 宅命相配：");
    int zg = this.getZhaiGua(wuxiang);
    int mg = this.getMingGua(year, sex);
    if(BaZhai.dxsg[zg] == BaZhai.dxsg[mg]) {
      str.append("\r\n    "+BaZhai.dxsm[BaZhai.dxsg[mg]]+"住"+BaZhai.dxsz[BaZhai.dxsg[zg]]+"，为得福元，主丁财两旺，大吉。");
    }else{
      str.append("\r\n    "+BaZhai.dxsm[BaZhai.dxsg[mg]]+"住"+BaZhai.dxsz[BaZhai.dxsg[zg]]+"，宅命不配，可从大门、房间调整以保平安，若要丁财两旺，须择吉迁居。");
    }
    str.append("\r\n");

    str.append("\r\n 3. 大门吉凶：");
    str.append("\r\n    1） 门位：");
    int dmwShen = this.getShen(mg, dmw);
    xiangDuan(dmwShen, dmw);
    str.append("\r\n    2） 门向：");
    int dmxShen = this.getShen(mg, dmx);
    xiangDuan(dmxShen, dmx);
    str.append("\r\n    3） 财位：");
    int caiw = BaZhai.hxtg[10 - BaZhai.xhtg[dmw]];
    str.append("\r\n        财位在大门对角线的"+BaZhai.fx[caiw]+
               "位，宜摆放五行为"+YiJing.WUXINGNAME[YiJing.jingguawx[caiw]]+
               "风水用品，如"+BaZhai.cwfs[YiJing.jingguawx[caiw]]);

    str.append("\r\n");

    str.append("\r\n 4. 卧室吉凶：");
    str.append("\r\n    1） 房位：");
    int wsShen = this.getShen(mg, woshi);
    xiangDuan(wsShen, woshi);
    str.append("\r\n    2） 门位：");
    int fmShen = this.getShen(mg, fangmen);
    xiangDuan(fmShen, fangmen);
    str.append("\r\n    3） 床位：");
    int cwShen = this.getShen(mg, chuang);
    xiangDuan(cwShen, chuang);
    str.append("\r\n");

    str.append("\r\n 5. 厨房吉凶：");
    str.append("\r\n    1） 房位：");
    int cfShen = this.getShen(mg, chufang);
    if(chufang==3 || chufang==6 || chufang==4 || chufang==2)
      str.append("\r\n        水火不留十字线，今厨房处四正位之"+BaZhai.fx[chufang]+"，火烧心脏，主家人脾气暴躁。");
    xiangDuan(cfShen, chufang);
    str.append("\r\n    2） 灶位：");
    int zwShen = this.getShen(mg, zaowei);
    xiangDuan2(zwShen, zaowei);
    str.append("\r\n    3） 灶向：");
    int zxShen = this.getShen(mg, zaoxiang);
    xiangDuan(zxShen, zaoxiang);
    str.append("\r\n");

    //str.append("\r\n 6. 厕所吉凶：");
    //str.append("\r\n    1） 厕位：");
    //int csShen = this.getShen(mg, cesuo);
    //xiangDuan(csShen, cesuo);
    //str.append("\r\n");

    str.append("\r\n 6. 婚姻：");
    int mg2 = this.getMingGua(year2, !sex);
    int hyShen = this.getShen(mg, mg2);
    str.append("\r\n    房主为"+YiJing.JINGGUANAME[mg]+"命，");
    str.append("第二房主为"+YiJing.JINGGUANAME[mg2]+"命，");
    str.append("夫妻二人合成"+BaZhai.yxp[hyShen]+"婚，为"+BaZhai.hunyin[hyShen]);
    int fqcw = BaZhai.fqcw[mg][mg2];
    String _fq = "";
    if(fqcw>=1000)
      _fq += "，"+BaZhai.fx[fqcw/1000];
    if(fqcw>=100)
      _fq += "，"+BaZhai.fx[(fqcw/100)%10];
    if(fqcw>=10)
      _fq += "，"+BaZhai.fx[(fqcw/10)%10];
    if(fqcw>=1)
      _fq += "，"+BaZhai.fx[fqcw%10];

    str.append("\r\n    夫妻床位宜在卧室小太极下列方向"+_fq);
    str.append("\r\n");

    str.append("\r\n 7. 子息：");
    str.append("\r\n    "+BaZhai.yxp[hyShen]+"婚，"+BaZhai.zixi[hyShen]);
    str.append("\r\n");

    str.append("\r\n 8. 疾病：");
    getJiBing(mg, dmw, dmwShen, "大门位置在住宅大太极");
    getJiBing(mg, dmx, dmxShen, "大门开门方向在住宅大太极");
    getJiBing(mg, woshi, wsShen, "卧室位置在住宅大太极");
    getJiBing(mg, fangmen, fmShen, "房门位置在卧室小太极");
    getJiBing(mg, chuang, cwShen, "床头位置在卧室小太极");
    getJiBing(mg, chufang, cfShen, "厨房位置在住宅大太极");
    //getJiBing(mg, zaowei, zwShen, "灶位在住宅大太极");
    getJiBing(mg, zaoxiang, zxShen, "灶向在住宅大太极");
    str.append("\r\n");

    str.append("\r\n 9. 灾祸：");
    getZaiHuo(mg, dmw, dmwShen, "大门位置在住宅大太极");
    getZaiHuo(mg, dmx, dmxShen, "大门开门方向在住宅大太极");
    getZaiHuo(mg, woshi, wsShen, "卧室位置在住宅大太极");
    getZaiHuo(mg, fangmen, fmShen, "房门位置在住宅大太极");
    getZaiHuo(mg, chuang, cwShen, "床头位置在卧室小太极");
    getZaiHuo(mg, chufang, cfShen, "厨房位置在住宅大太极");
    //getZaiHuo(mg, zaowei, zwShen, "灶位在住宅大太极");
    getZaiHuo(mg, zaoxiang, zxShen, "灶向在住宅大太极");
    str.append("\r\n");

    str.append("\r\n 10. 迁移：");
    str.append(this.getQianYi(year, sex , zaoxiang, jiuzhai, wuxiang));
    str.append("\r\n");

    return str.toString();
  }

  /**
   * 输出对灾祸的描述
   * @param mg
   * @param fx
   * @param shen
   * @param wuti
   */
  private void getZaiHuo(int mg, int fx,int shen, String wuti) {
    String s = null;

    s = BaZhai.zaihuo[mg][fx];
    if(s!=null)
      str.append("\r\n    "+wuti+BaZhai.fx[fx]+"方处"+BaZhai.yxp[shen]+"位，防"+s);
  }

  /**
   * 输出对疾病的描述
   * @param mg
   * @param fx
   * @param shen
   * @param wuti
   */
  private void getJiBing(int mg, int fx,int shen, String wuti) {
    String s = null;

    s = BaZhai.jibing[mg][fx];
    if(s!=null)
      str.append("\r\n    "+wuti+BaZhai.fx[fx]+"方处"+BaZhai.yxp[shen]+"位，防"+s);
  }

  /**
   * 输出详细的吉凶描述
   * @param shen
   * @param fx
   */
  private void xiangDuan(int shen, int fx) {
    String[] _s = null;

    str.append("\r\n        a） 游星吉凶：");
    str.append(BaZhai.fx[fx]+"，处"+BaZhai.yxp3[shen][0]);

    str.append("\r\n        b） 趋吉避凶：");
    _s = BaZhai.yxp3[shen];
    for(int i=1; i<_s.length; i++) {
      str.append("\r\n            "+_s[i]);
    }

    str.append("\r\n        c） 宫位生克：");
    str.append("\r\n            ");
    int gwx = YiJing.jingguawx[fx];
    int xwx = BaZhai.yxpwx[shen];
    str.append(YiJing.JINGGUANAME[fx]+"宫五行属"+YiJing.WUXINGNAME[gwx]+"，");
    str.append(BaZhai.yxp[shen]+"五行属"+YiJing.WUXINGNAME[xwx]+"，");
    if(YiJing.WXDANKE[gwx][xwx]==1 || YiJing.WXDANSHENG[xwx][gwx]==1 ||
       YiJing.WXDANKE[xwx][gwx]==1)
      str.append("本星受到克泄耗损，力量有所减弱。");
    else
      str.append("本星受到生助，力量有所增强。");

    str.append("\r\n        d） 流年吉凶：");
    str.append("\r\n            于五行属"+YiJing.WUXINGNAME[xwx]+"之流年流月，吉凶必有所应。");
  }

  private void xiangDuan2(int shen, int fx) {
    String[] _s = null;

    str.append("\r\n        a） 游星吉凶：");
    str.append(BaZhai.fx[fx]+"，处"+BaZhai.zaoweiJX[shen]);

    str.append("\r\n        b） 趋吉避凶：");
    _s = BaZhai.yxp3[shen];
    for(int i=1; i<_s.length; i++) {
      str.append("\r\n            "+_s[i]);
    }

    str.append("\r\n        c） 宫位生克：");
    str.append("\r\n            ");
    int gwx = YiJing.jingguawx[fx];
    int xwx = BaZhai.yxpwx[shen];
    str.append(YiJing.JINGGUANAME[fx]+"宫五行属"+YiJing.WUXINGNAME[gwx]+"，");
    str.append(BaZhai.yxp[shen]+"五行属"+YiJing.WUXINGNAME[xwx]+"，");
    if(YiJing.WXDANKE[gwx][xwx]==1 || YiJing.WXDANSHENG[xwx][gwx]==1 ||
       YiJing.WXDANKE[xwx][gwx]==1)
      str.append("本星受到克泄耗损，力量有所减弱。");
    else
      str.append("本星受到生助，力量有所增强。");

    str.append("\r\n        d） 流年吉凶：");
    str.append("\r\n            于五行属"+YiJing.WUXINGNAME[xwx]+"之流年流月，吉凶必有所应。");
  }


  /**
   * 得到头部输出
   * @return
   */
  public String getHead(int year, boolean sex, int wx) {
    String s= "";
    int gua = getMingGua(year,sex);

    s += "命卦游星盘：";
    s += sex ? "乾造" : "坤造";
    s += "，"+year+"年生，"+YiJing.JINGGUANAME[gua] +
          "卦"+BaZhai.dxsm[BaZhai.dxsg[gua]];

    //得到房屋座向，因与屋向相对，而先天八卦相对数相加为9
    int zx = BaZhai.hxtg[10 - BaZhai.xhtg[wx]];
    s += "\r\n    宅卦游星盘：房屋坐"+BaZhai.fx[zx]+"朝"+BaZhai.fx[wx]+"，"+
        YiJing.JINGGUANAME[zx]+"卦"+BaZhai.dxsz[BaZhai.dxsg[zx]];
    return s;
  }

  /**
   * 排游星盘
   * @param year
   * @param sex
   * @return
   */
  public String pp(int year, boolean sex, int wx) {
    String s = "";
    str.delete(0, str.length());

    out1("┏━━━━━━━━┳━━━━━━━━┳━━━━━━━━┓");
    out1("┃41┃91┃21┃");
    out1("┃42┃92┃22┃");
    out1("┃43┃93┃23┃");
    out1("┃44┃94┃24┃");
    out1("┃45┃95┃25┃");
    out1("┃46┃96┃26┃");
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫");
    out1("┃31┃51┃71┃");
    out1("┃32┃52┃72┃");
    out1("┃33┃53┃73┃");
    out1("┃34┃54┃74┃");
    out1("┃35┃55┃75┃");
    out1("┃36┃56┃76┃");
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫");
    out1("┃81┃11┃61┃");
    out1("┃82┃12┃62┃");
    out1("┃83┃13┃63┃");
    out1("┃84┃14┃64┃");
    out1("┃85┃15┃65┃");
    out1("┃86┃16┃66┃");
    out1("┗━━━━━━━━┻━━━━━━━━┻━━━━━━━━┛");

    s = str.toString();
    int hxnum = 0;

    //排人盘
    int mg = this.getMingGua(year,sex);
    int[] x = BaZhai.yxp2[mg];
    for(int i = 1; i<x.length; i++) {
      hxnum = BaZhai.xhtg[i];
      s = s.replaceAll(hxnum+"3",format(BaZhai.yxp[x[i]]+"  "+YiJing.WUXINGNAME[BaZhai.yxpwx[x[i]]]));
    }
    s = s.replaceAll("53",format(YiJing.JINGGUANAME[mg]+"命    "));

    //排宅盘
    int zg = this.getZhaiGua(wx);
    x = BaZhai.yxp2[zg];
    for(int i = 1; i<x.length; i++) {
      hxnum = BaZhai.xhtg[i];
      s = s.replaceAll(hxnum+"4",format(BaZhai.yxp[x[i]]+"  "+YiJing.WUXINGNAME[BaZhai.yxpwx[x[i]]]));
    }
    s = s.replaceAll("54",format(YiJing.JINGGUANAME[zg]+"宅    "));

    //排地盘
    for(int i = 1; i<=x.length; i++) {
      if(i==5)
        continue;
      s = s.replaceAll(i+"5",format(QiMen.dpjg[i]+"  "+YiJing.WUXINGNAME[QiMen.jgwh[i]]));
    }

    for(int i=0; i<=9; i++) {
      for(int j=0; j<=6; j++) {
        s = s.replaceAll(""+i+j, format(""));
      }
    }

    return s;
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
    if(o.getBytes().length<8)
      o=daoyj.getRepeats(" ", (8-o.getBytes().length))+o;

    int len = o.getBytes().length;
    int hlen = (16-len)/2;
    return (daoyj.getRepeats(" ",hlen)+o+daoyj.getRepeats(" ",hlen));
  }

  /**
   * 输出九宫图
   * @param o
   */
  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  /**
   * 返回宅卦
   * @return
   */
  public int getZhaiGua(int wx) {
    return BaZhai.hxtg[10 - BaZhai.xhtg[wx]];
  }

  /**
   * 返回命卦, 都是先天八卦数 乾1坤8
   * @param year
   * @return
   */
  public int getMingGua(int year, boolean sex) {
    int i = year/1000;
    int j = (year/100)%10;
    int k = (year/10)%(year/100);
    int n = year%(year/10);

    int m = (11-(i+j+k+n)%9)%9;
    if(!sex)
      m = BaZhai.nlmg[m];
    if(sex && m==5)
      m = 2;
    else if(!sex && m==5)
      m = 8;

    return BaZhai.hxtg[m];
  }

  /**
   * 由宅卦或命卦及第几宫得到何神
   * @param zhaiOrMing
   * @param gong
   * @return
   */
  public int getShen(int zhaiOrMing, int gong) {
    return BaZhai.yxp2[zhaiOrMing][gong];
  }

  /**
   * 得到疾病
   * @param zm
   * @param gong
   * @return
   */
  public String getJiBing(int zm, int gong) {
    return BaZhai.jibing[zm][gong];
  }

  /**
   * 得到灾祸
   * @param zm
   * @param gong
   * @return
   */
  public String getZaiHuo(int zm, int gong) {
    return BaZhai.jibing[zm][gong];
  }

  /**
   * 判断是否是阴或阳方
   * @param gua
   * @return 1为阳爻 2为阴爻
   */
  public int isYangGua(int gua) {
    boolean bl =  gua == YiJing.QIAN ||
        gua == YiJing.GEN ||
        gua == YiJing.ZHEN ||
        gua == YiJing.KAN;
    return bl?1:2;
  }

  /**
   * 迁移，玄空装卦
   * jiuzhai 第2爻 旧宅在新宅之阳方或阴方画阳阴爻
   * 第3爻 下画新宅在旧宅之阴阳方
   * zaoxiang 第1爻 新宅门向、灶向之阴阳方，若一阴一阳以灶向为主
   * xinzhaiwx 上卦   新宅的屋向，以向为卦，如坐北朝南为离卦。
   * year: 第一个年命
   * @return
   */
  public String getQianYi(int year, boolean sex, int zaoxiang,
                             int jiuzhai, int xinzhaiwx) {
    String str = "";

    int dxsm = BaZhai.dxsg[getMingGua(year, sex)];
    if(dxsm==1) {
      if(isYangGua(jiuzhai)==1) {
        str += "\r\n    1. 来路。"+BaZhai.dxsm[dxsm]+
            "，但旧宅又在新宅四阴方坤巽离兑方之"+BaZhai.fx[jiuzhai]+
            "，属来路无根。以凶论，主退财、疾病。\r\n    "+
            "化解办法，于新宅之四阴方坤巽离兑暂住七七四十九天再行迁居。";
      }else{
        str += "\r\n    1. 来路。"+BaZhai.dxsm[dxsm]+
            "，而旧宅又在新宅四阳方乾震坎艮方之"+BaZhai.fx[jiuzhai]+
            "，属来路有根。以吉论，主健康、进财。";
      }
    }else{
      if(isYangGua(jiuzhai)==1) {
        str += "\r\n    1. 来路。"+BaZhai.dxsm[dxsm]+
            "，而旧宅又在新宅四阳方乾震坎艮方之"+BaZhai.fx[jiuzhai]+
            "，属来路有根。以吉论，主健康、进财。";
      }else{
        str += "\r\n    1. 来路。"+BaZhai.dxsm[dxsm]+
            "，但旧宅又在新宅四阴方坤巽离兑方之"+BaZhai.fx[jiuzhai]+
            "，属来路无根。以凶论，主退财、疾病。\r\n    "+
            "化解办法，于新宅之四阳方乾震坎艮暂住七七四十九天再行迁居。";
      }
    }

    str += "\r\n    2. 玄空装卦。";
    int zaoyao = isYangGua(zaoxiang);
    int jzyao = isYangGua(jiuzhai);
    int xg = YiJing.sanyao[zaoyao][jzyao][jzyao==1?2:1];
    str += "\r\n        第一爻，新宅灶向在"+BaZhai.fx[zaoxiang]+"为"+
        (zaoyao==1?"阳方":"阴方")+"，为"+YiJing.YAONAME3[zaoyao]+" "+YiJing.YAONAME[zaoyao];
    str += "\r\n        第二爻，旧宅在新宅之"+BaZhai.fx[jiuzhai]+
        (jzyao==1?"阳方":"阴方")+"，为"+YiJing.YAONAME3[jzyao]+" "+YiJing.YAONAME[jzyao];
    str += "\r\n        第三爻，新宅相对旧宅为"+(jzyao==1?"阴方":"阳方") +
        "，为"+YiJing.YAONAME3[jzyao==1?2:1]+" "+YiJing.YAONAME[jzyao==1?2:1];
    str += "\r\n        下  卦，新宅屋向在"+BaZhai.fx[xinzhaiwx]+",为"+
        YiJing.JINGGUANAME[xinzhaiwx];

    str += "\r\n    3. 断卦。";
    int sg = YiJing.sanyao[zaoyao][jzyao][jzyao==1?2:1];
    int sgdxsg = BaZhai.dxsg[sg];
    int xgdxsg = BaZhai.dxsg[xinzhaiwx];
    int shen = BaZhai.yxp2[xinzhaiwx][sg];
    if(sgdxsg == xgdxsg)
      str += "同为"+BaZhai.dxsg2[sgdxsg]+"，吉。又下卦"+YiJing.JINGGUANAME[xinzhaiwx]+
          "为上卦"+YiJing.JINGGUANAME[sg]+"的"+BaZhai.yxp[shen];
    else
      str += "上卦为"+BaZhai.dxsg2[sgdxsg]+"，下卦为"+BaZhai.dxsg2[xgdxsg]+
          "，下卦"+YiJing.JINGGUANAME[xinzhaiwx]+
          "为上卦"+YiJing.JINGGUANAME[sg]+"的"+BaZhai.yxp[shen];
    if(shen > 4) {
      str += "，为凶神，不吉。";
      str += "\r\n    4. 抽爻换象。宜改动门向或灶向，以此改变吉凶。";
    }else{
      str += "，为吉神，以吉论。";
      str += "\r\n    4. 抽爻换象。吉，不需要改动。";
    }

    return str;
  }
}