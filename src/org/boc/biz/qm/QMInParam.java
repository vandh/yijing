package org.boc.biz.qm;

import java.util.HashMap;
import java.util.Map;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.db.qm.QimenPublic;

	/**
	 * 输入参数都在这里初始化
	 * 1. 实例化时就初始化常量，只加载一次
	 * 2. 每次换局，必须性新加载变量
	 */
public class QMInParam {
	private QimenPublic pub;
	private DaoQiMen daoqm;
	private QMFunction function;
	private static Map<String,Object> params = new HashMap<String,Object>();
	public QMInParam(QimenPublic p, QMFunction function) {
		if(p==null) return;
		pub = p;
		daoqm = pub.getDaoQiMen();
		this.function = function;
		initConstant();
	}
	
	public QMFunction getFunction() {
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
		
		params.put("无气",-1);
		params.put("旺相",1);
		params.put("稍有气",0);
		params.put("合局",1);
		params.put("失局",-1);
		params.put("得三奇",1);
		params.put("不得三奇",-1);
		params.put("凶",-1);
		params.put("中",0);  //有的是0凶，所以最好判断吉，不要判断凶
		params.put("吉",1);		
		
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
		
		params.put("休门", QiMen.MENXIU);
		params.put("死门", QiMen.MENSI);
		params.put("伤门", QiMen.MENSHANG);
		params.put("杜门", QiMen.MENDU);
		params.put("中门", QiMen.MENZHONG);
		params.put("开门", QiMen.MENKAI);
		params.put("惊门", QiMen.MENJING1);
		params.put("生门", QiMen.MENSHENG);
		params.put("景门", QiMen.MENJING3);
		
		params.put("值符", QiMen.SHENFU);
		params.put("腾蛇", QiMen.SHENSHE);
		params.put("太阴", QiMen.SHENYIN);
		params.put("六合", QiMen.SHENHE);
		params.put("白虎", QiMen.SHENHU);
		params.put("玄武", QiMen.SHENWU);
		params.put("九地", QiMen.SHENDI);
		params.put("九天", QiMen.SHENTIAN);
		
		params.put("天蓬星", QiMen.XINGPENG);
		params.put("天芮星", QiMen.XINGRUI);
		params.put("天冲星", QiMen.XINGCHONG);
		params.put("天辅星", QiMen.XINGFU);
		params.put("天禽星", QiMen.XINGQIN);
		params.put("天心星", QiMen.XINGXIN);
		params.put("天柱星", QiMen.XINGZHU);
		params.put("天任星", QiMen.XINGREN);
		params.put("天英星", QiMen.XINGYING);
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
		params.put("阳遁", daoqm.getJu()>0);
		params.put("阴遁", daoqm.getJu()<0);
		params.put("八门伏呤",pub.isMenFu());
		params.put("八门反呤",pub.isMenFan());
		params.put("九星伏呤",pub.isXingFu());
		params.put("九星反呤",pub.isXingFan());
		params.put("八门伏吟",pub.isMenFu());
		params.put("八门反吟",pub.isMenFan());
		params.put("九星伏吟",pub.isXingFu());
		params.put("九星反吟",pub.isXingFan());
	}
	
