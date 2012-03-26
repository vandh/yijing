package org.boc.delegate;

import java.io.PrintWriter;

import org.boc.dao.xk.DaoFS_Xkfx;
import org.boc.util.Messages;

public class DelFs_Xkfx {
 private StringBuffer str;
 private PrintWriter pw;
 private DaoFS_Xkfx xk;

 public DelFs_Xkfx() {
   str = new StringBuffer();
   pw = DelLog.getLogObject();
   xk = new DaoFS_Xkfx();
 }

 /**
  * 详细推断信息
  * @return
  */
 public String getInfo(int y3, int iJy,int iwx,int[] n4, int[] mz,
                               int[] syfx, boolean[] yyyun) {
   str.delete(0,str.length());
   str.append("\r\n    ");
   str.append(xk.getHead(y3,iJy,iwx,n4,mz,syfx,yyyun));
   str.append("\r\n");
   str.append(xk.analyse(y3,iJy,iwx,n4,mz,syfx,yyyun));

   //Debug.out(str.toString());
   return str.toString();
 }

 /**
  * 由时间排盘
  * @return
  */
 public String pp(int y3, int iJy,int iwx,int[] n4, int[] mz,
                               int[] syfx, boolean[] yyyun) {
   str.delete(0,str.length());
   str.append("\r\n    ");
   str.append(xk.getHead(y3,iJy,iwx,n4,mz,syfx,yyyun));
   str.append("\r\n");
   str.append(xk.pp(y3,iJy,iwx,n4,mz,syfx,yyyun));
   str.append("\r\n");

   //Debug.out(str.toString());
   return str.toString();
 }

 public String zheri(int y3, int iJy,int iwx,int[] n4, int[] mz,
                     int[] syfx, boolean[] yyyun) {
   try{
     str.delete(0, str.length());
     str.append("\r\n    ");
     str.append(xk.getZrHead(syfx, yyyun));
     str.append("\r\n");
     str.append(xk.wtjPP(syfx, yyyun[4], yyyun[1]));
     str.append("\r\n");
     //Debug.out(str.toString());
   }catch(Exception ex) {
     ex.printStackTrace();
     Messages.error(ex.toString());
   }

   return str.toString();
 }

 public static void main(String[] args) {
   DelFs_Xkfx del = new DelFs_Xkfx();
   //del.pp(0,6,1,null,null,null,new boolean[]{false,false,false,false,false});
   //del.zheri(0,6,1,null,null,new int[]{0,1977,2,23,6,0,30},new boolean[]{false,true,false,false,false});
   del.pp(1988, 0, 1, null,null,null,
                             new boolean[]{false,false,false,false,false});

 }
}
