package org.boc.dao.sz;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuPublic {

  public String sep = "\r\n    ";
  public String kg = "    ";

  /**
   * 判断某个五行大八字中多
   * 1.数量>=4
   * 2.生它+自己>4 && 自己>=1
   * 3.会局或方局
   * 4.干合局或支六合或紧邻半合 && 自己>=2
   * @param type
   * @return
   */
  public boolean isTooManyWX(int type) {
    int j = 0;
    j = getHowWx(type);
    if(j>=4) return true;

    if(j<1)
      return false;

    if(type==YiJing.SHUI) j+=getHowWx(YiJing.JIN);
    if(type==YiJing.MU) j+=getHowWx(YiJing.SHUI);
    if(type==YiJing.HUO) j+=getHowWx(YiJing.MU);
    if(type==YiJing.TU) j+=getHowWx(YiJing.HUO);
    if(type==YiJing.JIN) j+=getHowWx(YiJing.TU);
    if(j>4) return true;

    if(j>1 &&
       ((isTF(6,10,2,18,2,10) && type==YiJing.JIN) ||
       (isTF(9,10,11,30,9,11) && type==YiJing.JIN) ||
       (isTF(12,4,8,24,4,12) && type==YiJing.MU) ||
       (isTF(3,4,5,12,3,5) && type==YiJing.MU) ||
       (isTF(9,1,5,15,1,9) && type==YiJing.SHUI) ||
       (isTF(12,1,2,15,1,12) && type==YiJing.SHUI) ||
       (isTF(3,7,11,21,3,11) && type==YiJing.HUO) ||
       (isTF(6,7,8,21,6,8) && type==YiJing.HUO)))
      return true;

    return false;
  }

  /**
   * 判断命局中是否有配偶星天干
   */
  public int[] isHasPOXOfMing(int ss) {
    int[] pos = new int[4];
    int i = 0;

    int sswx = YiJing.TIANGANWH[getShenShaName(ss,1)[0]];
    if(YiJing.TIANGANWH[SiZhu.ng] == sswx)
      pos[i++] = SiZhu.ng;
    if(YiJing.TIANGANWH[SiZhu.yg] == sswx)
      pos[i++] = SiZhu.yg;
    if(YiJing.TIANGANWH[SiZhu.rg] == sswx)
      pos[i++] = SiZhu.rg;
    if(YiJing.TIANGANWH[SiZhu.sg] == sswx)
      pos[i++] = SiZhu.sg;

    return pos;
  }

  /**
   * 判断大运或流年天干是否是配偶星
   */
  public boolean isPOX(int ss, int g) {
    int sswx = YiJing.TIANGANWH[getShenShaName(ss,1)[0]];
    return sswx==YiJing.TIANGANWH[g];
  }


  /**
   * 是否六合
   */
  public int isLiuHe(int z1, int z2) {
    return (YiJing.DZLIUHE[z1][z2]);
  }

  /**
   * 该支是否与命局有六合不含日支
   */
  public int isLiuHe(int z) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz, z};
    int wx = YiJing.DZLIUHE[z][SiZhu.nz];
    if(wx > 0) return wx;
    wx = YiJing.DZLIUHE[z][SiZhu.yz];
    if(wx > 0) return wx;
    wx = YiJing.DZLIUHE[z][SiZhu.sz];
    if(wx > 0) return wx;

    return 0;
  }


  /**
   * 是否五合
   */
  public int isWuHe(int z1, int z2) {
    return (YiJing.TGWUHE[z1][z2]);
  }

  /**
   * 是否三会大运流年日支
   */
  public int isSanHuiOfRizhu(int z1, int z2, int z3) {
    return (YiJing.DZSHANHUI[z1][z2][z3]);
  }

  /**
   * 是否三会大运流年不含日支的命局
   */
  public int isSanHui(int z1, int z2) {
    int wx = (YiJing.DZSHANHUI[z1][z2][SiZhu.nz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][z2][SiZhu.yz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][z2][SiZhu.sz]);
    if(wx>0) return wx;
    return 0;
  }

  /**
   * 是否三会流年含日支的命局
   */
  public int isSanHuiOfRizhu(int z1) {
    int wx = (YiJing.DZSHANHUI[z1][SiZhu.rz][SiZhu.nz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][SiZhu.rz][SiZhu.yz]);
    if(wx>0) return wx;
    wx = (YiJing.DZSHANHUI[z1][SiZhu.rz][SiZhu.sz]);
    if(wx>0) return wx;
    return 0;
  }

  /**
   * 是否三会流年不含日支的命局
   */
  public int isSanHui(int z1) {
        int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
        for(int i=0; i<zs.length; i++) {
          for(int j=0; j<zs.length; j++) {
            if(zs[j]==zs[i]) continue;
            if(YiJing.DZSHANHUI[zs[i]][zs[j]][z1] > 0)
              return YiJing.DZSHANHE[zs[i]][zs[j]][z1];
          }
        }
        return 0;
  }

  /**
   * 流年与命局是否三合局，不含日支
   */
  public int isSanHe(int z) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
    for(int i=0; i<zs.length; i++) {
      for(int j=0; j<zs.length; j++) {
        if(zs[j]==zs[i]) continue;
        if(YiJing.DZSHANHE[zs[i]][zs[j]][z] > 0)
          return YiJing.DZSHANHE[zs[i]][zs[j]][z];
      }
    }
    return 0;
  }

  /**
   * 流年与命局包含日支是否三合局
   */
  public int isSanHeOfRizhu(int z) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
    for(int i=0; i<zs.length; i++) {
      if(YiJing.DZSHANHE[zs[i]][SiZhu.rz][z] > 0)
        return YiJing.DZSHANHE[zs[i]][SiZhu.rz][z];
    }
    return 0;
  }


  /**
   * 流年与命局与大运是否三合局不含日支
   */
  public int isSanHe(int z1, int z2) {
    int[] zs = {SiZhu.nz, SiZhu.yz, SiZhu.sz};
    for (int i = 0; i < zs.length; i++) {
      if (YiJing.DZSHANHE[zs[i]][z1][z2] > 0)
        return YiJing.DZSHANHE[zs[i]][z1][z2];
    }
    return 0;
  }

  /**
   * 流年与大运与日支是否三合局
   */
  public int isSanHeOfRizhu(int z1, int z2) {
    return YiJing.DZSHANHE[z1][z2][SiZhu.rz];
  }


  /**
   * 流年与日支是否三合中的旺半合
   */
  public int isWangBanHe(int z1, int z2) {
    return (SiZhu.wangbanhe[z1][z2]);
  }

  /**
   * 盖头
   * 截脚：反克 生支 克支(木逢金火戌丑 火逢水金丑辰 土逢木金水 金逢火木水 水逢木火戌未)
   * 盖1 截2
   * type 1断干  2断支
   * @return
   */
  public int getGaiTouJieJiao(int g, int z, int type) {
    if(type == 2) {
      if (YiJing.WXDANKE[YiJing.TIANGANWH[g]][YiJing.DIZIWH[z]] > 0) {
        return 1;
      }
    }else{
      if ( (YiJing.WXDANSHENG[YiJing.TIANGANWH[g]][YiJing.DIZIWH[z]] > 0 ||
            YiJing.WXDANKE[YiJing.DIZIWH[z]][YiJing.TIANGANWH[g]] > 0 ||
            YiJing.WXDANKE[YiJing.TIANGANWH[g]][YiJing.DIZIWH[z]] > 0) &&
          !isHasMu(g, z) && !isHasYuqi(g, z) &&
          YiJing.TIANGANWH[g] != YiJing.DIZIWH[z] &&
          YiJing.WXDANSHENG[YiJing.DIZIWH[z]][YiJing.TIANGANWH[g]] <= 0) {
        return 2;
      }
    }

    return 0;
  }

  /**
   * 由指的五行得到其为日主的神煞
   */
  public int getShenShaByWX(int wx) {
    if(YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.rg]][wx]>0)
      return 2;
    else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.rg]][wx]>0)
      return 1;
    else if(YiJing.WXDANKE[wx][YiJing.TIANGANWH[SiZhu.rg]]>0)
      return 3;
    else if(YiJing.WXDANSHENG[wx][YiJing.TIANGANWH[SiZhu.rg]]>0)
      return 4;
    return 0;
  }

  /**
   * 判断指定的干是否有根
   * @param g
   * @return
   */
  public boolean isYouGen(int g) {
    return isHasRen(g) || isHasLu(g) || isHasMu(g) || isHasYuqi(g) || isHasChangS(g);
  }

  /**
   * 指定的干是否有阳刃
   * @return
   */
  public boolean isHasRen(int g) {
    boolean b = false;

    b = (SiZhu.YANGREN[g][SiZhu.nz] ||
         SiZhu.YANGREN[g][SiZhu.yz] ||
         SiZhu.YANGREN[g][SiZhu.rz] ||
         SiZhu.YANGREN[g][SiZhu.sz]);
    if(b) return b;

    if(g==YiJing.WUG) {
      b = (SiZhu.nz == YiJing.CHEN || SiZhu.nz == YiJing.XU ||
           SiZhu.yz == YiJing.CHEN || SiZhu.yz == YiJing.XU ||
           SiZhu.rz == YiJing.CHEN || SiZhu.rz == YiJing.XU ||
           SiZhu.sz == YiJing.CHEN || SiZhu.sz == YiJing.XU);
      if (b)  return b;
    }
    if(g==YiJing.JI) {
      b = (SiZhu.nz == YiJing.CHOU || SiZhu.nz == YiJing.WEI ||
           SiZhu.yz == YiJing.CHOU || SiZhu.yz == YiJing.WEI ||
           SiZhu.rz == YiJing.CHOU || SiZhu.rz == YiJing.WEI ||
           SiZhu.sz == YiJing.CHOU || SiZhu.sz == YiJing.WEI);
      if (b)  return b;
    }
    return false;
  }

  /**
   * 指定的干是否有禄
   * @return
   */
  public boolean isHasLu(int g) {
    boolean b = false;

    b =  (SiZhu.LUSHEN[g][SiZhu.nz] ||
          SiZhu.LUSHEN[g][SiZhu.yz] ||
          SiZhu.LUSHEN[g][SiZhu.rz] ||
          SiZhu.LUSHEN[g][SiZhu.sz]);
    if(b) return b;

    if(g==YiJing.JI) {
      b = (SiZhu.nz == YiJing.CHEN || SiZhu.nz == YiJing.XU ||
           SiZhu.yz == YiJing.CHEN || SiZhu.yz == YiJing.XU ||
           SiZhu.rz == YiJing.CHEN || SiZhu.rz == YiJing.XU ||
           SiZhu.sz == YiJing.CHEN || SiZhu.sz == YiJing.XU);
      if (b)  return b;
    }
    if(g==YiJing.WUG) {
      b = (SiZhu.nz == YiJing.CHOU || SiZhu.nz == YiJing.WEI ||
           SiZhu.yz == YiJing.CHOU || SiZhu.yz == YiJing.WEI ||
           SiZhu.rz == YiJing.CHOU || SiZhu.rz == YiJing.WEI ||
           SiZhu.sz == YiJing.CHOU || SiZhu.sz == YiJing.WEI);
      if (b)  return b;
    }
    return false;

  }

  /**
   * 指定的干是否有墓
   * @return
   */
  public boolean isHasMu(int g) {
    return (SiZhu.TGSWSJ[g][SiZhu.nz]==9 ||
       SiZhu.TGSWSJ[g][SiZhu.yz]==9  ||
       SiZhu.TGSWSJ[g][SiZhu.rz]==9  ||
       SiZhu.TGSWSJ[g][SiZhu.sz]==9 );
  }

  /**
   * 指定的干是否有余气
   * @return
   */
  public boolean isHasYuqi(int g) {
    if(YiJing.TIANGANWH[g]==YiJing.MU) {
      return getGzNum(YiJing.WEI,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.HUO) {
      return getGzNum(YiJing.XU,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.JIN) {
      return getGzNum(YiJing.CHOU,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.SHUI) {
      return getGzNum(YiJing.CHEN,2)>0;
    }

    return false;
  }

  /**
   * 指定的干是否是指定的支的墓
   * @return
   */
  public boolean isHasMu(int g, int z) {
    return (SiZhu.TGSWSJ[g][z]==9);
  }

  /**
   * 指定的干是否是指定的支的余气
   * @return
   */
  public boolean isHasYuqi(int g, int z) {
    if(YiJing.TIANGANWH[g]==YiJing.MU) {
      return (z == YiJing.WEI);
    }else if(YiJing.TIANGANWH[g]==YiJing.HUO) {
      return (z == YiJing.XU);
    }else if(YiJing.TIANGANWH[g]==YiJing.JIN) {
      return (z == YiJing.CHOU);
    }else if(YiJing.TIANGANWH[g]==YiJing.SHUI) {
      return (z == YiJing.CHEN);
    }

    return false;
  }


  /**
   * 指定的干是否有长生
   * @return
   */
  public boolean isHasChangS(int g) {
    if(YiJing.TIANGANWH[g]==YiJing.MU) {
      return getGzNum(YiJing.HAI,2)+getGzNum(YiJing.ZI,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.HUO) {
      return getGzNum(YiJing.YIN,2)+getGzNum(YiJing.MAO,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.JIN) {
      return getGzNum(YiJing.CHEN,2)+getGzNum(YiJing.XU,2) +
          getGzNum(YiJing.CHOU,2)+getGzNum(YiJing.WEI,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.SHUI) {
      return getGzNum(YiJing.SHEN,2)+getGzNum(YiJing.YOU,2)>0;
    }else if(YiJing.TIANGANWH[g]==YiJing.TU) {
      return getGzNum(YiJing.SI,2)+getGzNum(YiJing.WUZ,2)>0;
    }

    return false;
  }


  /**
   * 指定的支是否是指定的干的禄
   * @return
   */
  public boolean isLu(int g ,int z) {
    return (SiZhu.LUSHEN[g][z]);

  }

  /**
   * 指定的支是否是指定的干的刃
   * @return
   */
  public boolean isRen(int g ,int z) {
    return (SiZhu.YANGREN[g][z]);

  }

  /**
   * 得到指定的支神煞是否受冲 0不冲 10紧冲 11隔支冲 12 遥冲 99妒二支冲
   * @param ss
   * @return
   */
  public int getShenShaIsChong(int ss) {
    int[] iret = new int[3];
    int j=0;
    int gz = getShenShaName(ss,2)[0];

    if(gz==SiZhu.nz) {
      if (YiJing.DZCHONG[SiZhu.yz][gz] > 0)  iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.rz][gz] > 0) iret[j++] = 11;
      else if (YiJing.DZCHONG[SiZhu.sz][gz] > 0) iret[j++] = 12;
    }
    if(gz==SiZhu.yz) {
      if (YiJing.DZCHONG[SiZhu.nz][gz] > 0)  iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.rz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.sz][gz] > 0) iret[j++] = 11;
    }
    if(gz==SiZhu.rz) {
      if (YiJing.DZCHONG[SiZhu.yz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.sz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.nz][gz] > 0)  iret[j++] = 11;
    }
    if(gz==SiZhu.sz) {
      if (YiJing.DZCHONG[SiZhu.rz][gz] > 0) iret[j++] = 10;
      else if (YiJing.DZCHONG[SiZhu.yz][gz] > 0) iret[j++] = 11;
      else if (YiJing.DZCHONG[SiZhu.nz][gz] > 0)  iret[j++] = 12;
    }

    if(j==2)
      return 99;

    //此处只在大运中判断是否有年月日时支冲克此神煞
    if(j==0){
      if(YiJing.DZCHONG[SiZhu.nz][gz] > 0 ||
         YiJing.DZCHONG[SiZhu.nz][gz] > 0 ||
         YiJing.DZCHONG[SiZhu.nz][gz] > 0 ||
         YiJing.DZCHONG[SiZhu.nz][gz] > 0)
        return -1;
    }
    return iret[0];
  }


  /**
   * 得到指定的干支神煞是否受克 0不克 10紧克 11隔干克 12 遥克 //99妒克
   * @param ss
   * @return
   */
  public int getShenShaIsKe(int ss, int gztype) {
    int iret = 0;
    int gz = getShenShaName(ss,gztype)[0];

    if(gztype==1) {
      if(gz==SiZhu.ng) {
        if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yg) {
        if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sg) {
        if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
    }else{
      if(gz==SiZhu.nz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.rz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sz) {
        if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
    }

    return iret;
  }

  /**
   * 得到指定的干支神煞是否受生 0不生 10紧生 11隔干生 12 遥生
   * @param ss
   * @return
   */
  public int getShenShaIsSheng(int ss, int gztype) {
    int iret = 0;
    int gz = getShenShaName(ss,gztype)[0];

    if(gztype==1) {
      if(gz==SiZhu.ng) {
        if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yg) {
        if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.sg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sg) {
        if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.yg]][YiJing.TIANGANWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.ng]][YiJing.TIANGANWH[gz]] > 0)
          iret = 12;
      }
    }else{
      if(gz==SiZhu.nz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
      if (gz == SiZhu.yz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.rz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.sz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
      }
      if (gz == SiZhu.sz) {
        if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.rz]][YiJing.DIZIWH[gz]] > 0)
          iret = 10;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.yz]][YiJing.DIZIWH[gz]] > 0)
          iret = 11;
        else if (YiJing.WXDANSHENG[YiJing.DIZIWH[SiZhu.nz]][YiJing.DIZIWH[gz]] > 0)
          iret = 12;
      }
    }

    return iret;
  }

  /**
   * 得到指定的干支神煞是否相合 0不合 10紧合 11隔干合 12 遥合 99妒合
   * @param ss
   * @return
   */
  public int getShenShaIsHe(int ss, int gztype) {
    int[] iret = new int[3];
    int j = 0;
    int gz = getShenShaName(ss,gztype)[0];

    if(gztype==1) {
      if(gz==SiZhu.ng) {
        if (YiJing.TGHE[SiZhu.yg][gz] > 0)
          iret[j++] = 10;
        else if (YiJing.TGHE[SiZhu.sg][gz] > 0)
          iret[j++] = 12;
      }
      if (gz == SiZhu.yg) {
        if (YiJing.TGHE[SiZhu.ng][gz] > 0)
          iret[j++] = 10;
        else if (YiJing.TGHE[SiZhu.sg][gz] > 0)
          iret[j++] = 11;
      }
      if (gz == SiZhu.sg) {
        if (YiJing.TGHE[SiZhu.yg][gz] > 0)
          iret[j++] = 11;
        else if (YiJing.TGHE[SiZhu.ng][gz] > 0)
          iret[j++] = 12;
      }

      //此处只在大运中判断是否有年月日时支冲克此神煞
      if(j==0){
        if(YiJing.TGHE[SiZhu.ng][gz] > 0 ||
           YiJing.TGHE[SiZhu.yg][gz] > 0 ||
           YiJing.TGHE[SiZhu.rg][gz] > 0 ||
           YiJing.TGHE[SiZhu.sg][gz] > 0)
          return -1;
      }
    }else{
      if(gz==SiZhu.nz) {
        if (YiJing.DZLIUHE[SiZhu.yz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.rz][gz] > 0) iret[j++] = 11;
        else if (YiJing.DZLIUHE[SiZhu.sz][gz] > 0) iret[j++] = 12;
      }
      if(gz==SiZhu.yz) {
        if (YiJing.DZLIUHE[SiZhu.nz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.rz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.sz][gz] > 0) iret[j++] = 11;
      }
      if(gz==SiZhu.rz) {
        if (YiJing.DZLIUHE[SiZhu.yz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.sz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.nz][gz] > 0) iret[j++] = 11;
      }
      if(gz==SiZhu.sz) {
        if (YiJing.DZLIUHE[SiZhu.rz][gz] > 0) iret[j++] = 10;
        else if (YiJing.DZLIUHE[SiZhu.yz][gz] > 0) iret[j++] = 11;
        else if (YiJing.DZLIUHE[SiZhu.nz][gz] > 0) iret[j++] = 12;
      }

      //此处只在大运中判断是否有年月日时支冲克此神煞
      if(j==0){
        if(YiJing.DZLIUHE[SiZhu.nz][gz] > 0 ||
           YiJing.DZLIUHE[SiZhu.yz][gz] > 0 ||
           YiJing.DZLIUHE[SiZhu.rz][gz] > 0 ||
           YiJing.DZLIUHE[SiZhu.sz][gz] > 0)
          return -1;
      }
    }
    if(iret[1]>0)
      return 99;
    return iret[0];
  }

  /**
   * 判断两神的位置
   * 0 紧贴 1 隔支 2 遥隔
   */
  public int getTwoShenLocation(int[] loc1, int[] loc2) {
    if(loc1[0]+loc2[0]==3 || loc1[0]+loc2[1]==3 || loc1[1]+loc2[0]==3 || loc1[1]+loc2[1]==3)
      return 0;
    else if(loc1[0]+loc2[0]==5 || loc1[0]+loc2[1]==5 || loc1[1]+loc2[0]==5 || loc1[1]+loc2[1]==5)
      return 2;
    else if(loc1[0]+loc2[0]==6 || loc1[0]+loc2[1]==6 || loc1[1]+loc2[0]==6 || loc1[1]+loc2[1]==6)
      return 1;
    return -1;
  }

  /**
   * 得到指定的干或支的位置
   * @param gz
   * @param gztype
   * @return
   */
  public int[] getGzLocation(int gz, int gztype){
    int j=0;
    int[] loc = new int[4];
    int[] gzs = new int[4];

    if(gztype==1) {
      gzs = new int[]{SiZhu.ng, SiZhu.yg, 0, SiZhu.sg};
    }else {
      gzs = new int[]{SiZhu.nz, SiZhu.yz, SiZhu.rz, SiZhu.sz};
    }

    for(int i=0; i<gzs.length; i++) {
      if(gzs[i] == gz)
        loc[j++] = i+1;
    }
    return loc;
  }

  /**
   * 由指定的神煞类型与天干或地支的位置得到其名字,日干为全局变量
   * @param ss 0主,1比肩 2劫财3 食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
   * @param gztype 1为干 2为支
   * @return
   */
  public int[] getShenShaName(int ss, int gztype) {
    int i = 0;
    for(i=1; i<=10; i++) {
      if(SiZhu.TGSHISHEN[SiZhu.rg][i] == ss)
        break;
    }

    if(gztype == 1) {
      return new int[]{i,0};
    }
    if(i==5)
      return new int[]{YiJing.CHEN,YiJing.XU};
    if(i==6)
      return new int[]{YiJing.CHOU,YiJing.WEI};
    if(i==1 || i==2 || i==7 || i==8)
      return new int[]{i+2,0};
    if(i==3 || i==4 || i==9)
      return new int[]{i+3,0};
    if(i==10)
      return new int[]{YiJing.ZI,0};
    return new int[]{SiZhu.rg,0};
  }

  /**
   * 由指定的神煞类型与天干或地支的位置得到其名字,日干为全局变量
   * @param ss 0比劫 1食伤 2财 3官杀 4印
   * @param gztype 1为干 2为支
   * @return
   */
  public int[] getShenShaName2(int ss, int gztype) {
    int[] name = new int[4];
    if(gztype==1) {
      name[0] = getShenShaName(ss * 2 + 1, gztype)[0];
      name[1] = getShenShaName(ss * 2 + 2, gztype)[0];
    }else {
      name[0] = getShenShaName(ss * 2 + 1, gztype)[0];
      name[1] = getShenShaName(ss * 2 + 2, gztype)[0];
      name[2] = getShenShaName(ss * 2 + 1, gztype)[1];
      name[3] = getShenShaName(ss * 2 + 2, gztype)[1];
    }

    return name;
  }

  /**
   * 由指定干或支类型判断局中是否有此干支
   * @param gz 干支
   * @param gztype 1为干 2为支
   * @return 真为没有此干或支
   */
  public boolean isNo(int gz, int gztype) {
    if(gztype==1){
      if(SiZhu.ng==gz || SiZhu.yg==gz || SiZhu.rg==gz || SiZhu.sg==gz)
        return false;
    }else{
      if(SiZhu.nz==gz || SiZhu.yz==gz || SiZhu.rz==gz || SiZhu.sz==gz)
        return false;
    }
    return true;
  }

  /**
   * 得到天干地支神煞描述
   * @return
   */
  public String getGanTouZiCangDesc() {
    String s = "";

    int g0 = getShenShaNum(1, 0);
    int g1 = getShenShaNum(1, 1);
    int g2 = getShenShaNum(1, 2);
    int g3 = getShenShaNum(1, 3);
    int g4 = getShenShaNum(1, 4);

    int z0 = getShenShaNum(2, 0);
    int z1 = getShenShaNum(2, 1);
    int z2 = getShenShaNum(2, 2);
    int z3 = getShenShaNum(2, 3);
    int z4 = getShenShaNum(2, 4);

    if (g0 > 1)
      s += "比劫重重";
    else if (g1 > 1)
      s += "食伤叠叠";
    else if (g2 > 1)
      s += "财星相叠";
    else if (g3 > 1)
      s += "官杀重重";
    else if (g4 > 1)
      s += "印枭叠叠";
    else if (g2 >= 1 && g3 >= 1 && g4 >= 1)
      s += "财官印俱全";
    else if (g2 >= 1 && g3 >= 1)
      s += "财官并透";
    else if (g3 >= 1 && g4 >= 1)
      s += "官印相卫";
    else if (g1 >= 1 && g3 >= 1)
      s += "食伤官杀并见";
    else if (g1 >= 1 && g4 >= 1)
      s += "印星食伤并见";
    else if (g2 >= 1 && g4 >= 1)
      s += "财印并透";
    else if (g2 >= 1 && g1 >= 1)
      s += "财与食伤相生";
    else
      s += "";

    return s;
  }

  /**
   * 得到十神的旺衰描述
   * @param type 0为比劫 1为食伤 2财才 3官杀 4印枭
   * @return
   */
  public String getShiShenWSDesc(int type) {
    int x = getShiShenCent(type);

    for(int i=0; i<SiZhu.baifen.length; i++) {
      if(x<SiZhu.baifen[i] && i==0) {
        return SiZhu.judgeWSName[i];
      }
      else if(x>=SiZhu.baifen[i] && i==3) {
        return SiZhu.judgeWSName[i + 1];
      }
      else if(x>=SiZhu.baifen[i] && x<SiZhu.baifen[i+1]) {
        return SiZhu.judgeWSName[i + 1];
      }
    }
    return null;
  }

  /**
   * 得到十神的旺衰
   * @param type 0为比劫 1为食伤 2财才 3官杀 4印枭
   * @return 1弱之极矣 2偏弱 3旺相 4强旺 5旺之极矣
   */
  public int getShiShenWS(int type) {
    int x = getShiShenCent(type);

    for(int i=0; i<SiZhu.baifen.length; i++) {
      if(x<SiZhu.baifen[i] && i==0) {
        return i+1;
      }
      else if(x>=SiZhu.baifen[i] && i==3) {
        return i+2;
      }
      else if(x>=SiZhu.baifen[i] && x<SiZhu.baifen[i+1]) {
        return i+2;
      }
    }
    return 0;
  }


  /**
   * 得到十神的旺衰分数
   * @param type 0为比劫 1为食伤 2财才 3官杀 4印枭
   * @return
   */
  public int getShiShenCent(int type) {

    if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.MU) {
      if(type == 0) {
        return SiZhu.muCent;
      }else if(type == 1) {
        return SiZhu.huoCent;
      }else if(type ==2) {
        return SiZhu.tuCent;
      }else if(type == 3) {
        return SiZhu.jinCent;
      }else if(type == 4) {
        return SiZhu.shuiCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.HUO) {
      if(type == 0) {
        return SiZhu.huoCent;
      }else if(type == 1) {
        return SiZhu.tuCent;
      }else if(type ==2) {
        return SiZhu.jinCent;
      }else if(type == 3) {
        return SiZhu.shuiCent;
      }else if(type == 4) {
        return SiZhu.muCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.TU) {
      if(type == 0) {
        return SiZhu.tuCent;
      }else if(type == 1) {
        return SiZhu.jinCent;
      }else if(type ==2) {
        return SiZhu.shuiCent;
      }else if(type == 3) {
        return SiZhu.muCent;
      }else if(type == 4) {
        return SiZhu.huoCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.JIN) {
      if(type == 0) {
        return SiZhu.jinCent;
      }else if(type == 1) {
        return SiZhu.shuiCent;
      }else if(type ==2) {
        return SiZhu.muCent;
      }else if(type == 3) {
        return SiZhu.huoCent;
      }else if(type == 4) {
        return SiZhu.tuCent;
      }
    }else if(YiJing.TIANGANWH[SiZhu.rg] == YiJing.SHUI) {
      if(type == 0) {
        return SiZhu.shuiCent;
      }else if(type == 1) {
        return SiZhu.muCent;
      }else if(type ==2) {
        return SiZhu.huoCent;
      }else if(type == 3) {
        return SiZhu.tuCent;
      }else if(type == 4) {
        return SiZhu.jinCent;
      }
    }
    return 0;
  }

  /**
   * 得到指定的干或支的某种神煞个数
   * @param gz 1干 2支
   * @param type 0为比劫 1为食伤 2财才 3官杀 4印枭
   * @return
   */
  public int getShenShaNum(int gz, int type) {
    int c1 = 0;
    int[] wxs = {
        YiJing.TIANGANWH[SiZhu.ng], YiJing.TIANGANWH[SiZhu.yg],
        YiJing.TIANGANWH[SiZhu.sg]};
    int rgwx = YiJing.TIANGANWH[SiZhu.rg];
    if (gz == 2)
      wxs = new int[] {
          YiJing.DIZIWH[SiZhu.nz], YiJing.DIZIWH[SiZhu.yz],
          YiJing.DIZIWH[SiZhu.sz]};

    if (type == 0) {
      for (int i = 0; i < wxs.length; i++) {
        if (wxs[i] == rgwx)
          c1++;
      }
    }
    if (type == 1) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANSHENG[rgwx][wxs[i]] > 0)
          c1++;
      }
    }
    if (type == 2) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANKE[rgwx][wxs[i]] > 0)
          c1++;
      }
    }
    if (type == 3) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANKE[wxs[i]][rgwx] > 0)
          c1++;
      }
    }
    if (type == 4) {
      for (int i = 0; i < wxs.length; i++) {
        if (YiJing.WXDANSHENG[wxs[i]][rgwx] > 0)
          c1++;
      }
    }

    return c1;
  }

  /**
   * 得到指定干支个数
   * @param x
   * @return
   */
  public int getGzNum(int x, int type) {
    int j = 0;
    if (type == 1) {
      if (SiZhu.ng == x)
        j++;
      if (SiZhu.yg == x)
        j++;
      if (SiZhu.rg == x)
        j++;
      if (SiZhu.sg == x)
        j++;
    }
    else {
      if (SiZhu.nz == x)
        j++;
      if (SiZhu.yz == x)
        j++;
      if (SiZhu.rz == x)
        j++;
      if (SiZhu.sz == x)
        j++;
    }

    return j;
  }

  /**
   * 得到指定干支个数
   * @param x
   * @return
   */
  public int getGzNum(int[] x, int type) {
    int j = 0;
    for(int i=0; i<x.length; i++) {
      if (type == 1) {
        if (SiZhu.ng == x[i])
          j++;
        if (SiZhu.yg == x[i])
          j++;
        if (SiZhu.rg == x[i])
          j++;
        if (SiZhu.sg == x[i])
          j++;
      }
      else {
        if (SiZhu.nz == x[i])
          j++;
        if (SiZhu.yz == x[i])
          j++;
        if (SiZhu.rz == x[i])
          j++;
        if (SiZhu.sz == x[i])
          j++;
      }
    }

    return j;
  }


  /**
   * 得到指定的五行有几个
   */
  public int getHowWx(int wx) {
    int j = 0;
    if (YiJing.DIZIWH[SiZhu.nz] == wx)
      j++;
    if (YiJing.DIZIWH[SiZhu.yz] == wx)
      j++;
    if (YiJing.DIZIWH[SiZhu.rz] == wx)
      j++;
    if (YiJing.DIZIWH[SiZhu.sz] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.ng] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.yg] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.rg] == wx)
      j++;
    if (YiJing.TIANGANWH[SiZhu.sg] == wx)
      j++;

    return j;
  }

  /**
   * 得到指定的五行有几个
   * gz 1为天干的各五行数，2为地支的各五行数
   */
  public int getHowWx(int wx, int gz) {
    int j = 0;

    if(gz==2) {
      if (YiJing.DIZIWH[SiZhu.nz] == wx)
        j++;
      if (YiJing.DIZIWH[SiZhu.yz] == wx)
        j++;
      if (YiJing.DIZIWH[SiZhu.rz] == wx)
        j++;
      if (YiJing.DIZIWH[SiZhu.sz] == wx)
        j++;
    }else {
      if (YiJing.TIANGANWH[SiZhu.ng] == wx)
        j++;
      if (YiJing.TIANGANWH[SiZhu.yg] == wx)
        j++;
      if (YiJing.TIANGANWH[SiZhu.rg] == wx)
        j++;
      if (YiJing.TIANGANWH[SiZhu.sg] == wx)
        j++;
    }

    return j;
  }


  /**
   * 判断xyz三者是否是指定的解
   */
  public boolean isTF(int x,int y,int z,int min,int max,int sum) {
    if(x+y+z==sum && getMin(x,y,z)==min && getMax(x,y,z)==max)
      return true;
    return false;
  }

  /**
   * 返回最小的
   */
  public int getMin(int i1,int i2,int i3){
    int i_1 = Math.min(i1,i2);
    int i_2 = Math.min(i1,i3);
    return Math.min(i_1,i_2);
  }
  public int getMin(int i1,int i2,int i3,int i4){
    int i_1 = Math.min(i1,i2);
    int i_2 = Math.min(i3,i4);
    return Math.min(i_1,i_2);
  }

  /**
   * 返回最大的
   */
  public int getMax(int i1,int i2,int i3){
    int i_1 = Math.max(i1,i2);
    int i_2 = Math.max(i1,i3);
    return Math.max(i_1,i_2);
  }
  public int getMax(int i1,int i2,int i3,int i4){
    int i_1 = Math.max(i1,i2);
    int i_2 = Math.max(i3,i4);
    return Math.max(i_1,i_2);
  }

  /**
   * 五行之宜忌
   */
  public String getWuXingYiJi(int type) {
    switch(type) {
      case YiJing.MU:
        return kg +"宜木，喜东方。可从事木材，木器，家具，装潢，木成品，纸业，种植，养花，育树苗，"+sep+
            "敬神物品，香料，植物性素食品等经营和事业；";
      case YiJing.HUO:
        return kg +"宜火，喜南方。可从事放光，照明，光学，高热，易燃，油类，酒精类，热饮食，食品，理发，化妆品，人身装饰品，文艺，"+sep+
            "文学，文具，文化学生，文人，作家，写作，撰文，教员，校长，秘书，出版，公务，正界等方面的经营和事业；";
      case YiJing.TU:
        return kg +"宜土，喜中央之地，本地。可从事土产，地产，农村，畜牧，布匹，服装，纺织，石料，石灰，山地，水泥，建筑，房产买卖，"+sep+
            "雨衣，雨伞，筑堤，容水物品，当铺，古董，中间人，律师，管理，买卖，设计，顾问，丧业，筑墓，墓地管理，僧尼等方面的经营和事业；";
      case YiJing.JIN:
        return kg +"宜金，喜西方。可从事精纤材或金属工具材料，坚硬，决断，武术，鉴定，总管，汽车，交通，金融，工程，种子，"+
            "开矿，民意代表，伐木，机械等方面的经营和工作；";
      case YiJing.SHUI:
        return kg +"宜水，喜北方。可从事航海，冷温不燃液体，冰水，鱼类，水产，水利，冷藏，冷冻，打捞，洗洁，扫除，流水，港口，泳池，湖池塘，浴池，"+sep+
            "冷食物买卖，飘游，奔波，流动，连续性，易变化，属水性质，音响性质，清洁性质，海上作业，迁旅，特技表演，运动，导游，旅行，玩具，"+sep+
            "魔术，记者，侦探，旅社，灭火器具，钓鱼器具，医疗业，药物经营，医生，护士，占卜等方面的经营和工作；";
    }
    return null;
  }

  /**
   * 十神心性
   * 0主,1比肩 2劫财 3食神 4伤官 5偏财 6正财 7偏官 8正官 9偏印 10正印
   */
  public String getShiShenXing(int type) {
    switch(type) {
      case 1:
        return kg +"比肩心性，稳健刚毅，勇敢冒险，积极进取，但易流于孤僻，缺乏合群，反为孤立寡合；";
      case 2:
        return kg +"劫财心性，热诚坦直，坚韧志旺，奋斗不屈，但易流于盲目，缺乏理智，反为蛮横冲动；";
      case 3:
        return kg +"食神心性，温文随和，带人宽厚，善良体贴，但易流于虚伪，缺乏是非，反为迂腐懦弱；";
      case 4:
        return kg +"伤官心性，聪明活跃，才华横溢，逞强好胜，但易流于任性，缺乏约束，反为桀傲不驯；";
      case 5:
        return kg +"偏财心性，慷慨重情，聪明机灵，乐观开朗，但易流于虚浮，缺乏节制，反为浮华风流；";
      case 6:
        return kg +"正财心性，勤劳节俭，踏实保守，任劳任怨，但易流于苟且，缺乏进取，反为懦弱无能；";
      case 7:
        return kg +"偏官心性，豪爽侠义，积极进取，威严机敏，但易流于偏激，叛逆霸道，反为堕落极端；";
      case 8:
        return kg +"正官心性，正直负责，端庄严肃，循规蹈矩，但易流于刻板，墨守成规，反为意志不坚；";
      case 9:
        return kg +"偏印心性，精明干练，反应机敏，多才多艺，但易流于孤独，缺乏人情，反为自私冷漠；";
      case 10:
        return kg +"正印心性，聪颖仁慈，淡薄名利，逆来顺受，但易流于庸碌，缺乏进取，反为迟钝消极；"+sep;
    }
    return null;
  }

  /**
   * 五行心性
   */
  public String[] getWuXingXing(int type) {
    switch(type) {
      case YiJing.MU:
        return new String[]{kg +"木主仁，其性直，其情和，其味酸，其色青。",
            "本命木盛，主人长得丰姿秀丽，骨骼修长，手足细腻，口尖发美，面色青白。为人有博爱恻隐之心，慈祥恺悌之意，清高慷慨，质朴无伪。",
            "本命木气太过，主人个子瘦长，头发稀少，性格偏狭，嫉妒不仁。",
            "本命木气死绝，主人眉眼不正，项长喉结，肌肉干燥，为人鄙下吝啬。"};
      case YiJing.HUO:
        return new String[]{kg +"火主礼，其性急，其情恭，其味苦，其色赤。",
            "本命火盛，主人头小脚长，上尖下阔，浓眉小耳，精神闪烁，为人谦和恭敬，纯朴急躁。",
            "本命火气太过，主人则黄瘦尖楞，语言妄诞，诡诈妒毒，做事有始无终。",
            "本命火衰，主人则黄瘦尖楞，语言妄诞，诡诈妒毒，做事有始无终。"};
      case YiJing.TU:
        return new String[]{kg +"土主信，其性重，其情厚，其味甘，其色黄。",
             "本命土盛，主人圆腰廓鼻，眉清木秀，口才声重。为人忠孝至诚，度量宽厚，言必行，行必果。",
             "本命土气太过，主人头脑僵化，愚拙不明，内向好静。",
             "本命土气不及，主人面色忧滞，面扁鼻低，为人狠毒乖戾，不讲信用，不通情理。"};
      case YiJing.JIN:
        return new String[]{kg +"金主义，其性刚，其情烈，其味辣，其色白。",
            "本命金盛，主人骨肉相称，面方白净，眉高眼深，体健神清。为人刚毅果断，疏财仗义，深知廉耻。",
            "本命金气太过，主人有勇无谋，贪欲不仁。",
            "本命金气不及，主人身材瘦小，为人刻薄内毒，喜淫好杀，吝啬贪婪。"};
      case YiJing.SHUI:
        return new String[]{kg +"水主智，其性聪，其情善，其味咸，其色黑。",
            "本命水旺，主人面黑有采，语言清和，为人深思熟虑，足智多谋，学识过人。",
            "本命水旺太过，主人好说是非，飘荡贪淫。",
            "本命水气不及，主人物短小，性情无常，胆小无略，行事反覆。"};
    }
    return null;

    }

  /**
   * 命宫心性
   */
  public String getMGXinXing(int type) {
    switch (type) {
      case YiJing.ZI:
        return kg +"子宫，天贵星，志气不凡，富裕清吉。" + sep +
            "子宫之人极少消极厌世。朋友情深，夫妇唱随，聪明笃实。有时标新立异，主观未免过强，致性情乖僻。然其意志坚强，不念宿怨，有宽宏大量。" + sep +
            "如逢岁运不佳，生理方面，可能影响心脏，或患血液循环之病，神思厌倦，怔忡不宁等症状。" ;
      case YiJing.CHOU:
        return kg +"丑宫，天厄星，先难得吉，离祖劳心，晚年吉。" + sep +
            "丑宫之人，志向蓬勃，好傲岸及教训之态度对人，或致信于人，博一时之敬仰，究以锋芒过露，易受怨咎，足以阻碍其事业之进展，" + sep +
            "如逢岁运不佳，生理方面，或患风湿，膝部痿弱，流行软脚病，及皮肤湿疹诸症。" ;
      case YiJing.YIN:
        return kg +"寅宫，天权星，聪明大器，中年有权柄。" + sep +
            "寅宫之人，秉性刚毅，富冲刺力，喜觅捷径，兼以性情活泼，谈吐精神，令人有深刻之印象，交游甚广，人缘极佳，老谋归隐，也将无法摆脱。" + sep +
            "如岁运不佳，生理方面，可能影响下肢及臀部，如癌瘤痈疽，痰水结聚，崇湿积痈，及血液不清等症。";
      case YiJing.MAO:
        return kg +"卯宫，天赦星，慷慨疏财，得权时需谦虚为上。" + sep +
            "卯宫之人，少时体弱，壮年强健，最易动怒酿争端经历挫折。自成年后性格严谨，观察力特强，于人事能作深度透视，善恶分明。" + sep +
            "如逢岁运不佳，生理方面，可能影响阴部，发生肾石，痔疮，脱肛，血毒等症。" ;
      case YiJing.CHEN:
        return kg +"辰宫，天如星，事多反复，机谋多能。" + sep +
            "辰宫之人，性格温和，彬彬有礼，乐于为人排解争端，反而惹起是非纠缠。看似随和，缺乏决断，其实天性使然。" + sep +
            "如逢岁运不佳，生理方面，可能影响肾部，水火不济，脊背软弱，及患脾肾两亏等症。" ;
      case YiJing.SI:
        return kg +"巳宫，天文星，文章振发，女命有好夫。" + sep +
            "巳宫之人，态度沉静，喜吹毛求疵，人多惧与接近，生活孤寂，好在心细于发，手段精明，假如经商做贾，尽能积少成大，前途无量。" + sep +
            "平时因深思过虑，致易沾轻微脑病，如逢岁运不佳，则影响腹部，身患肠胃，便秘，泄泻，痢疾等症。" ;
      case YiJing.WUZ:
        return kg +"午宫，天福星，荣华吉命。" + sep +
            "午宫之人，天生高贵，有野心。坚强的意志常不顾险阻，但有时傲气很盛，待人和蔼无非笼络手段。戒除骄态，自有成功之一日。" + sep +
            "如逢岁运不佳，生理方面，可能影响腰部，发生脊骨疼痛，风湿，黄疸等症。";
      case YiJing.WEI:
        return kg +"未宫，天驿星，一生劳碌，离祖始安。" + sep +
            "未宫之人，其性格则敏感而易发怒;貌为柔顺，内心则极固执。办事严谨，有很好的领悟力与观察力，相象力活跃但容易耽于声色之好。" + sep +
            "如逢岁运不佳，生理方面，可能患胃病，逆呃，消化不良等疾。" ;
      case YiJing.SHEN:
        return kg +"申宫，天孤星，不宜早婚，女命妨夫。" + sep +
            "申宫之人，具有双重性格，有时自信乐观有时疑虑。心极机警每能提出新颖意见。言论姿态，表露尤佳。若论事业则应有始有终，不可半途而废。" + sep +
            "如逢岁运不佳，生理方面，宜防肺胸两部，发生咳嗽，哮喘，气呃及呼吸器官之疾病。" ;
      case YiJing.YOU:
        return kg +"酉宫，天秘星，性情刚直，时有是非。" + sep +
            "酉宫之人，沉默宁静，思虑深远，心地善良，忠实可靠，迄年齿日增，主观日强，得一诤友，免流为刚愎自用。一生富大自然爱好，失意时尽能枕石漱流，自我解譬，涤荡烦襟。" + sep +
            "生理方面，如置岁运不佳，可能影响喉部，发生气管炎，心脏不宁等症。" ;
      case YiJing.XU:
        return kg +"戌宫，天艺星，心性平和，艺道有名。" + sep +
            "戌宫之人，对于事务一经计划即全力以赴，绝不踌躇顾虑。但缺乏忍耐性易躁进，应以冷静缜密思考为要。" + sep +
            "如逢岁运不佳，生理方面，可能影响头部，如眩晕，中风，思想错乱，牙筋疼痛等疾病。" ;
      case YiJing.HAI:
        return kg +"亥宫，天寿星，心慈明悟，克己助人。" + sep +
            "亥宫之人，情感极为浓厚，神经又属敏锐，需在世情通达处下功夫。至其生平无视个人财产，愿与亲友同处于恬逸之境，心休意美。" + sep +
            "如逢岁运不佳，生理方面，可能影响身体全部，或因感冒而成痛风症，或因血液有毒而化肿疮，以及关节炎，肢体麻痹等症。" + sep +
            "命宫带有华盖和空亡，并且有孤辰寡宿，特适合学艺之研究，成就也大。为人雅洁高致，财业自微，故应主名不主利。" + sep +
            "此格主孤独，虽贵少子，六亲亦属无情，可能将以沙门终老。" + sep +
            "命宫带羊刃，且命旺无依，性情刚烈，易于冲动，应注意流年与命宫支相冲克，以防意外灾祸。" + sep +
            "如八字清纯不杂，七杀化印相生且驾刃，则胆略才干，堪负方面责任，延誉四方，无论创办新事业，或任重大之职，皆能举轻若重。" + sep +
            "命宫有驿马，必远走他乡发展;命支座财星，主发财快速，事业有成，每可聚获横财而富。命支座七杀，可能成为外交官或出外征战的将领。";
    }
    return null;
  }





}
