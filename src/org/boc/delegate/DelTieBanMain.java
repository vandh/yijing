package org.boc.delegate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.PrintWriter;
import org.boc.util.Debug;
import org.boc.dao.DaoPublic;
import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoTieBan;
import org.boc.util.Messages;

public class DelTieBanMain {
  private StringBuffer str;
  private PrintWriter pw;
  private DaoPublic pub;
  private DaoTieBan tb;
  private DaoCalendar cal;
  private String kg ;

  public DelTieBanMain() {
    pub = new DaoPublic();
    str = new StringBuffer();
    pw = DelLog.getLogObject();
    tb = new DaoTieBan();
    cal = new DaoCalendar();
    kg = "\r\n    ";
  }

  /**
     * 输出命运
     * @param y 年
     * @param m 月
     * @param d 日
     * @param h 时
     * @param mi 分
     * @param type 是阴历还阳历时间，true为阴历
     * @param yun  是闰月还是非闰月，true为闰月
     * @param isMan 是否男女
     * @param str
     */
    public String getMingYun(
        int y, int m, int d, int h,int mi,
        boolean type, boolean yun, int sheng ,int shi, boolean isMan) {
      try{
        str.delete(0,str.length());
        //输出时间信息
        str.append("\r\n基本信息：");
        str.append(pub.getStandHead(y,m,d,h,mi,type,yun,sheng,shi));

        int[] bz = cal.getSiZhu(y,m,d,h,mi,yun,type);
        int jq = pub.getJieQi(y,m,d,h,mi,yun,isMan)[1];  //本节节气
        printOut(bz,y,isMan,jq, y);

        //Debug.out(str.toString());
      }catch(Exception ex) {
        Messages.error("DelTieBanMain("+ex+")");
      }

      return str.toString();
    }

    /**
     * 由八字推算命运
     */
    public String getMingYun(
        int ng,int nz,int yg,int yz,int rg,int rz,int sg,int sz,
        boolean yun, boolean isMan) {
      try{
        str.delete(0,str.length());
        //输出时间信息
        //dao.getDateOut(ng, nz, yg, yz, rg, rz, sg, sz, yun, isMan, str);
        //printOut();
      }catch(Exception ex) {
        Messages.error("DelTieBanMain("+ex+")");
      }

      return str.toString();
    }

    /**
     * 打印出所有的基本信息如大运，小运，流年等
     * @return
     * @throws java.lang.Exception
     */
    private void printOut(int[] bz,int y, boolean boy,
                          int jq,int year) throws Exception{
      int ths ;

      int[] zg = tb.getXianTianGua(bz, y,boy,jq);
      ths = tb.getGuaTaiHuShu(zg);
      str.append("\r\n\r\n太互数配卦：");
      str.append(kg+"主卦太互数：先天运势");
      str.append(kg+"    "+tb.getGuaTaiHuDesc(zg));
      str.append(kg+"    神数曰：终日为人谋，何不自为之？");
      str.append("\r\n");
      int[] bg = tb.getHouTianGua(zg, bz[4]);
      ths = tb.getGuaTaiHuShu(bg);
      str.append(kg+"变卦太互数：后天运势");
      str.append(kg+"    "+tb.getGuaTaiHuDesc(bg));
      str.append(kg+"    神数曰：整顿琴声，不负良辰美景！");
      str.append("\r\n");
      int[] hg = tb.getHuGua(zg);
      ths = tb.getGuaTaiHuShu(hg);
      str.append(kg+"互卦太互数：过程运势");
      str.append(kg+"    "+tb.getGuaTaiHuDesc(hg));
      str.append(kg+"    神数曰：整顿琴声，不负良辰美景！");
      str.append("\r\n");
      str.append("\r\n大值运卦与值年卦：");
      str.append(tb.getDaZhiYunGua(zg, bg, year));
      str.append("\r\n");
      str.append("\r\n六亲卦：");
      str.append(kg+"父母卦："+tb.guaShuByGanZi(bz[1],bz[2],5,true)+"，以查乾宫甲流度，坤宫甲流度。");
      str.append(kg+"兄妹卦："+tb.guaShuByGanZi(bz[3],bz[4],1,false)+"。");
      if(boy)
        str.append(kg+"贤妻卦："+tb.guaShuByGanZi(bz[5],bz[6],3,false)+"，以查木宫甲乙度。");
      else
        str.append(kg+"良夫卦："+tb.guaShuByGanZi(bz[5],bz[6],4,false)+"。");
      str.append(kg+"子孙卦："+tb.guaShuByGanZi(bz[7],bz[8],2,false)+"。");
      str.append("\r\n");
      str.append("\r\n先后天卦互卦：");
      str.append("\r\n");
      str.append(tb.printGuaXiang(bz[5],zg,bg,hg));
    }

    public static void main(String[] args) {
      DelTieBanMain del = new DelTieBanMain();
      String s = del.getMingYun(1977,3,23,6,30,true,true,0,1,true); //阴阳 闰 男女
      System.out.println(s);
    }

}
