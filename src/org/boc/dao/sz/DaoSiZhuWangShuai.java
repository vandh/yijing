package org.boc.dao.sz;

import org.boc.dao.DaoCalendar;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class DaoSiZhuWangShuai {
  private DaoCalendar daoC;
  private DaoYiJingMain daoY;
  private DaoSiZhuPublic pub;
  //public int jinxs = 0;
  //public int muxs = 0;
  //public int shuixs = 0;
  //public int huoxs = 0;
  //public int tuxs = 0;
  public String desc = "";
  public String _desc = "";
  private double jin, mu, shui, huo, tu;
  double _jin=0, _mu=0, _shui=0, _huo=0, _tu=0;
  private String _tttt = "";
  private String sep="。\r\n    ";

  public DaoSiZhuWangShuai() {
    daoC = new DaoCalendar();
    daoY = new DaoYiJingMain();
    pub = new DaoSiZhuPublic();
    jin = 0; mu = 0; shui=0; huo=0; tu=0;
  }

/**
 * 取格
 * @return
 */
public String getGeJu() {
  String s = "";

  //1. 正格
  if(getBaGe() > 0) s += _tttt;

  //2. 三会三合格
  if(getWuGe() > 0)  s += _tttt;

  //3. 从格
  if(getCongGe() > 0 ) s += _tttt;

  //4. 化气格
  if(getHuaQiGe() > 0 ) s += _tttt;

  //5. 建禄格
  if(isJianLu()) s += _tttt;

    //6. 月刃格
  if(getYueRen(YiJing.JIA, YiJing.MAO) || getYueRen(YiJing.GENG, YiJing.YOU) ||
     getYueRen(YiJing.REN, YiJing.ZI))
    s += _tttt;
  //6.1 其它月刃格,在无格可取情况下用
    if(getYueRenOtherGe(YiJing.YI,YiJing.YIN) || getYueRenOtherGe(YiJing.WUG,YiJing.WUZ) ||
       getYueRenOtherGe(YiJing.BING,YiJing.WUZ) || getYueRenOtherGe(YiJing.DING,YiJing.SI) ||
       getYueRenOtherGe(YiJing.JI,YiJing.SI) || getYueRenOtherGe(YiJing.XIN,YiJing.SHEN) ||
       getYueRenOtherGe(YiJing.GUI,YiJing.HAI))
      s += "月令阳刃，但刃乃凶暴之物，此处支有所藏故不以月刃格论，而为"+_tttt+sep;


  //7. 杂格
    if(_getOneOrTwo()!=null) s += _tttt;
    if(_getDaoCh()!=null) s += _tttt;
    if(_getChaoYang()!=null) s += _tttt;
    if(_getHeLu()!=null) s += _tttt;
    if(_getJingLanCa()!=null) s += _tttt;
    if(_getXingHeGe()!=null) s += _tttt;
    if(_getYaoHe()!=null) s += _tttt;
    if(_getZaGe()) s += _tttt;
    if(_getFeiTianLuMa()!=null) s += _tttt;

  return s;
}

public boolean isJianLu() {
  if(_getJianLu(YiJing.JIA, YiJing.YIN) || _getJianLu(YiJing.YI, YiJing.MAO) ||
       _getJianLu(YiJing.BING, YiJing.SI) || _getJianLu(YiJing.WUG, YiJing.SI) ||
       _getJianLu(YiJing.DING, YiJing.WUZ) || _getJianLu(YiJing.JI, YiJing.WUZ) ||
       _getJianLu(YiJing.GENG, YiJing.SHEN) || _getJianLu(YiJing.XIN, YiJing.YOU) ||
       _getJianLu(YiJing.REN, YiJing.HAI) || _getJianLu(YiJing.GUI, YiJing.ZI))
      return true;
    return false;
}

/**
   * 只以阳日干为刃，阴日干皆以月劫论
   * 此方法不为取格，为断用神用
   * @return
   */
  public boolean isYueRen() {
    if((SiZhu.rg==YiJing.JIA && SiZhu.yz==YiJing.MAO) ||
       (SiZhu.rg==YiJing.BING && SiZhu.yz==YiJing.WUZ) ||
       (SiZhu.rg==YiJing.WUG && SiZhu.yz==YiJing.WUZ) ||
       (SiZhu.rg==YiJing.GENG && SiZhu.yz==YiJing.YOU) ||
       (SiZhu.rg==YiJing.REN && SiZhu.yz==YiJing.ZI))
      return true;
    return false;
  }

  /**
   * 阴日干与月支同五行 阳日干的劫为刃 阳日干的比为禄
   * 此方法不为取格，为断用神用
   * @return
   */
  public boolean isYueJie() {
    if((SiZhu.rg%2==0) && YiJing.DIZIWH[SiZhu.yz]==YiJing.TIANGANWH[SiZhu.rg])
      return true;
    return false;
  }


public int getHuaQiGe() {
  if (_getHuaQiGe(YiJing.JIA, YiJing.JI,
                  new int[] {YiJing.CHEN, YiJing.XU, YiJing.CHOU, YiJing.WEI}
                  ,
                  new int[] {YiJing.JIA, YiJing.YI, YiJing.YIN, YiJing.MAO}
                  ,
                  "不见木，为化土格" + sep))
    return YiJing.TU;
  if (_getHuaQiGe(YiJing.YI, YiJing.GENG,
                  new int[] {YiJing.SI, YiJing.YOU, YiJing.CHOU, YiJing.SHEN}
                  ,
                  new int[] {YiJing.BING, YiJing.DING, YiJing.WUZ, YiJing.WUZ}
                  ,
                  "不见火，为化金格" + sep))
    return YiJing.JIN;
  if (_getHuaQiGe(YiJing.BING, YiJing.XIN,
                  new int[] {YiJing.SHEN, YiJing.ZI, YiJing.CHEN, YiJing.HAI}
                  ,
                  new int[] {YiJing.WUG, YiJing.JI, YiJing.WEI, YiJing.XU}
                  ,
                  "不见土，为化水格" + sep))
    return YiJing.SHUI;
  if (_getHuaQiGe(YiJing.DING, YiJing.REN,
                  new int[] {YiJing.HAI, YiJing.MAO, YiJing.WEI, YiJing.YIN}
                  ,
                  new int[] {YiJing.GENG, YiJing.XIN, YiJing.SHEN, YiJing.YOU}
                  ,
                  "不见金，为化木格" + sep))
    return YiJing.MU;
  if (_getHuaQiGe(YiJing.WUG, YiJing.GUI,
                  new int[] {YiJing.YIN, YiJing.WUZ, YiJing.XU, YiJing.SI}
                  ,
                  new int[] {YiJing.REN, YiJing.GUI, YiJing.HAI, YiJing.ZI}
                  ,
                  "不见水，为化火格" + sep))
    return YiJing.SHUI;
  return 0;
}

public int getWuGe() {
  _tttt = null;
  String _temp = "";
  int gejuNum = getFangOrHui();

  if(SiZhu.rg==1 || SiZhu.rg==2) {
    if(SiZhu.yz==3 || SiZhu.yz==4 || SiZhu.yz==5) {
      if(gejuNum==10 || gejuNum==11 ||gejuNum==50 ||gejuNum==51 )
        if(pub.isNo(YiJing.GENG,1) && pub.isNo(YiJing.XIN,1) &&
           pub.isNo(YiJing.SHEN,2) && pub.isNo(YiJing.YOU,2)) {
          if(gejuNum==1) _temp="地支三会寅卯辰东方木局";
          if(gejuNum==5) _temp="地支三合亥卯未东方木局";
          if(_temp.length()>6) {
            _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，生於春月，" + _temp +
                "，而无庚辛申酉，为曲直人寿格" + sep;
            return YiJing.MU;
          }
        }
    }
  }
  if(SiZhu.rg==3 || SiZhu.rg==4) {
    if(SiZhu.yz==6 || SiZhu.yz==7 || SiZhu.yz==8) {
      if(gejuNum==21 || gejuNum==20 ||gejuNum==60 ||gejuNum==61)
        if(pub.isNo(YiJing.REN,1) && pub.isNo(YiJing.GUI,1) &&
           pub.isNo(YiJing.HAI,2) && pub.isNo(YiJing.ZI,2)) {
          if(gejuNum==2) _temp="地支三会巳午未南方火局";
          if(gejuNum==6) _temp="地支三合寅午戌南方火局";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，生临夏月，" + _temp +
                  "，而无壬癸亥子，为炎上格" + sep;
              return YiJing.HUO;
            }
        }
    }
  }
  if(SiZhu.rg==5 || SiZhu.rg==6) {
    if(SiZhu.yz==5 || SiZhu.yz==8 || SiZhu.yz==11 || SiZhu.yz==2) {
      //if(gejuNum==1 || gejuNum==5)
        if(pub.isNo(YiJing.JIA,1) && pub.isNo(YiJing.YI,1) &&
           pub.isNo(YiJing.YIN,2) && pub.isNo(YiJing.MAO,2)) {
          if(SiZhu.nz+SiZhu.yz+SiZhu.rz+SiZhu.sz==2+5+8+11 &&
             SiZhu.nz*SiZhu.yz*SiZhu.rz*SiZhu.sz==2*5*8*11 &&
             pub.getMin(SiZhu.nz,SiZhu.yz,SiZhu.rz,SiZhu.sz)==2 &&
             pub.getMax(SiZhu.nz,SiZhu.yz,SiZhu.rz,SiZhu.sz)==11) _temp="地支全辰戌丑未";
          if((SiZhu.nz==2 || SiZhu.nz==5 || SiZhu.nz==8 || SiZhu.nz==11) &&
             (SiZhu.yz==2 || SiZhu.yz==5 || SiZhu.yz==8 || SiZhu.yz==11) &&
             (SiZhu.rz==2 || SiZhu.rz==5 || SiZhu.rz==8 || SiZhu.rz==11) &&
             (SiZhu.sz==2 || SiZhu.sz==5 || SiZhu.sz==8 || SiZhu.sz==11)) _temp="地支纯土";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，生於四季月，" + _temp +
                  "，而无甲乙寅卯，为稼穑格" + sep;
              return YiJing.TU;
            }
        }
    }
  }
  if(SiZhu.rg==7 || SiZhu.rg==8) {
    if(SiZhu.yz==9 || SiZhu.yz==10 || SiZhu.yz==11) {
      if(gejuNum==31 || gejuNum==30 ||gejuNum==71 ||gejuNum==70)
        if(pub.isNo(YiJing.BING,1) && pub.isNo(YiJing.DING,1) &&
           pub.isNo(YiJing.SI,2) && pub.isNo(YiJing.WUZ,2)) {
          if(gejuNum==3) _temp="地支三会申酉戌西方金局";
          if(gejuNum==7) _temp="地支三合巳酉丑西方金局";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，生於秋月，" + _temp +
                  "，而无丙丁巳午，为从革格" + sep;
              return YiJing.JIN;
            }
        }
    }
  }
  if(SiZhu.rg==9 || SiZhu.rg==10) {
    if(SiZhu.yz==12 || SiZhu.yz==1 || SiZhu.yz==2) {
      if(gejuNum==41 ||gejuNum==40 ||gejuNum==81 ||gejuNum==80)
        if(pub.isNo(YiJing.WUG,1) && pub.isNo(YiJing.JI,1) &&
           pub.isNo(YiJing.WEI,2) && pub.isNo(YiJing.XU,2)) {
          if(gejuNum==4) _temp="地支三会亥子丑北方水局";
          if(gejuNum==8) _temp="地支三合申子辰北方水局";
            if(_temp.length()>6) {
              _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，生於冬月，" + _temp +
                  "，而无戊己未戌，为润下格" + sep;
              return YiJing.SHUI;
            }
        }
    }
  }

  return 0;
}

