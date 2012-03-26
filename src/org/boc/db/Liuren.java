package org.boc.db;

public class Liuren {
  //十干寄宫
  /**
   甲----寅
   乙----辰
   丙----巳
   丁----未
   戊----巳
   己----未
   庚----申
   辛----戌
   壬----亥
   癸----丑
   */
  public static final int[] gan2g = {0,3,5,6,8,6,8,9,11,12,2};
  //十宫对应之干
  public static final int[] gong2g = {0,0,10,1,0,2,305,0,406,7,0,8,9};
  //月将
  /**
   雨水2-----3 亥将
   春分4-----5 戌将
   谷雨6-----7 酉将
   小满8-----9 申将
   夏至10-----11 未将
   大暑12-----13 午将
   处暑14-----15 巳将
   秋分16-----17 辰将
   霜降18-----19 卯将
   小雪20-----21 寅将
   冬至22-----23 丑将
   大寒24-----1 子将
   */
      public static final int[] yuej = {0, 1, 12,12, 11,11, 10,10, 9,9, 8,8, 7,7,
      6,6, 5,5, 4,4, 3,3, 2,2, 1};
  //干阴阳、支阴阳 1=阳 0=阴
  public static final int[] gyy = {0, 1,0,1,0,1,0,1,0,1,0};
  public static final int[] zyy = {0, 1,0,1,0,1,0,1,0,1,0,1,0};
  //得到干六合之其它干
  public static final int[] g6h = {0, 6,7,8,9,10,1,2,3,4,5};
  public static final int[] zadd4 = {0, 5,6,7,8,9,10,11,12,1,2,3,4};
  //三刑，如为自刑则冲
  public static final int[] xing3 = {0, 4,11,6,1, 11,9,1,2, 3,4,8,6};
  //十二贵人，也是1-12的序号
  public static final String[] tjname = {"","贵","蛇","雀","合","勾","龙","空","虎","常","玄","阴","后"};
  //取贵人0暮阴1旦阳
  public static final int[] gui00 = {0, 2,1,12,10, 8,9,8,7, 6,4};
  public static final int[] gui01 = {0, 8,9,10,12, 2,1,2,3, 4,6};
  public static final int[] gui10 = {0, 8,9,10,10, 8,9,7,7, 6,6};
  public static final int[] gui11 = {0, 2,1,12,12, 2,1,3,3, 4,4};
  public static final int[] gui20 = {0, 8,9,10,10, 8,9,8,3, 4,4};
  public static final int[] gui21 = {0, 2,1,12,12, 2,1,2,7, 6,6};
}