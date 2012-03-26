package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.ly.Guaing;
import org.boc.db.ly.Liuyao;
import org.boc.ui.ResultPanel;
import org.boc.util.Messages;

public class DelYiJingMain {

  private DaoYiJingMain daoly;
  private LiuyaoPublic pub;
  private DaoCalendar daocal;
  private PrintWriter pw;
  private Guaing gua;
  StringBuffer str ;

  public DelYiJingMain() {
  	daoly = new DaoYiJingMain();    
    pw = DelLog.getLogObject();
    daocal = new DaoCalendar();
    pub = new LiuyaoPublic(daoly, daocal);
    gua = new Guaing(pub);
    str = new StringBuffer();
  }
  
  public DaoCalendar getDaoCalendar() {
  	return daocal;
  }
  public DaoYiJingMain getDaoYiJingMain() {
  	return daoly;
  }
  public LiuyaoPublic getLiuyaoPublic() {
  	return pub;
  }
  public void setHead(int[] sj, int h, int mi, int sheng ,int shi,String born,boolean sex) {
    str = new StringBuffer();
    //if (SiZhu.yinli == null || "".equals(SiZhu.yinli.trim())) {
    str.append("\n    "+daocal.getShiJian(sj, h, mi, sheng, shi,born,sex));
    //}
  }
  
  /**
   * 按所有方式起卦
   */
  public void getGuaXiang(ResultPanel rp,
  		int mode, int yshen, String mzhu,
  		boolean isBoy, int sheng, int shi,
  		int y, int m, int d, int h, int mi,
  		boolean isYin, boolean isYun,
      int up, int down, int[] acts) {
  	
  	gua.setParameter(rp, mode, yshen, mzhu, isBoy, sheng, shi, y, m, d, h, mi, isYin, isYun, up, down, acts);
  	gua.getGua();
  }

  /**
   * 由时间+年支|年干 起卦
   * type: 年干或年支
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int sheng, int shi,String mzhu,boolean sex, int type) {
    str = new StringBuffer();
    
    //0是否闰月 1-8四柱 9-11阳历年月日 12-14取的农历年月日
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    //得到是按年干还是年支起卦
    int nian = type==Liuyao.SJTG ? sz[1] : type==Liuyao.SJDZ ? sz[2] : sz[2];
    int up = (nian+sz[13]+sz[14])%8 == 0 ? 8 : (nian+sz[13]+sz[14])%8;
    int down = (up+sz[8])%8 == 0 ? 8 : (up+sz[8])%8;
    int change = (nian+sz[10]+sz[11]+sz[8])%6 == 0 ? 6 : (nian+sz[10]+sz[11]+sz[8])%6;
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    return getGuaXiang(up,down,new int[]{change},sz,ys);
  }
  /**
   * 手工摇卦或电脑摇卦
   * 前台传来1少阳 ─── 2少阴 ─　─ 3老阳─── ○ 4老阴─　─ ╳
   * 代理要求1为阳,2为阴
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int[] yg,
                            int sheng, int shi,String mzhu,boolean sex) {
    str = new StringBuffer();
    int i=0, j=0;
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    int up = YiJing.XIANGGUA[yg[4]%2==0?2:yg[4]%2]
                            [yg[5]%2==0?2:yg[5]%2]
                            [yg[6]%2==0?2:yg[6]%2];
    int down = YiJing.XIANGGUA[yg[1] % 2 == 0 ? 2 : yg[1] % 2]
                            [yg[2] % 2 == 0 ? 2 : yg[2] % 2]
                            [yg[3] % 2 == 0 ? 2 : yg[3] % 2];

    for(i=0; i<yg.length; i++) {
      if(yg[i]>2) j++;
    }
    int[] change ;
    if(j==0) {
      change = new int[]{0};
    }else{
      change = new int[j];
      j=0;
      for (i = 0; i < yg.length; i++) {
        if (yg[i] > 2) change[j++] = i;
      }
    }

    return getGuaXiang(up,down,change,sz,ys);
  }

  /**
   * 按卦象装卦
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int up, int down,int[] change,
                            int sheng, int shi,String mzhu,boolean sex) {
    str = new StringBuffer();
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    return getGuaXiang(up,down,change,sz,ys);
  }

  /**
   * 报数起卦
   */
  public String getGuaXiang(int y, int m, int d, int h, int mi,
                            boolean isYin, boolean isYun, int ys,
                            int sg, int xg, boolean isAdd,
                            int sheng, int shi,String mzhu,boolean sex) {
    str = new StringBuffer();
    int[] sz = daocal.getSiZhu(y,m,d,h,mi,isYun,isYin);
    setHead(sz,h,mi,sheng,shi,mzhu,sex);
    int up = sg % 8 == 0 ? 8 : sg % 8;
    int down = xg % 8 == 0 ? 8 : xg % 8;
    int change = 0;
    if(isAdd)
      change = (sg+xg+sz[8])%6==0?6:(sg+xg+sz[8])%6;
    else
      change = (sg+xg)%6==0?6:(sg+xg)%6;
    return getGuaXiang(up,down,new int[]{change},sz,ys);
  }