private boolean _getZaGe() {
  _tttt = "";
  if(SiZhu.TGSWSJ[SiZhu.rg][SiZhu.sz]==4 && pub.getShiShenCent(3)==0)
    _tttt += "日禄居时没官星，号『青云得路』，此俗谓归禄格。\r\n";
  if(SiZhu.rg==YiJing.REN && (SiZhu.sz==YiJing.CHEN || SiZhu.rz==YiJing.CHEN))
    _tttt += "阳水叠逢辰位，是『壬骑龙背』之乡，此俗谓壬骑龙背格；喜辰寅字多，辰多贵，寅多富；官破宜伤制，最忌官多"+sep;
  if(SiZhu.rg==YiJing.YI && SiZhu.sz==YiJing.ZI)
    _tttt += "阴木独遇子时，为『六乙鼠贵』之地，名为聚贵为奇，此俗谓六乙鼠贵格；宜生于亥卯未月及春月，最喜丙子时，忌午冲，不宜见四柱见庚辛申酉丑"+sep;

  String _gong = "须日时同干，禄与月令通气，运行身、禄旺地大好，印绶、伤官、食神、财运亦吉；忌填实及刑冲破害、羊刃、七杀、空亡，伤了日时，则拱不住"+sep;
  if((SiZhu.rg==YiJing.GUI) && SiZhu.rz==YiJing.HAI &&
     SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.CHOU )
    _tttt += "癸亥日癸丑时，拱子禄，此俗谓拱禄格；" + _gong;
  if(SiZhu.rg==YiJing.GUI && SiZhu.rz==YiJing.CHOU &&
     SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.HAI )
    _tttt += "癸丑日癸亥时，拱子禄，此俗谓拱禄格；" + _gong;
  if(SiZhu.rg==YiJing.DING && SiZhu.rz==YiJing.SI &&
     SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.WEI )
    _tttt += "丁巳日丁未时，拱午禄，此俗谓拱禄格；" + _gong;
  if(SiZhu.rg==YiJing.JI && SiZhu.rz==YiJing.WEI &&
     SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.SI )
    _tttt += "己未日己巳时，拱午禄，此俗谓拱禄格；" + _gong;
  if(SiZhu.rg==YiJing.WUG && SiZhu.rz==YiJing.CHEN &&
     SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.WUZ )
    _tttt += "戊辰日戊午时，拱巳禄，此俗谓拱禄格；" + _gong;

  if(SiZhu.rg==YiJing.JIA && SiZhu.rz==YiJing.SHEN &&
     SiZhu.sg==YiJing.JIA && SiZhu.sz==YiJing.XU )
    _tttt += "甲申日甲戌时拱酉贵，即正官（天福贵人），此俗谓拱贵格；" + _gong;
  if(SiZhu.rg==YiJing.YI && SiZhu.rz==YiJing.WEI &&
     SiZhu.sg==YiJing.YI && SiZhu.sz==YiJing.YOU )
    _tttt += "乙未日乙酉时拱申贵，即正官（天福贵人），此俗谓拱贵格；" + _gong;
  if(SiZhu.rg==YiJing.JIA && SiZhu.rz==YiJing.YIN &&
     SiZhu.sg==YiJing.JIA && SiZhu.sz==YiJing.ZI)
    _tttt += "甲寅日甲子时拱丑贵，即拱玉堂贵人（阴贵、夜贵）与天乙贵人（阳贵、昼贵），此俗谓拱贵格；" + _gong;
  if(SiZhu.rg==YiJing.WUG && SiZhu.rz==YiJing.SHEN &&
     SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU )
    _tttt += "戊申日戊午时拱未贵，即拱玉堂贵人（阴贵、夜贵）与天乙贵人（阳贵、昼贵），此俗谓拱贵格；" + _gong;
  if(SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.CHOU &&
     SiZhu.sg==YiJing.XIN && SiZhu.sz==YiJing.MAO )
    _tttt += "辛丑日辛卯时拱寅贵，即拱玉堂贵人（阴贵、夜贵）与天乙贵人（阳贵、昼贵），此俗谓拱贵格；" + _gong;
  if(SiZhu.rg==YiJing.REN && SiZhu.rz==YiJing.CHEN &&
     SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.YIN )
    _tttt += "壬辰日壬寅时拱卯贵，即拱玉堂贵人（阴贵、夜贵）与天乙贵人（阳贵、昼贵），此俗谓拱贵格；" + _gong;

  if(SiZhu.rg==YiJing.JIA && SiZhu.sg==YiJing.YI && SiZhu.sz==YiJing.HAI) {
    _tttt += "甲日乙亥时，亥配乾卦，此俗谓六甲趋乾格；亥为天门、紫微垣，甲为十干之首，尊贵也。亥为甲木长生之宫，且暗合寅为甲禄，故又称为“合禄格”。四柱不喜见财星，又忌寅填实，巳冲刑，岁运同" +sep;
  }

  if(SiZhu.rg==YiJing.REN && SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.YIN) {
    _tttt += "壬日壬寅时，寅配艮卦为生门，合出亥为壬禄，此俗谓六壬趋艮格，又即“暗禄格”。忌亥字填实（逢亥月者贫）及刑冲克破。壬寅日、壬辰日为正。见寅字多者大富，以寅中甲木生丙火长生之财，财旺生官，故美。忌官杀损身，申冲寅、庚伤甲、午合寅" +sep;
  }

  if(pub.getGzNum(pub.getShenShaName(7,1),1)==1 &&
     pub.getGzLocation(pub.getShenShaName(7,1)[0],1)[0]==4)
    _tttt += "时上七杀一位独见，此俗谓时上一位贵格；须日旺，偏官有制，则贵" + sep;

  if(SiZhu.rg==YiJing.WUG &&
     (SiZhu.rz==YiJing.SHEN || SiZhu.rz==YiJing.ZI || SiZhu.rz==YiJing.CHEN )) {
   _tttt += "戊日主生申子辰日，此俗谓勾陈得位格；忌刑、冲、杀旺"+sep;
  }
  if(SiZhu.rg==YiJing.WUG &&
     getWuGe()==YiJing.SHUI && pub.getGzNum(YiJing.SHEN,2)>0 && pub.getGzNum(YiJing.CHEN,2)>0) {
   _tttt += "戊日主，地支申子辰三合，此俗谓勾陈得位格；忌刑、冲、杀旺"+sep;
  }
  if(SiZhu.rg==YiJing.JI &&
     (SiZhu.rz==YiJing.HAI || SiZhu.rz==YiJing.MAO || SiZhu.rz==YiJing.WEI )) {
   _tttt += "己日主生于亥卯未日，此俗谓勾陈得位格；忌刑、冲、杀旺"+sep;
  }
  if(SiZhu.rg==YiJing.JI &&
     getWuGe()==YiJing.MU && pub.getGzNum(YiJing.HAI,2)>0 && pub.getGzNum(YiJing.WEI,2)>0) {
   _tttt += "己日主，地支亥卯未三合，此俗谓勾陈得位格；忌刑、冲、杀旺"+sep;
  }

  if(SiZhu.rg==YiJing.REN &&
     (SiZhu.rz==YiJing.YIN || SiZhu.rz==YiJing.WUZ || SiZhu.rz==YiJing.XU )) {
   _tttt += "壬日主生于寅午戌日，此俗谓玄武当权格；忌水及刑冲"+sep;
  }
  if(SiZhu.rg==YiJing.REN &&
     getWuGe()==YiJing.HUO) {
   _tttt += "壬日主，地支合会火局，此俗谓勾陈得位格；忌水及刑冲"+sep;
  }
  if(SiZhu.rg==YiJing.GUI &&
     (SiZhu.rz==YiJing.SI || SiZhu.rz==YiJing.YOU || SiZhu.rz==YiJing.CHOU )) {
   _tttt += "癸日主生于丑酉巳日，此俗谓玄武当权格；忌水及刑冲"+sep;
  }
  if(SiZhu.rg==YiJing.GUI &&
     getWuGe()==YiJing.JIN) {
   _tttt += "癸日主，地支合会金局，此俗谓玄武当权格；忌水及刑冲"+sep;
  }

  if(((SiZhu.rg==YiJing.BING && SiZhu.rz==YiJing.ZI) ||
     (SiZhu.rg==YiJing.REN && SiZhu.rz==YiJing.ZI) ||
     (SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.MAO) ||
     (SiZhu.rg==YiJing.DING && SiZhu.rz==YiJing.YOU)) &&
      pub.getGzNum(YiJing.YI,1)>0) {
   _tttt += YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+"日有乙出干，此俗谓日德秀气格；忌刑冲克"+sep;
  }
  if(pub.getGzNum(YiJing.YI,1)>=3 && getFangOrHui()==YiJing.JIN &&
     !pub.isNo(YiJing.SI,2) && !pub.isNo(YiJing.CHOU,2)) {
   _tttt += "天干三乙会巳酉丑全者，此俗谓日德秀气格；忌刑冲克"+sep;
  }

  if(SiZhu.rg==YiJing.JI &&
    (SiZhu.rz==YiJing.SI || SiZhu.rz==YiJing.YOU || SiZhu.rz==YiJing.CHOU) &&
     getFangOrHui()==YiJing.JIN) {
   _tttt += "己日干生于巳酉丑日，合会金局，此俗谓福德格；忌刑冲及见火"+sep;
  }

  if(SiZhu.rg==YiJing.JIA && SiZhu.ng==YiJing.GENG) {
   _tttt += "甲日见庚年，此俗谓岁德扶杀格；杀有制则祖上有要职"+sep;
  }

  if(SiZhu.rg==YiJing.JIA && (SiZhu.ng==YiJing.WUG || SiZhu.ng==YiJing.JI)) {
   _tttt += "甲日见戊己年，此俗谓岁德扶财格；得祖上财产"+sep;
  }

  if(SiZhu.ng==SiZhu.yg && SiZhu.rg==SiZhu.sg && SiZhu.rg!=SiZhu.ng) {
   _tttt += "年月、日时连占两干，统一而不杂，此俗谓两干不杂格；利名"+sep;
  }

  String nayin = SiZhu.NAYIN[SiZhu.ng][SiZhu.nz]+
      SiZhu.NAYIN[SiZhu.yg][SiZhu.yz]+
      SiZhu.NAYIN[SiZhu.rg][SiZhu.rz]+
      SiZhu.NAYIN[SiZhu.sg][SiZhu.sz]+
      SiZhu.NAYIN[SiZhu.tyg][SiZhu.tyz]+
      SiZhu.NAYIN[SiZhu.mgg][SiZhu.mgz];
  if(nayin.indexOf("金")!=-1 && nayin.indexOf("木")!=-1 &&
     nayin.indexOf("水")!=-1 && nayin.indexOf("火")!=-1 &&
     nayin.indexOf("土")!=-1) {
   _tttt += "即年月日时胎之纳音刚好五行都有配齐，此俗谓五行俱足格"+sep;
  }


  if(_tttt==null || "".equals(_tttt))
    return false;
  return true;
}

public int getCongGe() {
  //3. 从格
  _tttt = null;

  if(SiZhu.muCent == 0 && SiZhu.huoCent == 0 && SiZhu.tuCent == 0 &&
     SiZhu.jinCent == 0 && SiZhu.shuiCent == 0) {
    getWuXingCent();
  }
  //生当财月，日主弱极<80, 财旺极>530
  if(YiJing.WXDANKE[YiJing.TIANGANWH[SiZhu.rg]][YiJing.DIZIWH[SiZhu.yz]]>0) {
    if(pub.getShiShenCent(0)<SiZhu.baifen[0] && pub.getShiShenCent(2)>=SiZhu.baifen[3]) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] +
          "日干，生於财月，财旺极而身弱极，委实不能任财，只得从之，为从财格" + sep;
      return 2;
    }
  }
  //生当杀月，日主弱极<80, 官杀旺极>530
  if(YiJing.WXDANKE[YiJing.DIZIWH[SiZhu.yz]][YiJing.TIANGANWH[SiZhu.rg]]>0) {
    if(pub.getShiShenCent(0)<SiZhu.baifen[0] && pub.getShiShenCent(3)>=SiZhu.baifen[3]) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，官杀旺极而身弱极，委实不能任杀，只得从之，为从杀格" +
          sep;
      return 3;
    }
  }
  //生当食伤月，日主弱极<80, 食伤旺极>530
  if(YiJing.WXDANSHENG[YiJing.TIANGANWH[SiZhu.rg]][YiJing.DIZIWH[SiZhu.yz]]>0) {
    if(pub.getShiShenCent(0)<SiZhu.baifen[0] && pub.getShiShenCent(1)>=SiZhu.baifen[3]) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，食伤旺极而身弱极，委实不能任其盗泄，只得从之，为从儿格" +
          sep;
      return 1;
    }
  }
  //日主、印旺极>
    if(pub.getShiShenCent(0)>=SiZhu.baifen[2] && pub.getShiShenCent(4)>=SiZhu.baifen[3] &&
       pub.getShiShenCent(2)==0 && pub.getShiShenCent(3)==0) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，身印两旺，而绝无一毫财星官杀之气，谓之二人同心。宜顺而不宜逆，为从强格"+sep;
      return 4;
    }
    //日主旺极>530
    if(pub.getShiShenCent(0)>=SiZhu.baifen[3] && pub.getShiShenCent(3)==0) {
      _tttt = YiJing.TIANGANNAME[SiZhu.rg] + "日干，旺极而无官杀制，或有印绶之生，只得从其旺神，为从旺格" + sep;
      return 5;
    }


    return 0;
}

