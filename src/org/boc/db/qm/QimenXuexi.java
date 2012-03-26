package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.dao.sz.DaoSiZhuMain;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenXuexi {
	private DaoSiZhuMain daosz;  //四柱的dao
	private DaoQiMen daoqm;
	private String mingzhu;  //为命主信息，形式如1997;1998;或者1,1;2,2;
	private int yongshen;    //用神序号，年干1，月干2，日干3，时干4
	private QimenPublic my; 
	private String[] xuexi ; //保存学习宫所有的描述情况
	int index = 0; //计数器，为xuexi所用

	public QimenXuexi(DaoQiMen daoqm, QimenPublic my, DaoSiZhuMain daosz) {
  	this.daoqm = daoqm;
  	this.my = my;
  	this.daosz = daosz; 
  }
  
	public String getXuexi(StringBuffer str,String mingzhu, int yongshen) {
		this.mingzhu = mingzhu;
		this.yongshen = yongshen; 		
		//Messages.info(mingzhu+"; 用神为："+yongshen);
		xuexi = new String[100];
		index = 0;
		
		getXuexi1();
		
		//以前台传递的用神推算
		getXuexi2(yongshen,0,0);
		getXuexi3(yongshen,0,0);
		getXuexi4(yongshen,0,0);
		
		//兼看年命		
		if(mingzhu!=null && !"".equals(mingzhu.trim())) {
			xuexi[index++]=my.NOKG+"****************************兼看年命****************************";
			int[] mzGanzi = my.getMZhu(mingzhu);
			for(int j = 0; j<mzGanzi.length; j=j+2){
				xuexi[index++]=my.NOKG+"----------------------------年命["+YiJing.TIANGANNAME[mzGanzi[j]]+YiJing.DIZINAME[mzGanzi[j+1]]+"]--------------------------";
				getXuexi2(0,mzGanzi[j],mzGanzi[j+1]);
				getXuexi3(0,mzGanzi[j],mzGanzi[j+1]);
				getXuexi4(0,mzGanzi[j],mzGanzi[j+1]);
			}
		}
		
		return my.format(str,xuexi);
	}
	
	/**
	 * 一、取用神：
	 */
	public void getXuexi1() {
		xuexi[index++]=my.NOKG+"一、取用神：";
		xuexi[index++]="考生本人求测，则以日干为考生本人；";
		xuexi[index++]="父母求测子女的考试情况，以时干为考生；";
		xuexi[index++]="兼看考生的年命，也可以年命为用神";
		xuexi[index++]="天辅星为考试院；";
		xuexi[index++]="值符为主考或监考官，值使为副主考官或副监考官；";
		xuexi[index++]="丁奇代表文章，文科；";
		xuexi[index++]="景门代表试卷、学校、录取通知书、理科；";
		xuexi[index++]="年干代表录取的学校；";
		xuexi[index++]="时干主事体；";
	}
	
	/**
	 * 二、大格局断：
	 * @param type：要判断大格局的用神, 4为年年柱，3为月柱，2为日柱，1为时柱,0则取gan与zi的值
	 * @param gan,zi: 有些用神要取年命，或直接指定干的，扩展之用；
	 * 如以日干为用神作推断，则传日柱；月干为用神，传月柱；时干为用神传时柱；
	 */
	public void getXuexi2(int type, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(type, gan, zi);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		int gong = Integer.valueOf(ysinfo[1]);  //用神落宫
		gan = Integer.valueOf(ysinfo[2]);       //用神的天干
		zi = Integer.valueOf(ysinfo[3]);        //用神的地支
		
		String sige = my.getSige(1)!=0?"岁格":my.getSige(2)!=0?"月格":my.getSige(3)!=0?"日格":my.getSige(4)!=0?"时格":null; //得到年月日时格落宫
		int rgGong = my.getTianGong(SiZhu.rg, SiZhu.rz); //得到日干宫
		int nianGong = my.getTianGong(SiZhu.ng, SiZhu.nz); //年干宫		
		int jing3Gong = my.getMenGong(QiMen.MENJING3);   //得到景门落宫
		int[] jing3Dpjy = my.getDpjy(jing3Gong);  //得到景门落宫的地盘奇仪
		int fuGong = my.getXingGong(QiMen.XINGFU); //得到天辅星落宫				
		
		String[] jxge = my.getJixiongge(gong);  //得到该宫的吉凶格
		String[] ganwx = my.gettgWS(gan, zi);   //得到该干落宫旺衰情况
		int[] dpjy = my.getDpjy(gong);   	//得到用神宫的地盘奇仪
		int dingGong = my.getTianGong(YiJing.DING, 0); //得到丁奇落宫
		int[] dingDpjy = my.getDpjy(dingGong);  //得到丁奇落宫的地盘奇仪
		String dingSMJ = my.isTGanSMJ(YiJing.DING, 0); //丁奇是否落宫死墓绝
		String[] dingJX = my.getJixiongge(dingGong); //判断丁奇落宫吉凶格
		 
		String[] jing3WS = my.getmenWS(jing3Gong); //景门落宫旺衰
		String[] jing3JX = my.getJixiongge(jing3Gong); //判断景门落宫吉凶格
		
		xuexi[index++]=my.NOKG+"二、大格局断（"+yshen+"）：";
		if(my.isMenFan()) xuexi[index++]="八门反吟，考试肯定不顺；";
		if(my.isXingFan()) xuexi[index++]="九星反吟，考试肯定不顺；";
		if(jxge[0].equals("1")) xuexi[index++]="用神落宫得吉格，大利考试录取；"+jxge[1];
		if(my.isKong(gong, my.SHIKONGWANG)) xuexi[index++]="用神落宫空亡，必定考不上；";
		
		if(ganwx[0].equals("1")) xuexi[index++]="用神落宫旺相，主自身状态比较好；"+ganwx[1];
		String smj = my.isTGanSMJ(gan, zi); //用神是否死墓绝
		if(smj!=null) xuexi[index++]="用神落宫处"+smj+"地，精神状态不好；";
		
		if(my.isSheng(nianGong, jing3Gong)) xuexi[index++]="年干落"+nianGong+"宫生景门"+jing3Gong+"宫，为太岁生文章必是第一名；";
		if(my.isKong(fuGong, my.SHIKONGWANG)) xuexi[index++]="天辅星空亡，主没有文凭；";
		if(my.isChongke(gong, fuGong)) xuexi[index++]="用神落"+gong+"宫冲克天辅星"+fuGong+"宫，证明不爱学习；";
		if(my.getSige(1)!=0)
		if(sige!=null) xuexi[index++]=sige+"：凡考试遇年月日时格，主评分统计或录取人员作崇；；";
		if(dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG) xuexi[index++]="用神加地盘庚，主换地盘，考学上主换专业；";		
		
		int duGong = my.getMenGong(QiMen.MENDU); //杜门落宫
		int tyGong = my.getXingGong(QiMen.SHENYIN); //太阴落宫
		if(duGong==tyGong) xuexi[index++]="杜门加太阴：有人暗中阻挠；";
		
		if(dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG)	
			xuexi[index++]="用神下临庚，必有阻力，庚如为年月日时中的天干，必主该六亲；";
		
		if(my.isChongke(gong, duGong)) xuexi[index++]="用神克杜门宫，不喜欢技术性的工作；";
		if(my.isChongke(gong, jing3Gong)) xuexi[index++]="用神克景门宫，不喜欢理工科等技术性强的工作；";
		if(gong==duGong) xuexi[index++]="用神临杜门，喜欢技术性的工作；";
		if(gong==jing3Gong) xuexi[index++]="用神临景门，喜欢理工科等技术性强的工作；";
		
		int shengGong = my.getMenGong(QiMen.MENSHENG); //临生门宫
		if(gong==shengGong) xuexi[index++]="用神临生门，喜欢做生意；";
		int xiuGong = my.getMenGong(QiMen.MENXIU);  //临休门
		if(gong==xiuGong) xuexi[index++]="用神临休门，喜欢社交能力、行政管理方面的工作；";
		int siGong = my.getMenGong(QiMen.MENSI); //临死门
		if(gong==siGong) xuexi[index++]="用神临死门，精神状态不好；";		
		
		int heGong = my.getShenGong(QiMen.SHENHE); //六合落宫
		int sheGong = my.getShenGong(QiMen.SHENSHE); //藤蛇落宫
		if(gong == heGong) xuexi[index++]="用神临六合，主脾气随和；";
		if(gong == sheGong) xuexi[index++]="用神临腾蛇，主精神压力大；";
	}

	
	/**
	 * 三、成绩优劣分析：
	 * @param type：要判断大格局的用神, 4为年年柱，3为月柱，2为日柱，1为时柱,0则取gan与zi的值
	 * @param gan,zi: 有些用神要取年命，或直接指定干的，扩展之用；
	 * 如以日干为用神作推断，则传日柱；月干为用神，传月柱；时干为用神传时柱；
	 */
	public void getXuexi3(int type, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(type, gan, zi);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		int gong = Integer.valueOf(ysinfo[1]);  //用神落宫
		gan = Integer.valueOf(ysinfo[2]);       //用神的天干
		zi = Integer.valueOf(ysinfo[3]);        //用神的地支

		xuexi[index++]=my.NOKG+"三、成绩优劣分析（"+yshen+"）：";
		String[] ysGanhj = my.isganHeju(gan,zi);
		if(ysGanhj[0].equals("1")) xuexi[index++]="用神落宫合局，成绩较好；"+ysGanhj[1];
		else if(ysGanhj[0].equals("0")) xuexi[index++]="用神落宫失局稍有气，成绩一般；"+ysGanhj[1];
		else xuexi[index++]="用神落宫完全失局无气，成绩糟糕；"+ysGanhj[1];
		
		String[] dingHj = my.isganHeju(YiJing.DING,0);
		int dingGong = my.getTianGong(YiJing.DING, 0); //得到丁奇落宫
		int[] dingDpjy = my.getDpjy(dingGong);  //得到丁奇落宫的地盘奇仪
		if(dingHj[0].equals("1")) xuexi[index++]="丁奇为文章，落宫合局，成绩较好；"+dingHj[1];
		else if(dingHj[0].equals("0")) xuexi[index++]="丁奇为文章，落宫失局稍有气，成绩一般；"+dingHj[1];
		else xuexi[index++]="丁奇为文章，落宫完全失局无气，成绩糟糕；"+dingHj[1];
		if(dingDpjy[0]==YiJing.GUI || dingDpjy[1]==YiJing.GUI ||
				dingDpjy[0]==YiJing.GENG || dingDpjy[1]==YiJing.GENG) xuexi[index++]="丁为文科，丁加癸、丁加庚为空题多，且卷面不整洁；";
//		if(my.isKong(dingGong, my.SHIKONGWANG)) xuexi[index++]="丁为文科，落宫空亡，成绩不好；";
//		if(dingSMJ!=null) xuexi[index++]="丁为文科，落宫处"+dingSMJ+"地，成绩不理想；";
//		if(dingJX[0].equals("0")) xuexi[index++]="丁为文科，落宫凶格，成绩不理想；"+dingJX[1];
		
		int jing3Gong = my.getMenGong(QiMen.MENJING3);
		String[] jing3Hj = my.ismenHeju(jing3Gong);
		if(jing3Hj[0].equals("1")) xuexi[index++]="景门为考卷，落宫合局，成绩较好；"+jing3Hj[1];
		else if(jing3Hj[0].equals("0")) xuexi[index++]="景门为考卷，落宫失局稍有气，成绩一般；"+jing3Hj[1];
		else xuexi[index++]="景门为考卷，落宫完全失局无气，成绩糟糕；"+jing3Hj[1];
//		if(my.isKong(jing3Gong, my.SHIKONGWANG)) xuexi[index++]="景门为理科，落宫空亡，成绩不好；";
//		if(jing3WS[0].equals("-1")) xuexi[index++]="景门为理科，落宫休囚无气，成绩不理想；"+jing3WS[1];
//		if(jing3JX[0].equals("0")) xuexi[index++]="景门为理科，落宫凶格，成绩不理想；"+jing3JX[1];
		
		if(dingGong==gong) xuexi[index++]="丁奇与用神同宫，文科成绩好；";
		else if(my.isSheng(dingGong, gong)) xuexi[index++]="丁奇落宫生用神宫，文科成绩好；";
		else if(my.isSheng(gong,dingGong)) xuexi[index++]="用神宫生丁奇落宫，文科成绩好；";
		else if(my.isBihe(gong,dingGong)) xuexi[index++]="用神宫与丁奇落宫相比和，文科成绩还可以；";
		else if(my.isChongke(gong,dingGong)||my.isChongke(dingGong,gong)) xuexi[index++]="用神宫与丁奇落宫相冲克，文科成绩不理想；";
		
		if(jing3Gong==gong) xuexi[index++]="景门与用神同宫，理科成绩好；";
		else if(my.isSheng(jing3Gong, gong)) xuexi[index++]="景门落宫生用神宫，理科成绩好；";
		else if(my.isSheng(gong,jing3Gong)) xuexi[index++]="用神宫生景门落宫，理科成绩好；";
		else if(my.isBihe(gong,jing3Gong)) xuexi[index++]="用神宫与景门落宫相比和，理科成绩还可以；";
		else if(my.isChongke(gong,jing3Gong)||my.isChongke(jing3Gong,gong)) xuexi[index++]="用神宫与景门落宫相冲克，理科成绩不理想；";
		
		String gongshu = my.getGongshu(gong);
		if(ysGanhj[0].equals("1")) xuexi[index++]="用神落宫合局，"+gongshu+"可断90%以上；";
		else if(ysGanhj[0].equals("0")) xuexi[index++]="用神落宫失局稍有气，"+gongshu+"可断60%左右；";
		else xuexi[index++]="用神落宫完全失局无气，"+my.getGongshu(gong)+"可断40%以下；";
		if(my.isKong(gong, my.SHIKONGWANG)) xuexi[index++]="又用神落宫空亡，"+gongshu+"分数应减半；";
		
		int wuGong = my.getTianGong(YiJing.WUG, 0);
		String wugongshu = my.getGongshu(wuGong);
		xuexi[index++]="上学费用看天盘戊落宫，"+wugongshu;
	}
	
	/**
	 * 四、录取学校及专业分析：
	 * @param type：要判断大格局的用神, 4为年年柱，3为月柱，2为日柱，1为时柱,0则取gan与zi的值
	 * @param gan,zi: 有些用神要取年命，或直接指定干的，扩展之用；
	 * 如以日干为用神作推断，则传日柱；月干为用神，传月柱；时干为用神传时柱；
	 */
	public void getXuexi4(int type, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(type, gan, zi);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		int gong = Integer.valueOf(ysinfo[1]);  //用神落宫
		gan = Integer.valueOf(ysinfo[2]);       //用神的天干
		zi = Integer.valueOf(ysinfo[3]);        //用神的地支
		
		int yearGong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //得到年干落宫
		int fuGong = my.getXingGong(QiMen.XINGFU);  //得到天辅星落宫
		int ruiGong = my.getXingGong(QiMen.XINGRUI);  //得到天芮星落宫
		int zfGong = daoqm.getZhifuGong(SiZhu.sg, SiZhu.sz); //得到值符落宫
    int zsGong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);  //得到值使落宫
    
    xuexi[index++]=my.NOKG+"四、录取学校及专业分析（"+yshen+"）：";
    
    if(my.isSheng(yearGong, gong)) xuexi[index++]="年干落宫生用神宫，一定被学校录取；";
    else if(my.isBihe(yearGong, gong)) xuexi[index++]="年干落宫与用神宫比和，有被学校录取之象；";
    else if(yearGong==gong) xuexi[index++]="年干落宫与用神同宫，被学校录取之象；";
    else if(my.isSheng(gong,yearGong)) xuexi[index++]="用神宫生年干落宫，表示希望被学校录取的主观愿望；";
    else xuexi[index++]="用神宫与年干落宫相冲克，不会被学校录取；";
    
    if(my.isSheng(fuGong, gong)) xuexi[index++]="天辅星落宫生用神宫，一定被学校录取；";
    else if(my.isBihe(fuGong, gong)) xuexi[index++]="天辅星落宫与用神宫比和，有被学校录取之象；";
    else if(fuGong==gong) xuexi[index++]="天辅星落宫与用神同宫，被学校录取之象；";
    else if(my.isSheng(gong,fuGong)) xuexi[index++]="用神宫生天辅星落宫，表示希望被学校录取的主观愿望；";
    else xuexi[index++]="用神宫与天辅星落宫相冲克，不会被学校录取；";
    
    if(my.isSheng(zfGong, gong)) xuexi[index++]="值符落宫生用神宫，一定被学校录取；";
    else if(my.isBihe(zfGong, gong)) xuexi[index++]="值符落宫与用神宫比和，有被学校录取之象；";
    else if(zfGong==gong) xuexi[index++]="值符落宫与用神同宫，被学校录取之象；";
    else if(my.isSheng(gong,zfGong)) xuexi[index++]="用神宫生值符落宫，表示希望被学校录取的主观愿望；";
    else xuexi[index++]="用神宫与值符落宫相冲克，不会被学校录取；";
    
    if(my.isSheng(fuGong, ruiGong)) xuexi[index++]="天辅星落宫生天芮星落宫，又表示可以被学校录取；";
    else if(my.isBihe(fuGong, ruiGong)) xuexi[index++]="天辅星落宫与天芮星落宫比和，又表示可以被学校录取之象；";
    else if(fuGong==ruiGong) xuexi[index++]="天辅星落宫与天芮星同宫，又表示可以被学校录取之象；";
    else if(my.isSheng(ruiGong,fuGong)) xuexi[index++]="天芮星落宫生天辅星落宫，又表示希望被学校录取的主观愿望；";
    else xuexi[index++]="天芮星落宫与天辅星落宫相冲克，又表示不会被学校录取；";
    
    String fxiang = QiMen.JIUGONGFXIANG[yearGong]; //得到年干宫的方位
    boolean isnei = my.isNenpan(yearGong);
    String neiwai = my.isNenpan(yearGong)?"省内":"省外";   //判断年干宫是内盘还是外盘
    xuexi[index++]="年干落"+yearGong+"宫，又在"+(isnei?"内盘":"外盘")+"，录取学校在居住地"+neiwai+fxiang+"；";
    
    int yearGongMen = daoqm.gInt[3][1][yearGong]; //得到年干落宫的门
    String[] BAMENJUANYE = {"","为婚姻家庭类相关专业","为地理、物理、生物医学类专业",
    		"为搏击竞争类专业","为技术类专业",
    		"",
    		"为政治管理类专业","为艺术类专业","为农业土地类专业","为生活娱乐类专业"};
    xuexi[index++]="年干落"+yearGong+"宫临"+QiMen.bm1[yearGongMen]+"门，"+BAMENJUANYE[yearGongMen]+"；";
	}
	


}