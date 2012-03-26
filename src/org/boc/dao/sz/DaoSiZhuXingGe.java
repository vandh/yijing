package org.boc.dao.sz;

import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuXingGe {
  private DaoSiZhuYongShen daoys;
  private DaoSiZhuPublic daop;
  private DaoSiZhuWangShuai daow;
  private StringBuffer buf;
  private int[][] ssnum;
  private int[][][] ssloc;
  private int[] ssws;
  private int[] sscent;
  private boolean sex ;
  private String _t = "";
  private final String sep = "\r\n    ";
  private int[] xyshen;   //0为比劫 1为食伤 2财才 3官杀 4印枭
  private String kg = "    ";

  public DaoSiZhuXingGe() {
    daoys = new DaoSiZhuYongShen();
    daop = new DaoSiZhuPublic();
    daow = new DaoSiZhuWangShuai();
    buf = new StringBuffer();
    //神煞天干地支个数 0主,1比肩 2劫财 3食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
    ssnum = new int[11][3];
    //神煞天干地支位置 [十神][干、支1、支2][年柱或月柱或日柱或时柱]
    ssloc = new int[11][4][4];
    //神煞旺衰 1弱之极矣 2偏弱 3旺相 4强旺 5旺之极矣
    ssws = new int[5];
    //神煞分 0为比劫 1为食伤 2财才 3官杀 4印枭
    sscent = new int[5];
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
    }

  }

  /**
   * 输出所有的性格汇总
   * @return
   */
  public String getXingGeOut() {
    daow.getWuXingCent();
    init();
    buf.delete(0,buf.length());
    sex = SiZhu.sex.indexOf("乾")!=-1;
    xyshen = daoys.getXYShen();

    getShenGaoOfQuwei();
    getXingGe();
    getWXYiJi();
    getWXXinXing();
    getMGXinXing();
    getSSXinXing();

    return buf.toString();
  }

  /**
   * 曲炜计算法
   * 1、生年 甲代表1
   * 2. 生月 寅代表1，卯代表2，戌代表9，亥代表10，子代表11
   * 3、日子 按初一代表1推算
   * 4、时辰 同生月
   * 5、男女基数 女1.55男1.66为计算基数。生年+月+日被8除余数，年月日时辰除于8余数。
   *    总数再被6除余数，再与或男或女的基数（女1.55、男1.66）相加为身高数。
   * 6、强弱 日干身强或身旺者只加三种余数的最小余数。身弱三种余数都用，
   * @return
   */
  private void getShenGaoOfQuwei() {
    //buf.delete(0,buf.length());

    int jishu = sex ? 166 : 155;
    int up ,down, yao;
    int yz,sz;
    int up1, down1;

    if(SiZhu.yz<3)
      yz = SiZhu.yz+10;
    else
      yz = SiZhu.yz-2;
    up = SiZhu.ng+yz+Calendar.DAYN1;

    if(SiZhu.sz<3)
      sz = SiZhu.sz+10;
    else
      sz = SiZhu.sz-2;
    down = up+sz;

    down1 = down%8==0?0:down%8;
    up1 = up%8==0?0:up%8;
    yao = down%6==0?0:down%6;

    //add("方法(1)：曲炜计算法--男166cm女155cm为基数；以年干甲数1，月时支寅数1子数11，阴历日起终身卦。身弱加上下卦及动爻数，否则加三者最小数");
    add(kg+YiJing.TIANGANNAME[SiZhu.ng]+"+"+YiJing.DIZINAME[SiZhu.yz]+"+"+
          Calendar.DAYN1+"="+SiZhu.ng+"+"+yz+"+"+Calendar.DAYN1+"="+up+
          "/8······"+up1);
    add(kg+YiJing.TIANGANNAME[SiZhu.ng]+"+"+YiJing.DIZINAME[SiZhu.yz]+"+"+
        Calendar.DAYN1+"+"+YiJing.DIZINAME[SiZhu.sz]+"="+
        SiZhu.ng+"+"+yz+"+"+Calendar.DAYN1+"+"+sz+"="+down+
        "/8······"+down1);
    add(kg+YiJing.TIANGANNAME[SiZhu.ng]+"+"+YiJing.DIZINAME[SiZhu.yz]+"+"+
        Calendar.DAYN1+"+"+YiJing.DIZINAME[SiZhu.sz]+"="+
        SiZhu.ng+"+"+yz+"+"+Calendar.DAYN1+"+"+sz+"="+down+
        "/6······"+yao);

    if(ssws[0]==3 || ssws[0]==4) {
      add(kg+"最后断定身高为： "+jishu+"+min("+up1+","+down1+","+yao+")="+jishu+"+"+
          daop.getMin(up1,down1,yao)+"="+(jishu+daop.getMin(up1,down1,yao))+"厘米");
    }else{
      add(kg+"最后断定身高为： "+jishu+"+"+up1+"+"+down1+"+"+yao+"="+
          (jishu+up1+down1+yao)+"厘米");
    }

    //return buf.toString();

  }

  /**
   * 专论性格
   * @return
   */
  private void getXingGe(){
    //buf.delete(0,buf.length());
    add("");
    add("性格参考：");
    if(!sex && (ssws[2]>3 || ssws[3]>3)) {
      add(kg+"财或官乃极旺，主其极有才能，作事干练，交际手段亦高");
    }
    if(!sex && daop.isTooManyWX(YiJing.SHUI)) {
      add(kg+"女命水多，性同鸽雀，从多风流");
    }
    if(ssws[1]>=3 && ssws[3]>=3) {
      add(kg+"食伤与官杀两旺，则意志不坚");
    }
    if(ssnum[3][1]>1 && ssnum[4][1]>1 && ssws[1]>=3) {
      add(kg+"伤食并露，秀气发越，聪明伶俐，精艺绝伦。不为银坛领袖，当亦作歌裙舞扇之翘楚也");
    }
    if(ssws[1]>3 && !sex) {
      add(kg+"受泄太过，秀气尽发，当有倾国倾城之姿");
    }
    if(ssnum[4][1]>0 && ssws[1]>=3) {
      add(kg+"伤官透露且旺相，主人性刚");
    }
    if(ssnum[3][1]+ssnum[4][1]>1 && ssws[1]>=3 &&
       (SiZhu.WENCANG[SiZhu.rg][SiZhu.nz] || SiZhu.WENCANG[SiZhu.rg][SiZhu.yz] ||
        SiZhu.WENCANG[SiZhu.rg][SiZhu.rz] || SiZhu.WENCANG[SiZhu.rg][SiZhu.sz])) {
      add(kg+"食伤透露且旺相，支坐文昌，聪明伶俐，金石书画，不学而能之也");
    }
    if(ssws[3]>=3 && sscent[4]==0 &&
       (ssloc[7][1][0]<3 && ssloc[7][1][1]<3 && ssloc[7][1][2]<3 &&
        ssloc[7][2][0]<3 && ssloc[7][2][1]<3 && ssloc[7][2][2]<3 &&
        ssloc[8][1][0]<3 && ssloc[8][1][1]<3 && ssloc[8][1][2]<3 &&
        ssloc[8][2][0]<3 && ssloc[8][2][1]<3 && ssloc[8][2][2]<3)) {
      add(kg+"杀无印不威，其人温厚和平，蔼然可亲，外权独擅，盖官杀居年月也");
    }else if(ssws[3]>=3 && sscent[4]==0) {
      add(kg+"杀无印不威，其人温厚和平，蔼然可亲，内权独擅，盖官杀居日时也");
    }
    //return buf.toString();
  }

  /**
   * 五行之宜忌
   * @return
   */
  private void getWXYiJi() {
    int name;
    String wxyj = "";
    add("");
    add("五行宜忌：");
    for(int i=0; i<xyshen.length; i++) {
      if(xyshen[i]<=0)
        break;
      name = daop.getShenShaName2(xyshen[i],1)[0];
      wxyj = daop.getWuXingYiJi(YiJing.TIANGANWH[name]);
      add(wxyj);
    }
  }

  /**
   * 五行心性
   * @return
   */
  private void getWXXinXing() {
    int wx ;
    String[] wxxx ;

    add("");
    add("五行心性：");
    //过犹不及
    wx = YiJing.TIANGANWH[SiZhu.rg];
    wxxx = daop.getWuXingXing(wx);
    if (ssws[0] == 3 || ssws[0] == 4) {
      add(wxxx[0]+wxxx[1]);
    }else if(ssws[0] == 5){
      add(wxxx[0]+wxxx[2]);
    }else {
      add(wxxx[0]+wxxx[3]);
    }
  }

  /**
   * 命宫心性
   * @return
   */
  private void getMGXinXing() {
    add("");
    add("命宫心性：");
    add(daop.getMGXinXing(SiZhu.mgz));
  }

  /**
   * 十神心性
   * 0主,1比肩 2劫财 3食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
   * @return
   */
  private void getSSXinXing() {
    add("");
    add("十神心性：");
    for(int i=0; i<=10; i++) {
      if(ssnum[i][1]+ssnum[i][2] > 0) {
        add(daop.getShiShenXing(i));
      }
    }
  }

  private void add(String s) {
    buf.append(s+"\r\n    ");
  }

  public static void main(String[] args) {
    DaoSiZhuXingGe dao = new DaoSiZhuXingGe();
    dao.getXingGe();
  }
}
