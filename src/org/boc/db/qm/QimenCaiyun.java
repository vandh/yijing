package org.boc.db.qm;

import java.util.ArrayList;
import java.util.List;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenCaiyun {
	private DaoQiMen daoqm;
	private QimenPublic my; 
	private String mingzhu;
	private int yongshen;  //用神类型，年干1，月干2，日干3，时干4
	private boolean boy;		
	private String[] m ; //保存命运宫所有的描述情况
	int n = 0; //计数器，为descMY所用
	String t=null;	
	boolean iszf ;  //是否转盘奇门
	
	int ystpgong;  		//用神天盘落宫
	int ysdpgong ;    //用神地盘宫
	String yshenname;               //用神的名称，如日干为用，年干为用等
	int ysgan ,yszi;  //用神干、用神支	
	String[] ysheju;  //用神是否合局
	String[] ysjxge;  //用神宫中的吉凶格局
	
	int[] mzhu ;      	//年命的干与支数组
	int mtpgong ;  			//命宫
	int zhishigong ;   	//值使宫
	int zhifugong;      //值符宫
	
	int kaimengong ;  //八门所在的宫
	int jing1mengong ;
	int jing3mengong ;
	int shengmengong; 
	int simengong;
	int shangmengong;
	int dumengong;
	int xiumengong;
	
	int niantpgong ;  //年干天盘落宫
	int niandpgong ;	//年干地盘落宫
	int nianzigong ;  //得到年支落宫
	int yuetpgong ;   //月干天盘宫
	int yuedpgong ;   //月干地盘宫
	int ritpgong ;   //日干天盘宫
	int ridpgong ;   //日干地盘宫
	int shitpgong ;   //时干天盘宫
	int shidpgong ;		//时干地盘宫
	
	int wutpgong ;	   //戊天盘落宫
	int wudpgong;
	int jitpgong;
	int jidpgong;
	int dingtpgong ;		//丁天盘落宫
	int dingdpgong ;		//丁地盘落宫
	int bingtpgong ;		//丁天盘落宫
	int bingdpgong ;		//丁地盘落宫
	int xintpgong ;		//丁天盘落宫
	int xindpgong ;		//丁地盘落宫
	int gengtpgong;
	int gengdpgong;
	int yitpgong;
	int yidpgong;
	int guitpgong;
	int guidpgong;
	int rentpgong;
	int rendpgong;
	
	int shenwugong ;		//玄武落宫
	int shenhegong; 		//六合落宫 
	int shenhugong;
	int shenshegong;
	int shenyingong;
	int shendigong;
	int shentiangong;
	
	int xingpenggong ;	//天蓬星落宫
	int xingruigong ;	//天蓬星落宫
	int xingrengong;
	int xingchonggong;
	int xingfugong;
	int xingyinggong;
	int xingzhugong;
	
	int ty ; //天乙星的序号
	int tygong ;//得到值符地盘九星所在的宫

	public QimenCaiyun(DaoQiMen daoqm, QimenPublic qmmy) {
  	this.daoqm = daoqm;
  	this.my = qmmy;
  }
	
	void init(String mingzhu, int yongshen,boolean boy) {
		m = new String[2500];
		n = 0;
		this.mingzhu = mingzhu;
		this.boy = boy;
		this.yongshen = yongshen;
		
  	String[] ysinfo = my.getYShenInfo(yongshen, 0, 0);		
  	yshenname = ysinfo[0];
  	ystpgong = Integer.valueOf(ysinfo[1]);  //用神天盘落宫
  	ysgan = Integer.valueOf(ysinfo[2]);
  	yszi= Integer.valueOf(ysinfo[3]);
  	ysdpgong = my.getDiGong(ysgan,yszi);
  	ysheju=my.isganHeju(ystpgong);       //用神是否合局
  	ysjxge = my.getJixiongge(ystpgong);  //用神宫中的吉凶格局
  	
  	mzhu = my.getMZhu(mingzhu);
  	mtpgong = (mzhu.length>1 && mzhu[0] * mzhu[1]!=0)?my.getTianGong(mzhu[0], mzhu[1]):0;  //命宫
  	
  	zhishigong = daoqm.getZhishiGong();   //值使宫
  	zhifugong = daoqm.getZhifuGong();  	
  	zhifugong = my.getJiGong528(zhifugong);  //此句非常重要！！！！！
  	
  	niantpgong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //年干宫
  	niandpgong = my.getDiGong(SiZhu.ng, SiZhu.nz);
  	nianzigong = my.getDiziGong(SiZhu.nz);  //得到年支落宫
  	ritpgong = my.getTianGong(SiZhu.rg, SiZhu.rz);  //日干天盘宫
  	ridpgong = my.getDiGong(SiZhu.rg, SiZhu.rz);  //日干地盘宫
  	yuetpgong = my.getTianGong(SiZhu.yg, SiZhu.yz);  //月干天盘宫
  	yuedpgong = my.getDiGong(SiZhu.yg, SiZhu.yz);  //月干地盘宫
  	shitpgong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //时干天盘宫
  	shidpgong = my.getDiGong(SiZhu.sg, SiZhu.sz);
  	
  	wutpgong = my.getTianGong(YiJing.WUG, 0);	
  	wudpgong = my.getDiGong(YiJing.WUG, 0);
  	jitpgong = my.getTianGong(YiJing.JI, 0);
  	jidpgong = my.getDiGong(YiJing.JI, 0);
  	dingtpgong = my.getTianGong(YiJing.DING, 0);
  	dingdpgong = my.getDiGong(YiJing.DING, 0);
  	bingtpgong = my.getTianGong(YiJing.BING, 0);
  	bingdpgong = my.getDiGong(YiJing.BING, 0);
  	xintpgong = my.getTianGong(YiJing.XIN, 0);
  	xindpgong = my.getDiGong(YiJing.XIN, 0);
  	gengtpgong = my.getTianGong(YiJing.GENG, 0);
  	gengdpgong = my.getDiGong(YiJing.GENG, 0);
  	yitpgong = my.getTianGong(YiJing.YI, 0);
  	yidpgong = my.getDiGong(YiJing.YI, 0);
  	rentpgong = my.getTianGong(YiJing.REN, 0);
  	rendpgong = my.getDiGong(YiJing.REN, 0);
  	guitpgong = my.getTianGong(YiJing.GUI, 0);
  	guidpgong = my.getDiGong(YiJing.GUI, 0);
  	
  	kaimengong = my.getMenGong(QiMen.MENKAI);
  	shengmengong = my.getMenGong(QiMen.MENSHENG);
  	jing1mengong = my.getMenGong(QiMen.MENJING1);
  	jing3mengong = my.getMenGong(QiMen.MENJING3);
  	simengong = my.getMenGong(QiMen.MENSI);
  	shangmengong = my.getMenGong(QiMen.MENSHANG);
  	dumengong = my.getMenGong(QiMen.MENDU);
  	xiumengong = my.getMenGong(QiMen.MENXIU);
  	
  	shenwugong = my.getShenGong(QiMen.SHENWU);
  	shenhegong = my.getShenGong(QiMen.SHENHE);
  	shenhugong = my.getShenGong(QiMen.SHENHU);
  	shenshegong = my.getShenGong(QiMen.SHENSHE);
  	shenyingong = my.getShenGong(QiMen.SHENYIN);
  	shendigong = my.getShenGong(QiMen.SHENDI);
  	shentiangong = my.getShenGong(QiMen.SHENTIAN);
  	
  	xingpenggong = my.getXingGong(QiMen.XINGPENG);
  	xingrengong = my.getXingGong(QiMen.XINGREN);
  	xingchonggong = my.getXingGong(QiMen.XINGCHONG);
  	xingfugong = my.getXingGong(QiMen.XINGFU);
  	xingruigong = my.getXingGong(QiMen.XINGRUI);
  	xingyinggong = my.getXingGong(QiMen.XINGYING);
  	xingzhugong = my.getXingGong(QiMen.XINGZHU);
  	
  	ty = zhifugong; //daoqm.gInt[4][3][zhifugong];
		tygong = my.getXingGong(my.getJiGong528(ty));  //得到值符地盘九星所在的宫
	}
  
	public String getCaiyun(StringBuffer str,String mingzhu, int yongshen,boolean boy, int iszf) {
		init(mingzhu, yongshen, boy);
		this.iszf = iszf!=2;
		m[n++]=my.NOKG+"一、取用神["+yshenname+"]：";
		getMoney1();
		m[n++]=my.NEWLINE;
		
		
		m[n++]=my.NOKG+"二、大格局断：";
		getMoney2();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"三、用神详断：";
		List<Integer> ysgong = new ArrayList<Integer>(); //保存用神所在的宫
		List<String> ysname = new ArrayList<String>(); //保存用神名称
		getYShen(ysgong, ysname);
		for(int i=0; i<ysgong.size(); i++) {
			int thegong = ysgong.get(i) == 5 ? 2 : ysgong.get(i);  //如果落5宫，则就是寄2宫
			m[n++]="------------------------------"+ysname.get(i)+"("+QiMen.BIGNUM[thegong]+"宫)------------------------------";
			getMoney31(thegong);
			if(ystpgong==thegong || thegong==shitpgong)  //如果为用神宫和时干宫，则加上一些额外的判断信息
				getMoney32(thegong);
			if(ystpgong==thegong) getMoney33(thegong);
		}
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"四、企业经营断：";
		getMoney40();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"五、商战竞争断：";
		getMoney50();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"六、利润日期断：";
		getMoney4();
		m[n++]=my.NEWLINE;
		
		m[n++]=my.NOKG+"七、投资求财详断：";
		m[n++]="------------------------------交易与租赁------------------------------";
		getMoney501();
		m[n++]="------------------------------买货------------------------------";
		getMoney502();
		m[n++]="------------------------------卖货------------------------------";
		getMoney503();
		m[n++]="------------------------------买卖租赁房地产------------------------------";
		getMoney504();
		m[n++]="------------------------------彩票与抽奖------------------------------";
		getMoney505();
		m[n++]="------------------------------投资与开店------------------------------";
		getMoney506();
		m[n++]="------------------------------合伙投资------------------------------";
		getMoney507();
		m[n++]="------------------------------借贷------------------------------";
		getMoney508();
		m[n++]="------------------------------放贷------------------------------";
		getMoney509();
		m[n++]="------------------------------讨债------------------------------";
		getMoney510();
		m[n++]=my.NEWLINE;
		m[n++]="------------------------------应期参考------------------------------";
		getMoney500();
		m[n++]=my.NEWLINE;
		
		return my.format(str, m);
	}
	
	/**
	 * 一、判断
	 */
	public void getMoney1() {
		m[n++]="月干为同行竞争者；";
		m[n++]="日干为求测方，如买方、融资方、承租方；";
		m[n++]="时干为与求测方关联的另一方，如卖方、投资方、出租方、加工方；为事体，如财务指标、房子、财、货物、经营项目；";
		m[n++]="戊为资本；";
		m[n++]="开门为店铺、门面；";
		m[n++]="生门为房屋、利息，生门所临之星为财星；";
		m[n++]="死门为地皮；";
		m[n++]="伤门为讨债人、运输车皮；";
		m[n++]="景门为行情、策划、合同、画卷、油票；";
		m[n++]="值符为求测方、货主、放贷人、银行、行情、现有新宅；";
		m[n++]="值使为事体、购货人、借贷人、经营项目、原有旧宅；";
		m[n++]="天乙星为贷款人、借款人；";
		m[n++]="六合为经纪人、代理商；";
		m[n++]="开、休、生落宫为得财方向，以生门落宫为得财数量；";		
	}
	/**
	 * 大格局断
	 */
	public void getMoney2() {
		if(my.isMenFu()) m[n++]="八门伏呤：主事情进展缓慢；宜静不宜动, 不宜追加投资，测生意主破财、货物积压、买卖不成，但宜收敛钱财、宜买货；";
		if(my.isMenFan()) m[n++]="八门反吟：主事情反复不顺，不利远行；求财不利反蚀本，即使赚钱也有宝难留；进了货也有退货之象；也主快，宜主动出击；";
		if(my.isXingFu()) m[n++]="九星伏呤：天时不利，主事情进展缓慢；宜静不宜动, 不宜追加投资，测生意主破财、货物积压、买卖不成，但宜收敛钱财、宜买货；";
		if(my.isXingFan()) m[n++]="九星反吟：天时不利，主事情反复不顺，不利远行；求财不利反蚀本，即使赚钱也有宝难留；进了货也有退货之象；也主快，宜主动出击；";
		//五不遇时2，天辅大吉时2
		int gejuNum = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];
  	if(gejuNum!=0)	m[n++] = QiMen.gGejuDesc[gejuNum][0]+"："+QiMen.gGejuDesc[gejuNum][1];

		//值使落宫断
		int zsgong = daoqm.getZhishiGong();
		String[] zsmen = my.getmenJX(zsgong);
		String t = "";
		if(zsmen[0].equals("-1")) t="逢"+zsmen[1]+"不吉之象；";
		if(my.isKong(zsgong)) t+="逢空，古有”值使逢空，有始无终“，好事成空之象；"; 
		if(!"".equals(t)) m[n++] = "值使落"+zsgong+"宫"+t;
	}
	/**
	 * 各用神共性断
	 */
	private void getMoney31(int gong) {
		int men = daoqm.gInt[3][1][gong];  //宫的门
		int shen = daoqm.gInt[1][1][gong]; //宫中的神		
		int[] tg = my.getTpjy(gong);  //宫中的天盘奇仪		
		String[] menjx = my.getmenJX(gong); 		
		boolean iskong = my.isKong(gong);
		
		//合局失局断
		String[] heju = my.isganHeju(gong);
		if(heju[0].equals("1")) m[n++]="合局，事可以办成，逢吉门，则门吉事吉；"+heju[1];
		else m[n++]="失局，说明状态不佳，难以成功，不吉之象；"+heju[1];
		
		//门迫制生断
		boolean ismenpo = QiMen.men_gong[men][gong].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[men][gong].equals(QiMen.zphym1);
		boolean ismenshe = QiMen.men_gong[men][gong].equals(QiMen.zphym4);
		if(!menjx[0].equals("1") && ismenpo)
			m[n++]=menjx[1]+"在宫中被迫，凶门被迫祸重重，事不成之象；";
		else if(menjx[0].equals("1") && ismenpo)
			m[n++]=menjx[1]+"在宫中被迫，吉门被迫吉不就，事不成之象；";
		else if(!menjx[0].equals("1") && ismenzhi)
			m[n++]=menjx[1]+"在宫中被制，凶门被克凶不起，事有解就之象；";
		else if(menjx[0].equals("1") && ismenzhi)
			m[n++]=menjx[1]+"在宫中被制，吉门被克吉不就，事不成之象；";
		else if(menjx[0].equals("1") && ismenshe)
			m[n++]=menjx[1]+"在宫中被生，吉门被生有大利，大吉之象；";
		else if(!menjx[0].equals("1") && ismenshe)
			m[n++]=menjx[1]+"在宫中被生，凶门得生祸难避，不吉之象；";
		
		//开门逢迫逢制不吉断		
		if(gong==kaimengong && (ismenpo || ismenzhi)) {
			t = "开门落"+kaimengong+"宫遇"+(ismenpo?"门迫":"门制")+"，有凶格等凶的信息明显时，开店等遇之必有麻烦事；现在";
			//找到克用神的宫
			for(int i=1; i<=9; i++) {
				if(i==5) continue;
				if(my.isChongke(i, ystpgong)) {
					if(my.getMenGong(QiMen.MENJING1)==i) t+="惊门落"+i+"宫克用神"+ystpgong+"宫，主官司口舌；";
					else if(my.getMenGong(QiMen.MENSHANG)==i) t+="伤门落"+i+"宫克用神"+ystpgong+"宫，主伤灾；";
					else if(my.getMenGong(QiMen.MENSI)==i) t+="死门落"+i+"宫克用神"+ystpgong+"宫，主主摊位地皮之类扯皮事；";
					else if(yuetpgong==i) t+="月干落"+i+"宫来克，主同行同事坑害钱财；";
					else if(niantpgong==i) t+="年干落"+i+"宫来克，主上级主管部门找麻烦；";
				}				
			}
			if(my.isBihe(yuetpgong, ystpgong) && my.getShenGong(QiMen.SHENWU)==yuetpgong) 
				t+="月干落"+yuetpgong+"宫与用神落"+ystpgong+"宫比和，但上临玄武，主朋友贪财；";
			if(ysjxge[0].equals("1")) t+="幸用神宫中大格局吉，不会出大麻烦；"+ysjxge[1];
			else t+="用神宫中大格局又不吉，小心出大乱子；"+ysjxge[1];
			m[n++]=t;
		}
		
		//主要吉凶格断
		if(my.isJixing(gong)) m[n++]= "遇六仪击刑，主受刑遭殃、极凶，测投资为损耗，测产品为质量有问题；";
		if(my.isTaohua(gong)) m[n++]= "坐桃花，也主梦想做好事，发大财；";
		for(int za : new int[]{420,421,422}) {
			if(QiMen.men_gan_shen[men][tg[0]][shen]==za || QiMen.men_gan_shen[men][tg[1]][shen]==za)
				m[n++]= "遇"+QiMen.gGejuDesc[za][0]+"，"+QiMen.gGejuDesc[za][1];
		}
		int[] geju = my.getShiganKeying(gong);
		for(int ge : geju) {
			if(ge==0) continue;
			m[n++]= QiMen.gGejuDesc[ge][0]+"，"+QiMen.gGejuDesc[ge][1];
		}
		
		//旬空断
		int wutggong = my.getTianGong(YiJing.WUG, 0);  //戊在天盘的宫
		int wudggong = my.getDiGong(YiJing.WUG, 0);  //戊在地盘的宫
		if(iskong) {
			t = "落宫空亡，也主好事成空之象；";
			if(kaimengong==gong) t += "开店办厂最怕开门空亡，未开者开不成，已开者必定关门停产；";
			if(wutggong==gong) t+="戊宫空亡为资本不够，也主有钱进不了货；"; 
			m[n++] = t;
		}
		
		//入墓断
		boolean ismu = my.isTGanMu(YiJing.WUG, 0);		
		if(ismu) {
			t = "天盘干入墓，主近期遇到了麻烦；";
			if(kaimengong==gong) t += "开店办厂最怕开门入墓，未开者开不成，已开者必定关门停产；";
			if(shitpgong==gong) t+="时干"+YiJing.TIANGANNAME[SiZhu.sg]+"在宫中入库，表示产品严重积压；"; 
			m[n++] = t;
		}
		
		//冲断
		t = "";
		if(my.isTGChong(gong)) t+="天盘干与宫相冲；";
		if(my.isTDChong(gong)) t+="天盘干与地盘干相冲；";
		if(!"".equals(t)) t+="主动、主速、主操心和心情急躁；";
		if(!"".equals(t) && (wutggong==gong || wudggong==gong)) t+="又临戊，资本逢冲为散，表明花钱速度快；";
		if(!"".equals(t)) m[n++] = t;
		
		//合断
		t = "";
		if(my.isTDG3He(gong)) t+="天地盘与宫地支三合；";
		else {
			if(my.isTG6He(gong)) t+="天盘与宫地支六合；";
			else if(my.isTG3He(gong)) t+="天盘与宫地支半合；";
			if(my.isTDZi3He(gong)) t+="天地盘地支半合；";
		}
		if(my.isTDGanHe(gong)) t+="天地盘干相合；";
		if(!"".equals(t)) m[n++] = t+"合则主货卖不出去；";
		
		//临八神断
		if(gong==my.getShenGong(QiMen.SHENTIAN)) m[n++] = "上临九天主大展宏图，如出乱子也主闹得很大很凶；";
		else if(gong==my.getShenGong(QiMen.SHENDI)) m[n++] = "上临九地主坐地经商，长久发展；";
		else if(gong==my.getShenGong(QiMen.SHENYIN)) m[n++] = "上临太阴正暗中策划，或有贵人相助；";
		else if(gong==my.getShenGong(QiMen.SHENHE)) m[n++] = "上临六合主有合伙人，也是大吉之星；";
		else if(gong==my.getShenGong(QiMen.SHENWU)) m[n++] = "上临玄武主投机，货物腐烂变质；";
		else if(gong==my.getShenGong(QiMen.SHENFU)) m[n++] = "上临值符有银行或顶头上司支持；";
		else if(gong==my.getShenGong(QiMen.SHENSHE)) m[n++] = "上临腾蛇为假货或腐烂变质，也主资金无法回笼，或事情有变化；";
		
		//临九星断
		if(gong==my.getXingGong(QiMen.XINGPENG)) m[n++] = "临天蓬测生意主破大财或赢大利，也主野心大；";
		else if(gong==my.getXingGong(QiMen.XINGCHONG)) m[n++] = "临天冲测生意主挣钱速度快；";
		else if(gong==my.getXingGong(QiMen.XINGFU)) m[n++] = "临天辅主文雅实在；";
		else if(gong==my.getXingGong(QiMen.XINGRUI)) m[n++] = "临天芮主做事有不当之处，也说明操劳过度身体疲惫；";
		else if(gong==my.getXingGong(QiMen.XINGZHU)) m[n++] = "临天柱主口舌是非；";

		//临八门断
		int dumengong = my.getMenGong(QiMen.MENDU);
		boolean isdunei = my.isNenpan(dumengong);
		if(gong==dumengong) m[n++] = "临杜门主闭塞，好隐藏；"+(isdunei?"幸在内盘，藏在近处":"现在外盘，人躲到外地去了！");
		else if(gong==my.getMenGong(QiMen.MENSI)) {
			t = "临死门，不吉之象；";
			if(zhishigong==gong) t+="又死门为值使，测开店大凶；";
			m[n++] = t;
		}else if (gong==my.getMenGong(QiMen.MENJING1)) m[n++] = "逢惊门，主担心；";
		
		//临天盘奇仪断
		int gengtpgong = my.getTianGong(YiJing.GENG, 0);
		int gengdpgong = my.getDiGong(YiJing.GENG, 0);
		if(gong==gengtpgong || gong==gengdpgong) {
			t = "与庚同宫，表示有阻力，或为大型、重点单位或行业；";
			if(niantpgong==gong || niandpgong==gong) t+="临年干，表示上级领导反对；";
			if(yuetpgong==gong || yuedpgong==gong) t+="临月干，表示合作伙伴捣鬼；";
			if(shitpgong==gong || shidpgong==gong) t+="临时干，防止下属有鬼；";
			m[n++] = t;
		}
		int dingtpgong = my.getTianGong(YiJing.DING, 0);
		int dingdpgong = my.getDiGong(YiJing.DING, 0);
		if(dingtpgong==gong || dingdpgong==gong) {
			t = "临丁，说明立项等文书以自己名义进行；";
			if(my.isTDGanHe(gong)) t+="恰逢丁与壬淫荡之合，又主贪利而破财；";
			m[n++] = t;
		}
		if(niantpgong==gong || niandpgong==gong) m[n++]="与年干同宫，被年干生，有上级领导支持；";
		else if(my.isSheng(niantpgong, gong)) m[n++]="受年干"+niantpgong+"宫生，有上级领导支持；";
		else if(my.isChongke(niantpgong, gong)) m[n++]="受年干"+niantpgong+"宫冲克，也主一年内行事不利；";
		if(my.isChongke(nianzigong, gong)) m[n++]="受年支所落"+nianzigong+"宫冲克，也主一年内行事不利；";
		//是否逢奇
		int niangan = my.getTiangan(SiZhu.ng, SiZhu.nz);
		int yuegan = my.getTiangan(SiZhu.yg, SiZhu.yz);
		int shigan = my.getTiangan(SiZhu.sg, SiZhu.sz);
		boolean isnianji = niangan==YiJing.YI || niangan==YiJing.BING || niangan==YiJing.DING;
		boolean isyueji = yuegan==YiJing.YI || yuegan==YiJing.BING || yuegan==YiJing.DING;
		boolean isshiji = shigan==YiJing.YI || shigan==YiJing.BING || shigan==YiJing.DING;
		if(isnianji && (niantpgong==gong || niandpgong==gong)) m[n++]="宫中逢奇，奇为年干["+YiJing.TIANGANNAME[niangan]+"]，必得上级领导支持；";
		if(isyueji && (yuetpgong==gong || yuedpgong==gong)) m[n++]="宫中逢奇，为月干["+YiJing.TIANGANNAME[yuegan]+"]，必得同行朋友相助；";
		if(isshiji && (shitpgong==gong || shidpgong==gong)) m[n++]="宫中逢奇，为时干["+YiJing.TIANGANNAME[shigan]+"]，必得下属鼎立相助；";
		//奇仪与宫合断
		int guitpgong = my.getTianGong(YiJing.GUI, 0);
		int guidpgong = my.getDiGong(YiJing.GUI, 0);
		if(gong==1 && (gong==guitpgong || gong==guidpgong)) m[n++]="临癸落坎宫，可能是做烟酒生意的；";
		
		//加入用神额外信息、加入时干额外信息
		//if(gong==ysgong || gong==shitpgong) getMoney32(gong);
	}
	/**
	 * 用神与时干宫 个性断
	 * 用神有些额外的信息，时干表示另一方，所以也有些额外信息
	 */
	private void getMoney32(int gong) {		
		String[] ysdpheju=my.isganHeju(ysdpgong);       //用神地盘是否合局
		int men = daoqm.gInt[3][1][gong];  //宫的门

		//用神合局入墓
		if(gong==ystpgong) {
			if(!ysdpheju[0].equals("1")) m[n++]="用神地盘干落"+ysdpgong+"宫失局，不吉之象；"+ysdpheju[1];
			if(my.isDGanMu(ysgan,yszi)) m[n++]="用神地盘干落"+ysdpgong+"宫入墓，表示犹豫不决；";
		}
		
		//旺衰看规模
		int gan = 0; //得到该宫的天盘干，有时为二个，只取一个
		gan = ystpgong==gong?ysgan:shitpgong==gong?my.getTiangan(SiZhu.sg, SiZhu.sz):my.getTpjy(gong)[0];
		String ysws = QiMen.gan_gong_wx[gan][gong];
		int ysws3 = QiMen.gan_gong_wx3[gan][gong];
		if(ysws3<=0) m[n++]="用神落宫处["+ysws+"]地，为初建规划阶段、力量比较弱；";
		else if(ysws3==-2) m[n++]="用神落宫处["+ysws+"]地，说明事情尚在孕育筹谋阶段；";
		else if(ysws3==1) m[n++]="用神落宫处["+ysws+"]地，为发展良好的大公司";
		               
		//临星门神格
		if(gong==my.getXingGong(QiMen.XINGRUI)) m[n++]="逢天芮，说明原有企业经营不善；";
		if(gong==my.getMenGong(QiMen.MENJING1)) m[n++]="逢惊门，正在吃官司；";
		else if(gong==my.getMenGong(QiMen.MENSHANG)) m[n++]="逢伤门，主要价高；";
		else if(gong==my.getMenGong(QiMen.MENDU)) m[n++]="临杜门，主货物卖不出去；";
		else if(gong==my.getMenGong(QiMen.MENSI)) m[n++]="临死门，主当前状况不好；";
		else if(gong==my.getMenGong(QiMen.MENKAI)) m[n++]="临开门，货物质量好，也主知情，或事情已经公开；";
		
		if(my.ismenzhi(gong)) m[n++]=QiMen.bm1[men]+"门受制，表示受人胁制";
		if(my.isGeju(gong,70)) m[n++]="遇人遁吉格，说明想通过熟人和关系逃离目前困境，谋求新的生财之道；";
		
		if(gong==my.getShenGong(QiMen.SHENSHE)) m[n++]="临腾蛇，主变动，想改变目前状况；";
		else if(gong==my.getShenGong(QiMen.SHENWU)) m[n++]="临玄武，主虚假、吹大话、道德败坏；";
		else if(gong==zhifugong) {
			if(gong==1 || gong==3 || gong==7 || gong==9)
				t = "上临值符，为有正职的领导或公司负责人；";
			else if(gong==6)
				t = "上临值符，为某单位领导或负责人；";
			else
				t = "上临值符，可能为副职的领导或公司负责人；";
			String[] zhifuhj = my.isganHeju(gong);
			if(zhifuhj[0].equals("1")) t +="宫中合局，应为有权有势之人；";
			else t +="宫中失局，官阶不大，应为科长主任之类；";
			if(t!=null) m[n++] = t;
		}
		
		//代理商关系
		int hegong = my.getShenGong(QiMen.SHENHE);
		if(gong==hegong) m[n++] ="与六合同宫，说明产品销售渠道主要依赖于代理商；";
		else if(my.isSheng(gong, hegong)) m[n++] ="生六合所落"+hegong+"宫，说明产品销售渠道主要依赖于代理商；";
		else if(my.isBihe(gong, hegong)) m[n++] ="与六合所落"+hegong+"宫比和，说明产品销售渠道主要依赖于代理商；";
		else if(my.isSheng(hegong, gong)) m[n++] ="得六合所落"+hegong+"宫生助，说明代理商助力很大；";
		else if(my.isChongke(hegong, gong) || my.isChongke(gong, hegong)) m[n++] ="与六合所落"+hegong+"宫相互冲克，说明与代理商关系没有处理好；";
		
		//广告
		int jinggong = my.getMenGong(QiMen.MENJING3);
		int dinggong = my.getTianGong(YiJing.DING, 0);
		if(my.isChongke(jinggong, gong)) m[n++] ="景门落"+jinggong+"宫冲克用神，证明宣传力度不够；";
		else if(my.isChongke(gong, jinggong)) m[n++] ="用神冲克景门所落"+jinggong+"宫，表示不愿意做广告；";
		else m[n++] ="用神与景门所落"+jinggong+"宫相生或比和或同宫，表示广告效应良好；";
		if(my.isChongke(dinggong, gong)) m[n++] ="丁奇落"+dinggong+"宫冲克用神，证明宣传力度不够；";
		else if(my.isChongke(gong, dinggong)) m[n++] ="用神冲克丁奇所落"+dinggong+"宫，表示不愿意做广告；";
		else m[n++] ="用神与丁奇所落"+dinggong+"宫相生或比和或同宫，表示广告效应良好；";
	}
	private void getMoney33(int gong) {
		//对冲
		if(mtpgong!=0 && my.isChongke(ystpgong, wutpgong) && my.isChongke(mtpgong, wutpgong)) {
			 t = "用神落"+ystpgong+"宫，年命落"+mtpgong+"宫，均克天盘戊所落"+wutpgong+"宫，有破财之患；逢凶格更凶";
			 if(ystpgong==dingtpgong || ystpgong==dingdpgong) t+="用神临丁，事因女人；";
			 else if(mtpgong==dingtpgong || mtpgong==dingdpgong) t+="年命临丁，事因女人；";
			 if(ystpgong==shenwugong || ystpgong==xingpenggong) t+="用神临玄武或天蓬，事起失盗；";
			 else if(mtpgong==shenwugong || mtpgong==xingpenggong) t+="年命临玄武或天蓬，事起失盗；";
			 if(ystpgong==jing1mengong) t+="用神临惊门，因口舌官非；";
			 else if(mtpgong==jing1mengong) t+="年命临惊门，因口舌官非；";
			 if(my.isChongke(niantpgong, ystpgong)) t+="用神受年干所落"+niantpgong+"宫克，必是行政管理部门，如果是做生意的，则是税务罚款；";
			 else if(my.isChongke(niantpgong, mtpgong)) t+="年命受年干所落"+niantpgong+"宫克，必是行政管理部门，如果是做生意的，则是税务罚款；";
			 m[n++] = t;
		}
		//用干入墓
		if(my.isTGanMu(SiZhu.yg, SiZhu.yz)) {
			t = "月干入墓，说明走入了朋友的圈套；";
			if(my.isKong(yuetpgong)) t+="又逢空亡，更凶；";
			m[n++] = t;
		}
	}
	/**
	 * 利润日期断
	 */
	private void getMoney4() {
		//生门与用神关系断
		m[n++] = "1) 生门与用神关系断是否获利：";
		if(my.isSheng( shengmengong, ystpgong)) m[n++] = "生门落"+shengmengong+"宫生用神所落"+ystpgong+"宫，主获利；";
		else if(my.isSheng(ystpgong,shengmengong)) m[n++] = "用神落"+ystpgong+"宫生门所落"+shengmengong+"宫，表明生财有道会挣钱；";
		else if(my.isBihe(ystpgong,shengmengong)) m[n++] = "用神落"+ystpgong+"宫与生门所落"+shengmengong+"宫比和，主效益不错；";
		else if(ystpgong==shengmengong) m[n++] = "用神与生门同落"+shengmengong+"宫，生门之上好求财，必获利；";
		//生门与戊断
		String[] smheju = my.ismenHeju(shengmengong);
		if(my.isSheng( shengmengong, wutpgong)) m[n++] = "生门落"+shengmengong+"宫生戊所落"+wutpgong+"宫，必获利；"+(smheju[0].equals("1")?"又合局，获倍利；":"但失局，获利不丰；");
		else if(my.isBihe(wutpgong,shengmengong)) m[n++] = "戊落"+wutpgong+"宫与生门所落"+shengmengong+"宫比和，获中利；";
		else if(ystpgong==shengmengong) m[n++] = "戊与生门同落"+shengmengong+"宫，生门之上好求财，必获利；";
		else if(my.isSheng(wutpgong,shengmengong)) m[n++] = "戊落"+wutpgong+"宫生门所落"+shengmengong+"宫，需要持续增加资本方可获利；";
		else if(my.isChongke(shengmengong, wutpgong)) m[n++] = "生门落"+shengmengong+"宫克戊所落"+wutpgong+"宫，必赔本；"+(smheju[0].equals("1")?"":"又失局，赔得惨不忍睹；");
		
		//得财时间 
		m[n++] = "2) 获利时间：";
		boolean iswunei = my.isNenpan(wutpgong);
		boolean isshnei = my.isNenpan(shengmengong);
		t=null;
		if(iswunei && isshnei) 
			t="生门落"+shengmengong+"宫，戊落"+wutpgong+"宫，均为内盘，得财速度快；";
		else if((!iswunei && isshnei) || (iswunei && !isshnei)) 
			t="生门落"+shengmengong+"宫，戊落"+wutpgong+"宫，一内一外，得财速度慢；";
		else t="生门落"+shengmengong+"宫，戊落"+wutpgong+"宫，均在外盘，得财速度慢；";
		if(my.isTDG3He(wutpgong) ||	my.isTG6He(wutpgong) || my.isTG3He(wutpgong) ||	my.isTDZi3He(wutpgong) || my.isTDGanHe(wutpgong)) 
			t+="戊宫中逢合，又主速度慢；";
		else if(my.isTGChong(wutpgong) || my.isTDChong(wutpgong)) 
			t+="戊宫中逢冲，又主速度快；";
		if(t!=null) m[n++] = t; 
		
		//用神干是否入墓、旬空
		int[] gongs = {ystpgong, shengmengong, wutpgong};
		String[] gongname = {"用神落"+ystpgong+"宫","生门落"+shengmengong+"宫","戊落"+wutpgong+"宫"};
		for(int i=0; i<gongs.length; i++) {
			if(my.isTGanMu(gongs[i])) m[n++] = gongname[i]+"入墓，冲起之期可断；";
		}
		for(int i=0; i<gongs.length; i++) {
			if(my.isKong(gongs[i])) m[n++] = gongname[i]+"空亡，冲起或填实之期可断；";
		}
		int wudpgong = my.getDiGong(YiJing.WUG, 0);
		int[] wutpjy = my.getTpjy(wudpgong);
		String wudpname = YiJing.TIANGANNAME[wutpjy[0]]+(wutpjy[1]==0?"":YiJing.TIANGANNAME[wutpjy[1]]);
		m[n++] = "地盘戊所落"+wudpgong+"宫上临之干["+wudpname+"]也可为应期；";
		
		//断数量
		m[n++] = "3) 以戊、生门落宫断利润多少：";
		String[] wuheju = my.isganHeju(wutpgong);
		String[] wujxge = my.getJixiongge(wutpgong);
		t=null;
		if(wuheju[0].equals("1")) t = "戊落"+wutpgong+"宫合局，求财可得；以落宫之数断，"+my.getGongshu(wutpgong);
		else t = "戊落"+wutpgong+"宫失局，求财艰难；";
		if(wujxge[0].equals("0")) t+="又逢"+wujxge[1]+"防止因财生非；"; 
		if(t!=null) m[n++] = t; 

		m[n++]="生门落"+shengmengong+"宫，以落宫数按是否合局、财星衰旺断利润，"+my.getGongshu(shengmengong);
		String[] shheju = my.ismenHeju(shengmengong);
		String[] shxge = my.getJixiongge(shengmengong);
		if(shheju[0].equals("1")) m[n++]="生门合局，又宫中"+shxge[1]+"利润大，可断生门落宫之大数；";
		else if(shheju[0].equals("0") && !shxge[0].equals("0")) m[n++]="生门失局有气，但宫中"+shxge[1]+"说明有一定的利润，可断生门落宫之中数；";
		else if(shheju[0].equals("-1") && !shxge[0].equals("0")) m[n++]="生门失局无气，但宫中"+shxge[1]+"利润微薄，可断生门落宫之小数；";
		else if(!shheju[0].equals("1") && shxge[0].equals("0")) m[n++]="生门失局，宫中"+shxge[1]+"无利赔本的买卖；";
		if(shengmengong==8) {
			m[n++]="生门落艮宫为不动，表示财运还没有发动；";
		}
		if(my.isKong(shengmengong)) m[n++]="生门遇空亡，只能获一半利润；";
		String[] cxws = my.getxingWS(shengmengong);
		String[] ysws = my.gettgWS(ysgan, yszi);
		if(cxws.equals("1") && ysws[0].equals("1")) m[n++]="财星"+cxws[1]+"用神"+ysws[1]+"有利可图，可断生门落宫之大数；";
		else if(cxws.equals("-1") && ysws[0].equals("-1")) m[n++]="财星"+cxws[1]+"用神"+ysws[1]+"利润微薄，以生门落宫之小数断；";
		else m[n++]="财星"+cxws[1]+"用神"+ysws[1]+"利润一般，以生门落宫之中数断；";
		
		//断产品数量
		m[n++] = "4) 以时干落宫断产品等数量：";
		m[n++] = "销售产品或招工人等，以时干落宫之数断，"+my.getGongshu(shitpgong);
		String[] shiws = my.gettgWS(SiZhu.sg, SiZhu.sz);
		String[] shijx = my.getJixiongge(shitpgong);
		if(shiws.equals("1") && shijx[0].equals("1")) m[n++]="时干"+shiws[1]+shijx[1]+"可断时干宫之大数；";
		if(shiws.equals("-1") && shijx[0].equals("-1")) m[n++]="时干"+shiws[1]+shijx[1]+"以时干落宫之小数断之；";
		else m[n++]="时干"+shiws[1]+shijx[1]+"以时干落宫之中数断之；";
		
	}
	private void getMoney501() {
		m[n++]="测出租房屋、酒店、门面等交易，以日干求测方如出租方，时干为另一方如承租方，六合为中介人；";
		m[n++]="测买卖等交易，以日干为买主，时干为卖主，六合为经纪人、中介人；";
		if(my.isMenFu()) m[n++]="遇伏吟，测物品（画）交易则为年代久远的旧货（旧画）；";
		
		if(my.isSheng(ritpgong, shitpgong)) m[n++]="日干落"+ritpgong+"宫生时干所落"+shitpgong+"宫，利卖方；";
		else if(my.isSheng(shitpgong,ritpgong)) m[n++]="时干落"+shitpgong+"宫生日干所落"+ritpgong+"宫，利买方；";
		else if(my.isChongke(ritpgong, shitpgong)) m[n++]="日干落"+ritpgong+"宫冲克时干所落"+shitpgong+"宫，买主不要；";
		else if(my.isChongke(shitpgong,ritpgong)) m[n++]="时干落"+shitpgong+"宫冲克日干所落"+ritpgong+"宫，卖主不卖；";
		else if(shitpgong==ritpgong) m[n++]="日时同落"+ritpgong+"宫，交易必成，测租赁主能租出去；";
		else if(my.isBihe(shitpgong,ritpgong)) m[n++]="时干落"+shitpgong+"宫与日干所落"+ritpgong+"宫比和，公平交易，也主成交；";
		
		if(my.isKong(ritpgong) && my.isKong(shitpgong)) m[n++]="现日干与时干均落宫空亡，交易必不成功；";
		else if(my.isKong(ritpgong)) m[n++]="现日干落宫空亡，交易必不成功；";
		else if(my.isKong(shitpgong)) m[n++]="现时干落宫空亡，交易必不成功；";
		
		if(my.isSheng(shenhegong, ritpgong)) m[n++]="六合落"+shenhegong+"宫生日干所落"+ritpgong+"宫，中介人向着买主；";
		else if(my.isBihe(shenhegong, ritpgong)) m[n++]="六合落"+shenhegong+"宫与日干所落"+ritpgong+"宫比和，中介人向着买主；";
		else if(shenhegong==ritpgong) m[n++]="六合与日干同落"+ritpgong+"宫，中介人向着买主；";
		else if(my.isChongke(shenhegong, ritpgong)||my.isChongke(ritpgong,shenhegong)) m[n++]="六合落"+shenhegong+"宫冲克日干所落"+ritpgong+"宫，中介人与买主不和，必有欺诈；";
		
		if(my.isSheng(shenhegong, shitpgong)) m[n++]="六合落"+shenhegong+"宫生时干所落"+shitpgong+"宫，中介人向着卖主；";
		else if(my.isBihe(shenhegong, shitpgong)) m[n++]="六合落"+shenhegong+"宫与时干所落"+shitpgong+"宫比和，中介人向着卖主；";
		else if(shenhegong==shitpgong) m[n++]="六合与时干同落"+shitpgong+"宫，中介人向着卖主；";
		else if(my.isChongke(shenhegong, shitpgong) || my.isChongke(shitpgong,shenhegong)) m[n++]="六合落"+shenhegong+"宫冲克时干所落"+shitpgong+"宫，中介人与卖主不和，必有欺诈；";
		
		if(my.isKong(shenhegong)) m[n++]="六合落"+shenhegong+"宫空亡，必有奸诈欺骗之事；";
		if(my.isTGanMu(shenhegong)) m[n++]="六合落"+shenhegong+"宫入墓，必有奸诈欺骗之事；";		
		//getMoney500();
	}	
	private void getMoney502() {
		m[n++]="日干为买主，时干为货物。";
		
		String[] shiheju = my.isganHeju(SiZhu.sg, SiZhu.sz);
		String[] shimen = my.getmenJX(shitpgong);
		String[] shigeju = my.getJixiongge(shitpgong);
		if(shiheju[0].equals("1")) m[n++]="时干落"+shitpgong+"宫合局，货的质量好；"+shiheju[1];
		else if(!shiheju[0].equals("1") && shenwugong==shitpgong)
			m[n++]="时干落"+shitpgong+"宫失局，又上乘玄武，必是假冒伪劣商品或是腐烂变质货物；"+shiheju[1];
		else if(!shiheju[0].equals("1") && shimen[0].equals("-1") && shigeju[0].equals("0")) 
			m[n++]="时干落"+shitpgong+"宫失局，又临"+shimen[1]+shigeju[1]+"必是假冒伪劣商品或是腐烂变质货物；"+shiheju[1];
		else m[n++]="时干落"+shitpgong+"宫失局稍有气，质量一般的次品；"+shiheju[1];
		if(shitpgong==7 && xingpenggong==7) m[n++]="时干落7宫，逢天蓬水神，防止货品糟水灾；";
		
		String shismj = my.isTGanSMJ(SiZhu.sg,SiZhu.sz);
		if(shismj!=null) m[n++]="时干落"+shitpgong+"宫处"+shismj+"地，无利可图；";
		if(my.isKong(shitpgong)) 		m[n++]="时干落"+shitpgong+"宫空亡，主无货可卖；";
		
		if(my.isSheng(shitpgong, ritpgong)) m[n++]="时干落"+shitpgong+"宫生日干"+ritpgong+"宫，不管好货孬货都有利，也主不缺买主；";
		else if(my.isChongke(shitpgong, ritpgong)) m[n++]="时干落"+shitpgong+"宫克日干"+ritpgong+"宫，生意难做；";
		else if(my.isSheng(ritpgong,shitpgong)) m[n++]="日干落"+ritpgong+"宫生时干"+shitpgong+"宫，为主动去买货, 测要租房为特别想租下来；";
		else if(my.isChongke(ritpgong,shitpgong)) m[n++]="日干落"+ritpgong+"宫克时干"+shitpgong+"宫，能买成，但成交迟缓；";
		else if(shitpgong==ritpgong) m[n++]="日时同落"+ritpgong+"宫，交易必成；";
		else if(my.isBihe(shitpgong,ritpgong)) m[n++]="时干落"+shitpgong+"宫与日干所落"+ritpgong+"宫比和，公平交易，也主成交；";
		
		if(my.isChongke(shenhegong, ritpgong)) m[n++]="六合落"+shenhegong+"宫冲克日干所落"+ritpgong+"宫，此中必有欺诈；";	
		
		getMoney600();
		//getMoney500();
	}
	private void getMoney503() {
		m[n++]="值符为卖方，值使为买方；";
		m[n++]="日干为卖方，时干为货物；";
		
		if(my.isSheng(zhishigong, zhifugong)) m[n++]="值使落"+zhishigong+"宫生值符"+zhifugong+"宫，货可以卖出去；";
		else if(my.isChongke(zhishigong, zhifugong) ||
				my.isChongke(zhishigong, zhifugong)) 
			m[n++]="值使落"+zhishigong+"宫与值符所落"+zhifugong+"宫相冲克，货卖不出去；";
		else if(my.isSheng(zhifugong,zhishigong)) m[n++]="值符落"+zhifugong+"宫生值使"+zhishigong+"宫，货卖不出去；";
		else if(zhishigong==zhifugong) m[n++]="值符与值使同落"+zhishigong+"宫，货可以卖出去；";
		else if(my.isBihe(zhishigong,zhifugong)) m[n++]="值使落"+zhishigong+"宫与值符所落"+zhifugong+"宫比和，公平交易，货可以卖出去；";
		
		if(my.isSheng(shitpgong, ritpgong)) m[n++]="时干落"+shitpgong+"宫生日干"+ritpgong+"宫，货恋人，卖不出去；";
		else if(my.isChongke(shitpgong, ritpgong)) m[n++]="时干落"+shitpgong+"宫克日干"+ritpgong+"宫，卖得快；";
		else if(my.isSheng(ritpgong,shitpgong)) m[n++]="日干落"+ritpgong+"宫生时干"+shitpgong+"宫，人恋货，因价格低货主不愿意卖；";
		else if(my.isChongke(ritpgong,shitpgong)) m[n++]="日干落"+ritpgong+"宫克时干"+shitpgong+"宫，货主急着出货，但成交迟缓；";
		else if(shitpgong==ritpgong) m[n++]="日时同落"+ritpgong+"宫，交易必成；";
		else if(my.isBihe(shitpgong,ritpgong)) m[n++]="时干落"+shitpgong+"宫与日干所落"+ritpgong+"宫比和，公平交易，也主成交；";
		
		
		if(my.isChongke(shenhegong, ritpgong)) m[n++]="六合落"+shenhegong+"宫冲克日干所落"+ritpgong+"宫，此中必有欺诈；";
		if(my.isChongke(shenhegong, zhifugong)) m[n++]="六合落"+shenhegong+"宫冲克值符所落"+zhifugong+"宫，此中必有欺诈；";
		
		getMoney600();
		//getMoney500();
	}
	private void getMoney504() {
		m[n++]="买卖（包括租赁）房地产一般以值符、日干为买房（或卖房）之人；";
		m[n++]="以生门为房屋，以死门为地皮；或者以时干为房屋或地皮；";
		
		if(ystpgong==shengmengong) m[n++]="用神与生门同落"+shengmengong+"宫，说明一定能买到商位；";
		else if(ystpgong==kaimengong) m[n++]="用神与开门同落"+kaimengong+"宫，说明一定能买到商位；";
		if(shengmengong==1) m[n++]="生门落1宫，可能买或租到一楼的商铺；";		
		
		int[] a = (zhifugong==ritpgong) ? new int[]{zhifugong} : new int[]{zhifugong, ritpgong};
		String[] aname = (zhifugong==ritpgong) ? new String[]{"值符、日干"} : new String[]{"值符", "日干"};
		int[] b = {shengmengong,simengong,shitpgong==shengmengong || shitpgong==simengong?0:shitpgong};
		String[] bname = {"生门","死门",shitpgong==shengmengong || shitpgong==simengong?null:"时干"};
		if(shitpgong==shengmengong) bname[0]+="、时干";
		if(shitpgong==shitpgong) bname[1]+="、时干";
		String[] bname2 = {"房屋","地皮",shitpgong==shengmengong || shitpgong==simengong?null:"房屋和地皮"};
		int[] good = {0,0,0};
		
		//门旺有吉格好，门休囚又凶格凶，其它如门旺无吉格或门休囚有吉格一般，
		for(int wu=0; wu<b.length; wu++) {				
			if(b[wu]==0) continue;			
			String[] ws = my.getmenWS(b[wu]);        //门的旺衰
			String[] jige = my.getJixiongge(b[wu]);  //宫的吉凶格
			if(ws[0].equals("1") && jige[0].equals("1")) {
				m[n++]=bname[wu]+"落"+b[wu]+"宫，"+ws[1]+jige[1]+"说明"+bname2[wu]+"好；";
				good[wu] = 1;
			}
			else if(!ws[0].equals("1") && !jige[0].equals("1")) {
				m[n++]=bname[wu]+"落"+b[wu]+"宫，"+ws[1]+jige[1]+"说明"+bname2[wu]+"很差；";
				good[wu] = 2;
			}
			else {
				m[n++]=bname[wu]+"落"+b[wu]+"宫，"+ws[1]+jige[1]+"说明"+bname2[wu]+"一般；";
				good[wu] = 3;
			}
		}
		
		//值符与生死门、日干与生死门、日干与时干；没有值符与时干的关系
		for(int ren=0; ren<a.length; ren++) {
			for(int wu=0; wu<b.length; wu++) {	
				if(b[wu]==0) continue;			
				if(a[ren]==zhifugong && b[wu]==shitpgong && a.length==2 && b.length==3) continue;
				String[] rs = new String[]{good[wu]==1?"对买主有利，买后可发达":"对卖主有利，卖后发达",
								good[wu]==2?"买后破家败财":"不吉之象",
								good[wu]==2?"买后必破败不利":"不吉之象",
								"不吉之象","主平安","主平安"};
				m[n++]=getMoney700(b[wu],bname[wu],a[ren],aname[ren],rs);
			}
		}		
		//getMoney500();
	}
	private void getMoney505() {	
		//共性
		if(my.isWuyangshi()) {
			m[n++]= YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"为五阳时，利客；";
		}else
			m[n++]= YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"为五阴时，利主；";
		if(my.isMenFan()) m[n++]= "八门反呤，利客；";
		else if(my.isMenFu()) m[n++]= "八门伏呤，利主；";
		if(my.isXingFan()) m[n++]= "九星反呤，利客；";
		else if(my.isXingFu()) m[n++]= "九星伏呤，利主；";
		
		String[] kaimenhj = my.ismenHeju(kaimengong);
		if(!kaimenhj[0].equals("1")) m[n++]= "开门落"+kaimengong+"宫失局，主破财，利客；"+kaimenhj[1];
		else m[n++]= "开门落"+kaimengong+"宫合局，利主；"+kaimenhj[1];
		String[] shiheju = my.ismenHeju(shitpgong);
		if(!shiheju[0].equals("1")) m[n++]= "时干落"+kaimengong+"宫失局，不利客；"+shiheju[1];
		else m[n++]= "时干落"+kaimengong+"宫合局，利客；"+shiheju[1];
		
		//测抽奖
		m[n++]="1）测抽奖是否出大奖：用神落"+ystpgong+"宫：";
		m[n++]="    按奖品定用神（如汽车=伤门），地盘天干为主为设奖人，天盘天干为客为抽奖人；";
		m[n++]="    时干为抽奖人，开门为主办单位；";
		int[] tpjy = my.getTpjy(ystpgong);
		int[] dpjy = my.getDpjy(ystpgong);
		for(int t1 : tpjy) 
			for(int d1 : dpjy) {
				if(t1==0 || d1==0) continue;
				String tname = YiJing.TIANGANNAME[t1];
				String dname = YiJing.TIANGANNAME[d1];
				m[n++]="    "+my.gettgWS(t1, 0)[1]+my.gettgWS(d1, 0)[1];
				if(YiJing.WXDANKE[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[d1]]==1) {
					m[n++]="    天盘干["+tname+"]克地盘干["+dname+"]，利客；";
				}else if(YiJing.WXDANKE[YiJing.TIANGANWH[d1]][YiJing.TIANGANWH[t1]]==1) {
					m[n++]="    地盘干["+dname+"]克天盘干["+tname+"]，利主；";
				}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[d1]]==1) {
					m[n++]="    天盘干["+tname+"]生地盘干["+dname+"]，利主；";
				}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[d1]][YiJing.TIANGANWH[t1]]==1) {
					m[n++]="    地盘干["+dname+"]生天盘干["+tname+"]，利客；";
				}else {
					m[n++]="    地盘干["+dname+"]与天盘干["+tname+"]比和，无所利；";
				}
			}

		
		
		m[n++]= "    "+getMoney700(shitpgong,"时干",ystpgong,"奖品",new String[]{"利客","利客","利客","利主","利客","利客"});
		m[n++]= "    "+getMoney700(shitpgong,"时干",kaimengong,"开门",new String[]{"利主","利客","利客","利主","利主","利主"});
		
		//测自摸彩票
		m[n++]="2）问测彩票：用神落"+ystpgong+"宫：";
		m[n++]= "    丁奇为彩票，以日干为抽奖人，开门为主办单位，时干或值使主事体；";
		String[] riheju = my.ismenHeju(ritpgong);
		if(!riheju[0].equals("1")) m[n++]= "    日干落"+kaimengong+"宫失局，不吉；"+riheju[1];
		else m[n++]= "    日干落"+kaimengong+"宫合局，大吉；"+riheju[1];
		
		m[n++]= "    "+getMoney700(dingtpgong,"丁奇",ritpgong,"日干",new String[]{"吉","不吉","吉","不吉","吉","吉"});
		m[n++]= "    "+getMoney700(ritpgong,"日干",kaimengong,"开门",new String[]{"不中之象","中奖之象","中奖之象","不中之象","不中之象","不中之象"});
		m[n++]= "    "+getMoney700(shengmengong,"生门",ritpgong,"日干",new String[]{"中彩之象","不中之象","不中之象","不中之象","中彩之象","中彩之象"});
		m[n++]= "    "+getMoney700(shengmengong,"生门",wutpgong,"戊干",new String[]{"必获利","定赔本","赔本之象","赔本之象","必获利","必获利"});
		
		//getMoney500();
	}
	private void getMoney506() {
		m[n++]= "买门面、商位，开办工厂等坐地经商，日干为求测人，时干为顾客；";
		m[n++]= "测投资，日干为融资方，时干为投资方；";
		m[n++]= "开门、生门为商场、工厂、酒店；可参考交易租赁一节；  ";
		m[n++]= "值使、时干为事体； ";
		m[n++]= "景门为合同、证件,丁奇为开工证；";
		
		int[] tpjy = my.getTpjy(ystpgong);
		int[] dpjy=my.getDpjy(ystpgong);
		if(my.isChongke(ystpgong, kaimengong)) {
			t=null;
			if(my.isGeju(ystpgong, 107)) t="宫中逢庚加壬移荡格；";			
			else if(tpjy[0]==YiJing.REN ||tpjy[1]==YiJing.REN ||dpjy[0]==YiJing.REN ||dpjy[1]==YiJing.REN)
				t="宫中临壬主流动；";
			else if(ysjxge[0].equals("-1")) t="宫中逢"+ysjxge[1];
			if(t!=null) m[n++]= "用神落"+ystpgong+"宫冲克开门所落"+kaimengong+"宫，事情也能办成；但"+t+"主所干时间不会太长；";
		}
		
		String kaimenname = zhishigong==kaimengong && shitpgong==kaimengong ?"开门、值使、时干":zhishigong==kaimengong?"开门、值使":shitpgong==kaimengong?"开门、时干":"开门";
		String shengmenname = zhishigong==shengmengong && shitpgong==shengmengong ?"生门、值使、时干":zhishigong==shengmengong?"生门、值使":shitpgong==shengmengong?"生门、时干":"生门";
		m[n++]= getMoney700(kaimengong,kaimenname,ystpgong,"用神",new String[]{"事成之象","必然破耗折本，乘凶神凶格者更凶","主动求财，吉","主动求财，吉","志在必得之象","次吉"});
		m[n++]= getMoney700(shengmengong,shengmenname,ystpgong,"用神",new String[]{"事成之象","必然破耗折本，乘凶神凶格者更凶",	"主动求财，吉","主动求财，吉","志在必得之象","次吉"});
		if(zhishigong!=kaimengong && zhishigong!=shengmengong)
			m[n++]= getMoney700(zhishigong,"值使",ystpgong,"用神",new String[]{"事成之象","必然破耗折本，乘凶神凶格者更凶","主动求财，吉","主动求财，吉","志在必得之象","次吉"});
		if(shitpgong!=kaimengong && shitpgong!=shengmengong && shitpgong!=zhishigong)
			m[n++]= getMoney700(shitpgong,"时干",ystpgong,"用神",new String[]{"事成之象","必然破耗折本，乘凶神凶格者更凶",	"主动求财，吉","主动求财，吉","志在必得之象","次吉"});
		
		m[n++]= getMoney700(shitpgong,"时干",kaimengong,"开门",new String[]{"顾客多","顾客少",	"顾客多","顾客少","顾客多","顾客多"});
		m[n++]= getMoney700(shitpgong,"时干",ritpgong,"日干",new String[]{"继续投资","不再投资",	"继续投资","不再投资","继续投资","继续投资"});
		m[n++]= getMoney700(shitpgong,"时干",wutpgong,"戊干",new String[]{"投资方有款","投资方暂时无款",	"投资方有款","投资方暂时无款","投资方有款","投资方有款"});
		m[n++]= getMoney700(ritpgong,"日干",wutpgong,"戊干",new String[]{"融资方急需资金","融资方资金难求",	"融资方能得到投资","融资方得不到投资","融资方能得到投资","融资方能得到投资"});
		
		int jing3mengong = my.getMenGong(QiMen.MENJING3);
		if(my.isKong(jing3mengong)) m[n++]= "景门落"+jing3mengong+"宫逢空亡，表明还没有签约；填或充实之日可以签订；";
		if(my.isKong(dingtpgong)) m[n++]= "景门落"+dingtpgong+"宫逢空亡，表明还没有签约；填或充实之日可以签订；";
		if(my.isTGanMu(YiJing.DING, 0)) m[n++]= "丁奇落"+dingtpgong+"宫入墓，待充起之日方可以签订合同或办下开工证等证件；";
		String kaiws = QiMen.gong_yue[kaimengong][SiZhu.yz];
		if(kaiws.equals(QiMen.wxxqs3) || kaiws.equals(QiMen.wxxqs4) || kaiws.equals(QiMen.wxxqs5)) 
			m[n++]="开门落"+kaimengong+"宫，在月令为"+kaiws+"地，待旺相之月可成！";
		
		
		//getMoney500();
	}
	private void getMoney507() {
		m[n++]= "日干为我方，时干为合伙之人；";
		m[n++]= "地盘日干为我方，以上乘天盘之干为合伙之人";
		
		m[n++]= "1) 日干与时干生克断：";
		m[n++]= getMoney700(shitpgong,"时干",ystpgong,"日干",new String[]{"对我有利","对我不利",	"对他有利","对他不利","共盈","公平"});
		m[n++]= getMoney700(shengmengong,"生门",ystpgong,"日干",new String[]{"吉","凶","平","平","吉","平"});
		
		m[n++]= "2) 地盘日干宫断：";
		int[] tpjy = my.getTpjy(ysdpgong); //得到用神地盘宫上的天盘奇仪
		for(int t1 : tpjy) {
			if(t1==0) continue;
			String tname = YiJing.TIANGANNAME[t1];
			String dname = YiJing.TIANGANNAME[ysgan];
			m[n++]=my.gettgWS(t1, 0)[1]+my.gettgWS(ysgan, yszi)[1];
			if(YiJing.WXDANKE[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[ysgan]]==1) {
				m[n++]="天盘干["+tname+"]克地盘干["+dname+"]，事不成，成则不顺利；";
			}else if(YiJing.WXDANKE[YiJing.TIANGANWH[ysgan]][YiJing.TIANGANWH[t1]]==1) {
				m[n++]="地盘干["+dname+"]克天盘干["+tname+"]，事不成，成则不顺利；";
			}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[t1]][YiJing.TIANGANWH[ysgan]]==1) {
				m[n++]="天盘干["+tname+"]生地盘干["+dname+"]，对我方有利；";
			}else if(YiJing.WXDANSHENG[YiJing.TIANGANWH[ysgan]][YiJing.TIANGANWH[t1]]==1) {
				m[n++]="地盘干["+dname+"]生天盘干["+tname+"]，对他方有利；";
			}else 
				m[n++]="地盘干["+dname+"]与天盘干["+tname+"]比和，公平；";
		}
		int gong2 = my.getTianGong(tpjy[0], 0);  //地盘日干上乘之干落宫
		String gname = YiJing.TIANGANNAME[tpjy[0]];  //地盘
		m[n++]= "3) 合伙人断：";
		m[n++]= gname+"所在天盘干落"+gong2+"宫为合伙人的方位，其格局也可看合伙人的近况；";
		getMoney31(gong2);
		m[n++]= "4) 投资数断：";
		m[n++]= "戊干所落"+wutpgong+"宫，可断投资数";
		
		//getMoney500();
	}
	private void getMoney508() {
		m[n++]="值符为款主或银行，以值使为借贷之人；";
		m[n++]="又可以所往之方的天盘为借贷之人；地盘为款主或银行；";
		
		if(my.isMenFan() || my.isXingFan()) m[n++]="遇反吟，主半途而废；";
		if(simengong==zhishigong) m[n++]="死门为值使，不吉之象；";
		
		m[n++] = "1) 看值符与值使：";
		m[n++]= getMoney700(zhifugong,"值符",zhishigong,"值使",new String[]{"可贷到款","借贷不成","借贷不成","可贷到款","平","平"});
		if(my.isKong(zhifugong) && my.isKong(zhishigong))
			m[n++]= getMoney800(new int[]{zhifugong,zhishigong},new String[]{"值符","值使"},"均落空亡，借贷必不成");
		else if(my.isKong(zhishigong))
			m[n++]= getMoney800(new int[]{zhishigong},new String[]{"值使"},"逢空亡，借贷必不成");
		else if(my.isKong(zhifugong))
			m[n++]= getMoney800(new int[]{zhifugong},new String[]{"值符"},"逢空亡，借贷必不成");
		
		m[n++] = "2) 看所往之方，"+yshenname+"：";
		if(my.isKong(ystpgong)) m[n++] ="该方落"+ystpgong+"宫空亡之地，说明款主无钱或人不在家，去了也借贷不成；";
		int txing = daoqm.gInt[2][1][ystpgong]; //所往之方天盘星
		int dxing = ystpgong; //所往之方地盘星
		m[n++] = "天盘星为["+QiMen.jx1[txing]+"]，地盘星为["+QiMen.jx1[dxing]+"]：";
		m[n++] = getXingRel(dxing,txing,new String[]{"借贷必成","不借反爱辱","平","不借反爱辱","借贷但必迟缓"});
		if(my.isTGanMu(ysgan,yszi)) m[n++] = "天盘星所临干["+YiJing.TIANGANNAME[ysgan]+"]入墓库，说明款主吝啬，不肯借";
		if(my.getChongGong(zhishigong)==shengmengong) m[n++] = "值使落"+zhishigong+"对冲生门所落"+shengmengong+"宫，表明是想借钱做生意；";
		
		//getMoney500();
	}
	private void getMoney509() {
		m[n++]="值符为银行为放贷人，天乙星为借贷人，生门为利息。";		
		String tyname = "["+QiMen.jx1[ty]+"]";
		m[n++]="1）值符与天乙断：";
		m[n++]= "    值符落"+QiMen.dpjg[zhifugong]+"宫，天乙["+QiMen.jx1[ty]+"]落"+tygong+"宫：";
		m[n++] = "    "+getGongGongRel(zhifugong,tygong,new String[]{"凶","吉","吉","凶","平"});
		
		m[n++]="2）值符与天乙、生门断：";
		if(my.isChongke(shengmengong,zhifugong) && YiJing.WXDANKE[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    生门落"+shengmengong+"宫与"+tyname+"落"+tygong+"宫，同克值符"+zhifugong+"宫，放出之款损失殆尽；";
		else if(my.isSheng(shengmengong,zhifugong)	&& YiJing.WXDANSHENG[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    生门落"+shengmengong+"宫与"+tyname+"落"+tygong+"宫，同生值符"+zhifugong+"宫，本息全还；";
		else if(my.isBihe(shengmengong,zhifugong)	&& YiJing.WXDANSHENG[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    生门落"+shengmengong+"宫与值符比和，"+tyname+"落"+tygong+"宫生值符"+zhifugong+"宫，本息全还；";
		else if(shengmengong==zhifugong	&& YiJing.WXDANSHENG[QiMen.jgwh[tygong]][QiMen.jgwh[zhifugong]]!=0)
			m[n++] = "    生门与值符同宫，"+tyname+"落"+tygong+"宫生值符"+zhifugong+"宫，本息全还；";
		else 
			m[n++] = "    生门落"+shengmengong+"宫，"+tyname+"落"+tygong+"宫，一生一克值符"+zhifugong+"宫，借出之款不能全还或迟还；";
		
		String tyws = QiMen.xing_gong[ty][zhifugong];
		if(!tyws.equals(QiMen.wxxqs1) && !tyws.equals(QiMen.wxxqs2))
			m[n++] = "天乙"+tyname+"在"+zhifugong+"宫处"+tyws+"地，是无力偿还，结局是不全还或迟还；";
		//getMoney500();
	}
	private void getMoney510() {
		m[n++]="值符为放债之人，天乙星、天蓬星为欠债人，伤门为讨债人，白虎为委托讨债之人，六合为经纪人；";
		
		if(my.isMenFu()) m[n++] = "八门伏呤，主躲藏；";
		if(my.isXingFu()) m[n++] = "九星星伏呤，主躲藏；";
		
		String[] tyws = my.getGongWS(tygong);
		int[] tytpjy = my.getTpjy(tygong);
		m[n++] = "1) 伤门与天乙断；";
		m[n++] = "    伤门落"+shangmengong+"宫，天乙["+QiMen.jx1[ty]+"]落"+tygong+"宫：";
		if(my.isChongke(shangmengong, tygong)) m[n++] = "    伤门克天乙，讨债人实心实意去讨；";
		else if(my.isChongke(tygong,shangmengong)) {
			t= "    天乙克伤门，彼此争斗不服；";
			if(tyws[0].equals("1")) t+="又落宫"+tyws[1]+"，说明是有能力却存心不还；";			
			m[n++]  = t;
		}else if(my.isSheng(tygong, shangmengong)) {
			t = "天乙生伤门，真心想还；";
			if(!tyws[0].equals("1")) t+="但落宫"+tyws[1]+"，有心无力，即使还款，也不能还全；";
			m[n++]  = t;
		}else if(my.isSheng(shangmengong, tygong)) m[n++] = "    伤门生天乙，讨债人不想讨；";
		else if(shangmengong==tygong) m[n++] = "    伤门与天乙同宫，讨债人与欠债人串通一气；";
		else if(my.isBihe(shangmengong, tygong)) m[n++] = "    伤门与天乙比和，讨债人与欠债人串通一气；";
		
		m[n++] = "2) 值符与天乙断；";
		m[n++] = "    值符落"+zhifugong+"宫，天乙["+QiMen.jx1[ty]+"]落"+tygong+"宫：";
		if(my.isChongke(zhifugong, tygong)) {
			t = "    值符克天乙，放债人执意讨债；";
			if(zhifugong==dingtpgong && zhifugong==4) t+= "值符克天乙乘丁奇落四宫（巽四宫杜门主执法机关），有经官之事；";
			if(zhifugong==jing3mengong && zhifugong==4) t+= "值符克天乙临景门落四宫（巽四宫杜门主执法机关），有经官之事；";
			m[n++] = t;
		}
		else if(my.isChongke(tygong,zhifugong)) {
			t= "    天乙克值符，不还之象；";
			if(tygong==shenwugong) t+="又上乘玄武，存心不还；";
			if(tygong==xingpenggong) t+="又临天蓬星，存心不还；";
			if(tytpjy[0]==YiJing.GENG || tytpjy[1]==YiJing.GENG) t+="又上乘庚来克值符，必有经官之事；";
			if(tytpjy[0]==YiJing.XIN || tytpjy[1]==YiJing.XIN) t+="又上乘辛来克值符，必有经官之事；";
			m[n++]  = t;
		}else if(my.isSheng(tygong, shangmengong)) {
			t = "天乙生伤门，真心想还；";
			if(!tyws[0].equals("1")) t+="但落宫"+tyws[1]+"，有心无力，即使还款，也不能还全；";
			m[n++]  = t;
		}else if(my.isSheng(zhifugong, tygong)) m[n++] = "    值符生天乙，现在不想讨；";
		else if(zhifugong==tygong) m[n++] = "    值符与天乙同宫，债主与欠债人已协商妥当；";
		else if(my.isBihe(zhifugong, tygong)) m[n++] = "    值符与天乙比和，债主与欠债人已协商妥当；";
		
		m[n++] = "3) 伤门、天乙与值符：";
		m[n++] = "    值符落"+zhifugong+"宫，天乙["+QiMen.jx1[ty]+"]落"+tygong+"宫，伤门落"+shangmengong+"宫：";
		if(my.isSheng(tygong, zhifugong) && my.isSheng(shangmengong, zhifugong))
			m[n++] = "    伤门与天乙同生值符，本息全能追回；";
		else if(my.isChongke(tygong, zhifugong) && my.isChongke(shangmengong, zhifugong))
			m[n++] = "    伤门与天乙同克值符，本息不还；";
		else if(my.isSheng(shangmengong, zhifugong) && my.isChongke(shangmengong, tygong))
			m[n++] = "    伤门生值符，克天乙， 能讨回；";
		else if(my.isSheng(shangmengong, tygong) && my.isChongke(shangmengong, zhifugong))
			m[n++] = "    伤门生天乙，克值符，讨不回；";
		else 
			m[n++] = "    伤门、天乙、值符三者关系不妙，难讨回；";
		
		if(wutpgong==kaimengong && my.isNenpan(wutpgong)) {
			m[n++] = "甲子戊落"+wutpgong+"宫，会开门，又落内盘，其债速还；";
		}			
		if(my.isNenpan(shangmengong) && my.isNenpan(zhifugong) && my.isNenpan(tygong)) 
			m[n++] = "值符落"+zhifugong+"宫，天乙["+QiMen.jx1[ty]+"]落"+tygong+"宫，伤门落"+shangmengong+"宫，均在内盘，速度快；";
		else
			m[n++] = "值符落"+zhifugong+"宫，天乙["+QiMen.jx1[ty]+"]落"+tygong+"宫，伤门落"+shangmengong+"宫，有内有外，速度慢；";
		
		//m[n++] = "；";
		//getMoney500();
	}
	
	private String getGongGongRel(int g1, int g2, String[] rs) {
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
	
	private String getXingGongRel(int g, int x, String[] rs) {
		String res = null;
		String name1 = "["+QiMen.dpjg[g]+"]";
		String name2 = "["+QiMen.jx1[x]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jgwh[g]][QiMen.jx3[x]]!=0) res=name1+"生"+name2+"，"+rs[0]+"；";
		else if(YiJing.WXDANKE[QiMen.jgwh[g]][QiMen.jx3[x]]!=0) t=name1+"克"+name2+"，"+rs[1]+"；";
		else if(YiJing.WXDANSHENG[QiMen.jx3[x]][QiMen.jgwh[g]]!=0) res=name2+"生"+name1+"，"+rs[2]+"；";
		else if(YiJing.WXDANKE[QiMen.jx3[x]][QiMen.jgwh[g]]!=0) t=name2+"克"+name1+"，"+rs[3]+"；";
		else  res=name1+"与"+name2+"比和，"+rs[4]+"；";
		
		return res;
	}
	
	private String getXingRel(int x1, int x2, String[] rs) {
		String res = null;
		String name1 = "["+QiMen.jx1[x1]+"]";
		String name2 = "["+QiMen.jx1[x2]+"]";
		
		if(YiJing.WXDANSHENG[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"生"+name2+"，"+rs[0]+"；";
		else if(YiJing.WXDANKE[QiMen.jx3[x1]][QiMen.jx3[x2]]!=0) res=name1+"克"+name2+"，"+rs[1]+"；";
		else if(YiJing.WXDANSHENG[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"生"+name1+"，"+rs[2]+"；";
		else if(YiJing.WXDANKE[QiMen.jx3[x2]][QiMen.jx3[x1]]!=0) res=name2+"克"+name1+"，"+rs[3]+"；";
		else  res=name1+"与"+name2+"比和，"+rs[4]+"；";
		
		return res;
	}
	
	private String getMoney800(int[] g1, String[] gname, String desc) {
		StringBuilder sb = new StringBuilder(gname[0]+"落"+g1[0]+"宫");
		for(int i=1; i<g1.length; i++) {
			sb.append("、"+gname[i]+"落"+g1[i]+"宫");
		}
		return sb.append("，"+desc).toString();
	}
	private String getMoney700(int g1, String name1, int g2, String name2, String[] rs) {
		t = null;
		//if(g1==5) g1 = my.getJiGong528(gong)
		if(my.isSheng(g1, g2)) t=name1+"落"+g1+"宫生"+name2+"所落"+g2+"宫，"+rs[0]+"；";
		else if(my.isChongke(g1, g2)) t=name1+"落"+g1+"宫冲克"+name2+"所落"+g2+"宫，"+rs[1]+"；";
		else if(my.isSheng(g2,g1)) t=name2+"落"+g2+"宫生"+name1+"所落"+g1+"宫，"+rs[2]+"；";
		else if(my.isChongke(g2,g1)) t=name2+"落"+g2+"宫冲克"+name1+"所落"+g1+"宫，"+rs[3]+"；";
		else if(g1==g2) t=name1+"与"+name2+"同落"+g2+"宫，"+rs[4]+"；";
		else if(my.isBihe(g1,g2)) t=name1+"落"+g1+"宫与"+name2+"所落"+g2+"宫比和，"+rs[5]+"；";
		return t;
	}
	
	private void getMoney600() {
		//时干与生门、戊断
		String[] shiheju = my.isganHeju(SiZhu.sg, SiZhu.sz);
		if(my.isSheng(shitpgong, shengmengong) || my.isSheng(shengmengong,shitpgong)) m[n++] = "时干落"+shitpgong+"宫与生门所落"+shengmengong+"宫相生，有利可图；";
		else if(my.isChongke(shitpgong, shengmengong) || my.isChongke(shengmengong, shitpgong))
			m[n++] = "时干落"+shitpgong+"宫与生门所落"+shengmengong+"宫相冲克，无利可图；"+(shiheju[0].equals("1")?"":"又时宫失局，必赔本；");
		else if(shitpgong==shengmengong) m[n++]="时干与生门同落"+shengmengong+"宫，有利可图；";
		else if(my.isBihe(shitpgong,shengmengong)) m[n++]="时干落"+shitpgong+"宫与生门所落"+shengmengong+"宫比和，可获利；";
		
		if(my.isSheng(shitpgong, wutpgong) || my.isSheng(wutpgong,shitpgong)) m[n++] = "时干落"+shitpgong+"宫与戊所落"+wutpgong+"宫相生，有利可图；";
		else if(my.isChongke(shitpgong, wutpgong) || my.isChongke(wutpgong, shitpgong))
			m[n++] = "时干落"+shitpgong+"宫与戊所落"+wutpgong+"宫相冲克，无利可图；"+(shiheju[0].equals("1")?"":"又时宫失局，必赔本；");
		else if(shitpgong==wutpgong) m[n++]="时干与戊同落"+wutpgong+"宫，有利可图；";
		else if(my.isBihe(shitpgong,wutpgong)) m[n++]="时干落"+shitpgong+"宫与戊所落"+wutpgong+"宫比和，可获利；";
	}
	
	private void getMoney500() {
		boolean isrinei = my.isNenpan(ritpgong);
		boolean isshinei = my.isNenpan(shitpgong);
		if(isrinei && isshinei) 
			m[n++]="日干落"+ritpgong+"宫，时干落"+shitpgong+"宫，均为内盘，交易速度快；";
		else if((!isrinei && isshinei) || (isrinei && !isshinei)) 
			m[n++]="日干落"+ritpgong+"宫，时干落"+shitpgong+"宫，一内一外，交易速度慢；";
		else m[n++]="日干落"+ritpgong+"宫，时干落"+shitpgong+"宫，均在外盘，交易速度慢；";
		
		if(my.isKong(shitpgong)) m[n++]="时干落"+shitpgong+"宫空亡，则填实或冲实之日可成；";
		if(my.isTGanMu(shitpgong)) m[n++]="时干落"+shitpgong+"宫入墓，则冲起之日可成；";
		int shiws = SiZhu.TGSWSJ[SiZhu.sg][SiZhu.yz];
		if(shiws>5) m[n++]="时干["+YiJing.TIANGANNAME[SiZhu.sg]+"]落"+shitpgong+"宫，在月令为"+SiZhu.TGSWSJNAME[shiws]+"地，旺相之月可成！";
		int[] shidpjy = my.getDpjy(shitpgong);
		String shidpjyname = YiJing.TIANGANNAME[shidpjy[0]]+(shidpjy[1]==0?"":YiJing.TIANGANNAME[shidpjy[1]]);
		m[n++] = "时干宫下临之干["+shidpjyname+"]也可为应期；";
		int[] zstpjy = my.getTpjy(zhishigong==5?2:zhishigong);
		String zstpjyname = YiJing.TIANGANNAME[zstpjy[0]]+(zstpjy[1]==0?"":YiJing.TIANGANNAME[zstpjy[1]]);
		m[n++] = "值使"+zhishigong+"宫所临之干["+zstpjyname+"]也可为应期；";
	}
	
	/**
	 * 用神、年命、生门、开门、时宫、值符、值使、戊宫
	 * 去掉重复的，放在Map<gong,名称>中返回
	 * @param mingzhu
	 * @param yongshen
	 * @return
	 */
	private void getYShen(List<Integer> ysgong, List<String> ysname) {			
		String mname = mtpgong!=0 ? "年命["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]" : null;
		String shiname = "时干宫["+YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+"]"; 
		
		int[] gongs = {ystpgong,mtpgong,kaimengong,shengmengong,shitpgong,zhifugong,zhishigong,wutpgong};
		String theyshen = yshenname.substring(0,yshenname.length()/2)+"宫";
		String[] ysdesc = {theyshen,mname,"开门宫","生门宫",shiname,"值符宫","值使宫","戊干宫"};
		for(int g=0; g<gongs.length; g++) {
			if(gongs[g]==5) gongs[g]=2;  //五宫寄2宫
			if(gongs[g]==0) continue;
			int index = -1;
			if((index=ysgong.indexOf(gongs[g]))!=-1) {
				ysname.set(index, ysname.get(index) + "," + ysdesc[g]);
			}	else {
				ysgong.add(gongs[g]);
				ysname.add(ysdesc[g]);
			}
		}
	}
	private void w(String text) {
		m[n++] = text;
	}
	private void getMoney40() {
		w("1) 求测人状况断：");
		if(ystpgong==dumengong) w("用神落"+ystpgong+"宫逢杜门，本人比较能干，但不愿意卖货，原因是卖出去货收不回资金。");
		if(my.isTaohua(ystpgong)) w("落宫为沐浴之地，沐浴为桃花败地，表示有欲望，光想好事，主财欲、桃花；");
		if(ystpgong==kaimengong && ystpgong==shenhegong) w("用神遇开门乘六合，为有两份以上的工作。");
		if(ystpgong==jing1mengong) w("逢惊门主担心；");
		if(my.isKong(ystpgong)) w("落空亡心中没有底；");
		if(ystpgong==bingtpgong) w("临丙有权威，自己说了算。");
		if(ystpgong==xintpgong) w("临辛主错误、违法；");
		if(ystpgong==xingruigong) w("乘天芮星，主贪心；");
		if(ystpgong==shangmengong) w("乘伤门，主态度强横；");
		if(ystpgong==shenwugong) w("日干上乘玄武，有减税免税的情况。");
		if(ystpgong==xintpgong && ystpgong==shenwugong) w("玄武加辛，一贯不讲道理；");
		if(ystpgong==shenwugong && ystpgong==dumengong) w("玄武和杜门同宫，一定是说假话。");
		if(ystpgong==niantpgong || ystpgong==niandpgong) {
			if(ystpgong==xiumengong) w("日干临太岁，断有后台，临休门为政府部门的高官；");
			else w("日干临太岁，断有后台；");
		}
		if(ystpgong==shenwugong && 
				(my.isSheng(ystpgong, zhifugong) || my.isSheng(ystpgong, zhishigong)))
			w("乘玄武，生值符或值使，有走后门，送礼。");
		if(ystpgong==xingyinggong) w("天英星代表性子急。");
		if(ystpgong==jing3mengong) w("临景门漂亮、文化素质高，但个性倔强；");
		if(ystpgong==shenhugong) w("乘白虎脾气不好。");
		m[n++]=my.NEWLINE;
		
		w("2) 企业状况断：");
		if(my.isMenFu() || my.isXingFu()) w("大局伏吟，主破财伤人，当前不赚钱。");
		if(my.isMenFu() || my.isXingFu() || my.isMenFan() || my.isXingFan())
			w("反吟、伏吟风水总不祥，说明工厂的风水不好。");
		String[] kmhj = my.ismenHeju(kaimengong);
		if(kmhj[0].equals("1")) {
			w("开门落"+kaimengong+"宫合局，为大企业，效益好。"+kmhj[1]);
		}else{
			w("开门落"+kaimengong+"宫失局，为小企业，或效益一般。"+kmhj[1]);
		}
		if(my.isKong(kaimengong)) {
			if(kmhj[0].equals("1")) w("开门遇空亡，主企业停产，但格局好可能是半停产。");
			else if(kmhj[0].equals("-1"))w("开门遇空亡，主企业停产，又完全失局，为已破产。");
			else w("开门遇空亡，格局 一般，主企业停产。");
		}
		if(my.isYima(kaimengong) || my.isChMa(kaimengong, my.SHIKONGWANG)) w("开门乘马星或受马星落宫冲，企业迁址。");
		if(kaimengong==shenshegong) w("开门为工厂上乘腾蛇，说明工厂在一个弯曲比较多的地方设立。");
		if(kaimengong==shenhugong) w("开门上乘白虎，主宽敞。");
		if(QiMen.men_gong[QiMen.MENKAI][kaimengong].equals(QiMen.zphym5)) w("开门入墓，不利。");
					
		if(my.isSheng(kaimengong, ystpgong)) w("开门生日干，单位离不开本人，岗位离不开本人");
		else if(my.isChongke(kaimengong, ystpgong)) w("开门冲克日干宫，主单位不要当事人，乘凶神，凶格更差。");
		else w("用神生开门，比和、同宫，要比开门生用神差，表示非单位骨干，并非单位离不开本人，一般员工而矣；");
		
		w("时干落"+shitpgong+"宫，为部属、工人、员工。");
		if(my.isKong(shitpgong)) w("时干逢空亡，企业人员少。");
		if(shitpgong==shendigong) w("时干乘九地，企业人员臃肿，办事效率太低。");
		if(QiMen.gan_gong_wx[my.getTiangan(SiZhu.sg,SiZhu.sz)][shitpgong].indexOf("官")!=-1) w("时干乘禄位，员工收入高。");
		if(shitpgong==xingruigong || shitpgong==jitpgong || shitpgong==xintpgong)
			w("时干逢己、逢天芮，逢辛，员工有不合理的要求。");
		if(shitpgong==shenwugong) w("时干乘玄武，有偷摸、贪污占便宜、不当行为。");
		if(shitpgong==xiumengong) w("时干逢休门，格局又凶，员工工作量不饱满，闲散、懒惰。");
		if(shitpgong==xingchonggong) w("时干逢天冲星，工作效率高。");
		if(shitpgong==xingzhugong) w("时干逢天柱星，员工易发生工伤事故。");
		if(shitpgong==zhifugong) w("时干乘值符，为人正派，组织性强，干劲足。");

		m[n++]=my.NEWLINE;
		
		w("3) 计划状况断：");
		w("景门落"+jing3mengong+"宫，为计划，主战略、规划、方案、战术、决策、广告、规章制度、账目、主意、点子。");
		String[] jing3hj = my.ismenHeju(jing3mengong);
		if(jing3hj[0].equals("1")) w("景门合局，主计划完善、缜密。");
		if(my.isKong(jing3mengong)) w("景门空亡，没有计划，没有制度。");
		if(jing3mengong==xintpgong) w("景门逢辛，主计划有错误，有缺陷。");
		if(jing3mengong==gengtpgong) w("景门逢庚，有阻力，难以实现。");
		Integer[] jing3ge = my.getJixiongge2(jing3mengong, iszf);
		for(int j3ge : jing3ge) {
			if(j3ge==16) w("景门逢悖格，主计划混乱。");
		}
		if(jing3mengong==jitpgong) w("景门逢己，计划不太光明正大。");
		if(my.isTDG3He(jing3mengong) || my.isTDGanHe(jing3mengong)
				|| my.isTDZi3He(jing3mengong) || my.isTG3He(jing3mengong) 
				|| my.isTG6He(jing3mengong)) w("景门中六仪逢合，主计划无法落实。");
		if(my.isYima(jing3mengong) || my.isChMa(jing3mengong, my.SHIKONGWANG)) 
			w("景门宫中逢冲或者遇马星，主计划很快就能实现。");
		if(my.isSheng(jing3mengong, ystpgong) || my.isSheng(jing3mengong, ystpgong))
			w("景门宫生日干或者遇日干比和，计划能实施、能落实。");
		else if(my.isChongke(ystpgong, jing3mengong))
			w("日干克景门宫，计划也可以落实。");
		else if(my.isChongke(jing3mengong, ystpgong) || !jing3hj[0].equals("1"))
			w("景门宫克日干或景门落宫失局，计划落实不了。");
		if(jing3mengong==shenshegong) {
			int[] tpjy = my.getTpjy(jing3mengong);
			int[] dpjy = my.getDpjy(jing3mengong);
			if((tpjy[0]==YiJing.DING || tpjy[1]==YiJing.DING) && 
					(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN))
				w("景门为经营策略，景门逢丁壬淫荡之合，又遇腾蛇，策略不当。");
			else
				w("景门宫乘腾蛇，主变化无常。");
		}
		if(jing3mengong==shenwugong) w("景门宫乘玄武，主计划是虚假不实的，胡编乱造的。");
		if(jing3mengong==shenhugong) w("景门乘白虎，主计划漏洞多。");
		if(jing3mengong==shendigong) w("景门乘九地，主计划实施的慢或计划陈旧。");
		
		if(my.isSheng(ystpgong, shenhegong)) w("日干生六合，自己想发展代理商；");
		if(my.isChongke(shenhegong, shitpgong)) w("六合克时干，代理商不想干代理的事，");
		if(my.isTaohua(shenhegong)){
			if(shenhegong==xingchonggong) w("六合临桃花代理商有欲望，想发大财，而且乘天冲星，想尽快发大财。");
			else w("六合临桃花代理商有欲望，想发大财。");				
		}
		if(dingtpgong==gengtpgong || dingtpgong==gengdpgong) w("丁奇临庚，主手续不好办。");
		if(my.isTGanMu(YiJing.DING, 0)) w("丁奇入墓，办下来还需很长时间。");
		if(zhishigong==xingpenggong || zhishigong==shenwugong) w("值使为具体的办事部门，逢天蓬、玄武为贪心，要好处，要大钱。");
		if(my.isChongke(zhishigong, dingtpgong)) w("值使克手续丁，故意为难，需要送礼。");
		
		m[n++]=my.NEWLINE;
		
		w("4) 资金状况断：");
		if(my.gettgWS(YiJing.WUG, 0)[0].equals("1"))
			w("甲子戊落"+wutpgong+"宫，为企业资本，现旺相为资金多。");
		else w("甲子戊落"+wutpgong+"宫，为企业资本，现处衰地为资金少。");
		if(my.isTDGanHe(wutpgong)) w("戊癸相合，资金被占压。");
		if(wutpgong==xindpgong) w("戊加辛，资金散得太快，支出得太快、留不住资金。");
		if(my.isJixing(wutpgong)) w("遇六仪击刑，主资金受损。");
		if(wutpgong==gengtpgong) w("戊逢庚，主资金缺乏，不到位。");
		if(my.isKong(wutpgong)) {
			if(my.getGongWS(wutpgong).equals("1")	
					&& my.getxingWS(shengmengong)[0].equals("1")) w("戊逢空，但落宫月令旺地，财星旺相，故资金只是暂时短缺。资金缺乏的原因可参看时干产品信息。");
			else w("戊逢空，又其宫在月令处衰地或生门宫财星不旺，表示确实没有资金。资金缺乏的原因可参看时干产品信息。");
		}
		if(my.isTGanMu(YiJing.WUG, 0)) {
			int yuews = SiZhu.TGSWSJ[YiJing.WUG][SiZhu.yz];
			if(yuews<=5) w("戊在月令旺地，落宫为入库，资金暂时短缺。");
			else w("戊在月令休囚，落宫入墓，资金被困。");			
		}
		if(my.isTGanMu(SiZhu.sg, SiZhu.sz)) w("时干入墓，产品库存起来，因销售时机不到卖不出去，货币无法回笼。");
		if(my.isTDG3He(shitpgong) || my.isTDGanHe(shitpgong)
				|| my.isTDZi3He(shitpgong) || my.isTG3He(shitpgong) 
				|| my.isTG6He(shitpgong)) w("时干逢合，产品卖不出去，货币无法回笼。");
		if(shitpgong==shenshegong) w("时干遇腾蛇缠绕，产品卖不出去。");
		if(my.isChongke(shitpgong, ritpgong)) w("时干克日干，应判断产品好销。");
		if(wutpgong==shengmengong && wutpgong==shenhegong) w("戊与生门同宫，上乘六合，说明多渠道赚钱");
		if(wutpgong==xingpenggong && my.isTDChong(wutpgong)) w("戊乘天蓬星，天地盘相冲，资金走得快，现在没有资金。");
		else if(wudpgong==xingpenggong && my.isTDChong(wudpgong)) 
			w("戊地盘落"+wudpgong+"宫乘天蓬星，天地盘相冲，资金走得快，现在没有资金。");
		
		m[n++]=my.NEWLINE;
		
		w("5) 产品状况断：");
		w("时干落"+shitpgong+"宫，为产品，代表商品。");
		String[] shihj = my.isganHeju(SiZhu.sg, SiZhu.sz);
		if(shihj[0].equals("1")) w("时干合局，产品好。"+shihj[1]);
		else w("时干失局，产品质量差。"+shihj[1]);
		if(shitpgong==zhifugong) w("时干逢值符，产品高级、完美。");
		if(shitpgong==shenshegong || my.isTGanMu(SiZhu.sg, SiZhu.sz))
			w("时干逢腾蛇或入墓，产品变质，质量不保。");
		if(shitpgong==shenyingong) w("时干逢太阴，质量好。");
		if(shitpgong==shenhegong) w("时干逢六合，品种多。");
		if(shitpgong==shenwugong) w("时干逢玄武，假货。时干为产品也为对方，乘玄武，货被弄走了，不给钱，难以觅利。");
		if(shitpgong==shenhugong) w("时干逢白虎，残次品，损耗大。"); 
		if(shitpgong==shendigong) w("时干逢九地，存货多，存放时间长。买货逢九地，有利于存货。");
		if(shitpgong==shentiangong) w("时干逢九天，主畅销、远销、名气大。");			
		
		m[n++]=my.NEWLINE;
		
		w("6) 利润状况断：");
		w("生门落"+shengmengong+"宫，代表利润，参看甲子戊。其宫代表所赚利润数量。");
		if(QiMen.gan_gong_wx[my.getTiangan(ysgan,yszi)][ystpgong].indexOf("官")!=-1) w("用神位于禄位，也得利。");
		if(my.isSheng(shengmengong, ystpgong) || my.isBihe(shengmengong, ystpgong)) {
			if(my.isTDG3He(shengmengong) || my.isTDGanHe(shengmengong)
					|| my.isTDZi3He(shengmengong) || my.isTG3He(shengmengong) 
					|| my.isTG6He(shengmengong)) w("生门生日干或比和，本来能赚钱，但生门落宫逢合，暂时没有利润，需等到生门旺或者合被冲开的时候有财。得积极努力，改变经营计划、改变宣传策略、发展代理商。");
			else
				w("生门宫生日干宫或比和，主赚钱。");		
		}
		else if(my.isSheng(ystpgong, shengmengong)) w("用神生生门，经过努力才能获利。");
		if(my.isKong(shengmengong)) w("生门宫空亡，没钱可赚，没有利润或者利润减半。");
		if(my.getxingWS(shengmengong)[0].equals("1"))
			w("生门宫所临之九星为财星，得财多少主要看财星的状况，现旺相为财厚。");
		else 
			w("生门宫所临之九星为财星，得财多少主要看财星的状况，现休囚为财薄。");
		if(wutpgong==shengmengong) w("戊与生门同宫，投资必赢利。");
		if(shengmengong==shenshegong) w("生门上乘腾蛇，钱不好拿。");
		m[n++]=my.NEWLINE;
	}
	private void getMoney50() {
		w("(一)时干落"+shitpgong+"宫，为竞争对手，为谈判对手、为敌方。月干落"+yuetpgong+"宫，为同行，有时也为竞争对手。时干最重要，月干次之。");
		if(shitpgong==shenshegong) w("时干乘腾蛇，对方诡诈，人不可交。");
		if(shitpgong==shenyingong) w("乘太阴，暗地里行事，也为诡诈，爱算计。");
		if(shitpgong==shenhugong) w("乘白虎，格局好为直爽，格局不好，为猖狂。");
		if(shitpgong==shenwugong) w("乘玄武，主暧昧、投机，搞小动作、违法，欺骗，不讲理。");
		if(shitpgong==shendigong) w("乘九地，动作迟缓、待机而动，固执。");
		if(shitpgong==shentiangong || (SiZhu.sg==YiJing.GENG && shitpgong==bingdpgong))
			w("乘九天，或者庚加丙，主外向，或者大张旗鼓的开拓市场。");
		if(shitpgong==shenhegong) w("时干逢六合，对方是谈判高手。");
		
		if(shitpgong==shangmengong) w("时干逢伤门，对手强，很厉害，不让人。");
		if(shitpgong==dumengong) w("逢杜门，悄悄的办事，悄悄的竞争。");
		if(shitpgong==jing3mengong) w("逢景门，策略型对手，战术型对手，主红火、热闹。");
		if(shitpgong==kaimengong) w("逢开门，主同意，好办事。");
		if(shitpgong==xiumengong || (SiZhu.sg==YiJing.BING && gengdpgong==shitpgong)) w("逢休门或者丙加庚，主退让，愿意讲和。");
		if(shitpgong==shengmengong) w("逢生门，主赢利。");
		if(shitpgong==xingfugong) w("逢天辅，比较文雅，有层次，好说话。");
		if(shitpgong==xingruigong) w("逢天芮，不好办。");
		
		if(shitpgong==gengtpgong || shitpgong==gengdpgong)
			w("时干逢庚或者为庚，主凶狠。");
		if(shitpgong==xintpgong || shitpgong==xindpgong)
			w("时干逢辛，会违法，会不择手段。");
		if(shitpgong==yitpgong || shitpgong==yidpgong ||
				shitpgong==bingtpgong || shitpgong==bingdpgong ||
				shitpgong==dingtpgong || shitpgong==dingdpgong)
			w("时干逢三奇，则对我方吉利。");
		
		w("(二)分清主客，在确定主客的过程中，首重大局，次重八门，再看奇仪，最后看八神。");
		if(my.isMenFu() || my.isXingFu()) w("大局伏吟，利于买货");
		if(my.isMenFan() || my.isXingFan()) w("大局反吟，利于卖货");
		if(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==2) w("大局五不遇时，白费力。");
		
		if(ritpgong==gengtpgong && gengtpgong==bingdpgong) w("日宫逢庚加丙，必须积极进攻；");
		if(ritpgong==bingtpgong && bingtpgong==gengdpgong) w("日宫逢丙加庚，要消极退守；");
		if(ritpgong==shangmengong || ritpgong==xiumengong) w("日宫所临八门当中，逢伤门利客，逢休门利主；");
		if(ritpgong==shendigong || ritpgong==shentiangong) w("日宫所临八神当中，九天利客，九地利主；");
		if(ritpgong==xingchonggong || ritpgong==xingfugong) w("日宫所临九星当中，天冲星利客，天辅星利主。");
		if((ritpgong==wutpgong && wutpgong==bingdpgong) ||
				(ritpgong==bingtpgong && bingtpgong==wudpgong)) w("日宫逢戊加丙，丙加戊，利求财，商战必胜。");
		if(SiZhu.rg==YiJing.XIN && xintpgong==dingdpgong) w("日宫辛加丁，经商获倍利。");
		if(zhishigong==ritpgong) w("日干乘值使门，中介人效力，向着自己，相关办事机构（政府部门）支持。");
		if(ritpgong==dingtpgong && ritpgong==zhishigong) w("日宫逢玉女守门，本地经商获利，不宜异地经营。");
		if((ritpgong==xintpgong && xintpgong==yitpgong) 
				||(ritpgong==yitpgong && yitpgong==xindpgong)) w("日宫逢辛加乙，乙加辛，凶格，合作双方互不信任。");
		if(ritpgong==guitpgong && guitpgong==dingtpgong) w("日宫逢癸加丁，反复无常有口舌。");
		if(ritpgong==dingtpgong && dingtpgong==guidpgong) w("日宫逢丁加癸，有货卖不出，有口舌，生是非。");
		if(ritpgong==gengtpgong && 
				(gengtpgong==guidpgong || gengtpgong==rendpgong)) w("日宫逢庚加癸（大格），庚加壬，不得利。");
		if((ritpgong==gengtpgong && gengtpgong==jidpgong) ||
				(ritpgong==bingtpgong)) w("日宫逢庚加己（刑格），丙加六仪（悖格），逢之早抽身走人，尽快躲避开，也指企业内部混乱不团结。");
		if(my.isTGanMu(SiZhu.rg, SiZhu.rz) || 
				(ritpgong==rendpgong || ritpgong==guidpgong)) w("日干入墓，或者加壬、癸，纵见利而不能得手，宁可不赚钱也不能去投资。");
		if((ritpgong==gengtpgong && ritpgong==wudpgong) ||
				(ritpgong==wutpgong && ritpgong==gengdpgong)) w("日宫逢庚加戊（伏宫格），戊加庚（飞宫格），此地不如他地，换地方（可灵活运用，如临开门可让商场、工厂换门）。");
		if(gengtpgong==ridpgong || ritpgong==gengdpgong) w("庚加日干（伏干格），日干加庚（飞干格），此人不比他人，防备遭人暗算（白虎凶神，防白虎咬人）。");
		
		if(my.isTGanMu(SiZhu.rg, SiZhu.rz) || my.isTpJixing(SiZhu.rg, SiZhu.rz)){
			if((SiZhu.rg==YiJing.YI && (ritpgong==6 || ritpgong==7)) ||
					(SiZhu.rg==YiJing.BING && (ritpgong==1)) ||
					(SiZhu.rg==YiJing.DING && (ritpgong==1))) 
				w("日宫三奇入墓，或受制、受刑，企业内部不协调，不团结。");
		}
		if(my.isTDChong(ritpgong) || my.isTGChong(ritpgong)) w("日干逢冲，应当立即行动。要遵守事物运行规律。");
		if(my.isTDChong(shitpgong) || my.isTGChong(shitpgong)) w("时干逢冲，应当立即行动。要遵守事物运行规律。");
		if(my.isTDG3He(ritpgong) || my.isTDGanHe(ritpgong)
				|| my.isTDZi3He(ritpgong) || my.isTG3He(ritpgong) 
				|| my.isTG6He(ritpgong)) w("日干逢合，被事情绊住了。");
		if(my.isTDG3He(shitpgong) || my.isTDGanHe(shitpgong)
				|| my.isTDZi3He(shitpgong) || my.isTG3He(shitpgong) 
				|| my.isTG6He(shitpgong)) w("时干逢合，被事情绊住了。");
		if(my.isTpJixing(SiZhu.rg, SiZhu.rz)) w("日干逢六仪击刑，求财不得。");
		if(ritpgong==gengtpgong && gengtpgong==bingdpgong) w("日干庚加丙，主进攻，买货有利。");
		if(yuetpgong==gengtpgong && gengtpgong==bingdpgong) w("月干庚加丙，对我方不利。");
		w("如果日宫逢九遁格，在商战中灵活机动才能取胜。逢诈格，设计而动，要运用计谋取得成功。");
		
		if((my.isNenpan(ritpgong) && my.isNenpan(yuetpgong)) ||
				(!my.isNenpan(ritpgong) && !my.isNenpan(yuetpgong))) w("测合伙人是否能长久，看日干和月干，现都在内盘或者都在外盘，不会分开；");
		else
			w("(三)测合伙人是否能长久，看日干和月干，现一个在内盘一个在外盘，必分家。");
		w("办事时选择人选，安排开门所冲之宫天盘奇仪的人去做事，容易成功。");
		
		w("(四)调查、侦查对方选择人选，以值符为我方，也为派出机关，值使为派出的人，即调查人，庚为被调查人，为他方，为竞争对手。");
		if(my.isChongke(zhifugong, gengtpgong) ||
				my.isChongke(zhishigong, gengtpgong) ||
				my.isSheng(gengtpgong, zhifugong) ||
				my.isSheng(gengtpgong, zhishigong)) w("值符宫、值使宫克庚宫或庚生值符、生值使，调查能成功、能得手；");
		else
			w("值符宫、值使宫均不克庚宫，且庚宫也不生值符、值使宫，调查不能成功；");
		
		w("(五)散步消息，天盘丙"+bingtpgong+"宫为我，天盘庚"+gengtpgong+"宫为敌，玄武所落"+shenwugong+"宫为消息，消息真假以景门所落"+jing3mengong+"宫为用神。");
		if(my.isChongke(bingtpgong, gengtpgong) ||
				my.isSheng(gengtpgong, bingtpgong)) w("丙克庚、庚生丙，事情可行。");
		else w("丙不克庚、庚也不生丙，事情不可行。");
		if(my.getGongWS(shenwugong)[0].equals("1")) w("玄武落"+shenwugong+"宫旺相，散布的面比较广；");
		else w("玄武落"+shenwugong+"宫休囚，散布的面比较窄；");
		
		if(jing3mengong==shenshegong || jing3mengong==shenwugong) 
			w("景门上乘腾蛇或玄武，为假消息；");
		if(my.isKong(jing3mengong)) w("景门遇空亡，表示没有消息；");
		if(my.getmenWS(jing3mengong)[0].equals("1")) w("景门落宫旺相，为真消息。");
		else w("景门落宫休囚，为假消息。");
	}
}













