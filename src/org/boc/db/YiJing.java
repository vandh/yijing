package org.boc.db;

import org.boc.db.ly.Liuyao;

public class YiJing {
  public final static int[] DZCHONG2 = new int[]{0,7,8,9,10,11,12,1,2,3,4,5,6};
  /**
   * 主互变卦左边空格数、二二间隔数、与六神间隔数
   * 先天八卦数，八卦象征，八宫象征
   * 五行
   * 十天干
   * 十二地支
   * 二仪
   * 六亲
   * 六神
   * 世应
   * 旺相死囚休
   */
  public final static int[] INTER = new int[]{4,14,6,2,10};
  public final static int QIAN = 1;
  public final static int DUI = 2;
  public final static int LI = 3;
  public final static int ZHEN = 4;
  public final static int SHUN = 5;
  public final static int KAN = 6;
  public final static int GEN = 7;
  public final static int KUN = 8;

  public final static int JIN = 1;
  public final static int MU = 2;
  public final static int SHUI = 3;
  public final static int HUO = 4;
  public final static int TU = 5;

  public final static int JIA = 1;
  public final static int YI = 2;
  public final static int BING = 3;
  public final static int DING = 4;
  public final static int WUG = 5;
  public final static int JI = 6;
  public final static int GENG = 7;
  public final static int XIN = 8;
  public final static int REN = 9;
  public final static int GUI = 10;

  public final static int ZI = 1;
  public final static int CHOU = 2;
  public final static int YIN = 3;
  public final static int MAO = 4;
  public final static int CHEN = 5;
  public final static int SI = 6;
  public final static int WUZ = 7;
  public final static int WEI = 8;
  public final static int SHEN = 9;
  public final static int YOU = 10;
  public final static int XU = 11;
  public final static int HAI = 12;

  public final static int YANGYAO = 1;
  public final static int YINYAO = 2;

  public final static int SHI = 1;
  public final static int YING = 2;

  public final static int SHIYAO = 0;
  public final static int XIONGDI = 1;
  public final static int ZISUI = 2;
  public final static int QICAI = 3;
  public final static int GUANGUI = 4;
  public final static int FUMU = 5;
  public final static int GUASHEN = 6;

  public final static int QINGLONG = 1;
  public final static int ZHUQUE = 2;
  public final static int GOUCHEN = 3;
  public final static int TENGSHE = 4;
  public final static int BAIHU = 5;
  public final static int XUANWU = 6;

  public final static int WANGVALUE = 1;
  public final static int XANGVALUE = 2;
  public final static int SHIVALUE = 3;
  public final static int QIUGVALUE = 4;
  public final static int XIUGVALUE = 5;

  public final static int SHENGRJVALUE = 1;
  public final static int WANGRJVALUE = 2;
  public final static int MURJVALUE = 3;
  public final static int JUERJVALUE = 4;

  public static final String NUM3 = "884856";
  /**
   *
   */


  /**
   * 世应名称
   * 阴阳符号1
   * 阴阳符号2
   * 动爻符号
   * 五行名称
   * 八经卦名称
   * 十天干名称
   * 十二地支名称
   * 六亲名称
   * 六神名称
   * 六十四卦名称
   * 六十四卦卦辞,六十四卦爻辞
   */
  public final static int[][][] sanyao = new int [3][3][3];
  static {
    sanyao[1][1][1] = 1;
    sanyao[1][1][2] = 2;
    sanyao[1][2][1] = 3;
    sanyao[1][2][2] = 4;
    sanyao[2][1][1] = 5;
    sanyao[2][1][2] = 6;
    sanyao[2][2][1] = 7;
    sanyao[2][2][2] = 8;
  }

  public final static String[] SHIYINGNAME = new String[] {null, "世", "应"};
  public final static String[] YAONAME = new String[] {"","━━━━━━","━━　　━━"};//{"", "───", "─  ─"};
  public final static String[] YAONAME2 = new String[] {"", "\\ ", "\\\\"};
  public final static String[] YAODONG = new String[] {"", "○", "×"};
  public final static String[] YAONAME3 = new String[] {"", "阳爻", "阴爻"};

