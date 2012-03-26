package org.boc.db.qm;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;

/**
 * 1. 取用神
 * 2. 用神、乙、庚、丙、丁的性格长相身材职业判断
 * 3. 大格局断，为PRIVATE + PUBLIC
 * 4. 恋爱篇，为PRIVATE+LOVE 和 PUBLIC+LOVE
 * 5. 婚姻篇，为PRIVATE+MARRY 和 PUBLIC+MARRY
 * 6. 年命参考，为3、4、5
 * @author Administrator
 *
 */
public class QimenHunyin {
	/**
	 * PRIVATE: 基本论断，即只输出与用神相关的恋爱或婚姻描述，如用神克六合，用神临凶星等；
	 * PUBLIC：输出与用神无关的恋爱或婚姻描述，如年干与六合、乙与庚、丙与丁等；
	 * LOVE: 恋爱论断
	 * MARRY: 婚姻论断
	 * 实际输出是上述的组合：传LOVE，则输出LOVE的BASIC与PUBLIC；传LOVE+BASIC只输出指定的
	 */
	private static final int PRIVATE = 1,PUBLIC=2, LOVE=3, MARRY=4;  
	private DaoQiMen daoqm;
	private QimenPublic my; 
	private String mingzhu ;  // 年命，如1997|2001或1,1|3,5
	private int yongshen;  //用神，从界面传来的，如年、月、日、时干
	private boolean boy;   //是男还是女
	private String[] hunyin ; //保存命运宫所有的描述情况
	int index = 0; //计数器，为hunyin所用

  public QimenHunyin(DaoQiMen daoqm, QimenPublic my) {
  	this.daoqm = daoqm;
  	this.my = my;
  }
  
	public String getHunyin(StringBuffer str,String mingzhu, int yongshen,boolean boy) {
		hunyin = new String[2000];
		index = 0;
		this.mingzhu = mingzhu;
		this.yongshen = yongshen;
		this.boy = boy;		
		
		hunyin[index++]=my.NOKG+"一、取用神：";
		getHunyin1();
		hunyin[index++]=my.NEWLINE;
		
		hunyin[index++]=my.NOKG+"二、性格、身材、长相、职业：";
		getHunyin2(yongshen, 0, 0);
		hunyin[index++]=my.NEWLINE;	
		
		String[] ysinfo = my.getYShenInfo(yongshen, 0, 0);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		hunyin[index++]=my.NOKG+"三、大格局断["+yshen+"]：";
		getHunyin3(yongshen, 0, 0,PUBLIC);
		getHunyin3(yongshen, 0, 0,PRIVATE);
		hunyin[index++]=my.NEWLINE;
		
		hunyin[index++]=my.NOKG+"四、恋爱篇["+yshen+"]：";
		getLoveAndMarry(PRIVATE, LOVE,yongshen,0,0);  //用神有关的
		getLoveAndMarry(PUBLIC, LOVE,yongshen,0,0);   //公共的我包括用神的
		hunyin[index++]=my.NEWLINE;
		
		hunyin[index++]=my.NOKG+"五、婚姻篇["+yshen+"]：";
		getLoveAndMarry(PRIVATE, MARRY,yongshen,0,0);
		getLoveAndMarry(PUBLIC, MARRY,yongshen,0,0);
		hunyin[index++]=my.NEWLINE;
		
		//年命参考，只看用神的年命，对象的年命不用判断，且只需要与用神相关的信息即PRIVATE部分
		int[] mzhu = my.getMZhu(mingzhu);
		if(mzhu.length>1 && mzhu[0] * mzhu[1]!=0) {
			hunyin[index++]=my.NOKG+"六、年命参考["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]";
			
			hunyin[index++]="------------------------------大格局篇------------------------------";
			getHunyin3(0, mzhu[0], mzhu[1],PRIVATE);
			hunyin[index++]=my.NEWLINE;
			
			hunyin[index++]="------------------------------恋爱篇------------------------------";
			getLoveAndMarry(PRIVATE, LOVE, 0, mzhu[0],mzhu[1]);
			hunyin[index++]=my.NEWLINE;
			
			hunyin[index++]="------------------------------婚姻篇------------------------------";
			getLoveAndMarry(PRIVATE, MARRY, 0, mzhu[0],mzhu[1]);
			hunyin[index++]=my.NEWLINE;
		}
		
