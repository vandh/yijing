package org.boc.db.qm;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;


public class QimenJinyun extends QimenBase{
	private String[] jibing ; //保存命运宫所有的描述情况
	int index = 0; //计数器，为descMY所用

	public QimenJinyun(QimenPublic pub) {
		this.p = pub;
		this.daoqm = pub.getDaoQiMen();
		this.daosz = pub.getDaoSiZhuMain();
	}
  
	public String getNow(StringBuffer str,String mzText, int ysNum,boolean boy, int iszf) {
		init(mzText, ysNum, boy, 2000);	//2000为数组长度	
		this.iszf = iszf!=2;
		w(p.NOKG+"一、大格局断：");
		getNow1();
		w(p.NEWLINE);
		
		w(p.NOKG+"二、自身状态：");
		getNow2();
		w(p.NEWLINE);
		
		w(p.NOKG+"三、六亲：");
		getNow3();
		w(p.NEWLINE);
				
		w(p.NOKG+"四、健康：");
		getNow4();
		w(p.NEWLINE);
		
		w(p.NOKG+"五、婚姻：");
		getNow5();
		w(p.NEWLINE);
		
		w(p.NOKG+"六、工作：");
		getNow6();
		w(p.NEWLINE);
		
		w(p.NOKG+"七、财运：");
		getNow7();
		w(p.NEWLINE);
		
		return p.format(str, my);
	}
	
