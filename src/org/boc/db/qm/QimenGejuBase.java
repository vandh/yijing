package org.boc.db.qm;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.ui.MyTextPane;
import org.boc.ui.ResultPanel;
import org.boc.util.PrintWriter;

public class QimenGejuBase {
	DaoQiMen daoqm;
	QimenPublic pub; 
	String[] liunian ; //保存命运宫所有的描述情况
	int index = 0; //计数器，为descMY所用
	
	final int UP = 1;
	final int MID1 = 2;
	final int MID2 = 3;
	final int DOWN = 4;
	final int START = 5;
	final int END = 6;
	
	//竖线、上面一条线、中间一条线、下一条线、要重复不可见的空白字符
	public static final String FILL1 = "　";   //占二个字符
	public static final String FILL2 = " ";   //占一个字符，用于微调
	final String BORDER1  =  "┊";  // ┈ ─┌ ┐──┆┊├  ┬  ┼  ┐┘└  ┤┴ │	  ┊┋┇┆ 
	final String BORDER2  =  "│";
//	private final String UPLINE   = "┌┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┐";
//	private final String MIDLINE =  "├┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┤";
//	private final String DOWNLINE = "└┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┘";	
	static final int COL = 15;  //一个九宫格的列数
	
	//这些是用来调整格子填充字符的个数的
	final int[] LINE4 = {2,2,4,2,3};  
	final int[] LINE5 = {0,0,0,0,4,3};
	//private final int[] LINE6 = LINE5; //{2,2,4,3};
	final int[] LINE7 = {1,3,1,1,1,1};		
	boolean iszf ; 
	
	/**
	 * 输出表头5,12  5,6    5,6
	 ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
    ┊格  局┊ 阴遁五局                               ┊  值符┊ 禽四宫                                     ┊  值使┊ 死九宫       ┊
      ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
    ┊干  支┊ 己丑  丙子  甲寅  己巳     ┊ 符  头┊ 甲寅                                       ┊旬首┊甲子(戊)   ┊  
      ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
    ┊大格局┊五不遇时、天辅大吉时、八门伏呤、九星伏呤、八门反吟、九星反吟                     ┊
      ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
	
	　　格局： 阳遁二局，值符（芮一宫），值使（死六宫）　
　　　干支： 辛卯 辛丑 丙寅 乙未，符头（甲子），旬首（甲午辛）
　　　主格：　　　　　　　　　　　　　　　　　　　　　　　　　　
	 */
	void printHead() throws BadLocationException {
		if(!QiMen2.HEAD && !QiMen2.ALL) return;
		
		String pre2 = speat(3, FILL1);
		int ju = daoqm.getJu();
		int zsgong = getZSgong();//转盘有寄宫
		
		// 2. 输出格局
		whead(pre2 + daoqm.getYangOrYinDesc(false)+FILL1);
		whead(QiMen.yydunju[ju > 0 ? ju : 9 - ju]);
		whead("，值符");
		whead2(QiMen.jx1[daoqm.gInt[0][0][12]]+ QiMen.BIGNUM[zhifugong]+"宫");
		whead("，值使");
		whead2(QiMen.bm1[daoqm.gInt[3][1][zsgong]] + QiMen.BIGNUM[zsgong] + "宫" );
		pw.newLine();
		// 3.输出四柱
		String sizhu = YiJing.TIANGANNAME[SiZhu.ng] + YiJing.DIZINAME[SiZhu.nz]
				+ FILL2 + YiJing.TIANGANNAME[SiZhu.yg] + YiJing.DIZINAME[SiZhu.yz]
				+ FILL2 + YiJing.TIANGANNAME[SiZhu.rg] + YiJing.DIZINAME[SiZhu.rz]
				+ FILL2 + YiJing.TIANGANNAME[SiZhu.sg] + YiJing.DIZINAME[SiZhu.sz];
		int[] futou = daoqm.getFutou(SiZhu.rg, SiZhu.rz);
		//String sfutou = YiJing.TIANGANNAME[futou[1]] + YiJing.DIZINAME[futou[2]];
		whead(pre2 + "干支："+FILL1 );
		whead2(sizhu);
		whead("，符头");
		whead(QiMen.xunname[daoqm.getXunShu(futou[1], futou[2])]);
		whead("，旬首");
		whead(QiMen.xunname[daoqm.getXunShu(SiZhu.sg, SiZhu.sz)]);
		pw.newLine();
		// 4. 输出大格局
		StringBuilder sb = new StringBuilder();
		int wu = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg]; // 五不遇时、天辅大吉时
		if (wu != 0)
			sb.append(QiMen.gGejuDesc[wu][0] + "、");
		if (pub.isMenFu())
			sb.append("八门伏呤、");
		if (pub.isMenFan())
			sb.append("八门反呤、");
		if (pub.isXingFu())
			sb.append("九星伏呤、");
		if (pub.isXingFan())
			sb.append("九星反呤、");
		if (sb.length() > 0)
			sb = sb.delete(sb.length() - 1, sb.length());
		whead(pre2 + "主格：" + FILL1 );
		whead(sb.toString() + speat(30-sb.length(),FILL1)); //固定输出30个空格，省得不好在tip中定位
	}
//	void printHead() throws BadLocationException {
//		int lenth = 41;
//		String H = "─", V = "│"; //"┊";
//		String line = speat(lenth,H);
//		String pre = speat(4,FILL1);
//		String pre2 = speat(3,FILL1);		
//		int ju = daoqm.getJu();
//		
//		//1. 输出上边线
//		wline(pre+line);  
//		pw.newLine();
//		//2. 输出格局
//		whead(pre2+V+"格"+FILL1+"局"+V+QiMen.yydunju[ju>0?ju:9-ju]+speat(8,FILL1));
//		whead(V+"值"+FILL1+"符"+V+QiMen.jx1[daoqm.gInt[2][1][zhifugong]]+QiMen.BIGNUM[zhifugong]+"宫"+speat(1,FILL1));
//		whead(V+"值"+FILL1+"使"+V+QiMen.bm1[daoqm.gInt[3][1][zhishigong]]+QiMen.BIGNUM[zhishigong]+"宫"+speat(1,FILL1)+V);
//		pw.newLine();
//		//pw.sblack(pre+line);  
//		//pw.newLine();
//		//3.输出四柱
//		String sizhu = YiJing.TIANGANNAME[SiZhu.ng]+YiJing.DIZINAME[SiZhu.nz]+FILL1+
//			YiJing.TIANGANNAME[SiZhu.yg]+YiJing.DIZINAME[SiZhu.yz]+FILL1+
//			YiJing.TIANGANNAME[SiZhu.rg]+YiJing.DIZINAME[SiZhu.rz]+FILL1+
//			YiJing.TIANGANNAME[SiZhu.sg]+YiJing.DIZINAME[SiZhu.sz]+FILL1;
//		whead(pre2+V+"干"+FILL1+"支"+V+sizhu);
//		int[] futou = daoqm.getFutou(SiZhu.rg, SiZhu.rz);
//		String sfutou = YiJing.TIANGANNAME[futou[1]]+YiJing.DIZINAME[futou[2]];
//		whead(V+"符"+FILL1+"头"+V+sfutou+speat(2,FILL1));
//		whead(V+"旬"+FILL1+"首"+V+QiMen.xunname[daoqm.getXunShu(SiZhu.sg, SiZhu.sz)]+V);
//		pw.newLine();
//		//pw.sblack(pre+line);  
//		//pw.newLine();
//		//4. 输出大格局
//		StringBuilder sb = new StringBuilder();
//		int wu = QiMen.da_gan_gan[SiZhu.rg][SiZhu.sg];  //五不遇时、天辅大吉时
//		if(wu!=0) sb.append(QiMen.gGejuDesc[wu][0]+"、");
//		if(pub.isMenFu()) sb.append("八门伏呤、");
//		if(pub.isMenFan()) sb.append("八门反呤、");
//		if(pub.isXingFu()) sb.append("九星伏呤、");
//		if(pub.isXingFan()) sb.append("九星反呤、");
//		if(sb.length()>0) sb = sb.delete(sb.length()-1, sb.length());
//		whead(pre2+V+"吉凶格"+V+sb.toString()+speat(lenth-sb.length()-11, FILL1)+V);
//		pw.newLine();
//		wline(pre+line);  
//	}
//	
	private void whead(String s) throws BadLocationException {
		pw.print(s,PrintWriter.SBLACK,false);
	}
	private void whead2(String s) throws BadLocationException {
		pw.print(s,PrintWriter.SBLUE,false);
	}
