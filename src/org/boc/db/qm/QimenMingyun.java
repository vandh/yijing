package org.boc.db.qm;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.util.Messages;

public class QimenMingyun extends QimenBase{	
	private final String JIINDEX = "★";
	private final String XIINDEX = "☆";
	private final String PIINDEX = "◎";  //□※◎▲■
	private final int INDEXLINE = 0;  //如果凶指或吉指大于此数，则显示该年份，否则不显示
	String t = null;
	int year;   		//出生年份
	
	public QimenMingyun(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
	public String getLife(StringBuffer str,String mzText, int ysNum,boolean boy){
		return this.getLife(str, mzText, ysNum, boy, 0);
	}
	public String getLife(StringBuffer str,String mzText, int ysNum,boolean boy, int year) {
		this.year = year;
		//process(year);
		init(mzText, ysNum, boy, 2000);		
		
		w(p.NOKG+"一、大格局断：");
		getLife1();
		w(p.NEWLINE);
		
		w(p.NOKG+"二、财运断：");
		getLife2();
		w(p.NEWLINE);
		
		w(p.NOKG+"三、官运断："); //包括职业
		getLife3();
		w(p.NEWLINE);
		
		w(p.NOKG+"四、婚姻家庭断：");
		getLife4();
		w(p.NEWLINE);
		
		w(p.NOKG+"五、学历文化断：");
		getLife5();
		w(p.NEWLINE);
		
		w(p.NOKG+"六、健康断：");
		getLife6();
		w(p.NEWLINE);
		
		w(p.NOKG+"七、六亲断：");
		getLife7();
		w(p.NEWLINE);
		
		w(p.NOKG+"八、大运断：");
		getLife8();
		w(p.NEWLINE);
		
		w(p.NOKG+"九、流年断：");
		getLife9();
		w(p.NEWLINE);

		return p.format(str, my);
	}
	
	/**
	 * 一、大格局断
	 */
	public void getLife1() {				
		int rgzt = Integer.valueOf(rgws[0]);
		int jxzt = Integer.valueOf(rgjxws[0]);
		int bmzt = Integer.valueOf(rgbmws[0]);
		int bszt = Integer.valueOf(rgbsjx[0]);
		
		w("1. 天时地利人和情况：");
		w("日干"+rgws[1]+"为"+YOUQI[rgzt+2]+"，"+(rgzt>0?TDRH1[1]:TDRH2[1]),true);
		w("九星"+rgjxws[1]+"为"+YOUQI[jxzt+1]+"，"+(jxzt>0?TDRH1[0]:TDRH2[0]),true);
		w(ritpgong==xingzhugong,"日干临天柱星，适合从事破旧立新的事业，具有中流砥柱、能言善辨的特性；",true);		
		w(ritpgong==xingqingong,"日干临天禽星，为百官之首；",true);
		w(ritpgong==xingpenggong,"日干临天蓬星，主大智慧，也有胆大妄为的一面；",true);
		w("八门"+rgbmws[1]+"为"+YOUQI[bmzt+1]+"，"+(bmzt>0?TDRH1[2]:TDRH2[2]),true);
		w(ritpgong==jing1mengong,"日干临惊门，主说，教师，演讲能力强；",true);
		w(ritpgong==kaimengong,"日干逢开门，为开国大将；",true);
		w(ritpgong==xiumengong,"日干临休门为礼，又为内部，社交手段高，善于处理上下左右关系；",true);
		w("八神"+rgbsjx[1]+(bszt>0?TDRH1[3]:TDRH2[3]),true);
		w(ritpgong==shenwugong,"日干临玄武，为文状元，又主智慧，能屈能伸；",true);
		w(ritpgong==shenhugong,"日干临白虎，虽主凶，但用于行兵打仗却吉，可得神助，因为白虎为武状元；",true);
		w(ritpgong==shendigong,"日干临九地，主平稳、老实、实在。也主老实实在，平稳，一生不出门；",true);
		
		w("2. 值符与值使：");
		w(boy && yang,"男命，阳遁为顺，吉；",true);
		w(boy && !yang,"男命，阴遁不顺，阴遁阴性信息多，为人阴柔；",true);
		w(!boy && yang,"女命，阳遁不顺，不吉；",true);
		w(!boy && !yang,"女命，阴遁为顺，吉；",true);
		int zfxing = daoqm.gInt[2][1][p.getJiGong528(zhifugong)];  //值符星，如果为5宫转成对应的寄宫
		int zsmen = daoqm.gInt[3][1][p.getJiGong528(zhishigong)];  //值使门
		String zfname = "值符[天"+QiMen.jx1[zfxing]+"]";
		String zsname = "值使["+QiMen.bm1[zsmen]+"门]";
		int rgxing = daoqm.gInt[2][1][ritpgong];  //日干临星
		int rgmen = daoqm.gInt[3][1][ritpgong];  //日干临门
		String rxname = "日干所临[天"+QiMen.jx1[rgxing]+"]";
		String rmname = "日干所临["+QiMen.bm1[rgmen]+"门]";
		
		w(QiMen.jxjx[zfxing]==1,zfname+"吉星照命，大吉；",true);
		w(QiMen.jxjx[zfxing]==-1,zfname+"凶星照命，不吉；",true);
		w(QiMen.jxjx[zfxing]==0,zfname+"为中性照命，平；",true);
		
		w(QiMen.bmjx[zsmen]==1,zsname+"吉门值班，大吉；",true);
		w(QiMen.bmjx[zsmen]==-1,zsname+"凶门值班，不吉；",true);
		w(QiMen.bmjx[zsmen]==0,zsname+"平门值班，平；",true);
		
		w(getXingRel(zfname,rxname,zfxing,rgxing,new String[]{"成功机遇较多","天时不利，难得善终，又日干所临之星作用难以发挥，即使为官不会很大","一生付出较多","不吉","吉"}),true);
		w(getXingRel(zsname,rmname,zsmen,rgmen,new String[]{"成功机遇较多","人事不利，又日干所临之门作用难以发挥，即使为官不会很大","一生付出较多","不吉","吉"}),true);
		
		w("3. 日干与时干：");
		if(rgheju[0].equals("1")) w("落宫合局，富贵运好；"+rgheju[1],true);
		else if(rgws[0].equals("-1") && rgsanji[0].equals("-1") && rgbmjx[0].equals("-1") && rgjxjx[0].equals("-1") && rgbsjx[0].equals("-1"))
			w("落宫囚休无奇，临凶星凶门凶神，贫贱命运不佳；"+rgws[1]+rgsanji[1]+rgbmjx[1]+rgjxjx[1]+rgbsjx[1],true);
		else w("落宫失局稍有气，平常命造；"+rgheju[1],true);
		
		boolean isRiKong = p.isKong(ritpgong, p.SHIKONGWANG); //日宫是否旬空，以时柱为主判断，日柱就判断了
		boolean isRiChKong = p.isChKong(ritpgong, p.SHIKONGWANG); //日宫是否是空亡对冲之宫
		String rgSMJ = p.isTGanSMJ(SiZhu.rg, SiZhu.rz);  //判断日干是否入死墓绝
		boolean isshiKong = p.isKong(shitpgong, p.SHIKONGWANG); //时干宫是否旬空，以时柱为主判断，日柱就判断了
		boolean isshiChKong = p.isChKong(shitpgong, p.SHIKONGWANG); //时干宫是否是空亡对冲之宫
		w(isRiKong,"日干落空亡之宫：用神逢空亡为孤；诸格忌落宫亡，吉者减少而苦者更苦。",true);
		w(isRiChKong,"日干落空亡对冲之宫：落空亡对冲之宫为虚；诸格忌落宫亡，吉者减少而苦者更苦。",true);		
		w(isRiKong && isshiChKong,"日干落空亡宫，时干落空亡对冲之宫：年月日时分别为根干花果，主少年时无依无靠；",true);
		w(isshiKong && isRiChKong,"时干空亡，日干为空亡对冲之宫：年月日时分别为根干花果，主老来孤单无所养；",true);
		w(rgSMJ!=null,"日干落宫处"+rgSMJ+"地：日干临墓绝之宫，一生愁眉不展；",true);
		String[] shiheju = p.isganHeju(SiZhu.sg, SiZhu.sz);
		w(shiheju[0].equals("1"), "时干合局，人生结局美满。时干为最终结局，所临星、门、神和格局可断最终结果；"+shiheju[1],true);
		w(!shiheju[0].equals("1"), "时干失局，人生结局悲怆。时干为最终结局，所临星、门、神和格局可断最终结果；"+shiheju[1],true);
		if(shitpgong==xingpenggong && shitpgong==dumengong) {
			t = "时干临杜门，杜门主武装，天蓬为元帅，最后可做到军事长官；";
			if(shitpgong==zhifugong) t+="又临值符，职位可到最高军事长官；";
			w(t,true);
		}else if(shitpgong==xingpenggong) {
			t = "临天蓬，主大智慧，也有胆大妄为的一面，得地为叛军首领；";
			if(rgjxjx[0].equals("-1")) t+="加上"+zfname+"照命，结局不会太好；";
			w(t,true);
		}
		
		w("4. 日宫格局断：");
		w(isMenfan,"八门反呤：八门代表人事，最怕反呤，主一生反复不顺；",true);
		w(isMenfu,"八门伏呤：八门代表人事，伏呤主一生坎坷，难有大的发展；",true);
		w(isXingfan,"九星反呤：九星代表天时，反呤主一生反复不顺；",true);
		w(isXingfu,"九星伏呤：九星代表天时，伏呤主一生坎坷，难有大的发展；",true);
		
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==1,"大局天显时格，一生平稳、顺利；",true);
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==2,"大局五不遇时，百事皆凶；",true);
		
