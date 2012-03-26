package org.boc.ui.qm;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QiMen2;
import org.boc.db.qm.QimenGeju1;
import org.boc.db.qm.QimenPublic;
import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.PrintWriter;

public class Tip {
	private MyTextPane textPane;
	private StyledDocument styledDoc; // 文本框的文档
	private QiMenFrame frame;
	private QimenPublic pub;
	private DaoQiMen daoqm;
	private final String sReturn = System.getProperty("line.separator"); // 换行符
	private HtmlMultiLineControl html;

	public Tip(MyTextPane textPane, BasicJPanel frame) {
		this.textPane = textPane;
		this.frame = (QiMenFrame) frame;
		html = new HtmlMultiLineControl();
	}

	/**
	 * 得到提示信息，必须指着第一个字符，一次取4个字符，因为最长的才4个字啊
	 * 
	 * @param e
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String getToolTip(MouseEvent e) {
		if (pub == null) {
			pub = this.frame.getDelQiMenMain().getQimenPublic();
			daoqm = pub.getDaoQiMen();
		}

		Point p = e.getPoint();
		int pos = textPane.viewToModel(p);
		String rs = null;
		if (styledDoc == null)
			styledDoc = (StyledDocument) textPane.getDocument();
		((AbstractDocument) styledDoc).readLock();
		AttributeSet as = null;
		try {
			as = styledDoc.getCharacterElement(pos).getAttributes();
		} finally {
			((AbstractDocument) styledDoc).readUnlock();
		}

		Enumeration en = as.getAttributeNames();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (key.toString().equals("foreground"))
				if (as.getAttribute(key).equals(Color.WHITE))
					return null;
				else
					break;
		}

		try {
			rs = getTipinfo(pos, as);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
//		if(rs!=null && rs.trim().equals("null")) rs = null;
//		System.out.println(rs);
		return rs;
	}

	/**
	 * 算法：要自动断词句 305-502 710-905 1114-1310之间的区断只取一个字就行
	 * 与行首表头的字数有关系，所以表头字数要固定
	 */
	private String geyKey(int pos, AttributeSet as) throws BadLocationException {
		if (pos < 10)
			return null; // 小于4不好取数会报错
		//System.out.println(pos);
		
		//如果是大字体，只返回当前的一个字符
		Enumeration en = as.getAttributeNames();	
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					return styledDoc.getText(pos, 1);
				}
				break;
			}
		}
		//如果任意变了，此时只取一个字符
		if(QiMen2.XMHW==100) {
			return styledDoc.getText(pos, 1);
		}
		if ((pos > 305 && pos < 502) || (pos > 710 && pos < 905) || (pos > 1114 && pos < 1310)) {
			return styledDoc.getText(pos, 1);
		}

		StringBuilder sb = new StringBuilder();
		String[] splits = { "│", "┊", "、", QimenGeju1.FILL1, QimenGeju1.FILL2 };
		for (int i = 0; i < 4; i++) {// 向前取4个字符
			String tmp = null;
			try {
				tmp = styledDoc.getText(pos + i, 1);
			} catch (Exception e) {
				return null;
			}
			boolean isSame = false;
			for (String split : splits) {
				if (tmp.equals(split)) {
					isSame = true;
					break;
				}
			}
			if (!isSame)
				sb.append(tmp);
			else
				break;
		}

		for (int i = 1; i <= 4; i++) {// 向后取4个字符
			String tmp = styledDoc.getText(pos - i, 1);
			boolean isSame = false;
			for (String split : splits) {
				if (tmp.equals(split)) {
					isSame = true;
					break;
				}
			}
			if (!isSame)
				sb.insert(0, tmp);
			else
				break;
		}

		return sb.toString();
	}
	
	/**
	 * 所有的大字体分别是下面的。 9+8+8+9=34
	 */
	public static final String[] BIGWORD = {"符","蛇","阴","合","虎","武","地","天",
			"蓬", "芮", "冲", "辅", "禽", "心", "柱", "任", "英",
			"休", "死", "伤", "杜","死", "开", "惊", "生", "景",
			"戊", "己", "庚", "辛", "壬", "癸", "丁", "丙", "乙"};
	/**
	 * 所有水红色字体对应的pos的值，分别属于第几宫
	 */
	public static final int[] REDWORD = {0,979,184,557,152,0,995,589,963,168};
	@SuppressWarnings("unchecked")
	/**
	 * 提示的先后顺序为：
	 * 1. 显示格局的时间头部信息
	 * 2. 首先看最大号字体，其对应的提示信息
	 * 3. 判断是否是水红色字体，显示八宫的详细信息
	 * 4. 判断是否是吉凶格、十干克应
	 * 5. 判断是否是退星进星隐门
	 * 6. 判断是否是十二神将
	 * 7. 活干支与纳音
	 * 8. 刑冲合墓库桃马空驲昳
	 */
	private String getTipinfo(int pos, AttributeSet as)
			throws BadLocationException {
		//1. 如果是表头部分，则显示详细的局信息
		//System.out.println(pos);
		if(pos>=3 && pos<=6) {
			String t = null;
			try{t = styledDoc.getText(3, 3);}catch(Exception e) {
				e.printStackTrace();
			}
			//if(t!=null && t.indexOf("")!=-1)
				return html.CovertDestionString(getDateHead());  //此时会先起局，然后再输出头部
		}
			
		String key = geyKey(pos, as);
		if (key == null || key.trim().length() == 0)
			return null;
		
		//System.out.println("-- pos="+pos+";key="+key);

		// 2. 最大号字体，判断是否是六乙到乾、门加门、门加天盘干/地盘干、星加门、神加门
		Enumeration en = as.getAttributeNames();
		boolean isBigFont = false;		
		while (en.hasMoreElements()) {
			Object k1 = en.nextElement();
			if (k1.toString().equals("size")) {
				if (new Integer(as.getAttribute(k1).toString()).intValue() == PrintWriter.BIG) {
					isBigFont = true;
				}
				break;
			}
		}
		if (isBigFont) {  //只有大字体才是的，即只有八门、九星、八神、十天干才是大字体
			//System.out.println(pos+";key="+key);
			for (int i = 0; i <BIGWORD.length; i++) {
				if (BIGWORD[i].equals(key)){
					//System.out.println("isbigfont pos="+pos+";key="+key);
					return html.CovertDestionString(getDesc(pos, as, key));
				}										
			}
		}
		
		//3. 判断是否是水红色字体，显示八宫的详细信息
		int gong = 0; //当前第几宫
//		boolean isRed = false;
//		en = as.getAttributeNames();
//		while (en.hasMoreElements()) {
//			Object k1 = en.nextElement();
//			if (k1.toString().equals("foreground")) {
//				if (as.getAttribute(k1) == Color.MAGENTA) {					
//					isRed = true;
//				}
//				break;
//			}
//		}
//		if(isRed) {
			for(int i = 1; i<REDWORD.length; i++) {
				if(REDWORD[i]==pos) {
					gong = i; 
					return html.CovertDestionString(QiMen.JIUGONGINFO[gong][0]+":"+QiMen.JIUGONGINFO[gong][1]);
				}
			}
//		}
		
		
		// 4. 判断是否是吉凶格、十干克应，这些关键字长度都大于1
		String name;
		if(key.length()>1) {
			for (int i = 0; i < QiMen.gGejuDesc.length; i++) {
				if (i >= 140 && i <= 417)
					continue; // 属于第一部分的内容，即大号字体的内容
				if (QiMen.gGejuDesc[i] == null || QiMen.gGejuDesc[i][0] == null)
					continue;
				name = QiMen.gGejuDesc[i].length == 3 ? QiMen.gGejuDesc[i][2]
						: QiMen.gGejuDesc[i][0];
				if (name.equals(key))
					return html.CovertDestionString(QiMen.gGejuDesc[i][0] + ": " + QiMen.gGejuDesc[i][1]);
			}
		}
		
		//5. 是否是退星进星、地八神、地八门退门隐门、暗干12;  第一排448-504; 第二排854-910; 第三排1259-1315;
		String t = getLine7Desc(pos);
		if(t!=null) {
			return html.CovertDestionString(t);
		}
		
		//System.out.println(key);		
		//6. 十二神将 
		for(int i=1; i<=12; i++) {			
			if(QiMen2.SHENJ[i][2].equals(key)) {
				return html.CovertDestionString(QiMen2.SHENJ[i][0] + ": " + QiMen2.SHENJ[i][1]);
			}
		}
		
		//7. 活干支与纳音
		for(int i=1; i<YiJing.DIZINAME.length; i++) {
			if(key.equals(YiJing.DIZINAME[i])) {
				//i = i==2?4 : (i==4? 2 : i);  
				int hg = pub.getHuogan(i);  //i即地支序数，由地支得到活干
				
				String rs = null;
				if(hg==YiJing.YI) {//丁即乙，乙即丁，此处得到的活干，不用再换了。
					//hg = YiJing.DING;
					rs="暗干为丁，活干为乙；<BR>";
				} else if(hg==YiJing.DING) {
					//hg = YiJing.YI;
					rs="暗干为乙，活干为丁；<BR>";
				}else rs = "";
				rs += YiJing.TIANGANNAME[hg]+YiJing.DIZINAME[i]+
				":"+SiZhu.NAYIN[hg][i]+"";
				return html.CovertDestionString(rs);
			}
		}
		
		//8. 是否是制迫和义墓、刑冲合墓库桃、马空驲昳
		for (int i = 0; i < QiMen.gGejuDesc.length; i++) {
			if ((i >= 450 && i <= 466) ||
					(i >= 36 && i <= 49)) {
				if (QiMen.gGejuDesc[i] == null || QiMen.gGejuDesc[i][0] == null)
					continue;
				name = QiMen.gGejuDesc[i].length == 3 ? QiMen.gGejuDesc[i][2]
						: QiMen.gGejuDesc[i][0];
				if (name.equals(key))
					return html.CovertDestionString(QiMen.gGejuDesc[i][0] + ": " + QiMen.gGejuDesc[i][1]);
			}
		}
		return null;
	}
	
	//输出详细的时间头部信息
	private String getDateHead() {
    //输出时间信息
		if(frame.getYear()==0) {//为干支形式
			return daoqm.getHead2(frame.getZhuanFei(), frame.getCaiYun(), 
	    		frame.getXiaozhifu(), frame.getJu());
		}
		daoqm.getDaoCalendar().calculate(frame.getYear(), 
				frame.getMonth(), frame.getDay(), frame.getHour(), frame.getMinute(), 
				frame.isYinli(), frame.isYun(), frame.getProvince(), frame.getCity());
    SiZhu.ISMAN = frame.isBoy();
    return daoqm.getHead1(frame.getZhuanFei(), frame.getCaiYun(), 
    		frame.getXiaozhifu(), frame.getJu());
	}

	/**
	 * 返回gong、星｜门｜天盘干｜地盘干序号、类型
	 * 
	 * @param pos
	 * @param as
	 * @param s
	 * @throws BadLocationException
	 */
	private String getDesc(int pos, AttributeSet as, String s)
			throws BadLocationException {
		int xmg = 0; // 星｜门｜天盘干｜地盘干｜八神序号
		int gong = 0; // 哪一宫
		int type = 0; // 哪种类型，1星 2门 3天盘干 4地盘干5神
		String rs = null;

		// 1. 是否是天盘干或地盘干
		for (int i = 1; i <= 10; i++) {
			if (s.equals(YiJing.TIANGANNAME[i])) {
				xmg = i;
				boolean isMen = false;
				String men = null;
				//1. 左取第二位或第一位是天干（天盘干二个），再左取第3位如果是门则必是天盘干，要看是否加了活支									
				int loc1 = QiMen2.HUO || QiMen2.ALL ? 2 : 1;
				int loc2 = QiMen2.HUO || QiMen2.ALL ? 5 : 4;
				boolean isgan = false;
				String gan = styledDoc.getText(pos - loc1, 1);
				for (int j = 1; j <= 10; j++) {
					if (gan.equals(YiJing.TIANGANNAME[j])) {
						isgan = true;
						break;
					}
				}
				if(isgan) {
					men = styledDoc.getText(pos - loc2, 1);
					for (int j = 0; j < QiMen.bm1.length; j++) {
						if (men.equals(QiMen.bm1[j])) {
							isMen = true;
							break;
						}
					}
				}
				
				//1.1 左取第3位如果是门则必是天盘干,用于判断落宫用。如果左起二位是天干，就不用判断左起三位是否是门了
				if (!isgan && !isMen) {	
					men = styledDoc.getText(pos - 3, 1);
					// System.out.println("men="+men);
					for (int j = 0; j < QiMen.bm1.length; j++) {
						if (men.equals(QiMen.bm1[j])) {						
							isMen = true ;
							break;
						}
					}
				}
				
				//1/2/3的值时是52/62组合，即门+天盘干，否则不是门但为054的值也是天盘干
				type = ((isMen && QiMen2.XMHW>0 && QiMen2.XMHW<4) ||
								(!isMen && (QiMen2.XMHW<1 || QiMen2.XMHW>3))) ? 3 : 4;
				//System.out.println(s+"; isMen="+isMen+"\tQiMen2.XMHW="+QiMen2.XMHW+"; type="+type);
				break;
			}
		}
		// 2.是否是九星
		if (type == 0) {
			// 先将星名称翻译成星的序号
			for (int i = 0; i < QiMen.jx1.length; i++) {
				if (s.equals(QiMen.jx1[i])) {
					xmg = i;
					type = 1;
					break;
				}
			}
		}
		// 3. 是否是八门
		if (type == 0) {
			for (int i = 0; i < QiMen.bm1.length; i++) {
				if (s.equals(QiMen.bm1[i])) {
					xmg = i;
					type = 2;
					break;
				}
			}
		}
		//4. 是否是八神
		if (type == 0) {
			// 先将神名称翻译成神的序号
			for (int i = 0; i < QiMen.bs1.length; i++) {
				if (s.equals(QiMen.bs1[i])) {
					xmg = i;
					type = 5;
					break;
				}
			}
		}
		if (type == 0)
			return null;		

		// 4. 得到宫
		if (type == 1)
			gong = pub.getXingGong(xmg);
		else if (type == 2)
			gong = pub.getMenGong(xmg);
		else if (type == 3)
			gong = pub.getTianGong(xmg, 0);
		else if (type == 4)
			gong = pub.getDiGong(xmg, 0);
		else if (type == 5)
			gong = pub.getShenGong(xmg);
		
		//System.out.println("text="+s+";xmg="+xmg+";gong="+gong+";type="+type);
		
		if (gong == 0)
			return null;

		// 5. 当指向天盘星时：天盘星+天盘门
		//System.out.println(pos+";key="+s+";type="+type+";gong="+gong);
		int ge, ge1;
		if (type == 1 && gong != 0) {
			ge = QiMen.xing_men[xmg][QiMen.dp_menxing_mc[daoqm.gInt[3][1][gong]]];
			if(ge!=0) {
				rs = QiMen.gGejuDesc[ge][0] + ":" + QiMen.gGejuDesc[ge][1];
				rs += "\r\n";
			}else 
				rs = "";
			rs += "天"+QiMen.jx1[xmg]+"值"+YiJing.DIZINAME[SiZhu.sz]+"时："+QiMen2.JXZS[xmg][SiZhu.sz];
			return html.CovertDestionString(rs);
		}
		// 6. 当指向人盘门时： 天盘门+地盘门
		if (type == 2 && gong != 0) {
			ge = QiMen.men_men[xmg][QiMen.dp_mengong_mc[gong]]; // daoqm.gInt[4][4][gong]
			if(ge==0) return null;
			rs = QiMen.gGejuDesc[ge][0] + ":" + QiMen.gGejuDesc[ge][1];
			return html.CovertDestionString(rs);
		}
		// 7. 当指向天/地盘干时： 天盘门+天盘干/地盘干 ； 天盘三奇+宫
		if ((type == 3 || type == 4) && gong != 0) {
			ge = QiMen.men_gan[daoqm.gInt[3][1][gong]][xmg];			
			ge1 = QiMen.gan_gong[xmg][gong];
			if(ge==0) return null;
			rs = QiMen.gGejuDesc[ge][0]+ ":" + QiMen.gGejuDesc[ge][1]
					+ (ge1 == 0 ? "" : "<BR>"+QiMen.gGejuDesc[ge1][0] + ":" + QiMen.gGejuDesc[ge1][1]);
			
			return html.CovertDestionString(rs);
		}
		// 8. 当指向八神时： 八神+人盘门 ；
		if (type == 5 && gong != 0) {
			ge = QiMen.shen_men[xmg][QiMen.dp_menxing_mc[daoqm.gInt[3][1][gong]]]; 
			if(ge==0) return null;
			rs = QiMen.gGejuDesc[ge][0] + ":" + QiMen.gGejuDesc[ge][1];
			return html.CovertDestionString(rs);
		}
		
		return rs;
	}
	
	private int[] tuixing = {0,1280,487,857,451,0,1298,893,1262,469};  //退星,一、二、三、...宫
	private final int[] steps = {0,1,3,5,6,7,9,10,12};  //活支与纳音单独拿出来了,不在这里判断，详270行
