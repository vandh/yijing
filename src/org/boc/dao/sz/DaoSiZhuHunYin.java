package org.boc.dao.sz;

import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuHunYin {
  private DaoSiZhuYongShen daoys;
  private DaoSiZhuPublic daop;
  private DaoSiZhuWangShuai daow;
  private DaoSiZhuMain daom;
  private StringBuffer buf;
  private int[][] ssnum;
  private int[][][] ssloc;
  private int[] ssws;
  private int[] sscent;
  private String _t = "";
  private final String sep = "\r\n    ";
  private int[] hunqisui;         //应该算好的婚期
  private int[] hunqisui2;        //没有好的，就选差的了
  private final int MINSUI1 = 20;
  private final int MAXSUI1 = 25;
  private final int MINSUI2 = 22;
  private final int MAXSUI2 = 27;
  private final int MINSUI3 = 25;
  private final int MAXSUI3 = 35;
  private int count;
  private boolean sex ;

  public DaoSiZhuHunYin() {
    daoys = new DaoSiZhuYongShen();
    daop = new DaoSiZhuPublic();
    daow = new DaoSiZhuWangShuai();
    daom = new DaoSiZhuMain();
    buf = new StringBuffer();
    //神煞天干地支个数，0主,1比肩 2劫财3 食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
    ssnum = new int[11][3];
    //神煞天干地支位置 [十神][干或支1、支2][年柱或月柱或时柱]
    ssloc = new int[11][4][4];
    //神煞旺衰 1弱之极矣 2偏弱 3旺相 4强旺 5旺之极矣
    ssws = new int[5];
    //神煞分 0为比劫 1为食伤 2财才 3官杀 4印枭
    sscent = new int[5];
    hunqisui = new int[50];
    hunqisui2 = new int[50];
    count = 0;
  }

  private void init() {
    for(int i=1; i<11; i++) {
      ssnum[i][1] = daop.getGzNum(daop.getShenShaName(i,1)[0],1);
      ssnum[i][2] = daop.getGzNum(daop.getShenShaName(i,2),2);
      if(i==1) ssnum[i][1]=ssnum[i][1]-1;
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
    }
    //String[] ss5 = new String[]{"比劫","食伤","财才","官杀","印枭"};
    for(int i=0; i<5; i++) {
      ssws[i] = daop.getShiShenWS(i);
      //Debug.out(ss5[i]+"神煞旺衰："+ssws[i]);
    }

    for(int i=0; i<5; i++) {
      sscent[i] = daop.getShiShenCent(i);
      //Debug.out(ss5[i]+"神煞分数："+sscent[i]);
    }

  }

  public String getSiZhuHunYin() {
    //得到起运岁、大运等基本信息
    getBasicInfo();
    init();
    buf.delete(0,buf.toString().length());
    sex = SiZhu.sex.indexOf("乾")!=-1;

    getSiZhuHunQi();
    getFuXingDesc();

    return buf.toString();
  }

  /**
   * 女命一丁被众水围，明暗夫星。荟萃重叠。满盘争妒之象。是宜蛾眉螓首。蛇物蝎心。招展一般狂蜂浪蝶。
   * 如蝼蚁之附膻也。现行酉运。生水有源。汪洋泛滥。恐仍意马心猿。得陇望蜀。生张熟魏。送往迎来而已。
   * 三十八岁换入戊运。堤岸功成。方有乐观。或不致浮沈花镜。得能从一而终。
   * @return
   */
  private void getFuXingDesc() {
    buf.append("\r\n    ");
    buf.append("配偶参考：\r\n        ");
    if(!sex && ssws[3]<2 && ssnum[7][1]+ssnum[7][2]+ssnum[8][1]+ssnum[8][2]>0) {
      add("官星死绝，乃非命发之格，或恐不安于室");
    }else if(!sex && ssnum[7][1]+ssnum[7][2]+ssnum[8][1]+ssnum[8][2]>3 && ssws[0]<3) {
      add("明暗夫星，荟萃重叠，满盘争妒之象。是宜蛾眉螓首，蛇物蝎心，招展一般狂蜂浪蝶，如蝼蚁之附膻也");
    }else if(!sex && ssws[3]<3 && ssnum[7][1]+ssnum[7][2]+ssnum[8][1]+ssnum[8][2]>0) {
      add("官星衰弱，恐难嫁如意之夫君");
    }else if(!sex && sscent[2]+sscent[3]==0) {
      add("财空官空，婚姻不顺极其明显也");
    }else if(!sex && ssws[2]>2 && sscent[3]==0) {
      add("官星不现，财星即是官星，旺相，定是相夫旺子之命");
    }else if(!sex && sscent[2]==0 && ssws[3]>2) {
      add("官星旺相，妇从夫贵之象");
    }else if(!sex && sscent[2]>0 && ssws[3]>2) {
      add("财旺生官，定是相夫旺子之命");
    }

  }


  /**
   * 婚期
   */
  private void getSiZhuHunQi() {
    int yearNum=0, hourNum=0;                 //个数：年月上妻或夫星，日时上的妻或官星
    int yearNumjs = 0, hourNumjs = 0;         //个数：年月上比劫伤食，日时上的
    int ss = 0;    //妻或夫星 如偏财 正财 偏官 正官，此分得细，ss11分得粗
    int js = 0;    //比劫或伤食
    int ss11 = 0;  //如财 或 官
    int js11 = 0;  //如比或食
    int ws = 0, jsws = 0; //妻或夫星的旺衰，比劫或食伤的旺衰
    int selws = ssws[0];; //自己的旺衰
    String ssname = null;
    String jsname = null;
    int hunZaoOrChi = 1;

    //add("曲炜计算法");
    add("    因素1：生活地域和风俗，一般乡村婚期早，城区结婚迟");
    if(SiZhu.sex.indexOf("乾")!=-1) {
      ss = 5;
      js = 1;
      ws = ssws[2];
      jsws = ssws[0];
      ssname = "妻星";
      jsname = "比劫";
      ss11 = 2;
      js11 = 0;
    }else{
      ss = 7;
      js = 3;
      ws = ssws[3];
      jsws = ssws[1];
      ssname = "夫星";
      jsname = "食伤";
      ss11 = 3;
      js11 = 1;
    }
    for(int k=ss; k<ss+2; k++) {
      for(int i=1; i<3; i++) {
        for(int j=0; j<3; j++) {
          if(ssloc[k][i][j]<3 && ssloc[k][i][j]>0) {
            yearNum++;
          }else if(ssloc[k][i][j]==4){
            hourNum++;
          }
        }
      }
    }

    for(int k=js; k<js+2; k++) {
      for(int i=1; i<3; i++) {
        for(int j=0; j<3; j++) {
          if(ssloc[k][i][j]<3 && ssloc[k][i][j]>0) {
            yearNumjs++;
          }else if(ssloc[k][i][j]==4){
            hourNumjs++;
          }
        }
      }
    }

    if(yearNum>0 && ws>2 && ws<4) {
      add("因素2："+ssname+"凸现年月，且旺相，早婚");
      hunZaoOrChi = 1;
    }else if(yearNum>0 && ws >= 4 && selws<3) {
      add("因素2："+ssname+"凸现年月，旺之极矣而身弱，迟婚之兆");
      hunZaoOrChi = 3;
    }else if(yearNum>0 && jsws<3) {
      add("因素2："+ssname+"凸现年月，虽嫌不旺，毕竟"+jsname+"虚弱，早婚");
      hunZaoOrChi = 1;
    }else if(yearNum>0 && yearNumjs==0 && jsws>2) {
      add("因素2："+ssname+"凸现年月，略嫌虚弱，所幸忌星旺相而不现，早婚");
      hunZaoOrChi = 1;
    }else if(yearNum>0) {
      add("因素2："+ssname+"凸现年月，所嫌虚弱，而"+jsname+"旺相，虽早婚但不会太早");
      hunZaoOrChi = 2;
    }else if(hourNum>0 && ws>2 && jsws<3) {
      add("因素2："+ssname+"迟现时日旺相，所幸"+jsname+"不旺，晚婚但不会太晚");
      hunZaoOrChi = 2;
    }else if(hourNum>0) {
      add("因素2："+ssname+"迟现时日，晚婚标志明显");
      hunZaoOrChi = 3;
    }else if(yearNumjs>0 && jsws<3 && ws>2) {
      add("因素2："+jsname+"凸现年月，虽不旺相，但"+ssname+"旺相，婚期不会太迟宜逢生旺之年运");
      hunZaoOrChi = 2;
    }else if(yearNumjs>0) {
      add("因素2："+jsname+"凸现年月，且旺相，迟婚之兆");
      hunZaoOrChi = 3;
    }else if(ws>2){
      add("因素2："+ssname+"四柱隐藏不现但旺相，所幸"+jsname+"虚弱，晚婚但不太晚");
      hunZaoOrChi = 2;
    }else if(isHasCang(ss)) {
      add("因素2："+ssname+"藏支于年月，所幸"+jsname+"虚弱，晚婚但不会太晚");
      hunZaoOrChi = 2;
    }else{
      add("因素2："+ssname+"四柱隐藏不现又虚弱，晚婚明矣");
      hunZaoOrChi = 3;
    }

    buf.append("因素3：\r\n        ");
    _getHunQis(ss,js,
               new int[]{SiZhu.DAYUN[1][1],SiZhu.DAYUN[2][1],SiZhu.DAYUN[3][1],SiZhu.DAYUN[4][1]},
               new int[]{SiZhu.DAYUN[1][2],SiZhu.DAYUN[2][2],SiZhu.DAYUN[3][2],SiZhu.DAYUN[4][2]},
               SiZhu.QIZHU,SiZhu.QIYUNSUI,
               ws, jsws, selws);

    //如果没有好的年份选择，就选不利的了。
    if(!_getHunqi(hunZaoOrChi,hunqisui))
      _getHunqi(hunZaoOrChi,hunqisui2);

    //return buf.toString();
  }

  private boolean _getHunqi(int hunZaoOrChi, int[] hunqisui) {
    for(int i=0; i<hunqisui.length; i++) {
      if (hunZaoOrChi==1) {
        if (hunqisui[i] <= MAXSUI1 && hunqisui[i] >= MINSUI1) {
          add("最后终合判断，可能在 " + hunqisui[i] + "岁 结婚");
          return true;
        }
      }else if (hunZaoOrChi==2) {
        if (hunqisui[i] <= MAXSUI2 && hunqisui[i] >= MINSUI2) {
          add("最后终合判断，可能在 " + hunqisui[i] + "岁 结婚");
          return true;
        }
      }else if (hunZaoOrChi==3) {
        if (hunqisui[i] <= MAXSUI3 && hunqisui[i] >= MINSUI3) {
          add("最后终合判断，可能在 " + hunqisui[i] + "岁 结婚");
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是否支藏配偶星
   */
  private boolean isHasCang(int ss) {
    int wx = YiJing.TIANGANWH[daop.getShenShaName(ss,1)[0]];
    int[] ycang = SiZhu.DZXUNCANG[SiZhu.nz];
    int[] mcang = SiZhu.DZXUNCANG[SiZhu.yz];
    for(int i=0; i<ycang.length; i++) {
      if(YiJing.TIANGANWH[ycang[i]]==wx)
        return true;
    }
    for(int i=0; i<mcang.length; i++) {
      if(YiJing.TIANGANWH[mcang[i]]==wx)
        return true;
    }
    return false;
  }

  /**
   * 是否流年大运日柱有三合
   * 是否流年支与含日支的命局有三合
   * 是否流年支与日支有六合
   * 是否流年与运与命局有三合 前三者均不满足时
   * 是否流年与命局有三合 前三者均不满足时
   * 是否流年支与命局有六合 前三者均不满足时
   * 是否流年支与命局有三会 前三者均不满足时
   * 其它：流年与日支旺半合，流年与运六合旺半合、与其它支旺半合皆不考虑
   * 必要条件：a.配偶星弱而助之 b.身弱而助之 c.均身强配偶星强 皆可成也，其它均难成
   * @param ss
   * @param js
   * @param dyg
   * @param dyz
   * @param ln
   * @param qiyunsui
   */
  private void _getHunQis(int ss, int js, int[] dyg, int[] dyz, int[][][] ln,
                          int qiyunsui, int ssws, int jsws, int selws) {
    int wx = 0;
    String[] res = new String[2];
    int sswx = 0;
    int jswx = 0;
    int year = Calendar.YEARN1;
    boolean isHasCur = false; //同为三合局，如果包含日支的已合局，不含日支的则不必显示了
    boolean isHasCur2 = false;

    //得到该配偶星的五行
    int ssname = daop.getShenShaName(ss,1)[0];
    int jsname = daop.getShenShaName(js,1)[0];
    int selwx = YiJing.TIANGANWH[SiZhu.rg];
    sswx = YiJing.TIANGANWH[ssname];
    jswx = YiJing.TIANGANWH[jsname];

    for(int i=0; i<dyz.length; i++) {
      for (int j = (qiyunsui+i*10); j < ((i+1)*10+qiyunsui); j++) {
        isHasCur = false;
        isHasCur2 = false;

        if(j<MINSUI1 || j>MAXSUI3)
          continue;
        res = isShengKeOfGan(ss, js, dyg[i], SiZhu.QIZHU[j][7][1]);
        //Debug.out(j+"岁："+YiJing.TIANGANNAME[SiZhu.QIZHU[j][7][1]]+YiJing.DIZINAME[SiZhu.QIZHU[j][7][2]]);
        if(res==null && res[0]==null)
          return ;

        //1 是否流年大运日柱有三合
        wx = daop.isSanHeOfRizhu(dyz[i], SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、配偶宫、大运三合");
          isHasCur = true;
        }

        //2 是否流年支与含日支的命局有三合
        wx = daop.isSanHeOfRizhu(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、配偶宫、命局三合");
          isHasCur = true;
        }

        //3 是否流年支与日支有六合
        wx = daop.isLiuHe(SiZhu.rz, SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、配偶宫六合");
          isHasCur = true;
        }

        //4 是否流年与运与命局有三合且生助配偶星 前三者均不满足时
        wx = daop.isSanHe(dyz[i], SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、大运、命局三合");
          isHasCur = true;
        }

        //5 是否流年与命局有三合且生助配偶星 前三者均不满足时
        wx = daop.isSanHe(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx,  "岁：流年、命局三合");
          isHasCur = true;
        }

        //6 是否流年支与命局有六合且生助配偶星 前三者均不满足时
        wx = daop.isLiuHe(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、命局六合");
          isHasCur = true;
        }

        //7 流年支与大运与配偶宫有三会
        wx = daop.isSanHuiOfRizhu(dyz[i], SiZhu.QIZHU[j][7][2], SiZhu.rz);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、大运、配偶宫三会");
          isHasCur = true;
        }
        //8 流年支与配偶宫与命局有三会
        wx = daop.isSanHuiOfRizhu(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、命局、配偶宫三会");
          isHasCur = true;
        }
        //9 流年支与大运与命局有三会
        wx = daop.isSanHui(SiZhu.QIZHU[j][7][2], dyz[i]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、命局、大运三会");
          isHasCur = true;
        }
        //10 流年支与命局有三会
        wx = daop.isSanHui(SiZhu.QIZHU[j][7][2]);
        if(wx > 0 && !isHasCur) {
          _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                     selwx, "岁：流年、命局三会");
          isHasCur = true;
        }
        //11 流年天干配偶星与命局日干相合且配偶星有根
        if(daop.isPOX(ss, SiZhu.QIZHU[j][7][1])) {
          wx = YiJing.TGWUHE[SiZhu.rg][SiZhu.QIZHU[j][7][1]];
          if(wx > 0 && !isHasCur2 && ssws>2) {
            _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                       selwx, "岁：流年天干配偶星与日干化合");
            isHasCur2 = true;
          }
        }

        int[] pos;
        //11 流年天干配偶星与命局相合且配偶星有根
        if(daop.isPOX(ss, SiZhu.QIZHU[j][7][1])) {
          pos = new int[] {
              SiZhu.ng, SiZhu.yg, SiZhu.sg};
          for (int x = 0; x < pos.length; x++) {
            wx = YiJing.TGWUHE[pos[x]][SiZhu.QIZHU[j][7][1]];
            if (wx > 0 && !isHasCur2 && ssws > 2) {
              _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                         selwx, "岁：流年天干配偶星与命局化合");
              isHasCur2 = true;
            }
          }
        }

        //12 命局天干配偶星与流年天干相合且配偶星有根
        pos = daop.isHasPOXOfMing(ss);
        for(int x=0; x<pos.length; x++) {
          if(pos[x]==0)
            break;
          wx = YiJing.TGWUHE[pos[x]][SiZhu.QIZHU[j][7][1]];
          if(wx > 0 && !isHasCur2 && ssws>2) {
            _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                       selwx, "岁：命局天干配偶星与流年天干化合");
            isHasCur2 = true;
          }
        }

        //13 大运天干配偶星与流年天干相合且配偶星有根
        if(daop.isPOX(ss,dyg[i])) {
          wx = YiJing.TGWUHE[dyg[i]][SiZhu.QIZHU[j][7][1]];
          if(wx > 0 && !isHasCur2 && ssws>2) {
            _getHunqi_(wx, res, sswx, jswx, year, j, ssws, jsws, selws,
                       selwx, "岁：大运天干配偶星与流年天干");
            isHasCur2 = true;
          }
        }

      }
    }
  }

  private void _getHunqi_(int wx, String[] res,
                          int sswx, int jswx, int year, int j,
                          int ssws, int jsws, int selws, int selwx, String desc) {
    String sep = "--";
    String nian = "";
    if(year>Calendar.IYEAR && Calendar.MONTHN>=Calendar.IMONTH)
      nian = (year+j-1)+"年";

    if((ssws<3 && (YiJing.WXDANSHENG[wx][sswx]>0 || wx == sswx || YiJing.WXDANKE[wx][jswx]>0)) ||
       (selws<3 && (YiJing.WXDANSHENG[wx][selwx]>0 || wx == selwx)) ||
       (selws>2 && ssws>2)) {
      add(nian+YiJing.TIANGANNAME[SiZhu.QIZHU[j][7][1]]+YiJing.DIZINAME[SiZhu.QIZHU[j][7][2]]+sep+
          j+desc+YiJing.WUXINGNAME[wx]+"局，"+res[1]+"，为婚期之应");
      hunqisui[count++] = j;
    }else {
      add(nian+YiJing.TIANGANNAME[SiZhu.QIZHU[j][7][1]]+YiJing.DIZINAME[SiZhu.QIZHU[j][7][2]]+sep+
          j+desc+YiJing.WUXINGNAME[wx]+"局，但不利配偶星，"+res[1]+"，阻力重重");
      hunqisui2[count++] = j;
    }
  }

  /**
   * 看大运流年与配偶星生克关系
   * 当大运支、流年支与命局支有三合五合时，运干、流年干、命干是否会组成六合生克配偶星
   * @param peiouwx 配偶星 0主,1比肩 2劫财3 食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
   * @param dyg: 大运天干
   * @param lng: 流年天干
   * @return 10:大运与流年干合生配偶星 11 同配偶星五行 12 克忌神 13克配偶星 14 生忌神
   *         20 大运与命局有合
   *         30 流年与命局有合
   *         40 大运与流年天干均生配偶星
   *         50 大运与流年天干一生一克配偶星
   *         60 大运与流年天干均克配偶星
   */
  private String[] isShengKeOfGan(int peiou, int jis, int dyg, int lng) {
    int wx = 0;
    int poname = daop.getShenShaName(peiou, 1)[0];
    int jiname = daop.getShenShaName(jis, 1)[0];
    String[] rets = new String[2];

    wx = YiJing.TGWUHE[dyg][lng];
    if(wx > 0) {
      rets = _g(wx, poname, jiname, "大运与流年天干"+YiJing.TIANGANNAME[dyg]+YiJing.TIANGANNAME[lng]+"相合");
      if (rets != null) {
        return rets;
      }
    }

    wx = _he(dyg);
    if(wx > 0) {
      rets = _g(wx, poname, jiname, "大运天干"+YiJing.TIANGANNAME[dyg]+"与命局天干相合");
      if (rets != null) {
        return rets;
      }
    }

    wx = _he(lng);
    if(wx > 0) {
      rets = _g(wx, poname, jiname, "流年天干"+YiJing.TIANGANNAME[lng]+"与命局天干相合，");
      if (rets != null) {
        return rets;
      }
    }

    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[poname]]>0 &&
       YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = "大运与流年天干均生助配偶星";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = "大运天干生助配偶星";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = "流年天干生助配偶星";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname] &&
             YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "流年天干与大运天干均为配偶星";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "大运天干凸现配偶星";
      return rets;
    }else if(YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "流年天干凸现配偶星";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname] &&
             YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "流年天干与大运天干均为配偶星";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "大运天干凸现配偶星";
      return rets;
    }else if(YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = "流年天干凸现配偶星";
      return rets;
    }else if(YiJing.WXDANKE[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0 &&
       YiJing.WXDANKE[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = "大运与流年天干均克泄忌星";
      return rets;
    }else
    if(YiJing.WXDANKE[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = "大运天干克泄忌星";
      return rets;
    }else
    if(YiJing.WXDANKE[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = "流年天干克泄忌星";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[jiname] &&
             YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[jiname]) {
      rets[0] = "0";
      rets[1] = "流年天干与大运天干均为忌星";
      return rets;
    }else if(YiJing.TIANGANWH[dyg] == YiJing.TIANGANWH[jiname]) {
      rets[0] = "0";
      rets[1] = "大运天干凸现忌星";
      return rets;
    }else if(YiJing.TIANGANWH[lng] == YiJing.TIANGANWH[jiname]) {
      rets[0] = "0";
      rets[1] = "流年天干凸现忌星";
      return rets;
    }else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0 &&
       YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = "大运与流年天干均生助忌星";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[dyg]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = "大运天干生助忌星";
      return rets;
    }else
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[lng]][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = "流年天干生助忌星";
      return rets;
    }

    return rets;
  }

  private int _he(int g) {
    int[] gs = {SiZhu.ng, SiZhu.yg, SiZhu.rg, SiZhu.sg};
    for(int i=0; i<gs.length; i++) {
      if(YiJing.TGWUHE[gs[i]][g]>0)
        return YiJing.TGWUHE[gs[i]][g];
    }
    return 0;
  }

  private String[] _g(int wx, int poname, int jiname, String desc) {
    String[] rets = new String[2];

    if(YiJing.WXDANSHENG[wx][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "1";
      rets[1] = desc+"生助配偶星";
    }else if(wx == YiJing.TIANGANWH[poname]) {
      rets[0] = "1";
      rets[1] = desc+"合化成配偶星";
    }else if(YiJing.WXDANKE[wx][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "1";
      rets[1] = desc+"克泄忌星";
    }else if(YiJing.WXDANKE[wx][YiJing.TIANGANWH[poname]]>0) {
      rets[0] = "0";
      rets[1] = desc+"力克配偶星";
    }else if(YiJing.WXDANSHENG[wx][YiJing.TIANGANWH[jiname]]>0) {
      rets[0] = "0";
      rets[1] = desc+"生助忌星";
    }else {
      return null;
    }

    return rets;
  }

  /**
   * 得到起运岁数，流年数，大运数
   * //找出第2、3、4步大运，即11-42岁，如果早婚看第2、3步运，迟则第3、4步运
    //SiZhu.DAYUN[2][1], SiZhu.DAYUN[2][2];
    //找出15-45岁流年，如果早婚则15-25岁，迟婚则22-45岁，须配合大运看
    //SiZhu.QIZHU[15][7][1],SiZhu.QIZHU[15][7][2]
    //起运岁数
    //SiZhu.QIYUNSUI;
   */
  private void getBasicInfo() {
    daom.getQiYunSui();
    daom.getDaYun();
    daom.getQiZhu();
    daow.getWuXingCent();
  }

  private void add(String s) {
    buf.append(s+"；\r\n        ");
  }

}