		return my.format(str, hunyin);
	}
	
	/**
	 * 一、取用神：
	 * 为节约篇幅，此处可不输出
	 */
	private void getHunyin1() {		
		hunyin[index++]="自己求测，首选日干为用神，时干为对象或配偶；父母测子女，以时干为用神；";
		hunyin[index++]="用神天盘干主现在，地盘干主过去；";
		hunyin[index++]="以天盘的乙奇、女方年命、男方相合之干代表女方；";
		hunyin[index++]="以天盘庚金、男方年命、女方相合之干代表男方；";
		hunyin[index++]="以六合为婚姻、媒人；";
		hunyin[index++]="以丁奇为情妇、离婚证书、现任女友，乙为前妻；";
		hunyin[index++]="以丙奇为情夫、现任男友，庚为前夫；";
		hunyin[index++]="时干也主事体；";
		hunyin[index++]="求测人所临方位也可为用神；";
	}
	/**
	 * 二、性格、身材、长相、职业：
	 * 用神、乙、庚、丙、丁、年命
	 * @param 
	 */
	private void getHunyin2(int ystype, int gan, int zi) {		
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		int ysgan = Integer.valueOf(ysinfo[2]);       //用神的天干
		int yszi = Integer.valueOf(ysinfo[3]);        //用神的地支
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		//为节约篇幅，可只看用神、乙与庚；以下代码是将ysgan排在最前面
		int[] ganzi = {ysgan, ysgan==YiJing.YI?0:YiJing.YI, 
				ysgan==YiJing.GENG?0:YiJing.GENG, ysgan==YiJing.DING?0:YiJing.DING, ysgan==YiJing.BING?0:YiJing.BING};; 
		
		for(int j = 0; j<ganzi.length; j++){
			if(ganzi[j]==0) continue; 
			if(ganzi[j]==ysgan)
				hunyin[index++]="----------------------------"+yshen+"["+YiJing.TIANGANNAME[ganzi[j]]+"]--------------------------";
			else
				hunyin[index++]="----------------------------["+YiJing.TIANGANNAME[ganzi[j]]+"]--------------------------";
			this.getXingeOfShen(ganzi[j],0);
			this.getXingeOfMen(ganzi[j],0);
			this.getZhangxiang(ganzi[j],0);
			this.getZhiye(ganzi[j],0);
		}		
	}
	/**
	 * 三、大格局断：
	 * @param ystype : 用神类型，0用指定的gan与zi为用神，1为年柱、2为月柱、3为日柱、4为时柱；
	 * @param gan,zi: 为指定干与支的用神，不用缺省的年、月、日、时柱，用于取年命为用神的情况；
	 * @param all： 输出是PUBLIC还是PRIVATE
	 */
	private void getHunyin3(int ystype, int gan ,int zi, int all) {		
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		String yshen = ysinfo[0];               //用神的名称，如日干为用，年干为用等
		int gong = Integer.valueOf(ysinfo[1]);  //用神落宫
		int ysgan = Integer.valueOf(ysinfo[2]);       //用神的天干
		int yszi = Integer.valueOf(ysinfo[3]);        //用神的地支
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		boolean iskong = my.isKong(gong, my.SHIKONGWANG);
		int[] tpjy = my.getTpjy(gong);
		int[] dpjy = my.getDpjy(gong);
		int nianganGong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //年柱天干落宫
		int nianziGong = my.getDiziGong(SiZhu.nz);  //年柱地支所对应的落宫
		int gengGong = my.getTianGong(YiJing.GENG, 0); //庚落宫
		int yiGong = my.getTianGong(YiJing.YI, 0); //乙落宫
		
		if(all==PUBLIC) {			 
			if(my.isMenFu()) hunyin[index++]="八门伏呤，测婚找对象谈不成，离婚则离不了；";
			if(my.isMenFan()) hunyin[index++]="八门反呤，谈对象反复不顺，离婚也是一样；";
			if(my.isXingFu()) hunyin[index++]="九星伏呤，测婚找对象谈不成，离婚则离不了；";
			if(my.isXingFan()) hunyin[index++]="九星反呤，谈对象反复不顺，离婚也是一样；";
			
			//只有日干为用神，时干才主事
			if(ystype==my.YSHENDAY) {
				int shigong = my.getTianGong(SiZhu.sg, SiZhu.sz);
				boolean shikong = my.isKong(shigong, my.SHIKONGWANG); //得到时柱所在的宫是否旬空
				int shimen = daoqm.gInt[3][1][shigong]; //得到时柱所在宫的门 
				int shishen = daoqm.gInt[1][1][shigong]; //得到时柱所在宫的神
				if(shikong) hunyin[index++]="时干逢空亡，也为好事落空，事不成之象；";
				if(shimen==QiMen.MENKAI) hunyin[index++]="时干临开门，表明事情已经公开；";
				if(my.isTaohua(ysgan, 0)) hunyin[index++]="时干临桃花，表明所测之事与婚姻有关；";
				else if(shishen==QiMen.SHENHE) hunyin[index++]="时干临六合，表明所测之事与婚姻有关；";
				else if(shishen==QiMen.SHENHE && my.isTaohua(ysgan, 0)) hunyin[index++]="时干临六合又坐桃花，表明所测之事与婚姻有关；";
			}
			
			int heGong= my.getShenGong(QiMen.SHENHE);
			int[] hetpjy=my.getTpjy(heGong);
			if(hetpjy[0]==YiJing.GENG || hetpjy[1]==YiJing.GENG) {
				hunyin[index++]="六合落"+heGong+"宫，逢庚为阻隔，奇门中庚是最凶的符号，恋爱与婚姻，逢庚必然中途夭折！";
			}
			
			int[] mzhu = my.getMZhu(mingzhu);  //年命干支，一般为二人的，默认0,1为自己的，2，3为对象的
			if(mzhu.length==4 && mzhu[0]*mzhu[1]*mzhu[2]*mzhu[3]!=0) {
				String boyname = null;
				String girlname = null;
				if(boy) {
					boyname = "男方年命["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
					girlname = "女方年命["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
				}else{
					boyname = "女方年命["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
					girlname = "男方年命["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
				}
				String ysname = boy ? boyname:girlname;  //用神年命
				String dxname = boy ? girlname:boyname;   //对象年命
				
				int selfmzGong = my.getTianGong(mzhu[0], mzhu[1]);
				int whomzGong = my.getTianGong(mzhu[2], mzhu[3]);
	
					String[] ysnmWS = my.gettgWS(mzhu[0], mzhu[1]);
					String[] dxnmWS = my.gettgWS(mzhu[2], mzhu[3]);
					if(!ysnmWS[0].equals("1") && selfmzGong==my.getXingGong(QiMen.XINGRUI))
						hunyin[index++] =ysname+"落宫休囚，又逢天芮病星，说明身体素质差，经常生病；";
					if(!dxnmWS[0].equals("1") && whomzGong==my.getXingGong(QiMen.XINGRUI))
						hunyin[index++] =dxname+"落宫休囚，又逢天芮病星，说明身体素质差，经常生病；";
			}
			
			if(boy && my.isChongke(nianganGong, yiGong))
				hunyin[index++] ="年干落"+nianganGong+"宫克[乙]所在"+yiGong+"宫，为单位或领导对其不利，目前状况不佳，遇凶格更凶；或父母不赞成这桩婚事；";
			else if(!boy && my.isChongke(nianganGong, gengGong))
				hunyin[index++] ="年干落"+nianganGong+"宫克[庚]所在"+gengGong+"宫，为单位或领导对其不利，目前状况不佳，遇凶格更凶；或父母不赞成这桩婚事；";
			if(my.isChongke(nianganGong, heGong))
				hunyin[index++] ="年干落"+nianganGong+"宫克六合所在"+heGong+"宫，也主父母不赞成这桩婚事；谁在月令旺相，谁就拗得过，不会屈服；";
		}
		//////////////////////////////////////////////////////////////////////
		///   以下与用神相关
		//////////////////////////////////////////////////////////////////////
		if(all==PRIVATE) {
			int menGong = daoqm.gInt[3][1][gong];  //此为宫中的序数，即为该门在地盘的第几宫
			int[] angan = my.getDpjy(menGong);
			if(boy && (angan[0]==YiJing.YI || angan[0]==YiJing.YI)) hunyin[index++]="用神宫中临暗干[乙]，表示正为情所困；";
			if(boy && (angan[0]==YiJing.DING || angan[0]==YiJing.DING)) hunyin[index++]="用神宫中临暗干[丁]，表示正为情所困；";
			if(!boy && (angan[0]==YiJing.GENG || angan[0]==YiJing.GENG)) hunyin[index++]="用神宫中临暗干[庚]，表示正为情所困；";
			if(!boy && (angan[0]==YiJing.BING || angan[0]==YiJing.BING)) hunyin[index++]="用神宫中临暗干[丙]，表示正为情所困；";
			
			String isTsmj = my.isTGanSMJ(ysgan, 0);  //判断用神天盘干是否入死墓绝
			boolean isDsmj = my.isDGanMu(ysgan, 0);  //判断用神地盘干是否入墓
			if(isTsmj!=null) hunyin[index++]="用神天盘干"+YiJing.TIANGANNAME[ysgan]+"落"+gong+"宫处"+isTsmj+"地，主近期遇到了麻烦；";
			else if(isDsmj) hunyin[index++]="用神地盘干"+YiJing.TIANGANNAME[ysgan]+"落"+my.getDiGong(ysgan, 0)+"宫处墓地，主近期遇到了麻烦；";		
			
			if(iskong) hunyin[index++]="用神旬空，主好事落空、事不成之象；也表示心里麻烦拿不定主意或不想谈恋爱；";
			if(boy && iskong && (tpjy[0]==YiJing.YI || tpjy[1]==YiJing.YI || dpjy[0]==YiJing.YI || dpjy[1]==YiJing.YI))
				hunyin[index++]="用神与[乙]同宫，但落空亡，未成家之象；";
			if(!boy && iskong && (tpjy[0]==YiJing.GENG || tpjy[1]==YiJing.GENG || dpjy[0]==YiJing.GENG || dpjy[1]==YiJing.GENG))
				hunyin[index++]="用神与[庚]同宫，但落空亡，未成家之象；";		
			
			if(my.isChongke(nianganGong, gong))
				hunyin[index++] ="年干落"+nianganGong+"宫克用神所在"+gong+"宫，为单位或领导对其不利，目前状况不佳，遇凶格更凶；";
			
			String[] jxge = my.getJixiongge(gong);
			if(my.isYima(gong)) {
				if(jxge[0].equals("1")) hunyin[index++]="用神临马星，主已离婚，吉格表示自己先提出的离婚；"+jxge[1];
				else hunyin[index++]="用神临马星，宫中又逢凶格，为诉讼，主已离婚；"+jxge[1];
			}
			
			if(daoqm.gInt[3][1][gong]==QiMen.MENDU) {
				String t="用神临杜门，主不愿意谈对象；也表示要保密；";			
				if(my.isKong(gong)) t += "逢空亡之宫，表示为半保密；";
				hunyin[index++] = t; 
			}
			if(daoqm.gInt[1][1][gong]==QiMen.SHENWU) hunyin[index++]="用神临玄武，表示有外遇；";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENSHE) hunyin[index++]="用神临腾蛇，表示事务缠身；";			
			if(my.isTaohua(ysgan, 0)) hunyin[index++]="用神临桃花，在"+(my.isNenpan(gong)?"内盘为墙内桃花":"外盘为墙外桃花")+"，应属风流之人";
			if((gong==2 || gong==6) && iskong && daoqm.gInt[3][1][gong]==QiMen.MENSI) {
				hunyin[index++]="用神落"+gong+"宫临死门，又逢空亡，空亡也主过去未成之事，表示过去因父母反对而没有成婚；";
			}else if((gong==2 || gong==6) && daoqm.gInt[3][1][gong]==QiMen.MENSI) {
				hunyin[index++]="用神落"+gong+"宫临死门，表示父母反对婚事；";
			}
			
			if(daoqm.gInt[1][1][gong]==QiMen.SHENHE) {
				hunyin[index++] = "用神上临六合，也主有二次婚姻；";
			}
		}		
	}
	/**
	 * 恋爱与婚姻共断
	 * @param all: 是PUBLIC还是PRIVATE
	 * @param love: LOVE只适合恋爱阶段，MARRY则适合婚姻阶段
	 * @param ystype
	 * @param gan
	 * @param zi
	 */
	private void getLoveAndMarry(int all, int love, int ystype,int gan, int zi) {	
		int[] mzhu = my.getMZhu(mingzhu);  //年命干支，一般为二人的，默认0,1为自己的，2，3为对象的
		String[] ysinfo = my.getYShenInfo(ystype, gan, zi);		
		int gong = Integer.valueOf(ysinfo[1]);  			//用神落宫
		int ysgan = Integer.valueOf(ysinfo[2]);       //用神的天干
		int yszi = Integer.valueOf(ysinfo[3]);        //用神的地支
		if(ysgan==YiJing.JIA) ysgan = daoqm.getXunShu(ysgan, yszi)+4;
		boolean iskong = my.isKong(gong);  //用神落宫是否空亡
		int shiGong = my.getTianGong(SiZhu.sg, SiZhu.sz);  //时柱落宫
		int heGong = my.getShenGong(QiMen.SHENHE); //六合落宫
		int nianganGong = my.getTianGong(SiZhu.ng, SiZhu.nz);  //年柱天干落宫
		int nianziGong = my.getDiziGong(SiZhu.nz);  //年柱地支所对应的落宫
		int gengGong = my.getTianGong(YiJing.GENG, 0); //庚落宫
		int yiGong = my.getTianGong(YiJing.YI, 0); //乙落宫
		int bingGong = my.getTianGong(YiJing.BING, 0); //丙落宫
		int dingGong = my.getTianGong(YiJing.DING, 0); //丁落宫
		int[] ystpjy = my.getTpjy(gong);  //得到用神的天盘奇仪
		int[] ysdpjy = my.getDpjy(gong);

		/**  以下专论用神有关的恋爱或婚姻 */
		if(all==PRIVATE) {
			//用神合局或失局
			String[] isheju = my.isganHeju(ysgan, 0); 
			if(!isheju[0].equals("1")) 
				hunyin[index++]="用神落宫失局，主"+(love==LOVE?"好事难成；":"婚姻不顺；")+isheju[1];
			else if(isheju[0].equals("1")) 
				hunyin[index++]="用神落宫合局，主"+(love==LOVE?"婚事易成；":"婚姻和顺；")+isheju[1];
			
			//用神生克六合宫、用神克太岁宫
			String t=null;
			if(my.isSheng(gong, heGong) || my.isSheng(heGong, gong)) {
				if(love==LOVE) t= "用神落"+gong+"宫与六合所落"+heGong+"宫相生，主婚事易成；";
				else if(love==MARRY) t= "用神落"+gong+"宫与六合所落"+heGong+"宫相生，没有离婚的想法，离不了婚；";
				if(iskong) t += "但逢空亡，主有心无力；";
				else if(my.isTpJixing(ysgan, yszi)) t+= "但逢六仪击刑，主有心无力；";
				else if(iskong && my.isTpJixing(ysgan, yszi)) t+= "但逢旬空，又六仪击刑，主有心无力；";
				if(t!=null) hunyin[index++] = t; 
			}else if(love==LOVE && my.isChongke(gong, heGong)) {
				hunyin[index++] ="用神落"+gong+"宫冲克六合"+heGong+"宫，主不想谈恋爱；";
			}		
			
			if(my.isChongke(gong, nianganGong)) {
				if(love==LOVE) hunyin[index++] ="用神落"+gong+"宫冲克太岁天干所在"+nianganGong+"宫，也主一年对象谈不成；";
				if(love==MARRY) hunyin[index++] ="用神落"+gong+"宫冲克太岁天干所在"+nianganGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
			}else if(my.isChongke(gong, nianziGong)) {
				if(love==LOVE) hunyin[index++] ="用神落"+gong+"宫冲克太岁地支所在"+nianziGong+"宫，也主一年对象谈不成；";
				if(love==MARRY) hunyin[index++] ="用神落"+gong+"宫冲克太岁地支所在"+nianziGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
			}
			
			if(love==MARRY) {
				if(daoqm.gInt[3][1][gong]==QiMen.MENJING1 && !my.getshenJX(gong)[0].equals("1") && 
						(ysdpjy[0]==YiJing.WUG || ysdpjy[1]==YiJing.WUG))
					hunyin[index++]="用神临惊门，主常发生口角；又上临凶神，下临戊，说明正在为钱财闹官司；";
				else if(daoqm.gInt[3][1][gong]==QiMen.MENJING1)
					hunyin[index++]="用神临惊门，主常发生口角；";
				
				
				if(my.isYima(gong)) hunyin[index++] = "测离婚，用神临马星，表示自己去意已决，先提出离婚；";
				if(daoqm.gInt[1][1][gong]==QiMen.SHENHU) hunyin[index++] = "测离婚，用神临白虎也表示脾气暴躁、离婚决心大；";
				
				//看用神落宫是否有：56乙加辛、110辛加乙
				t = null;
				for(int thegeju : my.getShiganKeying(gong)){
					if(thegeju==110) t = "用神落"+gong+"宫逢辛+乙，主已离婚，男方主动；离婚时间参考宫中地支和天地盘奇仪断年份；";
					if(thegeju==56)  t = "用神落"+gong+"宫逢乙+辛，说明妻子已离家出走；";
				}
				//用神与所合之干宫的关系
				t=null;
				int hegan = my.getGanHe(ysgan);
				int ganheGong = 0;
				if(hegan==YiJing.JIA) 
					ganheGong=daoqm.getZhifuGong();
				else
					ganheGong = my.getTianGong(hegan, 0);
				if(my.isSheng(gong, ganheGong) || my.isSheng(ganheGong, gong)) {
					t = "用神落"+gong+"宫与所合之干["+YiJing.TIANGANNAME[hegan]+"]所落"+ganheGong+"宫相生，证明双方感情很好，离婚也主离不了；";
					if(my.isYima(ganheGong))
						t+="但"+ganheGong+"宫逢马星，说明其所心仪之人走了；";
					if(my.isKong(ganheGong))
						t += "但"+ganheGong+"宫逢空亡，也主相恋的人走了；";
					int[] tpjy = my.getTpjy(ganheGong);
					int[] dpjy = my.getDpjy(ganheGong);
					if(daoqm.gInt[1][1][ganheGong]==QiMen.SHENWU && 
							(tpjy[0]==YiJing.XIN || tpjy[1]==YiJing.XIN || dpjy[0]==YiJing.XIN || dpjy[1]==YiJing.XIN))
						t+="落宫临辛，又上乘玄武，进一步说明是因暧昧之事而犯下大错；";
				}else if(my.isChongke(gong, ganheGong) || my.isChongke(ganheGong, gong))
					t = "用神落"+gong+"宫与所合之干["+YiJing.TIANGANNAME[hegan]+"]所落"+ganheGong+"宫相冲克，证明双方关系不和，婚姻关系不稳定；";
				if(t!=null) hunyin[index++] = t;
				
				//用神下临乙丁（庚丙）
				t=null;
				int dingdpGong = my.getDiGong(YiJing.DING, 0);  //丁所在地盘宫
				int bingdpGong = my.getDiGong(YiJing.BING, 0);
				int gengdpGong = my.getDiGong(YiJing.GENG, 0);
				int yidpGong = my.getDiGong(YiJing.YI, 0);  //得到地盘乙落宫
				int _gan1=0, gong1 = 0;
				if(gong==yidpGong) {_gan1=YiJing.YI; gong1=yiGong; }
				else if(gong==gengdpGong) {_gan1=YiJing.GENG; gong1=gengGong;}
				else if(gong==bingdpGong) {_gan1=YiJing.BING; gong1=bingGong;}
				else if(gong==dingdpGong) {_gan1=YiJing.DING; gong1=dingGong;}
				if(gong1!=0) {
					t="用神下临["+YiJing.TIANGANNAME[_gan1]+"]，主交了新朋友，感情深可能未婚同居；";
					if(ystpjy[0]==YiJing.XIN || ystpjy[1]==YiJing.XIN || ysdpjy[0]==YiJing.XIN || ysdpjy[1]==YiJing.XIN)
						t += "又与辛同宫，更主犯错误违反婚姻法同居；";
					if(daoqm.gInt[2][1][gong]==QiMen.XINGRUI)
						t += "又与天芮同宫，为病星也主第三者插足；";
					if(gong1==7)
						t+=YiJing.TIANGANNAME[_gan1]+"落7宫，表明找了一个年龄较小对象；";
					if(my.isBihe(gong1, gong))
						t+="二者又落宫比和，事情铁板钉钉；";
					if(my.isSheng(gong, gong1) || my.isSheng(gong1, gong))
						t+="二者又落宫相生，事情铁板钉钉；";
					if(t!=null)
						t+="相好几年则从"+YiJing.TIANGANNAME[_gan1]+"所落"+gong1+"宫之数断，旺相时间长，临九地也主时间长；";
				}
				if(t!=null) hunyin[index++]=t;
			}
		}
		
		//年命落宫；同宫、地支合、相生、相比、相冲克；六合克对象年命；年命克太岁宫；		
		if(mzhu.length==4 && mzhu[0]*mzhu[1]*mzhu[2]*mzhu[3]!=0 &&(gan!=0 && zi!=0)) { //gan,zi!=0表示要算年命了
			String boyname = null;
			String girlname = null;
			if(boy) {
				boyname = "男方年命["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
				girlname = "女方年命["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
			}else{
				boyname = "女方年命["+YiJing.TIANGANNAME[mzhu[0]]+YiJing.DIZINAME[mzhu[1]]+"]"; 
				girlname = "男方年命["+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"]";
			}
			String ysname = boy ? boyname:girlname;  //用神年命
			String dxname = boy ? girlname:boyname;   //对象年命
			
			int selfmzGong = my.getTianGong(mzhu[0], mzhu[1]);
			int whomzGong = my.getTianGong(mzhu[2], mzhu[3]);
			if(selfmzGong==whomzGong ) {				
				String t = null;
				if(love==LOVE) t = boyname+"、"+girlname+"同落"+selfmzGong+"宫，婚事必成之象；";
				else if(love==MARRY) t =  boyname+"、"+girlname+"同落"+selfmzGong+"宫，婚姻稳固之象；";
				if(!my.getJixiongge(selfmzGong)[0].equals("1")) t += "可惜临凶格，又主好事多磨，不吉之象；";
				int[] dpjy = my.getDpjy(selfmzGong);
				if(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN) t+= "临壬，又主流散不成之象；";
				if(t!=null) hunyin[index++] = t;
			}
			
			if(my.isGongLiuhe(selfmzGong, whomzGong) && love==LOVE)
				hunyin[index++]= boyname+"、"+girlname+"落宫逢地支六合，婚事必成之象；临吉门或吉神或吉星或吉格，把握更大；相克，凶格则不成；";	
			
			if(my.isSheng(selfmzGong, whomzGong) || my.isSheng(whomzGong, selfmzGong)) {
				if(love==LOVE) hunyin[index++]= boyname+"、"+girlname+"落宫相生，也主婚事能成，临吉门或吉神或吉星或吉格，可能性更大；";
			}	else if(my.isBihe(selfmzGong, whomzGong)) { 
				if(love==LOVE) hunyin[index++]= boyname+"、"+girlname+"落宫相比和，也主婚事能成，临吉门或吉神或吉星或吉格，可能性更大；";
			}	else if(my.isChongke(selfmzGong, whomzGong) || my.isChongke(whomzGong, selfmzGong)) { 
				if(love==LOVE) hunyin[index++]= boyname+"、"+girlname+"落宫相冲克，又有婚事不成之象；";			
			}
			
			if(love==LOVE && my.isChongke(heGong, whomzGong)) {
				hunyin[index++] ="六合落"+heGong+"宫冲克对象年命"+YiJing.TIANGANNAME[mzhu[2]]+YiJing.DIZINAME[mzhu[3]]+"所落"+whomzGong+"宫，又主婚事难成；";
			}
			
			if(my.isChongke(selfmzGong, nianganGong)) {
				if(love==LOVE) hunyin[index++] =ysname+"落"+selfmzGong+"宫冲克太岁天干所在"+nianganGong+"宫，也主一年对象谈不成；";
				if(love==MARRY) hunyin[index++] =ysname+"落"+selfmzGong+"宫冲克太岁天干所在"+nianganGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
			}else if(my.isChongke(selfmzGong, nianziGong)) {
				if(love==LOVE) hunyin[index++] =ysname+"落"+selfmzGong+"宫冲克太岁地支所在"+nianziGong+"宫，也主一年对象谈不成；";
				if(love==MARRY) hunyin[index++] =ysname+"落"+selfmzGong+"宫冲克太岁地支所在"+nianziGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
			}
		}
		
	//////////////以下全是公共部分PUBLIC,即与用神无关的///////////////////////////////
	//////
	///////////////////////////////////////////////////////////////////
		if(all!=PUBLIC) return;
	//六合落宫是否合局、时干落宫是否合局
		String[] isShenheju = my.isshenHeju(heGong);  
		if(!isShenheju[0].equals("1")) {
			if(love==LOVE) 
				hunyin[index++]="六合落"+heGong+"宫失局，也主婚事难成；"+isShenheju[1];
			else if(love==MARRY)
				hunyin[index++]="六合落"+heGong+"宫失局，也主二人没有感情，婚姻基础不好，宫中地支为"+my.getDznameOfGong(heGong)+"，防止"+my.getDzyueOfGong(heGong)+"婚灾；"+isShenheju[1];
		}else{
			if(love==LOVE) 
				hunyin[index++]="六合落"+heGong+"宫合局，也主婚事易成；"+isShenheju[1];
			else if(love==MARRY)
				hunyin[index++]="六合落"+heGong+"宫合局，也主家庭和睦；"+isShenheju[1];
		}	
			
		String[] isshiHeju = my.isganHeju(SiZhu.sg, SiZhu.sz);
		if(!isshiHeju[0].equals("1"))
			hunyin[index++]="时干落"+shiGong+"宫失局，"+(love==LOVE?"也主婚事难成；":"又主婚姻不顺；")+isshiHeju[1];
		else 
			hunyin[index++]="时干落"+shiGong+"宫合局，"+(love==LOVE?"也主婚事易成；":"又主家庭和睦；")+isshiHeju[1];
		
		//如果用神为日干，则判断时日宫相生、相比和
		if(ystype==my.YSHENDAY && (my.isSheng(gong, shiGong) || my.isSheng(shiGong, gong))) {
			if(love==LOVE) hunyin[index++]="时日二宫相生，也主婚事能成，临吉门或吉神或吉星或吉格，可能性更大；";
			else if(love==MARRY) hunyin[index++]="时日二宫相生，离婚离不了之象，临吉门或吉神或吉星或吉格，可能性更大；";
		}else if(ystype==my.YSHENDAY && my.isBihe(gong, shiGong)) {
			if(love==LOVE) hunyin[index++]="时日二宫相比和，也主婚事能成，临吉门或吉神或吉星或吉格，可能性更大；";
			else if(love==MARRY) hunyin[index++]="时日二宫相比和，离婚离不了之象，临吉门或吉神或吉星或吉格，可能性更大；";
		}else if(ystype==my.YSHENDAY && (my.isChongke(gong, shiGong) || my.isChongke(shiGong, gong))) {
			if(love==LOVE) hunyin[index++]="时日二宫相冲克，也主婚事难成；";
			else if(love==MARRY) hunyin[index++]="时日二宫相冲克，测离婚，有离散之象；";
		}
		
		//六合宫克对象年命宫｜女庚宫｜男乙宫、六合克太岁宫；
		if(love==LOVE && boy && my.isChongke(heGong, yiGong)) {
			hunyin[index++] ="六合落"+heGong+"宫冲克乙奇"+heGong+"宫，也主婚事难成；";
		}
		if(love==LOVE && !boy && my.isChongke(heGong, gengGong)) {
			hunyin[index++] ="六合落"+heGong+"宫冲克庚所落"+gengGong+"宫，也主婚事难成；";
		}
		if(my.isChongke(heGong, nianganGong)) {
			if(love==LOVE) hunyin[index++] ="六合落"+heGong+"宫冲克太岁天干所在"+nianganGong+"宫，也主婚事难成；";
			if(love==MARRY) hunyin[index++] ="六合落"+heGong+"宫冲克太岁天干所在"+nianganGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
		}else if(my.isChongke(heGong, nianziGong)) {
			if(love==LOVE) hunyin[index++] ="六合落"+heGong+"宫冲克太岁地支所在"+nianziGong+"宫，也主婚事难成；";
			if(love==MARRY) hunyin[index++] ="六合落"+heGong+"宫冲克太岁地支所在"+nianziGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
		}
		if(my.isChongke(nianganGong, heGong)) {
			if(love==LOVE) hunyin[index++] ="太岁天干落"+nianganGong+"宫冲克六合所在"+heGong+"宫，也主婚事难成；";
			if(love==MARRY) hunyin[index++] ="太岁天干落"+nianganGong+"宫冲克六合所在"+heGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
		}else if(my.isChongke(nianziGong,heGong)) {
			if(love==LOVE) hunyin[index++] ="太岁地支落"+nianziGong+"宫冲克六合所在"+heGong+"宫，也主婚事难成；";
			if(love==MARRY) hunyin[index++] ="太岁地支落"+nianziGong+"宫冲克六合所在"+heGong+"宫，必主凶，恐有婚变，太岁地支月为应期；";
		}
		
		//年干生对象宫或六合宫；克用神，用神对象，六合；
		if(boy && love==LOVE && my.isSheng(nianganGong, yiGong))
			hunyin[index++] ="年干落"+nianganGong+"宫生对象[乙]所在"+yiGong+"宫，父母满意这桩婚事；";
		else if(!boy && love==LOVE && my.isSheng(nianganGong, gengGong))
			hunyin[index++] ="年干落"+nianganGong+"宫生对象[庚]所在"+gengGong+"宫，父母满意这桩婚事；";
		if(love==LOVE && my.isSheng(nianganGong, heGong))
			hunyin[index++] ="年干落"+nianganGong+"宫生六合所在"+heGong+"宫，父母满意这桩婚事；";		
		
		//乙与庚相生、比和、同宫；
		boolean isboy = my.isSheng(gengGong,yiGong);
		boolean isgirl = my.isSheng(yiGong, gengGong);
		if(isboy || isgirl){
			String t = null;
			if(love==LOVE) {
				t = "乙与庚落宫相生，恋爱可成，逢吉门吉格吉星吉神可能性更大；"; 
				if(!isboy) t+="女方追求男方；";
				else t+="男方追求女方；";
				if(isboy && my.isKong(gengGong)) t+="但男方落宫空亡，有心杀敌，无力回天，无力追求对方；";
				else if(isboy && !my.isganHeju(YiJing.GENG, 0)[0].equals("1")) t+="但男方落宫休囚，有心杀敌，无力回天，无力追求对方；";
				
				if(isgirl && my.isKong(yiGong)) t+="但女方落宫空亡，因自身条件无力追求对方；";
				else if(isgirl && !my.isganHeju(YiJing.YI, 0)[0].equals("1")) t+="但女方落宫空亡，因自身条件无力追求对方；";
			}	else if(love==MARRY) {
				t = "乙与庚落宫相生，婚姻美满，离婚离不了；逢吉门吉格吉星吉神可能性更大；";
			}			
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENDI) t+="乙宫逢九地，主长久；";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENDI) t+="庚宫逢九地，主长久；";
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENHE) t+="乙宫逢六合，主情投意合；";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENHE) t+="庚宫逢六合，主情投意合；";
			if(t!=null) hunyin[index++] = t; 
		}
		else if( my.isBihe(gengGong,yiGong)){  //比和不包括自身
			String t = null;
			if(love==LOVE) t = "乙与庚落宫比和，恋爱可成，逢吉门、吉格、吉星、吉神可能性更大；"; 
			else if(love==MARRY) t = "乙与庚落宫比和，婚姻美满，离婚离不了；逢吉门、吉格、吉星、吉神可能性更大；";
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENDI) t+="乙宫逢九地，主长久；";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENDI) t+="庚宫逢九地，主长久；";
			if(daoqm.gInt[1][1][yiGong]==QiMen.SHENHE) t+="乙宫逢六合，主情投意合；";
			if(daoqm.gInt[1][1][gengGong]==QiMen.SHENHE) t+="庚宫逢六合，主情投意合；";
			if(t!=null) hunyin[index++] = t; 
		}else if(gengGong == yiGong){
			String t = null;
			if(love==LOVE) t = "乙与庚同落"+gengGong+"宫，婚事必成之象；";
			else if(love==MARRY) t="乙与庚同落"+gengGong+"宫，婚姻稳定之象；";
			if(!my.getJixiongge(gengGong)[0].equals("1")) t += "可惜临凶格，又主好事多磨，不吉之象；";
			int[] dpjy = my.getDpjy(gengGong);
			if(dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN) t+= "临壬，又主流散不成之象；";
			if(t!=null) hunyin[index++] = t;
		}
			
		//乙与庚冲克
		boolean isyi = my.isChongke(yiGong, gengGong);
		boolean isgeng = my.isChongke(gengGong, yiGong);
		if(isyi || isgeng) {
			String t = null;
			if(love==LOVE) {
				t = "乙与庚落宫相冲克，相冲则散，皆主婚事难成；";
				if(my.isSheng(gengGong, dingGong)) 
					t+="又庚与丁落宫相生，说明男方心中另有所爱 ；";
				if(my.isSheng(yiGong, bingGong)) 
					t+="又乙与丙落宫相生，说明女方早已心有所属；";
				int[] yidpjy = my.getDpjy(yiGong);
				int[] gengdpjy = my.getDpjy(gengGong);
				if(yiGong==bingGong || yidpjy[0]==YiJing.BING || yidpjy[1]==YiJing.BING )
					t += "乙丙同宫，可能性更大；";
				if(gengGong==dingGong || gengdpjy[0]==YiJing.DING || gengdpjy[1]==YiJing.DING )
					t += "庚丁同宫，可能性更大；";
			} else if(love==MARRY) {
				t = "乙与庚落宫相冲克，相冲则散，皆主夫妻关系出现了问题；";
				if(isyi && !my.getJixiongge(gengGong)[0].equals("1")) t += "又逢凶格，离婚几成定局；";
				else if(isgeng && !my.getJixiongge(yiGong)[0].equals("1")) t += "又逢凶格，离婚几成定局；";
				int yiws = SiZhu.TGSWSJ[YiJing.YI][SiZhu.yz];
				int gengws = SiZhu.TGSWSJ[YiJing.GENG][SiZhu.yz];				
				if(gengws>yiws) t+="庚在月令处"+SiZhu.TGSWSJNAME[gengws]+"地，乙在月令处"+SiZhu.TGSWSJNAME[yiws]+"地，男方更强硬，先提出离婚；";
				else t+="庚在月令处"+SiZhu.TGSWSJNAME[gengws]+"地，乙在月令处"+SiZhu.TGSWSJNAME[yiws]+"地，女方更强硬，先提出离婚；";
				if((yiGong==2 && gengGong==8) || (yiGong==8 && gengGong==2)) t+="幸好为2与8宫相冲，毕竟为和，主有夫妻矛盾；";
				if(my.isSheng(gengGong, dingGong) || my.isSheng(dingGong, gengGong)) 
					t+="庚与丁落宫相生，说明男方有外遇 ，必因情人而与妻子闹离婚；";
				if(my.isSheng(yiGong, bingGong) || my.isSheng(bingGong, yiGong)) 
					t+="乙与丙落宫相生，说明女方红杏出墙 ，必因情人而与丈夫闹离婚；";
				int[] yidpjy = my.getDpjy(yiGong);
				int[] gengdpjy = my.getDpjy(gengGong);
				if(yiGong==bingGong || yidpjy[0]==YiJing.BING || yidpjy[1]==YiJing.BING )
					t += "乙丙同宫，可能性更大；";
				if(gengGong==dingGong || gengdpjy[0]==YiJing.DING || gengdpjy[1]==YiJing.DING )
					t += "庚丁同宫，可能性更大；";
			}
			if(t!=null) hunyin[index++] = t;
		}
			
		//结婚之期
		if(ystype==my.YSHENDAY && love==LOVE){
			hunyin[index++] = "婚期之应：";
			boolean rinei = my.isNenpan(gong);
			boolean shinei = my.isNenpan(shiGong); 
			String t = null;
			if(rinei && shinei) t = "日时均在内盘，主快；";
			else if(!rinei && !shinei) t = "日时均在外盘，时间不会太快；";
			else t = "日时一内一外，主慢；";				
			if(t!=null) hunyin[index++] = "    "+t;
		}
		if(love==LOVE) {
			String t = null;
			if(my.getSige(4)!=0) t="逢时格，更快；";
			else if(my.getSige(3)!=0) t="逢日格，更快；";
			else if(my.getSige(2)!=0) t="逢月格，年内成婚之象；";
			else if(my.getSige(1)!=0) t="逢年格，年内成婚之象；";
			if(t!=null) hunyin[index++] = "    "+t;
			
			t = "";
			if(YiJing.DZLIUHE[SiZhu.sz][SiZhu.nz]!=0)
				t += "时支与年支合，";
			if(YiJing.DZLIUHE[SiZhu.rz][SiZhu.nz]!=0)
				t += "日支与年支合，";
			if(YiJing.DZLIUHE[SiZhu.sz][SiZhu.yz]!=0)
				t += "时支与月支合，";
			if(YiJing.DZLIUHE[SiZhu.rz][SiZhu.yz]!=0)
				t += "日支与月支合，";
			if(!"".equals(t)) hunyin[index++] = "    "+t+"更有年内成婚之象；";
			
			if(heGong == nianganGong) hunyin[index++] = "    六合与太岁同落"+heGong+"宫，年内成婚之象；";
			if(my.isSheng(nianganGong, heGong)) hunyin[index++] = "    年干太岁落"+nianganGong+"宫生六合"+heGong+"宫，也是年内成婚之象；";
			if(my.isSheng(nianziGong, heGong)) hunyin[index++] = "    地支太岁落"+nianziGong+"宫生六合"+heGong+"宫，也是年内成婚之象；";
			if(my.isKong(heGong)) hunyin[index++] = "    六合落"+heGong+"宫空亡，以冲实或填实之日为应期；";
		}
		
		/////////////////////////////////////////////////////////////////////////////
		//以下专测已婚的//////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////		
		if(love!=MARRY) return;	
		int shigangong = my.getTianGong(SiZhu.sg, SiZhu.sz);
		int xingruigong = my.getXingGong(QiMen.XINGRUI);
		if(shigangong==xingruigong) {
			hunyin[index++] = "时干为小孩，或小孩年命临天芮，表示现在是学生；";
		}
		if(dingGong==my.getMenGong(QiMen.MENKAI)) {
			String t="丁为离婚证书，临开门为法院法官，可断已批准离婚；";
			if(dingGong == my.getShenGong(QiMen.SHENWU))
				t+= "上临玄武，为私下协议离婚，不愿让人知道；";
			hunyin[index++] = t;
		}
		if(ystype==3)
			hunyin[index++] ="时干为子女落"+shiGong+"宫，对应地支为"+my.getDznameOfGong(shiGong)+"，"+my.getGongshu(shiGong)+"可由地支序号、先后天数、五行之数综合旺衰断子女数量；"+my.gettgWS(SiZhu.sg, SiZhu.sz)[1];		
				
		if(yiGong==3) hunyin[index++] = "乙奇落震3宫，为回归乙卯正位，主已离婚；";
		//乙与庚落宫断是否离婚
		for(int _g_ : new int[]{yiGong,gengGong})
			if((daoqm.gInt[3][1][_g_]==QiMen.MENSI || daoqm.gInt[3][1][_g_]==QiMen.MENSHANG) &&
					(daoqm.gInt[2][1][_g_]==QiMen.XINGRUI || daoqm.gInt[2][1][_g_]==QiMen.XINGZHU) &&
					(my.getJixiongge(_g_)[0].equals("-1")) &&
					my.isYima(_g_))
				hunyin[index++] = (_g_==yiGong?"[乙]":"[庚]")+"落"+_g_+"宫带马星，临"+QiMen.bm1[daoqm.gInt[3][1][_g_]]+"门、天"+QiMen.jx1[daoqm.gInt[2][1][_g_]]+"星、凶格"+my.getJixiongge(_g_)[1]+"主已离婚，离婚之期看空亡或相克之宫；";
		
		//地盘乙与庚
		String t=null;
		int dingdpGong = my.getDiGong(YiJing.DING, 0);  //丁所在地盘宫
		int bingdpGong = my.getDiGong(YiJing.BING, 0);
		int gengdpGong = my.getDiGong(YiJing.GENG, 0);
		int yidpGong = my.getDiGong(YiJing.YI, 0);  //得到地盘乙落宫
		t = null;
		if(yidpGong==gengGong || yidpGong==gengdpGong ) {
			t = "地盘乙与庚为以前的婚姻状况，同落"+yidpGong+"宫，临壬主流动分开，主已离婚；离婚之期看宫之地支所主年支；";
			if(my.isYima(yidpGong)) t += "又临马星，也主离婚；";
		}	else if(gengdpGong==yiGong) {
			t = "地盘乙与庚为以前的婚姻状况，同落"+gengdpGong+"宫，临壬主流动分开，主已离婚；离婚之期看宫之地支所主年支；";
			if(my.isYima(gengdpGong)) t += "又临马星，也主离婚；";
		}
		if(t!=null) hunyin[index++] = t; 
		
		t=null;
		if(my.isChongke(yiGong, gengGong)) {
			t = "[乙]宫与配偶[庚]宫相冲克，表示要离婚的决心较大；";
			if(daoqm.gInt[3][1][yiGong]==QiMen.MENSI) t+="又临死门，更是死心塌地；";
			else if(daoqm.gInt[3][1][gengGong]==QiMen.MENSI) t+="又临死门，更是死心塌地；";
		}
		if(t!=null) hunyin[index++] = t;
		
		//乙(庚)宫克丁(丙)
		if(my.isChongke(yiGong, dingGong)) hunyin[index++]="乙落"+yiGong+"宫克丁所落"+dingGong+"宫，表示妻子不同意离婚；";
		if(my.isChongke(gengGong, bingGong)) hunyin[index++]="庚落"+gengGong+"宫克丙所落"+bingGong+"宫，表示丈夫不同意离婚；";
		
		
		//庚下临丁,乙下临丙
		t = "";
		if(gengGong==dingdpGong) {
			t += "庚下临丁，也主其夫有外遇；";
			if(my.isChongke(gengGong, dingGong) || my.isChongke(dingGong, gengGong))
				t+="但二者落宫相冲克，主不会长久，主克方旺相时必定分手；";
		}
		if(yiGong==bingdpGong) {
			t += "乙下临丙，妻子红杏出墙；";
			if(my.isChongke(yiGong, bingGong) || my.isChongke(bingGong, yiGong))
				t+="但二者落宫相冲克，主不会长久，主克方旺相时必定分手；";
		}
		if(dingGong==bingdpGong) {
			t += "丁下临丙，也主婚外情；";
			if(my.isSheng(bingGong, dingGong)) t+="丙生丁宫，如果丁宫又旺相，断追求其情妇的人很多；";
		}else if(bingGong==dingdpGong) {
			t += "丙下临丁，也主婚外情；";
			if(my.isSheng(bingGong, dingGong)) t+="丙生丁宫，如果丁宫又旺相，断追求其情妇的人很多；";
		}
		if(!"".equals(t)) hunyin[index++]= t; 
		int renGong = my.getTianGong(YiJing.REN, 0);
		if(daoqm.gInt[1][1][dingGong]==QiMen.SHENHU && 
				daoqm.gInt[3][1][dingGong]==QiMen.MENSHANG && my.isKong(renGong)) {
			hunyin[index++]="丁临白虎，伤门，其相合之干为壬，又落"+renGong+"宫空亡，主死了丈夫，为寡妇；";
		}
		//时生比丁
		t = null;
		int shigan = my.getTiangan(SiZhu.sg, SiZhu.sz);
		if(my.isSheng(shiGong, dingGong)) {
			t = "时干落"+shiGong+"宫生丁奇所落"+dingGong+"宫，也主有外遇；";
			if(shigan==YiJing.REN) t+="又时干为壬，丁壬合更主淫荡之合，婚外情信息更加明了；";
		}else if(my.isBihe(shiGong, dingGong)) {
			t = "时干落"+shiGong+"宫与丁奇所落"+dingGong+"宫比和，也主有外遇；";
			if(shigan==YiJing.REN) t+="又时干为壬，丁壬合更主淫荡之合，婚外情信息更加明了；";
		}
		if(t!=null) hunyin[index++]=t;
		
		//女的前夫现夫
		t="";
		int[] dingdpjy = my.getDpjy(dingGong);  //得到丁宫的地盘奇仪
		int[] bingdpjy = my.getDpjy(bingGong);  		
		if(!boy && (my.isSheng(gengGong, dingGong) || my.isSheng(dingGong, gengGong))) {
			t+="女已离异的，庚为前夫，丁为前夫后续之妻，二者落宫相生，表示前夫已谈一对象，要再婚了；";
			if(my.isKong(dingGong)) t+="丁落空亡，则填实之日为婚期；";
			if(daoqm.gInt[1][1][dingGong]==QiMen.SHENWU && (dingdpjy[0]==YiJing.REN || dingdpjy[1]==YiJing.REN))
				t+="丁宫下临壬上乘玄武，表示该女已结合过，即结过婚；";
			if(my.isSheng(bingGong, gong) || my.isSheng(gong, bingGong))
				t+="丙为现夫，现与用神宫相生，为有一男人追求，相遇时间为宫中地支所临月份；";
			if(bingdpjy[0]==YiJing.XIN || bingdpjy[1]==YiJing.XIN)
				t+="丙下临辛为合，表示该男人已结过婚；";
		}
		if(!"".equals(t)) hunyin[index++]=t;
		
	//男的前妻现妻
		t="";
		if(boy && (my.isSheng(yiGong, bingGong) || my.isSheng(bingGong, yiGong))) {
			t+="男已离异的，乙为前妻，丙为前妻后续之夫，二者落宫相生，表示前妻已谈一对象，要再婚了；";
			if(my.isKong(bingGong)) t+="丙落空亡，则填实之日为婚期；";
			if(daoqm.gInt[1][1][bingGong]==QiMen.SHENWU && (bingdpjy[0]==YiJing.XIN || bingdpjy[1]==YiJing.XIN))
				t+="丙宫下临辛上乘玄武，表示该男已结合过，即结过婚；";
			if(my.isSheng(dingGong, gong) || my.isSheng(gong, dingGong))
				t+="丁为现妻，与用神宫相生，为正追求一女子，相遇时间为宫中地支所临月份；";
			if(dingdpjy[0]==YiJing.REN || dingdpjy[1]==YiJing.REN)
				t+="丁下临壬为合，表示该男人已结过婚；";
		}
		if(!"".equals(t)) hunyin[index++]=t;
		
		//戊断离婚钱财
		int wuTGong = my.getTianGong(YiJing.WUG, 0);
		int wuDGong = my.getDiGong(YiJing.WUG, 0);
		if(my.gettgWS(YiJing.WUG, 0)[0].equals("1")) {
			hunyin[index++]="天盘戊落"+wuTGong+"宫断离婚钱财总数目，现落宫旺相，可取大值；天盘戊也为客为其妻分得的财物，地盘戊落"+wuDGong+"宫为自己分得的财物；";
		}else {
			hunyin[index++]="天盘戊落"+wuTGong+"宫断离婚钱财总数目，现落宫休囚，可取小值；天盘戊也为客为其妻分得的财物，地盘戊落"+wuDGong+"宫为自己分得的财物；";
		}
		if(ystype==my.YSHENDAY && shiGong==gong){
			hunyin[index++]="时日同宫，离婚后小孩判给自己；";
		}
		int jing1Gong = my.getMenGong(QiMen.MENJING1);
		hunyin[index++]="如果要打官司，现惊门落"+jing1Gong+"宫，应在"+QiMen.JIUGONGFXIANG[jing1Gong]+"找律师；";
		
	//离婚之期
		if(ystype==my.YSHENDAY ){
			hunyin[index++] = "离婚之期：";
			boolean rinei = my.isNenpan(gong);
			boolean shinei = my.isNenpan(shiGong); 
			t = null;
			if(rinei && shinei) t = "日时均在内盘，主快；";
			else if(!rinei && !shinei) t = "日时均在外盘，时间不会太快；";
			else t = "日时一内一外，主慢；";				
			if(t!=null) hunyin[index++] = "    "+t;
		}
		t = null;
		if(my.getSige(4)!=0) t="逢时格，更快；";
		else if(my.getSige(3)!=0) t="逢日格，更快；";
		else if(my.getSige(2)!=0) t="逢月格，年内成婚之象；";
		else if(my.getSige(1)!=0) t="逢年格，年内成婚之象；";
		if(t!=null) hunyin[index++] = "    "+t;
		
		t = "";
		if(YiJing.DZCHONG[SiZhu.sz][SiZhu.nz]!=0)
			t += "时支与年支冲，";
		if(YiJing.DZCHONG[SiZhu.rz][SiZhu.nz]!=0)
			t += "日支与年支冲，";
		if(YiJing.DZCHONG[SiZhu.sz][SiZhu.yz]!=0)
			t += "时支与月支冲，";
		if(YiJing.DZCHONG[SiZhu.rz][SiZhu.yz]!=0)
			t += "日支与月支冲，";
		if(!"".equals(t)) hunyin[index++] = "    "+t+"更有年内离婚之象；";
		
		if(my.isChongke(nianganGong, heGong)) hunyin[index++] = "    年干太岁落"+nianganGong+"宫冲克六合"+heGong+"宫，也是年内离婚之象；";
		if(my.isChongke(nianziGong, heGong)) hunyin[index++] = "    地支太岁落"+nianziGong+"宫冲克六合"+heGong+"宫，也是年内离婚之象；";
		if(my.isYima(dingGong)) hunyin[index++] = "    丁临马星，则可以丁下所临之干为离婚之期，为戊则为甲日；";
		int zsGong = daoqm.getZhishiGong(SiZhu.sg, SiZhu.sz);  //得到值使落宫
		hunyin[index++] = "    值使门落"+zsGong+"宫，其所临之干可断应期，或以落宫之数断月数或天数；";
	}
	
	//由八神判断先天性格
	private void getXingeOfShen(int gan, int zi) {
		int gong = my.getTianGong(gan, zi);   //得到哪一宫
		
		//hunyin[index++]="----八神看先天性格----";
		String[] heju = my.isganHeju(gan, zi); //用神落宫是否合局
		if(heju[0].equals("1")) hunyin[index++] = "用神落宫合局，表示长得很不错，工作条件好，很有能力；"+heju[1];
		else hunyin[index++] = "用神落宫失局，表示长相、工作条件、能力一般；"+heju[1];
		
		if(daoqm.gInt[1][1][gong]==QiMen.SHENFU) hunyin[index++]="落宫临值符，代表比较有正直，有领导才能，关键时刻能处理好危急事物，比较善于帮助别人；"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENSHE) hunyin[index++]="落宫临藤蛇，代表随机应变的能力，处理事物圆滑，有虚诈不实的现象，善于撒谎；"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENYIN) hunyin[index++]="落宫临太阴，具有很好的策划头脑，善于出谋划策，考虑问题比较细腻和全面；"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENHE) hunyin[index++]="落宫临六合，代表公关能力和组织能力，人缘比较好，性格随和，与各种人群都能和睦相处；"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENHU) hunyin[index++]="落宫临白虎，代表脾气倔强，任性，刚烈，是个典型的直脾气，有意见当面摆出来；"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENWU) hunyin[index++]="落宫临玄武，代表聪慧，接受能力和领悟能力，但如果所处宫位不利则为奸诈、小聪明的信息表现；"; 
		else if(daoqm.gInt[1][1][gong]==QiMen.SHENDI) hunyin[index++]="落宫临九地，有善于掩藏，消极被动，容忍心；";
		else hunyin[index++]="落宫临九天，志高气昂，壮志凌云，积极开拓、主动表现的信息特征；"; 		
	}
	
	//由八门看后天性格
	private void getXingeOfMen(int gan, int zi) {
		int gong = my.getTianGong(gan, zi);   //得到哪一宫		 
		
		//hunyin[index++]="----八门看后天性格----";
		if(my.isTpJixing(gan, zi)) hunyin[index++]="落宫带六仪击刑，性暴烈；";
		if(daoqm.gInt[3][1][gong]==QiMen.MENXIU) hunyin[index++]="落宫临休门，代表比较懒散，工作和生活比较清闲的人，不善于处理家务；"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG) hunyin[index++]="落宫临生门，比较有经济头脑，经济意识强列，能在逆境中创出一条生路来；"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENSHANG) hunyin[index++]="落宫临伤门，好强争胜、善于出风头，凶杀打斗，聚众闹事等信息特征；"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENDU) hunyin[index++]="落宫临杜门，代表性格偏向传统和保守，不开放，在与人交往中属于能吞不能吐的人，是打了牙齿往自己肚子里咽的人；"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENJING3) hunyin[index++]="落宫临景门，善于表达，善于沟通，也代表造谣惑众，见风点火；"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENSI) hunyin[index++]="落宫临死门，死气沉沉，不善于沟通表达，独做主张，性格怪异；"; 
		else if(daoqm.gInt[3][1][gong]==QiMen.MENJING1) hunyin[index++]="落宫临惊门，积极的一面是心直口快，快言快语，消极的一面是胆小怕事，虚惊怪异，闲话是非；";
		else hunyin[index++]="落宫临落宫临开门，比较开放，能包容，敞开心扉，带这种性格的人不容易保守秘密，心里想什么不经过考虑就说什么。女人遇开门，再见乙＋戊就是穿衣暴露、性感；"; 		
	}
	//由九星看长相
	private void getZhangxiang(int gan, int zi) {
		String _t_ = null;
		int gong = my.getTianGong(gan, zi);   //得到哪一宫
		String[] ganWS = my.gettgWS(gan, zi);  //判断天干是否旺相
		String[] xingWS = my.getxingWS(gong);  //判断该宫星是否旺衰
		String[] shenJX = my.getshenJX(gong);  //得到该宫神的吉凶
		
		//hunyin[index++]="----九星看长相----";
		if(gong==3 || gong==4){ 
			if(ganWS[0].equals("1")) hunyin[index++]="落"+(gong==3?"震三":"巽四")+"宫旺相，身材高挑；";
			else hunyin[index++]="虽落"+(gong==3?"震三":"巽四")+"宫但休囚，身材不高；";
		}
		if(daoqm.gInt[2][1][gong]==QiMen.XINGPENG) {
			_t_ ="临天蓬星，男士胡须比较粗硬，浓黑，雄性特征明显，女性比较矮小，皮肤黑，或有男子气概；";
			if(xingWS[0].equals("1")) _t_+="落宫又旺相，身材肥大，脚步沉稳，长相比较凶；"+xingWS[1];
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGREN) {
			_t_="临天任星，身材矮小，心肠善良，容易接近宗教；";
			if(xingWS[0].equals("1")) _t_+="落宫又旺相，身材肥胖；"+xingWS[1]; 
			if(shenJX[0].equals("-1")) _t_+=shenJX[1]+"可能有驼背、手部变形受伤、瘸腿等；";		
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGCHONG) {
			_t_="临天冲星，女性身材苗条，男性偏瘦；";
			if(xingWS[0].equals("1")) _t_+="落宫又旺相，女性有曲线美，男性身材伟岸；"+xingWS[1]; 
			else _t_+="落宫休囚，女性就象鲁迅笔下圆规式的杨二嫂，男性弱不禁风；"+xingWS[1];
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGFU) {
			_t_="临天辅星，为人和气，能够赢得较好的人缘，眉清目秀；";
			if(xingWS[0].equals("1")) _t_+="落宫又旺相，在女性为美女，在男性为帅哥；"+xingWS[1]; 
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGQIN) hunyin[index++]="临天禽星，长相清秀，但比不上天辅，此种人人品不错，为人忠良正直；";
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGYING) hunyin[index++]="临天英星，面部多麻斑，男性胡须稀疏，女性头发缺少营养容易枯黄；";
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGRUI) {
			_t_="临天芮星，身材比较胖，个头不高，皮肤偏黑色，但这种人胸怀比较广，善于忍耐；";
			if(xingWS[0].equals("1")) _t_+="落宫又旺相，特征比较明显；"+xingWS[1];
			else _t_+="落宫休囚，特征不是很明显；"+xingWS[1];
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGZHU) {
			_t_="临天柱星，身材高大，刚烈，原则性比较强，容易给人不容易接近的感觉；";
			if(daoqm.gInt[1][1][gong]==QiMen.SHENHU && xingWS[0].equals("1"))
				_t_+="落宫又旺相，上临白虎，脾气暴躁之人；";
			hunyin[index++] = _t_;
		}
		else if(daoqm.gInt[2][1][gong]==QiMen.XINGXIN) hunyin[index++]="临天心星，面部宽，大腮，耳垂明显，处理事情果断勇敢；";
	}
	
	//由八门格局看职业
	private void getZhiye(int gan, int zi) {
		String _t_ = null;
		int gong = my.getTianGong(gan, zi);   //得到哪一宫
		int[] tpjy = my.getTpjy(gong); 
		int[] dpjy = my.getDpjy(gong);
		int yearGong = my.getTianGong(SiZhu.ng, SiZhu.nz);
		int kaiGong = my.getMenGong(QiMen.MENKAI); //得到开门所在的宫
		int duGong = my.getMenGong(QiMen.MENDU); //得到杜门宫
		int huGong = my.getShenGong(QiMen.SHENHU); //得到白虎宫
		int pengGong = my.getXingGong(QiMen.XINGPENG); //天蓬星所在的宫
		int shengGong = my.getMenGong(QiMen.MENSHANG);  //得到生门所在的宫
		int sheGong = my.getShenGong(QiMen.SHENSHE);  //得到腾蛇所在的宫
		int fuGong = my.getShenGong(QiMen.SHENFU);  //得到值符所在的宫
		boolean isKong = my.isKong(gong, my.SHIKONGWANG);  //判断用神是否为旬空
		boolean isNei = my.isNenpan(gong); //判断是否为内盘
		String[] heju = my.isganHeju(gan, zi); //用神落宫是否合局
		String[] jxge = my.getJixiongge(gong);  //得到宫的吉凶格
		
		//hunyin[index++]="----八门看职业----";
		if(my.isChongke(gong, kaiGong)) hunyin[index++]="用神落"+gong+"宫克开门所在"+kaiGong+"宫，表示不爱工作；"; 
		if(my.isSheng(gong, shengGong)) hunyin[index++]="用神落"+gong+"宫生生门所在"+shengGong+"宫，表示挣钱有方；"; 
		if(gong==sheGong) {
			_t_="用神落宫上临腾蛇，表示信仰宗教；";
			if(isKong) _t_ += "但逢空亡，表示身入教但心不实；";
			hunyin[index++] = _t_;
		}
		if(my.isChongke(gong, yearGong)) hunyin[index++]="用神落"+gong+"宫克年干所在"+yearGong+"宫，表示与父母关系不好；";
		if(gong==fuGong) {
			if(gong==1 || gong==3 || gong==7 || gong==9)
				_t_ = "用神落"+gong+"宫，上临值符，为有正职的领导或公司负责人；";
			else if(gong==6)
				_t_ = "用神落"+gong+"宫，上临值符，为某单位领导或负责人；";
			else
				_t_ = "用神落"+gong+"宫，上临值符，可能为副职的领导或公司负责人；";
			String[] zhifuhj = my.isganHeju(gong);
			if(zhifuhj[0].equals("1")) _t_ +="宫中合局，应为有权有势之人；"+zhifuhj[1];
			else _t_ +="宫中失局，官阶不大，应为科长主任之类；"+zhifuhj[1];
			if(_t_!=null) hunyin[index++] = _t_;
		}
		if(gong==duGong && gong==huGong) {
			 _t_ = null;
			if(tpjy[0]==YiJing.REN || tpjy[1]==YiJing.REN || dpjy[0]==YiJing.REN || dpjy[1]==YiJing.REN)
				_t_ = "用神落"+gong+"宫，上临白虎，宫中又逢壬为天罗，主被关押过；";
			else if(tpjy[0]==YiJing.XIN || tpjy[1]==YiJing.XIN || dpjy[0]==YiJing.XIN || dpjy[1]==YiJing.XIN)
				_t_ = "用神落"+gong+"宫，上临白虎，宫中又逢辛为犯人，主被关押过；";
			else if(tpjy[0]==YiJing.GUI || tpjy[1]==YiJing.GUI || dpjy[0]==YiJing.GUI || dpjy[1]==YiJing.GUI)
				_t_ = "用神落"+gong+"宫，上临白虎，宫中又逢癸为地网，主被关押过；";
			if( _t_!=null && gong==pengGong) _t_+="并且被罚了款；";
			if(_t_!=null) hunyin[index++] = _t_;
		}		
		if(isNei) hunyin[index++] = "用神落"+gong+"宫，为内盘，工作地点不远；";
		else hunyin[index++] = "用神落"+gong+"宫，为外盘，在外地工作；";
		
		if(daoqm.gInt[3][1][gong]==QiMen.MENKAI){
			_t_ = "落宫临开门，为开店做生意的老板，或主有公职的管理者；";
			if(isKong) _t_ += "但逢空亡，表示目前还没有工作；";
			else if(heju.equals("1")) _t_+="又合局，目前工作比较顺心；";
			else _t_+="但失局，目前工作不顺心；";
			hunyin[index++] = _t_;
		}
		if(daoqm.gInt[3][1][gong]==QiMen.MENXIU){
			_t_ = "落宫临休门，为水产、旅游、劳碌性质工作的人；";
			if(gong==6 || gong==7) _t_ += "逢"+gong+"宫，可能为财务人员，如出纳、会计；";
			hunyin[index++] = _t_;
		}
		
		String[] xingWS = my.getxingWS(gong);
		if(daoqm.gInt[3][1][gong]==QiMen.MENSHENG){
			_t_ = "落宫临生门，为生产管理、经营、贸易之人；";
			if(heju.equals("1")) {
				_t_+="又合局，表示收入丰厚，物质基础较好；";				
				if(xingWS[0].equals("1")) _t_+="又财星处旺地，更主财力雄厚；";
				if(jxge[0].equals("1")) _t_+= "再逢吉格，为巨商大贾；"+jxge[1]+xingWS[1];
			}	else {
				_t_+="但失局，表示收入不稳定，处境不佳；";
				if(!xingWS[0].equals("1")) _t_+="又逢财星不旺，更主生计艰难；"+xingWS[1];	
			}
			hunyin[index++] = _t_;			
			
			if(QiMen.men_gong[QiMen.MENSHENG][gong]==QiMen.zphym1) {
				if((tpjy[0]==YiJing.WUG ||tpjy[1]==YiJing.WUG ) && (dpjy[0]==YiJing.GENG || dpjy[0]==YiJing.GENG)) {
					if(daoqm.gInt[1][1][gong]==QiMen.SHENWU ){
						hunyin[index++] = "落宫临生门，宫中受制，又上临玄武，局中又有戊+庚飞宫格，必主失过财；";
					}
					if(daoqm.gInt[2][1][gong]==QiMen.XINGPENG)  {
						hunyin[index++] = "落宫临生门，宫中受制，又临天蓬凶星，局中又有戊+庚飞宫格，必主失过财；";
					}
				}
			}
					
		}
		
		if(daoqm.gInt[3][1][gong]==QiMen.MENSHANG) hunyin[index++] = "落宫临伤门，为运动员、军人、军警、渔猎、机械设备、兵工、车船、技术制造使用有关；";
		if(daoqm.gInt[3][1][gong]==QiMen.MENDU) hunyin[index++] = "落宫临杜门，为从事与技术有关，或在部队工作的人；";
		if(daoqm.gInt[3][1][gong]==QiMen.MENJING3) hunyin[index++] = "落宫临景门，为影视、广告、图书、电子、文化艺术、美容美体等职业；";
		if(daoqm.gInt[3][1][gong]==QiMen.MENSI) hunyin[index++] = "落宫临死门，为教徒、宗教工作、宗教信仰，或从事职业与地产、不动产屠宰、医院等有关；";
		if(daoqm.gInt[3][1][gong]==QiMen.MENJING1) hunyin[index++] = "落宫临惊门，为教师、律师、歌手、乐器、音响、播报员、导游、营销人员、娱乐场人员等；";		
	}
	
}




