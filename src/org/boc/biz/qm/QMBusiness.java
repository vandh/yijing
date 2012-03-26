package org.boc.biz.qm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;

import org.boc.biz.Business;
import org.boc.db.qm.QimenPublic;
import org.boc.rule.ELException;
import org.boc.rule.ExpressionEvaluatorImpl;

public class QMBusiness extends Business {
	private QMInParam param;
	private QMFunction function;
	private Map<String,Object> inParams ;   //所有输入参数
	private ExpressionEvaluatorImpl expr = new ExpressionEvaluatorImpl();
	private boolean isSaveResult;  //是否保存处理结果
	public QMBusiness(QimenPublic pub) {
		function = new QMFunction(pub);
		param = new QMInParam(pub, function);		
	}
	
	/**
	 * 执行一条规则
	 */
	public String evaluate(String rule) {		
		try {
			expr.evaluate(rule, String.class, this, this);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.toString();
		}
		return null;
	}
	
	/**
	 * 解析输入变量，以及自定义函数的对象this指针
	 */
	public Object resolveVariable(String param) throws ELException {
		Context ctx;
		Object obj = inParams.get(param);
		//System.out.println("输入变量------"+param+"="+obj);
		return obj == null ? param : obj;
	}
	/**
	 * 处理输出变量
	 */
	public void setVariable(String name, Object value) throws ELException {
		if(!isSaveResult) return;
		if(name==null || value==null) return; 
		if(name.equals(QMOutParam.OUTPARAM1))
			QMOutParam.append(value.toString());
	}
	/**
	 * 处理函数
	 */
	public Method resolveFunction(String prefix, String localName) {
		//System.out.println(localName+":"+function);
		return function.getMethod(localName);
	}
	/**
	 * 处理输入的文本，并加入到List中返回，是有序的
	 */
	private List<String> procRule(String text) {
		List<String> result=new ArrayList<String>();
		
		Pattern p = Pattern.compile("(.*?(\r\n|\n)).*");
		Matcher m = p.matcher(text);	
		while(m.find()){
			String s = m.group();
			//System.out.println("1: "+s);
			if("".equals(s.trim())) continue;
			//1. 替换掉｛...｝内所有的半角双引号，括号句号感叹号等均不用替换掉
			s = s.replaceAll("\"", "");
			//2. 将...｛｝...外的所有全角（，）空格  替换成半角(,)空格     包括二个参数、一个参数和无参数			
			s = s.replaceAll("(.*?)(（)(.*?)(，)(.*?)(）)(.*?)(\\{|｛)(.*?)(\\}|｝)","$1($3,$5)$7$8$9$10");
			//3. 去掉结尾｝后多余的 空格、;、；、.。等所有符号
			s = s.replaceAll("(}|｝)(.*)", "$1");
			s = s.replaceAll("(.*?)(（)(.*?)(）)(.*?)(\\{|｛)(.*?)(\\}|｝)","$1($3)$5$6$7$8");
			//3. 将  ｛|{...}|｝  替换成       如果 那么 结果 等于 "...";
			s = s.replaceAll("(\\{|｛)(.*?)(\\}|｝)", " 结果 等于  \"$2\";");
			//System.out.println("2: "+s);
			result.add(s);
		}
		return result;
	}
	
	/**
	 * 检查输入框中的所有规则 
	 */
	public String checkRules(String text) {
		inParams = param.getInputParam();		//每次都重新取一次，如果有效率问题，后面在变局时加载也可以
//		QMOutParam.clean();  //清空缓存，防止越存越多
		//System.out.println(text);
		isSaveResult = false;   //不需要保存处理结果，增加检查的效率
		List<String> list = procRule("\r\n"+text);  //必须加一个换行符，否则第一行丢失了
		String rs = null;
		String ruleText = null;
		for(int line = 0; line<list.size(); line++) {
			ruleText = list.get(line).trim().replaceAll("\r\n", "");
			//System.out.println(line+" : "+ruleText);
			//System.out.println(line+":"+list.get(line));
			if((rs = this.evaluate(ruleText))!=null) {
				//System.out.println((line+1) + " 为：   "+ruleText);
				return "第"+(line+1)+"行：规则处理出错，信息为：                                             "+rs;
			}
		}
		printFunction();
		printVariable();
		return "写得不错，所有规则语法检查均已成功，请放心执行！";
	}
	public void printVariable() {		
		for(Iterator<String> it = inParams.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			Object o = inParams.get(name);
			System.out.println(name);
		}
	}
	public void printFunction() {
		Map<String, Method> map = function.getMethods();
		String pname="";
		for(Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String name = it.next();
			Method method = map.get(name);
			Class[] paras = method.getParameterTypes();
			if(paras.length==1) pname = "落宫";
			else if(paras.length==2) pname="落宫1, 落宫2";
			System.out.println(name+"("+pname+")");
		}
	}
	
	/**
	 * 执行所有的规则 
	 */
	public String runRules(String text) {
		inParams = param.getInputParam();		//每次都重新取一次，如果有效率问题，后面在变局时加载也可以
		//System.out.println("----------------------text--------------------\r\n"+text);
		QMOutParam.clean();
		isSaveResult = true;   //需要保存处理结果，增加检查的效率
		List<String> list = procRule("\r\n"+text);  //不然，第一行丢失！
		String err = null;
		for(int line = 0; line<list.size(); line++) {
			//System.out.println(list.get(line));
			if((err = this.evaluate(list.get(line)))!=null) 
				return "第"+(line+1)+"行：规则处理出错，信息为：                                            "+err;
		}
		
		//处理输出变量
		String rs = getResult();
		//取出带括号的变量 （var1）
		Pattern p = Pattern.compile("((\\(|（)[^）|^\\)]*(\\)|）))");
		Matcher m = p.matcher(rs);	
		while(m.find()){
			String s1 = m.group();
			if("".equals(s1.trim())) continue;
			String s2 = s1.replaceAll("\\(|（|\\)|）", "");
			//System.out.println("s1="+s1+";s2="+s2+";value="+inParams.get(s2));
			rs = rs.replaceAll("((\\(|（)"+s2+"(\\)|）))", String.valueOf(inParams.get(s2)));
		}
		
//		String k;
//		Object v;
//		for(Iterator<String> it=inParams.keySet().iterator(); it.hasNext();) {
//			k = it.next();
//			v = inParams.get(k);
//			if(v==null) v="";
//			rs = rs.replaceAll(k, String.valueOf(v));
//		}
		return rs;
	}
	
	public String getResult() {
		return QMOutParam.getInfo();
	}
	
	public static void main(String[] args) {
		String text = "如果 你好（坎宫， 乙宫）  ｛aaaa（111）222｝\n如果 旺（工）{bbbbbbbbbbb}\r\n如果 你（）｛ccccccccccccc｝\n";
		text = "{（兄弟）你好（师傅)哈 哈 （工人）兄弟(农民）}";
		text = "{a}aa}   ；；;;；    ,。  a   ; .";
		//Pattern p = Pattern.compile("((\\(|（)[^）|^\\)]*(\\)|）))");
		System.out.println(text.replaceAll("(}|｝)(.*)", "$1"));
//		Matcher m = p.matcher(text);	
//		while(m.find()){
//			String s = m.group();
//			s = s.replaceAll("\\(|（|\\)|）", "");
//			System.out.println("1: "+s);
//		}
	}
}
