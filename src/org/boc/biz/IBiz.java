package org.boc.biz;

import org.boc.rule.FunctionMapper;
import org.boc.rule.VariableResolver;

/**
 * 解析原理：
 * 1. 在parser.ELParserTokenManager中定义各种词法分隔关键字，可加入中文
 * 2. 在parser.ELParserConstants接口中定义每个分隔的常量，如：
 * 		int IF1 = 82, IF2 = 83, IF3 = 84;
 * 3. 在上类的parser.ELParserConstants.Statement()方法中判断这个分隔词法：
 * 		case IF1:
 *    case IF2:
 *    case IF3:
 *      expr = IfThenElseExpression();
 *      break;
 * 4. 其实还是用javacc，定义了ELParser.jj文件     
 *      
 * 原版与现版：parser.ELParserTokenManager
 * 0= 1=null 2=${ 3=null 4=null 5=null 6=null 7=null 8=null 9=null 10=null
 * 11=null 12=true 13=false 14=null 15=} 16=. 17=> 18=gt 19=< 20=lt 21=== 22=eq
 * 23=<= 24=le 25=>= 26=ge 27=!= 28=ne 29=( 30=) 31=, 32=: 33=[ 34=] 35=+ 36=-
 * 37=* 38=/ 39=div 40=% 41=mod 42=not 43=! 44=and 45=&& 46=or 47=|| 48=empty
 * 49=? 50=null 51=null 52=null 53=null 54=null
 * 
 * 0= 1=null 2=null 3=null 4=null 5=null 6=null 7=null 8=null 9=null 10=null
 * 11=true 12=真 13=false 14=假 15=null 16=. 17=> 18=gt 19=大于 20=超过 21=< 22=lt
 * 23=小于 24=少于 25=== 26=eq 27=是 28=<= 29=le 30=不大于 31=没超过 32=>= 33=ge 34=不小于
 * 35=至少 36=!= 37=ne 38=不等于 39=( 40=（ 41=) 42=） 43=; 44=； 45=, 46=， 47=: 48=[
 * 49=] 50=+ 51=加 52=正 53=＋ 54=- 55=减 56=负 57=－ 58=* 59=乘 60=× 61=/ 62=div 63=除
 * 64=÷ 65=% 66=mod 67=not 68=! 69=不是 70=and 71=&& 72=并且 73=or 74=|| 75=或者
 * 76=empty 77=? 78== 79=等于 80=为 81=＝ 82=if 83=如果 84=假如 85=then 86=那么 87=就
 * 88=else 89=否则 90=不然 91=+= 92=加上 93=-= 94=减去 95={ 96=} 97=null 98=null 99=null
 * 100=null 101=null
 * 
 * {
0-10  "", null, null, null, null, null, null, null, null, null, null, 
11-17 "\164\162\165\145", "\u771f", "\146\141\154\163\145", "\u5047", "\156\165\154\154", "\56", "\76", 
18-23  "\147\164", "\u5927\u4e8e", "\u8d85\u8fc7", "\74", "\154\164", "\u5c0f\u4e8e", 
24-31 "\u5c11\u4e8e", "\75\75", "\145\161", "\u662f", "\74\75", "\154\145", "\u4e0d\u5927\u4e8e", 
32-37 "\u6ca1\u8d85\u8fc7", "\76\75", "\147\145", "\u4e0d\u5c0f\u4e8e", "\u81f3\u5c11", "\41\75", 
38-45 "\156\145", "\u4e0d\u7b49\u4e8e", "\50", "\uff08", "\51", "\uff09", "\73", "\uff1b", 
46-55 "\54", "\uff0c", "\72", "\133", "\135", "\53", "\u52a0", "\u6b63", "\uff0b", "\55", 
56-64 "\u51cf", "\u8d1f", "\uff0d", "\52", "\u4e58", "\327", "\57", "\144\151\166", "\u9664", 
65-71 "\367", "\45", "\155\157\144", "\156\157\164", "\41", "\u4e0d\u662f", "\141\156\144", 
72-76 "\46\46", "\u5e76\u4e14", "\157\162", "\174\174", "\u6216\u8005", 
77-84 "\145\155\160\164\171", "\77", "\75", "\u7b49\u4e8e", "\u4e3a", "\uff1d", "\151\146", "\u5982\u679c", 
85-89 "\u5047\u5982", "\164\150\145\156", "\u90a3\u4e48", "\u5c31", "\145\154\163\145", 
90-96 "\u5426\u5219", "\u4e0d\u7136", "\53\75", "\u52a0\u4e0a", "\55\75", "\u51cf\u53bb", "\173", 
97- "\175", null, null, null, null, null, };
 * 原版与现版： format.ELParserTokenManager
 * 0= 1=null 2=${ 3=null 4=null 5=null 6=null 7=null 8=null 9=null 10=null 11=null 12=true 
 * 13=false 14=null 15=} 16=. 17=> 18=gt 19=< 20=lt 21=== 22=eq 23=<= 24=le 25=>= 26=ge 
 * 27=!= 28=ne 29=( 30=) 31=, 32=: 33=[ 34=] 35=+ 36=- 37=* 38=/ 39=div 40=% 41=mod 42=not 
 * 43=! 44=and 45=&& 46=or 47=|| 48=empty 49=? 50=null 51=null 52=null 53=null 54=null 
 * 0= 1=null 2=null 3=null 4=null 5=null 6=null 7=true 8=真 9=false 10=假 11=null 12=. 13=> 
 * 14=gt 15=大于 16=超过 17=< 18=lt 19=小于 20=少于 21=== 22=eq 23=是 24=<= 25=le 26=不大于 27=没超过 28=>= 
 * 29=ge 30=不小于 31=至少 32=!= 33=ne 34=不等于 35=( 36=（ 37=) 38=） 39=; 40=； 41=, 42=， 43=: 44=[ 45=] 
 * 46=+ 47=加 48=正 49=＋ 50=- 51=减 52=负 53=－ 54=* 55=乘 56=× 57=/ 58=div 59=除 60=÷ 61=% 62=mod 63=not 
 * 64=! 65=不是 66=and 67=&& 68=并且 69=or 70=|| 71=或者 72=empty 73=? 74== 75=等于 76=为 77=＝ 78=if 
 * 79=如果 80=假如 81=then 82=那么 83=就 84=else 85=否则 86=不然 87=+= 88=加上 89=-= 90=减去 91={ 92=} 93=null 
 * 94=null 95=null 96=null 97=null 
 */
