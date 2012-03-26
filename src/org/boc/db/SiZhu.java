package org.boc.db;

public class SiZhu {
  /**
   * 四柱干支
   * 年上起月法
   * 日上起时法
   */
  public static int ng = 0;
  public static int nz = 0;
  public static int yg = 0;
  public static int yz = 0;
  public static int rg = 0;
  public static int rz = 0;
  public static int sg = 0;
  public static int sz = 0;
  public static int tyg = 0;
  public static int tyz = 0;
  public static int mgg = 0;
  public static int mgz = 0;

  public final static int LNBEGIN=20;  //排7柱中流年的最小岁数
  public final static int LNEND=50;  //排7柱中流年的最大岁数

  public static final int[] yueByNian = {0,3,5,7,9,1,3,5,7,9,1};
  public static final int[] shiByRi =   {0,1,3,5,7,9,1,3,5,7,9};

  public static String yinli = "";
  public static String yangli = "";
  public static String sex = "";
  public static boolean ISMAN ;
  public static int NAYININT = 0;
  public static String SPLIT = "";
  public static final String SZNAME = "四柱：";
  public static final String DYNAME = "大运：";
  public static final String XYNAME = "小运：";
  public static final String LLNAME = "流年：";
  //大运的天干地支。9为8步大运；3为1天干2地支；
  public static int[][] DAYUN = new int[9][3];
  public static int[][] XIAOYUN = new int[LNEND][3];
  public static int[][][] QIZHU = new int[LNEND][8][3];
  public static int QIYUNSUI = 0;   //起运岁数，有余数舍去
  public static final String NUM2 = "xh";

  //天干化合要则:
  //首先，干支化合，有合化与只合不化之别。天干合化与否，须以日干为主，紧临月干或时干为合，且月支须为合化之同一五行方论合化。如甲与己合土，须日干为甲，月干或时干为己;日干为己，月干或时干为甲，而且月支为辰戌丑未土月生人，与合化之土五行相同方可论化。还有两种情况也可合化:一是年月天干相合，年支为合化之五行有根得化。如年庚月乙合金，年支为申金。
  //二是日干与月干或日干与时干合，月支不化，所化五行在其余三支为三合局或三会局也可论化。如庚日与乙月合金，月支不是申或酉，但年日时支合成了申子辰或申酉戌，其化成功。地支合化与否，要两支紧临相贴，且天干须透出地支合化之五行方可论化。如卯与戌合火，天干透出丙火或丁火，与地支合化之火同为一五行而论化。相临之合不化，以合而不化论。其次，凡天合，地合，合化之后，以合化后的五行论，原五行失却其作用;合而不化，为独立五行，均不再与其他干支论生克刑冲。
  //天干生克要则:
  //天干相生:临干之生，其生力大于隔干;同性之生，其生力大于异性;生者减气，受生者得益。天干相克:吉神相克为凶，凶神相克为吉;两干相克，临干力大，隔干其次，远干无力;两干同性相克之力大于异性相克;两干相克均受损，受克损伤大;隔干之克，中隔之干化克则不以克论。如丙火隔干克庚金，中隔土，是土泄火气而生金气，连续相生，故以生论不以克论。克中有合，合去克则不作克论。如丙火克庚金，但柱中有辛，丙辛合水，水克火，丙火克不了庚金，故不以克论。日干被他干克，又有克他干的制，不作克论。如庚日干被丙月干克，丙月干则被壬年干制服，故不以丙庚而作壬丙可论。
  public static final String[][] GANHEZHU= new String[11][11];
  static
  {
    GANHEZHU[0][0]="干合者，有早婚之兆。";
    GANHEZHU[1][6]="甲己合化土，为中正之合。主安分守己，重信讲义。";
    //若命局无他土，又带七杀，则缺乏情义，诡计多端，性刚。甲日干合己，遇乙木，妻财暗损。丁火，衣禄成空。辛金，贵显高门。戊土，家殷大富。癸水，平生发福。庚金，家徒四壁。丙火，禄享千钟。己日干合甲，遇丁火，他人凌辱。乙木，自己遭遇。辛金，家殷大富。庚金，孤寒白屋。癸水，官职迁荣。
    GANHEZHU[2][7]="乙庚合化金，主仁义之合。刚柔兼备，重仁守义。";
    //若有偏官或坐死绝等弱运者，反固执己见，轻仁寡义。乙日干合庚，遇丙火，蹇难。壬水，荣华。丁火，似春花之笑日。己土，满堂金玉。辛金，若秋草逢霜。甲木，麻麦盈仓。庚日干合乙，遇辛金，暗损。丙火，相煎。丁火，如蛟龙得云雨。癸水，田园漂荡。壬水，财禄增减。戊土，不成巨富，逢壬水助方永保长年。
    GANHEZHU[3][8]="丙辛合化水，主威严之合。仪表威严，智力优秀。";
    //若带七杀或座死绝者，反性酷无情，乖僻寡和。女命逢支冲，合化之水，主性感纵欲。丙日干合辛，遇戊土，成名。乙木，官爵迁荣。癸水己土，家门显赫。壬水辰土，祸败。辛日干合丙，遇戊土庚金，功名。
    GANHEZHU[4][9]="丁壬合化木，主仁寿之合。心地仁慈，长命多寿。";
    //妇命若命局水过旺泄木，则为淫欲之合。若座死绝者，酒色破家。丁日干合壬，遇丙火，历年安逸。辛金，一进优游，富贵双全。戊土，活计消遣。癸水，生涯寂寞。乙木重重，财禄无成。庚金叠叠，功名莫望。喜甲临辰，禄封双美。喜己共酉，亦禄封双美。壬日干合丁，遇甲木，多遭仆马。辛金，广置田庄。丙火，英雄豪杰。癸水，辛苦经商。己土，佩印乘轩。戊土，漂蓬落魄。庚金，皓首无成。乙木，青年不遇。
    GANHEZHU[5][10]="戊癸合化火，主无情之合。相貌俊秀，薄情乏义。男多抱玩世之心，女则多嫁俊夫。";
    //戊日干合癸，遇乙木，终能显达。壬水，独自丰隆。丙火，难寻福禄。庚金，易见亨通。己土，妻子有损。辛金，谋略为拙。癸日干合戊，遇丙辛，一世多成败。甲己，历年劳心。丁火，仓库丰肥。庚金，田财殷实。乙木，官爵陆荣。壬水，财禄两全。辛金，财缘得失。己土，仕途蹭蹬。
  }