	/**
	 * 三、落宫，int
	 * 1. 年干、月干、日干、时干天盘|地盘落宫
	 * 2. 值使落宫
	 * 3. 用神天盘落宫（用神天宫）、用神地盘落宫（用神地宫）、年命天盘落宫、年命地盘落宫
	 * 4. 天盘乙落宫|天盘天干乙落宫|乙落宫、...  === 天盘干落宫，即包含“乙落宫”但不包含“地”
	 * 5. 地盘乙落宫|地盘天干乙落宫、...  			=== 地盘干落宫，即包含“地盘乙落宫”
	 * 6. 开门落宫|开门宫、生门落宫、...   		=== 八门落宫
	 * 7. 天蓬落宫|天蓬星落宫、...  				=== 九星落宫，即包含三字“蓬落宫”，用java正则表达式
	 * 9. 九天落宫、九地落宫、...						=== 八神落宫
	 */ 
	private void initVar2(Map<String,Object> params) {
		params.put("年干落宫", pub.getTianGong(SiZhu.ng, SiZhu.nz));
		params.put("年干地盘落宫", pub.getDiGong(SiZhu.ng, SiZhu.nz));
		params.put("月干落宫", pub.getTianGong(SiZhu.yg, SiZhu.yz));
		params.put("月干地盘落宫", pub.getDiGong(SiZhu.yg, SiZhu.yz));
		params.put("日干落宫", pub.getTianGong(SiZhu.rg, SiZhu.rz));
		params.put("日干地盘落宫", pub.getDiGong(SiZhu.rg, SiZhu.rz));
		params.put("时干落宫", pub.getTianGong(SiZhu.sg, SiZhu.sz));
		params.put("时干地盘落宫", pub.getDiGong(SiZhu.sg, SiZhu.sz));		
		
		params.put("值使落宫", daoqm.getZhishiGong());
		
		params.put("用神天盘干落宫", pub.getYSTgong());
		params.put("用神地盘干落宫", pub.getYSDgong());
		params.put("年命天盘干落宫", pub.getMingTgong());
		params.put("年命地盘干落宫", pub.getMingDgong());		
		
		params.put("乙落宫", pub.getTianGong(YiJing.YI, 0));
		params.put("丙落宫", pub.getTianGong(YiJing.BING, 0));
		params.put("丁落宫", pub.getTianGong(YiJing.DING, 0));
		params.put("戊落宫", pub.getTianGong(YiJing.WUG, 0));
		params.put("己落宫", pub.getTianGong(YiJing.JI, 0));
		params.put("庚落宫", pub.getTianGong(YiJing.GENG, 0));
		params.put("辛落宫", pub.getTianGong(YiJing.XIN, 0));
		params.put("壬落宫", pub.getTianGong(YiJing.REN, 0));
		params.put("癸落宫", pub.getTianGong(YiJing.GUI, 0));
		
		
		params.put("地盘乙落宫", pub.getDiGong(YiJing.YI, 0));
		params.put("地盘丙落宫", pub.getDiGong(YiJing.BING, 0));
		params.put("地盘丁落宫", pub.getDiGong(YiJing.DING, 0));
		params.put("地盘戊落宫", pub.getDiGong(YiJing.WUG, 0));
		params.put("地盘己落宫", pub.getDiGong(YiJing.JI, 0));
		params.put("地盘庚落宫", pub.getDiGong(YiJing.GENG, 0));
		params.put("地盘辛落宫", pub.getDiGong(YiJing.XIN, 0));
		params.put("地盘壬落宫", pub.getDiGong(YiJing.REN, 0));
		params.put("地盘癸落宫", pub.getDiGong(YiJing.GUI, 0));
		
		params.put("休门落宫", pub.getMenGong(QiMen.MENXIU));
		params.put("生门落宫", pub.getMenGong(QiMen.MENSHENG));
		params.put("伤门落宫", pub.getMenGong(QiMen.MENSHANG));
		params.put("杜门落宫", pub.getMenGong(QiMen.MENDU));
		params.put("景门落宫", pub.getMenGong(QiMen.MENJING3));
		params.put("死门落宫", pub.getMenGong(QiMen.MENSI));
		params.put("惊门落宫", pub.getMenGong(QiMen.MENJING1));
		params.put("开门落宫", pub.getMenGong(QiMen.MENKAI));
		
		params.put("天蓬星落宫", pub.getXingGong(QiMen.XINGPENG));
		params.put("天芮星落宫", pub.getXingGong(QiMen.XINGRUI));
		params.put("天冲星落宫", pub.getXingGong(QiMen.XINGCHONG));
		params.put("天辅星落宫", pub.getXingGong(QiMen.XINGFU));
		params.put("天禽星落宫", pub.getXingGong(QiMen.XINGQIN));
		params.put("天心星落宫", pub.getXingGong(QiMen.XINGXIN));
		params.put("天柱星落宫", pub.getXingGong(QiMen.XINGZHU));
		params.put("天任星落宫", pub.getXingGong(QiMen.XINGREN));
		params.put("天英星落宫", pub.getXingGong(QiMen.XINGYING));
		
		params.put("值符落宫", pub.getShenGong(QiMen.SHENFU));
		params.put("腾蛇落宫", pub.getShenGong(QiMen.SHENSHE));
		params.put("太阴落宫", pub.getShenGong(QiMen.SHENYIN));
		params.put("六合落宫", pub.getShenGong(QiMen.SHENHE));
		params.put("白虎落宫", pub.getShenGong(QiMen.SHENHU));
		params.put("玄武落宫", pub.getShenGong(QiMen.SHENWU));
		params.put("九地落宫", pub.getShenGong(QiMen.SHENDI));
		params.put("九天落宫", pub.getShenGong(QiMen.SHENTIAN));
	}
	
