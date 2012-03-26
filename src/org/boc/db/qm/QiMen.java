package org.boc.db.qm;

import org.boc.db.YiJing;

public class QiMen {
	public static final String FUHAOYI="\\|";   //｜符号
	public static final String FUHAODOT=",";   //,符号
	public static final String DUN="、";   //、顿号
	public static final String wxxqs1="旺";   //五行地月令十二地支的状态
	public static final String wxxqs2="相";  //旺相休囚死废
	public static final String wxxqs3="休";
	public static final String wxxqs4="囚";
	public static final String wxxqs5="死";
	public static final String wxxqs6="废";
  
	public static final String zphym1="制";   //八门与宫的状态
	public static final String zphym2="迫";
	public static final String zphym3="和";
	public static final String zphym4="义";
	public static final String zphym5="墓";
	public static final String zphym6="旺";
	
	//各宫所主身体部位,内与外
	public static final String[] JGBUWEINEI = {"","肾脏、膀胱、内分泌系统","脾胃、食道、胰脏等消化系统","肝胆、左肺等",
		"肝胆、血管、经络、气血等",	"","脊髓、筋骨、大肠","肺部、气管等","肠胃等消化系统","心脏、心血管、脑血管等"};
	public static final String[] JGBUWEIWAI = {"","外阴部","右耳、右臂、右手、肌肤","左肋、左腰，足部",
		"左耳、左臂、头发","","右腿、右脚、头部","右肋、右腰，嘴、舌头、牙齿","左腿、左脚，鼻子","头、眼、面部"};
	public static final String[] TIANGANNEI = {"","胆脏","肝脏","小肠","心脏、血液","胃、胰腺","脾脏","大肠、肿瘤、癌症","肺、颗粒、小瘤、精子","膀胱、妇科","肾、糖尿、妇科"};
	public static final String[] TIANGANWAI = {"","头部","肩部","额部","牙齿","鼻子、面部","鼻子、面部","筋络、癌症","胸部","腿部","脚部"};
	public static final String[] BIGNUM = {"","一","二","三","四","五","六","七","八","九","十"};
	
	//九宫所包括的方位
	public static final String[] JIUGONGBUWEI = {"","下身衣兜里","上衣上口袋","上衣下口袋","上衣上口袋","","裤兜","上衣下口袋","裤兜","上衣口袋"};
	public static final String[] JIUGONGFXIANG = {"","北方","西南方","东方","东南方","本地","西北方","西方","东北方","南方"};
	public static final String[] JIUGONGPEOPLE = {"","中年男子","老年妇女","成年男子","成年女子","本地","老年男子","小姑娘","小伙子","中年妇女"};
	public static final String[] JIUGONGPEOPLE2 = {"","中男","母亲","长男","长女","","父亲","少女","少男","中女"};
	//九宫所含后天八卦数1、先天八卦数2、五行之数3,4断
	public static final int[][] JIUGONGSHU = new int[10][5];
	static{
		JIUGONGSHU[1] = new int[]{0,1,6,1,6};
		JIUGONGSHU[2] = new int[]{0,2,8,5,10};
		JIUGONGSHU[3] = new int[]{0,3,4,3,8};
		JIUGONGSHU[4] = new int[]{0,4,5,3,8};
		JIUGONGSHU[5] = new int[]{0,0,0,0,0};
		JIUGONGSHU[6] = new int[]{0,6,1,4,9};
		JIUGONGSHU[7] = new int[]{0,7,2,4,9};
		JIUGONGSHU[8] = new int[]{0,8,7,5,10};
		JIUGONGSHU[9] = new int[]{0,9,3,2,7};
	}
	
	 //八门编号,{1休,  8生, 3伤, 4杜, 9景, 2死, 7惊, 6开}
	public static final int MENXIU=1,MENSHENG=8,MENSHANG=3,	MENDU=4,MENZHONG=5,MENJING3=9,MENSI=2,MENJING1=7,MENKAI=6; 
  //九星编号, 蓬1、芮2、冲3、辅4、禽5、心6、柱7、任8、英9
	public static final int XINGPENG=1,XINGRUI=2,XINGCHONG=3,XINGFU=4,XINGQIN=5,XINGXIN=6,XINGZHU=7,XINGREN=8,XINGYING=9;
	//八神编号，符1、蛇2、阴3、六4、白5、玄6、地7、天8
	public static final int SHENFU=1,SHENSHE=2,SHENYIN=3,SHENHE=4,SHENHU=5,SHENWU=6,SHENDI=7,SHENTIAN=8;
	
  //三元
  public static final String[] SANYUANNAME = new String[] {
      "", "上元", "中元", "下元"};
  //地支属何卦
  public static final int[] ziOfGua = {0, 1, 8,8, 3, 4,4, 9, 2,2, 7, 6,6};
  //一卦有何支
  public static final String[] guaOfZi2 = {"","子","未、申","卯","辰、巳","","戌、亥","酉","丑、寅","午"};
  //24节气
  public static final String[] JIEQI24 =
      new String[] {
      "", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨",
      "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
      "立秋", "处暑", "白露", "秋分", "寒露", "霜降",
      "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"};
  //节气阴阳
  public static final int[] jieqiyy = {
      0, 1, 1, 1, 1, 1, 1,
      1, 1, 1, -1, -1, -1,
      -1, -1, -1, -1, -1, -1,
      -1, -1, -1, 1, 1, 1};
  //每个节气上元头一天的干支不是甲子或甲午，就是己卯或己酉；
  //中元头一天的干支不是甲申或甲寅，就是己巳乙亥；
  //下元头一天的干支不是甲戌或甲辰，就是己丑或己未。
  public static final int[][][] sanyuan = new int[4][11][13];
  //24节气三元阴阳遁
  public static final int[][] yydun = new int[25][4];
  public static final String[] yydunju = {"  ", "阳遁一局", "阳遁二局", "阳遁三局", "阳遁四局", "阳遁五局", "阳遁六局",
      "阳遁七局", "阳遁八局", "阳遁九局", "阴遁一局", "阴遁二局", "阴遁三局",
      "阴遁四局", "阴遁五局", "阴遁六局", "阴遁七局", "阴遁八局", "阴遁九局"};
  //用神选择
  public static final String[] yongshen = {"  ", "年干","月干","日干","时干",
  	"休门","死门","伤门","杜门","开门","惊门","生门","景门",
  	"第一宫","第二宫","第三宫","第四宫","第五宫","第六宫","第七宫","第八宫","第九宫"};
  //规则定制
  public static final int ZSMY = 1;
  public static final int JDYQ = 2;
  public static final int SWBD = 3;
  public static final int LAHY = 4;
  public static final int QXKS = 5;
  public static final int GZJY = 6;
  public static final int JYQC = 7;
  public static final int CXCG = 8;
  public static final int RTJB = 9;
  public static final int TSQX = 10;
  public static final int DLFS = 11;
  public static final int XRZS = 12;
  public static final int HYFW = 13;
  public static final int TYJS = 14;
  public static final String[] rules = {"  ", "终生命运","近段运气","失物被盗","恋爱婚姻",
  	"求学考试","工作就业","经营求财","出行出国","人体疾病","天时气象","地理风水","行人走失","怀孕分娩","体育竞赛"};
  static {
    sanyuan[1][1][1] = sanyuan[1][1][7] = sanyuan[1][6][4] = sanyuan[1][6][10] = 1;
    sanyuan[2][1][3] = sanyuan[2][1][9] = sanyuan[2][6][6] = sanyuan[2][6][12] = 1;
    sanyuan[3][1][5] = sanyuan[3][1][11] = sanyuan[3][6][2] = sanyuan[3][6][8] = 1;

    yydun[1] = new int[] {  0, 8, 5, 2};  //立春
    yydun[2] = new int[] {  0, 9, 6, 3};
    yydun[3] = new int[] {  0, 1, 7, 4};
    yydun[4] = new int[] {  0, 3, 9, 6};
    yydun[5] = new int[] { 0, 4, 1, 7};
    yydun[6] = new int[] { 0, 5, 2, 8};
    yydun[7] = new int[] { 0, 4, 1, 7};    
    yydun[8] = new int[] { 0, 5, 2, 8};
    yydun[9] = new int[] { 0, 6, 3, 9};
    yydun[10] = new int[] {  0, -9, -3, -6};  //夏至
    yydun[15] = new int[] { 0, -9, -3, -6};   //白露
    yydun[11] = new int[] { 0, -8, -2, -5};   //小暑
    yydun[12] = new int[] { 0, -7, -1, -4};   //大暑
    yydun[16] = new int[] { 0, -7, -1, -4};   //秋分
    yydun[13] = new int[] { 0, -2, -5, -8};   //立秋
    yydun[14] = new int[] { 0, -1, -4, -7};   //处暑
    yydun[17] = new int[] { 0, -6, -9, -3};   //寒露", "霜降",
    yydun[19] = new int[] {  0, -6, -9, -3};  //立冬", "小雪", "大雪
    yydun[18] = new int[] { 0, -5, -8, -2};
    yydun[20] = new int[] { 0, -5, -8, -2};
    yydun[21] = new int[] { 0, -4, -7, -1};
    yydun[22] = new int[] { 0, 1, 7, 4};     //冬至
    yydun[23] = new int[] { 0, 2, 8, 5};     //小寒
    yydun[24] = new int[] { 0, 3, 9, 6};     //大寒
  }

  //九宫顺序
  public static final int[] jgxh = {1,8,3,4,9,2,7,6};
  //九宫八卦，后天八卦数
  public static final int[] jgbg = {0,6,8,4,5,0,1,8,2,3};
  //九宫藏支
  public static final int[] jgdz = {0,1,809,4,506,0,1112,10,203,7};
  //九宫二十四节气{3i-2,3i-1,3i}
  public static final int[] jgjq = {0,22,23,24,13,14,15,4,5,6,7,8,9,0,0,0,19,20,21,16,17,18,1,2,3,10,11,12};
  //九宫五行
  public static final int[] jgwh = {0, YiJing.SHUI,YiJing.TU,YiJing.MU, YiJing.MU, YiJing.TU, YiJing.JIN,YiJing.JIN,YiJing.TU,YiJing.HUO};
  //九宫名称
  public static final String[] dpjg = {"","坎一","坤二","震三","巽四","中五","乾六","兑七","艮八","离九",};
  //戊、己、庚、辛、壬、癸、丁、丙、乙，三奇六仪对应：1、2、3、4、5、6、7、8、9；无论顺逆皆是此顺序
  public static final String[] sjly1 = { "", "戊", "己", "庚", "辛", "壬", "癸", "丁", "丙", "乙"};
  public static final int[] sjly2 = {0,0,9,8,7,1,2,3,4,5,6}; //此为天干与三奇六仪数对应
  public static final int[] sjly3 = {0,0,2,3,4,5,6,7,8,9}; //此为三奇六仪次序数
  public static final int[] sjly4 = {0,1,11,9,7,5,3,0,0,0,0,0}; //此为六仪所藏六甲地支,以六仪顺序数为准
  public static final int[] sjly5 = {0,5,6,7,8,9,10,4,3,2,0}; //此为三奇六仪数与天干对应
  //休、死、伤、杜、中、开、惊、生、景，9门对应：1、2、3、4、5、6、7、8、9； //中门寄坤二宫为死门  
  public static final String[] bm1 = {"", "休", "死", "伤", "杜","中", "开", "惊", "生", "景"};
  public static final int[] bm2 = {0, 1, 8, 3, 4, 9, 2, 7, 6}; //８门的排列次序：休、生、伤、杜、景、死、惊、开，依次环排８宫；
  public static final int[] bm3 = {0, YiJing.SHUI,YiJing.TU,YiJing.MU,YiJing.MU,YiJing.TU,YiJing.JIN,YiJing.JIN,YiJing.TU,YiJing.HUO};
  public static final int[] dpbm4 = {0, 1, 8, 3, 4, 5, 9, 2, 7, 6};
  public static final int[] bmjx = {0,1,-1,-1,0,-1,1,-1,1,0}; //八门吉凶,1吉0平-1凶
  
  ///////////simon 2011-11-16/////////////////////
  //门+门中实际数组的顺序                 0  1休1、    2生8、   3伤3、 4杜4、 5  6景9、  7死2、    8惊7、  9开6
  public static final int[] dp_mengong_mc = {0, 1, 7, 3, 4, 0, 9, 8, 2, 6};  //将地盘门转换成门1+门2中的门2顺序
  public static final int[] dp_menxing_mc = {0, 1, 7, 3, 4, 0, 9, 8, 2, 6};  //将天盘门转成星+门中的天盘门顺序
  ///////////////////////////////////////////////
  
  //蓬、芮、冲、辅、禽、心、柱、任、英，9星对应：1、2、3、4、5、6、7、8、9； 天禽心即禽芮
  //９星的排列次序：蓬、任、冲、辅、英、芮（禽）、柱、心，依次环排８宫；
  public static final String[] jx1 = { "", "蓬", "芮", "冲", "辅", "禽", "心", "柱", "任", "英"};
  public static final int[] jx2 = {0,1,8,3,4,9,2,7,6};
  public static final int[] jx3 = {0, YiJing.SHUI,YiJing.TU,YiJing.MU,YiJing.MU,YiJing.TU,YiJing.JIN,YiJing.JIN,YiJing.TU,YiJing.HUO};
  public static final int[] dpjx4 = {0,1,8,3,4,5,9,2,7,6}; //地盘对应九星顺序
  public static final int[] jxjx = {0,-1,-1,0,1,1,1,-1,1,0}; //九星吉凶,1吉0平-1凶
  
  //符、蛇、阴、六、白、玄、地、天，8神对应：1、2、3、4、5、6、7、8。
  //符、蛇、阴、六、白、玄、地、天，８神的排列次序,依次环排８宫。
  public static final String[] bs1 = { "", "符","蛇","阴","合","虎","武","地","天"};//{ "", "值符", "腾蛇", "太阴", "六合", "白虎", "玄武", "九地", "九天"};//阳遁
  public static final String[] bs2 = { "", "值符","腾蛇","太阴","六合","白虎","玄武","九地","九天"}; //阴遁
  public static final int[] bs3 = {0, YiJing.TU, YiJing.HUO, YiJing.JIN,YiJing.MU,YiJing.JIN, YiJing.SHUI,YiJing.TU, YiJing.JIN}; //八神五行
  public static final int[] bsjx = {0,1,-1,1,1,-1,-1,1,1}; //八神吉凶,1吉0平-1凶
  
  //甲子（戊）旬、甲戌（己）旬、甲申（庚）旬、甲午（辛）旬、甲辰（壬）旬、甲寅（癸）旬；分别对应数字：1，2，3，4，5，6；
  //60甲子共分6旬，每旬分别对应数字：0甲子，10甲戌，8甲申，6甲午，4甲辰，2甲寅  此数即（旬支-旬干+12）%12
  //旬0对应： 旬序数xunshu[0]=1,对应旬名为：xunname[1],旬支为xunzi[0],旬干为xungan[0]
  public static final int[] xunshu = {1, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2}; 
  public static final String[] xunname = {"","甲子(戊)","甲戌(己)","甲申(庚)","甲午(辛)","甲辰(壬)","甲寅(癸)"};
  //得到甲+旬支，如果z-g=0:子1，2：寅3
  public static final int[] xunzi = {1, 0, 3, 0, 5, 0, 7, 0, 9, 0, 11};
  //旬首藏干，即甲子对应的戊
  public static final int[] xungan = {5, 0, 10, 0, 9, 0, 8, 0, 7, 0, 6};
  //十天干分别对应1 至 10 的数字；
  //十二地支分别对应：1 到 12 的数字；
  public static final int[] fpjg = 		{0,1,2,3,4,5,6,7,8,9}; //飞盘九宫
  public static final String[] fpjx =  { "", "蓬", "芮", "冲", "辅", "禽", "心", "柱", "任", "英"}; //飞盘九星
  public static final String[] fpjm =  {	"", "休", "死", "伤", "杜","中", "开", "惊", "生", "景"};		//飞盘九门
  public static final String[] fpjs1 = { "", "符","蛇",	"阴",		"合","勾",	"常","雀",	"地","天"};		//九神阳局
  public static final String[] fpjs2 = { "", "符","蛇",	"阴",		"合","虎",	"常","武",	"地","天"};  //九神阴局
  //九神五行
  public static final int[] fpjswx = {0, YiJing.TU, YiJing.HUO, YiJing.JIN,YiJing.MU,YiJing.JIN, YiJing.TU, YiJing.SHUI,YiJing.TU, YiJing.JIN}; //八神五行
  //九星五行
  public static final int[] fpjxwx = {0, YiJing.SHUI,YiJing.TU,YiJing.MU,YiJing.MU,YiJing.TU,YiJing.JIN,YiJing.JIN,YiJing.TU,YiJing.HUO};
  //九门五行
  public static final int[] fpjmwx = {0, YiJing.SHUI,YiJing.TU,YiJing.MU,YiJing.MU,YiJing.TU,YiJing.JIN,YiJing.JIN,YiJing.TU,YiJing.HUO};

  /**
   * 大格局的吉格、凶格：日干+时干，da_gan_gan[][]只要不为0，即为成格；
   * 1-20
   */
  public static final int[][] da_gan_gan = new int[11][11];
  static{
  	da_gan_gan[1][1] = da_gan_gan[6][1] = 1;	
  	da_gan_gan[2][1] = da_gan_gan[7][1] = 1;
  	da_gan_gan[3][1] = da_gan_gan[8][1] = 1;
  	da_gan_gan[4][1] = da_gan_gan[9][1] = 1;
  	da_gan_gan[5][1] = da_gan_gan[10][1] = 1;
  	
  	da_gan_gan[1][7] = da_gan_gan[2][8] = 2;
  	da_gan_gan[3][9] = da_gan_gan[4][10] = 2;
  	da_gan_gan[5][1] = da_gan_gan[6][2] = 2;
  	da_gan_gan[7][3] = da_gan_gan[8][4] = 2;
  	da_gan_gan[9][5] = da_gan_gan[10][6] = 2;
  }
  
