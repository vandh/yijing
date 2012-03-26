package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public abstract class QimenBase {
	final String[] YOUQI = {"无气","无气","有气","旺相"}; //日干特殊，-2,-1为无气，0有气，1旺相
	final String[] TDRH1 = {"得天时","得地利","得人和","得神助"}; 
	final String[] TDRH2 = {"不得天时","不得地利","不得人和","不得神助"};
	
	DaoQiMen daoqm;
	DaoSiZhuMain daosz;  //四柱的dao
	QimenPublic p;
	String[] my ; //保存命运宫所有的描述情况
	int index = 0; 				//计数器，为my所用
	boolean iszf; 	//是否是转盘奇门

	String mingzhu;  	//命主
	int yongshen;  		//用神类型，年干1，月干2，日干3，时干4
	int ysgan,yszi;   //用神干、支
	int ystpgong;  		//用神天盘宫
	int ysdpgong;
	boolean boy;			//是否男
	boolean yang;			//是否是阳遁
	
	String[] rgws ,rgjxws ,rgbmws ;  //日干落宫天干、九星、八门旺衰
	String[] rgsanji; 		//日宫是否得三奇
	String[] rgheju; 			//得到日干是否合局
	String[] rgbsjx; 			//日宫神的吉凶
	String[] rgjxjx;  		//日宫是否得吉星
	String[] rgbmjx;   		//日宫门的吉凶		
	
	int zhishigong ;   	//值使宫
	int zhifugong;      //值符宫
	int[] mzhu ;      	//年命的干与支数组
	int mtpgong ;  			//命宫
	int mdpgong;				//命地盘宫
	int caixing; 				//财星，生门所临之星
	
	boolean isMenfu; 		//八门伏呤
	boolean isMenfan; 	//八门反呤
	boolean isXingfu; 	//九星伏呤
	boolean isXingfan;  //九星反呤
	
	int kaimengong ;  //八门所在的宫
	int jing1mengong ;
	int jing3mengong ;
	int shengmengong; 
	int simengong;
	int shangmengong;
	int xiumengong;
	int dumengong;
	
	int niantpgong ;  //年干天盘落宫
	int niandpgong ;	//年干地盘落宫
	int yuetpgong ;   //月干天盘宫
	int yuedpgong ;   //月干地盘宫
	int ritpgong ;   //日干天盘宫
	int ridpgong ;   //日干地盘宫
	int shitpgong ;   //时干天盘宫
	int shidpgong ;		//时干地盘宫
	int[] rgtptpjy,rgdpdpjy;   //日宫天盘奇仪，地盘奇仪
	int[] rgtpdpjy; 	//日宫天盘地盘奇仪
	int[] rgdptpjy; 	//日宫地盘天盘奇仪
	
	int wutpgong ;	   //十天敢天盘与地盘落宫
	int wudpgong ;
	int jitpgong;
	int jidpgong;
	int xintpgong;
	int xindpgong;
	int rentpgong;
	int guitpgong;
	int rendpgong;
	int guidpgong;
	int gengtpgong;
	int gengdpgong;
	int yitpgong;
	int yidpgong;
	int bingtpgong;
	int bingdpgong;
	int dingtpgong ;		//丁天盘落宫
	int dingdpgong ;		//丁地盘落宫
	
	int shenwugong ;		//八神落宫 
	int shenshegong;
	int shenyingong;
	int shenhugong;
	int shendigong;
	int shentiangong;
	int shenhegong;
	
	int xingpenggong ;	//九星落宫	
	int xingchonggong;
	int xingfugong;
	int xingyinggong;
	int xingruigong;
	int xingzhugong;
	int xingxingong;
	int xingqingong;	
	int xingrengong;
	
	protected void init(String mzText1, int ysNum1,boolean boy1, int len) {
		my = new String[len];
		index = 0;
		
		mingzhu = mzText1;
		boy = boy1;
		yongshen = ysNum1;
		String[] ysinfo = p.getYShenInfo(yongshen, 0, 0);		
  	//yshenname = ysinfo[0];
  	ystpgong = Integer.valueOf(ysinfo[1]);  //用神天盘落宫
  	ysgan = Integer.valueOf(ysinfo[2]);
  	yszi = Integer.valueOf(ysinfo[3]);
  	ysdpgong = p.getDiGong(ysgan, yszi);
		
		yang = daoqm.getJu()>0;
		
		mzhu = p.getMZhu(mingzhu);
  	mtpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)?p.getTianGong(mzhu[0], mzhu[1]):0;  //命宫
  	mdpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)?p.getDiGong(mzhu[0], mzhu[1]):0;  //命宫
  	
  	niantpgong = p.getTianGong(SiZhu.ng, SiZhu.nz);  //年干宫
  	niandpgong = p.getDiGong(SiZhu.ng, SiZhu.nz);
  	ritpgong = p.getTianGong(SiZhu.rg, SiZhu.rz);  //日干天盘宫
  	ridpgong = p.getDiGong(SiZhu.rg, SiZhu.rz);  //日干地盘宫
  	yuetpgong = p.getTianGong(SiZhu.yg, SiZhu.yz);  //月干天盘宫
  	yuedpgong = p.getDiGong(SiZhu.yg, SiZhu.yz);  //月干地盘宫
  	shitpgong = p.getTianGong(SiZhu.sg, SiZhu.sz);  //时干天盘宫
  	shidpgong = p.getDiGong(SiZhu.sg, SiZhu.sz);
		
  	rgtpdpjy = p.getDpjy(ritpgong); 		
  	rgdptpjy = p.getTpjy(ridpgong); 
  	rgtptpjy = p.getTpjy(ritpgong);
  	rgdpdpjy = p.getDpjy(ridpgong);
  	
		rgws = p.gettgWS(SiZhu.rg, SiZhu.rz);  //得到日干旺衰情况 -1无气，0，-2为稍有气，1为旺相
		rgjxws = p.getxingWS(ritpgong);
		rgbmws = p.getmenWS(ritpgong);
		rgbsjx = p.getshenJX(ritpgong);  //神的吉凶
		rgsanji = p.getSanji(ritpgong); 	//是否得三奇
		rgheju = p.isganHeju(SiZhu.rg, SiZhu.rz); //得到日干是否合局
		rgjxjx = p.getxingJX(ritpgong);  //是否得吉星
		rgbmjx = p.getmenJX(ritpgong);   //门的吉凶	
		
		zhishigong = daoqm.getZhishiGong();   //值使宫
  	zhifugong = daoqm.getZhifuGong(); 
  	
  	kaimengong = p.getMenGong(QiMen.MENKAI);
  	shengmengong = p.getMenGong(QiMen.MENSHENG);
  	jing1mengong = p.getMenGong(QiMen.MENJING1);
  	jing3mengong = p.getMenGong(QiMen.MENJING3);
  	simengong = p.getMenGong(QiMen.MENSI);
  	shangmengong = p.getMenGong(QiMen.MENSHANG);
  	xiumengong = p.getMenGong(QiMen.MENXIU);
  	dumengong = p.getMenGong(QiMen.MENDU);

		wutpgong = p.getTianGong(YiJing.WUG, 0);	
		wudpgong = p.getDiGong(YiJing.WUG, 0);
  	dingtpgong = p.getTianGong(YiJing.DING, 0);
  	dingdpgong = p.getDiGong(YiJing.DING, 0);
  	bingtpgong = p.getTianGong(YiJing.BING, 0);
  	bingdpgong = p.getDiGong(YiJing.BING, 0);
  	yitpgong = p.getTianGong(YiJing.YI, 0);
  	yidpgong = p.getDiGong(YiJing.YI, 0);
  	gengtpgong = p.getTianGong(YiJing.GENG, 0);
  	gengdpgong = p.getDiGong(YiJing.GENG, 0);
  	jitpgong = p.getTianGong(YiJing.JI, 0);
  	jidpgong = p.getDiGong(YiJing.JI, 0);
  	xintpgong = p.getTianGong(YiJing.XIN, 0);
  	xindpgong = p.getDiGong(YiJing.XIN, 0);
  	rentpgong = p.getTianGong(YiJing.REN, 0);
  	rendpgong = p.getDiGong(YiJing.REN, 0);
  	guitpgong = p.getTianGong(YiJing.GUI, 0);
  	guidpgong = p.getDiGong(YiJing.GUI, 0);
  		
  	shenwugong = p.getShenGong(QiMen.SHENWU);
  	shenhegong = p.getShenGong(QiMen.SHENHE);
  	shenshegong = p.getShenGong(QiMen.SHENSHE);
  	shenyingong = p.getShenGong(QiMen.SHENYIN);
  	shenhugong = p.getShenGong(QiMen.SHENHU);
  	shendigong = p.getShenGong(QiMen.SHENDI);
  	shentiangong = p.getShenGong(QiMen.SHENTIAN);
  	
  	xingpenggong = p.getXingGong(QiMen.XINGPENG); 
  	xingchonggong = p.getXingGong(QiMen.XINGCHONG);
  	xingfugong = p.getXingGong(QiMen.XINGFU);
  	xingyinggong = p.getXingGong(QiMen.XINGYING);
  	xingruigong = p.getXingGong(QiMen.XINGRUI);
  	xingzhugong = p.getXingGong(QiMen.XINGZHU);
  	xingxingong = p.getXingGong(QiMen.XINGXIN);
  	xingqingong = p.getXingGong(QiMen.XINGQIN);
  	xingrengong = p.getXingGong(QiMen.XINGREN);
  	
  	caixing = daoqm.gInt[2][1][shengmengong];
  	
  	isMenfu = p.isMenFu(); //八门伏呤
		isMenfan = p.isMenFan(); //八门反呤
		isXingfu = p.isXingFu(); //九星伏呤
		isXingfan = p.isXingFan();  //九星反呤
	}
	/**
	 * 写入缓充区，
	 * @param istrue: 如果为真，则写入字符串
	 * @param hasKong : 是否加空格，如果加则4个
	 */
	protected void w(boolean istrue, String s) {
  	w(istrue,s,false);
  }
	protected void w(boolean istrue, String s,boolean hasKong) {
  	if(istrue) w(s, hasKong);
  }
	protected void w(String s,boolean hasKong) {
  	my[index++] = (hasKong?"    ":"")+s;
  }
	protected void w(String s) {
  	w(s,false);
  }
	/**
	 * 得到星1和星2之间的生、克、被生、被克、比和之间的关系及描述
	 * @param x1
	 * @param x2
	 * @param rs
	 * @return
	 */
  String getXingRel(String name1, String name2, int x1, int x2, String[] rs) {
		String res = null;
		//String name1 = "["+QiMen.jx1[x1]+"]";
		//String name2 = "["+QiMen.jx1[x2]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"生"+name2+"，"+rs[0]+"；";
		else if(YiJing.WXDANKE[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"克"+name2+"，"+rs[1]+"；";
		else if(YiJing.WXDANSHENG[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"生"+name1+"，"+rs[2]+"；";
		else if(YiJing.WXDANKE[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"克"+name1+"，"+rs[3]+"；";
		else  res=name1+"与"+name2+"比和，"+rs[4]+"；";
		
		return res;
	}
  /**
   * 得到某名落g1与g2之间的生、冲克、被生、被冲克、同宫、比和之间的关系描述
   * @param g1
   * @param name1
   * @param g2
   * @param name2
   * @param rs
   * @return
   */
  String getGongRel(int g1, String name1, int g2, String name2, String[] rs) {
		String t = null;
		if(p.isSheng(g1, g2)) t=name1+"落"+g1+"宫生"+name2+"所落"+g2+"宫，"+rs[0]+"；";
		else if(p.isChongke(g1, g2)) t=name1+"落"+g1+"宫冲克"+name2+"所落"+g2+"宫，"+rs[1]+"；";
		else if(p.isSheng(g2,g1)) t=name2+"落"+g2+"宫生"+name1+"所落"+g1+"宫，"+rs[2]+"；";
		else if(p.isChongke(g2,g1)) t=name2+"落"+g2+"宫冲克"+name1+"所落"+g1+"宫，"+rs[3]+"；";
		else if(g1==g2) t=name1+"与"+name2+"同落"+g2+"宫，"+rs[4]+"；";
		else if(p.isBihe(g1,g2)) t=name1+"落"+g1+"宫与"+name2+"所落"+g2+"宫比和，"+rs[5]+"；";
		return t;
	}
  /**
   * 得到宫之间生、克、被生、被克、比和之间的关系描述
   * @param g1
   * @param g2
   * @param rs
   * @return
   */
  String getGongRel(int g1, int g2, String[] rs) {
		String res = null;
		String name1 = "["+QiMen.dpjg[g1]+"]";
		String name2 = "["+QiMen.dpjg[g2]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jgwh[g1]][QiMen.jgwh[g2]]!=0) res=name1+"生"+name2+"，"+rs[0]+"；";
		else if(YiJing.WXDANKE[QiMen.jgwh[g1]][QiMen.jgwh[g2]]!=0) res=name1+"克"+name2+"，"+rs[1]+"；";
		else if(YiJing.WXDANSHENG[QiMen.jgwh[g2]][QiMen.jgwh[g1]]!=0) res=name2+"生"+name1+"，"+rs[2]+"；";
		else if(YiJing.WXDANKE[QiMen.jgwh[g2]][QiMen.jgwh[g1]]!=0) res=name2+"克"+name1+"，"+rs[3]+"；";
		else  res=name1+"与"+name2+"比和，"+rs[4]+"；";
		
		return res;
	}
}