package org.boc.dao.sz;

import org.boc.db.BaZiKu;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuYongShen {
  private final String[] ss5 = new String[]{"比劫","食伤","财才","官杀","印枭"};
  private final String[] ss10 = new String[]{"","比肩","劫财","食神","伤官","偏财","正财","偏官","正官","偏印","正印"};
  private DaoSiZhuMain daos;
  private DaoSiZhuPublic daop;
  private DaoSiZhuWangShuai daow;
  private StringBuffer buf;
  private int[] xyShen;
  private int[] jiShen;
  private int[][] ssnum;
  private int[][][] ssloc;
  private int[] ssws;
  private int[] sscent;
  private String _t = "";
  private BaZiKu bazi ;
  private final int cent = 30;
  private final String sep = "\r\n    ";
  int geju = 0;

  public DaoSiZhuYongShen() {
    bazi = new BaZiKu();
    daos = new DaoSiZhuMain();
    daop = new DaoSiZhuPublic();
    daow = new DaoSiZhuWangShuai();
    xyShen = new int[5];
    jiShen = new int[5];
    buf = new StringBuffer();
    //神煞天干地支个数
    ssnum = new int[11][3];
    //神煞天干地支位置 [十神][干或支1、支2][年柱或月柱或时柱]
    ssloc = new int[11][4][4];
    //神煞旺衰 1弱之极矣 2偏弱 3旺相 4强旺 5旺之极矣
    ssws = new int[5];
    //神煞分
    sscent = new int[5];
  }

  private void init() {
    for(int i=1; i<11; i++) {
      ssnum[i][1] = daop.getGzNum(daop.getShenShaName(i,1)[0],1);
      ssnum[i][2] = daop.getGzNum(daop.getShenShaName(i,2),2);
      //比肩不包括日主自己
      if(i==1) ssnum[i][1]=ssnum[i][1]-1;
      //System.out.println(ss10[i]+"神煞个数："+ssnum[i][1]+":"+ssnum[i][2]);
    }

    for(int i=1; i<11; i++) {
      ssloc[i][1] = daop.getGzLocation(daop.getShenShaName(i,1)[0],1);
      ssloc[i][2] = daop.getGzLocation(daop.getShenShaName(i,2)[0],2);
      ssloc[i][3] = daop.getGzLocation(daop.getShenShaName(i,2)[1],2);
      if(ssloc[i][2][0]==0) {
        ssloc[i][2][0]=ssloc[i][3][0];
        ssloc[i][2][1]=ssloc[i][3][1];
        ssloc[i][2][2]=ssloc[i][3][2];
        ssloc[i][3][0] = 0;
        ssloc[i][3][1] = 0;
        ssloc[i][3][2] = 0;
      }
      //System.out.println(ss10[i]+"神煞位置："+ssloc[i][1][0]+ssloc[i][1][1]+ssloc[i][1][2]+
      //                   ":"+ssloc[i][2][0]+ssloc[i][2][1]+ssloc[i][2][2]+
      //                   ":"+ssloc[i][3][0]+ssloc[i][3][1]+ssloc[i][3][2]);
    }
    for(int i=0; i<5; i++) {
      ssws[i] = daop.getShiShenWS(i);
      //System.out.println(ss5[i]+"神煞旺衰："+ssws[i]);
    }
    for(int i=0; i<5; i++) {
      sscent[i] = daop.getShiShenCent(i);
      //System.out.println(ss5[i]+"神煞分数："+sscent[i]);
    }

  }

  private void uninit() {
    xyShen = new int[5];
    jiShen = new int[5];
    //神煞天干地支个数
    ssnum = new int[11][3];
    //神煞天干地支位置 [十神][干或支1、支2][年柱或月柱或时柱]
    ssloc = new int[11][4][4];
    //神煞旺衰 1弱之极矣 2偏弱 3旺相 4强旺 5旺之极矣
    ssws = new int[5];
    //神煞分
    sscent = new int[5];
  }


  /**
   * 个例赏析
   * 1 完全类似者 2差一字者 3差二字者 4五行同分者
   * @return
   */
  public String analyseSameBaZi() {
    uninit();
    init();
    buf.delete(0,buf.length());
    init();
    String str = "";
    int i1,i2,i3,i4,i5,i6,i7,i8;
    int j = 0;
    int k = 0;

    for (int i = 0; i < bazi.bazi.length; i++) {
      if (bazi.bazi[i][0] == null) continue;
      _t = null;
      j = 0;
      i1 = Integer.valueOf(bazi.bazi[i][0]).intValue();
      i2 = Integer.valueOf(bazi.bazi[i][1]).intValue();
      i3 = Integer.valueOf(bazi.bazi[i][2]).intValue();
      i4 = Integer.valueOf(bazi.bazi[i][3]).intValue();
      i5 = Integer.valueOf(bazi.bazi[i][4]).intValue();
      i6 = Integer.valueOf(bazi.bazi[i][5]).intValue();
      i7 = Integer.valueOf(bazi.bazi[i][6]).intValue();
      i8 = Integer.valueOf(bazi.bazi[i][7]).intValue();

      if (i1 == SiZhu.ng)  j++;
      if (i2 == SiZhu.nz)  j++;
      if (i3 == SiZhu.yg)  j++;
      if (i4 == SiZhu.yz)  j++;
      if (i5 == SiZhu.rg)  j++;
      if (i6 == SiZhu.rz)  j++;
      if (i7 == SiZhu.sg)  j++;
      if (i8 == SiZhu.sz)  j++;
      if (j >= 6)  {
        //System.out.println(bazi.bazi[i][8]);
        str += sep + "例"+ ++k + " ：" +
            YiJing.TIANGANNAME[i1]+YiJing.DIZINAME[i2] + " " +
            YiJing.TIANGANNAME[i3]+YiJing.DIZINAME[i4] + " " +
            YiJing.TIANGANNAME[i5]+YiJing.DIZINAME[i6] + " " +
            YiJing.TIANGANNAME[i7]+YiJing.DIZINAME[i8] + " " +
            sep +
            bazi.bazi[i][8] +
            sep;
      }
    }

    int mucent = SiZhu.muCent;
    int jincent = SiZhu.jinCent;
    int shuicent =SiZhu.shuiCent;
    int huocent = SiZhu.huoCent;
    int tucent = SiZhu.tuCent;
    int mucent1 = SiZhu.muCent1;
    int jincent1 = SiZhu.jinCent1;
    int shuicent1 =SiZhu.shuiCent1;
    int huocent1 = SiZhu.huoCent1;
    int tucent1 = SiZhu.tuCent1;
    int ng = SiZhu.ng;
    int nz = SiZhu.nz;
    int yg = SiZhu.yg;
    int yz = SiZhu.yz;
    int rg = SiZhu.rg;
    int rz = SiZhu.rz;
    int sg = SiZhu.sg;
    int sz = SiZhu.sz;

    for (int i = 0; i < bazi.bazi.length; i++) {
      if(bazi.bazi[i][0]==null) continue;
      i1 = Integer.valueOf(bazi.bazi[i][0]).intValue();
      i2 = Integer.valueOf(bazi.bazi[i][1]).intValue();
      i3 = Integer.valueOf(bazi.bazi[i][2]).intValue();
      i4 = Integer.valueOf(bazi.bazi[i][3]).intValue();
      i5 = Integer.valueOf(bazi.bazi[i][4]).intValue();
      i6 = Integer.valueOf(bazi.bazi[i][5]).intValue();
      i7 = Integer.valueOf(bazi.bazi[i][6]).intValue();
      i8 = Integer.valueOf(bazi.bazi[i][7]).intValue();

      if(YiJing.TIANGANWH[i5]==YiJing.TIANGANWH[rg] &&
         YiJing.DIZIWH[i4] == YiJing.DIZIWH[yz]) {
        getMainInfo(i1,i2,i3,i4,i5,i6,i7,i8);
        if((jincent >= SiZhu.jinCent - cent && jincent <= SiZhu.jinCent + cent) &&
           (mucent >= SiZhu.muCent - cent && mucent <= SiZhu.muCent + cent) &&
           (shuicent >= SiZhu.shuiCent - cent && shuicent <= SiZhu.shuiCent + cent) &&
           (huocent >= SiZhu.huoCent - cent && huocent <= SiZhu.huoCent + cent) &&
           (tucent >= SiZhu.tuCent - cent && tucent <= SiZhu.tuCent + cent)) {
          //System.out.println( bazi.bazi[i][8]);
          str += sep + "例"+ ++k + " ：" +
              YiJing.TIANGANNAME[i1]+YiJing.DIZINAME[i2] + " " +
              YiJing.TIANGANNAME[i3]+YiJing.DIZINAME[i4] + " " +
              YiJing.TIANGANNAME[i5]+YiJing.DIZINAME[i6] + " " +
              YiJing.TIANGANNAME[i7]+YiJing.DIZINAME[i8] +
              "    木："+SiZhu.muCent +
              "  火："+SiZhu.huoCent +
              "  土："+SiZhu.tuCent +
              "  金："+SiZhu.jinCent +
              "  水："+SiZhu.shuiCent +
              sep +
              bazi.bazi[i][8] +
              sep;
        }
      }
    }

    SiZhu.muCent = mucent;
    SiZhu.jinCent = jincent;
    SiZhu.shuiCent = shuicent;
    SiZhu.huoCent = huocent;
    SiZhu.tuCent = tucent;
    SiZhu.muCent1 = mucent1;
    SiZhu.jinCent1 = jincent1;
    SiZhu.shuiCent1 = shuicent1;
    SiZhu.huoCent1 = huocent1;
    SiZhu.tuCent1 = tucent1;
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;

    return str;
  }

  private void getMainInfo(int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz) {
    SiZhu.ng = ng;
    SiZhu.nz = nz;
    SiZhu.yg = yg;
    SiZhu.yz = yz;
    SiZhu.rg = rg;
    SiZhu.rz = rz;
    SiZhu.sg = sg;
    SiZhu.sz = sz;
    daow.getWuXingCent();
  }

  /**
   * 得到喜用神
   * @return
   */
  public int[] getXYShen() {
    analyseYongShen();
    return xyShen;
  }

  /**
   * 取用神
   * 0主,1比肩 2劫财 3食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
   * @return
   */
  public String analyseYongShen() {
    buf.delete(0,buf.length());
    uninit();
    init();
    int ysType = 0;

    //1八正格
    ysType = daow.getBaGe();
    if(ysType==3) {
      getShiGeYongShen();
    }else if(ysType==4) {
      getShangGeYongShen();
    }else if(ysType==5 || ysType==6) {
      getCaiGeYongShen();
    }else if(ysType==7){
      getShaGeYongShen();
    }else if(ysType==8) {
      getGuanGeYongShen();
    }else if(ysType==9 || ysType==10) {
      getYinGeYongShen();
    }
    //2五格
    ysType = daow.getWuGe();
    if(ysType==YiJing.MU) {
      getRenShouGe();
      geju = 52;
    }else if(ysType==YiJing.HUO) {
      getYanShangGe();
      geju = 54;
    }else if(ysType==YiJing.TU) {
      getJiaQiangGe();
      geju = 55;
    }else if(ysType==YiJing.JIN) {
      getCongGeGe();
      geju = 51;
    }else if(ysType==YiJing.SHUI) {
      getRunXiaGe();
      geju = 53;
    }
    //3从格
    ysType = daow.getCongGe();
    if(ysType==1) {
      getCongErGe();
      geju = 41;
    }else if(ysType==2) {
      getCongCaiGe();
      geju = 42;
    }else if(ysType==3) {
      getCongGuanGe();
      geju = 43;
    }else if(ysType==4) {
      getCongQiangGe();
      geju = 44;
    }
    else if(ysType==5) {
      getCongWangGe();
      geju = 45;
    }

    //4化气格
    ysType = daow.getHuaQiGe();
    if(ysType>0) {
      getHuaQiGe();
      geju = 50;
    }
    //5建禄格
    if(daow.isJianLu()) {
      //todo
    }
    //6月刃格
    if(daow.isYueRen()) {
      getRenGeYongShen();
    }
    //7月劫
    if(daow.isYueJie()) {
      getJieGeYongShen();
    }
    //8杂格不论
    //9其它补充
    {
      getTongGuan();
      isGuanShaHunZa();
      getXieShangBangZhu();
      getWuJiBiFan();
      getZhuoQingKu();
      getZhenJiaShen();
      getUniversalCanKao();
    }
    return buf.toString();
  }

  /**
   * 运程分析
   */
  public String analyseYunCheng() {
    uninit();
    init();
    buf.delete(0,buf.length());

    if(geju==41) {
      add("从儿格，喜財运秀氣流行，比劫运生食傷不忌，食傷运相得益彰；官殺运剋我且与食傷戰剋損耗不吉;印綬运剋食傷故大忌");
    }else if(geju==42) {
      add("从财格，喜財运黨財，食傷运生財，官运秀氣流行，大忌比劫运剋財，印运生身之助克财");
    }else if(geju==43) {
      add("从官杀格，喜官殺运生助，財运生官殺大吉，忌印运洩官殺生日主，比劫运不吉，食伤运破格也");
    }else if(geju==45) {
      add("从旺格，喜印运、比劫运，食傷运與印相剋不吉，財运克用神大凶，官殺运凶");
    }else if(geju==44) {
      add("从强格，喜印运、比劫运，食傷运與印相剋不吉，財运克用神大凶，官殺运凶");
    } else if(geju==50) {
      if(SiZhu.rg==YiJing.JIA || SiZhu.rg==YiJing.JI) {
        add("甲己化土格，宜行火土运，即南方火运");
      }else if(SiZhu.rg==YiJing.YI || SiZhu.rg==YiJing.GENG) {
        add("乙庚化金格，宜行土金运，即西方金运");
      } else if(SiZhu.rg==YiJing.BING || SiZhu.rg==YiJing.XIN) {
        add("丙辛化水格，宜行西北金水运");
      } else if(SiZhu.rg==YiJing.DING || SiZhu.rg==YiJing.REN) {
        add("丁壬化木格，宜行东北水木运");
      } else if(SiZhu.rg==YiJing.WUG || SiZhu.rg==YiJing.GUI) {
        add("戊癸化火格，宜行木火运，即东南暖身运");
      }
    }else if(geju==51) {
      add("从革格，喜土金运生助，也喜水运洩秀，木財运必須有水通關，大忌火运");
    }else if(geju==52) {
      add("曲直仁壽格，喜水木运生助，火运洩秀亦吉，土財运必須有火通關，大忌金运");
    } else if(geju==53) {
      add("润下格，喜金水运生助，也喜木运洩秀，火財运必須有木通關，大忌土运");
    } else if(geju==54) {
      add("炎上格，喜木火运生助，也喜土运洩秀，但富而不貴，金財运必須有土通關，大忌水运");
    } else if(geju==55) {
      add("稼墙格，喜火土运生助，也喜金运洩秀，水財运必須有金通關，大忌木运");
    }else if(geju==66) {
      add("偏枯之造，天寒地冻，宜行东南木火运，暖身调侯为要");
    }else if(geju==81) {
      add("身旺印旺，大利财运损印，食伤运生财泄秀，官杀运亦美，忌印，比劫强身运");
    }else if(geju==82) {
      add("身旺，大利食伤运生财泄秀，官杀运制比劫，财运生官，忌印，比劫强身运");
    }else if(geju==83) {
      add("身弱官杀纵横，大利印运泄杀生身，比劫强身运亦美，食伤运泄身，官杀运损耗，财运克印均非美地");
    }else if(geju==84) {
      add("身弱，大利印运生身，比劫强身运亦美，食伤运泄身，官杀运损耗，财运克印均非美地");
    }else if(geju==99) {
      add("偏枯之造，火炎土燥，宜行西北金水运，以阴寒之地调侯为要");
    }

    int ig = 0, iz = 0;
    int ssg1 = 0, ssg2 = 0, ssz1 = 0, ssz2 = 0, ssz3 = 0, ssz0 = 0;
    String sg = null, sz = null;
    int ij=0,ik=0;
    if(SiZhu.DAYUN[1][1]==0) {
      daos.getDaYun();
      daos.getQiYunSui();
    }
    int qiyun = SiZhu.QIYUNSUI;
    for(int i=1; i<SiZhu.DAYUN.length; i++) {
      ij=0;
      ik=0;
      _t = null;
      ig = SiZhu.DAYUN[i][1];
      iz = SiZhu.DAYUN[i][2];
      sg = YiJing.TIANGANNAME[ig];
      sz = YiJing.DIZINAME[iz];

      for(int j=0; j<xyShen.length; j++) {
        if(xyShen[j]==-1)
          break;
        ssg1 = daop.getShenShaName2(xyShen[j],1)[0];
        ssg2 = daop.getShenShaName2(xyShen[j],1)[1];
        ssz0 = daop.getShenShaName2(xyShen[j],2)[0];
        ssz1 = daop.getShenShaName2(xyShen[j],2)[1];
        ssz2 = daop.getShenShaName2(xyShen[j],2)[2];
        ssz3 = daop.getShenShaName2(xyShen[j],2)[3];
        if(ssg1==ig || ssg2==ig) {
          ij++;
        }
        if(ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz) {
          ik++;
        }
        if(ij>0 && ik>0) {
          _t = getJiXiongCi(true, i);
          break;
        }
      }

      for(int j=0; j<xyShen.length; j++) {
        if(ij>0 && ik>0) {
          ij = 0;
          ik = 0;
          break;
        }
        if(xyShen[j]==-1)
          break;
        ssg1 = daop.getShenShaName2(xyShen[j],1)[0];
        ssg2 = daop.getShenShaName2(xyShen[j],1)[1];
        ssz0 = daop.getShenShaName2(xyShen[j],2)[0];
        ssz1 = daop.getShenShaName2(xyShen[j],2)[1];
        ssz2 = daop.getShenShaName2(xyShen[j],2)[2];
        ssz3 = daop.getShenShaName2(xyShen[j],2)[3];
        //System.err.println("用神："+xyShen[j] +";"+ssg1+":"+ssg2+":"+ssz0+":"+ssz1+":"+ssz2+":"+ssz3);
        if(((ssg1==ig || ssg2==ig) && daop.getGaiTouJieJiao(ig,iz,1)!=2) ||
           ((ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz) && daop.getGaiTouJieJiao(ig,iz,2)!=1) ||
           ((ssg1==ig || ssg2==ig) &&(ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz))) {
          _t = getJiXiongCi(true, i);
          break;
        }else if((ssg1==ig || ssg2==ig) && daop.getGaiTouJieJiao(ig,iz,1)==2) {
          _t = getGaiTou(i);
          break;
        }else if((ssz0==iz || ssz1==iz || ssz2==iz || ssz3==iz) && daop.getGaiTouJieJiao(ig,iz,2)==1) {
          _t = getJiejiao(i);
          break;
        }
      }
      if(_t == null) _t = getJiXiongCi(false, i);
      add( ((i-1) * 10 + qiyun) + "岁-" + (qiyun + 10 * i) + "岁，大运" + sg + sz + "，"+_t);
    }

    return buf.toString();
  }

  private String getJiejiao(int i) {
    String gaitou[] = new String[] {
        "命喜运支而逢盖头，吉凶减半，此运平平；如命局天干有制则吉凶再减半，再逢流年制化则无凶矣；如运支逢命局冲则反凶",
        "运支逢盖头，吉凶减半，平运；命局天干有制吉凶再减半，流年制化无凶；运支逢命局冲反凶",
        "盖头运，运气平平；如命局天干有制则吉凶再减半，再逢流年制化则无凶矣；如运支逢命局冲则反凶",
    };
    if(i%3==0) return gaitou[2];
    if(i%2==0) return gaitou[1];
    else return gaitou[0];
  }

  private String getGaiTou(int i) {
    String gaitou[] = new String[] {
        "命喜运干而运支不载，谓之截脚，此运不吉矣；如命局天干有助则和平无凶，如有反克则十年皆凶",
        "命喜运干逢截脚，此运平平；命局天干有助则无凶，反克则凶",
        "运干吉而支不载，谓截脚，不吉；如命局天干有助则和平无凶，如有反克则十年皆凶",
    };
    if(i%3==0) return gaitou[2];
    if(i%2==0) return gaitou[1];
    else return gaitou[0];
  }

  private String getJiXiongCi( boolean b, int i) {
    String str = null;
    String[] jis = new String[] {
        "",
        "芹香早采",
        "秋桂高攀",
        "财喜辐辏，事事称心",
        "春风吹枊，红绫易公子之裳，杏露沾衣，膏雨沐王孙之袖",
        "自应台曜高躔，朗映紫薇",
        "叨化日之光，丰衣足食",
        "蒲柳望秋而凋，松柏经冬而茂",
        "名利裕如",
        "用神得地，吉",
        "澄浊求清清得净，时来寒谷也回春",
        };
    String[] chas = new String[] {
        "",
        "难免珠沉沧海，顺受其正",
        "蹭蹬秋闱纳捐瞩，仁路亦不能通达",
        "刑耗异常",
        "奔驰不遇",
        "格局尽破，不禄",
        "青蚨化蝶，家业渐消",
        "财若春后霜雪，事业萧条",
        "虽稍有生色，亦是春月秋花",
        "起倒不宁，刑丧难免"
        };

    if(b)  str = jis[i];
    else  str = chas[i];

    return str;
  }

  /**
   * 财格
   */
  private void getCaiGeYongShen() {
    //buf.delete(0,buf.length());
    add("月令财星，财喜食神以相生，生官以护财。财喜根深，不宜太露，然透一位以清用，格所最喜，不为之露。太多则露矣");
    if((SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU)
      add("夏木用财，火炎土燥，贵多就武");
    if((ssnum[5][1]>0 || ssnum[6][1]>0) && (ssnum[9][1]>0 || ssnum[10][1]>0)) {
      if(daop.getShenShaIsKe(9,1)>0 || daop.getShenShaIsKe(10,1)>0) {
        add("财格佩印者，盖孤财不贵，佩印帮身，即印取贵，然财印不宜相并(紧贴)，即有好处，小富而已");
      }else if(daop.getShenShaIsKe(9,1)==0 && daop.getShenShaIsKe(10,1)==0){
        add("财格佩印，财印二不相碍，盖孤财不贵，佩印帮身，即印取贵");
      }
    }
    if(ssws[2]<=2 && ssws[0]>=4)
      add("财轻比重，则财被分夺，伤官可解，以食伤生财为美，伤能成格也");
    if(ssnum[7][1]>0 && daop.getShenShaIsKe(7,1)>0)
      add("财带七煞，喜食伤透出制煞生财，使财不泄于煞而成贵格也，否则财不为我用而党煞，为财格所忌");
    if(ssnum[7][1]>0 && daop.getShenShaIsHe(7,1)>0)
      add("财带七煞，有干透合煞存财，使财不泄于煞而成贵格也，否则财不为我用而党煞，为财格所忌");
    if(ssnum[7][1]>0 && daop.isHasRen(SiZhu.rg))
      add("财带七煞，有刃来解厄贵格成也，否则财不为我用而党煞，为财格所忌");
    if(ssnum[3][1]>0 || ssnum[4][1]>0)
      add("财行煞运，命中食伤透出，似忌而实喜");

    if(ssnum[8][1]>0 && (ssnum[3][1]>0 || ssnum[4][1]>0))
      add("财旺生官，以财为用，官为相，但天干食伤露，破格也");
    if(ssnum[8][1]>0 && (ssnum[5][1]>0 || ssnum[6][1]>0))
      add("财旺生官，天干财多财露不忌，有官则劫退");
    if(ssws[2]>=4 && ssnum[1][1]==0 && ssnum[2][1]==0 && (ssnum[3][1]>0 || ssnum[4][1]>0))
      add("财旺无劫而透伤，则须佩印");
    if(ssws[2]>=4 && ssnum[1][1]==0 && ssnum[2][1]==0 && ssnum[9][1]>0 && ssnum[10][1]>0)
      add("财旺无劫又无印，财多身弱，难望富贵");
    if(ssws[0]<=2 && sscent[3]>0 && sscent[4]>0)
      add("有财身弱用煞印，党煞为忌，印以化之，格成富局");
    if(ssws[0]>=4 && daop.isHasRen(SiZhu.rg))
      add("劫刃太重，弃财就煞，化煞为权，才从煞化，此财格归入偏官格中");
    _getYongShen();
  }

  /**
   * 官格
   */
  private void getGuanGeYongShen() {
    add("月令官星，官喜透财以相生，生印以护官");
    if(ssnum[8][1]>1 || ssnum[8][2]>1)
      add("官多同煞");
    if((ssnum[5][1]>0 || ssnum[6][1]>0) && daop.getShenShaIsKe(8,1)>10)
      add("伤官见官，透财而地位配置合宜，则伤官生财来生官");
    if(daop.getShenShaIsKe(8,1)>0 && ssnum[7][1]>0)
      add("用官本忌伤官，官星不清而带煞则不忌，取其可以制煞也");
    if(daop.getShenShaIsKe(8,1)>0 && ssnum[5][1]==0 && ssnum[6][1]==0)
      add("伤官见官而不透财，破格矣");
    if(ssnum[3][1]>0 || ssnum[4][1]>0)
      add("用官而带伤食，运喜官旺印绶之乡，伤食为害，逢煞不忌矣");
    if(ssnum[8][1]>0 && (daop.getShenShaIsHe(5,1)>0 || daop.getShenShaIsHe(6,1)>0))
      add("用官透财，而财逢合孤官无辅，破格也");
    if(daop.getShenShaIsChong(8)==10)
      add("伤刃冲官星，破格也");
    if(daop.getShenShaIsKe(8,1)>0 && (ssnum[9][1]>0 || ssnum[10][1]>0))
      add("四柱带伤，反推佩印，本造印克伤是有情而非情之至");
    if(daop.getShenShaIsKe(8,1)>0 && daop.getShenShaIsHe(4,1)>0)
      add("四柱带伤，反推佩印，本造伤印一合以清官，是有情而兼有力者也");
    if(ssws[3]>=3 && (ssnum[5][1]>0 || ssnum[6][1]>0) && ssws[0]>=3)
      add("官强财透，身逢禄刃，三者皆均，遂成大贵，以其有力也");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU
       && (SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU))
      add("印绶遇官，此谓官印双全，无人不贵。而冬木逢水，虽透官星，亦难必贵，盖金寒水冻不生木也");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.MU)
      add("印绶遇官，此谓官印双全，无人不贵");
    if(daop.getShenShaIsHe(9,1)!=0 || daop.getShenShaIsHe(10,1)!=0)
      add("官逢印运，而本命有合，似喜而实忌");
    if(ssnum[9][1]>0 || ssnum[10][1]>0)
      add("官逢伤运，而命透印，似忌而实喜");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && ssws[0]>=3 && ssws[4]>=3)
      add("正官佩印，身旺印重，运喜财星损印，伤食泄秀生财为美运");
    if((ssnum[9][1]>0 || ssnum[10][1]>0) && ssws[0]<=2 && ssws[4]>=3)
      add("官重身轻而佩印，宜行比劫禄印之地");
    if(sscent[1]>0 && (ssnum[9][1]>0 || ssnum[10][1]>0) &&
       ssws[1]>=3 && ssws[4]<=2)
      add("正官带伤食而用印，若伤重印轻喜行印地，官旺能生印亦喜，财运破印大忌");
    if(ssnum[9][1]+ssnum[10][1]>1 || ssnum[9][2]+ssnum[10][2]>1)
      add("印绶重叠，用食伤泄秀，财运损印反吉");
    if(daop.getShenShaIsHe(7,1)>0 && daop.getShenShaName(7,1)[0]%2==1)
      add("合煞有二，阳干合煞用劫，阴干合煞用伤。本造用劫合煞，最忌再行煞运");
    if(sscent[2]>0 && sscent[4]>0)
      add("有官而兼带财印者，所谓身强值三奇，尤为贵气。三奇者，财官印也，只要以官隔之，使财印两不相伤，其格便大");
    if(ssws[0]<=2 && ssws[3]>=4)
      add("杀重而身轻，非贫即夭");
    if(ssws[3]<=2 && ssws[1]>=4)
      add("杀微而制过，虽学无成");
    _getYongShen();
  }

  /**
   * 印格
   */
  private void getYinGeYongShen() {
    add("月令印星，喜官煞以相生，劫才以护印");
    if(ssws[4]<=2 && sscent[3]>0)
      add("印轻逢煞，印格成也");
    if(ssnum[8][1]>0)
      add("或官印双全，印格成也");
    if(ssws[0]>=3 && ssws[4]>=3 && sscent[1]>0)
      add("身印两旺而用食伤泄气，印格成也");
    if(ssws[4]>=3 && ssnum[5][1]+ssnum[6][1]>0 && ssws[2]<=2)
      add("印多逢财而财透根轻，印格成也");
    if(ssws[4]<=2 && ssws[2]>=3)
      add("印轻逢财则印被财破，为印格之忌也");
    if(ssnum[9][1]>0 && ssnum[5][1]+ssnum[6][1]>0)
      add("透煞而又透财则财破印党煞，为印格之忌也");
    if(ssws[0]>=3 && ssws[4]>2 && sscent[1]==0 && ssnum[7][1]>0)
      add("身强印重不见食神而透煞生印又生身，为印格之忌也");
    if(ssws[0]>=3 && ssws[4]>2)
      add("身强印旺，透煞孤贫，盖身旺不劳印生，印旺何劳煞助？偏之又偏，以其无情也");
    if(ssws[0]<=2 && ssnum[7][1]>0 && daop.getShenShaIsKe(9,1)>10 && daop.getShenShaIsKe(10,1)>10)
      add("印用七煞，身弱，用印以化煞也，见财则破印党煞本为所忌，但位置妥贴财生煞生印生身为因成得败");
    if(ssws[0]<=2 && ssnum[7][1]>0 && (daop.getShenShaIsKe(9,1)==10 || daop.getShenShaIsKe(10,1)==10))
      add("印用七煞，身弱，用印以化煞也，见财紧贴破印党煞大忌");
    if(ssws[0]<2 && ssnum[7][1]>0)
      add("印逢官运，而本命用煞，似喜而实忌");
    if(ssws[0]<=2 && ssws[3]>=3 && daop.getShenShaIsKe(7,1)>0)
      add("身弱宜印，而制煞之格不宜印地者，恐其制伤夺食也");
    if(ssws[4]<3 && ssws[1]>=3)
      add("印浅身轻，而用层层伤食，则寒贫之局矣");
    if(ssws[4]>=3 && ssnum[5][1]+ssnum[6][1]>0)
      add("印多而用财者，印重身强，透财以抑太过，权而用之，只要印根深，无防财破");
    if(ssws[4]<3 && ssws[2]>2)
      add("印轻财重，又无劫财以救，则为贪财破印，贫贱之局也");
    if(ssws[4]>2 && ssws[2]<3 && ssnum[3][1]+ssnum[4][1]>0)
      add("印重财轻而兼露伤食，财与食相生，轻而不轻，即可就富，亦不贵矣。若天干食印一合不生身，亦可为贵");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)>0)
      add("有印而兼透官煞，煞被合，为贵格");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsKe(7,1)>0)
      add("有印而兼透官煞，煞有制，为贵格");
    if(ssws[0]>2 && ssnum[8][1]>0 && ssws[4]>2)
      add("印绶用官者，官露印重，财运反吉，伤食之方，亦为最利?因食生财克印，至于食制官，因有强印制不忌");
    if(ssws[0]>2 && ssnum[3][1]+ssnum[4][1]>0)
      add("印绶而用伤食，因身旺，财运反吉，伤食利，若行官运，反见其灾，煞运则反能为福矣");
    if(ssws[0]>2 && ssnum[7][1]>0)
      add("印用七煞，运喜伤食，身旺之方，亦为美地，一见财乡，因克印不能化煞，其凶立至");
    if(ssws[0]<3)
      add("印绶遇财，运喜劫地，官印亦亨，财乡则忌");
    _getYongShen();
  }

  /**
   * 食格
   */
  private void getShiGeYongShen() {
    add("月令食神，食喜身旺以相生，生财以护食");
    if(sscent[3]>0 && sscent[4]>0 && sscent[2]==0)
      add("食带煞印而无财，食为枭印所夺，只得弃食就煞，食格成也");
    if(ssnum[7][1]+ssnum[7][2]>0 && sscent[2]>0 && sscent[4]>0 && daop.getShenShaIsSheng(7,1)==10)
      add("食带煞印见财，食生财，财煞紧贴党煞，再破印，格局破矣");
    if(ssnum[7][1]+ssnum[7][2]>0 && sscent[2]>0 && sscent[4]>0 && daop.getShenShaIsSheng(7,1)!=10)
      add("食带煞印见财，食生财，而财先煞后，食以间之，财不党煞，可贵也");
    if(ssws[0]>2 && ssws[4]>0 && ssnum[3][1]>0 &&
       (SiZhu.yz==YiJing.HAI ||SiZhu.yz==YiJing.ZI ||SiZhu.yz==YiJing.CHOU) &&
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU)
      add("身印两旺，透食则贵，凡印格皆然。又本造为冬木，尤为秀气，以冬木逢火，不惟可以泄身，而即可以调候也");
    else if(ssws[0]>2 && ssws[4]>0 && ssnum[3][1]>0)
      add("身印两旺，透食则贵，凡印格皆然");
    if((ssnum[3][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && ssnum[1][1]+ssnum[2][1]==0) ||
      (ssnum[3][2]>0 && ssnum[9][2]+ssnum[10][2]>0 && ssnum[1][2]+ssnum[2][2]==0))
      add("食神逢印而无比，谓枭印夺食，格破矣");
    if(ssnum[3][1]>0 && ssnum[9][1]+ssnum[10][1]>0 &&
       (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU)
      add("食神虽逢正印，亦谓夺食，本造夏木火盛，轻用之既秀而贵，与木火伤官喜见水同论，亦调候之谓也");
    if(ssws[1]>2 && ssws[3]<3)
      add("食太重而煞轻，印运最利，逢财反吉矣");
    if(ssws[0]>2 && ssws[3]>2)
      add("身旺煞强，则食伤制煞，极为贵格。运喜食伤，唯忌财运");
    if(ssws[3]<3 && ssws[1]>2)
      add("食伤制煞太过，即煞轻食重也，法须扶煞，故财运反吉。然不及印运之美，盖印可以去食之太过，化煞滋身，一得三用也");
    _getYongShen();
  }

  /**
   * 煞格
   */
  private void getShaGeYongShen() {
    add("月令七杀，七煞喜食神以制伏，忌财印以资扶");
    if(sscent[1]==0 && sscent[2]>0)
      add("七煞以制为用，有财之生而无制，七煞肆逞而身危矣");
    if(ssws[0]>2 && ssnum[7][1]>0 && ssws[1]>2)
      add("身强煞露而食神又旺，三者皆备，极等之贵，以其有力也");
    if(ssws[1]>2 && ssws[3]>2 && ssws[0]<3)
      add("煞强食旺而身无根，或夭或贫，以其无力也，是皆格之低而无用者也");
    if(daop.getShenShaIsHe(3,1)>0)
      add("煞刃逢食，格之败也，然食神合官留煞，而官煞不杂，煞刃局清，是因败得成矣");
    if(ssnum[3][1]>0 && daop.getShenShaIsHe(3,1)==0)
      add("煞刃逢食，格之败也");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)==0 && daop.getShenShaIsKe(7,1)==0)
      add("官煞混杂，煞刃不清，格之败矣");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(8,1)==0 && daop.getShenShaIsKe(8,1)==0)
      add("官煞混杂，煞刃不清，格之败矣");
    if(daop.getShenShaIsKe(7,1)>0)
      add("煞逢食制，则煞为用，食为相");
    if(daop.getShenShaIsKe(7,1)>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("煞逢食制，则煞为用，食为相。煞逢食制，透印无功，印破格也");
    if(ssws[0]>2 && ssws[4]<3)
      add("七煞本非美物，藉其生印不得已而用之。本造身重印轻有所不足，资为有性");
    if(ssws[0]<3 && ssws[4]>2)
      add("七煞本非美物，藉其生印不得已而用之。本造身轻印重有所不足，资为有性");
    if(ssws[0]>2 && ssws[4]>2 && sscent[2]==0)
      add("身印并重而用七煞，非孤则贫矣。身印并重而见七煞，则又非财不可。用财破印生煞，与用煞生印，截然不同");
    if(ssws[0]>2 && ssws[4]>2 && sscent[2]>0)
      add("身印并重而见七煞，又见财则非财不可。用财破印生煞，与用煞生印，截然不同");
    if(ssws[0]>2 && sscent[1]>0)
      add("用煞而兼带伤食，煞有制，生身而有泄，为贵格");
    if(ssws[0]>2)
      add("煞以攻身，似非美物，百大贵之格，多存七煞");
    if(ssws[0]<3)
      add("七煞用印，印能护煞，本非所宜，而印有情，便为贵格");
    if(ssws[3]>2 && ssws[0]<3 && sscent[4]==0 && sscent[1]>0)
      add("煞重身轻，有食而无印，克泄交加身不能当，不若就印耳");
    if(ssws[3]>2 && ssws[0]<3 && sscent[4]>0)
      add("煞重身轻，用食则克泄交加身不能当，幸有印转而就之，不论通根月令否，亦为无情而有情。格亦许贵，但不大耳");
    if(ssws[0]>2 && sscent[2]>0 && sscent[1]==0)
      add("有煞而用财，财以党煞，并非所喜也");
    if(ssws[0]>2 && sscent[2]>0 && daop.getShenShaIsKe(1,1)>0)
      add("有煞而用财者，财以党煞，并非所喜也，但食被制，不能伏煞，而财以去印存食，便为贵格");
    if(ssws[0]>2 && ssws[3]<3 && sscent[4]>0 && sscent[2]>0)
      add("身重煞轻，煞又化印，用神不清，而借财以清格，亦为贵格");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)>0 && daop.getShenShaIsKe(7,1)>0)
      add("煞而杂官，本造煞逢制合，取清则贵");
    if(ssnum[7][1]>0 && ssnum[8][1]>0 && daop.getShenShaIsHe(7,1)>0 && daop.getShenShaIsKe(7,1)>0)
      add("煞而杂官者，本造官逢制合，取清则贵");
    if(ssws[0]>2 && ssws[3]<3 && sscent[2]>0)
      add("身强杀浅，以财星滋杀，贵格");
    if(ssws[0]>2 && ssws[3]>2 && sscent[1]>0)
      add("身杀两停，以食神制杀，贵格");
    if(ssws[0]<3 && ssws[3]>2 && sscent[4]>0)
      add("杀强身弱，以印绶化杀，贵格");
    if(ssws[3]>2 && ssws[0]<3)
      add("局中杀重身轻，非贫即夭");
    if(ssws[1]>2 && ssws[3]<3)
      add("局中制杀太过，虽学无成");
    if(ssws[0]<3 && ssws[3]>3)
      add("行运杀旺，复行杀地，立见凶灾");
    if(daop.getShenShaIsKe(7,1)>0)
      add("制杀再行制乡，必遭穷乏");
    _getYongShen();
  }

  /**
   * 伤格
   */
  private void getShangGeYongShen() {
    add("月令伤官，喜佩印以制伏，生财以化伤");
    add("伤官虽非吉神，实为秀气，故文人学士，多于伤官格内得之");
    if(ssnum[9][1]+ssnum[10][1]>0 && ssws[1]>2 && ssws[4]>2)
      add("伤官佩印，伤旺，印有根伤官格成也");
    if(ssws[1]>2 && ssws[0]<3 && ssnum[7][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("伤旺，身弱，干透煞印，伤官格成也");
    if(ssnum[7][1]>0 && sscent[2]==0 && sscent[4]>0)
      add("伤官带煞，无财破印，伤官格成也");
    if(ssws[0]>2 && ssnum[9][1]+ssnum[10][1]>0)
      add("身旺用伤，本无需佩印，佩印则破格也");
    if(ssws[1]<3 && ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("伤轻见印，则伤为印所制，不能发舒其秀气");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && ssws[0]<3)
      add("伤官佩印，秀而且贵");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && ssws[0]>2 && ssws[1]<3 && ssws[4]>2)
      add("伤官佩印，本秀而贵，而身旺伤浅印重，不贵不秀，盖欲助身则身强，制伤则伤浅，重印无用是亦无情，为格之败也");
    if(ssnum[4][1]>0 && ssnum[8][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.JIN &&
       ssnum[5][1]+ssnum[6][1]>0)
      add("虽非金水伤官而见官，但透财则化伤为财，伤非其伤，作财旺生官而不作伤官见官");
    if(ssnum[4][1]>0 && ssnum[8][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.JIN &&
       ssnum[5][1]+ssnum[6][1]==0)
      add("伤官见官，为祸百端");
    if(ssnum[4][1]>0 && ssnum[8][1]>0 && YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN)
      add("伤官见官，为祸百端，而金水伤官喜见官煞，反为秀气。非不畏伤，调候为急，权而用之也");
    if(ssnum[4][1]>0 && ssnum[7][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.JIN &&
       (SiZhu.yz!=YiJing.HAI && SiZhu.yz!=YiJing.ZI && SiZhu.yz!=YiJing.CHOU))
      add("伤官带煞，随时可用");
    if(ssnum[4][1]>0 && ssnum[7][1]>0 && YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN &&
       (SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU))
      add("伤官带煞，随时可用，而用之冬金，其秀百倍");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && YiJing.TIANGANWH[SiZhu.rg]!=YiJing.MU &&
       SiZhu.yz!=YiJing.SI && SiZhu.yz!=YiJing.WUZ && SiZhu.yz!=YiJing.WEI)
      add("伤官佩印，随时可用");
    if(ssnum[4][1]>0 && ssnum[9][1]+ssnum[10][1]>0 && YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU &&
       (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI))
      add("伤官佩印，随时可用，而用之夏木，其秀百倍，火济水，水济火也");
    if(ssnum[4][1]>0 && ssnum[5][1]+ssnum[6][1]>0) {
      add("伤官用财，本为贵格");
      if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.SHUI)
        add("用之水木伤官，犹为秀气，水木伤官喜见财也");
      if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN)
        add("而用之金水，即使小富亦多不贵，冻水不能生木也");
      if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU &&
         (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI))
        add("而用之夏木贵而不甚秀，燥土不甚灵秀也。");
    }
    if(ssws[1]<3 && ssws[0]>2 && ssws[4]>2)
      add("伤轻身重而印绶多见，贫穷之格也");
    if(daop.getShenShaIsKe(5,1)>10 &&  daop.getShenShaIsKe(6,1)>10)
      add("伤官兼用财印者，财印相克，本不并用，但干头两清而不相碍");
    if(ssws[4]>2 && ssnum[9][1]+ssnum[10][1]>0 && ssnum[5][1]+ssnum[6][1]>0)
      add("佩印者印太重而带财，调停中和，遂为贵格");
    if(ssws[1]>2 && ssws[0]<3 && ssnum[7][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("伤官用煞印，伤多身弱，赖煞生印以邦身而制伤。有非金水而见官，化伤为财，伤非其伤，作财旺生官而不作伤官见官");
    _getYongShen();
  }

   /**
    * 曲直仁壽格
    */
   private void getRenShouGe() {
     add("曲直仁壽格---以木為用神,忌金剋,喜水木生助,火洩秀亦吉,若見土財,必須有火通關");
     add("有曲直仁壽格,專取乙木,甲木則不入此格,蓋因甲木氣旺,宜剋宜洩,剋時以財官,洩時以食傷,不以專旺之曲直格論");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * 炎上格
    */
   private void getYanShangGe() {
     add("炎上格---以火為用神,忌水來剋,喜木火生助,如遇金為財,必須有土通關,見土之食傷,富而不貴,蓋土晦火之光,難取貴,而土能生金財,發富");
     add("有论丁火為衰退之氣,不入炎上格,祇依從旺格論,其貴氣遜於炎上格");
     add("又無論丙丁火,若局內見土多晦火,須用金財以洩土氣,乃是火炎土燥,亦不作炎上格論");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * 稼穡格
    */
   private void getJiaQiangGe() {
     add("稼穡格---以土為用神,忌木來剋,喜火土生助,逢金吐秀氣吉,如遇水為財,必須有金通關");
     add("有以己土為正,戊土大多作火炎土燥看");
     add("又稼穡格生於未月宜用庚辛金洩秀氣,生於戌,丑月宜用丙丁火調候,至生於辰月,木有餘氣能剋土,多作雜氣財官論,鮮有成格者");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * 從革格
    */
   private void getCongGeGe() {
     add("從革格---以金為用神,忌火來剋,喜土金生助,喜水洩秀,若遇木為財,必須有水通關");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

   /**
    * 潤下格
    */
   private void getRunXiaGe() {
     add("潤下格---以水為用神,忌土來剋,喜金水生助,木來吐秀,若遇火為財,必須有木通關");
     xyShen = new int[]{4,0,1,-1,-1};
     jiShen = new int[]{3,2,-1,-1,-1};
   }

  /**
   * 从官杀格
   */
   private void getCongGuanGe() {
     add("从格皆取所从之神为用神，喜生旺者扶助，大忌克地泄地。");
     add("从格见克地為破格，一般是以正格論之。另外見到解救之神，則可反破為成。此时以反破為成者為用神，所從之旺神為喜神。");
     add("凡從格純粹者格局為真,自然富貴尊崇.假從格者不純粹,即柱內見有該格局所忌之五行者,而不能入於普通格局,又別無用神可取用,祇得從柱內旺神之勢,謂之假從格");
     add("假從格者,其局內之忌神為病,須俟行運去之方發富貴,運過則又敗矣.至真從格者,格局本高,雖行運不佳,仍然富貴,祇不能達巔峰狀態而已,蓋其原有相當之富貴存在,豈全恃行運! ");
     add("從官殺格---既從之,則以官殺為用神,喜官殺及財生官殺,忌印洩殺及生日主");
     xyShen = new int[]{3,2,-1,-1,-1};
     jiShen = new int[]{1,4,0,-1,-1};
   }

   /**
   * 从儿格
   */
   private void getCongErGe() {
     add("从格皆取所从之神为用神，喜生旺者扶助，大忌克地。");
     add("从格见克地為破格，一般是以正格論之。另外見到解救之神，則可反破為成。此时以反破為成者為用神，所從之旺神為喜神。");
     add("凡從格純粹者格局為真,自然富貴尊崇.假從格者不純粹,即柱內見有該格局所忌之五行者,而不能入於普通格局,又別無用神可取用,祇得從柱內旺神之勢,謂之假從格");
     add("假從格者,其局內之忌神為病,須俟行運去之方發富貴,運過則又敗矣.至真從格者,格局本高,雖行運不佳,仍然富貴,祇不能達巔峰狀態而已,蓋其原有相當之富貴存在,豈全恃行運! ");
     add("從兒格---取食傷為用神,喜財星受食傷之生,秀氣再流行;比劫不忌,蓋可生食傷;見食傷亦喜,蓋相得益彰;逢官殺不吉,因來剋我身,且食傷與官殺戰剋,損耗食傷之氣;印綬尤忌,剋食傷故也");
     xyShen = new int[]{1,2,0,-1,-1};
     jiShen = new int[]{4,3,-1,-1,-1};
   }

   /**
   * 从财格
   */
   private void getCongCaiGe() {
     add("从格皆取所从之神为用神，喜生旺者扶助，大忌克地");
     add("凡从财格，必要食神吐秀，不但功名显达，而且一生无大起倒凶灾。最忌比劫运，柱中有食伤，能化比劫生财之妙也。若无若食伤吐秀，书香难遂，一逢比劫，无生化之情，必有起倒刑伤也。");
     add("从格见克地為破格，一般是以正格論之。另外見到解救之神，則可反破為成。此时以反破為成者為用神，所從之旺神為喜神。");
     add("凡從格純粹者格局為真,自然富貴尊崇.假從格者不純粹,即柱內見有該格局所忌之五行者,而不能入於普通格局,又別無用神可取用,祇得從柱內旺神之勢,謂之假從格");
     add("假從格者,其局內之忌神為病,須俟行運去之方發富貴,運過則又敗矣.至真從格者,格局本高,雖行運不佳,仍然富貴,祇不能達巔峰狀態而已,蓋其原有相當之富貴存在,豈全恃行運! ");
     add("從財格---以財為用神,喜財來黨財及食傷來生財,忌比劫來剋財及印來生身助我");
     xyShen = new int[]{2,1,3,-1,-1};
     jiShen = new int[]{0,4,-1,-1,-1};
   }

   /**
    * 从旺格
    */
   private void getCongWangGe() {
     add("从格皆取所从之神为用神，喜生旺者扶助，大忌克地泄地。");
     add("从格见克地為破格，一般是以正格論之。另外見到解救之神，則可反破為成。此时以反破為成者為用神，所從之旺神為喜神。");
     add("凡從格純粹者格局為真,自然富貴尊崇.假從格者不純粹,即柱內見有該格局所忌之五行者,而不能入於普通格局,又別無用神可取用,祇得從柱內旺神之勢,謂之假從格");
     add("假從格者,其局內之忌神為病,須俟行運去之方發富貴,運過則又敗矣.至真從格者,格局本高,雖行運不佳,仍然富貴,祇不能達巔峰狀態而已,蓋其原有相當之富貴存在,豈全恃行運! ");
     add("從旺格---运行比劫印绶制则吉；如局中印轻，行伤食亦佳，印旺伤轻行印运颠沛流离；官杀运，谓之犯旺，凶祸立至；遇财星，群劫相争，九死一生");
     add("因方局从旺，则生地库地，亦能发福，以再行生旺为佳");
     add("但局中如有财官无气，则碌碌终身，名利无成。再行生地库地之运，不但不能发福，而且刑耗多端");
     add("若得岁运去其官星，亦可发达，必要柱中先见食伤，然后岁运去净官煞之根，名利遂矣");
     add("如木局见土运，斯虽财神资养，先要四柱有食有伤，庶无分争之虑");
     add("见火运，谓英华发秀，须看原局有财无印，方免反克为殃，名利可遂");
     add("见金运，谓破局，凶多吉少");
     add("见水运，而局中无火，误用生助强神，亦主光亨，若四柱先有食伤，必主凶祸临身");
     xyShen = new int[]{4,0,-1,-1,-1};
     jiShen = new int[]{1,2,3,-1,-1};
   }

   /**
    * 从强格
    */
   private void getCongQiangGe() {
     add("从格皆取所从之神为用神，喜生旺者扶助，大忌克地泄地。");
     add("从格见克地為破格，一般是以正格論之。另外見到解救之神，則可反破為成。此时以反破為成者為用神，所從之旺神為喜神。");
     add("凡從格純粹者格局為真,自然富貴尊崇.假從格者不純粹,即柱內見有該格局所忌之五行者,而不能入於普通格局,又別無用神可取用,祇得從柱內旺神之勢,謂之假從格");
     add("假從格者,其局內之忌神為病,須俟行運去之方發富貴,運過則又敗矣.至真從格者,格局本高,雖行運不佳,仍然富貴,祇不能達巔峰狀態而已,蓋其原有相當之富貴存在,豈全恃行運! ");
     add("從強格---以印星為用神,可顺而不可逆也。财纯行比劫运财吉，印绶运亦佳，食伤运有印绶冲克必凶，财官运为触怒强神，大凶");
     add("因方局从強，则生地库地，亦能发福，以再行生旺为佳");
     add("但局中如有财官无气，则碌碌终身，名利无成。再行生地库地之运，不但不能发福，而且刑耗多端");
     add("若得岁运去其官星，亦可发达，必要柱中先见食伤，然后岁运去净官煞之根，名利遂矣");
     add("如木局见土运，斯虽财神资养，先要四柱有食有伤，庶无分争之虑");
     add("见火运，谓英华发秀，须看原局有财无印，方免反克为殃，名利可遂");
     add("见金运，谓破局，凶多吉少");
     add("见水运，而局中无火，误用生助强神，亦主光亨，若四柱先有食伤，必主凶祸临身");
     xyShen = new int[]{0,4,-1,-1,-1};
     jiShen = new int[]{1,2,3,-1,-1};
   }


   /**
    * 化气格
    */
   private void getHuaQiGe() {
     add("化氣格須局內生助化神之氣勢旺,隔干不化,又妒合亦不化");
     add("化氣格以純粹無雜者,真大富貴之命,縱令行運不助化神,無損其功業,只艱辛而已");
     add("其餘不純粹而雜者,乃係假化,須賴行運生助化神,則發富發貴,若無運助則又不足道也");
     add("化氣格成立者,以生化神者為用神。化氣之兩干,所化之神即是其五行,如丙辛合水,丙辛皆以水看,若見火為財");
     add("凡來剋化神者破格為忌,凡來洩化神之氣者為耗損格局");
     if(SiZhu.rg==YiJing.JIA || SiZhu.rg==YiJing.GUI) {
       xyShen = new int[] {1,2,-1, -1, -1};
       jiShen = new int[] {0,3, 4, -1, -1};
     }
     if(SiZhu.rg==YiJing.JI || SiZhu.rg==YiJing.GENG) {
       xyShen = new int[] {4,0,-1, -1, -1};
       jiShen = new int[] {3,1,2,-1, -1};
     }
     if(SiZhu.rg==YiJing.YI || SiZhu.rg==YiJing.BING) {
       xyShen = new int[] {2,3,-1, -1, -1};
       jiShen = new int[] {1,4,0,-1, -1};
     }
     if(SiZhu.rg==YiJing.XIN || SiZhu.rg==YiJing.REN) {
       xyShen = new int[] {0,1,-1, -1, -1};
       jiShen = new int[] {4,2,3,-1, -1};
     }
     if(SiZhu.rg==YiJing.DING || SiZhu.rg==YiJing.WUG) {
       xyShen = new int[] {3,4,-1, -1, -1};
       jiShen = new int[] {2,0,1,-1, -1};
     }
   }

  /**
   * 刃格
   */
  private void getRenGeYongShen() {
    add("月令阳刃，必以官煞为用神");
    if(ssnum[7][1]+ssnum[8][1]>0 &&
       (ssnum[5][1]+ssnum[6][1]>0 || ssnum[9][1]+ssnum[10][1]>0) &&
       sscent[1]==0)
      add("阳刃透官煞而露财印，不见伤官，阳刃格成也");
    if(ssnum[8][1]>0 && ssnum[4][1]+ssnum[3][1]>0)
      add("透官而见伤官，失制刃之效用矣");
    if(ssnum[7][1]>0 && daop.getShenShaIsHe(7,1)>0)
      add("透煞而煞被合，失制刃之效用矣");
    if(ssws[0]>2 && ssws[3]>2 && ssws[1]<3 && ssws[2]<3 && ssws[4]<3)
      add("煞刃两停者甚少，即使真煞刃两停，亦以印运为最宜");
    if(ssws[0]>2 && ssws[3]>2 && ssnum[7][1]+ssnum[8][1]>0)
      add("身愈旺更能用煞，官煞露而根深其贵也大");
    if(ssws[0]>2 && ssws[3]<3 && ssnum[7][1]+ssnum[8][1]>0)
      add("身愈旺更能用煞，官煞露而根浅其贵也小");
    if(ssws[0]>2 && ssnum[7][1]+ssnum[8][1]==0)
      add("身愈旺更能用煞，官煞藏而不露其贵也小");
    if(ssws[0]>2 && sscent[3]==0)
      add("身愈旺更能用煞，局无官煞则刃旺而无裁抑之神矣");
  }

  /**
   * 劫格
   */
  private void getJieGeYongShen() {
    add("月令劫财，喜透官以制伏");
    if(sscent[1]>0 && sscent[2]>0)
      add("月劫用财，则须以食伤为转枢，以食化劫，转而生财，尤为秀气");
    if(ssws[0]>2 && ssws[3]>0 && sscent[1]>0)
      add("用煞则身煞两停，宜用食制。身旺煞强，以食神制煞为用");
    if(ssws[0]>2 && ssnum[5][1]+ssnum[6][1]==0 && ssnum[8][1]==0 &&
       ssnum[7][1]>0 && ssnum[9][1]+ssnum[10][1]>0)
      add("建禄月劫，日主必旺，喜财生官，无财官而透煞印，则煞生印，转而生身，其旺无极，皆为破格也");
    if(ssws[0]>3 && ssws[2]<3)
      add("身强比重而财无气，或夭或贫，以其无力也，是皆格之低而无用者也");
    if(ssnum[7][1]>0 && ssnum[5][1]+ssnum[6][1]>0 &&
       (daop.getShenShaIsHe(7,1)>0 || daop.getShenShaIsKe(7,1)>0))
      add("用煞而又财，本为不美，然能去煞存财，又成贵格");
    if(sscent[2]+sscent[3]==0 && sscent[1]>0)
      add("其禄劫之格，无财官而用伤食，泄其太过，亦为秀气");
    if(ssnum[7][1]>0 && ssnum[8][1]>0)
      add("有禄劫而官煞竞出，必取清方为贵格");
    if(ssnum[8][1]>1)
      add("有禄劫而两官竞出，亦须制伏，所谓争正官不可无伤也");
    if(ssnum[5][1]+ssnum[6][1]>0 && ssnum[3][1]+ssnum[4][1]==0)
      add("禄劫用财而不透伤食，便难于发端");
    if(ssnum[5][1]+ssnum[6][1]==0 && ssws[2]>2)
      add("禄劫用财，干头透一位而不杂，地支根多，亦可取富，但不贵耳");
    if(ssws[3]>2 && sscent[1]==0)
      add("官煞重而无制伏，运行制伏，亦可发财，但不可官煞太重，致令身危也");
  }


  /**
   * 通關
   */
  public boolean getTongGuan() {
    if(xyShen[0]>0 && jiShen[0]>0)
      return false;
    if (ssws[2] >= 4 && ssws[0] >= 4 &&
        sscent[3] == 0 && sscent[4] == 0) {
      add("局中比劫財星兩旺，必取食傷通关为用神");
      xyShen = new int[] { 1, 3, -1, -1, -1};
      jiShen = new int[] { 4, 2, 0, -1, -1};
      return true;
    }
    if (ssws[4] >= 4 && ssws[1] >= 4 &&
        sscent[2] == 0 && sscent[3] == 0){
      add("局中印綬食傷兩旺，必取比劫通关为用神");
      xyShen = new int[] { 0, 4, 1, 2, -1};
      jiShen = new int[] { 3, -1, -1, -1, -1};
      return true;
    }
    if (ssws[4] >= 4 && ssws[2] >= 4 &&
        sscent[1] == 0){
      add("局中印綬財星兩旺，必取官杀通关为用神");
      if(sscent[3]==0) {
        xyShen = new int[] { 3, 0, -1, -1, -1};
        jiShen = new int[] { 2, 4, 1, -1, -1};
      }else{
        xyShen = new int[] { 3, 0, 2, 4, -1};
        jiShen = new int[] { 1, -1, -1, -1, -1};
      }
      return true;
    }
    if (ssws[3] >= 4 && ssws[1] >= 4 &&
        sscent[4] == 0){
      add("局中食傷官殺兩旺，必取财星通关为用神");
      xyShen = new int[] { 2, 0, 4, -1, -1};
      jiShen = new int[] { 1, 3, -1, -1, -1};
      return true;
    }
    if (ssws[3] >= 4 && ssws[0] >= 4 &&
        sscent[1] == 0 && sscent[3] == 0){
      add("局中官殺比劫兩旺，必取印綬通关为用神");
      xyShen = new int[] { 4, 1,  3, -1, -1};
      jiShen = new int[] { 2, 0, -1, -1, -1};
      return true;
    }
    return false;
  }

  /**
   * 太旺者似克我旺极者似我生均喜受克；即太旺者喜泄，旺极者从其势喜生。
   * 太衰者似生我衰极者似我克均喜被生；即太衰者喜克，衰极者宜从其势喜泄。
   */
  private void getWuJiBiFan() {
    int max = SiZhu.baifen[3];
    int min = SiZhu.baifen[0];
    int midmax = SiZhu.baifen[2];
    int midmin = SiZhu.baifen[1];
    if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU) {
      if (SiZhu.muCent > midmax && SiZhu.muCent < max)
        add("木太旺者而似金，喜火之炼也");
      if (SiZhu.muCent > max)
        add("木旺极者而似火，喜水之克也");
      if (SiZhu.muCent > min && SiZhu.muCent < midmin)
        add("木太衰者而似水也，宜金以生之");
      if (SiZhu.muCent < min)
        add("木衰极者而似土也，宜火以生之");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.HUO) {
      if (SiZhu.huoCent > midmax && SiZhu.huoCent < max)
        add("火太旺者而似水，喜土之止也");
      if (SiZhu.huoCent > max)
        add("火旺极者而似土，喜木之克也");
      if (SiZhu.huoCent > min && SiZhu.huoCent < midmin)
        add("火太衰者而似木也，宜水以生之");
      if (SiZhu.huoCent < min)
        add("火衰极者而似金也，宜土以生之");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.TU) {
      if (SiZhu.tuCent > midmax && SiZhu.tuCent < max)
        add("土太旺者而似木，喜金之克也");
      if (SiZhu.tuCent > max)
        add("土旺极者而似金，喜火之练也");
      if (SiZhu.tuCent > min && SiZhu.tuCent < midmin)
        add("土太衰者而似火也，宜木以生之");
      if (SiZhu.tuCent < min)
        add("土衰极者而似水也，宜金以生之");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN) {
      if (SiZhu.jinCent > midmax && SiZhu.jinCent < max)
        add("金太旺者而似火，喜水之济也");
      if (SiZhu.jinCent > max)
        add("金旺极者而似水，喜土之止也");
      if (SiZhu.jinCent > min && SiZhu.jinCent < midmin)
        add("金太衰者而似土，宜火以生之");
      if (SiZhu.jinCent < min)
        add("金衰极者而似木也，宜水以生之");
    }else if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.SHUI) {
      if (SiZhu.shuiCent > midmax && SiZhu.shuiCent < max)
        add("水太旺者而似土，喜木之制也");
      if (SiZhu.shuiCent > max)
        add("水旺极者而似木，喜金之克也");
      if (SiZhu.shuiCent > min && SiZhu.shuiCent < midmin)
        add("水太衰者而似金也，宜土以生之");
      if (SiZhu.shuiCent < min)
        add("水衰极者而似火也，宜木以生之");
    }
  }

  /**
   泄者食伤也，伤者官杀也。
   帮者比劫也，助者印绶也。
   */
  private void getXieShangBangZhu() {
    if(ssws[0]>=3 && ssws[3]<=2)
      add("日主旺相，柱中官杀无气，泄之则官星有损，伤则去比劫之有余，补官星之不足，所谓伤之有利，而泄之有害也");
    if(ssws[0]>=3 && sscent[3]==0 && sscent[2]==0)
      add("日主旺相，柱中财官不见，满局比劫，伤之则激而有害，不若泄之以顺其气势，所谓伤之有害，而泄之有利也");
    if(ssws[0]<=2 && ssws[2]>=3)
      add("日主衰弱，柱中财星重叠，印绶助之反坏，帮则去财星之有余，补日主之不足，所以帮之则吉，而助之则凶也");
    if(ssws[0]<=2 && ssws[3]>=3)
      add("日主衰弱，柱中官杀交加，满盘杀势，帮之恐反克无情，不若助之以化其强暴，所以帮之则凶，而助之则吉也");
  }

  public void getUniversalCanKao() {
    if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU &&
       (SiZhu.yz==YiJing.YIN || SiZhu.yz==YiJing.MAO || SiZhu.yz==YiJing.CHEN) &&
       ssnum[8][1]>0 && ssnum[3][1]+ssnum[4][1]>0)
      add("春木逢火，木火通明，不利见官，见官则忌，官能破格也");
    if(YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN &&
       (SiZhu.yz==YiJing.SHEN || SiZhu.yz==YiJing.YOU || SiZhu.yz==YiJing.XU) &&
       ssnum[3][1]+ssnum[4][1]>0 && ssnum[8][1]>0)
       add("而秋金遇水，金水相涵，见官无碍");
     if(SiZhu.rg==YiJing.JIA && daop.getGzNum(YiJing.YI,1)>0 && daop.getGzNum(YiJing.GENG,1)>0)
       add("甲以乙妹妻庚，凶为吉兆");
     if(SiZhu.rg==YiJing.YI && SiZhu.yz==YiJing.YOU &&
        daop.getGzNum(YiJing.XIN,1)>0 && daop.getGzNum(YiJing.DING,1)>0)
       add("乙生酉月，辛金透，丁火刚，秋木盛，三者皆备，极等之贵");
     if(SiZhu.rg==YiJing.BING && SiZhu.yz==YiJing.ZI &&
        daop.getGzNum(YiJing.GUI,1)>0 && daop.getGzNum(YiJing.GENG,1)>0 &&
        SiZhu.yz==YiJing.BING && SiZhu.yz==YiJing.YIN)
	add("丙生子月，癸水透，庚金露，而坐寅午，三者皆均，遂成大贵");
  }

  /**
   * 是否官杀混杂
   */
  private boolean isGuanShaHunZa() {
    if(ssnum[7][1]==1 && ssloc[7][1][0]+ssloc[7][1][1]<3 &&
       ssnum[5][2]+ssnum[6][2]>0 &&
       ssnum[8][1]==1 && ssloc[8][1][0]==4 && !daop.isHasLu(SiZhu.sg)) {
      add("年月两干透一杀，年月支中有财，时遇官星无根，此官从杀势，非混也");
      return false;
    }else if(ssnum[8][1]==1 && ssloc[8][1][0]+ssloc[7][1][1]<3 &&
       ssnum[5][2]+ssnum[6][2]>0 &&
       ssnum[7][1]==1 && ssloc[7][1][0]==4 && !daop.isHasLu(SiZhu.sg)) {
      add("年月两干透一官，年月支中有财，时遇杀星无根，此杀从官势，非混也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsHe(7,1)>0) {
      add("虽官杀混杂，但有劫财合杀，官可混也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsKe(7,1)>0) {
      add("虽官杀混杂，但有比肩敌杀，官可混也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsHe(8,1)>0) {
      add("虽官杀混杂，但有比肩合官，杀可混也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0 &&
             daop.getShenShaIsHe(8,1)>0) {
      add("虽官杀混杂，但有劫财挡官，杀可混也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]==1 &&
             ssnum[9][1]+ssnum[10][1]+ssnum[9][2]+ssnum[10][2]>1) {
      add("一官而印绶重逢，官星泄气，杀助之，非混也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]==1 && ssnum[8][1]+ssnum[8][2]>0 &&
             ssnum[3][1]+ssnum[4][1]+ssnum[3][2]+ssnum[4][2]>1) {
      add("一杀而食伤并见，制杀太过，官助之，非混也");
      return false;
    }else if(ssws[3]<3 && (ssws[0]>2 || ssws[4]>2) &&
             ssnum[7][1]>0 && ssnum[8][1]>0) {
      add("若官杀并透无根，四柱劫印重逢，不但喜混，尚宜财星助杀官也");
      return false;
    }else if(ssnum[7][1]+ssnum[7][2]>0 && ssnum[8][1]+ssnum[8][2]>0) {
      return true;
    }
    return false;
  }

  private void _getYongShen() {
    if(getTongGuan())
      return ;
    if(xyShen[0]>0 && jiShen[0]>0)
      return ;

    if((SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
        SiZhu.huoCent>SiZhu.baifen[3] && SiZhu.shuiCent<SiZhu.baifen[0]) {
        geju = 99;
        xyShen = new int[]{daop.getShenShaByWX(YiJing.JIN),
          daop.getShenShaByWX(YiJing.SHUI),-1,-1,-1};
        jiShen = new int[]{daop.getShenShaByWX(YiJing.HUO),
          daop.getShenShaByWX(YiJing.MU),
          daop.getShenShaByWX(YiJing.TU),-1,-1};
      return;
    }
    if((SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU) &&
        SiZhu.shuiCent>SiZhu.baifen[3] && SiZhu.huoCent<SiZhu.baifen[0]) {
        geju = 66;
        xyShen = new int[]{daop.getShenShaByWX(YiJing.HUO),
           daop.getShenShaByWX(YiJing.MU),-1,-1,-1};
        jiShen = new int[]{daop.getShenShaByWX(YiJing.SHUI),
           daop.getShenShaByWX(YiJing.JIN),
           daop.getShenShaByWX(YiJing.TU),-1,-1};
       return;
    }

    if(ssws[0]>2 && ssws[4]>2) {
      geju = 81;
      xyShen = new int[]{2,1,3,-1,-1};
      jiShen = new int[]{4,0,-1,-1,-1};
    }else if(ssws[0]>2) {
      geju = 82;
      xyShen = new int[]{1,3,2,-1,-1};
      jiShen = new int[]{4,0,-1,-1,-1};
    }else if(ssws[0]<3 && ssws[3]>2) {
      geju = 83;
      xyShen = new int[]{4,0,-1,-1,-1};
      jiShen = new int[]{1,2,3,-1,-1};
    }else if(ssws[0]<3) {
      geju = 84;
      xyShen = new int[]{0,4,-1,-1,-1};
      jiShen = new int[]{1,2,3,-1,-1};
    }
  }

  /**
   * 清浊枯荣
   */
  private void getZhuoQingKu() {
    _t = "";
    String _temp = "";
    int j = 0;
    if(sscent[2]>0 && sscent[4]>0) _t += "财印相碍，";
    if(sscent[1]>0 && sscent[3]>0) _t += "食伤见官杀，";
    if(isGuanShaHunZa()) _t += "官杀混杂，";
    if(sscent[1]>0 && sscent[4]>0) _t += "枭印夺食，";
    if(_t.length()>1) _t = "局中"+_t+"略显混浊";

    if(!daop.isYouGen(SiZhu.rg)) _temp+="日干在各支中不得长生、禄、刃、墓、余气，可谓无根，";
    for(int i=0; i<xyShen.length; i++) {
      if(xyShen[i]==-1 || i==xyShen.length-1)
        break;
      if(ssws[xyShen[i]]>1) {
        j++;
        break;
      }
    }
    if(j==0) _temp += "用神无气，";
    if((SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
        SiZhu.huoCent>SiZhu.baifen[3] && SiZhu.shuiCent<SiZhu.baifen[0]) {
      if(SiZhu.tuCent>0 && SiZhu.jinCent>0) _temp += "火炎土燥金脆，";
      else if(SiZhu.tuCent>0) _temp += "火炎土燥，";
      else if(SiZhu.jinCent>0) _temp += "火炎金脆，";
      else _temp += "火气燥热，";
    }
    if((SiZhu.yz==YiJing.HAI || SiZhu.yz==YiJing.ZI || SiZhu.yz==YiJing.CHOU) &&
    SiZhu.shuiCent>SiZhu.baifen[3] && SiZhu.huoCent<SiZhu.baifen[0]) {
      if(SiZhu.tuCent>0 && SiZhu.jinCent>0) _temp += "土冻水冷金寒，";
      else if(SiZhu.tuCent>0) _temp += "水寒土冻，";
      else if(SiZhu.jinCent>0) _temp += "寒金冷水，";
      else _temp += "冰天雪地，";
    }
    if(_temp.length()>1) _t = "四柱"+_temp+"偏枯之造";

    if(_t.length()>1) {
      add(_t);
      add("浊与清枯二字酌之，宁使清中浊，不可清中枯。命之日主枯者，非贫即夭；用神枯者，非贫即孤");
      add("所以清有精神终必发，偏枯无气断孤贫，满盘浊气须看运，抑浊扶清也可亨");
    }
  }

  /**
   * 真者，得时秉令之神也；假者，失时退气之神也。
   */
  private void getZhenJiaShen() {
    add("真假神判断，须日主所用之神，在提纲司令，又透出天干，谓聚得真，不为假神破损，得之者生平富贵");
  }

  /**
   * 只可称其不强不弱。故既难论其喜忌。更难推岁运之休咎。然有一法焉。行帮身运。贵逢财官之年。行财官运。则喜帮身之年。若岁运皆属生扶或抑挫，即趋于偏枯。而非中和八字所宜矣。
   * 为乙丑己卯乙亥壬午。其乙禄在卯。己禄在午。壬禄在亥。交互得禄。旺气所系。且木旺水健。午水泄秀。
   * 刃之为物。暴戾而不易驯伏。若再逢冲。为祸尤烈。恐如朝露之易稀。画锦前程。或不可得。惜哉。后闻此孩染疫而死病仅一日耳。夫刃之逢冲。若无解救。微论身强身弱。祸变接踵而至。如影随形。如响斯应。
   *夫寒弱之木。不宜多水。祇喜木火，尝见冬木孤寒之命。走水运而倾家荡产。走木火运而仓满库勇者。不知凡几。若泥于衰则喜帮。而以印为喜见者。失诸毫厘。差以千里矣。
   * 命局有食不见财来。何悈类羹土饭。
   *则益信用神之禄。冲去犹可。用神之母。万不可冲，是又增我一番经验矣。
   * @param s
   */

  private void add(String s) {
    buf.append(s+"；\r\n    ");
  }
}