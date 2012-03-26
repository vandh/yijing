package org.boc.delegate;

import org.boc.dao.DaoBh;
import org.boc.db.Xingming;
import org.boc.db.YiJing;
import java.util.*;
import java.io.PrintWriter;
import org.boc.util.Messages;

public class DelXmMain {
  private DaoBh bh ;
  private int[] wuxing;
  private String[][] wggx;
  private String kg;
  private int[] bhArray;
  private int[] wugeNum;
  private String pingfen;
  private PrintWriter pw;

  public DelXmMain() {
    bh = new DaoBh();
    wuxing = new int[]{YiJing.SHUI, YiJing.MU, YiJing.MU, YiJing.HUO, YiJing.HUO
        , YiJing.TU, YiJing.TU, YiJing.JIN, YiJing.JIN, YiJing.SHUI};
    wggx = new String[5][11];
    wugeNum = new int[5];
    kg = "\r\n    ";
    getWugeGxDesc();
    pw = DelLog.getLogObject();
  }

  public String fx(String xm, boolean isDx) {
    if(xm==null || "".equals(xm.trim())) {
      return "阁下的姓名是无字天书啊！";
    }
    StringBuffer buff = new StringBuffer();

    try{
      bhArray = new int[xm.length()];
      String tJudge = "";
      int num = 0;
      char zang;
      for (int i = 0; i < xm.length(); i++) {
        zang = xm.charAt(i);
        for (int j = 0; j < Xingming.filter.length; j++) {
          for (int k = 0; k < Xingming.filter[j].length; k++) {
            if (Xingming.filter[j][k] == 0)
              break;
            if (zang == Xingming.filter[j][k])
              num++;
          }
        }
      }
      if (num >= 2)
        return "阁下的姓名多有不雅，趁早改名为上，不测也罢。";
      if (num == 1)
        tJudge = "有不雅之字，望斟酌一二。";

      String kg = "    ";
      String xmjt = "", xmft = "", xmbh = "", xmwh = "";
      int x1 = 0, x2 = 0, m1 = 0, m2 = 0;
      //String[] wx = {"水","木","木","火","火","土","土","金","金","水"};
      //得到繁体
      String xm2 = bh.gb2big5(xm);
      //得到空格的姓名、繁体、笔画、五行
      int _bh = 0;
      for (int i = 0; i < xm.length(); i++) {
        xmjt += xm.charAt(i) + "  ";
        xmft += xm2.charAt(i) + "  ";
        _bh = bh.getBh(xm2.charAt(i));
        bhArray[i] = _bh;
        xmbh += _bh + "   ";
        xmwh += YiJing.WUXINGNAME[wuxing[ (_bh % 10)]] + "  ";
      }
      //判断是否是单名
      boolean isDm = false;
      int len = xm.length();
      if ( (isDx && len >= 3) || (!isDx && len >= 4))
        isDm = false;
      else
        isDm = true;
        //得到姓和名的笔画
      x1 = bh.getBh(xm2.charAt(0));
      if (isDx && isDm) {
        x2 = 0;
        m1 = bh.getBh(xm2.charAt(1));
        m2 = 0;
      }
      else if (!isDx && isDm) {
        x2 = bh.getBh(xm2.charAt(1));
        m1 = bh.getBh(xm2.charAt(2));
        m2 = 0;
      }
      else if (isDx && !isDm) {
        x2 = 0;
        m1 = bh.getBh(xm2.charAt(1));
        m2 = bh.getBh(xm2.charAt(2));
      }
      else {
        x2 = bh.getBh(xm2.charAt(1));
        m1 = bh.getBh(xm2.charAt(2));
        m2 = bh.getBh(xm2.charAt(3));
      }
      //得到五格之数
      int tg = x2 == 0 ? x1 + 1 : x1 + x2;
      int dg = m2 == 0 ? m1 + 1 : m1 + m2;
      int rg = isDx ? x1 + m1 : x2 + m1;
      int zg = x1 + x2 + m1 + m2;
      int wg = 0;
      if (len == 2) wg = zg - rg + 2;
      else if (len == 3) wg = zg - rg + 1;
      else wg = zg - rg;

      wugeNum = new int[] {
          tg, rg, dg, wg, zg};

      //输出
      buff.append(kg + "姓名： ");
      buff.append(kg + xmjt + "\r\n");
      buff.append(kg + "繁体： ");
      buff.append(kg + xmft + "\r\n");
      buff.append(kg + "笔画:  ");
      buff.append(kg + xmbh + "\r\n");
      buff.append(kg + "五行： ");
      buff.append(kg + xmwh + "\r\n");
      buff.append(kg + "五格： ");
      buff.append(kg + "天格-" + tg + "-" + YiJing.WUXINGNAME[wuxing[tg % 10]] +
                  "-" + getJxStr(getJx(tg)) +
                  "   人格-" + rg + "-" + YiJing.WUXINGNAME[wuxing[rg % 10]] +
                  "-" + getJxStr(getJx(rg)) +
                  "   地格-" + dg + "-" + YiJing.WUXINGNAME[wuxing[dg % 10]] +
                  "-" + getJxStr(getJx(dg)) +
                  "   外格-" + wg + "-" + YiJing.WUXINGNAME[wuxing[wg % 10]] +
                  "-" + getJxStr(getJx(wg)) +
                  "   总格-" + zg + "-" + YiJing.WUXINGNAME[wuxing[zg % 10]] +
                  "-" + getJxStr(getJx(zg)));
      buff.append("\r\n");
      buff.append("\r\n");
      buff.append("形义断：" + tJudge);
      getXingYi(buff, xm2, xm);
      buff.append("\r\n");
      buff.append("\r\n");
      buff.append("天格数" + tg + "：" + Xingming.wuge[0] + "\r\n");
      buff.append(kg + getDesc(tg));
      buff.append("\r\n");
      buff.append("人格数" + rg + "：" + Xingming.wuge[1] + "\r\n");
      buff.append(kg + getDesc(rg));
      buff.append("\r\n");
      buff.append("地格数" + dg + "：" + Xingming.wuge[2] + "\r\n");
      buff.append(kg + getDesc(dg));
      buff.append("\r\n");
      buff.append("外格数" + wg + "：" + Xingming.wuge[3] + "\r\n");
      buff.append(kg + getDesc(wg));
      buff.append("\r\n");
      buff.append("总格数" + zg + "：" + Xingming.wuge[4] + "\r\n");
      buff.append(kg + getDesc(zg));
      buff.append(kg + "\r\n");
      buff.append("数之吉凶：" + "\r\n");
      buff.append(kg + "天格数"+tg+":"+(Xingming.sljx[tg]==null?"":Xingming.sljx[tg])+"\r\n");
      buff.append(kg + "人格数"+rg+":"+(Xingming.sljx[rg]==null?"":Xingming.sljx[rg])+"\r\n");
      buff.append(kg + "地格数"+dg+":"+(Xingming.sljx[dg]==null?"":Xingming.sljx[dg])+"\r\n");
      buff.append(kg + "外格数"+wg+":"+(Xingming.sljx[wg]==null?"":Xingming.sljx[wg])+"\r\n");
      buff.append(kg + "总格数"+zg+":"+(Xingming.sljx[zg]==null?"":Xingming.sljx[zg])+"\r\n");
      buff.append(kg + "\r\n");
      buff.append("六亲：\r\n");
      buff.append(kg + getLiuqin(tg, rg, dg, wg, zg));
      buff.append("\r\n");
      buff.append("\r\n");
      buff.append("评分：");
      buff.append(getTotalCent(xm) + "分");
      buff.append(pingfen);
      buff.append("\r\n");
    }catch(Exception ex) {
    	ex.printStackTrace();
      Messages.error("DelXmMain("+ex+")");
    }

    //System.out.println("x1="+x1+";x2="+x2+";m1="+m1+";m2="+m2);
    //System.out.println(buff.toString());
    return buff.toString();
  }