  /**
   * 大格局的凶格：八门+九宫，八门伏呤、反呤
   * 坎1宫，离9宫，休门1，景门9
   */
  public static final int[][] da_men_gong = new int[10][2];
  static{
  	da_men_gong[1][1] = 3; da_men_gong[9][1] = 4;
  }
  /**
   * 大格局的凶格：九星伏呤、反呤
   */
  public static final int[][] da_xing_gong = new int[10][2];
  static{
  	da_xing_gong[1][1] = 5; da_xing_gong[9][1] = 6;
  }
  
  /**
   * 宫在月令的旺衰，宫+月令地支（子1亥12）
   */
  public static final String[][] gong_yue = new String[11][13];  
  static{
  	gong_yue[1] = new String[]{"",wxxqs1,wxxqs5,wxxqs3,wxxqs3,wxxqs5,wxxqs4,wxxqs4,wxxqs5,wxxqs2,wxxqs2,wxxqs5,wxxqs1}; 
  	gong_yue[2]=gong_yue[8] = new String[]{"",wxxqs4,wxxqs1,wxxqs5,wxxqs5,wxxqs1,wxxqs2,wxxqs2,wxxqs1,wxxqs3,wxxqs3,wxxqs1,wxxqs4};
  	gong_yue[3]=gong_yue[4] = new String[]{"",wxxqs2,wxxqs4,wxxqs1,wxxqs1,wxxqs4,wxxqs3,wxxqs3,wxxqs4,wxxqs5,wxxqs5,wxxqs4,wxxqs2};
  	gong_yue[5] = new String[]{"","  ","  ","  ","  ","  ","  ","  ","  ","  ","  ","  ","  "};
  	gong_yue[6]=gong_yue[7] = new String[]{"",wxxqs3,wxxqs2,wxxqs4,wxxqs4,wxxqs2,wxxqs5,wxxqs5,wxxqs2,wxxqs1,wxxqs1,wxxqs2,wxxqs3};
  	gong_yue[9] = new String[]{"",wxxqs5,wxxqs3,wxxqs2,wxxqs2,wxxqs3,wxxqs1,wxxqs1,wxxqs3,wxxqs4,wxxqs4,wxxqs3,wxxqs5};
  }
  /**
   * 干在月令十二地支的旺衰
   * SiZhu.TGSWSJNAME[SiZhu.TGSWSJ[]]
   */
  /**
   * 干在宫所含地支的旺衰
   * 死墓绝=-1为无气，衰病=0为稍有气，胎养=-2，其它=1为旺相
   */
  public static final int[][] gan_gong_wx3 = new int[11][10];
  static{
  	gan_gong_wx3[1] = new int[]{0,1,-1,1,0,0,2,-2,1,-1}; //胎养
  	gan_gong_wx3[2] = new int[]{0,0,-2,1,1,0,-1,-1,1,1}; //胎养
  	gan_gong_wx3[3] = gan_gong_wx3[5] =new int[]{0,-2,0,1,1,0,-1,-1,1,1};
  	gan_gong_wx3[4] = gan_gong_wx3[6] = new int[]{0,-1,1,0,1,0,-2,1,-1,1};
  	gan_gong_wx3[7] = new int[]{0,-1,1,-2,1,0,0,1,-1,1};
  	gan_gong_wx3[8] = new int[]{0,1,1,-1,-1,0,1,1,-2,0};
  	gan_gong_wx3[9] = new int[]{0,1,1,-1,-1,0,1,1,0,-2};
  	gan_gong_wx3[10] = new int[]{0,1,-1,1,-2,0,1,0,1,-1};
  }
  public static final String[][] gan_gong_wx = new String[11][10];
  static{
  	gan_gong_wx[1] = new String[]{"","沐","墓绝","帝旺","衰病","","长养","胎","官冠","死"}; //胎养
  	gan_gong_wx[2] = new String[]{"","病","养","临官","沐冠","","死墓","绝","帝旺","长生"}; //胎养
  	gan_gong_wx[3] = gan_gong_wx[5] =new String[]{"","胎","衰病","沐浴","官冠","","墓绝","死","长养","帝旺"};
  	gan_gong_wx[4] = gan_gong_wx[6] = new String[]{"","绝","沐冠","病","帝旺","","胎养","长生","死墓","临官"};
  	gan_gong_wx[7] = new String[]{"","死","官冠","胎","长养","","病衰","帝旺","墓绝","沐浴"};
  	gan_gong_wx[8] = new String[]{"","长生","帝旺","绝","死墓","","沐冠","临官","胎养","病"};
  	gan_gong_wx[9] = new String[]{"","帝旺","长养","死","墓绝","","官冠","沐浴","衰病","胎"};
  	gan_gong_wx[10] = new String[]{"","临官","死墓","长生","胎养","","帝旺","病","沐冠","绝"};
  }
  public static final String[][] gan_gong_wx2 = new String[11][10];
  static{
  	gan_gong_wx2[1] = new String[]{"","沐","墓","刃","病","  ","生","胎","冠","死"}; //胎养
  	gan_gong_wx2[2] = new String[]{"","病","养","禄","冠","  ","墓","绝","刃","生"}; //胎养
  	gan_gong_wx2[3] = gan_gong_wx2[5] =new String[]{"","胎","病","沐","冠","  ","墓","死","生","刃"};
  	gan_gong_wx2[4] = gan_gong_wx2[6] = new String[]{"","绝","冠","病","刃","  ","胎","生","墓","禄"};
  	gan_gong_wx2[7] = new String[]{"","死","冠","胎","生","  ","病","刃","墓","沐"};
  	gan_gong_wx2[8] = new String[]{"","生","刃","绝","墓","  ","冠","禄","胎","病"};
  	gan_gong_wx2[9] = new String[]{"","刃","生","死","墓","  ","冠","沐","病","胎"};
  	gan_gong_wx2[10] = new String[]{"","禄","墓","生","胎","  ","刃","病","冠","绝"};
  }
  // 天盘干在宫中是否是死
  //2在6宫为死墓，46在8宫死墓，8在4宫死墓，10在2死墓
  public static final String[][] gan_gong_si = new String[11][10];
  static{
  	gan_gong_si[1][9]=gan_gong_si[2][6]=gan_gong_si[3][7]=gan_gong_si[4][8]="死";
  	gan_gong_si[5][7]=gan_gong_si[6][8]=gan_gong_si[7][1]=gan_gong_si[8][4]="死";
  	gan_gong_si[9][3]=gan_gong_si[10][2]="死";
  }
//天盘干在宫中是否是墓
  public static final String[][] gan_gong_mu = new String[11][10];
  static{
  	gan_gong_mu[1][2]=gan_gong_mu[10][2]=gan_gong_mu[3][6]=gan_gong_mu[5][6]="墓";
  	gan_gong_mu[2][6]=gan_gong_mu[7][8]=gan_gong_mu[4][8]=gan_gong_mu[6][8]="墓";
  	gan_gong_mu[9][4]=gan_gong_mu[8][4]=gan_gong_mu[2][2]="墓";
  }
//天盘干在宫中是否是绝
  //1在2为墓绝，2在8为衰绝，35在6宫为墓绝，7在宫8墓绝，9在4宫墓绝，
  public static final String[][] gan_gong_jue = new String[11][10];
  static{
  	gan_gong_jue[1][2]=gan_gong_jue[2][7]=gan_gong_jue[3][6]=gan_gong_jue[4][1]="绝";
  	gan_gong_jue[5][6]=gan_gong_jue[6][1]=gan_gong_jue[7][8]=gan_gong_jue[8][3]="绝";
  	gan_gong_jue[9][4]=gan_gong_jue[10][9]="绝";
  }
  
//天盘干在宫中是否是沐浴之地，即桃花
  public static final String[][] gan_gong_hua = new String[11][10];
  static{
  	gan_gong_hua[1][1]=gan_gong_hua[4][2]=gan_gong_hua[6][2]=gan_gong_hua[3][3]="桃";
  	gan_gong_hua[5][3]=gan_gong_hua[2][4]=gan_gong_hua[8][6]=gan_gong_hua[9][7]="桃";
  	gan_gong_hua[10][8]=gan_gong_hua[7][9]="桃";
  }
  /**
   * 六冲：天盘天干与九宫所含地支相冲，主要有甲子戊+离9；甲寅癸+坤2；甲辰壬+乾6；甲午辛+坎1；甲申庚+艮8；甲戌己+巽4；
   */
  public static final int[][] gan_gong_ch = new int[11][10];
  static{
  	gan_gong_ch[8][1] = gan_gong_ch[5][9] = 450;
  	gan_gong_ch[7][8] =gan_gong_ch[10][2] = 451;
  	gan_gong_ch[6][4]=gan_gong_ch[9][6] = 452;
  	
  }    
  