  /**
   * 由上下卦的数、变爻、日干得到卦象
   * 1. 由上下卦得到所属八宫卦、
   * 2. 所属五行
   * 3. 卦象
   * 4. 世在哪爻、应在哪爻
   * 5. 各爻地支如何排列
   * 6. 各爻六亲
   * 7. 找卦身、世身
   * 8. 装六神
   * 9. 装变爻卦卦象
   * 10. 装飞伏
   * @param b 如果为真，则要清空str缓存，因网站测以public取阴阳历头部，而单机版用全局函数
   * @return
   */
  public String getGuaXiang(int up, int down, int[] changes,
                            int[] shijian, int yongshen,boolean b) {
    if(b)  str = new StringBuffer();
    return getGuaXiang(up,down,changes,shijian,yongshen);
  }
  public String getGuaXiang(int up, int down, int[] changes,
                            int[] shijian, int yongshen) {
    try{
      //1. 主/互/变/八宫经卦的上、下卦及动爻
      up = up % 8 == 0 ? 8 : up % 8;
      down = down % 8 == 0 ? 8 : down % 8;
      //for(int i=0; i<changes.length; i++) {
      //  changes[i] = (changes[i]%6 == 0 && changes[i]>0)? 6 : changes[i]%6;
      //}
      int rigan = shijian[5];
      int yueZi = shijian[4];

      int upHu = daoly.getHuGuaUpOrDown(up, down, "UP");
      int downHu = daoly.getHuGuaUpOrDown(up, down, "DOWN");

      int upBian = daoly.getBianGuaUpOrDown(up, down, changes, "UP");
      int downBian = daoly.getBianGuaUpOrDown(up, down, changes, "DOWN");

      //2. 主互变卦的所属宫、八宫经卦的上、下卦
      int whichGongZhu = daoly.getWhichGong(up, down);
      int whichGongHu = daoly.getWhichGong(upHu, downHu);
      int whichGongBian = daoly.getWhichGong(upBian, downBian);

      int upGong = whichGongZhu;
      int downGong = whichGongZhu;

      //3. 主卦所属八宫卦的五行
      int whichWh = daoly.getWhichWH(whichGongZhu);

      //4. 得到主/互/变卦卦象
      int[] upGuaXiang = daoly.getGuaXiang(up);
      int[] downGuaXiang = daoly.getGuaXiang(down);
      int[] guaXiang = daoly.mergeIntArray(upGuaXiang, downGuaXiang);

      int[] upGuaXiangHu = daoly.getGuaXiang(upHu);
      int[] downGuaXiangHu = daoly.getGuaXiang(downHu);
      int[] guaXiangHu = daoly.mergeIntArray(upGuaXiangHu, downGuaXiangHu);

      int[] upGuaXiangBian = daoly.getGuaXiang(upBian);
      int[] downGuaXiangBian = daoly.getGuaXiang(downBian);
      int[] guaXiangBian = daoly.mergeIntArray(upGuaXiangBian, downGuaXiangBian);

      //5. 得到主/互/变卦世爻与应爻的位置
      int shiYao = daoly.getShiYao(up, down);
      int yingYao = daoly.getYingYao(shiYao);

      int shiYaoHu = daoly.getShiYao(upHu, downHu);
      int yingYaoHu = daoly.getYingYao(shiYaoHu);

      int shiYaoBian = daoly.getShiYao(upBian, downBian);
      int yingYaoBian = daoly.getYingYao(shiYaoBian);

      //6. 得到主/互/变/宫经卦的地支
      int[] upDizi = daoly.getGuaDiZi(up, 1);
      int[] downDizi = daoly.getGuaDiZi(down, 0);
      int[] diZi = daoly.mergeIntArray(upDizi, downDizi);

      int[] upDiziHu = daoly.getGuaDiZi(upHu, 1);
      int[] downDiziHu = daoly.getGuaDiZi(downHu, 0);
      int[] diZiHu = daoly.mergeIntArray(upDiziHu, downDiziHu);

      int[] upDiziBian = daoly.getGuaDiZi(upBian, 1);
      int[] downDiziBian = daoly.getGuaDiZi(downBian, 0);
      int[] diZiBian = daoly.mergeIntArray(upDiziBian, downDiziBian);

      int[] upDiziGong = daoly.getGuaDiZi(upGong, 1);
      int[] downDiziGong = daoly.getGuaDiZi(downGong, 0);
      int[] diZiGong = daoly.mergeIntArray(upDiziGong, downDiziGong);

      //7. 得到主/互/变/宫卦的六亲
      int[] liuQin = daoly.getLiuQin(diZi, whichWh);
      int[] liuQinHu = daoly.getLiuQin(diZiHu, whichWh);
      int[] liuQinBian = daoly.getLiuQin(diZiBian, whichWh);
      int[] liuQinGong = daoly.getLiuQin(diZiGong, whichWh);

      //8. 得到六神
      int[] liuShen = daoly.getLiuShen(rigan);

      //9. 开始取用神、年支、月支、日支、时支

      //10. 得到月卦身或世身
      int yueGuaShen = daoly.getGuaShen(up, down, shiYao);
      boolean isHasGuaShen = daoly.isGuaShen(up, down, yueGuaShen);
      int shiShen = daoly.getShiShen(diZi, shiYao);

      //11. 伏神、飞神
      int[] ff = daoly.howManyFeiFu(liuQin, liuQinGong);

      //12. 卦反呤、爻反呤
      boolean isGuaFanLing = daoly.isGuaXIANGKE(whichGongZhu, whichGongBian);
      boolean isYaoFanLing = false;
      for (int fl = 0; fl < changes.length; fl++) {
        if (daoly.isYaoFanLing(diZi[changes[fl]], diZiBian[changes[fl]])) {
          isYaoFanLing = true;
          break;
        }
      }
      //20. 得到周易卦辞与爻辞
      String[] guaCi = daoly.getGuaCiAndYaoCi(up, down);

      /**
       * 21. 开始打印输出
       */

      //0). 得到年月日时
      //str.append("\r\n    "+SiZhu.yinli);
      //str.append("\r\n    "+SiZhu.yangli);
      daoly.getNYRDOut(str, shijian);

      //1). 得到卦象
      daoly.getGuaXiangOut(
          str,
          up, down, changes,
          upBian, downBian,
          upHu, downHu,
          whichGongZhu, whichGongBian, whichGongHu,
          shiYao, yingYao,
          shiYaoBian, yingYaoBian,
          shiYaoHu, yingYaoHu,
          diZi, diZiBian, diZiHu, diZiGong,
          guaXiang, guaXiangBian, guaXiangHu,
          liuQin, liuQinBian, liuQinHu, liuQinGong,
          liuShen,
          ff);
      //2). 卦词与爻词
      daoly.getGuaYaoCiOut(str, guaCi, guaXiang, changes, up, down);
      // 旬空
      daoly.getXunKongOut(str, rigan, shijian[6]);
      //3). 月卦身
      daoly.getYeuGuaShenOut(str, yueGuaShen, isHasGuaShen);
      //4). 世身
      daoly.getShiShenOut(str, shiShen);
      //5). 反呤
      daoly.getFanLingOut(str, isGuaFanLing, isYaoFanLing);
      //6). 伏呤
      daoly.getFuLingOut(str, diZi, diZiBian, up, down, upBian, downBian);
      //7). 进神、退神
      daoly.getJinTuiShenOut(str, changes, diZi, diZiBian);
      //8). 六合、六冲卦
      daoly.getLiuHeChongOut(str, up, down);
      //9). 飞伏
      daoly.getFuFeiOut(str, ff, diZi, diZiGong);
      //10). 六神占断
      daoly.getLiuShenOut(str, liuShen, changes, diZi, liuQin, shiYao, yueZi);
      //11). 十二辟卦
      daoly.getTwenteenOut(str, up, down);
      //12). 京房十六变卦
      daoly.getJFSixteenOut(str, whichGongZhu, upBian, downBian, changes);
      //13). 用神
      daoly.getLiuYaoZhanOut(str, up, down, shijian,
                           upGong, downGong, shiYao, yingYao,
                           yongshen, diZi, liuQin, changes,
                           diZiBian, diZiGong, liuQinGong,
                           whichGongZhu, whichGongBian
                           );
      str.append("\r\n\n");
      //Debug.out(str.toString());
    }catch(Exception ex) {
      ex.printStackTrace();
      Messages.error("DelYiJingMain("+ex+")");
    }

    return str.toString();
  }

  public void finalize() {
    str = null;
    pw = null;
    pub = null;
    daocal = null;
    SiZhu.yinli = null;
    SiZhu.yangli = null;
  }

}