  private void getXingYi(StringBuffer buff, String xm2,String xm) {
    String zi = null;
    String s1, s2 ,hz;

    for(int i=0; i<xm2.length(); i++) {
      hz = String.valueOf(xm.charAt(i));
      zi = (String)Xingming.map.get(hz);
      if(zi==null) {
        Set set1 = Xingming.mapBshz.keySet();
        for(Iterator it1=set1.iterator(); it1.hasNext();) {
          s1 = (String)it1.next();
          s2 = (String)Xingming.mapBshz.get(s1);
          if(s2.indexOf(hz)!=-1) {
            zi = (String) Xingming.mapBsms.get(s1);
            break;
          }
        }
      }
      if(zi==null){
        zi = "五格剖象字库缺。";
      }
      buff.append(kg+xm2.charAt(i)+"："+zi);
    }
  }

  //1吉 0中 2凶
  private int getJx(int bh) {
    for(int j=0; j<=2; j++) {
      for (int i = 0; i < Xingming.szjx[j].length; i++) {
        if (bh == Xingming.szjx[j][i])
          return j;
      }
    }

    return 0;
  }

  private String getDesc(int bh) {
    if(bh > Xingming.szhy.length)
      return "这个笔画数不在五格剖象法研究之列。";

    String[] desc = Xingming.szhy[bh];
    String rs = "";
    for(int i=0; i<desc.length; i++) {
      rs += desc[i]+"\r\n    ";
    }
    return rs;
  }

