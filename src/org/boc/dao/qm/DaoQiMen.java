package org.boc.dao.qm;

import org.boc.dao.DaoCalendar;
import org.boc.dao.DaoPublic;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.db.Calendar;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;

public class DaoQiMen {
  private StringBuffer str;
  private DaoYiJingMain daoyj;
  private DaoPublic pub;
  private DaoCalendar daoc;
  private int whichJie; //本日属何节
  private int whichYuan; //本日属何元
  private int whichJu;   //本日属何局
	private int igJigong=2;  //中五宫寄何宫，缺省寄坤二宫
  /**
   * gInt[0]=四柱干支4 个 节气8 三元11 局数12 值符使13 值符落宫14 值使落宫 小值符15
   * gInt[1][1][1-9]=八神,gInt[1][2][1-9]=八神五行
   * gInt[2][1][1-9]=九星顺序数，2九星五行,3天盘奇仪，天干五行，三奇六仪藏支
   * gInt[3][1][1-9]=宫门序数,gInt[3][2]=门五行
   * gInt[4][1][1-9]=九宫后天八卦数，2八卦五行，3地盘九星所在的宫，4地盘八门，5地盘奇仪，6地盘奇仪五行、7所藏六甲地支
   */
  public int[][][] gInt;

  public DaoQiMen() {
      str = new StringBuffer();
      daoyj = new DaoYiJingMain();
      pub = new DaoPublic();
      daoc = new DaoCalendar();
      gInt = new int[5][10][16];
  }
  