  public final static String[] WUXINGNAME = new String[] {"","金","木","水","火","土"};
  public final static int[] jingguawx = {0, 1, 1, 4, 2, 2, 3, 5, 5};
  public final static String[] JINGGUANAME = new String[] {"","乾","兑","离","震","巽","坎","艮","坤"};
  public final static String[] TIANGANNAME = new String[] {"","甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
  public final static String[] DIZINAME = new String[] {"","子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
  public final static String[] LIUQINNAME = new String[] {"日干","兄弟","子孙","妻财","官鬼","父母","卦身"};
  public final static String[] LIUSHENNAME = new String[] {null,"青龙","朱雀","勾陈","腾蛇","白虎","玄武"};
  public final static String[][] GUA64NAME = new String[9][9];
  static
  {
    GUA64NAME[0] = null;
    GUA64NAME[1] = new String[] {null, "乾卦", "天泽履", "天火同人", "天雷无妄", "天风姤", "天水讼", "天山遁", "天地否"};
    GUA64NAME[2] = new String[] {null, "泽天夬", "泽卦", "泽火革", "泽雷随", "泽风大过", "泽水困", "泽山咸", "泽地萃"};
    GUA64NAME[3] = new String[] {null, "火天大有", "火泽睽", "离卦", "火雷噬嗑","火风鼎","火水未济","火山旅","火地晋"};
    GUA64NAME[4] = new String[] {null, "雷天大壮","雷泽归妹","雷火丰","震卦","雷风恒","雷水解","雷山小过","雷地豫"};
    GUA64NAME[5] = new String[] {null, "风天小畜","风泽中孚","风火家人","风雷益","巽卦","风水涣","风山渐","风地观"};
    GUA64NAME[6] = new String[] {null, "水天需","水泽节","水火既济","水雷屯","水风井","坎卦","水山蹇","水地比"};
    GUA64NAME[7] = new String[] {null, "山天大畜","山泽损","山火贲","山雷颐","山风蛊","山水讼","艮卦","山地剥"};
    GUA64NAME[8] = new String[] {null, "地天泰","地泽临","地火明夷","地雷复","地风升","地水师","地山谦","坤卦"};
  }
  public final static String[][][] GUA64CI = new String[9][9][8];
  static
  {
    GUA64CI[1][1] = new String[] {"乾：元，亨，利，贞。\n    彖曰：大哉乾元，万物资始，乃统天。云行雨施，品物流形。大明始终，六位时成，时乘六龙以御天。乾道变化，各正性命，保合大和，乃利贞。首出庶物，万国咸宁。\n    象曰：天行健，君子以自强不息。潜龙勿用，阳在下也。 见龙再田，德施普也。 终日乾乾，反复道也。 或跃在渊，进无咎也。飞龙在天，大人造也。 亢龙有悔，盈不可久也。 用九，天德不可为首也。\n    文言曰：元者，善之长也，亨者，嘉之会也，利者，义之和也，贞者，事之干也。 君子体仁，足以长人；嘉会，足以合礼；利物，足以和义；贞固，足以干事。 君子行此四者，故曰：乾：元亨利贞。",
        "初九：潜龙，勿用。",
        "九二：见龙再田，利见大人。",
        "九三：君子终日乾乾，夕惕若，厉无咎。",
        "九四：或跃在渊，无咎。",
        "九五：飞龙在天，利见大人。",
        "上九：亢龙有悔。",
        "见群龙无首，吉。"};

    GUA64CI[1][2] = new String[] {
        "履：履虎尾，不咥人，亨。\n    彖曰：履，柔履刚也。说而应乎乾，是以履虎尾，不咥人，亨。刚中正，履帝位而不疚，光明也。\n    象曰：上天下泽，履；君子以辨上下，安民志。",
        "初九：素履，往无咎。",
        "九二：履道坦坦，幽人贞吉。",
        "六三：眇能视，跛能履，履虎尾，咥人，凶。 武人为于大君。",
        "九四：履虎尾，愬愬终吉。",
        "九五：夬履，贞厉。",
        "上九：视履考祥，其旋元吉。",""
    };
    GUA64CI[1][8] = new String[] {
        "否：否之匪人，不利君子贞，大往小来。\n    彖曰：否之匪人，不利君子贞。 大往小来， 则是天地不交，而万物不通也；上下不交，而天下无邦也。内阴而外阳，内柔而外刚，内小人而外君子。 小人道长，君子道消也。\n    象曰：天地不交，否；君子以俭德辟难，不可荣以禄。",
        "初六：拔茅茹，以其夤，贞吉亨。",
        "六二：包承。 小人吉，大人否亨。",
        "六三：包羞。",
        "九四：有命无咎，畴离祉。",
        "九五：休否，大人吉。 其亡其亡，系于苞桑。",
        "上九：倾否，先否后喜。"
    };
    GUA64CI[1][6] = new String[] {
        "讼：有孚，窒。 惕中吉。 终凶。 利见大人，不利涉大川。\n    彖曰：讼，上刚下险，险而健讼。讼有孚窒，惕中吉，刚来而得中也。终凶；讼不可成也。 利见大人；尚中正也。不利涉大川；入于渊也。\n    象曰：天与水违行，讼；君子以作事谋始。",
        "初六：不永所事，小有言，终吉。",
        "九二：不克讼，归而逋，其邑人三百户，无眚。",
        "六三：食旧德，贞厉，终吉，或从王事，无成。",
        "九四：不克讼，复自命，渝安贞，吉。",
        "九五：讼元吉。",
        "上九：或锡之鞶带，终朝三褫之。"
    };
    GUA64CI[1][3] = new String[] {
        "同人：同人于野，亨。 利涉大川，利君子贞。\n    彖曰：同人，柔得位得中，而应乎乾，曰同人。 同人曰，同人于野，亨。",
        "初九：同人于门，无咎。",
        "六二：同人于宗，吝。",
        "九三：戎于莽，升其高陵，三岁不兴。",
        "九四：乘其墉，弗克攻，吉。",
        "九五：同人，先号啕而后笑。 大师克相遇。",
        "上九：同人于郊，无悔。"
    };
    GUA64CI[1][5] = new String[] {
        "姤：女壮，勿用取女。\n    彖曰：姤，遇也，柔遇刚也。勿用取女，不可与长也。 天地相遇，品物咸章也。 刚遇中正，天下大行也。 姤之时义大矣哉！\n    象曰：天下有风，姤；后以施命诰四方。",
        "初六：系于金柅，贞吉，有攸往，见凶，羸豕踟躅。",
        "九二：包有鱼，无咎，不利宾。",
        "九三：臀无肤，其行次且，厉，无大咎。",
        "九四：包无鱼，起凶。",
        "九五：以杞包瓜，含章，有陨自天。",
        "上九：姤其角，吝，无咎。"
    };
    GUA64CI[1][7] = new String[] {
        "遯：亨，小利贞。\n    彖曰：遯亨，遯而亨也。 刚当位而应，与时行也。 小利贞，浸而长也。",
        "初六：遯尾，厉，勿用有攸往。",
        "六二：执之用黄牛之革，莫之胜说。",
        "九三：系遯，有疾厉，畜臣妾吉。",
        "九四：好遯君子吉，小人否。",
        "九五：嘉遯，贞吉。",
        "上九：肥遯，无不利。"
    };
    GUA64CI[1][4] = new String[] {
        "无妄：元，亨，利，贞。 其匪正有眚，不利有攸往。\n    彖曰：无妄，刚自外来，而为主於内。动而健，刚中而应，大亨以正，天之命也。 其匪正有眚，不利有攸往。无妄之往，何之矣？ 天命不佑，行矣哉？ \n    象曰：天下雷行，物与无妄；先王以茂对时，育万物。",
        "初九：无妄，往吉。",
        "六二：不耕获，不菑畲，则利有攸往。",
        "六三：无妄之灾，或系之牛，行人之得，邑人之灾。",
        "九四：可贞，无咎。",
        "九五：无妄之疾，勿药有喜。",
        "上九：无妄，行有眚，无攸利。"
    };

    GUA64CI[2][2] = new String[] {
        "兑：亨，利贞。\n    彖曰：兑，说也。 刚中而柔外，说以利贞，是以顺乎天，而应乎人。说以先民，民忘其劳；说以犯难，民忘其死；说之大，民劝矣哉！\n    象曰：丽泽，兑；君子以朋友讲习.",
        "初九：和兑，吉。",
        "九二：孚兑，吉，悔亡。",
        "六三：来兑，凶。",
        "九四：商兑，未宁，介疾有喜。",
        "九五：孚于剥，有厉。",
        "上六：引兑。"
    };
    GUA64CI[2][6] = new String[] {
        "困：亨。贞，大人吉，无咎。有言不信。\n    《彖》曰：困，刚掩也。险以说，困而不失其所亨，其唯君子乎！\n    《象》曰：泽无水，困。君子以致命遂志。",
        "初六：臀困于株木，入于幽谷，三岁不觌。",
        "九二：困于酒食，朱绂方来，利用享祀。征凶，无咎。",
        "六三：困于石，据于蒺藜，入于其宫，不见其妻，凶。",
        "九四：来徐徐，困于金车。吝，有终。",
        "九五：劓刖，困于赤绂，乃徐有说。利用祭祀。",
        "上六：困于葛藟，于臲卼，曰动悔。有悔，征吉。"
    };
    GUA64CI[2][8] = new String[] {
        "萃：亨。 王假有庙，利见大人，亨，利贞。 用大牲吉，利有攸往。\n    彖曰：萃，聚也；顺以说，刚中而应，故聚也。王假有庙，致孝享也。利见大人亨，聚以正也。 用大牲吉，利有攸往，顺天命也。 观其所聚，而天地万物之情可见矣。\n    象曰：泽上於地，萃；君子以除戎器，戒不虞。",
        "初六：有孚不终，乃乱乃萃，若号一握为笑，勿恤，往无咎。",
        "六二：引吉，无咎，孚乃利用禴。",
        "六三：萃如，嗟如，无攸利，往无咎，小吝。",
        "九四：大吉，无咎。",
        "九五：萃有位，无咎。 匪孚，元永贞，悔亡。",
        "上六： 齑咨涕洟，无咎。"
    };
    GUA64CI[2][7] = new String[] {
        "咸：亨，利贞，取女吉。\n    彖曰：咸，感也。柔上而刚下，二气感应以相与，止而说，男下女，是以亨利贞，取女吉也。天地感而万物化生，圣人感人心而天下和平；观其所感，而天地万物之情可见矣！\n    象曰：山上有泽，咸；君子以虚受人。",
        "初六：咸其拇。",
        "六二：咸其腓，凶，居吉。",
        "九三：咸其股，执其随，往吝。",
        "九四：贞吉悔亡，憧憧往来，朋从尔思。",
        "九五：咸其脢，无悔。",
        "上六：咸其辅，颊，舌。"
    };
    GUA64CI[2][3] = new String[] {
        "革：己日乃孚，元亨利贞，悔亡。\n    彖曰：革，水火相息，二女同居，其志不相得，曰革。己日乃孚；革而信也。 文明以说，大亨以正，革而当，其悔乃亡。天地革而四时成，汤武革命，顺乎天而应乎人，革之时义大矣哉！\n    象曰：泽中有火，革；君子以治历明时。",
        "初九：巩用黄牛之革。",
        "六二：己日乃革之，征吉，无咎。",
        "九三：征凶，贞厉，革言三就，有孚。",
        "九四：悔亡，有孚改命，吉。",
        "九五：大人虎变，未占有孚。",
        "上六：君子豹变，小人革面，征凶，居贞吉。"
    };
    GUA64CI[2][1] = new String[] {
        "夬：扬于王庭，孚号，有厉，告自邑，不利即戎，利有攸往。\n    彖曰：夬，决也，刚决柔也。健而说，决而和，扬于王庭，柔乘五刚也。孚号有厉，其危乃光也。 告自邑，不利即戎，所尚乃穷也。 利有攸往，刚长乃终也。\n    象曰：泽上于天，夬；君子以施禄及下，居德则忌。",
        "初九：壮于前趾，往不胜为吝。",
        "九二：惕号，莫夜有戎，勿恤。",
        "九三：壮于頄，有凶。 君子夬夬，独行遇雨，若濡有愠，无咎。",
        "九四：臀无肤，其行次且。 牵羊悔亡，闻言不信。",
        "九五：苋陆夬夬，中行无咎。",
        "上六：无号，终有凶。"
    };
    GUA64CI[2][5] = new String[] {
        "大过：栋桡，利有攸往，亨。\n    彖曰：大过，大者过也。 栋桡，本末弱也。 刚过而中，巽而说行，利有攸往，乃亨。 大过之时义大矣哉！\n    象曰：泽灭木，大过；君子以独立不惧，遯世无闷。",
        "初六：藉用白茅，无咎。",
        "九二：枯杨生祶，老夫得其女妻，无不利。",
        "九三：栋桡，凶。",
        "九四：栋隆，吉；有它吝。",
        "九五：枯杨生华，老妇得士夫，无咎无誉。",
        "上六：过涉灭顶，凶，无咎。"
    };
    GUA64CI[2][4] = new String[] {
        "随：元亨利贞，无咎。\n    彖曰：随，刚来而下柔，动而说，随。大亨贞，无咎，而天下随时，随之时义大矣哉！\n    象曰：泽中有雷，随；君子以晦入宴息。",
        "初九：官有渝，贞吉。 出门交有功。",
        "六二：系小子，失丈夫。",
        "六三：系丈夫，失小子。 随有求得，利居贞。",
        "九四：随有获，贞凶。有孚在道，以明，何咎。",
        "九五：孚于嘉，吉。",
        "上六：拘系之，乃从维之。 王用亨于西山。"
    };

    GUA64CI[3][3] = new String[] {
        "离：利贞，亨。 畜牝牛，吉。\n    彖曰：离，丽也；日月丽乎天，百谷草木丽乎土，重明以丽乎正，乃化成天下。 柔丽乎中正，故亨；是以畜牝牛吉也。\n    象曰：明两作离，大人以继明照于四方。",
        "初九：履错然，敬之无咎。",
        "六二：黄离，元吉。",
        "九三：日昃之离，不鼓缶而歌，则大耋之嗟，凶。",
        "九四：突如其来如，焚如，死如，弃如。",
        "六五：出涕沱若，戚嗟若，吉。",
        "上九：王用出征，有嘉折首，获其匪丑，无咎。"
    };
    GUA64CI[3][2] = new String[] {
        "睽：小事吉。\n    彖曰：睽，火动而上，泽动而下； 二女同居，其志不同行；说而丽乎明，柔进而上行，得中而应乎刚；是以小事吉。 天地睽，而其事同也；男女睽，而其志通也；万物睽，而其事类也；睽之时用大矣哉！\n    象曰：上火下泽，睽；君子以同而异。",
        "初九：悔亡，丧马勿逐，自复；见恶人无咎。",
        "九二：遇主于巷，无咎。",
        "六三：见舆曳，其牛掣，其人天且劓，无初有终。",
        "九四：睽孤，遇元夫，交孚，厉无咎。",
        "六五：悔亡，厥宗噬肤，往何咎。",
        "上九：睽孤， 见豕负涂，载鬼一车， 先张之弧，后说之弧，匪寇婚媾，往遇雨则吉。"
    };
    GUA64CI[3][7] = new String[] {
        "旅：小亨，旅贞吉。\n    彖曰：旅，小亨，柔得中乎外，而顺乎刚，止而丽乎明，是以小亨，旅贞吉也。 旅之时义大矣哉！\n    象曰：山上有火，旅；君子以明慎用刑，而不留狱。",
        "初六：旅琐琐，斯其所取灾。",
        "六二：旅即次，怀其资，得童仆贞。",
        "九三：旅焚其次，丧其童仆，贞厉。",
        "九四：旅于处，得其资斧，我心不快。",
        "六五：射雉一矢亡，终以誉命。",
        "上九：鸟焚其巢，旅人先笑后号啕。 丧牛于易，凶。"
    };
    GUA64CI[3][5] = new String[] {
        "鼎：元吉，亨。\n    彖曰：鼎，象也。 以木巽火，亨饪也。 圣人亨以享上帝，而大亨以养圣贤。巽而耳目聪明，柔进而上行，得中而应乎刚，是以元亨。\n    象曰：木上有火，鼎；君子以正位凝命。 ",
        "初六：鼎颠趾，利出否，得妾以其子，无咎。",
        "九二：鼎有实，我仇有疾，不我能即，吉。",
        "九三：鼎耳革，其行塞，雉膏不食，方雨亏悔，终吉。",
        "九四：鼎折足，覆公餗，其形渥，凶。",
        "六五：鼎黄耳金铉，利贞。",
        "上九：鼎玉铉，大吉，无不利。"
    };
    GUA64CI[3][6] = new String[] {
        "未济：亨，小狐汔济，濡其尾，无攸利。\n    彖曰：未济，亨；柔得中也。 小狐汔济，未出中也。濡其尾，无攸利；不续终也。 虽不当位，刚柔应也。\n    象曰：火在水上，未济；君子以慎辨物居方。",
        "初六：濡其尾，吝。",
        "九二：曳其轮，贞吉。",
        "六三：未济，征凶，利涉大川。",
        "九四：贞吉，悔亡，震用伐鬼方，三年有赏于大国。",
        "六五：贞吉，无悔，君子之光，有孚，吉。",
        "上九：有孚于饮酒，无咎，濡其首，有孚失是。"
    };
    GUA64CI[3][8] = new String[] {
        "晋：康侯用锡马蕃庶，昼日三接。\n    彖曰：晋，进也。 明出地上，顺而丽乎大明，柔进而上行。是以康侯用锡马蕃庶，昼日三接也。\n    象曰：明出地上，晋；君子以自昭明德。",
        "初六：晋如，摧如，贞吉。 罔孚，裕无咎。",
        "六二：晋如，愁如，贞吉。 受兹介福，于其王母。",
        "六三：众允，悔亡。",
        "九四：晋如硕鼠，贞厉。",
        "六五：悔亡，失得勿恤，往吉无不利。",
        "上九：晋其角，维用伐邑，厉吉无咎，贞吝。"
    };
    GUA64CI[3][1] = new String[] {
        "大有：元亨。 \n    彖曰：大有，柔得尊位，大中而上下应之，曰大有。其德刚健而文明，应乎天而时行，是以元亨。\n    象曰：火在天上，大有；君子以竭恶扬善，顺天休命。",
        "初九：无交害，匪咎，艰则无咎。",
        "九二：大车以载，有攸往，无咎。",
        "九三：公用亨于天子，小人弗克。",
        "九四：匪其彭，无咎。",
        "六五：厥孚交如，威如；吉。",
        "上九：自天佑之，吉无不利。"
    };
    GUA64CI[3][4] = new String[] {
        "噬嗑：亨。 利用狱。\n    彖曰：颐中有物，曰噬嗑，噬嗑而亨。刚柔分，动而明，雷电合而章。柔得中而上行，虽不当位，利用狱也。\n    象曰：雷电噬嗑；先王以明罚敕法。",
        "初九：履校灭趾，无咎。",
        "六二：噬肤灭鼻，无咎。",
        "六三：噬腊肉，遇毒；小吝，无咎。",
        "九四：噬乾胏，得金矢，利艰贞，吉。",
        "六五：噬乾肉，得黄金，贞厉，无咎。",
        "上九：何校灭耳，凶。"
    };

    GUA64CI[4][4] = new String[] {
        "震：亨。 震来萀萀，笑言哑哑。 震惊百里，不丧匕鬯。\n    彖曰：震，亨。 震来萀萀，恐致福也。笑言哑哑，后有则也。 震惊百里，惊远而惧迩也。 出可以守宗庙社稷，以为祭主也。\n    象曰：洊雷，震；君子以恐惧修身。",
        "初九：震来萀萀，后笑言哑哑，吉。",
        "六二：震来厉，亿丧贝，跻于九陵，勿逐，七日得。",
        "六三：震苏苏，震行无眚。",
        "九四：震遂泥。",
        "六五：震往来厉，亿无丧，有事。",
        "上六：震索索，视矍矍，征凶。 震不于其躬，于其邻，无咎。 婚媾有言。"
    };
    GUA64CI[4][7] = new String[] {
        "小过：亨，利贞，可小事，不可大事。飞鸟遗之音，不宜上宜下，大吉。\n    彖曰：小过，小者过而亨也。 过以利贞，与时行也。 柔得中，是以小事吉也。 刚失位而不中，是以不可大事也。 有飞鸟之象焉，有飞鸟遗之音，不宜上宜下，大吉；上逆而下顺也。\n    象曰：山上有雷，小过；君子以行过乎恭，丧过乎哀，用过乎俭。",
        "初六：飞鸟以凶。",
        "六二：过其祖，遇其妣；不及其君，遇其臣；无咎。",
        "九三：弗过防之，从或戕之，凶。",
        "九四：无咎，弗过遇之。 往厉必戒，勿用永贞。",
        "六五：密云不雨，自我西郊，公弋取彼在穴。",
        "上六：弗遇过之，飞鸟离之，凶，是谓灾眚。"
    };
    GUA64CI[4][2] = new String[] {
        "归妹：征凶，无攸利。\n    彖曰：归妹，天地之大义也。天地不交，而万物不兴，归妹人之终始也。 说以动，所归妹也。 征凶，位不当也。 无攸利，柔乘刚也。\n    象曰：泽上有雷，归妹；君子以永终知敝。",
        "初九：归妹以娣，跛能履，征吉。",
        "九二：眇能视，利幽人之贞。",
        "六三：归妹以须，反归以娣。",
        "九四：归妹愆期，迟归有时。",
        "六五：帝乙归妹，其君之袂，不如其娣之袂良，月几望，吉。",
        "上六：女承筐无实，士刲羊无血，无攸利。"
    };
    GUA64CI[4][3] = new String[] {
        "丰：亨，王假之，勿忧，宜日中。\n    彖曰：丰，大也。 明以动，故丰。王假之，尚大也。 勿忧宜日中，宜照天下也。日中则昃，月盈则食，天地盈虚，与时消息，而况人於人乎？况於鬼神乎？\n    象曰：雷电皆至，丰；君子以折狱致刑。",
        "初九：遇其配主，虽旬无咎，往有尚。",
        "六二：丰其蔀，日中见斗，往得疑疾，有孚发若，吉。",
        "九三：丰其沛，日中见昧，折其右肱，无咎。",
        "九四：丰其蔀，日中见斗，遇其夷主，吉。",
        "六五：来章，有庆誉，吉。",
        "上六：丰其屋，蔀其家，窥其户，阒其无人，三岁不见，凶。"
    };
    GUA64CI[4][1] = new String[] {
        "大壮：利贞。\n    彖曰：大壮，大者壮也。 刚以动，故壮。 大壮利贞；大者正也。正大而天地之情可见矣！\n    象曰：雷在天上，大壮；君子以非礼勿履。",
        "初九：壮于趾，征凶，有孚。",
        "九二：贞吉。",
        "九三：小人用壮，君子用罔，贞厉。 羝羊触藩，羸其角。",
        "九四：贞吉悔亡，藩决不羸，壮于大舆之輹。",
        "六五：丧羊于易，无悔。",
        "上六：羝羊触藩，不能退，不能遂，无攸利，艰则吉。"
    };
    GUA64CI[4][8] = new String[] {
        "豫：利建侯行师。\n    彖曰：豫，刚应而志行，顺以动，豫。豫，顺以动，故天地如之，而况建侯行师乎？天地以顺动，故日月不过，而四时不忒；圣人以顺动，则刑罚清而民服。豫之时义大矣哉！\n    象曰：雷出地奋，豫。 先王以作乐崇德，殷荐之上帝，以配祖考。",
        "初六：鸣豫，凶。",
        "六二：介于石，不终日，贞吉。",
        "六三：盱豫，悔。 迟有悔。",
        "九四：由豫，大有得。勿疑。 朋盍簪。",
        "六五：贞疾，恒不死。",
        "上六：冥豫，成有渝，无咎。"
    };
    GUA64CI[4][6] = new String[] {
        "解：利西南，无所往，其来复吉。 有攸往，夙吉。\n    彖曰：解，险以动，动而免乎险，解。解利西南，往得众也。其来复吉，乃得中也。有攸往夙吉，往有功也。 天地解，而雷雨作，雷雨作，而百果草木皆甲坼，解之时义大矣哉！\n    象曰：雷雨作，解；君子以赦过宥罪。",
        "初六：无咎。",
        "九二：田获三狐，得黄矢，贞吉。",
        "六三：负且乘，致寇至，贞吝。",
        "九四：解而拇，朋至斯孚。",
        "六五：君子维有解，吉；有孚于小人。",
        "上六：公用射隼，于高墉之上，获之，无不利。"
    };
    GUA64CI[4][5] = new String[] {
        "恒：亨，无咎，利贞，利有攸往。\n    彖曰：恒，久也。 刚上而柔下，雷风相与，巽而动，刚柔皆应，恒。 恒亨无咎，利贞； 久於其道也，天地之道，恒久而不已也。 利有攸往，终则有始也。日月得天，而能久照，四时变化，而能久成，圣人久於其道，而天下化成；观其所恒，而天地万物之情可见矣！\n    象曰：雷风，恒；君子以立不易方。",
        "初六：浚恒，贞凶，无攸利。",
        "九二：悔亡。",
        "九三：不恒其德，或承之羞，贞吝。",
        "九四：田无禽。",
        "六五：恒其德，贞，妇人吉，夫子凶。",
        "上六：振恒，凶。"
    };

    GUA64CI[5][5] = new String[] {
        "巽：小亨，利攸往，利见大人。\n    彖曰：重巽以申命，刚巽乎中正而志行。柔皆顺乎刚，是以小亨，利有攸往，利见大人。\n    象曰：随风，巽；君子以申命行事。",
        "初六：进退，利武人之贞。",
        "九二：巽在□下，用史巫纷若，吉无咎。",
        "九三：频巽，吝。",
        "六四：悔亡，田获三品。",
        "九五：贞吉悔亡，无不利。 无初有终，先庚三日，后庚三日，吉。",
        "上九：巽在爿木下，丧其资斧，贞凶。"
    };
    GUA64CI[5][2] = new String[] {
        "中孚：豚鱼吉，利涉大川，利贞。\n    彖曰：中孚，柔在内而刚得中。 说而巽，孚，乃化邦也。 豚鱼吉，信及豚鱼也。 利涉大川，乘木舟虚也。 中孚以利贞，乃应乎天也。\n    象曰：泽上有风，中孚；君子以议狱缓死。",
        "初九：虞吉，有他不燕。",
        "九二：鸣鹤在阴，其子和之，我有好爵，吾与尔靡之。",
        "六三：得敌，或鼓或罢，或泣或歌。",
        "六四：月几望，马匹亡，无咎。",
        "九五：有孚挛如，无咎。",
        "上九：翰音登于天，贞凶。"
    };
    GUA64CI[5][7] = new String[] {
        "渐：女归吉，利贞。\n    彖曰：渐之进也，女归吉也。 进得位，往有功也。进以正，可以正邦也。其位刚，得中也。 止而巽，动不穷也。\n    象曰：山上有木，渐；君子以居贤德，善俗。",
        "初六：鸿渐于干，小子厉，有言，无咎。",
        "六二：鸿渐于磐，饮食□□，吉。",
        "九三：鸿渐于陆，夫征不复，妇孕不育，凶；利御寇。",
        "六四：鸿渐于木，或得其桷，无咎。",
        "九五：鸿渐于陵，妇三岁不孕，终莫之胜，吉。",
        "上九：鸿渐于逵，其羽可用为仪，吉。"
    };
    GUA64CI[5][6] = new String[] {
        "涣：亨。 王假有庙，利涉大川，利贞。\n    彖曰：涣，亨。 刚来而不穷，柔得位乎外而上同。 王假有庙，王乃在中也。 利涉大川，乘木有功也。\n    象曰：风行水上，涣；先王以享于帝立庙。",
        "初六：用拯马壮，吉。",
        "九二：涣奔其机，悔亡。",
        "六三：涣其躬，无悔。",
        "六四：涣其群，元吉。 涣有丘，匪夷所思。",
        "九五：涣汗其大号，涣王居，无咎。",
        "上九：涣其血，去逖出，无咎。"
    };
    GUA64CI[5][8] = new String[] {
        "观：盥而不荐，有孚顒若。\n    彖曰：大观在上，顺而巽，中正以观天下。观，盥而不荐，有孚顒若，下观而化也。 观天之神道，而四时不忒， 圣人以神道设教，而天下服矣。\n    象曰：风行地上，观；先王以省方，观民设教。",
        "初六：童观，小人无咎，君子吝。",
        "六二：窥观，利女贞。",
        "六三：观我生，进退。",
        "六四：观国之光，利用宾于王。",
        "九五：观我生，君子无咎。",
        "上九：观其生，君子无咎。"
    };
    GUA64CI[5][1] = new String[] {
        "小畜：亨。 密云不雨，自我西郊。\n    彖曰：小畜； 柔得位，而上下应之，曰小畜。健而巽，刚中而志行，乃亨。 密云不雨，尚往也。 自我西郊，施未行也。\n    象曰：风行天上，小畜；君子以懿文德。",
        "初九：复自道，何其咎，吉。",
        "九二：牵复，吉。",
        "九三：舆说辐，夫妻反目。",
        "六四：有孚，血去惕出，无咎。",
        "九五：有孚挛如，富以其邻。",
        "上九：既雨既处，尚德载，妇贞厉。 月几望，君子征凶。"
    };
    GUA64CI[5][3] = new String[] {
        "家人：利女贞。\n    彖曰：家人，女正位乎内，男正位乎外，男女正，天地之大义也。家人有严君焉，父母之谓也。父父，子子，兄兄，弟弟，夫夫，妇妇，而家道正；正家而天下定矣。\n    象曰：风自火出，家人；君子以言有物，而行有恒。",
        "初九：闲有家，悔亡。",
        "六二：无攸遂，在中馈，贞吉。",
        "九三：家人鎬鎬，悔厉吉；妇子嘻嘻，终吝。",
        "六四：富家，大吉。",
        "九五：王假有家，勿恤吉。",
        "上九：有孚威如，终吉。"
    };
    GUA64CI[5][4] = new String[] {
        "益：利有攸往，利涉大川。\n    彖曰：益，损上益下，民说无疆，自上下下，其道大光。利有攸往，中正有庆。 利涉大川，木道乃行。 益动而巽，日进无疆。 天施地生，其益无方。 凡益之道，与时偕行。\n    象曰：风雷，益；君子以见善则迁，有过则改。",
        "初九：利用为大作，元吉，无咎。",
        "六二：或益之，十朋之龟弗克违，永贞吉。 王用享于帝，吉。",
        "六三：益之用凶事，无咎。有孚中行，告公用圭。",
        "六四：中行，告公从。 利用为依迁国。",
        "九五：有孚惠心，勿问元吉。 有孚惠我德。",
        "上九：莫益之，或击之，立心勿恒，凶。"
    };

    GUA64CI[6][6] = new String[] {
        "坎：习坎，有孚，维心亨，行有尚。\n    彖曰：习坎，重险也。 水流而不盈，行险而不失其信。维心亨，乃以刚中也。 行有尚，往有功也。 天险不可升也，地险山川丘陵也，王公设险以守其国，坎之时用大矣哉！\n    象曰：水洊至，习坎；君子以常德行，习教事。",
        "初六：习坎，入于坎錎，凶。",
        "九二：坎有险，求小得。",
        "六三：来之坎坎，险且枕，入于坎錎，勿用。",
        "六四：樽酒簋贰，用缶，纳约自牖，终无咎。",
        "九五：坎不盈，只既平，无咎。",
        "上六：用徽纒，置于丛棘，三岁不得，凶。"
    };
    GUA64CI[6][7] = new String[] {
        "蹇：利西南，不利东北；利见大人，贞吉。\n    彖曰：蹇，难也，险在前也。 见险而能止，知矣哉！蹇利西南， 往得中也；不利东北，其道穷也。 利见大人，往有功也。 当位贞吉，以正邦也。 蹇之时用大矣哉！\n    象曰：山上有水，蹇；君子以反身修德。",
        "初六：往蹇，来誉。",
        "六二：王臣蹇蹇，匪躬之故。",
        "九三：往蹇来反。",
        "六四：往蹇来连。",
        "九五：大蹇朋来。",
        "上六：往蹇来硕，吉；利见大人。"
    };
    GUA64CI[6][2] = new String[] {
        "节：亨。 苦节不可贞。\n    彖曰：节，亨，刚柔分，而刚得中。苦节不可贞，其道穷也。说以行险，当位以节，中正以通。 天地节而四时成，节以制度，不伤财，不害民。\n    象曰：泽上有水，节；君子以制数度，议德行。",
        "初九：不出户庭，无咎。",
        "九二：不出门庭，凶。",
        "六三：不节若，则嗟若，无咎。",
        "六四：安节，亨。",
        "九五：甘节，吉；往有尚。",
        "上六：苦节，贞凶，悔亡。"
    };
    GUA64CI[6][4] = new String[] {
        "屯：元，亨，利，贞，勿用，有攸往，利建侯。\n    彖曰：屯，刚柔始交而难生，动乎险中，大亨贞。雷雨之动满盈，天造草昧，宜建侯而不宁。\n    象曰：云，雷，屯；君子以经纶。",
        "初九：磐桓；利居贞，利建侯。",
        "六二：屯如邅如，乘马班如。 匪寇婚媾，女子贞不字，十年乃字。",
        "六三：既鹿无虞，惟入于林中，君子几不如舍，往吝。",
        "六四：乘马班如，求婚媾，无不利。",
        "九五：屯其膏，小贞吉，大贞凶。",
        "上六：乘马班如，泣血涟如。"
    };
    GUA64CI[6][3] = new String[] {
        "既济：亨，小利贞，初吉终乱。\n    彖曰：既济，亨，小者亨也。利贞，刚柔正而位当也。 初吉，柔得中也。 终止则乱，其道穷也。\n    象曰：水在火上，既济；君子以思患而预防之。",
        "初九：曳其轮，濡其尾，无咎。",
        "六二：妇丧其髴，勿逐，七日得。",
        "九三：高宗伐鬼方，三年克之，小人勿用。",
        "六四：繻有衣袽，终日戒。",
        "九五：东邻杀牛，不如西邻之禴祭，实受其福。",
        "上六：濡其首，厉。"
    };
    GUA64CI[6][1] = new String[] {
        "需：有孚，光亨，贞吉。 利涉大川。\n    彖曰：需，须也；险在前也。 刚健而不陷，其义不困穷矣。 需有孚，光亨，贞吉。 位乎天位，以正中也。 利涉大川，往有功也。 \n    象曰：云上於天，需；君子以饮食宴乐。",
        "初九：需于郊。 利用恒，无咎。",
        "九二：需于沙。 小有言，终吉。",
        "九三：需于泥，致寇至。",
        "六四：需于血，出自穴。",
        "九五：需于酒食，贞吉。",
        "上六：入于穴，有不速之客三人来，敬之终吉。"
    };
    GUA64CI[6][8] = new String[] {
        "比：吉。 原筮元永贞，无咎。 不宁方来，后夫凶。\n    彖曰：比，吉也，比，辅也，下顺从也。 原筮元永贞，无咎，以刚中也。不宁方来，上下应也。 后夫凶，其道穷也。\n    象曰：地上有水，比；先王以建万国，亲诸侯。",
        "初六：有孚比之，无咎。 有孚盈缶，终来有他，吉。",
        "六二：比之自内，贞吉。",
        "六三：比之匪人。",
        "六四：外比之，贞吉。",
        "九五：显比，王用三驱，失前禽。 邑人不诫，吉。",
        "上六：比之无首，凶。"
    };
    GUA64CI[6][5] = new String[] {
        "井：改邑不改井，无丧无得，往来井井。汔至，亦未蹫井，羸其瓶，凶。\n    彖曰：巽乎水而上水，井；井养而不穷也。改邑不改井，乃以刚中也。汔至亦未蹫井，未有功也。 羸其瓶，是以凶也。\n    象曰：木上有水，井；君子以劳民劝相。",
        "初六：井泥不食，旧井无禽。",
        "九二：井谷射鲋，瓮敝漏。",
        "九三：井渫不食，为我民恻，可用汲，王明，并受其福。",
        "六四：井甃，无咎。",
        "九五：井冽，寒泉食。",
        "上六：井收勿幕，有孚无吉。"
    };

    GUA64CI[7][7] = new String[] {
        "艮：艮其背，不获其身，行其庭，不见其人，无咎。\n    彖曰：艮，止也。 时止则止，时行则行，动静不失其时，其道光明。 艮其止，止其所也。 上下敌应，不相与也。 是以不获其身，行其庭不见其人，无咎也。\n    象曰：兼山，艮；君子以思不出其位。",
        "初六：艮其趾，无咎，利永贞。",
        "六二：艮其腓，不拯其随，其心不快。",
        "九三：艮其限，列其夤，厉薰心。",
        "六四：艮其身，无咎。",
        "六五：艮其辅，言有序，悔亡。",
        "上九：敦艮，吉。"
    };
    GUA64CI[7][3] = new String[] {
        "贲：亨。 小利有所往。\n    彖曰：贲，亨；柔来而文刚，故亨。分刚上而文柔，故小利有攸往。天文也；文明以止，人文也。观乎天文，以察时变；观乎人文，以化成天下。\n    象曰：山下有火，贲；君子以明庶政，无敢折狱。",
        "初九：贲其趾，舍车而徒。",
        "六二：贲其须。",
        "九三：贲如濡如，永贞吉。",
        "六四：贲如皤如，白马翰如，匪寇婚媾。",
        "六五：贲于丘园，束帛戋戋，吝，终吉。",
        "上九：白贲，无咎。"
    };
    GUA64CI[7][1] = new String[] {
        "大畜：利贞，不家食吉，利涉大川。\n    彖曰：大畜，刚健笃实辉光，日新其德，刚上而尚贤。 能止健，大正也。不家食吉，养贤也。 利涉大川，应乎天也。\n    象曰：天在山中，大畜；君子以多识前言往行，以畜其德。",
        "初九：有厉利已。",
        "九二：舆说辐。",
        "九三：良马逐，利艰贞。 曰闲舆卫，利有攸往。",
        "六四：童豕之牿，元吉。",
        "六五：豮豕之牙，吉。",
        "上九：何天之衢，亨。"
    };
    GUA64CI[7][2] = new String[] {
        "损：有孚，元吉，无咎，可贞，利有攸往？ 曷之用，二簋可用享。\n    彖曰：损，损下益上，其道上行。损而有孚，元吉，无咎，可贞，利有攸往。 曷之用？ 二簋可用享；二簋应有时。损刚益柔有时，损益盈虚，与时偕行。\n    象曰：山下有泽，损；君子以惩忿窒欲。",
        "初九：已事遄往，无咎，酌损之。",
        "九二：利贞，征凶，弗损益之。",
        "六三：三人行，则损一人；一人行，则得其友。",
        "六四：损其疾，使遄有喜，无咎。",
        "六五：或益之，十朋之龟弗克违，元吉。",
        "上九：弗损益之，无咎，贞吉，利有攸往，得臣无家。"
    };
    GUA64CI[7][6] = new String[] {
        "蒙：亨。 匪我求童蒙，童蒙求我。 初噬告，再三渎，渎则不告。利贞。\n    彖曰：蒙，山下有险，险而止，蒙。 蒙亨，以亨行时中也。匪我求童蒙，童蒙求我，志应也。 初噬告，以刚中也。再三渎， 渎则不告，渎蒙也。 蒙以养正，圣功也。 \n    象曰：山下出泉，蒙；君子以果行育德。",
        "初六：发蒙，利用刑人，用说桎梏，以往吝。",
        "九二：包蒙吉；纳妇吉；子克家。",
        "六三：勿用娶女；见金夫，不有躬，无攸利。",
        "六四：困蒙，吝。",
        "六五：童蒙，吉。",
        "上九：击蒙；不利为寇，利御寇。"
    };
    GUA64CI[7][8] = new String[] {
        "剥：不利有攸往。\n    彖曰：剥，剥也，柔变刚也。 不利有攸往，小人长也。 顺而止之，观象也。君子尚消息盈虚，天行也。\n    象曰：山附地上，剥；上以厚下，安宅。",
        "初六：剥牀以足，蔑贞凶。",
        "六二：剥牀以辨，蔑贞凶。",
        "六三：剥之，无咎。",
        "六四：剥牀以肤，凶。",
        "六五：贯鱼，以宫人宠，无不利。",
        "上九：硕果不食，君子得舆，小人剥庐。"
    };
    GUA64CI[7][4] = new String[] {
        "颐：贞吉。 观颐，自求口实。\n    彖曰：颐贞吉，养正则吉也。 观颐，观其所养也； 自求口实，观其自养也。 天地养万物，圣人养贤，以及万民；颐之时义大矣哉！\n    象曰：山下有雷，颐；君子以慎言语，节饮食。",
        "初九：舍尔灵龟，观我朵颐，凶。",
        "六二：颠颐，拂经，于丘颐，征凶。",
        "六三：拂颐，贞凶，十年勿用，无攸利。",
        "六四：颠颐吉，虎视眈眈，其欲逐逐，无咎。",
        "六五：拂经，居贞吉，不可涉大川。",
        "上九：由颐，厉吉，利涉大川。"
    };
    GUA64CI[7][5] = new String[] {
        "蛊：元亨，利涉大川。 先甲三日，后甲三日。\n    彖曰：蛊，刚上而柔下，巽而止，蛊。 蛊，元亨，而天下治也。 利涉大川，往有事也。 先甲三日，后甲三日，终则有始，天行也。\n    象曰：山下有风，蛊；君子以振民育德。",
        "初六：干父之蛊，有子，考无咎，厉终吉。",
        "九二：干母之蛊，不可贞。",
        "九三：干父小有晦，无大咎。",
        "六四：裕父之蛊，往见吝。",
        "六五：干父之蛊，用誉。",
        "上九：不事王侯，高尚其事。"
    };

    GUA64CI[8][8] = new String[] {
        "坤：元，亨，利牝马之贞。 君子有攸往，先迷后得主，利西南得朋，东北丧朋。 安贞，吉。\n    彖曰：至哉坤元，万物资生，乃顺承天。 坤厚载物，德合无疆。 含弘光大，品物咸亨。牝马地类，行地无疆，柔顺利贞。 君子攸行，先迷失道，后顺得常。 西南得朋，乃与类行；东北丧朋，乃终有庆。安贞之吉，应地无疆。 \n    象曰：地势坤，君子以厚德载物。",
        "初六：履霜，坚冰至。",
        "六二：直，方，大，不习无不利。",
        "六三：含章可贞。 或从王事，无成有终。",
        "六四：括囊；无咎，无誉。",
        "六五：黄裳元吉。",
        "上六：战龙於野，其血玄黄。",
        "利永贞。"
    };
    GUA64CI[8][7] = new String[] {
        "谦：亨，君子有终。\n    彖曰：谦，亨，天道下济而光明，地道卑而上行。天道亏盈而益谦，地道变盈而流谦，鬼神害盈而福谦，人道恶盈而好谦。谦尊而光，卑而不可逾，君子之终也。\n    象曰：地中有山，谦；君子以裒多益寡，称物平施。",
        "初六：谦谦君子，用涉大川，吉。",
        "六二：鸣谦，贞吉。",
        "九三：劳谦君子，有终吉。",
        "六四：无不利，撝谦。",
        "六五：不富，以其邻，利用侵伐，无不利。",
        "上六：鸣谦，利用行师，征邑国。"
    };
    GUA64CI[8][3] = new String[] {
        "明夷：利艰贞。\n    彖曰：明入地中，明夷。 内文明而外柔顺，以蒙大难，文王以之。 利艰贞，晦其明也，内难而能正其志，箕子以之。\n    象曰：明入地中，明夷；君子以莅众，用晦而明。",
        "初九：明夷于飞，垂其翼。 君子于行，三日不食， 有攸往，主人有言。",
        "六二：明夷，夷于左股，用拯马壮，吉。",
        "九三：明夷于南狩，得其大首，不可疾贞。",
        "六四：入于左腹，获明夷之心，出于门庭。",
        "六五：箕子之明夷，利贞。",
        "上六：不明晦，初登于天，后入于地。"
    };
    GUA64CI[8][6] = new String[] {
        "师：贞，丈人，吉无咎。\n    彖曰：师，众也，贞正也，能以众正，可以王矣。 刚中而应，行险而顺，以此毒天下，而民从之，吉又何咎矣。\n    象曰：地中有水，师；君子以容民畜众。",
        "初六：师出以律，否臧凶。",
        "九二：在师中，吉无咎，王三锡命。",
        "六三：师或舆尸，凶。",
        "六四：师左次，无咎。",
        "六五：田有禽，利执言，无咎。长子帅师，弟子舆尸，贞凶。",
        "上六：大君有命，开国承家，小人勿用。"
    };
    GUA64CI[8][4] = new String[] {
        "复：亨。 出入无疾，朋来无咎。 反复其道，七日来复，利有攸往。\n    彖曰：复亨；刚反，动而以顺行，是以出入无疾，朋来无咎。 反复其道，七日来复，天行也。 利有攸往，刚长也。复其见天地之心乎？\n    象曰：雷在地中，复；先王以至日闭关，商旅不行，后不省方。",
        "初九：不复远，无只悔，元吉。",
        "六二：休复，吉。",
        "六三：频复，厉无咎。",
        "六四：中行独复。",
        "六五：敦复，无悔。",
        "上六：迷复，凶，有灾眚。用行师，终有大败，以其国君，凶；至于十年，不克征。"
    };
    GUA64CI[8][2] = new String[] {
        "临：元，亨，利，贞。 至于八月有凶。\n    彖曰：临，刚浸而长。 说而顺，刚中而应，大亨以正，天之道也。 至于八月有凶，消不久也。\n    象曰：泽上有地，临； 君子以教思无穷，容保民无疆。",
        "初九：咸临，贞吉。",
        "九二：咸临，吉无不利。",
        "六三：甘临，无攸利。 既忧之，无咎。",
        "六四：至临，无咎。",
        "六五：知临，大君之宜，吉。",
        "上六：敦临，吉无咎。"
    };
    GUA64CI[8][1] = new String[] {
        "泰：小往大来，吉亨。\n    彖曰：泰，小往大来，吉亨。则是天地交，而万物通也；上下交，而其志同也。内阳而外阴，内健而外顺，内君子而外小人，君子道长，小人道消也。\n    象曰：天地交泰，后以财（裁）成天地之道，辅相天地之宜，以左右民。",
        "初九：拔茅茹，以其夤，征吉。",
        "九二：包荒，用冯河，不遐遗，朋亡，得尚于中行。",
        "九三：无平不陂，无往不复，艰贞无咎。 勿恤其孚，于食有福。",
        "六四：翩翩不富，以其邻，不戒以孚。",
        "六五：帝乙归妹，以祉元吉。",
        "上六：城复于隍，勿用师。 自邑告命，贞吝。"
    };
    GUA64CI[8][5] = new String[] {
        "升：元亨，用见大人，勿恤，南征吉。\n    彖曰：柔以时升，巽而顺，刚中而应，是以大亨。用见大人，勿恤；有庆也。 南征吉，志行也。\n    象曰：地中生木，升；君子以顺德，积小以高大。",
        "初六：允升，大吉。",
        "九二：孚乃利用禴，无咎。",
        "九三：升虚邑。",
        "六四：王用亨于岐山，吉无咎。",
        "六五：贞吉，升阶。",
        "上六：冥升，利于不息之贞。"
    };

  }

  /**
   * 八卦卦象图,由卦得到卦象
   * 由卦象得到卦
   * 各八宫卦所属五行
   * 各天干所属五行
   * 各地支所属五行
   * 各八宫卦包含哪些卦
   * 各八宫卦世应爻位置。八卦之首世六当，以下初爻轮上场。游魂八卦四爻位，归魂八卦三爻详。
   * 各八宫卦地支。乾在内子寅辰，乾在外午申戌。
                  坎在内寅辰午，坎在外申戌子。
                  艮在内辰午申，艮在外戌子寅。
                  震在内子寅辰，震在外午申戌。
                  巽在内丑女酉，巽在外未巳卯。
                  离在内卯丑亥，离在外酉未巳。
                  兑在内巳卯丑，兑在外亥酉未。
                  坤在内未巳卯，坤在外丑亥酉。
   * 六亲，以八宫卦所属为我，卦中爻作用我，以前面的作用后面的而导出如：linqin[jin][mu]=金克木=妻财:"1兄弟","2子孙","3妻财","4官鬼","5父母"
   * 六神与起卦日期的关系：甲乙起青龙丙丁起朱雀 戊日起勾陈 己日起腾蛇 庚辛起白虎壬癸起玄武。
   * 月卦身或卦身的起法。阴世则从午月起，阳世还从子月生。欲得识其卦中意，从初数至世方真。
   * 安世身.要点：看世爻的地支。子午持世身居初，丑未持世身居二。申持世身居三，卯酉持世身居四。辰戌持世身居五，巳亥持世身居六。
   * 定飞神伏神.伏克飞神为除暴，
                飞来克伏反伤身。
                 伏去生飞名泄气，
                 来生伏得长生。
                 爻逢伏克飞无事，
                 用见飞伤伏不宁。
                 飞伏不和为无助，
                伏藏出现审来因。
   * 进神退神论 丑 辰 未 戌 丑
   *   寅化卯、巳化午、申化酉、亥化子、丑化辰、辰化未、未化戌、戌化丑
   *   卯化寅、午化巳、酉化申、子化亥、辰化丑、丑化戌、戌化未、未化辰
   */
  public static int[][] GUAXIANG = new int[9][4];
  static
  {
    GUAXIANG[0] = null;
    GUAXIANG[1] = new int[]{0,YANGYAO,YANGYAO,YANGYAO};
    GUAXIANG[2] = new int[]{0,YANGYAO,YANGYAO,YINYAO};
    GUAXIANG[3] = new int[]{0,YANGYAO,YINYAO,YANGYAO};
    GUAXIANG[4] = new int[]{0,YANGYAO,YINYAO,YINYAO};
    GUAXIANG[5] = new int[]{0,YINYAO,YANGYAO,YANGYAO};
    GUAXIANG[6] = new int[]{0,YINYAO,YANGYAO,YINYAO};
    GUAXIANG[7] = new int[]{0,YINYAO,YINYAO,YANGYAO};
    GUAXIANG[8] = new int[]{0,YINYAO,YINYAO,YINYAO};
  }
  public static int[][][] XIANGGUA = new int[3][3][3];
  static
  {
    XIANGGUA[1][1] = new int[]{0, QIAN, DUI};
    XIANGGUA[1][2] = new int[]{0, LI, ZHEN};
    XIANGGUA[2][1] = new int[]{0, SHUN, KAN};
    XIANGGUA[2][2] = new int[]{0, GEN, KUN};
  }

  public final static int[] BAGONGGUAWH = {0,JIN,JIN,HUO,MU,MU,SHUI,TU,TU};
  public final static int[] TIANGANWH = {0,MU,MU,HUO,HUO,TU,TU,JIN,JIN,SHUI,SHUI};
  public final static int[] DIZIWH = {0,SHUI,TU,MU,MU,TU,HUO,HUO,TU,JIN,JIN,TU,SHUI};

  public final static int[][] BAGONGGUA = new int[9][9];
  static
  {
    BAGONGGUA[0] = null;
    BAGONGGUA[1] = new int[]{0,QIAN,GEN,LI,SHUN,QIAN,LI,QIAN,QIAN};
    BAGONGGUA[2] = new int[]{0,KUN,DUI,KAN,ZHEN,ZHEN,DUI,DUI,DUI};
    BAGONGGUA[3] = new int[]{0,QIAN,GEN,LI,SHUN,LI,LI,LI,QIAN};
    BAGONGGUA[4] = new int[]{0,KUN,DUI,KAN,ZHEN,ZHEN,ZHEN,DUI,ZHEN};
    BAGONGGUA[5] = new int[]{0,SHUN,GEN,SHUN,SHUN,SHUN,LI,GEN,QIAN};
    BAGONGGUA[6] = new int[]{0,KUN,KAN,KAN,KAN,ZHEN,KAN,DUI,KUN};
    BAGONGGUA[7] = new int[]{0,GEN,GEN,GEN,SHUN,SHUN,LI,GEN,QIAN};
    BAGONGGUA[8] = new int[]{0,KUN,KUN,KAN,KUN,ZHEN,KAN,DUI,KUN};
  }
  public final static int[][] BAGONGSHIYING = new int[9][9];
  static
  {
    BAGONGSHIYING[0] = null;
    BAGONGSHIYING[1] = new int[] {0,6,5,3,4,1,4,2,3};
    BAGONGSHIYING[2] = new int[] {0,5,6,4,3,4,1,3,2};
    BAGONGSHIYING[3] = new int[] {0,3,4,6,5,2,3,1,4};
    BAGONGSHIYING[4] = new int[] {0,4,3,5,6,3,2,4,1};
    BAGONGSHIYING[5] = new int[] {0,1,4,2,3,6,5,3,4};
    BAGONGSHIYING[6] = new int[] {0,4,1,3,2,5,6,4,3};
    BAGONGSHIYING[7] = new int[] {0,2,3,1,4,3,4,6,5};
    BAGONGSHIYING[8] = new int[] {0,3,2,4,1,4,3,5,6};
  }
  public final static int[][][] BAGUADIZI = new int[9][2][4];
  static
  {
    BAGUADIZI[1][0] = new int[]{0,1,3,5};
    BAGUADIZI[4][0] = new int[]{0,1,3,5};
    BAGUADIZI[1][1] = new int[]{0,7,9,11};
    BAGUADIZI[4][1] = new int[]{0,7,9,11};

    BAGUADIZI[2][0] = new int[]{0,6,4,2};
    BAGUADIZI[2][1] = new int[]{0,12,10,8};
    BAGUADIZI[3][0] = new int[]{0,4,2,12};
    BAGUADIZI[3][1] = new int[]{0,10,8,6};
    BAGUADIZI[5][0] = new int[]{0,2,12,10};
    BAGUADIZI[5][1] = new int[]{0,8,6,4};
    BAGUADIZI[6][0] = new int[]{0,3,5,7};
    BAGUADIZI[6][1] = new int[]{0,9,11,1};
    BAGUADIZI[7][0] = new int[]{0,5,7,9};
    BAGUADIZI[7][1] = new int[]{0,11,1,3};
    BAGUADIZI[8][0] = new int[]{0,8,6,4};
    BAGUADIZI[8][1] = new int[]{0,2,12,10};

  }
  //八卦纳干
  public final static int[][][] BAGUATG = new int[9][2][4];
  static
  {
    BAGUATG[1][0] = new int[]{0,1,1,1};
    BAGUATG[1][1] = new int[]{0,9,9,9};
    BAGUATG[2][0] = new int[]{0,4,4,4};
    BAGUATG[2][1] = new int[]{0,4,4,4};
    BAGUATG[3][0] = new int[]{0,6,6,6};
    BAGUATG[3][1] = new int[]{0,6,6,6};
    BAGUATG[4][0] = new int[]{0,7,7,7};
    BAGUATG[4][1] = new int[]{0,7,7,7};
    BAGUATG[5][0] = new int[]{0,8,8,8};
    BAGUATG[5][1] = new int[]{0,8,8,8};
    BAGUATG[6][0] = new int[]{0,5,5,5};
    BAGUATG[6][1] = new int[]{0,5,5,5};
    BAGUATG[7][0] = new int[]{0,3,3,3};
    BAGUATG[7][1] = new int[]{0,3,3,3};
    BAGUATG[8][0] = new int[]{0,2,2,2};
    BAGUATG[8][1] = new int[]{0,10,10,10};

  }


  public final static int[][] LIUQIN = new int[6][6];
  static
  {
    LIUQIN[JIN] = new int[]{0,1,3,2,4,5};
    LIUQIN[MU] = new int[]{0,4,1,5,2,3};
    LIUQIN[SHUI] = new int[]{0,5,2,1,3,4};
    LIUQIN[HUO] = new int[]{0,3,5,4,1,2};
    LIUQIN[TU] = new int[]{0,2,4,3,5,1};
  }
  public final static int[][] RIGANLIUSHEN = new int[11][7];
  static
  {
    RIGANLIUSHEN[JIA] = new int[]{0,QINGLONG,ZHUQUE,GOUCHEN,TENGSHE,BAIHU,XUANWU};
    RIGANLIUSHEN[YI] = new int[]{0,QINGLONG,ZHUQUE,GOUCHEN,TENGSHE,BAIHU,XUANWU};
    RIGANLIUSHEN[BING] = new int[]{0,ZHUQUE,GOUCHEN,TENGSHE,BAIHU,XUANWU,QINGLONG};
    RIGANLIUSHEN[DING] = new int[]{0,ZHUQUE,GOUCHEN,TENGSHE,BAIHU,XUANWU,QINGLONG};
    RIGANLIUSHEN[WUG] = new int[]{0,GOUCHEN,TENGSHE,BAIHU,XUANWU,QINGLONG,ZHUQUE};
    RIGANLIUSHEN[JI] = new int[]{0,TENGSHE,BAIHU,XUANWU,QINGLONG,ZHUQUE,GOUCHEN};
    RIGANLIUSHEN[GENG] = new int[]{0,BAIHU,XUANWU,QINGLONG,ZHUQUE,GOUCHEN,TENGSHE};
    RIGANLIUSHEN[XIN] = new int[]{0,BAIHU,XUANWU,QINGLONG,ZHUQUE,GOUCHEN,TENGSHE};
    RIGANLIUSHEN[REN] = new int[]{0,XUANWU,QINGLONG,ZHUQUE,GOUCHEN,TENGSHE,BAIHU};
    RIGANLIUSHEN[GUI] = new int[]{0,XUANWU,QINGLONG,ZHUQUE,GOUCHEN,TENGSHE,BAIHU};
  }
  public final static int[][] YUEGUASHEN = new int[3][7];
  static
  {
    YUEGUASHEN[1] = new int[] {0,ZI,CHOU,YIN,MAO,CHEN,SI};
    YUEGUASHEN[2] = new int[] {0,WUZ,WEI,SHEN,YOU,XU,HAI};
  }
  public final static String[] YUEGUASHENNOTE = new String[]{"卦身没上卦，主事无头绪。","卦身在，本占事之主。"};
  public final static int[] SHISHEN2 = {0,1,2,3,4,5,6,1,2,3,4,5,6};
  public final static String[] SHISHEN2NAME = {null,"第一爻。","第二爻。","第三爻。","第四爻。","第五爻。","第六爻。"};
  public final static String[] FEIFUNAME = new String[] {null, "飞←", "伏←"};
  public final static int[][] JINSHEN = new int[13][13];
  public final static int[][] TUISHEN = new int[13][13];
  static
  {
    JINSHEN[YIN] = new int[]   {0, 0,0, 0,1, 0,0, 0,0, 0,0, 0,0};
    TUISHEN[MAO] = new int[]   {0, 0,0, 1,0, 0,0, 0,0, 0,0, 0,0};
    JINSHEN[SI] = new int[]    {0, 0,0, 0,0, 0,0, 1,0, 0,0, 0,0};
    TUISHEN[WUZ] = new int[]   {0, 0,0, 0,0, 0,1, 0,0, 0,0, 0,0};
    JINSHEN[SHEN] = new int[]  {0, 0,0, 0,0, 0,0, 0,0, 0,1, 0,0};
    TUISHEN[YOU] = new int[]   {0, 0,0, 0,0, 0,0, 0,0, 1,0, 0,0};
    JINSHEN[HAI] = new int[]   {0, 1,0, 0,0, 0,0, 0,0, 0,0, 0,0};
    TUISHEN[ZI] = new int[]    {0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,1};
    TUISHEN[CHOU] = new int[]  {0, 0,0, 0,0, 0,0, 0,0, 0,0, 1,0};
    JINSHEN[CHOU] = new int[]  {0, 0,0, 0,0, 1,0, 0,0, 0,0, 0,0};
    TUISHEN[CHEN] = new int[]  {0, 0,1, 0,0, 0,0, 0,0, 0,0, 0,0};
    JINSHEN[CHEN] = new int[]  {0, 0,0, 0,0, 0,0, 0,1, 0,0, 0,0};
    TUISHEN[WEI] = new int[]  {0, 0,0, 0,0, 1,0, 0,0, 0,0, 0,0};
    JINSHEN[WEI] = new int[]  {0, 0,0, 0,0, 0,0, 0,0, 0,0, 1,0};
    TUISHEN[XU] = new int[]  {0, 0,0, 0,0, 0,0, 0,1, 0,0, 0,0};
    JINSHEN[XU] = new int[]  {0, 0,1, 0,0, 0,0, 0,0, 0,0, 0,0};
  }

  /**
   * 五行相生,包括十天干相生、十二地支相生、八卦相生、六十四卦相生，分为主生与互生
   * 五行相克，同上
   * 五行主生 即前者是否生后者，而非相互生之
   * 五行主克 同上
   * 五行比和
   * 十冲卦，八合卦
   *     六冲卦有：乾、坎、艮、震、舅、离、坤、兑、天雷无妾、雷天大壮、共十卦。
   *     六合卦有：天地否、地天泰、地雷复、雷地予、水泽节、泽水困、火山旅、山火贲、共八个卦。
   * 京房十六变卦
   *     占者遇：变入本宫卦者，灾福应十分。
   *     外戒卦：吉凶从外来。
   *     内戒卦：祸福从内起。
   *     骸骨卦：生则赢瘦，死不葬埋。
   *     棺椁卦：病必死亡。
   *     血脉卦：主血疾漏下。
   *     绝命卦：事多反复，为人孤独，不谐于俗。
   *     游魂、肌肉卦：精神恍惚，如梦如痴。
   *     归魂、家墓卦：坟墓吉，而无事可成也。”
   *       乾、女后、遁、否、观、剥、晋、  旅、 鼎、大有、离、噬嗑、  颐、益、无妄、同人、乾。
           兑、困、  萃、咸、骞、谦、小过、豫、解、归妹、震、丰、     夷、既、革、  随、  泽
           离、旅、 鼎、未济、蒙、涣、讼、姤、遁、同人、乾、履、      孚、损、睽、大有、离
           震、豫、解、恒、 升、井、大过、困、萃、随、 泽、夬、       需、泰、大壮、归妹、震
           巽、小畜、家、益、妄、噬、颐、贲、大畜、蛊、艮、剥、       晋、否 观、  益、  巽
           坎、节、屯、既济、革、丰、夷、复、临、师、  坤、谦、     小过、咸、骞、 比、 坎
           艮、贲、大畜、损、睽、履、孚、小率、家、渐、巽、涣、       讼、未、蒙、蛊、  艮
           坤、复、 临、 泰、大壮、夬、需、节、 屯、比、坎、井、 大过、恒、升、  师、坤
   * 十二辟卦，又名侯卦，意思是诸侯之卦。
   *     复、临、泰、大壮、夬、乾、姤、遁、否、观、剥、坤
   */
  public final static int[][] WXXIANGSHENG = new int[6][6];
  public final static int[][] WXDANSHENG = new int[6][6];
  static
  {
    WXXIANGSHENG[JIN] = new int[] {0, 0,0, 1,0,1};
    WXXIANGSHENG[MU] = new int[]  {0, 0,0, 1,1,0};
    WXXIANGSHENG[SHUI] = new int[]{0, 1,1, 0,0,0};
    WXXIANGSHENG[HUO] = new int[] {0, 0,1, 0,0,1};
    WXXIANGSHENG[TU] = new int[]  {0, 1,0, 0,1,0};

    WXDANSHENG[JIN] = new int[] {0, 0,0, 1,0,0};
    WXDANSHENG[MU] = new int[]  {0, 0,0, 0,1,0};
    WXDANSHENG[SHUI] = new int[]{0, 0,1, 0,0,0};
    WXDANSHENG[HUO] = new int[] {0, 0,0, 0,0,1};
    WXDANSHENG[TU] = new int[]  {0, 1,0, 0,0,0};

  }
  public final static int[][] WXXIANGKE = new int[6][6];
  public final static int[][] WXDANKE = new int[6][6];
  static
  {
    WXXIANGKE[JIN] = new int[] {0, 0,1, 0,1,0};
    WXXIANGKE[MU] = new int[]  {0, 1,0, 0,0,1};
    WXXIANGKE[SHUI] = new int[]{0, 0,0, 0,1,1};
    WXXIANGKE[HUO] = new int[] {0, 1,0, 1,0,0};
    WXXIANGKE[TU] = new int[]  {0, 0,1, 1,0,0};

    WXDANKE[JIN] = new int[] {0, 0,1, 0,0,0};
    WXDANKE[MU] = new int[]  {0, 0,0, 0,0,1};
    WXDANKE[SHUI] = new int[]{0, 0,0, 0,1,0};
    WXDANKE[HUO] = new int[] {0, 1,0, 0,0,0};
    WXDANKE[TU] = new int[]  {0, 0,0, 1,0,0};

  }

  public final static int[][] WXBIHE = new int[6][6];
  static
  {
    WXBIHE[JIN] = new int[] {0,1,0, 0,0,0};
    WXBIHE[MU] = new int[]  {0, 0,1, 0,0,0};
    WXBIHE[SHUI] = new int[]{0, 0,0, 1,0,0};
    WXBIHE[HUO] = new int[] {0, 0,0, 0,1,0};
    WXBIHE[TU] = new int[]  {0, 0,0, 0,0,1};
  }

  public final static int[][] LIUCHONGGUA = new int[9][9]; //{11,22,33,44,55,66,77,88,14,41};
  public final static int[][] LIUHEGUA = new int[9][9]; //{18, 81, 84,48,62,26,37,73};
  static
  {
    LIUCHONGGUA[1] = new int[]{0, 1,0,0,1, 0,0,0,0};
    LIUCHONGGUA[2] = new int[]{0, 0,1,0,0, 0,0,0,0};
    LIUCHONGGUA[3] = new int[]{0, 0,0,1,0, 0,0,0,0};
    LIUCHONGGUA[4] = new int[]{0, 1,0,0,1, 0,0,0,0};
    LIUCHONGGUA[5] = new int[]{0, 0,0,0,0, 1,0,0,0};
    LIUCHONGGUA[6] = new int[]{0, 0,0,0,0, 0,1,0,0};
    LIUCHONGGUA[7] = new int[]{0, 0,0,0,0, 0,0,1,0};
    LIUCHONGGUA[8] = new int[]{0, 0,0,0,0, 0,0,0,1};

    LIUHEGUA[1] = new int[] {0, 0,0,0,0, 0,0,0,1};
    LIUHEGUA[2] = new int[] {0, 0,0,0,0, 0,1,0,0};
    LIUHEGUA[3] = new int[] {0, 0,0,0,0, 0,0,1,0};
    LIUHEGUA[4] = new int[] {0, 0,0,0,0, 0,0,0,1};
    LIUHEGUA[6] = new int[] {0, 0,1,0,0, 0,0,0,0};
    LIUHEGUA[7] = new int[] {0, 0,0,1,0, 0,0,0,0};
    LIUHEGUA[8] = new int[] {0, 1,0,0,1, 0,0,0,0};
  }
  public final static String[] JINGFANGNAME = new String[]{
        null,"本宫卦","游魂卦", "外戒卦","内戒卦","归魂卦","绝命卦","血脉卦",
             "肌肉卦","骸骨卦", "棺椁卦","墓库卦"};
  public final static String[] JINGFANGZHAN = new String[]{
        null,"为本宫卦，占遇灾福应十分。",
             "为游魂卦，主精神恍惚，如梦如痴。",
             "为外戒卦：主吉凶从外来。",
             "为内戒卦：主吉凶从内起。",
             "为归魂卦，除占坟墓吉外，而无事可成也。",
             "为绝命卦，事多反复，为人孤独，不谐于俗。",
             "为血脉卦，主血疾漏下。",
             "为肌肉卦，主精神恍惚，如梦如痴。",
             "为骸骨卦，生则赢瘦，死不葬埋。",
             "为棺椁卦，病必死亡。",
             "为墓库卦，除占坟墓吉外，而无事可成也。"};
  public final static String[] JINGFANGZHAN2 = new String[]{
    null,"本宫卦","游魂卦","外戒卦","内戒卦","归魂卦","绝命卦","血脉卦","肌肉卦","骸骨卦","棺椁卦","墓库卦"};

  public final static int[][][] SIXTEENGUA = new int[9][9][9];
  static
  {
    SIXTEENGUA[QIAN][QIAN][SHUN]=1;SIXTEENGUA[QIAN][QIAN][GEN]=1;SIXTEENGUA[QIAN][QIAN][KUN]=1;SIXTEENGUA[QIAN][SHUN][KUN]=1;
    SIXTEENGUA[QIAN][GEN][KUN]=1;SIXTEENGUA[QIAN][LI][KUN]=2;SIXTEENGUA[QIAN][LI][GEN]=3;SIXTEENGUA[QIAN][LI][SHUN]=4;
    SIXTEENGUA[QIAN][LI][QIAN]=5;SIXTEENGUA[QIAN][LI][LI]=6;SIXTEENGUA[QIAN][LI][ZHEN]=7;SIXTEENGUA[QIAN][GEN][ZHEN]=8;
    SIXTEENGUA[QIAN][SHUN][ZHEN]=9;SIXTEENGUA[QIAN][QIAN][ZHEN]=10;SIXTEENGUA[QIAN][QIAN][LI]=11;

    SIXTEENGUA[DUI][DUI][KAN]=1;SIXTEENGUA[DUI][DUI][KUN]=1;SIXTEENGUA[DUI][DUI][GEN]=1;SIXTEENGUA[DUI][KAN][GEN]=1;
    SIXTEENGUA[DUI][KUN][GEN]=1;SIXTEENGUA[DUI][ZHEN][GEN]=2;SIXTEENGUA[DUI][ZHEN][KUN]=3;SIXTEENGUA[DUI][ZHEN][KAN]=4;
    SIXTEENGUA[DUI][ZHEN][DUI]=5;SIXTEENGUA[DUI][ZHEN][ZHEN]=6;SIXTEENGUA[DUI][ZHEN][LI]=7;SIXTEENGUA[DUI][KUN][LI]=8;
    SIXTEENGUA[DUI][KAN][LI]=9;SIXTEENGUA[DUI][DUI][LI]=10;SIXTEENGUA[DUI][DUI][ZHEN]=11;

    SIXTEENGUA[LI][LI][GEN]=1;SIXTEENGUA[LI][LI][SHUN]=1;SIXTEENGUA[LI][LI][KAN]=1;SIXTEENGUA[LI][GEN][KAN]=1;
    SIXTEENGUA[LI][SHUN][KAN]=1;SIXTEENGUA[LI][QIAN][KAN]=2;SIXTEENGUA[LI][QIAN][SHUN]=3;SIXTEENGUA[LI][QIAN][GEN]=4;
    SIXTEENGUA[LI][QIAN][LI]=5;SIXTEENGUA[LI][QIAN][QIAN]=6;SIXTEENGUA[LI][QIAN][DUI]=7;SIXTEENGUA[LI][SHUN][DUI]=8;
    SIXTEENGUA[LI][GEN][DUI]=9;SIXTEENGUA[LI][LI][DUI]=10;SIXTEENGUA[LI][LI][QIAN]=11;

    SIXTEENGUA[ZHEN][ZHEN][KUN]=1;SIXTEENGUA[ZHEN][ZHEN][KAN]=1;SIXTEENGUA[ZHEN][ZHEN][SHUN]=1;SIXTEENGUA[ZHEN][KUN][SHUN]=1;
    SIXTEENGUA[ZHEN][KAN][SHUN]=1;SIXTEENGUA[ZHEN][DUI][SHUN]=2;SIXTEENGUA[ZHEN][DUI][KAN]=3;SIXTEENGUA[ZHEN][DUI][KUN]=4;
    SIXTEENGUA[ZHEN][DUI][ZHEN]=5;SIXTEENGUA[ZHEN][DUI][DUI]=6;SIXTEENGUA[ZHEN][DUI][QIAN]=7;SIXTEENGUA[ZHEN][KAN][QIAN]=8;
    SIXTEENGUA[ZHEN][KUN][QIAN]=9;SIXTEENGUA[ZHEN][ZHEN][QIAN]=10;SIXTEENGUA[ZHEN][ZHEN][DUI]=11;

    SIXTEENGUA[SHUN][SHUN][QIAN]=1;SIXTEENGUA[SHUN][SHUN][LI]=1;SIXTEENGUA[SHUN][SHUN][ZHEN]=1;SIXTEENGUA[SHUN][QIAN][ZHEN]=1;
    SIXTEENGUA[SHUN][LI][ZHEN]=1;SIXTEENGUA[SHUN][GEN][ZHEN]=2;SIXTEENGUA[SHUN][GEN][LI]=3;SIXTEENGUA[SHUN][GEN][QIAN]=4;
    SIXTEENGUA[SHUN][GEN][SHUN]=5;SIXTEENGUA[SHUN][GEN][GEN]=6;SIXTEENGUA[SHUN][GEN][KUN]=7;SIXTEENGUA[SHUN][LI][KUN]=8;
    SIXTEENGUA[SHUN][QIAN][KUN]=9;SIXTEENGUA[SHUN][SHUN][KUN]=10;SIXTEENGUA[SHUN][SHUN][ZHEN]=11;

    SIXTEENGUA[KAN][KAN][DUI]=1;SIXTEENGUA[KAN][KAN][ZHEN]=1;SIXTEENGUA[KAN][KAN][LI]=1;SIXTEENGUA[KAN][DUI][LI]=1;
    SIXTEENGUA[KAN][ZHEN][LI]=1;SIXTEENGUA[KAN][KUN][LI]=2;SIXTEENGUA[KAN][KUN][ZHEN]=3;SIXTEENGUA[KAN][KUN][DUI]=4;
    SIXTEENGUA[KAN][KUN][KAN]=5;SIXTEENGUA[KAN][KUN][KUN]=6;SIXTEENGUA[KAN][KUN][GEN]=7;SIXTEENGUA[KAN][ZHEN][GEN]=8;
    SIXTEENGUA[KAN][DUI][GEN]=9;SIXTEENGUA[KAN][KAN][GEN]=10;SIXTEENGUA[KAN][KAN][KUN]=11;

    SIXTEENGUA[GEN][GEN][LI]=1;SIXTEENGUA[GEN][GEN][QIAN]=1;SIXTEENGUA[GEN][GEN][DUI]=1;SIXTEENGUA[GEN][LI][DUI]=1;
    SIXTEENGUA[GEN][QIAN][DUI]=1;SIXTEENGUA[GEN][SHUN][DUI]=2;SIXTEENGUA[GEN][SHUN][QIAN]=3;SIXTEENGUA[GEN][SHUN][LI]=4;
    SIXTEENGUA[GEN][SHUN][GEN]=5;SIXTEENGUA[GEN][SHUN][SHUN]=6;SIXTEENGUA[GEN][SHUN][KAN]=7;SIXTEENGUA[GEN][QIAN][KAN]=8;
    SIXTEENGUA[GEN][LI][KAN]=9;SIXTEENGUA[GEN][GEN][KAN]=10;SIXTEENGUA[GEN][GEN][SHUN]=11;

    SIXTEENGUA[KUN][KUN][ZHEN]=1;SIXTEENGUA[KUN][KUN][DUI]=1;SIXTEENGUA[KUN][KUN][QIAN]=1;SIXTEENGUA[KUN][ZHEN][QIAN]=1;
    SIXTEENGUA[KUN][DUI][QIAN]=1;SIXTEENGUA[KUN][KAN][QIAN]=2;SIXTEENGUA[KUN][KAN][DUI]=3;SIXTEENGUA[KUN][KAN][ZHEN]=4;
    SIXTEENGUA[KUN][KAN][KUN]=5;SIXTEENGUA[KUN][KAN][KAN]=6;SIXTEENGUA[KUN][KAN][SHUN]=7;SIXTEENGUA[KUN][DUI][SHUN]=8;
    SIXTEENGUA[KUN][ZHEN][SHUN]=9;SIXTEENGUA[KUN][KUN][SHUN]=10;SIXTEENGUA[KUN][KUN][KAN]=11;
  }
  public final static String[] HOUGUAZHAN = new String[]{
        "非十二辟卦之一。",
        "侯卦，乃一月之卦。","侯卦，乃二月之卦。","侯卦，乃三月之卦。",
        "侯卦，乃四月之卦。","侯卦，乃五月之卦。","侯卦，乃六月之卦。",
        "侯卦，乃七月之卦。","侯卦，乃八月之卦。","侯卦，乃九月之卦。",
        "侯卦，乃十月之卦。","侯卦，乃十一月之卦。","侯卦，乃十二月之卦。",
    };
  public final static String[] HOUGUAZHAN2 = new String[]{
    " ",
    "一月侯卦","二月侯卦","三月侯卦",
    "四月侯卦","五月侯卦","六月侯卦",
    "七月侯卦","八月侯卦","九月侯卦",
    "十月侯卦","十一月侯卦","十二月侯卦",
};
  public final static int[][] HOUGUA = new int[9][9];
  static
  {
    HOUGUA[KUN][QIAN]  = 1; HOUGUA[ZHEN][QIAN] = 2; HOUGUA[DUI][QIAN] = 3;
    HOUGUA[QIAN][QIAN] = 4; HOUGUA[QIAN][SHUN] = 5; HOUGUA[QIAN][GEN] = 6;
    HOUGUA[QIAN][KUN] = 7;  HOUGUA[SHUN][KUN] =  8; HOUGUA[GEN][KUN] =  9;
    HOUGUA[KUN][KUN] = 10;  HOUGUA[KUN][ZHEN] = 11; HOUGUA[KUN][DUI] = 12;
  }

  /**
   * 十天干合
   * 十二地支相冲
   * 十二地支相扶
   * 十二地支相拱
   * 十二地支六合
   * 十二地支三合
   * 十二地支三会
   * 十二地支六害
   * 十二地支相刑
   * 月建主旺相休囚
   *     月建-为月支同某地支，不再定义
   *     五行在四季中的旺相休囚，寅、卯、辰为春季，余此类推
   *     月破-为地支相冲，不再定义
   * 日辰定生旺墓绝空
   * 旬空也叫空亡
   * 六神休囚临世、持世、克世、临六亲占断
   */
  public final static int[][] TGHE = new int[11][11];
  public final static int[][] DZCHONG = new int[13][13];
  public final static int[][] DZFU = new int[13][13];
  public final static int[][] DZGONG = new int[13][13];
  public final static int[][] DZLIUHE = new int[13][13];  //地支六合
  public final static int[][] TGWUHE = new int[11][11];   //天干相合
  public final static int[][][] DZSHANHE = new int[13][13][13];  //地支三合
  public final static int[][] DZBANHE = new int[13][13];  //地支三合中的半合
  public final static int[][][] DZSHANHUI = new int[13][13][13]; //地支三会
  public final static int[][] DZHAI = new int[13][13];  //地支相害
  public final static int[][] DZXING = new int[13][13];  //地支相刑
  public final static int[][] WXSQX= new int[13][6];
  public final static int[][] SWMJ= new int[6][13];
  public final static int[][] KONGWANG = new int[11][13];

  public final static String[] WXSQXNAME = new String[]{null,"旺","相","死","囚","休"};
  public final static String[] SWMJNAME = new String[]{"不得","长生","旺","墓","绝"};

  public final static String SFGHZHAN = "生扶拱合时雨滋苗，于用神吉，于忌神凶。";
  public final static String HAIZHAN = "相害主损害之象，凡占遇之，均主不吉。婚主离异再婚，财主耗损，生财散人离……";
  public final static String XINGZHAN = "相刑，多主刑事犯法之事，也主伤灾病痛之疾。";
  public final static String ZMXINGZHAN = "子刑卯，卯刑子，为无礼之刑。";
  public final static String YINXINGSI = "寅刑巳，";
  public final static String SIXINGSHEN = "巳刑申，";
  public final static String SHENXINGYIN = "申刑寅，";
  public final static String YSSXINGZHAN = "为无恩之刑。";
  public final static String CHOUXINGWEI = "丑刑未，";
  public final static String WEIXINGXU = "未刑戌，";
  public final static String XUXINGCHOU = "戌刑丑，";
  public final static String CXWZHAN = "为恃势之刑。";
  public final static String YUEPOZHAN = "逢月破，倒霉之象，为枯根朽木，逢生不起，非死即伤。";

  public final static String[] LIUSHENFADONG = new String[] {
      null,"青龙发动，主吉庆之事，也主酒色。",
           "朱雀发动，主口舌是非、文书、信息、通讯等。",
           "勾陈发动，主田土、文章、契约之事。",
           "腾蛇发动，主虚惊怪异之事，也主牢狱之灾，有捆绑，环绕，戴手拷，拘留之象。",
           "白虎发动，主凶灾、横祸、伤病灾、牢狱之灾、血光之灾、孝服、丧事、风波等。",
           "玄武发动，主爱昧不明、隐私、盗窃等。"
  };
  public final static String[] LIUSHENCISHIGOOD = new String[] {
      null,"青龙持世旺相，主为人耿直、正义、光明磊落。",
           "朱雀持世旺相，主为人象火一样急切，热情多礼，爱说爱讲，好说好笑。",
           "勾陈持世旺相，主为人处世死板，不圆滑，自我约束力强。",
           "腾蛇持世旺相，主为人......",
           "白虎持世旺相，主为人主人性格沉稳，城府深，善于心计；测人体主肥大、体胖、面恶。",
           "玄武持世旺相，主为人主人轻浮，说话华而不实，做事没有信用，明—套暗一套。"
  };
  public final static String[] LIUSHENCISHIBAD = new String[] {
      null,"青龙持世休囚，主为人固执己见，为人死板等。",
           "朱雀持世休囚，主为人象火一样急切，热情多礼，爱说爱讲，好说好笑。",
           "勾陈持世休囚，主为人处世死板，不圆滑，自我约束力强。",
           "腾蛇持世休囚，主为人......",
           "白虎持世休囚，主为人主人性格沉稳，城府深，善于心计；测人体主肥大、体胖、面恶。",
           "玄武持世休囚，主为人主人轻浮，说话华而不实，做事没有信用，明—套暗一套。"
  };
  public final static String[] LIUSHENKESHIYONG = new String[] {
      null,"青龙克世，无妨。",
           "朱雀克世，无妨。",
           "勾陈克世，主有牢狱、拘留、审查之象。",
           "腾蛇克世，主有牢狱、拘留、审查之象。",
           "白虎克世，主凶灾、横祸、伤病灾、牢狱之灾、血光之灾、孝服、丧事、风波。",
           "玄武克世，主爱昧不明、隐私、盗窃等。"
  };
  public final static String[][] LIUSHENQIN = new String[7][6];



  static
  {
    LIUSHENQIN[QINGLONG][GUANGUI] = "青龙临官鬼爻，代表法律，官禄，正直，占絪缘为正配之夫星．家宅风水为神位．占申请为官方或海关等．";
    LIUSHENQIN[QINGLONG][FUMU] = "青龙临父母爻，代表文书，文凭，证件，法律条文，契约，官方档案，登记簿册，经文．";
    LIUSHENQIN[QINGLONG][XIONGDI] = "青龙临兄弟爻，为正直的兄弟姐妹、朋友、伙伴等。";
    LIUSHENQIN[QINGLONG][QICAI] = "青龙临妻财爻，为福禄之财、正道之财、福气等。男占婚为结发之妻、正妻。占身命为福禄之财，享福．";
    LIUSHENQIN[QINGLONG][ZISUI] = "青龙临子孙爻，为亲生子女，嫡系晚辈，为酒色，娱乐，享乐、开心等。占财获利．占事业为清闲．男占姻则吉．女占婚不利,占病可不药而愈．占官职不吉．";

    LIUSHENQIN[ZHUQUE][GUANGUI] = "朱雀临官鬼爻，主口舌官非。测风水，朱雀临官克世用为火形煞，火灾等。";
    LIUSHENQIN[ZHUQUE][FUMU] = "朱雀临父母爻，代表书信，邮寄，电报，电话，诉状、供词，演说家等。";
    LIUSHENQIN[ZHUQUE][XIONGDI] = "朱雀临兄弟爻，发动主有口舌争端，是非争斗，吵架，辨论等。";
    LIUSHENQIN[ZHUQUE][QICAI] = "朱雀临妻财爻，主动咀的、靠口挣钱的营生，男占婚，妻子能说会道或任职公关之务．";
    LIUSHENQIN[ZHUQUE][ZISUI] = "朱雀临子孙爻，主念佛，诵经，说书，唱戏，算命的，庞物为小狗，小猪，音乐歌曲，宠物雀鸟或为念诵佛咒。";

    LIUSHENQIN[GOUCHEN][GUANGUI] = "勾陈临官鬼爻，主官司，纠缠不休。占生意为熟客．女占婚为丈夫或男友．男占婚为情敌．官司主纠缠．亦为忧虑．";
    LIUSHENQIN[GOUCHEN][FUMU] = "勾陈临父母爻，占出行主劳苦，有拖累。占生产为难产。占申请为迟滞。";
    LIUSHENQIN[GOUCHEN][XIONGDI] = "勾陈临兄弟爻，主拉帮结伙，团伙。发动，主因朋友或熟人引起破财。";
    LIUSHENQIN[GOUCHEN][QICAI] = "勾陈临妻财爻，占求财为土财，房地产之财。世爻休囚，主受女人之苦。男占为妻，女友．女占姻为情敌．";
    LIUSHENQIN[GOUCHEN][ZISUI] = "勾陈临子孙爻，占工作为靠技术，手艺挣钱。";

    LIUSHENQIN[TENGSHE][GUANGUI] = "腾蛇临官鬼爻，占官司灾凶。占病为虚病、怪病。占宅主有怪异之事。女占姻为注定之夫星．";
    LIUSHENQIN[TENGSHE][FUMU] = "腾蛇临父母爻，占父母，主有怪病，怪事，虚病。主无效合同，虚假证件。";
    LIUSHENQIN[TENGSHE][XIONGDI] = "腾蛇临兄弟爻，占合伙做生意，防对方故意巧设圈套坑害自己。占朋友官司为牢狱．";
    LIUSHENQIN[TENGSHE][QICAI] = "腾蛇临妻财爻，占生童，为靠巧取、手段、机智得来之财。生意财运为注定之财．男占姻为夙缘注定之妻．";
    LIUSHENQIN[TENGSHE][ZISUI] = "腾蛇临子孙爻，子孙持世临腾蛇，主人心机多，言语狡诈，虚夸不实。占子息为命中注定之子女．亦为积德，有阴功．";

    LIUSHENQIN[BAIHU][GUANGUI] = "白虎临官鬼爻，发动克用，为血光之灾．或开刀手术．重重克用为死亡．冲用而不克者为疾病，兼克为重病．占自身，运程，疾病，孕产，家宅可用此法．";
    LIUSHENQIN[BAIHU][FUMU] = "白虎临父母爻，长辈有伤病之灾。测家宅，主有丧事孝服。";
    LIUSHENQIN[BAIHU][XIONGDI] = "白虎临兄弟爻，主平辈有病伤缠身、灾难、血光、争夺、争斗之灾。";
    LIUSHENQIN[BAIHU][QICAI] = "白虎临妻财爻，生世主暴发、得横财或得丧事白事，从事危险性之财。占自身为富而带疾或富而吝惜．男占姻为女有病．";
    LIUSHENQIN[BAIHU][ZISUI] = "白虎临子孙爻，主子女小辈有病、伤、死亡之灾，主小孩顽皮，不服管。";

    LIUSHENQIN[XUANWU][GUANGUI] = "玄武临官鬼爻，主官场上有贪污，隐私之事。占失物为盗贼，动而克用为抢劫受灾．女占为苟合";
    LIUSHENQIN[XUANWU][FUMU] = "玄武临父母爻，克世用，主文书有伪，证件有伪或长辈有伪情相暪．";
    LIUSHENQIN[XUANWU][XIONGDI] = "玄武临兄弟爻，主欺诈、欺骗，蒙蔽，测赌博必输。";
    LIUSHENQIN[XUANWU][QICAI] = "玄武临妻财爻，主赌博，贪污，受贿，走私，贩毒，偷税漏税等非法之财。男占姻为不正当女友或苟合之妇";
    LIUSHENQIN[XUANWU][ZISUI] = "玄武临子孙爻，代表享乐，不正当性行为，厕小辈主小辈有暖昧之事。";
  }



  static
  {
    TGHE[JIA] = new int[] {0, 0,0,0,0, 0,1,0,0, 0,0};
    TGHE[YI] = new int[]  {0, 0,0,0,0, 0,0,1,0, 0,0};
    TGHE[BING] = new int[]{0, 0,0,0,0, 0,0,0,1, 0,0};
    TGHE[DING] = new int[]{0, 0,0,0,0, 0,0,0,0, 1,0};
    TGHE[WUG] = new int[] {0, 0,0,0,0, 0,0,0,0, 0,1};
    TGHE[JI] = new int[]  {0, 1,0,0,0, 0,0,0,0, 0,0};
    TGHE[GENG] = new int[]{0, 0,1,0,0, 0,0,0,0, 0,0};
    TGHE[XIN] = new int[] {0, 0,0,1,0, 0,0,0,0, 0,0};
    TGHE[REN] = new int[] {0, 0,0,0,1, 0,0,0,0, 0,0};
    TGHE[GUI] = new int[] {0, 0,0,0,0, 1,0,0,0, 0,0};

    DZCHONG[ZI] = new int[]   {0,  0,0, 0,0, 0,0, 1,0, 0,0 ,0,0};
    DZCHONG[CHOU] = new int[] {0,  0,0, 0,0, 0,0, 0,1, 0,0 ,0,0};
    DZCHONG[YIN] = new int[]  {0,  0,0, 0,0, 0,0, 0,0, 1,0 ,0,0};
    DZCHONG[MAO] = new int[]  {0,  0,0, 0,0, 0,0, 0,0, 0,1 ,0,0};
    DZCHONG[CHEN] = new int[] {0,  0,0, 0,0, 0,0, 0,0, 0,0 ,1,0};
    DZCHONG[SI] = new int[]   {0,  0,0, 0,0, 0,0, 0,0, 0,0 ,0,1};
    DZCHONG[WUZ] = new int[]  {0,  1,0, 0,0, 0,0, 0,0, 0,0 ,0,0};
    DZCHONG[WEI] = new int[]  {0,  0,1, 0,0, 0,0, 0,0, 0,0 ,0,0};
    DZCHONG[SHEN] = new int[] {0,  0,0, 1,0, 0,0, 0,0, 0,0 ,0,0};
    DZCHONG[YOU] = new int[]  {0,  0,0, 0,1, 0,0, 0,0, 0,0 ,0,0};
    DZCHONG[XU] = new int[]   {0,  0,0, 0,0, 1,0, 0,0, 0,0 ,0,0};
    DZCHONG[HAI] = new int[]  {0,  0,0, 0,0, 0,1, 0,0, 0,0 ,0,0};

    DZFU[HAI][ZI] = 1;DZFU[CHOU][CHEN] = 1;DZFU[YIN][MAO] = 1;
    DZFU[SI][WUZ] = 1;DZFU[WEI][XU] = 1;   DZFU[SHEN][YOU] = 1;

    DZGONG[ZI][HAI] = 1; DZGONG[MAO][YIN] = 1; DZGONG[CHEN][CHOU] = 1;
    DZGONG[WUZ][SI] = 1; DZGONG[WEI][CHEN] = 1; DZGONG[YOU][SHEN] = 1;
    DZGONG[XU][WEI] = 1;

    DZLIUHE[ZI][CHOU] = TU;DZLIUHE[CHOU][ZI] = TU;DZLIUHE[YIN][HAI] = MU;DZLIUHE[HAI][YIN] = MU;
    DZLIUHE[MAO][XU] = HUO;DZLIUHE[XU][MAO] = HUO;DZLIUHE[YOU][CHEN] = JIN;DZLIUHE[CHEN][YOU] = JIN;
    DZLIUHE[SI][SHEN] = SHUI;DZLIUHE[SHEN][SI] = SHUI;DZLIUHE[WEI][WUZ] = TU;DZLIUHE[WUZ][WEI] = TU;

    TGWUHE[JIA][JI] = TU;TGWUHE[JI][JIA] = TU;TGWUHE[YI][GENG] = JIN;TGWUHE[GENG][YI] = JIN;
    TGWUHE[BING][XIN] = SHUI;TGWUHE[XIN][BING] = SHUI;TGWUHE[DING][REN] = MU;TGWUHE[REN][DING] = MU;
    TGWUHE[WUG][GUI] = HUO;TGWUHE[GUI][WUG] = HUO;

    DZSHANHE[YIN][WUZ][XU] = HUO;DZSHANHE[YIN][XU][WUZ] = HUO;
    DZSHANHE[WUZ][YIN][XU] = HUO;DZSHANHE[WUZ][XU][YIN] = HUO;
    DZSHANHE[XU][WUZ][YIN] = HUO;DZSHANHE[XU][YIN][WUZ] = HUO;
    DZSHANHE[SI][YOU][CHOU]=JIN; DZSHANHE[SI][CHOU][YOU]=JIN;
    DZSHANHE[YOU][SI][CHOU]=JIN; DZSHANHE[YOU][CHOU][SI]=JIN;
    DZSHANHE[CHOU][YOU][SI]=JIN; DZSHANHE[CHOU][SI][YOU]=JIN;
    DZSHANHE[SHEN][ZI][CHEN]=SHUI; DZSHANHE[SHEN][CHEN][ZI]=SHUI;
    DZSHANHE[ZI][SHEN][CHEN]=SHUI; DZSHANHE[ZI][CHEN][SHEN]=SHUI;
    DZSHANHE[CHEN][ZI][SHEN]=SHUI; DZSHANHE[CHEN][SHEN][ZI]=SHUI;
    DZSHANHE[HAI][MAO][WEI]=MU; DZSHANHE[HAI][WEI][MAO]=MU;
    DZSHANHE[MAO][HAI][WEI]=MU; DZSHANHE[MAO][WEI][HAI]=MU;
    DZSHANHE[WEI][MAO][HAI]=MU; DZSHANHE[WEI][HAI][MAO]=MU;
    
    DZBANHE[YIN]=new int[]{0,0,0,0,0,0,0,HUO,0,0,0,HUO,0};
    DZBANHE[WUZ]=new int[]{0,0,0,HUO,0,0,0,0,0,0,0,HUO,0};
    DZBANHE[XU]=new  int[]{0,0,0,HUO,0,0,0,HUO,0,0,0,0,0};
    DZBANHE[SI]=new   int[]{0,0,JIN,0,0,0,0,0,0,0,JIN,0,0};
    DZBANHE[YOU]=new  int[]{0,0,JIN,0,0,0,JIN,0,0,0,0,0,0};
    DZBANHE[CHOU]=new int[]{0,0,0,0,0,0,JIN,0,0,0,JIN,0,0};    
    DZBANHE[ZI]=new   int[]{0,0,0,0,0,SHUI,0,0,0,SHUI,0,0,0};
    DZBANHE[SHEN]=new int[]{0,SHUI,0,0,0,SHUI,0,0,0,0,0,0,0};
    DZBANHE[CHEN]=new int[]{0,SHUI,0,0,0,0,0,0,0,SHUI,0,0,0};
    DZBANHE[HAI]=new  int[]{0,0,0,0,MU,0,0,0,MU,0,0,0,0};
    DZBANHE[MAO]=new  int[]{0,0,0,0,0,0,0,0,MU,0,0,0,MU};
    DZBANHE[WEI]=new  int[]{0,0,0,0,MU,0,0,0,0,0,0,0,MU};

    DZSHANHUI[YIN][MAO][CHEN]=MU;DZSHANHUI[MAO][YIN][CHEN]=MU;DZSHANHUI[CHEN][MAO][YIN]=MU;
    DZSHANHUI[YIN][CHEN][MAO]=MU;DZSHANHUI[MAO][CHEN][YIN]=MU;DZSHANHUI[CHEN][YIN][MAO]=MU;
    DZSHANHUI[SI][WUZ][WEI]=HUO;DZSHANHUI[WEI][SI][WUZ]=HUO;DZSHANHUI[WUZ][WEI][SI]=HUO;
    DZSHANHUI[SI][WEI][WUZ]=HUO;DZSHANHUI[WEI][WUZ][SI]=HUO;DZSHANHUI[WUZ][SI][WEI]=HUO;
    DZSHANHUI[SHEN][YOU][XU]=JIN;DZSHANHUI[YOU][XU][SHEN]=JIN;DZSHANHUI[XU][YOU][SHEN]=JIN;
    DZSHANHUI[SHEN][XU][YOU]=JIN;DZSHANHUI[YOU][SHEN][XU]=JIN;DZSHANHUI[XU][SHEN][YOU]=JIN;
    DZSHANHUI[HAI][ZI][CHOU]=SHUI;DZSHANHUI[ZI][HAI][CHOU]=SHUI;DZSHANHUI[CHOU][HAI][ZI]=SHUI;
    DZSHANHUI[HAI][CHOU][ZI]=SHUI;DZSHANHUI[ZI][CHOU][HAI]=SHUI;DZSHANHUI[CHOU][ZI][HAI]=SHUI;

    DZHAI[ZI][WEI] = 1; DZHAI[WEI][ZI] = 1; DZHAI[CHOU][WUZ] = 1; DZHAI[WUZ][CHOU] = 1;
    DZHAI[YIN][SI] = 1; DZHAI[SI][YIN] = 1; DZHAI[MAO][CHEN] = 1; DZHAI[CHEN][MAO] = 1;
    DZHAI[SHEN][HAI] = 1; DZHAI[HAI][SHEN] = 1; DZHAI[YOU][XU] = 1; DZHAI[XU][YOU] = 1;

    DZXING[ZI][MAO] = 1; DZXING[MAO][ZI] = 1;
    DZXING[YIN][SI] = 1;DZXING[SI][SHEN] = 1;DZXING[SHEN][YIN] = 1;
    DZXING[CHOU][WEI] = 1;DZXING[WEI][XU] = 1;DZXING[XU][CHOU] = 1;
    DZXING[CHEN][CHEN] = DZXING[WUZ][WUZ] = DZXING[YOU][YOU] = DZXING[HAI][HAI] = 1;

    //旺1相2死3囚4休5, 金1木2水3火4土5
    WXSQX[YIN] = WXSQX[MAO] = new int[]{0, 4,1,5,2,3}; 
    WXSQX[SI] = WXSQX[WUZ] = new int[]{0, 3,5,4,1,2}; 
    WXSQX[SHEN] = WXSQX[YOU] = new int[]{0, 1,3,2,4,5}; 
    WXSQX[HAI] = WXSQX[ZI] = new int[]{0,5,2,1,3,4}; 
    WXSQX[CHEN] = WXSQX[WEI] = WXSQX[XU] = WXSQX[CHOU] = new int[]{0,2,4,3,5,1};

    SWMJ[JIN][SI] = SHENGRJVALUE;SWMJ[JIN][YOU] = WANGRJVALUE;SWMJ[JIN][CHOU] = MURJVALUE;SWMJ[JIN][YIN] = JUERJVALUE;
    SWMJ[MU][HAI] = SHENGRJVALUE;SWMJ[MU][MAO] = WANGRJVALUE;SWMJ[MU][WEI] = MURJVALUE;SWMJ[MU][SHEN] = JUERJVALUE;
    SWMJ[HUO][YIN] = SHENGRJVALUE;SWMJ[HUO][WUZ] = WANGRJVALUE;SWMJ[HUO][XU] = MURJVALUE;SWMJ[HUO][HAI] = JUERJVALUE;
    SWMJ[SHUI][SHEN] = SHENGRJVALUE;SWMJ[SHUI][ZI] = WANGRJVALUE;SWMJ[SHUI][CHEN] = MURJVALUE;SWMJ[SHUI][SI] = JUERJVALUE;
    SWMJ[TU] = SWMJ[SHUI];

    KONGWANG[JIA][ZI]=KONGWANG[YI][CHOU]=KONGWANG[BING][YIN]=KONGWANG[DING][MAO]=KONGWANG[WUG][CHEN]=KONGWANG[JI][SI]=KONGWANG[GENG][WUZ]=KONGWANG[XIN][WEI]=KONGWANG[REN][SHEN]=KONGWANG[GUI][YOU]=XU*100+HAI;
    KONGWANG[JIA][YIN]=KONGWANG[YI][MAO]=KONGWANG[BING][CHEN]=KONGWANG[DING][SI]=KONGWANG[WUG][WUZ]=KONGWANG[JI][WEI]=KONGWANG[GENG][SHEN]=KONGWANG[XIN][YOU]=KONGWANG[REN][XU]=KONGWANG[GUI][HAI]=ZI*100+CHOU;
    KONGWANG[JIA][CHEN]=KONGWANG[YI][SI]=KONGWANG[BING][WUZ]=KONGWANG[DING][WEI]=KONGWANG[WUG][SHEN]=KONGWANG[JI][YOU]=KONGWANG[GENG][XU]=KONGWANG[XIN][HAI]=KONGWANG[REN][ZI]=KONGWANG[GUI][CHOU]=YIN*100+MAO;
    KONGWANG[JIA][WUZ]=KONGWANG[YI][WEI]=KONGWANG[BING][SHEN]=KONGWANG[DING][YOU]=KONGWANG[WUG][XU]=KONGWANG[JI][HAI]=KONGWANG[GENG][ZI]=KONGWANG[XIN][CHOU]=KONGWANG[REN][YIN]=KONGWANG[GUI][MAO]=CHEN*100+SI;
    KONGWANG[JIA][SHEN]=KONGWANG[YI][YOU]=KONGWANG[BING][XU]=KONGWANG[DING][HAI]=KONGWANG[WUG][ZI]=KONGWANG[JI][CHOU]=KONGWANG[GENG][YIN]=KONGWANG[XIN][MAO]=KONGWANG[REN][CHEN]=KONGWANG[GUI][SI]=WUZ*100+WEI;
    KONGWANG[JIA][XU]=KONGWANG[YI][HAI]=KONGWANG[BING][ZI]=KONGWANG[DING][CHOU]=KONGWANG[WUG][YIN]=KONGWANG[JI][MAO]=KONGWANG[GENG][CHEN]=KONGWANG[XIN][SI]=KONGWANG[REN][WUZ]=KONGWANG[GUI][WEI]=SHEN*100+YOU;
  }


  /**
   * 用神取
   */
  public final static String[] YONGSHEN = new String[] {
      "世为之，以自占吉凶。",
      "兄弟为之，以占兄弟、朋友，同事等同辈人事。",
      "子孙为之，以占儿女、子孙，忠臣、良将、医生、医药、兵卒、僧道、禽畜等。",
      "妻财为之，以占男人之妻及妻同辈、女友，钱、财、粮食、器皿等为我驱使之物。",
      "官鬼为之，以占女人之夫及夫兄弟、朋友，功名、工作、公事、官吏、乱臣、盗贼、邪祟、鬼神等拘束我之物。",
      "父母为之，以占父母辈及以上的亲人、师长，天地、城池、住宅、舟车、出行、衣服、雨具、文书、契约等庇护我之物。"
  };

  /**
   * 各类断辞
   */
  public final static String FLSELF = "\n反呤占：\n";
  public final static String GUAFLSELF = "主卦与变卦相克，有卦反呤；";
  public final static String YAOFLSELF = "动爻变冲克，有爻反呤；";
  public final static String FLZHANSELF = "反吟主事有反复。";
  public final static String WUFLSELF = "无卦反吟与爻反呤。";

  public final static String FULSELF = "\n伏呤占：\n";
  public final static String GUAFULWSELF = "内卦卦变地支同，有内卦伏呤；";
  public final static String GUAFULNSELF = "外卦卦变地支同，有外卦伏呤；";
  public final static String FULZHANSELF = "伏吟主呻吟或哭泣，遇之不吉，内卦伏吟内不适，外卦伏吟外不顺。";
  public final static String WUFULSELF = "无内卦伏吟或外卦伏吟。";


  public final static String LYDONGSELF = "六爻皆动，取彖词。";
  public final static String LYJINGSELF = "六爻安静，取卦词。";

  public final static String YUEGUASHENSELF = "\n月卦身：\n";
  public final static String SHISHENSELF = "\n世身：\n";

  public final static String GUACISELF = "\n卦词：\n";
  public final static String YAOCISELF = "\n爻辞：\n";

  public final static String FUFEISELF = "\n伏飞占：\n";
  public final static String FUKFEISELF = "爻逢伏克飞无事，伏克飞神为除暴。";
  public final static String FUSFEISELF = "伏去生飞名泄气。";
  public final static String FEISFUSELF = "飞来生伏得长生。";
  public final static String FEIKFUSELF = "飞来克伏反伤身。";
  public final static String FEIFUWUSELF = "本卦无飞伏。";
  public final static String FEIFUHESELF = "飞伏比和。";

  public final static String GONGZHUGUASELF = "宫主卦《";
  public final static String GONGHUGUASELF = "宫互卦《";
  public final static String GONGBIANGUASELF = "宫变卦《";

  public final static String JTSELF = "\n进神退神占\n";
  public final static String JTZHANSELF = "进神者，表示事物不断向前发展。退神者，是事物变为倒退。有喜忌祸福之分，吉神宜遇化进，凶神宜遇化退。";
  public final static String JSSELF = "动爻化成进神。";
  public final static String TSSELF = "动爻化成退神。";
  public final static String JTWUSELF = "动爻没有化成进退神。";

  public final static String CHSELF = "\n六冲六合卦占\n";
  public final static String CHZHANSELF = "六冲卦主其事必散如近病逄冲即愈，久病逄冲即死；六合卦凡谋易就，久远和同如近病逄六合即死，久病逄六合则愈。";
  public final static String LCSELF = "为六冲卦。";
  public final static String LHSELF = "为六合卦。";
  public final static String CHWUSELF = "非六冲六合卦。";

  public final static String JINGFANGGUASELF = "\n十六变卦占\n";
  public final static String JINGFANGWUSELF = "变卦并非京房十六变卦之一。";
  public final static String WUBIANGUASELF = "六爻安静，无变卦。";

  public final static String HOUGUASELF = "\n十二辟卦占\n";
  public final static String LIUYAOSELF = "\n六爻占\n";
  public final static String SHIYAOSELF = "\n世爻占\n";
  public final static String YONGSHENSELF = "\n用神占\n";
  public final static String YUANSHENSELF = "\n原神占\n";
  public final static String JISHENSELF = "\n忌神占\n";
  public final static String CHUSHENSELF = "\n仇神占\n";

  public final static String LIUSHENSELF = "\n六神占\n";
}