//	private void wline(String s) throws BadLocationException {
//		SimpleAttributeSet smallcray = new SimpleAttributeSet();
//		StyleConstants.setForeground(smallcray, Color.GRAY);
//		StyleConstants.setFontSize(smallcray, SMALL);
//		doc.insertString(doc.getLength(), s, smallcray);
//	}
	
	/**
	 * 输出边竖线
	 * @param gong: 哪一宫
	 * @param type：是开始还是结束边线
	 * @throws BadLocationException
	 */
	void println(int gong, int type) throws BadLocationException {
		if(isYang) {
			if((gong==2 || gong==7) && type==START) pw.sblack(BORDER1);
			else if((gong==2 || gong==7 || gong==6) && type==END) pw.sblack(BORDER1);
			else pw.sred(BORDER2);
		}else {
			if((gong==9 || gong==2 || gong==7 || gong==6) && type==START) pw.sred(BORDER2);
			else if((gong==2 || gong==7 || gong==6) && type==END) pw.sred(BORDER2);
			else pw.sblack(BORDER1);
		}
	}
	
	/**
	 * 输出九宫格边线，内盘为红线，外盘为黑线
	 * @param type: 上、中、下连线
	 * @throws BadLocationException 
	 */
	void print(int type) throws BadLocationException {		
		if(type==UP && isYang) {  //阳局第一线
			pw.sred("┌┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬");
			pw.sblack("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┐");
		}else if(type==UP) {  //阴局第一线
			pw.sblack("┌┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬");
			pw.sred("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┬┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┐");
		}
		
		if(type==MID1 && isYang) {  //阳局第一线
			pw.sred("├┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼");
			pw.sblack("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┤");
		}else if(type==MID1) {  //阴局第一线
			pw.sblack("├┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼");
			pw.sred("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┤");
		}
		
		if(type==MID2 && isYang) {  //阳局第一线
			pw.sred("├┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼");
			pw.sblack("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┤");
		}else if(type==MID2) {  //阴局第一线
			pw.sblack("├┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┼");
			pw.sred("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┤");
		}
		
		if(type==DOWN && isYang) {  //阳局第一线
			pw.sred("└┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴");
			pw.sblack("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┘");
		}else if(type==DOWN) {  //阴局第一线
			pw.sblack("└┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┴");
			pw.sred("┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┘");
		}
	}
	
	/**
	 * 输出第一行：宫旺衰smallpink，年月日时smallblue，smallblack
	 */
	public void pLine1(int gong) throws BadLocationException{	
		int len = COL; 
		if(gong==5 && iszf) {
			pw.swhite(speat(len,FILL1));
			return;
		}
		
		//1. 宫的旺衰		
		if(QiMen2.MA || QiMen2.ALL) {
			String gongWS = QiMen.gong_yue[gong][SiZhu.yz];
			pw.spink(gongWS);
			len--;
		}
		
		//2. 十二神将	
		if(gong!=5 && (QiMen2.SJ || QiMen2.ALL)) {
			int[] sj = pub.getSJ12(gong);
			pw.sblack("、"+QiMen2.SHENJ[sj[0]][2]); 
			len=len-2;
			if(sj[1]!=0) {
				pw.sblack("、"+QiMen2.SHENJ[sj[1]][2]);
				len=len-2;
			}
		}
		
		//3. 是否有日马、时马
		if(QiMen2.MA || QiMen2.ALL) {			
			if(pub.isYimaOfRi(gong)) {pw.sblack("、"+QiMen.gGejuDesc[36][2]); len=len-2;}
			if(pub.isYima(gong)) {pw.sblack("、");pw.sred(QiMen.gGejuDesc[37][2]); len=len-2;}
			
			//4. 是否有日空、时空			
			if(pub.isKong(gong, pub.RIKONGWANG)) {pw.sblack("、"+QiMen.gGejuDesc[38][2]); len=len-2;}
			if(pub.isKong(gong, pub.SHIKONGWANG)) {pw.sblack("、");pw.sred(QiMen.gGejuDesc[39][2]); len=len-2;}
		}
		//5. 剩余补空格
		pw.swhite(speat(len,FILL1));
	}
	/**
	 * 输出第二行：重要吉凶格smallblack-19
	 * 第二行、第三行需合并部分项，防止长出格外
	 */
	//存放每宫第三行的数据
	//private Map<Integer, String> lineMap = new HashMap<Integer, String>();
	public void pLine2(int gong) throws BadLocationException{
		int len = COL;
		StringBuilder sb  = new StringBuilder();
		if((gong==5 && iszf) || (!QiMen2.JXGE && !QiMen2.ALL)) {
			pw.swhite(speat(len,FILL1));
			return;
		}		
		
		//1. 是否年月日时格、飞干伏干
		String ge = "";
		int[] tgan = pub.getTpjy(gong);
		int[] dgan = pub.getDpjy(gong);
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==ng || dgan[1]==ng)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(9); //"、年格";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==yg || dgan[1]==yg)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(10); //"、月格";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==rg || dgan[1]==rg)) {
			len-=6; 
			ge += QiMen.DUN+getGjname(11)+QiMen.DUN+getGjname(24);  //"、日格、伏干";
		}
		if((tgan[0]==rg || tgan[1]==rg) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(13); //"、飞干";
		}
		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==sg || dgan[1]==sg)) {
			len-=3;
			ge += QiMen.DUN+getGjname(12); //"、时格";
		}
		sb.append(ge);
		
		//2. 飞宫伏宫格，在十干克应中有了，此去省略
