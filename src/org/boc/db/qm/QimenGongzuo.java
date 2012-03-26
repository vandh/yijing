package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenGongzuo {
	/**
	 * PRIVATE: 基本论断，即只输出与用神相关的工作描述，如用神克开门，用神临凶星等；
	 * PUBLIC：输出与用神无关的恋爱或婚姻描述，如年开门克年干、时干克日干等；
	 */
	private static final int PRIVATE = 1,PUBLIC=2; 
	private DaoQiMen daoqm;
	private QimenPublic my; 
	private String mingzhu;
	private int yongshen; 
	private boolean boy; 
	private String[] swork ; //保存命运宫所有的描述情况
	int index = 0; //计数器，为descMY所用

	public QimenGongzuo(DaoQiMen daoqm, QimenPublic my) {
  	this.daoqm = daoqm;
  	this.my = my;
  }
  
	public String getWork(StringBuffer str,String mingzhu, int yongshen,boolean boy) {
		swork = new String[500];
		index = 0;
		this.mingzhu = mingzhu;
		this.yongshen = yongshen;
		this.boy = boy;		
		
		swork[index++]=my.NOKG+"一、取用神：";
		getWork1();
		swork[index++]=my.NEWLINE;
		
		String[] ysinfo = my.getYShenInfo(yongshen, 0, 0);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		swork[index++]=my.NOKG+"二、大格局断["+yshen+"]：";
		getWork2();
		swork[index++]=my.NEWLINE;
		
		swork[index++]=my.NOKG+"三、求官、升职、就业、退伍、退休详断["+yshen+"]：";
		getWork3(PRIVATE, yongshen,0,0);  //用神有关的
		getWork3(PUBLIC, yongshen,0,0);   //公共的不包括用神的
		swork[index++]=my.NEWLINE;
		
		swork[index++]=my.NOKG+"四、方向日期断["+yshen+"]：";
		getWork4(PRIVATE, yongshen,0,0);
		getWork4(PUBLIC, yongshen,0,0);
		swork[index++]=my.NEWLINE;
		
		//用神的年命参考，且只需要与用神相关的信息即PRIVATE部分
		int[] mzhu = my.getMZhu(mingzhu);
		if(mzhu.length>1 && mzhu[0] * mzhu[1]!=0) {
			swork[index++]=my.NOKG+"五、年命参考["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]";
			swork[index++]="------------------------------详细诊断参考------------------------------";
			getWork3(PRIVATE, 0, mzhu[0],mzhu[1]);
			swork[index++]=my.NEWLINE;
			
			swork[index++]="------------------------------工作方向参考------------------------------";
			getWork4(PRIVATE, 0, mzhu[0],mzhu[1]);
			swork[index++]=my.NEWLINE;
		}
		
		return my.format(str, swork);
	}
	
	/**
	 * 一、取用神
	 */
	public void getWork1() {
		swork[index++]="年干为上级，高级评委、上级批准机关；";
		swork[index++]="月干为同事；";
		swork[index++]="日干为求测者；";
		swork[index++]="时干为下级或群众、事体、求测人配偶、子女、开会人数；";
		swork[index++]="开门为工作、单位、官星、法院、法官、开会；";
		swork[index++]="杜门为武职工作及单位、军队干部或士兵转业退伍的官长；";
		swork[index++]="值符为顶头上司、监考、裁判、评委；";
		swork[index++]="值使门也主事体、考官；";
		swork[index++]="丁为调令、退休手续；";
		swork[index++]="兼看年命；"; 
	}
	/**
	 * 二、大格局断
	 */
	public void getWork2() {		
		if(my.isMenFu()) swork[index++]="八门伏吟，利主不利客；主痛苦、忧伤、时间长；主伏而不动维持原状；测工作辞职辞不了；";
		if(my.isMenFan()) swork[index++]="八门反呤，利客不利主；主事情反复；主事情成败易分，速度快；主调任、工作变动；测工作调动必成；";
		if(my.isXingFu()) swork[index++]="九星伏呤，利主不利客；主痛苦、忧伤、时间长；主伏而不动维持原状；测工作辞职辞不了；";
		if(my.isXingFan()) swork[index++]="九星反呤，利客不利主；主事情反复；主事情成败易分，速度快；主调任、工作变动；测工作调动必成；";
	}
	/**
	 * 三、求官、升职、就业、退伍、退休详断
	 * gInt[1][1][1-9]=八神,gInt[1][2][1-9]=八神五行
   * gInt[2][1][1-9]=九星顺序数，2九星五行,3天盘奇仪，天干五行，三奇六仪藏支
   * gInt[3][1][1-9]=宫门序数,gInt[3][2]=门五行
   * gInt[4][1][1-9]=九宫后天八卦数，2八卦五行，3地盘九星，4地盘八门，5地盘奇仪，6地盘奇仪五行、7所藏六甲地支
	 */
	public void getWork3(int all, int ystype, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		int ysgan = Integer.valueOf(ysinfo[2]);       //用神的天干
		int yszi = Integer.valueOf(ysinfo[3]);        //用神的地支
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		int gong = Integer.valueOf(ysinfo[1]);  //用神落宫
		int[] tpjy = my.getTpjy(gong);
		int[] dpjy = my.getDpjy(gong);
		int ngtpgong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //年柱天干天盘落宫
		int ngdpgong = my.getDiGong(SiZhu.ng, SiZhu.nz);  //年柱天干地盘落宫
		int kaimengong = my.getMenGong(QiMen.MENKAI);  //开门落宫			
		int shigangong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //时干落宫
		int zhifugong = daoqm.getZhifuGong();
		int yuegong = my.getTianGong(SiZhu.yg, SiZhu.yz);
		
		/** 1. 用神所临断 */
		String[] ysheju = my.isganHeju(ysgan, 0);
		String[] ysjxge = my.getJixiongge(gong); //吉凶格
		String[] ysxing = my.getxingJX(gong);  //星的吉凶
		String[] ysshen = my.getshenJX(gong);  //神的吉凶		
		String t = null;
		if(all==PRIVATE) {
			if(ysheju[0].equals("1")) {
				t="用神落"+gong+"宫合局，自身状态比较好；";
				if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG && ysjxge[0].equals("1"))
					t+="又临生门，吉格，为有权有势之人；"+ysjxge[1];
				swork[index++] = t+ysheju[1];
			}else{
				swork[index++] = "用神落"+gong+"宫失局，自身状态差，很难得到官职和工作；"+ysheju[1];
			}
			if(ysxing[0].equals("1") && ysshen[0].equals("1")) swork[index++] = "用神临吉星吉神，主自身运气不错，有贵人相助；";
			
			if(gong==zhifugong) {
				if(gong==1 || gong==3 || gong==7 || gong==9)
					t = "用神落"+gong+"宫，上临值符，为有正职的领导或公司负责人；";
				else if(gong==6)
					t = "用神落"+gong+"宫，上临值符，为某单位领导或负责人；";
				else
					t = "用神落"+gong+"宫，上临值符，可能为副职的领导或公司负责人；";
				String[] zhifuhj = my.isganHeju(gong);
				if(zhifuhj[0].equals("1")) t +="宫中合局，应为有权有势之人；"+zhifuhj[1];
				else t +="宫中失局，官阶不大，应为科长主任之类；"+zhifuhj[1];
				if(t!=null) swork[index++] = t;
			}
			
			if(gong==my.getMenGong(QiMen.MENXIU)) {
				t = "用神临休门，主公门人、或清闲在家；";
				if(my.getMenGong(QiMen.MENKAI)==6) t+="开门临乾宫，应为公职；";
				if(my.isKong(6)) t+="但开门旬空，说明已辞职；";
				swork[index++] = t;
			}
			
			if(isThisGeju(gong,56))	swork[index++] = "用神逢青龙逃走，必退伍或退休，或要离职；"+QiMen.gGejuDesc[56][1];
			if(isThisGeju(gong,65))	swork[index++] = "用神逢荧入太白，必退伍或退休，或要离职；"+QiMen.gGejuDesc[65][1];
			if(isThisGeju(gong,101))	swork[index++] = "用神逢太白入荧，不准退伍或退休、离职；"+QiMen.gGejuDesc[101][1];
			if(isThisGeju(gong,110))	swork[index++] = "用神逢白虎猖狂，不准退伍或退休、离职；"+QiMen.gGejuDesc[110][1];
			if(isThisGeju(gong,132))	swork[index++] = "用神逢螣蛇夭矫，可能有官司缠身，退伍或退休离职，欲退不能；"+QiMen.gGejuDesc[132][1];
			if(isThisGeju(gong,108))	swork[index++] = "用神逢大格，可能会被革职，或被公司炒鱿鱼；"+QiMen.gGejuDesc[108][1];
			if(isThisGeju(gong,78))	swork[index++] = "用神朱雀投江，可能会被革职，或被公司炒鱿鱼；"+QiMen.gGejuDesc[142][1];
			
			if(daoqm.gInt[3][1][gong]==QiMen.MENJING1) swork[index++] = "用神临惊门，主官司口舌；";
			if(daoqm.gInt[3][1][gong]==QiMen.MENSHANG) swork[index++] = "用神临伤门，说明有竞争能力；";
			if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG) swork[index++] = "用神临生门，说明与财利有关；";
			if(daoqm.gInt[3][1][gong]==QiMen.MENDU) swork[index++] = "用神临杜门主闭塞；";
			if(daoqm.gInt[3][1][gong]==QiMen.MENSI) swork[index++] = "用神临死门主生气；";
			if(daoqm.gInt[3][1][gong]==QiMen.MENJING3 && daoqm.gInt[1][1][gong]==QiMen.SHENHU) {
				t = "景门主有大学文凭，白虎主有文化，现景门加白虎，应为大字生；";
				if(QiMen.men_gong[QiMen.MENJING3][gong].equals(QiMen.zphym1)) t+="但景门落宫受制，主理科成绩不好；";
				if(my.isSheng(gong, my.getTianGong(YiJing.DING, 0))) t+="又生丁奇，无疑是学文科的；";
				swork[index++] = t;
			}

			if(daoqm.gInt[2][1][gong]==QiMen.XINGFU) swork[index++] = "用神临天辅星，说明此人能干，有文化；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGCHONG) swork[index++] = "用神临天冲，干事爽快利落；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGREN) swork[index++] = "用神临天任星，慈祥；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGXIN) swork[index++] = "用神临天心星，正直；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGQIN) swork[index++] = "用神临天禽星，忠厚；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGYING) swork[index++] = "用神临天英星，昏烈；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGRUI) swork[index++] = "用神临天芮星，贪毒；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGZHU) swork[index++] = "用神临天柱星，奸诈；";
			if(daoqm.gInt[2][1][gong]==QiMen.XINGPENG) swork[index++] = "用神临天蓬星，大奸大恶之人；";
			
			if(daoqm.gInt[1][1][gong]==QiMen.SHENTIAN) swork[index++] = "用神上乘九天，主好高骛远，也主变动之象；";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENHU) swork[index++] = "用神上乘白虎，有命岸牵连；";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENSHE) swork[index++] = "用神上乘腾蛇，主身材瘦长，琐事缠绕，也主调动之象；";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENDI) swork[index++] = "用神上乘九地，主不动之象；";
			
			int sigong = my.getMenGong(QiMen.MENSI);
			String[] simenheju = my.ismenHeju(sigong);
			if(my.isChongke(sigong, gong)) {
				t = "死门落"+sigong+"宫冲克用神落"+gong+"宫，表明工会遇到困难和阻力；"; 
				if(!simenheju[0].equals("1")) t+="但死门落宫失局，主问题可以解决；"+simenheju[1];
				swork[index++] = t;
			}
			
			int shengmengong = my.getMenGong(QiMen.MENSHENG);		
			int wutgong = my.getTianGong(YiJing.WUG, 0);
			int wudgong = my.getDiGong(YiJing.WUG, 0);
			String[] shengmenwx = my.getmenWS(shengmengong);
			String[] caixingwx = my.getxingWS(shengmengong);
			if(wutgong==gong || wudgong==gong) {
				if(!shengmenwx[0].equals("1") || !caixingwx[0].equals("1")) 
					swork[index++] ="用神与戊同落"+gong+"宫，但生门或财星不旺，主口袋中钱不多；";
			}			
			if(shengmengong==gong && (wutgong==gong || wudgong==gong)) {
				swork[index++] = "用神落"+gong+"宫，临生门，又临戊，搞财务工作的；";
			}
		}else{			
			int[] ktpjy = my.getTpjy(kaimengong);
			int[] kdpjy = my.getDpjy(kaimengong);
			t=null;
			String[] kheju = my.isganHeju(ktpjy[0], 0);
			if(ktpjy[0]==YiJing.GENG || ktpjy[1]==YiJing.GENG) t="开门落"+kaimengong+"宫临天盘庚，证明是大单位，也代表工作不顺利；";
			else if(kdpjy[0]==YiJing.GENG || kdpjy[1]==YiJing.GENG) t="开门落"+kaimengong+"宫临地盘庚，证明是大单位，也代表工作不顺利；";
			if(t!=null && kheju[0].equals("1")) t+="又本宫合局，表明单位效益好；"+kheju[1];
			else if(t!=null) t+="但本宫失局，表明单位效益不好；"+kheju[1];				
			if(t!=null) swork[index++] = t;
		}
		
		/** 2. 是否工作变动 */
		if(all==PRIVATE) {
			if(my.isTChong(gong)) swork[index++] = "用神落"+gong+"宫天盘干与宫相冲，主工作变动之象；";
			if(my.isTDChong(gong)) swork[index++] = "用神落"+gong+"宫，天地盘相冲，主工作变动之象；";
			if(my.isChongke(kaimengong, gong)) swork[index++] = "开门落"+kaimengong+"宫冲克用神"+gong+"宫，主工作调动；测招聘也主不被单位录取；测退休则主能退成";
			if(my.isTDGanHe(gong)) swork[index++] = "用神"+gong+"宫中天盘与地盘干相合，也主调动不成之象；";
			if(my.isKong(gong)) swork[index++] = "用神落"+gong+"宫空亡，恐有革职之忧，也主工作调动等事不成之象；"; 
			boolean tgmu = my.isTGanMu(ysgan,0);
			boolean dgmu = my.isDGanMu(ysgan,0);
			if(tgmu) swork[index++]="用神落"+gong+"宫天盘干处墓地，不但降罚，还会招来罪责；";
			if(dgmu) swork[index++]="用神地盘干落"+my.getDiGong(ysgan, 0)+"宫处墓地，不但降罚，还会招来罪责；";
			if(my.getDiGong(ysgan, 0)==daoqm.getZhifuGong()) {
				t = "用神地盘干主过去状态，现落"+my.getDiGong(ysgan, 0)+"宫上乘值符，表明得领导支持；";
				if(my.isTDGanHe(my.getDiGong(ysgan, 0))) t+="又与上临天干相合，更说明与领导关系不错；";
				swork[index++] = t; 
			}
			if(tpjy[0]==YiJing.GENG || tpjy[1]==YiJing.GENG ){
				swork[index++] = "用神落"+gong+"宫上临庚，为阻隔，晋升不利之象；";
			}else if( dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG) {
				swork[index++] = "用神落"+gong+"宫下临庚，为阻隔，晋升不利之象；";
			}
		}else{
			if(my.isKong(shigangong)) swork[index++]="时干落"+shigangong+"宫空亡，也主好事落空不成之象；";
			if(my.isKong(kaimengong)) swork[index++] = "开门落"+kaimengong+"宫空亡，也主要辞职；测开店、办公司逢开门为空大凶，表示将来要关门；";
			if(my.isTChong(kaimengong)) swork[index++] = "开门落"+kaimengong+"宫天盘干与宫相冲，也主工作调动；"; 
			if(my.isTDChong(kaimengong)) swork[index++] = "开门落"+kaimengong+"宫天盘干与地盘干相冲，也主工作调动；";
			if(my.isYima(kaimengong)) swork[index++] = "开门落"+kaimengong+"宫临马星也主工作调动；";
			if(my.isYima(my.getChongGong(kaimengong))) swork[index++] = "开门落"+kaimengong+"宫受马星落"+my.getChongGong(kaimengong)+"宫相冲，也主工作调动；";
			
			int[] kmtpjy = my.getTpjy(kaimengong);
			int[] kmdpjy = my.getDpjy(kaimengong);			
			t=null;
			if(my.isGeju(kaimengong, 125)) t = "开门落"+kaimengong+"宫临壬加庚，也称为移荡格，主工作变动；"; 
			else if(my.isGeju(kaimengong, 107)) t = "开门落"+kaimengong+"宫临庚加壬，又称为移荡格，主工作变动；";
			else if(kmtpjy[0]==YiJing.REN || kmtpjy[1]==YiJing.REN) t = "开门落"+kaimengong+"宫上临壬，也主工作流动；";
			else if(kmdpjy[0]==YiJing.REN || kmdpjy[1]==YiJing.REN) t = "开门落"+kaimengong+"宫下临壬，也主工作流动；";
			if(t!=null) if(kaimengong == my.getShenGong(QiMen.SHENHE)) t+="上临六合，主多人同时变动；";
			if(t!=null) swork[index++] = t;
			
			if(my.isJixing(kaimengong)) swork[index++] ="开门落"+kaimengong+"宫逢六仪击刑，也主辞职；";
			if(ystype==my.YSHENDAY || ystype==my.YSHENMONTH) {				
				if(my.isTChong(yuegong)) swork[index++] ="月干落"+yuegong+"宫，天盘干与宫相冲，也主同事要变动；";
				if(my.isTDChong(yuegong)) swork[index++] ="月干落"+yuegong+"宫，天盘干与地盘干相冲，也主同事要变动；";
			}
		}
		
		/** 3. 用神与各宫生克 */
		if(all==PRIVATE) {
			if(my.isGongke(gong, kaimengong)) t ="用神克开门"+kaimengong+"宫，经过努力也能得工作或官职，也主本人不想在单位干了，想换工作；";
			else if(my.isGongSheng(kaimengong, gong)) t ="开门落"+kaimengong+"宫生用神，则求职得官顺利，晋升之象；测调动则主单位不让调走；有工作的代表事业心强，工作顺利；";
			else if(my.isGongSheng(gong,kaimengong)) t ="用神生开门"+kaimengong+"宫，说明有想得到这份工作的主观愿望；有工作的代表事业心强，工作顺利；";
			else if(my.isChongke(kaimengong, gong)) t ="开门落"+kaimengong+"宫冲克用神，也主降职，或不利干此工作和职位，或单位不想要本人了。";
			else if(gong==kaimengong) t = "用神与开门同落"+kaimengong+"宫，志在必得；有工作的代表事业心强，工作顺利；";
			else if(my.isBihe(kaimengong, gong)) t = "用神落"+gong+"宫与开门落"+kaimengong+"宫比和；有工作的代表事业心强，工作顺利；";
			if(my.isKong(kaimengong)) t+="但开门旬空，失去作用；";
			swork[index++] = t; 
			
			int dumengong = my.getMenGong(QiMen.MENDU);
			if(my.isGongke(gong, dumengong)) t ="用神克杜门落"+dumengong+"宫，经过努力也能得工作或官职；";
			else if(my.isGongSheng(dumengong, gong)) t ="杜门落"+dumengong+"宫生用神，则求职得官顺利，晋升之象；对转业退伍，主工作需要领导有意挽留，不准退；";
			else if(my.isGongSheng(gong,dumengong)) t ="用神生杜门落"+dumengong+"宫，说明有想得到这份工作的主观愿望；";
			else if(my.isChongke(dumengong, gong)) t ="杜门落"+dumengong+"宫冲克用神，也主降职，测转业退伍可退；";
			else if(gong==dumengong) t = "用神与杜门同落"+dumengong+"宫，测武职必得之象；";
			else if(my.isBihe(dumengong, gong)) t = "用神与杜门落"+dumengong+"宫比和，测转业退伍可退；";
			if(my.isKong(dumengong)) t+="但杜门旬空，失去作用；";
			swork[index++] = t; 
			
			if(my.isSheng(zhifugong, gong)) swork[index++] = "值符落"+zhifugong+"宫生用神，也主能晋升之象；";
			else if(my.isChongke(zhifugong, gong)) swork[index++] = "值符落"+zhifugong+"宫克用神，顶头上司不满意，有降职之危；";
			
			if(ngtpgong==gong) {
				swork[index++] = "用神落"+gong+"宫上临年干，为临太岁，百灾消除，大吉之象，也主得到领导支持；";
			}else if(ngdpgong==gong) {
				swork[index++] = "用神落"+gong+"宫下临年干，为临太岁，百灾消除，大吉之象，也主得到领导支持；";
			}else if(my.isSheng(ngtpgong, gong)) {
				swork[index++] = "太岁落"+ngtpgong+"宫生用神，表示能得到上级领导的支持，也说明与领导关系融洽；年干为学校，测毕业分配也主顺利；";
			}else if(my.isBihe(ngtpgong, gong)) {
				swork[index++] = "太岁落"+ngtpgong+"宫与用神比和，说明与领导关系融洽；测毕业分配也表示顺利；";
			}else if(my.isGongke(ngtpgong, gong)) {
				swork[index++] = "太岁落"+ngtpgong+"宫来克用神，上级领导不高兴，如调动则主领导要求而非自己意愿；";
			}
			
			if(my.isChongke(yuegong, gong)) {
				t = "月干落"+yuegong+"宫来克用神，同事或竞争对手捣鬼；";
				if(yuegong == my.getShenGong(QiMen.SHENWU)) t+= "又上乘玄武，主暗中打小报告；";
				swork[index++] = t;
			}
			if(my.isChongke(shigangong, gong)) {
				t = "时干落"+shigangong+"宫来克用神，下属或群众来告状；";
				if(shigangong == my.getShenGong(QiMen.SHENWU)) t+= "又上乘玄武，主暗中打小报告；";
				swork[index++] = t;
			}
		}else{
			if(my.isChongke(kaimengong, ngtpgong)) swork[index++] = "开门落"+kaimengong+"宫克年干"+ngtpgong+"宫，表明领导的工作不好干，工作压力大；";
			if(my.isChongke(shigangong, ngtpgong)) swork[index++] = "时干落"+shigangong+"宫克年干"+ngtpgong+"宫，表明领导的担子很重，工作难度大；";
			
			t = "时干落"+shigangong+"宫";
			boolean isshigan = false;
			if(shigangong==my.getMenGong(QiMen.MENSI)) {
				t+="又临死门，主事不成之象；";isshigan=true;
			}
			if(shigangong==my.getMenGong(QiMen.SHENDI)) {
				t+="又临九地，主事不成之象；";isshigan=true;
			}
			String[] shihj = my.isganHeju(SiZhu.sg, SiZhu.sz);
			if(!shihj[0].equals("1")) {
				t+="又失局，主事不成之象；"+shihj[1];isshigan=true;
			}
			String shismj = my.isTGanSMJ(SiZhu.sg, SiZhu.sz); {
				if(shismj!=null) t+="时干入死绝墓，也主出师不利，办事不顺利；";isshigan=true;
			}
			if(isshigan) swork[index++] = t;
			
			if(my.isChongke(shigangong, gong)) swork[index++] = "时干"+shigangong+"宫克日干"+gong+"宫，也主工作任务繁重；"; 
			int dinggong = my.getTianGong(YiJing.DING, 0);
			int[] dingdpjy = my.getDpjy(dinggong);
			if(dingdpjy[0]==YiJing.GENG || dingdpjy[1]==YiJing.GENG) 
				swork[index++] = "丁落"+dinggong+"宫下临庚，说明手续有阻，一时办不下来；";
		}
	}
	/**
	 * 四、方向日期断
	 */
	public void getWork4(int all, int ystype, int gan, int zi) {
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		int ysgan = Integer.valueOf(ysinfo[2]);       //用神的天干
		int yszi = Integer.valueOf(ysinfo[3]);        //用神的地支
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		int gong = Integer.valueOf(ysinfo[1]);  //用神落宫
		int kaimengong = my.getMenGong(QiMen.MENKAI);  //开门落宫			
		int shigangong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //时干落宫
		int rigangong = my.getTianGong(SiZhu.rg, SiZhu.rz);
		String t = null;
		
		if(all==PRIVATE) {
			if(my.isTianKeDi(gong)) swork[index++] = "用神"+gong+"宫中天盘干克地盘干，说明利客不利主，利于调动；";
			else if(my.isDiShengTian(gong)) swork[index++] = "用神"+gong+"宫中地盘干生天盘干，说明利客不利主，利于调动；";
			else if(my.isDiKeTian(gong)) swork[index++] = "用神"+gong+"宫中地盘干克天盘干，说明利主不利客，不利于调动；";
			else if(my.isTianShengDi(gong)) swork[index++] = "用神"+gong+"宫中天盘干生地盘干，说明利主不利客，不利于调动；";
			if(my.isKong(gong)) swork[index++] = "用神落宫旬空，填实或冲实之日，事情必定有眉目；";
		}else{ 
			if(my.isTianKeDi(shigangong)) swork[index++] = "时干"+shigangong+"宫中天盘干克地盘干，说明利客不利主，利于调动；";
			else if(my.isDiShengTian(shigangong)) swork[index++] = "时干"+shigangong+"宫中地盘干生天盘干，说明利客不利主，利于调动；";
			else if(my.isDiKeTian(shigangong)) swork[index++] = "时干"+shigangong+"宫中地盘干克天盘干，说明利主不利客，不利于调动；";
			else if(my.isTianShengDi(shigangong)) swork[index++] = "时干"+shigangong+"宫中天盘干生地盘干，说明利主不利客，不利于调动；";
									
			boolean rinei = my.isNenpan(rigangong);
			boolean shinei = my.isNenpan(shigangong); 
			t = null;
			if(rinei && shinei) t = "日落"+rigangong+"宫，时落"+shigangong+"宫，均在内盘宫，主快；";
			else if(!rinei && !shinei) t = "日落"+rigangong+"宫，时落"+shigangong+"宫，均在外盘，时间不会太快；";
			else t = "日落"+rigangong+"宫，时落"+shigangong+"宫，一内一外，主慢；";				
			if(t!=null) swork[index++] = t;
			
			if(my.isKong(kaimengong)) swork[index++] = "开门落"+kaimengong+"宫旬空，填实或冲实之日，单位必定落实；";
			if(my.isKong(my.getMenGong(QiMen.MENJING3))) swork[index++] = "景门主消息，现落"+my.getMenGong(QiMen.MENJING3)+"宫空亡，待填实或冲实之日必有消息；";
			if(my.isKong(my.getTianGong(YiJing.DING,0))) {
				t = "测调动，丁奇也主调令，现落"+my.getTianGong(YiJing.DING,0)+"宫空亡，则填实或冲实必有消息；参看丁下所临之干；";
				swork[index++] = t;
			}else{
				swork[index++] = "测调动，丁奇也主调令，可参看丁下所临之干断应期；";
			}
			
			String fxiang = QiMen.JIUGONGFXIANG[kaimengong]; //得到开门宫的方位
	    boolean isnei = my.isNenpan(kaimengong);
	    String neiwai = my.isNenpan(kaimengong)?"省内":"省外";   //判断是内盘还是外盘
	    swork[index++]="开门落"+kaimengong+"宫，又在"+(isnei?"内盘":"外盘")+"，工作或调动单位在居住地"+neiwai+fxiang+"；";
	    swork[index++] = "测调动，丁下所临之干、及其所落"+my.getTianGong(YiJing.DING,0)+"宫，也可断方向；";
		}
	}
	
	/**
	 * 是否传来的geju在指定的gong中有这个十干克应
	 * @param geju
	 * @return
	 */
	private boolean isThisGeju(int gong, int geju) {
		int[] ysky = my.getShiganKeying(gong);  //用神宫的十干克应
		for(int ge : ysky)
			if(ge==geju) return true;
		return false;
	}
}