  private String getJxStr(int jx) {
    if(jx==0) return "平";
    else if(jx==1) return "吉";
    else return "凶";
  }

  private String getLiuqin(int tg, int rg, int dg, int wg, int zg) {
    String s = "";

    s += getRelate(tg, rg, 1);
    s += getRelate(rg, dg, 2);
    s += getRelate(rg, wg, 3);
    s += getRelate(rg, zg, 4);
    s += getZongAndRen(zg,rg);

    return s;
  }

  /**
   * 天格与人格的关系，可以看祖荫运
   * @param type 1-天格与人格 2-人格与地格 3-人格与外格 4-人格与总格
   * @return String
   */
  private String getRelate(int g1, int g2, int type) {
    String rs = null;
    boolean g1bh2 = false; //比和人
    boolean g1k2 = false;  //g1克g2
    boolean g2k1 = false;  //g2克g1
    int g2Jx = getJx(g2);

    if(YiJing.WXDANKE[wuxing[g1%10]][wuxing[g2%10]]>0) {
      g1k2 = true;
      rs =  wggx[type][0];
    }
    else if(YiJing.WXDANSHENG[wuxing[g1%10]][wuxing[g2%10]]>0)
      rs =  wggx[type][1];
    else if(YiJing.WXDANKE[wuxing[g2%10]][wuxing[g1%10]]>0) {
      g2k1 = true;
      rs =  wggx[type][2];
    }
    else if(YiJing.WXDANSHENG[wuxing[g2%10]][wuxing[g1%10]]>0)
      rs =  wggx[type][3];
    else {
      g1bh2 = true;
      rs =  wggx[type][4];
    }

    if(g2Jx==1 && g1bh2)
      rs +=  wggx[type][5];
    else if(g2Jx==1 && g2k1)
      rs +=  wggx[type][6];
    else if(g2Jx==1 && g1k2)
      rs +=  wggx[type][7];
    else if(g2Jx==2 && g1bh2)
      rs +=  wggx[type][8];
    else if(g2Jx==2 && g2k1)
      rs +=  wggx[type][9];
    else if(g2Jx==2 && g1k2)
      rs +=  wggx[type][10];

    return rs;
  }

  private String getZongAndRen(int zg,int rg) {
    String rs = "";
    int zgjx = getJx(zg);
    int rgjx = getJx(rg);
    if(zgjx == rgjx)
      rs += kg+"总格与人格意义相同，表里如一之人。";
    if((zgjx==1 && rgjx==2) || (zgjx==2 && rgjx==1)) {
      rs += "总格与人格意义相反，表里不相同之人。";
    }
    if(zgjx==1 && rgjx==2)
      rs += kg+"物质生活华丽，精神生活苦闷而空虚。";
    if(zgjx==2 && rgjx==1)
      rs += kg+"比较注重精神方面的修养。";
    if(zgjx==1 && rgjx==1)
      rs += kg+"容易在社会上功成名就。";
    if(zgjx==2 && rgjx==2)
      rs += kg+"思想偏激，容易遭逢灾难与失败之运。";

      return rs;
  }

  /**
   * 姓名评分
   * 1. 名字五行生克 10分
   * 2. 天地人外总   50分
   * 3. 天地人外总关系 20分
   * 4. 字形意义       20分
   * @return int
   */
  private int getTotalCent(String xm) {
    pingfen = "";
    int cent = getWxskCent();
    cent += getWuGeCent();
    cent += getWugeGxCent();
    cent += getZixingCent(xm);

    return 100+cent;
  }

