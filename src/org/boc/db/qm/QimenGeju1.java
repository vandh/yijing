package org.boc.db.qm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.BadLocationException;

import org.boc.dao.qm.DaoQiMen;
import org.boc.ui.ResultPanel;

public class QimenGeju1 extends QimenGejuBase{
	/**
	 * 转盘为1，飞盘为2
	 * 如果调大小：1、2、3行不用调，4、5、6、7要调
	 * 上1、中2、下1是死的，其它9格”┃    “是一致的，后面加“┃”进行封闭
	 * 每格分7行， 每行列数固定；
	 * 1行：宫旺衰smallpink-1，年月日时smallblue-2，smallblack-16
	 * 2行：重要吉凶格smallblack-19
	 * 3行：空格smallblack-19
	 * 4行：空格smallblack-3,八神bigblack(bigred)-1，空格smallblack-4，天地盘冲合刑smallblack-10
	 * 5行：八门宫月旺衰smallblack-3，八门bigblack(bigred)-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
	 * 6行：九星宫月旺衰smallblack-3，九星bigblack-1，奇仪宫月旺衰smallblack-4，奇仪bigblack-2，型冲合桃墓smallblack-6
	 * 7行：空格smallblack-4，地盘八神smallblack(smallred)-1，空格smallblack-5，暗干smallblack-9
	 */
	public String getGeju(ResultPanel rp, int izf, StringBuffer str) {
		this.resultPane = rp;  	
		this.iszf = izf!=2;
		daoqm.setZhuanpan(iszf);
		try {
			init();
			//1. 打印表头
			printHead();
			pw.newLine();
			//2. 打印第一条线
			print(UP);
			pw.newLine();
			//
			for(int i=1; i<=7; i++) {
				pGong(4, i);
				pGong(9, i);
				pGong(2, i);
				println(2,END);
				pw.newLine();
			}
			
			print(MID1);
			pw.newLine();
			
			for(int i=1; i<=7; i++) {
				pGong(3, i);
				pGong(5, i);
				pGong(7, i);
				println(7,END);
				pw.newLine();
			}
			
			print(MID2);
			pw.newLine();
			
			for(int i=1; i<=7; i++) {
				pGong(8, i);
				pGong(1, i);
				pGong(6, i);
				println(6,END);
				pw.newLine();
			}
			
			print(DOWN);
			pw.newLine();
		} catch (BadLocationException e) {
			e.printStackTrace();
			return null;
		}
		
		return null;
	}

	/**
	 * 输出九宫格
	 * @param gong
	 * @throws BadLocationException
	 * 5星天盘干、6门地盘干、52门天盘干、62星地盘干
	 * 永远是pLine5/pLine6或 pLine52/pLine62的先后输出关系
	 */
	private void pGong(int gong, int line) throws BadLocationException{		
		println(gong,START);		
		
		if(QiMen2.XMHW==100) {  //72变，即1234567乱跳			
				try {
					Method method = map.get(line);
					//System.out.println(line+":"+method.getName());
					method.invoke(this, gong);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
		}

		if(line==1) pLine1(gong);
		else if(line==2) pLine2(gong);
		else if(line==3) pLine3(gong);
		else if(line==7) pLine7(gong);	
		//xmhw=0/4/5都是星+天盘干，门+地盘干； 其余123皆是门+天盘干、星+地盘干；这个045123的值不能变了，一变到Tip:getDesc()方法就乱了。
		if(QiMen2.XMHW==0) { //神星门，这是默认的，所以提示都是对的，否则就乱了；
			if(line==4) pLine4(gong);
			else if(line==5) pLine5(gong);
			else if(line==6) pLine6(gong);
		}else if(QiMen2.XMHW==1) {//神门星
			if(line==4) pLine4(gong);
			else if(line==5) pLine52(gong);
			else if(line==6) pLine62(gong);
		}else if(QiMen2.XMHW==2){ //门神星
			if(line==4) pLine52(gong);
			if(line==5) pLine4(gong);
			if(line==6) pLine62(gong);
		}else if(QiMen2.XMHW==3){  //门星神
			if(line==4) pLine52(gong);
			if(line==5) pLine62(gong);
			if(line==6) pLine4(gong);
		}else if(QiMen2.XMHW==4){  //星神门
			if(line==4) pLine5(gong);
			if(line==5) pLine4(gong);
			if(line==6) pLine6(gong);
		}else if(QiMen2.XMHW==5){	//星门神
			if(line==4) pLine5(gong);
			if(line==5) pLine6(gong);
			if(line==6) pLine4(gong);
		} 
	}
	
	private static Map<Integer, Method> map = new HashMap<Integer, Method>(7);
	public static void getRandomInvoke() {		
		int[] r = getRandom();
		for(int i=1; i<=7; i++) {
			//r[i-1]是当前行要变换的位置序号，由此得到对应的调用方法
			Method m = null;
			try {
				m = QimenGejuBase.class.getMethod("pLine"+r[i-1], int.class);
				map.put(i, m);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}

	//得到一组123456不同顺序的随机数
	private static int[] getRandom() {
		int[] arr = new int[7];
		for (int i = 0; i < 7; i++) {
			arr[i] = (int) (Math.random() * 7) + 1;
			for (int j = 0; j < i; j++) {
				if (arr[j] == arr[i]) {//如果arr[i]与arr[j]相同，则arr[i]重新取值，并检验
					i--;
					break;
				}
			}
		}
		return arr;
	}
	
	public QimenGeju1(DaoQiMen daoqm, QimenPublic pub) {
  	this.daoqm = daoqm;
  	this.pub = pub;  	
  }
	
	public static void main(String[] args) {
		int[] arr = new int[7];
		for (int i = 0; i < 7; i++) {
			arr[i] = (int) (Math.random() * 7) + 1;
			for (int j = 0; j < i; j++) {
				if (arr[j] == arr[i]) {//如果arr[i]与arr[j]相同，则arr[i]重新取值，并检验
					i--;
					break;
				}
			}
		}
		for (int i = 0; i < 7; i++)
			System.out.print(arr[i] + " ");
	}

}