//		ge = "";
//		if((tgan[0]==YiJing.GENG || tgan[1]==YiJing.GENG) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) {
//			len-=3; ge += "、伏宫";
//		}
//		else if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG)) {
//			len-=3; ge += "、飞宫";
//		}
//		sb.append(ge);
		
		//3. 悖格 丙+戊或戊+丙或丙+年月日时
		ge="";
		if((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.BING || dgan[1]==YiJing.BING)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(16); //"、悖格";
		}
		else if((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && 
				(dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[1]==ng ||dgan[1]==yg ||dgan[1]==rg ||dgan[1]==sg)) {
			len-=3; 
			ge += QiMen.DUN+getGjname(16); //"、悖格";
		}
		sb.append(ge);
		
		//4. 天网四张：天盘六癸，地盘时干 ；地网遮蔽：天盘六壬，地盘时干。
//		ge = "";
//		if((tgan[0]==YiJing.GUI || tgan[1]==YiJing.GUI) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; 
//			ge += QiMen.DUN+getGjname(17); //"、天张";
//		}
//		else if((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==sg || dgan[1]==sg)) {
//			len-=3; 
//			ge += QiMen.DUN+getGjname(18); //"、地蔽";
//		}
//		sb.append(ge);
		
		//5. 直符反吟为：天盘甲子，地盘甲午；天盘甲戍，地盘甲辰；天盘甲申，地盘甲寅，主灾祸立至，遇奇门无妨。
//		boolean iszhifan = ((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
//			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG)) ||
//			((tgan[0]==YiJing.JI || tgan[1]==YiJing.JI) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN)) ||
//			((tgan[0]==YiJing.WUG || tgan[1]==YiJing.WUG) && (dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN)) ||
//			((tgan[0]==YiJing.REN || tgan[1]==YiJing.REN) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI)) ||
//			((tgan[0]==YiJing.XIN || tgan[1]==YiJing.XIN) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG));
//		if(iszhifan) {
//			sb.append(QiMen.DUN+getGjname(20)); //、值符反呤
//			len-=3;
//		}
		
		//6. 三奇得使：天盘乙奇加临地盘甲戍或甲午；天盘丙奇加临地盘甲子或甲申；天盘丁奇加临地盘甲辰或甲寅
		boolean is3 = (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI) && (dgan[0]==YiJing.JI || dgan[1]==YiJing.JI || dgan[0]==YiJing.XIN || dgan[1]==YiJing.XIN);
		is3 = is3 || ((tgan[0]==YiJing.BING || tgan[1]==YiJing.BING) && (dgan[0]==YiJing.WUG || dgan[1]==YiJing.WUG || dgan[0]==YiJing.GENG || dgan[1]==YiJing.GENG));
		is3 = is3 || ((tgan[0]==YiJing.DING || tgan[1]==YiJing.DING) && (dgan[0]==YiJing.REN || dgan[1]==YiJing.REN || dgan[0]==YiJing.GUI || dgan[1]==YiJing.GUI));
		if(is3) {
			sb.append(QiMen.DUN+getGjname(21) ); //"、奇使"
			len-=3;
		}
		
		//7. 玉女守门：门盘直使加临地盘丁奇
		if(zhishigong==gong && (dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
			sb.append(QiMen.DUN+getGjname(22)); //"、玉守"
			len-=3;
		}
		
		//8. 欢怡： 三奇临六甲值符之宫为欢怡
//		if(gong==zhifugong && (tgan[0]==YiJing.YI || tgan[1]==YiJing.YI || dgan[0]==YiJing.YI || dgan[1]==YiJing.YI ||
//				tgan[0]==YiJing.BING || tgan[1]==YiJing.BING || dgan[0]==YiJing.BING || dgan[1]==YiJing.BING ||
//				tgan[0]==YiJing.DING || tgan[1]==YiJing.DING || dgan[0]==YiJing.DING || dgan[1]==YiJing.DING)) {
//			sb.append(QiMen.DUN+getGjname(23));  //"、欢怡"
//			len-=3;
//		}
		
		int kaimen = pub.getMenGong(QiMen.MENKAI);
		int xiumen = pub.getMenGong(QiMen.MENXIU);
		int shmen = pub.getMenGong(QiMen.MENSHENG);
		int jing3 = pub.getMenGong(QiMen.MENJING3);
		int du = pub.getMenGong(QiMen.MENDU);
		int jing1 = pub.getMenGong(QiMen.MENJING1);
		int shang = pub.getMenGong(QiMen.MENSHANG);
		int si = pub.getMenGong(QiMen.MENSI);
		int shenyin = pub.getShenGong(QiMen.SHENYIN);
		int shenhe = pub.getShenGong(QiMen.SHENHE);
		int shendi = pub.getShenGong(QiMen.SHENDI);
		int shentian = pub.getShenGong(QiMen.SHENTIAN);
		boolean isyi = tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI ||dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI; 
		boolean isbing = tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING ||dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING;
		boolean isding = tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING ||dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING;
		boolean isren = tgan[0]==YiJing.REN ||tgan[1]==YiJing.REN ||dgan[0]==YiJing.REN ||dgan[1]==YiJing.REN;
		boolean isji = tgan[0]==YiJing.JI ||tgan[1]==YiJing.JI ||dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI;
		boolean isgui = tgan[0]==YiJing.GUI ||tgan[1]==YiJing.GUI ||dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI;
		//9. 三诈
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenyin==gong)) {
			sb.append(QiMen.DUN+getGjname(420));//"、真诈"
					len-=3;
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shenhe==gong)) {
			sb.append(QiMen.DUN+getGjname(421));//"、休诈"
			len-=3;
		}
		if((kaimen==gong || xiumen==gong || shmen==gong) && (isyi || isbing || isding) && (shendi==gong)) {
			sb.append(QiMen.DUN+getGjname(422));//"、重诈"
					len-=3;
		}
		
		//10. 五鬼
		if(jing3==gong && (isyi || isbing || isding) && shentian==gong) {
			sb.append(QiMen.DUN+getGjname(423));//"、天假"
					len-=3;
		}
		if(du==gong && (isgui || isji || isding) && (shenyin==gong || shendi==gong || shenhe==gong)) {
			sb.append(QiMen.DUN+getGjname(424));//"、地假"
					len-=3;
		}
		if(jing1==gong && isren && shentian==gong) {
			sb.append(QiMen.DUN+getGjname(425));//"、人假"
			len-=3;
		}
		if(shang==gong && (isgui || isji || isding) && (shendi==gong || shenhe==gong)) {
			sb.append(QiMen.DUN+getGjname(426));//"、神假"
			len-=3;
		}
		if(si==gong && (isgui || isji || isding) && shendi==gong ) {
			sb.append(QiMen.DUN+getGjname(427));//"、鬼假"
			len-=3;
		}
		
		//11. 九遁
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && xiumen==gong && shenyin==gong) {
			sb.append(QiMen.DUN+getGjname(428));//"、人遁"
			len-=3;
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && shentian==gong) {
			sb.append(QiMen.DUN+getGjname(429));//"、神遁"
			len-=3;
		}
		if((tgan[0]==YiJing.DING ||tgan[1]==YiJing.DING) && du==gong && shendi==gong) {
			sb.append(QiMen.DUN+getGjname(430));//"、鬼遁"
			len-=3;
		}
		if((tgan[0]==YiJing.BING ||tgan[1]==YiJing.BING) && shmen==gong && (dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)) {
			sb.append(QiMen.DUN+getGjname(431));//"、天遁"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && kaimen==gong && (dgan[0]==YiJing.JI ||dgan[1]==YiJing.JI)) {
			sb.append(QiMen.DUN+getGjname(432));//"、地遁"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN)) {
			sb.append(QiMen.DUN+getGjname(433));//"、云遁"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && (dgan[0]==YiJing.GUI ||dgan[1]==YiJing.GUI || gong==1)) {
			sb.append(QiMen.DUN+getGjname(434));//"、龙遁"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && (kaimen==gong || xiumen==gong || shmen==gong) && gong==4) {
			sb.append(QiMen.DUN+getGjname(436));//"、风遁"
			len-=3;
		}
		if((tgan[0]==YiJing.YI ||tgan[1]==YiJing.YI) && xiumen==gong && (dgan[0]==YiJing.XIN ||dgan[1]==YiJing.XIN || gong==8)) {
			sb.append(QiMen.DUN+getGjname(435));//"、虎遁"
			len-=3;
		}		

		//12. 奇格、天盘六庚，地盘乙，丙，丁三奇
		if((tgan[0]==YiJing.GENG ||tgan[1]==YiJing.GENG) && (
				dgan[0]==YiJing.YI ||dgan[1]==YiJing.YI || 
				dgan[0]==YiJing.BING ||dgan[1]==YiJing.BING ||
				dgan[0]==YiJing.DING ||dgan[1]==YiJing.DING)){
			sb.append(QiMen.DUN+getGjname(25));//"、奇格"
			len-=3;
		}
		
		//13 时干入墓，就是干入墓