  public DaoCalendar getDaoCalendar() {
  	return this.daoc;
  }
  //设置中五宫寄何宫，供delQimenMain调用
  public void setJigong(int jigong) {
  	this.igJigong = jigong; 
  }
  public int getTheJigong() {
  	return this.igJigong ; 
  }
  public DaoPublic getPublic() {
  	return pub;
  }
  /**
   * 得到各宫吉凶，每宫描述的具体格式为：
   *   宫X；A宫X月X；B宫X月X；C宫X月X；D月X宫Y；Z；
   *   A+B:L;gan+gong; men+men; men+gan; men+xing; gan+xing; men+gan+shen; gan+men+gan; gan+men+gon; 
   * X=旺相休囚死,A=天盘干，B=地盘干，C=九星名，D=门盘名，Y=制/迫/和/义/墓


   * gInt[0]=四柱干支 节气 三元 局数 值符使 值符落宫 值使落宫 小值符
   * gInt[1][1][1-9]=八神,gInt[1][2][1-9]=八神五行
   * gInt[2][1][1-9]=九星顺序数，2九星五行,3天盘奇仪，天干五行，三奇六仪藏支
   * gInt[3][1][1-9]=宫门序数,gInt[3][2]=门五行
   * gInt[4][1][1-9]=九宫后天八卦数，2八卦五行，3地盘九星，4地盘八门，5地盘奇仪，6地盘奇仪五行、7所藏六甲地支

	二、显示每一宫的信息
  1. 宫在月令的旺衰；gong_yue[][]
  2. 奇仪在宫的旺衰和月令的旺衰：gan_gong[][],gan_yue[][]
  3. 九星在宫和月令的旺衰: xing_gong[][], xing_yue[][]
  4. 八门在月令的旺衰和宫的关系：制、迫、和、义、墓 : men_yue[][],men_gong[][]
  
  6. 干+干:gan_gan[][]
  7. 天盘干+宫:gan_gong[][],gan_gong_t[][], 地盘干+宫：gan_gong[][]
  8. 门+门:men_men[][]
  9. 门+干:men_gan[][]
  10. 星+门:men_xing[][]
  11. 门+干+神：men_gan_shen[][][]
  12. 干+门+干: gan_men_gan[][][]
  13. 干+门+宫: gan_men_gong[][][]
  14. 干+星 ：gan_xing[][]
   */
  public String[] getJiXiongOfGong() {
    String[] s = new String[1000];
    String[] geHelp = new String[500];  //对格局的帮助，即解释用的
    int index = 0;  //为s数组自动累加用
    int ihelp = 0;  //为geHelp数组自动累加用
    StringBuffer sb = new StringBuffer();
    
    geHelp[0]="====================================帮助信息====================================";
    geHelp[++ihelp]="|    年月日时：分别为年干、月干、日干、时干落宫；";
    geHelp[++ihelp]="|    空    昳：空指该宫在时柱旬空，昳指该宫在日柱旬空；";
    geHelp[++ihelp]="|    墓 库 冢：墓指宫天盘干落宫处墓地，库指天盘干在月令旺相但落宫入墓；冢指地盘干落宫入墓";
    geHelp[++ihelp]="|    马    驲：马指该宫为时支驿马星，驲指该宫为日支驿马星；";
    geHelp[++ihelp]="|    桃    花：指天盘干在该宫处沐浴之地；";
    geHelp[++ihelp]="|    刑刂型形：刑为天盘干与落宫六仪击刑，刂为地盘干与落宫六仪击刑，型为天地盘地支相刑，形为年月日时地支与其干落宫地支相刑；";
    geHelp[++ihelp]="|    冲    充：冲指天盘干与落宫六冲，充指天盘干与地盘干六冲；";
    geHelp[++ihelp]="|    峇匼冾洽：合指天地盘干六合，峇指天地盘支三合，匼指天地盘与宫支三合，冾为天盘与宫支六合，洽为天盘与宫支三合；";
    geHelp[++ihelp]="--------------------------------------------------------------------------------";
    
    
    for(int i=1; i<=9; i++) {
    	if(i==5) continue;    	
	     sb.append("  "+i+"宫─-月"+QiMen.gong_yue[i][SiZhu.yz]+"；");  //1宫--月旺；
	     sb.append(YiJing.TIANGANNAME[gInt[2][3][i]]);
	     sb.append("宫"+QiMen.gan_gong_wx[gInt[2][3][i]][i]);
	     sb.append("月"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[2][3][i]][SiZhu.yz]]+"；"); //天盘天干+宫旺月相
	     if(isTpJigong(i)) {//如果天盘九星顺序为2宫，则星要同时显示第5宫的天盘奇仪
	    	 sb.append(YiJing.TIANGANNAME[gInt[4][5][5]]);
	    	 sb.append("宫"+QiMen.gan_gong_wx[gInt[4][5][5]][i]);
      	 sb.append("月"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][5]][SiZhu.yz]]+"；");//额外加入第5宫
       }
	     
	     if(gInt[2][3][i]!=gInt[4][5][i]) {  //只有天盘干不等于地盘干才需要判断
		     sb.append(YiJing.TIANGANNAME[gInt[4][5][i]]);
		     sb.append("宫"+QiMen.gan_gong_wx[gInt[4][5][i]][i]);
		     sb.append("月"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][i]][SiZhu.yz]]+"；"); //地盘天干旺月相	     
		     if(this.isJiGong(i)) {
		    	 sb.append(YiJing.TIANGANNAME[gInt[4][5][5]]);
	      	 sb.append("宫"+QiMen.gan_gong_wx[gInt[4][5][5]][i]);
	      	 sb.append("月"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][5]][SiZhu.yz]]+"；");//额外加入第5宫
	       }
	     }
	     
	     sb.append(QiMen.jx1[gInt[2][1][i]]);
	     sb.append("宫"+QiMen.xing_gong[gInt[2][1][i]][i]);
	     sb.append("月"+QiMen.xing_yue[gInt[2][1][i]][SiZhu.yz]+"；"); //天盘星在宫和月的旺衰
       
	     sb.append(QiMen.bm1[gInt[3][1][i]]);
	     sb.append("月"+QiMen.men_yue[gInt[3][1][i]][SiZhu.yz]);
	     sb.append("宫"+QiMen.men_gong[gInt[3][1][i]][i]+"；"); //八门在月和宫的旺衰
	     
	     s[++index] = sb.toString();
       sb.delete(0, sb.length());
              
	     int ganganNum = QiMen.gan_gan[gInt[2][3][i]][gInt[4][5][i]];
	     sb.append("    ※→"+QiMen.gGejuDesc[ganganNum][0]+"；");  //输出干+干
	     int ganganNum2 = 0; 
	     if(this.isJiGong(i)) {//还要加上地盘五宫的干
	    	 ganganNum2 = QiMen.gan_gan[gInt[2][3][i]][gInt[4][5][5]];
		     sb.append(QiMen.gGejuDesc[ganganNum2][0]+"；");
	     }
	     int ganganNum5 = 0;
	     if(isTpJigong(i)) {//如果天盘九星顺序为2宫，还要加上天盘五宫的干
	    	 ganganNum5 = QiMen.gan_gan[gInt[4][5][5]][gInt[4][5][i]];
	    	 if(ganganNum5!=0) sb.append(QiMen.gGejuDesc[ganganNum5][0]+"；"); 
	     }
	     
	     int gangongNum_t = QiMen.gan_gong[gInt[2][3][i]][i];  //天盘干+宫	    
	     if(gangongNum_t!=0) sb.append(QiMen.gGejuDesc[gangongNum_t][0]+"；");  //输出天盘干+宫	     
	     if(isTpJigong(i)) {//如果天盘九星顺序为2宫
	    	 gangongNum_t = QiMen.gan_gong[gInt[4][5][5]][i];
	    	 if(gangongNum_t!=0) sb.append(QiMen.gGejuDesc[gangongNum_t][0]+"；");  //输出五宫的天盘干+宫
	     }
	     
	     int gangongNum_t1 = QiMen.gan_gong_t[gInt[2][3][i]][i];  //天盘干+宫，只适用于天盘干，用于六仪击刑
	     if(gangongNum_t1!=0) sb.append(QiMen.gGejuDesc[gangongNum_t1][0]+"；");  //输出六仪击刑
	     
	     int gangongNum_t2 = QiMen.gan_gong_ch[gInt[2][3][i]][i];
	     if(gangongNum_t2!=0) sb.append(QiMen.gGejuDesc[gangongNum_t2][0]+"；");  //输出支六冲信息
	     
	     int gangongNum_d = 0;
	     if(gInt[2][3][i]!=gInt[4][5][i])  //只有天盘干不等于地盘干才需要判断
	    	 gangongNum_d = QiMen.gan_gong[gInt[4][5][i]][i];	//地盘干+宫
	     if(gangongNum_d!=0) sb.append(QiMen.gGejuDesc[gangongNum_d][0]+"；");  //输出地盘干+宫
	     
	     //天盘门+地盘门
	     int menmenNum = QiMen.men_men[gInt[3][1][i]][QiMen.dp_mengong_mc[i]];  
	     if(menmenNum!=0) sb.append(QiMen.gGejuDesc[menmenNum][0]+"；");
	     
	     //门+干,包括门+天盘干，门+地盘干
	     int menganNum = QiMen.men_gan[gInt[3][1][i]][gInt[2][3][i]];  
	     if(menganNum!=0) sb.append(QiMen.gGejuDesc[menganNum][0]+"；");	     
	     int menganNum2 = 0;
	     if(this.isJiGong(i)) {//如果天盘九星顺序为2宫
	    	 menganNum2 = QiMen.men_gan[gInt[3][1][i]][gInt[4][5][5]];  
		     if(menganNum2!=0) sb.append(QiMen.gGejuDesc[menganNum2][0]+"；");
	     }
	     int menganNum3 = 0;   
	     if(gInt[2][3][i]!=gInt[4][5][i] && menganNum3!=0) {
	    	 menganNum3 = QiMen.men_gan[gInt[3][1][i]][gInt[4][5][i]];
	    	 sb.append(QiMen.gGejuDesc[menganNum3][0]+"；");
	     }
	     int menganNum4 = 0;
	     if(menganNum3!=0 && isTpJigong(i)) {//如果天盘九星顺序为2宫,只有menganNum3！=0才需要继续判断
	    	 menganNum4 = QiMen.men_gan[gInt[3][1][i]][gInt[4][5][5]];  
		     if(menganNum4!=0) sb.append(QiMen.gGejuDesc[menganNum4][0]+"；");
	     }
	     
	     //星+门
	     int xingmenNum = QiMen.xing_men[gInt[2][1][i]][QiMen.dp_menxing_mc[gInt[3][1][i]]];  
	     if(xingmenNum!=0) sb.append(QiMen.gGejuDesc[xingmenNum][0]+"；");
	     int xingmenNum2 = 0;
	     if(isTpJigong(i)) {//如果天盘九星顺序为2宫
	    	 xingmenNum2 = QiMen.xing_men[5][QiMen.dp_menxing_mc[gInt[3][1][i]]];  
		     if(xingmenNum2!=0) sb.append(QiMen.gGejuDesc[xingmenNum2][0]+"；");
	     }
	     
	     //门+干+神
	     int mganshenNum = QiMen.men_gan_shen[gInt[3][1][i]][gInt[2][3][i]][gInt[1][1][i]];  
	     if(mganshenNum==0) mganshenNum = QiMen.men_gan_shen[gInt[3][1][i]][gInt[4][5][i]][gInt[1][1][i]];
	     if(mganshenNum!=0) sb.append(QiMen.gGejuDesc[mganshenNum][0]+"；");
	     //门+天盘干+神
	     int mtganshenNum = QiMen.men_tgan_shen[gInt[3][1][i]][gInt[2][3][i]][gInt[1][1][i]];  
	     if(mtganshenNum!=0) sb.append(QiMen.gGejuDesc[mtganshenNum][0]+"；");
	     
	     //干+门+干
	     int ganmenganNum = QiMen.gan_men_gan[gInt[2][3][i]][gInt[3][1][i]][gInt[4][5][i]];  
	     if(ganmenganNum!=0) sb.append(QiMen.gGejuDesc[ganmenganNum][0]+"；");
	     int ganmenganNum2 = 0;
	     if(isTpJigong(i)) {//如果天盘九星顺序为2宫
	    	 ganmenganNum2 = QiMen.gan_men_gan[gInt[4][5][5]][gInt[3][1][i]][gInt[4][5][i]];  
		     if(ganmenganNum2!=0) sb.append(QiMen.gGejuDesc[ganmenganNum2][0]+"；");
	     }
	     
	     //干+门+宫
	     int ganmengongNum = QiMen.gan_men_gong[gInt[2][3][i]][gInt[3][1][i]][i];  
	     if(ganmengongNum!=0) sb.append(QiMen.gGejuDesc[ganmengongNum][0]+"；");
	     int ganmengongNum2 = 0;
	     if(isTpJigong(i)) {//如果天盘九星顺序为2宫
	    	 ganmengongNum2 = QiMen.gan_men_gong[gInt[4][5][5]][gInt[3][1][i]][i];  
		     if(ganmengongNum2!=0) sb.append(QiMen.gGejuDesc[ganmengongNum2][0]+"；");
	     }
	     
	     //干+星
	     int ganxingNum = QiMen.gan_xing[gInt[2][3][i]][gInt[2][1][i]];  
	     if(ganxingNum!=0) sb.append(QiMen.gGejuDesc[ganxingNum][0]+"；");
	     int ganxingNum2 = 0;
	     if(ganxingNum==0 && isTpJigong(i)) {//如果天盘九星顺序为2宫，如果天盘干已有一个遁甲开，不需要再判断另一个寄宫的是否是了
	    	 ganxingNum2 = QiMen.gan_xing[gInt[4][5][5]][gInt[2][1][i]];  
		     if(ganxingNum2!=0) sb.append(QiMen.gGejuDesc[ganxingNum2][0]+"；");
	     }
	     
	     s[++index] = sb.toString();
       sb.delete(0, sb.length());
	     
	     ///////////////////////输出所有的帮助描述信息/////////////////////////////	           
	     geHelp[++ihelp]=i+"宫：";
	     geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganganNum][0]+"："+QiMen.gGejuDesc[ganganNum][1];	 
	     if(ganganNum5!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganganNum5][0]+"："+QiMen.gGejuDesc[ganganNum5][1];	 
	     if(ganganNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganganNum2][0]+"："+QiMen.gGejuDesc[ganganNum2][1];
	     
	     if(gangongNum_t!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_t][0]+"："+QiMen.gGejuDesc[gangongNum_t][1];
	     if(gangongNum_t1!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_t1][0]+"："+QiMen.gGejuDesc[gangongNum_t1][1];
	     if(gangongNum_t2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_t2][0]+"："+QiMen.gGejuDesc[gangongNum_t2][1];
	     
	     if(gangongNum_d!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[gangongNum_d][0]+"："+QiMen.gGejuDesc[gangongNum_d][1];
	     if(menmenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menmenNum][0]+"："+QiMen.gGejuDesc[menmenNum][1];
	     if(menganNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum][0]+"："+QiMen.gGejuDesc[menganNum][1];
	     if(menganNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum2][0]+"："+QiMen.gGejuDesc[menganNum2][1];
	     if(menganNum3!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum3][0]+"："+QiMen.gGejuDesc[menganNum3][1];
	     if(menganNum4!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[menganNum4][0]+"："+QiMen.gGejuDesc[menganNum4][1];
	     
	     if(xingmenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[xingmenNum][0]+"："+QiMen.gGejuDesc[xingmenNum][1];
	     if(xingmenNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[xingmenNum2][0]+"："+QiMen.gGejuDesc[xingmenNum2][1];
	     
	     if(mganshenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[mganshenNum][0]+"："+QiMen.gGejuDesc[mganshenNum][1];
	     if(mtganshenNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[mtganshenNum][0]+"："+QiMen.gGejuDesc[mtganshenNum][1];
	     
	     if(ganmenganNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmenganNum][0]+"："+QiMen.gGejuDesc[ganmenganNum][1];
	     if(ganmenganNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmenganNum2][0]+"："+QiMen.gGejuDesc[ganmenganNum2][1];
	     if(ganmengongNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmengongNum][0]+"："+QiMen.gGejuDesc[ganmengongNum][1];
	     if(ganmengongNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganmengongNum2][0]+"："+QiMen.gGejuDesc[ganmengongNum2][1];
	     if(ganxingNum!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganxingNum][0]+"："+QiMen.gGejuDesc[ganxingNum][1];
	     if(ganxingNum2!=0) geHelp[++ihelp]="  "+QiMen.gGejuDesc[ganxingNum2][0]+"："+QiMen.gGejuDesc[ganxingNum2][1];
    }
    
    //将帮助信息加入全局数组中返回，并从22个地方开始加入
    index=22;
    for(int i=0; i<geHelp.length; i++) {
    	if(geHelp[i]==null) break;
    	s[index++] = geHelp[i];
    }
    
    return s;
  }
  /**
   * 得到大格局的吉凶格	
   * @return
   */
  public String[] getDageJixiong() {
  	String[] s = new String[20];
  	int gejuNum; 
  	int index = 0;
  	
  	if(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]!=0) {
  		gejuNum=QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];  //只要是天辅吉时，就不可能是五不遇时
  		if(gejuNum!=0)
  			s[index++] = ("  "+QiMen.gGejuDesc[gejuNum][0]+"："+QiMen.gGejuDesc[gejuNum][1]);
  	}
  	if(QiMen.da_men_gong[gInt[3][1][1]][1]!=0) { //只要1宫的门与1宫不为0，则格成反吟或伏吟
  		gejuNum=QiMen.da_men_gong[gInt[3][1][1]][1];
  		if(gejuNum!=0)
  			s[index++] = ("  "+QiMen.gGejuDesc[gejuNum][0]+"："+QiMen.gGejuDesc[gejuNum][1]);
  	}
  	if(QiMen.da_xing_gong[gInt[2][1][1]][1]!=0) { //只要1宫的星与1宫不为0，即格成反呤或伏呤
  		gejuNum=QiMen.da_xing_gong[gInt[2][1][1]][1];
  		if(gejuNum!=0)
  			s[index++] = ("  "+QiMen.gGejuDesc[gejuNum][0]+"："+QiMen.gGejuDesc[gejuNum][1]);
  	}
  	
  	//7/8时干入墓,时干丙戌时，天盘丙临乾；戊戌天盘戊临乾；丁丑天盘丁临艮；己丑天盘己临艮。壬辰天盘壬临巽；癸未天盘癸加坤
  	if((SiZhu.sg==YiJing.BING && SiZhu.sz==YiJing.XU && gInt[2][3][6]==YiJing.BING) ||
  			(SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU && gInt[2][3][6]==YiJing.WUG) ||
  			(SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.CHOU && gInt[2][3][8]==YiJing.DING) ||
  			(SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.CHOU && gInt[2][3][8]==YiJing.JI) ||
  			(SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.CHEN && gInt[2][3][4]==YiJing.REN) ||
  			(SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.WEI && gInt[2][3][2]==YiJing.GUI)) {
  		if(SiZhu.TGSWSJ[SiZhu.sg][SiZhu.yz]>5)
  			s[index++] = ("  "+QiMen.gGejuDesc[7][0]+"："+QiMen.gGejuDesc[7][1]);
  		else
  			s[index++] = ("  "+QiMen.gGejuDesc[8][0]+"："+QiMen.gGejuDesc[8][1]);
  	}
  	//9-12岁格、月格、日格、时格，如果年月日时有甲开头的则要转换
  	int suige = 9, yuege=10, rige=11, shige=12;
  	int newNg = 0, newYg=0, newRg=0, newSg=0; 
  	//将甲子年转换成对应的戊干
  	if(SiZhu.ng==1) newNg=getXunShu(SiZhu.ng, SiZhu.nz)+4;
  	if(SiZhu.yg==1) newYg=getXunShu(SiZhu.yg, SiZhu.yz)+4;
  	if(SiZhu.rg==1) newRg=getXunShu(SiZhu.rg, SiZhu.rz)+4;
  	if(SiZhu.sg==1) newSg=getXunShu(SiZhu.sg, SiZhu.sz)+4;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		//如果天盘干为庚，地盘干为年干或转换后的年干或（如果是第二宫，则第五宫的地盘干是否是newNg或SiZhu.Ng）
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.ng || gInt[4][5][i]==newNg || (isJiGong(i) && (gInt[4][5][5]==newNg || gInt[4][5][5]==SiZhu.ng))))
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"："+QiMen.gGejuDesc[suige][1]);
  		//如果天盘星的序号是天芮星2，则证明有二个天盘干，加一个天盘干就是第5宫的地盘干即gInt[4][5][5]，如果是庚，而且地盘干是年干
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.ng  || gInt[4][5][i]==newNg))
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"："+QiMen.gGejuDesc[suige][1]);
  		
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.yg  || gInt[4][5][i]==newYg || (isJiGong(i) && (gInt[4][5][5]==newYg  || gInt[4][5][5]==SiZhu.yg))))
  			s[index++] = ("  "+QiMen.gGejuDesc[yuege][0]+"："+QiMen.gGejuDesc[yuege][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.yg  || gInt[4][5][i]==newYg))
  			s[index++] = ("  "+QiMen.gGejuDesc[yuege][0]+"："+QiMen.gGejuDesc[yuege][1]);
  		
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.rg  || gInt[4][5][i]==newRg || (isJiGong(i) && (gInt[4][5][5]==newRg  || gInt[4][5][5]==SiZhu.rg))))
  			s[index++] = ("  "+QiMen.gGejuDesc[rige][0]+"："+QiMen.gGejuDesc[rige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.rg  || gInt[4][5][i]==newRg))
  			s[index++] = ("  "+QiMen.gGejuDesc[rige][0]+"："+QiMen.gGejuDesc[rige][1]);
  		
  		if(gInt[2][3][i]==YiJing.GENG && (gInt[4][5][i]==SiZhu.sg  || gInt[4][5][i]==newSg || (isJiGong(i) && (gInt[4][5][5]==newSg  || gInt[4][5][5]==SiZhu.sg))))
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && (gInt[4][5][i]==SiZhu.sg  || gInt[4][5][i]==newSg))
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  	}
  		
  	//13飞干格：天盘日干，地盘六庚。
  	suige = 13;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==SiZhu.rg && gInt[4][5][i]==YiJing.GENG )
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"："+QiMen.gGejuDesc[suige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==SiZhu.rg && gInt[4][5][i]==SiZhu.rg)
  			s[index++] = ("  "+QiMen.gGejuDesc[suige][0]+"："+QiMen.gGejuDesc[suige][1]);
  	}
  	//14伏宫格：天盘六庚，地盘本时旬头。旬序数+4即为旬头所对应的六仪
  	shige = 14;
  	int xuntou_liuyi = getXunShu(SiZhu.sg, SiZhu.sz)+4;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.GENG && gInt[4][5][i]==YiJing.WUG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GENG && gInt[4][5][i]==YiJing.WUG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  	}

  	//15飞宫格：天盘直符，地盘六庚  
  	shige = 15;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.WUG && gInt[4][5][i]==YiJing.GENG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[2][3][i]==YiJing.WUG && gInt[4][5][i]==YiJing.GENG)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  	}
  	//16悖格：天盘丙加地盘值符，或天盘值符加地盘丙，或丙加年月日时干之上。
  	shige = 16;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.BING && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.BING && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  		
  		if(gInt[2][3][i]==xuntou_liuyi && gInt[4][5][i]==YiJing.BING) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  		if(isTpJigong(i) && gInt[2][3][i]==xuntou_liuyi && gInt[4][5][i]==YiJing.BING){
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  		
  		if(gInt[2][3][i]==YiJing.BING && (gInt[4][5][i]==SiZhu.ng || gInt[4][5][i]==SiZhu.yg || gInt[4][5][i]==SiZhu.rg || gInt[4][5][i]==SiZhu.sg)){
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	
  	//17天网四张：天盘六癸，地盘时干。
  	shige = 17;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.GUI && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.GUI && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  	}
		//18地网遮蔽：天盘六壬，地盘时干。
  	shige = 18;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==YiJing.REN && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  		if(isTpJigong(i) && gInt[4][5][5]==YiJing.REN && gInt[4][5][i]==SiZhu.sg)
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);
  	}
  	//19值符伏呤: 六甲值符在本宫不动如戊+戊为值符伏呤
  	shige = 19;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==xuntou_liuyi && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  		if(isTpJigong(i) && gInt[4][5][5]==xuntou_liuyi && gInt[4][5][i]==xuntou_liuyi) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	//20直符反吟为：天盘甲子，地盘甲午；天盘甲戍，地盘甲辰；天盘甲申，地盘甲寅，主灾祸立至，遇奇门无妨。
  	shige = 20;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(gInt[2][3][i]==xuntou_liuyi && ((gInt[2][3][i]==YiJing.WUG && gInt[4][5][i]==YiJing.XIN) ||
  				(gInt[2][3][i]==YiJing.JI && gInt[4][5][i]==YiJing.REN) ||
  				(gInt[2][3][i]==YiJing.GENG && gInt[4][5][i]==YiJing.GUI) ||
  				(gInt[2][3][i]==YiJing.XIN && gInt[4][5][i]==YiJing.WUG) ||
  				(gInt[2][3][i]==YiJing.REN && gInt[4][5][i]==YiJing.JI) ||
  				(gInt[2][3][i]==YiJing.GUI && gInt[4][5][i]==YiJing.GENG))) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	//21三奇得使：天盘乙奇加临地盘甲戍或甲午；天盘丙奇加临地盘甲子或甲申；天盘丁奇加临地盘甲辰或甲寅。得使可以用事。若无吉门亦有小助。
  	shige = 21;
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if((gInt[2][3][i]==YiJing.YI && (gInt[4][5][i]==YiJing.JI ||gInt[4][5][i]==YiJing.XIN))||
  				(gInt[2][3][i]==YiJing.BING && (gInt[4][5][i]==YiJing.WUG ||gInt[4][5][i]==YiJing.GENG))||
  				(gInt[2][3][i]==YiJing.DING && (gInt[4][5][i]==YiJing.REN ||gInt[4][5][i]==YiJing.GUI))) {
  			if(i==getZhishiGong(SiZhu.sg,SiZhu.sz)) {
  				s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  			}
  		}
  	}
  	//22玉女守门：门盘直使加临地盘丁奇。利婚恋，相会，宴喜娱乐之事。
  	shige = 22;
  	int zhishiGong = getZhishiGong(SiZhu.sg,SiZhu.sz);
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(i==zhishiGong && gInt[4][5][i]==YiJing.DING) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}
  	//23欢怡： 三奇临六甲值符之宫为欢怡，凡事谋为皆有利，抚恤将士、众情悦服；
  	shige = 23;
  	int zhifuGong = getZhifuGong(SiZhu.sg,SiZhu.sz);
  	for(int i=1; i<=9; i++) {
  		if(i==5) continue;
  		if(i==zhifuGong && (gInt[4][5][i]==YiJing.DING || gInt[4][5][i]==YiJing.YI ||
  				gInt[4][5][i]==YiJing.BING || gInt[2][3][i]==YiJing.DING ||
  				gInt[2][3][i]==YiJing.YI || gInt[2][3][i]==YiJing.BING)) {
  			s[index++] = ("  "+QiMen.gGejuDesc[shige][0]+"："+QiMen.gGejuDesc[shige][1]);break;
  		}
  	}  	
  	
  	return s; 
  }

  /**
   * 横=8字符=16空格
   * 竖=6字符=6行
   * _6=  _5=离九  戊, _4=蓬  乙,_3= 休 ,_2=六合  _1=
   */
  public String getBody1(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;


    
    //1. 得到全局的大格，并显示在此
    String[] dgeJx = getDageJixiong();
    out1(" ");
    out1("主要格局：");
    for(int i=0; i<dgeJx.length; i++) {
      if(dgeJx[i]==null || dgeJx[i].trim().equals("")) {
        break;
      }else{
        out1(dgeJx[i]);
      }
    }
    
    //2. 得到各宫的吉凶格
    String _s[] = getJiXiongOfGong();
    for(int i=1; i<_s.length; i++) {
      if(_s[i]==null) _s[i]="";
    }

    out1("┏━━━━━━━━┳━━━━━━━━┳━━━━━━━━┓");
    out1("┃41┃91┃21┃"+kg+_s[index+1]);
    out1("┃42┃92┃22┃"+kg+_s[index+2]);
    out1("┃43┃93┃23┃"+kg+_s[index+3]);
    out1("┃44┃94┃24┃"+kg+_s[index+4]);
    out1("┃45┃95┃25┃"+kg+_s[index+5]);
    out1("┃46┃96┃26┃"+kg+_s[index+6]);
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫"+kg+_s[index+7]);
    out1("┃31┃51┃71┃"+kg+_s[index+8]);
    out1("┃32┃52┃72┃"+kg+_s[index+9]);
    out1("┃33┃53┃73┃"+kg+_s[index+10]);
    out1("┃34┃54┃74┃"+kg+_s[index+11]);
    out1("┃35┃55┃75┃"+kg+_s[index+12]);
    out1("┃36┃56┃76┃"+kg+_s[index+13]);
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫"+kg+_s[index+14]);
    out1("┃81┃11┃61┃"+kg+_s[index+15]);
    out1("┃82┃12┃62┃"+kg+_s[index+16]);
    out1("┃83┃13┃63┃"+kg+_s[index+17]);
    out1("┃84┃14┃64┃"+kg+_s[index+18]);
    out1("┃85┃15┃65┃"+kg+_s[index+19]);
    out1("┃86┃16┃66┃"+kg+_s[index+20]);
    out1("┗━━━━━━━━┻━━━━━━━━┻━━━━━━━━┛"+kg+_s[index+21]);
    //将多余的吉凶格显示在此
    for(int i=22; i<500; i++) {
      if(_s[i]==null || _s[i].trim().equals("")) {
        break;
      }else{
        out1(_s[i]);
      }
    }

    _str = str.toString();

    //排局,result中的东西将替换上面的21/22/23/92....
    String[][] result = makeJiuGong(iZfs);
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        _str = _str.replaceAll(""+i+""+j, result[i][j]);
      }
    }

    return _str;
  }
  /**
   * 只有局，没有其它信息
   * simon(2011-11-28)
   */
  public String getBody0(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;

    out1("┏━━━━━━━━┳━━━━━━━━┳━━━━━━━━┓");
    out1("┃41┃91┃21┃");
    out1("┃42┃92┃22┃");
    out1("┃43┃93┃23┃");
    out1("┃44┃94┃24┃");
    out1("┃45┃95┃25┃");
    out1("┃46┃96┃26┃");
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫");
    out1("┃31┃51┃71┃");
    out1("┃32┃52┃72┃");
    out1("┃33┃53┃73┃");
    out1("┃34┃54┃74┃");
    out1("┃35┃55┃75┃");
    out1("┃36┃56┃76┃");
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫");
    out1("┃81┃11┃61┃");
    out1("┃82┃12┃62┃");
    out1("┃83┃13┃63┃");
    out1("┃84┃14┃64┃");
    out1("┃85┃15┃65┃");
    out1("┃86┃16┃66┃");
    out1("┗━━━━━━━━┻━━━━━━━━┻━━━━━━━━┛");

    _str = str.toString();

    //排局,result中的东西将替换上面的21/22/23/92....
    String[][] result = makeJiuGong(iZfs);
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        _str = _str.replaceAll(""+i+""+j, result[i][j]);
      }
    }

    return _str;
  }
  /**
   * 原方法 simon(2011-11-14)
   */
  public String getBody2(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String kg = "    ";
    String _str;
    int index = 0;

    //吉凶格
    String _s[] = getJiXiongOfGong();
    for(int i=1; i<_s.length; i++) {
      if(_s[i]==null) _s[i]="";
    }

    out1("┏━━━━━━━━┳━━━━━━━━┳━━━━━━━━┓");
    out1("┃41┃91┃21┃"+kg+_s[index+1]);
    out1("┃42┃92┃22┃"+kg+_s[index+2]);
    out1("┃43┃93┃23┃"+kg+_s[index+3]);
    out1("┃44┃94┃24┃"+kg+_s[index+4]);
    out1("┃45┃95┃25┃"+kg+_s[index+5]);
    out1("┃46┃96┃26┃"+kg+_s[index+6]);
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫"+kg+_s[index+7]);
    out1("┃31┃51┃71┃"+kg+_s[index+8]);
    out1("┃32┃52┃72┃"+kg+_s[index+9]);
    out1("┃33┃53┃73┃"+kg+_s[index+10]);
    out1("┃34┃54┃74┃"+kg+_s[index+11]);
    out1("┃35┃55┃75┃"+kg+_s[index+12]);
    out1("┃36┃56┃76┃"+kg+_s[index+13]);
    out1("┣━━━━━━━━╋━━━━━━━━╋━━━━━━━━┫"+kg+_s[index+14]);
    out1("┃81┃11┃61┃"+kg+_s[index+15]);
    out1("┃82┃12┃62┃"+kg+_s[index+16]);
    out1("┃83┃13┃63┃"+kg+_s[index+17]);
    out1("┃84┃14┃64┃"+kg+_s[index+18]);
    out1("┃85┃15┃65┃"+kg+_s[index+19]);
    out1("┃86┃16┃66┃"+kg+_s[index+20]);
    out1("┗━━━━━━━━┻━━━━━━━━┻━━━━━━━━┛"+kg+_s[index+21]);
    //将多余的吉凶格显示在此
    for(int i=index+22; i<100; i++) {
      if(_s[i]==null || _s[i].trim().equals("")) {
        break;
      }else{
        out1(daoyj.getRepeats(" ",56)+kg+_s[i]);
      }
    }

    _str = str.toString();

    //排局,result中的东西将替换上面的21/22/23/92....
    String[][] result = makeJiuGong(iZfs);
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=6; j++) {
        _str = _str.replaceAll(""+i+""+j, result[i][j]);
      }
    }

    return _str;
  }

  /**
   * 由时间得到奇门头部信息
   * @param iszf
   * @param iscy
   * @param iZfs
   * @param ju
   * @return
   */
  public String getHead1(int iszf, int iscy, int iZfs, int ju) {
    int h = Calendar.HOUR/100;
    int mi = Calendar.HOUR - Calendar.HOUR/100*100;
    int h2 = Calendar.HOUR2/100;
    int mi2 = Calendar.HOUR2 - Calendar.HOUR2/100*100;
    str.delete(0,str.length());
    String zf_, zy_, zfs_,jg_, ju_;

    zf_ = (iszf==1) ? "转盘-":"飞盘-";
    zy_ = (iscy==1) ? "置闰法-":"拆补法-";
    zfs_ = (iZfs==1) ? "小值符随天盘-":"小值符随地盘-";
    jg_ = (getJiGong()==8) ? "阴坤阳艮":"永寄坤宫";
    
    str.append(getYangOrYinDesc(true)+zf_+zy_+zfs_+jg_);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+
               "阳  历： "+Calendar.YEARP+"-"+Calendar.MONTHP+"-"+Calendar.DAYP+
               " "+h+":"+mi);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+
               "农  历： "+Calendar.YEARN1+"年"+(Calendar.YUN ? "闰":"") +
               Calendar.MONTHN1+"月初"+Calendar.DAYN+" "+h+":"+mi+" "+
               Calendar.WEEKNAME[Calendar.WEEK]);
    if(Calendar.isSunTrue) {
      str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+
          "真  阳： " + Calendar.YEARN1 + "年" +
          (Calendar.YUN ? "闰" : "") +
          Calendar.MONTHN + "月初" + Calendar.DAYN + " " + h2 + ":" + mi2 + " " +
          Calendar.WEEKNAME[Calendar.WEEK]);
    }
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "干  支： "+
               YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "旬  空： "+
               pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[0]+pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[0]+pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[0]+pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[0]+pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[1]);


    int lastjie = 0; int nextjie = 0, lastjiename=0, nextjiename=0;
    int lastjieyear=0, nextjieyear=0;
    //取不论节气的阴历月
    int z = Calendar.MONTHN1;
    //取不分节气的阴历年
    int year = Calendar.YEARN1-Calendar.IYEAR;
    //有2004.2.1阴历为2004.1.11，但节气是上年12月
    int _date2 = Calendar.jieqi2[year][z*2-1 + 1];
    if(!isMoreBig(Calendar.YEARN1, z*2-1+1)) { //该月第二节气
      if(!isMoreBig(Calendar.YEARN1, z*2-1)) { //该月第一节气
        if(z==1) {  //如果还小于第一节气，直接取上一月第二节气，不用判断了
          lastjie = Calendar.jieqi2[year-1][24];
          nextjie = Calendar.jieqi2[year][1];;
          lastjiename = 24;
          nextjiename = 1;
          lastjieyear = year-1 + Calendar.IYEAR;
          nextjieyear = year + Calendar.IYEAR;
        }else{
          lastjie = Calendar.jieqi2[year][z * 2 - 2];
          nextjie = Calendar.jieqi2[year][z * 2 - 1];;
          lastjiename = z * 2 - 2;
          nextjiename = z * 2 - 1;
          lastjieyear = year + Calendar.IYEAR;
          nextjieyear = year + Calendar.IYEAR;
        }
      }else {
        lastjie = Calendar.jieqi2[year][z * 2 - 1];
        nextjie = _date2;
        lastjiename = z * 2 - 1;
        nextjiename = z * 2 - 1 + 1;
        //如果立春是去年12月
        lastjieyear = year + Calendar.IYEAR;
        nextjieyear = year + Calendar.IYEAR;
      }

    }else{
      int z2 = 0;
        int y2 = 0;
        if(z==12) {
          z2 = 1;
          y2 = Calendar.YEARN1+1;
        }else{
          z2 = z*2-1+2;
          y2 = Calendar.YEARN1;
        }
        if(isMoreBig(y2, z2)) { //如果大于该月第二节气，看大于下月第一节气不
          if(z==12) {
            lastjie = Calendar.jieqi2[year+1][1];
            nextjie = Calendar.jieqi2[year+1][2];
            lastjiename = 1;
            nextjiename = 2;
            lastjieyear = year +1+ Calendar.IYEAR;
            nextjieyear = year +1+ Calendar.IYEAR;
          }else{
            lastjie = Calendar.jieqi2[year][z*2+1];
            nextjie = Calendar.jieqi2[year][z*2+2];
            lastjiename = z*2+1;
            nextjiename = z*2+2;
            lastjieyear = year + Calendar.IYEAR;
            nextjieyear = year + Calendar.IYEAR;
          }
        }else{
          lastjie = _date2;
          if (z == 12) {
            nextjie = Calendar.jieqi2[year + 1][1];
            lastjiename = 24; //而非_maxMonth-1
            nextjiename = 1;
            lastjieyear = year + Calendar.IYEAR;
            nextjieyear = year + Calendar.IYEAR + 1;
          }
          else {
            nextjie = Calendar.jieqi2[year][z * 2 - 1 + 2];
            lastjiename = z * 2 - 1 + 1;
            nextjiename = z * 2 - 1 + 2;
            lastjieyear = year + Calendar.IYEAR;
            nextjieyear = year + Calendar.IYEAR;
          }
        }
    }
    int ly = lastjie/1000000;
    int lr = lastjie/10000 - ly*100;
    int ls = lastjie/100 - ly*10000 - lr*100;
    int lf = lastjie - ly*1000000 - lr*10000 - ls*100;
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "上  节： "+
               QiMen.JIEQI24[lastjiename]+" "+lastjieyear+"年"+
               (isYunJieqi(lastjieyear, lastjiename) ? "闰":"") +
               ly+"月初"+ lr+"日"+ls+"时"+lf+"分");
    String jieqi1 = null;
    String jieqi2 = null;
    if(iscy==2) {   //1为置闰、2为拆补
      jieqi1 = getJieqi1(lastjiename);  //拆补法为上节节气，不到节气，拆补肯定不会跑到前面去
    }else {
      jieqi2 = getJieqi2(iscy, lastjiename, lastjieyear, ly, lr, ls, lf);
    }

    ly = nextjie/1000000;
    lr = nextjie/10000 - ly*100;
    ls = nextjie/100 - ly*10000 - lr*100;
    lf = nextjie - ly*1000000 - lr*10000 - ls*100;
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "下  节： "+
               QiMen.JIEQI24[nextjiename]+" "+nextjieyear+"年"+
               (isYunJieqi(nextjieyear, nextjiename) ? "闰":"") +
               ly+"月初"+lr+"日"+ls+"时"+lf+"分");

    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "节  气： "+
               ((iscy==1)?jieqi2:jieqi1));

    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "格  局： "+
               getGeju(ju,iszf, iZfs));

    return str.toString();
  }

  /**
   * 由干支得到奇门信息
   * @param iszf
   * @param iscy
   * @param iZfs
   * @param ju
   * @return
   */
  public String getHead2(int iszf, int iscy, int iZfs, int ju) {
    str.delete(0,str.length());
    String zf_, zy_, ju_, zfs_, jg_;

    zf_ = (iszf==1) ? "转盘 -":"飞盘奇-";
    zy_ = (iscy==1) ? "置闰法-":"拆补法-";
    zfs_ = (iZfs==1) ? "小值符随天盘-":"小值符随地盘-";
    jg_ = (getJiGong()==8) ? "阴坤阳艮":"永寄坤宫";    
    str.append(getYangOrYinDesc(true)+zf_+zy_+zfs_+jg_);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "干  支： "+
               YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+ "  "+
               YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "旬  空： "+
               pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[0]+pub.getXunKongOut(SiZhu.ng,SiZhu.nz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[0]+pub.getXunKongOut(SiZhu.yg,SiZhu.yz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[0]+pub.getXunKongOut(SiZhu.rg,SiZhu.rz)[1]+ "  "+
               pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[0]+pub.getXunKongOut(SiZhu.sg,SiZhu.sz)[1]);

    String jieqi = getJieqi3(ju);
    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "节  气： "+ jieqi);

    str.append("\r\n"+daoyj.getRepeats(" ", YiJing.INTER[0])+ "格  局： "+
               getGeju(ju,iszf, iZfs));

    return str.toString();
  }

  public String getYangOrYinDesc(boolean isKongge) {
  	String desc = null;
  	if(isKongge)
  		desc = QiMen2.YANG?"阳  盘： ":"阴  盘： ";
  	else
  		desc = QiMen2.YANG?"阳盘：":"阴盘：";
  	return desc;
  }

  /**
   * 得到是何局，值符，值使
   * @return
   */
  private String getGeju(int ju, int iszf, int iZfs) {
    //Debug.out("节="+this.whichJie+" ; 元="+this.whichYuan);
    String str = "";
    int _ret = 0;

    if(ju>0) {
      str += "自选"+QiMen.yydunju[ju];
      this.whichJu = ju>9?9-ju:ju;
    }else{
    	//int _ret = QiMen.yydun[this.whichJie][this.whichYuan];
    	//this.whichJu = _ret;
      _ret = getYangOrYinJu();      
      str += _ret>0?QiMen.yydunju[_ret]:QiMen.yydunju[9-_ret];
    }

    //得到全局元素
    getGlobleInfo(iszf, iZfs);

    str += "  ";
    
    /**
     * (一)中五宫寄何宫，阳遁寄igJigong指定的宫，阴遁永寄2宫，这是第一处改动
     */
    int zfmen = gInt[0][0][12];  //值符落5宫时直接选5宫的星为天禽星
    int zsmen = gInt[0][0][12]!=5?gInt[0][0][12]: (this.getJu()>0)? (igJigong==0?2:igJigong) : 2;  
    if(gInt[0][0][13]==5)
    	gInt[0][0][13]=(this.getJu()>0)? (igJigong==0?2:igJigong) : 2;
    if(gInt[0][0][14]==5)
    	gInt[0][0][14] = (this.getJu()>0)? (igJigong==0?2:igJigong) : 2;
    
    str +="值符["+QiMen.jx1[zfmen]+"]落"+gInt[0][0][13]+"宫";
    str += "  ";
    str +="值使["+QiMen.bm1[zsmen]+"]落"+gInt[0][0][14]+"宫";
    return str;
  }
  
  //定阴或阳盘的局数
  private int getYangOrYinJu() {
  	int _ret = QiMen.yydun[this.whichJie][this.whichYuan];
    if(QiMen2.YANG){
    	this.whichJu = _ret;
    } else { //由阳盘局数定正负号，再由年月日时定阴盘局数
    	//System.out.println(SiZhu.nz+"-"+Calendar.MONTHN1+"月初"+Calendar.DAYN+"-"+SiZhu.sz);
    	int ju = SiZhu.nz+Calendar.MONTHN1+Calendar.DAYN+SiZhu.sz;
    	ju = ju%9==0 ? 9 :ju%9;
    	this.whichJu = _ret > 0 ? ju : 0 - ju;
    }
    
    return this.whichJu;
  }
  
  
  

