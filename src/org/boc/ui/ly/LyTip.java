package org.boc.ui.ly;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.ly.Liuyao;
import org.boc.ui.BasicJPanel;
import org.boc.ui.MyTextPane;
import org.boc.util.HtmlMultiLineControl;
import org.boc.util.PrintWriter;

public class LyTip {
	private MyTextPane textPane;
	private StyledDocument styledDoc; // 文本框的文档
	private LiuYaoFrame frame;
	private LiuyaoPublic pub;
	private DaoYiJingMain daoly;
	private final String sReturn = System.getProperty("line.separator"); // 换行符
	private HtmlMultiLineControl html;

	public LyTip(MyTextPane textPane, BasicJPanel frame) {
		this.textPane = textPane;
		this.frame = (LiuYaoFrame) frame;
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
			pub = this.frame.getDelYiJingMain().getLiuyaoPublic();
			daoly = pub.getDaoYiJingMain();
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

		if ((pos > 305 && pos < 502) || (pos > 710 && pos < 905) || (pos > 1114 && pos < 1310)) {
			return styledDoc.getText(pos, 1);
		}

		StringBuilder sb = new StringBuilder();
		String[] splits = { "│", "┊", "、", Liuyao.FILL1, Liuyao.FILL2 };
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
					//return html.CovertDestionString(getDesc(pos, as, key));
				}										
			}
		}
		
		return null;
	}
	
	//输出详细的时间头部信息
	private String getDateHead() {
    //输出时间信息
		return "";
	}
}