//  	if((SiZhu.sg==YiJing.BING && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.BING) ||
//  			(SiZhu.sg==YiJing.WUG && SiZhu.sz==YiJing.XU && daoqm.gInt[2][3][6]==YiJing.WUG) ||
//  			(SiZhu.sg==YiJing.DING && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.DING) ||
//  			(SiZhu.sg==YiJing.JI && SiZhu.sz==YiJing.CHOU && daoqm.gInt[2][3][8]==YiJing.JI) ||
//  			(SiZhu.sg==YiJing.REN && SiZhu.sz==YiJing.CHEN && daoqm.gInt[2][3][4]==YiJing.REN) ||
//  			(SiZhu.sg==YiJing.GUI && SiZhu.sz==YiJing.WEI && daoqm.gInt[2][3][2]==YiJing.GUI)) {
//  		if(pub.gettgWS(SiZhu.sg, SiZhu.sz)[0].equals("1"))
//  			sb.append(QiMen.DUN+getGjname(8));//"、时干入库"
//  		else
//  			sb.append(QiMen.DUN+getGjname(7));//"、时干入墓"
//  		len-=3;
//  	}
			
		//14. 遁甲开，六甲加会阳星
//		if(QiMen.gan_xing[tgan[0]][daoqm.gInt[2][1][gong]]!=0 || QiMen.gan_xing[tgan[1]][daoqm.gInt[2][1][gong]]!=0) {
//			sb.append(QiMen.DUN+getGjname(437));//"、遁开"
//			len-=3;
//		}
		String strLine3 = this.pLine3(gong,false);
		if(strLine3!=null) {
			sb.append(QiMen.DUN+strLine3);
			sb.delete(sb.length()-1, sb.length());
			len -= strLine3.length();
		}
		if(sb.length()>0) {
			sb.delete(0, 1);
			len++;
			pw.sblack(sb.toString());			
		}
		pw.swhite(speat(len,FILL1));
	}
	/**
	 * 输出第三行：空格smallblack-19
	 * 总长二个，第一个为满长，则第二个为超长，如果不超，则第二个为null；返回null，表示为第5宫
	 * 第二行循环时，如果行3有超长，则并入2行，如果行2有超长则并入行3；
	 * 因第2行不会超，所以行3直接输出缓存中的数据
	 */
	private void proc(StringBuilder sb1, StringBuilder sb2, String s) {
		if(sb1.length()+s.length()-1 > COL) sb2.append(s);
		else sb1.append(s);
	}
	public String pLine3(int gong) throws BadLocationException{
		return pLine3(gong, true);
	}
	private String pLine3(int gong,boolean w) throws BadLocationException{
		if((gong==5 && iszf) || (!QiMen2.SGKY && !QiMen2.ALL)) {
			if(w) pw.swhite(speat(COL,FILL1));
			return null;
		}
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		int[] t = pub.getTpjy(gong);
		int[] d = pub.getDpjy(gong);
		
		//1. 格局十干克应
		Integer[] ge = pub.getJixiongge2(gong,iszf);
		for(Integer g : ge) {
			//sb1.append(QiMen.gGejuDesc[g][0]+QiMen.DUN);
			proc(sb1,sb2,getGjname(g)+QiMen.DUN);
		}
		
		//2. 三奇入墓，干中已有了
		
		//3. 三奇受刑（又叫三奇受制）
		if(((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING ) && gong==1 ) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || t[0]==YiJing.DING || t[1]==YiJing.DING) && 
				 (d[0]==YiJing.REN || d[1]==YiJing.REN || d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				 ((d[0]==YiJing.BING || d[1]==YiJing.BING || d[0]==YiJing.DING || d[1]==YiJing.DING) && 
						 (t[0]==YiJing.REN || t[1]==YiJing.REN || t[0]==YiJing.GUI || t[1]==YiJing.GUI)) ||
						 ((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==6) ||
						 ((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG || d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
						 ((d[0]==YiJing.YI || d[1]==YiJing.YI) && (t[0]==YiJing.GENG || t[1]==YiJing.GENG || t[0]==YiJing.XIN || t[1]==YiJing.XIN)))
			//sb.append(getGjname(26)+QiMen.DUN  );//"奇制、"
			proc(sb1,sb2,getGjname(26)+QiMen.DUN);
		
		//4. 三奇贵人升殿：乙奇临震宫，为日出扶桑，丙奇到离宫，为月照端门，丁奇到兑宫，为星见西方。
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3)
			proc(sb1,sb2,getGjname(27)+QiMen.DUN); //sb.append(getGjname(27)+QiMen.DUN  );//"日出扶桑、"
		if((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==9)
			proc(sb1,sb2,getGjname(28)+QiMen.DUN); //sb.append(getGjname(28)+QiMen.DUN  );//"月照端门、"
		if((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==7)
			proc(sb1,sb2,getGjname(29)+QiMen.DUN); //sb.append(getGjname(29)+QiMen.DUN  );//"星见西方、"
		
		//5. 三奇之灵：三奇合四吉神［阴合地天］或合三吉门者，为吉道清灵、用事俱吉。
		if((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI ||
				t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING ||
				t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) &&
				(daoqm.gInt[1][1][gong]==QiMen.SHENYIN || daoqm.gInt[1][1][gong]==QiMen.SHENDI || 
						daoqm.gInt[1][1][gong]==QiMen.SHENHE || daoqm.gInt[1][1][gong]==QiMen.SHENTIAN ) && 
						(daoqm.gInt[3][1][gong]==QiMen.MENKAI || daoqm.gInt[3][1][gong]==QiMen.MENXIU || daoqm.gInt[3][1][gong]==QiMen.MENSHENG)) 
			proc(sb1,sb2,getGjname(30)+QiMen.DUN); //sb.append(getGjname(30)+QiMen.DUN  );//"奇灵、"
		
		//6. 奇游禄位：乙到震、丙到巽、丁到离为本禄之位
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI || d[0]==YiJing.YI || d[1]==YiJing.YI) && gong==3) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING || d[0]==YiJing.BING || d[1]==YiJing.BING) && gong==4) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING || d[0]==YiJing.DING || d[1]==YiJing.DING) && gong==9))
			proc(sb1,sb2,getGjname(31)+QiMen.DUN); //sb.append(getGjname(31)+QiMen.DUN ); //"奇禄、"
		
		//7. 奇仪相合：乙庚、丙辛、丁壬为奇合，戊癸、甲己为仪合，得吉门，凡事有和之象，主和解、了结、平局、平分。
		if(((t[0]==YiJing.YI || t[1]==YiJing.YI) && (d[0]==YiJing.GENG || d[1]==YiJing.GENG)) ||
				((t[0]==YiJing.GENG || t[1]==YiJing.GENG) && (d[0]==YiJing.YI || d[1]==YiJing.YI)) ||
				((t[0]==YiJing.BING || t[1]==YiJing.BING) && (d[0]==YiJing.XIN || d[1]==YiJing.XIN)) ||
				((t[0]==YiJing.XIN || t[1]==YiJing.XIN) && (d[0]==YiJing.BING || d[1]==YiJing.BING)) ||
				((t[0]==YiJing.DING || t[1]==YiJing.DING) && (d[0]==YiJing.REN || d[1]==YiJing.REN)) ||
				((t[0]==YiJing.REN || t[1]==YiJing.REN) && (d[0]==YiJing.DING || d[1]==YiJing.DING)))
			proc(sb1,sb2,getGjname(32)+QiMen.DUN); //sb.append(getGjname(32)+QiMen.DUN  );//"奇合、"
		else if(((t[0]==YiJing.WUG || t[1]==YiJing.WUG) && (d[0]==YiJing.GUI || d[1]==YiJing.GUI)) ||
				((t[0]==YiJing.GUI || t[1]==YiJing.GUI) && (d[0]==YiJing.WUG || d[1]==YiJing.WUG)) ||
				((t[0]==YiJing.JI || t[1]==YiJing.JI) && (gong==zhifugong)) ||
				((gong==zhifugong) && (d[0]==YiJing.JI || d[1]==YiJing.JI)))
			proc(sb1,sb2,getGjname(33)+QiMen.DUN); //sb.append(getGjname(33)+QiMen.DUN  );//"仪合、"
		
		if(w) {
			sb1 = sb1.delete(sb1.length()-1, sb1.length());
			pw.sblack(sb1.toString());
			pw.swhite(speat(COL-sb1.length(),FILL1));
		}
		
		return sb2.toString();
	}
	/**
	 * 输出第四行：八神
	 * 空格smallblack-2,八神bigblack(bigred)-1，空格smallblack-4，天地盘冲合刑smallblack-10
	 */
	public void pLine4(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {
			pw.swhite(speat(COL,FILL1));  //对于五宫，其干与下干、宫的刑冲合桃墓都已反应到天盘所在宫与地盘所在宫了
			return ;
		}
		pw.swhite(speat(LINE4[0],FILL1));
		
		int shen = daoqm.gInt[1][1][gong]; //八神名称
		if(shen == QiMen.SHENFU) {
			if(iszf) pw.bred(QiMen.bs1[shen]);
			else {
				if(daoqm.getJu()>0) pw.bred(QiMen.fpjs1[shen]);
				else pw.bred(QiMen.fpjs2[shen]);
			}
		}	else {
			if(iszf) pw.bblack(QiMen.bs1[shen]);
			else {
				if(daoqm.getJu()>0) pw.bblack(QiMen.fpjs1[shen]);
				else pw.bblack(QiMen.fpjs2[shen]);
			}
		}
		  
		pw.swhite(speat(LINE4[1],FILL1));
		
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(pub.isTDXing(gong)) { //如果天地盘相刑
				str += this.getGjname(40); //"刑";
				i++;
			}
			if(pub.isTDChong(gong)) {  //如果天地盘冲
				str += this.getGjname(41); //"冲";
				i++;
			}
			if(pub.isTDGanHe(gong)) {//如果天地干六合
				str += this.getGjname(42); //"合";
				i++;
			}
			if(pub.isTDG3He(gong)) {//如果天地宫三合
				str += this.getGjname(43); //"匼";
				i++;
			}else if(pub.isTDZi3He(gong)) {//如果天地半合，不存在天地六合
				str += this.getGjname(44); //"峇";
				i++;
			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE4[2]-i,FILL1));	
		pw.bwhite(speat(LINE4[3],FILL1));		
		pw.swhite(speat(LINE4[4],FILL2));
	}

	/**
	 * 输出第七行：
	 * LINE7 = {1,3,1,1,3,1}，FILL1二字符空格，FILL2一字符空格；COL=15
	 * 空格，退星进星，空格，地盘八神，空格，暗干12，空格8个
	 */
	public void pLine7(int gong) throws BadLocationException{
		if(gong==5 && iszf) {
			pw.swhite(speat(2,FILL2));
			pw.swhite(speat(10,FILL1));
			
			if(QiMen2.HUO || QiMen2.ALL) {
				//暗干，幺老师用
				int angan = pub.getAngan(gong);
				pw.sblack(YiJing.TIANGANNAME[angan]);  
				//活干支，活干即暗干，只是丁乙互换；此处只要活支
				if(angan==YiJing.DING) angan = YiJing.YI;
				else if(angan==YiJing.YI) angan = YiJing.DING;
				pw.sblack(YiJing.DIZINAME[pub.getHuozi(angan)]);
			}else
				pw.swhite(speat(2,FILL1));
			
			pw.swhite(speat(1,FILL1));
			pw.swhite(speat(2,FILL2));
			return ;
		}
		//pw.swhite(speat(LINE7[0],FILL1));
		pw.swhite(speat(2,FILL2));
		
		if(QiMen2.JTXMS || QiMen2.ALL) {
			//夭法、退星进星
			pw.sblack(QiMen.jx1[pub.getTuiXing(gong)]);	 
			pw.sblack(QiMen.jx1[pub.getJinXing(gong)]);
			pw.swhite(speat(LINE7[2],FILL1));
			
			//地盘八神，即地盘干在天盘落宫的八神		
			int dpbs = daoqm.gInt[1][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			if(dpbs==QiMen.SHENFU) {
				if(iszf)
					pw.sred(QiMen.bs1[dpbs]);  
				else {
					if(daoqm.getJu()>0)
						pw.sred(QiMen.fpjs1[dpbs]);
					else
						pw.sred(QiMen.fpjs2[dpbs]);
				}
			}	else {			
				if(iszf)
					pw.sblack(QiMen.bs1[dpbs]);	  
				else {
					if(daoqm.getJu()>0)
						pw.sblack(QiMen.fpjs1[dpbs]);
					else
						pw.sblack(QiMen.fpjs2[dpbs]);
				}
			}
			pw.swhite(speat(LINE7[2],FILL1));
			
			//地盘八门
			int dpbm = daoqm.gInt[3][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			int zhishimen = daoqm.gInt[3][1][zhishigong];
			if(zhishimen==dpbm) pw.sred(QiMen.bm1[dpbm]);
			else pw.sblack(QiMen.bm1[dpbm]);		
			pw.sblack(QiMen.bm1[gong]);  //退门、主过去，也即宫之门
			pw.sblack(QiMen.bm1[pub.getYinmen(gong)]);	//隐门，主未来
			pw.swhite(speat(LINE7[3],FILL1));
		}else{
			pw.swhite(speat(9,FILL1));
		}
		
		if(QiMen2.HUO || QiMen2.ALL) {
			//暗干1，张老师用，第五宫没有暗干，因没有门; 
			String angan_z = YiJing.TIANGANNAME[daoqm.gInt[4][5][daoqm.gInt[3][1][gong]]];
			pw.sblack(angan_z==null || angan_z.equals("")?FILL1:angan_z);  
			//暗干2，幺老师用
			int angan = pub.getYaoAngan(gong);
			pw.sblack(YiJing.TIANGANNAME[angan]);  
			//活干支，活干即暗干，只是丁乙互换；此处只要活支
			if(angan==YiJing.DING) angan = YiJing.YI;
			else if(angan==YiJing.YI) angan = YiJing.DING;
			pw.sblack(YiJing.DIZINAME[pub.getHuozi(angan)]);
			//暗干3，王老师阴盘奇门用
			int wangan = pub.getWangAngan(gong);
			pw.sblack(YiJing.TIANGANNAME[wangan]);
		}else
			pw.swhite(speat(4,FILL1));
		
		pw.swhite(speat(0,FILL1));
		pw.swhite(speat(2,FILL2));
	}
	
	/**
	 * 输出第五行：门+天盘干
	 * 八门宫月旺衰smallblack-3，八门bigblack(bigred)-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
	 */
	public void pLine52(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {    //对于中五宫，输出地盘干在天盘落宫的旺衰即可							
			pw.swhite(speat(2,FILL1));
			pw.bwhite(FILL1);
			
			if(QiMen2.WSXQ || QiMen2.ALL) {							
				int tpjgong = daoqm.getTpJigong();	 //天盘干在宫和月令旺衰
				String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][tpjgong];
				int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
				String tganyws = YiJing.WXSQXNAME[itganyws];
				pw.sblack(tgangws+tganyws);  //天干旺衰
			}else{
				pw.swhite(speat(2,FILL1));
			}
			
			pw.bwhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[4], FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}		
  	/////////////////1. 门在宫与月令旺衰/////////////////////
  	if(QiMen2.WSXQ || QiMen2.ALL) {
  		String mengws =  QiMen.men_gong2[daoqm.gInt[3][1][gong]][gong];
    	String menyws =  QiMen.men_yue[daoqm.gInt[3][1][gong]][SiZhu.yz];
  		pw.sblack(mengws+menyws);
  	}
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. 八门名称,bm1与fpjm定义是一致的，不用转换
		int zsgong = getZSgong();		
		if(gong==zhishigong || gong==zsgong) pw.bred(QiMen.bm1[daoqm.gInt[3][1][gong]]);  
		else pw.bblack(QiMen.bm1[daoqm.gInt[3][1][gong]]);
		pw.swhite(speat(LINE5[1],FILL1));
		///////////////////////////////////////////////////
				
		//3. 干在宫与月旺衰				
		int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[2][3][gong]]];
		if(QiMen2.WSXQ || QiMen2.ALL) {
			String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[2][3][gong]][gong];			
			String tganyws = YiJing.WXSQXNAME[itganyws];
			pw.sblack(tgangws+tganyws);
		}	else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));	
		
		//4. 天盘干
		if(daoqm.gInt[2][3][gong]==rg || daoqm.gInt[2][3][gong]==sg) {
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}else{
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}		
		
		//5. 天盘干地支
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[2][3][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}		
		//5. 天盘干寄宫干
		if(daoqm.isTpJigong(gong) && iszf) { //只有转盘才需要寄宫
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//6. 刑冲桃合
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//天地宫三合不用判断了，在前面天地盘支时已判断
				if(pub.isTG6He(gong)) {//是否天盘与宫六合
					i++;
					str += this.getGjname(45); //"冾";
				}else if(pub.isTG3He(gong)) {//是否半合
					i++;
					str += getGjname(46); //"洽";
				}
			}
			if(pub.isJixing(gong)) {//是否是六仪击刑
				i++;
				str += getGjname(40); //"刑";
			}
			if(pub.isTGChong(gong)) {
				i++;
				str += getGjname(41); //"冲";
			}
			if(pub.isTpTaohua(gong)) {
				i++;
				str += getGjname(47); //"桃";
			}
