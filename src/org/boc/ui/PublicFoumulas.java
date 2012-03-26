package org.boc.ui;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.db.qm.QiMen;
import org.boc.util.PrintWriter;

public class PublicFoumulas {
	public static void print(PrintWriter pw) throws Exception {

	}

	public static void printYima(PrintWriter pw) throws Exception {
		pw.println("      驿马",PrintWriter.BRED);
		pw.newLine();
		pw.println("申子辰马在寅；",PrintWriter.BBLUE);
		pw.println("寅午戌马在申；",PrintWriter.BBLUE);
		pw.println("巳酉丑马在亥；",PrintWriter.BBLUE);
		pw.println("亥卯未马在巳。",PrintWriter.BBLUE);
	}

	public static void printRsqsf(PrintWriter pw) throws Exception {
		pw.println("       日上起时法",PrintWriter.BRED);
		pw.newLine();
	  pw.println("甲己还加甲，乙庚丙作初。",PrintWriter.BBLUE);
	  pw.println("丙辛从戊起，丁壬庚子居。",PrintWriter.BBLUE);
	  pw.println("戊癸何方发，壬子是真途。",PrintWriter.BBLUE);
	}

	public static void printNayin(PrintWriter pw) throws Exception {
		pw.bblack("                六十甲子纳音表");
		pw.newLine();
		pw.newLine();
		int index = 0,i=0,j=0;
		while(++index <= 60) {
			i = (++i)%11==0 ? 1 : i;
			j = (++j)%13==0 ? 1 : j;			
			
			for(int k=0; k<4; k++) {
				pw.mpink((1863+k*60+index)+"  ");
			}
			pw.mblue("     ");
			pw.mblue(YiJing.TIANGANNAME[i]+YiJing.DIZINAME[j]);
			pw.mred("          "+SiZhu.NAYIN[i][j]);
			pw.newLine();		
		}
	}

	public static void printSgkyb(PrintWriter pw) throws Exception {
		pw.bblack("                  十干克应表");
		pw.newLine();
		pw.newLine();
		int ge = 0;
		for(int i=2; i<=10; i++) {
			pw.bblack("                      ");
			pw.bred(YiJing.TIANGANNAME[i]); //+"加"+YiJing.TIANGANNAME[j]);
			pw.newLine();
			for(int j=2; j<=10; j++) {
				ge = QiMen.gan_gan[i][j];					
				pw.sblue(QiMen.gGejuDesc[ge][0]+":"+QiMen.gGejuDesc[ge][1].replace(QiMen.HH, ""));
				pw.newLine();
			}
			pw.newLine();
		}
	}

	public static void printNsqyf(PrintWriter pw) throws Exception {
		pw.println("          年上起月法", PrintWriter.BRED);
		pw.newLine();
		pw.println("甲己之年丙作首，乙庚之年戊为头。", PrintWriter.BBLUE);
		pw.println("丙辛之岁寻庚上，丁壬壬寅顺水流。", PrintWriter.BBLUE);
		pw.println("若问戊癸何处起，甲寅之上好追求。", PrintWriter.BBLUE);
	}

	public static void printShiTianganSWSJB(PrintWriter pw) throws Exception {
		pw.bblack("        十天干生旺死绝表");
		pw.newLine();
		pw.newLine();
		pw.println("┏━━━━━┯━┯━┯━┯━┯━┯━┯━┯━┯━┯━┯━┯━┓", PrintWriter.SBLUE, false);
		pw.println("┃五行  状态│长│沐│冠│临│帝│衰│病│死│墓│绝│胎│养┃", PrintWriter.SRED, false);
		pw.println("┠──┬──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃    │ 甲 │亥│子│丑│寅│卯│辰│巳│午│未│申│酉│戌┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃ 五 │ 丙 │寅│卯│辰│巳│午│未│申│酉│戌│亥│子│丑┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃ 阳 │ 戊 │寅│卯│辰│巳│午│未│申│酉│戌│亥│子│丑┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃ 干 │ 庚 │巳│午│未│申│酉│戌│亥│子│丑│寅│卯│辰┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃    │ 壬 │申│酉│戌│亥│子│丑│寅│卯│辰│巳│午│未┃", PrintWriter.SBLACK, false);
		pw.println("┠──┼──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃    │ 乙 │午│巳│辰│卯│寅│丑│子│亥│戌│酉│申│未┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃ 五 │ 丁 │酉│申│未│午│巳│辰│卯│寅│丑│子│亥│戌┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃ 阴 │ 己 │酉│申│未│午│巳│辰│卯│寅│丑│子│亥│戌┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃ 干 │ 辛 │子│亥│戌│酉│申│未│午│巳│辰│卯│寅│丑┃", PrintWriter.SBLACK, false);
		pw.println("┃    ├──┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┼─┨", PrintWriter.SBLUE, false);
		pw.println("┃    │ 癸 │卯│寅│丑│子│亥│戌│酉│申│未│午│巳│辰┃", PrintWriter.SBLACK, false);
		pw.println("┗━━┷━━┷━┷━┷━┷━┷━┷━┷━┷━┷━┷━┷━┷━┛", PrintWriter.SBLUE, false);
		pw.newLine();
	}
}
