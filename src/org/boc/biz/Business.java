package org.boc.biz;

import java.lang.reflect.Method;

import org.boc.rule.ELException;
import org.boc.rule.ExpressionEvaluatorImpl;
import org.boc.rule.FunctionMapper;
import org.boc.rule.VariableResolver;
import org.boc.rule.parser.ELParserTokenManager;

public class Business implements IBiz {
	public void run() {
		ExpressionEvaluatorImpl expr = new ExpressionEvaluatorImpl();
		String s1 = "100*2 + 300 + 500/1-800";
		// 需要空格
		String s2 = "如果((乾卦 不小于 3 并且 天冲 小于 4)或者(100>200 或者 11<10))那么(y 为  \"你好\"); 否则 (\"刘德华，好！\")；";
		String s3 = "如果    乾卦<=300 那么 y 为 你好;";
		String s4 = "如果 乾卦<=300 并且 天冲 小于 1 那么 是对的； 否则 \"是错的，因为天冲等说1\"；";
		String s5 = "如果 乾卦<=300 那么 你好;";
		String s6 = "如果  相克(8,45) 那么 函数；";
		String s7 = "结果     等于 \"你好,！｛｝{}“”朋友\";"; 
		s7 = "如果 (不是true 并且 (2==2  或者 3==3)) 那么  结果 等于  \"  天英星乘旺相，落于震三、巽四宫，或克日、时二干，主晴天。\";";
		s7 = "如果 真 那么 y = aaaaaaaa乾卦aaaaaaaa; ";
		try {
			String rs = (String) expr.evaluate(s7, String.class, this, this);
			System.out.println("运行结果为：" + rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public Object resolveVariable(String pName) throws ELException {
		// System.out.println(pName);
		if (pName.equals("乾卦"))
			return 100;
		else if (pName.equals("天冲"))
			return 2; // 200，2
		else if (pName.equals("this"))
			return this;
		return pName;
	}

	public void setVariable(String name, Object value) throws ELException {
		 System.out.println("setVariable="+name+"\t"+value);
	}

	public Method resolveFunction(String prefix, String localName) {
		//System.out.println("resolveFunction="+prefix+"\t"+localName);
		return this.getMethod(localName,int.class , int.class);
	}
	
	public Method getMethod(String mname,Class... params) {
		try {
			return this.getClass().getMethod(mname,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean 相克(int i, int j) {
		return i>j;
	}

	public static void main(String[] args) {
		new Business().run();
	}
}