//			if(pub.isTGanMu(gong)) {
//				i++;  //旺1相2  休3囚4死5
//				str += itganyws<=2 ? getGjname(49):getGjname(48); //"库":"墓";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
		pw.swhite(speat(LINE5[5],FILL2));
	}
	
	//星+天盘干
	public void pLine5(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {    //对于中五宫，输出地盘干在天盘落宫的旺衰即可							
			/////////////1. 九星旺衰///////////////////////////
			int jigong2 = daoqm.getJiGong();
	  	String xinggws =  QiMen.xing_gong[5][jigong2];
	  	String xingyws =  QiMen.xing_yue[5][SiZhu.yz];
	  	if(QiMen2.WSXQ || QiMen2.ALL) 
	  		pw.sblack(xinggws+xingyws);  //输出天禽星旺衰
	  	else
	  		pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[0],FILL1));  
			//2. 九星
			pw.bblack(QiMen.jx1[5]);  //天禽星
			//////////////////////////////////////

			if(QiMen2.WSXQ || QiMen2.ALL) {							
				int tpjgong = daoqm.getTpJigong();	 //天盘干在宫和月令旺衰
				String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][tpjgong];
				int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
				String tganyws = YiJing.WXSQXNAME[itganyws];
				pw.sblack(tgangws+tganyws);  //天干旺衰
			}else{
				pw.swhite(speat(2,FILL1));
			}
			
			pw.bwhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[4], FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}		
		///////////////1. 九星旺衰///////////////////////////////
  	String xinggws =  QiMen.xing_gong[daoqm.gInt[2][1][gong]][gong];
  	String xingyws =  QiMen.xing_yue[daoqm.gInt[2][1][gong]][SiZhu.yz];  	
  	if(QiMen2.WSXQ || QiMen2.ALL)
  		pw.sblack(xinggws+xingyws);  //星在宫与月令旺衰
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. 九星
		pw.bblack(QiMen.jx1[daoqm.gInt[2][1][gong]]);  //九星
		pw.swhite(speat(LINE5[1],FILL1));
		//////////////////////////////////////////////////////
				
		//3. 干在宫与月旺衰				
		int itganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[2][3][gong]]];
		if(QiMen2.WSXQ || QiMen2.ALL) {
			String tgangws = QiMen.gan_gong_wx2[daoqm.gInt[2][3][gong]][gong];			
			String tganyws = YiJing.WXSQXNAME[itganyws];
			pw.sblack(tgangws+tganyws);
		}	else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));	
		
		//4. 天盘干
		if(daoqm.gInt[2][3][gong]==rg || daoqm.gInt[2][3][gong]==sg) {
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}else{
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[2][3][gong]]);
		}		
		
		//5. 天盘干地支
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[2][3][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}		
		//5. 天盘干寄宫干
		if(daoqm.isTpJigong(gong) && iszf) { //只有转盘才需要寄宫
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//6. 刑冲桃合
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//天地宫三合不用判断了，在前面天地盘支时已判断
				if(pub.isTG6He(gong)) {//是否天盘与宫六合
					i++;
					str += this.getGjname(45); //"冾";
				}else if(pub.isTG3He(gong)) {//是否半合
					i++;
					str += getGjname(46); //"洽";
				}
			}
			if(pub.isJixing(gong)) {//是否是六仪击刑
				i++;
				str += getGjname(40); //"刑";
			}
			if(pub.isTGChong(gong)) {
				i++;
				str += getGjname(41); //"冲";
			}
			if(pub.isTpTaohua(gong)) {
				i++;
				str += getGjname(47); //"桃";
			}
