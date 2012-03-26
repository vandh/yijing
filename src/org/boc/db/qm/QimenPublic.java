package org.boc.db.qm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.BadLocationException;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Messages;

public class QimenPublic {
	private DaoQiMen daoqm;
	private DaoSiZhuMain daosz;  //四柱的dao
	public final String NOKG = "<no>";  //加在字符串前面，表示不需要空格
	public final String NEWLINE = "<newline>";  //表示换行
	public final int RIKONGWANG = 3; //日空亡
	public final int SHIKONGWANG = 4; //时空亡
	public final int YSHENYEAR=1,YSHENMONTH=2,YSHENDAY=3,YSHENHOUR=4; 
	
  public QimenPublic(DaoQiMen daoqm, DaoSiZhuMain daosz) {
  	this.daoqm = daoqm;
  	this.daosz = daosz;
  }
  
  public DaoQiMen getDaoQiMen() {
  	return this.daoqm;
  }
  public DaoSiZhuMain getDaoSiZhuMain() {
  	return this.daosz;
  }
  
	//是否飞干
	public boolean isFeigan() {
		int riganGong = getTianGong(SiZhu.rg, SiZhu.rz); //得到日干落宫，只要判断地盘干有geng就可以了
		int[] dpjy = this.getDpjy(riganGong);
		return (dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG);  	
	}
	//是否伏干
	public boolean isFugan() {
		int riganGong = getTianGong(SiZhu.rg, SiZhu.rz); //得到日干落宫，只要判断天盘干有geng就可以了
		int[] tpjy = this.getTpjy(riganGong);
		return (tpjy[0]==YiJing.GENG || tpjy[1]==YiJing.GENG);  	
	}
	
	/**
	 * 得到幺法暗干
	 * 时干找值使，阳局按（丁丙乙戊己庚辛壬癸）顺布九宫，阴局按（丁丙乙戊己庚辛壬癸）逆布九宫；
	 * 伏吟局（此时值使门地盘天干与时干相同），为了多看信息，则时干入中宫重排，阳顺阴逆；
	 */
	private final int[] ANGAN = {YiJing.DING,YiJing.BING,YiJing.YI,YiJing.WUG,YiJing.JI,YiJing.GENG,YiJing.XIN,YiJing.REN,YiJing.GUI}; 
	public int getYaoAngan(int gong) {		
		return getAngan(gong);
	}
	public int getAngan(int gong) {		
		return gYaoangan[gong];
	}
	private int _getYaoAngan(int gong) {
		int zsgong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);
		int zstgan = daoqm.gInt[2][3][zsgong];
		int zsdgan = daoqm.gInt[4][5][zsgong];
		int sg = this.getTiangan(SiZhu.sg, SiZhu.sz);
		boolean isyang = daoqm.getJu()>0;
		
		int location = 0;  //时干落宫在ANGONG中元素的位置
		int how = zstgan==zsdgan ? 5 : zsgong; //伏呤格时干落5宫，否则落值使宫
		//如果时干落五宫与五宫地盘干相同，则还是入zsgong，为了看更多信息需要来回折腾一下的：）
		if(how==5 && daoqm.gInt[4][5][5]==sg) {
			how = zsgong;
		}
		for(int i=0; i<9; i++) {
			if(ANGAN[i]==sg) {
				location = i; 
				break;
			}
		}		
			