		w(rgtpdpjy[0]==YiJing.REN || rgtpdpjy[1]==YiJing.REN,"日干+壬：壬为地牢，再遇凶星凶门凶神，轻者容易犯错误，受处分，重者犯法坐牢，一生抑郁难伸，难有杨眉吐气之日；",true);
		w(rgtpdpjy[0]==YiJing.XIN || rgtpdpjy[1]==YiJing.XIN,"日干+辛：辛为天狱，再遇凶星凶门凶神，轻者容易犯错误，受处分，重者犯法坐牢，一生抑郁难伸，难有杨眉吐气之日；",true);
		w(rgdptpjy[0]==YiJing.REN || rgdptpjy[1]==YiJing.REN,"壬+日干：壬为地牢，再遇凶星凶门凶神，轻者容易犯错误，受处分，重者犯法坐牢，一生抑郁难伸，难有杨眉吐气之日；",true);
		w(rgdptpjy[0]==YiJing.XIN || rgdptpjy[1]==YiJing.XIN,"辛+日干：辛为天狱，再遇凶星凶门凶神，轻者容易犯错误，受处分，重者犯法坐牢，一生抑郁难伸，难有杨眉吐气之日；",true);
		if((ritpgong==6 || ritpgong==7 || ritpgong==8 || ritpgong==9) && (rgtpdpjy[0]==YiJing.GUI || rgtpdpjy[1]==YiJing.GUI))
			w("日干+癸：癸为天网，落入"+ritpgong+"宫为网高，合局者化为华盖，可为上流社会富贵人物；",true);
		else if(rgtpdpjy[0]==YiJing.GUI || rgtpdpjy[1]==YiJing.GUI)
			w("日干+癸：癸为天网，落入"+ritpgong+"宫为网低，又失局则天网缠身，寂寂孤贫，下等贫民；",true);
		if((ridpgong==6 || ridpgong==7 || ridpgong==8 || ridpgong==9) && (rgdptpjy[0]==YiJing.GUI || rgdptpjy[1]==YiJing.GUI))
			w("癸+日干：癸为天网，落入"+ridpgong+"宫为网高，合局者化为华盖，可为上流社会富贵人物；",true);
		else if(rgdptpjy[0]==YiJing.GUI || rgdptpjy[1]==YiJing.GUI)
			w("癸+日干：癸为天网，落入"+ridpgong+"宫为网低，又失局则天网缠身，寂寂孤贫，下等贫民；",true);
		
		if(ritpgong==kaimengong) {
			t = "日干与开门同宫，一生与官职伴身；";
			if(ritpgong==shenhugong) t+="同时白虎加开门 为开路，可为勇猛大将；";
			w(t,true);			
		}
		w(p.isYima(shitpgong),"时干临马星，一生东奔西走；",true);
		w(p.isYima(zhishigong),"值使临马星，一生东奔西走；",true);
		w(p.isYima(ritpgong),"日干落宫临马星，一生东奔西走；",true);
		w(niantpgong==xingqingong,"年命临天禽，可为百官之首；",true); 
		String[] niantgws = p.gettgWS(SiZhu.ng, SiZhu.nz);
		w(niantgws[0].equals("1"),"年命为根，落宫旺相，根深，寿命长或家族势力强大；",true);
		String[] niansanji = p.getSanji(niantpgong);
		String[] nianbmjx = p.getmenJX(niantpgong);
		if(ritpgong==xingruigong && rgsanji[0].equals("1") && rgbmjx[0].equals("1")) 
			w("日干落宫有天芮，逢奇又遇吉门，为曹操董卓之流；",true);		
		else if(niantpgong==xingruigong && niansanji[0].equals("1") && nianbmjx[0].equals("1")) 
			w("年干落宫有天芮，逢奇又遇吉门，为曹操董卓之流；",true);
		w(p.isGongSheng(niantpgong, ritpgong),"年干生日干，一生能得到领导信任、重用；",true);
		w(p.isChongke(zhifugong, ritpgong),"值符克日干，一生得不到领导的信任和重用；",true);
		
		w(boy && (rgtpdpjy[0]==YiJing.YI || rgtpdpjy[1]==YiJing.YI),"日干下临乙奇，为一生得女人帮助；",true);
		
		t=null;
		if(ritpgong==shangmengong && ritpgong==xingchonggong) {
			t = "日干所落"+ritpgong+"宫临伤门和天冲星，存储了受伤信息；";
		} else if(ridpgong==shangmengong && ridpgong==xingchonggong) {
			t = "日干地盘所落"+ridpgong+"宫临伤门和天冲星，存储了受伤信息；";
		}
		if(t!=null) {
			//阳日为天盘庚下之干，阴日为地盘庚上之干
			int yqgan = p.is5YangGan(SiZhu.rg) ? p.getDpjy(gengtpgong)[0] : p.getTpjy(gengdpgong)[0];
			String yqnian = p.getYearString(1, year, 1, 50, yqgan);  //出生以后即需要防车祸
			if(p.is5YangGan(SiZhu.rg))
				t += "阳日寻"+gengtpgong+"宫庚下之干，"+yqnian+"防车祸；";
			else
				t += "阴日寻"+gengdpgong+"宫庚上之干，"+yqnian+"防车祸；";
			w(t,true);
		}
		getShare3(true,ritpgong,true,false);  //输出日干宫的一些共享信息
		w("地盘日干落宫也要参看；",true);
		
		w("5. 日干落宫吉凶格局断：");
		boolean hasgeju = false;
		int ge1 = QiMen.gan_gan[rgtptpjy[0]][rgtpdpjy[0]];
		int ge2= QiMen.gan_gan[rgtptpjy[1]][rgtpdpjy[0]] ;
		int ge3= QiMen.gan_gan[rgtptpjy[0]][rgtpdpjy[1]] ;
		int ge4= QiMen.gan_gan[rgtptpjy[1]][rgtpdpjy[1]] ;
		int[] gejunum = {81,63,73,82,110,56,78,132,101,65,103,85,105,100,55,75,102,108,107};
		String[] gejudesc = {"青龙返首：戊加丙，鏊头独占","飞鸟跌穴：丙加戊，富贵成名","青龙转光：丁加戊，邑长县令",
				"青龙耀明：戊加丁，富贵荣耀","白虎猖狂：辛加乙，凶顽之辈","青龙逃走：乙加辛，懦弱之人，更主因妻成败，而且驼背驼身",
				"朱雀投江：丁加癸，刀笔书吏","螣蛇夭矫：癸加丁，毒心小人，失时者目盲耳聩，乘气者火焚伤身",
				"太白入荧：庚加丙，进主先贫后富","荧入太白：丙加庚，退主家业萧条","伏宫格：庚加戊，多成多败，此地不比他地",
				"飞宫格：戊加庚，多成多败，此地不比他地","太白同宫：庚加庚，又名战格，兄弟雷攻","太白逢星：庚加乙，妻室常病",
				"日奇被刑：乙加庚，妻室常病","星逢太白：丁加庚，惟薄丑声，即有婚外恋等丑事","金屋藏娇：庚加丁，惟薄丑声，即有婚外恋等丑事",
				"大格：庚加癸，萍迹四海","小格：庚加壬，又称移荡格，暂时清贫"};
		for(int i=0; i<gejunum.length; i++) {
			if(ge1==gejunum[i] || ge2==gejunum[i] || ge3==gejunum[i] || ge4==gejunum[i]) {
				hasgeju = true;
				w(gejudesc[i],true);
			}				
		}
		