  /**
   * 星在月令的旺衰
   */
  public static final String[][] xing_yue = new String[10][13];
  static{
  	xing_yue[1] = new String[]{"",wxxqs2,wxxqs4,wxxqs1,wxxqs1,wxxqs4,wxxqs3,wxxqs3,wxxqs4,wxxqs6,wxxqs6,wxxqs4,wxxqs2};
  	xing_yue[2]=xing_yue[5] =xing_yue[8]=new String[]{"",wxxqs3,wxxqs2,wxxqs4,wxxqs4,wxxqs2,wxxqs6,wxxqs6,wxxqs2,wxxqs1,wxxqs1,wxxqs2,wxxqs3};
  	xing_yue[3]=xing_yue[4]=new String[]{"",wxxqs6,wxxqs3,wxxqs2,wxxqs2,wxxqs3,wxxqs1,wxxqs1,wxxqs3,wxxqs4,wxxqs4,wxxqs3,wxxqs6};
  	xing_yue[6] = xing_yue[7] =new String[]{"",wxxqs1,wxxqs6,wxxqs3,wxxqs3,wxxqs6,wxxqs4,wxxqs4,wxxqs6,wxxqs2,wxxqs2,wxxqs6,wxxqs1};
  	xing_yue[9] = new String[]{"",wxxqs4,wxxqs1,wxxqs6,wxxqs6,wxxqs1,wxxqs2,wxxqs2,wxxqs1,wxxqs3,wxxqs3,wxxqs1,wxxqs4};
  }
  /**
   * 星在宫的旺衰
   */
  public static final String[][] xing_gong = new String[10][10];
  static{
  	xing_gong[1] = new String[]{"",wxxqs2,wxxqs4,wxxqs1,wxxqs1,wxxqs4,wxxqs6,wxxqs6,wxxqs4,wxxqs3};
  	xing_gong[2]=xing_gong[5] =xing_gong[8]=new String[]{"",wxxqs3,wxxqs2,wxxqs4,wxxqs4,wxxqs2,wxxqs1,wxxqs1,wxxqs2,wxxqs6};
  	xing_gong[3]=xing_gong[4]=new String[]{"",wxxqs6,wxxqs3,wxxqs2,wxxqs2,wxxqs3,wxxqs4,wxxqs4,wxxqs3,wxxqs1};
  	xing_gong[6] = xing_gong[7] =new String[]{"",wxxqs1,wxxqs6,wxxqs3,wxxqs3,wxxqs6,wxxqs2,wxxqs2,wxxqs6,wxxqs4};
  	xing_gong[9] = new String[]{"",wxxqs4,wxxqs1,wxxqs6,wxxqs6,wxxqs1,wxxqs3,wxxqs3,wxxqs1,wxxqs2};
  }
  /**
   * 门在月令的旺衰
   */
  public static final String[][] men_yue = new String[10][13];
  static{
  	men_yue[1] = new String[]{"",wxxqs1,wxxqs5,wxxqs3,wxxqs3,wxxqs5,wxxqs4,wxxqs4,wxxqs5,wxxqs2,wxxqs2,wxxqs5,wxxqs1};
  	men_yue[2]=men_yue[5] =men_yue[8]=new String[]{"",wxxqs4,wxxqs1,wxxqs5,wxxqs5,wxxqs1,wxxqs2,wxxqs2,wxxqs1,wxxqs3,wxxqs3,wxxqs1,wxxqs4};
  	men_yue[3]=men_yue[4]=new String[]{"",wxxqs2,wxxqs4,wxxqs1,wxxqs1,wxxqs4,wxxqs3,wxxqs3,wxxqs4,wxxqs5,wxxqs5,wxxqs4,wxxqs2};
  	men_yue[6] = men_yue[7] =new String[]{"",wxxqs3,wxxqs2,wxxqs4,wxxqs4,wxxqs2,wxxqs5,wxxqs5,wxxqs2,wxxqs1,wxxqs1,wxxqs2,wxxqs3};
  	men_yue[9] = new String[]{"",wxxqs5,wxxqs3,wxxqs2,wxxqs2,wxxqs3,wxxqs1,wxxqs1,wxxqs3,wxxqs4,wxxqs4,wxxqs3,wxxqs5};
  }
  /**
   * 八门和八宫的关系：制1、迫2、和3、义4、墓5、相6 ；
   * 宫克门为制、门克宫为迫或被迫、门生宫和、宫生门义、墓
   * 反、伏不用判断，一门伏，则八门伏，在大格中断
   */
  public static final String[][] men_gong = new String[10][10];
  static{
  	men_gong[1] = new String[]{"",zphym6,zphym1,zphym3,zphym3+zphym5,"",zphym4,zphym4,zphym1,zphym2};
  	men_gong[2]=men_gong[8]=new String[]{"",zphym2,zphym6,zphym1,zphym1+zphym5,"",zphym3,zphym3,zphym6,zphym4};
  	men_gong[3]=men_gong[4]=new String[]{"",zphym4,zphym2+zphym5,zphym6,zphym6,"",zphym1,zphym1,zphym2,zphym3};
  	men_gong[6] = men_gong[7] =new String[]{"",zphym3,zphym4,zphym2,zphym2,"",zphym6,zphym6,zphym4+zphym5,zphym1};
  	men_gong[9] = new String[]{"",zphym1,zphym3,zphym4,zphym4,"",zphym2+zphym5,zphym2,zphym3,zphym6};
  }
  public static final String[][] men_gong2 = new String[10][10];
  static{
  	men_gong2[1] = new String[]{"",zphym6,zphym1,zphym3,zphym5,"  ",zphym4,zphym4,zphym1,zphym2};
  	men_gong2[2]=men_gong2[8]=new String[]{"",zphym2,zphym6,zphym1,zphym1,"  ",zphym3,zphym3,zphym6,zphym4};
  	men_gong2[3]=men_gong2[4]=new String[]{"",zphym4,zphym2,zphym6,zphym6,"  ",zphym1,zphym1,zphym2,zphym3};
  	men_gong2[5] = new String[]{"",zphym2,zphym6,zphym1,zphym1,zphym6,zphym3,zphym3,zphym6,zphym4};
  	men_gong2[6] = men_gong2[7] =new String[]{"",zphym3,zphym4,zphym2,zphym2,"  ",zphym6,zphym6,zphym5,zphym1};
  	men_gong2[9] = new String[]{"",zphym1,zphym3,zphym4,zphym4,"  ",zphym2,zphym2,zphym3,zphym6};
  }
  
  
  public static final String[][] gGejuDesc = new String[534][3];
  public static final String[][] gGejuDesc2 = new String[534][3];
  public static final String HH="<BR>&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String _HEGE = "逢合格，一般是此人和气，不是问自已的事，看合到谁，合到四时上就是问谁。"+HH+
		"对出行不利，主牵绊，牵扯的事多，最好别一个人去。"+HH+
		"但对合作有利，对单独行动不利，对群体行动有利。"+HH+
		"测婚姻有男女同居之意；"+HH+
		"遇到合格，自己先别动，消极一点比较好。";
  static{
  	//gGejuDesc[0]=new String[]{"",""};
  	gGejuDesc[1]=new String[]{"天辅大吉时","六甲时，此时虽格成伏呤，但由于六甲大将在带班，故不凶反吉，诸事可为，有罪者也能逢赦免；"+HH+
  			"但伏呤主迟，若求速则不达，求速则凶。"};
  	gGejuDesc[2]=new String[]{"五不遇时","时干克日干，必须是阳克阳，阴克阴，四柱中叫七杀；"+HH+
  			"百事皆凶，得奇得门也不可用；"+HH+
  			"测病遇之大凶，表示人将去世；"+HH+
  			"出行出国上任求财婚等用事时遇之，轻者不顺，重者有灾；"+HH+
  			"问事时遇之不一定为凶。"};
  	gGejuDesc[3]=new String[]{"八门伏呤","八门在本宫不动，人和欠佳。值使死+死为最凶；"+HH+
  			"一般多遭破财或死伤人口，凶灾日至，若不遇吉门吉格更凶，否则有救；"+HH+
  			"1. 利主不利客，不宜采取主动，动则破财耗失，伤人伤心；"+HH+
  			"2. 因利主，宜收敛财货、讨债催款，竞赛利主场作战，测某人是否出走或下台为不动之象；"+HH+
  			"3. 时间上主迟主慢；"+HH+
  			"4. 空间上主本地、主内部。"};
  	gGejuDesc[4]=new String[]{"八门反呤","门落到与它相对冲的地盘宫内；"+HH+
  			"一般为凶，主事情反复，遇吉门或三奇为有救，问题不大，否则凶灾即至；"+HH+
  			"1. 利客不利主，如果不努力尽人事，则事情会半途而废；"+HH+
  			"2. 时间上主快主速，空间上主外地外部；"+HH+
  			"3. 主做事速度快，成败易分；"+HH+
  			"4. 不吉一面：近病反呤易愈，久病反呤难痊；测婚不成；求财一场空，无利反亏本；"+HH+
  			"5. 吉的一面：测竞赛客队胜；行人走失可回来；失物可以找回；买商铺遇之也主要抓紧时间去买；案件可破。"};
  	gGejuDesc[5]=new String[]{"九星伏呤","九星在本宫不动，天时不利。值符星蓬+蓬最凶；"+HH+
  			"一般多遭破财或死伤人口，凶灾日至，若不遇吉门吉格更凶，否则有救；"+HH+
  			"1. 利主不利客，不宜采取主动，动则破财耗失，伤人伤心；"+HH+
  			"2. 因利主，宜收敛财货、讨债催款，竞赛利主场作战，测某人是否出走或下台为不动之象；"+HH+
  			"3. 时间上主迟主慢；"+HH+
  			"4. 空间上主本地、主内部。"};
  	gGejuDesc[6]=new String[]{"九星反呤","星落到与它相对冲的地盘宫内；"+HH+
  			"一般为凶，主事情反复，遇吉门或三奇为有救，问题不大，否则凶灾即至；"+HH+
  			"1. 利客不利主，如果不努力尽人事，则事情会半途而废；"+HH+
  			"2. 时间上主快主速，空间上主外地外部；"+HH+
  			"3. 主做事速度快，成败易分；"+HH+
  			"4. 不吉一面：近病反呤易愈，久病反呤难痊；测婚不成；求财一场空，无利反亏本；"+HH+
  			"5. 吉的一面：测竞赛客队胜；行人走失可回来；失物可以找回；买商铺遇之也主要抓紧时间去买；案件可破。"};  	
  	
  	gGejuDesc[7]=new String[]{"时干入墓","时干在宫中地支处墓地，不局限于戊戌、"+HH+"己丑等六个时辰。干在月支为衰则为入墓，在月支旺相则为入库。入墓，凶的成分大，吉事不吉，凶的不凶，无力之象，事情难以成功。"+HH+"入库，则暂时受困，待冲出之时，还可成事。","时墓"};
  	gGejuDesc[8]=new String[]{"时干入库",gGejuDesc[7][1],"时库"};
  	gGejuDesc[9]=new String[]{"岁格","庚/年干｜年干/庚。用事大凶；"+HH+
  			"年、月、日、时分别为用神，用神受阻，自然不吉为凶；"+HH+
  			"但如果用神需要受阻（如破案、失物、行人走失）等反而不凶反吉，主凶犯被擒，失物找到，行为归来。"};
  	gGejuDesc[10]=new String[]{"月格","庚/月干｜月干/庚。用事大凶"};
  	gGejuDesc[11]=new String[]{"日格","庚/日干｜日干/庚。主客皆伤，尤不利主。其中庚+日干又称伏干格。"};
  	gGejuDesc[12]=new String[]{"时格","庚/时干｜时干/庚。用事凶"};  	
  	gGejuDesc[13]=new String[]{"飞干格","天盘日干，地盘六庚，交战主客两伤，也主此人不比他人。","飞干"};
  	gGejuDesc[13]=new String[]{"三奇入墓","乙/乾坤、丙/干、丁/艮。因乙阴木墓于戍，五行墓于未。三奇入墓，百事不宜，谋事尽休。凡吉事不吉，凶事不凶，难以发挥其力量，无力之象。","奇墓"};
  	
  	gGejuDesc[24]=new String[]{"伏干格","天盘六庚，地盘日干，交战主客两伤，也主此人不比他人。","伏干"};
  	gGejuDesc[14]=new String[]{"伏宫格","庚加戊，此格大凶，主客皆不利，求人不在，等人不来；"+HH+
  			"出行在路上易遇盗贼，或车折马死，百事不顺。也主此地不比他地；","伏宫"};
  	gGejuDesc[15]=new String[]{"飞宫格","戊加庚，主客皆不利，尢不利客。"+HH+"作战主败亡，大将被擒；做生意破财，必须换地方。也主此地不比他地；","飞宫"};
  	gGejuDesc[16]=new String[]{"悖格","天盘丙加地盘值符，或天盘值符加地盘丙，或丙加年月日时干之上；"+HH+
  			"除得三吉门相会外，均容易出乱子，倒行逆施、纲纪紊乱、难达理想，易出乱臣贼子和叛逆之人。"};
  	gGejuDesc[17]=new String[]{"天网四张","天盘六癸，地盘时干；"+HH+"天网四张不可挡，此时行事有灾殃。"+HH+"若是有人强出者，立便身躯见血光；"+HH+"值坎一至巽四宫，网低可匍匐而出；中五至离九宫，网高难出不用。","天张"};
  	gGejuDesc[18]=new String[]{"地网遮蔽","天盘六壬，地盘时干。不宜出兵，出行大凶。","地蔽"};
  	gGejuDesc[19]=new String[]{"值符伏呤",":六甲值符在本宫不动，以甲午时为辛+辛最凶；"+HH+
  			"六甲之时星门皆伏呤，变为天显时格，也可以行动。","值伏"};
  	gGejuDesc[20]=new String[]{"值符反吟","天盘甲子，地盘甲午；天盘甲戍，地盘甲辰；天盘甲申，地盘甲寅；"+HH+
  			"主灾祸立至，遇奇门无妨。","值反"};
  	gGejuDesc[21]=new String[]{"三奇得使","天盘乙丙丁+值使门。若遇吉格吉门则大吉大利，凶门凶格则不吉。","得使"};
  	gGejuDesc[22]=new String[]{"玉女守门","门盘直使加临地盘丁奇。利婚恋，相会，宴喜娱乐之事。但测婚姻有妻随人行之象。","守门"};
  	gGejuDesc[23]=new String[]{"欢怡","天盘三奇加地盘值符，凡事谋为皆有利，怃恤将士，众情悦服；"+HH+
  			"而丙加值符，为悖格，凶格，悖格之时举事，多倒行逆施，纲纪紊乱，难达理想，多出乱臣贼子和叛逆之人；"+HH+
  			"所以说，丙加值符须会和吉门，才能称为欢怡。"};
  	gGejuDesc[25]=new String[]{"奇格","庚/乙丙丁。庚+乙为合格、庚+丙为贼格、庚+丁为破格，三奇格出行用兵大凶。"};
  	gGejuDesc[26]=new String[]{"三奇受刑","丙丁/壬癸坎，为火入水乡。乙/庚辛乾兑，为木入金乡；"+HH+
  			"因吉的作用不能发挥，不可行动；"+HH+
  			"但还要参考月令和宫的旺衰，不可一律判定。","奇刑"};
  	gGejuDesc[27]=new String[]{"日出扶桑","乙奇临震宫，有禄之乡，是贵人升于乙卯正殿；"+HH+
  			"又为三奇贵人升殿，此格百事可为。","日扶"};
  	gGejuDesc[28]=new String[]{"月照端门","丙奇到离宫，火旺之地，是贵人升于丙午正殿；"+HH+
  			"又为三奇贵人升殿，此格百事可为。","端门"};
  	gGejuDesc[29]=new String[]{"星见西方","丁奇到兑宫，天之神位，丁火长生于酉，是贵人升于丁酉正殿；"+HH+
  			"又为三奇贵人升殿，此格百事可为。","星西"};
  	gGejuDesc[30]=new String[]{"三奇之灵","三奇合四吉神［阴合地天］或合三吉门者，为吉道清灵、用事俱吉。","奇灵"};
  	gGejuDesc[31]=new String[]{"奇游禄位","乙到震、丙到巽、丁到离为本禄之位；"+HH+
  			"合三吉门宜上官赴任、求财祈福等各种谋为都吉利。","禄位"};
  	
  	gGejuDesc[32]=new String[]{"奇合","乙庚、丙辛、丁壬为奇合；"+HH+_HEGE};
  	gGejuDesc[33]=new String[]{"仪合","戊癸、甲己为仪合；"+HH+_HEGE};  	
  	
  	gGejuDesc[36]=new String[]{"驿马","本宫地支为日柱马星；","驲"};  //△
  	gGejuDesc[37]=new String[]{"驿马","本宫地支为时柱马星；","▲"};  //马▲○§■☆★※←↓↑〓
  	gGejuDesc[38]=new String[]{"空亡","本宫地支在日柱落空亡；","昳"};		//○●
  	gGejuDesc[39]=new String[]{"空亡","本宫地支在时柱落空亡；","●"};		//空
  	gGejuDesc[40]=new String[]{"刑","天盘六仪、地盘六仪、宫支之间地支相刑；"+HH+
  			"年月日时干落宫与其四柱地支相刑；"+HH+
  			"六仪击刑极凶，即使六仪为直符，也不可用；"+HH+
  			"一动必有灾伤，遇天网四张格，必被捕捉，有牢役之灾；"+HH+
  			"轻者闹矛盾，破财；重则有病痛、伤灾、牢狱之灾。"};
  	gGejuDesc[41]=new String[]{"冲","天盘与地盘、天盘与宫、地盘与宫、年月日时支与年月日时干落宫均可以构成冲，主速、主快；"+HH+
  			"一般是麻烦事，动荡之事。"+HH+
  			"合作不利，合作就散。"+HH+
  			"单独行动有利，群体行动不利，矛盾大。"+HH+
  			"遇到冲格，主动，积极的，先行动就好。"};
  	gGejuDesc[42]=new String[]{"合","天盘干与地盘干六合，主和解、了结、平局、平分。"+HH+_HEGE};
  	gGejuDesc[43]=new String[]{"匼","天盘、地盘、宫地支三合，主和解、了结、平局、平分。"+HH+_HEGE};
  	gGejuDesc[44]=new String[]{"峇","天盘与地盘地支半合，主和解、了结、平局、平分。"+HH+_HEGE};
  	gGejuDesc[45]=new String[]{"冾","天盘或地盘与落宫地支六合，主和解、了结、平局、平分。"+HH+_HEGE};
  	gGejuDesc[46]=new String[]{"洽","天盘或地盘与落宫地支半合，主和解、了结、平局、平分。"+HH+_HEGE};
  	gGejuDesc[47]=new String[]{"桃花","主桃花运、或梦想做好事。","桃"};
  	gGejuDesc[48]=new String[]{"墓","天干在宫中地支或月令地支处墓地。"};
  	gGejuDesc[49]=new String[]{"库","天干在宫中地支处墓地，但在月令旺相。"};
  	
  	gGejuDesc[50]=new String[]{"日奇伏吟","乙加乙，不宜谒贵求名，只可安分守身；"+HH+
  			"阴干相排斥，逢排斥就没有好事。希望太多，各个虚无缥缈。"+HH+
  			"牵连的事物多，测出行往往走不了。","日伏"};
  	gGejuDesc[51]=new String[]{"奇仪顺遂","乙加丙，吉事为迁官进职；凶事为夫妻离别；"+HH+
  			"乙临吉星本质好能升官，临凶星则作风坏，难免与丙情人搞到一起，出现夫妻别离。"+HH+
  			"机遇多，有贵人扶持。有些好事发生，但当中会有些乱子与麻烦。"};
  	gGejuDesc[52]=new String[]{"奇仪相佐","乙加丁，文书事吉，百事皆可为；"+HH+
  			"最利文书、考试，有机遇奇迹发生。"+HH+
  			"测婚姻不好，男人有第三者或女性是第三者。"};
  	gGejuDesc[53]=new String[]{"利阴害阳","乙加戊，门逢凶迫，财破人伤。"+HH+
  			"乙为阴为地户，戊为阳为天门，即对女人或隐藏性事情有利，对男人或公开的事情不利。"+HH+
  			"吉凶还是看门，门凶、门迫不行。"+HH+
  			"遇到此格，最好的方法是:一是委托女人去做；二是白天不做晚上做(7点以后。卯酉为门户)，这样就能避免破财。"+HH+
  			"遇到此格，不宜主动做事，宜被动的，等事情发生以后再出击。"};
  	gGejuDesc[54]=new String[]{"日奇入墓","乙加己，门吉有救，门凶必凶；"+HH+
  			"乙入甲戌己之墓。如果门凶，事必凶。如果门吉，则有救；"+HH+
  			"不适合女性做事，女性去了就倒霉。适合男性，而且是适合白天做事，不适合晚上做事。"+HH+
  			"测病，查无病因。逢阴干都是被动做事；逢阳干都是主动做事。","日墓"};
  	gGejuDesc[55]=new String[]{"日奇被刑","乙加庚，争讼财产，夫妻怀私。"+HH+
  			"因阴在上阳在下违反常规，故论刑克不论合，往往夫妻不和，为争讼财产各怀私意。"+HH+
  			"合格，对出行不利，但对合作有利；对单独行动不利，对群体行动有利。"+HH+
  			"出行遇合格，主牵绊，牵扯的事多，最好别一个人去。自己做不行，就找合作者。"+HH+
  			"此格有私欲，想达到自己的私欲。测婚姻，有矛盾，各有各的算盘。","日刑"};
  	gGejuDesc[56]=new String[]{"青龙逃走","乙加辛，人亡财破，奴仆拐带，六畜皆伤。"+HH+
  			"测婚遇此格，一般皆为妻子主动离开男方，提出离婚。"+HH+
  			"是个动格，变动的，被动的，有思想活动的事。测疾病要好的快了。测求财要破财了。"+HH+
  			"测工作要变动，是阴配阴，主消积，被动，自已不想走，但犯错误了，不得不走。"};
  	gGejuDesc[57]=new String[]{"日奇入天罗","乙加壬，尊卑悖乱，官讼是非，有人谋害之事。"+HH+
  			"因乙日奇入壬天罗，表示内心自我矛盾，变化，茫然，徘徊，犹豫不决。也表示有人在你背后捣鬼。","入天罗"};
  	gGejuDesc[58]=new String[]{"日奇入地网","乙加癸，宜退不宜进，以躲灾避难为吉。"+HH+
  			"完全退守的意思，是不积极，消极避守的。不要主动出击，要退守，闭关。一出去，就容易撞到网里。"+HH+
  			"遇到乙+癸或癸+乙最适合修炼，打坐，练气功。"+HH+
  			"测终生局，遇到癸+乙或乙+癸，此人可能具有特异功能。因癸为天网，是信息网，能接受宇宙的信息。"+HH+
  			"又癸主微小的变化，有掣肘，是出于私欲的破坏，被人拖了后退。"};

  	gGejuDesc[60]=new String[]{"日月并行","丙加乙，公谋私为皆吉。"+HH+
  			"也是利于合作，合伙，利于求谋和寻求他人的帮助。"};
  	gGejuDesc[61]=new String[]{"月奇悖师","丙加丙，文书逼迫，破耗遗失。"+HH+
  			"火势太大就出乱子，特别容易出现单据票证遗失、不明遗失的情况。"+HH+
  			"由于丙的性格暴躁，办事容易偏激过火，不守常规，主客二个丙到一起，犹如午午自刑一样，更加重了丙奇的缺点，乱套了。"+HH+
  			"也是伏吟，逢伏吟就拖拉，难受，需要提防，防患于未然。","月悖"};
  	gGejuDesc[62]=new String[]{"月奇朱雀","丙加丁，贵人文书吉利，常人平静安乐。"+HH+
  			"因阳在上、阴在下，丙火又名朱雀，丁为星奇，二奇阴阳互补，自然为吉。"+HH+
  			"好事不怕多，多多益善。遇此格，测病不吉，代表炎症。"};
  	gGejuDesc[63]=new String[]{"飞鸟跌穴","丙加戊，奇门第二吉格，百事吉，事业可为，可谋大事。"+HH+
  			"逢门迫墓或刑，吉事成凶。"+HH+
  			"测坏事则不吉，测病遇之，表示病重，可能去世入穴的征兆。"+HH+
  			"利于投资，经商，储存和交易，如同放出去的膺，抓到猎物再回来，表示获得。"+HH+
  			"测求官能提职。但最怕墓迫击刑，遇之容易出问题。"+HH+
  			"入墓想回来回不来。击刑受伤了。门迫，在行为上去做了，但是心理承受能力还没有达到，公司关门了。"};
  	gGejuDesc[64]=new String[]{"大悖入刑","丙加己，囚人刑杖，文书不行，吉门得吉，凶门转凶。"+HH+
  			"因为自己的私利和幻想惹出的麻烦，因为己主私欲，丙主麻烦，丙入戌墓。"+HH+
  			"容易犯小人，但影响力不是太大。"+HH+
  			"由于是阳配阴，阴阳相合，能量守中，就不会有大问题，即使出现一些问题也都能够解决。"};
  	gGejuDesc[65]=new String[]{"荧入太白","丙加庚，门户破败，盗贼耗失，事业亦凶。"+HH+
  			"因丙庚争战不矣，故无论主客，皆不吉。"+HH+
  			"又因奇门多以庚为敌人、仇人、盗贼，今有火克之，故古有“火入金乡贼即去”，又为贼退格，"+HH+
  			"主敌人、仇人、疾病等自动退去，打官司能够消除障碍。"+HH+
  			"临生门反而得财，只是这个得财的方式需要到处跑，如同狗出去找食一样；"+HH+
  			"临戊，则是破财，出行就破财。临死门，也代表破财，死门代表死伤和财产死亡。"+HH+
  			"很多情况下庚是最难以解决的问题，但临上了丙，就容易解决，丁的小火不足以改变庚金的性质。"+HH+
  			"测工作要变动，是自已不想干了，要走。"};
  	gGejuDesc[66]=new String[]{"月奇相合","丙加辛，谋事就成，测病不凶。"+HH+
  			"指在合作中会有一些失误或错误，但是这些问题都能够解决掉。"+HH+
  			"逢合格，必有他人他事牵连牵绊，就不是一件事，会有很多事情搅合在一起。"+HH+
  			"利于合作，利于等待，但不利于出击，出行。"};
  	gGejuDesc[67]=new String[]{"火入天罗","丙加壬，为客不利，是非颇多。"+HH+
  			"阳配阳不和谐，而且五行中水火是最不稳定的。"+HH+
  			"丙壬在一起，就是麻烦多多，是非多多，都是因为外界而不是因为自己的错误造成的。"+HH+
  			"此格，需釜底抽薪，越往前进，乱子事就越多。壬丙相逢是非多。"};
  	gGejuDesc[68]=new String[]{"月奇地网","丙加癸，凡事暧昧不明，阴人、小人害事，灾祸频生。"+HH+
  			"是内部人造成的，要从身边人，内部人找原因，是内因造成的，而丙+壬，是受到了外在的影响。"+HH+
  			"凡事阳的都具有辐射性；凡事阴的都具有内敛和吸收性。丙+癸，是指有人在背后捣鬼。"};
  	
  	gGejuDesc[70]=new String[]{"人遁吉格","丁加乙，贵人加官进爵，常人婚姻财喜。"+HH+
  			"由于丁为玉女，得到乙奇相生，故又名曰“玉女奇生”格。"+HH+
  			"阴配阴，实际上这是一个变化，消极的格，只利于合同，文书，测其他事情都不利。"+HH+
  			"测婚姻更不好，丁乙同宫，另续相亲。"+HH+
  			"测工作，为工作调动，或原有的工作保不住、不理想。"};
  	gGejuDesc[71]=new String[]{"星随月转","丁加丙，贵人越级高升；常人乐中生悲。"+HH+
  			"就是过头的意思，要保持冷静，收敛一些比较好，要忍，小不忍则乱大谋。"};
  	gGejuDesc[72]=new String[]{"奇入太阴","丁加丁，文书即至，喜事遂心，万事如意。"+HH+
  			"是奇门中最吉利的一个格局，群星灿烂。"};
  	gGejuDesc[73]=new String[]{"青龙转光","丁加戊，奇门第四吉格，贵人升迁，常人威昌。"+HH+
  			"也指事物有转机的意思。因甲木生丁火，丁火在上，光明更加显著，"+HH+
  			"所以，诸如求婚、求学、求财、求官等好事会更加顺利。"+HH+
  			"即使平民百姓遇此吉格，也会事业发达、声名卓著。"+HH+
  			"如果遇到入墓或门迫之时，吉的程度会大减，甚至招来是非。","龙转光"};
  	gGejuDesc[74]=new String[]{"火入勾陈","丁加己，主有阴私之事，谋求不利，奸私仇冤，事因女人。"+HH+
  			"己土又名勾陈，己对应地支为丑，为阴火之库。"+HH+
  			"因个人的事情或私人的事情，出现一些意外和问题。"};
  	gGejuDesc[75]=new String[]{"星逢太白","丁加庚，文书阻隔，行人必归。"+HH+
  			"主消息不通，但测外出行人，则受阻必归。"+HH+
  			"也不是特别坏的格局，指遇到了难题，需要回避和退守之意。"+HH+
  			"测去干事，干了但停滞不前，又回到了起点。"+HH+
  			"测婚姻，有情人，但难以离婚(因为行人必归来)。"};
  	gGejuDesc[76]=new String[]{"朱雀入狱","丁加辛，罪人释囚，官人失位。"+HH+
  			"罪人遇丁故释放，官人遇辛则犯罪犯错误，失掉官位，有劳役之灾。"+HH+
  			"丁为希望，辛为错误，让希望变成错误了，这个格局不够好，但临丁奇一般有解救。"};
  	gGejuDesc[77]=new String[]{"奇仪相合","丁加壬，凡事有成，贵人辅助，讼狱公平。"+HH+
  			"测婚遇之，为淫荡之合，苟且之合，但也不能一概而论。"+HH+
  			"也是利于合作，不过这种合作多是非道义的合作，将来会出现问题。"+HH+
  			"不利于出行，出行主牵绊。测婚姻，未婚先同居。"};
  	gGejuDesc[78]=new String[]{"朱雀投江","丁加癸，文书口舌是非，经官动府，词讼不利，音信沉溺不到。"+HH+
  			"出行大忌，特别是走水路或乘飞机，有翻船坠机之事发生。但遇吉星吉神则没有大碍。"+HH+
  			"老出小事的人没有大灾；病秧子，没有大病。"};
  	
  	gGejuDesc[80]=new String[]{"青龙合灵","戊加乙，由门来定吉凶，门吉事吉，门凶事凶。"+HH+
  			"因戊为甲木所遁故，甲加乙故为合。"+HH+
  			"合格，青龙合会，必有他人他事牵连，遇到好的中间人，事情就吉，反之则凶。"+HH+
  			"利于合作和聚集，此格局不利于出行。一个人出行不好，一大帮子朋友一起去比较好。"+HH+
  			"怕墓迫击刑，吉事成凶。测病，有多种病。"};
  	gGejuDesc[81]=new String[]{"青龙返首","戊加丙，奇门第一吉格，为事所谋，大吉大利，若逢迫墓击刑，吉事成凶。"+HH+
  			"测投资，发财，钱出去就回来。"+HH+
  			"但入墓则主破财，击刑主损财，逢冲则钱出去了就没了，戊落巽宫最吉。"+HH+
  			"如测疾病，主病又回来了，主反复。测要债很好。","龙返首"};
  	gGejuDesc[82]=new String[]{"青龙耀明","戊加丁，奇门第三吉格，谒贵求名吉利，若值墓迫，招惹是非；"+HH+
  			"诸如求婚、求学、求官、求财等大吉大利。宜见上级领导、贵人；"+HH+
  			"又由于丁奇主文书、主文明，所以利于求取功名富贵。"+HH+
  			"如果丁奇入墓或遇门迫，则不仅吉的程度大减，甚至会招来是非。"};
  	gGejuDesc[83]=new String[]{"伏吟","戊加戊，凡事不利，道路闭塞，静守为吉；"+
  			HH+"六甲之时问事，可谋为行动。"+
  			HH+"障碍+障碍，遇事困难重重。"+
  			HH+"测投资，为二次重复投资，以预算高一倍。"+
  			HH+"测办事，一次办不成,需要不断的完善和补充才能办成。"+
  			HH+"测婚姻测事，没有动向。"+
  			HH+"为止，出行遇之，堵车。"+
  			HH+"测病，有肿胀的地方，胃胀难受。"};
  	gGejuDesc[84]=new String[]{"贵人入狱","戊加己，公私皆不利，此格破财，打官司，牢狱。"+HH+
  			"因甲子戊入戌之墓库，只有等待冲了墓库之时，才能有转机，这是站在天盘甲子戊为贵人的角度讲的。"+HH+
  			"测投资是短暂的困难，贵人不能老呆在狱中。"+HH+
  			"测终生局，人生会遇到短暂的困难，但是能挺过去。"+HH+
  			"测合伙投资，为各怀鬼胎。"};
  	gGejuDesc[85]=new String[]{"值符飞宫","戊加庚，吉事不吉，凶事更凶。多成多败，此地不比他地。"+HH+
  			"甲子戊为甲遁之地，最怕庚金，唯一出路是值符甲飞离此宫。"+HH+
  			"最忌讳合作、开店与坐地求财。开公司、杂货店、饭店等肯定开不成，"+HH+
  			"测工作肯定做不长，测运气为换了就好须变动才可不变不行，"+HH+
  			"测合作不行主换人，测病为转移，"+HH+
  			"测坟地遇景＋戊＋庚＋腾蛇不能用，主坟换地方，因景为路，故以后这地方要修路。"+HH+
  			"测什么换什么，代表转移，走。"};
  	gGejuDesc[86]=new String[]{"青龙折足","戊加辛，吉门生助，尚可谋为。"+HH+
  			"若逢凶门，主招灾、失财、有足疾、折伤。"+HH+
  			"冲格，主动态型的事，坐不住也留不住，也代表事物半道折损。"+HH+
  			"最忌讳投资，钱上会犯错。疾病为增生之类的病。"+HH+
  			"有经济困难或身陷困境，因为基础没有了，注定破财。"};
  	gGejuDesc[87]=new String[]{"青龙入天牢","戊加壬，凡阴阳皆不吉利。"+HH+
  			"因子水入辰墓，是被困住，长久的困惑之意。"+HH+
  			"忌讳投资。也代表办不成事，运气不好，处在一个低潮期。"+HH+
  			"测疾病，病要走了，是好事。"+HH+
  			"官员遇之不是犯错误，就是退二线或进去了，因青龙代表权位，代表政府官员。"};
  	gGejuDesc[88]=new String[]{"青龙华盖","戊加癸，门吉招福；门凶多乖。"+HH+
  			"合格，利于合作，与别人有牵连或受事物的牵连。"+HH+
  			"测婚姻为年龄差距大。"+HH+
  			"由于甲与癸一阴一阳，水能生木，又戊与癸相合，所以吉凶由门来定，逢吉门可招福临门；逢凶门事多不成。"};
  	
  	gGejuDesc[90]=new String[]{"墓神不明","己加乙，地户蓬星，宜遁迹隐形为利逸。"+HH+
  			"做事不成，看不清方向，还没有头绪。"+HH+
  			"一般主做事拖拉，本身就不是很好的事，却还要去做。"};
  	gGejuDesc[91]=new String[]{"火悖地户","己加丙，男人遇此格，容易冤冤相害，女人遇此格，易被人奸污。"+HH+
  			"容易因为不正确的思想和行为、私心，造成一些麻烦是非。"+HH+
  			"犯小人，且知道小人是谁。"};
  	gGejuDesc[92]=new String[]{"朱雀入墓","己加丁，文状词讼，先曲后直。"+HH+
  			"因地户逢丁奇毕竟为吉，故先凶后吉。"+HH+
  			"事情要在曲中求，婉转的去办。不要主动冒进，要缓一步才能成。主事先曲后直。"};
  	gGejuDesc[93]=new String[]{"犬遇青龙","己加戊，门吉谋望遂意，上人见喜；门凶枉劳心机。"+HH+
  			"有贵人相帮之意，贵人出钱出力。"+HH+
  			"有了施展才华的机遇和新生事物的发生。因甲戌为狗，甲子为龙。"};
  	gGejuDesc[94]=new String[]{"地户逢鬼","己加己，病者必死，百事不遂。不宜有谋为，谋为必凶。"+HH+
  			"比和格又伏吟，百事不顺，主贪欲太强，做事无力，自私心重，幻想多，光做美梦。"};
  	gGejuDesc[95]=new String[]{"利格反名","己加庚，词讼先动者不利，阴星有谋害之情。"+HH+
  			"己想吃掉庚还吃不下去，消化不了，吐又吐不出来，代表难受，别扭，是刑格。"+HH+
  			"不是伤害到心灵就是伤害到肉体。"};
  	gGejuDesc[96]=new String[]{"游魂入墓","己加辛，大人鬼魂，小人家先为祟。"+HH+
  			"主人鬼相侵，住宅易遭阴邪鬼魅作崇，凡事须小心谨慎为妥。"+HH+
  			"容易上当受骗犯小人，而且不知道小人是谁，受人暗算。"+HH+
  			"己代表私欲陷阱，辛代表错误，布置一个陷阱让你犯错误。"+HH+
  			"测房子风水时，一般指房子里有怪异的东西，不干净，有阴气，有鬼。"+HH+
  			"见九地主老鬼，只遇己无辛为空坟。测合作办事，合作伙伴不一定是好人。"};
  	gGejuDesc[97]=new String[]{"地网高张","己加壬，狡童淫女，奸情伤杀。"+HH+
  			"因上下相冲，阴阳冲克。辰戌相冲格，主怄气，生气，争执，冲突。"+HH+
  			"天罡与地煞，谁也不服谁，大了官讼，小了是非口舌。己壬相逢斗讼场。"};
  	gGejuDesc[98]=new String[]{"地刑玄武","己加癸，男女疾病垂危，有词讼囚狱之灾。"+HH+
  			"也是一个容易犯小人，犯口舌的格局。而且容易上当受骗。"+HH+
  			"合作，出行容易招人破坏与受骗。有疾病，凶伤。"};
  	
  	
  	gGejuDesc[100]=new String[]{"太白逢星","庚加乙，退吉进凶，谋为不利。"+HH+
  			"由于为客有利，故宜退不宜进。表示留恋，牵绊，有合伙人，与异性合伙好。"+HH+
  			"利于合作，只要处在委曲求全的状态之下，合作就是愉快的，退而求其次，事就好办。"};
  	gGejuDesc[101]=new String[]{"太白入荧","庚加丙，占贼必来，为客进利，为主破财。"+HH+
  			"虽丙火能克庚金，但因丙火在下处于不利状态，故为大凶。"+HH+
  			"但求测人年命或日干为庚时，庚即我方，则利客，只要主动进入、先发制人，则有利于自己。"+HH+
  			"比荧入太白更凶，须防贼来偷营，以固守为吉。"+HH+
  			"临生门，会有钱财送上门。测病，病来。测行人，肯定回来。测小偷，小偷还来。"+HH+
  			"测什么来什么。"};
  	gGejuDesc[102]=new String[]{"亭亭之格","庚加丁，因私暱或男女关系起官司是非，门吉有救，门凶事必凶。"+HH+
  			"庚为男人，丁为玉女，有金屋藏娇之象。口舌是非，家庭纠纷。"+HH+
  			"测事业，主因一己私利出是非，被人告状，损公肥私。"};
  	gGejuDesc[103]=new String[]{"伏宫格","庚加戊，百事不可谋为，凶。多成多败，此地不比他地，此人不比他人。"+HH+
  			"最忌讳合作、开店与坐地求财。开公司、杂货店、饭店等肯定开不成，"+HH+
  			"测工作肯定做不长，测运气为换了就好须变动才可不变不行，测合作不行主换人。"};
  	gGejuDesc[104]=new String[]{"官府刑格","庚加己，官司是非，经商破财，出行患病。"+HH+
  			"因己为丑为庚金墓地，无论主客皆不利。测官讼可能被重刑、坐牢或更凶。"+HH+
  			"刑格，难受，别扭，而且这个难受是不情愿的，是被别人拖累的，心灵或肉体上受到伤害。","官刑格"};
  	gGejuDesc[105]=new String[]{"太白同宫","庚加庚，又名“战格”：官灾横祸，兄弟雷攻。"+HH+
  			"主朋友、同事、兄弟之间不和，不仅不利谋事，还会遭来官灾横祸。"+HH+
  			"伏吟格中最特殊的一个，利客不利主，不利于呆着静守，而是要积极行动，利于战斗和攻击。"+HH+
  			"是仇人见面，谁先动谁有利。最不利合作，合作必打架。"+HH+
  			"老人测时干见庚＋庚，两儿子打架不合。测公司主内部乱套，内外忧患，为重组之象。"};
  	gGejuDesc[106]=new String[]{"白虎干格","庚加辛，又名太白刑格，不宜远行，远行车折马死，求财更凶，诸事有灾殃。"+HH+
  			"主打架、受伤、犯错，因冲动斗打之事。因为是一阴一阳的组合，并不是特别凶。"};
  	gGejuDesc[107]=new String[]{"小格","庚加壬，又名上格，远行迷失道路，男女音信难通。"+HH+
  			"以庚为用，因庚为阻隔，遇壬水流动，又为移荡格。"+HH+
  			"冲格，动荡不安，忧患，频繁跑动，一般是局部的变化与调整。"+HH+
  			"出行遇之，堵车堵不住。测工作遇之一般是岗位的调动，在原单位内部动。"};
  	gGejuDesc[108]=new String[]{"大格","庚加癸，行人至，官司止，生育母子俱伤。"+HH+
  			"因寅申相冲相刑，故为大凶之格，庚主道路多主车祸，故行人不至。"+HH+
  			"冲格，萍迹四害，变化大，彻底改变。五湖四海到处跑。"+HH+
  			"格局旺，远方创业之人，或为高官，为大领导服务的人。格局衰，给人打工的。"+HH+
  			"测工作不是经常出差跑业务的，就得调动工作了。"};
  	
  	gGejuDesc[110]=new String[]{"白虎猖狂","辛加乙，家败人亡，远行多灾殃。"+HH+
  			"测婚遇此格是男人主动离婚，把家拆散。动格，利客不利主，利于主动去做。"+HH+
  			"测病不吉，病重。测投资、举事可以。","虎猖狂"};
  	gGejuDesc[111]=new String[]{"干合悖师","辛加丙，荧惑出现，占雨无，占晴旱，占事必因财致讼。"+HH+
  			"因阴上阳下，阴阳颠倒虽合但吉的程度减少，如果门吉则事吉，门凶则事凶，如果合作求财，容易因财致讼。"+HH+
  			"合格，但容易在合作中出现一些是非麻烦。"+HH+
  			"测事合作投资，因钱财与他人发生官司争斗。"+HH+
  			"只要合作，肯定出口舌官司是非。"};
  	gGejuDesc[112]=new String[]{"狱神得奇","辛加丁，经商获倍利，囚人逢天赦释免。"+HH+
  			"如果经商求财，可以获得加倍的利润；办其他事也会有意外的收获；"+HH+
  			"如果犯了错误，也会免予处分，坐牢狱的囚犯，也会获得赦免。"+HH+
  			"测罪人，获释。测婚姻，主第三者插足。"};
  	gGejuDesc[113]=new String[]{"困龙被伤","辛加戊，官司破财，屈抑守分吉，妄动祸殃。"+HH+
  			"冲格，因自己的错误，导致的经济危机或事情的失败而身陷困境，但基础还有，只是目前短缺。"+HH+
  			"只要辛、戊在一起，必破财，或身陷困境之象，没钱了。"+HH+
  			"但如果辛为用，则不凶反吉，为客无害，但午子相冲，挣到手的钱容易花掉。"};
  	gGejuDesc[114]=new String[]{"入狱自刑","辛加己，奴仆背主，讼诉难伸。"+HH+
  			"主错误由自己造成。千万别帮人，主心里有委曲的事，帮了变成仇人。"+HH+
  			"此格也叫奴欺主，你手底下的人，以前不如你，现在比你强了，反过来了。"+HH+
  			"测坟地风水，前面有大的山或建筑物靠得太近压住了，应该矮才行。"};
  	gGejuDesc[115]=new String[]{"白虎出力","辛加庚，遇庚同类相残，故又名天狱自刑，刀刃相接，主客相残，逊让退步稍可，强攻血溅衣衫。"+HH+
  			"是由于自己的错误，引来了外界的错误。因为自己的失误，引来了外界的影响而导致更大的问题。"+HH+
  			"不利合作，不利主动去做，越强行去做，越倒霉。"};
  	gGejuDesc[116]=new String[]{"伏吟天庭","辛加辛，主为事自破，进退不果，公废私就，讼狱自罹罪名。"+HH+
  			"因甲午为自刑，辛又名天庭。辛＋辛一大堆错误在一起，毛病多多，"+HH+
  			"漏洞百出，还老挑别人的毛病，实际都是自己的问题造成的。","伏天"};
  	gGejuDesc[117]=new String[]{"凶蛇入狱","辛加壬，两男争女，争讼不息，先动失理。"+HH+
  			"辛为客先动必入辰墓，故不宜先；争执，是因为自己的错误，导致竞争或同时受到多方面的影响。"+HH+
  			"两男争女，容易出现婚外恋，三角恋，而且是被两者同时发现了，引起打斗。"+HH+
  			"测事则表示发生三角、连锁官司，牵动多人多面。"};
  	gGejuDesc[118]=new String[]{"天牢华盖","辛加癸，日月失明，误入天网，动止乖张。"+HH+
  			"辛为白虎，癸为地网，又名虎投地网格。"+HH+
  			"因自己判断失误，走错道了，到网里面了。测生意不能干，判断错了。"};
  	
  	gGejuDesc[120]=new String[]{"小蛇日奇","壬加乙，又名小蛇得势，女子柔顺，男人发达。占孕生子，禄马光华。"+HH+
  			"在忧愁之中突然遇到了别人的帮助，曲中求变，不能直截了当的去办，要以柔克刚就好，别急躁。"};
  	gGejuDesc[121]=new String[]{"水蛇入火","壬加丙，官灾刑禁，络绎不绝，二败俱伤。"+HH+
  			"口舌是非，麻烦一大堆，越急越出乱子。 己壬相逢斗讼场，壬丙相逢是非多。"};
  	gGejuDesc[122]=new String[]{"干合蛇刑","壬加丁，文书牵连，贵人匆匆，男吉女凶。"+HH+
  			"因天盘壬为阳为男，遇丁奇相合，自然为吉，而地盘丁为阴为女，上有天罗壬盖头，毕竟不吉。"+HH+
  			"合格，利于合作，谈判。"};
  	gGejuDesc[123]=new String[]{"小蛇化龙","壬加戊，男人发达，女产婴童。"+HH+
  			"本来是阳阳碰撞，为什么会有良性信息呢?就是因为壬水在动，再者壬为小蛇，甲子戊为甲木水木相生。"+HH+
  			"表示有贵人相帮，找到了靠山或找到了强大的支撑因素。"+HH+
  			"测企业，测公司发展，好格，有大发展，要上个台阶。"+HH+
  			"测事业运气，要转运了，测前途，不错，提升之象。"+HH+
  			"也利于投资。测疾病不吉，小病变大病了。"};
  	gGejuDesc[124]=new String[]{"反呤蛇刑","壬加己，官事败诉，大祸将至，顺守斯吉，妄动必凶。"+HH+
  			"甲辰与甲戌反呤。在阴阳相配中只有壬，己势不两立，是特殊的。 "+HH+
  			"这个局动不好，主口舌是非多。保持中立不变化还平安，如果变化就会掉入陷阱，被人设计和陷害。"+HH+
  			"己壬相逢斗讼场。"};
  	gGejuDesc[125]=new String[]{"太白擒蛇","壬加庚，凡作为难以进展，但遇词讼，则会刑狱公平，立剖邪正。"+HH+
  			"因蛇为用遇太白受阻之故。"+HH+
  			"测终生局遇之，一是做政客，二是做科研，三是做教师。"+HH+
  			"是个吉格，一般有正确的主见，不被其他事物或不利因素所迷惑，能够看清方向，不被别人所骗，叫立剖邪正，是非分明。"+HH+
  			"六合临之，是个好中介，中立，秉公办事。"+HH+
  			"测学习，老师有水平，立剖邪正，主老师能分清是非。测事也是邪正分明。"};
  	gGejuDesc[126]=new String[]{"腾蛇相缠","壬加辛，纵得奇门，变不能安，若有谋望，被人欺瞒。"+HH+
  			"麻烦事一大堆，解决不了，到处都是内忧外患。"+HH+
  			"明知道上当受骗还要上。没有正经事，但很棘手。"};
  	gGejuDesc[127]=new String[]{"蛇入地罗","壬加壬，又名天狱自刑，外人缠绕，内事索索，吉门吉星，庶免磋砣。"+HH+
  			"主求谋无成，祸患起于内部，诸事主破败，遇吉门吉星，尚可缓解困顿处境。"+HH+
  			"主事勾连，越变越乱。测家庭或公司，都是内外都有事。"+HH+
  			"测合作，是按下葫芦浮起瓢，内忧外患。"+HH+
  			"内因和外因同时缠绕着，是没有秩序的乱，是难以解决，找不到一个源头和突破点，不管动不动，烦心事都会找上门。"};
  	gGejuDesc[128]=new String[]{"天罗地网","壬加癸，家有丑声，门吉星凶，反福为祸。"+HH+
  			"因阴阳水相遇交和，故又名幼女奸淫，主有家丑外杨之事发生。"+HH+
  			"如果门吉，可谓之为风流男女；星凶，则本质坏，本性难改，必然反福为祸，因男女暧昧之事招来凶灾。"+HH+
  			"是外部没事，而内部出现的问题。外部一团和谐，原因出在内部，外清而内浊。"+HH+
  			"只要把自己的事情搞清楚了，外边一般比较清晰。"+HH+
  			"说明事物还没做，问题就一大堆了，毛病就已经出来了。"};
  	
  	gGejuDesc[130]=new String[]{"华盖蓬星","癸加乙，贵人禄位，常人平安，门吉则吉，门凶则凶。"+HH+
  			"癸为地网，高者可为华盖，得凶门则为凶。"+HH+
  			"遇此格，应性格含蓄，做事内敛比较好，不要太露锋芒，要悄悄的办。"+HH+
  			"也主希望被网罩住了，有财难伸。"};
  	gGejuDesc[131]=new String[]{"华盖悖师","癸加丙，贵贱逢之皆不利，只有能屈能伸，善于因势利导的上等人物才能化不利为有利，反怒为喜。"+HH+
  			"遇此格，易犯小人，忌讳女人参与。"+HH+
  			"遇此格，一动就有乱子事。"};
  	gGejuDesc[132]=new String[]{"腾蛇夭矫","癸加丁，文书官司，火焚也逃不掉。"+HH+
  			"有蛇被火烤弯曲摆动不已之象。"+HH+
  			"官司口舌，问题林立。"+HH+
  			"测病必加重病情，合作必散伙，乘船坐车则有船翻车毁之险，测风水家宅不宁。"};
  	gGejuDesc[133]=new String[]{"天乙会合","癸加戊，吉格，财喜婚姻，吉人赞助成合。"+HH+
  			"若门凶迫制，反招来祸害官非。"+HH+
  			"是个合格，利于合作，必有他人他事牵连。"+HH+
  			"做买卖与人合作，测事与他人有关，测病不止一个病，测出行必有他事牵连，出行遇冲格好。"};
  	gGejuDesc[134]=new String[]{"华盖地户","癸加己，男女占之，音信皆阻。"+HH+
  			"遇此格躲灾遇避难方为吉。要求你安静，退守，静止，等待，观看，你就没有事，就应吉。如果乱撞，就出灾。"+HH+
  			"是一个小冲格，不利于客，私欲幻想多，难实现。单相思，办不成。"};
  	gGejuDesc[135]=new String[]{"太白入网","癸加庚，以暴力争讼，自罹罪责。"+HH+
  			"由于寅申相冲相刑，所以凡事无成，吉事也易成空。"+HH+
  			"是事物难以控制，容易被他人他事影响，造成麻烦和是非。"+HH+
  			"因冲突冲动造成，主斗打，相冲突，容易有人来捣乱，破坏。"};
  	gGejuDesc[136]=new String[]{"网盖天牢","癸加辛，官司败诉，死罪难逃。占病也大凶。"+HH+
  			"奇门中最凶的一个格局。 落巽宫，犯罪或坐过牢，因巽主执法机关。"+HH+
  			"测运气，一般主牢狱之象，唯一的方法是什么都不干，静守闭关，不动网就不下来。"+HH+
  			"小孩子遇此格，犯错误，不爱出门。","盖天牢"};
  	gGejuDesc[137]=new String[]{"复见腾蛇","癸加壬，嫁娶重婚，后嫁无子，不保年华。"+HH+
  			"因阴阳颠倒，主事物变化，另找主人，或另找合作伙伴。"+HH+
  			"重复做以往的错误，屡教不改，又导致第二次失败。重复之象，没有结果。"};
  	gGejuDesc[138]=new String[]{"天网四张","癸加癸，行人失伴，病讼皆伤。"+HH+
  			"如果落1234宫为网低，受困更重，以躲避忍耐等待时机为佳，如果落6789为网高，可匍匐而出，故尚可小心谨慎谋事，以逐步改变困境。"+HH+
  			"但也有相反之说，1234网低可过，6789网高不可过。天网四张不可挡，此时行事有灾殃。若是有人强出者，立便身躯见血光。"+HH+
  			"不可谋事，只宜退避，如果强行谋事，不仅无成，还会带来凶灾。"+HH+
  			"出行结伴主失散，还容易有病。"+HH+
  			"此格有一个最大的特点，只要独立出行，独立行事就没事，最怕多人出行及合作。"+HH+
  			"在合作上最容易出现问题的格局就是庚+庚/癸+癸，只要一合作，人一多，矛盾就出来了。"+HH+
  			"癸+癸是有秩序和规律，只要你不乱，非常镇静的理清头绪，则什么事都没有，不妄动就没事。"};
  	
  	gGejuDesc[140]=new String[]{"玉兔投泉","六乙到坎，吉。","乙"};
  	gGejuDesc[141]=new String[]{"丙火烧壬","六丙到坎，吉，主胜。","丙"};
  	gGejuDesc[142]=new String[]{"朱雀投江","六丁到坎，吉。","丁"};  	
  	gGejuDesc[143]=new String[]{"玉兔入坤","六乙到坤，吉。","乙"};  	
  	gGejuDesc[144]=new String[]{"子居母舍","六丙到坤，吉。","丙"};
  	gGejuDesc[145]=new String[]{"玉女游地户","六丁到坤，吉。","丁"};  	
  	gGejuDesc[146]=new String[]{"日出扶桑","六乙到震，有禄之乡，是贵人升于乙卯正殿，吉；<BR>本禄之位，合三吉门宜上官赴任、求财祈福等各种谋为都吉利。","乙"};
  	gGejuDesc[147]=new String[]{"月入雷门","六丙到震，吉。","丙"};
  	gGejuDesc[148]=new String[]{"最明","六丁到震，吉。","丁"};
  	gGejuDesc[149]=new String[]{"玉兔乘风","六乙到巽，吉。","乙"};
  	gGejuDesc[150]=new String[]{"火起风行","六丙到巽，吉；<BR>本禄之位，合三吉门宜上官赴任、求财祈福等各种谋为都吉利。","丙"};
  	gGejuDesc[151]=new String[]{"美女留神","六丁到巽，吉。","丁"};  	
  	gGejuDesc[152]=new String[]{"玉兔入天门","六乙到乾，吉。","乙"};
  	gGejuDesc[153]=new String[]{"天成天权","六丙到乾，凶。","丙"};
  	gGejuDesc[154]=new String[]{"火到天门","六丁到乾，吉。","丁"};
  	gGejuDesc[155]=new String[]{"玉女受制","六乙到兑，平平。","乙"};
  	gGejuDesc[156]=new String[]{"凤凰折翅","六丙到兑，凶。","丙"};
  	gGejuDesc[157]=new String[]{"星见西方","六丁到兑，天之神位，丁火长生于酉，是贵人升于丁酉正殿。","丁"};  	  	
  	gGejuDesc[158]=new String[]{"玉兔步贵宫","六乙到艮，吉。","乙"};
  	gGejuDesc[159]=new String[]{"凤入丹山","六丙到艮，吉。","丙"};  	
  	gGejuDesc[160]=new String[]{"玉女乘云","六丁到艮，吉。","丁"};  	
  	gGejuDesc[161]=new String[]{"玉兔当阳","六乙到离，吉。","乙"};
  	gGejuDesc[162]=new String[]{"月照端门","六丙到离，火旺之地，是贵人升于丙午正殿，吉。","丙"};
  	gGejuDesc[163]=new String[]{"乘龙万里","六丁到离，吉；<BR>本禄之位，合三吉门宜上官赴任、求财祈福等各种谋为都吉利。","丁"};
  	
  	gGejuDesc[164]=new String[]{"六仪击刑","天盘甲子，地盘震三宫（子刑卯）；<BR>极凶，即使六仪为直符，也不可用。","刑"};
  	gGejuDesc[165]=new String[]{"六仪击刑","天盘甲戍，地盘坤二宫（戍刑未）；<BR>极凶，即使六仪为直符，也不可用。","刑"};
  	gGejuDesc[166]=new String[]{"六仪击刑","天盘甲申，地盘艮八宫（申刑寅）；<BR>极凶，即使六仪为直符，也不可用。","刑"};
  	gGejuDesc[167]=new String[]{"六仪击刑","天盘甲午，地盘离九宫（午自刑）；<BR>极凶，即使六仪为直符，也不可用。","刑"};
  	gGejuDesc[168]=new String[]{"六仪击刑","天盘甲辰，地盘巽四宫（辰自刑）；<BR>极凶，即使六仪为直符，也不可用。","刑"};
  	gGejuDesc[169]=new String[]{"六仪击刑","天盘甲寅，地盘巽四宫或坤二宫（寅刑巳，申刑寅）；<BR>极凶，即使六仪为直符，也不可用。","刑"};
  	
  	gGejuDesc[170]=new String[]{"休加休","求财、进人口、谒贵吉，朝见、上官、修造、大利。","休"};
  	gGejuDesc[171]=new String[]{"休加生","主得阴人财物。干贵谋望，虽迟应吉。","休"};
  	gGejuDesc[172]=new String[]{"休加伤","主上官、吉庆，求财不得。有亲故分产。变动事不吉。","休"};
  	gGejuDesc[173]=new String[]{"休加杜","主破财，失物难寻。","休"};
  	gGejuDesc[174]=new String[]{"休加景","主求望文书印信事不至，反招口舌小凶。","休"};
  	gGejuDesc[175]=new String[]{"休加死","主求文书印信官司事，或僧道，远行事，不吉，占病凶。","休"};
  	gGejuDesc[176]=new String[]{"休加惊","主损财、招非并疾病、惊恐事。","休"};
  	gGejuDesc[177]=new String[]{"休加开","主开张店肆及见贵、求财、喜庆事，大吉。","休"};
  	
  	gGejuDesc[180]=new String[]{"死加休","主求财物事不吉，若问生道求方吉","死"};
  	gGejuDesc[181]=new String[]{"死加生","主丧事，求财得，占病死者复生","死"};
  	gGejuDesc[182]=new String[]{"死加伤","主官事动而被刑杖，凶","死"};
  	gGejuDesc[183]=new String[]{"死加杜","主破财，妇人风疾，腹肿","死"};
  	gGejuDesc[184]=new String[]{"死加景","主因文契印信财产事见官，选怒后喜，不凶","死"};
  	gGejuDesc[185]=new String[]{"死加死","主官而留，印信无气，凶","死"};
  	gGejuDesc[186]=new String[]{"死加惊","主因官司不给，忧疑患病，凶","死"};
  	gGejuDesc[187]=new String[]{"死加开","主见贵人，求印信文书事大利","死"};

  	gGejuDesc[190]=new String[]{"伤加休","主阳人变动，或托人谋干财名不利","伤"};
  	gGejuDesc[191]=new String[]{"伤加生","主房产、种植事业，凶","伤"};
  	gGejuDesc[192]=new String[]{"伤加伤","主变动、远行折伤，凶","伤"};
  	gGejuDesc[193]=new String[]{"伤加杜","主变动、失脱、官司桎梏，百事皆凶","伤"};
  	gGejuDesc[194]=new String[]{"伤加景","主文书印信、口舌，动挠啾唧","伤"};
  	gGejuDesc[195]=new String[]{"伤加死","主官司印信凶，出行大忌，点病凶","伤"};
  	gGejuDesc[196]=new String[]{"伤加惊","主亲人疾病忧惧，媒伐不利，凶","伤"};
  	gGejuDesc[197]=new String[]{"伤加开","主贵人开张有走失变动之事，不利","伤"};


  	gGejuDesc[200]=new String[]{"杜加休","主求财有益","杜"};
  	gGejuDesc[201]=new String[]{"杜加生","主阳人小口破财及田宅，求财不成","杜"};
  	gGejuDesc[202]=new String[]{"杜加伤","主兄弟相争田产，破财","杜"};
  	gGejuDesc[203]=new String[]{"杜加杜","主因父母疾病，田宅出脱，事凶","杜"};
  	gGejuDesc[204]=new String[]{"杜加景","主文书印信阻隔，阳人小口疾病","杜"};
  	gGejuDesc[205]=new String[]{"杜加死","主田宅文书失落，官司破财，小凶","杜"};
  	gGejuDesc[206]=new String[]{"杜加惊","主门户内忧疑惊恐，并有词讼事","杜"};
  	gGejuDesc[207]=new String[]{"杜加开","主见贵人官长，谋事主先破己财后吉","杜"};

  	gGejuDesc[210]=new String[]{"开加休","主见贵人财喜及开张铺店，贸易大吉","开"};
  	gGejuDesc[211]=new String[]{"开加生","主见贵人，谋望所求遂意","开"};
  	gGejuDesc[212]=new String[]{"开加伤","主变动、更改、移徙，事皆不吉","开"};
  	gGejuDesc[213]=new String[]{"开加杜","主失脱，刊印书契小凶","开"};
  	gGejuDesc[214]=new String[]{"开加景","主见贵人，因文书事不利","开"};
  	gGejuDesc[215]=new String[]{"开加死","主官司惊忧，先忧后喜","开"};
  	gGejuDesc[216]=new String[]{"开加惊","主百事不利","开"};
  	gGejuDesc[217]=new String[]{"开加开","主贵人财喜","开"};

  	gGejuDesc[220]=new String[]{"惊加休","主求财事或因口舌求财事迟吉","惊"};
  	gGejuDesc[221]=new String[]{"惊加生","主因妇人生忧惊，或因求财生忧惊，皆吉","惊"};
  	gGejuDesc[222]=new String[]{"惊加伤","主因商议同谋害人，事泄惹讼，凶","惊"};
  	gGejuDesc[223]=new String[]{"惊加杜","主因失脱破财惊恐，不凶","惊"};
  	gGejuDesc[224]=new String[]{"惊加景","主词讼不息，小口疾病，凶","惊"};
  	gGejuDesc[225]=new String[]{"惊加死","主因宅中怪异而生是非，凶","惊"};
  	gGejuDesc[226]=new String[]{"惊加惊","主疾病、忧疑、惊疑","惊"};
  	gGejuDesc[227]=new String[]{"惊加开","主忧疑、官司、惊恐，又主上见喜，不凶","惊"};

  	gGejuDesc[230]=new String[]{"生加休","主阴人处求望财利吉","生"};
  	gGejuDesc[231]=new String[]{"生加生","主远行、求财吉","生"};
  	gGejuDesc[232]=new String[]{"生加伤","主亲友变动，道路不吉","生"};
  	gGejuDesc[233]=new String[]{"生加杜","主阴谋，阴人破财，不利","生"};
  	gGejuDesc[234]=new String[]{"生加景","主阴人、小口不宁及文书事后吉","生"};
  	gGejuDesc[235]=new String[]{"生加死","主田宅官司，病主难救","生"};
  	gGejuDesc[236]=new String[]{"生加惊","主尊长财产、词讼，病迟愈，吉","生"};
  	gGejuDesc[237]=new String[]{"生加开","主见贵人，求财大发","生"};

  	gGejuDesc[240]=new String[]{"景加休","主文书遗失，争讼不休","景"};
  	gGejuDesc[241]=new String[]{"景加生","主阴人生产大喜，更主求财旺利，行人皆吉","景"};
  	gGejuDesc[242]=new String[]{"景加伤","主姻亲亲眷小口口舌","景"};
  	gGejuDesc[243]=new String[]{"景加杜","主失脱文书，散财后平","景"};
  	gGejuDesc[244]=new String[]{"景加景","主文状未动有预先见之意，内有小口忧患","景"};
  	gGejuDesc[245]=new String[]{"景加死","主官讼，因田宅事争竞啾唧","景"};
  	gGejuDesc[246]=new String[]{"景加惊","主阳人小口疾病事凶","景"};
  	gGejuDesc[247]=new String[]{"景加开","主官人升迁，吉；求文印更吉","景"};
  	
  	gGejuDesc[250]=new String[]{"休加乙","求谋重，不得；求轻，可得","乙"};
  	gGejuDesc[251]=new String[]{"休加丙","文书和合喜庆","丙"};
  	gGejuDesc[252]=new String[]{"休加丁","百讼休歇","丁"};
  	gGejuDesc[253]=new String[]{"休加戊","财物和合","戊"};
  	gGejuDesc[254]=new String[]{"休加己","暗昧不宁","己"};
  	gGejuDesc[255]=new String[]{"休加庚","文书词讼先结后解","庚"};
  	gGejuDesc[256]=new String[]{"休加辛","疾病退愈，失物不得","辛"};
  	gGejuDesc[257]=new String[]{"休加壬","阴人词讼牵连","壬"};
  	gGejuDesc[258]=new String[]{"休加癸","阴人词讼牵连","癸"};

  	gGejuDesc[260]=new String[]{"死加乙","主求事不成","乙"};
  	gGejuDesc[261]=new String[]{"死加丙","主信息忧疑","丙"};
  	gGejuDesc[262]=new String[]{"死加丁","主老阳人疾病","丁"};
  	gGejuDesc[263]=new String[]{"死加戊","主作伪财","戊"};
  	gGejuDesc[264]=new String[]{"死加己","主病讼牵连不已，凶","己"};
  	gGejuDesc[265]=new String[]{"死加庚","主女人生产，母子俱凶","庚"};
  	gGejuDesc[266]=new String[]{"死加辛","主盗贼失脱难获","辛"};
  	gGejuDesc[267]=new String[]{"死加壬","主讼人自讼自招","壬"};
  	gGejuDesc[268]=new String[]{"死加癸","主嫁娶事凶","癸"};
  		
  	gGejuDesc[270]=new String[]{"伤加乙","主求谋不得，反防盗失财","乙"};
  	gGejuDesc[271]=new String[]{"伤加丙","主道路损失","丙"};
  	gGejuDesc[272]=new String[]{"伤加丁","主音信不实","丁"};
  	gGejuDesc[273]=new String[]{"伤加戊","主失脱难获","戊"};
  	gGejuDesc[274]=new String[]{"伤加己","主财散人死","己"};
  	gGejuDesc[275]=new String[]{"伤加庚","主讼狱被刑杖，凶","庚"};
  	gGejuDesc[276]=new String[]{"伤加辛","主夫妻怀私恣怨","辛"};
  	gGejuDesc[277]=new String[]{"伤加壬","主因盗牵连","壬"};
  	gGejuDesc[278]=new String[]{"伤加癸","讼狱被冤，有理难伸","癸"};
  		
  	gGejuDesc[280]=new String[]{"杜加乙","主宜暗求阳人财物，得主不明至讼","乙"};
  	gGejuDesc[281]=new String[]{"杜加丙","主文契遗失","丙"};
  	gGejuDesc[282]=new String[]{"杜加丁","主阳人入狱","丁"};
  	gGejuDesc[283]=new String[]{"杜加戊","主谋事不成，密处求财得","戊"};
  	gGejuDesc[284]=new String[]{"杜加己","主私害人招非","己"};
  	gGejuDesc[285]=new String[]{"杜加庚","主因女人讼狱被刑","庚"};
  	gGejuDesc[286]=new String[]{"杜加辛","主打伤人，词讼阳人小口凶","辛"};
  	gGejuDesc[287]=new String[]{"杜加壬","主奸盗事，凶","壬"};
  	gGejuDesc[288]=new String[]{"杜加癸","主百事皆阻，病者不食","癸"};
  		
  	gGejuDesc[290]=new String[]{"开加乙","小财可求","乙"};
  	gGejuDesc[291]=new String[]{"开加丙","贵人印绶","丙"};
  	gGejuDesc[292]=new String[]{"开加丁","远信心至","丁"};
  	gGejuDesc[293]=new String[]{"开加戊","财名俱得","戊"};
  	gGejuDesc[294]=new String[]{"开加己","事绪不定","己"};
  	gGejuDesc[295]=new String[]{"开加庚","道路词讼，谋为两歧","庚"};
  	gGejuDesc[296]=new String[]{"开加辛","阴人道路","辛"};
  	gGejuDesc[297]=new String[]{"开加壬","远行有失","壬"};
  	gGejuDesc[298]=new String[]{"开加癸","阴人人财小凶","癸"};
  		
  	gGejuDesc[300]=new String[]{"惊加乙","主谋财不得","乙"};
  	gGejuDesc[301]=new String[]{"惊加丙","主文书印信惊恐","丙"};
  	gGejuDesc[302]=new String[]{"惊加丁","主词讼牵连","丁"};
  	gGejuDesc[303]=new String[]{"惊加戊","主损财，信阻","戊"};
  	gGejuDesc[304]=new String[]{"惊加己","主恶犬伤人成讼","己"};
  	gGejuDesc[305]=new String[]{"惊加庚","主道路损折、贼盗，凶","庚"};
  	gGejuDesc[306]=new String[]{"惊加辛","主女人成讼，凶","辛"};
  	gGejuDesc[307]=new String[]{"惊加壬","主官司因禁，病者大凶","壬"};
  	gGejuDesc[308]=new String[]{"惊加癸","主被盗，失物难获","癸"};
  		
  	gGejuDesc[310]=new String[]{"生加乙","主阴人生产迟吉","乙"};
  	gGejuDesc[311]=new String[]{"生加丙","主贵人印绶、婚姻、书信喜事","丙"};
  	gGejuDesc[312]=new String[]{"生加丁","主词讼、婚姻、财利大吉","丁"};
  	gGejuDesc[313]=new String[]{"生加戊","嫁娶、求财、谒贵皆吉","戊"};
  	gGejuDesc[314]=new String[]{"生加己","主得贵人维持吉","己"};
  	gGejuDesc[315]=new String[]{"生加庚","主财产争讼破产，不利","庚"};
  	gGejuDesc[316]=new String[]{"生加辛","主官事、疾病后吉","辛"};
  	gGejuDesc[317]=new String[]{"生加壬","失财后得、盗贼易获","壬"};
  	gGejuDesc[318]=new String[]{"生加癸","主婚姻不成，余事皆吉","癸"};
  		
  	gGejuDesc[320]=new String[]{"景加乙","主讼事不成","乙"};
  	gGejuDesc[321]=new String[]{"景加丙","主文书急迫火速不利","丙"};
  	gGejuDesc[322]=new String[]{"景加丁","主因文书印状招非","丁"};
  	gGejuDesc[323]=new String[]{"景加戊","主因财产词讼，远行吉","戊"};
  	gGejuDesc[324]=new String[]{"景加己","主官事牵连","己"};
  	gGejuDesc[325]=new String[]{"景加庚","主讼人自讼","庚"};
  	gGejuDesc[326]=new String[]{"景加辛","主阴人词讼","辛"};
  	gGejuDesc[327]=new String[]{"景加壬","主因贼牵连","壬"};
  	gGejuDesc[328]=new String[]{"景加癸","主因奴婢刑","癸"};
  				
  	gGejuDesc[330]=new String[]{"蓬加休","披枷戴锁，锒铛入狱 小凶","蓬"	};
  	gGejuDesc[331]=new String[]{"蓬加生","纵欲无度，尽兴不返 大吉"	,"蓬"};
  	gGejuDesc[332]=new String[]{"蓬加伤","大难临头，六宅不安 小凶"	,"蓬"};
  	gGejuDesc[333]=new String[]{"蓬加杜","十年寒窗，不得一举 小凶"	,"蓬"};
  	gGejuDesc[334]=new String[]{"蓬加景","万事具备，只欠东风 小凶"	,"蓬"};
  	gGejuDesc[335]=new String[]{"蓬加死","夺戒口食，剥戒身衣 小凶"	,"蓬"};
  	gGejuDesc[336]=new String[]{"蓬加惊","别生枝节，何所取义 小凶"	,"蓬"};
  	gGejuDesc[337]=new String[]{"蓬加开","三年不鸣，一鸣惊人 大吉"	,"蓬"};

  	gGejuDesc[340]=new String[]{"芮加休","一以当千，南争北战 大吉"	,"芮"};
  	gGejuDesc[341]=new String[]{"芮加生","力不从心，望尘莫及 小凶"	,"芮"};
  	gGejuDesc[342]=new String[]{"芮加伤","七窃生烟，九世之仇 小凶"	,"芮"};
  	gGejuDesc[343]=new String[]{"芮加杜","安生而食，用钱如水 小凶"	,"芮"};
  	gGejuDesc[344]=new String[]{"芮加景","才情卓越，大智若愚 小吉"	,"芮"};
  	gGejuDesc[345]=new String[]{"芮加死","日落西山，旦不保夕 大凶"	,"芮"};
  	gGejuDesc[346]=new String[]{"芮加惊","风吹草动，一夕数惊 小凶"	,"芮"};
  	gGejuDesc[347]=new String[]{"芮加开","堆金积玉，安富尊荣 大吉"	,"芮"};

  	gGejuDesc[350]=new String[]{"冲加休","履险如夷，转危为安 大吉"	,"冲"};
  	gGejuDesc[351]=new String[]{"冲加生","塞翁失马，安之非福 大吉"	,"冲"};
  	gGejuDesc[352]=new String[]{"冲加伤","遍体鳞伤，痛入骨髓 大凶"	,"冲"};
  	gGejuDesc[353]=new String[]{"冲加杜","墨守成规，不知改变 小凶"	,"冲"};
  	gGejuDesc[354]=new String[]{"冲加景","车载斗量，指不胜屈 小吉"	,"冲"};
  	gGejuDesc[355]=new String[]{"冲加死","刀光剑影，枕戈待旦 小凶"	,"冲"};
  	gGejuDesc[356]=new String[]{"冲加惊","遇人不淑，劳燕分飞 大凶"	,"冲"};
  	gGejuDesc[357]=new String[]{"冲加开","力行不怠，一日千重 大吉"	,"冲"};

  	gGejuDesc[360]=new String[]{"辅加休","雾消云散，重见光明 大吉"	,"辅"};
  	gGejuDesc[361]=new String[]{"辅加生","心心相印，海誓山盟 大吉","辅"	};
  	gGejuDesc[362]=new String[]{"辅加伤","先天不足，后天未补 小凶"	,"辅"};
  	gGejuDesc[363]=new String[]{"辅加杜","七颠八倒，自相矛盾 大凶"	,"辅"};
  	gGejuDesc[364]=new String[]{"辅加景","春风一度，始乱终弃 小吉"	,"辅"};
  	gGejuDesc[365]=new String[]{"辅加死","花天酒地，玩物丧志 小凶"	,"辅"};
  	gGejuDesc[366]=new String[]{"辅加惊","天罗地网，插翅难飞 小凶"	,"辅"};
  	gGejuDesc[367]=new String[]{"辅加开","字无分文，牛衣对泣 小凶"	,"辅"};

  	gGejuDesc[370]=new String[]{"禽加休","披枷戴锁，锒铛入狱 小凶"	,"禽"};
  	gGejuDesc[371]=new String[]{"禽加生","前山后海，进退两难 小凶","禽"	};
  	gGejuDesc[372]=new String[]{"禽加伤","遍体鳞伤，痛入骨髓 大凶"	,"禽"};
  	gGejuDesc[373]=new String[]{"禽加杜","七颠八倒，自相矛盾 大凶"	,"禽"};
  	gGejuDesc[374]=new String[]{"禽加景","春风一度，始乱终弃 小凶"	,"禽"};
  	gGejuDesc[375]=new String[]{"禽加死","日落西山，旦不保夕 大凶"	,"禽"};
  	gGejuDesc[376]=new String[]{"禽加惊","投机取巧，惟利是图 大凶"	,"禽"};
  	gGejuDesc[377]=new String[]{"禽加开","四海漂泊，何处是家 小凶 "	,"禽"};

  	gGejuDesc[380]=new String[]{"心加休","官明如镜，吏清如水 大吉"	,"心"};
  	gGejuDesc[381]=new String[]{"心加生","六根清净，山林隐逸 大吉"	,"心"};
  	gGejuDesc[382]=new String[]{"心加伤","飞来横祸，轩然大波 小凶"	,"心"};
  	gGejuDesc[383]=new String[]{"心加杜","山穷水尽，千辛万苦 大凶"	,"心"};
  	gGejuDesc[384]=new String[]{"心加景","相见恨晚，握手言欢 小吉"	,"心"};
  	gGejuDesc[385]=new String[]{"心加死","生死有命，回天乏术 小凶"	,"心"};
  	gGejuDesc[386]=new String[]{"心加惊","千变万化，不可捉摸 小凶"	,"心"};
  	gGejuDesc[387]=new String[]{"心加开","四海漂泊，何处是家 小凶 "	,"心"};

  	gGejuDesc[390]=new String[]{"柱加休","孤云野鹤，枕流漱石 大吉"	,"柱"};
  	gGejuDesc[391]=new String[]{"柱加生","怜我怜卿，鸾凤和鸣 大吉"	,"柱"};
  	gGejuDesc[392]=new String[]{"柱加伤","上下其手，一手遮天 大凶"	,"柱"};
  	gGejuDesc[393]=new String[]{"柱加杜","七颠八倒，自相矛盾 大凶"	,"柱"};
  	gGejuDesc[394]=new String[]{"柱加景","先著祖鞭，长治久安 小吉"	,"柱"};
  	gGejuDesc[395]=new String[]{"柱加死","引狼入室，虎视耽耽 小凶"	,"柱"};
  	gGejuDesc[396]=new String[]{"柱加惊","投机取巧，惟利是图 大凶"	,"柱"};
  	gGejuDesc[397]=new String[]{"柱加开","有勇有谋，果决前进 大吉 "	,"柱"};

  	gGejuDesc[400]=new String[]{"任加休","因材施教，青出于蓝 大吉"	,"任"};
  	gGejuDesc[401]=new String[]{"任加生","前山后海，进退两难 小凶"	,"任"	};
  	gGejuDesc[402]=new String[]{"任加伤","饥不择食，饮鸩止渴 小凶"		,"任"};
  	gGejuDesc[403]=new String[]{"任加杜","积劳成疾，病入膏肓 小凶"		,"任"};
  	gGejuDesc[404]=new String[]{"任加景","衣暖食饱，琼楼玉宇 小吉"		,"任"};
  	gGejuDesc[405]=new String[]{"任加死","明目张胆，坐地分赃 大凶"	,"任"};
  	gGejuDesc[406]=new String[]{"任加惊","一无所得，人财两空 小凶"	,"任"};
  	gGejuDesc[407]=new String[]{"任加开","仓斗库实，有备无患 大吉"	,"任"};

  	gGejuDesc[410]=new String[]{"英加休","寸进尺退，一事无成 小凶 "	,"英"};
  	gGejuDesc[411]=new String[]{"英加生","游山玩水，走遍天下 大吉 "	,"英"};
  	gGejuDesc[412]=new String[]{"英加伤","龙争虎斗，两败俱伤 小凶 "	,"英"};
  	gGejuDesc[413]=new String[]{"英加杜","倚官仗势，作威作福 小凶 "	,"英"};
  	gGejuDesc[414]=new String[]{"英加景","春风一度，始乱终弃 小凶 "	,"英"};
  	gGejuDesc[415]=new String[]{"英加死","连年征战，劳民伤财 小凶 "	,"英"};
  	gGejuDesc[416]=new String[]{"英加惊","目瞪口呆，方寸已乱 小凶 "	,"英"};
  	gGejuDesc[417]=new String[]{"英加开","金碧辉煌，前呼后拥 大吉 "	,"英"};

  	gGejuDesc[420]=new String[]{"真诈","开休生+乙丙丁+阴；"+HH+"诈就是设计谋事，三诈格百事皆吉。"};
  	gGejuDesc[421]=new String[]{"休诈","开休生+乙丙丁+合；"+HH+"诈就是设计谋事，三诈格百事皆吉。"};
  	gGejuDesc[422]=new String[]{"重诈","开休生+乙丙丁+地；"+HH+"诈就是设计谋事，三诈格百事皆吉。"};  	
  	gGejuDesc[423]=new String[]{"天假","乙丙丁+景+天。宜争战诉讼、见贵求官、上书献策、谈判结盟；"+HH+"假就是借助某种力量来谋事，五假如门迫制，奇仪入墓，则难以借八神之灵气达到目的。"};  	
  	gGejuDesc[424]=new String[]{"地假","丁己癸+杜+地。宜潜藏埋伏、逃亡躲灾、谋探私事；"+HH+"假就是借助某种力量来谋事，五假如门迫制，奇仪入墓，则难以借八神之灵气达到目的。"};
  	gGejuDesc[425]=new String[]{"人假","壬+惊+天。宜捕捉逃亡、如再遇太白入荧一定能抓获逃亡者；"+HH+"假就是借助某种力量来谋事，五假如门迫制，奇仪入墓，则难以借八神之灵气达到目的。"};
  	gGejuDesc[426]=new String[]{"神假","丁己癸+伤+合。宜索债、捕捉、交易、伏藏；"+HH+"假就是借助某种力量来谋事，五假如门迫制，奇仪入墓，则难以借八神之灵气达到目的。"};
  	gGejuDesc[427]=new String[]{"鬼假","丁己癸+死+地。宜超度亡灵、破土修茔、伐邪、狩猎；"+HH+"假就是借助某种力量来谋事，五假如门迫制，奇仪入墓，则难以借八神之灵气达到目的。"};
  	
  	gGejuDesc[428]=new String[]{"人遁","丁/乙+休+阴。大吉之格，百事谋为，均为吉利；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	gGejuDesc[429]=new String[]{"神遁","丙+生+天。宜祭祀祈神，建坛点将，训练士兵，挥师远征；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	gGejuDesc[430]=new String[]{"鬼遁","(丁+杜+地)｜(丁+开+地)。宜偷袭攻虚，禳镇灾邪，超度亡灵；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};  	
  	gGejuDesc[431]=new String[]{"天遁","丙/丁+生，月奇朱雀吉格，临吉门，自然百事生旺，谋为吉利；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	gGejuDesc[432]=new String[]{"地遁","乙/己+开。日奇入墓凶格，临吉门有救，地户逢开门宜扎寨藏兵，"+HH+"建筑修造，埋伏截击，吉不如天遁；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	gGejuDesc[433]=new String[]{"云遁","乙/辛+开休生三吉门之一。宜求雨，立营寨，造军械，埋伏掩袭敌人。"+HH+"也利于外出云游，修炼仙道；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	gGejuDesc[434]=new String[]{"龙遁","乙/(癸|坎)+开休生。宜练水军水战，修桥穿井筑堤开渠，密送机谋，掩袭敌人；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	gGejuDesc[435]=new String[]{"虎遁","(乙/辛+艮+休生)｜(庚+开+兑)。宜安营扎寨，招安设伏，建筑修造，捕捉射猎；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};  	
  	gGejuDesc[436]=new String[]{"风遁","乙/巽+开休生。巽主风故名，宜祷风雨、行垒战、用火攻，用飞砂走石来对付敌军；"+HH+"如吉门被迫制、三奇入墓，则难以称心如意。"};
  	
  	gGejuDesc[437]=new String[]{"遁甲开","六甲加会阳星，为开。主动，百事吉。","遁开"};
  	
  	gGejuDesc[450]=new String[]{"子午冲","一身不安。"};
  	gGejuDesc[451]=new String[]{"寅申冲","多情且好管闲事。"};
  	gGejuDesc[452]=new String[]{"辰戌冲","克亲伤子寿短。"};
  	
  	gGejuDesc[455]=new String[]{wxxqs1,"八门为同我者旺，九星为我生者旺；"};
  	gGejuDesc[456]=new String[]{wxxqs2,"八门为生我者相，九星为同我者相；"};
  	gGejuDesc[457]=new String[]{wxxqs3,"八门为我生者休，九星为我克者休；"};
  	gGejuDesc[458]=new String[]{wxxqs4,"八门为我克者囚，九星为克我者囚；"};
  	gGejuDesc[459]=new String[]{wxxqs5,"八门为克我者死；"};
  	gGejuDesc[460]=new String[]{wxxqs6,"九星为生我者废，绝不会死，顶多不起作用；"};
  	gGejuDesc[461]=new String[]{zphym1,"宫克门为制，吉门被克吉不就，凶门被克凶不起；"};
  	gGejuDesc[462]=new String[]{zphym2,"门克宫为迫或被迫，吉门克宫吉不就，凶门克宫事更凶；"};
  	gGejuDesc[463]=new String[]{zphym3,"门生宫为和；"};
  	gGejuDesc[464]=new String[]{zphym4,"宫生门为义，吉门被生有大利，凶门得生祸难避；"};
  	gGejuDesc[465]=new String[]{zphym5,"门与宫比和为相；"};
  	gGejuDesc[466]=new String[]{zphym6,"门在宫中入墓；"};
  	
  	gGejuDesc[470]=new String[]{"符加休","符会休兮昼见晴，时有财帛到门庭。壮汉携妇并见齐，虚情假意辩详情。"};
  	gGejuDesc[471]=new String[]{"符加生","符会生门两相宜，兄弟二人悦财喜。文书应侯逢酒色，禄马相逢定不移。"};
  	gGejuDesc[472]=new String[]{"符加伤","值符伤门卦象阴，险途不测莫寻人。如问居家多不利，换庄移家须急论。"};
  	gGejuDesc[473]=new String[]{"符加杜","值符杜门艳阳天，主客相交两不欢。往来只为酒食事，相见不曾露良言。"};
  	gGejuDesc[474]=new String[]{"符加景","符景相会云雨收，谋望多依小人筹。文章异彩衣丝缕，君子嘉爵亦封侯。"};
  	gGejuDesc[475]=new String[]{"符加死","符死相见阴雨加，秋风习习路难拔。来问只为灾祸事，为谋多被小人辖。"};
  	gGejuDesc[476]=new String[]{"符加惊","符惊相会两难晴，天盘逢英利出行。惊虚多因子女起，交易败于露言行。"};
  	gGejuDesc[477]=new String[]{"符加开","值符开门两相排，相约不见有人来。空谈几多虚空事，捕贼需求术士猜。"};
  	gGejuDesc[478]=new String[]{"蛇加休","蛇会休兮晴见少，妇人有灾是非忧。问病多是眼目疾，近水河边难匿逃。"};
  	gGejuDesc[479]=new String[]{"蛇加生","蛇会生兮两相刑，来人必定有威名。遇奇方知贤人至，见英可断讼事兴。"};
  	gGejuDesc[480]=new String[]{"蛇加伤","蛇遇伤兮飓风狂，门上不安有纷张。阴人于家多邪事，盗心不息恶名扬。"};
  	gGejuDesc[481]=new String[]{"蛇加杜","蛇遇杜兮莫捕贼，贼已远去不可追。仪见戊己官司起，不见天蓬不息宁。"};
  	gGejuDesc[482]=new String[]{"蛇加景","蛇遇景兮远信来，火光惊忧惧官灾。路遇矮人并雀叫，少报喜来多报灾。"};
  	gGejuDesc[483]=new String[]{"蛇加死","蛇会死兮病面容，居家有人外逃行。灶上炊烟断已久，铁器时时自发生。"};
  	gGejuDesc[484]=new String[]{"蛇加惊","蛇会惊兮阴私多，遇己方才凑三合。不然多生暧昧事，桃花从中起风波。"};
  	gGejuDesc[485]=new String[]{"蛇加开","蛇遇开兮祀神坛，路遇常是妄论仙。不然多遭小人戏，时结冤仇夜难眠。"};
  	gGejuDesc[486]=new String[]{"阴加休","太阴临休天长阴，忧失财产盗贼临。相邻欺侮文字匿，文士阴谋将丙侵。"};
  	gGejuDesc[487]=new String[]{"阴加生","太阴临生文字交，财帛金银有虚耗。天雨逢伐路边树，行兵败北损财宝。"};
  	gGejuDesc[488]=new String[]{"阴加伤","太阴会伤暗阴和，女子阴私盗贼多。追捕逃亡亦无功，先喜后忧终无措。"};
  	gGejuDesc[489]=new String[]{"阴加杜","太阴杜兮恶人斯，事事难成又主迟。行兵受阻水难渡，金石之物带怀中。"};
  	gGejuDesc[490]=new String[]{"阴加景","太阴临景主喧争，官司阴私口舌频。刑讼牢狱多愁事，风波阵阵几时停。"};
  	gGejuDesc[491]=new String[]{"阴加死","太阴死兮欲谋财，和合婚姻求祝谋。八里可见三孝子，金银财帛藏于怀。"};
  	gGejuDesc[492]=new String[]{"阴加惊","太阴惊兮甘雨降，进兵征战必有功。占事民亡谋计好，刀还钱物镜中晴。"};
  	gGejuDesc[493]=new String[]{"阴加开","太阴开兮远行人，居家恐被贼兵惊。天空晴朗兵可进，凶死龙虎石金形。"};
  	gGejuDesc[494]=new String[]{"合加休","六合休兮寻人难，求物投参事亦然。行兵水阻贤人至，求谋不成事不遂。"};
  	gGejuDesc[495]=new String[]{"合加生","六合生兮雷无雨，守战皆宜西南地。艰难险阻消除尽，更宜上梁盖新居。"};
  	gGejuDesc[496]=new String[]{"合加伤","六合伤兮风雨疾，事故起于西南禺。高山峻岭有骑兵，将防火灾客酒色。"};
  	gGejuDesc[497]=new String[]{"合加杜","六合杜兮契约交，争讼阴私六畜逃。立将家忧三九日，九地天伏利出兵。"};
  	gGejuDesc[498]=new String[]{"合加景","六合景兮天有雷，三人同心擒贼人。颜色赤红斑斓物，出行多厄不顺心。"};
  	gGejuDesc[499]=new String[]{"合加死","六合死兮和会亲，酒宴钱谷会相邻。谋求诸事多欢畅，多顺少逆好天时。"};
  	gGejuDesc[500]=new String[]{"合加惊","六合会惊大旱生，成就交关多虚诈。城中空兮伏兵悍，破关徙河防被捉。"};
  	gGejuDesc[501]=new String[]{"合加开","六合开兮雷电生，捕贼官司囚禁身。百人可阻万人路，四时宜守不宜攻。"};
  	gGejuDesc[502]=new String[]{"虎加休","白虎休兮主争张，占病难安官讼长。谋事求官皆不遂，行兵防诈有虚惊。"};
  	gGejuDesc[503]=new String[]{"虎加生","白虎临生主杀伤，远行凶死病须亡。求谋在天莫强取，行兵险阻防火攻。"};
  	gGejuDesc[504]=new String[]{"虎加伤","白虎伤兮死伤起，人口不知休门己。官司贼人杀伤害，遇事严厉莫宽容。"};
  	gGejuDesc[505]=new String[]{"虎加杜","白虎加杜必死亡，六蓄相争坟道桑。五里逢人相斗讼，求谋谨慎莫轻信。"};
  	gGejuDesc[506]=new String[]{"虎加景","白虎加景凶孝事，官灾病患宅难居。事多反复防女色，求谋做事北方宜。"};
  	gGejuDesc[507]=new String[]{"虎加死","白虎死兮主孤军，竞妇争婚病难痊。行人须防贼来偷，灾星频频忧患缠。"};
  	gGejuDesc[508]=new String[]{"虎加惊","白虎加惊有异云，行兵有险不可进。伤亡病死忧愁事，暗昧不明有灾殃。"};
  	gGejuDesc[509]=new String[]{"虎加开","白虎临开贵出兵，远行事凶出难逃。征战诛伐有人助，追捕掩捉不出营。"};
  	gGejuDesc[510]=new String[]{"武加休","玄武休兮守战休，鬼贼投井产妇厄。求谋难成不宜进，逃亡已远不可捉。"};
  	gGejuDesc[511]=new String[]{"武加生","玄武生兮论文状，鬼着人身争讼多。征战主吉客不利，掩捕东方则可获。"};
  	gGejuDesc[512]=new String[]{"武加伤","玄武伤兮贼即至，求事难成官讼灾。行兵山中有埋伏，商贾之人报贼来。"};
  	gGejuDesc[513]=new String[]{"武加杜","玄武临杜争斗起，恶人牵引酒迷失。兵出奇谋必全胜，贼人已远无消息。"};
  	gGejuDesc[514]=new String[]{"武加景","玄武景兮六畜亡，官司口舌防见伤。行兵征战无险阻，掩捕盗贼在东方。"};
  	gGejuDesc[515]=new String[]{"武加死","玄武死兮井坟院，多耗财帛鬼神惊。行兵两难思进退，主吉客凶要分清。"};
  	gGejuDesc[516]=new String[]{"武加惊","玄武惊兮贼上门，死亡官司总缠身。行兵有险休轻进，捕贼远遁不须寻。"};
  	gGejuDesc[517]=new String[]{"武加开","玄武临开主逃亡，官司纷争起田宅。行兵险阻宜坚守，盗贼藏匿在震方。"};
  	gGejuDesc[518]=new String[]{"地加休","九地休兮晴反雨，行兵地险山火名。西北方位见天使，捕捉不利勿轻敌。"};
  	gGejuDesc[519]=new String[]{"地加生","九地生兮见水灾，用兵进退莫轻裁。朝廷颂恩显荣耀，贤人助我捕盗贼。"};
  	gGejuDesc[520]=new String[]{"地加伤","九地临伤酒席宴，路途奔波行程连。牛羊须防多遭损，卯木克土妇难安。"};
  	gGejuDesc[521]=new String[]{"地加杜","九地临杜小女忧，井灶钱财防散丢。占病难安终难断，远行求谋消息求。"};
  	gGejuDesc[522]=new String[]{"地加景","九地会景门无奇，宜在舟中破顽敌。功成名就声大震，遇捕盗贼尚在巢。"};
  	gGejuDesc[523]=new String[]{"地加死","九地死兮天晴朗，行兵险阴虎道挡。综有贤人来相助，弱不从心主有伤。"};
  	gGejuDesc[524]=new String[]{"地加惊","九地惊兮风云幻，主将变化几多端。客将生疑事见凶，贼人躲藏捕捉难。"};
  	gGejuDesc[525]=new String[]{"地加开","九地开兮太阳红，用兵宜守不宜攻。君子行事须三思，众人同心百事成。"};
  	gGejuDesc[526]=new String[]{"天加休","九天临休云雨散，出兵越境有忧难。功成上级来奖赏，贼逃人报定可捉。"};
  	gGejuDesc[527]=new String[]{"天加生","九天生门多连雨，行兵险阻出可击。贼逃可捉西方位，求谋诸事莫轻心。"};
  	gGejuDesc[528]=new String[]{"天加伤","九天伤兮天大睛，外亲相见吉事多。掩捕贼人东方觅，进退荣耀步云梯。"};
  	gGejuDesc[529]=new String[]{"天加杜","九天杜兮得天时，用兵登舟渡江吉。贼通术数难捕获，客占祥瑞百事遂。"};
  	gGejuDesc[530]=new String[]{"天加景","九天景门主天时，行兵北阻南方利。主将功成褒封至。客将出师法之制。"};
  	gGejuDesc[531]=new String[]{"天加死","九天死兮阴晦风，诸事逢之又小吉。行兵亦主小破敌，忌害贤良易生疑。"};
  	gGejuDesc[532]=new String[]{"天加惊","九天惊门多危难，不宜轻进得助吉。捕捉应在西山下，主将褒封客多谋。"};
  	gGejuDesc[533]=new String[]{"天加开","九天开门比合星，用兵水火西北吉。夫妻双至立交通，主将火灾客避锋。"};  	
  	
  	QiMen2.cp(gGejuDesc, gGejuDesc2);
  }
  
  public static final String[][] JIUGONGINFO = new String[10][2];
  public static final String[][] JIUGONGINFO2 = new String[10][2];
  static{
  	JIUGONGINFO[1] = new String[]{"坎一宫","坤八宫"+HH+"不稳定、曲折、劳心、苦难、色情、智者。"+HH+"事情总不会顺利发展，总会有些波折才能达到结果。"+HH+"与液体或影像有关、流动性的人、事、物，测人有忧患意识；"};
  	JIUGONGINFO[2] = new String[]{"坤二宫","巽五宫"+HH+"主群众，具体、大众的工作，有包容性、随和、脚踏实地。"+HH+"大多演员，画家，艺术家都落坤宫，但很难有政府要员。"+HH+"厚胖、粗糙、内部、第二位有关的人、事、物；"};
  	JIUGONGINFO[3] = new String[]{"震三宫","离三宫"+HH+"震动、奋起、突发性的、机遇突然来临、没有预期的。"+HH+"蓄势震动，瞬间突发、出现动态、摩擦效果的人、事、物；"};
  	JIUGONGINFO[4] = new String[]{"巽四宫","兑二宫"+HH+"进退不果，犹豫不决。"+HH+"坎宫虽然曲折，但却是不断往前走的，巽宫主徘徊，时好时坏。"+HH+"飘动、条、具散发、传播性质的人、事、物；"};
  	JIUGONGINFO[6] = new String[]{"乾六宫","艮七宫"+HH+"落乾宫测的事都比较重要、庞大、涉及面广、难度大、事情起点高。"+HH+"测人为成就大、层次高，一般大人物都落乾震离三宫，发大财的落坤坎宫。"+HH+"测职业，不是大单位就是政府要员，有社会地位的人。"+HH+"震撼、特性突显等特征的人、事、物；"};
  	JIUGONGINFO[7] = new String[]{"兑七宫","坎六宫"+HH+"喜悦、口舌是非、毁损；"+HH+"光有语言，光说不练，会有意外之事发生。"+HH+"表里不一，管状内流动，引导有关的人、事、物；"};
  	JIUGONGINFO[8] = new String[]{"艮八宫","震四宫"+HH+"保守、固执、停滞不前，一步一个脚印，稳稳当当的；"+HH+"慢，测人也是慢性子，但讲诚信，固执。"+HH+"有阻隔性、从大向小延伸有纽带连接的人、事、物如漏斗、显示器；"};
  	JIUGONGINFO[9] = new String[]{"离九宫","乾一宫"+HH+"聪明、名誉、虚心、轰轰烈烈，虎头蛇尾。"+HH+"一开始声势很大，但难以持久。"+HH+"测人好面子，重场面。"+HH+"多彩、外显、出众、求真的人、事、物；"};
  	QiMen2.cp(JIUGONGINFO, JIUGONGINFO2);
  }
  
  /**
   * 八神与八门组合吉凶
   */
  public static final int[][] shen_men={{0,0,0,0,0,0,0,0,0,0,},
  	{0,470,471,472,473,0,474,475,476,477},
  	{0,478,479,480,481,0,482,483,484,485},
  	{0,486,487,488,489,0,490,491,492,493},
  	{0,494,495,496,497,0,498,499,500,501},
  	{0,502,503,504,505,0,506,507,508,509},
  	{0,510,511,512,513,0,514,515,516,517},
  	{0,518,519,520,521,0,522,523,524,525},
  	{0,526,527,528,529,0,530,531,532,533}};
  
  /**
   * 干+干，甲1...癸10，30-100; 
   */
  public static final int[][] gan_gan = {{0,0,0,0,0,0,0,0,0,0,0},
  	{0,0,0,0,0,0,0,0,0,0,0}, {0,0,50,51,52,53,54,55,56,57,58},
  	{0,0,60,61,62,63,64,65,66,67,68}, {0,0,70,71,72,73,74,75,76,77,78},
  	{0,0,80,81,82,83,84,85,86,87,88}, {0,0,90,91,92,93,94,95,96,97,98},
  	{0,0,100,101,102,103,104,105,106,107,108}, {0,0,110,111,112,113,114,115,116,117,118},
  	{0,0,120,121,122,123,124,125,126,127,128}, {0,0,130,131,132,133,134,135,136,137,138}};
  /**
   * 干与干组合的吉凶
   */
  public static final int[] gan_gan_jx={51,52,60,62,63,66,70,71,72,73,77,80,81,82,93,112,120,123,130,133};
  public static final boolean isJige(int genum) {
  	for(int ge : gan_gan_jx)
  		if(ge==genum) return true;
  	return false;
  }
  /**
   * 天盘干与地盘干相冲，如甲子戊5+甲午辛8，甲寅癸10+甲申庚7，甲辰壬9+甲戌己6
   * 干与宫冲为冲，干与干冲为充
   */
  public static final String[][] gan_gan_ch = new String[11][11];
  static{
  	gan_gan_ch[5][8]=gan_gan_ch[8][5]="充";
  	gan_gan_ch[10][7]=gan_gan_ch[7][10]="充";
  	gan_gan_ch[9][6]=gan_gan_ch[6][9]="充";
  }
  
  /**
   * 天盘和地盘的干都用这个
   * 三奇+宫，传参数：天盘干（地盘干）+宫	
   */
  public static final int[][] gan_gong = new int[11][11];
  static{
  	gan_gong[2] = new int[]{0,140,143,146,149,0,152,155,158,161};
  	gan_gong[3] = new int[]{0,141,144,147,150,0,153,156,159,162};
  	gan_gong[4] = new int[]{0,142,145,148,151,0,154,157,160,163};
  } 
  /**
   * 只有天盘的干用这个，主要是六仪击刑
   */
  public static final int[][] gan_gong_t = new int[11][11];
  static{
  	gan_gong_t[5][3] = 164;
  	gan_gong_t[6][2] = 165;
  	gan_gong_t[7][8] = 166;
  	gan_gong_t[8][9] = 167;
  	gan_gong_t[9][4] = 168;
  	gan_gong_t[10][4] = 169;
  	gan_gong_t[10][2] = 169;
  } 
  /**
   * 门+门
   */
  public static final int[][] men_men = new int[10][10];
  static{
  	men_men[1] = new int[]{0,170,171,172,173,0,174,175,176,177};
  	men_men[2] = new int[]{0,180,181,182,183,0,184,185,186,187};
  	men_men[3] = new int[]{0,190,191,192,193,0,194,195,196,197};
  	men_men[4] = new int[]{0,200,201,202,203,0,204,205,206,207};
  	men_men[6] = new int[]{0,210,211,212,213,0,214,215,216,217};
  	men_men[7] = new int[]{0,220,221,222,223,0,224,225,226,227};
  	men_men[8] = new int[]{0,230,231,232,233,0,234,235,236,237};
  	men_men[9] = new int[]{0,240,241,242,243,0,244,245,246,247};
  } 
  /**
   * 门+干
   */
  public static final int[][] men_gan = new int[10][11];
  static{
  	men_gan[1] = new int[]{0,0,250,251,252,253,254,255,256,257,258};
  	men_gan[2] = new int[]{0,0,260,261,262,263,264,265,266,267,268};
  	men_gan[3] = new int[]{0,0,270,271,272,273,274,275,276,277,278};
  	men_gan[4] = new int[]{0,0,280,281,282,283,284,285,286,287,288};
  	men_gan[6] = new int[]{0,0,290,291,292,293,294,295,296,297,298};
  	men_gan[7] = new int[]{0,0,300,301,302,303,304,305,306,307,308};
  	men_gan[8] = new int[]{0,0,310,311,312,313,314,315,316,317,318};
  	men_gan[9] = new int[]{0,0,320,321,322,323,324,325,326,327,328};
  } 
  /**
   * 星+门
   */
  public static final int[][] xing_men = new int[10][10];
  static{
  	xing_men[1] = new int[]{0,330,331,332,333,0,334,335,336,337};
  	xing_men[2] = new int[]{0,340,341,342,343,0,344,345,346,347};
  	xing_men[3] = new int[]{0,350,351,352,353,0,354,355,356,357};
  	xing_men[4] = new int[]{0,360,361,362,363,0,364,365,366,367};
  	xing_men[5] = new int[]{0,370,371,372,373,0,374,375,376,377};
  	xing_men[6] = new int[]{0,380,381,382,383,0,384,385,386,387};
  	xing_men[7] = new int[]{0,390,391,392,393,0,394,395,396,397};
  	xing_men[8] = new int[]{0,400,401,402,403,0,404,405,406,407};
  	xing_men[9] = new int[]{0,410,411,412,413,0,414,415,416,417};
  } 

  /**
   * 休1 死2 伤3 杜4 开6 惊7 生8 景9
   * 符1 蛇2 阴3 合4 虎5 武6 地7 天8
   * 开休生三吉门合乙丙丁三奇上乘太阴者叫真诈，六合者叫休诈，九地者叫重诈，宜经商，远行、婚嫁、百事皆吉。
   * 景门合乙丙丁三奇上乘九天叫天假宜争战诉讼、见贵求官、上书献策、扬兵颁号、申明盟约；
   * 杜门合丁己癸上乘九地或太阴或六合叫地假宜潜藏埋伏、逃亡躲灾、谋探私事；
   * 惊门合六壬上乘九天叫人假宜捕捉逃亡、如再遇太白入荧一定能抓获逃亡者；
   * 伤门合丁己癸上乘九地或六合叫神假（又叫物假）宜埋藏伏藏、祈祷索债、捕捉交易、使人难知；
   * 死门合丁己癸上乘九地叫鬼假（又叫神假）宜超度亡灵、抚重安民、破土修茔、伐邪、狩猎。
   * 人遁：天盘丁奇，中盘休门，神盘太阴。此遁得星精之蔽，其方可以和谈，探密，伏藏，求贤，结婚，交易，献策。
   * 神遁：天盘丙奇，中盘生门，神盘九天。宜攻虚，开路，塞河，造像。
   * 鬼遁：天盘丁奇，中盘杜门，神盘九地。宜偷袭攻虚。
   */
  public static final int[][][] men_tgan_shen = new int[10][11][9];
  static{  //此专用于天盘干+门+神
  	men_tgan_shen[1][4][3] = 428; 
  	men_tgan_shen[8][3][8] = 429;
  	men_tgan_shen[4][4][7] = 430;
  }
  public static final int[][][] men_gan_shen = new int[10][11][9];
  static{  	  		
  	men_gan_shen[2][4][7] = men_gan_shen[2][6][7] = men_gan_shen[2][10][7] = 427;
  	
  	men_gan_shen[3][4][7] = men_gan_shen[3][6][7] = men_gan_shen[3][10][7] = 426;
  	men_gan_shen[3][4][4] = men_gan_shen[3][6][4] = men_gan_shen[3][10][4] = 426;
  	
  	men_gan_shen[7][9][8] = 425;
  	
  	men_gan_shen[4][4][7] = men_gan_shen[4][6][7] = men_gan_shen[4][10][7] = 424;
  	men_gan_shen[4][4][3] = men_gan_shen[4][6][3] = men_gan_shen[4][10][3] = 424;
  	men_gan_shen[4][4][4] = men_gan_shen[4][6][4] = men_gan_shen[4][10][4] = 424;
  	
  	men_gan_shen[9][2][8] = men_gan_shen[9][3][8] = men_gan_shen[9][4][8] = 423;
  	
  	men_gan_shen[6][2][3] = men_gan_shen[6][3][3] = men_gan_shen[6][4][3] = 420;
  	men_gan_shen[1][2][3] = men_gan_shen[1][3][3] = men_gan_shen[1][4][3] = 420;
  	men_gan_shen[8][2][3] = men_gan_shen[8][3][3] = men_gan_shen[8][4][3] = 420;
  	
  	men_gan_shen[6][2][4] = men_gan_shen[6][3][4] = men_gan_shen[6][4][4] = 421;
  	men_gan_shen[1][2][4] = men_gan_shen[1][3][4] = men_gan_shen[1][4][4] = 421;
  	men_gan_shen[8][2][4] = men_gan_shen[8][3][4] = men_gan_shen[8][4][4] = 421;
  	
  	men_gan_shen[6][2][7] = men_gan_shen[6][3][7] = men_gan_shen[6][4][7] = 422;
  	men_gan_shen[1][2][7] = men_gan_shen[1][3][7] = men_gan_shen[1][4][7] = 422;
  	men_gan_shen[8][2][7] = men_gan_shen[8][3][7] = men_gan_shen[8][4][7] = 422;
  }
  /**
   * 休1 死2 伤3 杜4 开6 惊7 生8 景9
   * 天遁：天盘丙奇，中盘生门，地盘丁奇。生门主兴隆，又得月华之气，百事生旺，利上书，求官，行商，隐迹，婚姻等。
   * 地遁：天盘乙奇，中盘开门，地盘六己。此遁开门通达，又得日精之蔽，百事皆吉。宜扎寨藏兵，修造，逃亡绝迹，安坟等。
   * 云遁：天盘乙奇，中盘开，休，生三吉门之一，地盘六辛。此遁得云精之蔽，宜求雨，立营寨，造军械。
   * 龙遁：天盘乙奇，中盘开，休，生三吉门之一，地盘坎一宫或六癸。宜求雨，利水路，修桥凿井。
   * 虎遁：天盘乙奇，中盘休门，地盘六辛或艮八宫。宜招降，立寨，守御。
   */
  public static final int[][][] gan_men_gan = new int[11][10][11];
  static{
  	gan_men_gan[3][8][4] = 431;
  	gan_men_gan[2][6][6] = 432;
  	gan_men_gan[2][6][8] = gan_men_gan[2][1][8] = gan_men_gan[2][8][8] = 433;
  	gan_men_gan[2][6][10] = gan_men_gan[2][1][10] = gan_men_gan[2][8][10] = 434;
  	gan_men_gan[2][1][8] = 435;
  }
  /**
   * 休1 死2 伤3 杜4 开6 惊7 生8 景9
   * 风遁：天盘乙奇，中盘开，休，生三吉门之一，地盘为巽四宫。如风从西北方来，宜顺风击敌；如风从东方来，"+HH+"敌方在东方，南方，皆不可交战。
   * 龙遁：天盘乙奇，中盘开，休，生三吉门之一，地盘坎一宫或六癸。宜求雨，利水路，修桥凿井。
   * 虎遁：天盘乙奇，中盘休门，地盘六辛或艮八宫。宜招降，立寨，守御。
   */
  public static final int[][][] gan_men_gong = new int[11][10][10];
  static{
  	gan_men_gong[2][6][4] = gan_men_gong[2][1][4] = gan_men_gong[2][8][4] = 436;
  	gan_men_gong[2][6][1] = gan_men_gong[2][1][1] = gan_men_gong[2][8][1] = 434;
  	gan_men_gong[2][1][8] = 435;
  }
  /**
   * 遁甲开：六甲加会阳星，为开。主动，百事吉。
   */
  public static final int[][] gan_xing = new int[11][10];
  static{
  	gan_xing[5][1] = gan_xing[5][2] =gan_xing[5][3] =gan_xing[5][4] =gan_xing[5][5] =437;
  	gan_xing[6][1] = gan_xing[6][2] =gan_xing[6][3] =gan_xing[6][4] =gan_xing[6][5] =437;
  	gan_xing[7][1] = gan_xing[7][2] =gan_xing[7][3] =gan_xing[7][4] =gan_xing[7][5] =437;
  	gan_xing[8][1] = gan_xing[8][2] =gan_xing[8][3] =gan_xing[8][4] =gan_xing[8][5] =437;
  	gan_xing[9][1] = gan_xing[9][2] =gan_xing[9][3] =gan_xing[9][4] =gan_xing[9][5] =437;
  	gan_xing[10][1] = gan_xing[10][2] =gan_xing[10][3] =gan_xing[10][4] =gan_xing[10][5] =437;
  }
}