		if(isyang)
			return ANGAN[(gong-how+location+9)%9];
		else
			return ANGAN[(location+9-gong+how)%9];
	}
	
	/**
	 * 王师暗干，时干加在值使门上，然后按照天盘的顺序或者地盘的三奇六仪顺序排列一圈
	 * @param gong
	 * @return
	 */
	public int getWangAngan(int gong) {		
		return gWangangan[gong];
	}
	private int _getWangAngan(int g) {
		int zsgong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);
		int sg = this.getTiangan(SiZhu.sg, SiZhu.sz);
	//按18349276布好
		int[] tpjys = {daoqm.gInt[2][3][1],daoqm.gInt[2][3][8],
				daoqm.gInt[2][3][3],daoqm.gInt[2][3][4],daoqm.gInt[2][3][9],
				daoqm.gInt[2][3][2],daoqm.gInt[2][3][7],daoqm.gInt[2][3][6]};  

  	int i=0,j = 0, x=0;;
  	for(; i<QiMen.jgxh.length; i++) {  //转盘
  		if(QiMen.jgxh[i]==zsgong) break;
  	}
  	for(; j<tpjys.length; j++) {
  		if(tpjys[j]==sg) break;
  	}

  	for(int k=0; k<QiMen.jgxh.length; k++) {
  		if(QiMen.jgxh[k]==g) {
  			//x = (k - i + j + 8)%7==0?7:(k - i + j + 8)%7;
  			x = (k - i + j + 8)%8;
  	    break;
  		}
  	}

  	return tpjys[x]; 
	}
	
	/**
	 * 判断天干落宫是否合局，用来判断成绩优劣，合局至少为1234
	 * 1）天干在宫为旺相，兼看月令状态，其落宫在月令旺相；
	 * 2）得吉格；
	 * 3）门旺相，即保证不制不迫，门吉凶不论；
	 * 4）不旬空
	 * 5）得三奇、星的吉凶、神的吉凶不论
	 * @param gong
	 * @return 死墓绝或空亡=-1为失局无气，衰病胎养=0为稍有气，其它旺相加吉格=1为合局
	 */
	public String[] isganHeju(int gong) {
		String[] desc = new String[2];
		int gan = this.getTpjy(gong)[0];
		int zi = 0;
		boolean isKong = isKong(gong, SHIKONGWANG);		//判断该宫是否在时柱旬空		
		String[] ganws = gettgWS(gan, zi);  //得到日干旺衰情况
		String[] jige = getJixiongge(gong);  //是否得吉格
		String[] sanji = getSanji(gong); 	//是否得三奇		
		String[] bmws = getmenWS(gong);    //门的旺衰
		String[] xingjx = getxingJX(gong);    //星的吉凶
		String[] shenjx = getshenJX(gong);    //神的的吉凶

		//1. 判断是否合局
		if(ganws[0].equals("-1") || isKong) desc[0]="-1";
		else if(ganws[0].equals("1") && jige[0].equals("1") && !isKong && !bmws[0].equals("-1")) desc[0]="1";
		else desc[0]="0";
		//2. 描述信息
		desc[1] = (isKong?"本宫空亡；":"")+ganws[1]+jige[1]+sanji[1]+bmws[1]+xingjx[1]+shenjx[1];
		
		return desc;		
	}
	public String[] isganHeju(int gan, int zi) {		
		int gong = getTianGong(gan, zi);
		return isganHeju(gong);
	}
	
	/**
	 * 判断八神落宫是否合局，合局至少为1234
	 * 1）神为吉神；
	 * 2）得吉格；
	 * 3）门旺相，即保证不制不迫，门吉凶不论；
	 * 4）不旬空
	 * 5）得三奇，星的吉凶、干的旺衰等不论
	 * @param gong
	 * @return 满足123=1为合局，否则为失局为-1
	 */
	public String[] isshenHeju(int gong) {
		if(gong==5) gong=this.getJigong();
		String[] desc = new String[2];
		//int gong = this.getShenGong(shen);
		
		boolean isKong = isKong(gong, SHIKONGWANG);		//判断该宫是否在时柱旬空
		String[] jige = getJixiongge(gong);  //是否得吉格
		String[] sanji = getSanji(gong); 	//是否得三奇		
		String[] bmws = getmenWS(gong);    //门的旺衰
		String[] xingjx = getxingJX(gong);    //星的吉凶
		String[] shenjx = getshenJX(gong);    //神的的吉凶

		//1. 判断是否合局
		if(!shenjx[0].equals("-1") && jige[0].equals("1") && !isKong && !bmws[0].equals("-1")) 
			desc[0]="1";
		else 
			desc[0]="-1";
		
		//2. 描述信息
		desc[1] = (isKong?"本宫空亡；":"")+shenjx[1]+jige[1]+bmws[1]+sanji[1]+xingjx[1];
		
		return desc;		
	}
	
	/**
	 * 判断八门合局情况
	 * 	合局至少为123｜124｜125：
	 * 1）门在宫为旺相，兼看月令状态，其落宫在月令旺相；
	 * 2）得吉格；不旬空；
	 * 3）得三奇；
	 * 4）得吉神；
	 * 5）得吉星；
	 *  失局无气为：门在宫中处死地囚地
	 *  失局稍有气为：除上述二种外的所有情况皆是；如门在宫处休地、有吉格、三奇；
	 * @return 
	 *   [0]= -1为无气，0为失局稍有气，1为合局
	 *   [1]=描述：[X]在Y宫长生，月令旺，其落宫月令处旺地，得吉格A+B=C，[不]得三奇，得［吉｜凶］星X1，得［吉｜凶］神X2。
	 * @param rigan=日干；gong=宫；
	 */
	public String[] ismenHeju(int gong) {
		if(gong==5) gong=this.getJigong();
		String[] desc = new String[2];
		
		boolean isKong = isKong(gong);		//判断该宫是否在时柱旬空
		String[] jxws = this.getmenWS(gong);  //门的旺衰
		String[] jige = this.getJixiongge(gong);  //是否得吉格
		String[] sanji = this.getSanji(gong); 	//是否得三奇
		String[] shenjx = this.getshenJX(gong); //神的吉凶
		String[] xingjx = this.getxingJX(gong);  //是否得吉星
		//1. 判断是否合局
		if(jxws[0].equals("-1")) desc[0]="-1";
		else if(jxws[0].equals("1") && jige[0].equals("1") && !isKong && 
				(sanji[0].equals("1") || shenjx[0].equals("1") || xingjx.equals("1"))) desc[0]="1";
		else desc[0]="0";
		//2. 描述信息
		desc[1] = (isKong?"本宫空亡；":"")+jxws[1]+jige[1]+sanji[1]+shenjx[1]+xingjx[1];
		
		return desc;		
	}
	
	/**
	 * 判断某一宫九星的合局情况
	 * 	合局至少满足1234：
	 * 1）星落宫旺相，兼看月令和落宫；
	 * 2）得吉格；
	 * 3）不旬空。
	 * 4）门旺相，即不逢制和迫；
	 * 5）得三奇
	 *  九星失局无气为：星在宫中处囚地废地，兼看月令；
	 *  九星失局稍有气为：除上述二种情况皆是；
	 * @return 
	 *   [0]= -1为失局无气，0为失局稍有气，1为合局
	 *   [1]=描述：[X]在Y宫长生，月令旺，其落宫月令处旺地，得吉格A+B为C，[不]得三奇，得［吉｜凶］门X1。
	 * @param gong=宫；
	 */
	public String[] isxingHeju(int gong) {
		if(gong==5) gong=this.getJigong();
		String[] desc = new String[2];
		
		boolean isKong = isKong(gong);		//判断该宫是否在时柱旬空
		String[] jxws = this.getxingWS(gong);  //星的旺衰
		String[] jige = this.getJixiongge(gong);  //是否得吉格
		String[] sanji = this.getSanji(gong); 	//是否得三奇
		String[] menjx = this.getmenJX(gong);   //门的吉凶
		//1. 判断是否合局
		if(jxws[0].equals("-1")) desc[0]="-1";
		else if(jxws[0].equals("1") && jige[0].equals("1") && !isKong && !menjx[0].equals("-1")) desc[0]="1";
		else desc[0]="0";
		//2. 描述信息
		desc[1] = jxws[1]+jige[1]+sanji[1]+menjx[1];
		
		return desc;
	}
	
	/**
	 * 判断某一宫的八门在该宫是否旺相，兼看月令和落宫；
	 * @param gong
	 * @return 
	 * 	 [0]= -1为无气=制迫墓，0为稍有气=和即休地，1为旺相=义相
	 *   [1]=描述：[X]在Y宫长生，月令旺，其落宫月令处旺地。
	 */
	public String[] getmenWS(int gong) {
		if(gong==5) gong=this.getJigong();
		int men = daoqm.gInt[3][1][gong];
		
		String[] desc = new String[2];
		StringBuilder sb = new StringBuilder();
		
		String wxsqf = QiMen.men_gong[men][gong]; 
		if(wxsqf.equals(QiMen.zphym6) || wxsqf.equals(QiMen.zphym4)) desc[0]="1";
		else if(wxsqf.equals(QiMen.zphym3)) desc[0]="0";
		else desc[0]="-1";
		
		sb.append("[").append(QiMen.bm1[men]).append("门]落宫");
		sb.append(wxsqf).append("，月令").append(QiMen.gong_yue[men][SiZhu.yz]);
		sb.append("，本宫月令").append(QiMen.gong_yue[gong][SiZhu.yz]).append("；");
		
		desc[1] = sb.toString();		
		return desc;		
	}
	/**
	 * 判断某一宫的九星在该宫是否旺相，兼看月令和落宫；
	 * @param gong
	 * @return 
	 * 	 [0]= -1为无气=囚废，0为稍有气=休，1为旺相=旺相
	 *   [1]=描述：[X]在Y宫长生，月令旺，其落宫月令处旺地。
	 */
	public String[] getxingWS(int gong) {
		if(gong==5) gong=this.getJigong();
		int xing = daoqm.gInt[2][1][gong];
		if(xing==0) xing=5;
		String[] desc = new String[2];
		StringBuilder sb = new StringBuilder();
		
		String wxsqf = QiMen.xing_gong[xing][gong]; 
//		if(wxsqf==null) {
//			System.err.println("星="+xing+";宫="+gong+"，在QiMen.xing_gong［星］［宫］找不到对应的旺相休囚！");
//			return null;
//		}
		if(wxsqf.equals(QiMen.wxxqs1) || wxsqf.equals(QiMen.wxxqs2)) desc[0]="1";
		else if(wxsqf.equals(QiMen.wxxqs3)) desc[0]="0";
		else desc[0]="-1";
		
		sb.append("[天").append(QiMen.jx1[xing]).append("]落宫");
		sb.append(wxsqf).append("，月令").append(QiMen.xing_yue[xing][SiZhu.yz]);
		sb.append("，本宫月令").append(QiMen.gong_yue[gong][SiZhu.yz]).append("；");
		
		desc[1] = sb.toString();		
		return desc;		
	}
	/**
	 * 判断天干在落宫和月令的状态是否旺相，以落宫为主，兼看月令和其落宫在月令状态：
	 * @return 
	 *   [0]= 死墓绝=-1为无气，衰病胎养=0|-2为稍有气，其它=1为旺相
	 *   [1]=描述：[X]在Y宫长生，月令旺，其落宫月令处旺地。
	 * @param gan=天干；zi=地支；如果gan=甲，则必须用gan+zi才能判断；
	 */
	public String[] gettgWS(int gong) {
		if(gong==5) gong=this.getJigong();
		int gan = this.getTpjy(gong)[0];
		return gettgWS(gan,0);
	}
	public String[] getdgWS(int gong) {
		int gan = this.getDpjy(gong)[0];
		return gettgWS(gan,0);
	}
	public String[] gettgWS(int gan, int zi) {
		int gong = this.getTianGong(gan, zi);
		if(gan==YiJing.JIA) gan = daoqm.getXunShu(gan, zi)+4;
		
		String[] desc = new String[2];
		StringBuilder sb = new StringBuilder();
		
		desc[0] = QiMen.gan_gong_wx3[gan][gong]+""; //
		sb.append("[").append(YiJing.TIANGANNAME[gan]).append("]落").append(gong).append("宫");
		sb.append(QiMen.gan_gong_wx[gan][gong]).append("，月令").append(SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gan][SiZhu.yz]]);
		sb.append("，本宫月令").append(QiMen.gong_yue[gong][SiZhu.yz]).append("；");
		
		desc[1] = sb.toString();		
		return desc;
	}
	/**
	 * 判断该干与支组合在天盘落何宫
	 * @param gan：天干
	 * @param zi：地支
	 * @return
	 */
	public int getTianGong(int gan, int zi) {
		if(gan==0) return 0;
		if(tpgGong==null)
			return _getTianGong(gan,zi);  //初始时这些缓存可能还没有起用
		return tpgGong.get(this.getTiangan(gan, zi));
	}
	public int _getTianGong(int gan, int zi) {
    int jiakaitou = -1; 
    for(int i=1; i<=9; i++) {
    	if(daoqm.iszf() && i==5) continue;
    	if(gan==YiJing.JIA) jiakaitou = daoqm.getXunShu(gan, zi)+4;
    		
    	if(daoqm.gInt[2][3][i]==gan || jiakaitou==daoqm.gInt[2][3][i]) return i;
    	//if(daoqm.gInt[2][1][i]==2) {
    	if(daoqm.isTpJigong(i)) {
    		if(daoqm.gInt[4][5][5]==gan || jiakaitou==daoqm.gInt[4][5][5]) return i;
    	}
    	//System.out.println("daoqm.gInt[2][3]["+i+"]="+daoqm.gInt[2][3][i]);
    }
    Messages.error("QimenPublic:getTianGong() 322行：出错啦，没有找到该干支［"+YiJing.TIANGANNAME[gan]+YiJing.DIZINAME[zi]+"]所在的宫！");
    return 0;
	}
	/**
	 * 判断该干与支组合在地盘落何宫
	 * @param gan：天干
	 * @param zi：地支
	 * @return
	 */
	public int getDiGong(int gan, int zi) {
		if(gan==0) return 0;
		if(dpgGong==null) return _getDiGong(gan,zi);
		return dpgGong.get(gan==YiJing.JIA ? daoqm.getXunShu(gan, zi)+4 : gan);
	}
	public int _getDiGong(int gan, int zi) {
    int jiakaitou = -1; 
    for(int i=1; i<=9; i++) {
    	//如果是转盘，不能直接返回第5宫，转盘有寄宫，飞盘没有
    	if(daoqm.iszf() && i==5) continue;
    	if(gan==YiJing.JIA) jiakaitou = daoqm.getXunShu(gan, zi)+4;
    		   
    	if(daoqm.gInt[4][5][i]==gan || jiakaitou==daoqm.gInt[4][5][i]) return i;
    	
    	if(daoqm.isJiGong(i)) {  
    		if(daoqm.gInt[4][5][5]==gan || jiakaitou==daoqm.gInt[4][5][5]) return i;
    	}
    	
    }
    Messages.error("QimenPublic:getDiGong() 343行：出错啦，没有找到该干支［"+YiJing.TIANGANNAME[gan]+YiJing.DIZINAME[zi]+"]所在的宫！");
    return 0;
	}
	//得到某个甲+地支对应的天干，如果不是甲开头的，则直接返回
	public int getTiangan(int gan ,int zi) {
		return (gan==YiJing.JIA)?daoqm.getXunShu(gan, zi)+4:gan;
	}
	/**
	 * 宫中是否得三奇，天盘与地盘奇仪均算
	 * 如果入墓也算，但要注明
	 * @return [0]=-1无1有；[1]=
	 */
	public String[] getSanji(int gong) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //天盘干1，2，地盘干1，2
		String[] desc = {"-1","得三奇["};//不得三奇；
		
		tpg1 = daoqm.gInt[2][3][gong];
		//if(daoqm.gInt[2][1][gong]==2) tpg2 = daoqm.gInt[4][5][5];
		if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
		dpg1 = daoqm.gInt[4][5][gong];
		//if(gong==2) 
		if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];
		
		int[] gans = {tpg1,tpg2,dpg1,dpg2};
		for(int gan : gans) {		
			if(gan==YiJing.YI || gan==YiJing.BING || gan==YiJing.DING) {
				desc[0]="1";
				desc[1] +=(QiMen.gan_gong_mu[tpg1][gong]!=null ? YiJing.TIANGANNAME[gan]+"入宫墓" : YiJing.TIANGANNAME[gan]);
			}
		}
			
		if(desc[0].equals("-1")) desc[1] = "不得三奇；"; 
		else desc[1] += "]；"; 
		return desc; 
	}
	/**
	 * 得到该宫中干与干组合的吉凶格,只要有一个吉格就算是吉
	 * @return [0] 0凶1吉；[1]描述，所有组合的吉凶格名称
	 */
	public String[] getJixiongge(int gong) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //天盘干1，2，地盘干1，2
		String[] rs = new String[2]; 
		
		tpg1 = daoqm.gInt[2][3][gong];
		//if(daoqm.gInt[2][1][gong]==2) tpg2 = daoqm.gInt[4][5][5];
		if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
		dpg1 = daoqm.gInt[4][5][gong];
		//if(gong==2) dpg2 = daoqm.gInt[4][5][5];
		if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];
		
		int ge1 = QiMen.gan_gan[tpg1][dpg1];
		boolean isJige1 = QiMen.isJige(ge1) , isJige2=true, isJige3=true, isJige4=true;
		rs[0] = isJige1 ? "1" : "0";
		rs[1] = YiJing.TIANGANNAME[tpg1]+"+"+YiJing.TIANGANNAME[dpg1]+"为"+QiMen.gGejuDesc[ge1][0];		
		
		if(dpg2!=0) {
			ge1 = QiMen.gan_gan[tpg1][dpg2];
			isJige2 = QiMen.isJige(ge1);
			rs[0] = isJige1 && isJige2 ? "1" : "0";
			rs[1] += ","+YiJing.TIANGANNAME[tpg1]+"+"+YiJing.TIANGANNAME[dpg2]+"为"+QiMen.gGejuDesc[ge1][0];
		}
		
		if(tpg2!=0) {
			ge1 = QiMen.gan_gan[tpg2][dpg1];
			isJige3 = QiMen.isJige(ge1);
			rs[0] = isJige1 && isJige2 && isJige3 ? "1" : "0";
			rs[1] += ","+YiJing.TIANGANNAME[tpg2]+"+"+YiJing.TIANGANNAME[dpg1]+"为"+QiMen.gGejuDesc[ge1][0];
			if(dpg2!=0) {
				ge1 = QiMen.gan_gan[tpg2][dpg2];
				isJige4 = QiMen.isJige(ge1);
				rs[0] = isJige1 && isJige2 && isJige3 && isJige4 ? "1" : "0";
				rs[1] += ","+YiJing.TIANGANNAME[tpg2]+"+"+YiJing.TIANGANNAME[dpg2]+"为"+QiMen.gGejuDesc[ge1][0];
			}
		}
		if(rs[0].equals("1")) 
			rs[1] += "吉格；";
		else 
			rs[1] += "凶格；";
		return rs; 
	}
	public Integer[] getJixiongge2(int gong,boolean iszf) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //天盘干1，2，地盘干1，2
		List<Integer> rs = new ArrayList<Integer>(4); 
		
		tpg1 = daoqm.gInt[2][3][gong];		
		dpg1 = daoqm.gInt[4][5][gong];
		if(iszf) {
			if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
			if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];
		}
		
		rs.add(QiMen.gan_gan[tpg1][dpg1]);
		if(dpg2!=0) 
			rs.add(QiMen.gan_gan[tpg1][dpg2]);
			
		if(tpg2!=0) {
			rs.add(QiMen.gan_gan[tpg2][dpg1]);
			if(dpg2!=0) rs.add(QiMen.gan_gan[tpg2][dpg2]);
		}

		return (Integer[])rs.toArray(new Integer[rs.size()]); 
	}
	
	/**
	 * 判断某一宫中门的吉凶；
	 * @param gong
	 * @return 
	 * 	 [0]= -1为凶门，0为平门，1吉门
	 *   [1]=描述：[X]门吉|凶。
	 */
	public String[] getmenJX(int gong) {
		int men = daoqm.gInt[3][1][gong];
		
		String[] desc = new String[2];
		desc[0]=QiMen.bmjx[men]+"";
		desc[1]="["+QiMen.bm1[men]+"门]"+(QiMen.bmjx[men]==-1?"凶；":QiMen.bmjx[men]==0?"平；":"吉；");

		return desc;		
	}
	/**
	 * 判断某一宫中星的吉凶；
	 * @param gong
	 * @return 
	 * 	 [0]= -1为凶，0为中，1吉
	 *   [1]=描述：[X]门吉|凶。
	 */
	public String[] getxingJX(int gong) {
		int xing = daoqm.gInt[2][1][gong];
		
		String[] desc = new String[2];
		desc[0]=QiMen.jxjx[xing]+"";
		desc[1]="[天"+QiMen.jx1[xing]+"]"+(QiMen.jxjx[xing]==-1?"凶；":QiMen.jxjx[xing]==0?"平；":"吉；");

		return desc;		
	}
	
	/**
	 * 判断某一宫中神的吉凶；
	 * @param gong
	 * @return 
	 * 	 [0]= -1为凶，0为中，1吉
	 *   [1]=描述：[X]吉|凶。
	 */
	public String[] getshenJX(int gong) {
		int shen = daoqm.gInt[1][1][gong];
		
		String[] desc = new String[2];
		desc[0]=QiMen.bsjx[shen]+"";
		desc[1]="["+QiMen.bs2[shen]+"]"+(QiMen.bsjx[shen]==-1?"凶；":QiMen.bsjx[shen]==0?"平；":"吉； ");

		return desc;		
	}
	
	//<no>表示不需要空格
  public String format(StringBuffer sb, String[] array) {  	
  	for(String s:array) {
  		if(s==null) break;
  		if(s.equals(this.NEWLINE)) {
  			sb.append("\r\n");
  			continue;
  		}
  		if(s.startsWith(NOKG))
  			sb.append(s.replaceAll(NOKG,"")+"\r\n");
  		else
  			sb.append("    "+s+"\r\n");
  	}
  	return sb.toString(); 
  }
  /**
   * 判断该宫是否在日或时旬空
   * @param gong
   * @param day 3为日柱，4为时柱
   * @return
   */
  public boolean isKong(int gong) {
  	return isKong(gong, this.SHIKONGWANG);
  }
  public boolean isKong(int gong,int day) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
    
    int[] xk; 
    if(day==SHIKONGWANG)
    	xk = daoqm.getPublic().getXunKong(SiZhu.sg,SiZhu.sz);  //得到时柱旬空地支
    else if(day==RIKONGWANG)
    	xk = daoqm.getPublic().getXunKong(SiZhu.rg,SiZhu.rz);  //得到日柱旬空地支
    else
    	return false;
    
    return dz1==xk[0] || dz2==xk[0] || dz1==xk[1] || dz2==xk[1];    
  }
  /**
   * 判断该宫是否空亡对冲之宫
   * @param day 旬空依据，3为日柱，4为时柱
   */
  public boolean isChKong(int gong) {
  	return isChKong(gong, SHIKONGWANG);
  }
  public boolean isChKong(int gong, int day) {
  	//先得到该宫的对冲之宫: 1-9, 2-8,3-7,4-6
  	int[] ch = {0, 9, 8, 7, 6, 0, 4, 3, 2, 1};  //定义某宫对应的对冲之宫
  	int chGong = ch[gong];
  	
  	return isKong(chGong, day);
  }
  /**
   * 判断该宫是否马星对冲之宫
   * @param day 旬空依据，3为日柱马，4为时柱马
   */
  public boolean isChMa(int gong) {
  	return isChMa(gong, SHIKONGWANG);
  }
  public boolean isChMa(int gong, int day) {
  	//先得到该宫的对冲之宫: 1-9, 2-8,3-7,4-6
  	int[] ch = {0, 9, 8, 7, 6, 0, 4, 3, 2, 1};  //定义某宫对应的对冲之宫
  	int chGong = ch[gong];
  	
  	return day==SHIKONGWANG ? isYima(chGong) : isYimaOfRi(chGong);
  }
  /**
   * 判断该天盘天干在所落之宫是否入死墓绝
   * @param gan+zi，因为如果以甲开头，要转换成六仪
   * @return
   */
  public String isTGanSMJ(int gan, int zi) {
  	int gong = this.getTianGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	String rs = QiMen.gan_gong_si[gan][gong]+
  				QiMen.gan_gong_mu[gan][gong]+
  				QiMen.gan_gong_jue[gan][gong];
  	
  	rs = rs.replaceAll("null", "").trim();
  	return rs.equals("") ? null : rs;
  }
  public boolean isTGanSMJ(int gong) {
  	int[] tpjy = this.getTpjy(gong);

    return isTGanSMJ(tpjy[0],0)!=null || isTGanSMJ(tpjy[1],0)!=null;
  }
  //是否天盘干入宫墓
  public boolean isTGanMu(int gan, int zi) {
  	int gong = this.getTianGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	return QiMen.gan_gong_mu[gan][gong]!=null;
  }
  public boolean isTGanMu(int gong) {
  	int[] tpjy = this.getTpjy(gong);    
  	return QiMen.gan_gong_mu[tpjy[0]][gong]!=null || QiMen.gan_gong_mu[tpjy[1]][gong]!=null;
  }
  //是否地盘干入墓
  public boolean isDGanMu(int gan, int zi) {
  	gan = this.getTiangan(gan, zi);
  	int gong = this.getDiGong(gan, zi);
  	return QiMen.gan_gong_mu[gan][gong]!=null ;
  }
  public boolean isDGanMu(int gong) {
  	int[] dpjy = this.getDpjy(gong);    
  	return QiMen.gan_gong_mu[dpjy[0]][gong]!=null || QiMen.gan_gong_mu[dpjy[1]][gong]!=null;
  }
  /**
   * 判断该地盘天干在所落之宫是否入死墓绝
   * @param gan+zi，因为如果以甲开头，要转换成六仪
   * @return
   */
  public String isDGanSMJ(int gan, int zi) {
  	int gong = this.getDiGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	String rs = QiMen.gan_gong_si[gan][gong]+
  				QiMen.gan_gong_mu[gan][gong]+
  				QiMen.gan_gong_jue[gan][gong];
  	
  	rs = rs.replaceAll("null", "").trim();
  	return rs.equals("") ? null : rs;
  }
  public boolean isDGanSMJ(int gong) {
  	int[] dpjy = this.getDpjy(gong);
  	return isTGanSMJ(dpjy[0],0)!=null || isTGanSMJ(dpjy[1],0)!=null;
  }
  /**
   * 八门是否伏呤
   */
  public boolean isMenFu() {
  	return QiMen.da_men_gong[daoqm.gInt[3][1][1]][1]==3; 
  }
  /**
   * 八门是否反呤
   */
  public boolean isMenFan() {
  	return QiMen.da_men_gong[daoqm.gInt[3][1][1]][1]==4; 
  }
  /**
   * 九星是否伏呤
   */
  public boolean isXingFu() {
  	return QiMen.da_xing_gong[daoqm.gInt[2][1][1]][1]==5;
  }
  /**
   * 九星是否反呤
   */
  public boolean isXingFan() {
  	return QiMen.da_xing_gong[daoqm.gInt[2][1][1]][1]==6; 
  }
  //得到某宫的天盘奇仪
  public int[] getTpjy(int gong) {
  	int[] rs = {daoqm.gInt[2][3][gong],0};
  	
  	//if(daoqm.gInt[2][1][gong]==2)
  	if(daoqm.isTpJigong(gong))
  		rs[1]=daoqm.gInt[4][5][5]; 
  	
  	return rs; 
  }
  //得到某宫的地盘奇仪
  public int[] getDpjy(int gong) {
  	int[] rs = {daoqm.gInt[4][5][gong],0};
  	
  	//if(gong==2)
  	if(daoqm.isJiGong(gong)) 
  		rs[1]=daoqm.gInt[4][5][5]; 
  	
  	return rs; 
  }
  /**
   * 得到指定的门的落宫
   * bm2 = {0,   1休,  8生, 3伤, 4杜, 9景, 2死, 7惊, 6开}
   */
  public int getMenGong(int men) {
  	if(menGong==null) return _getMenGong(men);
  	return menGong.get(men);
  }
  public int _getMenGong(int men) {
  	for(int i=0; i<10; i++) {
  		if(daoqm.gInt[3][1][i]==men) return i; 
  	}
  	return 0;
  }
  
  /**
   * 得到指定的神神神的落宫
   * 符1、蛇2、阴3、六4、白5、玄6、地7、天8
   */
  public int getShenGong(int shen) {
  	return shenGong.get(shen);
  }
  public int _getShenGong(int shen) {
  	for(int i=0; i<10; i++) {
  		if(daoqm.gInt[1][1][i]==shen) return i; 
  	}
  	return 0;
  }
  
  /**
   * 得到指定的星的落宫
   * jx2 = 1蓬 2芮 3冲 4辅 5禽 6心 7柱 8任 9英
   */
  public int getXingGong(int xing) {
  	return xingGong.get(xing);
  }
  public int _getXingGong(int xing) {
  	for(int i=0; i<10; i++) {
  		if(daoqm.gInt[2][1][i]==xing) return i; 
  	}
  	return 0;
  }
  /**
   * 判断该宫是内盘还是外盘
   * 阳局1、8、3、4为内盘
   */
  public boolean isNenpan(int gong) {
  	int ju = daoqm.getJu();
  	return (ju>0 && (gong==1 || gong==8 || gong==3 || gong==4)) ||
  	(ju<0 && (gong==9 || gong==2 || gong==7 || gong==6));
  }
  /**
   * 判断g1宫是否冲克g2宫
   */
  public boolean isChongke(int g1, int g2) {
  	int[][] chke = new int[10][3];
  	chke[1]=new int[]{2,8,9};
  	chke[2] = new int[]{8,3,4};
  	chke[3] = new int[]{7,6};
  	chke[4] = new int[]{6,7};
  	chke[6] = new int[]{4,9};
  	chke[7] = new int[]{3,9};
  	chke[8] = new int[]{2,3,4};
  	chke[9] = new int[]{1};
  	
  	int[] ckgong = chke[g2];
  	for(int i=0; i<ckgong.length; i++)
  		if(ckgong[i]==g1) return true;
  	
  	return false;
  }
  /** 对上面的方法改个名 **/
  public boolean isGongChongke(int g1, int g2) {
  	return this.isChongke(g1, g2);
  }
  /**
   * g1与g2是否相克
   * @param g1
   * @param g2
   * @return
   */
  public boolean isGongke2(int g1, int g2) {
  	return isGongke(g1,g2) || isGongke(g2,g1);
  }
  //g1是否克g2
  public boolean isGongke(int g1, int g2) {
  	int[][] chke = new int[10][3];
  	chke[1]=new int[]{2,8};
  	chke[2] = new int[]{3,4};
  	chke[3] = new int[]{7,6};
  	chke[4] = new int[]{6,7};
  	chke[6] = new int[]{9};
  	chke[7] = new int[]{9};
  	chke[8] = new int[]{3,4};
  	chke[9] = new int[]{1};
  	
  	int[] ckgong = chke[g2];
  	for(int i=0; i<ckgong.length; i++)
  		if(ckgong[i]==g1) return true;
  	
  	return false;
  }
  /**
   * 判断g1宫是否冲g2宫
   */
  public boolean isGongChong(int g1, int g2) {
  	return new int[]{0,9,8,7,6,0,4,3,2,1}[g1]==g2;
  }
  /**
   * 得到g1宫的对冲宫
   */
  public int getChongGong(int g1) {
  	
  	return new int[]{0,9,8,7,6,0,4,3,2,1}[g1];
  }
  
  /**
   * 判断g1宫是否生g2宫
   */
  public boolean isGongSheng(int g1, int g2) {
  	return this.isSheng(g1, g2);
  }
  public boolean isSheng2(int g1, int g2) {
  	return isSheng(g1,g2) || isSheng(g2,g1);
  }
  public boolean isSheng(int g1, int g2) {
  	int[][] chke = new int[10][3];
  	chke[1]=new int[]{6,7};
  	chke[2] = new int[]{9};
  	chke[3] = new int[]{1};
  	chke[4] = new int[]{1};
  	chke[6] = new int[]{2,8};
  	chke[7] = new int[]{2,8};
  	chke[8] = new int[]{9};
  	chke[9] = new int[]{3,4};
  	
  	int[] ckgong = chke[g2];
  	for(int i=0; i<ckgong.length; i++)
  		if(ckgong[i]==g1) return true;
  	
  	return false;
  }
  //判断g1与g2宫是否比和
  public boolean isBihe(int g1, int g2) {
  	int[] chke = new int[10];
  	chke[2] = 8;
  	chke[3] = 4;
  	chke[4] = 3;
  	chke[6] = 7;
  	chke[7] = 6;
  	chke[8] = 2;
  	
  	return chke[g2]==g1;
  }
  
  /**
   * 得到《年月日时格》落宫，如果没有，则返回0
   * @param type: 类型，岁格1，月格2，日格3，时格4；
   */
  public int getSige(int type){
  	int gan2 = 0, gan = 0; 
  	
  	gan = type==1?SiZhu.ng:type==2?SiZhu.yg:type==3?SiZhu.rg:type==4?SiZhu.sg:0;
  	//将甲子年转换成对应的戊干
  	if(type==1 && SiZhu.ng==1) gan2=daoqm.getXunShu(SiZhu.ng, SiZhu.nz)+4;
  	else if(type==2 && SiZhu.yg==1) gan2=daoqm.getXunShu(SiZhu.yg, SiZhu.yz)+4;
  	else if(type==3 && SiZhu.rg==1) gan2=daoqm.getXunShu(SiZhu.rg, SiZhu.rz)+4;
  	else if(type==4 && SiZhu.sg==1) gan2=daoqm.getXunShu(SiZhu.sg, SiZhu.sz)+4;
  	
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		//如果天盘干为庚，地盘干为年干或转换后的年干或（如果是第二宫，则第五宫的地盘干是否是newNg或SiZhu.Ng）
  		if(daoqm.gInt[2][3][i]==YiJing.GENG && (daoqm.gInt[4][5][i]==gan || daoqm.gInt[4][5][i]==gan2 || 
  				(daoqm.isJiGong(i) && (daoqm.gInt[4][5][5]==gan2 || daoqm.gInt[4][5][5]==gan))))
  			return i;
  		//如果天盘星的序号是天芮星2，则证明有二个天盘干，加一个天盘干就是第5宫的地盘干即gInt[4][5][5]，如果是庚，而且地盘干是年干
  		if(daoqm.isTpJigong(i) && daoqm.gInt[4][5][5]==YiJing.GENG && 
  				(daoqm.gInt[4][5][i]==gan  || daoqm.gInt[4][5][i]==gan2 ||
  				  (daoqm.isJiGong(i) && (daoqm.gInt[4][5][5]==gan2 || daoqm.gInt[4][5][5]==gan))))
  			return i;  		
  	}
  	return 0;
  }
  //天盘干是否落宫带六仪击刑
  public boolean isTpJixing(int gan, int zi) {
  	int gong = this.getTianGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	return QiMen.gan_gong_t[gan][gong]!=0 || isTDXing(gong) ;
  }
  public boolean isTpJixing(int gong) {
  	int[] tpjy = this.getTpjy(gong);
  	return  isTpJixing(tpjy[0],0) || isTpJixing(tpjy[1],0) || 
  					isTDXing(gong);
  }
  //本宫是否有击刑
  public boolean isJixing(int gong) {
  	int[] tpjy = this.getTpjy(gong);
  	int[] dpjy = this.getDpjy(gong);
  	return  isTpJixing(tpjy[0],0) || isTpJixing(tpjy[1],0) || 
  					isTDXing(gong) || 
  					isDpJixing(dpjy[0],0) || isDpJixing(dpjy[1],0);
  }
  //地盘干是否落宫带击刑
  public boolean isDpJixing(int gan, int zi) {
  	int gong = this.getDiGong(gan, zi);
  	gan = this.getTiangan(gan, zi);
  	return QiMen.gan_gong_t[gan][gong]!=0 || isTDXing(gong);
  }
  public boolean isDpJixing(int gong) {
  	int[] dpjy = this.getDpjy(gong);
  	return  isTDXing(gong) || 
  					isDpJixing(dpjy[0],0) || isDpJixing(dpjy[1],0);
  }
  /**
   * 由用神类型得到描述、落宫、干支信息
   * @param type 要判断大格局的用神, 4为年年柱，3为月柱，2为日柱，1为时柱,0则取gan与zi的值
   * @param gan
   * @param zi
   * @return
   */
	public String[] getYShenInfo(int type, int gan ,int zi) {
		int gong = 0; 		//为用神宫
		String yshen = type==YSHENHOUR?"时干为用":type==YSHENDAY?"日干为用":type==YSHENMONTH?"月干为用":type==YSHENYEAR?"年干为用":"指定天盘干["+YiJing.TIANGANNAME[gan]+"]为用";
		if(type==YSHENHOUR) {
			gong = getTianGong(SiZhu.sg, SiZhu.sz); //得到时干宫
			gan = SiZhu.sg; zi = SiZhu.sz;
		}
		else if(type==YSHENDAY) {
			gong = getTianGong(SiZhu.rg, SiZhu.rz); //得到日干宫
			gan = SiZhu.rg; zi = SiZhu.rz;
		}
		else if(type==YSHENMONTH) {
			gong = getTianGong(SiZhu.yg, SiZhu.yz); //得到月干宫
			gan = SiZhu.yg; zi = SiZhu.yz;
		}
		else if(type==YSHENYEAR) {
			gong = getTianGong(SiZhu.ng, SiZhu.nz); //得到年干宫
			gan = SiZhu.ng; zi = SiZhu.nz;
		}
		else if(gan!=0 && zi!=0)
			gong = getTianGong(gan,zi); //得到指定的干与支之落宫
	//如果指定了八门九星八神三奇六仪为用神，则日干宫为用神
		else {			
			int[] ys = {0,0,0,0,0,1,2,3,4,6,7,8,9};  //目前对应关系: 5～12为八门；13~21为九宫
			if(type>=5 && type<=12) {  //八门为用神
				yshen = QiMen.bm1[ys[type]]+"门为用";
				gong = this.getMenGong(ys[type]); 
				int[] typjy = this.getTpjy(gong);
				gan = typjy[0]; zi = 0;
			}else if(type>=13 && type<=21) {
				gong = type-12;  //九宫为用神
				yshen = "第"+QiMen.BIGNUM[gong]+"宫为用";
				int[] typjy = this.getTpjy(gong);
				gan = typjy[0]; zi = 0;
			}
		}
		
		return new String[]{yshen, gong+"", gan+"", zi+""};
	}
	/**
	 * 分析命主的格式，得到年命的干与支
	 * 命主格式一般为：1977或1,1二种形式，在婚姻中存在2个等多个的情况
	 * @return
	 */
	public int[] getMZhu(String mingzhu) {
		if(mingzhu==null || "".equals(mingzhu.trim()))
			return new int[]{0,0};
		
		//去掉空格及前后|
		String[] kg = {"\\|",";",",","\\","\\/","-","%","$","@","*"};
		mingzhu = mingzhu.trim();
		for(int i =0; i<kg.length; i++ ) {
			if(mingzhu.startsWith(kg[i])) mingzhu = mingzhu.substring(1);
			if(mingzhu.endsWith(kg[i])) mingzhu = mingzhu.substring(0, mingzhu.length()-1);
		}
		
		String[] yeararry = mingzhu.split("\\|");  //得到多个年份或年干支		
		int year = 0;
		int ng=0, nz=0;
		int[] yearganzi = new int[2*yeararry.length];
		int j = 0;
		
		for(int i=0; i<yeararry.length; i++) {
			String[] douhao = yeararry[i].split(",");		
			if(douhao.length==1) {
				year = Integer.valueOf(yeararry[i]);
				int[] ngz = this.getYearGanzi(year); 
				yearganzi[j++] = ngz[0];
				yearganzi[j++] = ngz[1];
			}else {			
				yearganzi[j++] = Integer.valueOf(douhao[0]);
				yearganzi[j++] = Integer.valueOf(douhao[1]);
			}
		}
		return yearganzi;
	}
	
	//判断该干支落宫是否有桃花
	public boolean isTaohua(int gan, int zi) {
		int gong = this.getTianGong(gan, zi);
		if(gan==YiJing.JIA) gan = daoqm.getXunShu(gan, zi)+4;
		
		return QiMen.gan_gong_hua[gan][gong]!=null;
	}
	public boolean isTaohua(int gong) {
		int[] tpjy = this.getTpjy(gong);
		return QiMen.gan_gong_hua[tpjy[0]][gong]!=null || QiMen.gan_gong_hua[tpjy[1]][gong]!=null;
	}
	//是个别名，以区分地盘桃花
	public boolean isTpTaohua(int gong) {
		return isTaohua(gong);
	}
	public boolean isDpTaohua(int gong) {
		int[] dpjy = this.getDpjy(gong);
		return QiMen.gan_gong_hua[dpjy[0]][gong]!=null || QiMen.gan_gong_hua[dpjy[1]][gong]!=null;
	}
	/**
	 * 该宫中是否有驿马星
	 * @param gong
	 * @return
	 */
	public boolean isYima(int gong) {
    int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
    	
    return (SiZhu.YIMA[SiZhu.sz][dz1] || SiZhu.YIMA[SiZhu.sz][dz2]); 
	}
	public boolean isYimaOfRi(int gong) {
    int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
    	
    return (SiZhu.YIMA[SiZhu.rz][dz1] || SiZhu.YIMA[SiZhu.rz][dz2]); 
	}
	
	//得到九宫所藏地支序数
	public int[] getDiziOfGong(int gong) {
		int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个
    return new int[]{dz1,dz2};  //符合从小到大的顺序
	}
