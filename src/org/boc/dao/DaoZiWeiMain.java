package org.boc.dao;

import java.util.*;

import org.boc.db.YiJing;
import org.boc.db.ZiWei;
import org.boc.db.SiZhu;

public class DaoZiWeiMain {
  private StringBuffer str;
  private DaoPublic pub;
  private int[][] dzjbxx;  //地支基本信息：十二宫、天干、长生12、博士12、岁建12、大限、小限、童限
  private int[][] dzlnss;  //地支流年神煞：...
  private int[][] dzjbss;  //地支基本神煞：...
  private int[][] dzshss;  //地支四化神煞：...
  private String[] jbxx;   //基本信息

  public DaoZiWeiMain() {
    str = new StringBuffer();
    pub = new DaoPublic();
    dzjbxx = new int[9][13];
    dzlnss = new int[13][5];
    dzjbss = new int[13][15];
    jbxx = new String[10];
    dzshss = new int[13][4];
  }

  /**
   * 排天盘
   */
  public String getTianPan(int[] bz, int[] lnz, int day,
                           boolean sex, String head) {
    str.delete(0, str.length());

    out1("┏━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┓");
    out1("┃6G00000000000000000000┃7G00000000000000000000┃8G00000000000000000000┃9G00000000000000000000┃");
    out1("┃6F00000000000000000000┃7F00000000000000000000┃8F00000000000000000000┃9F00000000000000000000┃");
    out1("┃6E00000000000000000000┃7E00000000000000000000┃8E00000000000000000000┃9E00000000000000000000┃");
    out1("┃6D00000000000000000000┃7D00000000000000000000┃8D00000000000000000000┃9D00000000000000000000┃");
    out1("┃6C00000000000000000000┃7C00000000000000000000┃8C00000000000000000000┃9C00000000000000000000┃");
    out1("┃6B00000000000000000000┃7B00000000000000000000┃8B00000000000000000000┃9B00000000000000000000┃");
    out1("┃6A00000000000000000000┃7A00000000000000000000┃8A00000000000000000000┃9A00000000000000000000┃");
    out1("┣━━━━━━━━━━━╋━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┫");
    out1("┃5G00000000000000000000┃1000000000000000000000000000000000000000000000┃AG00000000000000000000┃");
    out1("┃5F00000000000000000000┃1010000000000000000000000000000000000000000000┃AF00000000000000000000┃");
    out1("┃5E00000000000000000000┃1020000000000000000000000000000000000000000000┃AE00000000000000000000┃");
    out1("┃5D00000000000000000000┃1030000000000000000000000000000000000000000000┃AD00000000000000000000┃");
    out1("┃5C00000000000000000000┃1040000000000000000000000000000000000000000000┃AC00000000000000000000┃");
    out1("┃5B00000000000000000000┃1050000000000000000000000000000000000000000000┃AB00000000000000000000┃");
    out1("┃5A00000000000000000000┃1060000000000000000000000000000000000000000000┃AA00000000000000000000┃");
    out1("┣━━━━━━━━━━━┫1070000000000000000000000000000000000000000000┣━━━━━━━━━━━┫");
    out1("┃4G00000000000000000000┃1080000000000000000000000000000000000000000000┃BG00000000000000000000┃");
    out1("┃4F00000000000000000000┃1090000000000000000000000000000000000000000000┃BF00000000000000000000┃");
    out1("┃4E00000000000000000000┃1100000000000000000000000000000000000000000000┃BE00000000000000000000┃");
    out1("┃4D00000000000000000000┃1110000000000000000000000000000000000000000000┃BD00000000000000000000┃");
    out1("┃4C00000000000000000000┃1120000000000000000000000000000000000000000000┃BC00000000000000000000┃");
    out1("┃4B00000000000000000000┃1130000000000000000000000000000000000000000000┃BB00000000000000000000┃");
    out1("┃4A00000000000000000000┃1140000000000000000000000000000000000000000000┃BA00000000000000000000┃");
    out1("┣━━━━━━━━━━━╋━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┫");
    out1("┃3G00000000000000000000┃2G00000000000000000000┃1G00000000000000000000┃CG00000000000000000000┃");
    out1("┃3F00000000000000000000┃2F00000000000000000000┃1F00000000000000000000┃CF00000000000000000000┃");
    out1("┃3E00000000000000000000┃2E00000000000000000000┃1E00000000000000000000┃CE00000000000000000000┃");
    out1("┃3D00000000000000000000┃2D00000000000000000000┃1D00000000000000000000┃CD00000000000000000000┃");
    out1("┃3C00000000000000000000┃2C00000000000000000000┃1C00000000000000000000┃CC00000000000000000000┃");
    out1("┃3B00000000000000000000┃2B00000000000000000000┃1B00000000000000000000┃CB00000000000000000000┃");
    out1("┃3A00000000000000000000┃2A00000000000000000000┃1A00000000000000000000┃CA00000000000000000000┃");
    out1("┗━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┛");

    //排局
    getGlobleInfo(bz, lnz[2], day, sex);
    String[] h = procHead(head.split("\r\n    "),lnz,bz[1],sex);
    return printImage(bz,lnz[2],day,sex,str.toString(), h);
  }

  /**
   * dzjbxx 00五行局数 01命宫 02身宫 03命主 04身主 05斗君
   */
  private String[] procHead(String[] head, int[] lnz,
                            int ng, boolean sex) {
    String[] s = new String[14];
    int i = 0;
    String kg = "    ";
    String ynyl = null;
    if(ng%2==1 && sex)
      ynyl = "阳男";
    else if(ng%2==1 && !sex)
      ynyl = "阳女";
    else if(ng%2==0 && sex)
      ynyl = "阴男";
    else
      ynyl = "阴女";

    for(; i<head.length; i++) {
      s[i] = kg+head[i];
    }
    s[i++] = kg+"命  局： "+ynyl+" "+ZiWei.wxjName[dzjbxx[0][0]]+"("+jbxx[0]+")";
    s[i++] = kg+"斗  君： "+"子-"+YiJing.DIZINAME[dzjbxx[0][5]]+" "+
        YiJing.DIZINAME[lnz[2]]+"-"+
        (YiJing.DIZINAME[(dzjbxx[0][5]+lnz[2]-1)%12==0?12:(dzjbxx[0][5]+lnz[2]-1)%12]);
    s[i++] = kg+"命  宫： "+YiJing.DIZINAME[dzjbxx[0][1]];
    s[i++] = kg+"身  宫： "+YiJing.DIZINAME[dzjbxx[0][2]];
    s[i++] = kg+"命  主： "+ZiWei.xName[dzjbxx[0][3]];
    s[i++] = kg+"身  主： "+ZiWei.xName[dzjbxx[0][4]];

    return s;
  }

