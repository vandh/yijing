package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.bz.DaoFS_BaZhai;

public class DelFs_BaZhai {
 private StringBuffer str;
 private PrintWriter pw;
 private DaoFS_BaZhai bz;

 public DelFs_BaZhai() {
   str = new StringBuffer();
   pw = DelLog.getLogObject();
   bz = new DaoFS_BaZhai();
 }

 /**
  * ”… ±º‰≈≈≈Ã
  * @return
  */
 public String getBaZhaiInfo(int boy, int girl, int sex, int[] hj,int wuxiang,
                             int damen ,int menxiang, int cesuo, int jiuzhai,
                             int woshi, int fangmen,  int chuang,
                             int chufang, int zaowei, int zaoxiang) {
   int year = 0;
   int year2 = 0;
   boolean b = false;

   if(sex==1) {
     year = boy;
     year2 = girl;
     b = true;
   }else{
     year2 = boy;
     year = girl;
   }

   str.delete(0,str.length());
   str.append("\r\n    ");
   str.append(bz.getHead(year,b, wuxiang));
   str.append("\r\n");
   str.append(bz.analyse(year, year2, b ,hj, wuxiang,
                         damen, menxiang, cesuo , jiuzhai,
                         woshi, fangmen, chuang,
                         chufang, zaowei, zaoxiang));

   //Debug.out(str.toString());
   return str.toString();
 }

 public String getBaZhaYxp(int boy, int girl, int sex, int[] hj,int wuxiang,
                             int damen ,int menxiang, int cesuo, int jiuzhai,
                             int woshi, int fangmen,  int chuang,
                             int chufang, int zaowei, int zaoxiang) {
   int year = 0;
   int year2 = 0;
   boolean b = false;

   if(sex==1) {
     year = boy;
     year2 = girl;
     b = true;
   }else{
     year2 = boy;
     year = girl;
   }

   str.delete(0,str.length());
   str.append("\r\n    ");
   str.append(bz.getHead(year,b, wuxiang));
   str.append("\r\n");
   str.append(bz.pp(year, b, wuxiang));
   str.append("\r\n");

   //Debug.out(str.toString());
   return str.toString();
 }


 public static void main(String[] args) {
   DelFs_BaZhai del = new DelFs_BaZhai();
   del.getBaZhaiInfo(1977,1981,1,new int[]{1,2,3,4,5},2,3,4,5,6,7,8,1,2,3,4);
 }
}