//  /**
//   * 得到是何局，值符，值使
//   * @return
//   */
//  private String getGeju1(int ju, int iZfs) {
//    //Debug.out("节="+this.whichJie+" ; 元="+this.whichYuan);
//    String str = "";
//    int _ret = 0;
//
//    if(ju>0) {
//      str += "自选"+QiMen.yydunju[ju];
//      this.whichJu = ju>9?9-ju:ju;
//    }else{
//      _ret = QiMen.yydun[this.whichJie][this.whichYuan];
//      this.whichJu = _ret;
//      str += _ret>0?QiMen.yydunju[_ret]:QiMen.yydunju[9-_ret];
//    }
//
//    str += "  ";
//    int zfs = getZhiFuShi(SiZhu.sg, SiZhu.sz);
//    int zflg = getZhifuGong(SiZhu.sg, SiZhu.sz);
//    int zslg = getZhishiGong(SiZhu.sg, SiZhu.sz);
//    str +="值符["+QiMen.jx1[zfs]+"]落"+zflg+"宫";
//    str += "  ";
//    str +="值使["+QiMen.bm1[zfs]+"]落"+zslg+"宫";
//    str += iZfs==1? "  小值符随天盘值符":"  小值符随地盘值符";
//    return str;
//  }


  /**
   * 得到是哪元
   * @param i
   * @return
   */
  private String getWhichYuan(int i) {
    String str = "";
    if(i==1)
      str = "上元";
    else if(i==2)
      str = "中元";
    else if(i==3)
      str = "下元";

    return str;
  }

  /**
   * 拆补法
   * 超神与接气按书上的是多加一天，如1日到3日为3天非3-1=2天。
   * @param zy
   * @return
   */
  private String getJieqi1(int lastjiename) {
    String str = "";
    int[] ret ;
    ret = getFutou(SiZhu.rg, SiZhu.rz);
    str = QiMen.JIEQI24[lastjiename];
    str += getWhichYuan(ret[3]);
    str += "第"+(ret[0]+1)+"日";
    str += "  符头:" + YiJing.TIANGANNAME[ret[1]]+YiJing.DIZINAME[ret[2]];
    str += "  旬首:"+QiMen.xunname[getXunShu(SiZhu.sg, SiZhu.sz)];

    whichJie = lastjiename;
    whichYuan = ret[3];

    return str;
  }

  /**
   * 超接置闰法
   * 超神与接气按书上的是多加一天，如1日到3日为3天非3-1=2天。
   * @param zy，jieyear,ly,lr,ls,lf  是否置闰/节气的年、节气月、节气日、时、分
   * @return
   */
  private String getJieqi2(int zy, int lastjiename, int jieyear,
                           int ly,int lr,int ls,int lf) {
    int[] ret ;
    int x;
    String str = "";

    //1. 求节气
    int yn = Calendar.YEARP;   //当前起局的年、月、日、时分、日干、日支
    int mn = Calendar.MONTHP;
    int dn = Calendar.DAYP;
    int hn = Calendar.HOUR;
    int szrg = SiZhu.rg;
    int szrz = SiZhu.rz;
    boolean yunp = Calendar.YUN;
    daoc.calculate(jieyear,ly,lr,ls,lf,true,isYunJieqi(jieyear, lastjiename),-1, -1);
    //2. 求节气干支
    int g = SiZhu.rg;
    int z = SiZhu.rz;
    //求完节气后，再恢复当前的时间信息，必须指定省和市以求真太阳时； simon(2011-12-14)
    daoc.calculate(yn,mn,dn,hn/100,hn-hn/100*100,false,yunp,Calendar.PROVINCE, Calendar.CITY);
    //3. 求节气的上元，确定是超神还是接气
    //   无论超神、接气，因均取节气之符头，故节气不变
    ret = getFutouOfUp(g, z);
    str = QiMen.JIEQI24[lastjiename];
    str += "上元  符头" + YiJing.TIANGANNAME[ret[1]]+YiJing.DIZINAME[ret[2]];
    str += "  ";
    if(ret[0]==0)
      str += "正授";
    else if(ret[0]>0)
      str += "超神"+Math.abs(ret[0]+1)+"日";
    else if(ret[0]<0)
      str += "接气"+Math.abs(ret[0]-1)+"日";
    str += "\r    ";
    //4. 取本节气的前后三十日看居第几日
    //   确定是否置闰，置闰的三元称作“闰奇”。只有芒种和大雪置闰，其余节气即使超过十天也不能置闰
    //   置闰
    boolean makeyun = false;
    if(lastjiename==9 || lastjiename==21) {
      if(ret[0]>9)
        makeyun=true;
    }


    int[][] jq = new int[61][5]; //共30组，干1支2何节气3 何元4
    int _i = 0;
    if(makeyun) {
      //因置闰，则不用前后30日，只需后30日，再后15日
      for (int ii = 1; ii <= 30; ii++) {
        jq[ii][1] = (ret[1] + ii - 1) > 10 ? (ret[1] + ii - 1) % 10 :(ret[1] + ii - 1);
        if(jq[ii][1]==0) jq[ii][1]=10;   //当ii>10时就会为0，其实应为10
        jq[ii][2] = (ret[2] + ii - 1) > 12 ? (ret[2] + ii - 1) % 12 :(ret[2] + ii - 1);
        if(jq[ii][2]==0) jq[ii][2]=12;  //当ii>12时就会为0，其实应为12
        jq[ii][3] = lastjiename;
        _i = (ii - 1) / 5 + 1;
        if (_i > 3)
          jq[ii][4] = _i - 3;
        else
          jq[ii][4] = _i;
      }

      for (int ii = 31; ii <= 45; ii++) {
        jq[ii][1] = (ret[1] + ii - 1) > 10 ? (ret[1] + ii - 1) % 10 : (ret[1] + ii - 1);     
        if(jq[ii][1]==0) jq[ii][1]=10;   //当ii>10时就会为0，其实应为10
        jq[ii][2] = (ret[2] + ii - 1) > 12 ? (ret[2] + ii - 1) % 12 : (ret[2] + ii - 1);
        if(jq[ii][2]==0) jq[ii][2]=12;  //当ii>12时就会为0，其实应为12
        jq[ii][3] = lastjiename + 1 > 24 ? (lastjiename + 1) % 24 : lastjiename + 1;
        _i = (ii - 1) / 5 + 1;
        jq[ii][4] = _i - 6;
      }
    }else{
    	/**
    	 * simon 修正了一个bug，即0其实为10或12 2011-12-22
    	 */
      for (int ii = 1; ii <= 30; ii++) {
        jq[ii][1] = (ret[1] + ii - 1) > 10 ? (ret[1] + ii - 1) % 10 : (ret[1] + ii - 1);
        if(jq[ii][1]==0) jq[ii][1]=10;  //
        jq[ii][2] = (ret[2] + ii - 1) > 12 ? (ret[2] + ii - 1) % 12 : (ret[2] + ii - 1);
        if(jq[ii][2]==0) jq[ii][2]=12;  //
        if (ii >= 16) {
          jq[ii][3] = lastjiename + 1 > 24 ? (lastjiename + 1) % 24 : lastjiename + 1;
        }
        else {
          jq[ii][3] = lastjiename;
        }
        _i = (ii - 1) / 5 + 1;
        if (_i > 3)
          jq[ii][4] = _i - 3;
        else
          jq[ii][4] = _i;
      }

      for (int ii = 31; ii <= 60; ii++) {
        jq[ii][1] = (ret[1] - ii - 30) <= 0 ? (ret[1] - ii - 30 + 100) % 10 : (ret[1] - ii - 30);
        if(jq[ii][1]==0) jq[ii][1]=10;   //当ii>10时就会为0，其实应为10
        jq[ii][2] = (ret[2] - ii - 30) <= 0 ? (ret[2] - ii - 30 + 120) % 12 : (ret[2] - ii - 30);
        if(jq[ii][2]==0) jq[ii][2]=12;  //当ii>12时就会为0，其实应为12
        if (ii >= 45) {
          jq[ii][3] = lastjiename - 2 <= 0 ? lastjiename - 2 + 24 : lastjiename - 2;
        }
        else {
          jq[ii][3] = lastjiename - 1 <= 0 ? lastjiename - 1 + 24 : lastjiename - 1;
        }
        _i = (ii - 1) / 5 + 1;
        if (_i == 7 || _i == 10)
          jq[ii][4] = 3;
        else if (_i == 8 || _i == 11)
          jq[ii][4] = 2;
        else if (_i == 9 || _i == 12)
          jq[ii][4] = 1;
      }
    }


    //7. 确定本日是何元 第几日，
    str += "本  日： ";
    int jj = 0;
    for(jj=1; jj<=30; jj++) {
      if(jq[jj][1]==szrg && jq[jj][2]==szrz) {
        break;
      }
    }

    int[] _futou = getFutou(szrg,szrz);
    str += QiMen.JIEQI24[jq[jj][3]];
    str += QiMen.SANYUANNAME[jq[jj][4]];
    str += "  ";
    str += "符头:"+YiJing.TIANGANNAME[_futou[1]]+YiJing.DIZINAME[_futou[2]];
    str += "  第"+(_futou[0]+1)+"日";
    str += "  旬首:"+QiMen.xunname[getXunShu(SiZhu.sg, SiZhu.sz)];

    whichJie = jq[jj][3];
    whichYuan = jq[jj][4];

    if(makeyun) {
      str += "\r\n    ";
      str += "置  闰： ";
      str += QiMen.JIEQI24[jq[jj][3]];
      str += QiMen.SANYUANNAME[jq[jj][4]];
      str += "  ";
      str += "符头:"+YiJing.TIANGANNAME[_futou[1]]+YiJing.DIZINAME[_futou[2]];
      str += "  第"+(_futou[0]+1)+"日";
      str += "  旬首:"+QiMen.xunname[getXunShu(SiZhu.sg, SiZhu.sz)];
    }

    return str;
  }

  /**
   * 得到何节气 何元 第几日  符头xx
   * @param ju
   * @return
   */
  private String getJieqi3(int ju) {
    int _ju = ju>9?9-ju:ju;
    int mz = SiZhu.yz ;
    int mg = SiZhu.yg ;
    int dg = SiZhu.rg;
    int dz =  SiZhu.rz;
    int yz = mz;
    int _g=0, _z = 0;
    boolean b = false;

    yz = yz<3 ? yz+10 : yz-2;
    int[] jies = { yz*2-1, yz*2-2==0?24:yz*2-2, yz*2};
    int i=0;
    int m=0;
    String str = "";

    if(ju>0) { // 自选局
      for (i = 0; i < jies.length; i++) {
        int[] ju_ = QiMen.yydun[jies[i]];
        for (m = 1; m < ju_.length; m++) {
          if (ju_[m] == _ju) {
            b = true;
            break;
          }
        }
        if (b)
          break;
      }
      str = QiMen.JIEQI24[jies[i]];
      str += getWhichYuan(m);
      this.whichJie = jies[i];
      this.whichYuan = m;
      this.whichJu = QiMen.yydun[jies[i]][m];

    }else{ //自动确定局
      b = false;
      int _j = (mz < 3 ? mz + 10 : mz - 2) * 2 - 1;
      str = QiMen.JIEQI24[_j];
      for (i = 0; i < 10; i++) {
        _g = (dg - i + 10) % 10 == 0 ? 10 : (dg - i + 10) % 10;
        _z = (dz - i + 12) % 12 == 0 ? 12 : (dz - i + 12) % 12;
        for (m=1; m < 4; m++) {
          if (QiMen.sanyuan[m][_g][_z] == 1) {
            b = true;
            break;
          }
        }
        if(b) break;
      }
      this.whichJie = _j;
      this.whichYuan = m;
      //此处由阴盘 阳盘定局数
//      if(QiMen2.YANG) {
//      	this.whichJu = QiMen.yydun[_j][m];
//      } else {
//      	this.whichJu = getYinPanJu(QiMen.yydun[_j][m]);
//      }
      getYangOrYinJu();
      
      str += getWhichYuan(m);
    }



    int days = 0;
    int fug = 0;
    int fuz = 0;
    b = false;
    //因是自选局，故需前后均15日判断有无超神，接气
    //对于自动定局，定是超神无疑，后面for可不管了
    for(int j=0; j<15; j++) {
      _g = (dg - j + 10) % 10 == 0 ? 10 : (dg - j + 10) % 10;
      _z = (dz - j + 12) % 12 == 0 ? 12 : (dz - j + 12) % 12;
      if(QiMen.sanyuan[m][_g][_z] == 1) {
        b = true;
        if(j ==0) {
          days = 0;
          str += "  符头" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] + "  正授";
        }else {
          days = j + 1;
          str += "  符头" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] + "  超神" +
              days + "日";
        }
        break;
      }
    }
    if(!b)
      for(int j=0; j<15; j++) {
        _g = (dg + j + 10) % 10 == 0 ? 10 : (dg + j + 10) % 10;
        _z = (dz + j + 12) % 12 == 0 ? 12 : (dz + j + 12) % 12;
      if(QiMen.sanyuan[m][_g][_z] == 1) {
        if(j ==0) {
          days = 0;
          str += "  符头" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] + "  正授";
        }else {
          days = j + 1;
          str += "  符头" + YiJing.TIANGANNAME[_g] + YiJing.DIZINAME[_z] +
              "  超神" + days + "日";
        }
          break;
        }
      }

    return str;
  }

  /**
   * 置闰 接不過五，超不過九
   * 某年置过闰 需在下年有反映，很麻烦
   * @param zy
   * @return
   */
  private String makeYun(int zy, int lastjie) {
    String str = "";
    int[] ret ;
    if(zy==1) {  //置闰
      ret = getFutouOfUp(SiZhu.rg, SiZhu.rz);
    }else{  //拆补
      ret = getFutou(SiZhu.rg, SiZhu.rz);
    }
    if(ret[0]<-5 || ret[0]>9) {
      if(lastjie==9 || lastjie==21) {

      }
    }

    return str;
  }


  /**
   * 得到上元符头，专用于置闰法, 超神多取些
   * 如丁卯 则先得到 己酉 甲寅 己未 甲子 己已 甲戌 己卯，不外乎此五段之内
   * 先取两甲，再取两己
   * @param dg,dz 为节气的干、支
   * @return
   */
  public int[] getFutouOfUp(int dg, int dz) {
    int day=0,g=0,z=0;
    int[][] futou = new int[2][4];
    int index = 0;

    //逆推上上个甲开头
    g = YiJing.JIA;
    day = 10+dg-YiJing.JIA;
    z = (dz - day)<=0 ? dz - day + 12 : dz - day;
    z = z<=0 ? z + 12 : z;
    //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
    if(QiMen.sanyuan[1][g][z]==1) {
      futou[index++] = new int[]{day,g,z,0};
    }

    //逆推到上个甲开头
    g = YiJing.JIA;
    day = dg - g;
    z = (dz - day)<=0 ? dz - day + 12 : dz - day;
    //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
    if(QiMen.sanyuan[1][g][z]==1) {
      futou[index++] = new int[]{day,g,z,0};
    }
    //顺推到下个甲开头
    g = YiJing.JIA;
    day = 10 + g - dg;
    z = (dz+day>12) ? dz+day-12:dz+day;
    //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
    if(QiMen.sanyuan[1][g][z]==1) {
      futou[index++] = new int[]{0-day,g,z,0};
    }

    // 对天干大于己的，先逆推到上个己开头，再顺推到下个己开头，再顺推到下下个己开头
    if(dg>YiJing.JI) {
      g = YiJing.JI;
      day = dg - g;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
      g = YiJing.JI;
      day = 10 + g - dg;
      z = (dz+day>12) ? dz+day-12:dz+day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
         futou[index++] = new int[]{0-day,g,z,0};
      }
      g = YiJing.JI;
      day = 10+dg-YiJing.JI;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
    }
    //对天干小于己的，先顺推到下个己开头，再逆推到上个己开头，再逆推到上上个己开头
    if(dg<=YiJing.JI) {
      g = YiJing.JI;
      day = g - dg;
      z = (dz+day>12) ? dz+day-12:dz+day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
         futou[index++] = new int[]{0-day,g,z,0};
      }
      g = YiJing.JI;
      day = 10+dg-YiJing.JI;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
      g = YiJing.JI;
      day = 20+dg-YiJing.JI;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
      z = z<=0 ? z + 12 : z;
      //Debug.out("g = "+g+"; z="+z+ "; day = "+day);
      if(QiMen.sanyuan[1][g][z]==1) {
        futou[index++] = new int[]{day,g,z,0};
      }
    }
    /**
     * 超神接氣有時間的限制--「接不過五，超不過九」。超神超过九日以上，接气超过五日以上，就要置闰
     * 超神过9日，而接气小于5日，按超神算
     * 超神<9日，而接气>5日，按接气算
     * 均过9超5，按超神算。
     * 此处少加了一天，本是x>-6 && x<10
     */
    //取小于9或>-5的，
    int[] ret = new int[4];
    if(ret[1]<1) {
    for(int i=0; i<futou.length; i++) {
      if(futou[i][1]>0  && futou[i][0] >0 && futou[i][0]<9) {
        ret = futou[i];
      }
    }
    }
    if(ret[1]<1) {
    for(int i=0; i<futou.length; i++) {
     if(futou[i][1]>0 && futou[i][0] >-5 && futou[i][0]<0) {
        ret = futou[i];
      }
    }
    }
    if(ret[1]<1) {
    for(int i=0; i<futou.length; i++) {
     if(futou[i][1]>0 && futou[i][0] >=9) {
        ret = futou[i];
      }
    }
    }

    return ret;
  }
  /**
   * 得到符头，专用于拆补法
   * @return
   */
  public int[] getFutou(int dg, int dz) {
    int day=0,g=0,z=0;
    int[] futou = new int[4];

    if(dg==YiJing.JI || dg==YiJing.JIA) {
      g = dg;
      z = dz;
      futou = new int[]{0,dg,dz,0};
    }else if(futou[1]==0 && dg>YiJing.JI) {
      g = YiJing.JI;
      day = dg - g;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
          futou = new int[]{day,g,z,0};
    }else if(futou[1]==0 && dg>YiJing.JIA) {
      g = YiJing.JIA;
      day = dg - g;
      z = (dz - day)<=0 ? dz - day + 12 : dz - day;
          futou = new int[]{day,g,z,0};
    }

    for(int i=1; i<4; i++) {
      if (QiMen.sanyuan[i][g][z] == 1) {
        futou[3] = i;
        break;
      }
    }

    return futou;
  }

  /**
   * 闰月的判断
   * 闰月原则：在节气表中，凡小于前一节的均是闰月，因为只差15左右天嘛
   *           闰月节后一个节如果同闰月也是闰月
   *           闰月不闰1与12月
   */
  private boolean isMoreBig(int y, int jieNo) {
    int yun = daoc.getYunYue(y);
    int _date2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    int _m2 = _date2/1000000;
    if((Calendar.MONTHN1*1000000+Calendar.DAYN1*10000+Calendar.HOUR) < _date2 &&
       Math.abs(_m2 - Calendar.MONTHN1)<=2) {
      if(Calendar.YUN && !isYunJieqi(y,jieNo)) {
        return true;
      }else{
        return false;
      }
    }else{
      if(!Calendar.YUN && isYunJieqi(y,jieNo)) {
        return false;
      }else{
        return true;
      }
    }
  }

  /**
   * 节气的序列号1立春.2雨水.3...
   * 闰月没有1或2月
   * @param y
   * @param jieNo
   * @return
   */
  private boolean isYunJieqi(int y, int jieNo) {
    if(jieNo<=2)
      return false;
    int yun = daoc.getYunYue(y);
    int jieqi0 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-2];
    int jieqi1 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo-1];
    int jieqi2 = Calendar.jieqi2[y - Calendar.IYEAR][jieNo];
    if(jieqi2<jieqi1 && jieqi2/1000000==yun)
      return true;
    if(jieqi2>jieqi1 && jieqi1<jieqi0 && jieqi2/1000000==yun)
      return true;

    return false;
  }

  public static void main(String[] args) {
    DaoQiMen qm = new DaoQiMen();
    int[] ret = qm.getFutou(6,8);
    //Debug.out("实际是：g = "+ret[1]+"; z="+ret[2]+ "; day = "+ret[0]+"; 第"+ret[3]+"元。");
  }

  /**
   * 得到地盘奇仪序数
   * 阳遁：1+何宫-阳遁几局 大于小于9或0要加减9或置9
   * 阴遁：1-何宫+阴遁几局
   * @param i 地盘何宫
   */
  public int getDipanJiyi(int i) {
    int ju = Math.abs(whichJu);
    if(whichJu > 0) {
      return (10+i-ju)%9==0 ? 9 : (10+i-ju)%9;
    }else{
      return (10-i+ju)%9==0 ? 9 : (10-i+ju)%9;
    }
  }

  /**
   * 得到旬序数 为 旬数对应数=地支数 － 天干数 再对应一次
   * int[] xunshu = {1, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2};
   * xunname = {"","甲子(戊)","甲戌(己)","甲申(庚)","甲午(辛)","甲辰(壬)","甲寅(癸)"};
   * 如甲子=0,则xunshu[0]=xunname[1]为甲子戊；
   *   甲戌=10，则为xunshu[10]=xunname[2]为甲戌(己)；
   *   旬数+4即可得到所对应的天干顺序号了；
   */
  public int getXunShu(int sg, int sz) {
    return QiMen.xunshu[(sz-sg+120)%12];
  }

  /**
   * 得到直符直使序数
   * 阳遁：直符直使序数=所用局数+时辰所在旬序数 – 1
   * 阴遁：直符直使序数=１＋所用局数－时辰所在旬序数
   */
  public int getZhiFuShi(int sg, int sz) {
    int xs = getXunShu(sg, sz);
    int zfs = 0;
    if(this.whichJu>0) {
      zfs = (whichJu+xs-1+90)%9;
    }else{
      zfs = ((0-whichJu)-xs+1+90)%9;
    }
    return zfs==0?9:zfs;
  }

  /**
   * 求直符落宫
   * 阳遁时的求法：直符落宫数=局数 － 1＋时干在三奇六仪中所对应的次序数
   * 阴遁时的求法：直符落宫数=１＋局数－时干在三奇六仪中所对应的次序数
   * 以上不分阴阳遁，如逢天禽为直符那么，天禽皆随天芮走宫，以死门为直使。
   * ixzf为2为随地盘，1为随天盘
   */
  private int ixzf = 1;  //默认随天盘值
  public void setXzf(int xzf) {
  	this.ixzf = xzf;
  }
  public int getZhifuGong(int sg, int sz) {  //小值符随地盘不能在这改，一改天盘奇仪都变了，因为是从值符宫推出来的
//  	if(ixzf==2){  //小值符随地盘值符，求地盘值符落宫，先求出旬首对应的藏干如甲子戊的戊
//    	int xungan = QiMen.xungan[(sz-sg+12)%12];
//    	int i=1;
//    	for(; i<=9; i++) {
//    	  if(QiMen.sjly5[getDipanJiyi(i)]==xungan) break;
//    	}
//    	return i;
//    }
  	
  	//为随天盘值符
    if(sg == 1) {
      int g2 = getZhiFuShi(sg, sz);
//      /**
//       * (1)中五宫寄何宫，阳遁寄igJigong指定的宫，阴遁永寄2宫，这是第一处改动
//       */
//      g2 = g2!=5 ? g2 : (this.getJu()>0)? (this.igJigong==0?2:igJigong) : 2;
      return g2;
    }

    int g = 0;
    if(this.whichJu>0) {
      g = (whichJu-1+QiMen.sjly2[sg]+90)%9 ;
    }else{
      g = ((0-whichJu)+1-QiMen.sjly2[sg]+90)%9;
    }

//    /**
//     * (1)中五宫寄何宫，阳遁寄igJigong指定的宫，阴遁永寄2宫，这是第一处改动
//     */
//    g = g!=5 ? g : (this.getJu()>0)? (this.igJigong==0?2:igJigong) : 2;

    return g==0?9:g;
  }
  
  public int getZhifuGong() {
  	return this.getZhifuGong(SiZhu.sg, SiZhu.sz);
  }

  /**
   * 求直使落宫
   * 阳遁时的求法：时干甲１、乙２、丙３，直使落宫＝直使序数＋时干在十天干中序数－１
   * 阴遁时的求法：时干甲１0、乙９、丙８，直使落宫＝直使序数＋时干在十天干中序数－１
   * 以上不分阴阳遁，如果直使落中５宫皆寄坤２宫。
   */
	public int getZhishiGong(int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz);
    int g = 0;
    if(this.whichJu>0) {
      g =  (zfsxs + sg -1 +90)%9;
    }else{
      g =  (zfsxs + (11 - sg) -1 +90)%9;
      
    }

