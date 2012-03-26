package org.boc.db.qm;

import org.boc.db.SiZhu;

public class QimenShidao extends QimenBase{
	private String[] jibing ; //保存命运宫所有的描述情况
	int index = 0; //计数器，为descMY所用

	public QimenShidao(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
  
	public String getThing(StringBuffer str,String mzText, int ysNum,boolean boy) {
		init(mzText, ysNum, boy, 2000);	//2000为数组长度	
		
		w(p.NOKG+"一、财物丢失断：");
		w("1. 取用神：");
		getThing1();
		w(p.NEWLINE);
		w("2. 大格局断：");
		getThing2();
		w(p.NEWLINE);
		w("3. 详断：");
		getThing3();
		w(p.NEWLINE);
		w("4. 方向与应期：");
		getThing4();
		w(p.NEWLINE);
		
		w(p.NOKG+"二、财物被盗断：");
		w("1. 取用神：");
		getThing11();
		w(p.NEWLINE);
		w("2. 大格局断：");
		getThing12();
		w(p.NEWLINE);
		w("3. 详断：");
		getThing13();
		w(p.NEWLINE);
		w("4. 方向与应期：");
		getThing14();
		w(p.NEWLINE);
		
		return p.format(str, my);
	}
	
	public void getThing1() {
		w("日干为失主，见某人失物起局为问事局以日干为用神；");
		w("时干为丢失之钱、耳环；六合为认失物之方；");
		w("丁为文件、身份证、银行卡、手机；景门为文书、发票、身份证、手机；戊为丢失之钱款、银行卡；");			
	}
	public void getThing2() {
		if(p.isMenFan() || p.isXingFan()) w("反呤速度快，也主能找回；");
		if(p.isMenFu() || p.isXingFu()) w("伏吟主在原地不动，主为旧物，主内部人，也主破财，物失不回；");
	}
	public void getThing3() {
		if(ritpgong==shitpgong ) w("日时同宫，没有丢失，可以找到。");
		else if(p.isBihe(ritpgong, shitpgong)) {
			if((ritpgong==2 || ritpgong==8) && p.isYima(ritpgong) || p.isYima(shitpgong))
				w("日时比和但落28宫对冲，且临驿马，为遗失之象，难以找回；");
			else
				w("日时比和物不失；");
		}
		else if(p.isSheng(ritpgong, shitpgong)) w("日生时，说明要求测人主动去找，物才回；");
		else if(p.isSheng(shitpgong, ritpgong)) w("时干落宫(乘旺相之气)来生日干落宫，也能找回。");
		else if(p.isGongke(shitpgong, ritpgong)) w("时干克日干，不好找。");
		else if(p.isGongke(ritpgong, shitpgong)) w("日干克时干，自己必须去找或去取才能回来；");		
		
		if(p.isTG3He(shitpgong) || p.isTG6He(shitpgong) || p.isTDGanHe(shitpgong))
			w("时干逢合格，不好找。");
		if(shitpgong==shendigong) w("时干临九地，不好找。");
		
		if(p.isKong(shitpgong) || p.isTGanSMJ(SiZhu.sg, SiZhu.sz)!=null) w("时干落空亡、或死墓绝之宫，也主难找回；");
		if(p.isTGanMu(SiZhu.sg, SiZhu.sz)) w("时干入墓为失物被深藏，或被人拾到后收藏；");
		if(p.isKong(ritpgong) || 
				(ritpgong==xindpgong || ritpgong==xintpgong)) w("日干空亡，或临辛，是自己放错了地方。");
		if(shitpgong==shengmengong) w("时干临生门，可能还在家里；");
		if(shitpgong==shendigong || shitpgong==shenyingong) w("时干临九地、太阴，不容易发现；");
		if(shitpgong==shenwugong) w("时干临玄武，则可能被人偷走或是自己忘记了；");
		if(shitpgong==shenshegong) w("时干临腾蛇是自己遗忘；");
		if(shitpgong==shenyingong || shitpgong==xingruigong || shitpgong==simengong)
			w("临太阴、天芮星、死门，找找门旁的鞋柜或有佛像之类的地点，此处有书籍、药品、包裹。");
		if(ystpgong==shenhugong) w("失物之宫临太岁或白虎，为重要物品；");		
		
		if(p.isGongke(shenwugong, shitpgong) || p.isGongke(xingpenggong, shitpgong))
			w("时干宫被玄武"+shenwugong+"宫或天蓬星"+xingpenggong+"宫所克，则可能是被盗走了。");
		else
			w("玄武宫或天蓬星宫不克时干宫，不是偷。");
		if(p.isGongke(ritpgong, shenwugong)) w("日宫克玄武，非失盗。");
		if(p.isKong(shenwugong)) w("玄武落宫空亡，非失盗；");
		//if(!p.isGongke2(shenwugong, shitpgong)) w("玄武不克时干，非偷；");
		if(p.isSheng(shitpgong, shenwugong)) w("时干生玄武，被偷之象；");
				
		if(dingtpgong==xiumengong) w("丁+休=旧手机；");
		if(xingpenggong==wutpgong) w("蓬+戊=破财；");
		if(shangmengong==zhifugong) w("伤+值符=名牌车辆；");
		
		if(shitpgong==guitpgong && guitpgong==bingdpgong) w("时干宫逢癸+丙上人见喜，可能被其长辈找到；");
		else if(shitpgong==wutpgong && wutpgong==gengdpgong) w("时干宫逢戊+庚，指被挪动了地方；");
		w("？？失物所代表之宫生日宫，或日时同宫，可以找回；克日干宫，难以找回；");
		w("？？失物所落宫、时干宫、日干宫临吉星吉神吉格，物不失之象；");
		w("？？戊落"+wutpgong+"宫，如果临奇，则取宫之大数；");
		w("？？丁落兑宫可取2、7，如临伤门可取3数，再看地盘丁如果临震3宫，则取3个已经明了；");
		w("？？失物如果为丁入丑库，丑为牛，则装在一个牛皮袋内，未日未时冲丑，说明未日未时翻动过这个物品；");
		w("？？失钱，则地盘戊为原来放钱的地方；失身份证，则地盘景门为物原来所在地方；");
		w("？？来人坐坤宫临天英，天英主文书帐本落宫泄气，坤主衣物，宫中壬+庚为变动，断失物或错帐；");				
	}
	
	public void getThing4() {
		if(p.isNenpan(ritpgong) && p.isNenpan(shitpgong)) w("日干与时干同在内盘，则钱物丢失在家中或近处；");
		else if(!p.isNenpan(ritpgong) && !p.isNenpan(shitpgong)) w("日干与时干同在外盘，则钱物丢失在外边或远处；");
		else if(p.isNenpan(ritpgong) && !p.isNenpan(shitpgong)) w("日干在内、时干在外，则丢失的钱物在外边；");
		else w("时干在内、日干在外，则丢失在家中。");
		
		w("时干落"+shitpgong+"宫，失物在"+QiMen.JIUGONGFXIANG[shitpgong]+"；");
		if(this.isMenfu || isXingfu) w("伏呤说明没有离开家在原地；");		
		
		if(dingtpgong==8 && dingtpgong==simengong)
			w("丁为身份证，临艮为高大，临死门为锁，则物被锁在柜内；");
		if(shitpgong==3 && 3==jing1mengong && jing1mengong==gengtpgong) w("时干逢震+惊+庚为铁木结构容器有门有口如铁皮保险柜；");
		if(shitpgong==3 && 3==shangmengong) w("时干逢震+伤=木柜；");
		if(shitpgong==9) w("时干逢离宫为红色容器如箱柜盒；");
		if(shangmengong==bingtpgong && bingtpgong==shenhugong) w("时干逢伤+丙+虎掉在路上了；");
		if(p.isNenpan(shitpgong) && (shitpgong==3 || shitpgong==4)) w("时干逢内盘+震或巽=家中木制家具；");
		if(p.isNenpan(shitpgong) && shitpgong==8) w("时干逢艮+内盘=高大立柜；");
		if(shitpgong==6 && 6==jing1mengong) w("时干逢乾+惊=电视机柜；");
		if(shitpgong==xingruigong) w("时干逢天芮=棉毛制品｜包｜口袋或信封；");
		if(shitpgong==2) w("时干逢坤宫为车；");
		if(p.isYima(shitpgong)) w("时干逢驿马也主车；");
		if(shitpgong==yitpgong && shitpgong==xingpenggong) w("时干逢乙+蓬=白色木柜；");
		if(shitpgong==8) w("时干逢艮=墨绿色；");
		if(shitpgong==wutpgong && shitpgong==gengtpgong) w("时干逢戊+庚=破财；");
		else if(ritpgong==wutpgong && ritpgong==gengtpgong) w("日干逢戊+庚=破财；");
				
		int men = daoqm.gInt[3][1][shitpgong];
		w("时干落"+shitpgong+"宫为"+QiMen.JIUGONGPEOPLE[shitpgong]+"，"+QiMen.bm1[men]+"门为人盘主"+men+"数，拾主是"+men+"个"+QiMen.JIUGONGPEOPLE[shitpgong]+"；");
		
		w("！六合落宫为失物方位，宫数为几公里或几里；");
		w("！失物所代表之用神宫在外盘为外面，临生门在家中；");
		w("！丢失钱物找到时间，一般以时干生日干之日时为应期；");
		w("！或按庚格而断，阴日干寻庚上，阳日干寻庚下；");
		w("！入库者，以冲出之日时为应期。");
		w("！旬空者以填实之日时为应期。");
		w("！值使落宫也可断天数；");
		w("！时干落宫也可断天数；");
	}
	public void getThing11() {
		w("玄武为小贼；");
		w("很贵重的东西或作案方式猖獗，以天蓬为大盗。");
		w("以勾陈为捕盗之人；");
		w("杜门为捕盗方位，逃跑方向；");
		w("伤门为车、公安；庚也为公安；白虎也为公安；值使门也为公安；");
	}
	public void getThing12() {
		
	}
	public void getThing13() {
		int wx = daoqm.gInt[2][1][shenwugong];  //玄武宫所临之星		
		if(wx==1 || wx==2 || wx==3 || wx==4 || wx==5) w("玄武落"+shenwugong+"宫，上乘阳星为男贼；");
		else w("玄武落"+shenwugong+"宫，上乘阴星为女贼。");
		if(shenwugong==yitpgong) w("玄武临乙，为女人；");
		if(p.getGongWS(shenwugong)[0].equals("1")) w("玄武所临宫旺相，青壮年贼；");
		else w("玄武休囚为老年贼。");
		w("玄武所临天盘六仪为上衣颜色，地盘六仪为裙裤颜色。");
		
		String[] wuhj = p.isshenHeju(shenwugong);
		if(!wuhj[0].equals("1")) w("玄武落"+shenwugong+"宫失局，破案之象；");
		if(gengtpgong==shenwugong) w("庚与玄武同宫，抓到小偷破案之象；");
		if(zhishigong==shenwugong || p.isGongke(shenwugong, zhishigong)
				|| p.isSheng(zhishigong, shenwugong)) w("直使临玄武，或玄武克直使，或直使生玄武，都是被盗。");
		if(p.isGongke(shenhugong, shenwugong) || p.isGongke(shenhugong, xingpenggong))
			w("勾陈所落"+shenhugong+"宫，克天蓬"+xingpenggong+"宫、玄武所落"+shenwugong+"宫，贼可抓住；");
		else if(p.isBihe(shenhugong, shenwugong) || p.isBihe(shenhugong, xingpenggong)) 
			w("勾陈所落"+shenhugong+"宫与天蓬"+xingpenggong+"宫、玄武所落"+shenwugong+"宫比和，有可能捕盗者与盗贼相勾结。");
		else if(shenhugong == shenwugong || shenhugong == xingpenggong) 
			w("勾陈所落"+shenhugong+"宫与天蓬"+xingpenggong+"宫、玄武所落"+shenwugong+"宫同宫，作案者就是破案者本身。");
		else w("勾陈所落"+shenhugong+"宫，不克天蓬"+xingpenggong+"宫、玄武所落"+shenwugong+"宫，贼的势力太大，捕盗者不敢下手。");
		
		if(p.isTGanMu(SiZhu.sg, SiZhu.sz)) w("时干入墓，主物被藏起来了；");
		if(niantpgong==gengdpgong || niandpgong==gengtpgong) w("逢年格，主找到之象；");
		if(yuetpgong==gengdpgong || yuedpgong==gengtpgong) w("逢月格，主找到之象；也主朋友、兄弟找到；");
		if(ritpgong==gengdpgong || ridpgong==gengtpgong) w("逢日格，主找到之象；");
		if(shitpgong==gengdpgong || shidpgong==gengtpgong) w("逢时格，主找到之象；");
		
		if(p.getSanji(shitpgong)[0].equals("1") && shitpgong==shangmengong) w("时干落"+shangmengong+"宫，临伤门带奇，为大件物品，为车；");
		if(p.isGongke(shenhugong, jing3mengong)) w("白虎"+shenhugong+"宫克景门"+jing3mengong+"宫，在学校或道路上找到；");
		if(jing3mengong==1) w("景+坎=学校比较破旧；");
		if(!p.isNenpan(shenwugong)) w("玄武+外盘=小偷是外地人；");
		if(shenwugong==xingyinggong) w("玄武+天英星，小偷长相红黑；");
		if(p.getSanji(shenwugong)[0].equals("1")) w("玄武宫临三奇，小偷为偶然作案；");
		if(p.isJixing(shangmengong)) w("伤门落"+shangmengong+"宫，临击刑，为车损坏；");
		if(xingzhugong==7) w("天柱星在兑宫，必有口舌是非；"); 		
	}
	public void getThing14() {
		if(gengtpgong==niandpgong) w("天盘庚落"+gengtpgong+"宫，所临地盘为年干，构成岁格，年内可以破案，如果合杜门，破案更有把握。");
		else if(gengtpgong==yuedpgong) w("天盘庚落"+gengtpgong+"宫，所临地盘为月干，构成月格，本月可以破案，如果合杜门，破案更有把握。");
		else if(gengtpgong==ridpgong) w("天盘庚落"+gengtpgong+"宫，所临地盘为日干，构成日格，本日可以破案，如果合杜门，破案更有把握。");
		else if(gengtpgong==shidpgong) w("天盘庚落"+gengtpgong+"宫，所临地盘为时干，构成时格，即时便可破案，如果合杜门，破案更有把握。");
		else w("天盘庚落"+gengtpgong+"宫，不构成这年、月、日、时四格，难于破案。");
		w("值使、时干落宫均可为应期；");
	}
}