//得到九宫所藏地支对应的月份
	public String getDzyueOfGong(int gong) {
//		int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
//    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个
//    String[] yuefen = {"","冬","腊","正","二","三","四","五",
//    		"六","七","八","九","十"};
//    String dzname = yuefen[dz1];
//    if(dz2>0 && (dz1==1 || dz1==2))
//      dzname = dzname + (?"、"+yuefen[dz2]:"");
		String[] yue = {"","冬月","农历六、七月份","农历二月份","农历三、四月份","农历六、七月份",
				"农历九、十月份","农历八月份","腊月、正月","农历五月份"};
    return yue[gong];
	}
	//得到九宫所藏地支名称
	public String getDznameOfGong(int gong) {
		int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个
    String dzname = YiJing.DIZINAME[dz1];
    dzname = dzname + (dz2>0?"、"+YiJing.DIZINAME[dz2]:"");
    return dzname;
	}
	//判断二个宫中的地支是否相合
	public boolean isGongLiuhe(int g1, int g2) {
		int[] dz1 = getDiziOfGong(g1);
		int[] dz2 = getDiziOfGong(g2);
		for(int zi1 : dz1) {
			for(int zi2 : dz2) {
				if(YiJing.DZLIUHE[zi1][zi2]!=0)
					return true;
			}
		}
		return false;
	}
	//由地支得到所对应的宫
	public int getDiziGong(int dz) {
		return QiMen.ziOfGua[dz];
	}
	
	//得到该干所合之干
	public int getGanHe(int gan) {
		return new int[]{0,6,7,8,9,10,1,2,3,4,5}[gan];
	}
	/**
	 * 得到某宫所含后天八卦数1、先天八卦数2、五行之数3,4
	 * @return
	 */
	public String getGongshu(int gong) {
		gong = getJiGong528(gong);  //如果是中五宫，则得到寄宫
		
		int[] num = QiMen.JIUGONGSHU[gong];
		return gong+"宫后天数"+num[1]+"，先天数"+num[2]+"，五行之数为"+num[3]+","+num[4]+"；";
	}
	/**
	 * 得到宫中数共4个值中的最大值、第二大值、最小值
	 * @param gong
	 * @param type 1=最大值、2=第二大值、3=第3大值、...; -1=最小值，-2=第二小的值，-x=第x小的值
	 * @return
	 */
	public int getGongshu(int gong, int type) {
		gong = getJiGong528(gong);  //如果是中五宫，则得到寄宫
		
		int[] num = QiMen.JIUGONGSHU[gong];
		//int[] num2 = num.clone();
		int[] num3 = new int[num.length];
		System.arraycopy(num, 0, num3, 0, num.length);
		Arrays.sort(num3);   
		
	//注意，因为最小值多了一个为0的，故去年第一个
		return num3[type>0?num3.length-type : Math.abs(type)];  
	}
	/**
	 * 该宫天盘干与宫是否相冲
	 * @param gong
	 * @return
	 */
	public boolean isTChong(int gong) {
		int[] tpjy = this.getTpjy(gong);
		
		return QiMen.gan_gong_ch[tpjy[0]][gong]!=0 || QiMen.gan_gong_ch[tpjy[1]][gong]!=0;
	}
	public boolean isTGChong(int gong) {
		return isTChong(gong);
	}
	//地盘干与宫是否相冲
	public boolean isDGChong(int gong) {
		int[] dpjy = this.getDpjy(gong);
		
		return QiMen.gan_gong_ch[dpjy[0]][gong]!=0 || QiMen.gan_gong_ch[dpjy[1]][gong]!=0;
	}
	/**
	 * 该宫是否天盘与地盘冲
	 * @param gong
	 * @return
	 */
	public boolean isTDChong(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(QiMen.gan_gan_ch[t][d]!=null) return true;
		
		return false;
	}
	/**
	 * 该宫中天地盘干是否相合
	 * @return
	 */
	public boolean isTDGanHe(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.TGHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * 天地盘地支与宫中地支是否三合
	 * @param gong
	 * @return
	 */
	public boolean isTDG3He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tpdz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] dpjy = this.getDpjy(gong);
		int[] dpdz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : tpdz)
			for(int d : dpdz)
				for(int g: gongzi)
				if(YiJing.DZSHANHE[t][d][g]!=0) return true;
		
		return false; 
	}
	/**
	 * 天地盘地支是否半合，因不存在六合，只有半三合情况
	 * @param gong
	 * @return
	 */
	public boolean isTDZi3He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tpdz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] dpjy = this.getDpjy(gong);
		int[] dpdz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		
		for(int t : tpdz)
			for(int d : dpdz)
				if(YiJing.DZBANHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * 天盘地支与宫支是否半合
	 * @param gong
	 * @return
	 */
	public boolean isTG3He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tddz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : tddz)
			for(int d : gongzi)
				if(YiJing.DZBANHE[t][d]!=0) return true;
		
		return false; 
	}
	//地盘地支与宫是否三合即半合
	public boolean isDG3He(int gong) {
		int[] dpjy = this.getDpjy(gong);
		int[] dddz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : dddz)
			for(int d : gongzi)
				if(YiJing.DZBANHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * 天盘地支与宫支是否六合
	 * @param gong
	 * @return
	 */
	public boolean isTG6He(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] tddz = {this.getdzOfgan(tpjy[0]),this.getdzOfgan(tpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : tddz)
			for(int d : gongzi)
				if(YiJing.DZLIUHE[t][d]!=0) return true;
		
		return false; 
	}
	//地盘干与宫是否六合
	public boolean isDG6He(int gong) {
		int[] dpjy = this.getDpjy(gong);
		int[] dddz = {this.getdzOfgan(dpjy[0]),this.getdzOfgan(dpjy[1])};
		int[] gongzi = this.getDiziOfGong(gong);
		
		for(int t : dddz)
			for(int d : gongzi)
				if(YiJing.DZLIUHE[t][d]!=0) return true;
		
		return false; 
	}
	/**
	 * 天盘干是否与地盘干相刑
	 */
	public boolean isTDXing(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.DZXING[this.getdzOfgan(t)][this.getdzOfgan(d)]!=0) return true;
		
		return false; 
	}
	//判断天干的阴阳
	public boolean isYangGan(int gan) {
//		int[] yang={YiJing.JIA, YiJing.BING,YiJing.WUG,YiJing.GENG,YiJing.REN};
//		for(int y :yang) 
//			if(gan==y) return true;
//		
//		return false;
		return gan%2==1;
	}
	/**
	 * 该宫中天盘干是否克地盘干
	 * @return
	 */
	public boolean isTianKeDi(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANKE[YiJing.TIANGANWH[t]][YiJing.TIANGANWH[d]]==1) return true;
		
		return false; 
	}
	public boolean isDiKeTian(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANKE[YiJing.TIANGANWH[d]][YiJing.TIANGANWH[t]]==1) return true;
		
		return false; 
	}
	/**
	 * 该宫中天盘干是否生地盘干
	 * @return
	 */
	public boolean isTianShengDi(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANSHENG[YiJing.TIANGANWH[t]][YiJing.TIANGANWH[d]]==1) return true;
		
		return false; 
	}
	public boolean isDiShengTian(int gong) {
		int[] tpjy = this.getTpjy(gong);
		int[] dpjy = this.getDpjy(gong);
		
		for(int t : tpjy)
			for(int d : dpjy)
				if(YiJing.WXDANSHENG[YiJing.TIANGANWH[d]][YiJing.TIANGANWH[t]]==1) return true;
		
		return false; 
	}
	/**
	 * 得到一个宫中的得到十干克应
	 * @param gong
	 * @return
	 */
	public int[] getShiganKeying(int gong) {
		int tpg1=0,tpg2=0,dpg1=0,dpg2=0; //天盘干1，2，地盘干1，2
		tpg1 = daoqm.gInt[2][3][gong];
		if(daoqm.isTpJigong(gong)) tpg2 = daoqm.gInt[4][5][5];
		dpg1 = daoqm.gInt[4][5][gong];
		//if(gong==2) 
		if(daoqm.isJiGong(gong)) dpg2 = daoqm.gInt[4][5][5];

		return new int[]{QiMen.gan_gan[tpg1][dpg1],QiMen.gan_gan[tpg1][dpg2],
				QiMen.gan_gan[tpg2][dpg1],QiMen.gan_gan[tpg2][dpg2]}; 
	}
	/**
	 * 判断该宫中是否有该十干克应或格局
	 * @param geju
	 * @return
	 */
	public boolean isGeju(int gong, int geju) {
		int[] ge = getShiganKeying(gong);
		for(int g : ge) 
			if(g==geju) return true;
		return false;
	}
	
	/** 由奇仪得到相应的地支 */
  public int getdzOfgan(int gan) {
  	return new int[]{0,0,0,0,0,YiJing.ZI,YiJing.XU,YiJing.SHEN,YiJing.WUZ,YiJing.CHEN,YiJing.YIN}[gan];
  }
  //判断宫中是否门受制
  public boolean ismenpo(int gong) {
  	int men = daoqm.gInt[3][1][gong];  //宫的门
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym2);
  }
  public boolean ismenzhi(int gong) {
  	int men = daoqm.gInt[3][1][gong];  //宫的门
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym1);
  }
  public boolean ismenmu(int men) {  //是否门入宫墓
  	int gong = this.getMenGong(men);  //宫的门
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym5);
  }
  public boolean ismensheng(int gong) {
  	int men = daoqm.gInt[3][1][gong];  //宫的门
  	return QiMen.men_gong[men][gong].equals(QiMen.zphym4);
  }
  public boolean isWuyangshi() {
  	return new boolean[]{false,true,true,true,true,true,false,false,false,false,false}[SiZhu.sg];
  }
  /**
   * 得到宫在月令的旺衰
   * @param gong
   * @return
   */
  public String[] getGongWS(int gong) {  	
  	String s = QiMen.gong_yue[gong][SiZhu.yz];
  	String[] rs = {"-1",s};
  	
  	if(s.equals(QiMen.wxxqs1) || s.equals(QiMen.wxxqs2))
  		rs[0]="1";
  	else if(s.equals(QiMen.wxxqs3) || s.equals(QiMen.wxxqs4))
  		rs[0]="0";
  	
  	return rs;
  }
  /**
   * 把中五宫转换成对应的寄宫
   * 阴永寄2宫，阳局寄八宫；如果没有指定，一律寄坤二宫
   * @param gong
   * @return
   */
  public int getJiGong528(int gong) {
  	if(gong!=5) return gong;
  	return daoqm.getJu()>0 ? (daoqm.getTheJigong()==8?8:2) : 2;
  }
  /**
   * 由年份得到年干支
   */
  public int[] getYearGanzi(int year) {
  	int[] gz = daosz.getDaoCalendar().getSiZhu(year, 6, 6, 6, 6, false, false);
  	return new int[]{gz[1],gz[2]};
  }
  /**
   * 是否为五阳干
   * @param gan
   * @return
   */
  public boolean is5YangGan(int gan) {
  	return gan==YiJing.JIA||gan==YiJing.YI||gan==YiJing.BING||gan==YiJing.DING||gan==YiJing.WUG;
  }
  /**
   * 从起始岁到终止岁，得到由传来的年干开头的年头
   * @param syear,eyear,ng: 起始岁数，结束岁数、要得到的年干开头的年干
   * @param iyear： 出生年份，0表示前台传的是干支而非具体日期
   * @return Map<年份，年干支>
   */
  public Map<Integer, String> getYearMapFromGan(int iyear, int syear, int eyear, int ng) {//庚-戊子  （甲-戊子）   
  	int[] sngz = addGanzi(SiZhu.ng,SiZhu.nz,syear);  //: getYearGanzi(syear);
  	int sng = sngz[0];
  	int snz = sngz[1];
  	Map<Integer,String> map = new TreeMap<Integer,String>();
  	
  	int cha = ng>=sng?(ng-sng):(ng-sng+10);   //得到ng与sng相差几年   	
  	String ngname = YiJing.TIANGANNAME[ng];
  	for(int i=0; i<(eyear-syear)/10+1; i++) {
  		int year = syear+cha+10*i;
  		int nz = (snz+cha+10*i)%12==0 ? 12 : (snz+cha+10*i)%12;  //得到以ng开头的年支
  		String nzname = YiJing.DIZINAME[nz];
  		map.put((iyear+year), ngname+nzname);
  	}
  	return map;
  }

  public Map<Integer, String> getYearMapFromZi(int iyear, int syear, int eyear, int nz) {
  	int[] sngz = addGanzi(SiZhu.ng,SiZhu.nz,syear); // : getYearGanzi(,SiZhu.ng,SiZhu.nz,syear);
  	int sng = sngz[0];
  	int snz = sngz[1];
  	Map<Integer,String> map = new TreeMap<Integer,String>();
  	
  	int cha = nz>=snz?(nz-snz):(nz-snz+12);   //得到nz与snz相差几年   	
  	String nzname = YiJing.DIZINAME[nz];
  	for(int i=0; i<(eyear-syear)/12+1; i++) {
  		int year = syear+cha+12*i;
  		int ng = (sng+cha+12*i)%10==0 ? 10 : (sng+cha+12*i)%10;  //得到以nz开头的年干
  		String ngname = YiJing.TIANGANNAME[ng];
  		map.put((iyear+year), ngname+nzname);
  	}
  	return map;
  }
  
  /**
   * 得到某宫填实之年
   */
  public Map<Integer, String> getYearTianMapFromGong(int iyear,int syear, int eyear, int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个
    Map<Integer,String> m1 = this.getYearMapFromZi(iyear, syear, eyear, dz1);
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, dz2));
    return m1;
  }
  /**
   * 得到某宫充实之年
   */
  public Map<Integer, String> getYearChongMapFromGong(int iyear, int syear, int eyear, int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    
    Map<Integer,String> m1 = this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz1));
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz2)));
    return m1;
  }
  /**
   * 得到某宫填实与充实之年
   */
  public Map<Integer, String> getYearTianChongMapFromGong(int iyear, int syear, int eyear, int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    
    
    Map<Integer,String> m1 = this.getYearMapFromZi(iyear, syear, eyear, dz1);
    m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz1)));    
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, dz2));
    if(dz2!=0) m1.putAll(this.getYearMapFromZi(iyear, syear, eyear, getChongzi(dz2)));
    
    return m1;
  }
  /**
   * 将得到的年份以字符串形式返回，如1995(庚子)、2000(丙辰)
   * @param type : 1为干，2为支，3为宫填实之年，4为宫充实之年，5为宫填与充实之年
   * @param syear : 起始岁数
   * @param eyear ： 结束岁数
   * @param ngzgong ： 以此开头的年干或年支或宫
   * @param iyear ： 出生年份
   * @return
   */
  public final static int YEARGAN = 1;
  public final static int YEARZI = 2;
  public final static int YEARGONGTIAN = 3;
  public final static int YEARGONGCHONG = 4;
  public final static int YEARGONGTIANCHONG = 5;
  public String getYearString(int type, int iyear,  int syear, int eyear, int ngzgong) {//庚-戊子  （甲-戊子）   
  	String s = "";  	
  	Map<Integer,String> m = null;
  	
  	switch(type) {
  	case YEARGAN:
  		m = getYearMapFromGan(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARZI:
  		m = getYearMapFromZi(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARGONGTIAN:
  		m = getYearTianMapFromGong(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARGONGCHONG:
  		m = getYearChongMapFromGong(iyear, syear,eyear,ngzgong);
  		break;
  	case YEARGONGTIANCHONG:
  		m = getYearTianChongMapFromGong(iyear, syear,eyear,ngzgong);
  		break;
  	}
  	
		for(Iterator<Integer> it = m.keySet().iterator(); it.hasNext();) {
			Integer key = it.next();
			String value = m.get(key);
			s += key+"("+value+")、";
		}
		return s.substring(0,s.length()-1);
  }

  /**
   * 得到所合之干
   * @param gan
   * @return
   */
  public int getHegan(int gan) {  	
  	if(gan<=5) return gan+5;
  	return gan-5;
  }
  /**
   * 得到所冲的地支
   * @param zi
   * @return
   */
  public int getChongzi(int zi) {
  	int[] ch = {0,7,8,9,10,11,12,1,2,3,4,5,6};
  	return ch[zi];
  }
  
	/**
	 * 由年干支倒推年份，以1864=甲子年往后推之
	 * @param year
	 */
//	public int getGanziFromYear(int year) {
//		if(year != 0) return year;
//		
//		int g = YiJing.JIA;  //SiZhu.ng; 
//		int z = YiJing.ZI;   //SiZhu.nz;
//		for(int i=1; i<=60; i++) {
//			g = (g+i)%10==0 ? 10 : (g+i)%10;
//			z = (z+i)%12==0 ? 12 : (z+i)%12;
//			if(g==SiZhu.ng && z==SiZhu.nz) {
//				return 1864+i;
//			}
//		}
//		return -1;
//	}
  /**
   * 在指定的干支上加上add年后的干支
   */
	public int[] addGanzi(int g, int z, int add) {
		g = (g+add)%10==0 ? 10 : (g+add)%10;
		z = (z+add)%12==0 ? 12 : (z+add)%12;
		
		return new int[]{g,z};
	}
	
	//得到幺法之退星、进星
	public int getTuiXing(int gong) {
		return gong;
	}
	/**
	 * 进星，永远按顺时针顺排八宫
	 */
	public int getJinXing(int gong) { 
  	return jxingMap.get(gong);
  }
	private void _getJinXing() { 
  	int x0 = getDiGong(SiZhu.rg, SiZhu.rz);  //地盘日干所落宫原九星
  	int zfg = daoqm.getZhifuGong();  //值符宫
  	if(daoqm.iszf() && zfg==5) zfg = daoqm.getJiGong();
  	//因zfg对应x0，阳顺阴逆
  	int i=0,j = 0;
  	for(; i<QiMen.jx2.length; i++) {
  		if(QiMen.jx2[i]==zfg) break;
  	}
  	for(; j<QiMen.jx2.length; j++) {
  		if(QiMen.jx2[j]==x0) break;
  	}
  	for(int g=1; g<=9; g++) {
	  	for(int k=0; k<QiMen.jx2.length; k++) {
	  		if(QiMen.jx2[k]==g) {
	  			int x = (k - i + j + 8)%8==0?8:(k - i + j + 8)%8;
	  	    jxingMap.put(g, QiMen.jx2[x]);
	  	    break;
	  		}
	  	}
  	}
  	jxingMap.put(5, 5); //兼容飞盘
  }
	
	/**
	 * 幺法隐门，永远顺排
	 */
	public int getYinmen(int gong) {
		return ymenMap.get(gong);
	}
	private void _getYinmen() {
		int x0 = daoqm.gInt[3][1][this.getTianGong(SiZhu.rg, SiZhu.rz)]; //天盘日干的门
  	int zsg = daoqm.getZhishiGong(); //值使宫
  	if(daoqm.iszf() && zsg==5) zsg = daoqm.getJiGong();
  	int i=0,j = 0;
  	for(; i<QiMen.bm2.length; i++) {
  		if(QiMen.bm2[i]==zsg) break;
  	}
  	for(; j<QiMen.bm2.length; j++) {
  		if(QiMen.bm2[j]==x0) break;
  	}
  	for(int g=1; g<=9; g++) {
	  	for(int k=0; k<QiMen.bm2.length; k++) {
	  		if(QiMen.bm2[k]==g) {
	  			int x = (k - i + j + 8)%8==0?8:(k - i + j + 8)%8;
	  	    ymenMap.put(g, QiMen.bm2[x]);
	  	    break;
	  		}
	  	}
  	}
  	ymenMap.put(5, 5); //兼容飞盘
	}
  /**
   * 得到某宫的十二神将
   * @param gong
   * @return
   */
	public int[] getSJ12(int gong) {
		if(gong==5) return null;
		int[] sj = new int[2];
		int[] zi = this.getDiziOfGong(gong);
		sj[0] = (zi[0]-SiZhu.nz+1+12)%12==0?12 : (zi[0]-SiZhu.nz+1+12)%12;
		if(zi[1]!=0) {
			sj[1] = sj[0];  //调整地支顺序
			sj[0] = (zi[1]-SiZhu.nz+1+12)%12==0?12 : (zi[1]-SiZhu.nz+1+12)%12;
		}
		return sj;
	}
//	/**
//	 * 得到某宫的活支，不能由暗干得，有时不吻合
//	 */
//	public int getHuoziOfGong(int gong) {
//		int g1 = daoqm.gint[][][daoqm.getZhifuGong()];
//	}
	/**
	 * 得到某天干的活支，活干即暗干，只是丁乙互换，此处不用转换丁与乙的互换
	 * 最多只能后退到甲X，再退就错了
	 * int[] xunshu = {1, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2};
   * xunname = {"","甲子(戊)","甲戌(己)","甲申(庚)","甲午(辛)","甲辰(壬)","甲寅(癸)"};
	 */
	public int getHuozi(int gan) {
		return huoganMap.get(gan);
	}
	/**
	 * 得到某地支的活干
	 * 最多只能后退到甲X，再退就错了
	 */
	public int getHuogan(int zi) { //8,2|1,11  x,1
		return huoziMap.get(zi);
	}
	public int _getHuozi(int gan) {
		int xz = QiMen.xunzi[(SiZhu.sz-SiZhu.sg+12)%12];  //得到时柱甲开头的支
		return (gan-1+xz+12)%12==0? 12 : (gan-1+xz+12)%12;
	}
	/**
	 * 由干或支得到纳音
	 * @param gan： 可省略，由支推出
	 * @param zi：可省略，由干推出
	 * @return
	 */
	public int[] getNayin(int gan, int zi) {
		if(gan==0 && zi==0) return null;
		if(gan==0) gan=getHuogan(zi);
		if(zi==0) zi = getHuozi(gan);
		return new int[]{gan,zi};
	}
	private boolean isboy;  //男女，男为true
	private boolean iszf;		//转飞盘，转为true
	private boolean istd; 	//小值符随天还是地，天盘为true
	private boolean iscy;   //拆补还是置闰，拆补为true
	private int jigong;	
	private int ysTgong;			//用神落宫，或用神天盘落宫
	private int ysDgong;			//用神地盘落宫
	private int ysGan;				//用神天干
	private int ysZi;					//用神地支
	private int mtpgong;			//年命天盘落宫
	private int mdpgong; 			//年命地盘落宫
	private int mingdz,mingtg;  //年命天干、地支
	public void setBoy(boolean isboy) {
		this.isboy = isboy;
	}
	public boolean isBoy() {
		return isboy;
	}
	public void setZf(boolean iszf) {
		this.iszf = iszf;
	}
	public boolean isZf() {
		return iszf;
	}
	public void setTd(boolean istd) {
		this.istd = istd;
	}
	public boolean isTd() {
		return istd;
	}
	public void setCy(boolean iscy) {
		this.iscy = iscy;
	}
	public boolean isCy() {
		return iscy;
	}
	public void setJigong(int jigong) {
		this.jigong = jigong;
	}
	public int getJigong() {
		return jigong;
	}
	public void setYongshen(int yshen) {
		String[] ysinfo = getYShenInfo(yshen, 0, 0);		
		ysTgong = Integer.valueOf(ysinfo[1]);  //用神天盘落宫
  	ysGan = Integer.valueOf(ysinfo[2]);
  	ysZi = Integer.valueOf(ysinfo[3]);
  	ysDgong = getDiGong(ysGan, ysZi);
	}
	public int getYSTgong() {
		return ysTgong;
	}
	public int getYSDgong() {
		return ysDgong;
	}
	public int getYSTiangan() {
		return ysGan;
	}
	public int getYSDizi() {
		return ysZi;
	}
	public void setNianming(String mingzhu) {
		int[] mzhu = getMZhu(mingzhu);
  	mtpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)? getTianGong(mzhu[0], mzhu[1]):0;  //命宫
  	mdpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)? getDiGong(mzhu[0], mzhu[1]):0;  //命宫
  	mingtg = mzhu[0];
  	mingdz = mzhu[1];
	}
	public int getMingTgong() {
		return mtpgong;
	}
	public int getMingDgong() {
		return mdpgong;
	}
	public int getMingtg() {
		return mingtg;
	}
	public int getMingdz() {
		return mingdz;
	}
	
	
	public static Map<Integer,Integer> tpgGong; 	//天盘干所对应的宫
	public static Map<Integer,Integer> dpgGong; 	//地盘干所对应的宫
	public static Map<Integer,Integer> menGong; 	//门所对应的宫
	public static Map<Integer,Integer> xingGong; 	//星所对应的宫
	public static Map<Integer,Integer> shenGong; 	//神所对应的宫
	public static Map<Integer,Integer> jxingMap; 	//幺法进星
	public static Map<Integer,Integer> ymenMap;		//幺法隐门
	public static Map<Integer,Integer> huoganMap;		//干对应的活支
	public static Map<Integer,Integer> huoziMap;		//支对应的活干
	private static int[] gYaoangan;								 	//幺法暗干
	private static int[] gWangangan;								 	//王师暗干
	public void initGlobal() {
		tpgGong = new HashMap<Integer,Integer>(10);
		dpgGong = new HashMap<Integer,Integer>(10);
		menGong = new HashMap<Integer,Integer>(10);
		xingGong = new HashMap<Integer,Integer>(10);
		shenGong = new HashMap<Integer,Integer>(10);
		jxingMap = new HashMap<Integer,Integer>(10);
		ymenMap = new HashMap<Integer,Integer>(10);
		huoganMap = new HashMap<Integer,Integer>(10);
		huoziMap = new HashMap<Integer,Integer>(10);
		gYaoangan=new int[10];		
		gWangangan=new int[10];
		for(int i=2; i<=10; i++) {
			tpgGong.put(i, _getTianGong(i, 0));
			dpgGong.put(i, _getDiGong(i, 0));
		}
		for(int i=1; i<=9; i++) {
			menGong.put(i, _getMenGong(i));
			
			int xg = _getXingGong(i);
			if(i==5 && xg==0) 
				xingGong.put(5, 5); //第五宫星缺省为为天禽
			else
				xingGong.put(i, xg);
			
			shenGong.put(i, _getShenGong(i));
		}
		for(int i=1; i<10; i++) {
			gYaoangan[i] = _getYaoAngan(i);
		}
		for(int i=1; i<10; i++) {
			gWangangan[i] = _getWangAngan(i);
		}
		for(int i=1; i<=10; i++) {
			int hz = _getHuozi(i);
			huoganMap.put(i, hz);
			huoziMap.put(hz, i);
		}
		this._getJinXing();
		this._getYinmen();
	}
	
	/**
	 * 得到该宫所有的格局
	 * @param gong
	 * @throws BadLocationException
	 */
	public List<Integer> getGejus(int gong) throws BadLocationException{
		List<Integer> list = new ArrayList<Integer>();
		int zfg = daoqm.getZhifuGong();
		int zsg = daoqm.getZhishiGong();
		int kaimen = getMenGong(QiMen.MENKAI);
		int xiumen = getMenGong(QiMen.MENXIU);
		int shmen = getMenGong(QiMen.MENSHENG);
		int jing3 = getMenGong(QiMen.MENJING3);
		int du = getMenGong(QiMen.MENDU);
		int jing1 = getMenGong(QiMen.MENJING1);
		int shang = getMenGong(QiMen.MENSHANG);
		int si = getMenGong(QiMen.MENSI);
		int shenyin = getShenGong(QiMen.SHENYIN);
		int shenhe = getShenGong(QiMen.SHENHE);
		int shendi = getShenGong(QiMen.SHENDI);
		int shentian = getShenGong(QiMen.SHENTIAN);
		int[] tgan = getTpjy(gong);
		int[] dgan = getDpjy(gong);
		int[] t = tgan;
		int[] d = dgan;
		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;

		//1. 是否年月日时格、飞干伏干		
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.ng || dgan[1]==SiZhu.ng)) {
			list.add(9); //"、年格";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.yg || dgan[1]==SiZhu.yg)) {
			list.add(10); //"、月格";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.rg || dgan[1]==SiZhu.rg)) {
			list.add(24);  //"、日格、伏干";
		}
		if((tgan[0]==SiZhu.rg || tgan[1]==SiZhu.rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			list.add(13); //"、飞干";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.sg || dgan[1]==SiZhu.sg)) {
			list.add(12); //"、时格";
		}

		//3. 悖格 丙+戊或戊+丙或丙+年月日时
		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) {
			list.add(16); //"、悖格";
		}
		else if((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || 
						dgan[1]==SiZhu.ng ||dgan[1]==SiZhu.yg ||dgan[1]==SiZhu.rg ||dgan[1]==SiZhu.sg)) {
			list.add(16); //"、悖格";
		}

		//4. 天网四张：天盘六癸，地盘时干 ；地网遮蔽：天盘六壬，地盘时干。
		if((tgan[0]==YiJing.GUI || tgan[1]==YiJing.GUI) && (dgan[0]==SiZhu.sg || dgan[1]==SiZhu.sg)) {
			list.add(17); //"、天张";
		}
		else if((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && 
				(dgan[0]==SiZhu.sg || dgan[1]==SiZhu.sg)) {
			list.add(18); //"、地蔽";
		}
		
		//5. 直符反吟为：天盘甲子，地盘甲午；天盘甲戍，地盘甲辰；天盘甲申，地盘甲寅，主灾祸立至，遇奇门无妨。
		boolean iszhifan = ((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) ||
			((tgan[0]==YiJing.JI || tgan[1]==YiJing.JI) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN)) ||
			((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
			((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI)) ||
			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG));
		if(iszhifan) {
			list.add(20); //、值符反呤
		}
		
		//6. 三奇得使：天盘乙奇加临地盘甲戍或甲午；天盘丙奇加临地盘甲子或甲申；天盘丁奇加临地盘甲辰或甲寅
		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
		if(is3) {
			list.add(21); //"、奇使"
		}
		
		//7. 玉女守门：门盘直使加临地盘丁奇
		if(zsg==gong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			list.add(22); //"、玉守"
		}
		
		//8. 欢怡： 三奇临六甲值符之宫为欢怡
		if(gong==zfg && (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI || dgan[0]==YiJing.YI || dgan[1]==YiJing.YI ||
				tgan[0]==YiJing.BING || tgan[1]==YiJing.BING || dgan[0]==YiJing.BING || dgan[1]==YiJing.BING ||
				tgan[0]==YiJing.DING || tgan[1]==YiJing.DING || dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			list.add(23);  //"、欢怡"
		}
		
		//9. 三诈
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenyin==gong)) {
			list.add(420);//"、真诈"
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenhe==gong)) {
			list.add(421);//"、休诈"
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shendi==gong)) {
			list.add(422);//"、重诈"
		}
		
		//10. 五鬼
		if(jing3==gong && (isyi || isbing || isding) && shentian==gong) {
			list.add(423);//"、天假"
		}
		if(du==gong && (isgui || isji || isding) && (shenyin==gong || shendi==gong || shenhe==gong)) {
			list.add(424);//"、地假"
		}
		if(jing1==gong && isren && shentian==gong) {
			list.add(425);//"、人假"
		}
		if(shang==gong && (isgui || isji || isding) && (shendi==gong || shenhe==gong)) {
			list.add(426);//"、神假"
		}
		if(si==gong && (isgui || isji || isding) && shendi==gong ) {
			list.add(427);//"、鬼假"
		}
		
		//11. 九遁
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumen==gong && shenyin==gong) {
			list.add(428);//"、人遁"
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && shentian==gong) {
			list.add(429);//"、神遁"
		}
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && du==gong && shendi==gong) {
			list.add(430);//"、鬼遁"
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && (dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)) {
			list.add(431);//"、天遁"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimen==gong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
			list.add(432);//"、地遁"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN)) {
			list.add(433);//"、云遁"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI || gong==1)) {
			list.add(434);//"、龙遁"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && gong==4) {
			list.add(436);//"、风遁"
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && xiumen==gong && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN || gong==8)) {
			list.add(435);//"、虎遁"
		}		

		//12. 奇格、天盘六庚，地盘乙，丙，丁三奇
		if((tgan[0]==YiJing.GENG ||tgan[1]==YiJing.GENG) && (
				dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI || 
				dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING ||
				dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)){
			list.add(25);//"、奇格"
		}
		
		//13 时干入墓，就是干入墓
  	if((SiZhu.sg==YiJing.BING && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.BING) ||
  			(SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.WUG) ||
  			(SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.DING) ||
  			(SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.JI) ||
  			(SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.CHEN && daoqm.gInt[2][3][4]==YiJing.REN) ||
  			(SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.WEI && daoqm.gInt[2][3][2]==YiJing.GUI)) {
  		if(gettgWS(SiZhu.sg, SiZhu.sz)[0].equals("1"))
  			list.add(8);//"、时干入库"
  		else
  			list.add(7);//"、时干入墓"
  	}
			
		//14. 遁甲开，六甲加会阳星
		if(QiMen.gan_xing[tgan[0]][daoqm.gInt[2][1][gong]]!=0 || QiMen.gan_xing[tgan[1]][daoqm.gInt[2][1][gong]]!=0) {
			list.add(437);//"、遁开"
		}
		
		//2. 三奇入墓，干中已有了
		if(this.isTGanMu(gong) || this.isDGanMu(gong))
			list.add(13);
				
		//3. 三奇受刑（又叫三奇受制）
		if(((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING ) && gong==1 ) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || t[0]==YiJing.DING || t[1]==YiJing.DING) && 
				 (d[0]==YiJing.REN || d[1]==YiJing.REN || d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				 ((d[0]==YiJing.BING || d[1]==YiJing.BING || d[0]==YiJing.DING || d[1]==YiJing.DING) && 
						 (t[0]==YiJing.REN || t[1]==YiJing.REN || t[0]==YiJing.GUI || t[1]==YiJing.GUI)) ||
						 ((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==6) ||
						 ((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG || d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
						 ((d[0]==YiJing.YI || d[1]==YiJing.YI) && (t[0]==YiJing.GENG || t[1]==YiJing.GENG || t[0]==YiJing.XIN || t[1]==YiJing.XIN)))
			list.add(26);//"奇制、"
		
		//4. 三奇贵人升殿：乙奇临震宫，为日出扶桑，丙奇到离宫，为月照端门，丁奇到兑宫，为星见西方。
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3)
			list.add(27);//"日出扶桑、"
		if((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==9)
			list.add(28);//"月照端门、"
		if((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==7)
			list.add(29);//"星见西方、"
		
		//5. 三奇之灵：三奇合四吉神［阴合地天］或合三吉门者，为吉道清灵、用事俱吉。
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI ||
				t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) &&
				(daoqm.gInt[1][1][gong]==QiMen.SHENYIN || daoqm.gInt[1][1][gong]==QiMen.SHENDI || 
						daoqm.gInt[1][1][gong]==QiMen.SHENHE || daoqm.gInt[1][1][gong]==QiMen.SHENTIAN ) && 
						(daoqm.gInt[3][1][gong]==QiMen.MENKAI || daoqm.gInt[3][1][gong]==QiMen.MENXIU || daoqm.gInt[3][1][gong]==QiMen.MENSHENG)) 
			list.add(30);//"奇灵、"
		
		//6. 奇游禄位：乙到震、丙到巽、丁到离为本禄之位
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==4) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==9))
			list.add(31); //"奇禄、"
		
		//7. 奇仪相合：乙庚、丙辛、丁壬为奇合，戊癸、甲己为仪合，得吉门，凡事有和之象，主和解、了结、平局、平分。
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG)) ||
				((t[0]==YiJing.GENG || t[1]==YiJing.GENG) && (d[0]==YiJing.YI || d[1]==YiJing.YI)) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING) && (d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
				((t[0]==YiJing.XIN || t[1]==YiJing.XIN) && (d[0]==YiJing.BING || d[1]==YiJing.BING)) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING) && (d[0]==YiJing.REN || d[1]==YiJing.REN)) ||
				((t[0]==YiJing.REN || t[1]==YiJing.REN) && (d[0]==YiJing.DING || d[1]==YiJing.DING)))
			list.add(32);//"奇合、"
		else if(((t[0]==YiJing.WUG || t[1]==YiJing.WUG) && (d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				((t[0]==YiJing.GUI || t[1]==YiJing.GUI) && (d[0]==YiJing.WUG || d[1]==YiJing.WUG)) ||
				((t[0]==YiJing.JI || t[1]==YiJing.JI) && (gong==zfg)) ||
				((gong==zfg) && (d[0]==YiJing.JI || d[1]==YiJing.JI)))
			list.add(33);//"仪合、"
		
		return list;
	}
	
  public static void main(String[] args) {
  	DaoQiMen dao = new DaoQiMen();
  	DaoSiZhuMain sizhu = new DaoSiZhuMain();
		QimenPublic p = new QimenPublic(dao,sizhu);
		//System.out.println(p.getYearString(1,1995, 2005, 3));
		//System.out.println(p.getYearString(2,1995, 2005, 6));
		//System.out.println(p.getYearString(5,1976+18, 1976+40, 8));
		SiZhu.sg=1; SiZhu.sz=11;
		for(int i=1; i<YiJing.TIANGANNAME.length; i++){
			int g = p.getHuozi(i);
			System.out.print(YiJing.TIANGANNAME[i]+YiJing.DIZINAME[g]+"\t");			
		}
		System.out.println();
		for(int i=1; i<YiJing.DIZINAME.length; i++){
			int g = p.getHuogan(i);
			System.out.print(YiJing.TIANGANNAME[g]+YiJing.DIZINAME[i]+"\t");
		}
		
	}
}