  //为h*100+m。子时序号为1，最大为59分，丑时序号为2，最大259分
  public static final int[] HOURNUM = {0,60,300,500,700,900,1100,1300,1500,1700,1900,2100,2300};
  //由时辰反推12时辰，如0点为序号1即子时，1点为序号2即丑时
  public static final int[] RHOURNUM = {1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12,0};
  //干支阴阳
  public static final int[] GANYINYANG = {0,1,0,1,0,1,0,1,0,1,0};
  public static final int[] ZIYINYANG =  {0,1,0,1,0,1,0,1,0,1,0,1,0};
  //六十甲子纳音
  public static final String[][] NAYIN = new String[11][13];
  static
  {
  NAYIN[1] = new String[]{null,"海中金","","大溪水","","覆灯火","","砂中金","","泉中水","","山头火",""};
  NAYIN[2] = new String[]{null,"","海中金","","大溪水","","覆灯火","","砂中金","","泉中水","","山头火"};
  NAYIN[3] = new String[]{null,"涧下水","","炉中火","","沙中土","","天河水","","山下火","","屋上土",""};
  NAYIN[4] = new String[]{null,"","涧下水","","炉中火","","沙中土","","天河水","","山下火","","屋上土"};
  NAYIN[5] = new String[]{null,"霹雳火","","城头土","","大林木","","天上火","","大驿土","","平地木",""};
  NAYIN[6] = new String[]{null,"","霹雳火","","城头土","","大林木","","天上火","","大驿土","","平地木"};
  NAYIN[7] = new String[]{null,"壁上土","","松柏木","","白蜡金","","路傍土","","石榴木","","钗钏金",""};
  NAYIN[8] = new String[]{null,"","壁上土","","松柏木","","白蜡金","","路傍土","","石榴木","","钗钏金"};
  NAYIN[9] = new String[]{null,"桑拓木","","锡箔金","","长流水","","杨柳木","","剑锋金","","大海水",""};
  NAYIN[10] =new String[]{null,"","桑拓木","","锡箔金","","长流水","","杨柳木","","剑锋金","","大海水"};
}

  //五行之性、盛、衰、宜之事、主疾病
  public static final String[][] wxxing = new String[6][4];
  static
  {
    wxxing[1] = new String[]{"金主义，其性刚，其情烈，其味辣，其色白。",
        "金盛，主人骨肉相称，面方白净，眉高眼深，体健神清。为人刚毅果断，疏财仗义，深知廉耻。太过则有勇无谋，贪欲不仁。",
        "金衰，主人身材瘦小，为人刻薄内毒，喜淫好杀，吝啬贪婪。",
        "宜金，喜西方。可从事精纤材或金属工具材料，坚硬，决断，武术，鉴定，总管，汽车，交通，金融，工程，种子，开矿，民意代表，伐木，机械等方面的经营和工作。",
        "金:肺与大肠互为脏腑表里，又属气管及整个呼吸系统。过旺或过衰，较宜患大肠，肺，脐，咳痰，肝，皮肤，痔疮，鼻气管等方面的疾病。",
        "",
        ""};
    wxxing[2] = new String[] {"木主仁，其性直，其情和，其味酸，其色青。",
        "木盛，主人长得丰姿秀丽，骨骼修长，手足细腻，口尖发美，面色青白。为人有博爱恻隐之心，慈祥恺悌之意，清高慷慨，质朴无伪。",
        "木衰，主人个子瘦长，头发稀少，性格偏狭，嫉妒不仁。木气死绝，主人眉眼不正，项长喉结，肌肉干燥，为人鄙下吝啬。",
        "宜木，喜东方。可从事木材，木器，家具，装潢，木成品，纸业，种植，养花，育树苗，敬神物品，香料，植物性素食品等经营和事业。",
        "木:肝与胆互为脏腑表里，又属筋骨和四肢。过旺或过衰，较宜患肝，胆，头，颈，四肢，关节，筋脉，眼，神经等方面的疾病。",
        "",
        ""};
    wxxing[3] = new String[]{"水主智，其性聪，其情善，其味咸，其色黑。",
        "水旺，主人面黑有采，语言清和，为人深思熟虑，足智多谋，学识过人。太过则好说是非，飘荡贪淫。",
        "水衰，主人短小，性情无常，胆小无略，行事反覆。",
        "宜水，喜北方。可从事航海，冷温不燃液体，冰水，鱼类，水产，水利，冷藏，冷冻，打捞，洗洁，扫除，流水，港口，泳池，湖池塘，浴池，冷食物买卖，飘游，奔波，流动，连续性，易变化，属水性质，音响性质，清洁性质，海上作业，迁旅，特技表演，运动，导游，旅行，玩具，魔术，记者，侦探，旅社，灭火器具，钓鱼器具，医疗业，药物经营，医生，护士，占卜等方面的经营和工作。",
        "水:肾与膀胱互为脏腑表里，又属脑与泌尿系统。过旺或过衰，较宜患肾，膀胱，胫，足，头，肝，泌尿，阴部，腰部，耳，子宫，疝气等方面的疾病。",
        "",
        ""};
    wxxing[4] = new String[]{"火主礼，其性急，其情恭，其味苦，其色赤。",
        "火盛，主人头小脚长，上尖下阔，浓眉小耳，精神闪烁，为人谦和恭敬，纯朴急躁。",
        "火衰，主人则黄瘦尖楞，语言妄诞，诡诈妒毒，做事有始无终。",
        "宜火，喜南方。可从事放光，照明，光学，高热，易燃，油类，酒精类，热饮食，食品，理发，化妆品，人身装饰品，文艺，文学，文具，文化学生，文人，作家，写作，撰文，教员，校长，秘书，出版，公务，正界等方面的经营和事业。",
        "火:心脏与小肠互为脏腑表里，又属血脉及整个循环系统。过旺或过衰，较宜患小肠，心脏，肩，血液，经血，脸部，牙齿，腹部，舌部等方面的疾病。",
        "",
        ""};
    wxxing[5] = new String[]{"土主信，其性重，其情厚，其味甘，其色黄。",
        "土盛，主人圆腰廓鼻，眉清木秀，口才声重。为人忠孝至诚，度量宽厚，言必行，行必果。土气太过则头脑僵化，愚拙不明，内向好静。",
        "不衰，主人面色忧滞，面扁鼻低，为人狠毒乖戾，不讲信用，不通情理。",
        "宜土，喜中央之地，本地。可从事土产，地产，农村，畜牧，布匹，服装，纺织，石料，石灰，山地，水泥，建筑，房产买卖，雨衣，雨伞，筑堤，容水物品，当铺，古董，中间人，律师，管理，买卖，设计，顾问，丧业，筑墓，墓地管理，僧尼等方面的经营和事业。",
        "土:脾与胃互为脏腑表里，又属肠及整个消化系统。过旺或过衰，较宜患脾，胃，肋，背，胸，肺，肚等方面的疾病。",
        "",
        ""};
  }