//			if(pub.isTGanMu(gong)) {
//				i++;  //旺1相2  休3囚4死5
//				str += itganyws<=2 ? getGjname(49):getGjname(48); //"库":"墓";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
		pw.swhite(speat(LINE5[5],FILL2));
	}
	/**
	 * 输出第六行：门+地盘干
	 */
	public void pLine6(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {  //对于中五宫，要特殊处理					
			pw.swhite(speat(2,FILL1));
			pw.bwhite(FILL1);
			
			//3. 地盘干在寄宫和月令旺衰
			int jigong = daoqm.getJiGong();
	  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][jigong];
	  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
			String dganyws = YiJing.WXSQXNAME[idganyws];
			if(QiMen2.WSXQ || QiMen2.ALL)
				pw.sblack(dgangws+dganyws);   //输出中五宫地盘干旺衰
			else
				pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[2],FILL1));

			//4. 地盘干
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			
			//5. 地盘干地支
			if(QiMen2.HUO || QiMen2.ALL) {
				int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
				pw.sblack(YiJing.DIZINAME[huozi]);
			}else
				pw.swhite(speat(1,FILL1));
				
			pw.bwhite(speat(1,FILL1));  //输出一个大空格
			pw.swhite(speat(LINE5[3]+LINE5[4]-1,FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}
		  	
		/////////////////1. 门在宫与月令旺衰/////////////////////
  	if(QiMen2.WSXQ || QiMen2.ALL) {
  		String mengws =  QiMen.men_gong2[daoqm.gInt[3][1][gong]][gong];
    	String menyws =  QiMen.men_yue[daoqm.gInt[3][1][gong]][SiZhu.yz];
  		pw.sblack(mengws+menyws);
  	}
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. 八门名称,bm1与fpjm定义是一致的，不用转换
		int zsgong = getZSgong();		
		if(gong==zhishigong || gong==zsgong) pw.bred(QiMen.bm1[daoqm.gInt[3][1][gong]]);  
		else pw.bblack(QiMen.bm1[daoqm.gInt[3][1][gong]]);
		//System.out.println("QimenGejuBase: gong="+gong+"men="+daoqm.gInt[3][1][gong]);
		pw.swhite(speat(LINE5[1],FILL1));
		///////////////////////////////////////////////////
		
  	//3. 地盘干在宫和月令旺衰
  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][gong]][gong];
  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][gong]]];
		String dganyws = YiJing.WXSQXNAME[idganyws];
		if(QiMen2.WSXQ || QiMen2.ALL)
			pw.sblack(dgangws+dganyws);			//地盘干在宫与月令旺衰
		else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));
		
		//4. 地盘干
		if(daoqm.gInt[4][5][gong]==rg || daoqm.gInt[4][5][gong]==sg)
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		else
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		
		//5. 地盘干地支
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}
		
		//6. 是否有寄宫地盘干	
		if(daoqm.isJiGong(gong) && iszf) {  //只有转盘才寄宫
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//7. 刑冲合
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//天地宫三合不用判断了，在前面天地盘支时已判断
				if(pub.isDG6He(gong)) {//是否地盘与宫六合
					i++;
					str += getGjname(45); //"冾";
				}else if(pub.isDG3He(gong)) {//是否半合
					i++;
					str += getGjname(46); //"洽";
				}
			}
			if(pub.isDpJixing(gong)) {//是否是六仪击刑
				i++;
				str += getGjname(40); //"刑";
			}
			if(pub.isDGChong(gong)) {
				i++;
				str += getGjname(41); //"冲";
			}
			if(pub.isDpTaohua(gong)) {
				i++;
				str += getGjname(47); //"桃";
			}
