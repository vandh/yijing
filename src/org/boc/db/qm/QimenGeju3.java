package org.boc.db.qm;


public class QimenGeju3 {
//	private DaoQiMen daoqm;
//	private QimenPublic pub; 
//	private String[] liunian ; //保存命运宫所有的描述情况
//	int index = 0; //计数器，为descMY所用
//	
//	//竖线、上面一条线、中间一条线、下一条线、要重复不可见的空白字符
//	private final String FILL1 = "　";   //占二个字符
//	private final String FILL2 = " ";   //占一个字符，用于微调
//	private final String BORDER  =  "┊";  // ┈ ─┌ ┐─┆┊├  ┬  ┼  ┐┘└  ┤┴ │
//	
//	private final String UPLINE   = "┌┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┐";
//	private final String MIDLINE =  "├┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┤";
//	private final String DOWNLINE = "└┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┘";
////	private final String UPLINEOUT   = "┏━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━┓";
////	private final String MIDLINEOUT =  "┣━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━┫";
////	private final String DOWNLINEOUT = "┗━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━┛";	
//	private final int SMALL = 12;  //小号字体 
//	private final int BIG = 21;    //大号字体
//	private final int COL = 15;  //一个九宫格的列数
//	
//	//这些是用来调整格子填充字符的个数的
//	private final int[] LINE4 = {2,2,4,2,3};  
//	private final int[] LINE5 = {0,0,0,0,4,3};
//	//private final int[] LINE6 = LINE5; //{2,2,4,3};
//	private final int[] LINE7 = {1,3,3,7,1};
//	
//	//五不遇时、天辅大吉时、八门伏呤、九星伏呤、八门反吟、九星反吟	
//	/**
//	 * 如果调大小：1、2、3行不用调，4、5、6、7要调
//	 * 上1、中2、下1是死的，其它9格”┃    “是一致的，后面加“┃”进行封闭
//	 * 每格分7行， 每行列数固定；
//	 * 1行：宫旺衰smallpink-1，年月日时smallblue-2，smallblack-16
//	 * 2行：重要吉凶格smallblack-19
//	 * 3行：空格smallblack-19
//	 * 4行：空格smallblack-3,八神bigblack(bigred)-1，空格smallblack-4，天地盘冲合刑smallblack-10
//	 * 5行：八门宫月旺衰smallblack-3，八门bigblack(bigred)-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
//	 * 6行：九星宫月旺衰smallblack-3，九星bigblack-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
//	 * 7行：空格smallblack-4，地盘八神smallblack(smallred)-1，空格smallblack-5，暗干smallblack-9
//	 */
//	public String getGeju(ResultPanel rp, StringBuffer str) {
//		this.resultPane = rp;  	
//		try {
//			init();
//			
//			sblack(UPLINE);
//			newLine();
//			
//			for(int i=1; i<=7; i++) {
//				pGong(4, i);
//				pGong(9, i);
//				pGong(2, i);
//				sblack(BORDER);
//				newLine();
//			}
//			
//			sblack(MIDLINE);
//			newLine();
//			
//			for(int i=1; i<=7; i++) {
//				pGong(3, i);
//				pGong(5, i);
//				pGong(7, i);
//				sblack(BORDER);
//				newLine();
//			}
//			
//			sblack(MIDLINE);
//			newLine();
//			
//			for(int i=1; i<=7; i++) {
//				pGong(8, i);
//				pGong(1, i);
//				pGong(6, i);
//				sblack(BORDER);
//				newLine();
//			}
//			
//			sblack(DOWNLINE);
//			newLine();
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		return null;
//	}
//	
//	/**
//	 * 输出九宫格
//	 * @param gong
//	 * @throws BadLocationException
//	 */
//	private void pGong(int gong, int line) throws BadLocationException{		
//		sblack(BORDER);
//		if(line==1) pLine1(gong);
//		else if(line==2) pLine2(gong);
//		else if(line==3) pLine3(gong);
//		else if(line==4) pLine4(gong);
//		else if(line==5) pLine5(gong);
//		else if(line==6) pLine6(gong);
//		else if(line==7) pLine7(gong);		
//	}
//	
//	/**
//	 * 输出第一行：宫旺衰smallpink，年月日时smallblue，smallblack
//	 */
//	private void pLine1(int gong) throws BadLocationException{	
//		int len = COL; 
//		if(gong==5) {
//			swhite(speat(len,FILL1));
//			return;
//		}
//		
//		//1. 宫的旺衰		
//		String gongWS = QiMen.gong_yue[gong][SiZhu.yz];
//		spink(gongWS);
//		len--;
//		
//		//2. 年月日时标志
////		String nyrs = "";
////		int[] gans = pub.getTpjy(gong);		
////		if(gans[0]==ng || gans[1]==ng) {nyrs+="、年"; len=len-2;}
////		if(gans[0]==yg || gans[1]==yg) {nyrs+="、月"; len=len-2;}
////		if(gans[0]==rg || gans[1]==rg) {nyrs+="、日"; len=len-2;}
////		if(gans[0]==sg || gans[1]==sg) {nyrs+="、时"; len=len-2;}
////		if(nyrs.length()>0) sblue(nyrs);
//		
//		//3. 是否有日马、时马
//		if(pub.isYima(gong)) {sblack("、马"); len=len-2;}
//		if(pub.isYimaOfRi(gong)) {sblack("、驲"); len=len-2;}
//		
//		//4. 是否有日空、时空
//		if(pub.isKong(gong, pub.SHIKONGWANG)) {sblack("、空"); len=len-2;}
//		if(pub.isKong(gong, pub.RIKONGWANG)) {sblack("、昳"); len=len-2;}
//		
//		//5. 剩余补空格
//		swhite(speat(len,FILL1));
//	}
//	/**
//	 * 输出第二行：重要吉凶格smallblack-19
//	 */
//	private void pLine2(int gong) throws BadLocationException{
//		int len = COL;
//		StringBuilder sb  = new StringBuilder();
//		if(gong==5) {
//			swhite(speat(len,FILL1));
//			return;
//		}		
//		
//		//1. 是否年月日时格、飞干伏干
//		String ge = "";
//		int[] tgan = pub.getTpjy(gong);
//		int[] dgan = pub.getDpjy(gong);
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==ng || dgan[1]==ng)) {
//			len-=3; ge += "、年格";
//		}
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==yg || dgan[1]==yg)) {
//			len-=3; ge += "、月格";
//		}
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==rg || dgan[1]==rg)) {
//			len-=6; ge += "、日格、伏干";
//		}
//		if((tgan[0]==rg || tgan[1]==rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
//			len-=3; ge += "、飞干";
//		}
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; ge += "、时格";
//		}
//		sb.append(ge);
//		
//		//2. 飞宫伏宫格
//		ge = "";
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) {
//			len-=3; ge += "、伏宫";
//		}
//		else if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
//			len-=3; ge += "、飞宫";
//		}
//		sb.append(ge);
//		
//		//3. 悖格 丙+戊或戊+丙或丙+年月日时
//		ge="";
//		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) {
//			len-=3; ge += "、悖格";
//		}
//		else if((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
//				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[1]==ng ||dgan[1]==yg ||dgan[1]==rg ||dgan[1]==sg)) {
//			len-=3; ge += "、悖格";
//		}
//		sb.append(ge);
//		
//		//4. 天网四张：天盘六癸，地盘时干 ；地网遮蔽：天盘六壬，地盘时干。
//		ge = "";
//		if((tgan[0]==YiJing.GUI || tgan[1]==YiJing.GUI) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; ge += "、天张";
//		}
//		else if((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; ge += "、地蔽";
//		}
//		sb.append(ge);
//		
//		//5. 直符反吟为：天盘甲子，地盘甲午；天盘甲戍，地盘甲辰；天盘甲申，地盘甲寅，主灾祸立至，遇奇门无妨。
//		boolean iszhifan = ((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
//			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) ||
//			((tgan[0]==YiJing.JI || tgan[1]==YiJing.JI) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN)) ||
//			((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
//			((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI)) ||
//			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG));
//		if(iszhifan) {
//			sb.append("、值反");
//			len-=3;
//		}
//		
//		//6. 三奇得使：天盘乙奇加临地盘甲戍或甲午；天盘丙奇加临地盘甲子或甲申；天盘丁奇加临地盘甲辰或甲寅
//		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
//		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
//		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
//		if(is3) {
//			sb.append("、奇使");
//			len-=3;
//		}
//		
//		//7. 玉女守门：门盘直使加临地盘丁奇
//		if(zhishigong==gong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
//			sb.append("、玉守");
//			len-=3;
//		}
//		
//		//8. 欢怡： 三奇临六甲值符之宫为欢怡
//		if(gong==zhifugong && (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI || dgan[0]==YiJing.YI || dgan[1]==YiJing.YI ||
//				tgan[0]==YiJing.BING || tgan[1]==YiJing.BING || dgan[0]==YiJing.BING || dgan[1]==YiJing.BING ||
//				tgan[0]==YiJing.DING || tgan[1]==YiJing.DING || dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
//			sb.append("、欢怡");
//			len-=3;
//		}
//		
//		int kaimen = pub.getMenGong(QiMen.MENKAI);
//		int xiumen = pub.getMenGong(QiMen.MENXIU);
//		int shmen = pub.getMenGong(QiMen.MENSHENG);
//		int jing3 = pub.getMenGong(QiMen.MENJING3);
//		int du = pub.getMenGong(QiMen.MENDU);
//		int jing1 = pub.getMenGong(QiMen.MENJING1);
//		int shang = pub.getMenGong(QiMen.MENSHANG);
//		int si = pub.getMenGong(QiMen.MENSI);
//		int shenyin = pub.getShenGong(QiMen.SHENYIN);
//		int shenhe = pub.getShenGong(QiMen.SHENHE);
//		int shendi = pub.getShenGong(QiMen.SHENDI);
//		int shentian = pub.getShenGong(QiMen.SHENTIAN);
//		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
//		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
//		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
//		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
//		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
//		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;
//		//9. 三诈
//		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenyin==gong)) {
//			sb.append("、真诈");len-=3;
//		}
//		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenhe==gong)) {
//			sb.append("、休诈");len-=3;
//		}
//		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shendi==gong)) {
//			sb.append("、重诈");len-=3;
//		}
//		
//		//10. 五鬼
//		if(jing3==gong && (isyi || isbing || isding) && shentian==gong) {
//			sb.append("、天假");len-=3;
//		}
//		if(du==gong && (isgui || isji || isding) && (shenyin==gong || shendi==gong || shenhe==gong)) {
//			sb.append("、地假");len-=3;
//		}
//		if(jing1==gong && isren && shentian==gong) {
//			sb.append("、人假");len-=3;
//		}
//		if(shang==gong && (isgui || isji || isding) && (shendi==gong || shenhe==gong)) {
//			sb.append("、神假");len-=3;
//		}
//		if(si==gong && (isgui || isji || isding) && shendi==gong ) {
//			sb.append("、鬼假");len-=3;
//		}
//		
//		//11. 九遁
//		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumen==gong && shenyin==gong) {
//			sb.append("、人遁");len-=3;
//		}
//		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && shentian==gong) {
//			sb.append("、神遁");len-=3;
//		}
//		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && du==gong && shendi==gong) {
//			sb.append("、鬼遁");len-=3;
//		}
//		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && (dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)) {
//			sb.append("、天遁");len-=3;
//		}
//		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimen==gong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
//			sb.append("、地遁");len-=3;
//		}
//		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN)) {
//			sb.append("、云遁");len-=3;
//		}
//		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI || gong==1)) {
//			sb.append("、龙遁");len-=3;
//		}
//		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && gong==4) {
//			sb.append("、风遁");len-=3;
//		}
//		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && xiumen==gong && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN || gong==8)) {
//			sb.append("、虎遁");len-=3;
//		}		
//
//		//12. 奇格、天盘六庚，地盘乙，丙，丁三奇
//		if((tgan[0]==YiJing.GENG ||tgan[1]==YiJing.GENG) && (
//				dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI || 
//				dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING ||
//				dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)){
//			sb.append("、奇格");len-=3;
//		}
//			
//		//9. 遁甲开，六甲加会阳星
//		if(QiMen.gan_xing[tgan[0]][gong]!=0 || QiMen.gan_xing[tgan[1]][gong]!=0) {
//			sb.append("、遁开");len-=3;
//		}
//		
//		if(sb.length()>0) {
//			sblack(sb.delete(0, 1).toString());
//			len++;
//		}
//		swhite(speat(len,FILL1));
//	}
//	/**
//	 * 输出第三行：空格smallblack-19
//	 */
//	private void pLine3(int gong) throws BadLocationException{
//		if(gong==5) {
//			swhite(speat(COL,FILL1));
//			return;
//		}
//		StringBuilder sb = new StringBuilder();
//		int[] t = pub.getTpjy(gong);
//		int[] d = pub.getDpjy(gong);
//		
//		//1. 格局十干克应
//		Integer[] ge = pub.getJixiongge2(gong,0);
//		for(Integer g : ge) {
//			sb.append(QiMen.gGejuDesc[g][0]+"、");			
//		}
//		
//		//2. 三奇入墓，干中已有了
//		
//		//3. 三奇受刑（又叫三奇受制）
//		if(((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
//				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING ) && gong==1 ) ||
//				((t[0]==YiJing.BING || t[1]==YiJing.BING || t[0]==YiJing.DING || t[1]==YiJing.DING) && 
//				 (d[0]==YiJing.REN || d[1]==YiJing.REN || d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
//				 ((d[0]==YiJing.BING || d[1]==YiJing.BING || d[0]==YiJing.DING || d[1]==YiJing.DING) && 
//						 (t[0]==YiJing.REN || t[1]==YiJing.REN || t[0]==YiJing.GUI || t[1]==YiJing.GUI)) ||
//						 ((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==6) ||
//						 ((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG || d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
//						 ((d[0]==YiJing.YI || d[1]==YiJing.YI) && (t[0]==YiJing.GENG || t[1]==YiJing.GENG || t[0]==YiJing.XIN || t[1]==YiJing.XIN)))
//			sb.append("奇制、");
//		
//		//4. 三奇贵人升殿：乙奇临震宫，为日出扶桑，丙奇到离宫，为月照端门，丁奇到兑宫，为星见西方。
//		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3)
//			sb.append("日出扶桑、");
//		if((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==9)
//			sb.append("月照端门、");
//		if((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==7)
//			sb.append("星见西方、");
//		
//		//5. 三奇之灵：三奇合四吉神［阴合地天］或合三吉门者，为吉道清灵、用事俱吉。
//		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI ||
//				t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
//				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) &&
//				(daoqm.gInt[1][1][gong]==QiMen.SHENYIN || daoqm.gInt[1][1][gong]==QiMen.SHENDI || 
//						daoqm.gInt[1][1][gong]==QiMen.SHENHE || daoqm.gInt[1][1][gong]==QiMen.SHENTIAN ) && 
//						(daoqm.gInt[3][1][gong]==QiMen.MENKAI || daoqm.gInt[3][1][gong]==QiMen.MENXIU || daoqm.gInt[3][1][gong]==QiMen.MENSHENG)) 
//			sb.append("奇灵、");
//		
//		//6. 奇游禄位：乙到震、丙到巽、丁到离为本禄之位
//		if(((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3) ||
//				((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==4) ||
//				((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==9))
//				sb.append("奇禄、");
//		
//		//7. 奇仪相合：乙庚、丙辛、丁壬为奇合，戊癸、甲己为仪合，得吉门，凡事有和之象，主和解、了结、平局、平分。
//		if(((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG)) ||
//				((t[0]==YiJing.GENG || t[1]==YiJing.GENG) && (d[0]==YiJing.YI || d[1]==YiJing.YI)) ||
//				((t[0]==YiJing.BING || t[1]==YiJing.BING) && (d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
//				((t[0]==YiJing.XIN || t[1]==YiJing.XIN) && (d[0]==YiJing.BING || d[1]==YiJing.BING)) ||
//				((t[0]==YiJing.DING || t[1]==YiJing.DING) && (d[0]==YiJing.REN || d[1]==YiJing.REN)) ||
//				((t[0]==YiJing.REN || t[1]==YiJing.REN) && (d[0]==YiJing.DING || d[1]==YiJing.DING)))
//				sb.append("奇合、");
//		else if(((t[0]==YiJing.WUG || t[1]==YiJing.WUG) && (d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
//				((t[0]==YiJing.GUI || t[1]==YiJing.GUI) && (d[0]==YiJing.WUG || d[1]==YiJing.WUG)) ||
//				((t[0]==YiJing.JI || t[1]==YiJing.JI) && (gong==zhifugong)) ||
//				((gong==zhifugong) && (d[0]==YiJing.JI || d[1]==YiJing.JI)))
//				sb.append("仪合、");
//		
//		sb = sb.delete(sb.length()-1, sb.length());
//		sblack(sb.toString());
//		swhite(speat(COL-sb.length(),FILL1));
//	}
//	/**
//	 * 输出第四行：
//	 * 空格smallblack-2,八神bigblack(bigred)-1，空格smallblack-4，天地盘冲合刑smallblack-10
//	 */
//	private void pLine4(int gong) throws BadLocationException{
//		int i=0;
//		String str = "";
//		if(gong==5) {
//			swhite(speat(COL,FILL1));  //对于五宫，其干与下干、宫的刑冲合桃墓都已反应到天盘所在宫与地盘所在宫了
//			return ;
//		}
//		swhite(speat(LINE4[0],FILL1));
//		
//		int shen = daoqm.gInt[1][1][gong]; //八神名称
//		if(shen == QiMen.SHENFU) bred(QiMen.bs1[shen]);
//		else bblack(QiMen.bs1[shen]);
//		  
//		swhite(speat(LINE4[1],FILL1));
//		
//		if(pub.isTDXing(gong)) { //如果天地盘相刑
//			str += "刑";
//			i++;
//		}
//		if(pub.isTDChong(gong)) {  //如果天地盘冲
//			str += "冲";
//			i++;
//		}
//		if(pub.isTDGanHe(gong)) {//如果天地干六合
//			str += "合";
//			i++;
//		}
//		if(pub.isTDG3He(gong)) {//如果天地宫三合
//			str += "匼";
//			i++;
//		}else if(pub.isTDZi3He(gong)) {//如果天地半合，不存在天地六合
//			str += "峇";
//			i++;
//		}
//		sblack(str);
//		swhite(speat(LINE4[2]-i,FILL1));	
//		bwhite(speat(LINE4[3],FILL1));		
//		swhite(speat(LINE4[4],FILL2));
//	}
//	
//	/**
//	 * 输出第五行：
//	 * 八门宫月旺衰smallblack-3，八门bigblack(bigred)-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
//	 */
//	private void pLine5(int gong) throws BadLocationException{
//		int i=0;
//		String str = "";
//		if(gong==5) {    //对于中五宫，输出地盘干在天盘落宫的旺衰即可
//		//天盘干在宫和月令旺衰
//			int tpjgong = daoqm.getTpJigong();
//			String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][tpjgong];
//			int itganyws = SiZhu.TGSWSJ[daoqm.gInt[4][5][5]][SiZhu.yz];
//	  	String tganyws = SiZhu.TGSWSJNAME[itganyws];
//
//			swhite(speat(LINE5[0]+2,FILL1));
//			bwhite(FILL1);
//			swhite(speat(LINE5[1],FILL1));
//			sblack(tgangws+tganyws);
//			swhite(speat(LINE5[2],FILL1));
//			bwhite(speat(2,FILL1));
//			swhite(speat(LINE5[3],FILL1));
//			swhite(speat(LINE5[4], FILL1));
//			swhite(speat(LINE5[5],FILL2));
//			return ;
//		}
//		//天盘干在宫和月令旺衰
//		String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[2][3][gong]][gong];
//		int itganyws = SiZhu.TGSWSJ[daoqm.gInt[2][3][gong]][SiZhu.yz];
//  	String tganyws = SiZhu.TGSWSJNAME[itganyws];
//  	//八门
//  	String mengws =  QiMen.men_gong2[daoqm.gInt[3][1][gong]][gong];
//  	String menyws =  QiMen.men_yue[daoqm.gInt[3][1][gong]][SiZhu.yz];
//		
//		sblack(mengws+menyws);
//		swhite(speat(LINE5[0],FILL1));
//		
//		//八门名称
//		if(gong==zhishigong) bred(QiMen.bm1[daoqm.gInt[3][1][gong]]);  
//		else bblack(QiMen.bm1[daoqm.gInt[3][1][gong]]);
//			
//		swhite(speat(LINE5[1],FILL1));
//		sblack(tgangws+tganyws);
//		swhite(speat(LINE5[2],FILL1));
//		if(daoqm.gInt[2][3][gong]==rg || daoqm.gInt[2][3][gong]==sg) {
//			bblue(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
//		}else{
//			bblack(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
//		}
//		if(daoqm.isTpJigong(gong)) {
//			bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
//		}else {
//			bwhite(speat(1,FILL1));
//		}
//		swhite(speat(LINE5[3],FILL1));
//		
//		if(!pub.isTDG3He(gong)) {//天地宫三合不用判断了，在前面天地盘支时已判断
//			if(pub.isTG6He(gong)) {//是否天盘与宫六合
//				i++;
//				str += "冾";
//			}else if(pub.isTG3He(gong)) {//是否半合
//				i++;
//				str += "洽";
//			}
//		}
//		if(pub.isJixing(gong)) {//是否是六仪击刑
//			i++;
//			str += "刑";
//		}
//		if(pub.isTGChong(gong)) {
//			i++;
//			str += "冲";
//		}
//		if(pub.isTpTaohua(gong)) {
//			i++;
//			str += "桃";
//		}
//		if(pub.isTGanMu(gong)) {
//			i++;
//			str += itganyws<=5 ? "库":"墓";
//		}
//		sblack(str);
//		swhite(speat(LINE5[4]-i, FILL1));
//		swhite(speat(LINE5[5],FILL2));
//	}
//	
//	/**
//	 * 输出第六行：
//	 * 九星宫月旺衰smallblack-3，九星bigblack-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
//	 */
//	private void pLine6(int gong) throws BadLocationException{
//		int i=0;
//		String str = "";
//		if(gong==5) {  //对于中五宫，要特殊处理
//			int jigong = daoqm.getJiGong();
//			
//			//地盘干在宫和月令旺衰
//	  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][jigong];
//	  	int idganyws = SiZhu.TGSWSJ[daoqm.gInt[4][5][5]][SiZhu.yz];
//	  	String dganyws = SiZhu.TGSWSJNAME[idganyws];
//	  	//九星
//	  	String xinggws =  QiMen.xing_gong[5][jigong];
//	  	String xingyws =  QiMen.xing_yue[5][SiZhu.yz];
//	  	
//			sblack(xinggws+xingyws);  //输出天禽星旺衰
//			swhite(speat(LINE5[0],FILL1));  
//			bblack(QiMen.jx1[5]);  //天禽星
//			swhite(speat(LINE5[1],FILL1));
//			sblack(dgangws+dganyws);   //输出中五宫地盘干旺衰
//			swhite(speat(LINE5[2],FILL1));
//			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)  //输出地盘干，如果为日时则为蓝色
//				bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
//			else
//				bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
//			bwhite(speat(1,FILL1));  //输出一个大空格
//			swhite(speat(LINE5[3],FILL1));
//			swhite(speat(LINE5[4], FILL1));
//			swhite(speat(LINE5[5],FILL2));
//			return ;
//		}
//		
//  	//地盘干在宫和月令旺衰
//  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][gong]][gong];
//  	int idganyws = SiZhu.TGSWSJ[daoqm.gInt[4][5][gong]][SiZhu.yz];
//  	String dganyws = SiZhu.TGSWSJNAME[idganyws];
//  	//九星
//  	String xinggws =  QiMen.xing_gong[daoqm.gInt[2][1][gong]][gong];
//  	String xingyws =  QiMen.xing_yue[daoqm.gInt[2][1][gong]][SiZhu.yz];
//  	
//		sblack(xinggws+xingyws);
//		swhite(speat(LINE5[0],FILL1));
//		bblack(QiMen.jx1[daoqm.gInt[2][1][gong]]);
//		swhite(speat(LINE5[1],FILL1));
//		sblack(dgangws+dganyws);
//		swhite(speat(LINE5[2],FILL1));
//		if(daoqm.gInt[4][5][gong]==rg || daoqm.gInt[4][5][gong]==sg)
//			bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
//		else
//			bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
//		if(daoqm.isJiGong(gong)) {
//			bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
//		}else {
//			bwhite(speat(1,FILL1));
//		}
//		swhite(speat(LINE5[3],FILL1));
//		
//		if(!pub.isTDG3He(gong)) {//天地宫三合不用判断了，在前面天地盘支时已判断
//			if(pub.isDG6He(gong)) {//是否地盘与宫六合
//				i++;
//				str += "冾";
//			}else if(pub.isDG3He(gong)) {//是否半合
//				i++;
//				str += "洽";
//			}
//		}
//		if(pub.isDpJixing(gong)) {//是否是六仪击刑
//			i++;
//			str += "刑";
//		}
//		if(pub.isDGChong(gong)) {
//			i++;
//			str += "冲";
//		}
//		if(pub.isDpTaohua(gong)) {
//			i++;
//			str += "桃";
//		}
//		if(pub.isDGanMu(gong)) {
//			i++;
//			str += idganyws<=5 ? "库":"墓";
//		}
//		sblack(str);
//		swhite(speat(LINE5[4]-i, FILL1));
//		swhite(speat(LINE5[5],FILL2));
//	}
//	/**
//	 * 输出第七行：
//	 * 空格smallblack-4，地盘八神smallblack(smallred)-1，空格smallblack-5，暗干smallblack-1，空格8个
//	 */
//	private void pLine7(int gong) throws BadLocationException{
//		if(gong==5) {
//			swhite(speat(COL,FILL1));
//			return ;
//		}
//		swhite(speat(LINE7[0],FILL1));
//		swhite(speat(LINE7[1],FILL2));
//		
//		//地盘八神，即地盘干在天盘落宫的八神
//		int dpbs = daoqm.gInt[1][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
//		if(dpbs==QiMen.SHENFU) sred(QiMen.bs1[dpbs]);  
//		else sblack(QiMen.bs1[dpbs]);
//		
//		swhite(speat(LINE7[2],FILL1));
//		sblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][daoqm.gInt[3][1][gong]]]);  //暗干，即
//		swhite(speat(LINE7[3],FILL1));
//		swhite(speat(LINE7[4],FILL2));
//	}
//	
//	private void bwhite(String str) throws BadLocationException {
//		bwhite(doc.getLength(), str);
//	}
//	private void bwhite(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, bigwhite);
//	}
//	private void swhite(String str) throws BadLocationException {
//		swhite(doc.getLength(), str);
//	}
//	private void swhite(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, smallwhite);
//	}
//	private void spink(String str) throws BadLocationException {
//		spink(doc.getLength(), str);
//	}
//	private void spink(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, smallpink);
//	}
//	private void sblue(String str) throws BadLocationException {
//		sblue(doc.getLength(), str);
//	}
//	private void sblue(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, smallblue);
//	}
//	private void bblue(String str) throws BadLocationException {
//		bblue(doc.getLength(), str);
//	}
//	private void bblue(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, bigblue);
//	}
//	private void bblack(String str) throws BadLocationException {
//		bblack(doc.getLength(), str);
//	}
//	private void bblack(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, bigblack);
//	}
//	private void sblack(String str) throws BadLocationException {
//		sblack(doc.getLength(), str);
//	}
//	private void sblack(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, smallblack);
//	}
//	private void bred(String str) throws BadLocationException {
//		bred(doc.getLength(), str);
//	}
//	private void bred(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, bigred);
//	}
//	private void sred(String str) throws BadLocationException {
//		sred(doc.getLength(), str);
//	}
//	private void sred(int len, String str) throws BadLocationException{
//		doc.insertString(len, str, smallred);
//	}
//	private void newLine() throws BadLocationException {
//		doc.insertString(doc.getLength(), "\r\n", null);
//	}
//	
//	/**
//	 * 循环输出几个空格或指定的字符
//	 */
//	private String speat(int len, String fillstr) {
//		StringBuilder sb = new StringBuilder();
//		for(int i=0; i<len; i++) sb.append(fillstr);
//		
//		return sb.toString();
//	}
//	
//	int zhishigong ,zhifugong;  //值使、值符宫
//	int ng ,yg, rg, sg;  //去掉甲开头后的转换年干
//	
//	ResultPanel resultPane;  //展现结果的面板
//	MyTextPane text;				 //显示具体的排盘信息
//	Document doc ;					 //显示结构的文档
//	SimpleAttributeSet smallblack;  //小黑色字体
//	SimpleAttributeSet bigblack;    //大黑色字体加粗
//	SimpleAttributeSet smallred;    //小红色字体
//	SimpleAttributeSet bigred;      //大红色字体加粗
//	SimpleAttributeSet smallwhite;  //小白色字体
//	SimpleAttributeSet bigwhite;   	//大白色字体
//	SimpleAttributeSet smallblue;   //小蓝色字体
//	SimpleAttributeSet bigblue;   	//大蓝色字体
//	SimpleAttributeSet smallpink;   //水红色小字体
//
//	
//	public QimenGeju3(DaoQiMen daoqm, QimenPublic pub) {
//  	this.daoqm = daoqm;
//  	this.pub = pub;
//  }
//	
//	private void init() throws BadLocationException {
//		text = resultPane.getTextPane();
//		doc = text.getDocument();
//		doc.remove(0, doc.getLength());
//		
//		smallblack = new SimpleAttributeSet();//MAGENTA ORANGE
//		StyleConstants.setForeground(smallblack, Color.BLACK);
//		StyleConstants.setFontSize(smallblack, SMALL);
//		
//		bigblack = new SimpleAttributeSet();
//		StyleConstants.setForeground(bigblack, Color.BLACK);
//		StyleConstants.setBold(bigblack, true);
//		StyleConstants.setFontSize(bigblack, BIG);
//		
//		smallred = new SimpleAttributeSet();
//		StyleConstants.setForeground(smallred, Color.RED);  			
//		StyleConstants.setFontSize(smallred, SMALL);
//		
//		bigred = new SimpleAttributeSet();
//		StyleConstants.setForeground(bigred, Color.RED);
//		StyleConstants.setBold(bigred, true);
//		StyleConstants.setFontSize(bigred, BIG);
//		
//		smallpink = new SimpleAttributeSet();
//		StyleConstants.setForeground(smallpink, Color.MAGENTA);  
//		StyleConstants.setFontSize(smallpink, SMALL);
//
//		smallblue = new SimpleAttributeSet();
//		StyleConstants.setForeground(smallblue, Color.BLUE);  		
//		StyleConstants.setFontSize(smallblue, SMALL);
//		
//		bigblue = new SimpleAttributeSet();
//		StyleConstants.setForeground(bigblue, Color.BLUE);  
//		StyleConstants.setBold(bigblue, true);
//		StyleConstants.setFontSize(bigblue, BIG);
//		
//		smallwhite = new SimpleAttributeSet();
//		StyleConstants.setForeground(smallwhite, Color.WHITE);  
//		StyleConstants.setFontSize(smallwhite, SMALL);
//		
//		bigwhite = new SimpleAttributeSet();
//		StyleConstants.setForeground(bigwhite, Color.WHITE);
//		StyleConstants.setBold(bigwhite, true);
//		StyleConstants.setFontSize(bigwhite, BIG);
//		
//		zhifugong = daoqm.getZhifuGong();
//		zhishigong = daoqm.getZhishiGong();  //值使、值符宫
//		ng = pub.getTiangan(SiZhu.ng, SiZhu.nz);  //去掉甲开头后的转换年干
//		yg = pub.getTiangan(SiZhu.yg, SiZhu.yz);
//		rg = pub.getTiangan(SiZhu.rg, SiZhu.rz);
//		sg = pub.getTiangan(SiZhu.sg, SiZhu.sz);
//	}
}