  /**
   * 旺半合
   */
  public static final int[][] wangbanhe = new int[13][13];
  static{
    wangbanhe[12][4]=wangbanhe[4][8]=YiJing.MU;
    wangbanhe[3][7]=wangbanhe[7][11]=YiJing.MU;
    wangbanhe[6][10]=wangbanhe[10][2]=YiJing.MU;
    wangbanhe[9][1]=wangbanhe[1][5]=YiJing.MU;
  }

  /**
   * 得到十神
   * 天干十神推断
   * 得到地支循藏
   */
  public static final String[] SHISHEN = new String[]{"主","比","劫","食","伤","财","才","杀","官","枭","印"};
  public static final String[] SHISHEN2 = new String[]{"主","比肩","劫财","食神","伤官","偏财","正财","偏官","正官","偏印","正印"};
  public static final int[][] TGSHISHEN = new int[11][11];
  static
  {
    TGSHISHEN[1] = new int[]{0, 1,2, 3,4, 5,6, 7,8, 9,10};
    TGSHISHEN[2] = new int[]{0, 2,1, 4,3, 6,5, 8,7, 10,9};
    TGSHISHEN[3] = new int[]{0, 9,10,1,2, 3,4, 5,6, 7,8 };
    TGSHISHEN[4] = new int[]{0, 10,9,2,1, 4,3, 6,5, 8,7 };
    TGSHISHEN[5] = new int[]{0, 7,8, 9,10,1,2, 3,4, 5,6 };
    TGSHISHEN[6] = new int[]{0, 8,7, 10,9,2,1, 4,3, 6,5 };
    TGSHISHEN[7] = new int[]{0, 5,6, 7,8, 9,10, 1,2, 3,4};
    TGSHISHEN[8] = new int[]{0, 6,5, 8,7, 10,9, 2,1, 4,3};
    TGSHISHEN[9] = new int[]{0, 3,4, 5,6, 7,8, 9,10, 1,2};
    TGSHISHEN[10] = new int[]{0,4,3, 6,5, 8,7, 10,9, 2,1};
  }
  public static final int[][] DZXUNCANG = new int[13][3];
  static
  {
    DZXUNCANG[1] = new int[]{10};
    DZXUNCANG[2] = new int[]{6,10,8};
    DZXUNCANG[3] = new int[]{1,3,5};
    DZXUNCANG[4] = new int[]{2};
    DZXUNCANG[5] = new int[]{5,2,10};
    DZXUNCANG[6] = new int[]{3,5,7};
    DZXUNCANG[7] = new int[]{4,6};
    DZXUNCANG[8] = new int[]{6,4,2};
    DZXUNCANG[9] = new int[]{7,9,5};
    DZXUNCANG[10] = new int[]{8};
    DZXUNCANG[11] = new int[]{5,8,4};
    DZXUNCANG[12] = new int[]{9,1};
  }
  /**
   * 十天干生旺死绝表
   */
  public static final String[] TGSWSJNAME = new String[]{null,"生","沐","冠","禄","刃","衰","病","死","墓","绝","胎","养"};
  public static final int[][] TGSWSJ = new int[11][13];
  static
  {
    TGSWSJ[1] = new int[]{0,2,3,4,5,6,7,8,9,10,11,12,1};
    TGSWSJ[2] = new int[]{0,7,6,5,4,3,2,1,12,11,10,9,8};
    TGSWSJ[3] = new int[]{0,11,12,1,2,3,4,5,6,7,8,9,10};
    TGSWSJ[4] = new int[]{0,10,9,8,7,6,5,4,3,2,1,12,11};
    TGSWSJ[5] = new int[]{0,11,12,1,2,3,4,5,6,7,8,9,10};
    TGSWSJ[6] = new int[]{0,10,9,8,7,6,5,4,3,2,1,12,11};
    TGSWSJ[7] = new int[]{0,8,9,10,11,12,1,2,3,4,5,6,7};
    TGSWSJ[8] = new int[]{0,1,12,11,10,9,8,7,6,5,4,3,2};
    TGSWSJ[9] = new int[]{0,5,6,7,8,9,10,11,12,1,2,3,4};
    TGSWSJ[10] = new int[]{0,4,3,2,1,12,11,10,9,8,7,6,5};
  }