  /**
   * 输出图像
   * 0身命主 1十二宫、2天干、3长生12、4博士12、5岁建12、6大限、7小限、8童限
   */
  private String printImage(int[] bz, int lnz, int day,
                            boolean sex, String s, String[] head) {
    String s1=null,s2=null,_s=null ;

    for(int i=1; i<=12; i++) {
      //第一行 大限
      s2 = "";
      _s = i>=10?String.valueOf((char)(55+i)):String.valueOf(i);
      s1 = ZiWei.bs12[dzjbxx[4][i]].substring(1,2);           //2
      s1 += ZiWei.ts12[dzjbxx[5][i]].substring(1,2);          //2
      for(int j=0;j<dzlnss[i].length;j++) {
        if(dzlnss[i][j]>0) {
          s2 += ZiWei.lnxx[dzlnss[i][j]].substring(1, 2);
        }
      }
      s1 += format(s2,6,0);                                   //6
      s1 += format(null,2,1);                                 //2
      if(dzjbxx[6][i]+9<100)
        s1 += format(dzjbxx[6][i]+"－"+(dzjbxx[6][i]+9),6,1); //6
      else
        s1 += format(null,6,1);
      s1 += format(null,2,1);                                 //2
      s1 += YiJing.DIZINAME[i];                               //2
      s = s.replaceAll(_s+"A00000000000000000000", s1);
      //第二行 十二宫
      s2 = "";
      s1 = ZiWei.bs12[dzjbxx[4][i]].substring(0,1);           //2
      s1 += ZiWei.ts12[dzjbxx[5][i]].substring(0,1);          //2
      for(int j=0;j<dzlnss[i].length;j++) {
        if(dzlnss[i][j]>0) {
          s2 += ZiWei.lnxx[dzlnss[i][j]].substring(0, 1);
        }
      }
      s1 += format(s2,6,0);                                   //6
      s1 += format(null,2,1);                                 //2
      //处理身宫
      if(i==dzjbxx[0][2])
        s1 += ZiWei.mg12[dzjbxx[1][i]].substring(0,1)+"★身";        //6
      else
        s1 += ZiWei.mg12[dzjbxx[1][i]];
      s1 += format(null,2,1);                                 //2
      s1 += YiJing.TIANGANNAME[dzjbxx[2][i]];                 //2
      s = s.replaceAll(_s+"B00000000000000000000", s1);
      //第三行 长生十二神煞
      s2 = ZiWei.cs12[dzjbxx[3][i]];
      if(s2.length()==2)
        s1 = format(s2.substring(1,2),22,2);
      else
        s1 = format(s2.substring(2,3),22,2);
      s = s.replaceAll(_s+"C00000000000000000000", s1);
      //第四行 长生十二神煞+四化
      s2 = "";
      s1 = format(ZiWei.cs12[dzjbxx[3][i]].substring(0,1),2,2);
      s = s.replaceAll(_s+"D00000000000000000000", _s+"D000000000000000000"+s1);
      for(int j=0; j<dzshss[i].length; j++) {
        if(dzshss[i][0]==0) break;
        if(dzshss[i][j]>0)
          s2 += ZiWei.xName[dzshss[i][j]];
      }
      s1 = format(s2,20,0);
      s = s.replaceAll(_s+"D000000000000000000", s1);
      //第五行 庙旺
      s2 = "";
      for(int j=0;j<dzjbss[i].length;j++) {
        if(dzjbss[i][j]>0) {
          s2 += ZiWei.zxdjName[ZiWei.zxdj[dzjbss[i][j]][i]];
        }
      }
      s1 = format(s2,22,0);
      s = s.replaceAll(_s+"E00000000000000000000", s1);
      //第六行 诸星
      s2 = "";
      for(int j=0;j<dzjbss[i].length;j++) {
        if(dzjbss[i][j]>0) {
          s2 += ZiWei.xName[dzjbss[i][j]].substring(1, 2);
        }
      }
      s1 = format(s2,22,0);
      s = s.replaceAll(_s+"F00000000000000000000", s1);
      //第七行 诸星
      s2 = "";
      for(int j=0;j<dzjbss[i].length;j++) {
        if(dzjbss[i][j]>0) {
          s2 += ZiWei.xName[dzjbss[i][j]].substring(0, 1);
        }
      }
      s1 = format(s2,22,0);
      s = s.replaceAll(_s+"G00000000000000000000", s1);
      //第四行，将来用
      s1 = format(null,20,2);
      s = s.replaceAll(_s+"D000000000000000000", s1);

      s1=null;
      s2=null;
      _s=null;
    }
    //中间那块
    for(int j=100; j<=114; j++) {
      if(j-100<head.length)
        s1 = format(head[j-100],46,0);
      else
        s1 = format(null,46,0);
      s = s.replaceAll(j+"0000000000000000000000000000000000000000000",s1);
    }

    dzjbxx = new int[9][13];
    dzlnss = new int[13][5];
    dzjbss = new int[13][15];
    jbxx = new String[10];
    dzshss = new int[13][4];
    str.delete(0,str.length());
    head = null;

    return s;
  }