private String _getYaoHe() {
  _tttt = null;
  if(((SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.CHOU) ||
     (SiZhu.rg==YiJing.GUI && SiZhu.rz==YiJing.CHOU)) &&
     pub.getGzNum(2, YiJing.CHOU)>1) {
     _tttt="丑中辛癸，遥合巳中丙火或戊土为官星，此俗谓丑遥巳格；"+sep+
         "若年支，时支见子或巳，则羁绊、填实而不能遥，反成虚名虚利。四柱中有申或酉之一字合住巳为妙，亦忌丙、丁、午，其中癸丑日又忌戌。"+sep;
  }
  if((SiZhu.rg==YiJing.JIA && SiZhu.rz==YiJing.ZI)) {
    if(SiZhu.jinCent == 0 && SiZhu.tuCent == 0)
     _tttt="甲子日甲子时，以子中癸水遥合巳中戊土，戊土动丙火，丙火合辛金，此俗谓子遥巳格"+sep+
         "大忌年月有午冲子、丑绊子，则不能遥合，亦忌见庚、辛、丙、申、酉、巳字为破格。喜生於壬、癸、亥、子印旺及寅、卯身旺之月。身强行官旺之运发达，忌南方运。"+sep;
  }

  return _tttt;
}

private String _getXingHeGe() {
  _tttt = null;
  if(SiZhu.rg==YiJing.GUI && SiZhu.sg==YiJing.JIA && SiZhu.sz==YiJing.YIN) {
     _tttt="六癸日时逢寅位，此俗谓刑合格；寅刑巳则财官全，运忌填实，不利金乡，喜柱中见酉丑，无官杀，不再见甲庚戊戌申己字，为贵，显赫"+sep;
  }

  return _tttt;
}

private String _getJingLanCa() {
  _tttt = null;
  if(SiZhu.rg==YiJing.GENG && pub.getGzNum(YiJing.GENG, 1)>=3 &&
     (SiZhu.rz==YiJing.CHEN || SiZhu.rz==YiJing.SHEN || SiZhu.rz==YiJing.ZI) &&
     (getWuGe()==YiJing.SHUI && pub.getGzNum(YiJing.SHEN,2)>0 && pub.getGzNum(YiJing.CHEN,2)>0)) {
      _tttt = "庚日全逢润下，以申子辰暗冲寅午戌火局，庚日得官星为贵，此俗谓井拦叉格；不利填实，最喜行东方财地，次者北方亦美。最忌官印，官煞克身，印绶制食，皆逆其旺势，时遇子申，其福减半"+sep;
  }
  return _tttt;
}

private String _getHeLu() {
  _tttt = null;
  if(SiZhu.rg==YiJing.WUG && SiZhu.sg==YiJing.GENG && SiZhu.sz==YiJing.SHEN &&
    (SiZhu.rz==YiJing.SHEN || SiZhu.rz==YiJing.ZI || SiZhu.rz==YiJing.CHEN || SiZhu.rz==YiJing.XU)) {
   _tttt="庚申时逢戊日，食神坐禄健旺，暗合乙卯官星，因其主而得其偶，此俗谓专食合禄格；运亦忌填实，官煞为犯其旺神，印星更伤食神秀气，卯字填实官星、寅字冲申（寅年申月尤忌）。喜秋冬生，食旺，爱财星、印绶，怕刑冲破害"+sep;
 }
 if(SiZhu.rg==YiJing.GUI && SiZhu.sg==YiJing.GENG && SiZhu.sz==YiJing.SHEN) {
   _tttt="癸日庚申时，以庚申暗合乙卯食禄（爵星、天厨禄），又以申暗合巳中戊官、庚印、丙财三奇俱全），又因其主而得其朋，此俗谓专印合禄格；喜生於秋冬，忌生於春夏及见戊、己、巳、午、丙克庚、寅冲申。喜行身旺、印旺，金水之运大发；忌入火运"+sep;
  }
  return _tttt;
}

private String _getChaoYang() {
  _tttt = null;
  if(SiZhu.rg == YiJing.XIN && SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.ZI)
    _tttt = "辛日主见戊子时，此俗谓六阴朝阳格，运喜土金水，木运平平，忌丙丁巳午官杀，忌丑合，年月财格以财格论，忌冲破"+sep;

  return _tttt;
}

private String _getDaoCh() {
  _tttt = null;
  int j=0;

  if (((SiZhu.rg==YiJing.BING && SiZhu.rz==YiJing.WUZ) ||
      (SiZhu.rg==YiJing.DING && SiZhu.rz==YiJing.SI)) &&
      (SiZhu.yz==YiJing.SI || SiZhu.yz==YiJing.WUZ || SiZhu.yz==YiJing.WEI) &&
      (pub.getGzNum(YiJing.SI,2)>=3 || pub.getGzNum(YiJing.WUZ,2)>=3)){
    _tttt = "又地支逢三必冲，丙以癸水为正官（禄），且柱中午多有力，冲出子中癸水（丁干亦同此），此俗谓倒冲格；更得丑、申、辰之一字暗合子为妙（多字则不可），忌未羁绊巳及亥壬填实" + sep;
  }

  return _tttt;
}

private String _getFeiTianLuMa() {
  _tttt = null;
  int j=0;

  if (((SiZhu.rg==YiJing.GENG && SiZhu.rz==YiJing.ZI) &&
       (SiZhu.rg==YiJing.REN && SiZhu.rz==YiJing.ZI) &&
        SiZhu.yz==YiJing.ZI && pub.getGzNum(YiJing.ZI,2)>=3)){
    _tttt = "子多并冲禄马，此俗谓飞天禄马格；忌官星显露，禄难飞冲；合神羁绊，不能飞冲（丑申辰绊子）。要柱中有一字合住，方不走了贵气（要柱中有未、寅、戌等任何一字暗合午）。喜伤官、食神及干支本运" + sep;
  }
  if (((SiZhu.rg==YiJing.XIN && SiZhu.rz==YiJing.HAI) &&
       (SiZhu.rg==YiJing.GUI && SiZhu.rz==YiJing.HAI) &&
       SiZhu.yz==YiJing.HAI && pub.getGzNum(YiJing.HAI,2)>=3)){
    _tttt = "亥多并冲禄马，此俗谓飞天禄马格；忌官星显露，禄难飞冲；合神羁绊，不能飞冲（寅绊亥）。要柱中有一字合住，方不走了贵气（要柱中有申、酉、丑等任何一字暗合巳）。喜伤官、食神及干支本运；" + sep;
  }

  return _tttt;
}


private String _getOneOrTwo() {
  _tttt = null;
  int j=0;

  if(SiZhu.muCent == 0) j++;
  if(SiZhu.huoCent == 0) j++;
  if(SiZhu.tuCent == 0) j++;
  if(SiZhu.jinCent == 0) j++;
  if(SiZhu.shuiCent == 0) j++;
  if(j==4) _tttt = "又干支清纯至极，此俗谓一行得气格"+sep;
  if(j==3) _tttt = "又干支清纯至极，此俗谓两神成象格"+sep;

  return _tttt;
}

/**
 * 八格
 * 比劫不在八格之内，而在外格禄刃格中
 */
public int getBaGe() {
  //虽比劫不能取格，但也不能就此返回，因为戊土生丑月，若透水还可取财格
  //if(YiJing.DIZIWH[SiZhu.yz] == YiJing.TIANGANWH[SiZhu.rg])
  //  return 0;

  int[] xc = SiZhu.DZXUNCANG[SiZhu.yz];
  int ngwx = YiJing.TIANGANWH[SiZhu.ng];
  int ygwx = YiJing.TIANGANWH[SiZhu.yg];
  int sgwx = YiJing.TIANGANWH[SiZhu.sg];
  int yzwx = 0;
  String _t = "";
  int whichxc = 0;

  for(int i=0; i<xc.length; i++) {
    yzwx = YiJing.TIANGANWH[xc[i]];
    whichxc = xc[i];
    _tttt = null;
    if(i==0) _t="月令本气";
    if(i>=1) _t="月令藏气";

    if (yzwx == ygwx && yzwx!=YiJing.TIANGANWH[SiZhu.rg]) {
      _tttt = _t + YiJing.TIANGANNAME[SiZhu.yg] +
          YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.yg]] +  "在月干透出，为" +
          SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][SiZhu.yg]] + "格"+sep;
      break;
    }
    else if (yzwx == ngwx && yzwx!=YiJing.TIANGANWH[SiZhu.rg]) {
      _tttt = _t + YiJing.TIANGANNAME[SiZhu.ng] + YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.ng]] +
          "在年干透出，为" + SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][SiZhu.ng]] + "格"+sep;
      break;
    }
    else if (yzwx == sgwx && yzwx!=YiJing.TIANGANWH[SiZhu.rg]) {
      _tttt = _t + YiJing.TIANGANNAME[SiZhu.sg] + YiJing.WUXINGNAME[YiJing.TIANGANWH[SiZhu.sg]] +
          "在时干透出，为" + SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][SiZhu.sg]] + "格"+sep;
      break;
    }
  }
  if(_tttt!=null)
    return SiZhu.TGSHISHEN[SiZhu.rg][whichxc];

  if(_tttt == null) {
    if(YiJing.DIZIWH[SiZhu.yz] == YiJing.TIANGANWH[SiZhu.rg]) {
      if(xc.length<=1)
        whichxc = xc[0];
      else
        whichxc = xc[1];
      _t = "月令本气藏气均不透，取其藏气";
    }else{
      whichxc = xc[0];
      _t = "月令本气藏气均不透，取其本气";
    }
    _tttt = _t + YiJing.TIANGANNAME[whichxc] +
        YiJing.WUXINGNAME[YiJing.TIANGANWH[whichxc]] + SiZhu.SHISHEN2[SiZhu.TGSHISHEN[SiZhu.rg][whichxc]] + "格"+sep;
    return SiZhu.TGSHISHEN[SiZhu.rg][whichxc];
  }

  return 0;
}

/**
 * 在无格可取情况下，取其它格
 */
private boolean getYueRenOtherGe(int x, int y) {
  _tttt = "";
  if(SiZhu.rg==x && SiZhu.yz==y) {
    //_tttt = YiJing.TIANGANNAME[x]+"日"+YiJing.DIZINAME[y]+"月，为";
    if(y==YiJing.YIN) {
      if(SiZhu.huoCent>=SiZhu.tuCent) _tttt += "丙火伤官格";
      else _tttt += "戊土偏财格";
    }else if(y==YiJing.WUZ) {
      if(x==YiJing.BING) _tttt += "己土伤官格";
      if(x==YiJing.WUG)  _tttt += "丁火正印格";
    }else if(y==YiJing.SI) {
      if(SiZhu.tuCent>=SiZhu.jinCent) {
        if (x == YiJing.DING) {
          _tttt += "戊土伤官格";
        }else{
          _tttt += "丙火正印格";
        }
      } else {
        if (x == YiJing.DING) {
          _tttt += "庚金正财格";
        }else{
         _tttt += "丙火正印格";
        }
      }
    }else if(y==YiJing.SHEN) {
      if(SiZhu.shuiCent>=SiZhu.tuCent) _tttt += "壬水伤官格";
      else _tttt += "戊土正印格";
    }else if(y==YiJing.HAI) {
      _tttt += "甲木伤官格";
    }

    return true;
  }
  return false;
}

/**
 * 月刃格
 */
private boolean getYueRen(int x, int y) {
  _tttt = null;
  if(SiZhu.rg==x && SiZhu.yz==y) {
    _tttt = YiJing.TIANGANNAME[x]+"日"+YiJing.DIZINAME[y]+"月，为月刃格"+sep;
    return true;
  }
  return false;
}

/**
 * 建禄格
 */
private boolean _getJianLu(int x, int y) {
  _tttt = null;
  if(SiZhu.rg==x && SiZhu.yz==y) {
    _tttt = YiJing.TIANGANNAME[x]+"日"+YiJing.DIZINAME[y]+"月，为建禄格"+sep;
    return true;
  }
  return false;
}

/**
 * //化气格
 * @return
 */