  //天乙贵人，日干见地支
  public static final boolean[][] TIANYI = new boolean[11][13];
  static
  {
    TIANYI[1][2]=true;TIANYI[1][8]=true;TIANYI[5][2]=true;TIANYI[5][8]=true;
    TIANYI[2][1]=true;TIANYI[2][9]=true;TIANYI[6][1]=true;TIANYI[6][9]=true;
    TIANYI[3][10]=true;TIANYI[3][12]=true;TIANYI[4][10]=true;TIANYI[4][12]=true;
    TIANYI[7][3]=true;TIANYI[7][7]=true;TIANYI[8][3]=true;TIANYI[8][7]=true;
    TIANYI[9][4]=true;TIANYI[9][6]=true;TIANYI[10][4]=true;TIANYI[10][6]=true;
  }
  //太极贵人，日干见地支
  public static final boolean[][] TAIJI = new boolean[11][13];
  static
  {
    TAIJI[1][1]=true;  TAIJI[1][7]=true;  TAIJI[2][1]=true;  TAIJI[2][7]=true;
    TAIJI[3][10]=true; TAIJI[3][4]=true;  TAIJI[4][10]=true; TAIJI[4][4]=true;
    TAIJI[5][5]=true;  TAIJI[5][8]=true;  TAIJI[5][11]=true; TAIJI[5][2]=true;
    TAIJI[6][5]=true;  TAIJI[6][8]=true;  TAIJI[6][11]=true; TAIJI[6][2]=true;
    TAIJI[7][3]=true;  TAIJI[7][12]=true;  TAIJI[8][3]=true;  TAIJI[8][12]=true;
    TAIJI[9][6]=true;  TAIJI[9][9]=true;  TAIJI[10][9]=true; TAIJI[10][6]=true;
  }
  //天德贵人,天干为1，地支为2，生月见天干或地支
  public static final boolean[][][] TIANDE = new boolean[3][13][13];
  static
  {
    TIANDE[1][3][4] =true;  TIANDE[2][4][9] =true;  TIANDE[1][5][9] =true;  TIANDE[1][6][8] =true;
    TIANDE[2][7][12] =true; TIANDE[1][8][1] =true;  TIANDE[1][9][10] =true; TIANDE[2][10][3] =true;
    TIANDE[1][11][3] =true; TIANDE[1][12][2] =true; TIANDE[2][1][6] =true;  TIANDE[1][2][7] =true;
  }
  //月德贵人，生月见天干
  public static final boolean[][] YUEDE = new boolean[13][11];
  static
  {
    YUEDE[3][3] =true;  YUEDE[7][3] =true;  YUEDE[11][3] =true;
    YUEDE[9][9] =true;  YUEDE[1][9] =true;  YUEDE[5][9] =true;
    YUEDE[4][1] =true;  YUEDE[8][1] =true;  YUEDE[12][1] =true;
    YUEDE[6][7] =true;  YUEDE[2][7] =true;  YUEDE[10][7] =true;
  }
  //三奇贵人天干或地支
  public static final boolean[][][] SHANJI = new boolean[13][13][13];
  static
  {
    SHANJI[2][3][4] = true;
    SHANJI[4][6][7] = true;
  }
  //福星贵人，年干或日干见地支
  public static final boolean[][] FUXING = new boolean[11][13];
  static
  {
    FUXING[1][3]=true;  FUXING[3][3]=true;  FUXING[1][1]=true;  FUXING[3][1]=true;
    FUXING[5][9]=true;  FUXING[6][8]=true;  FUXING[4][12]=true; FUXING[2][4]=true;
    FUXING[2][2]=true;  FUXING[10][2]=true; FUXING[10][4]=true; FUXING[7][7]=true;
    FUXING[8][6]=true;  FUXING[9][5]=true;
  }
  //文昌贵人，以年干或日干见地支
  public static final boolean[][] WENCANG = new boolean[11][13];
  static
  {
    FUXING[1][6]=true;  FUXING[1][7]=true;  FUXING[2][6]=true;  FUXING[2][7]=true;
    FUXING[3][9]=true;  FUXING[5][9]=true;  FUXING[4][10]=true; FUXING[6][10]=true;
    FUXING[7][12]=true; FUXING[8][1]=true;  FUXING[9][3]=true;  FUXING[10][4]=true;
  }
  //魁罡贵人，日柱
  public static final boolean[][] KUIGANG = new boolean[11][13];
  static
  {
    KUIGANG[9][5]=true;  KUIGANG[7][11]=true;  KUIGANG[7][5]=true;  KUIGANG[5][11]=true;
  }
  //国印贵人，以年干或日干见地支
  public static final boolean[][] GUOYIN = new boolean[11][13];
  static
  {
    GUOYIN[1][11]=true;  GUOYIN[2][12]=true;  GUOYIN[3][2]=true;  GUOYIN[4][3]=true;
    GUOYIN[5][2]=true;  GUOYIN[6][3]=true;  GUOYIN[7][5]=true;
    GUOYIN[8][6]=true;  GUOYIN[9][8]=true;  GUOYIN[10][9]=true;
  }
  //学堂，纳音(金1木2水3火4土5)见柱
  public static final boolean[][][] XUETANG = new boolean[6][11][13];
  static
  {
    XUETANG[1][8][6]=true;  XUETANG[2][6][12]=true;  XUETANG[3][1][9]=true;
    XUETANG[4][3][3]=true;  XUETANG[5][5][9]=true;
  }
  //词馆，以年干或日干为主，见柱
  public static final boolean[][][] CIGUAN = new boolean[11][11][13];
  static
  {
    CIGUAN[1][7][3]=true;  CIGUAN[2][8][4]=true;  CIGUAN[3][2][6]=true;
    CIGUAN[4][5][7]=true;  CIGUAN[5][4][6]=true;  CIGUAN[6][7][7]=true;
    CIGUAN[7][9][9]=true;  CIGUAN[8][10][10]=true;  CIGUAN[9][10][12]=true;
    CIGUAN[10][9][11]=true;
  }
  //德秀贵人，以生月为主，看四柱天干中有否，其中1为德贵人、二为秀贵人
  public static final boolean[][][] DEXIU = new boolean[13][3][11];
  static
  {
    DEXIU[3][1][3]=true;  DEXIU[3][1][4]=true;  DEXIU[3][2][5]=true;  DEXIU[3][2][10]=true;
    DEXIU[7][1][3]=true;  DEXIU[7][1][4]=true;  DEXIU[7][2][5]=true;  DEXIU[7][2][10]=true;
    DEXIU[11][1][3]=true; DEXIU[11][1][4]=true;  DEXIU[11][2][5]=true;  DEXIU[11][2][10]=true;

    DEXIU[9][1][9]=true; DEXIU[9][1][10]=true; DEXIU[9][1][5]=true; DEXIU[9][1][6]=true;
    DEXIU[9][2][3]=true; DEXIU[9][2][8]=true; DEXIU[9][2][1]=true; DEXIU[9][2][6]=true;
    DEXIU[1][1][9]=true; DEXIU[1][1][10]=true; DEXIU[1][1][5]=true; DEXIU[1][1][6]=true;
    DEXIU[1][2][3]=true; DEXIU[1][2][8]=true; DEXIU[1][2][1]=true; DEXIU[1][2][6]=true;
    DEXIU[5][1][9]=true; DEXIU[5][1][10]=true; DEXIU[5][1][5]=true; DEXIU[5][1][6]=true;
    DEXIU[5][2][3]=true; DEXIU[5][2][8]=true; DEXIU[5][2][1]=true; DEXIU[5][2][6]=true;

    DEXIU[6][1][7]=true;  DEXIU[6][1][8]=true;  DEXIU[6][2][2]=true;  DEXIU[6][2][7]=true;
    DEXIU[10][1][7]=true;  DEXIU[10][1][8]=true;  DEXIU[10][2][2]=true;  DEXIU[10][2][7]=true;
    DEXIU[2][1][7]=true;  DEXIU[2][1][8]=true;  DEXIU[2][2][2]=true;  DEXIU[2][2][7]=true;

    DEXIU[12][1][1]=true;  DEXIU[12][1][2]=true;  DEXIU[12][2][4]=true;  DEXIU[12][2][9]=true;
    DEXIU[4][1][1]=true;  DEXIU[4][1][2]=true;  DEXIU[4][2][4]=true;  DEXIU[4][2][9]=true;
    DEXIU[8][1][1]=true;  DEXIU[8][1][2]=true;  DEXIU[8][2][4]=true;  DEXIU[8][2][9]=true;
  }
  //华盖，以年支或日支为主，看四柱中何地支临之
  public static final boolean[][] HUAGAI = new boolean[13][13];
  static
  {
    HUAGAI[9][5]=true; HUAGAI[1][5]=true; HUAGAI[5][5]=true;
    HUAGAI[3][11]=true; HUAGAI[7][11]=true; HUAGAI[11][11]=true;
    HUAGAI[6][2]=true;HUAGAI[10][2]=true; HUAGAI[2][2]=true;
    HUAGAI[12][8]=true; HUAGAI[4][8]=true; HUAGAI[8][8]=true;
  }
  //驿马，以年支或日支为主，看四柱中何地支临之
  public static final boolean[][] YIMA = new boolean[13][13];
  static
  {
    YIMA[9][3]=true; YIMA[1][3]=true; YIMA[5][3]=true;
    YIMA[3][9]=true; YIMA[7][9]=true; YIMA[11][9]=true;
    YIMA[6][12]=true;YIMA[10][12]=true; YIMA[2][12]=true;
    YIMA[12][6]=true; YIMA[4][6]=true; YIMA[8][6]=true;
  }
  //将星，以年支或日支为主，看四柱中何地支临之
  public static final boolean[][] JIANG = new boolean[13][13];
  static
  {
    JIANG[9][1]=true; JIANG[1][1]=true; JIANG[5][1]=true;
    JIANG[3][7]=true; JIANG[7][7]=true; JIANG[11][7]=true;
    JIANG[6][10]=true;JIANG[10][10]=true; JIANG[2][10]=true;
    JIANG[12][4]=true; JIANG[4][4]=true; JIANG[8][4]=true;
  }
  //金舆，以日干为主，四支见者为是
  public static final boolean[][] JINYU = new boolean[11][13];
  static
  {
    FUXING[1][5]=true;  FUXING[2][6]=true;  FUXING[3][8]=true;  FUXING[4][9]=true;
    FUXING[5][8]=true;  FUXING[6][9]=true;  FUXING[7][11]=true; FUXING[8][12]=true;
    FUXING[9][2]=true;  FUXING[10][3]=true;
  }
  //金神，日柱或时柱见者为是
  public static final boolean[][] JINSHEN = new boolean[11][13];
  static
  {
    FUXING[2][2]=true;  FUXING[6][6]=true;  FUXING[10][10]=true;
  }
  //天医，以月支查其它地支，见者为是
  public static final boolean[][] TIANYI1 = new boolean[13][13];
  static
  {
    TIANYI1[1][12]=true;  TIANYI1[2][1]=true;  TIANYI1[3][2]=true;
    TIANYI1[4][3]=true;  TIANYI1[5][4]=true;  TIANYI1[6][5]=true;
    TIANYI1[7][6]=true;  TIANYI1[8][7]=true;  TIANYI1[9][8]=true;
    TIANYI1[10][9]=true;  TIANYI1[11][10]=true;  TIANYI1[12][11]=true;
  }
  //禄神，以日干查四支，见之者为是
  public static final boolean[][] LUSHEN = new boolean[11][13];
  static
  {
    TIANYI1[1][3]=true;  TIANYI1[2][4]=true;  TIANYI1[3][6]=true;
    TIANYI1[4][7]=true;  TIANYI1[5][6]=true;  TIANYI1[6][7]=true;
    TIANYI1[7][9]=true;  TIANYI1[8][10]=true;  TIANYI1[9][12]=true;
    TIANYI1[10][1]=true;
  }
  //拱禄，五日五时
  public static final boolean[][][][] GONGLU = new boolean[11][13][11][13];
  static
  {
    GONGLU[10][12][10][2]=true;
    GONGLU[10][2][10][12]=true;
    GONGLU[4][6][4][8]=true;
    GONGLU[6][8][6][6]=true;
    GONGLU[5][5][5][7]=true;
  }
  //天赦，月支查日柱
  public static final boolean[][][] TIANSE = new boolean[13][11][13];
  static
  {
    TIANSE[3][5][3]=true; TIANSE[4][5][3]=true; TIANSE[5][5][3]=true;
    TIANSE[6][1][7]=true; TIANSE[7][1][7]=true; TIANSE[8][1][7]=true;
    TIANSE[9][5][9]=true;TIANSE[10][5][9]=true; TIANSE[11][5][9]=true;
    TIANSE[12][1][1]=true; TIANSE[1][1][1]=true; TIANSE[2][1][1]=true;
  }
  //天罗地网，以年支或日支为主，其它地支见之者为是，或纳音年命见之
  //0是支1-5是纳音火3水5土命；1是天罗2是地网；支；支；
  public static final boolean[][][][] TIANLUO = new boolean[6][3][13][13];
  static
  {
    TIANLUO[0][1][11][12]=true; TIANLUO[0][1][12][11]=true;
    TIANLUO[0][2][5][6]=true;   TIANLUO[0][2][6][5]=true;
    TIANLUO[4][1][11] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[4][1][12] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[3][2][5] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[3][2][6] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[5][2][5] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
    TIANLUO[5][2][6] = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true};
  }
  //羊刃，以日干查四支，见之者为是
  public static final boolean[][] YANGREN = new boolean[11][13];
  static
  {
    YANGREN[1][4]=true;  YANGREN[2][3]=true;  YANGREN[3][7]=true;
    YANGREN[4][6]=true;  YANGREN[5][7]=true;  YANGREN[6][6]=true;
    YANGREN[7][10]=true;  YANGREN[8][9]=true;  YANGREN[9][1]=true;
    YANGREN[10][12]=true;
  }
  //得到十干的羊刃
  public static final int[] YANGREN2 = {0,4,3,7,6,7,6,10,9,1,12};

  //亡神，以年支或日支为主，看四柱中何地支临之
  public static final boolean[][] WANGSHEN = new boolean[13][13];
  static
  {
    WANGSHEN[9][12]=true; WANGSHEN[1][12]=true; WANGSHEN[5][12]=true;
    WANGSHEN[3][6]=true; WANGSHEN[7][6]=true; WANGSHEN[11][6]=true;
    WANGSHEN[6][9]=true;WANGSHEN[10][9]=true; WANGSHEN[2][9]=true;
    WANGSHEN[12][3]=true; WANGSHEN[4][3]=true; WANGSHEN[8][3]=true;
  }
  //劫煞，以年支或日支为主，看四柱中何地支临之
  public static final boolean[][] JIESHA = new boolean[13][13];
  static
  {
    JIESHA[9][6]=true; JIESHA[1][6]=true; JIESHA[5][6]=true;
    JIESHA[3][12]=true; JIESHA[7][12]=true; JIESHA[11][12]=true;
    JIESHA[6][3]=true;JIESHA[10][3]=true; JIESHA[2][3]=true;
    JIESHA[12][9]=true; JIESHA[4][9]=true; JIESHA[8][9]=true;
  }
  //灾煞，以年支或日支为主，看四柱中何地支临之
  public static final boolean[][] ZAISHA = new boolean[13][13];
  static
  {
    ZAISHA[9][7]=true; ZAISHA[1][7]=true; ZAISHA[5][7]=true;
    ZAISHA[3][1]=true; ZAISHA[7][1]=true; ZAISHA[11][1]=true;
    ZAISHA[6][4]=true;ZAISHA[10][4]=true; ZAISHA[2][4]=true;
    ZAISHA[12][10]=true; ZAISHA[4][10]=true; ZAISHA[8][10]=true;
  }
  //勾绞煞，以年支为主，查四柱其余地支。其中1为阳男阴女，2反之
  public static final boolean[][][] GOUSHA = new boolean[3][13][13];
  public static final boolean[][][] JIAOSHA = new boolean[3][13][13];
  static
  {
    GOUSHA[1][1][4]=true;GOUSHA[2][1][10]=true;
    JIAOSHA[1][1][10]=true;JIAOSHA[2][1][4]=true;
    GOUSHA[1][2][5]=true;GOUSHA[2][2][11]=true;
    JIAOSHA[1][2][11]=true;JIAOSHA[2][2][5]=true;
    GOUSHA[1][3][6]=true;GOUSHA[2][3][12]=true;
    JIAOSHA[1][3][12]=true;JIAOSHA[2][3][6]=true;
    GOUSHA[1][4][7]=true;GOUSHA[2][4][1]=true;
    JIAOSHA[1][4][1]=true;JIAOSHA[2][4][7]=true;
    GOUSHA[1][5][8]=true;GOUSHA[2][5][2]=true;
    JIAOSHA[1][5][2]=true;JIAOSHA[2][5][8]=true;
    GOUSHA[1][6][9]=true;GOUSHA[2][6][3]=true;
    JIAOSHA[1][6][3]=true;JIAOSHA[2][6][9]=true;
    GOUSHA[1][7][10]=true;GOUSHA[2][7][4]=true;
    JIAOSHA[1][7][4]=true;JIAOSHA[2][7][10]=true;
    GOUSHA[1][8][11]=true;GOUSHA[2][8][5]=true;
    JIAOSHA[1][8][5]=true;JIAOSHA[2][8][11]=true;
    GOUSHA[1][9][12]=true;GOUSHA[2][9][6]=true;
    JIAOSHA[1][9][6]=true;JIAOSHA[2][9][12]=true;
    GOUSHA[1][10][1]=true;GOUSHA[2][10][7]=true;
    JIAOSHA[1][10][7]=true;JIAOSHA[2][10][1]=true;
    GOUSHA[1][11][2]=true;GOUSHA[2][11][8]=true;
    JIAOSHA[1][11][8]=true;JIAOSHA[2][11][2]=true;
    GOUSHA[1][12][3]=true;GOUSHA[2][12][9]=true;
    JIAOSHA[1][12][9]=true;JIAOSHA[2][12][3]=true;
  }
  //孤辰寡宿,以年支为准，四柱其它地支见者为是,1是孤辰2是寡宿
  public static final boolean[][][] GCGX = new boolean[3][13][13];
  static
  {
    GCGX[1][12][3]=true;  GCGX[2][12][11]=true;
    GCGX[1][1][3]=true;  GCGX[2][1][11]=true;
    GCGX[1][2][3]=true;  GCGX[2][2][11]=true;

    GCGX[1][3][6]=true;  GCGX[2][3][2]=true;
    GCGX[1][4][6]=true;  GCGX[2][4][2]=true;
    GCGX[1][5][6]=true;  GCGX[2][5][2]=true;

    GCGX[1][6][9]=true;  GCGX[2][6][5]=true;
    GCGX[1][7][9]=true;  GCGX[2][7][5]=true;
    GCGX[1][8][9]=true;  GCGX[2][8][5]=true;

    GCGX[1][9][12]=true;  GCGX[2][9][8]=true;
    GCGX[1][10][12]=true;  GCGX[2][10][8]=true;
    GCGX[1][11][12]=true;  GCGX[2][11][8]=true;
  }
  //元辰，以年支为准，查四柱其余地支。其中1为阳男阴女，2反之
  public static final boolean[][][] YUANCHEN = new boolean[13][13][13];
  static
  {
    YUANCHEN[1][1][8]=true;  YUANCHEN[2][1][6]=true;
    YUANCHEN[1][2][9]=true;  YUANCHEN[2][2][7]=true;
    YUANCHEN[1][3][10]=true;  YUANCHEN[2][3][8]=true;
    YUANCHEN[1][4][11]=true;  YUANCHEN[2][4][9]=true;
    YUANCHEN[1][5][12]=true;  YUANCHEN[2][5][10]=true;
    YUANCHEN[1][6][1]=true;  YUANCHEN[2][6][11]=true;
    YUANCHEN[1][7][2]=true;  YUANCHEN[2][7][12]=true;
    YUANCHEN[1][8][3]=true;  YUANCHEN[2][8][1]=true;
    YUANCHEN[1][9][4]=true;  YUANCHEN[2][9][2]=true;
    YUANCHEN[1][10][5]=true;  YUANCHEN[2][10][3]=true;
    YUANCHEN[1][11][6]=true;  YUANCHEN[2][11][4]=true;
    YUANCHEN[1][12][7]=true;  YUANCHEN[2][12][5]=true;
  }
  //空亡，以日柱为主，柱中年、月、时支见者为空亡
  //详YiJing.KONGWANG();
  //十恶大败，四柱日干支逢之即是
  public static final boolean[][] DABAI = new boolean[11][13];
  static
  {
    DABAI[1][5]=true;  DABAI[2][6]=true;  DABAI[9][9]=true;
    DABAI[3][9]=true;  DABAI[4][12]=true; DABAI[7][5]=true;
    DABAI[5][11]=true; DABAI[10][12]=true;DABAI[8][6]=true;
    DABAI[6][2]=true;
  }
  //咸池，以年支或日支查四柱其它地支
  public static final boolean[][] XIANCI = new boolean[13][13];
  static
  {
    XIANCI[9][10]=true; XIANCI[1][10]=true; XIANCI[5][10]=true;
    XIANCI[3][4]=true; XIANCI[7][4]=true; XIANCI[11][4]=true;
    XIANCI[6][7]=true;XIANCI[10][7]=true; XIANCI[2][7]=true;
    XIANCI[12][1]=true; XIANCI[4][1]=true; XIANCI[8][1]=true;
  }
  //孤鸾煞，四柱日时同时出现以上任何两组者为是
  public static final boolean[][] GULUAN = new boolean[11][13];
  static
  {
    GULUAN[2][6]=true;  GULUAN[4][6]=true;  GULUAN[8][12]=true;
    GULUAN[5][9]=true;  GULUAN[9][3]=true;  GULUAN[5][7]=true;
    GULUAN[9][1]=true;  GULUAN[3][7]=true;
  }
  //阴阳差错，日柱见者为是
  public static final boolean[][] YYCACUO = new boolean[11][13];
  static
  {
    YYCACUO[3][1]=true;  YYCACUO[4][2]=true;  YYCACUO[5][3]=true;
    YYCACUO[8][4]=true;  YYCACUO[9][5]=true;  YYCACUO[10][6]=true;
    YYCACUO[3][7]=true;  YYCACUO[4][8]=true;  YYCACUO[5][9]=true;
    YYCACUO[8][10]=true; YYCACUO[9][11]=true; YYCACUO[10][12]=true;
  }
  //四废，凡四柱日干支生于该月为是
  public static final boolean[][][] SIFEI = new boolean[13][11][13];
  static
  {
    SIFEI[12][3][7]=true;  SIFEI[1][3][7]=true;  SIFEI[2][3][7]=true;
    SIFEI[12][4][6]=true;  SIFEI[1][4][6]=true;  SIFEI[2][4][6]=true;
    SIFEI[3][7][9]=true;    SIFEI[4][7][9]=true;   SIFEI[5][7][9]=true;
    SIFEI[3][8][10]=true;    SIFEI[4][8][10]=true;   SIFEI[5][8][10]=true;
    SIFEI[6][9][1]=true;    SIFEI[7][9][1]=true;   SIFEI[8][9][1]=true;
    SIFEI[6][10][12]=true;    SIFEI[7][10][12]=true;   SIFEI[8][10][12]=true;
    SIFEI[9][1][3]=true;   SIFEI[10][1][3]=true; SIFEI[11][1][3]=true;
    SIFEI[9][2][4]=true;   SIFEI[10][2][4]=true; SIFEI[11][2][4]=true;
  }

  /**
   * 五行打分
   */
  public static int muCent = 0;
  public static int huoCent = 0;
  public static int tuCent = 0;
  public static int jinCent = 0;
  public static int shuiCent = 0;
  //以月令为100，同我为100，我生者相80，生我者休60，克我者囚40，我克者死20，以此为旺相基数初始分
  public static final int wangCent = 100;
  public static final int xiangCent = 80;
  public static final int xiuCent = 60;
  public static final int keCent = 40;
  public static final int siCent = 20;
  // 三会(其五行系数-有月令：3.0月支  无月令：2.5最大分支,减去本气基本本)
  public static final double[] sanhuiXS = {0.0, 1.0, 0.8};
  //六冲-隔二支冲为动，不损力。紧临支的子午卯酉(月令冲：不隔一支有月令-0.5月支,-0.2月支非-0.6另支,-0.3另支，不隔一支二支无月令冲:-0.5本支,-0.2本支,0.1本支；)
  //       -寅申巳亥冲：不隔一支有月令-0.5月支,-0.2月支，非-0.6另支,-0.3另支；不隔一支二支无月支-0.5本支,-0.2本支,0.1本支
  //      -辰戌丑款冲：不隔一支有月令0.5月支,0.3月支,非0.3另支,0.2另支；不隔一支二支无月支0.3本支,0.2本支,0.1本支
  public static final double[] lcXS1 = {0.0,-0.5,-0.2,-0.6,-0.3,-0.5,-0.2,0.1};
  public static final double[] lcXS2 = {0.0,0.5,0.3,0.3,0.2,0.3,0.2,0.1};
  //三合(其五行系数-有月令：2.5月支 无月令：2.0最大分支,要减去本支分)
  public static final double[] sanheXS = {0.0, 1.0, 0.8};
  //三合旺半合 其五行系数：不隔一支有月支1.3月支，1.0月支，不隔一支二支无月支1.0最大分支，0.6最大分支，0.2最大分支
  public static final double[] wangBHXS = {0.0, 0.5, 0.3, 0.3, 0.2, 0.1};
  //六合 其五行系数：合而不化(不隔一支有月支1.2月支，1.0月支，不隔一支二支无月支1.0最大分支，0.6最大分支，0.2最大分支)
  //                 合化(有月支2.0月支 无月支1.5最大分支)
  public static final double[] lhXS = {0.0,0.3,0.2,0.3,0.2,0.1,2.0,1.5};
  //三合非旺半合 其五行系数：不隔一支有月支0.5月支，0.3月支，不隔一支二支无月支0.3最大分支，0.2最大分支，0.1最大分支
  public static final double[] feiBHXS = {0.0,0.3,0.1,0.2,0.1,0.05};
  //相生 其五行系数,生者不损气,只是造势，其实没有生：不隔一支二支0.3生支，0.2生支，0.1生支
  public static final double[] shengXS = {0.0,0.15,0.1,0.05};
  //相克 其五行系数,克者不损气，只是一种威胁(戊未戌克水力大-0.3,其它土0.05)：不隔一支二支-0.3克支，-0.2克支，-0.1克支
  public static final double[] keXS = {0.0,-0.15,-0.1,-0.05,0.1};
  //暗合 如卯申暗合须紧临，其五行系数：0.5克支,意即抵消克气
  public static final double anheXS = 0.1;
  //天覆地载(即干支相生): 0.8 天地相克：-0.8 天合地（只有戊子、辛巳、丁亥、壬午四日）：0.5*此支分数
  public static final double[] ganziXS = {0.0,0.1,-0.1,0.1};
  //五行得一比肩 在其它3支得一墓库 0.35此支基本分  得一余气  0.7 禄刃 1.0此支基本分 长生不如本气0.7
  public static final double[] sanziXS = {0.3,0.35,0.7,1.0,0.7};
  //相害 -0.1各支
  public static final double haiXS = -0.05;
  //相刑 -0.1各支
  public static final double xingXS = -0.05;
  public static final double tdhXS = 0.05;


  //各五行金木水火土在月支五行的分数,金在金100分，金在土80分
  public static final int[][] jibenfen = new int[6][6];
  static {
    jibenfen[YiJing.JIN][YiJing.JIN]=wangCent;jibenfen[YiJing.SHUI][YiJing.JIN]=xiangCent;
    jibenfen[YiJing.TU][YiJing.JIN]=xiuCent;jibenfen[YiJing.HUO][YiJing.JIN]=keCent;
    jibenfen[YiJing.MU][YiJing.JIN]=siCent;
    jibenfen[YiJing.MU][YiJing.MU]=wangCent;jibenfen[YiJing.HUO][YiJing.MU]=xiangCent;
    jibenfen[YiJing.SHUI][YiJing.MU]=xiuCent;jibenfen[YiJing.JIN][YiJing.MU]=keCent;
    jibenfen[YiJing.TU][YiJing.MU]=siCent;
    jibenfen[YiJing.SHUI][YiJing.SHUI]=wangCent;jibenfen[YiJing.MU][YiJing.SHUI]=xiangCent;
    jibenfen[YiJing.JIN][YiJing.SHUI]=xiuCent;jibenfen[YiJing.TU][YiJing.SHUI]=keCent;
    jibenfen[YiJing.HUO][YiJing.SHUI]=siCent;
    jibenfen[YiJing.HUO][YiJing.HUO]=wangCent;jibenfen[YiJing.TU][YiJing.HUO]=xiangCent;
    jibenfen[YiJing.MU][YiJing.HUO]=xiuCent;jibenfen[YiJing.SHUI][YiJing.HUO]=keCent;
    jibenfen[YiJing.JIN][YiJing.HUO]=siCent;
    jibenfen[YiJing.TU][YiJing.TU]=wangCent;jibenfen[YiJing.JIN][YiJing.TU]=xiangCent;
    jibenfen[YiJing.HUO][YiJing.TU]=xiuCent;jibenfen[YiJing.MU][YiJing.TU]=keCent;
    jibenfen[YiJing.SHUI][YiJing.TU]=siCent;

  }

  //public final static int wangjiCent = 96;
  public final static int[] judgeWS = {80,130,190,260,700}; //y
  public final static int[] baifen =  {40,60, 80, 90, 100}; //x     当x<80 x=x*x0/y0     否则 x = x0 + (x-y0)*(x1-x0)/(y1-y0)
  public static int muCent1 = 0;
  public static int huoCent1 = 0;
  public static int tuCent1 = 0;
  public static int jinCent1 = 0;
  public static int shuiCent1 = 0;

  public final static String[] judgeWSName = {"弱之极矣","偏弱","旺相","强旺","旺之极矣"};

}
