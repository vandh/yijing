package org.boc.db.qm;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;

public class QimenJibing extends QimenBase{
	private String[] jibing ; //保存命运宫所有的描述情况
	int index = 0; //计数器，为descMY所用

	public QimenJibing(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
  
	public String getBing(StringBuffer str,String mzText, int ysNum,boolean boy) {
		init(mzText, ysNum, boy, 2000);	//2000为数组长度	
		
		w(p.NOKG+"一、定用神：");
		getBing1();
		w(p.NEWLINE);
		
		w(p.NOKG+"二、详断：");
		w("1) 何人有病：");
		getBing21();
		w(p.NEWLINE);
		w("2) 有何病：");
		getBing22();
		w(p.NEWLINE);		
		w("3) 医药：");
		getBing23();
		w(p.NEWLINE);
		w("4) 病情：");
		getBing24();
		w(p.NEWLINE);
		w("5) 应期：");
		getBing25();
		w(p.NEWLINE);
		w("6) 年命：");
		getBing26();
		w(p.NEWLINE);
		w("7) 其它：");
		getBing27();
		w(p.NEWLINE);
		
		return p.format(str, my);
	}
	
	/**
	 * 一、大格局断
	 */
	public void getBing1() {
		w("天芮为疾病、故障；天心为西药；");
		w("乙为中药、医生；庚为白虎，主手术；");
		w("开门为手术；");
	}
	//何人有病
	public void getBing21() {
		w("病落"+xingruigong+"宫，"+QiMen.JIUGONGPEOPLE2[xingruigong]+"有病；");		
		if(p.isGongke(yitpgong, xingruigong)) w("乙宫克芮星病宫，妻无病；");
		boolean isnei = p.isNenpan(xingruigong);
		
		if(xingruigong==niantpgong || xingruigong==niandpgong) {
			if(isnei) w("天芮星落"+xingruigong+"宫，在内盘，临年干，主家里长辈或父母有疾病；");
			else w("天芮星落"+xingruigong+"宫，临年干，在外盘，主岳父、岳母或长辈有疾病；");
		}
		if(xingruigong==yuetpgong || xingruigong==yuedpgong) {
			if(isnei) w("天芮星落"+xingruigong+"宫，在内盘，临月干，主家里兄弟姐妹有疾病；");
			else w("天芮星落"+xingruigong+"宫，临月干，在外盘，主妹夫、姐夫、姨妹子有疾病；");
		}
		if(xingruigong==shitpgong || xingruigong==shidpgong) {
			if(isnei) w("天芮星落"+xingruigong+"宫，在内盘，临时干，主家中小孩有疾病；");
			else w("天芮星落"+xingruigong+"宫，临时干，在外盘，主亲戚家小孩有疾病；");
		}
	}
	//有何病
	public void getBing22() {
		boolean isnei = p.isNenpan(xingruigong);
		String bwyi = (yitpgong==1 || yitpgong==8 || yitpgong==3 || yitpgong==4) && !p.isNenpan(1) ? 	"左侧" : "右侧";
		String bwxin = (xintpgong==1 || xintpgong==8 || xintpgong==3 || xintpgong==4) && !p.isNenpan(1) ? 	"左侧" : "右侧";
		String bwren = (rentpgong==1 || rentpgong==8 || rentpgong==3 || rentpgong==4) && !p.isNenpan(1) ? 	"左侧" : "右侧";
		String bwgui = (guitpgong==1 || guitpgong==8 || guitpgong==3 || guitpgong==4) && !p.isNenpan(1) ? 	"左侧" : "右侧";
		
		if(isnei) w("天芮星落"+xingruigong+"宫，在内盘，主"+QiMen.JGBUWEINEI[xingruigong]+"疾病；");
		else w("天芮星落"+xingruigong+"宫，在外盘，主"+QiMen.JGBUWEIWAI[xingruigong]+"疾病；");
		w("时干也代表所问疾病、伤门代表车祸、死门为伤疤、庚、白虎落宫都要看，可和芮宫同断；");
		w("天芮星落宫之上乘干受克，门受迫制，此干、门所代表部位必有疾病；");
		
		if(xingruigong==shenhegong) w("天芮星临六合，代表不止一种病；");
		if(p.isKong(shenhegong)) w("六合主阴私合和之事，现落"+shenhegong+"宫空亡，表示无性生活；");
		if(xingruigong==shenwugong) w("天芮临玄武，代表有暗疾或与流动状态的疾病（血液、尿、唾液），或病人昏迷；");
		if(xingruigong==shenshegong) w("天芮临藤蛇，疾病缠绕，也主病转移成别的病。");
		
		if(xingruigong==zhifugong) w("天芮临值符，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[1]:"外盘，"+QiMen.TIANGANWAI[1])+"疾病；");
		if(xingruigong==yitpgong) w("天芮临乙，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[2]:"外盘，"+bwyi+QiMen.TIANGANWAI[2])+"疾病；");
		if(xingruigong==bingtpgong) w("天芮临丙，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[3]:"外盘，"+QiMen.TIANGANWAI[3])+"疾病；");
		if(xingruigong==dingtpgong) w("天芮临丁，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[4]:"外盘，"+QiMen.TIANGANWAI[4])+"疾病；");
		if(xingruigong==wutpgong) w("天芮临戊，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[5]:"外盘，"+QiMen.TIANGANWAI[5])+"疾病；");
		if(xingruigong==jitpgong) w("天芮临己，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[6]:"外盘，"+QiMen.TIANGANWAI[6])+"疾病；");
		if(xingruigong==gengtpgong) w("天芮临庚，"+(isnei? "内盘，"+QiMen.TIANGANNEI[7]:"外盘，"+QiMen.TIANGANWAI[7])+"疾病；如逢吉门三奇为良性；");
		if(xingruigong==xintpgong) w("天芮临辛，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[8]:"外盘，"+bwxin+QiMen.TIANGANWAI[8])+"疾病；");
		if(xingruigong==rentpgong) w("天芮临壬，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[9]:"外盘，"+bwren+QiMen.TIANGANWAI[9])+"疾病；");
		if(xingruigong==guitpgong) w("天芮临癸，落"+ (isnei ? "内盘，"+QiMen.TIANGANNEI[10]:"外盘，"+bwgui+QiMen.TIANGANWAI[10])+"疾病；");
		
		if(xingruigong==shenhugong) w("天芮临白虎，主伤病；");
		if(xingruigong==jing3mengong) w("天芮临景门，主吐血、出血、血管疾病，也主动手术，或营养跟得上；");
		if(shangmengong==shentiangong) w("伤门与九天同落"+shangmengong+"宫，高处落下摔伤之象；");
		
		if(xingruigong==6 && gengtpgong==6) w("乾也主男生殖器，遇庚主有阻房事不举；");
		if(xingruigong==2 && shenhugong==2 && kaimengong==2) w("坤+白虎+开门=做流产手术；");
		if(xingruigong==9 && shenwugong==9 && dingtpgong==9) w("丁+玄武+离=脑溢血；");
		if(xingruigong==xintpgong && xintpgong==guitpgong) w("辛+癸=心脏不好，心率不齐，心跳快；");
		if(xingruigong==3 && simengong==3) w("震+死=陈旧性脂肪肝；");
		if(shangmengong==6) w("伤+乾+凶格=头部受过伤；");
		if(xingruigong==dingtpgong && dingtpgong==xintpgong) w("丁+辛=心脏病；");
		if(xingruigong==dingtpgong && dingtpgong==gengtpgong) w("丁+庚=心脏病；");
		if(shangmengong==dingtpgong && p.isTGanMu(YiJing.DING, 0)) w("伤+丁+入墓=心脏病；");
		if(shangmengong==4 && shenhugong==4) w("巽+白虎+伤[+击刑]=脑血管疾病，巽主血管；");
		if(simengong==7 && xingxingong==7) w("死+兑宫+天心=医生手术留下7厘米长的疤；");
		if(xingruigong==dingtpgong && !p.isNenpan(xingruigong)) w("丁+外盘=口腔溃疡；");
		if(xingruigong==jitpgong && jitpgong==guidpgong) w("己+癸，痔疮；");
		if((gengtpgong==6 || gengtpgong==9) && gengtpgong==guidpgong) w("庚临癸，落乾宫或离宫，脑血栓，脑梗塞等。");
		if(xingruigong==7 && xintpgong==7 &&  xintpgong==wudpgong) w("辛和戊到一块，落兑宫，主骨质增生。");
		if(xingruigong==2 && xintpgong==2 &&  xintpgong==wudpgong) w("辛和戊到一块，落坤宫，一般是颈椎增生。");
		if(xingruigong==4 && xintpgong==4 &&  xintpgong==wudpgong) w("辛和戊到一块，落巽宫，一般是颈椎增生。");		
		if(xingruigong==guitpgong && guitpgong==dingdpgong && !boy) w("癸＋丁，为妇科炎症。");
		if(xingruigong==rentpgong && rentpgong==dingdpgong && !boy) w("壬＋丁，为妇科炎症。");
		if(xingruigong==shenshegong && shenshegong==guitpgong) w("癸水临腾蛇，神经系统出现了问题。");
		
		w("天芮与死门、伤门、庚、白虎同宫多为癌症或其它绝症，且病情严重。只要其中三个在一起也是癌症或绝症；二个一起但逢凶格或天地盘干刑，也可断疾病伤灾，受凶宫冲克之宫可断来源；");
	}
	//医药
	public void getBing23() {
		String[] xinw = p.isxingHeju(xingxingong);
		String[] yiw = p.isganHeju(yitpgong);
		if(xinw[0].equals("1")) w("天心星落"+xingxingong+"宫合局，为良医。"+xinw[1]);
		else w("天心星落"+xingxingong+"宫失局，为庸医。"+xinw[1]);
		if(yiw[0].equals("1")) w("乙奇落"+yitpgong+"宫合局，为良医。"+yiw[1]);
		else w("乙奇落"+yitpgong+"宫失局，为庸医。"+yiw[1]);
		
		if(p.isGongke(xingxingong, xingruigong)) w("不论良医、庸医，但天心星落"+xingxingong+"宫能克天芮病神之"+xingruigong+"宫者，医必有功；");
		if(p.isGongke(yitpgong, xingruigong)) w("不论良医、庸医，但乙奇落"+yitpgong+"宫能克天芮病神之"+xingruigong+"宫者，医必有功；");
		if(p.isGongke(xingruigong,xingxingong)) w("天芮病神之"+xingruigong+"宫克天心星所落"+xingxingong+"宫，虽良医亦不能治也；");
		if(p.isGongke(xingruigong,yitpgong)) w("天芮病神之"+xingruigong+"宫克乙奇所落"+yitpgong+"宫，虽良医亦不能治也；");
		
		if(p.isTaohua(yitpgong)) w("乙奇所落"+yitpgong+"宫处沐浴败地也主起作用不大；");
		if(p.isTGanMu(YiJing.YI,0)) w("乙入墓主医生医术不行；");
		if(yitpgong==shendigong) w("乙临九地主老医生，落几宫则多少岁；");
		if(yitpgong==xingfugong) w("乙为主刀医生临天辅星旺相为教授；");
		if(yitpgong==shentiangong) w("乙临九天也主利动手术；");
		if(yitpgong==shenshegong || yitpgong==simengong) w("乙临螣蛇死门也主巫婆、神汉、仙姑之类。");
		if(yitpgong==dumengong || yitpgong==shenhugong) w("乙奇临杜门、白虎则主医术一流，技术过硬。");
		if(yitpgong==shenyingong) w("乙临太阴则主医生细腻，心细认真。");
		if(yitpgong==dingtpgong || yitpgong==kaimengong) w("乙临开门、丁奇则主手术开刀治疗。");
		
		w("天心星为西药，乙奇为中药。");
		if(xingxingong==kaimengong || xingxingong==xiumengong || xingxingong==shengmengong) w("天心临开、休、生三吉门主药到病除之象。");
		else w("天心临死、惊、伤、杜则主西药不理想；");
		if(yitpgong==kaimengong || yitpgong==xiumengong || yitpgong==shengmengong) w("乙临开、休、生三吉门主药到病除之象。");
		else w("乙临死、惊、伤、杜则中药不理想；");
		if(p.isKong(yitpgong)) w("乙空亡，主医生或中药不起作用；");
		if(p.isKong(xingxingong)) w("天心空亡，主西药不起作用；");
		if(yitpgong==zhifugong || xingxingong==zhifugong) w("天心或乙临值符，则为高级药品，名医名院；");
		if(yitpgong==zhishigong || xingxingong==zhishigong) w("天心或乙临值使，则为高级药品，名医名院；");
		if(yitpgong==shenshegong || xingxingong==shenshegong || yitpgong==shenwugong || xingxingong==shenwugong)
			w("天心或乙临螣蛇或玄武是多虚假不实，假医假药，欺诈上当受骗。");
	}
	//病情
	public void getBing24() {
		if(p.isMenFu() || p.isXingFu()) w("伏吟则为久病、慢性病。得病时间较长，短时期难以治愈。");
		if(p.isMenFan() || p.isXingFan()) w("反呤主病情反复，也主胎不实。反吟近病则愈、久病则凶；又代表急性病。");
		
		if(p.isGongke(ystpgong, xingruigong)) w("用神落"+ystpgong+"宫克芮宫，为人克病，病情发展缓慢，吉。如果落宫旺相，又有吉格或吉星吉神则更佳。");
		else if(p.isChongke(xingruigong, ystpgong)) w("天芮星冲克用神所落"+ystpgong+"宫，大凶。");
		if(p.isganHeju(SiZhu.sg,SiZhu.sz)[0].equals("1")) w("时干落"+shitpgong+"宫合局，吉。");
		else w("时干落"+shitpgong+"宫失局，凶！");
		if(p.isganHeju(ysgan,yszi)[0].equals("1")) w("用神落"+ystpgong+"宫合局，吉。");
		else w("用神落"+ystpgong+"宫失局，凶！");
		if(p.getmenWS(simengong)[0].equals("-1") || (simengong==3 || simengong==4)) w("死门落"+simengong+"宫休囚或受制，吉！");
		
		if(p.isTGanMu(ysgan,yszi)) w("用神落宫入墓，凶！也表示住院（临乙奇为医院把握更大），也表示在家休息或退休；");
		if(p.isDGanMu(ysgan,yszi)) w("用神地盘干落"+ysdpgong+"宫入墓，凶！也表示住院（临乙奇为医院把握更大），也表示在家休息或退休；");		
		if(p.isKong(ysgan,yszi)) w("用神遇空亡，新病落空亡者生，久病落空亡者死，逢空也主没有疾病；");
		
		if(p.getGongWS(xingruigong)[0].equals("1")) w("天芮落宫在月令旺相，病重！");
		else w("天芮落宫在月令休囚，病神受制，病情不是很严重。");
		
		if(p.isGongke2(ystpgong, niantpgong) || p.isGongke2(ystpgong, QiMen.ziOfGua[SiZhu.nz])) w("用神被太岁天干或地支宫所克，或又克太岁，必有灾；");
		if(ystpgong==kaimengong) w("用神临开门主开刀，也主病情明确，病必除掉。");
		if(ystpgong==simengong || ysdpgong==simengong) w("用神在天盘或地盘逢死门，大凶；旺相更甚。");
		
		if(xingruigong==8 || xingruigong==4 || xingruigong==2 || xingruigong==6) w("天芮落四维宫，主时间长");
		if(xingruigong==zhifugong) w("天芮上临值符，不会太厉害；");
		
		if(xingruigong==xingxingong || xingruigong==yitpgong) w("天心星、乙奇与天芮同宫，说明病用药物维持，也表示治疗及时；");
		if(xingruigong==yitpgong && yitpgong==gengdpgong) w("乙临芮宫逢乙庚合，手续成功；");
		if(ystpgong==xingxingong || ystpgong==yitpgong) w("用神临天心或乙奇，有病也治疗及时，同时随身带着药；");
		if(xingruigong==shengmengong) w("天芮得生门者生。");
		if(xingruigong==simengong) w("天芮得死门者死。");
		
		if(xingruigong==6 || xingruigong==7) w("天芮星属土，落乾，兑二宫，土生金，病难治。");
		if(xingruigong==9 || xingruigong==5 || xingruigong==2 || xingruigong==8) w("天芮星属土，落离宫，离火生土，落中五、坤、艮宫也为土，其病缠绵难愈。");
		if(xingruigong==1) w("天芮星属土，落坎宫休囚，病虽不能很快就好，但终归可以痊愈。");
		if(xingruigong==3 || xingruigong==4) w("落震，巽二宫，病受宫克，不药而愈。");
		
		if(xingruigong==yitpgong && yitpgong==xindpgong) w("天芮病神临乙+辛，龙逃走，说明病神即将离去，或病情不会迅速发展。");
		if(xingruigong==bingtpgong && bingtpgong==gengdpgong) w("天芮病神临丙+庚贼必去，说明病神即将离去，或病情不会迅速发展。");
		if(xingruigong==xintpgong && xintpgong==yidpgong) w("天芮病神临辛+乙虎猖狂，虽临吉格，但有病返回之象。");
		if(xingruigong==bingtpgong && bingtpgong==wudpgong) w("天芮病神临丙+戊鸟跌穴，虽临吉格，但有病返回之象。");
		if(xingruigong==wutpgong && wutpgong==bingdpgong) w("天芮病神临戊+丙龙返首，虽临吉格，但有病返回之象。");
		if(xingruigong==xintpgong && xintpgong==yidpgong) w("天芮病神临辛+乙虎猖狂，则说明病情将加重。");
		if(xingruigong==guitpgong && guitpgong==dingdpgong) w("天芮病神临癸+丁蛇夭矫，则说明病情将加重。");
		if(xingruigong==xintpgong && xintpgong==gengdpgong) w("天芮病神临辛+庚白虎出力，则说明病情将加重。");
		if(xingruigong==bingtpgong && bingtpgong==guidpgong) w("天芮病神临丙+癸阴人害事，则说明病情将加重。");
		if(xingruigong==gengtpgong && gengtpgong==bingdpgong) w("天芮病神临庚+丙贼必来，则说明病情将加重。");
		if(xingruigong==wutpgong && wutpgong==dingdpgong) w("天芮病神临戊+丁青龙转光，则预示病情即将好转。");
		if(xingruigong==dingtpgong && dingtpgong==bingdpgong) w("天芮病神临丁+丙星随月转，则预示病情即将好转。");
		if(xingruigong==xintpgong && xintpgong==dingdpgong) w("天芮病神临辛+丁狱神得奇，则预示病情即将好转。");
		if(xingruigong==wutpgong && wutpgong==gengdpgong) w("天芮病神临戊+庚，则疾病要扩散或要转换成另外的疾病，或需换医院医治；");
		if(xingruigong==gengtpgong && gengtpgong==wudpgong) w("天芮病神临庚+戊，则疾病要扩散或要转换成另外的疾病，或需换医院医治；");
		if(xingruigong==gengtpgong && gengtpgong==jidpgong) w("天芮病神临庚+己刑格，也主动手术；");
		if(xingruigong==wutpgong && wutpgong==rendpgong) w("天芮病神临戊+壬青龙入天牢，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==yitpgong && yitpgong==jidpgong) w("天芮病神临乙+己日奇入墓，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==yitpgong && yitpgong==rendpgong) w("天芮病神临乙+壬日奇入地，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==dingtpgong && dingtpgong==xindpgong) w("天芮病神临丁+辛朱雀入狱，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==jitpgong && jitpgong==jidpgong) w("天芮病神临己+己地户逢鬼，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==jitpgong && jitpgong==xindpgong) w("天芮病神临己+辛游魂入墓，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==jitpgong && jitpgong==rendpgong) w("天芮病神临己+壬地网高张，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==jitpgong && jitpgong==guidpgong) w("天芮病神临己+癸地刑玄武，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==xintpgong && xintpgong==wudpgong) w("天芮病神临辛+戊困龙被伤，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==xintpgong && xintpgong==guidpgong) w("天芮病神临辛+癸天牢华盖，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==rentpgong && rentpgong==xindpgong) w("天芮病神临壬+辛螣蛇相缠，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==rentpgong && rentpgong==rendpgong) w("天芮病神临壬+壬蛇入地罗，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==guitpgong && guitpgong==gengdpgong) w("天芮病神临癸+庚太白入网，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==guitpgong && guitpgong==xindpgong) w("天芮病神临癸+辛网盖天牢，则主疾病缠绵不去，病情加重，久病不愈之象。");
		if(xingruigong==jitpgong && jitpgong==xindpgong) w("天芮病神临己+辛，则预示旧病不去，新病又来。");
		if(xingruigong==jitpgong && jitpgong==jidpgong) w("天芮病神临己+己，则预示旧病不去，新病又来。");
		if(xingruigong==yitpgong && yitpgong==bingdpgong) w("天芮病神临乙+丙奇仪顺遂，则说明病情向良好方向发展。");
		if(xingruigong==yitpgong && yitpgong==dingdpgong) w("天芮病神临乙+丁奇仪相佐，则说明病情向良好方向发展。");
		if(xingruigong==bingtpgong && bingtpgong==yidpgong) w("天芮病神临丙+乙日月并行，则说明病情向良好方向发展。");
		if(xingruigong==dingtpgong && dingtpgong==dingdpgong) w("天芮病神临丁+丁星奇入太阴，则说明病情向良好方向发展。");
		if(xingruigong==gengtpgong && gengtpgong==yidpgong) w("天芮病神临庚+乙太白逢星，则说明病情向良好方向发展。");
		if(xingruigong==rentpgong && rentpgong==yidpgong) w("天芮病神临壬+乙小蛇得势，则说明病情向良好方向发展。");
		if(xingruigong==jitpgong && jitpgong==wudpgong) w("天芮病神临己+戊犬遇青龙，则有人帮助，得遇名师良药之象。");
		if(xingruigong==rentpgong && rentpgong==wudpgong) w("天芮病神临壬+戊小蛇化龙，则有人帮助，得遇名师良药之象。");
		if(xingruigong==xintpgong && xintpgong==dingdpgong) w("天芮病神临辛+丁狱神得奇，则有人帮助，得遇名师良药之象。");
		if(xingruigong==dingtpgong && dingtpgong==guidpgong) w("天芮病神临丁+癸朱雀投江必死。");
		if(xingruigong==guitpgong && guitpgong==dingdpgong) w("天芮病神临癸+丁蛇夭矫，易见鬼怪之象。");
		if(xingruigong==guitpgong && guitpgong==guidpgong) w("天芮病神临癸+癸天网四张，则主病难走，人临危。");
		if(xingruigong==guitpgong && guitpgong==xindpgong) w("天芮病神临癸+辛网盖天牢，则主病难走，人临危。");
		
		if(xingruigong==gengtpgong && gengtpgong==rendpgong) w("天芮病神临庚+壬小格，为病情不稳定，胸闷而大小便不畅。");
		if(xingruigong==gengtpgong && gengtpgong==guidpgong) w("天芮病神临庚+癸大格，为病情不稳定，胸闷而大小便不畅。");
		
		int gejuNum = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];
		if(gejuNum==2) w("五不遇时人将逝。");
				
		if(p.isJixing(xingruigong)) {
			if(xingruigong==4 && (rentpgong==4 || guitpgong==4)) w("天芮落宫临六仪击刑主大凶。特别是甲辰壬临巽四宫，甲寅癸临巽四宫必有伤残肢体、腿脚之灾。");
			else if(xingruigong==8 && gengtpgong==8) w("天芮落宫临六仪击刑主大凶。甲申庚临艮八宫则多主车祸、伤灾。");
			else if((xingruigong==3 && wutpgong==3) || 
					(xingruigong==9 && xintpgong==9) || 
					(xingruigong==2 && jitpgong==2)) w("天芮落宫临六仪击刑主大凶。戊临震宫，辛临离宫，己临坤宫一般主病痛之灾。");
			else if(xingruigong==gengtpgong && gengtpgong==guidpgong) w("天芮落宫临六仪击刑主大凶。庚+癸临巽宫，一般均主伤灾病痛。");
			else w("天芮落宫六仪击刑，主大凶。");
		}

		int[] tgan = p.getTpjy(xingruigong);
		int[] dgan = p.getDpjy(xingruigong);
		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;
		//三诈
		boolean is3za = false;
		if((kaimengong==xingruigong || xiumengong==xingruigong || shengmengong==xingruigong) && 
				(isyi || isbing || isding) && (shenyingong==xingruigong)) {
			is3za = true;
		}else 
		if((kaimengong==xingruigong || xiumengong==xingruigong || shengmengong==xingruigong) && 
				(isyi || isbing || isding) && (shenhegong==xingruigong)) {
			is3za = true;
		}else
		if((kaimengong==xingruigong || xiumengong==xingruigong || shengmengong==xingruigong) && 
				(isyi || isbing || isding) && (shendigong==xingruigong)) {
			is3za = true;
		}
		if(is3za) w("天芮落宫逢三诈，病情反复不定。");
		
		//五假
		boolean is5jia = false;
		if(jing3mengong==xingruigong && (isyi || isbing || isding) && shentiangong==xingruigong) {
			is5jia = true;
		}else 
		if(dumengong==xingruigong && (isgui || isji || isding) && (shenyingong==xingruigong || shendigong==xingruigong || shenhegong==xingruigong)) {
			is5jia = true;
		}else 
		if(jing1mengong==xingruigong && isren && shentiangong==xingruigong) {
			is5jia = true;
		}else 
		if(shangmengong==xingruigong && (isgui || isji || isding) && (shendigong==xingruigong || shenhegong==xingruigong)) {
			is5jia = true;
		}else 
		if(simengong==xingruigong && (isgui || isji || isding) && shendigong==xingruigong ) {
			is5jia = true;
		}
		if(is5jia) w("天芮逢五假也非吉兆。");
		
		//九遁
		boolean is9dun = false;
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumengong==xingruigong && shenyingong==xingruigong) {
			is9dun = true;
		}else 
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && dumengong==xingruigong && shendigong==xingruigong) {
			is9dun = true;
		}else 
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimengong==xingruigong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
			is9dun = true;
		}
		if(is9dun) w("天芮逢地遁其人必死，遇鬼遁,人遁也非吉兆。");
		
		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
		if(is3) {
			w("天芮逢三奇得使，对病情有利，特别是丁奇为解救之神。");
		}
		
		if(zhishigong==xingruigong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			w("天芮逢玉女守门，久病不会治愈。");
		}
		
		if(((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) ||
		 	((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || 
						dgan[1]==SiZhu.ng ||dgan[1]==SiZhu.yg ||dgan[1]==SiZhu.rg ||dgan[1]==SiZhu.sg))) {
			w("天芮逢悖格，肢体相伤害，但年月日时临悖格则新旧病可治。");
		}
		
		if((tgan[0]==SiZhu.rg || tgan[1]==SiZhu.rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			w("天芮临飞干格，诊断不明而药不对症。");
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==SiZhu.rg || dgan[1]==SiZhu.rg)) {
			w("天芮临伏干格，诊断不明而药不对症。");
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) {
			w("天芮临伏宫格，病人不安，家人惊恐。");
		}
		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			w("天芮临飞宫格，病人不安，家人惊恐。");
		}
		
	}
	//应期
	public void getBing25() {
		if(p.isNenpan(xingruigong)) w("天芮在内盘快，否则主慢；伏呤主慢；");
		w("如果要去医院，由内外盘定远近，方位就是那个克芮宫的方向；");
		if(p.isTGanMu(SiZhu.ng) && ( xingruigong==niantpgong || p.isGongChong(xingruigong, niantpgong)))
			w("太岁入墓之年，又冲动芮宫或落芮宫，必生疾病；");
		if(p.isGongChong(niantpgong, ritpgong) && p.isGongke2(QiMen.ziOfGua[SiZhu.nz], QiMen.ziOfGua[SiZhu.rz]))
			w("与日柱天冲地克之年，必生疾病；");
		w("看天芮落宫的十干是什么，克此干之月日为病愈之期。");
		w("用神死墓之年月，大病必死；");
		w("值使落宫可断病愈或死的天数，临干也可断具体日期；");
		if(p.isNenpan(shitpgong) && p.isNenpan(ritpgong)) w("动手术，日时同为内盘，主快。乙为医生，冲其宫之日动手术；");
		else w("动手术，日时同为外盘或一内一外，主慢。乙为医生，冲其宫之日动手术；");
		w("戊为住院费，落几宫，则按旺相休囚取数；");
	}	
	//年命
	public void getBing26() {
		if(mzhu[0]==0) return;
		
		w("年命［"+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"］");
		if(p.isTGanMu(mzhu[0],mzhu[1])) w("年命天干落宫入墓，凶！也表示住院（临乙奇为医院把握更大），也表示在家休息或退休；");
		if(p.isKong(mzhu[0],mzhu[1])) w("年命落宫空亡，新病落空亡者生，久病落空亡者死，逢空也主没有疾病；");
		
		if(p.isganHeju(mzhu[0],mzhu[1])[0].equals("1")) w("年命落"+mtpgong+"宫合局，吉。");
		else w("年命落"+mtpgong+"宫失局，凶！");		
		if(p.isGongke(mtpgong, xingruigong)) w("年命落"+mtpgong+"宫克芮宫，为人克病，病情发展缓慢，吉。或近几年身体好，没有疾病；如果落宫旺相，又有吉格或吉星吉神则更佳。");
		else if(p.isChongke(xingruigong, mtpgong) && mtpgong!=0) w("天芮星冲克年命所落"+mtpgong+"宫，大凶。");
		
		w("年命落宫旺相休囚，老怕帝旺少怕衰；");
		if(mtpgong==zhifugong && p.isKong(mtpgong)) w("年命临值符逢空，年命不保；");
		if(mtpgong==kaimengong) w("年命临开门主开刀，也主病情明确，病必除掉。");
		if(mtpgong==simengong || mdpgong==simengong) w("年命在天盘或地盘逢死门，大凶；旺相更甚。");
		
		//年命干在年月支死墓绝之月份大凶；
		String smj = "";
		for(int i=1; i<13; i++) {
			if(SiZhu.TGSWSJ[mzhu[0]][i]==8 || SiZhu.TGSWSJ[mzhu[0]][i]==9 || SiZhu.TGSWSJ[mzhu[0]][i]==10)
				smj += YiJing.DIZINAME[i];
		}
		w("年命天干［"+YiJing.TIANGANNAME[mzhu[0]]+"］在｛"+smj+"｝为死墓绝之年月，大凶！");
	}
	//其它
	public void getBing27() {
		if(p.isTGanMu(SiZhu.sg,SiZhu.sz)) w("占儿女病时干入墓必死。");
		if(!boy && p.isKong(gengtpgong)) w("女测，庚为夫，空亡表示不在家；");
		if(!boy && (gengtpgong==wudpgong || gengdpgong==wutpgong)) w("女测，庚为夫，遇庚+戊或戊+庚表示换地盘，都表示其丈夫离家出走了；");
	}
}