//    /**
//     * (2)中五宫寄何宫，阳遁寄igJigong指定的宫，阴遁永寄2宫，这是第二处改动
//     */
//    g = g!=5 ? g : (this.getJu()>0)? (this.igJigong==0?2:igJigong) : 2;

    return g==0?9:g;
  }
  public int getZhishiGong() {
  	return this.getZhishiGong(SiZhu.sg, SiZhu.sz);
  }

  /**
   * 得到指定宫的星序数
   * 九星不分阴阳遁局，永远顺时针依次环排八宫
   * 这里改中五宫寄宫没有任何影响，因为从来不会直接取第5宫对应的星
   */
  public int getGongXingOfZhuan(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //值符为某星的序号
    int zflg = getZhifuGong(sg,sz); //值符落宫数
    if(zflg == 5)  //落中5宫寄坤2宫
      zflg = 2;  //2原值为2
    if(zfsxs == 5) //为5则为2，需寄坤二宫，否则，5中宫后下一星上一星是?
      zfsxs = 2;  //2原值为2
    int i=1;
    int j=0;
    int k=0;

    for(; i<QiMen.jx2.length; i++) {
      if(zfsxs == QiMen.jx2[i])  break;
    }
    for(; j<QiMen.jgxh.length; j++) {
      if(zflg==QiMen.jgxh[j])  break;
    }
    for(; k<QiMen.jgxh.length; k++) {
      if(gong==QiMen.jgxh[k])  break;
    }
    if(gong==5)
      return 0;

    int x = (i + k - j + 8)%8==0?8:(i + k - j + 8)%8;
    //x = x == 5? 2:x;
    return QiMen.jx2[x];  ////返回什不会出现5，因为只在8星中搜索的
  }
  //飞宫法，值符中五宫不寄宫
  public int getGongXingOfFei(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //值符为某星的序号，有5为天禽星
    int zflg = getZhifuGong(sg,sz); //值符落宫数，有5为中宫

    int x = 0;
    if(whichJu>0) //zflg=5,1 gong==5,9 zfsxs=1,9;
    	x = (gong - zflg + zfsxs + 9)%9 == 0 ? 9 : (gong - zflg + zfsxs + 9)%9;
    else
    	x = (zflg - gong + zfsxs + 9)%9==0 ? 9 : (zflg - gong + zfsxs + 9)%9;

    return x;  
  }

  /**
   * 得到指定宫的门序数
   * 九星不分阴阳遁局，永远顺时针依次环排八宫
   * 这里改中五宫寄宫没有任何影响，因为从来不会直接取第5宫对应的星
   * simon 2011-12-23
   */
  public int getGongMenOfZhuan(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //值使为某门的序号
    int zslg = getZhishiGong(sg,sz); //值使落宫数
    if(zslg == 5)  //落中5宫寄坤2宫
      zslg = 2;
    if(zfsxs == 5) //为5则为2，需寄坤二宫，否则，5中门后下一门上一门是?
      zfsxs = 2;
    int i=1;
    int j=0;
    int k=0;

    for(; i<QiMen.bm2.length; i++) {
      if(zfsxs == QiMen.bm2[i])  break;  //zfsxs=2,8--> i=6,2
    }
    for(; j<QiMen.jgxh.length; j++) {
      if(zslg==QiMen.jgxh[j])  break;  //zslg=2,8--> j=5,1
    }
    for(; k<QiMen.jgxh.length; k++) {
      if(gong==QiMen.jgxh[k])  break;  //gong=5, k=0
    }
    if(gong==5)
      return 0;
    return QiMen.bm2[(i + k - j + 8)%8==0?8:(i + k - j + 8)%8];  //返回什不会出现5，因为只在8门中搜索的
  }
  public int getGongMenOfFei(int gong, int sg, int sz) {
    int zfsxs = getZhiFuShi(sg,sz); //值使为某门的序号
    int zslg = getZhishiGong(sg,sz); //值使落宫数
    
    int x = 0;
    if(whichJu>0) //zflg=5,1 gong==5,9 zfsxs=1,9;
    	x = (gong - zslg + zfsxs + 9)%9 == 0 ? 9 : (gong - zslg + zfsxs + 9)%9;
    else
    	x = (zslg - gong + zfsxs + 9)%9==0 ? 9 : (zslg - gong + zfsxs + 9)%9;

    return x;    
  }

  /**
   * 天盘直符随时干走（阳顺阴逆）
   * 此天盘值符随天盘值符，转盘法
   */
  public int getGongShenOfZhuan(int gong, int sg, int sz) {
    //int zflg =  getZhifuGong(sg,sz); //值符落宫数
  	int zflg = 0;
    if(ixzf==2) zflg = this.getDipanzhifu(sg, sz);
    else zflg = getZhifuGong(sg,sz); //值符落宫数
    
    //(二)中五宫寄何宫，阳遁寄igJigong指定的宫，阴遁永寄2宫，这是第二处改动
    if(zflg == 5) {
    //zflg = 2;
    	zflg = (this.getJu()>0)? (igJigong==0?2:igJigong) : 2;      
    }
    int j=0;
    int k=0;

    for(; j<QiMen.jgxh.length; j++) {
      if(zflg==QiMen.jgxh[j])  break;
    }
    for(; k<QiMen.jgxh.length; k++) {
      if(gong==QiMen.jgxh[k])  break;
    }

    if(gong==5)
      return 0;
    if(whichJu>0)
      return (1 + k - j + 8)%8==0?8:(1 + k - j + 8)%8;
    else
      return (1 - k + j + 8)%8==0?8:(1 - k + j + 8)%8;
  }
  //飞盘法，值符落宫遇5宫，不寄宫
  public int getGongShenOfFei(int gong, int sg, int sz) {
    int zflg = 0;
    if(ixzf==2) zflg = this.getDipanzhifu(sg, sz);
    else zflg = getZhifuGong(sg,sz); //值符落宫数

		if(whichJu>0)
			return QiMen.fpjg[(gong-zflg+9+1)%9==0 ? 9 : (gong-zflg+9+1)%9];
		else
			return QiMen.fpjg[(zflg-gong+9+1)%9==0 ? 9 : (zflg-gong+9+1)%9];
  }
  /**
   * 转盘法天盘奇仪公式：
   * 阳遁：天盘奇仪对应数字＝天盘星对应数字－局数+1（如果结果小于1那么将结果加上9即为奇仪对应的数字）；
   * 阴遁：天盘奇仪对应数字＝局数－天盘星对应数字+1（如果结果小于1那么将结果加上9即为奇仪对应的数字）；
   */
  public int getTianpanJiyiOfZhuan(int gong, int sg, int sz) {
    if(gong==5)
      return 0;
    int x = getGongXingOfZhuan(gong, sg, sz);
    int g = 0;

    if(this.whichJu>0) {
      g =  (x - whichJu + 1 + 90)%9;
    }else{
      g =  ((0 - whichJu) - x + 1 + 90)%9;
    }

    return g==0?9:g;
  }
  public int getTianpanJiyiOfFei(int gong, int sg, int sz) {
    int x = getGongXingOfFei(gong, sg, sz);  //得到该宫的天盘星，天盘星序数即为宫序号
    //return QiMen.sjly5[getDipanJiyi(x)]; //该宫的地盘奇仪序号对应的天干
    return getDipanJiyi(x); //该宫的地盘奇仪序号即为天盘的奇仪序号
  }

  /**
   * 在八宫图中央输出天地人神盘
   * @param o
   * @return
   */
  private String format(String o) {
    if(o==null)
      o="";
    if(o.trim().equals("null"))
       o="";
    if(o.getBytes().length<8)
      o=daoyj.getRepeats(" ", (8-o.getBytes().length))+o;
    else if(o.getBytes().length==12){
    	return (o+daoyj.getRepeats(" ",16-o.getBytes().length));
    }else if(o.getBytes().length==10){
    	return (daoyj.getRepeats(" ",2)+o+daoyj.getRepeats(" ",16-2-o.getBytes().length));
    }
    
    int len = o.getBytes().length;
    int hlen = (16-len)/2;
    return (daoyj.getRepeats(" ",hlen)+o+daoyj.getRepeats(" ",hlen));
  }
  /**
   * 地九宫图中以靠左输出
   * @param o
   * @return
   */
  private String formatLeft(String o) {
    if(o==null)
      o="";
    o = o.trim();
    o = o.replaceAll("null", "");

    int len = o.getBytes().length;
    int hlen = 16-len;
    return (o+daoyj.getRepeats(" ",hlen));
  }

  /**
   * 输出九宫图
   * @param o
   */
  private void out1(Object o) {
    str.append("    "+o.toString()+"\r\n");
  }

  /**
   * 排局
   * 设置地盘每宫显示的信息
   * _6格为空，_5显示九星+地盘天干、_4显示八门+地盘天干、_3显示八神，_2显示空亡、马星年、月、日、时干标识
   */
  private String[][] makeJiuGong(int iZfs) {
    String[][] rs = new String[10][7];
    String[] bs = whichJu>0?QiMen.bs1:QiMen.bs1;
    
    /**
     * 设置X0为天盘干宫月旺衰，地盘干宫月旺衰，八门宫月旺衰，九星宫月旺衰
     */
    for(int i=1; i<=9; i++) {
    	if(i==5) continue;
    	//天盘干
    	rs[i][6] = QiMen.gan_gong_wx2[gInt[2][3][i]][i];
    	rs[i][6] += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[2][3][i]][SiZhu.yz]];
    	//地盘干
    	rs[i][6] += QiMen.gan_gong_wx2[gInt[4][5][i]][i];
    	rs[i][6] += SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[gInt[4][5][i]][SiZhu.yz]];
    	//八门
    	rs[i][6] +=  QiMen.men_gong2[gInt[3][1][i]][i];
    	rs[i][6] +=  QiMen.men_yue[gInt[3][1][i]][SiZhu.yz];
    	//九星
    	rs[i][6] +=  QiMen.xing_gong[gInt[2][1][i]][i];
    	rs[i][6] +=  QiMen.xing_yue[gInt[2][1][i]][SiZhu.yz];
    }