	/**
	 * 一、大格局断
	 */
	public void getNow1() {
		w(isMenfan,"八门反呤：八门代表人事，最怕反呤，主近期不顺利；");
		w(isMenfu,"八门伏呤：八门代表人事，伏呤主近期不顺利，人事坎坷；");
		w(isXingfan,"九星反呤：九星代表天时，反呤主近期反复，不顺利；；");
		w(isXingfu,"九星伏呤：九星代表天时，伏呤主近期不顺利，行事坎坷；");
		
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==1,"大局天显时格，近期发展平稳、工作顺利；");
		w(QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]==2,"大局五不遇时，百事皆凶，主近期不顺利；；");
	}
	public void getNow2() {
		String[] riheju = p.isganHeju(ritpgong);
		if(riheju[0].equals("1")) {
			w("日干合局，表明本人事业顺利，处于上升时期；"+riheju[1]);
		}else{
			w("日干失局，表明本人近段运气较差；"+riheju[1]);
		}
		w("地盘日干落"+ridpgong+"宫，老家或工作单位在"+QiMen.JIUGONGFXIANG[ridpgong]);
		
		if(ritpgong==gengdpgong || ridpgong==gengtpgong)
			w("日干临庚，小人多，或反对的人多，矛盾点多，反对派多。");
		if(mtpgong!=0 && (mtpgong==gengdpgong || mdpgong==gengtpgong))
			w("年命临庚，小人多，或反对的人多，矛盾点多，反对派多。");
		if(boy && (ritpgong==dingtpgong || ritpgong==dingdpgong))
			w("日干临丁，就表明除老婆之外还有小蜜，不承认也有。特别是日干下临丁的情况更准，现在就有情人。如再临杜门，玄武，说他有他也不承认。");
		if(boy && (mtpgong==dingtpgong || mtpgong==dingdpgong))
			w("年命临丁，就表明除老婆之外还有小蜜，不承认也有。也许现在还没有出现，但将来就会出现。如再临杜门，玄武，你说他有他也不承认。");
		if(!boy && (ritpgong==bingtpgong || ritpgong==bingdpgong))
			w("日干临丙，就表明除丈夫之外还有情人，不承认也有。特别是日干下临丙的情况更准，现在就有情人。如再临杜门，玄武，说他有他也不承认。");
		if(!boy && (mtpgong==bingtpgong || mtpgong==bingdpgong))
			w("年命临丙，就表明除丈夫之外还有情人，不承认也有。也许现在还没有出现，但将来就会出现。如再临杜门，玄武，你说他有他也不承认。");
		
		String bwei = !p.isNenpan(wutpgong) && 
			(wutpgong==1 || wutpgong==8 || wutpgong==3 || wutpgong==4) ? "左侧":"右侧";
		w("戊落"+wutpgong+"宫，钱在身体"+bwei+QiMen.JIUGONGBUWEI[wutpgong]+"中放着；");
		
		String bw1834 = !p.isNenpan(3) ? "左侧":"右侧" ;		 //判断1834为身体左还是右侧
		String bw9276 = !p.isNenpan(7) ? "左侧":"右侧" ;		 //判断9276为身体左还是右侧
		if(rentpgong==3 || guitpgong==3) w("壬或癸落3宫，身体"+bw1834+"腹部有痣；");
		if(rentpgong==7 || guitpgong==7) w("壬或癸落7宫，身体"+bw9276+"腹部有痣；");
		if(rentpgong==5 || guitpgong==5) w("壬或癸落中5宫，身体后背有痣；");
		if(rentpgong==8 || guitpgong==8) w("壬或癸落8宫，身体"+bw1834+"腿部有痣；");
		if(rentpgong==6 || guitpgong==6) w("壬或癸落6宫，身体"+bw9276+"腿部有痣；");
		if(rentpgong==1 || guitpgong==1) w("壬或癸落1宫，身体下阴或臀部有痣；");
		if(rentpgong==9 || guitpgong==9) w("壬或癸落9宫，身体头或眼部有痣；");
		
		if(zhifugong==ritpgong) w("上乘值符，有组织能力，善于当经理，气宇轩昂，能发挥团队作用。");
		else if(shenshegong==ritpgong) w("上乘腾蛇，事情缠绕。");
		else if(shenyingong==ritpgong) w("上乘太阴，宜策划问题，易犯小人。");
		else if(shenhegong==ritpgong) w("上乘六合，善于待人接物，善于谈判。");
		else if(shenhugong==ritpgong) w("上乘白虎，脾气直爽暴躁，容易受损或遇到凶事。");
		else if(shenwugong==ritpgong) w("上乘玄武，宜搞投机，不干正经事，偷税漏税，连蒙带骗，主暧昧，办事情不光明磊落。");
		else if(shentiangong==ritpgong) w("上乘九天，性格外向，有利于主动，格局衰则好高骛远，半途而废。");
		else if(shendigong==ritpgong) w("上乘九地，稳重，但易钻牛角尖。");
		
		if(ritpgong==xiumengong) w("遇休门吉门，吉；");
		else if(ritpgong==shengmengong) w("遇生门，吉；");
		else if(ritpgong==kaimengong) w("遇开门，吉；");
		else if(ritpgong==dumengong) w("遇杜门，中平；");
		else if(ritpgong==jing3mengong) w("遇景门，小凶；");
		else if(ritpgong==shangmengong) w("遇伤门，凶；");
		else if(ritpgong==simengong) w("遇死门，近期心里不快，来克本宫的宫可分析不快的原因；占年运遇之，还要防孝服。");
		else if(ritpgong==jing1mengong) w("遇惊门，凶；");
		
		if(ritpgong==xingzhugong) w("临天柱星，较差，有诸多不吉因素；");
		else if(ritpgong==xingruigong) w("临天芮星，善于结交朋友，善于学习，但贪财，有诸多不吉因素；");
		else if(ritpgong==xingpenggong) w("临天蓬星有时好，有时坏，如遇庚加己破大财，总之有诸多不吉因素；");
		else if(ritpgong==xingxingong) w("临天心星，为有领导才能；");
		else if(ritpgong==xingfugong) w("临天辅星，为人正直，有文化；");
		else if(ritpgong==xingyinggong) w("临天英星，中平；");
		else if(ritpgong==xingchonggong) w("临天冲星，次吉；");
		
		if(ritpgong==bingtpgong && bingtpgong==guidpgong) w("日宫临丙加癸是酒鬼；");
		if(ritpgong==dingtpgong && dingtpgong==guidpgong) w("日宫临癸加丁，有性行为；");
		if(ritpgong==guitpgong && guitpgong==jidpgong) w("日宫临癸加已是酒鬼；");
		if(ritpgong==dingtpgong && dingtpgong==jidpgong) w("日宫临丁加己是烟鬼；");
		if(!boy && ritpgong==dingtpgong && dingtpgong==1) w("日干在坎宫，临丁，来例假了。");
		if(ritpgong==zhifugong && zhifugong==yitpgong) w("日干临值符加乙，有高贵的希望；");
		if(!boy && ritpgong==zhifugong && zhifugong==wutpgong) w("日干临值符加戊，有肥臀；");
		if(!boy && ritpgong==zhifugong && zhifugong==jitpgong) w("日干临值符加已，带一个高贵的乳罩；");
		if(ritpgong==shenshegong && ritpgong==wutpgong) w("日宫临腾蛇，加戊好打扮。");
		
		if(ritpgong==dingtpgong) w("临丁主文书宣传工作；");
		else if(ritpgong==bingtpgong) {			
			if(ritpgong==xingpenggong) w("临丙和天蓬，有权威，自己说了算，但脾气暴躁。");
			else w("临丙，有权威，自己说了算。");
		}
		else if(ritpgong==gengtpgong) w("临庚，有阻力，主凶。");
		else if(ritpgong==p.getTianGong(YiJing.JI, 0)) w("临己，隐晦，有私欲，办事手段不正当。");
		else if(ritpgong==p.getTianGong(YiJing.XIN, 0)) w("临辛，设想不正确，办事情不正确。");
		else if(ritpgong==p.getTianGong(YiJing.REN, 0) ||
				ritpgong==p.getTianGong(YiJing.GUI, 0)) w("临壬、癸，难以发展，主凶。");
		if(ritpgong==p.getDiGong(YiJing.REN, 0)) w("加地盘壬，主走动、出行。");
		
		Integer[] rigeju = p.getJixiongge2(ritpgong,iszf);
		for(int rge : rigeju) {
			if(rge==16) w("日干遇悖格，做事没有顺序，办事混乱，无秩序。");			
		}
		
		String t = "";
		if(p.isJixing(ritpgong)) 	t += "日干与其落宫地支相刑，";
		if(p.isTDXing(ritpgong))	t += "日宫天盘干与地盘干相刑，";
		int[] gzi = p.getDiziOfGong(ritpgong);  //宫中所藏地支
		if(YiJing.DZXING[gzi[0]][SiZhu.rz]==1 || YiJing.DZXING[gzi[1]][SiZhu.rz]==1) t += "日宫地支与日柱地支相刑，";
		if(!t.equals("")) w(t+"日干遇到六仪击刑，难受、压力大、疲劳、损失、受伤、车祸、受批评、辞职。");
		
		if(p.isTGanMu(SiZhu.rg,SiZhu.rz)) w("日干入天盘墓，无作为，遇事束手无策，能力不能发挥或遇到棘手之事。");
		if(p.isDGanMu(SiZhu.rg,SiZhu.rz)) w("日干在地盘"+ridpgong+"宫入墓，犹豫不决，有志难伸。");
		if(p.isTianKeDi(ridpgong)) w("日干在地盘"+ridpgong+"宫受天盘干克，近期不顺");
		if(p.isKong(ritpgong)) w("日干逢空，为当前休假或空闲；");
		
		if(zhifugong==ritpgong) {
			if(ritpgong==8 || ritpgong==2 ||ritpgong==4 ||ritpgong==6) w("日干临值符，处四维，应为副职，但临乾宫有可能例外；");
			else w("日干临值符，处四正宫，应为正职；");
		}
			
		if(p.isBihe(shitpgong, ritpgong)) {
			if(riheju[0].equals("1")) w("时日比和，日宫合局，本年运势较好；");
			else w("时日比和，日宫有吉有凶，平常年份有些不顺；");
		}
		
		if(mzhu.length>1 && mzhu[0]!=0) {
			int mzgong = p.getTianGong(mzhu[0], mzhu[1]);
			int nzgong = QiMen.ziOfGua[SiZhu.nz];
			if(p.isChongke(mzgong, nzgong) || p.isChongke(nzgong,mzgong)) w("年命落"+mzgong+"宫冲克流年地支太岁"+nzgong+"宫，肯定不顺；");
		}
	}
	public void getNow3() {
		int fathergong,mothergong;  //得到父母所在的宫
		if(p.isYangGan(SiZhu.ng)) {
			fathergong = p.getTianGong(SiZhu.ng, SiZhu.nz);
			int mothergan = p.getGanHe(SiZhu.ng);			
			mothergong = mothergan==YiJing.JIA ? zhifugong : p.getTianGong(mothergan, 0);
			w("年干［"+YiJing.TIANGANNAME[SiZhu.ng]+"］为阳为父，落"+fathergong+
					"宫，相合之干［"+YiJing.TIANGANNAME[mothergan]+"］为其母，落"+mothergong+"宫；");
		}else{
			mothergong = p.getTianGong(SiZhu.ng, SiZhu.nz);
			int fathergan = p.getGanHe(SiZhu.ng);			
			fathergong = fathergan==YiJing.JIA ? zhifugong : p.getTianGong(fathergan, 0);
			w("年干［"+YiJing.TIANGANNAME[SiZhu.ng]+"］为阴为母，落"+mothergong+
					"宫，相合之干［"+YiJing.TIANGANNAME[fathergan]+"］为其父，落"+fathergong+"宫；");
		}				
		
		Integer[] ge = p.getJixiongge2(6,iszf);  //得到父宫的十干克应
		for(int g : ge) {
			if(g==67 && xingzhugong==6) w("父乾六宫为“丙+壬火入天罗”凶格，又临天柱破军，父不在了；");
		}
		ge = p.getJixiongge2(2,iszf);  //得到母宫的十干克应
		for(int g : ge) {
			if(g==67 && xingzhugong==2) w("母坤二宫为“丙+壬火入天罗”凶格，又临天柱破军，母不在了；");
		}
		
		ge = p.getJixiongge2(yuetpgong,iszf);  //得到月干落宫
		for(int g : ge) {
			if(g==80) w("月干为兄妹落"+yuetpgong+"宫，临戊+乙，有发了财的；");
		}
		if(fathergong==yuetpgong) w("月干与父干同落"+fathergong+"宫，表明父亲与兄弟姐妹住在一起；");
		if(mothergong==yuetpgong) w("月干与母干同落"+mothergong+"宫，表明母亲与兄弟姐妹住在一起；");
		
		int peiou = p.getHegan(SiZhu.rg);
		int peiougong = peiou==YiJing.JIA ? zhifugong : p.getTianGong(peiou, 0);
		w("与日干相合之天干［"+YiJing.TIANGANNAME[peiou]+"］为配偶，落"+peiougong+"宫；");
		if(p.isSheng(peiougong, zhifugong)) w("配偶宫生值符所落"+zhifugong+"宫，为领导做服务工作的；");
	}
	public void getNow4() {
		w("请参考人体疾病；");
	}
	public void getNow5() {
		w("请参考恋爱婚姻；");
	}
	public void getNow6() {
		w("请参考工作就业；");
	}
	public void getNow7() {
		w("请参考经营求财；");
	}
}