		w(p.isFeigan(),QiMen.gGejuDesc[13][0]+"：天盘日干，地盘六庚，此人不比他人",true);
		w(p.isFugan(),QiMen.gGejuDesc[24][0]+"：天盘六庚，地盘日干，此人不比他人",true);
		w(!hasgeju,QiMen.gGejuDesc[ge1][0]+"："+QiMen.gGejuDesc[ge1][1],true);
	}
	
	/**
	 * 二、
	 */
	public void getLife2() {
		String[] menHeju = p.ismenHeju(shengmengong);  //判断生门是否合局
		String[] sanji = p.getSanji(shengmengong);  //判断生门宫是否有三奇		
		String[] ws = p.getmenWS(shengmengong);
		String[] xingjx = p.getxingJX(shengmengong);
		
		w("1. 生门产为断：");
		w(menHeju[0].equals("1"),"生门合局：代表产业为财，合局则产业丰足；"+menHeju[1],true);
		w(sanji[0].equals("1"),sanji[1]+"生门产业，要得三奇；",true);
		
		boolean kong = p.isKong(shengmengong);
		boolean ismenpo = QiMen.men_gong[QiMen.MENSHENG][shengmengong].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[QiMen.MENSHENG][shengmengong].equals(QiMen.zphym1);
		w(ismenpo,"生门落宫被迫，吉门克宫吉不就，难以达到目的；",true);
		w(ismenzhi,"生门落宫受制，吉门受克吉不就，难以达到目的；",true);
		w(kong,"生门落宫空亡，吉者减昌，苦者更苦，颠簸困苦；",true);
		
		w("2. 生门与日宫断：");
		//得到生门落宫的地盘奇仪与天盘奇仪，判断是否有庚
		int[] tpjy = p.getTpjy(shengmengong);
		int[] dpjy = p.getDpjy(shengmengong);
		//if(tpjy[0]==YiJing.GENG ||tpjy[1]==YiJing.GENG ||dpjy[0]==YiJing.GENG ||dpjy[1]==YiJing.GENG) {
		w(p.isChongke(shengmengong, ritpgong),"生门又代表出生之地、祖籍、祖业，落宫(临庚更凶)冲克日干落宫，背井离乡；",true);
		w(p.isChongke(ritpgong, shengmengong),"生门又代表出生之地、祖籍、祖业，日干冲克生门，必是败家子；",true);
		
		w(getGongRel(shengmengong, "生门", ritpgong, "日干", 
				new String[]{"为财来找我，吃穿不用愁"+(sanji[0].equals("1")?sanji[1]+"更容易得财":""),
				"有财难求","主动求财","主动求财","吉","吉"}),true);
		
		w("3. 钱财多少断：");
		if(ws[0].equals("1") && xingjx[0].equals("1")) {
			t = "生门落宫旺，临吉星财大；";
			if(gengtpgong==shengmengong && shengmengong==shenyingong) t+="但临庚、乘太阴为掠夺之财；";
			else if(gengtpgong==shengmengong) t+="但临庚、为不义之财；";
			else if(shengmengong==shenyingong) t+="但上乘太阴，为掠夺之财；";
			w(t,true);
		}
		
		String gongws = QiMen.xing_gong[caixing][shengmengong]; 
		String yuews = QiMen.xing_yue[caixing][SiZhu.yz];		
		if(gongws.equals(QiMen.wxxqs1) && yuews.equals(QiMen.wxxqs1)){
			t = "财星["+QiMen.jx1[caixing]+"]在"+shengmengong+"宫和月令均为旺地；";
			if(kong) t+= "但落宫空亡，财富易聚易散；";
			else t+="必是亿万富翁级人物；";
			w(t,true);
		}else if(gongws.equals(QiMen.wxxqs1) && yuews.equals(QiMen.wxxqs2)){
			t = "财星["+QiMen.jx1[caixing]+"]在"+shengmengong+"宫为旺地，月令为相地；";
			if(kong) t+= "但落宫空亡，财富易聚易散；";
			else t+="必是千万富翁；";
			w(t,true);
		}else 
		if( gongws.equals(QiMen.wxxqs2) && (yuews.equals(QiMen.wxxqs1) || yuews.equals(QiMen.wxxqs2))){
			t = "财星["+QiMen.jx1[caixing]+"]在"+shengmengong+"宫为相地，"+"在月令旺相；";
			if(kong) t+= "但落宫空亡，财富易聚易散；";
			else t+="必是百万富翁；";
			w(t,true);
		}else if((gongws.equals(QiMen.wxxqs5) || gongws.equals(QiMen.wxxqs6) )&& (yuews.equals(QiMen.wxxqs5) || yuews.equals(QiMen.wxxqs6))){
			w("财星["+QiMen.jx1[caixing]+"]在"+shengmengong+"宫"+gongws+"，在月令"+yuews+"，无财可求，终生穷困潦倒；",true);
		}else if((gongws.equals(QiMen.wxxqs3) || gongws.equals(QiMen.wxxqs4) )&& (yuews.equals(QiMen.wxxqs3) || yuews.equals(QiMen.wxxqs4) || yuews.equals(QiMen.wxxqs5) || yuews.equals(QiMen.wxxqs6))){
			w("财星["+QiMen.jx1[caixing]+"]在"+shengmengong+"宫"+gongws+"，在月令"+yuews+"，财运极差，艰辛难得；",true);
		}else{
			w("财星["+QiMen.jx1[caixing]+"]在"+shengmengong+"宫"+gongws+"，在月令"+yuews+"，财运一般，勤劳致富；",true);
		}
			
		w("4. 钱财方位断：");
		boolean rinei = p.isNenpan(ritpgong);
		boolean shnei = p.isNenpan(shengmengong);
		boolean isyang = daoqm.getJu()>0;
		w(rinei && !shnei,(daoqm.getJu()>0?"阳局":"阴局")+"，日干落内盘"+ritpgong+"宫，生门落外盘"+shengmengong+"宫，必须迁居才能富起来；",true);
		w(!rinei && shnei,(isyang?"阳局":"阴局")+"，日干落外盘"+	ritpgong+"宫，生门落内盘"+shengmengong+"宫，祖业即使丰富自己也难利用；",true);
		w(rinei && shnei,(isyang?"阳局":"阴局")+"，日干落内盘"+	ritpgong+"宫，生门落内盘"+shengmengong+"宫，身生皆在内盘，一生安享富贵；",true);
		w(!rinei && !shnei,(isyang?"阳局":"阴局")+"，日干落外盘"+	ritpgong+"宫，生门落外盘"+shengmengong+"宫，身生皆在外盘，必须到外地去才能闯出好局面；",true);
		
		t = null;
		if(shnei) {
			t = "生门落内盘"+shengmengong+"宫，财在家乡"+QiMen.JIUGONGFXIANG[shengmengong]+"；";
		}else{
			t = "生门落外盘"+shengmengong+"宫，财在外地"+QiMen.JIUGONGFXIANG[shengmengong]+"；";
		}
		if(p.isKong(shengmengong) && (p.isYima(shengmengong) || p.getGongWS(shengmengong)[0].equals("1"))) {
			t+="虽生门落宫空亡，"+(p.isYima(shengmengong)?"但逢马星不为空；":"但旺不为空；");
		}else if(p.isKong(shengmengong)) {
			int chgong = p.getChongGong(shengmengong);
			boolean isnei = p.isNenpan(chgong);
			t+="但生门落宫空亡，看对冲之"+chgong+"宫，因落"+(isnei?"内盘":"外盘")+"，则财在"+(isnei?"家乡":"外地")+QiMen.JIUGONGFXIANG[shengmengong]+"；";
		}
		w(t,true);		
	}
	/**
	 * 三、
	 */
	public void getLife3() {
		String xing[] = p.isxingHeju(ritpgong);
		String men[] = p.ismenHeju(ritpgong);
		String[] jxdesc1 = {"","日干临天蓬星合局：边关大将；",
				"日干临天芮星合局：虽位高权重，但为曹操董卓之流；",				
				"日干临天冲星合局：威镇边疆或军队有权有势之人；",
				"日干临天辅星合局：文化教育上有成就有名气的官员；",
				 "日干临天临天禽合局：百官元首；",				 
				 "日干临天心合局：为医疗卫生部门官员、或才华横溢；",				 
				 "日干临天柱合局：公检法官员、或外交家演说家、闻名的律师、歌唱家；",
				 "日干临天任合局：农林水利官员、或广有田庄；",
				 "日干临天英星合局：文教科技事业栋梁之材，闻名于世；",				 
				 };		
		String[] jxdesc0 = {"","日干临天蓬星失局稍有气：可能是公务员或科长或军队的排长班长；",
				"日干临天芮星失局稍有气：为大姓家人富有田庄；",
				"日干临天冲星失局稍有气：为部队中的小头头或领班；",
				"日干临天辅星失局稍有气：为清贫的知识分子；",
				"",
				"日干临天心星失局稍有气：可从事医卜星相等脑力劳动者；",
				"日干临天柱星失局稍有气：为幕僚宾客或教书先生；",
				"日干临天任星失局稍有气：有田有土自给自足；",	 
				"日干临天英星失局稍有气：在文教事业有一定成绩；"};
		String[] jxdesc2 = {"","日干临天蓬星失局无气：为士兵或小偷小人物；",
				"日干临天芮星失局无气：孤苦无依的穷苦百姓；",
				"日干临天冲星失局无气：为普通士兵或司机车夫；",
				"日干临天辅星失局无气：和尚道士等归隐之人或卖艺为生；",
				"",
				"日干临天心星失局无气：为手艺人体力劳动者；",
				"日干临天柱星失局无气：为说唱卖艺或巫婆神汉。",
				"日干临天任星失局无气：为打工卖体力；",	 
				"日干临天英星失局无气：为冶炼或与火有关的苦力或昏庸之人；"};
	 //"休", "死", "伤", "杜","死", "开", "惊", "生", "景"
		String[] bmdesc1 = {"","日干临休门合局：为办公室主任，或秘书长、或机关事务管理局长等为首长服务人员；",
				"日干临死门合局：为司法、检查院、法院、监狱劳改局官员。",
				"日干临伤门合局：武官；",
				"日干临杜门合局：为保密机关、或军警官员；",
				"",
				"日干临开门合局：文官；",
				"日干临惊门合局：能言善辨，为教师、律师、外交家、歌唱家、或与口有关职业；",
				"日干临生门合局：象石崇一样富足；",
				"日干临景门合局：文化事业有成，是作家、教育家、科学家、书法家；"		
		}; 
		
		String[] bmdesc0 = {"","日干临休门失局稍有气：可能是公务员或科长或军队的排长班长；",
				"日干临死门失局稍有气：为大姓家人富有田庄；",
				"日干临伤门失局稍有气：为部队中的小头头或领班；",
				"日干临杜门失局稍有气：为清贫的知识分子；",
				"", 
				"日干临开门失局稍有气：可从事医卜星相等脑力劳动者；",
				"日干临惊门失局稍有气：为幕僚宾客或教书先生；", 
			 "日干临生门失局稍有气：有田有土自给自足；",				 
			 "日干临景门失局稍有气：在文教事业有一定成绩；"};
				 
		String[] bmdesc2 = {"","日干临休门失局无气：为士兵或小偷小人物；",
				"日干临死门失局无气：孤苦无依的穷苦百姓；",
				"日干临伤门失局无气：为普通士兵或司机车夫；",
				"日干临杜门失局无气：和尚道士等归隐之人或卖艺为生；",
				"", 
				"日干临开门失局无气：为手艺人体力劳动者；",
				"日干临惊门失局无气：为说唱卖艺或巫婆神汉。", 
			 "日干临生门失局无气：为打工卖体力；",				 
			 "日干临景门失局无气：为冶炼或与火有关的苦力或昏庸之人；"};
				 
		//w(rgheju[0].equals("1"),"八门九星决定职业，旺衰决定地位，日宫合局，为社会上等人物，失之一二，中等人物，完全失局为平民：");		
		w("1. 九星所主职业：");
		w(xing[0].equals("1"), jxdesc1[daoqm.gInt[2][1][ritpgong]]+xing[1],true);
		w(xing[0].equals("0"),jxdesc0[daoqm.gInt[2][1][ritpgong]]+xing[1],true);
		w(xing[0].equals("-1"),jxdesc2[daoqm.gInt[2][1][ritpgong]]+xing[1],true);
		
		w("2. 八门所主职业：");
		w(men[0].equals("1"), bmdesc1[daoqm.gInt[3][1][ritpgong]]+men[1],true);
		w(men[0].equals("0"), bmdesc0[daoqm.gInt[3][1][ritpgong]]+men[1],true);
		w(men[0].equals("-1"),bmdesc2[daoqm.gInt[3][1][ritpgong]]+men[1],true);
		
		w("3. 开门所主事业官运：");
		boolean iskong = p.isKong(kaimengong);
		int[] tpjy = p.getTpjy(kaimengong);
		int[] dpjy = p.getDpjy(kaimengong);
		w(getGongRel(kaimengong, "开门", ritpgong, "日干", 
				new String[]{"一生事业发达，官运亨通","不吉","有求官的意愿",
				"事业发达、加官晋级"+(iskong?"，现落空亡，则填或充实之年为应":""),	"吉","吉"}),true);
		if(tpjy[0]==SiZhu.rg || tpjy[0]==SiZhu.ng ||
				tpjy[1]==SiZhu.rg || tpjy[1]==SiZhu.ng ||
				dpjy[0]==SiZhu.rg || dpjy[0]==SiZhu.ng ||
				dpjy[1]==SiZhu.rg || dpjy[1]==SiZhu.ng ) {
			t = "开门落宫临年干或日干，表明官职伴其一身；";
			if(kaimengong==shenhugong) {
				t+="上临白虎，为恶官，或为军事指挥官；";
				if(kaimengong==xingruigong) t+="又临天芮星，为军事院校长官；";
			}
			else if(kaimengong==shenwugong) t+="上临玄武，为贪官；";
			else if(kaimengong==shenshegong) t+="上临腾蛇，为奸臣贼子；";
			w(t,true);
		}
		
		String[] sanji = p.getSanji(kaimengong);
		if(sanji[0].equals("1")) w("开门"+sanji[1]+"应为文官；",true);
		
		boolean ismenpo = QiMen.men_gong[QiMen.MENKAI][kaimengong].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[QiMen.MENKAI][kaimengong].equals(QiMen.zphym1);
		w(ismenpo,"开门落宫被迫，吉门克宫吉不就，难以达到目的；",true);
		w(ismenzhi,"开门落宫受制，吉门受克吉不就，难以达到目的；",true);
		
		w(iskong,"开门落宫空亡，吉者减昌，苦者更苦，颠簸困苦；");
		int[] ggong = p.getTpjy(kaimengong);
		w(kaimengong==shenhugong && (YiJing.GENG==ggong[0] || YiJing.GENG==ggong[1]),"白虎加庚有权柄之象。",true);
		
		boolean kainei = p.isNenpan(kaimengong);
		if(kainei) {
			w("开门落内盘"+kaimengong+"宫，事业在本地，在"+QiMen.JIUGONGFXIANG[kaimengong]+"；",true);
		}else{
			w("开门落外盘"+kaimengong+"宫，事业为外地，在"+QiMen.JIUGONGFXIANG[kaimengong]+"；",true);
		}
	}
	
	/**
	 * 四、
	 */
	public void getLife4() {
		int marry = 0;
		
		w("1. 看休门与六合：");
		w(getGongRel(xiumengong, "休门", ritpgong, "日干", new String[]{"吉","不吉","吉","不吉",	"吉","吉"}),true);
		w(getGongRel(shenhegong, "六合", ritpgong, "日干", new String[]{"吉","不吉","吉","不吉",	"吉","吉"}),true);
		if(p.isChongke(shenhegong, ritpgong)) marry++;
		if(p.isChongke(xiumengong, ritpgong)) marry++;
		if(p.isKong(xiumengong)) { w("休门落空亡，婚姻不顺的标志；",true); marry++;}
		if(xiumengong==gengtpgong) {  w("休门逢庚，婚姻不顺的标志；",true);marry++;}
		if(p.isKong(shenhegong))  { w("六合落空亡，婚姻不顺的标志；",true);marry++;}
		if(shenhegong==gengtpgong)  { w("六合逢庚，婚姻不顺的标志；",true);marry++;}
		
		w("2. 看乙与庚、日干与所合之干：");
		String rgname = YiJing.TIANGANNAME[SiZhu.rg];
		int hegan = p.getHegan(SiZhu.rg);  //求与日干所合之干
		if(hegan==YiJing.JIA) hegan = YiJing.WUG;  //如果为甲，则直接返回值符所在宫即戊
		int hegangong = p.getTianGong(hegan,0);
		String heganname = YiJing.TIANGANNAME[hegan];
		w(getGongRel(yitpgong, "[乙]", gengtpgong, "[庚]", new String[]{"婚姻美满","夫妻感情不好，婚姻不顺，也主非自由恋爱","婚姻美满","夫妻感情不好，婚姻不顺，也主非自由恋爱",	"吉","吉"}),true);
		if(SiZhu.rg!=YiJing.YI && SiZhu.rg!=YiJing.GENG) //非乙与庚才求与干相合之干
			w(getGongRel(ritpgong, "日干["+rgname+"]", hegangong, "相合之干["+heganname+"]", new String[]{"吉","自己在家是一把手","吉","配偶在家是一把手",	"吉","吉"}),true);
		if(p.isChongke(ritpgong, hegangong) || p.isChongke(hegangong,ritpgong)) marry++;
		if(p.isKong(yitpgong))  { w("[乙]落空亡，婚姻不止一次的标志；",true);marry++;}
		if(yitpgong==simengong) {  w("[乙]逢死门，必有生离死别之事；",true);marry++;}
		if(p.isKong(gengtpgong))  { w("[庚]落空亡，婚姻不止一次的标志；",true);marry++;}
		if(gengtpgong==simengong) {  w("[庚]逢死门，必有生离死别之事；",true);marry++;}
		if(p.isChongke(yitpgong, gengtpgong) || p.isChongke(gengtpgong, yitpgong)) {marry++; };
			
		w("3. 断"+(boy?"妻子":"丈夫")+"：");
		int qzTgong = boy?p.getTianGong(YiJing.YI, 0):p.getTianGong(YiJing.GENG, 0); //得到 妻子|丈夫的天盘宫
		int qzDgong = boy?p.getDiGong(YiJing.YI, 0):p.getDiGong(YiJing.GENG, 0);   //得到妻子|丈夫所在的地盘宫
		int qzDjy[] = p.getDpjy(qzTgong);
		int qzTjy[] = p.getTpjy(qzDgong);		
		int qieTgong = boy?p.getTianGong(YiJing.DING, 0):p.getTianGong(YiJing.BING, 0); //得到 小三｜面首的天盘宫
		int qieDgong = boy?p.getDiGong(YiJing.DING, 0):p.getDiGong(YiJing.BING, 0);   //得到小三｜面首所在的地盘宫		
		if(qzTjy[0]==YiJing.GENG || qzTjy[1]==YiJing.GENG) {
			w("天盘六庚临"+(boy?"妻":"夫")+"宫，为刑格，对"+(boy?"妻":"夫")+"不利，常生病或早亡；",true);	
			marry++;
		}
		else if(qzDjy[0]==YiJing.GENG || qzDjy[1]==YiJing.GENG) {
			w("地盘六庚临"+(boy?"妻":"夫")+"宫，为刑格，对"+(boy?"妻":"夫")+"不利，常生病或早亡；",true);
			marry++;
		}
		
		int zhishiGong = daoqm.getZhishiGong(SiZhu.sg,SiZhu.sz);
  	if(boy) {
			for(int i=1; i<=9; i++) {
	  		if(i==5) continue;
	  		if((i==zhishiGong && daoqm.gInt[4][5][i]==YiJing.DING) ||
	  				(i==2 && i==zhishiGong && daoqm.gInt[4][5][5]==YiJing.DING)){
	  			marry++;
	  			w("玉女守门：即门盘直使加临地盘丁奇，主妻随人行，也主妻红杏出墙；",true);
	  			break;
	  		}
	  	}
  	
	  	w(QiMen.gan_gong_mu[YiJing.YI][qzTgong]!=null, "天盘乙奇入墓，妻不生子；",true);
	  	w(QiMen.gan_gong_mu[YiJing.YI][qzDgong]!=null,"地盘乙奇入墓，妻不生子；",true);
	  	
	  	//w(p.isSheng(ritpgong,qzTgong),"乙妻丁为小三，谁亲谁疏看落宫生克，夫宫生妻宫，夫妻和睦；",true);
	  	w(p.isSheng(ritpgong,qieTgong),"乙妻丁为小三，谁亲谁疏看落宫生克，夫宫生小三宫，主喜欢外边的野花；",true);
	  	//w(p.isChongke(ritpgong,qzTgong) || p.isChongke(qzTgong,ritpgong),"夫妻宫相冲克，主夫妻感情不睦；",true);
	  	//w(p.isSheng(ritpgong,qzTgong) || p.isSheng(qzTgong,ritpgong),"夫妻宫相生，主夫妻感情基础牢固，婚姻美满；",true);
	  	
	  	w(qzDjy[0]==YiJing.DING || qzDjy[1]==YiJing.DING,"丁乙同宫，会再娶小老婆。乙奇在上，丁奇在下，妻妾和谐；",true);
	  	w(qzTjy[0]==YiJing.DING || qzTjy[1]==YiJing.DING,"丁乙同宫，会再娶小老婆。丁奇在上，乙奇在下，妾幸夫心；",true);
  	}
		
  	w("4. 断应期：");
		boolean yinei = p.isNenpan(yitpgong);
		boolean gengnei = p.isNenpan(gengtpgong);
		if(yinei && gengnei) {
			w("[乙]落"+yitpgong+"宫，[庚]落"+gengtpgong+"，同在内盘，早婚之象！",true);
		}else if(!yinei && !gengnei) {
			w("[乙]落"+yitpgong+"宫，[庚]落"+gengtpgong+"，同在外盘，晚婚之象！",true);
		}else {
			w("[乙]落"+yitpgong+"宫，[庚]落"+gengtpgong+"，一内一外，晚婚之象！",true);
		}
		if(marry>=2) {
			w("婚姻不顺标志明显，婚姻次数按六合落宫旺衰取数。现六合落"+shenhegong+"宫，月令"+p.getGongWS(shenhegong)[1]+"地，"+p.getGongshu(shenhegong),true);
			if(p.isKong(shenhegong)) w("六合落"+shenhegong+"宫空亡，防止以下年份婚变："+p.getYearString(5, year, 18, 40, shenhegong),true); //18～30岁结过婚后10年内防
			else if(p.isKong(xiumengong)) w("休门落"+xiumengong+"宫空亡，防止以下年份婚变："+p.getYearString(5, year, 18, 40, xiumengong),true); //18～30岁结过婚后10年内防
			if(p.isKong(yitpgong)) w("[乙]落"+yitpgong+"宫空亡，防止以下年份婚变："+p.getYearString(5, year, 18, 40, yitpgong),true); //18～30岁结过婚后10年内防
			else if(p.isKong(gengtpgong)) w("[庚]落"+gengtpgong+"宫空亡，防止以下年份婚变："+p.getYearString(5, year, 18, 40, gengtpgong),true); //18～30岁结过婚后10年内防
			
			if(p.isChongke(yitpgong, gengtpgong) || p.isChongke(gengtpgong,yitpgong)) {
				w("因乙与庚落宫相冲克，须防主克旺相之年或受克休囚之年婚变；",true);
			}
		}	
	}

	/**
	 * 五、
	 */
	public void getLife5() {
		t="";
		int times = 0;
		if(ritpgong==dingdpgong) {
			t += "下临丁奇，文科成绩好；";times++;
		}else if(p.isSheng(dingdpgong, ritpgong)) {
			t += "受"+dingtpgong+"宫丁奇所生，文科成绩好；";times++;
		}
		if(ritpgong==jing3mengong) {
			t += "临景门，理科成绩好；";times++;
		}else if(p.isSheng(jing3mengong, ritpgong)) {
			t += "受"+jing3mengong+"宫景门所生，理科成绩好；";times++;
		}
		if(ritpgong==xingfugong) {
			t += "临天辅星，主有文化；";times++;
		}else if(p.isSheng(xingfugong, ritpgong)) {
			t += "受"+jing3mengong+"宫天辅星所生，主有文化；";times++;
		}
		if(ritpgong==xingpenggong) {
			t += "临天蓬星，智商高；";times++;
		}else if(p.isSheng(xingpenggong, ritpgong)) {
			t += "受"+xingpenggong+"宫天蓬星所生，智商高；";times++;
		}
		
		t = "日干落"+ritpgong+"宫，"+t;
		if(times>2) t+="研究生或以上学历；";
		else if(times>1) t+="本科或以上学历；";
		else if(times==1) t+="学历不是很高，中专、大专或高职一类学历；";
		else if(times==0) t+="读书成绩不好，初小文化；";
		w(t);
	}
	
	/**
	 * 六、
	 */
	public void getLife6() {
		int ngws = QiMen.gan_gong_wx3[SiZhu.ng][niantpgong];
		int rgws = QiMen.gan_gong_wx3[SiZhu.rg][ritpgong];
		boolean ruinei = p.isNenpan(xingruigong);
		
		w("1. 疾病：");
		if((ngws==1 || rgws==1) && !p.isChongke(xingruigong, ritpgong) && !p.isChongke(simengong, ritpgong) &&
				!p.isChongke(jing1mengong, ritpgong) && !p.isChongke(shangmengong, ritpgong)) {
			w("年干或日干落宫旺相，天芮病星、死门、惊门、伤门均不克日干，身体不错，一生无大灾！",true);
		}
		
		//1. 先断天芮星
		int howmanyyears = 60;  //注意多少年
		t = "天芮星落"+xingruigong+"宫";
		t += ruinei?"，在内盘":"，在外盘";
		if(p.isGongke(xingruigong, ritpgong)) t+="，又冲克日干";
		t += "，要防止｛"+(ruinei?QiMen.JGBUWEINEI[xingruigong]:QiMen.JGBUWEIWAI[xingruigong])+"｝疾病；";
		w(t,true);
		if(xingruigong==simengong || xingruigong==shenhugong) w("天芮星又与死门、白虎同宫，一旦得病就会比较严重；",true);		
		if(p.isKong(xingruigong))  //如果旬空，则冲实/填实之年也要防
			w("天芮星落宫旬空，则以下填实或冲实之年月要注意防止疾病："+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, xingruigong),true);
		else
			w("以下年份注意疾病："+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, xingruigong)),true);  //本宫填实之年防疾病

		w("2. 天灾人祸：");
		if(p.isGongke(shangmengong, ritpgong)) {
			w("伤门落"+shangmengong+"宫冲克日干宫，大凶，一生要防伤灾；",true);
			if(p.isKong(xingruigong))  //如果旬空，则冲实/填实之年也要防
				w("又遇空亡，则以下填实或冲实之年月要注意伤灾："+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, shangmengong),true);
			else
				w("以下年份注意伤灾："+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, shangmengong)),true);  //本宫填实之年防疾病
		}
		if(p.isGongke(simengong, ritpgong)) {
			w("死门落"+simengong+"宫冲克日干宫，大凶，一生要防死病伤；",true);
			if(p.isKong(simengong))  //如果旬空，则冲实/填实之年也要防
				w("又遇空亡，则以下填实或冲实之年月要注意死病伤灾："+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, simengong),true);
			else
				w("以下年份注意死病伤灾："+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, simengong)),true);  //本宫填实之年防疾病
		}
		if(p.isGongke(jing1mengong, ritpgong)) {
			w("惊门落"+jing1mengong+"宫冲克日干宫，大凶，一生要防口舌官非；",true);
			if(p.isKong(simengong))  //如果旬空，则冲实/填实之年也要防
				w("又遇空亡，则以下填实或冲实之年月要注意口舌官非："+p.getYearString(p.YEARGONGTIANCHONG, year, 1, howmanyyears, jing1mengong),true);
			else
				w("以下年份注意口舌官非："+(p.getYearString(p.YEARGONGTIAN, year, 1, howmanyyears, jing1mengong)),true);  //本宫填实之年防疾病
		}
		w("3. 寿命：");
		w("值使["+QiMen.bm1[zhishigong]+"]门落"+zhishigong+"宫，其旺衰是判断寿限的主要依据！",true);
		int maxnum = p.getGongshu(zhishigong, 2); 
		w("现值使宫"+p.getGongshu(zhishigong)+"取宫的先后天数中的最大数（"+maxnum+"），寿命应在"+maxnum+"0以上；",true);
	}
	/**
	 * 七、
	 */
	public void getLife7() {
		int[] ngTpjy = p.getTpjy(p.getDiGong(SiZhu.ng, SiZhu.nz));  //得到地盘年干所在宫的天盘奇仪
		int[] ngDpjy = p.getDpjy(p.getTianGong(SiZhu.ng, SiZhu.nz));//得到天盘年干所在宫的地盘奇仪
		int tpjy6[] = p.getTpjy(6); //得到乾宫天盘奇仪
		int dpjy6[] = p.getDpjy(6); //得到乾宫地盘奇仪
		int tpjy2[] = p.getTpjy(2); //得到坤宫天盘奇仪
		int dpjy2[] = p.getDpjy(2); //得到坤宫地盘奇仪
		int xdDjy[] = p.getDpjy(yuetpgong);
		int xdTjy[] = p.getTpjy(yuedpgong);
		int zyDjy[] = p.getDpjy(shitpgong);
		int zyTjy[] = p.getTpjy(shidpgong);
		
		w("1. 断父母：");
		if(tpjy6[0]==YiJing.GENG || tpjy6[1]==YiJing.GENG)	w("天盘六庚临乾宫，父早亡；",true);	
		else if(dpjy6[0]==YiJing.GENG || dpjy6[1]==YiJing.GENG) w("地盘六庚临乾宫，父早亡；",true);	
		if(tpjy2[0]==YiJing.GENG || tpjy2[1]==YiJing.GENG) w("天盘六庚临坤宫，母早亡；",true);	
		else if(dpjy2[0]==YiJing.GENG || dpjy2[1]==YiJing.GENG)	w("地盘六庚临坤宫，母早亡；",true);			
		if(ngTpjy[0]==YiJing.XIN || ngTpjy[1]==YiJing.XIN) w("天盘六辛临父母宫，父母一生抑郁难伸；",true);	
		else if(ngDpjy[0]==YiJing.XIN || ngDpjy[1]==YiJing.XIN) w("地盘六辛临父母宫，父母一生抑郁难伸；",true);
		if(ngTpjy[0]==YiJing.REN || ngTpjy[1]==YiJing.REN) w("天盘六壬临父母宫，父母一生抑郁难伸；",true);	
		else if(ngDpjy[0]==YiJing.REN || ngDpjy[1]==YiJing.REN) w("地盘六壬临父母宫，父母一生抑郁难伸；",true);
		
		w("2. 断兄弟姐妹：");
		if(xdTjy[0]==YiJing.GENG || xdTjy[1]==YiJing.GENG) w("天盘六庚临兄弟宫，兄弟姐妹关系不好；",true);	
		else if(xdDjy[0]==YiJing.GENG || xdDjy[1]==YiJing.GENG) w("地盘六庚临兄弟宫，兄弟姐妹关系不好；",true);	
		if(xdTjy[0]==YiJing.XIN || xdTjy[1]==YiJing.XIN) w("天盘六辛临兄弟宫，兄弟姐妹一生抑郁难伸；",true);	
		else if(xdDjy[0]==YiJing.XIN || xdDjy[1]==YiJing.XIN) w("地盘六辛临兄弟宫，兄弟姐妹一生抑郁难伸；",true);
		if(xdTjy[0]==YiJing.REN || xdTjy[1]==YiJing.REN) w("天盘六壬临兄弟宫，兄弟姐妹一生抑郁难伸；",true);	
		else if(xdDjy[0]==YiJing.REN || xdDjy[1]==YiJing.REN) w("地盘六壬临兄弟宫，兄弟姐妹一生抑郁难伸；",true);
		String xdws = QiMen.gong_yue[yuetpgong][SiZhu.yz];
		if(xdws.equals(QiMen.wxxqs1) || xdws.equals(QiMen.wxxqs2))
			w("月干["+YiJing.TIANGANNAME[SiZhu.yg]+"]落"+yuetpgong+"宫旺相，"+p.getGongshu(yuetpgong)+"取其中的大数，则兄弟姐妹共有"+p.getGongshu(yuetpgong,1)+"个",true);
		else if(xdws.equals(QiMen.wxxqs5))
			w("月干["+YiJing.TIANGANNAME[SiZhu.yg]+"]落"+yuetpgong+"宫处死地，"+p.getGongshu(yuetpgong)+"取其中的小数，则兄弟姐妹共有"+p.getGongshu(yuetpgong,-1)+"个",true);
		else 
			w("月干["+YiJing.TIANGANNAME[SiZhu.yg]+"]落"+yuetpgong+"宫休囚，"+p.getGongshu(yuetpgong)+"取其中的中数，则兄弟姐妹共有"+p.getGongshu(yuetpgong,-2)+"个",true);
		
		
		w("3. 断子孙：");
		if(zyTjy[0]==YiJing.GENG || zyTjy[1]==YiJing.GENG) w("天盘六庚临子孙宫，主子女少而难养；",true);	
		else if(zyDjy[0]==YiJing.GENG || zyDjy[1]==YiJing.GENG) w("地盘六庚临子孙宫，主子女少而难养；",true);	
		if(zyTjy[0]==YiJing.XIN || zyTjy[1]==YiJing.XIN) w("天盘六辛临子孙宫，子孙后代一生抑郁难伸；",true);	
		else if(zyDjy[0]==YiJing.XIN || zyDjy[1]==YiJing.XIN) w("地盘六辛临子孙宫，子孙后代一生抑郁难伸；",true);
		if(zyTjy[0]==YiJing.REN || zyTjy[1]==YiJing.REN) w("天盘六壬临子孙宫，子孙后代一生抑郁难伸；",true);	
		else if(zyDjy[0]==YiJing.REN || zyDjy[1]==YiJing.REN) w("地盘六壬临子孙宫，子孙后代一生抑郁难伸；",true);
		String childws = QiMen.gong_yue[shitpgong][SiZhu.yz];
		if(childws.equals(QiMen.wxxqs1) || childws.equals(QiMen.wxxqs2))
			w("时干["+YiJing.TIANGANNAME[SiZhu.sg]+"]落"+shitpgong+"宫旺相，"+p.getGongshu(shitpgong)+"取其中的大数，子女共有"+p.getGongshu(shitpgong,1)+"个",true);
		else if(childws.equals(QiMen.wxxqs5))
			w("时干["+YiJing.TIANGANNAME[SiZhu.sg]+"]落"+shitpgong+"宫处死地，"+p.getGongshu(shitpgong)+"取其中的小数，子女共有"+p.getGongshu(shitpgong,-1)+"个",true);
		else 
			w("时干["+YiJing.TIANGANNAME[SiZhu.sg]+"]落"+shitpgong+"宫休囚，"+p.getGongshu(shitpgong)+"取其中的中数，子女共有"+p.getGongshu(shitpgong,-2)+"个",true);
		t = "";
		if(p.isKong(shitpgong)) t += "遇空亡，";
		if(p.isYima(shitpgong))  t += "逢马星，";
		if(!t.equals("")) w("但时干"+t+"表示子女有的走，有的死；",true);
		
		t="";
		if(shitpgong==simengong) t+="逢死门，";
		if(p.isTGanMu(SiZhu.sg, SiZhu.sz)) t+="入墓，";
		if(!t.equals("")) w("但时干"+t+"表示子女有流产或夭折信息；",true);
		
	}
	
	public void getLife8() {
		int gong = getFirstGong();		
		for(int i=0; i<8; i++) {
			gong = (i==0) ? gong : getNextGong(gong);			
//			getShare3(true,gong,true,false);
//			getShare21(true,gong,true);
//			getShare22(true,gong,true,false);
			int[] jxindex = getDayun(false, gong, false,false);
			w((i+1)+". "+(i*15+1)+"～"+((i+1)*15)+"岁----"+QiMen.dpjg[gong]+"宫（"+getJXindex(jxindex[0], jxindex[1])+"）：");
			getDayun(true, gong, true,false);
		}
	}
	
	public void getLife9() {
		//只推算0～60岁的运气
		for(int i=1; i<=60; i++) {			
			int[] gz = getNextSuiGanzi(i);
			String gan = YiJing.TIANGANNAME[gz[0]];
			String zi = YiJing.DIZINAME[gz[1]];
			int niangangong = p.getTianGong(gz[0], gz[1]);
			int nianzigong = p.getDiziGong(gz[1]);
			
			//吉凶性指数			
			int[] jxindex = getYearInfo(niangangong, nianzigong, i, gz[1], false, false,false);
			if(jxindex[0]>=INDEXLINE || jxindex[1]>=INDEXLINE) {
				w(i+"岁----"+(year==0?"":(year+i))+"("+getNextSui(i)+"，"+gan+niangangong+"宫，"+zi+nianzigong+"宫 "+getJXindex(jxindex[0],jxindex[1])+" )：");
				getYearInfo(niangangong, nianzigong, i, gz[1], true,true,true);
				w(p.NEWLINE);
			}			
		}
	}
	
	private int[] getDayun(boolean isw,int g,boolean iskong,boolean ispre) {
		int[] jxtg3 = getShare3(isw,g,iskong,ispre);
		int[] jxtg21 = getShare21(isw,g,iskong);
		int[] jxtg22 = getShare22(isw,g,iskong,ispre);
		
		return new int[]{jxtg3[0]+jxtg21[0]+jxtg22[0],jxtg3[1]+jxtg21[1]+jxtg22[1]};
	}
	
	private int[] getYearInfo(int niangangong, int nianzigong, int sui, int nianzi, boolean isw, boolean iskong,boolean ispre) {
		//int ji = 0, xi=0; //设一个吉凶性指数
		
		int[] jxtg3 = getShare3(isw,niangangong,iskong,ispre);
		//getShare21(isw,niangangong,iskong,ispre);
		int[] jxtg22 = getShare22(isw,niangangong,iskong,ispre);
		int[] jxtg1 = {0,0};
		if(niangangong!=nianzigong) {
			jxtg1 = getShare1(isw,niangangong,iskong,ispre); //不相等，则输出年干宫共享信息					
		}
		int[] jxtg = getNiangan(isw,niangangong, iskong,ispre);		
		
		int[] jxzi3 = getShare3(isw,nianzigong,iskong,ispre);
		//getShare21(isw,nianzigong,iskong,ispre);
		int[] jxzi22 = getShare22(isw,nianzigong,iskong,ispre);
		int[] jxzi1 = getShare1(isw,nianzigong,iskong,ispre); //不管是否相等，均输出年支宫共享信息，年干专用信息，年支专用信息
		int[] jxzi = getNianzi(isw,nianzigong, iskong, sui, nianzi,ispre);		
		 
		//System.out.println(sui+":"+(jxtg3[0]+jxtg22[0]+jxtg1[0]+jxtg[0]+jxzi3[0]+jxzi22[0]+jxzi1[0]+jxzi[0]));
		//System.out.println(sui+":"+(jxtg3[1]+jxtg22[1]+jxtg1[1]+jxtg[1]+jxzi3[1]+jxzi22[1]+jxzi1[1]+jxzi[1]));
		return new int[]{jxtg3[0]+jxtg22[0]+jxtg1[0]+jxtg[0]+jxzi3[0]+jxzi22[0]+jxzi1[0]+jxzi[0],
				jxtg3[1]+jxtg22[1]+jxtg1[1]+jxtg[1]+jxzi3[1]+jxzi22[1]+jxzi1[1]+jxzi[1]};
	}
	
	/**
	 * 年支专用,year为岁数，yearzi为该岁的年支
	 * @param w是否输出，ispre是否显示前缀，b是否空格
	 * @param year 推算流年年份
	 * @param yearzi 流年年支
	 * @return
	 */
	private int[] getNianzi(boolean w, int g,boolean b,int year, int yearzi,boolean ispre) {
		int ji = 0, xi=0; //设一个吉凶性指数
		int down = 18, up = 45;
		int dz = QiMen.jgdz[g];  //得到九宫所藏的地支
    int dz1 = dz%100, dz2 = dz/100;  //取到其中的每个    	
    String rgname = YiJing.TIANGANNAME[SiZhu.rg];
    String nzname = YiJing.DIZINAME[yearzi];
    String pre = g+"宫：";
    String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		String pipre = ispre ? PIINDEX+" "+pre : "";
		
		if(year>up) {//老怕帝旺5
			if(SiZhu.TGSWSJ[SiZhu.rg][yearzi]==5) {
				w(w,xipre+"日干["+rgname+"]在年支["+nzname+"]处{"+SiZhu.TGSWSJNAME[5]+"}地，老怕帝旺少怕衰，大凶；",b); 
				xi++; 
			}else{
				w(w,jipre+" "+pre+"日干["+rgname+"]在年支["+nzname+"]处{"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.rg][yearzi]]+"}地，吉；",b); 
				ji++; 
			}
		}	else if(year<down) {//少怕衰病6,7,
			boolean istrue = false;
			for(int i=6; i<=7; i++) {
				if(SiZhu.TGSWSJ[SiZhu.rg][yearzi]==i) {
					w(w,xipre+"日干["+rgname+"]在年支["+nzname+"]处{"+SiZhu.TGSWSJNAME[i]+"}地，老怕帝旺少怕衰，大凶；",b); 
					xi++; 
					istrue = true;
					break;
				}
			}
			if(!istrue) {
					w(w,jipre+"日干["+rgname+"]在年支["+nzname+"]处{"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.rg][yearzi]]+"}地，吉；",b); 
					ji++; 
			}
		} else if(year>=down && year<=up) {  //中年最怕死墓绝胎养8，9，10，11，12
			boolean istrue = false;
			for(int i=8; i<=12; i++){
				if(SiZhu.TGSWSJ[SiZhu.rg][yearzi]==i) {
					w(w,xipre+"日干["+rgname+"]在年支["+nzname+"]处{"+SiZhu.TGSWSJNAME[i]+"}地，中年最怕死绝胎，大凶；",b); 
					xi++; 
					istrue = true;
					break;
				}
			}
			if(!istrue) {
				w(w,jipre+"日干["+rgname+"]在年支["+nzname+"]处{"+SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[SiZhu.rg][yearzi]]+"}地，吉；",b); 
				ji++; 
			}
		}
		
		if(p.isYima(p.getChongGong(p.getDiziGong(yearzi)))) w(w,pipre+"年支冲动马星落宫地支之年，有动象；",b);
		if(p.isKong(g) && (dz1==yearzi || dz2==yearzi)) w(w,pipre+"本宫空亡，被年支填实，必有变化；",b);
		if(p.isKong(g) && (p.getChongzi(dz1)==yearzi || p.getChongzi(dz2)==yearzi)) w(w,pipre+"本宫空亡，被年支冲实，必有变化；",b);
		if(p.isYima(g) || p.isYimaOfRi(g)) w(w,pipre+"年支为马星，年内必有变化；",b);
		
		return new int[]{ji,xi};
	}
	
	/**
	 * 年干专用
	 * @param w是否输出，ispre是否显示前缀，b是否空格
	 * @return
	 */
	
	private int[] getNiangan(boolean w, int g,boolean b,boolean ispre) {
		int ji = 0, xi=0; //设一个吉凶性指数
		String pre = g+"宫：";  
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		int[] dpjy = p.getTpjy(g);
		int rigan = p.getTiangan(SiZhu.rg, SiZhu.rz);
		if(dpjy[0]== rigan || dpjy[1]==rigan) {
			w(w,jipre+"日干与年干相同，为天干透出，大有作为；",b);
			ji++;
		}
		
		return new int[]{ji,xi};
	}
	
	/**
	 * 得到流年年干与年支共享信息
	 * @param g
	 * @param isw是否输出，ispre是否显示前缀，b是否空格
	 * @int index：凶性指数，返回后供判断该年是否要显示出来
	 */
	private int[] getShare1(boolean w, int g,boolean b,boolean ispre) {
		int ji = 0, xi=0; //设一个吉凶性指数
		String pre = g+"宫：";
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		//在share3与share22中已有了
//		String[] bmjx = p.getmenJX(g);
//		String[] bsjx = p.getshenJX(g);
//		String[] jxjx = p.getxingJX(g);
//		String[] gjjx = p.getJixiongge(g);
//		if(gjjx[0].equals("0")) {  //遇凶格，再得一凶象，则凶性指数加1
//			//t = "逢"+gjjx[1]+"凶象；";
//			if(bmjx[0].equals("-1") && bsjx[0].equals("-1") && jxjx[0].equals("-1")) {t+="又临"+bmjx[1]+jxjx[1]+bsjx[1]; index++;}
//			else if(bmjx[0].equals("-1") && bsjx[0].equals("-1")) { t+="又临"+bmjx[1]+bsjx[1];index++;} 
//			else if(bmjx[0].equals("-1") && jxjx[0].equals("-1")) { t+="又临"+bmjx[1]+jxjx[1];index++;}
//			else if(jxjx[0].equals("-1") && bsjx[0].equals("-1")) { t+="又临"+bmjx[1]+bsjx[1];index++;}
//			
//			if(p.isKong(g)) { t+="逢凶格，又遇空亡，凶事更加重；"; index++;}			
//			w(w,pre+t,b);
//		}else{	//遇吉格，则凶性指数减1
//			w(w,pre+"逢"+gjjx[1]+"吉；",b);
//			index--;
//		}
		
		if(p.isChongke(g, ritpgong)) {
			w(w,xipre+"冲克日干宫，必有大灾大难；",b);
			xi++;
		}

		boolean ismenpo = QiMen.men_gong[daoqm.gInt[3][1][g]][g].equals(QiMen.zphym2);
		boolean ismenzhi = QiMen.men_gong[daoqm.gInt[3][1][g]][g].equals(QiMen.zphym1);
		if(ismenpo) {w(w,xipre+"逢门迫，人事不利；",b); xi++;}
		if(ismenzhi) {w(w,xipre+"逢门制，人事不利；",b); xi++;}
		
		if(p.isTGanMu(g)) {w(w,xipre+"天盘干在本宫入墓，一年困顿之象；",b);xi++;}
		
		if(zhifugong==g) {w(w,jipre+"临值符，百灾消退；",b);ji++;}
		
		int[] dpjy = p.getDpjy(g);
		if(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN) {w(w,xipre+"下临壬，年内颠沛流离；",b);xi++;}
		if(dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG) {w(w,xipre+"下临庚，大凶，凡事有阻，丢官破财；",b);xi++;}
		
		if(g==shangmengong && g==xingchonggong) {w(w,xipre+"伤门加天冲，遭遇车祸之象；",b);xi++;}
		
		return new int[]{ji,xi};
	}
	/**
	 * 得到本宫格局信息，大运、流年共享
	 * @param isw是否输出，ispre是否显示前缀，b是否空格
	 */
	private int[] getShare22(boolean isw, int g,boolean b,boolean ispre) {
		int rigan = p.getTiangan(SiZhu.rg, SiZhu.rz);
		String riganname = YiJing.TIANGANNAME[rigan];
		int xi = 0;  //凶指
		int ji = 0;  //吉指
		String pre = g+"宫：";
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		if(QiMen.gan_gong_mu[rigan][g]!=null) {
			t = ("本宫为日干["+riganname+"]之墓库，为潜龙勿用，处于困顿状态；");
			if(p.isYima(g) || p.isYimaOfRi(g)) t += "幸临马星，则表示没有完全困住；";
			else if(p.isYima(p.getChongGong(g)) ||
					p.isYimaOfRi(p.getChongGong(g))) t+= "幸遇对宫马星相冲，则表示没有完全困住；";
			w(isw ,xipre+t,b);
			xi++;
		}
		
		String[] bmjx = p.getmenJX(g);
		String[] bsjx = p.getshenJX(g);
		String[] jxjx = p.getxingJX(g);
		String[] gjjx = p.getJixiongge(g);
		if(gjjx[0].equals("0")) {  //遇凶格则为凶
			t = "遇"+gjjx[1];
			if(bmjx[0].equals("-1")) t+="逢"+bmjx[1];
			if(jxjx[0].equals("-1")) t+="临"+jxjx[1];
			if(bsjx[0].equals("-1")) t+="乘"+bsjx[1];
			w(isw ,xipre+t+"人生不如意十之八九；",b);  //必是人生中一段艰难的时光，时运不济
			xi++;
		}
		else if(gjjx[0].equals("1")) {  //遇吉格才叫吉
			String ma = "";
			if(p.isYima(g) || p.isYimaOfRi(g)) ma = "又逢马星，必定发迹更快；";
			else if(p.isYima(p.getChongGong(g)) ||
					p.isYimaOfRi(p.getChongGong(g))) t+= "又逢对宫马星相冲，必定发迹更快；";
			
			t = "遇"+gjjx[1];
			if(!bmjx[0].equals("-1")) t+="逢"+bmjx[1];
			if(!jxjx[0].equals("-1")) t+="临"+jxjx[1];
			if(!bsjx[0].equals("-1")) t+="乘"+bsjx[1];
			w(isw , jipre+t+"人生得意马蹄急；"+ma,b);  //必是人生中一段成功、得意的时间
			ji++;
		}
		
		if(g==zhifugong && p.isSheng(g, ritpgong)) {
			w(isw, jipre+"临值符，生日宫，加官进爵之象",b);
			ji++;
		}
		
		return new int[]{ji,xi};
	}
	
	/**
	 * 得到本宫信息，大运、流年共享
	 * @param g
	 */
	private int[] getShare21(boolean isw, int g,boolean b) {
		int ji=0, xi=0; 
		
		if(p.getshenJX(g)[0].equals("1"))  ji++ ;
		else if(p.getshenJX(g)[0].equals("-1")) xi++;	//daoqm.gInt[1][1][g];		
		if(p.getmenJX(g)[0].equals("1"))  ji++ ;
		else if(p.getmenJX(g)[0].equals("-1")) xi++;	//daoqm.gInt[3][1][g];
		if(p.getxingJX(g)[0].equals("1"))  ji++ ;
		else if(p.getxingJX(g)[0].equals("-1")) xi++;	 // daoqm.gInt[2][1][g];
		
		w(isw && g==dumengong,"遇杜门，主在家乡，或发展遇到了堵塞，也主武装；",b);
		w(isw && g==jing3mengong,"遇景门，主文化，荣誉、声名，利读书；",b);
		w(isw && g==kaimengong,"遇开门，主开创一番事业，或主重新开始新的生活，又主官职，若为隐私之事必被泄漏；",b);
		w(isw && g==shangmengong,"遇伤门，主伤病；",b);
		w(isw && g==shengmengong,"遇生门，利求财；",b);
		w(isw && g==xiumengong,"遇休门，主婚姻和家庭方面；",b);
		w(isw && g==simengong,"遇死门，防凶丧孝服，吉的一方面是掌握生杀大权，但经商大凶；",b);
		w(isw && g==jing1mengong,"遇惊门，主口舌官非；",b);
		
		w(isw && g==xingfugong,"临天辅星，主读书,传播文化；",b);
		w(isw && g==xingyinggong,"临天英星，主文化、声名鹊起；",b);
		w(isw && g==xingchonggong,"临天冲星，为武事；",b);
		w(isw && g==xingruigong,"临天芮星，主疾病；",b);
		w(isw && g==xingrengong,"临天任星，大吉；",b);
		w(isw && g==xingzhugong,"临天柱星星，喜杀好战，能言善辨，有中流砥柱的一面；",b);
		w(isw && g==xingxingong,"临天心星，为医药；",b);
		w(isw && g==xingpenggong,"临天蓬凶星，主犯大罪或成为大智慧的人、大商人，也主不停的整人；",b);
		w(isw && g==xingqingong,"临天禽星，为百官之首；",b);
		
		w(isw && g==shenhegong,"上乘六合，主婚姻；",b);
		w(isw && g==shenyingong,"上乘太阴，为阴佑之神，贵人相助之象；",b);
		w(isw && g==shenshegong,"上乘腾蛇，为虚惊；",b);
		w(isw && g==shendigong,"上乘九地，坚牢之神；",b);
		w(isw && g==shentiangong,"上乘九天，为预谋前途之事，显扬之象，代表志向高大，也主扬兵，或命归西天；",b);
		w(isw && g==zhifugong,"上乘值符，百灾消退；",b);
		w(isw && g==shenhugong,"上乘白虎，主凶丧孝服；",b);
		w(isw && g==shenwugong,"上乘玄武，主匪盗；",b);
		
		return new int[]{ji,xi};
	}
	
	/**
	 * 得到本宫信息，为日宫、大运、流年共享
	 * @param isw表示是否输出，b表示是否空格，ispre表示是否要显示前缀
	 * @return int[] {ji, xi}
	 */
	private int[] getShare3(boolean isw, int g,boolean b, boolean ispre) {
		String s = null;
		String[] shenjx = p.getshenJX(g);
		int xi = 0;  //凶指
		int ji = 0;  //吉指
		String pre = g+"宫：";
		String jipre = ispre ? JIINDEX+" "+pre : "";
		String xipre = ispre ? XIINDEX+" "+pre : "";
		
		if(g==shangmengong && g==shenshegong) {
			s = "伤门加腾蛇，受伤被蛇咬之象；";
			if(SiZhu.nz==YiJing.SI) s+="年命属蛇，上临腾蛇则不凶反吉；";	
			w(isw,xipre+s,b);
			xi++;
		}
		
		int[] ggong = p.getTpjy(g);
		w(isw && g==shenhugong && (YiJing.GENG==ggong[0] || YiJing.GENG==ggong[1]),"白虎加庚有权柄之象。",b);
		if(p.isJixing(g)) {
			w(isw, xipre+"刑主伤病官非，又主刑动，其动幅小于冲；",b);
			xi++;
		}
		if(p.isKong(g)) {
			w(isw, xipre+"空亡吉者减昌，苦者更苦，颠簸困苦；",b);
			xi++;
		}
		if(p.isTDGanHe(g)) {
			ji++;
			if(shenjx[0].equals("-1")) 
				w(isw, jipre+"落宫天干与地干相合，有同别人合作之象，本为吉格，但上临"+shenjx[1]+"，故为苟且之合或利益之合；",b);
			else
				w(isw, jipre+"落宫天干与地干相合，有同别人合作之象；",b);
		}
		
		return new int[]{ji,xi};
	}
	
	private int getFirstGong() {
		return QiMen.ziOfGua[SiZhu.nz];
	}
	private int getNextGong(int gong) {
		if(gong==5) {
			Messages.error("取第5宫的下一宫出错，不存大第5宫！");
			return -1;
		}
		int[] gongs = {1,8,3,4,9,2,7,6};
		for(int i=0; i<gongs.length; i++) {
			if(gongs[i]==gong) {
				return i<gongs.length-1 ? gongs[i+1] : gongs[0];
			}
		}
		Messages.error("取第"+gong+"宫的下一宫出错！");
		return -1;
	}
	/**
	 * 在出生年份上加上指定的add年后，返回干支
	 * @param add
	 * @return
	 */
	private String getNextSui(int add) {
		int gan = (SiZhu.ng+add)%10==0 ? 10 : (SiZhu.ng+add)%10; 
		int zi = (SiZhu.nz+add)%12==0 ? 12 : (SiZhu.nz+add)%12; 
		return YiJing.TIANGANNAME[gan]+YiJing.DIZINAME[zi];
	}
	private int[] getNextSuiGanzi(int add) {
		int gan = (SiZhu.ng+add)%10==0 ? 10 : (SiZhu.ng+add)%10; 
		int zi = (SiZhu.nz+add)%12==0 ? 12 : (SiZhu.nz+add)%12; 
		return new int[]{gan,zi};
	}
	
	/**
	 * 得到吉凶指数星形描述
	 * @param ji: 为吉的指数★表示
	 * @param xi：为凶的指数☆表示
	 * @return
	 */
	private String getJXindex(int ji, int xi) {
		String s = "";
		for(int i=0; i<ji; i++) {
			s+=JIINDEX;
		}
		for(int i=0; i<xi; i++) {
			s+=XIINDEX;
		}
		return s;
	}
}