	/** 
	 * 四、用神、年命，int
	 * 1. 用神天干、用神地支、年命天干、年命地支
	 * 2. 年干、年支、月干、月支、日干、日支、时干、时支
	 */	
	private void initVar3(Map<String,Object> params) {		
		params.put("用神天干", pub.getYSTiangan());
		params.put("用神地支", pub.getYSDizi());
		params.put("年命天干", pub.getMingtg());
		params.put("年命地支", pub.getMingdz());
		
		params.put("年干", SiZhu.ng);
		params.put("年支", SiZhu.nz);
		params.put("月干", SiZhu.yg);
		params.put("月支", SiZhu.yz);
		params.put("日干", SiZhu.rg);
		params.put("日支", SiZhu.rz);
		params.put("时干", SiZhu.sg);
		params.put("时支", SiZhu.sz);
	}
	
	/*
	 * 五、状态判断，int
	 * 1. 用神天干旺衰、日干旺衰、 时干旺衰、值使天干旺衰   				=== 旺相、无气、稍有气
	 * 2. 用神宫九星旺衰、日宫九星旺衰、时宫九星旺衰、值使宫九星旺衰
	 * 3. 用神宫八门旺衰、日宫八门旺衰、时宫八门旺衰、值使宫八门旺衰
	 * 4. 用神宫天干状态、日宫天干状态、时宫天干状态、值使宫天干状态		=== 合局、失局
	 * 5. 用神宫八门状态、日宫八门状态、时宫八门状态、值使宫八门状态
	 * 6. 用神宫九星状态、日宫九星状态、时宫九星状态、值使宫九星九星
	 * 7. 用神宫八神状态、日宫八神状态、时宫八神状态、值使宫八神九星  							
	 * 8. 用神宫得奇、日宫得奇、时宫得奇、值使宫得奇 					=== 得三奇、不得三奇
	 * 9. 用神宫八门吉凶、日宫八门吉凶、时宫八门吉凶、值使宫八门吉凶		=== 吉、凶、中
	 * 10. 用神宫八神吉凶、日宫八神吉凶、时宫八神吉凶、值使宫八神吉凶
	 * 11. 用神宫九星吉凶、日宫九星吉凶、时宫九星吉凶、值使宫九星吉凶
	 * 12. 用神宫格局吉凶、日宫格局吉凶、时宫格局吉凶、值使宫格局吉凶
	 */
	private void initVar4(Map<String,Object> params) {
		int zhishigong = daoqm.getZhishiGong();
		zhishigong = zhishigong==5 ? pub.getJigong() : zhishigong;
		
		params.put("用神天干旺衰", Integer.valueOf(pub.gettgWS(pub.getYSTgong())[0]));
		params.put("日干旺衰", Integer.valueOf(pub.gettgWS(SiZhu.rg,SiZhu.rz)[0]));
		params.put("时干旺衰", Integer.valueOf(pub.gettgWS(SiZhu.sg,SiZhu.sz)[0]));
		params.put("值使天干旺衰", Integer.valueOf(pub.gettgWS(zhishigong)[0]));
		
		params.put("用神宫九星旺衰", Integer.valueOf(pub.getxingWS(pub.getYSTgong())[0]));
		params.put("日宫九星旺衰", Integer.valueOf(pub.getxingWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫九星旺衰", Integer.valueOf(pub.getxingWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫九星旺衰", Integer.valueOf(pub.getxingWS(zhishigong)[0]));
		
		params.put("用神宫八门旺衰", Integer.valueOf(pub.getmenWS(pub.getYSTgong())[0]));
		params.put("日宫八门旺衰", Integer.valueOf(pub.getmenWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫八门旺衰", Integer.valueOf(pub.getmenWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫八门旺衰", Integer.valueOf(pub.getmenWS(zhishigong)[0]));
		
		params.put("用神宫天干状态", Integer.valueOf(pub.isganHeju(pub.getYSTgong())[0]));
		params.put("日宫天干状态", Integer.valueOf(pub.isganHeju(SiZhu.rg, SiZhu.rz)[0]));
		params.put("时宫天干状态", Integer.valueOf(pub.isganHeju(SiZhu.sg, SiZhu.sz)[0]));
		params.put("值使宫天干状态", Integer.valueOf(pub.isganHeju(zhishigong)[0]));
		
		params.put("用神宫九星状态", Integer.valueOf(pub.isxingHeju(pub.getYSTgong())[0]));
		params.put("日宫九星状态", Integer.valueOf(pub.isxingHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫九星状态", Integer.valueOf(pub.isxingHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫九星状态", Integer.valueOf(pub.isxingHeju(zhishigong)[0]));
		
		params.put("用神宫八门状态", Integer.valueOf(pub.ismenHeju(pub.getYSTgong())[0]));
		params.put("日宫八门状态", Integer.valueOf(pub.ismenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫八门状态", Integer.valueOf(pub.ismenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫八门状态", Integer.valueOf(pub.ismenHeju(zhishigong)[0]));
		
		params.put("用神宫八神状态", Integer.valueOf(pub.isshenHeju(pub.getYSTgong())[0]));		
		params.put("日宫八神状态", Integer.valueOf(pub.isshenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));		
		params.put("时宫八神状态", Integer.valueOf(pub.isshenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫八神状态", Integer.valueOf(pub.isshenHeju(zhishigong)[0]));
		
		params.put("用神宫得奇", Integer.valueOf(pub.getSanji(pub.getYSTgong())[0]));
		params.put("日宫得奇", Integer.valueOf(pub.getSanji(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫得奇", Integer.valueOf(pub.getSanji(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫得奇", Integer.valueOf(pub.getSanji(zhishigong)[0]));
		
		params.put("用神宫八门吉凶", Integer.valueOf(pub.getmenJX(pub.getYSTgong())[0]));
		params.put("日宫八门吉凶", Integer.valueOf(pub.getmenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫八门吉凶", Integer.valueOf(pub.getmenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫八门吉凶", Integer.valueOf(pub.getmenJX(zhishigong)[0]));
		
		params.put("用神宫八神吉凶", Integer.valueOf(pub.getshenJX(pub.getYSTgong())[0]));
		params.put("日宫八神吉凶", Integer.valueOf(pub.getshenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫八神吉凶", Integer.valueOf(pub.getshenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫八神吉凶", Integer.valueOf(pub.getshenJX(zhishigong)[0]));
		
		params.put("用神宫九星吉凶", Integer.valueOf(pub.getxingJX(pub.getYSTgong())[0]));
		params.put("日宫九星吉凶", Integer.valueOf(pub.getxingJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫九星吉凶", Integer.valueOf(pub.getxingJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));
		params.put("值使宫九星吉凶", Integer.valueOf(pub.getxingJX(zhishigong)[0]));
		
		params.put("用神宫格局吉凶", Integer.valueOf(pub.getJixiongge(pub.getYSTgong())[0]));
		params.put("日宫格局吉凶", Integer.valueOf(pub.getJixiongge(pub.getTianGong(SiZhu.rg, SiZhu.rz))[0]));
		params.put("时宫格局吉凶", Integer.valueOf(pub.getJixiongge(pub.getTianGong(SiZhu.sg, SiZhu.sz))[0]));		
		params.put("值使宫格局吉凶", Integer.valueOf(pub.getJixiongge(zhishigong)[0]));
	}
	
	/**
	 * 六、字符串描述，String
	 * 1. 用神天干旺衰描述、日干旺衰描述、 时干旺衰描述、值使宫旺衰描述   					=== 旺相、无气、稍有气
	 * 2. 用神宫九星旺衰描述、日宫九星旺衰描述、时宫九星旺衰描述、。。。
	 * 3. 用神宫八门旺衰描述、日宫八门旺衰描述、时宫八门旺衰描述
	 * 4. 用神宫天干状态描述、日宫天干状态描述、时宫天干状态描述		=== 合局、失局
	 * 5. 用神宫八门状态描述、日宫八门状态描述、时宫八门状态描述、
	 * 6. 用神宫九星状态描述、日宫九星状态描述、时宫九星状态描述
	 * 7. 用神宫八神状态描述、日宫八神状态描述、时宫八神状态 描述 							
	 * 8. 用神宫得奇描述、日宫得奇描述、时宫得奇描述 						=== 得三奇、不得三奇
	 * 9. 用神宫八门吉凶描述、日宫八门吉凶描述、时宫八门吉凶描述		=== 吉、凶、中
	 * 10. 用神宫八神吉凶描述、日宫八神吉凶描述、时宫八神吉凶描述
	 * 11. 用神宫九星吉凶描述、日宫九星吉凶描述、时宫九星吉凶描述
	 */
	private void initVar5(Map<String,Object> params) {
		int zhishigong = daoqm.getZhishiGong();
		zhishigong = zhishigong==5 ? pub.getJigong() : zhishigong;
		
		params.put("用神天干旺衰描述", pub.gettgWS(pub.getYSTgong())[1]);
		params.put("日干旺衰描述", pub.gettgWS(SiZhu.rg,SiZhu.rz)[1]);
		params.put("时干旺衰描述", pub.gettgWS(SiZhu.sg,SiZhu.sz)[1]);
		params.put("值使天干旺衰描述", pub.gettgWS(zhishigong)[1]);
		
		params.put("用神宫九星旺衰描述", pub.getxingWS(pub.getYSTgong())[1]);
		params.put("日宫九星旺衰描述", pub.getxingWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫九星旺衰描述", pub.getxingWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫九星旺衰描述", pub.getxingWS(zhishigong)[1]);
		
		params.put("用神宫八门旺衰描述", pub.getmenWS(pub.getYSTgong())[1]);
		params.put("日宫八门旺衰描述", pub.getmenWS(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫八门旺衰描述", pub.getmenWS(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫八门旺衰描述", pub.getmenWS(zhishigong)[1]);
		
		params.put("用神宫天干状态描述", pub.isganHeju(pub.getYSTgong())[1]);
		params.put("日宫天干状态描述", pub.isganHeju(SiZhu.rg, SiZhu.rz)[1]);
		params.put("时宫天干状态描述", pub.isganHeju(SiZhu.sg, SiZhu.sz)[1]);
		params.put("值使宫天干状态描述", pub.isganHeju(zhishigong)[1]);
		
		params.put("用神宫九星状态描述", pub.isxingHeju(pub.getYSTgong())[1]);
		params.put("日宫九星状态描述", pub.isxingHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫九星状态描述", pub.isxingHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫九星状态描述", pub.isxingHeju(zhishigong)[1]);
		
		params.put("用神宫八门状态描述", pub.ismenHeju(pub.getYSTgong())[1]);
		params.put("日宫八门状态描述", pub.ismenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫八门状态描述", pub.ismenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫八门状态描述", pub.ismenHeju(zhishigong)[1]);
		
		params.put("用神宫八神状态描述", pub.isshenHeju(pub.getYSTgong())[1]);		
		params.put("日宫八神状态描述", pub.isshenHeju(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);		
		params.put("时宫八神状态描述", pub.isshenHeju(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫九星旺衰描述", pub.getxingWS(zhishigong)[1]);
		
		params.put("用神宫得奇描述", pub.getSanji(pub.getYSTgong())[1]);
		params.put("日宫得奇描述", pub.getSanji(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫得奇描述", pub.getSanji(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫得奇描述", pub.getSanji(zhishigong)[1]);
		
		params.put("用神宫八门吉凶描述", pub.getmenJX(pub.getYSTgong())[1]);
		params.put("日宫八门吉凶描述", pub.getmenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫八门吉凶描述", pub.getmenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫八门吉凶描述", pub.getmenJX(zhishigong)[1]);
		
		params.put("用神宫八神吉凶描述", pub.getshenJX(pub.getYSTgong())[1]);
		params.put("日宫八神吉凶描述", pub.getshenJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫八神吉凶描述", pub.getshenJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫八神吉凶描述", pub.getshenJX(zhishigong)[1]);
		
		params.put("用神宫九星吉凶描述", pub.getxingJX(pub.getYSTgong())[1]);
		params.put("日宫九星吉凶描述", pub.getxingJX(pub.getTianGong(SiZhu.rg, SiZhu.rz))[1]);
		params.put("时宫九星吉凶描述", pub.getxingJX(pub.getTianGong(SiZhu.sg, SiZhu.sz))[1]);
		params.put("值使宫九星吉凶描述", pub.getxingJX(zhishigong)[1]);
	}
	
	/**
	 * 将常量与变量分别加载
	 * @param params
	 */
	public Map<String,Object> getInputParam() {
		Map<String,Object> customParams = new HashMap<String,Object>();
		customParams.putAll(params);
		initVar1(customParams);
		initVar2(customParams);
		initVar3(customParams);
		initVar4(customParams);
		initVar5(customParams);
		
		return customParams;
	}
}