private boolean _getHuaQiGe(int x, int y, int[] yz, int[] nozi, String name) {
  String _temp = null;
  _tttt = null;

  if(SiZhu.rg==x && SiZhu.yg==y) _temp = YiJing.TIANGANNAME[x] +"日"+YiJing.TIANGANNAME[y]+"月";
  else if(SiZhu.rg==x && SiZhu.sg==y) _temp = YiJing.TIANGANNAME[x] +"日"+YiJing.TIANGANNAME[y]+"时";
  else if(SiZhu.rg==y && SiZhu.yg==x) _temp = YiJing.TIANGANNAME[y]+"日"+YiJing.TIANGANNAME[x] +"月";
  else if(SiZhu.rg==y && SiZhu.sg==x) _temp = YiJing.TIANGANNAME[y]+"日"+YiJing.TIANGANNAME[x] +"时";
  if(_temp != null) {
    if((SiZhu.yz == yz[0] || SiZhu.yz == yz[1] ||
        SiZhu.yz == yz[2] || SiZhu.yz == yz[3] ) &&
       (pub.isNo(nozi[0], 1) && pub.isNo(nozi[1], 1) &&
        pub.isNo(nozi[2],2) && pub.isNo(nozi[3],2))) {
      _tttt = _temp + "，生于" + YiJing.DIZINAME[SiZhu.yz] + "月，" + name;
      return true;
    }
  }
  return false;
}

  /**
   * 由日主干支和出身月份简单描述
   * @return
   */
  public String sijiDesc() {
    String s = null;

    switch(SiZhu.yz){
      case 3:
        if(SiZhu.rg==1 || SiZhu.rg==2){
          s = "春初木嫩，余寒犹存。";
        }else if(SiZhu.rg==3 || SiZhu.rg==4){
          s = "火生于春初，火明木秀，谓相火有焰，不作旺论。";
        }else if(SiZhu.rg==5 || SiZhu.rg==6){
          s = "余寒犹存，其势孤虚。";
        }else if(SiZhu.rg==7 || SiZhu.rg==8){
          s = "木嫩金寒。";
        }else{
          s = "其势稍寒。";
        }
        break;
      case 4:
      if(SiZhu.rg==1 || SiZhu.rg==2){
        s = "木旺当令，余寒犹存。";
      }else if(SiZhu.rg==3 || SiZhu.rg==4){
        s = "木火通明。";
      }else if(SiZhu.rg==5 || SiZhu.rg==6){
        s = "其势孤虚。";
      }else if(SiZhu.rg==7 || SiZhu.rg==8){
        s = "木坚金碎，余寒未尽。";
      }else{
        s = "性滥滔淫。";
      }
      break;
    case 5:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "尚有余气。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "湿土晦光。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "得令当权。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "母旺子相。";
      }
      else {
        s = "能够蓄水。";
      }
      break;
    case 6:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "长于夏令，木从火势，根干叶枯。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "乘旺秉权。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "土荣夏令，其势燥烈。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "火生金绝，尤为柔弱，形质未备，更嫌死绝。";
      }
      else {
        s = "执性归源，时当涸际，欲得比肩。";
      }
      break;
    case 7:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "长于夏令，根干叶枯。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "得令当权，旺之极矣。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "火炎土燥。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "烈火熔金，尤为柔弱，形质未备，更嫌死绝。";
      }
      else {
        s = "执性归源，时当涸际，欲得比肩。";
      }
      break;
    case 8:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "其势燥烈，根干叶枯。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "其势犹存。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "得令当旺。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "燥土难生，尤为柔弱，形质未备，更嫌死绝。";
      }
      else {
        s = "燥土克之，其势弱极。";
      }
      break;
    case 9:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "秋木凋零。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "性息体休。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "子旺母衰。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "得令当权。";
      }
      else {
        s = "秋水通源。";
      }
      break;
    case 10:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "金锐秉权，秋木休囚。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "性息体休。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "子旺母衰。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "当令乘权。";
      }
      else {
        s = "秋水通源。";
      }
      break;
    case 11:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "休囚至极。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "其势休囚。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "得令当权。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "余气尚存。";
      }
      else {
        s = "燥土克之，其势弱极。";
      }
      break;
    case 12:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "木凋水寒。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "体绝形亡。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "水寒土冻。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "寒金冷水。";
      }
      else {
        s = "寒冰冷水。";
      }
      break;
    case 1:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "木衰水寒，冻水难生。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "体绝形亡。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "寒湿至极。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "寒金冷水。";
      }
      else {
        s = "寒冰冷水。";
      }
      break;
    case 2:
      if (SiZhu.rg == 1 || SiZhu.rg == 2) {
        s = "木凋水寒，冻水难生。";
      }
      else if (SiZhu.rg == 3 || SiZhu.rg == 4) {
        s = "湿土晦光。";
      }
      else if (SiZhu.rg == 5 || SiZhu.rg == 6) {
        s = "天寒地冻。";
      }
      else if (SiZhu.rg == 7 || SiZhu.rg == 8) {
        s = "金寒土冻。";
      }
      else {
        s = "土冻水寒。";
      }
      break;
    }
    return s;
  }



  /**
   * 取会局或方局
   * 两位数，第1位是五行，第二位是有无月支，0为无月支
   * 1:会木局=3+4+5=12 min(x,y,z)=3 max(x,y,z)=5
   * 2:会火局=6+7+8=21
   * 3:会金局=9+10+11=30
   * 4:会水局=12+1+2=15
   * 5:合木局=12+4+8=24
   * 6:合火局=11+3+7=21
   * 7:合金局=10+2+6=18
   * 8:合水局=9+1+5=15
   */
  private int getFangOrHui() {
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 3, 5, 12) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 3, 5, 12) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 3, 5, 12))
      return 11;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 3, 5, 12))
      return 10;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 6, 8, 21) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 6, 8, 21) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 6, 8, 21))
      return 21;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 6, 8, 21))
      return 20;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 9, 11, 30) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 9, 11, 30) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 9, 11, 30))
      return 31;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 9, 11, 30))
      return 30;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 1, 12, 15) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 1, 12, 15) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 1, 12, 15))
      return 41;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 1, 12, 15))
      return 40;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 4, 12, 24) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 4, 12, 24) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 4, 12, 24))
      return 51;
    if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 4, 12, 24))
      return 50;
    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 3, 11, 21) ||
       pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 3, 11, 21) ||
       pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 3, 11, 21))
     return 61;
     if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 3, 11, 21))
       return 60;
   if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 2, 10, 18) ||
       pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 2, 10, 18) ||
       pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 2, 10, 18))
     return 71;
   if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 2, 10, 18))
     return 70;
   if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 1, 9, 15) ||
       pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 1, 9, 15) ||
       pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 1, 9, 15))
     return 81;
   if(pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 1, 9, 15))
     return 80;

   return 0;
  }

  /**
   * 返回该类型是否六冲或六合或半合及其位置
   * 0 紧贴 1隔一支 2隔二支 >=100 为含月支 如100为含月支紧贴 101为含月支隔支
   * gz 天干或地支
   * type 何五行
   */
  private int isHasRelate(int gz, int type, int sum ,int power) {
    int iret = 0;
    int year,month,day,hour;
    if(gz==1) {
      year = SiZhu.ng;
      month = SiZhu.yg;
      day = SiZhu.rg;
      hour = SiZhu.sg;
    }else{
      year = SiZhu.nz;
      month = SiZhu.yz;
      day = SiZhu.rz;
      hour = SiZhu.sz;
    }

    if(year+month==sum && year*month==power) {
      return type*100;
    }
    if(year+day==sum && year*day==power) {
      return type*10+1;
    }
    if(year+hour==sum && year*hour==power) {
      return type*10+2;
    }
    if(day+month==sum && day*month==power) {
      return type*100;
    }
    if(hour+month==sum && hour*month==power) {
      return type*100+1;
    }
    if(day+hour==sum && day*hour==power) {
      return type*10;
    }

    return iret;
  }

  /**
   * 得到六冲类型及位置
   * 二位数字，第一位类型，第二位位置
   * 1子午 4卯酉 3寅申 6巳亥 5辰戌 2丑未
   * 0紧临 1隔一支 2隔二支
   * type 为哪种支冲
   * @return
   */
  private int getLiuChong(int type, int sum, int power) {
    int[] irets = new int[2];
    int j = 0;

    int i = isHasRelate(2,type,sum,power);
    if(i>0) irets[j++] = i;

    return irets[0];
  }

  /**
   * 得到三会或三合，且三合有无冲破，此方法暂不用
   * 1:会木 2:会火 3:会金 4:会水
   * 5:合木 6:合火 7:合金 8:合水
   * 如果返回数是=i*10，则为冲破
   */
  private int getSanHuiOrSanHe() {
    int i = getFangOrHui();
    int c = 0;
    // 判断是否合局中的子午卯酉被紧贴之支冲破
    if(i>4) {
      if(i==50 || i==51) {
        c = getLiuChong(4, 14, 40);
        if(c==40)
          return i * 10;
      }
      if(i==60 || i==61) {
        c = getLiuChong(1, 8, 7);
        if(c==10)
          return i * 10;
      }
      if(i==70 || i==71) {
        c = getLiuChong(4, 14, 40);
        if(c==40)
          return i * 10;
      }
      if(i==80 || i==81) {
        c = getLiuChong(1, 8, 7);
        if(c==10)
          return i * 10;
      }
    }

    return i;
  }

  /**
   *半合：二位数字，第一位类型，第二位位置
   * 有三合不以旺半合论
   * 5:合木 6:合火 7:合金 8:合水
   * type 1前旺半合 2后旺半合 3非旺半合
   */
  private int getBanHe(int type, int wx) {
    int i = getFangOrHui();
    int result = 0;
    int[] irets = new int[2];
    int j = 0;

    if(wx == 5) {
      if (i != 50 && i != 51) {
        if (type == 1) {
          result = isHasRelate(2, 5, 16, 48);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 5, 12, 32);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 5, 20, 96);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }
    if(wx == 6) {
      if (i != 60 && i != 61) {
        if (type == 1) {
          result = isHasRelate(2, 6, 10, 21);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 6, 18, 77);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 6, 14, 33);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }
    if(wx == 7) {
      if (i != 70 && i != 71) {
        if (type == 1) {
          result = isHasRelate(2, 7, 16, 60);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 7, 12, 20);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 7, 8, 12);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }
    if(wx == 8) {
      if (i != 80 && i != 81) {
        if (type == 1) {
          result = isHasRelate(2, 8, 10, 9);
          if (result > 0)
            irets[j++] = result;
        }
        else if (type == 2) {
          result = isHasRelate(2, 8, 6, 5);
          if (result > 0)
            irets[j++] = result;
        }
        else {
          result = isHasRelate(2, 8, 14, 45);
          if (result > 0)
            irets[j++] = result;
        }
      }
    }

    return irets[0];
  }

  /**
   * 六合是否得化
   a) 天干合化与否，须以日干为主，紧临月干或时干为合，且月支须为合化之同一五行方论合化。
   b) 年月天干相合，年支为合化之五行有根得化。
   c) 日干与月干或日干与时干合，月支不化，所化五行在其余三支为三合局或三会局也可论化。

   地支合化与否，要两支紧临相贴，且天干须透出地支合化之五行方可论化。
   相临之合不化，以合而不化论。
   凡天合，地合，合化之后，以合化后的五行论，原五行失却其作用;
   合而不化，为独立五行，均不再与其他干支论生克刑冲。
   */
  private int getHeHua(int type, int x1, int x2) {
    int i = getFangOrHui();
    if(type==1) {
      if(SiZhu.yg+SiZhu.rg==x1+x2 && SiZhu.yg*SiZhu.rg==x1*x2) {
        if(YiJing.DIZIWH[SiZhu.yz] == YiJing.TGHE[x1][x2]) {
           return 10001;
        }
        if((YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)) ||
           (YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)) ||
           (YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)) ||
           (YiJing.TGHE[x1][x2] == YiJing.JIN && (i==3 || i==7)))
          return 10003;
        if((YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)) ||
           (YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)) ||
           (YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)) ||
           (YiJing.TGHE[x1][x2] == YiJing.MU && (i==1 || i==5)))
          return 10003;
        if((YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)) ||
           (YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)) ||
           (YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)) ||
           (YiJing.TGHE[x1][x2] == YiJing.SHUI && (i==4 || i==8)))
          return 10003;
        if((YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)) ||
           (YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)) ||
           (YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)) ||
           (YiJing.TGHE[x1][x2] == YiJing.HUO && (i==2 || i==6)))
          return 10003;

      }
      if(SiZhu.yg+SiZhu.ng==x1+x2 && SiZhu.yg*SiZhu.ng==x1*x2) {
        int j = YiJing.DZLIUHE[x1][x2];
        if(j==YiJing.JIN && (YiJing.DIZIWH[SiZhu.nz]==YiJing.JIN ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.TU))
          return 10002;
        if(j==YiJing.MU && (YiJing.DIZIWH[SiZhu.nz]==YiJing.SHUI ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.MU) ||
            SiZhu.nz==YiJing.WEI || SiZhu.nz==YiJing.CHEN)
          return 10002;
        if(j==YiJing.SHUI && (YiJing.DIZIWH[SiZhu.nz]==YiJing.JIN ||
                              YiJing.DIZIWH[SiZhu.nz]==YiJing.SHUI)||
            SiZhu.nz==YiJing.CHOU || SiZhu.nz==YiJing.CHEN)
          return 10002;
        if(j==YiJing.HUO && (YiJing.DIZIWH[SiZhu.nz]==YiJing.MU ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.HUO)||
            SiZhu.nz==YiJing.XU || SiZhu.nz==YiJing.WEI)
          return 10002;
        if(j==YiJing.TU && (YiJing.DIZIWH[SiZhu.nz]==YiJing.HUO ||
                             YiJing.DIZIWH[SiZhu.nz]==YiJing.TU))
          return 10002;
      }
    }else{
      if((SiZhu.nz+SiZhu.yz==x1+x2 && SiZhu.nz*SiZhu.yz==x1*x2) ||
         (SiZhu.yz+SiZhu.rz==x1+x2 && SiZhu.yz*SiZhu.rz==x1*x2) ||
         (SiZhu.rz+SiZhu.sz==x1+x2 && SiZhu.rz*SiZhu.sz==x1*x2)){
        if(YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.ng] ||
           YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.yg] ||
           YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.rg] ||
           YiJing.DZLIUHE[x1][x2] == YiJing.TIANGANWH[SiZhu.sg])
          return 10000;
      }
    }

    return 0;
  }

  /**
   * 得到六合,无论天干或地支
   * 三会火则不论午未合，三会水不论子丑合 >100为含月支（100紧贴101隔支）,<100不含月支
   * >10000为合而化。10001为月支得化 10002为年支有根 10003为合化五行有三合或三会局
   * 1:会木 2:会火 3:会金 4:会水
   * 5:合木 6:合火 7:合金 8:合水 9:合甲己或子丑土 0合午未土
   * type 1为天干 2为地支
   * wx 为五行
   */
  private int _getLiuHe(int type, int wx) {
    int i = getFangOrHui();
    int result = 0;
    int[] irets = new int[2];
    int j=0;
    int _i = 0;

    if(type==1) {
      if(wx==5) {
        result = isHasRelate(1, 5, 13, 36);
        if (result > 0) {
          _i = getHeHua(type,4,9);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==6) {
        result = isHasRelate(1, 6, 15, 50);
        if (result > 0) {
          _i = getHeHua(type,5,10);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==7) {
        result = isHasRelate(1, 7, 9, 14);
        if (result > 0) {
          _i = getHeHua(type,2,7);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==8) {
        result = isHasRelate(1, 8, 11, 24);
        if (result > 0) {
          _i = getHeHua(type,3,8);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==9) {
        result = isHasRelate(1, 9, 7, 6);
        if (result > 0) {
          _i = getHeHua(type,1,6);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
    }else{
      if(wx==5) {
        result = isHasRelate(2, 5, 15, 36);
        if (result > 0) {
          _i = getHeHua(type,3,12);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==6) {
        result = isHasRelate(2, 6, 15, 44);
        if (result > 0) {
          _i = getHeHua(type,4,11);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==7) {
        result = isHasRelate(2, 7, 15, 50);
        if (result > 0) {
          _i = getHeHua(type,5,10);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==8) {
        if(i!=40 || i!=41) {
          result = isHasRelate(2, 8, 15, 54);
          if (result > 0) {
          _i = getHeHua(type,6,9);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
        }
      }
      if(wx==9) {
        result = isHasRelate(2, 9, 3, 2);
        if (result > 0) {
          _i = getHeHua(type,1,2);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
      }
      if(wx==10) {
        if(i!=20 || i!=21) {
          result = isHasRelate(2, 10, 15, 56);
          if (result > 0) {
          _i = getHeHua(type,7,8);
          if(_i == 0) {
            irets[j++] = result;
          }else{
            irets[j++] = _i;
          }
        }
        }
      }
    }

    return irets[0];
  }

  /**
   * 得到六害
   * 三会金不论9 三会木不论4
   * 子未 2丑午 3寅巳 4卯辰 9申戌 10酉亥相害
   * @return
   */
  private double getLiuHai(int x, int y, String name) {
    int i = getFangOrHui();
    int result = 0;

    if (i == 30 || i == 31) {
      if(x+y==20 && x*y==99)
        return 0;
    }
    if (i == 10 || i == 11) {
      if(x+y==9 && x*y==20)
        return 0;
    }
    result = isHasRelate(2, 1, x+y, x*y);
    if (result > 0) {
      desc += name + "；";
      return SiZhu.haiXS;
    }
    return 0;
  }

  /**
   * 得到三刑,第一位是wx，第二位是位置
   * 1:子卯 2:寅巳申 201寅巳 202巳申 203寅申 3:丑戌未
   * wx 何种五行
   */
  private void getSanXing() {
    int result = 0;
    int[] irets = new int[2];
    int j = 0;

    result = isHasRelate(2, 1, 5, 4);
    if (result > 0) {
      _shui += SiZhu.xingXS * shui;
      _mu += SiZhu.xingXS * mu;
      desc += "子卯相刑；";
      _p("子卯相刑，水加 ",SiZhu.xingXS * shui,"木加 ",SiZhu.xingXS * mu);
    }

    if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 3, 9, 18) ||
        pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 3, 9, 18) ||
        pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 3, 9, 18) ||
        pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 3, 9, 18))
      return;
    else {
      result = isHasRelate(2, 2, 9, 18);
      if (result > 0) {
        _mu += SiZhu.xingXS * mu;
        _huo += SiZhu.xingXS * huo;
        desc += "寅巳相刑；";
        _p("寅巳相刑，木加 ",SiZhu.xingXS * mu,"火加 ",SiZhu.xingXS * huo);
      }
      else {
        result = isHasRelate(2, 2, 15, 54);
        if (result > 0) {
          _huo += SiZhu.xingXS * huo;
          _jin += SiZhu.xingXS * jin;
          desc += "巳申相刑；";
          _p("巳申相刑，火加 ",SiZhu.xingXS * huo,"金加 ",SiZhu.xingXS * jin);
        }
        else {
          result = isHasRelate(2, 2, 12, 27);
          if (result > 0) {
            _mu += SiZhu.xingXS * mu;
            _jin += SiZhu.xingXS * jin;
            desc += "寅申相刑；";
            _p("寅申相刑，木加 ",SiZhu.xingXS * mu,"金加 ",SiZhu.xingXS * jin);
          }
        }
      }

      if (pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.rz, 2, 11, 21) ||
          pub.isTF(SiZhu.nz, SiZhu.yz, SiZhu.sz, 2, 11, 21) ||
          pub.isTF(SiZhu.nz, SiZhu.rz, SiZhu.sz, 2, 11, 21) ||
          pub.isTF(SiZhu.yz, SiZhu.rz, SiZhu.sz, 2, 11, 21))
        return;
      else {
        result = isHasRelate(2, 3, 13, 22);
        if (result > 0) {
          _tu += SiZhu.xingXS * 2 * tu;
          _p("丑戌相刑，土加 ",SiZhu.xingXS * 2 * tu);
          desc += "丑戌相刑；";
        }
        else {
          result = isHasRelate(2, 3, 19, 88);
          if (result > 0) {
            _tu += SiZhu.xingXS * 2 * tu;
            _p("戌未相刑，土加 ",SiZhu.xingXS * 2 * tu);
            desc += "戌未相刑；";
          }
          else {
            result = isHasRelate(2, 3, 10, 16);
            if (result > 0) {
              _tu += SiZhu.xingXS * 2 * tu;
              _p("丑未相刑，土加 ",SiZhu.xingXS * 2 * tu);
              desc += "丑未相刑；";
            }
          }
        }
      }
    }
  }

  /**
   * //2）各支相对各支打分
    //三会或三合(其五行系数-有月令：3.0月支  无月令：2.5最大分支)
   */
  private double getFangOrHuiXS(int wx, String name1, String name2) {
    double xs = 0;
    double xs1 = 0;
    String _temp = "合";
    double xs2=SiZhu.sanheXS[2],xs3=SiZhu.sanheXS[1];

    if(wx<5) {
      _temp = "会";
      xs2 = SiZhu.sanhuiXS[2];
      xs3 = SiZhu.sanhuiXS[1];
    }

    xs = getFangOrHui();
    if(xs == wx*10) {
      xs1 = xs2;
      desc += name1+"不含月令三"+_temp+name2+"；";
    }
    if(xs == wx*10+1) {
      xs1 = xs3;
      desc += name1+"含月令三"+_temp+name2+"；";
    }
    return xs1;
  }

  /**
   * 地支六冲
   * wx:何种类型的六冲
   */
  private double[] getDZLiuChong(int wx,int sum, int power, String name1, String name2, String name3, String name4) {
    double xs = 0;
    double[] _xs = new double[2];

    xs = getLiuChong(wx, sum, power);
    double xs1=0, xs2=0;

    if(wx==5 || wx==2) {
      if(xs == wx * 100) {
        xs1 = SiZhu.lcXS1[1] ;
        xs2 = SiZhu.lcXS1[3];
        desc += name1 + "紧贴相冲；";
      }
      if(xs == wx * 100+1) {
        xs1 = SiZhu.lcXS1[2] ;
        xs2 = SiZhu.lcXS1[4];
        desc += name1 + "隔支相冲；";
      }
    }else{
      if (xs == wx * 100 && SiZhu.yz == wx) {
        xs2 = SiZhu.lcXS1[3];
        xs1 = SiZhu.lcXS1[1];
        desc += name1 + "紧贴月令相冲；";
      }
      if (xs == wx * 100 && SiZhu.yz == wx+6) {
        xs2 = SiZhu.lcXS1[1];
        xs1 = SiZhu.lcXS1[3];
        desc += name1 + "紧贴月令相冲；";
      }
      if (xs == wx * 100 + 1 && SiZhu.yz == wx) {
        xs2 = SiZhu.lcXS1[4];
        xs1 = SiZhu.lcXS1[2];
        desc += name1 + "隔支与月令相冲；";
      }
      if (xs == wx * 100 + 1 && SiZhu.yz == wx+6) {
        xs2 = SiZhu.lcXS1[2];
        xs1 = SiZhu.lcXS1[4];
        desc += name1 + "隔支与月令相冲；";
      }
    }
    if(xs == wx*10) {
      xs2 = SiZhu.lcXS1[5];
      xs1 = SiZhu.lcXS1[5];
      desc += name1+"紧贴相冲；";
    }
    if(xs == wx*10+1) {
      xs2 = SiZhu.lcXS1[6];
      xs1 = SiZhu.lcXS1[6];
      desc += name1+"隔支相冲；";
    }
    if(xs == wx*10+2) {
      xs2 = SiZhu.lcXS1[7];
      xs1 = SiZhu.lcXS1[7];
      desc += name1+"遥隔相冲而暗动；";
    }

    _xs[0]= xs1;
    _xs[1] = xs2;
    return _xs;
  }

  /**
   * 得到半合系数
   * qh: 半合前后
   * wx: 哪种五行
   */
  private double getBanHeXS(int qh, int wx, String name1, String name2) {
    double xs1 = 0;
    double xs = 0;

    xs =  getBanHe(qh, wx);
   if(xs == wx*100) {
     xs1 = SiZhu.wangBHXS[1];
     desc += name1+"紧贴月令半合；";
   }
   if(xs == wx*100+1) {
     xs1 = SiZhu.wangBHXS[2];
     desc += name1+"与月令隔支半合；";
   }
   if(xs == wx*10) {
     xs1 = SiZhu.wangBHXS[3];
     desc += name1+"紧贴半合；";
   }
   if(xs == wx*10+1) {
     xs1 = SiZhu.wangBHXS[4];
     desc += name1+"隔支半合；";
   }
   if(xs == wx*10+2) {
     xs1 = SiZhu.wangBHXS[5];
     desc += name1+"隔二支半合；";
   }
   return xs1;
  }

  /**
   * 得到天干六合
   * 乙庚 甲己合化时需减去原分
   * 寅亥 辰酉 子丑 午未应减去原分
   * gz: 天干或地支
   * wx: 哪种五行
   */
  private double getLiuHe(int gz, int wx, String name1, String name2) {
    double xs1 = 0;
    double xs = 0;

    xs = _getLiuHe(gz,wx);
    if(xs == wx*100) {
      xs1 = SiZhu.lhXS[1];
      desc += name1 + "紧贴与月令合；";
    }
    if(xs == wx*100+1) {
      xs1 = SiZhu.lhXS[2];
      desc += name1 + "隔支与月令合；";
    }
    if(xs == wx*10) {
      xs1 = SiZhu.lhXS[3];
      desc += name1 + "紧贴合；";
    }
    if(xs == wx*10+1) {
      xs1 = SiZhu.lhXS[4];
      desc += name1 + "隔支合；";
    }
    if(xs == wx*10+2) {
      xs1 = SiZhu.lhXS[5];
      desc += name1 + "遥合；";
    }
    if(gz==1) {
      if (xs == 10001) {
        xs1 = SiZhu.lhXS[6];
        desc += name1 + "因月支同五行而合化；";
      }
      if (xs == 10002) {
        xs1 = SiZhu.lhXS[6];
        desc += name1 + "因年支有根合化；";
      }
      if (xs == 10003) {
        xs1 = SiZhu.lhXS[6];
        desc += name1 + "因地支三会或三合而化成真；";
      }
    }else{
      if(xs == 10000) {
         xs1 = SiZhu.lhXS[6];
         desc += name1 + "因天干透出而合化；";
       }
    }

    if(gz == 1 && (wx == 7 || wx == 9)) {
      if(xs>=10000)
        xs1 = xs1/2;
    }
    if(gz == 2 && (wx==5 || wx == 7 || wx == 9 || wx == 10)) {
      if(xs>=10000)
        xs1 = xs1/2;
    }

    return xs1;
  }

  private double getSK(int sk, int gz, int loc, int x, int y) {
    double xs1 = 0;
    String name1=null, name2=null;
    int xwx=0, ywx=0;

    if (gz == 1) {
      name1 = YiJing.TIANGANNAME[x];
      name2 = YiJing.TIANGANNAME[y];
      xwx = YiJing.TIANGANWH[x];
      ywx = YiJing.TIANGANWH[y];
    }
    else {
      name1 = YiJing.DIZINAME[x];
      name2 = YiJing.DIZINAME[y];
      xwx = YiJing.DIZIWH[x];
      ywx = YiJing.DIZIWH[y];
    }
    if (sk == 1) {
      if (YiJing.WXDANSHENG[xwx][ywx] > 0) {
        if (loc == 0) {
          //desc += name1 + "紧贴相生" + name2 + "；";
          xs1 += SiZhu.shengXS[1];
        }
        else if (loc == 1) {
          //desc += name1 + "隔干相生" + name2 + "；";
          xs1 += SiZhu.shengXS[2];
        }
        else {
          //desc += name1 + "遥隔相生" + name2 + "；";
          xs1 += SiZhu.shengXS[3];
        }
      }
    }
    else {
      if (YiJing.WXDANKE[xwx][ywx] > 0
          && ywx == YiJing.SHUI &&
          (x == YiJing.JI || x == YiJing.CHEN || x == YiJing.CHOU)) {
        desc += "湿土"+name1+"畜水；";
        xs1 += SiZhu.keXS[4];
      }
      else if (YiJing.WXDANKE[xwx][ywx] > 0) {
        if (loc == 0) {
          if((x==YiJing.MAO && y==YiJing.SHEN) || (x==YiJing.SHEN && y==YiJing.MAO)) {
            desc += name1 + "紧贴相克" + name2 + "，但卯申暗合；";
            xs1 += SiZhu.keXS[1]+SiZhu.anheXS;
          }else{
            //desc += name1 + "紧贴相克" + name2 + "；";
            xs1 += SiZhu.keXS[1];
          }
        }
        else if (loc == 1) {
          //desc += name1 + "隔干相克" + name2 + "；";
          xs1 += SiZhu.keXS[2];
        }
        else {
          //desc += name1 + "遥隔相克" + name2 + "；";
          xs1 += SiZhu.keXS[3];
        }
      }
    }
    return xs1;
  }

  /**
   * 判断指定的干或支是否生或克
   * @param sk 1生2克
   * @param wx 哪种五行
   */
  private double getShengKeXS(int wx, int sk) {
    double xs1 = 0;

    if(YiJing.TIANGANWH[SiZhu.ng] == wx) {
      xs1 += getSK(sk, 1, 0, SiZhu.yg, SiZhu.ng);
      xs1 += getSK(sk, 1, 1, SiZhu.rg, SiZhu.ng);
      xs1 += getSK(sk, 1, 2, SiZhu.sg, SiZhu.ng);
    }else if(YiJing.TIANGANWH[SiZhu.yg] == wx) {
      xs1 += getSK(sk, 1, 0, SiZhu.ng, SiZhu.yg);
      xs1 += getSK(sk, 1, 0, SiZhu.rg, SiZhu.yg);
      xs1 += getSK(sk, 1, 1, SiZhu.sg, SiZhu.yg);
    }else if(YiJing.TIANGANWH[SiZhu.rg] == wx) {
      xs1 += getSK(sk, 1, 1, SiZhu.ng, SiZhu.rg);
      xs1 += getSK(sk, 1, 0, SiZhu.yg, SiZhu.rg);
      xs1 += getSK(sk, 1, 0, SiZhu.sg, SiZhu.rg);
    }else if(YiJing.TIANGANWH[SiZhu.sg] == wx) {
      xs1 += getSK(sk, 1, 2, SiZhu.ng, SiZhu.sg);
      xs1 += getSK(sk, 1, 1, SiZhu.yg, SiZhu.sg);
      xs1 += getSK(sk, 1, 0, SiZhu.rg, SiZhu.sg);
    }

    if(YiJing.DIZIWH[SiZhu.nz] == wx) {
      xs1 += getSK(sk, 2, 0, SiZhu.yz, SiZhu.nz);
      xs1 += getSK(sk, 2, 1, SiZhu.rz, SiZhu.nz);
      xs1 += getSK(sk, 2, 2, SiZhu.sz, SiZhu.nz);
    }else if(YiJing.DIZIWH[SiZhu.yz] == wx) {
      xs1 += getSK(sk, 2, 0, SiZhu.nz, SiZhu.yz);
      xs1 += getSK(sk, 2, 0, SiZhu.rz, SiZhu.yz);
      xs1 += getSK(sk, 2, 1, SiZhu.sz, SiZhu.yz);
    }else if(YiJing.DIZIWH[SiZhu.rz] == wx) {
      xs1 += getSK(sk, 2, 1, SiZhu.nz, SiZhu.rz);
      xs1 += getSK(sk, 2, 0, SiZhu.yz, SiZhu.rz);
      xs1 += getSK(sk, 2, 0, SiZhu.sz, SiZhu.rz);
    }else if(YiJing.DIZIWH[SiZhu.sz] == wx) {
      xs1 += getSK(sk, 2, 2, SiZhu.nz, SiZhu.sz);
      xs1 += getSK(sk, 2, 1, SiZhu.yz, SiZhu.sz);
      xs1 += getSK(sk, 2, 0, SiZhu.rz, SiZhu.sz);
    }

    return xs1;
  }

  private void judgeWX(int gz, int x, double xs) {
    if(gz==1) {
      if (YiJing.TIANGANWH[x] == YiJing.MU) {
        _mu += xs * mu;
        _p("木天地覆载克得分", xs * mu);
      }
      if (YiJing.TIANGANWH[x] == YiJing.HUO){
        _huo += xs * huo;
        _p("火天地覆载克得分", xs * huo);
      }
      if (YiJing.TIANGANWH[x] == YiJing.TU) {
        _tu += xs * tu;
        _p("土天地覆载克得分", xs * tu);
      }
      if (YiJing.TIANGANWH[x] == YiJing.JIN){
        _jin += xs * jin;
        _p("金天地覆载克得分", xs * jin);
      }
      if (YiJing.TIANGANWH[x] == YiJing.SHUI){
        _shui += xs * shui;
        _p("水天地覆载克得分", xs * shui);
      }
    }else
    if(gz==2) {
      if (YiJing.DIZIWH[x] == YiJing.MU){
        _mu += xs * mu;
        _p("木天地覆载克得分", xs * mu);
      }
      if (YiJing.DIZIWH[x] == YiJing.HUO){
        _huo += xs * huo;
        _p("火天地覆载克得分", xs * huo);
      }
      if (YiJing.DIZIWH[x] == YiJing.TU){
        _tu += xs * tu;
        _p("土天地覆载克得分", xs * tu);
      }
      if (YiJing.DIZIWH[x] == YiJing.JIN){
        _jin += xs * jin;
        _p("金天地覆载克得分", xs * jin);
      }
      if (YiJing.DIZIWH[x] == YiJing.SHUI){
        _shui += xs * shui;
        _p("水天地覆载克得分", xs * shui);
      }
    }
  }

  private void getTFDZK(int x, int y) {
    if(YiJing.WXDANSHENG[YiJing.TIANGANWH[x]][YiJing.DIZIWH[y]] > 0) {
      desc += YiJing.TIANGANNAME[x]+"生"+YiJing.DIZINAME[y]+"谓之天覆；";
      judgeWX(2,y,SiZhu.ganziXS[1]);
    }
    if(YiJing.WXDANSHENG[YiJing.DIZIWH[y]][YiJing.TIANGANWH[x]] > 0) {
      desc += YiJing.DIZINAME[y]+"生"+YiJing.TIANGANNAME[x]+"谓之地载；";
      judgeWX(1,x,SiZhu.ganziXS[1]);
    }
    if(YiJing.WXDANKE[YiJing.TIANGANWH[x]][YiJing.DIZIWH[y]] > 0) {
      desc += YiJing.TIANGANNAME[x]+"克"+YiJing.DIZINAME[y]+"谓之天克；";
      judgeWX(2,y,SiZhu.ganziXS[2]);
    }
    if(YiJing.WXDANKE[YiJing.DIZIWH[y]][YiJing.TIANGANWH[x]] > 0) {
      desc += YiJing.DIZINAME[y]+"克"+YiJing.TIANGANNAME[x]+"谓之地克；";
      judgeWX(1,x,SiZhu.ganziXS[2]);
    }
  }

  private void _thdh(int x, int y) {
    if(x==YiJing.WUG && y==YiJing.ZI) {
      desc += "戊子天合地合；";
      _tu += SiZhu.tdhXS * tu;
      _shui += SiZhu.tdhXS * shui;
      _p("戊子天合地合,土加 ",SiZhu.tdhXS * tu,"水加 ",SiZhu.tdhXS * shui);
    }
    if(x==YiJing.XIN && y==YiJing.SI) {
      _jin += SiZhu.tdhXS * jin;
      _huo += SiZhu.tdhXS * huo;
      _p("辛巳天合地合,金加 ",SiZhu.tdhXS * jin,"火加 ",SiZhu.tdhXS * huo);
      desc += "辛巳天合地合；";
    }
    if(x==YiJing.DING && y==YiJing.HAI) {
      _huo += SiZhu.tdhXS * huo;
      _shui += SiZhu.tdhXS * shui;
      _p("丁亥天合地合,火加 ",SiZhu.tdhXS * huo,"水加 ",SiZhu.tdhXS * shui);
      desc += "丁亥天合地合；";
    }
    if(x==YiJing.REN && y==YiJing.WUZ) {
      _huo += SiZhu.tdhXS * huo;
      _shui += SiZhu.tdhXS * shui;
      _p("壬午天合地合,火加 ",SiZhu.tdhXS * huo,"水加 ",SiZhu.tdhXS * shui);
      desc += "壬午天合地合；";
    }
  }

  /**
   * //天覆(即天干生之): 0.1*此干分数 地载: 0.1此支基本分
    //天克：-0.1*此干分数 地克：-0.1此支基本分
    //天合地合（只有戊子、辛巳、丁亥、壬午四日）：0.1*此支分数
   */
  private void getGZRelate() {
    getTFDZK(SiZhu.ng,SiZhu.nz);
    getTFDZK(SiZhu.yg,SiZhu.yz);
    getTFDZK(SiZhu.rg,SiZhu.rz);
    getTFDZK(SiZhu.sg,SiZhu.sz);
    _thdh(SiZhu.ng,SiZhu.nz);
    _thdh(SiZhu.yg,SiZhu.yz);
    _thdh(SiZhu.rg,SiZhu.rz);
    _thdh(SiZhu.sg,SiZhu.sz);
  }

  /**
   * //五行在其它3支得一墓库如木墓在未。。。 0.35此支基本分
    //五行在其它3支得一余气如木余气在辰。。 0.7此支基本分
    //五行在其它3支长生沐禄刃              1.0此支基本分
   因得地不为弱论，
   */
  private void getMuYuWang() {
    double _temp = 0;

    if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.MU ||
       YiJing.TIANGANWH[SiZhu.yg]==YiJing.MU ||
       YiJing.TIANGANWH[SiZhu.rg]==YiJing.MU ||
       YiJing.TIANGANWH[SiZhu.sg]==YiJing.MU) {
      _temp =
          _getMuYuWang("木", YiJing.WEI, YiJing.CHEN, new int[] {0, YiJing.HAI,
                       YiJing.ZI, YiJing.YIN, YiJing.MAO});
      _mu += _temp * 100;
      _p(_tttt, _temp * 100);
    }

  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.HUO ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.HUO ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.HUO ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.HUO) {
    _temp =
        _getMuYuWang("火", YiJing.XU, YiJing.WEI, new int[] {0, YiJing.YIN,
                     YiJing.MAO, YiJing.SI, YiJing.WUZ});
    _huo += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.TU ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.TU ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.TU ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.TU) {
    _temp =
        _getMuYuWang("土", YiJing.WEI, YiJing.CHEN, new int[] {0, YiJing.SI,
                     YiJing.WUZ, YiJing.XU, YiJing.CHOU});
    _tu += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.JIN ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.JIN ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.JIN ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.JIN) {
    _temp =
        _getMuYuWang("金", YiJing.CHOU, YiJing.XU, new int[] {0, YiJing.CHOU,
                     YiJing.CHEN, YiJing.SHEN, YiJing.YOU});
    _jin += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  if(YiJing.TIANGANWH[SiZhu.ng]==YiJing.SHUI ||
     YiJing.TIANGANWH[SiZhu.yg]==YiJing.SHUI ||
     YiJing.TIANGANWH[SiZhu.rg]==YiJing.SHUI ||
     YiJing.TIANGANWH[SiZhu.sg]==YiJing.SHUI) {
    _temp =
        _getMuYuWang("水", YiJing.CHEN, YiJing.CHOU, new int[] {0,
                     YiJing.SHEN, YiJing.YOU, YiJing.HAI, YiJing.ZI});
    _shui += _temp * 100;
    _p(_tttt, _temp * 100);
  }
  }

  private double _getMuYuWang(String name, int mu, int yu, int[] wang) {
    double xs = 0;
    String _t = "";

    if(SiZhu.nz == mu) {
      _t += "墓库在年支"+YiJing.DIZINAME[mu]+"；";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.yz == mu) {
      _t += "墓库在月支"+YiJing.DIZINAME[mu]+"；";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.rz == mu) {
      _t += "墓库在日支"+YiJing.DIZINAME[mu]+"；";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.sz == mu){
      _t += "墓库在时支"+YiJing.DIZINAME[mu]+"；";
      xs += SiZhu.sanziXS[1];
    }
    if(SiZhu.nz == yu) {
      _t += "在年支"+YiJing.DIZINAME[yu]+"有余气；";
      xs += SiZhu.sanziXS[2];
    }
    if(SiZhu.yz == yu){
      _t += "在月支"+YiJing.DIZINAME[yu]+"有余气；";
      xs += SiZhu.sanziXS[2];
    }
    if(SiZhu.rz == yu){
      _t += "在日支"+YiJing.DIZINAME[yu]+"有余气；";
      xs += SiZhu.sanziXS[2];
    }
    if(SiZhu.sz == yu) {
      _t += "在时支"+YiJing.DIZINAME[yu]+"有余气；";
      xs += SiZhu.sanziXS[2];
    }

    if(SiZhu.nz == wang[1] || SiZhu.nz == wang[2]) {
      _t += "在年支长生；";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.nz == wang[3] || SiZhu.nz == wang[4]){
      _t += "通根年支；";
      xs += SiZhu.sanziXS[3];
    }
    if(SiZhu.yz == wang[1] || SiZhu.yz == wang[2]) {
      _t += "在月支长生；";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.yz == wang[3] || SiZhu.yz == wang[4]) {
      _t += "通根月支；";
      xs += SiZhu.sanziXS[3];
    }
    if(SiZhu.rz == wang[1] || SiZhu.rz == wang[2]) {
      _t += "在日支长生；";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.rz == wang[3] || SiZhu.rz == wang[4]) {
      _t += "通根日支；";
      xs += SiZhu.sanziXS[3];
    }
    if(SiZhu.sz == wang[1] || SiZhu.sz == wang[2]) {
      _t += "在时支长生；";
      xs += SiZhu.sanziXS[4];
    }
    if(SiZhu.sz == wang[3] || SiZhu.sz == wang[4]){
      _t += "通根时支；";
      xs += SiZhu.sanziXS[3];
    }
    if(_t.length()>1) {
      desc += name + _t;
      //1只为>0
      _tttt = name+_t;
    }

    return xs;
  }

  private void  _p(String str, double cent) {
    if(cent != 0)
      _desc += str+cent+"；";
  }
  private void  _p(String str, double cent, String str1, double cent1) {
    if(cent != 0)
      _desc += str+cent+","+str1+cent1+"；";
  }
  private void _p() {
    desc += "\r\n    ";
  }

  ////转换成百分制 x/2 40+(x-80)/2.5 60+(x-130)/4 75+(x-190)/17 90+(x-230)/5
  private void _getBaiFenZhi() {
    int[] cent = {SiZhu.muCent1,SiZhu.huoCent1,SiZhu.tuCent1,SiZhu.jinCent1,SiZhu.shuiCent1};
    for(int j=0; j<cent.length; j++) {
      for (int i = 0; i < SiZhu.baifen.length; i++) {
        if (cent[j] < SiZhu.judgeWS[i] || i==SiZhu.baifen.length-1) {
          //当x<80 x[0]=x[0]*x0/y0     否则 x = x0 + (x-y0)*(x1-x0)/(y1-y0)
          if(i==0)
            cent[j] = (cent[j] * SiZhu.baifen[0] /SiZhu.judgeWS[0]);
          else
            cent[j] = SiZhu.baifen[i-1]+(cent[j]-SiZhu.judgeWS[i-1])*(SiZhu.baifen[i]-SiZhu.baifen[i-1])/(SiZhu.judgeWS[i]-SiZhu.judgeWS[i-1]);
          break;
        }
      }
    }
    SiZhu.muCent = cent[0];
    SiZhu.huoCent = cent[1];
    SiZhu.tuCent = cent[2];
    SiZhu.jinCent = cent[3];
    SiZhu.shuiCent = cent[4];
  }

  /**
   * 得到五行分数
   * 打分原理：
  只分析五行旺衰，以月令为100分，最旺(支三会干全透100*（4+4）=800分)，最衰(20-100*0.8*7=-540)分。
  100分是旺衰平衡点，当然以五行各相对分数论绝对强弱才有意义。
  各支相互间生克对分数进行损益。
  同我为100，我生者相80，生我者休60，克我者囚40，我克者死20，以此为旺相基数初始分。
  三会、三合、旺半合、非旺半合、六合均以合化会后的五行气乘系数，而原五行分数不变。
  三会、三合、旺半合、非旺半合、六合均以合化后的五行论, 原五行失却其作用;合而不化, 为独立五行, 均不再与其他干支论生克刑冲.
  三会与六冲并见，以三会论。
  有三合不再论半合，除四正紧临冲散外，均按三合论
  各支不论刑害
  六冲与六合、三会、三合并见，都要考虑。
  */
  public String getWuXingCent() {
    desc = "";
    _desc = "";
    _tttt = "";
    _huo = 0;
    _mu = 0;
    _tu = 0;
    _jin = 0 ;
    _shui = 0;

    int xs = 0;
    int len = 0;
    double _temp= 0;

    //1) 各支相对月令打分
    //a) 以寅卯木旺火相土死金囚水休、四季土旺金相水死木囚火休论。
    jin = SiZhu.jibenfen[YiJing.JIN][YiJing.DIZIWH[SiZhu.yz]];
    mu = SiZhu.jibenfen[YiJing.MU][YiJing.DIZIWH[SiZhu.yz]];
    shui = SiZhu.jibenfen[YiJing.SHUI][YiJing.DIZIWH[SiZhu.yz]];
    huo = SiZhu.jibenfen[YiJing.HUO][YiJing.DIZIWH[SiZhu.yz]];
    tu = SiZhu.jibenfen[YiJing.TU][YiJing.DIZIWH[SiZhu.yz]];

    _desc += "五行基本分：mu="+mu+";huo="+huo+";tu="+tu+";jin="+jin+";shui="+shui+";";

    //得到全局五行个数
    desc += "全局"+pub.getHowWx(YiJing.MU)+YiJing.WUXINGNAME[YiJing.MU]+",";
    desc += pub.getHowWx(YiJing.HUO)+YiJing.WUXINGNAME[YiJing.HUO]+",";
    desc += pub.getHowWx(YiJing.TU)+YiJing.WUXINGNAME[YiJing.TU]+",";
    desc += pub.getHowWx(YiJing.JIN)+YiJing.WUXINGNAME[YiJing.JIN]+",";
    desc += pub.getHowWx(YiJing.SHUI)+YiJing.WUXINGNAME[YiJing.SHUI]+"；";
    _p();
    desc += "天干"+pub.getGanTouZiCangDesc()+",";
    len = desc.length();

    //得到天干五行个数,得一比肩为0.3本支分
    if(pub.getHowWx(YiJing.MU, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.MU, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("天干比劫木加", _temp);
      _mu += _temp;
    }
    if(pub.getHowWx(YiJing.HUO, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.HUO, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("天干比劫火加", _temp);
      _huo += _temp;
    }
    if(pub.getHowWx(YiJing.TU, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.TU, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("天干比劫土加", _temp);
      _tu += _temp;
    }
    if(pub.getHowWx(YiJing.JIN, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.JIN, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("天干比劫金加", _temp);
      _jin += _temp;
    }
    if(pub.getHowWx(YiJing.SHUI, 1) > 1) {
      _temp = (pub.getHowWx(YiJing.SHUI, 1) - 1) * SiZhu.sanziXS[0] * 100;
      _p("天干比劫水加", _temp);
      _shui += _temp;
    }


    //得到地支五行个数,得一比肩为0.3本支分
    //二个相同的地支不作比劫加分，只作对透出天干的通根来加分。若不透，以比劫论。
    if(pub.getHowWx(YiJing.MU, 1) == 0 && pub.getHowWx(YiJing.MU, 2) > 1) {
        _temp = (pub.getHowWx(YiJing.MU, 2) - 1) * SiZhu.sanziXS[3] * 100;
        _p("地支比劫木加", _temp);
        _mu += _temp;
    }
    if(pub.getHowWx(YiJing.HUO, 1) == 0 && pub.getHowWx(YiJing.HUO, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.HUO, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("地支比劫火加", _temp);
      _huo += _temp;
    }
    if(pub.getHowWx(YiJing.TU, 1) == 0 && pub.getHowWx(YiJing.TU, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.TU, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("地支比劫土加", _temp);
      _tu += _temp;
    }
    if(pub.getHowWx(YiJing.JIN, 1) == 0 && pub.getHowWx(YiJing.JIN, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.JIN, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("地支比劫金加", _temp);
      _jin += _temp;
    }
    if(pub.getHowWx(YiJing.SHUI, 1) == 0 && pub.getHowWx(YiJing.SHUI, 2) > 1) {
      _temp = (pub.getHowWx(YiJing.SHUI, 2) - 1) * SiZhu.sanziXS[3] * 100;
      _p("地支比劫水加", _temp);
      _shui += _temp;
    }


    //三会系数，与月令比较故*100
    _temp = 100 * getFangOrHuiXS(1,"寅卯辰","木");
    _p("三会寅卯辰，木加 ",_temp);
    _mu += _temp;
    _temp = 100 * getFangOrHuiXS(2,"巳午未","火");
    _p("三会巳午未，火加 ",_temp);
    _huo += _temp;
    _temp = 100 * getFangOrHuiXS(3,"申酉戌","金");
    _p("三会申酉戌，金加 ",_temp);
    _jin += _temp;
    _temp = 100 * getFangOrHuiXS(4,"亥子丑","水");
    _p("三会亥子丑，水加 ",_temp);
    _shui += _temp;

    //b) 六冲-隔二支冲为动，不损力。紧临支的子午卯酉(月令冲：不隔一支有月令-0.5月支,-0.2月支非-0.6另支,-0.3另支，不隔一支二支无月令冲:-0.5本支,-0.2本支,0.1本支；)
    //       -寅申巳亥冲：不隔一支有月令-0.5月支,-0.2月支，非-0.6另支,-0.3另支；不隔一支二支无月支-0.5本支,-0.2本支,0.1本支
    //       -辰戌丑款冲：不隔一支有月令0.5月支,0.3月支,非0.3另支,0.2另支；不隔一支二支无月支0.3本支,0.2本支,0.1本支
    double[] _lc ;
    _lc = getDZLiuChong(1, 8, 7, "子午","子水","午火","水火");
    _shui += _lc[0] * Math.min(shui,huo);
    _huo += _lc[1] * Math.min(shui,huo);
    _p("子午冲后水火加分：水",_lc[0] * Math.min(shui,huo), "火",_lc[1] * Math.min(shui,huo));

    _lc = getDZLiuChong(4, 14, 40, "卯酉","卯木","酉金","金木");
    _mu += _lc[0] * Math.min(mu,jin);
    _jin += _lc[1] * Math.min(mu,jin);
    _p("卯酉冲后水火加分：木",_lc[0] * Math.min(mu,jin), "金",_lc[1] * Math.min(mu,jin));

    _lc = getDZLiuChong(3,12,27,"寅申","寅木","申金","金木");
    _mu += _lc[0] * Math.min(mu,jin);
    _jin += _lc[1] * Math.min(mu,jin);
    _p("寅申冲后水火加分：木",_lc[0] * Math.min(mu,jin), "金",_lc[1] * Math.min(mu,jin));

    _lc = getDZLiuChong(6,18,72,"巳亥","巳火","亥水","水火");
    _huo += _lc[0] * Math.min(shui,huo);
    _shui += _lc[1] * Math.min(shui,huo);
    _p("巳亥冲后火水加分：火",_lc[0] * Math.min(shui,huo), "水",_lc[1] * Math.min(shui,huo));

    _lc = getDZLiuChong(5,16,55,"辰戌","","","土");
    _tu += (_lc[0] + _lc[1]) * tu;
    _p("辰戌冲后土加分 ",(_lc[0] + _lc[1]) * tu);

    _lc = getDZLiuChong(2,10,16,"丑未","","","土");
    _tu += (_lc[0] + _lc[1]) * tu;
    _p("丑未后土加分 ",(_lc[0] + _lc[1]) * tu);


    //c) 三合(其五行系数-有月令：2.5月支 无月令：2.0最大分支)
    _temp = 100 * getFangOrHuiXS(5,"亥卯未","木");
    _p("三合亥卯未，木加 ",_temp);
    _mu += _temp;
    _temp = 100 * getFangOrHuiXS(6,"寅午戌","火");
    _p("三合寅午戌，火加 ",_temp);
    _huo += _temp;
    _temp = 100 * getFangOrHuiXS(7,"巳酉丑","金");
    _p("三合巳酉丑，金加 ",_temp);
    _jin += _temp;
    _temp = 100 * getFangOrHuiXS(8,"申子辰","水");
    _p("三合申子辰，水加 ",_temp);
    _shui +=  _temp;
    if(desc.length()>len+10) {
      len = desc.length();
      _p();
    }

    // 三合旺半合 不隔一支有月支1.3月支，1.0月支，不隔一支二支无月支1.0最大分支，0.6最大分支，0.2最大分支
    // 三合非旺半合 不隔一支有月支0.5月支，0.3月支，不隔一支二支无月支0.3最大分支，0.2最大分支，0.1最大分支
   _temp = getBanHeXS(1,5,"亥卯","木") * Math.max(shui,mu);
   _p("亥卯半合木，加 ",_temp);
   _mu += _temp;
   _temp = getBanHeXS(2,5,"卯未","木") * Math.max(tu,mu);
   _p("卯未半合木，加 ",_temp);
   _mu += _temp;
   _temp = getBanHeXS(3,5,"亥未","木") * Math.max(shui,tu);
   _p("亥未半合木，加 ",_temp);
   _mu += _temp;
   _temp = getBanHeXS(1,6,"寅午","火") * Math.max(huo,mu);
   _p("寅午半合火，加 ",_temp);
   _huo += _temp;
   _temp = getBanHeXS(2,6,"午戌","火") * Math.max(huo,tu);
   _p("午戌半合火，加 ",_temp);
   _huo += _temp;
   _temp = getBanHeXS(3,6,"寅戌","火") * Math.max(mu,tu);
   _p("寅戌半合火，加 ",_temp);
   _huo += _temp;
   _temp = getBanHeXS(1,7,"巳酉","金") * Math.max(huo,jin);
   _p("巳酉半合金，加 ",_temp);
   _jin += _temp;
   _temp = getBanHeXS(2,7,"酉丑","金") * Math.max(jin,tu);
   _p("酉丑半合金，加 ",_temp);
   _jin += _temp;
   _temp = getBanHeXS(3,7,"巳丑","金") * Math.max(huo,tu);
   _p("巳丑半合金，加 ",_temp);
   _jin += _temp;
   _temp = getBanHeXS(1,8,"申子","水") * Math.max(jin,shui);
   _p("申子半合水，加 ",_temp);
   _shui += _temp;
   _temp = getBanHeXS(2,8,"子辰","水") * Math.max(shui,tu);
   _p("子辰半合水，加 ",_temp);
   _shui += _temp;
   _temp = getBanHeXS(3,8,"申辰","水") * Math.max(jin,tu);
   _p("申辰半合水，加 ",_temp);
   _shui += _temp;

     //e) 六合 合而不化(不隔一支有月支1.2月支，1.0月支，不隔一支二支无月支1.0最大分支，0.6最大分支，0.2最大分支)
    //         合化(有月支2.0月支 无月支1.5最大分支)
    _temp = getLiuHe(1,5,"丁壬","木") * Math.max(huo,shui);
    _p("丁壬合木加 ",_temp);
    _mu += _temp;
    _temp = getLiuHe(1,6,"戊癸","火") * Math.max(tu,shui);
    _p("戊癸合火加 ",_temp);
    _huo += _temp;
    _temp = getLiuHe(1,7,"乙庚","金") * Math.max(mu,jin);
    _p("乙庚合金加 ",_temp);
    _jin += _temp;
    _temp = getLiuHe(1,8,"丙辛","水") * Math.max(huo,jin);
    _p("丙辛合水加 ",_temp);
    _shui += _temp;
    _temp = getLiuHe(1,9,"甲己","土") * Math.max(mu,tu);
    _p("甲己合土加 ",_temp);
    _tu += _temp;
    _temp = getLiuHe(2,5,"寅亥","木") * Math.max(mu,shui);
    _p("寅亥半合木加 ",_temp);
    _mu += _temp;
    _temp = getLiuHe(2,6,"卯戌","火") * Math.max(mu,tu);
    _p("卯戌合火加 ",_temp);
    _huo += _temp;
    _temp = getLiuHe(2,7,"辰酉","金") * Math.max(tu,jin);
    _p("辰酉合金加 ",_temp);
    _jin += _temp;
    _temp = getLiuHe(2,8,"巳申","水") * Math.max(huo,jin);
    _p("巳申合水加 ",_temp);
    _shui += _temp;
    _temp = getLiuHe(2,9,"子丑","土") * Math.max(shui,tu);
    _p("子丑合土加 ",_temp);
    _tu += _temp;
    _temp = getLiuHe(2,10,"午未","土") * Math.max(huo,tu);
    _p("午未合土加 ",_temp);
    _tu += _temp;

    //l) 相害  -0.1各支
    _temp = getLiuHai(1,8,"子未相害");
    _tu += _temp * tu;
    _shui += _temp * shui;
    _p("子未相害，水加 ", _temp * shui,"土加 ",_temp * tu);
    _temp = getLiuHai(2,7,"丑午相害");
    _tu += _temp * tu;
    _huo += _temp * huo;
    _p("丑午相害，火加 ", _temp * huo,"土加 ",_temp * tu);
    _temp = getLiuHai(3,6,"寅巳相害");
    _mu += _temp * mu;
    _huo += _temp * huo;
    _p("寅巳相害，木加 ", _temp * mu,"火加 ",_temp * huo);
    _temp = getLiuHai(4,5,"卯辰相害");
    _tu += _temp * tu;
    _mu += _temp * mu;
    _p("卯辰相害，木加 ", _temp * mu,"土加 ",_temp * tu);
    _temp = getLiuHai(9,11,"申戌相害");
    _tu += _temp * tu;
    _jin += _temp * jin;
    _p("申戌相害，金加 ", _temp * jin,"土加 ",_temp * tu);
    _temp = getLiuHai(10,12,"酉亥相害");
    _jin += _temp * jin;
    _shui += _temp * shui;
    _p("酉亥相害，水加 ", _temp * shui,"金加 ",_temp * jin);

    //m) 相刑 -0.1各支
    getSanXing();

    if(desc.length()>len+10) {
      len = desc.length();
      _p();
    }

    //g) 相生 生者不损气,只是造势，其实没有生：不隔一支二支0.3生支，0.2生支，0.1生支
    //i) 相克 克者不损气，只是一种威胁(戊未戌克水力大-0.3,其它土0.1)：不隔一支二支-0.3克支，-0.2克支，-0.1克支
    //j) 暗合 如卯申暗合须紧临，其五行系数：0.2克支,意即抵消克气
    _temp = (getShengKeXS(YiJing.MU,1) + getShengKeXS(YiJing.MU,2)) * mu;
    _mu += _temp;
    _p("木干支远近生克影响加分 ",_temp);
    _temp = (getShengKeXS(YiJing.TU,1) + getShengKeXS(YiJing.TU,2)) * tu;
    _tu += _temp;
    _p("土干支远近生克影响加分 ",_temp);
    _temp = (getShengKeXS(YiJing.HUO,1) + getShengKeXS(YiJing.HUO,2)) * huo;
    _huo += _temp;
    _p("火干支远近生克影响加分 ",_temp);
    _temp = (getShengKeXS(YiJing.JIN,1) + getShengKeXS(YiJing.JIN,2)) * jin;
    //System.out.println(getShengKeXS(YiJing.JIN,1) +":"+ getShengKeXS(YiJing.JIN,2));
    _jin += _temp;
    _p("金干支远近生克影响加分 ",_temp);
    _temp = (getShengKeXS(YiJing.SHUI,1) + getShengKeXS(YiJing.SHUI,2)) * shui;
    _shui += _temp;
    _p("水干支远近生克影响加分 ",_temp);

    //天覆(即天干生之): 0.1*此干分数 地载: 0.1此支基本分
    //天克：-0.1*此干分数 地克：-0.1此支基本分
    //天合地合化五行（只有戊子、辛巳、丁亥、壬午四日）：0.1*此支分数
    getGZRelate();

    //五行在其它3支得一墓库如木墓在未。。。 0.35此支基本分
    //五行在其它3支得一余气如木余气在辰。。 0.7此支基本分
    //五行在其它3支长生沐禄刃              1.0此支基本分
    getMuYuWang();

    //总分（相当于五行透干者加分，不透者不加，天干加地支即为总分）
    if(pub.getHowWx(YiJing.MU) == 0) {
      SiZhu.muCent1 = 0;
    }else {
      SiZhu.muCent1 = (int)(_mu + mu);
    }

    if(pub.getHowWx(YiJing.HUO) == 0) {
      SiZhu.huoCent1 = 0;
    }else {
      SiZhu.huoCent1 = (int)(_huo+huo);
    }

    if(pub.getHowWx(YiJing.TU) == 0) {
      SiZhu.tuCent1 = 0;
    }else {
      SiZhu.tuCent1 = (int)(_tu+tu);
    }

    if(pub.getHowWx(YiJing.SHUI) == 0) {
      SiZhu.shuiCent1 = 0;
    }else {
      SiZhu.shuiCent1 = (int)(_shui+shui);
    }

    if(pub.getHowWx(YiJing.JIN) == 0) {
      SiZhu.jinCent1 = 0;
    }else {
      SiZhu.jinCent1 = (int)(_jin+jin);
    }

    ////转换成百分制 x/2 40+(x-80)/2.5 60+(x-130)/4 75+(x-190)/17 90+(x-230)/5
    _getBaiFenZhi();

    _p();
    int x0 = pub.getShiShenCent(0);
    int x1 = pub.getShiShenCent(1);
    int x2 = pub.getShiShenCent(2);
    int x3 = pub.getShiShenCent(3);
    int x4 = pub.getShiShenCent(4);
    desc += "再综合远近生克影响，最后断定，日干"+x0+"分"+pub.getShiShenWSDesc(0)+",";
    if(x1 >0)
      desc += "食伤"+x1+"分"+pub.getShiShenWSDesc(1)+"；";
    if(x2 >0)
      desc += "财星"+x2+"分"+pub.getShiShenWSDesc(2)+"；";
    if(x3 >0)
      desc += "官杀"+x3+"分"+pub.getShiShenWSDesc(3)+"；";
    if(x4 >0)
      desc += "印星"+x4+"分"+pub.getShiShenWSDesc(4)+"；";
    //Debug.out("\r\n\r\n木="+SiZhu.muCent1+"，火="+SiZhu.huoCent1+"，土="+SiZhu.tuCent1+"，金="+SiZhu.jinCent1+"，水="+SiZhu.shuiCent1+"。");
    //Debug.out("\r\n"+_desc);

    return desc;
  }

}
