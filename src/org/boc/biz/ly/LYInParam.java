package org.boc.biz.ly;

import java.util.HashMap;
import java.util.Map;

import org.boc.biz.qm.QMFunction;
import org.boc.dao.ly.DaoYiJingMain;
import org.boc.dao.ly.LiuyaoPublic;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;

	/**
	 * 输入参数都在这里初始化
	 * 1. 实例化时就初始化常量，只加载一次
	 * 2. 每次换局，必须性新加载变量
	 */
public class LYInParam {
	private LiuyaoPublic pub;
	private DaoYiJingMain daoly;
	private LYFunction function;
	private static Map<String,Object> params = new HashMap<String,Object>();
	public LYInParam(LiuyaoPublic p, LYFunction function) {
		if(p==null) return;
		pub = p;
		daoly = pub.getDaoYiJingMain();
		this.function = function;
		initConstant();
	}
	
	public LYFunction getFunction() {
		return function;
	}
	
	/**
	 * 一、常量，只需要初始化一次，int
	 * 1. 无气=-1，旺相=1，稍有气=0|-2
	 * 2. 合局=1，失局=-1
	 * 3. 得三奇=1，不得三奇=-1
	 * 4. 凶=-1，中=0，吉=1
	 * 5. 甲、乙、丙、丁、戊、己、庚、辛、壬、癸
	 * 6. 子、丑、寅、卯、辰、巳、午、未、申、酉、戌、亥
	 * 7. 坎宫,坤宫,震宫,巽宫,中宫,乾宫,兑宫,艮宫,离宫 
	 * 8. 休门、死门、伤门、杜门、中门、开门、惊门、生门、景门
	 * 9. 值符、腾蛇、太阴、六合、白虎、玄武、九地、九天
	 * 10. 天蓬星、天芮星、天冲星、天辅星、天禽星、天心星、天柱星、天任星、天英星
	 */
	private void initConstant() {
		params.put("this", function);
		
		params.put("甲", YiJing.JIA);
		params.put("乙", YiJing.YI);
		params.put("丙", YiJing.BING);
		params.put("丁", YiJing.DING);
		params.put("戊", YiJing.WUG);
		params.put("己", YiJing.JI);
		params.put("庚", YiJing.GENG);
		params.put("辛", YiJing.XIN);
		params.put("壬", YiJing.REN);
		params.put("癸", YiJing.GUI);
		
		params.put("子", YiJing.ZI);
		params.put("丑", YiJing.CHOU);
		params.put("寅", YiJing.YIN);
		params.put("卯", YiJing.MAO);
		params.put("辰", YiJing.CHEN);
		params.put("巳", YiJing.SI);
		params.put("午", YiJing.WUZ);
		params.put("未", YiJing.WEI);
		params.put("申", YiJing.SHEN);
		params.put("酉", YiJing.YOU);
		params.put("戌", YiJing.XU);
		params.put("亥", YiJing.HAI);
		
		params.put("坎宫", 1);
		params.put("坤宫", 2);
		params.put("震宫", 3);
		params.put("巽宫", 4);
		params.put("中宫", 5);
		params.put("乾宫", 6);
		params.put("兑宫", 7);
		params.put("艮宫", 8);
		params.put("离宫", 9);
		
	}
	
	/**
	 * 二、判断，boolean
	 * 1. 乾造，坤造
	 * 2. 阳遁，阴遁
	 * 3. 八门伏呤、八门反呤、九星伏呤、九星反呤
	 */
	private void initVar1(Map<String,Object> params) {
		params.put("乾造", pub.isBoy());
		params.put("坤造", !pub.isBoy());
	}
	
	/** 
	 * 四、用神、年命，int
	 * 1. 用神天干、用神地支、年命天干、年命地支
	 * 2. 年干、年支、月干、月支、日干、日支、时干、时支
	 */	
	private void initVar3(Map<String,Object> params) {		
//		params.put("用神天干", pub.getYSTiangan());
//		params.put("用神地支", pub.getYSDizi());
//		params.put("年命天干", pub.getMingtg());
//		params.put("年命地支", pub.getMingdz());
//		
		params.put("年干", SiZhu.ng);
		params.put("年支", SiZhu.nz);
		params.put("月干", SiZhu.yg);
		params.put("月支", SiZhu.yz);
		params.put("日干", SiZhu.rg);
		params.put("日支", SiZhu.rz);
		params.put("时干", SiZhu.sg);
		params.put("时支", SiZhu.sz);
	}
	
	/**
	 * 将常量与变量分别加载
	 * @param params
	 */
	public Map<String,Object> getInputParam() {
		Map<String,Object> customParams = new HashMap<String,Object>();
		customParams.putAll(params);
		initVar1(customParams);
		initVar3(customParams);
		
		return customParams;
	}
}