  /**
   * 格式化指定的字符串
   * @param s String
   * @param len int 长度范围内以空格填充，超过则截断
   * @param fm int  0左对齐，1居中，2右对齐，奇数居中左多
   * @return String
   */
  private String format(String s, int len, int fm) {
    if(s==null || "".equals(s.trim()))
      return pub.getRepeats(' ',len);

    int slen = s.getBytes().length;
    if(slen>len)
      return s.substring(0,len/2);

    int zuo = (len-slen)/2;
    int you = len - slen - zuo;
    if(fm==0)
      return s+pub.getRepeats(' ',zuo+you);
    else if(fm==1)
      return pub.getRepeats(' ',zuo)+s+pub.getRepeats(' ',you);
    else
      return pub.getRepeats(' ',len-slen)+s;
  }

  /**
   * 得到全局信息
   * dzjbxx 00五行局数 01命宫 02身宫 03命主 04身主 05斗君
   *        1十二宫、2天干、3长生12、4博士12、5岁建12、6大限、7小限、8童限
   * dzlnss 流年神煞
   * dzjbss 基本神煞
   */
  public void getGlobleInfo(int[] bz, int lnz, int day, boolean sex) {
    //1. 求命宫/身宫/命宫12宫/命12宫天干/命宫纳音/五形局数/
    int mg = getMingGong(bz[4],bz[8]);
    int sg = getShenGong(bz[4],bz[8]);
    Map mg12 = getMg12(mg);
    Map mgg12 = getGongGan(bz[1]);
    String mgny = getMgNayin(Integer.parseInt(mgg12.get(String.valueOf(mg)).toString()), mg);
    int wxjs = getWxjs(Integer.parseInt((String)mgg12.get(String.valueOf(mg))),mg);
    //2. 紫微星系/天府星系/命12宫星系/
    Map zwxx = getZiWeiXx(wxjs, day);
    Map tfxx = getTianFuXx(Integer.valueOf(zwxx.get("1").toString()).intValue());
    Map gxx = getGongXx(Integer.valueOf(mg12.get("8").toString()).intValue(),
                        Integer.valueOf(mg12.get("6").toString()).intValue());
    //3. 年干星系/年支星系/年柱星系/月柱星系/日柱星系/时柱星系/四化星系/流年星系
    Map ngxx = getNianGanXx(bz[1]);
    Map nzxx = getNianZiXx(bz[2],mg,sg);
    int[] nxx = getNianXx(bz[1],bz[2]);
    Map yxx = getYueXx(bz[4]);
    Map sxx = getShiXx(bz[2], bz[8]);
    Map rxx = getRiXx(Integer.valueOf(yxx.get("46").toString()).intValue(),
                       Integer.valueOf(yxx.get("47").toString()).intValue(),
                       Integer.valueOf(sxx.get("58").toString()).intValue(),
                       Integer.valueOf(sxx.get("59").toString()).intValue(),
                       day);
    Map shxx = getSiHua(bz[1], zwxx, tfxx, yxx, sxx);
    Map lnxx = getLiuNianXx(bz[1],bz[2]);
    //4. 命主/身主/斗君
    int mz = getMingZhu(mg,zwxx,tfxx,ngxx,sxx);
    int sz = getShenZhu(bz[2],zwxx,tfxx,nzxx,sxx);
    int dz = getDouJun(YiJing.ZI,bz[4],bz[8]);
    //5. 博士12神煞/长生12神煞/太岁12神煞
    Map bs12 = getBoShi12(Integer.valueOf(ngxx.get("15").toString()).intValue(),
                               bz[1],sex);
    Map cs12 = getChangSheng12(wxjs,bz[1],sex);
    Map sj12 = getTaiSui12(bz[2]);
    //6. 大限、小限、童限
    Map mgs = getMgs(mg);
    Map dx12 = getDaXian(Integer.valueOf(mgs.get("1").toString()).intValue(),
                         bz[1],sex,wxjs);
    Map xx12 = getXiaoXian(sex);
    Map tx15 = getTongXian(mg12);

    //完善基本信息
    jbxx[0] = mgny;
    //完善地支对应的基本信息
    dzjbxx[0] = new int[]{wxjs,mg,sg,mz,sz,dz};
    for(int i=1; i<=12; i++) {
      dzjbxx[1][i] = Integer.valueOf(mg12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[2][i] = Integer.valueOf(mgg12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[3][i] = Integer.valueOf(cs12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[4][i] = Integer.valueOf(bs12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[5][i] = Integer.valueOf(sj12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[6][i] = Integer.valueOf(dx12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[7][i] = Integer.valueOf(xx12.get(String.valueOf(i)).toString()).intValue();
      dzjbxx[8][i] = tx15.get(String.valueOf(i))==null?
          0:Integer.valueOf(tx15.get(String.valueOf(i)).toString()).intValue();
    }
    //完善地支对应流年神煞
    putArray(lnxx, dzlnss);
    //完善地支对应的基本神煞
    putArray(zwxx, dzjbss); //紫微
    putArray(tfxx, dzjbss); //天府
    putArray(ngxx, dzjbss); //年干
    putArray(nzxx, dzjbss); //年支
    putArray(gxx, dzjbss);  //年上
    putArray(yxx, dzjbss);  //月上
    putArray(rxx, dzjbss);  //日上
    putArray(sxx, dzjbss);  //时上
    ssdzMaArray(dzjbss, nxx, 45);
    //四化写在第四行
    putArray(shxx, dzshss);
  }

  private void putArray(Map map, int[][] array) {
    Iterator it ;
    Set set ;
    int key, value;
    set = map.keySet();
    for(it=set.iterator(); it.hasNext();) {
      key = Integer.valueOf((String)it.next()).intValue();
      //System.out.println(key+"  "+map.get(String.valueOf(key)));
      value = Integer.valueOf((String)map.get(String.valueOf(key))).intValue();
      ssdzMaArray(array, value, key);
    }
  }

  /**
   * 将神煞放到对应的地支数组中，只是新增
   * @param dz int 地支
   * @param ss int 地支对应的神煞
   */
  private void ssdzMaArray(int[][] array, int dz, int ss) {
    for(int i=0; i<array[dz].length; i++) {
      if(array[dz][i]==0) {
        array[dz][i]=ss;
        break;
      }
    }
  }

  private void ssdzMaArray(int[][] array, int[] dzs, int ss) {
    for(int j=0; j<dzs.length; j++) {
      for (int i = 0; i < array[dzs[j]].length; i++) {
        if (array[dzs[j]][i] == 0) {
          array[dzs[j]][i] = ss;
          break;
        }
      }
    }
  }


  /**
   * 1. 命宫，如正月申时为午宫。
   * 参数：月支与时支
   */
  private int getMingGong(int yz, int sz) {
    return pub.getNiShu(1,yz,sz);
  }

  /**
   * 2. 身宫，如正月申时为戌宫。
   */
  private int getShenGong(int yz, int sz) {
    return pub.getShunShu(1,yz,sz);
  }

  /**
   * 3. 安十二宫，由命宫逆数。地支宫对应何宫
   * 参数：命宫支
   */
  private Map getMg12(int mgz) {
    Map map = new TreeMap();
    int gz;
    for(int i=1; i<=12; i++) { //从命宫到第12宫父母宫
      gz = (mgz-i+12+1)%12==0?12:(mgz-i+12+1)%12;
      map.put(String.valueOf(gz),String.valueOf(i));
    }
    return map;
  }

  /**
   * 3.1 安十二宫，由命宫逆数。何宫对应地支宫
   * 参数：命宫支
   */
  private Map getMgs(int mgz) {
    Map map = new TreeMap();
    int gz;
    for(int i=1; i<=12; i++) { //从命宫到第12宫父母宫
      gz = (mgz-i+12+1)%12==0?12:(mgz-i+12+1)%12;
      map.put(String.valueOf(i), String.valueOf(gz));
    }
    return map;
  }

  /**
   * 4. 求各宫天干，由年干遁至命宫支即为命宫干。地支宫对应何干
   * 生年干、各宫的地支
   */
  private Map getGongGan(int ng) {
    int tg;
    Map map = new TreeMap();

    for(int i=1; i<=12; i++) {
      tg = pub.getShunShu2(YiJing.YIN,SiZhu.yueByNian[ng],i);
      map.put(String.valueOf(i),String.valueOf(tg));
    }

    return map;
  }

  /**
   * 5. 查命宫纳音
   */
  private String getMgNayin(int mgg, int mgz) {
    return SiZhu.NAYIN[mgg][mgz];
  }

  /**
   * 6. 定五形局 水二局、木三局、金四局、土五局、火六局
   */
  private int getWxjs(int mgg, int mgz) {
    String s = getMgNayin(mgg,mgz);
    if(s.indexOf("水")!=-1)
      return 2;
    else if(s.indexOf("木")!=-1)
      return 3;
    else if(s.indexOf("金")!=-1)
      return 4;
    else if(s.indexOf("土")!=-1)
      return 5;
    else
      return 6;
  }

  /**
   * 7. 安紫微星系 何星对应何宫
   * 紫微、天机、空一格、太阳、武曲、天同、空二格、廉贞
   * d-阴历生日
   */
  private Map getZiWeiXx(int wxjs, int d) {
    int x = (wxjs-d%wxjs)%wxjs;
    int y = x%2==0?x+(d+x)/wxjs:(d+x)/wxjs-x;
    y = y<=0?y+12:y;
    y = pub.getShunShu(1,YiJing.YIN,y);

    Map map = new TreeMap();
    map.put("1",String.valueOf(y));  //1-紫微，值为对应的地支
    y = (y-1+12)%12==0?12:(y-1+12)%12;
    map.put("2",String.valueOf(y));
    y = (y-2+12)%12==0?12:(y-2+12)%12;
    map.put("3",String.valueOf(y));
    y = (y-1+12)%12==0?12:(y-1+12)%12;
    map.put("4",String.valueOf(y));
    y = (y-1+12)%12==0?12:(y-1+12)%12;
    map.put("5",String.valueOf(y));
    y = (y-3+12)%12==0?12:(y-3+12)%12;
    map.put("6",String.valueOf(y));

    return map;
  }

  /**
   * 8. 安天府星系 何星对应何宫
   * 天府、太阴、贪狼、巨门、天相、天梁、七杀、空三格、破军
   * zwg-紫微宫
   */
  private Map getTianFuXx(int zwg) {
    int[] tf = {0,YiJing.CHEN,YiJing.MAO,YiJing.YIN,YiJing.CHOU,
                  YiJing.ZI,YiJing.HAI,YiJing.XU,YiJing.YOU,
                  YiJing.SHEN,YiJing.WEI,YiJing.WUZ,YiJing.SI};
    int y = tf[zwg];

    Map map = new TreeMap();
    map.put("7",String.valueOf(y));  //1-天府星，值为对应的地支
    y = (y+1+12)%12==0?12:(y+1+12)%12;
    map.put("8",String.valueOf(y));
    y = (y+1+12)%12==0?12:(y+1+12)%12;
    map.put("9",String.valueOf(y));
    y = (y+1+12)%12==0?12:(y+1+12)%12;
    map.put("10",String.valueOf(y));
    y = (y+1+12)%12==0?12:(y+1+12)%12;
    map.put("11",String.valueOf(y));
    y = (y+1+12)%12==0?12:(y+1+12)%12;
    map.put("12",String.valueOf(y));
    y = (y+1+12)%12==0?12:(y+1+12)%12;
    map.put("13",String.valueOf(y));
    y = (y+4+12)%12==0?12:(y+4+12)%12;
    map.put("14",String.valueOf(y));

    return map;
  }

  /**
   * 9. 得到年干星系  何星对应何宫
   * 禄存","擎羊","陀罗","天魁","天钺","化禄","化权","化科","化忌","天福","截空","天官
   */
  private Map getNianGanXx(int ng) {
    Map map = new TreeMap();
    int[] lcs = {0,YiJing.YIN,YiJing.MAO,YiJing.SI,YiJing.WUZ,
        YiJing.SI,YiJing.WUZ,YiJing.SHEN,YiJing.YOU,YiJing.HAI,YiJing.ZI};
    int lc = lcs[ng];
    int[] tk = {0,YiJing.CHOU,YiJing.ZI,YiJing.HAI,YiJing.HAI,
        YiJing.CHOU,YiJing.ZI,YiJing.YIN,YiJing.YIN,YiJing.MAO,YiJing.MAO};
    int[] ty = {0,YiJing.WEI,YiJing.SHEN,YiJing.YOU,YiJing.YOU,
        YiJing.WEI,YiJing.SHEN,YiJing.WUZ,YiJing.WUZ,YiJing.SI,YiJing.SI};
    int[] tf = {0,YiJing.YOU,YiJing.SHEN,YiJing.ZI,YiJing.HAI,
        YiJing.MAO,YiJing.YIN,YiJing.WUZ,YiJing.SI,YiJing.WUZ,YiJing.SI};
    int[] jk = {0,YiJing.SHEN,YiJing.WUZ,YiJing.CHEN,YiJing.YIN,
        YiJing.ZI,YiJing.YOU,YiJing.WEI,YiJing.SI,YiJing.MAO,YiJing.CHOU};
    int[] tg = {0,YiJing.WEI,YiJing.CHEN,YiJing.SI,YiJing.YIN,
        YiJing.MAO,YiJing.YOU,YiJing.HAI,YiJing.YOU,YiJing.XU,YiJing.WUZ};

    map.put("15",String.valueOf(lcs[ng]));  //1-禄存星，值为对应的地支
    map.put("16",String.valueOf((lc+1)%12==0?12:(lc+1)%12));
    map.put("17",String.valueOf((lc-1+12)%12==0?12:(lc-1+12)%12));
    map.put("18",String.valueOf(tk[ng]));
    map.put("19",String.valueOf(ty[ng]));
    //四化

    map.put("24",String.valueOf(tf[ng]));
    map.put("25",String.valueOf(jk[ng]));
    map.put("26",String.valueOf(tg[ng]));
    return map;
  }

  /**
   * 安四化星
   * @param zw 紫微星系 紫微、天机、太阳、武曲、天同、廉贞
   * @param tf 天府星系 天府、太阴、贪狼、巨门、天相、天梁、七杀、破军
   * @param yxx 月星系 左辅 右弼 天刑 天姚 天月 阴煞 解神 天巫
   * @param sxx 时星系 文昌 文曲 天空 地劫 台辅 封诰
   */
  private Map getSiHua(int ng, Map zw, Map tf, Map yxx, Map sxx) {
    Map map = new TreeMap();
    Object[] hl = {null,zw.get("6"),zw.get("2"),zw.get("5"),tf.get("8"),tf.get("9"),
                        zw.get("4"),zw.get("3"),tf.get("10"),tf.get("12"),tf.get("14")};
    Object[] hq = {null,tf.get("14"),tf.get("12"),zw.get("2"),zw.get("5"),tf.get("8"),
                        tf.get("9"),zw.get("4"),zw.get("3"),zw.get("1"),tf.get("10")};
    Object[] hk = {null,zw.get("4"),zw.get("1"),sxx.get("58"),zw.get("2"),yxx.get("47"),
                        tf.get("12"),zw.get("5"),sxx.get("59"),tf.get("7"),tf.get("8")};
    Object[] hj = {null,zw.get("3"),tf.get("8"),zw.get("6"),tf.get("10"),zw.get("2"),
                        sxx.get("59"),tf.get("11"),sxx.get("58"),zw.get("4"),tf.get("9")};

    map.put("20",String.valueOf(hl[ng]));
    map.put("21",String.valueOf(hq[ng]));
    map.put("22",String.valueOf(hk[ng]));
    map.put("23",String.valueOf(hj[ng]));

    return map;
  }

  /**
   * 10. 得到年支星系  何星对应何宫
   * 天马 火星 铃星 天哭 天虚 孤辰 寡宿 劫煞 华盖 咸池 红鸾 天喜 破碎 蜚廉 天才 天寿 龙池 凤阁
   * 参数：生年支 命宫支 身宫支
   */
  private Map getNianZiXx(int nz,int mgz,int sgz) {
    Map map = new TreeMap();

    int tk = pub.getNiShu(7,1,nz);
    int tx = pub.getShunShu(7,1,nz);
    int[] gc = {0,YiJing.YIN,YiJing.YIN,YiJing.SI,YiJing.SI,
        YiJing.SI,YiJing.SHEN,YiJing.SHEN,YiJing.SHEN,
        YiJing.HAI,YiJing.HAI,YiJing.HAI,YiJing.YIN};
    int[] gs = {0,YiJing.XU,YiJing.XU,YiJing.CHOU,YiJing.CHOU,
        YiJing.CHOU,YiJing.CHEN,YiJing.CHEN,YiJing.CHEN,
        YiJing.WEI,YiJing.WEI,YiJing.WEI,YiJing.XU};
    int[] js = {0,YiJing.SI,YiJing.YIN,YiJing.HAI,YiJing.SHEN,
        YiJing.SI,YiJing.YIN,YiJing.HAI,YiJing.SHEN,
        YiJing.SI,YiJing.YIN,YiJing.HAI,YiJing.SHEN};
    int[] hg = {0,YiJing.CHEN,YiJing.CHOU,YiJing.XU,YiJing.WEI,
        YiJing.CHEN,YiJing.CHOU,YiJing.XU,YiJing.WEI,
        YiJing.CHEN,YiJing.CHOU,YiJing.XU,YiJing.WEI};
    int[] xc = {0,YiJing.YOU,YiJing.WUZ,YiJing.MAO,YiJing.ZI,
        YiJing.YOU,YiJing.WUZ,YiJing.MAO,YiJing.ZI,
        YiJing.YOU,YiJing.WUZ,YiJing.MAO,YiJing.ZI};
    int hl = pub.getNiShu(1,4,nz);
    int tx2 = pub.getDuiGong(hl);
    int[] ps = {0,YiJing.SI,YiJing.CHOU,YiJing.YOU,YiJing.SI,
        YiJing.CHOU,YiJing.YOU,YiJing.SI,YiJing.CHOU,
        YiJing.YOU,YiJing.SI,YiJing.CHOU,YiJing.YOU};
    int[] fl = {0,YiJing.SHEN,YiJing.YOU,YiJing.XU,YiJing.SI,
        YiJing.WUZ,YiJing.WEI,YiJing.YIN,YiJing.MAO,
        YiJing.CHEN,YiJing.HAI,YiJing.ZI,YiJing.CHOU};
    int tc = pub.getShunShu(1,mgz,nz);
    int ts = pub.getShunShu(1,sgz,nz);
    int[] lc = {0,YiJing.CHEN,YiJing.SI,YiJing.WUZ,YiJing.WEI,
        YiJing.SHEN,YiJing.YOU,YiJing.XU,YiJing.HAI,
        YiJing.ZI,YiJing.CHOU,YiJing.YIN,YiJing.MAO};
    int[] fg = {0,YiJing.XU,YiJing.YOU,YiJing.SHEN,YiJing.WEI,
        YiJing.WUZ,YiJing.SI,YiJing.CHEN,YiJing.MAO,
        YiJing.YIN,YiJing.CHOU,YiJing.ZI,YiJing.HAI};

    map.put("30",String.valueOf(tk));
    map.put("31",String.valueOf(tx));
    map.put("32",String.valueOf(gc[nz]));
    map.put("33",String.valueOf(gs[nz]));
    map.put("34",String.valueOf(js[nz]));
    map.put("35",String.valueOf(hg[nz]));
    map.put("36",String.valueOf(xc[nz]));
    map.put("37",String.valueOf(hl));
    map.put("38",String.valueOf(tx2));
    map.put("39",String.valueOf(ps[nz]));
    map.put("40",String.valueOf(fl[nz]));
    map.put("41",String.valueOf(tc));
    map.put("42",String.valueOf(ts));
    map.put("43",String.valueOf(lc[nz]));
    map.put("44",String.valueOf(fg[nz]));

    return map;
  }

  /**
   * 11. 得到年上星系 何星对应何宫
   * 旬空
   */
  private int[] getNianXx(int ng, int nz) {
    return pub.getXunKong(ng,nz);
  }

  /**
   * 12. 得到月上星系 何星对应何宫
   * 左辅 右弼 天刑 天姚 天月 阴煞 解神 天巫
   */
  private Map getYueXx(int yz) {
    Map map = new TreeMap();
    int zf = pub.getShunShu(YiJing.YIN,5,yz);
    int yb = pub.getNiShu(YiJing.YIN,11,yz);
    int tx = pub.getShunShu(YiJing.YIN,10,yz);
    int ty = pub.getShunShu(YiJing.YIN,2,yz);
    int[] ty2 = {0,YiJing.XU,YiJing.YIN,YiJing.XU,YiJing.SI,YiJing.CHEN,YiJing.YIN,
         YiJing.WEI,YiJing.MAO,YiJing.HAI,YiJing.WEI,
         YiJing.YIN,YiJing.WUZ};
    int[] ys = {0,YiJing.WUZ,YiJing.CHEN,YiJing.YIN,YiJing.ZI,
        YiJing.XU,YiJing.SHEN,YiJing.WUZ,YiJing.CHEN,
        YiJing.YIN,YiJing.ZI,YiJing.XU,YiJing.SHEN};
    int[] js = {0,YiJing.WUZ,YiJing.WUZ,YiJing.SHEN,YiJing.SHEN,
        YiJing.XU,YiJing.XU,YiJing.ZI,YiJing.ZI,
        YiJing.YIN,YiJing.YIN,YiJing.CHEN,YiJing.CHEN};
    int[] tw = {0,YiJing.YIN,YiJing.HAI,YiJing.SI,YiJing.SHEN,
        YiJing.YIN,YiJing.HAI,YiJing.SI,YiJing.SHEN,
        YiJing.YIN,YiJing.HAI,YiJing.SI,YiJing.SHEN};
    int[] tm = {0,YiJing.YIN,YiJing.HAI,YiJing.SHEN,YiJing.SI,
        YiJing.YIN,YiJing.HAI,YiJing.SHEN,YiJing.SI,
        YiJing.YIN,YiJing.HAI,YiJing.SHEN,YiJing.SI};

    map.put("27",String.valueOf(tm[yz]));
    map.put("46",String.valueOf(zf));
    map.put("47",String.valueOf(yb));
    map.put("48",String.valueOf(tx));
    map.put("49",String.valueOf(ty));
    map.put("50",String.valueOf(ty2[yz]));
    map.put("51",String.valueOf(ys[yz]));
    map.put("52",String.valueOf(js[yz]));
    map.put("53",String.valueOf(tw[yz]));

    return map;
  }

  /**
   * 13. 得到日上星系 何星对应何宫
   * 三台 八座 恩光 天贵
   * 参数：左辅宫，右弼宫，文昌宫，文曲宫，生日
   */
  private Map getRiXx(int zfg,int ybg,int wcg, int wqg, int d) {
    Map map = new TreeMap();
    int st = pub.getShunShu(1,zfg,d);
    int bz = pub.getNiShu(1,ybg,d);
    int eg = pub.getShunShu(1,wcg,d-1);
    int tg = pub.getShunShu(1,wqg,d-1);

    map.put("54",String.valueOf(st));
    map.put("55",String.valueOf(bz));
    map.put("56",String.valueOf(eg));
    map.put("57",String.valueOf(tg));

    return map;
  }

  /**
   * 14. 得到时上星系 何星对应何宫
   * 文昌 文曲 天空 地劫 台辅 封诰
   * 参数：左辅宫，右弼宫，文昌宫，文曲宫
   */
  private Map getShiXx(int nz, int sz) {
    Map map = new TreeMap();
    int[] hx = {0,YiJing.YIN,YiJing.MAO,YiJing.CHOU,YiJing.YOU,
        YiJing.YIN,YiJing.MAO,YiJing.CHOU,YiJing.YOU,
        YiJing.YIN,YiJing.MAO,YiJing.CHOU,YiJing.YOU};
    int[] lx = {0,YiJing.XU,YiJing.XU,YiJing.MAO,YiJing.XU,
        YiJing.XU,YiJing.XU,YiJing.MAO,YiJing.XU,
        YiJing.XU,YiJing.XU,YiJing.MAO,YiJing.XU};

    int wc = pub.getNiShu(1,11,sz);
    int wq = pub.getShunShu(1,5,sz);
    int tk = pub.getShunShu(nz,nz,nz+1);
    int dj = pub.getShunShu(1,12,sz);
    int dk = pub.getNiShu(1,12,sz);
    int tf = pub.getShunShu(1,7,sz);
    int fh = pub.getShunShu(1,3,sz);
    int hx1 = pub.getShunShu(1,hx[nz],sz);
    int lx2 = pub.getShunShu(1,lx[nz],sz);

    map.put("28",String.valueOf(hx1));
    map.put("29",String.valueOf(lx2));
    map.put("58",String.valueOf(wc));
    map.put("59",String.valueOf(wq));
    map.put("60",String.valueOf(tk));
    map.put("61",String.valueOf(dj));
    map.put("66",String.valueOf(dk));
    map.put("62",String.valueOf(tf));
    map.put("63",String.valueOf(fh));

    return map;
  }

  /**
   * 15. 得到固定某宫的星系 何星对应何宫
   * 天伤 天使
   * 参数：奴仆宫 疾厄宫
   */
  private Map getGongXx(int npg, int jeg) {
    Map map = new TreeMap();
    map.put("64",String.valueOf(npg));
    map.put("65",String.valueOf(jeg));

    return map;
  }

  /**
   * 16. 起博士十二神煞 阳男阴女顺，阴男阳女逆排 何宫对应何星
   * 博士 力士 青龙 小耗 将军 奏书 飞廉 喜神 病符 大耗 伏兵 官符
   */
  private Map getBoShi12(int lcg, int ng, boolean sex) {
    Map map = new TreeMap();
    int gz ;
    if((ng%2==1 && sex) || (ng%2==0 && !sex)) {
      for(int i=1; i<=12; i++) {
        gz = pub.getShunShu(lcg,1,i);
        map.put(String.valueOf(i), String.valueOf(gz));
      }
    }else{
      for(int i=1; i<=12; i++) {
        gz = pub.getNiShu(lcg,1,i);
        map.put(String.valueOf(i), String.valueOf(gz));
      }
    }

    return map;
  }

  /**
   * 17. 起长生十二神煞 阳男阴女顺，阴男阳女逆排 何宫对应何星
   * 长生 沐浴 冠带 临官 帝旺 衰 病 死 墓 绝 胎 养
   */
  private Map getChangSheng12(int wxj, int ng, boolean sex) {
    Map map = new TreeMap();

    int[] cs = {0,0,YiJing.SHEN,YiJing.HAI,YiJing.SI,YiJing.SHEN,YiJing.YIN};
    int gz ;
    if((ng%2==1 && sex) || (ng%2==0 && !sex)) {
      for(int i=1; i<=12; i++) {
        gz = pub.getShunShu(cs[wxj],1,i);
        map.put(String.valueOf(i), String.valueOf(gz));
      }
    }else{
      for(int i=1; i<=12; i++) {
        gz = pub.getNiShu(cs[wxj],1,i);
        map.put(String.valueOf(i), String.valueOf(gz));
      }
    }

    return map;
  }

  /**
   * 18. 起太岁十二神煞 何宫对应何星
   * 太岁 太阳 伤门 太阴 官符 死符 岁破 龙德 白虎 福德 弔客 病符
   */
  private Map getTaiSui12(int nz) {
    int gz;
    Map map = new TreeMap();
    for(int i=0; i<=11; i++) {
      gz = (nz+i)%12==0?12:(nz+i)%12;
      map.put(String.valueOf(gz), String.valueOf(i+1));
    }
    return map;
  }

  /**
   * 19. 起大限
   * 阳男阴女从父母宫，阴男阳女从兄弟宫，十岁一宫 某宫映射岁数
   * 起限岁数，木三局3岁，水2，金4，土5，火6
   */
  private Map getDaXian(int mg, int ng, boolean sex, int wxj) {
    Map map = new TreeMap();

    if((ng%2==1 && sex) || (ng%2==0 && !sex)) {
      for (int i = 1; i <= 12; i++) {
        map.put(String.valueOf(i), String.valueOf(((i-mg+12)%12==0?12:(i-mg+12)%12)*10+wxj));
      }
      map.put(String.valueOf(mg),String.valueOf(wxj));
    }else {
      for (int i = 1; i <= 12; i++) {
        map.put(String.valueOf(i), String.valueOf(((mg-i+12)%12==0?12:(mg-i+12)%12)*10+wxj));
      }
      map.put(String.valueOf(mg),String.valueOf(wxj));
    }

    return map;
  }

  /**
   * 20. 起小限 某宫映射岁数
   * 以生年支，寅午戌-辰，申子辰-戌 巳酉丑-未 亥卯未-丑
   */
  private Map getXiaoXian(boolean sex) {
    Map map = new TreeMap();
    int[] xx = {0,YiJing.XU,YiJing.WEI,YiJing.CHEN,YiJing.CHOU,
                  YiJing.XU,YiJing.WEI,YiJing.CHEN,YiJing.CHOU,
                  YiJing.XU,YiJing.WEI,YiJing.CHEN,YiJing.CHOU};
    if(sex) {
      for (int i = 1; i <= 12; i++) {
        map.put(String.valueOf(i), String.valueOf(((i-xx[i]+12)%12==0?12:(i-xx[i]+12)%12)+1));
      }
    }
    else {
      for (int i = 1; i <= 12; i++) {
        map.put(String.valueOf(i), String.valueOf(((xx[i]-i+12)%12==0?12:(xx[i]-i+12)%12)+1));
      }
    }

    return map;
  }

  /**
   * 21. 安斗君
   */
  private int getDouJun(int nz, int yz, int sz) {
    int x = pub.getNiShu(YiJing.YIN,nz,yz);
    return pub.getShunShu(1,x,sz);
  }

  /**
   * 22. 安命主
   * @param zw 紫微星系 紫微、天机、太阳、武曲、天同、廉贞
   * @param tf 天府星系 天府、太阴、贪狼、巨门、天相、天梁、七杀、破军
   * @param ng 禄存 擎羊 陀罗 天魁 天钺 化禄 化权 化科 化忌 天福 截空 天官
   * @param sxx 时星系 文昌 文曲 天空 地劫 台辅 封诰
   */
  private int getMingZhu(int mgz, Map zw, Map tf, Map ng, Map sxx) {
    int[] mz = {0,9,10,15,59,6,4,14,4,6,59,15,10};
    return mz[mgz];
  }

  /**
   * 23. 安身主
   * @param zw 紫微星系 紫微、天机、太阳、武曲、天同、廉贞
   * @param tf 天府星系 天府、太阴、贪狼、巨门、天相、天梁、七杀、破军
   * @param nz 天马 火星 铃星 天哭 天虚 孤辰 寡宿 劫煞 华盖 咸池 红鸾 天喜 破碎 蜚廉 天才 天寿 龙池 凤阁
   * @param sxx 时星系 文昌 文曲 天空 地劫 台辅 封诰
   */
  private int getShenZhu(int snz, Map zw, Map tf, Map nz, Map sxx) {
    int[] sz = {0,28,11,12,5,58,2,29,11,12,5,58,2};
    return sz[snz];
  }

  /**
   * 24 安流年星系
   * 流昌 流禄 流擎 流陀 流马 流年三杀劫煞-灾煞-岁煞 流年三吉天德-月德-解神
   * @param nz 天马 火星 铃星 天哭 天虚 孤辰 寡宿 劫煞 华盖 咸池 红鸾 天喜 破碎 蜚廉 天才 天寿 龙池 凤阁
   * @param ng 禄存 擎羊 陀罗 天魁 天钺 化禄 化权 化科 化忌 天福 截空 天官
   */
  private Map getLiuNianXx(int ng, int nz) {
    Map map = new TreeMap();

    int[] lnwc = {0,YiJing.SI,YiJing.WUZ,YiJing.SHEN,YiJing.YOU,
        YiJing.SHEN,YiJing.YOU,YiJing.HAI,YiJing.ZI,YiJing.YIN,YiJing.MAO};
    int[] js = {0,YiJing.SI,YiJing.YIN,YiJing.HAI,YiJing.SHEN,
        YiJing.SI,YiJing.YIN,YiJing.HAI,YiJing.SHEN,
        YiJing.SI,YiJing.YIN,YiJing.HAI,YiJing.SHEN};
    int[] zs = {0,YiJing.WUZ,YiJing.MAO,YiJing.ZI,YiJing.YOU,
        YiJing.WUZ,YiJing.MAO,YiJing.ZI,YiJing.YOU,
        YiJing.WUZ,YiJing.MAO,YiJing.ZI,YiJing.YOU};
    int[] ss = {0,YiJing.WEI,YiJing.CHEN,YiJing.CHOU,YiJing.XU,
        YiJing.WEI,YiJing.CHEN,YiJing.CHOU,YiJing.XU,
        YiJing.WEI,YiJing.CHEN,YiJing.CHOU,YiJing.XU};
    int td = pub.getShunShu(YiJing.YOU,1,nz);
    int yd = pub.getShunShu(YiJing.ZI,1,nz);
    int js2 = pub.getShunShu(YiJing.XU,1,nz);
    int[] lcs = {0,YiJing.YIN,YiJing.MAO,YiJing.SI,YiJing.WUZ,
        YiJing.SI,YiJing.WUZ,YiJing.SHEN,YiJing.YOU,YiJing.HAI,YiJing.ZI};
    int lc = lcs[ng];
    int[] tm = {0,YiJing.YIN,YiJing.HAI,YiJing.SHEN,YiJing.SI,
        YiJing.YIN,YiJing.HAI,YiJing.SHEN,YiJing.SI,
        YiJing.YIN,YiJing.HAI,YiJing.SHEN,YiJing.SI};


    map.put("1",String.valueOf(lnwc[ng]));
    map.put("2",String.valueOf(lcs[ng]));  //1-禄存星，值为对应的地支
    map.put("3",String.valueOf((lc+1)%12==0?12:(lc+1)%12));
    map.put("4",String.valueOf((lc-1+12)%12==0?12:(lc-1+12)%12));
    map.put("5",String.valueOf(tm[ng]));
    map.put("6",String.valueOf(js[ng]));
    map.put("7",String.valueOf(zs[ng]));
    map.put("8",String.valueOf(ss[ng]));
    map.put("9",String.valueOf(td));
    map.put("10",String.valueOf(yd));
    map.put("11",String.valueOf(js2));

    return map;
  }

  /**
   * 25. 安童限 某宫映射岁数
   * 命宫1、兄弟、夫妻4、子女、财帛2、疾厄3、迁移、奴仆、官禄6、田宅、福德5、父母。
   * @param mg12 命宫12宫
   */
  private Map getTongXian(Map mg12) {
    Map map = new TreeMap();

    map.put(mg12.get("1"),"1");
    map.put(mg12.get("5"),"2");
    map.put(mg12.get("6"),"3");
    map.put(mg12.get("3"),"4");
    map.put(mg12.get("9"),"5");
    map.put(mg12.get("11"),"6");
    map.put(mg12.get("1"),"15");

    return map;
  }

  /**
   * 26. 安主星星等
   */

  /**
   * 输出九宫图
   */
  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  public static void main(String[] args) {
    DaoZiWeiMain dao = new DaoZiWeiMain();
    System.out.println("命宫："+dao.getMingGong(YiJing.CHOU, YiJing.SHEN));
    System.out.println("身宫："+dao.getShenGong(YiJing.YIN, YiJing.SHEN));
    System.out.println("你好 ".length());
  }

}
