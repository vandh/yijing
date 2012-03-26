package org.boc.dao.ly;

import org.boc.dao.DaoPublic;
import org.boc.db.YiJing;

public class DaoYiJingMain {

  /**
   * 得到旬空的爻
   */
  public void getXunKongOut(StringBuffer str, int rigan, int rizi) {
    int xk = YiJing.KONGWANG[rigan][rizi];
    int zi1 = xk/100;
    int zi2 = xk - zi1*100;

    str.append("旬空：");
    str.append("\n");
    str.append(getRepeats(" ", YiJing.INTER[0]));
    str.append(YiJing.DIZINAME[zi1]+", " + YiJing.DIZINAME[zi2]);
  }

  /**
   * 得到应期
   */
  public void getYingQiOut(
      StringBuffer str, int zi, int local, int yuezi, int rigan, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int[] liuqin, int[] diziGong,
      int[] liuqinGong, boolean isFu) {
    //13、用爻旬空发动，又逢日冲，谓之冲实，即以本日断之。
    if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        if(YiJing.DZCHONG[zi][rizi] == 1) {
          str.append("\n");
          str.append(getRepeats(" ", YiJing.INTER[0]));
          str.append("应期：");
          str.append("该爻旬空发动或暗动，又逢日冲谓之冲实，即以本日断之。");
        }
      }
    }
    //9、用爻旬空安静，当以出旬逢冲之日断之。
    if(!isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("应期：");
        str.append("该爻旬空安静，当以出旬逢冲之日断之。");
      }
    }
    //10、用爻旬空发动，即以出旬之日断之。
    if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("应期：");
        str.append("该爻旬空发动或暗动，当以出旬之日断之。");
      }
    }
    //11、用爻旬空，发动被合，当以出旬冲合神之日断之。
    if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        if(isHe(str,rigan,rizi,yuezi,changes,dizi,diziBian,zi)) {
           str.append("\n");
           str.append(getRepeats(" ", YiJing.INTER[0]));
           str.append("应期：");
           str.append("该爻旬空发动或暗动又逢变爻或其它动爻或日、月合，当以出旬冲合神之日断之。");
        }
      }
    }
    //12、用爻旬空，安静被冲，当以出旬合冲神之日断之。
    if(!isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isXunKong(zi,rigan,rizi)) {
        if(isJingChong(str,rigan,rizi,yuezi,changes,dizi,diziBian,zi)) {
           str.append("\n");
           str.append(getRepeats(" ", YiJing.INTER[0]));
           str.append("应期：");
           str.append("该爻旬空安静被冲，当以出旬合冲神之日断之。");
        }
      }
    }
    //8、用爻人墓，当以冲墓之月日断之。
    if(isRuMu(str,new int[]{local,0}, changes,rigan, liuqinGong, diziGong, liuqin, dizi,
             diziBian, isFu, rizi, yuezi, "", false)) {
     str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("应期：");
     str.append("该爻人墓，当以冲墓之月日断之。");
   }
   //7、用爻得时旺动而又遇生扶，此为太旺，当以墓库月日断之。
   if(isWxsqx(zi,yuezi)){
     if(isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
       if(isDongAndBianSheng(zi,changes,dizi,diziBian)) {
         str.append("\n");
         str.append(getRepeats(" ", YiJing.INTER[0]));
         str.append("应期：");
         str.append("该爻得时旺动而又遇生扶，此为太旺，当以墓库月日断之。");
       }
     }
   }
   //4、如果用爻被合住，当以忡合之日断之。若用神被冲，当以合日断之。
   if(isHe(str,rigan,rizi,yuezi,changes,dizi,diziBian,zi)) {
     str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("应期：");
     str.append("该爻被合住，当以冲合之日断之。");
   }
   if(isDongAndBianChong(zi,changes,dizi,diziBian) || isRiYueChong(zi,rizi,yuezi)) {
     str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("应期：");
     str.append("该爻被动变日月爻冲，当以合日断之。");
   }
   //3、日辰临用神，或日辰临动爻来生合世爻用神，即以本日断之。
   boolean bool1 = false;
   boolean bool2 = false;
   if(zi == rizi)
     bool1 = true;
    for(int i=0; i<changes.length; i++) {
      if(rizi == dizi[changes[i]]) {
        if(YiJing.DZLIUHE[dizi[changes[i]]][zi] == 1 ||
           YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[changes[i]]]][YiJing.DIZIWH[zi]] == 1) {
          bool2 = true;
          break;
        }
      }
    }
    if(bool1 || bool2) {
      str.append("\n");
     str.append(getRepeats(" ", YiJing.INTER[0]));
     str.append("应期：");
     str.append("该爻临日辰，或日辰临动爻来生合世爻用神，即以本日断之。");
    }

    //2、用神旺相不动，待日辰冲动之时应验。
    if(!isDongOrJing(str,zi,yuezi,rizi,changes,dizi,diziBian)) {
      if(isHasQuanForSilen(str,zi,yuezi,rigan,rizi,changes,dizi,diziBian,false)) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("应期：");
        str.append("该爻旺相不动，待日辰冲动之时应验。");

      }
    }
    //5、用爻休囚，必在生旺之期方能成事。
    if(!isHasQuanForActive(str,zi,yuezi,rizi,changes,dizi,diziBian,local,rigan,false) ||
       !isHasQuanForSilen(str,zi,yuezi,rigan,rizi,changes,dizi,diziBian,false)){
      str.append("\n");
      str.append(getRepeats(" ", YiJing.INTER[0]));
      str.append("应期：");
      str.append("该爻休囚，必在生旺之期方能成事。");
    }
    //6、用爻受克，当以制克之日断之。
    if(isDongAndBianKe(zi,changes,dizi,diziBian,false)) {
      str.append("\n");
      str.append(getRepeats(" ", YiJing.INTER[0]));
      str.append("应期：");
      str.append("该爻受克，当以制克之日断之。");
    }
  }

  /**
   * 输出年月日时
   * @param shijian
   */
  public void getNYRDOut(StringBuffer str, int[] shijian) {
    //SiZhu.yinli = null;
    str.append("\n    干支：");
    str.append(YiJing.TIANGANNAME[shijian[1]]);
    str.append(YiJing.DIZINAME[shijian[2]]);
    str.append("年");
    str.append("  ");
    str.append(YiJing.TIANGANNAME[shijian[3]]);
    str.append(YiJing.DIZINAME[shijian[4]]);
    str.append("月（破"+YiJing.DIZINAME[YiJing.DZCHONG2[shijian[4]]]+"）");
    str.append("  ");
    str.append(YiJing.TIANGANNAME[shijian[5]]);
    str.append(YiJing.DIZINAME[shijian[6]]);
    DaoPublic daoPub = new DaoPublic();
    str.append("日（空"+daoPub.getXunKongOut(shijian[5],shijian[6])[0]+
               daoPub.getXunKongOut(shijian[5],shijian[6])[1]+"）");
    str.append("  ");
    str.append(YiJing.TIANGANNAME[shijian[7]]);
    str.append(YiJing.DIZINAME[shijian[8]]);
    str.append("时");
    str.append("\n\n");
  }

  /**
   * 输出用神、原神、仇神、忌神等的主要信息:

   一。 先论用神
   1. 用神概况
      1.1 有几个用神
      1.2 分属阴阳
      1.3 位于内或外或伏
      1.4 动静情况
   2. 月建日建
      2.1 月建之旺相死休囚
          a) 月建，就是旺相的意思。
          b) 五行在四季中的旺相休囚，其卦八卦宫五行、地支五行均断。
      2.2 日建之长生旺墓绝
          a) “随鬼入墓”是指自占官鬼持世，代占官鬼临于用爻，又逢休囚无力，入于墓中，便为凶危之象。
          b) 入日墓。世爻或用爻六亲为官鬼，恰逢墓于日辰。如果旺相或遇他爻生扶，或可有救；如休囚或遇克冲，则凶危无疑。
          c) 入动墓。是世爻或用爻的六亲为官鬼，且休囚无力，卦中动爻的地支恰好是此官鬼爻的墓库。
          d) 静爻、动爻、变爻入日墓，破墓的方式是冲墓爻而不能冲日。
          e) 静爻入动爻之墓，合墓、冲墓、冲爻都可以解墓。
          f) 动爻入变爻之墓解墓方式有三种：一是冲爻；二是冲墓；三是合墓。入墓之象有入医院、牢狱、洞穴、库房、容器之含义。
          d) 动而化墓。世爻或用爻为动爻，而变爻之地支恰好是动爻之墓。
      2.3 旬空
          a) 凡遇空亡均有不实、落空。虚伪、虚假等含义。如测婚，世空，已有悔；应空，对方不实；世应俱空，双方均不实，此婚难成。
          b) 凡所求之事，遇空难求。凡将避、将舍之事，遇空则吉。
   3. 日月静动变爻生克权、合化权分析
      先论生克权、再论合化权。有生克权才会有合化权，没有生克权也就谈不上合化权。有了生克权后才论合化。
      3.1 日、月分析
          a) 日月同功同权，对卦中任何爻永远有生克合化权。
      3.2 静爻生克权
          生克权是能生克其他爻如用神、仇神等，只是不能参与合化。
          a) 静爻与静爻相冲，在有动爻或暗动爻的卦中，一般不论，而在静卦中还是相冲的，但静爻相冲，双方谁也冲不败谁。不论。
          b) 月建冲体囚静爻为彻底破，永无生克权。
          c) 月建冲旺相静爻，在月内为月破，无生克权。 但逢日合、值日及出月便有生克权。
          d) 日建、动爻、变爻冲月支之休囚静爻。有动变、日月之爻克、泄、耗爻时则为日破。无生克权。
          e) 休囚之爻逢日辰连冲带克为日破。无生克权。
          f) 静爻在日、月两方都休囚无气，便无生克权。
          g) 静爻在日、月一方受克，一方休囚也无生克权。
          h) 静爻在日、月双方都受克，更无生克权。
          i) 静爻在日、月一方受生一方受克，有无生克权关键看卦中动爻的向背。如果是连冲带克，则无生克权。
          j) 静爻旬空，若本身旺相有气，可发挥一半的力量，待出空才能正常发挥生克职能。
          k) 伏神逢空：若伏神有气为假空，出空逢值之时有用。若无气，不但逢值无用，冲空、冲飞都解不了空，那种无气的伏神是引拔不出来的。

      3.3 如何成为暗动爻
          a) 月建冲值日之静爻，不但不论破反而为暗动。
          b) 日建冲月支之旺相静爻。为暗动，静爻不但不受伤，反而因冲而动，按动爻看。
          c) 日建冲月支之休囚静爻。有动变之爻生之为暗动。
          d) 变爻冲主卦中的旺相静爻。为暗动，静爻不但不受伤，反而因冲而动，按动爻看。
          e) 变爻冲主卦中的休囚静爻。有动变、日月之爻生之为暗动。
          f) 动爻冲主卦中的旺相静爻。为暗动，静爻不但不受伤，反而因冲而动，按动爻看。
          g) 动爻冲主卦中的的休囚静爻。有动变、日月之爻生之为暗动。
          h) 凡静爻逢日、月台，在静爻旺相时，谓之合起，相当于动爻。
      3.4 动爻/暗动爻之生克合化权
          a) 原则3。日、月建冲动爻。月冲，日合可解。日冲逢进行时的日合住可解。
          b) 动爻与动爻相冲，旺(指是否有变爻、日、月相助)者胜，衰者败，旺者有生克权，衰者冲而散无生克权。双方都休囚，为两败俱伤，都无生克权。
          c) 月建冲动爻无论旺衰都为月破。但旺相之动爻或虽休囚但不受克之动爻逢月冲，在动爻值日或逢日合之日，也有生克权，出月有生克权。日冲逢进行时的日合住可解。
          d) 月、日建冲休囚动爻谓冲散，此动爻永无生克权，不可解。
          e) 本位动爻与变爻。休囚时，被变爻冲而冲散，失去生克权。旺相时，如果变爻对动爻只冲不克时，本位动爻减力，但仍有生克权。 变爻对本位动爻连冲带克时，不论动爻旺衰，动爻无生克权。只有本位动爻临日、月建时，本位动爻才有生克权。
          f) 动爻在日、月两处只是休囚，不受克，但化退、化泄、化绝、生克权减小；化回头克，无生克权；若化回头生、化进神，有生克权。
          g) 动爻在日、月一方受生，一方受克，有生克权。
          h) 动爻在日、月两处都得生扶，更有生克权。
          i) 动爻在日、月双方均受冲克，无生克权。
          j) 凡动爻逢日或月合，无论旺衰，均论绊住，暂时失去生克权。
          k) 用神临月冲为破，倒霉之象，月破为枯根朽木，逢生不起，逢伤更伤。
          l) 动爻旬空：冲空、出空均能正常发挥生克职能。
      3.5 变爻生克合化权
          a) 原则1。卦中变爻可生克变出自己的爻，当对动爻没有生、克、合、冲时，会对主卦中其它旁爻产生生、克、合、冲作用。
          b) 原则2。变爻与变爻相冲。变爻与变爻相冲一般不看，只看变爻对主卦之爻的作用。
          c) 原则3。日、月建冲变爻。月冲，日合可解。日冲逢进行时的日合住可解。
          d) 月建冲变爻无论旺衰都为月破。但旺相之变爻或虽休囚但不受克之变爻逢月冲，在变爻值日或逢日合之日，也有生克权，出月有生克权。日冲逢进行时的日合住可解。
          e) 变爻旬空：动爻本身不空，但变爻旬空，动爻也不能正常发挥生克权，待变爻出空时才有生克权。冲动爻、冲旬空的变爻都不起作用。
   4. 三合六合
      若合化成功，以化神论五行。
      4.1  三合
          a) 实合局。有卦中动爻、变爻、暗动爻、日、月成化，则得其化神。化神五行同日或月之五行 && 日和月五行不克化神五行则成功，否则合而不化，论绊住。
          b) 待合局。a中有一爻不动或旬空，待填实值日之时或动时成局，此为“虚一待用”，应期就在“待用”之爻上。
      3.2 六合
          a) 合化成功。必须是卦中的两个爻都动，或卦中动爻与本位变爻之合。&& 必须化神是日旺月建，且日或月中之一为化神，且日、月任何一方不能是克化神之五行。
          b) 凡日或月与卦中之爻相合，都为合而不化。 但日、月与静爻为克合时，无论爻旺衰都不以合起论。
   5. 生拱扶克害刑冲
          a) 日建与月建相冲，六爻断卦中，年、月、日、时属同功同权，因此不看日建与月建相合还是相冲。
          b) 月、日可以生克卦中之爻，卦中之爻不能生克月、 日。

          d) 卦中动爻包括暗动之爻能生、克、合、冲主卦中的静爻，也能生、克、合、冲同层次的动爻。
          e) 卦中旺相之静爻能生克衰弱之动或静爻，而衰弱之爻不能生克旺相之爻。
          f) 卦中旺相之动爻能生克衰弱之动或静爻，而衰弱之爻不能生克旺相之爻。
          g) 拱
          h) 扶
          i) 害
          j) 刑
          k) 冲
   6.
   二。 再论原神
        原神是生用神的爻

   三。 再论忌神
        忌神是克制用神的爻

   四。 再论仇神
        仇神是生忌神的爻，

   * @param str
   * @param ysLiuqin 哪个六亲为用，从前台传来
   * @param dizi
   * @param liuqin
   * @param diziBian
   * @param diziGong
   */
  public void getLiuYaoZhanOut(StringBuffer str,int up, int down, int[] shijian,
                             int upGong, int downGong, int shiyao, int yingyao,
                             int ysLiuqin,int[] dizi, int[] liuqin, int[] changes,
                             int[] diziBian, int[] diziGong, int[] liuqinGong,
                             int whichGongZ, int whichGongB) {
  int yuezi = shijian[4];
  int rizi = shijian[6];
  int rigan = shijian[5];
  boolean isFu = false; //判断是否是伏神用神
  int[] ysLocal = new int[2]; //用神位置数组
  int _ysLiuqin = ysLiuqin;
  int ysDizi = 0;
  int yuanshendz = 0;
  int jishendz = 0;
  int chushendz = 0;

  //如果以世爻为用神，先要转化
  if (ysLiuqin == 0) {
    ysLiuqin = liuqin[shiyao];
  }
  //得到用神地支
  for (int i = 1; i <= 6; i++) {
    if (liuqin[i] == ysLiuqin) {
      ysDizi = dizi[i];
      break;
    }
  }
  //原神
  int yuanshen = 0;
  for (int i = 1; i <= 6; i++) {
    if (YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[i]]][YiJing.DIZIWH[ysDizi]] == 1) {
      yuanshen = liuqin[i];
      yuanshendz = dizi[i];
      break;
    }
  }

  //忌神
  int jishen = 0;
  for (int i = 1; i <= 6; i++) {
    if (YiJing.WXDANKE[YiJing.DIZIWH[dizi[i]]][YiJing.DIZIWH[ysDizi]] == 1) {
      jishen = liuqin[i];
      jishendz = dizi[i];
      break;
    }
  }

  //仇神
  int chushen = 0;
  for (int i = 1; i <= 6; i++) {
    if (YiJing.WXDANKE[YiJing.DIZIWH[ysDizi]][YiJing.DIZIWH[dizi[i]]] == 1) {
      chushen = liuqin[i];
      chushendz = dizi[i];
      break;
    }
  }

  //六爻占断
  //主卦与变卦宫的旺衰情况
  str.append(YiJing.LIUYAOSELF);
  str.append(getRepeats(" ", YiJing.INTER[0]));
  str.append("主卦" + YiJing.JINGGUANAME[whichGongZ] + "宫在月建处" +
             YiJing.WXSQXNAME[YiJing.WXSQX[yuezi][YiJing.BAGONGGUAWH[
             whichGongZ]]] + "地，");
  str.append("变卦" + YiJing.JINGGUANAME[whichGongB] + "宫在月建处" +
             YiJing.WXSQXNAME[YiJing.WXSQX[yuezi][YiJing.BAGONGGUAWH[
             whichGongB]]] + "地。");
  int[] silents = getSilents(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  int[] andongs = getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  int[] actives = getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  int[] bians   = getBians(str,rigan,rizi,yuezi,changes,dizi,diziBian,true);
  getWangOrNoOut(str,yuezi,rigan,rizi,dizi,diziBian,changes,silents,andongs,actives,bians);

  {
    //用神占断
    str.append(YiJing.YONGSHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));
    str.append(YiJing.YONGSHEN[_ysLiuqin]);

    //用神的位置及是否该用神出现在伏神上
    isFu = getShenLocal(ysLiuqin, liuqin, liuqinGong, ysLocal);

    //用神状况
    getShenState(str, liuqin, ysLiuqin, ysLocal, isFu, up, down, upGong,
                 downGong, "用神");

    //用神是否入日或动或动而入变墓
    isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                       liuqin, dizi, diziBian, isFu, rizi, yuezi, "用神", true);

    //生拱扶克害刑冲论五行旺衰
    for(int i=0; i<ysLocal.length; i++) {
      if(ysLocal[i] == 0) break;
      getSGFKHXCout(dizi[ysLocal[i]], silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }

    //应期
    for(int i=0; i<ysLocal.length; i++) {
      if(ysLocal[i] == 0) break;
      getYingQiOut(str, dizi[ysLocal[i]], ysLocal[i], yuezi, rigan, rizi, changes, dizi,
                   diziBian, liuqin,
                   diziGong, liuqinGong, isFu);
    }
  }
  {
    //如果世爻六亲即用神或原或仇或忌神六亲，不再判断世爻也
    if (ysLiuqin != liuqin[shiyao] && chushen != liuqin[shiyao] &&
        yuanshen != liuqin[shiyao] && jishen != liuqin[shiyao]) {
      //世爻占断
      str.append(YiJing.SHIYAOSELF);
      str.append(getRepeats(" ", YiJing.INTER[0]));
      str.append(YiJing.YONGSHEN[liuqin[shiyao]]);

      //世爻位置及是否该用神出现在伏神上
      isFu = getShenLocal(liuqin[shiyao], liuqin, liuqinGong, ysLocal);

      //世爻状况
      getShenState(str, liuqin, liuqin[shiyao], ysLocal, isFu, up, down,
                   upGong,
                   downGong, "世爻");

      //世爻是否入日或动墓
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "世爻",true);

      //生拱扶克害刑冲论五行旺衰
    getSGFKHXCout(dizi[shiyao] ,silents,andongs,actives,bians,rizi,yuezi,str,rigan, changes, dizi,diziBian);
    }
  }
  {
    //原神占断
    str.append(YiJing.YUANSHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));

    if(yuanshen == 0) {
      str.append("原神没有上卦。");
    }else {
      str.append(YiJing.YONGSHEN[yuanshen]);
      //原神的位置及是否该用神出现在伏神上
      isFu = getShenLocal(yuanshen, liuqin, liuqinGong, ysLocal);

      //原神状况
      getShenState(str, liuqin, yuanshen, ysLocal, isFu, up, down, upGong,
                   downGong, "原神");

      //原神是否入日或动墓
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "原神",true);

      //生拱扶克害刑冲论五行旺衰
      getSGFKHXCout(yuanshendz, silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }
  }
  {
    //忌神占断
    str.append(YiJing.JISHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));

    if(jishen == 0) {
      str.append("忌神没有上卦。");
    }else {
      str.append(YiJing.YONGSHEN[jishen]);
      //忌神的位置及是否该用神出现在伏神上
      isFu = getShenLocal(jishen, liuqin, liuqinGong, ysLocal);

      //忌神状况
      getShenState(str, liuqin, jishen, ysLocal, isFu, up, down, upGong,
                   downGong, "忌神");

      //忌神是否入日或动墓
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "忌神",true);

      //生拱扶克害刑冲论五行旺衰
      getSGFKHXCout(jishendz, silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }
  }
  {
    //仇神占断
    str.append(YiJing.CHUSHENSELF);
    str.append(getRepeats(" ", YiJing.INTER[0]));

    if(chushen == 0) {
      str.append("仇神没有上卦。");
    }else{
      str.append(YiJing.YONGSHEN[chushen]);
      //仇神的位置及是否该用神出现在伏神上
      isFu = getShenLocal(chushen, liuqin, liuqinGong, ysLocal);

      //仇神状况
      getShenState(str, liuqin, chushen, ysLocal, isFu, up, down, upGong,
                   downGong, "仇神");

      //仇神是否入日或动墓
      isRuMu(str, ysLocal, changes, rigan, liuqinGong, diziGong,
                         liuqin, dizi, diziBian, isFu, rizi, yuezi, "仇神",true);

      //生拱扶克害刑冲论五行旺衰
      getSGFKHXCout(chushendz, silents, andongs, actives, bians, rizi, yuezi,
                    str, rigan, changes, dizi, diziBian);
    }
  }


}

  /**
   * 取用神或原神或忌或仇神的状况，如几个、在哪卦、阴阳等
   * @param str
   * @param liuqin
   * @param ysLiuqin
   * @param ysNum
   * @param ysLocal
   * @param up
   * @param down
   * @param upGong
   * @param downGong
   * @param ziName
   */
  public void getShenState(
      StringBuffer str, int[] liuqin, int ysLiuqin, int[] ysLocal,
      boolean isFu,
      int up, int down, int upGong, int downGong, String ziName) {
    int num = 0;
    if (ysLocal[1] != 0)
      num = 2;
    else
      num = 1;

    str.append("\n");
    str.append(getRepeats(" ", YiJing.INTER[0]));
    if (isFu) {
      str.append(ziName + "没有上卦，取伏神。有" + num + "个" + ziName + "；");
      if (num > 1) {
        str.append("其中一个为" +
                   YiJing.YAONAME3[getYinOrYang(upGong, downGong, ysLocal[0])] +
                   "，");
        str.append("另一个为" +
                   YiJing.YAONAME3[getYinOrYang(upGong, downGong, ysLocal[1])] +
                   "；");
      }
      else {
        str.append("为" +
                   YiJing.YAONAME3[getYinOrYang(upGong, downGong, ysLocal[0])] +
                   "；");
      }
      if (ysLocal[0] > 3 && ysLocal[1] > 3) {
        str.append("均位于外卦；");
      }
      else if (ysLocal[0] <= 3 && ysLocal[1] <= 3 && ysLocal[1] > 0) {
        str.append("均位于内卦；");
      }
      else if (ysLocal[0] <= 3 && ysLocal[1] == 0) {
        str.append("位于内卦；");
      }
      else if (ysLocal[0] > 3 && ysLocal[1] == 0) {
        str.append("位于外卦；");
      }
      else {
        str.append("其中一个位于外卦，另一个位于内卦；");
      }
      str.append("详细占断详伏飞占。");
      return;
    }
    else if (num > 1) {
      str.append("有" + num + "个" + ziName + "；");
      str.append("其中一个为" + YiJing.YAONAME3[getYinOrYang(up, down, ysLocal[0])] +
                 "，");
      str.append("另一个为" + YiJing.YAONAME3[getYinOrYang(up, down, ysLocal[1])] +
                 "；");
      if (ysLocal[0] > 3 && ysLocal[1] > 3) {
        str.append("均位于外卦；");
      }
      else if (ysLocal[0] <= 3 && ysLocal[1] <= 3) {
        str.append("均位于内卦；");
      }
      else {
        str.append("其中一个位于外卦，另一个位于内卦；");
      }
    }
    else {
      str.append("有" + num + "个" + ziName + "；");
      str.append("为" + YiJing.YAONAME3[getYinOrYang(up, down, ysLocal[0])] +
                 "；");
      if (ysLocal[0] > 3) {
        str.append("位于外卦；");
      }
      else {
        str.append("位于内卦；");
      }
    }
  }


  /**
   * 该地支是否处墓地，主要是入日墓或入动墓或动而入变墓
   * @param str
   * @param ysLocal 用神位置数组或地支数组
   * @param changes 动爻数组
   * @param liuqinGong 经卦六亲
   * @param diziGong 经卦地支
   * @param liuqin 主卦六亲
   * @param dizi 主卦地支
   * @param diziBian 变卦地支
   * @param isFu 是否是伏爻
   * @param rizi 日支
   * @param yuezi 月支
   * @param ziName 支名，如用神或仇神或世爻
   */
  public boolean isRuMu(
      StringBuffer str,int[] ysLocal, int[] changes,int rigan,
      int[] liuqinGong,int[] diziGong,
      int[] liuqin, int[] dizi, int[] diziBian,
      boolean isFu,int rizi, int yuezi, String ziName,boolean bl
                     ) {
    boolean bYJWang = false;
    boolean bRJWang = false;
    boolean bRJZhi = false;

    int num = 0;
    if(ysLocal[1] != 0)
      num = 2;
    else
      num = 1;

    int _liuqin, _dizi;
    for (int i = 0; i < num; i++) {
      if (isFu) {
        _liuqin = liuqinGong[ysLocal[i]]; //此用神宫卦的六亲
        _dizi = diziGong[ysLocal[i]];     //此用神宫卦的地支
      }
      else {
        _liuqin = liuqin[ysLocal[i]];
        _dizi = dizi[ysLocal[i]];
      }
      //月建之旺相死休囚
      if(bl) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append(ziName + YiJing.LIUQINNAME[_liuqin] + YiJing.DIZINAME[_dizi] +
                   "爻：");
        str.append("在月建处" + YiJing.WXSQXNAME[howWxsqx(_dizi, yuezi)] + "地；");
      }
      bYJWang = isWxsqx(_dizi, yuezi);

      //日建旺衰绝墓
      bRJWang = isSwmj(_dizi, rizi);
      bRJZhi = _dizi == rizi;
      if(bl) {
        str.append("在日建处" + YiJing.SWMJNAME[howSwmj(_dizi, rizi)] + "地；");
      }

      //用神入日墓
      boolean _b = false;
      String _temp = "";
      if (liuqin[ysLocal[i]] == YiJing.GUANGUI)
        _temp = "带鬼";
      if (howSwmj(_dizi, rizi) == YiJing.MURJVALUE) {
        if(bl) {
          str.append("\n");
          str.append(getRepeats(" ", YiJing.INTER[0]));
          str.append(ziName + _temp + "入日墓，");
        }
        if(isZiWang(str,_dizi,yuezi,rigan,rizi,changes,dizi,diziBian)) {
          if(bl) {
            str.append("因其旺相遇助，有救！");
          }
        }else{
          if(bl) {
            str.append("因其休囚无力，大凶！");
          }
        }
        if(bl) {
          str.append("入日墓，破墓的方式是冲墓爻而不能冲日。");
        }
        return true;
      }
      //入动墓
      for (int i1 = 0; i1 < changes.length; i1++) {
        //如果某爻动入动爻，不能是自己啊
        if(ysLocal[i] == changes[i1])
          continue;
        if (howSwmj(_dizi, dizi[changes[i1]]) == YiJing.MURJVALUE) {
          if(bl) {
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
            str.append(ziName + _temp + "入动墓，");
          }
          if(isZiWang(str,_dizi,yuezi,rigan,rizi,changes,dizi,diziBian)) {
            if(bl) {
              str.append("因其旺相遇助，有救！");
            }
          }else{
            if(bl) {
              str.append("因其休囚无力，大凶！");
            }
          }
          for (int i2 = 0; i2 < changes.length; i2++) {
            //如果用神为动爻
            if (changes[i2] == ysLocal[i]) {
              _b = true;
              break;
            }
          }
          if (_b)
            if(bl) {
              str.append(ziName + "是动爻入动墓，其灾已解。");
            }
          else
            if(bl) {
              str.append(ziName + "是静爻入动爻之墓，合墓、冲墓、冲爻都可以解墓。");
            }
          return true;
        }
      }

      if(isDongRuBianMuOut(str,changes,liuqin,dizi,diziBian,_dizi,bl))
        return true;
    }
    return false;
  }

  /**
   * 是否有动而化墓
   * @param str
   * @param changes
   * @param liuqin
   * @param dizi
   * @param diziBian
   */
  public boolean isDongRuBianMuOut(
      StringBuffer str,int[] changes,
      int[] liuqin, int[] dizi, int[] diziBian, int zi, boolean bl) {
    String _temp = "";
    for (int i = 0; i < changes.length; i++) {
      if(dizi[changes[i]] != zi)
        continue;
      if (howSwmj(dizi[changes[i]], diziBian[changes[i]]) == YiJing.MURJVALUE) {
          if(bl) {
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
          }
          if (liuqin[i] == YiJing.GUANGUI)
            _temp = "带鬼";

          if(bl) {
            str.append("动爻" + YiJing.DIZINAME[dizi[changes[i]]] + _temp + "动而化墓，");
            str.append("解墓方式有三种：一是冲爻；二是冲墓；三是合墓。入墓之象有入医院、牢狱、洞穴、库房、容器之含义。");
          }
          return true;
        }
      }
      return false;
    }

  /**
   * 输出六神的所有信息
   * 1. 哪个神发动
   * 2. 持世之神衰旺休囚情况
   * 3. 克世之神情况
   * 4. 六神临何六亲
   */
  public void getLiuShenOut(StringBuffer str, int[] liushen, int[] changes,
                            int[] dizi, int[] liuqin, int shiyao, int yuez) {
    str.append(YiJing.LIUSHENSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    //哪个神发动
    for(int i=0; i<changes.length; i++) {
      if(changes[i]==0) continue;
      str.append(YiJing.LIUSHENFADONG[liushen[changes[i]]]);
      str.append("\n");
      str.append(getRepeats(" ",YiJing.INTER[0]));
    }
    //持世之神衰旺休囚情况
    if(this.isWxsqx(dizi[shiyao],yuez)) {
      str.append(YiJing.LIUSHENCISHIGOOD[liushen[shiyao]]);
    }else{
      str.append(YiJing.LIUSHENCISHIBAD[liushen[shiyao]]);
    }
    str.append("\n");
    str.append(getRepeats(" ",YiJing.INTER[0]));
    //克世之神情况
    for(int i=1; i<=6; i++) {
      if(YiJing.WXDANKE[YiJing.DIZIWH[dizi[i]]][YiJing.DIZIWH[dizi[shiyao]]] == 1) {
        str.append(YiJing.LIUSHENKESHIYONG[liushen[i]]);
        str.append("\n");
        str.append(getRepeats(" ",YiJing.INTER[0]));
      }
    }
    //六神临何六亲
    for(int i=1; i<=6; i++) {
      str.append(YiJing.LIUSHENQIN[liushen[i]][liuqin[i]]);
      str.append("\n");
      str.append(getRepeats(" ",YiJing.INTER[0]));
    }
  }

  /**
   * 输出是否是十二辟卦之一
   * @param str
   * @param up
   * @param down
   */
  public void getTwenteenOut(StringBuffer str, int up, int down) {
    str.append(YiJing.HOUGUASELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(YiJing.HOUGUAZHAN[YiJing.HOUGUA[up][down]]);
  }

  /**
   * 得到变卦看属京房十六变卦否
   * @param str
   * @param whichGongZhu
   * @param upBian
   * @param downBian
   */
  public void getJFSixteenOut(StringBuffer str,int whichGongZhu,
                           int upBian, int downBian, int[] changes) {
    str.append(YiJing.JINGFANGGUASELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    if(changes.length == 0 || changes[0] == 0) {
      str.append(YiJing.WUBIANGUASELF);
      return;
    }

    int i = YiJing.SIXTEENGUA[whichGongZhu][upBian][downBian];
    if(i != 0) {
      str.append(YiJing.JINGFANGZHAN[i]);
    }else{
      str.append(YiJing.JINGFANGWUSELF);
    }
  }

  /**
   * 是否是六合卦或六冲卦
   * @param str
   * @param up
   * @param down
   */
  public void getLiuHeChongOut(StringBuffer str, int up, int down) {
    boolean bool = false;
    str.append(YiJing.CHSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));

    if(YiJing.LIUCHONGGUA[up][down] != 0) {
      bool = true;
      str.append(YiJing.LCSELF);
    }
    if(YiJing.LIUHEGUA[up][down] != 0) {
      bool = true;
      str.append(YiJing.LHSELF);
    }
    if(bool)
      str.append(YiJing.CHZHANSELF);
    else
      str.append(YiJing.CHWUSELF);

  }

  /**
   * 输出是否是进退神的信息
   * @param str
   * @param changes
   * @param diZi
   * @param diZiBian
   */
  public void getJinTuiShenOut(StringBuffer str, int[] changes, int[] diZi, int[] diZiBian) {
    boolean bool = false;
    str.append(YiJing.JTSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    int i1=0,i2=0; //为防止重复

    for(int i=0; i<changes.length; i++) {
      if(YiJing.JINSHEN[diZi[changes[i]]][diZiBian[changes[i]]] != 0) {
        if(i1 == 0) {
          bool = true;
          str.append(YiJing.JSSELF);
          i1++;
        }
      }
      if(YiJing.TUISHEN[diZi[changes[i]]][diZiBian[changes[i]]] != 0) {
        if(i2 == 0) {
          bool = true;
          str.append(YiJing.TSSELF);
          i2++;
        }
      }
    }
    if(bool)
      str.append(YiJing.JTZHANSELF);
    else
      str.append(YiJing.JTWUSELF);
  }

  /**
   * 输出是否有伏呤,伏吟指卦变而爻的地支不变。
   * @param str
   * @param zhu 主卦地支
   * @param bian
   * @param whichZ 所属何宫
   */
  public void getFuLingOut(StringBuffer str, int[] z, int[] b, int upZ, int downZ, int upB, int downB) {
    boolean bool = false;
    str.append(YiJing.FULSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    if(downZ != downB) {
      if(z[1] == b[1] && z[2] == b[2] && z[3] == b[3]) {
        bool = true;
        str.append(YiJing.GUAFULWSELF);
      }
    }

    if(upZ != upB) {
      if(z[4] == b[4] && z[5] == b[5] && z[6] == b[6]) {
        bool = true;
        str.append(YiJing.GUAFULNSELF);
      }
    }

    if(bool) {
      str.append(YiJing.FULZHANSELF);
    }else{
      str.append(YiJing.WUFULSELF);
    }

  }

  /**
   * 输出是否是否有卦或爻反呤
   * 卦反吟指卦所属宫五行相克，而艮坤是特例
   * 爻反吟指卦中的某个爻动变冲克。
   * @param str
   * @param <any>
   */
  public void getFanLingOut(StringBuffer str, boolean isGuaFanLing, boolean isYaoFanLing) {
    str.append(YiJing.FLSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    if(isGuaFanLing)
      str.append(YiJing.GUAFLSELF);
    if(isYaoFanLing)
      str.append(YiJing.YAOFLSELF);
    if(isGuaFanLing || isYaoFanLing)
      str.append(YiJing.FLZHANSELF);
    else
      str.append(YiJing.WUFLSELF);
  }

  /**
   * 得到月卦身
   * @return
   */
  public void getYeuGuaShenOut(StringBuffer str, int yueGuaShen, boolean isHasGuaShen) {
    str.append(YiJing.YUEGUASHENSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(YiJing.DIZINAME[yueGuaShen]);
    if(isHasGuaShen)
      str.append("。 "+YiJing.YUEGUASHENNOTE[1]);
    else
      str.append("。 "+YiJing.YUEGUASHENNOTE[0]);
  }

  /**
   * 得到世身
   * @return
   */
  public void getShiShenOut(StringBuffer str, int shiShen) {
    str.append(YiJing.SHISHENSELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(YiJing.SHISHEN2NAME[shiShen]);
  }

  /**
   * 得到卦词与爻词
   * @return
   */
  public void getGuaYaoCiOut(StringBuffer str, String[] guaCi, int[] guaXiang,
                             int[] changes, int up, int down ) {
    str.append(YiJing.GUACISELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    str.append(guaCi[0]);
    str.append(YiJing.YAOCISELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    //如果是六爻皆动，除乾坤两卦外均取彖词。
    String temp_ = null;
    if(changes.length == 6 && up != 1 && up != 8 && down != 1 && down != 8)
      temp_ = YiJing.LYDONGSELF;
    else if(changes.length == 0 || changes[0] == 0)
      temp_ = YiJing.LYJINGSELF;
    else {
      int which = getDongYaoCi(guaXiang, changes);
      if(which>6) temp_="六爻全动，取卦辞";
      else temp_ = guaCi[which];
    }
    str.append(temp_);
    str.append("\n");
  }

  /**
   * 得到伏飞
   * @param z 为主宫地支
   * @param jing 为八宫卦经卦支
   * @return
   */
  public void getFuFeiOut(StringBuffer str, int[] ff,int[] z, int[] jing) {
    boolean bool = false;
    int zwh = 0;
    int bwh = 0;
    int i1=0,i2=0,i3=0,i4=0,i5=0; //防止两个伏生飞时出现重复

    str.append(YiJing.FUFEISELF);
    str.append(getRepeats(" ",YiJing.INTER[0]));
    for(int i=0; i<ff.length; i++) {
      if(ff[i] == 0)
        continue;
      zwh = YiJing.DIZIWH[z[ff[i]]];
      bwh = YiJing.DIZIWH[jing[ff[i]]];
      if(YiJing.WXDANSHENG[zwh][bwh] != 0) {
        if(i1 == 0) {
          i1++;
          bool = true;
          str.append(YiJing.FEISFUSELF);
        }
      }
      if(YiJing.WXDANKE[zwh][bwh] != 0) {
        if(i2 == 0) {
          i2++;
          bool = true;
          str.append(YiJing.FEIKFUSELF);
        }
      }
      if(YiJing.WXDANSHENG[bwh][zwh] != 0) {
        if(i3 == 0) {
          i3++;
          bool = true;
          str.append(YiJing.FUSFEISELF);
        }
      }
      if(YiJing.WXDANKE[bwh][zwh] != 0) {
        if(i4 == 0) {
          i4++;
          bool = true;
          str.append(YiJing.FUKFEISELF);
        }
      }
      if(YiJing.WXBIHE[bwh][zwh] != 0) {
        if(i5 == 0) {
          i5++;
          bool = true;
          str.append(YiJing.FEIFUHESELF);
        }
      }
    }
    if(!bool)
      str.append(YiJing.FEIFUWUSELF);
  }


  /**
   * 得到卦象
   * @param up 上卦先天八卦数
   * @param down 下卦先天八卦数
   * @param changes 动爻数组
   * @param upBian 变卦上卦先天八卦数
   * @param downBian 变卦下卦先天八卦数
   * @param upHu 互卦上卦先天八卦数
   * @param downHu 互卦下卦先天八卦数
   * @param whichGongZhu 主卦何宫
   * @param whichGongBian 变卦何宫
   * @param whichGongHu 互卦何宫
   * @param shiYao 主卦世爻位置
   * @param yingYao 主卦应爻位置
   * @param shiYaoBian 变卦世爻位置
   * @param yingYaoBian 变卦应爻位置
   * @param shiYaoHu 互卦世爻位置
   * @param yingYaoHu 互卦应爻位置
   * @param diZi 主卦地支初爻至六爻
   * @param diZiBian 变卦地支初爻至六爻
   * @param diZiHu 互卦地支初爻至六爻
   * @param diZiGong 主卦所属八宫卦之经卦的地支初爻至六爻
   * @param guaXiang 主卦卦象初爻至六爻的爻象1为阳爻2为阴爻
   * @param guaXiangBian 变卦卦象
   * @param guaXiangHu 互卦卦象
   * @param liuQin 主卦六亲
   * @param liuQinBian 变卦六亲
   * @param liuQinHu 互卦六亲
   * @param liuQinGong 八宫卦经卦六亲
   * @param liuShen 六神
   * @param yueGuaShen 月卦身位置用地支表示
   * @param isHasGuaShen 是否有月卦身
   * @param shiShen 世身
   * @param ff 飞伏爻的位置
   * @param guaCi 六十四卦卦词
   * @return
   */
  public String getGuaXiangOut(
      StringBuffer str,
      int up, int down, int[] changes,
      int upBian, int downBian,
      int upHu, int downHu,
      int whichGongZhu, int whichGongBian, int whichGongHu,
      int shiYao, int yingYao,
      int shiYaoBian, int yingYaoBian,
      int shiYaoHu, int yingYaoHu,
      int[] diZi, int[] diZiBian, int[] diZiHu, int[] diZiGong,
      int[] guaXiang, int[] guaXiangBian, int[] guaXiangHu,
      int[] liuQin, int[] liuQinBian, int[] liuQinHu, int[] liuQinGong,
      int[] liuShen,
      int[] ff
      ) {
    //为格式而设
    String zhuLen = null;   //主卦串
    String huLen = null;    //变卦串
    String bianLen = null;  //互卦串
    String dyLen = null;    //变爻串
    int totalLen1 = 0;  //主卦的中心点
    int totalLen2 = 0;  //变卦的中心点
    int totalLen3 = 0;  //互卦的中心点
    String temp_ = null; //临时变量

    /**
     * 1. 加入何宫八卦，先空2格，再加上X宫八卦，再空12加X宫互卦，12空格X宫变卦
     * 2. 爻象+动爻，有动爻的前加一空格，没有的有空格补齐
     * 3. 互卦，先空10格，再加卦象
     * 4. 变卦，先空10格，再加变卦
     * 5. 加入六亲 + 加入地支 + 加入五行 + 加入变爻 + 伏
     * 6. 加入周易卦辞，爻辞
     */
    str.append(getRepeats(" ",YiJing.INTER[0]));
    zhuLen = YiJing.JINGGUANAME[whichGongZhu]+YiJing.GONGZHUGUASELF+YiJing.GUA64NAME[up][down]+"》";
    str.append(zhuLen);
    str.append(getRepeats(" ",16));
    bianLen = YiJing.JINGGUANAME[whichGongBian]+YiJing.GONGBIANGUASELF+YiJing.GUA64NAME[upBian][downBian]+"》";
    str.append(bianLen);
    str.append(getRepeats(" ",16));
    huLen = YiJing.JINGGUANAME[whichGongHu]+YiJing.GONGHUGUASELF+YiJing.GUA64NAME[upHu][downHu]+"》";
    str.append(huLen);

    totalLen1 = YiJing.INTER[0]+zhuLen.getBytes().length/2;
    totalLen2 = YiJing.INTER[0]+zhuLen.getBytes().length+YiJing.INTER[1]+bianLen.getBytes().length/2;
    totalLen3 = YiJing.INTER[0]+zhuLen.getBytes().length+YiJing.INTER[1]+bianLen.getBytes().length+YiJing.INTER[1]+huLen.getBytes().length/2;

    str.append("\n");
    for(int i=6; i>0; i--) {
      //飞神/主卦六亲/阴阳符号/地支/五行/动爻/世爻、应爻/伏神
      zhuLen = "";
      if(ff[i] == i) {
        zhuLen += YiJing.FEIFUNAME[1];
      }else{
        //不要完全居中，故加几个空格
        zhuLen += getRepeats(" ",YiJing.FEIFUNAME[1].getBytes().length);
      }
      zhuLen += YiJing.LIUQINNAME[liuQin[i]];
      zhuLen += " "+YiJing.YAONAME[guaXiang[i]]+" ";
      zhuLen += YiJing.DIZINAME[diZi[i]];
      zhuLen += YiJing.WUXINGNAME[YiJing.DIZIWH[diZi[i]]];
      //zhuLen += YiJing.YAONAME2[guaXiang[i]];

      if(isDongYao(changes ,i)) {
        zhuLen += YiJing.YAODONG[guaXiang[i]];
      }else{
        zhuLen += getRepeats(" ",YiJing.YAODONG[1].getBytes().length);
      }

      if(shiYao == i) {
        zhuLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.SHI];
      }else if(yingYao == i) {
        zhuLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.YING];
      }else{
        zhuLen += getRepeats(" ",YiJing.SHIYINGNAME[YiJing.YING].getBytes().length+1);
      }
      str.append(makeCenter(zhuLen,totalLen1));

      //此处为格式调整
      huLen = "";
      if(ff[i] == i) {
        huLen = getRepeats(" ",1);
        huLen += YiJing.FEIFUNAME[2];
        huLen += YiJing.LIUQINNAME[liuQinGong[i]].substring(0,YiJing.LIUQINNAME[liuQinGong[i]].length()/2);
        huLen += YiJing.DIZINAME[diZiGong[i]];
        str.append(huLen+"  ");
      }else{
        str.append(getRepeats(" ", 4));
      }

      //变卦六亲/地支/五行/世应爻
      bianLen = YiJing.LIUQINNAME[liuQinBian[i]];
      bianLen += " "+YiJing.YAONAME[guaXiangBian[i]]+" ";
      bianLen += YiJing.DIZINAME[diZiBian[i]];
      bianLen += YiJing.WUXINGNAME[YiJing.DIZIWH[diZiBian[i]]];
      //bianLen += YiJing.YAONAME2[guaXiangBian[i]];
      if(shiYaoBian == i) {
        bianLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.SHI];
      }else if(yingYaoBian == i) {
        bianLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.YING];
      }else{
        bianLen += getRepeats(" ",YiJing.SHIYINGNAME[YiJing.YING].getBytes().length+1);
      }
      str.append(makeCenter(bianLen,totalLen2-totalLen1-zhuLen.getBytes().length/2-huLen.getBytes().length));

      //互卦六亲/地支/五行/世应爻
      huLen = YiJing.LIUQINNAME[liuQinHu[i]];
      huLen += " "+YiJing.YAONAME[guaXiangHu[i]]+" ";
      huLen += YiJing.DIZINAME[diZiHu[i]];
      huLen += YiJing.WUXINGNAME[YiJing.DIZIWH[diZiHu[i]]];
      //huLen += YiJing.YAONAME2[guaXiangHu[i]];
      if(shiYaoHu == i) {
        huLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.SHI];
      }else if(yingYaoHu == i) {
        huLen += getRepeats(" ",1)+YiJing.SHIYINGNAME[YiJing.YING];
      }else{
        huLen += getRepeats(" ",YiJing.SHIYINGNAME[YiJing.YING].getBytes().length+1);
      }
      str.append(makeCenter(huLen,totalLen3-totalLen2-bianLen.getBytes().length/2));

      //六神
      str.append(getRepeats(" ",YiJing.INTER[2]));
      str.append(YiJing.LIUSHENNAME[liuShen[i]]);

      str.append("\n");
    }

    return str.toString();
  }

  /**
   * 判断是否是卦反呤,即主卦与变卦是否相克，即是否两卦相克
   * 坤变艮，艮变坤，·坤艮两卦都属土，为什么属于反吟呢?
   * 《卜筮正宗》解：“艮卦坐于东北。艮右有丑，艮左有寅；坤卦坐于西南，坤右有未，坤左有申。二卦相对，有丑未相冲．，寅申相冲”。
   * @return
   */
  public boolean isGuaXIANGKE(int whichGongZhu, int whichGongBian) {
    if(whichGongZhu == YiJing.KUN && whichGongBian == YiJing.GEN)
      return true;

    if(whichGongZhu == YiJing.GEN && whichGongBian == YiJing.KUN)
      return true;

    int i1 = YiJing.BAGONGGUAWH[whichGongZhu];
    int i2 = YiJing.BAGONGGUAWH[whichGongBian];
    int i = YiJing.WXXIANGKE[i1][i2];

    return i==0 ? false : true;
  }

  /**
  * 得到周易卦辞与爻辞
  * @return
  */
 public String[] getGuaCiAndYaoCi(int up, int down) {
   return YiJing.GUA64CI[up][down];
 }

 /**
  * 两地支是否相克
  * @param i1
  * @param i2
  * @return
  */
 public boolean isDiZiKe(int i1, int i2){
   int i = YiJing.WXXIANGKE[YiJing.DIZIWH[i1]][YiJing.DIZIWH[i2]];
   return i == 0 ? false : true;
 }

 /**
  * 两地支是否相冲
  * @param i1
  * @param i2
  * @return
  */
 public boolean isDiZiChong(int i1, int i2){
   int i = YiJing.DZCHONG[i1][i2];
   return i == 0 ? false : true;
 }

 /**
  * 两地支是否反呤
  * @param i1
  * @param i2
  * @return
  */
 public boolean isYaoFanLing(int i1, int i2){
   return isDiZiKe(i1,i2) || isDiZiChong(i1,i2);
 }

 /**
  * 是否当前爻是动爻
  * @param change
  * @param current
  * @return
  */
 public boolean isDongYao(int[] changes, int current) {
   for(int i=0; i<changes.length; i++) {
     if(changes[i] == current) {
       return true;
     }
   }
   return false;
 }

  /**
   * 由动爻返回其哪个爻的爻词
    六爻安定的，以本卦卦辞断之。
　　一爻动，以动爻之爻辞断之。
　　两爻动者，则取阴爻之爻辞以为断，盖以“阳主过去，阴主未来”故也。如天风女后囗卦，初六、九五两爻皆动，则以初六爻断之，九五爻为辅助之断，“阳主过去，阴主未来”，其中大有学问。
　　所动的两爻如果同是阳爻或阴爻，则取上动之爻断之，如囗既济卦，初九、九五两爻皆动，则以九五爻的爻辞为断。
　　三爻动者，以所动三爻的中间一爻之爻辞为断，如囗卦，九二、九四、九五等三爻皆动，则取九四爻的爻辞为断。
　　四爻动者，以下静之爻辞断之，如囗火水未济卦，九二、六三、九四、六五四爻皆动，则以初六囗的爻辞断之，如囗初六、六三、九四、六五等四爻皆动，则取九二爻的爻辞断之。
　　五多动者，取静爻的爻辞断之。
　　六爻皆动的卦，如果是乾坤二卦，以“用九”、“用六”之辞断之，如囗乾卦六爻皆动，则为群龙无首，吉。
　　乾坤两卦外其余各卦，如果是六爻皆动，则以变卦的彖辞断之，如囗天风女后卦六爻皆动，则以乾卦的彖辞断之，因为女后卦是自乾卦变来，女后卦是在八宫卦的乾宫之中。
   * @param changes
   * @return
   */
  public int getDongYaoCi(int[] guaX, int[] changes) {
    int len = changes.length;
    int c1,c2;
    int who = 0;
    String str = "ERR!";

    if(len == 1)
      who = changes[0];

    if(len ==2){
      c1 = guaX[changes[0]];
      c2 = guaX[changes[1]];
      if(c1 == c2) {
        who = Math.max(changes[0], changes[1]);
      }else{
        if(c1 == YiJing.YANGYAO)
          who = changes[1];
        else
          who = changes[0];
      }
    }

    if(len ==3){
      int[] sortChanges = sort(changes);
      who = changes[1];
    }

    if(len == 4) {
      boolean b = false;
      for(int i=1; i<=6; i++) {
        b = false;
        for(int j=0; j<changes.length; j++) {
          if(i == changes[j]) {
            b = true;
            break;
          }
        }
        if(!b) {
          who = i;
          break;
        }
      }
    }

    if(len == 5) {
      who = 21 - changes[0] - changes[1] - changes[2] - changes[3] - changes[4];
    }

    if(len == 6) {
        who = 7;
    }

    return who;
  }

  /**
   * 冒泡法排序
   * @param array
   * @return
   */
  public int[] sort(int[] array) {
    int length = array.length;
    int t;
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          t = array[j];
          array[j] = array[j + 1];
          array[j + 1] = t;
        }
      }
    }
    return array;
  }

  /**
   * 判断是否宫经卦中此六亲本卦中没有，如果有则返回true
   * @param str
   * @param center
   * @return
   */
  public int[] howManyFeiFu(int[] liuQinZhu, int[] liuQinQong) {
    boolean b = false;
    int[] ret = new int[7];

    for(int i=1; i<=6; i++) {
      b = false;
      for(int j=1; j<=6; j++) {
        if(liuQinQong[i] == liuQinZhu[j]) {
          b = true;
          break;
        }
      }
      if(!b)
        ret[i] = i;
    }
/*
    int j = 0;
    for(int i = 0; i<7; i++) {
      if(ret[i] != 0) j++;
    }
    iret = new int[j];
    j = 0;
    for(int i = 0; i<7; i++) {
      if(ret[i] != 0) iret[j++] = ret[i];
    }
*/
    return ret;
  }

  /**
   * 将传来的字符使其中心在center上
   * @param str
   * @param center
   * @return
   */
  public String makeCenter(String str, int center) {
    int len = str.getBytes().length;
    return getRepeats(" ",center+len/2 - len)+str;
  }

  /**
   * 找卦身(月卦身)
   * @param str
   * @param len
   * @return
   */
  public int getGuaShen(int up, int down, int shiyao) {
    int guashen = YiJing.YUEGUASHEN[getYinOrYang(up,down,shiyao)][shiyao];
    return guashen;
  }

  /**
   * 卦身是否上卦
   * @param up
   * @param down
   * @param guashen
   * @return
   */
  public boolean isGuaShen(int up, int down, int guashen) {
    int[] diZi = mergeIntArray(getGuaDiZi(up,1),getGuaDiZi(down,0));
    boolean b = false;
    for(int i=1; i<7; i++) {
      if(diZi[i] == guashen)
        b = true;
    }
    return b;
  }

  /**
   * 安世身
   * @param diZi
   * @param shiYao
   * @return
   */
  public int getShiShen(int[] diZi, int shiYao) {
    return YiJing.SHISHEN2[diZi[shiYao]];
  }

  /**
   * 由上卦及下卦返回世爻或应爻或其他位置爻的阴阳属性
   * @param str
   * @param len
   * @return
   */
  public int getYinOrYang(int up, int down, int where) {
    int[] upGuaXiang1 = getGuaXiang(up);
    int[] downGuaXiang1 = getGuaXiang(down);
    int[] guaXiang1 = mergeIntArray(upGuaXiang1,downGuaXiang1);

    return guaXiang1[where];
  }

  /**
   * 返回重复的元素
   * @param str
   * @param len
   * @return
   */
  public String getRepeats(String str, int len) {
    String retStr = "";
    for(int i = 0; i<len; i++) {
      retStr += str;
    }
    return retStr;
  }

  /**
   * 由上卦和下卦得到其所属卦宫
   * @param up
   * @param down
   * @return
   */
  public int getWhichGong(int up ,int down) {
    return YiJing.BAGONGGUA[up][down];
  }

  /**
   * 由上卦和下卦得到其互卦上卦或下卦
   * @param up
   * @param down
   * @return
   */
  public int getHuGuaUpOrDown(int up ,int down, String type) {
    int upOrDown = 0;
    int[] upGuaX = YiJing.GUAXIANG[up];
    int[] downGuaX = YiJing.GUAXIANG[down];
    if("UP".equalsIgnoreCase(type))
      upOrDown = YiJing.XIANGGUA[downGuaX[3]][upGuaX[1]][upGuaX[2]];
    else
      upOrDown = YiJing.XIANGGUA[downGuaX[2]][downGuaX[3]][upGuaX[1]];

    return upOrDown;
  }

  /**
   * 由上卦和下卦得到其变卦上卦或下卦
   * @param up
   * @param down
   * @param change
   * @param type
   * @return
   */
  public int getBianGuaUpOrDown(int up, int down, int changes[],String type) {
    if(changes.length == 1 && changes[0]==0) {
      if("UP".equalsIgnoreCase(type)) {
        return up;
      }else{
        return down;
      }
    }

    int upOrDown = 0;
    int[] upGuaX = YiJing.GUAXIANG[up];
    int[] downGuaX = YiJing.GUAXIANG[down];
    int i1=0,i2=0,i3=0,i;

      if("UP".equalsIgnoreCase(type)) {
        i1 = upGuaX[1];
        i2 = upGuaX[2];
        i3 = upGuaX[3];
        for(int j=0; j<changes.length; j++) {
          if(changes[j] <= 3)
            continue;
          i = changes[j]%3==0 ? 3 : changes[j]%3;
          if (i == 1) {
            i1 = getReverseYinYang(upGuaX[1]);
          }else if (i == 2) {
            i2 = getReverseYinYang(upGuaX[2]);
          }else {
            i3 = getReverseYinYang(upGuaX[3]);
          }
        }
        upOrDown = YiJing.XIANGGUA[i1][i2][i3];
      }else{
        i1 = downGuaX[1];
        i2 = downGuaX[2];
        i3 = downGuaX[3];
        for(int j=0; j<changes.length; j++) {
          if(changes[j] >= 4)
            continue;
          i = changes[j];
          if (i == 1) {
            i1 = getReverseYinYang(downGuaX[1]);
          }else if (i == 2) {
            i2 = getReverseYinYang(downGuaX[2]);
          }else {
            i3 = getReverseYinYang(downGuaX[3]);
          }
        }
        upOrDown = YiJing.XIANGGUA[i1][i2][i3];
      }

    return upOrDown;
  }

  /**
   * 返回相反的阴爻与阳爻
   * @param i
   * @return
   */
  public int getReverseYinYang(int i) {
    if(i==1)
      return 2;
    return 1;
  }

  /**
   * 由八宫卦得到所属何五行
   * @param gong
   * @return
   */
  public int getWhichWH(int gong) {
    return YiJing.BAGONGGUAWH[gong];
  }

  /**
   * 由卦得到其卦的爻象图
   * @param gua
   * @return
   */
  public int[] getGuaXiang(int gua) {
    return YiJing.GUAXIANG[gua];
  }

  /**
   * 得到八卦的世爻位置
   * @param up
   * @param down
   * @return
   */
  public int getShiYao(int up, int down) {
    return YiJing.BAGONGSHIYING[up][down];
  }

  /**
   * 由世爻得到其应爻的位置
   * @param shi
   * @return
   */
  public int getYingYao(int shi){
    int shi1 = (shi+3)%6 == 0 ? 6 : (shi+3)%6;
    return shi1;
  }

  /**
   * 由卦及其其内外位置得到其地支 0内1外
   * @param gua
   * @param upOrDown
   * @return
   */
  public int[] getGuaDiZi(int gua, int upOrDown) {
    return YiJing.BAGUADIZI[gua][upOrDown];
  }

  /**
   * 由卦及其其内外位置得到其天干 0内1外
   * @param gua
   * @param upOrDown
   * @return
   */
  public int[] getGuaTG(int gua, int upOrDown) {
    return YiJing.BAGUATG[gua][upOrDown];
  }


  /**
   * 由我的五行与八宫卦的五行得到其六亲之一
   * @param selfWH
   * @param gongWH
   * @return
   */
  public int getLiuQin(int selfWH, int gongWH) {
    return YiJing.LIUQIN[selfWH][gongWH];
  }

  /**
   * 由八卦地支和其所属宫五行得到其六亲
   * @param self
   * @param gong
   * @return
   */
  public int[] getLiuQin(int[] self, int gong) {
    int[] lq = new int[7];
    lq[1] = getLiuQin(gong, YiJing.DIZIWH[self[1]]);
    lq[2] = getLiuQin(gong, YiJing.DIZIWH[self[2]]);
    lq[3] = getLiuQin(gong, YiJing.DIZIWH[self[3]]);
    lq[4] = getLiuQin(gong, YiJing.DIZIWH[self[4]]);
    lq[5] = getLiuQin(gong, YiJing.DIZIWH[self[5]]);
    lq[6] = getLiuQin(gong, YiJing.DIZIWH[self[6]]);
    return lq;
  }

  /**
   * 合并上下卦
   * @param arr1
   * @param arr2
   * @return
   */
  public String[] mergeStrArray(String[] up,String[] down) {
    String[] newArray = new String[7];
    newArray[1] = down[1];
    newArray[2] = down[2];
    newArray[3] = down[3];
    newArray[4] = up[1];
    newArray[5] = up[2];
    newArray[6] = up[3];

    return newArray;
  }

  /**
   * 合并上下卦地支
   * @param up
   * @param down
   * @return
   */
  public int[] mergeIntArray(int[] up, int[] down) {
    int[] dizi = new int[7];
    dizi[1] = down[1];
    dizi[2] = down[2];
    dizi[3] = down[3];
    dizi[4] = up[1];
    dizi[5] = up[2];
    dizi[6] = up[3];

    return dizi;
  }

  public int[] transToInt(Integer[] i) {
    int[] iret = new int[7];
    for(int len = 1; len<i.length; len++) {
      iret[len] = i[len].intValue();
    }
    return iret;
  }

  /**
   * 由日干得到六神
   * @param rigan
   * @return
   */
  public int[] getLiuShen(int rigan) {
    return YiJing.RIGANLIUSHEN[rigan];
  }

  /**
   * 判断该支在月支上是旺相还是死休囚
   * 到底处何地
   * 1/2为旺相、3、4、5为死囚休
   * @param zi 为哪个地支
   * @param yuez 为月支
   * @return
   */
  public boolean isWxsqx(int zi, int yuez) {
    return YiJing.WXSQX[yuez][YiJing.DIZIWH[zi]] > 2 ? false : true;
  }
  public int howWxsqx(int zi, int yuez) {
    return YiJing.WXSQX[yuez][YiJing.DIZIWH[zi]] ;
  }

  /**
   * 判断该支在日支上是旺还是衰墓绝
   * 到底处何地
   * 1/2为旺相、3、4、5为死囚休
   * @param zi
   * @param rizi
   * @return
   */
  public boolean isSwmj(int zi, int rizi) {
    return YiJing.SWMJ[YiJing.DIZIWH[zi]][rizi] > 2 ? false:true;
  }
  public int howSwmj(int zi, int rizi) {
    return YiJing.SWMJ[YiJing.DIZIWH[zi]][rizi] ;
  }

  /**
   * 分析整个主变卦中的各个地支的旺衰情况
    //1. 求静爻的旺衰，即有生克权就旺
    //2. 求暗动爻
    //3. 求动、暗动爻的旺衰，即生克权
    //4. 求变爻之旺衰，即生克权
    //5. 三合六合看五行旺衰
    //6. 生拱扶克害刑冲论五行旺衰
   * @param str
   * @param bRJWang
   * @param bRJZhi
   */
  public boolean getWangOrNoOut(
      StringBuffer str, int yuezi, int rigan, int rizi,
      int dizi[], int[] diziBian, int[] changes,
       int[] silents, int[] andongs, int[] actives, int[] bians) {

    //三合局
    //不要静爻
    int[] zis1 = new int[15];
    int j = 0;
    for(int i=0; i<andongs.length; i++) {
      if(andongs[i] == 0)
        break;
      zis1[j++] = andongs[i];
    }
    for(int i=0; i<actives.length; i++) {
      if(actives[i] == 0)
        break;
      zis1[j++] = actives[i];
    }
    for(int i=0; i<bians.length; i++) {
      if(bians[i] == 0)
        break;
      zis1[j++] = bians[i];
    }
    zis1[j++] = rizi;
    zis1[j++] = yuezi;

    //要加上静爻
    int[] zis2 = new int[15];
    for(int i=0; i<zis1.length; i++) {
      if(zis1[i] == 0)
        break;
      zis2[i] = zis1[i];
    }

    for(int i=0; i<silents.length; i++) {
      if(silents[i] == 0)
        break;
      zis2[j++] = silents[i];
    }
    getShanHeOut(str,zis1,zis2,silents,rigan,rizi,yuezi);

    //六合局
    this.getLiuHeOut(andongs,actives,bians,rizi,yuezi,str,dizi,changes,diziBian);

    return true;
  }

  /**
   * 生拱扶克害刑冲论五行旺衰
   */
  public void getSGFKHXCout(int zi, int[] silents, int[] andongs, int[] actives,
                            int[] bians, int rizi, int yuezi, StringBuffer str,
                            int rigan, int[] changes, int[] dizi,int[] diziBian) {
    //生
    str.append("\n");
    str.append(getRepeats(" ", YiJing.INTER[0]));
    str.append("支"+YiJing.DIZINAME[zi]+"。受生：");
    for(int j=0; j<silents.length; j++) {
      if(silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[silents[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("静爻"+YiJing.DIZINAME[silents[j]]+"；");
      }
    }
    for(int j=0; j<andongs.length; j++) {
      if(andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[andongs[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("暗动爻"+YiJing.DIZINAME[andongs[j]]+"；");
      }
    }
    for(int j=0; j<actives.length; j++) {
      if(actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[actives[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("动爻"+YiJing.DIZINAME[actives[j]]+"；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.WXDANSHENG[YiJing.DIZIWH[bians[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("日支"+YiJing.DIZINAME[rizi]+"；");
    }
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("月支"+YiJing.DIZINAME[yuezi]+"；");
    }

    //拱
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("受拱：");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZGONG[silents[j]][zi] == 1) {
        str.append("静爻" + YiJing.DIZINAME[silents[j]] + "；");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZGONG[andongs[j]][zi] == 1) {
        str.append("暗动爻" + YiJing.DIZINAME[andongs[j]] + "；");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZGONG[actives[j]][zi] == 1) {
        str.append("动爻" + YiJing.DIZINAME[actives[j]] + "；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZGONG[bians[j]][zi] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if (YiJing.DZGONG[rizi][zi] == 1) {
      str.append("日支" + YiJing.DIZINAME[rizi] + "；");
    }
    if (YiJing.DZGONG[yuezi][zi] == 1) {
      str.append("月支" + YiJing.DIZINAME[yuezi] + "；");
    }

    //扶
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("受扶：");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZFU[silents[j]][zi] == 1) {
        str.append("静爻" + YiJing.DIZINAME[silents[j]] + "；");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZFU[andongs[j]][zi] == 1) {
        str.append("暗动爻" + YiJing.DIZINAME[andongs[j]] + "；");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZFU[actives[j]][zi] == 1) {
        str.append("动爻" + YiJing.DIZINAME[actives[j]] + "；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZFU[bians[j]][zi] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if (YiJing.DZFU[rizi][zi] == 1) {
      str.append("日支" + YiJing.DIZINAME[rizi] + "；");
    }
    if (YiJing.DZFU[yuezi][zi] == 1) {
      str.append("月支" + YiJing.DIZINAME[yuezi] + "；");
    }

    //克
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("受克：");
    for(int j=0; j<silents.length; j++) {
      if(silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANKE[YiJing.DIZIWH[silents[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("静爻"+YiJing.DIZINAME[silents[j]]+"；");
      }
    }
    for(int j=0; j<andongs.length; j++) {
      if(andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if(YiJing.WXDANKE[YiJing.DIZIWH[andongs[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("暗动爻"+YiJing.DIZINAME[andongs[j]]+"；");
      }
    }
    for(int j=0; j<actives.length; j++) {
      if(actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if(YiJing.WXDANKE[YiJing.DIZIWH[actives[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("动爻"+YiJing.DIZINAME[actives[j]]+"；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.WXDANKE[YiJing.DIZIWH[bians[j]]][YiJing.DIZIWH[zi]] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("日支"+YiJing.DIZINAME[rizi]+"；");
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) {
      str.append("月支"+YiJing.DIZINAME[yuezi]+"；");
    }

    //害
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("受害：");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZHAI[silents[j]][zi] == 1) {
        str.append("静爻" + YiJing.DIZINAME[silents[j]] + "；");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZHAI[andongs[j]][zi] == 1) {
        str.append("暗动爻" + YiJing.DIZINAME[andongs[j]] + "；");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZHAI[actives[j]][zi] == 1) {
        str.append("动爻" + YiJing.DIZINAME[actives[j]] + "；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZHAI[bians[j]][zi] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if (YiJing.DZHAI[rizi][zi] == 1) {
      str.append("日支" + YiJing.DIZINAME[rizi] + "；");
    }
    if (YiJing.DZHAI[yuezi][zi] == 1) {
      str.append("月支" + YiJing.DIZINAME[yuezi] + "；");
    }

    //刑
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("受刑：");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZXING[silents[j]][zi] == 1) {
        str.append("静爻" + YiJing.DIZINAME[silents[j]] + "；");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZXING[andongs[j]][zi] == 1) {
        str.append("暗动爻" + YiJing.DIZINAME[andongs[j]] + "；");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZXING[actives[j]][zi] == 1) {
        str.append("动爻" + YiJing.DIZINAME[actives[j]] + "；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZXING[bians[j]][zi] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if (YiJing.DZXING[rizi][zi] == 1) {
      str.append("日支" + YiJing.DIZINAME[rizi] + "；");
    }
    if (YiJing.DZXING[yuezi][zi] == 1) {
      str.append("月支" + YiJing.DIZINAME[yuezi] + "；");
    }

    //冲
    str.append(getRepeats(" ", YiJing.INTER[3]));
    str.append("受冲：");
    for (int j = 0; j < silents.length; j++) {
      if (silents[j] == 0)
        continue;
      if(!isHasQuanForSilen(str,silents[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZCHONG[silents[j]][zi] == 1) {
        str.append("静爻" + YiJing.DIZINAME[silents[j]] + "；");
      }
    }
    for (int j = 0; j < andongs.length; j++) {
      if (andongs[j] == 0)
        break;
      if(!isHasQuanForSilen(str,andongs[j],yuezi,rigan,rizi,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZCHONG[andongs[j]][zi] == 1) {
        str.append("暗动爻" + YiJing.DIZINAME[andongs[j]] + "；");
      }
    }
    for (int j = 0; j < actives.length; j++) {
      if (actives[j] == 0)
        break;
      if(!isHasQuanForActive(str,actives[j],yuezi,rizi,changes,dizi,diziBian,changes[j],rigan,false))
        continue;
      if (YiJing.DZCHONG[actives[j]][zi] == 1) {
        str.append("动爻" + YiJing.DIZINAME[actives[j]] + "；");
      }
    }
    for (int j = 0; j < bians.length; j++) {
      if (bians[j] == 0)
        break;
      if(!isHasQuanForBian(str,bians[j],yuezi,rizi,rigan,changes,dizi,diziBian,false))
        continue;
      if (YiJing.DZCHONG[bians[j]][zi] == 1) {
        str.append("变爻" + YiJing.DIZINAME[bians[j]] + "；");
      }
    }
    if (YiJing.DZCHONG[rizi][zi] == 1) {
      str.append("日支" + YiJing.DIZINAME[rizi] + "；");
    }
    if (YiJing.DZCHONG[yuezi][zi] == 1) {
      str.append("月支" + YiJing.DIZINAME[yuezi] + "；");
    }

  }

  /**
   * 得到六合局
   * a) 合化成功。必须是卦中的两个爻都动，或卦中动爻与本位变爻之合。
   *              && 必须化神是日旺月建，且日或月中之一为化神，且日、月任何一方不能是克化神之五行。
   * b) 凡日或月与卦中之爻相合，都为合而不化。 但日、月与静爻为克合时，无论爻旺衰都不以合起论。
   */
  public void getLiuHeOut(int[] andongs, int[] actives, int[] bians,
                          int rizi, int yuezi, StringBuffer str,
                          int[] dizi, int[] changes, int[] diziBian) {
    int[] zis1 = new int[6];
    int j = 0;
    int wx = 0;

    for(int i=0; i<andongs.length; i++) {
      if(andongs[i] == 0)
        break;
      zis1[j++] = andongs[i];
    }
    for(int i=0; i<actives.length; i++) {
      if(actives[i] == 0)
        break;
      zis1[j++] = actives[i];
    }
    for(int i=0; i<j; i++) {
      for(int k=0; k<j; k++) {
        if (YiJing.DZLIUHE[zis1[i]][zis1[k]] == 1) {
          //日月一个为化神且不克
          wx = YiJing.DZLIUHE[zis1[i]][zis1[k]];
          if(wx > 0) {
            if (YiJing.DIZIWH[rizi] == wx || YiJing.DIZIWH[yuezi] == wx) {
              if (YiJing.WXDANKE[YiJing.DIZIWH[rizi]][wx] == 0 &&
                  YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][wx] == 0)  {
                str.append("\n");
                str.append(getRepeats(" ", YiJing.INTER[0]));
                str.append("六合局：");
                str.append(YiJing.DIZINAME[zis1[i]]);
                str.append(YiJing.DIZINAME[zis1[k]]);
                str.append("。因卦中动爻与动爻合而成化，化神五行同日或月之五行且日和月五行不克化神五行。");
              }
            }else{
              str.append("\n");
              str.append(getRepeats(" ", YiJing.INTER[0]));
              str.append("六合合而不化：");
              str.append(YiJing.DIZINAME[zis1[i]]);
              str.append(YiJing.DIZINAME[zis1[k]]);
              str.append("。因卦中动爻与动爻合，但化神五行不同日或月之五行或日和月五行克化神五行。");
            }
          }
        }          ;
      }
    }

    for(int i=0; i<changes.length; i++) {
      //日月一个为化神且不克
      wx = YiJing.DZLIUHE[dizi[changes[i]]][diziBian[changes[i]]];
      if (wx > 0) {
        if (YiJing.DIZIWH[rizi] == wx || YiJing.DIZIWH[yuezi] == wx) {
          if (YiJing.WXDANKE[YiJing.DIZIWH[rizi]][wx] == 0 &&
              YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][wx] == 0) {
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
            str.append("六合局：");
            str.append(YiJing.DIZINAME[dizi[changes[i]]]);
            str.append(YiJing.DIZINAME[diziBian[changes[i]]]);
            str.append("。因卦中动爻与变爻合而成化，化神五行同日或月之五行且日和月五行不克化神五行。");
          }
        }else{
          str.append("\n");
          str.append(getRepeats(" ", YiJing.INTER[0]));
          str.append("六合合而不化：");
          str.append(YiJing.DIZINAME[dizi[changes[i]]]);
          str.append(YiJing.DIZINAME[diziBian[changes[i]]]);
          str.append("。因卦中动爻与变爻合，但化神五行不同日或月之五行或日和月五行克化神五行。");
        }
      }
    }

  }

  /**
   * 得到三合局
   * a) 实合局。有卦中动爻、变爻、暗动爻、日、月成化，则得其化神。
   *       化神五行同日或月之五行 && 日和月五行不克化神五行则成功，否则合而不化，论绊住。
   * b) 待合局。a中有一爻不动或旬空，待填实值日之时或动时成局，此为“虚一待用”，应期就在“待用”之爻上。
   * @param str
   * @param zi
   */
  public void getShanHeOut(StringBuffer str, int[] zi, int[] zi2, int[] silents,
                           int rigan, int rizi, int yuezi) {
    int wx = 0;
    boolean bool = false;
    String _s = "";

    for(int i=0; i<zi.length; i++){
      for(int j=0; j<zi.length; j++){
        bool = false;
        for(int k=0; k<zi2.length; k++){
          //日月一个为化神且不克
          wx = YiJing.DZSHANHE[zi[i]][zi[j]][zi[k]];
          if(wx > 0 && _s.indexOf(""+wx) == -1) {
            _s += ""+wx;
            if(YiJing.DIZIWH[rizi] == wx || YiJing.DIZIWH[yuezi] == wx) {
              if(YiJing.WXDANKE[YiJing.DIZIWH[rizi]][wx] == 0 &&
                 YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][wx] == 0) {
                //是否有一爻不动
                for(int l=0; l<silents.length; l++) {
                  if(silents[l] == zi2[k]) {
                    bool = true;
                    break;
                  }
                }
                if(!bool) {
                  //是否有一爻为旬空
                  if(!isXunKong(zi[i],rigan,rizi) && !isXunKong(zi[j],rigan,rizi) &&
                     !isXunKong(zi2[k],rigan,rizi)) {
                    str.append("\n");
                    str.append(getRepeats(" ", YiJing.INTER[0]));
                    str.append("三合局实合局：");
                    str.append(YiJing.DIZINAME[zi[i]]);
                    str.append(YiJing.DIZINAME[zi[j]]);
                    str.append(YiJing.DIZINAME[zi[k]]);
                    str.append("。因卦中动、变、暗动、日、月合而成化，化神五行同日或月之五行且日和月五行不克化神五行。");
                }else{
                  str.append("\n");
                  str.append(getRepeats(" ", YiJing.INTER[0]));
                  str.append("三合局待合局：");
                  str.append(YiJing.DIZINAME[zi[i]]);
                  str.append(YiJing.DIZINAME[zi[j]]);
                  str.append(YiJing.DIZINAME[zi[k]]);
                  str.append("。因卦中三爻合而成化，化神五行同日或月之五行且日和月五行不克化神五行。但一爻为旬空,待出空时成局，此为“虚一待用”，应期就在待用之爻上。");

                }
              }else{
                if(!isXunKong(zi[i],rigan,rizi) && !isXunKong(zi[j],rigan,rizi) &&
                     !isXunKong(zi2[k],rigan,rizi)) {
                    str.append("\n");
                    str.append(getRepeats(" ", YiJing.INTER[0]));
                    str.append("三合局待合局：");
                    str.append(YiJing.DIZINAME[zi[i]]);
                    str.append(YiJing.DIZINAME[zi[j]]);
                    str.append(YiJing.DIZINAME[zi[k]]);
                    str.append(
                        "。因卦中三爻合而成化，化神五行同日或月之五行且日和月五行不克化神五行。但一爻不动,待动时成局，此为“虚一待用”，应期就在待用之爻上。");
                  }else if(!isXunKong(zi[i],rigan,rizi) && !isXunKong(zi[j],rigan,rizi) &&
                     isXunKong(zi2[k],rigan,rizi)){
                    str.append("\n");
                    str.append(getRepeats(" ", YiJing.INTER[0]));
                    str.append("三合局待合局：");
                    str.append(YiJing.DIZINAME[zi[i]]);
                    str.append(YiJing.DIZINAME[zi[j]]);
                    str.append(YiJing.DIZINAME[zi[k]]);
                    str.append(
                        "。因卦中三爻合而成化，化神五行同日或月之五行，且日月五行不克化神五行。但一爻不动又恰旬空，此例特殊也。");
                  }
              }
            }
          }else{
            str.append("\n");
            str.append(getRepeats(" ", YiJing.INTER[0]));
            str.append("三合合而不化局：");
            str.append(YiJing.DIZINAME[zi[i]]);
            str.append(YiJing.DIZINAME[zi[j]]);
            str.append(YiJing.DIZINAME[zi[k]]);
            str.append("。因卦中三爻合，但化神五行不同日或月之五行，或日和月五行克化神五行。");
          }
        }
      }
    }
  }
  }

  /**
   * 判断一个地支是否旺相
   * @return
   */
  public boolean isZiWang(
      StringBuffer str, int zi, int yuezi, int rigan ,int rizi, int[] changes,
      int[] dizi, int[] diziBian) {

    int[] silents = getSilents(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] andongs = getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] actives = getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] bians   = getBians(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);

    for(int i=0; i<silents.length; i++) {
      if(silents[i] == zi)
        return true;
    }
    for (int i = 0; i < andongs.length; i++) {
      if (andongs[i] == zi)
        return true;
    }
    for (int i = 0; i < actives.length; i++) {
      if (actives[i] == zi)
        return true;
    }
    for (int i = 0; i < bians.length; i++) {
      if (bians[i] == zi)
        return true;
    }
    return false;
  }

  /**
   * 返回所有的暗动爻
   * @return
   */
  public int[] getAnDongs(StringBuffer str, int rigan, int rizi, int yuezi,
                         int[] changes, int[] dizi, int[] diziBian,boolean bl) {
   int zi = 0;
   int[] silent = new int[6];
   int j = 0;
   String _temp = null;
   int start = 0;
   int end = 0;
   boolean bool = false;

   for (int i = 1; i <= 6; i++) {
     bool = false;
     for (int k = 0; k < changes.length; k++) {
       if (i == changes[k]) {
         bool = true;
         break;
       }
     }
     if(bool)
       continue;

      zi = dizi[i];
      _temp = "\n";
      _temp += getRepeats(" ", YiJing.INTER[0]);
      _temp += "暗动爻" + YiJing.DIZINAME[zi] + "：";
      start = str.length();
      if(bl)
        str.append(_temp);
      end = str.length();
      if (isAndong(str, zi, yuezi, rizi, changes, dizi, diziBian,bl)) {
        if (isHasQuanForSilen(str, zi, yuezi, rigan, rizi, changes, dizi,
                              diziBian,bl)) {
          if(bl) {
            if(isXunKong(zi,rigan,rizi))
             str.append("该暗动爻虽旬空但有生克权。");
           else
             str.append("该暗动爻有生克权。");
          }
        }
        silent[j++] = zi;
      }else{
        str.delete(start,end);
      }
    }

    return silent;
  }

  /**
   * 返回所有的静爻
   * @return
   */
  public int[] getSilents(StringBuffer str, int rigan, int rizi, int yuezi,
                         int[] changes, int[] dizi, int[] diziBian,boolean bl) {
   int zi = 0;
   int[] silent = new int[6];
   int j = 0;
   boolean bool = false;

   for (int i = 1; i <= 6; i++) {
     bool = false;
     for (int k = 0; k < changes.length; k++) {
       if (i == changes[k]) {
         bool = true;
         break;
       }
     }
     if(bool)
       continue;

     zi =  dizi[i];
     if (!isAndong(str, zi, yuezi, rizi, changes, dizi, diziBian,false)) {
       if(bl) {
         str.append("\n");
         str.append(getRepeats(" ", YiJing.INTER[0]));
         str.append("静爻" + YiJing.DIZINAME[zi] + "：");
       }
       if (isHasQuanForSilen(str, zi, yuezi, rigan, rizi, changes, dizi,
                             diziBian,bl)) {
         if(bl) {
           if(isXunKong(zi,rigan,rizi))
             str.append("该静爻虽旬空但有生克权。");
           else
             str.append("该静爻有生克权。");
         }
       }
       silent[j++] = zi;
     }
   }

   return silent;
 }


  /**
   * 返回所有的动爻
   * @return
   */
  public int[] getActives(StringBuffer str, int rigan, int rizi, int yuezi,
                          int[] changes, int[] dizi, int[] diziBian,boolean bl) {
    int zi = 0;
    int[] silent = new int[6];
    int j = 0;
    boolean bool = false;

    for (int i = 1; i <= 6; i++) {
      bool = false;
      for (int k = 0; k < changes.length; k++) {
        if (i == changes[k]) {
          bool = true;
          break;
        }
      }
      if(!bool)
        continue;

      zi =  dizi[i];
      if(bl) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("动爻" + YiJing.DIZINAME[zi] + "：");
      }
      if (isHasQuanForActive(str, zi, yuezi, rizi, changes, dizi,
                                  diziBian, i, rigan,bl)) {
        if(bl) {
          if(isXunKong(zi,rigan,rizi))
             str.append("该动爻虽旬空但有生克权。");
           else
             str.append("该动爻有生克权。");
        }
      }
      silent[j++] = zi;
    }

    return silent;
  }

  /**
   * 返回所有的的变爻
   * @return
   */
  public int[] getBians(StringBuffer str, int rigan, int rizi, int yuezi,
                          int[] changes, int[] dizi, int[] diziBian,boolean bl) {
    int zi = 0;
    int[] silent = new int[6];
    int j = 0;

    for (int i = 0; i < changes.length; i++) {
      if(changes[i]==0)
        continue;
      zi =  diziBian[changes[i]];
      if(bl) {
        str.append("\n");
        str.append(getRepeats(" ", YiJing.INTER[0]));
        str.append("变爻" + YiJing.DIZINAME[zi] + "：");
      }
      if (isHasQuanForBian(str, zi, yuezi, rizi, rigan, changes, dizi,
                                  diziBian,bl)) {
        if(bl) {
          if(isXunKong(zi,rigan,rizi))
             str.append("该变爻虽旬空但有生克权。");
           else
             str.append("该变爻有生克权。");
        }
      }
      silent[j++] = zi;
    }

    return silent;
  }


  /**
   * 得到变爻的生克权
   * @return
   */
  public boolean isHasQuanForBian(
      StringBuffer str, int zi, int yuezi, int rizi,int rigan,
      int[] changes, int[] dizi, int[] diziBian, boolean bl) {
    //变爻旬空：动爻本身不空，但变爻旬空，动爻也不能正常发挥生克权，待变爻出空时才有生克权。
    //冲动爻、冲旬空的变爻都不起作用。
    if(isXunKong(zi,rigan,rizi)) {
      if(bl)
        str.append("该变爻逢旬空，无生克权，只有待变爻出空时才有生克权；冲动爻、冲旬空的变爻都不起作用。");
      return false;
    }

    //月建冲变爻无论旺衰都为月破。但旺相之变爻或虽休囚但不受克之变爻逢月冲，
    //在变爻值日或逢日合之日，也有生克权，出月有生克权。日冲逢进行时的日合住可解。
    if(YiJing.DZCHONG[yuezi][zi] == 1) {
      if(isWxsqx(zi,yuezi) || (!isWxsqx(zi,yuezi) && !isDongAndBianKe(zi,changes,dizi,diziBian,true)))
        if(zi != rizi && YiJing.DZLIUHE[zi][rizi] == 0) {
          if(bl)
            str.append("该变爻逢月冲，虽旺相或休囚不受克但不值日或逢日合，只有逢进行时的日合或出月才有生克权。");
          return false;
        }
    }

    return true;
  }

  /**
   * 得到动爻与暗动爻生克权
   * @param local 此支的位置
   * @return
   */
  public boolean isHasQuanForActive(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int local, int rigan, boolean bl) {
    boolean bool1 = isHasQuanForActive2(str, zi, yuezi, rizi, changes, dizi, diziBian, local, rigan, bl);
    if(!bool1) {
      return bool1;
    }
    else {
      boolean bool2 = getTwoDongYaoChong(str, zi, yuezi, rizi, changes, dizi, diziBian, local, rigan, bl);
      return bool2;
    }
  }

  public boolean isHasQuanForActive2(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int local, int rigan, boolean bl) {
    //动爻旬空：冲空、出空均能正常发挥生克职能。
   if(isXunKong(zi,rigan,rizi)) {
     if(!isRiYueChong(zi,rizi,yuezi) && !isDongAndBianChong(zi,changes,dizi,diziBian)) {
       if(bl)
         str.append("该动爻逢旬空，又无日、月、动、变之爻冲之，已失去生克权，只有冲空、出空才能正常发挥生克职能。");
       return false;
     }
   }
   //变爻旬空：动爻本身不空，但变爻旬空，动爻也不能正常发挥生克权，待变爻出空时才有生克权。
    //冲动爻、冲旬空的变爻都不起作用。
    if(isXunKong(diziBian[local],rigan,rizi)) {
      if(bl)
        str.append("该动爻所变之爻逢旬空，动爻也失去生克权，只有待变爻出空时才有生克权；冲动爻、冲旬空的变爻都不起作用。");
      return false;
    }

    //月建冲动爻无论旺衰都为月破。但旺相之动爻或虽休囚但不受克之动爻逢月冲，
    //在动爻值日或逢日合之日，也有生克权，出月有生克权。日冲逢进行时的日合住可解。
    if(YiJing.DZCHONG[yuezi][zi] == 1) {
      if(isWxsqx(zi,yuezi) || (!isWxsqx(zi,yuezi) && !isDongAndBianKe(zi,changes,dizi,diziBian,true)))
        if(zi != rizi && YiJing.DZLIUHE[zi][rizi] == 0) {
          if(bl)
            str.append("该动爻逢月冲，虽旺相或休囚不受克但不值日或逢日合，只有逢进行时的日合或出月才有生克权。");
          return false;
        }
    }
    //日建冲休囚动爻谓冲散，此动爻永无生克权，不可解。
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[rizi][zi] == 1) {
        if(bl)
          str.append("该休囚动爻被日冲而冲散，永无生克权，不可解。");
        return false;
      }
    }
    //本位动爻与变爻。休囚时，被变爻冲而冲散，失去生克权。
    //旺相时，如果变爻对动爻只冲不克时，本位动爻减力，但仍有生克权。
    // 变爻对本位动爻连冲带克时，不论动爻旺衰，动爻无生克权。只有本位动爻临日、月建时，本位动爻才有生克权。
    if(YiJing.DZCHONG[diziBian[local]][zi] == 1) {
      if(!isWxsqx(zi,yuezi)) {
        str.append("该休囚动爻被变爻冲而冲散，失去生克权。");
        return false;
      }else{
        if(YiJing.WXDANKE[YiJing.DIZIWH[diziBian[local]]][YiJing.DIZIWH[zi]] == 1) {
          if(rizi != zi && YiJing.DZLIUHE[yuezi][zi] != 1 && YiJing.DZLIUHE[rizi][zi] != 1) {
            if(bl)
              str.append("该动爻虽旺相但被变爻连冲带克，又不值月建、值日、日月合，失去生克权。");
            return false;
          }
        }
        //if(YiJing.WXDANKE[YiJing.DIZIWH[diziBian[local]]][YiJing.DIZIWH[zi]] == 0) {
        //  str.append("该动爻虽旺相但被变爻只冲不克，有生克权但力减小。");
        //  return false;
        //}
      }
    }
    //动爻在日、月两处只是休囚，不受克，但化退、化泄、化绝、生克权减小；
    //化回头克，无生克权；若化回头生、化进神，有生克权。
    if(!isWxsqx(zi,yuezi) && !isSwmj(zi,rizi)) {
      if(YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 0 &&
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 0 )
        if(YiJing.WXDANKE[YiJing.DIZIWH[diziBian[local]]][YiJing.DIZIWH[zi]] == 1) {
          if(bl)
            str.append("该动爻在日、月两处只是休囚，不受克，但化回头克，无生克权。");
          return false;
        }
    }
    //动爻在日、月双方均受冲克，无生克权。
    if((YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1 &&
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ) ||
      (YiJing.DZCHONG[rizi][zi] == 1 && YiJing.DZCHONG[yuezi][zi] == 1) ||
      (YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1 &&
       YiJing.DZCHONG[rizi][zi] == 1) ||
      (YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 &&
       YiJing.DZCHONG[yuezi][zi] == 1)) {
     if(bl)
       str.append("该动爻在日、月双方均受冲克，无生克权。");
     return false;
   }
   //凡动爻逢日或月合，无论旺衰，均论绊住，暂时失去生克权。无变爻冲的情况下
   //程序走到此，证明无变爻冲之
   if(YiJing.DZLIUHE[zi][yuezi] == 1 || YiJing.DZLIUHE[zi][rizi] == 1) {
     if(bl)
       str.append("该动爻在日、月有一方合住称绊住，又无变爻来冲，暂时失去生克权。");
     return false;
   }

    return true;
  }

  //动爻与动爻相冲，旺(指是否有变爻、日、月相助)者胜，衰者败，旺者有生克权，
    //衰者冲而散无生克权。双方都休囚，为两败俱伤，都无生克权。
    public boolean getTwoDongYaoChong(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian, int local, int rigan, boolean bl) {
      for (int i = 0; i < changes.length; i++) {
        if (YiJing.DZCHONG[dizi[changes[i]]][zi] == 1) {
          boolean dong1 = isHasQuanForActive2(str, zi, yuezi, rizi, changes, dizi,
                                             diziBian, local, rigan, false);
          boolean dong2 = isHasQuanForActive2(str, dizi[changes[i]], yuezi, rizi,
                                             changes, dizi, diziBian, local,
                                             rigan, false);
          if (dong1 && dong2) {
            if (bl)
              str.append("该旺相动爻冲另一旺相动爻，为两败俱伤，无生克权。");
            return false;
          }
          if (!dong1 && !dong2) {
            if (bl)
              str.append("该休囚动爻冲另一休囚动爻，都被冲散，无生克权。");
            return false;
          }
          if (!dong1 && dong2) {
            if (bl)
              str.append("该休囚动爻冲另一旺相动爻，被冲散，无生克权。");
            return false;
          }
        }
      }
      return true;
    }


  /**
   * 得到静爻的生克权
   * @return
   */
  public boolean isHasQuanForSilen(
      StringBuffer str, int zi, int yuezi, int rigan ,int rizi,
      int[] changes, int[] dizi, int[] diziBian,boolean bl) {
    //静爻旬空，若本身休囚无气，无生克权
    if(isXunKong(zi,rigan,rizi) && !isWxsqx(zi,yuezi) && !isSwmj(zi,rizi)) {
       if(bl)
         str.append("该静爻旬空，于日月又休囚无气，无生克权。");
       return false;
    }

    //月建冲体囚静爻为彻底破，永无生克权。
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][yuezi] == 1) {
        if(bl)
          str.append("该体囚静爻逢月冲，为彻底破，永无生克权。");
        return false;
      }
    }
    //月建冲旺相静爻，在月内为月破，无生克权。 但逢日合、值日及出月便有生克权。
    if(isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][yuezi] == 1) {
        if(zi != rizi ||
           YiJing.DZLIUHE[zi][rizi] != 1) {
          if(bl)
            str.append("该旺相静爻逢月冲，但无日合、值日来补救，为月破无生克权，出月便有。");
          return false;
        }
      }
    }
    //日建、动爻、变爻冲月支之休囚静爻。有动变、日月之爻克、泄、耗爻时则为日破。无生克权。休囚之爻不能克动变爻
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][rizi] == 1 || this.isDongAndBianChong(zi,changes,dizi,diziBian))
         if(isDongAndBianKe(zi,changes,dizi,diziBian,false) ||
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1||
         YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]]  == 1) {
        if (bl) {
          str.append("该旺相静爻逢日或动或自由变爻冲，又有动变、日月之爻克、泄、耗为日破，无生克权。");
        }
        return false;
      }
    }
    //休囚之爻逢日辰连冲带克为日破。无生克权。
    if(!isWxsqx(zi,yuezi)) {
      if(YiJing.DZCHONG[zi][rizi] == 1 &&
         YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1){
        if(bl)
          str.append("该休囚静爻逢日辰连冲带克为日破，无生克权。");
        return false;
      }
    }
    //静爻在日、月两方都休囚无气，便无生克权。
    if(!isWxsqx(zi,yuezi) && !isSwmj(zi,rizi)) {
      if(bl) {
        str.append("该静爻在日、月两方都休囚无气，无生克权。");
      }
      return false;
    }
    //静爻在日、月一方受克，一方休囚也无生克权。
    if((!isWxsqx(zi,yuezi) && YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1) ||
      (!isSwmj(zi,rizi) && YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1)) {
      if(bl)
        str.append("该静爻在日、月一方受克一方休囚，无生克权。");
      return false;
    }
    //静爻在日、月双方都受克，更无生克权。
    if(YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 &&
       YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) {
      if(bl)
        str.append("该静爻在日、月双方都受克，无生克权。");
      return false;
    }
    //静爻在日、月一方受生一方受克，有无生克权关键看卦中动爻的向背。如果是连冲带克，则无生克权。
    if((YiJing.WXDANKE[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
       YiJing.WXDANKE[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1) &&
      (YiJing.WXXIANGSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
       YiJing.WXXIANGSHENG[YiJing.DIZIWH[yuezi]][YiJing.DIZIWH[zi]] == 1)){
      if(isDongAndBianKe(zi,changes,dizi,diziBian,false)) {
        if(bl) {
          str.append("该静爻在日、月一方受生一方受克，又有动变爻克之，无生克权。");
        }
        return false;
      }
    }

    return true;
  }

  /**
   * 是否是暗动爻
   * @return
   */
  public boolean isAndong(
      StringBuffer str, int zi, int yuezi, int rizi,
      int[] changes, int[] dizi, int[] diziBian,boolean bl) {

    //月建冲值日之静爻，不但不论破反而为暗动
    if(zi == rizi && YiJing.DZCHONG[zi][yuezi] == 1) {
      if(bl)
        str.append("该静爻值日逢月冲，不但不论破反而为暗动。");
      return true;
    }
    //日建冲月支之旺相静爻，为暗动，静爻不但不受伤，反而因冲而动，按动爻看。
    if(isWxsqx(zi, yuezi) && YiJing.DZCHONG[zi][rizi] == 1) {
      if(bl)
        str.append("该旺相静爻逢日冲，为暗动。");
      return true;
    }
    //日建冲月支之休囚静爻。有动变之爻生之为暗动。
    if(!isWxsqx(zi, yuezi) && YiJing.DZCHONG[zi][rizi] == 1 &&
       isDongAndBianSheng(zi,changes,dizi,diziBian)){
      if(bl)
        str.append("该休囚静爻逢日冲，但有动变之爻生之为暗动。");
      return true;
    }
    //变爻冲主卦中的旺相静爻，为暗动，此变爻与动爻必须没有任何关系，即比和
    if(isWxsqx(zi,yuezi)) {
       int[] doBians = getDoBians(changes, dizi, diziBian);
       for (int i = 0; i < doBians.length; i++) {
         if(YiJing.DZCHONG[doBians[i]][zi] == 1) {
           if(bl)
             str.append("该旺相静爻被与动爻没有生克刑冲关系的变爻冲，为暗动。");
           return true;
         }
       }
     }
     //变爻冲主卦中的休囚静爻。有动变、日月之爻生之为暗动。
     if(!isWxsqx(zi,yuezi)) {
       int[] doBians = getDoBians(changes, dizi, diziBian);
       for (int i = 0; i < doBians.length; i++) {
         if (YiJing.DZCHONG[doBians[i]][zi] == 1) {
           if (YiJing.WXDANSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
               isDongAndBianSheng(zi, changes, dizi, diziBian)) {
             if(bl)
               str.append("该休囚静爻被变爻冲，但有动变、日生之为暗动。");
             return true;
           }
         }
       }
     }
     //动爻冲主卦中的旺相静爻，为暗动
     if(isWxsqx(zi,yuezi)) {
       for (int i = 0; i < changes.length; i++) {
         if(YiJing.DZCHONG[dizi[changes[i]]][zi] == 1) {
           if(bl)
             str.append("该旺相静爻被动爻冲，为暗动。");
           return true;
         }
       }
     }
     //动爻冲主卦中的的休囚静爻。有动变、日月之爻生之为暗动。
     if(!isWxsqx(zi,yuezi)) {
       for (int i = 0; i < changes.length; i++) {
         if (YiJing.DZCHONG[dizi[changes[i]]][zi] == 1) {
           if (YiJing.WXDANSHENG[YiJing.DIZIWH[rizi]][YiJing.DIZIWH[zi]] == 1 ||
               isDongAndBianSheng(zi, changes, dizi, diziBian)) {
             if(bl)
               str.append("该休囚静爻被动爻冲，但有动变、日生之为暗动。");
             return true;
           }
         }
       }
     }
     //凡静爻逢日、月台，在静爻旺相时，谓之合起，相当于动爻。
     if(isWxsqx(zi,yuezi)) {
       if(YiJing.DZLIUHE[zi][rizi] == 1 ||
          YiJing.DZLIUHE[zi][yuezi] == 1) {
         if (bl)
           str.append("该旺相静爻逢日、月合，谓之合起，相当于动爻。");
         return true;
       }
     }

    return false;
  }

  /**
   * 得到所有能作用其爻的变爻的地支
   * @return
   */
  public int[] getDoBians(int[] changes, int[] dizi, int[] diziBian) {
    int[] bian = new int[6];
    int j = 0;

    for(int i=0; i<changes.length; i++) {
      if(isDoseBian(dizi[changes[i]],diziBian[changes[i]])) {
        bian[j++] = diziBian[changes[i]];
      }
    }

    int[] retBian = new int[j];
    for(int i=0; i<j; i++) {
      retBian[i] = bian[i];
    }

    return retBian;
  }

  /**
   * 此变爻是否与动爻没有任何生克冲关系而能去作用其它爻
   * @return
   */
  public boolean isDoseBian(int dong, int bian) {
    return dong == bian;
  }

  /**
   * 是否有动变之爻生之
   * @return
   */
  public boolean isDongAndBianSheng(int zi, int[] changes, int dizi[], int[] diziBian) {
    for(int i=0; i<changes.length; i++) {
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[changes[i]]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
    }
    int[] doBians = getDoBians(changes,dizi,diziBian);
    for(int i=0; i<doBians.length; i++) {
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[doBians[i]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
    }

    return false;
  }

  /**
   * 是否有动变之爻克该爻
   * 如果该爻是动爻或旺相，则能否生或克动变爻
   * @return
   */
  public boolean isDongAndBianKe(int zi, int[] changes, int dizi[], int[] diziBian, boolean isSheng) {
    for(int i=0; i<changes.length; i++) {
      if(YiJing.WXDANSHENG[YiJing.DIZIWH[dizi[changes[i]]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
    }
    int[] doBians = getDoBians(changes,dizi,diziBian);
    for(int i=0; i<doBians.length; i++) {
      if(YiJing.WXDANKE[YiJing.DIZIWH[doBians[i]]][YiJing.DIZIWH[zi]] == 1 )
        return true;
      if(isSheng) {
        if(YiJing.WXDANSHENG[YiJing.DIZIWH[zi]][YiJing.DIZIWH[doBians[i]]] == 1 )
          return true;
        if(YiJing.WXDANKE[YiJing.DIZIWH[zi]][YiJing.DIZIWH[doBians[i]]] == 1 )
          return true;
      }
    }

    return false;
  }

  /**
   * 是否有动变之爻冲该爻
   * @return
   */
  public boolean isDongAndBianChong(int zi, int[] changes, int dizi[], int[] diziBian) {
    for(int i=0; i<changes.length; i++) {
      if(YiJing.DZCHONG[dizi[changes[i]]][zi] == 1 )
        return true;
    }
    int[] doBians = getDoBians(changes,dizi,diziBian);
    for(int i=0; i<doBians.length; i++) {
      if(YiJing.DZCHONG[doBians[i]][zi] == 1 )
        return true;
    }

    return false;
  }

  /**
   * 是否有日月之爻冲该爻
   * @return
   */
  public boolean isRiYueChong(int zi, int rizi, int yuezi) {
    return (YiJing.DZCHONG[rizi][zi] == 1) && (YiJing.DZCHONG[yuezi][zi] ==1) ;
  }

  /**
   * 返回旬空，为第一支*100+第二支
   * 是否是旬空
   * @return
   */
  public int getXunKong(int rigan, int rizi) {
    return YiJing.KONGWANG[rigan][rizi];
  }
  public boolean isXunKong(int zi, int rigan ,int rizi) {
    int k = getXunKong(rigan, rizi);
    if(zi == k/100 || zi == (k - k/100 * 100))
      return true;
    return false;
  }

  /**
   * 得到神的位置及是否是伏神
   * @param ysLiuqin
   * @param liuqin
   * @param liuqinGong
   * @param ysLocal
   * @return
   */
  public boolean getShenLocal(int ysLiuqin ,int[] liuqin,
                              int[] liuqinGong, int[] ysLocal) {
    int j = 0;
    boolean isFu = true;
    //得到用神的位置
    for (int i = 1; i < liuqin.length; i++) {
      if (liuqin[i] == ysLiuqin) {
        isFu = false;
        ysLocal[j++] = i;
      }
    }
    //如果不现，则得到伏神的数量与位置
    if(isFu) {
      for(int i = 1; i<= 6; i++) {
        if(liuqinGong[i] == ysLiuqin) {
          ysLocal[j++] = i;
        }
      }
    }

    return isFu;
  }

  /**
   * 判断该支是否是静爻或是动、暗动爻
   * @return
   */
  public boolean isDongOrJing(
      StringBuffer str, int zi, int yuezi, int rizi,int[] changes,
      int[] dizi, int[] diziBian) {
    if(isAndong(str,zi,yuezi,rizi,changes,dizi,diziBian,false))
      return true;
    for(int i=0; i<changes.length; i++) {
      if(dizi[changes[i]] == zi)
        return true;
    }

    return false;
  }

  /**
   * 该动爻卡静爻是否与其它动爻暗动爻或变爻日月合
   * @return
   */
  public boolean isHe(
      StringBuffer str, int rigan, int rizi, int yuezi,
      int[] changes, int[] dizi, int[] diziBian, int zi) {
    int[] actives = getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] andongs = this.getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] zis1 = new int[6];
    int j = 0;
    int wx = 0;

    for(int i=0; i<andongs.length; i++) {
      if(andongs[i] == 0)
        break;
      zis1[j++] = andongs[i];
    }
    for(int i=0; i<actives.length; i++) {
      if(actives[i] == 0)
        break;
      zis1[j++] = actives[i];
    }
    for(int i=0; i<j; i++) {
      if (YiJing.DZLIUHE[zis1[i]][zi] == 1) {
        return true;
      }
      ;
    }

    for(int i=0; i<changes.length; i++) {
      if(dizi[changes[i]] != zi)
        continue;
      wx = YiJing.DZLIUHE[dizi[changes[i]]][diziBian[changes[i]]];
      if (wx > 0) {
        return true;
      }
    }

    if(YiJing.DZLIUHE[zi][rizi] == 1 || YiJing.DZLIUHE[zi][yuezi] == 1)
      return true;
    return false;
  }

  /**
   * 该静爻是否被冲
   * @return
   */
  public boolean isJingChong(StringBuffer str, int rigan, int rizi, int yuezi,
                             int[] changes, int[] dizi, int[] diziBian, int zi) {
    int[] actives = this.getActives(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] andongs = this.getAnDongs(str,rigan,rizi,yuezi,changes,dizi,diziBian,false);
    int[] bians = this.getDoBians(changes,dizi,diziBian);
    for(int i=0; i<actives.length; i++) {
      if(YiJing.DZCHONG[zi][actives[i]] == 1)
        return true;
    }
    for(int i=0; i<andongs.length; i++) {
      if(YiJing.DZCHONG[zi][andongs[i]] == 1)
        return true;
    }
    for(int i=0; i<bians.length; i++) {
      if(YiJing.DZCHONG[zi][bians[i]] == 1)
        return true;
    }
    if(YiJing.DZCHONG[zi][rizi] == 1 || YiJing.DZCHONG[zi][yuezi] == 1)
        return true;

      return false;
  }
}