//			if(pub.isDGanMu(gong)) {
//				i++;
//				str += idganyws<=2 ? getGjname(49):getGjname(48); //"库":"墓";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
//		if(i<LINE5[4]) {//太多了，多于4个字符时，FILL2就要缩小了
//			pw.swhite(speat(LINE5[5],FILL2));
//		}else{
//			pw.swhite(speat(2,FILL2));
//		}
		pw.swhite(speat(LINE5[5],FILL2));
		//System.out.println("gong="+gong+";i="+i);
	}
	
	//星+地盘干
	public void pLine62(int gong) throws BadLocationException{
		int i=0;
		String str = "";
		if(gong==5 && iszf) {  //对于中五宫，要特殊处理					
			
	  	/////////////1. 九星旺衰///////////////////////////
			int jigong2 = daoqm.getJiGong();
	  	String xinggws =  QiMen.xing_gong[5][jigong2];
	  	String xingyws =  QiMen.xing_yue[5][SiZhu.yz];
	  	if(QiMen2.WSXQ || QiMen2.ALL) 
	  		pw.sblack(xinggws+xingyws);  //输出天禽星旺衰
	  	else
	  		pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[0],FILL1));  
			//2. 九星
			pw.bblack(QiMen.jx1[5]);  //天禽星
			//////////////////////////////////////
			
			//3. 地盘干在寄宫和月令旺衰
			int jigong = daoqm.getJiGong();
	  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][5]][jigong];
	  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][5]]];
			String dganyws = YiJing.WXSQXNAME[idganyws];
			if(QiMen2.WSXQ || QiMen2.ALL)
				pw.sblack(dgangws+dganyws);   //输出中五宫地盘干旺衰
			else
				pw.swhite(speat(2,FILL1));
			pw.swhite(speat(LINE5[2],FILL1));

			//4. 地盘干
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			
			//5. 地盘干地支
			if(QiMen2.HUO || QiMen2.ALL) {
				int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
				pw.sblack(YiJing.DIZINAME[huozi]);
			}else
				pw.swhite(speat(1,FILL1));
				
			pw.bwhite(speat(1,FILL1));  //输出一个大空格
			pw.swhite(speat(LINE5[3]+LINE5[4]-1,FILL1));
			pw.swhite(speat(LINE5[5],FILL2));
			return ;
		}
		
  	///////////////1. 九星旺衰///////////////////////////////
  	String xinggws =  QiMen.xing_gong[daoqm.gInt[2][1][gong]][gong];
  	String xingyws =  QiMen.xing_yue[daoqm.gInt[2][1][gong]][SiZhu.yz];  	
  	if(QiMen2.WSXQ || QiMen2.ALL)
  		pw.sblack(xinggws+xingyws);  //星在宫与月令旺衰
  	else
  		pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[0],FILL1));		
		//2. 九星
		pw.bblack(QiMen.jx1[daoqm.gInt[2][1][gong]]);  //九星
		pw.swhite(speat(LINE5[1],FILL1));
		//////////////////////////////////////////////////////
		
  	//3. 地盘干在宫和月令旺衰
  	String dgangws = QiMen.gan_gong_wx2[daoqm.gInt[4][5][gong]][gong];
  	int idganyws = YiJing.WXSQX[SiZhu.yz][YiJing.TIANGANWH[daoqm.gInt[4][5][gong]]];
		String dganyws = YiJing.WXSQXNAME[idganyws];
		if(QiMen2.WSXQ || QiMen2.ALL)
			pw.sblack(dgangws+dganyws);			//地盘干在宫与月令旺衰
		else
			pw.swhite(speat(2,FILL1));
		pw.swhite(speat(LINE5[2],FILL1));
		
		//4. 地盘干
		if(daoqm.gInt[4][5][gong]==rg || daoqm.gInt[4][5][gong]==sg)
			pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		else
			pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][gong]]);
		
		//5. 地盘干地支
		if(QiMen2.HUO || QiMen2.ALL) {
			int huozi = pub.getHuozi(daoqm.gInt[4][5][gong]);
			pw.sblack(YiJing.DIZINAME[huozi]);
			i++;
		}
		
		//6. 是否有寄宫地盘干	
		if(daoqm.isJiGong(gong) && iszf) {  //只有转盘才寄宫
			if(daoqm.gInt[4][5][5]==rg || daoqm.gInt[4][5][5]==sg)
				pw.bblue(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
			else
				pw.bblack(YiJing.TIANGANNAME[daoqm.gInt[4][5][5]]);
		}else {
			pw.bwhite(speat(1,FILL1));
		}
		pw.swhite(speat(LINE5[3],FILL1));
		
		//7. 刑冲合
		if(QiMen2.XCHM || QiMen2.ALL) {
			if(!pub.isTDG3He(gong)) {//天地宫三合不用判断了，在前面天地盘支时已判断
				if(pub.isDG6He(gong)) {//是否地盘与宫六合
					i++;
					str += getGjname(45); //"冾";
				}else if(pub.isDG3He(gong)) {//是否半合
					i++;
					str += getGjname(46); //"洽";
				}
			}
			if(pub.isDpJixing(gong)) {//是否是六仪击刑
				i++;
				str += getGjname(40); //"刑";
			}
			if(pub.isDGChong(gong)) {
				i++;
				str += getGjname(41); //"冲";
			}
			if(pub.isDpTaohua(gong)) {
				i++;
				str += getGjname(47); //"桃";
			}
//			if(pub.isDGanMu(gong)) {
//				i++;
//				str += idganyws<=2 ? getGjname(49):getGjname(48); //"库":"墓";
//			}
		}
		pw.sblack(str);
		pw.swhite(speat(LINE5[4]-i, FILL1));
		pw.swhite(speat(LINE5[5],FILL2));
	}
	

	
	/**
	 * 循环输出几个空格或指定的字符
	 */
	String speat(int len, String fillstr) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<len; i++) sb.append(fillstr);
		
		return sb.toString();
	}
	
	int zhishigong ,zhifugong;  //值使、值符宫
	int ng ,yg, rg, sg;  //去掉甲开头后的转换年干
	boolean isYang;  //是阳局还是阴局
	
	ResultPanel resultPane;  //展现结果的面板
	MyTextPane text;				 //显示具体的排盘信息
	Document doc ;					 //显示结构的文档
	PrintWriter pw ;
	
	void init() throws BadLocationException {
		text = resultPane.getTextPane();
		//text.setEditable(false);
		doc = text.getDocument();
		doc.remove(0, doc.getLength());
		
		zhifugong = daoqm.getZhifuGong();
		zhishigong = daoqm.getZhishiGong();  //值使、值符宫
		ng = pub.getTiangan(SiZhu.ng, SiZhu.nz);  //去掉甲开头后的转换年干
		yg = pub.getTiangan(SiZhu.yg, SiZhu.yz);
		rg = pub.getTiangan(SiZhu.rg, SiZhu.rz);
		sg = pub.getTiangan(SiZhu.sg, SiZhu.sz);
		
		isYang = daoqm.getJu()>0;
		pw = new PrintWriter();
		pw.setDocument(doc);
		pub.initGlobal();  //优化全局变量的访问速度
	}
	/**
	 * 由格局数得到格局的简短名称，如果简短名称2不存在，则直接使用1
	 * @param ge
	 * @return
	 */
	String getGjname(int ge) {
		//如果只有二位长度，或三位长度但为Null
		return QiMen.gGejuDesc[ge].length==2 || QiMen.gGejuDesc[ge][2]==null ? QiMen.gGejuDesc[ge][0] :QiMen.gGejuDesc[ge][2];
	}
	//得到值使宫
	int getZSgong() {
		int zsgong = zhishigong;
		if(iszf && zhishigong==5) zsgong = daoqm.getJiGong(); //转盘有寄宫
		return zsgong;
	}
}