//	private final int jxstep = 1;  		//进星，与退星相距1；
//	private final int dpbsstep = 3; 	//地盘八神，与退星距3
//	private final int dpbmstep = 5;		//地盘八门
//	private final int tmstep = 6; 		//退门，即原门
//	private final int ymstep = 7; 		//隐门
//	private final int agzstep = 9;		//张氏暗干
//	private final int agystep = 10;		//幺法暗干
//	private final int aghstep = 11;		//金亮奇门，活干支
//  private final int agwystep = 12;		//阴盘奇门暗干		
	public String getLine7Desc(int pos) {
//		四452/453,455,457/458/459,461/462  || 九470,二488
//		三858,七894,八1263，一1281，六1299/ 五886；		
		int gong = 0;  //得到当前指向哪一宫
		int type = -1;  //得到当前要访问的类型0退星；1进星；3地盘神；5地盘门；6退门；7隐门；9张氏暗干；10幺法暗干
		for(int i=1; i<tuixing.length; i++) {
			if(i==5) continue;  //第五宫，此处不能按加多少判断，跳过
			for(int j=0; j<steps.length; j++)
			if(pos==tuixing[i]+steps[j]) {
				gong = i;
				type = steps[j];
				break;
			}
		}
		//System.out.println("pos="+pos+";gong="+gong+";type="+type);
		if(pos==885) {type=10;gong=5;}  //第五宫幺法暗干
		if(gong==0 || type==-1) return null;
		String rs = null;
		int x,xg,ge;
		switch(type){
		case 0: //退星
			x = pub.getTuiXing(gong);
			xg = pub.getXingGong(x);
			rs = "退星［"+QiMen.jx1[x]+"］现落"+xg+"宫，主过去之事；";
			break;
		case 1: //进星
			x = pub.getJinXing(gong);
			xg = pub.getXingGong(x);
			rs = "进星［"+QiMen.jx1[x]+"］现落"+xg+"宫，主未来之事；";
			break;
		case 3: //地盘神
			x = daoqm.gInt[1][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			xg = pub.getShenGong(x);
			rs = "地盘神［"+QiMen.bs1[x]+"］现落"+xg+"宫，主过去之事；";
			break;
		case 5: //地盘门
			x = daoqm.gInt[3][1][pub.getTianGong(daoqm.gInt[4][5][gong], 0)];
			xg = pub.getMenGong(x);
			rs = "地盘门［"+QiMen.bm1[x]+"］现落"+xg+"宫，主过去之事；";
			break;
		case 6: //退门
			x = gong;
			xg = pub.getMenGong(x);
			rs = "退门［"+QiMen.bm1[x]+"］现落"+xg+"宫，主过去之事；";
			break;
		case 7: //隐门
			x = pub.getYinmen(gong);
			xg = pub.getMenGong(x);
			rs = "隐门［"+QiMen.bm1[x]+"］现落"+xg+"宫，主未来之事；";
			break;
		case 9: //张氏暗干
			x = daoqm.gInt[4][5][daoqm.gInt[3][1][gong]];
			ge = QiMen.gan_gan[x][daoqm.gInt[4][5][gong]];
			xg = pub.getTianGong(x, 0);
			rs = "张氏暗干［"+YiJing.TIANGANNAME[x]+"］落"+xg+"宫；<BR>"+
				QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1];
			break;
		case 10: //要法暗干
			x = pub.getAngan(gong);
			//int jg = gong==5?daoqm.getTpJigong() : gong;  //中五宫在天盘所寄之宫
			int jg = gong;  //中五宫也用中五宫的地判干
			//System.out.println("jg="+jg+";gong="+gong);
			ge = QiMen.gan_gan[x][daoqm.gInt[4][5][jg]];
			xg = pub.getTianGong(x, 0);
			rs = "幺法暗干［"+YiJing.TIANGANNAME[x]+"］落"+xg+"宫；<BR>"+
			QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1];
			break;
//		case 11: //活支
//			x = pub.getAngan(gong);
//			x = x==2?4 : (x==4? 2 : x);  //丁即乙，乙即丁
//			xg = pub.getHuozi(x);
//			rs = YiJing.TIANGANNAME[x]+YiJing.DIZINAME[xg];
//			break;
		case 12: //阴盘暗干
			x = pub.getWangAngan(gong);
			ge = QiMen.gan_gan[x][daoqm.gInt[4][5][gong]];
			xg = pub.getTianGong(x, 0);
			rs = "阴盘暗干［"+YiJing.TIANGANNAME[x]+"］落"+xg+"宫；<BR>"+
			QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1];
			break;
		default:
			break;
		}
		return rs;
	}
}