public interface IBiz extends VariableResolver, FunctionMapper {
	/**
	 * 不能用！，因为这代表非的意思
	 
	public void run() {
		ExpressionEvaluatorImpl expr = new ExpressionEvaluatorImpl();
		String s1 = "100*2 + 300 + 500/1-800";
		// 需要空格
		String s2 = "如果((乾卦 不小于 3 并且 天冲 小于 4)或者(100>200 或者 11<10))那么(y 为  \"你好\"); 否则 (\"刘德华，好！\")；";
		String s3 = "如果 乾卦<=300 那么 y 为 你好;";
		String s4 = "如果 乾卦<=300 并且 天冲 小于 1 那么 是对的； 否则 \"是错的，因为天冲等说1\"；";
		String s5 = "如果 乾卦<=300 那么 你好;";
		try {
			String rs = (String) expr.evaluate(s5, String.class, this, this);
			System.out.println("运行结果为：" + rs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void outCn() {
		for (int i = 0; i < ELParserTokenManager.jjstrLiteralImages.length; i++) {
			if (ELParserTokenManager.jjstrLiteralImages[i] == null) {
				System.out.print(i + "=" + ELParserTokenManager.jjstrLiteralImages[i]+ " ");
				continue;
			}
			System.out.print(i + "=" + new String(ELParserTokenManager.jjstrLiteralImages[i]) + " ");
		}
	}
*/
	/**
	 * 这里提供输入变量及对应的值 1.
	 * 给每个输入变量规定不同的类型，int/string/date相当于八门/九星/天干/地支类型，从而每个变量封装成Variable类 2.
	 * 由变量查找其对应的Variable对象，如果没有找到，则从缓存inputMap中取该变量，inputMap没有则直接作为字符串返回； 3.
	 * 如果找到，inputMap中还没有该变量对应的值，则加入； 4.
	 * 如果是输出变量如公式、sql、中间结果，则计算该变量的中间值后放入outputMap后返回；
	 * 不能识别的，就以原字符串返回
	 * 严重注意：修改了FunctionInvocation：：evaluate：174行
	 
	public Object resolveVariable(String pName) throws ELException {
		// System.out.println(pName);
		if (pName.equals("乾卦"))
			return 100;
		else if (pName.equals("天冲"))
			return 2; // 200，2
		return pName;
	}
*/
	/**
	 * 程序中每次给参数赋值时，都会触发该方法。可以将这个输出参数放入输出变量outputMap中 如： if(x>3)
	 * y=5，则y=5就会触发本方法，但给x赋值不会触发
	 
	public void setVariable(String name, Object value) throws ELException {
		 System.out.println("setVariable="+name+"\t"+value);
	}

	public Method resolveFunction(String prefix, String localName) {
		return null;
	}
	
*/
}