  /**
   * 字形意义       20分
   * @param xm2 String
   * @return int
   */
  private int getZixingCent(String xm) {
    int cent = 0;
    String hz;

    for(int i=0; i<xm.length(); i++) {
      hz = String.valueOf(xm.charAt(i)).trim();
      if (Xingming.map.get(hz) != null)
        cent -= 5;
      else {
        Set set0 = Xingming.mapBshz.keySet();
        for (Iterator it1 = set0.iterator(); it1.hasNext(); ) {
          if (((String)Xingming.mapBshz.get((String) it1.next())).indexOf(hz) != -1) {
            cent -= 5;
          }
        }
      }
    }
    pingfen += kg+"字形意义分扣除: "+cent+"%";
    return cent;
  }

  /**
   * 天地人外总关系 20分
   * @return int
   */
  private int getWugeGxCent() {
    int cent = 0;
    //天格与人格
    if (getJx(wugeNum[1])==2 &&
        YiJing.WXXIANGKE[wuxing[wugeNum[0]%10]][wuxing[wugeNum[1]%10]] > 0) {
      cent -= 5;
    }
    //地、外、总与人格
    for(int i=2; i<wugeNum.length; i++) {
      //System.out.println(getJx(wugeNum[i])+";"+wugeNum[i]);
      if(i==1)
        continue;
      if (getJx(wugeNum[i])==2 &&
          YiJing.WXXIANGKE[wuxing[wugeNum[i]%10]][wuxing[wugeNum[1]%10]] > 0) {
        cent -= 5;
      }
    }
    pingfen += kg+"五格关系分扣除: "+cent+"%";
    return cent;
  }

  /**
   * 天地人外总   50分
   * @return int
   */
  private int getWuGeCent() {
    int cent = 0;
    for(int i=0; i<wugeNum.length; i++) {
      if (getJx(wugeNum[i]) == 2)
        cent -= 10;
    }
    pingfen += kg+"五格吉凶分扣除: "+cent+"%";
    return cent;
  }

  /**
   *  名字五行生克 10分
   * @return int
   */
  private int getWxskCent() {
    int cent = 0;
    for(int i=0; i<bhArray.length - 1; i++) {
      if(YiJing.WXXIANGKE[wuxing[bhArray[i]%10]][wuxing[bhArray[i+1]%10]]>0) {
        cent -= 5;
      }
    }
    if(cent<-10)
      cent=-10;
    pingfen += kg+"五形生克分扣除: "+cent+"%";
    return cent;
  }

  private void getWugeGxDesc() {
    wggx[1] = new String[]{"父母的管教比较严厉。",
        "父母比较疼爱子女。",
        "对父母管束容易产生反抗心理。",
        "比较孝顺。",
        "父母比较疼爱子女。",
        kg+"祖业可继承且与父母相处感情融洽。",
        kg+"为白手成家的类型。",
        kg+"和父母亲的关系容易发生纷歧。",
        kg+"父母对子女孩子有娇纵之虑。",
        kg+"对长辈的反抗心理特别强烈。",
        ""
        };
     wggx[2] = new String[]{
         kg+"家庭多劳苦之事。",
         kg+"能得到配偶及子女的帮助。",
         kg+"子女的身体较差。",
         kg+"对子女比较娇宠。",
         kg+"家庭团结向心力强。",
         kg+"能出贵子光耀门户。",
         "",
         kg+"子女的独立个性比较明显。",
         "",
         kg+"配偶的脾气古怪，子女的反抗心理比较强烈。",
         kg+"家庭中多是非。"
     };
     wggx[3] = new String[]{
         kg+"一生多为同事朋友受累。",
         kg+"一生多得朋友部属之助。",
         kg+"能得部属之助。",
         kg+"对待朋友比较宽宏大量。",
         "",
         kg+"处处得贵人相助。",
         kg+"能得良好的名声，受到别人的尊敬。",
         kg+"部属相当能干，但不能长久为自己所用。",
         "",
         kg+"容易和别人发生法律上的纠纷，造成损失。",
         ""
     };
     wggx[4] = new String[]{
         "",
         "",
         "",
         "",
         "",
         kg+"一生常多不劳而获的好运。",
         kg+"大多辛苦奋斗，脚踏实地工作而在社会上成功。",
         kg+"虽能成大事，但一生运途较劳碌。",
         "",
         kg+"运途不佳，思想悲观。",
         kg+"容易遭逢感情上或精神上的打击与纠纷。"
     };
  }

  public static void main(String[] args) {
    DelXmMain main = new DelXmMain();
    System.out.println(main.fx("aaa",true));
  }
}