//    //1. 设置x5=八门+ 三奇六仪
//    for(int i=1; i<=9; i++) {
//      if(i==2) {//如果为2宫，则星要同时显示第5宫的地盘奇仪
//        rs[i][5] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
//      } else {
//        rs[i][5] = QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
//      }
//    }
//
//    //2. 设置x4=九星+天盘奇仪，为天芮星取地盘5宫奇仪   
//    for(int i=1; i<=9; i++) {
//      if(gInt[2][1][i]==2) {//如果天盘九星顺序为2宫，则星要同时显示第5宫的天盘奇仪和星
//        rs[i][4] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.jx1[5] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
//      } else {
//        rs[i][4] = QiMen.jx1[gInt[2][1][i]] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
//      }
//    }
    //1. 设置x5=九星+ 地盘三奇六仪，第三处改动，中五宫之寄宫问题
    for(int i=1; i<=9; i++) {
      if(this.isJiGong(i)) {//如果igJigong为0或2，则在第2宫要同时显示第5宫的地盘奇仪；如果igJigong为8，则第8宫要同时显示第5宫的地盘奇仪
        rs[i][5] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.jx1[gInt[2][1][i]]  + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
      } else {
        rs[i][5] = QiMen.jx1[gInt[2][1][i]] + "  " + YiJing.TIANGANNAME[gInt[4][5][i]];
      }
    }

    //2. 设置x4=八门+天盘奇仪，为天芮星取地盘5宫奇仪   
    for(int i=1; i<=9; i++) {
      if(this.isTpJigong(i)) {//如果igJigong为0或2，则在第2宫要同时显示第5宫的地盘奇仪；如果igJigong为8，则第8宫要同时显示第5宫的地盘奇仪
        rs[i][4] = YiJing.TIANGANNAME[gInt[4][5][5]] + "  " + QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
      }else {
        rs[i][4] = QiMen.bm1[gInt[3][1][i]] + "  " + YiJing.TIANGANNAME[gInt[2][3][i]];
      }
    }
    
    //3. 设置x3=神盘
    if(iZfs==1) {
      for(int i=1; i<=9; i++) {
        rs[i][3] = bs[gInt[1][1][i]] + "    " ;
      }
    }else{
    }
    
    /**
     * 4. 设置x2=一些重要的：马星、桃花、六仪击刑、天盘干与九宫相冲
     * 六仪击刑对地盘干也要看的
     */
    for(int i=1; i<=9; i++) {
    	int dz = QiMen.jgdz[i];  //得到九宫所藏的地支
    	int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
    	
    	if(SiZhu.YIMA[SiZhu.sz][dz1] || SiZhu.YIMA[SiZhu.sz][dz2]) {
				rs[i][2] += "马 ";  //是否临时支驿马
			}
    	if(SiZhu.YIMA[SiZhu.rz][dz1] || SiZhu.YIMA[SiZhu.rz][dz2]) {
				rs[i][2] += "驲 ";  //是否临日支驿马
			}
    	
    	String gangongHua = QiMen.gan_gong_hua[gInt[2][3][i]][i];
    	if(gangongHua!=null) 
    		rs[i][2] += "桃 ";  //时柱空亡    			
    	if(isTpJigong(i) && gangongHua==null && QiMen.gan_gong_hua[gInt[4][5][5]][i]!=null) 
    		rs[i][2] += "桃 ";
    	
    	int gangongNum_t1 = QiMen.gan_gong_t[gInt[2][3][i]][i];  //天盘干+宫，用于六仪击刑
	    if(gangongNum_t1!=0) rs[i][2] += "刑 ";
	    if(isTpJigong(i) && gangongNum_t1==0 && QiMen.gan_gong_t[gInt[4][5][5]][i]!=0) 
    		rs[i][2] += "刑 "; //这是天盘干的
	    
	    int gangongNum_d1 = QiMen.gan_gong_t[gInt[4][5][i]][i];  //地盘干+宫，用于六仪击刑
	    if(gangongNum_d1!=0) rs[i][2] += "刂 ";
	    if(isJiGong(i) && gangongNum_d1==0 && QiMen.gan_gong_t[gInt[4][5][5]][i]!=0) 
    		rs[i][2] += "刂 "; //这是地盘干的
	    
	    //天盘干与地盘干地支相刑
	    if(this.isTDZiXing(i)) rs[i][2] += "型 "; 
	    if(this.isNyrsXing(i)) rs[i][2] += "形 ";
	    
	    int gangongNum_t2 = QiMen.gan_gong_ch[gInt[2][3][i]][i];  //天盘干+宫，只适用于天盘干，用于六仪击刑
	    if(gangongNum_t2!=0) rs[i][2] += "冲 ";
	    if(isTpJigong(i) && gangongNum_t2==0 && QiMen.gan_gong_ch[gInt[4][5][5]][i]!=0) 
    		rs[i][2] += "冲 ";
	    
	    String ganganCh = QiMen.gan_gan_ch[gInt[2][3][i]][gInt[4][5][i]];  //天盘干+地盘干是否相冲
	    if(ganganCh!=null) rs[i][2] += "充 ";
	    if(isTpJigong(i) && ganganCh==null && QiMen.gan_gan_ch[gInt[4][5][5]][gInt[4][5][i]]!=null) 
    		rs[i][2] += "充 ";
	    
	    //天盘干、地盘干是否六合
	    boolean isTD6he = YiJing.TGWUHE[gInt[2][3][i]][gInt[4][5][i]]!=0;
	    if(isTD6he) rs[i][2] += "合 ";
	    else if(isTpJigong(i) && YiJing.TGWUHE[gInt[4][5][5]][gInt[4][5][i]]!=0) //天盘2个
	    	rs[i][2] += "合 ";
	    else if(isJiGong(i) && YiJing.TGWUHE[gInt[2][3][i]][gInt[4][5][5]]!=0)  //地盘2个
    		rs[i][2] += "合 ";
	    //else if(gInt[2][1][i]==2 && i==2 && YiJing.TGWUHE[gInt[4][5][5]][gInt[4][5][5]]!=0)  //天地盘各2个
    	//	rs[i][2] += "合 ";
	    
	    //天地盘支与宫支是否三合
	    if(isTDG3he(i)) rs[i][2] += "匼 ";
	    else {//如果有天地宫支三合，下面的天地支半合、天宫半合、天宫六合就不判断了
	    	if(this.isTDZi3he(i)) rs[i][2] += "峇 ";
	    	if(this.isTG6he(i)) rs[i][2] += "冾 ";  //有天宫六合，就不判断天宫半合了
	    	else if(this.isTG3he(i)) rs[i][2] += "洽 ";
	    }
    }
    
    /**
     * 5. 设置x1=年、月、日、时、空亡、入墓
     * 如果年月日时落5宫则寄2宫
     */
  //加上宫的旺衰
    for(int i=1; i<=9; i++) {
    	rs[i][1] += QiMen.gong_yue[i][SiZhu.yz]+" ";
    }
    
    int[] xksz = pub.getXunKong(SiZhu.sg,SiZhu.sz);  //得到时柱旬空地支    	
    int[] xkrz = pub.getXunKong(SiZhu.rg,SiZhu.rz);  //得到日柱旬空地支    	
    //对于甲开头的，需要另外换算
    int jiakaitou1 = -1,jiakaitou2 = -1,jiakaitou3 = -1,jiakaitou4 = -1 ; 
    for(int i=1; i<=9; i++) {
    	if(SiZhu.ng==1) jiakaitou1 = this.getXunShu(SiZhu.ng, SiZhu.nz)+4;
    	if(SiZhu.yg==1) jiakaitou2 = this.getXunShu(SiZhu.yg, SiZhu.yz)+4;
    	if(SiZhu.rg==1) jiakaitou3 = this.getXunShu(SiZhu.rg, SiZhu.rz)+4;
    	if(SiZhu.sg==1) jiakaitou4 = this.getXunShu(SiZhu.sg, SiZhu.sz)+4;
    		
    	if(gInt[2][3][i]==SiZhu.ng || jiakaitou1==gInt[2][3][i]) rs[i][1] += "年 ";
    	if(gInt[2][3][i]==SiZhu.yg || jiakaitou2==gInt[2][3][i]) rs[i][1] += "月 ";
    	if(gInt[2][3][i]==SiZhu.rg || jiakaitou3==gInt[2][3][i]) rs[i][1] += "日 ";
    	if(gInt[2][3][i]==SiZhu.sg || jiakaitou4==gInt[2][3][i]) rs[i][1] += "时 ";
    	if(isTpJigong(i)) {
    		if(gInt[4][5][5]==SiZhu.ng || jiakaitou1==gInt[4][5][5]) rs[i][1] += "年 ";
      	if(gInt[4][5][5]==SiZhu.yg || jiakaitou2==gInt[4][5][5]) rs[i][1] += "月 ";
      	if(gInt[4][5][5]==SiZhu.rg || jiakaitou3==gInt[4][5][5]) rs[i][1] += "日 ";
      	if(gInt[4][5][5]==SiZhu.sg || jiakaitou4==gInt[4][5][5]) rs[i][1] += "时 ";
    	}
    }
    
    
    for(int i=1; i<=9; i++) {
    	int dz = QiMen.jgdz[i];  //得到九宫所藏的地支
    	int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	        	 
    	
    	if(dz1==xksz[0] || dz2==xksz[0] || dz1==xksz[1] || dz2==xksz[1]) {
    		rs[i][1] += "空 ";  //时柱空亡    			
    	}
    	if(dz1==xkrz[0] || dz2==xkrz[0] || dz1==xkrz[1] || dz2==xkrz[1]) {
    		rs[i][1] += "昳 ";  //日柱空亡    			
    	}
    	
    	String gangongMu = QiMen.gan_gong_mu[gInt[2][3][i]][i];
    	if(gangongMu!=null) {
    		if(SiZhu.TGSWSJ[gInt[2][3][i]][SiZhu.yz]>5) rs[i][1] += "墓 ";  //天盘干入墓			
    		else rs[i][1] += "库 ";  //天盘干在月令旺相入库
    	}else if(isTpJigong(i) && QiMen.gan_gong_mu[gInt[4][5][5]][i]!=null) {
    		if(SiZhu.TGSWSJ[gInt[4][5][5]][SiZhu.yz]>5) rs[i][1] += "墓 ";  //天盘干入墓			
    		else rs[i][1] += "库 ";  //天盘干在月令旺相入库
    	}  
    	
    	//地盘干入墓，不判断库了
    	gangongMu = QiMen.gan_gong_mu[gInt[4][5][i]][i];
    	if(gangongMu!=null) {
    		rs[i][1] += "冢 ";  
    	}else if(isJiGong(i) && QiMen.gan_gong_mu[gInt[4][5][5]][i]!=null) {
    		rs[i][1] += "冢 ";  
    	}  
    } 
        
    //格式化
    for(int i=1; i<=9; i++) {
      for(int j=1; j<=2; j++) {
        rs[i][j] = formatLeft(rs[i][j]);
      }
    }
    for(int i=1; i<=9; i++) {
      for(int j=3; j<=6; j++) {
        rs[i][j] = format(rs[i][j]);
      }
    }

    return rs;
  }
  
  /**
   * 天地盘干所对应的地支是否相刑
   * @param gong
   * @return
   */
  private boolean isTDZiXing(int gong) {
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	int dpdz1 = getZiOfgan(gInt[4][5][gong]), dpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZXING[tpdz1][dpdz1]!=0) return true;
  	else if(isTpJigong(gong) && YiJing.DZXING[tpdz2][dpdz1]!=0 ) //天盘2个
  		return true;
  	else if(this.isJiGong(gong) && YiJing.DZXING[tpdz1][dpdz2]!=0)  //地盘2个
  		return true;
  	else if(this.isJiGong(gong) && isTpJigong(gong) && YiJing.DZXING[tpdz2][dpdz2]!=0)
  		return true;
  	//tpdz2,dpdz2不需要组合
  	
  	return false;
  }
  
  /**
   * 判断该宫是否是年、月、日、时干落宫，如果是，再判断落宫地支与年月日时地支是否相刑
   * @param gong
   * @return
   */
  private boolean isNyrsXing(int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
  	int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
  	int[] tg2 = {gInt[2][3][gong],0}; //得到该宫的天干
  	
  	if(isTpJigong(gong)) tg2[1]=gInt[4][5][5];
  	
  	int[] zi4 = {SiZhu.nz, SiZhu.yz, SiZhu.rz, SiZhu.sz};
  	int[] gan4 = {SiZhu.ng, SiZhu.yg, SiZhu.rg, SiZhu.sz};
  	for(int i = 0; i<gan4.length; i++) {  //将甲开头的转成对应的天干
  		if(gan4[i]==YiJing.JIA) gan4[i] = getXunShu(gan4[i], zi4[i])+4;
  	}  	
  	
  	for(int i=0; i<zi4.length; i++) {
  		if((tg2[0]==gan4[i] || tg2[1]==gan4[i]) && (dz1==zi4[i] || dz2==zi4[i])) {
  			if(YiJing.DZXING[zi4[i]][dz1]!=0 || YiJing.DZXING[zi4[i]][dz2]!=0) return true;
  		}
  	}
  	
  	return false;
  }
  
  /**
   * 判断天盘支、地盘支、宫支是否三合
   * @param gong
   * @return
   */
  private boolean isTDG3he(int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
  	int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	int dpdz1 = getZiOfgan(gInt[4][5][gong]), dpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	boolean isTDG3he = YiJing.DZSHANHE[tpdz1][dpdz1][dz1]!=0 ||
		 									 YiJing.DZSHANHE[tpdz1][dpdz1][dz2]!=0;
  	if(isTDG3he) return true;
  	else if(isTpJigong(gong) && (YiJing.DZSHANHE[tpdz2][dpdz1][dz1]!=0 ||
  										YiJing.DZSHANHE[tpdz2][dpdz1][dz2]!=0)) //天盘2个
  		return true;
  	else if(isJiGong(gong) && (YiJing.DZSHANHE[tpdz1][dpdz2][dz1]!=0 ||
  										YiJing.DZSHANHE[tpdz1][dpdz2][dz2]!=0))  //地盘2个
  		return true;
  	//tpdz2,dpdz2不需要组合
  	
  	return false;
  }
  private boolean isTG6he(int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
  	int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZLIUHE[tpdz1][dz1]!=0 || YiJing.DZLIUHE[tpdz1][dz2]!=0)
  		return true;
  	else if(isTpJigong(gong) && (YiJing.DZLIUHE[tpdz2][dz1]!=0 || 
  			YiJing.DZLIUHE[tpdz2][dz2]!=0)) return true;

  	return false;
  }
  private boolean isTG3he(int gong) {
  	int dz = QiMen.jgdz[gong];  //得到九宫所藏的地支
  	int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZBANHE[tpdz1][dz1]!=0 || YiJing.DZBANHE[tpdz1][dz2]!=0)
  		return true;
  	else if(isTpJigong(gong) && (YiJing.DZBANHE[tpdz2][dz1]!=0 || 
  			YiJing.DZBANHE[tpdz2][dz2]!=0)) return true;

  	return false;
  }
  private boolean isTDZi3he(int gong) {
  	int tpdz1 = getZiOfgan(gInt[2][3][gong]), tpdz2 = getZiOfgan(gInt[4][5][5]);
  	int dpdz1 = getZiOfgan(gInt[4][5][gong]), dpdz2 = getZiOfgan(gInt[4][5][5]);
  	
  	if(YiJing.DZBANHE[tpdz1][dpdz1]!=0) return true;
  	else if(isTpJigong(gong) && YiJing.DZBANHE[tpdz2][dpdz1]!=0 ) //天盘2个
  		return true;
  	else if(isJiGong(gong) && YiJing.DZBANHE[tpdz1][dpdz2]!=0)  //地盘2个
  		return true;
  	//tpdz2,dpdz2不需要组合
  	
  	return false;
  }
  //得到地盘值符，即小值符随地盘时的旬首在地盘支落宫
  public int getDipanzhifu(int sg, int sz) {
		int xungan = QiMen.xungan[(sz-sg+12)%12];
		int i=1;
		for(; i<=9; i++) {
		  if(QiMen.sjly5[getDipanJiyi(i)]==xungan) break;
		}
		return i;
  }
  
  /** 由奇仪得到相应的地支 */
  private int getZiOfgan(int gan) {
  	return new int[]{0,0,0,0,0,YiJing.ZI,YiJing.XU,YiJing.SHEN,YiJing.WUZ,YiJing.CHEN,YiJing.YIN}[gan];
  }

  /**
   * 得到盘柱各要素，各五行与干支数均以六爻所定义数为准
   * gInt[0][0]=四柱干支 节气 三元 局数 值符使12  值符落宫13  值使落宫14 小值符
   * gInt[1][1][1-9]=八神,gInt[1][2][1-9]=八神五行
   * gInt[2][1][1-9]=九星顺序数，九星五行,天盘奇仪，天干五行，三奇六仪藏支
   * gInt[3][1][1-9]=宫门序数,gInt[3][2]=门五行
   * gInt[4][1][1-9]=九宫后天八卦数，八卦五行，地盘九星，地盘八门，地盘奇仪，地盘奇仪五行、 所藏六甲地支
   */
  public void getGlobleInfo(int iszf, int iZfs) {
  	this.setXzf(iZfs);
    gInt[0][0] = new int[]{0, SiZhu.ng, SiZhu.nz, SiZhu.yg, SiZhu.yz, SiZhu.rg,
        SiZhu.rz, SiZhu.sg,SiZhu.sz, 
        whichJie, whichYuan, whichJu,
        getZhiFuShi(SiZhu.sg, SiZhu.sz), getZhifuGong(SiZhu.sg, SiZhu.sz),
        getZhishiGong(SiZhu.sg, SiZhu.sz),1};
    /**
     * 神盘：小值符随大值符情况
     */
    if(iZfs==1) {
      for(int i=1; i<=9; i++) {
        gInt[1][1][i] = iszf==2 ? getGongShenOfFei(i,SiZhu.sg,SiZhu.sz) : getGongShenOfZhuan(i,SiZhu.sg,SiZhu.sz);
      }
    }else{  //随地盘值符    	
    	for(int i=1; i<=9; i++) {
        gInt[1][1][i] = iszf==2 ? getGongShenOfFei(i,SiZhu.sg,SiZhu.sz) : getGongShenOfZhuan(i,SiZhu.sg,SiZhu.sz);
      }
    }
    for(int i=1; i<=9; i++) {
    	try{
    		gInt[1][2][i] = iszf==2 ? QiMen.fpjswx[gInt[1][1][i]] : QiMen.bs3[gInt[1][1][i]];
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("gInt[1][1]["+i+"]="+gInt[1][1][i]);
    	}
    }

    /**
     * 天盘
     */
    for(int i=1; i<=9; i++) {
      gInt[2][1][i] = iszf==2 ? getGongXingOfFei(i,SiZhu.sg,SiZhu.sz) : 
      	getGongXingOfZhuan(i,SiZhu.sg,SiZhu.sz);  
    }
    for(int i=1; i<=9; i++) {
      gInt[2][2][i] = iszf==2 ? QiMen.fpjxwx[gInt[2][1][i]] : QiMen.jx3[gInt[2][1][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[2][3][i] = iszf==2 ? getTianpanJiyiOfFei(i,SiZhu.sg,SiZhu.sz) :
      	getTianpanJiyiOfZhuan(i,SiZhu.sg,SiZhu.sz);
    }
    for(int i=1; i<=9; i++) {
      gInt[2][5][i] = QiMen.sjly4[gInt[2][3][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[2][3][i] = QiMen.sjly5[gInt[2][3][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[2][4][i] = YiJing.TIANGANWH[gInt[2][3][i]];
    }


    /**
     * 人盘
     */
    for(int i=1; i<=9; i++) {
      gInt[3][1][i] = iszf==2 ? getGongMenOfFei(i,SiZhu.sg,SiZhu.sz) :
      	getGongMenOfZhuan(i,SiZhu.sg,SiZhu.sz);  
    }
    for(int i=1; i<=9; i++) {
      gInt[3][2][i] = iszf==2 ? QiMen.fpjmwx[gInt[3][1][i]]:QiMen.bm3[gInt[3][1][i]];
    }

    /**
     * 地盘
     */
    for(int i=1; i<=9; i++) {
      gInt[4][1][i] = QiMen.jgbg[i];  //YiJing.JingGuaName[]
    }
    for(int i=1; i<=9; i++) {
      gInt[4][2][i] = QiMen.jgwh[i];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][3][i] = iszf==2 ? QiMen.fpjg[i]:QiMen.dpjx4[i];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][4][i] = iszf==2 ? QiMen.fpjg[i]:QiMen.dpbm4[i];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][5][i] = getDipanJiyi(i);
    }
    for(int i=1; i<=9; i++) {
      gInt[4][7][i] = QiMen.sjly4[gInt[4][5][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][5][i] = QiMen.sjly5[gInt[4][5][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][6][i] = YiJing.TIANGANWH[gInt[4][5][i]];
    }
    for(int i=1; i<=9; i++) {
      gInt[4][8][i] = QiMen.jgdz[i];
    }
  }

  /**
   * 得到当前局数
   * 正数为阳局，负数为阴遁局；
   */
  public int getJu() {
  	return whichJu;
  }
  /**
   * 是否是中五宫所寄之宫
   * @param gong
   * @return
   */
  public boolean isJiGong(int gong) {
  	if(!iszf) return false;
  	//return gong== (igJigong==8?8:2);
  	return gong== getJiGong();
  }
  /**
   * 得到中五宫在地盘所寄之宫
   * @param gong
   * @return
   */
  private boolean iszf = false;  //设置是否是转盘奇门
  public void setZhuanpan(boolean iszf) {
  	this.iszf = iszf;
  }
  public boolean iszf() {
  	return iszf;
  }
  public int getJiGong() {
  	//return gong== (igJigong==8?8:2);
  	return whichJu>0 ? (igJigong==8?8:2) : 2;
  }
  /**
   * 判断当前宫的天盘星是否为中五宫所寄之宫的地盘星
   * 因为：如果是，则表示该宫的天盘奇仪有2个，要作特殊处理
   * @param gong
   * @return
   */
  public boolean isTpJigong(int gong) {
  	//return gInt[2][1][gong]==(igJigong==8?8:2);
  	if(!iszf) return false;  //飞盘奇门，没有寄宫
  	return gInt[2][1][gong]==getJiGong();
  }
  /**
   * 得到中五宫在天盘所寄之宫，即天盘奇仪有2个
   * @param gong
   * @return
   */
  public int getTpJigong() {
  	for(int i=1; i<=9; i++) {
  		if(isTpJigong(i)) return i;
  	}
  	return 0;
  }
}
