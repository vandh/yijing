package org.boc.biz.qm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.boc.dao.qm.DaoQiMen;
import org.boc.db.YiJing;
import org.boc.db.qm.QimenPublic;

public class QMFunction {
	private QimenPublic pub;
	private DaoQiMen daoqm;
	private static Map<String, Method> methodMap = new HashMap<String, Method>();;
	
	public QMFunction(QimenPublic pub) {
		this.pub = pub;
		if(pub!=null) daoqm = pub.getDaoQiMen();		
		setMethod();
	}
	/**
	 * 将所有方法放在缓存里存起来
	 */
	private void setMethod() {
		try {
			Method[] methods = getClass().getMethods();
			for(Method method : methods) {
				methodMap.put(method.getName(), method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<String, Method> getMethods() {
		return this.methodMap;
	}
	/**
	 * 由方法名返回方法
	 */
	public Method getMethod(String name) {
		return methodMap.get(name);
	}
	
	//1. g1与g2宫五行是否比和
	public boolean 二宫比和(int g1, int g2) {
		return pub.isBihe(g1, g2);
	}
	//1. g1宫五行是否生g2宫
	public boolean 二宫相生(int g1, int g2) {
		return pub.isGongSheng(g1, g2);
	}
	//2. g1生g2与g2生g1
	public boolean 二宫互生(int g1, int g2) {
		return pub.isSheng2(g1, g2);
	}
	//3. g1宫五行是否克g2宫
	public boolean 二宫相克(int g1, int g2) {
		return pub.isGongke(g1, g2);
	}
	//4. g1克g2与g2克g1
	public boolean 二宫互克(int g1, int g2) {
		return pub.isGongke2(g1, g2);
	}
	//5. g1与g2是否相冲
	public boolean 二宫相冲(int g1, int g2) {
		return pub.isGongChong(g1, g2);
	}	
	//6. 判断g1宫是否冲克g2宫
	public boolean 二宫冲克(int g1, int g2) {
		return pub.isChongke(g1, g2);
	}	
	//本宫地支是否相合
	public boolean 二宫地支相合(int g1, int g2) {
		return pub.isGongLiuhe(g1, g2);
	}	
	
	/////////////////////////////////////////////////////////////
	//7. 是否本宫空亡，因空亡宫可能有二个
	public boolean 本宫空亡(int g1) {
		return pub.isKong(g1);
	}
	//8. 是否本宫马星，因马宫可能有二个
	public boolean 本宫马星(int g1) {
		return pub.isYima(g1);
	}
	//判断天盘干与地盘干、天盘干与落宫、地盘干与落宫是否击刑
	public boolean 本宫击刑(int gong){
		return pub.isJixing(gong);
	}
	//9. 是否本宫空亡对冲之宫
	public boolean 空亡对冲本宫(int g1) {
		return pub.isChKong(g1);
	}
	//10. 是否本宫马星，因马宫可能有二个
	public boolean 马星对冲本宫(int g1) {
		return pub.isChMa(g1);
	}
	//15. 判断本宫是否是内盘
	public boolean 本宫内盘(int gong){
		return pub.isNenpan(gong);
	}
	//15. 判断本宫是否天克地
	public boolean 本宫天克地(int gong){
		return pub.isTianKeDi(gong);
	}
	//15. 判断本宫是否地克天
	public boolean 本宫地克天(int gong){
		return pub.isDiKeTian(gong);
	}
	//15. 判断本宫是否天生地
	public boolean 本宫天生地(int gong){
		return pub.isTianShengDi(gong);
	}
	//15. 判断本宫是否地生天
	public boolean 本宫地生天(int gong){
		return pub.isDiShengTian(gong);
	}
	//15. 判断本宫是否门制
	public boolean 宫克门(int gong){
		return pub.ismenzhi(gong);
	}
	//15. 判断本宫门迫
	public boolean 门克宫(int gong){
		return pub.ismenpo(gong);
	}
	//15. 判断本宫门相生
	public boolean 宫门相生(int gong){
		return pub.ismensheng(gong);
	}
	public boolean 门入宫墓(int men){
		return pub.ismenmu(men);
	}
	//15. 判断本宫
	public boolean 宫克星(int gong){
		return YiJing.WXDANKE[daoqm.gInt[4][2][gong]][daoqm.gInt[2][2][gong]]==1;  
	}
	//15. 判断本宫
	public boolean 星克宫(int gong){
		return YiJing.WXDANKE[daoqm.gInt[2][2][gong]][daoqm.gInt[4][2][gong]]==1; 
	}
	//15. 判断本宫
	public boolean 宫星相生(int gong){
		return YiJing.WXXIANGSHENG[daoqm.gInt[4][2][gong]][daoqm.gInt[2][2][gong]]==1; 
	}
//15. 判断本宫
	public boolean 宫克干(int gong){
		return YiJing.WXDANKE[daoqm.gInt[4][2][gong]][daoqm.gInt[2][4][gong]]==1;  
	}
	//15. 判断本宫
	public boolean 干克宫(int gong){
		return YiJing.WXDANKE[daoqm.gInt[2][4][gong]][daoqm.gInt[4][2][gong]]==1; 
	}
	//15. 判断本宫
	public boolean 宫干相生(int gong){
		return YiJing.WXXIANGSHENG[daoqm.gInt[4][2][gong]][daoqm.gInt[2][4][gong]]==1; 
	}
	//本宫十干克应吉凶
	public boolean 本宫吉格(int gong){
		return pub.getJixiongge(gong)[0].equals("1");
	}
	//本宫吉门
	public boolean 本宫吉门(int g1) {
		return pub.getmenJX(g1)[0].equals("1");
	}
	public boolean 本宫凶门(int g1) {
		return pub.getmenJX(g1)[0].equals("-1");
	}
	//本宫吉神
	public boolean 本宫吉神(int g1) {
		return pub.getshenJX(g1)[0].equals("1");
	}
	public boolean 本宫凶神(int g1) {
		return pub.getshenJX(g1)[0].equals("-1");
	}
	//本宫吉星
	public boolean 本宫吉星(int g1) {
		return pub.getxingJX(g1)[0].equals("1");
	}	
	public boolean 本宫凶星(int g1) {
		return pub.getxingJX(g1)[0].equals("-1");
	}
	//本宫在月令旺衰
	public boolean 本宫旺相(int gong){
		return pub.getGongWS(gong)[0].equals("1");
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////是以宫还是干星神为参数，看日后应用，不能重载，要写多个方法///////////////////
	//11. 是否天盘干落宫死墓绝
	public boolean 天盘干死墓绝(int gong) {
		return pub.isTGanSMJ(gong);
	}
	//12. 是否地盘干落宫死墓绝
	public boolean 地盘干死墓绝(int gong) {
		return pub.isDGanSMJ(gong);
	}
	//13. 是否天盘干入墓
	public boolean 天盘干入墓(int gong) {
		return pub.isTGanMu(gong);
	}
	//14. 是否地盘干入墓
	public boolean 地盘干入墓(int gong) {
		return pub.isDGanMu(gong);
	}
	//判断本宫天盘干落宫为桃花
	public boolean 天盘干桃花(int gong){
		return pub.isTpTaohua(gong);
	}
	//判断本宫地盘干落宫为桃花
	public boolean 地盘干桃花(int gong){
		return pub.isDpTaohua(gong);
	}
	//判断本宫天盘干与宫、地盘干是否六仪击刑
	public boolean 天盘干击刑(int gong){
		return pub.isTpJixing(gong);
	}	
	//判断本宫地盘干与宫、天盘干是否六仪击刑
	public boolean 地盘干击刑(int gong){
		return pub.isDpJixing(gong);
	}
	//判断本宫天盘干与宫是否相冲
	public boolean 天盘干逢冲(int gong){
		return pub.isTChong(gong);
	}	
	//判断本宫地盘干与宫是否相冲
	public boolean 地盘干逢冲(int gong){
		return pub.isDGChong(gong);
	}
	//判断本宫天盘干与宫、地盘干是否半合、三合、六合
	public boolean 天盘干逢合(int gong){
		return pub.isTDGanHe(gong) || pub.isTDG3He(gong) || pub.isTDZi3He(gong) ||
					 pub.isTG3He(gong) || pub.isTG6He(gong);
	}	
	//判断本宫地盘干与宫、天盘干是否半合、三合、六合
	public boolean 地盘干逢合(int gong){
		return pub.isTDGanHe(gong) || pub.isTDZi3He(gong) ||
					 pub.isDG3He(gong) || pub.isDG6He(gong);
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////
	//是否是阳干
	public boolean 阳干(int gan) {
		return pub.isYangGan(gan);
	}
	//是否五阳干
	public boolean 五阳干(int gan) {
		return pub.is5YangGan(gan);
	}
	//11. 是否天盘干旺相
	public boolean 天干旺相(int gan) {
		return pub.gettgWS(gan)[0].equals("1");
	}
	//判断九星旺衰
	public boolean 九星旺相(int xing) {
		return pub.getxingWS(pub.getXingGong(xing))[0].equals("1");
	}
	//判断八门旺衰
	public boolean 八门旺相(int men) {
		return pub.getmenWS(pub.getMenGong(men))[0].equals("1");
	}
	
	
	
	
	
	public static void main(String[] args) {
		QMFunction function = new QMFunction(null);
		Method method = function.getMethod("二宫五行相生");
		System.out.println(method.getName());
	}
}
