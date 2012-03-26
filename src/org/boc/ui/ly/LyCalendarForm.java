package org.boc.ui.ly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.boc.calendar.ui.MiniCalendarForm;
import org.boc.ui.UIPublic;
import org.boc.util.Messages;

/**
 * 具体的万年历界面
 * 改进：
 * 1）点击当前时间切换到现在；
 * 2）点击干支历，显示干支录入方式；
 * 3）双击某日，隐藏对话框; 
 * 4)添加小时和分钟
 */
public class LyCalendarForm extends MiniCalendarForm {
	private LiuYaoFrame frame;   //父窗口

	public LyCalendarForm() {		
		super();
	}
	
	/**
	 * 初始化时皆为全干、全支，防止双击树叶子时找到支的现象
	 */
	public void initGanzi() {			
		this.buttonGanzi = UIPublic.getInitImageButton("yes","yes","干支排盘",new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				//必须放到这里更新，防止第一次不选就直接起局.此处在下位框值更新时也必须调用，否则点击保存时就不会有值.
	      updateFrameganzi();
				frame.pan();
			}
		});
		super.initGanzi();
	}

	/**
	 * 重新起局排盘
	 * 为防止重复触发起局事件，需要判断frame的年月日时与日历面板选中值不一样才需要更新
	 * 0：更新frame中所有的年月日时分值，然后重新起局；
	 * 1，2，3，4：分别为年、月、日、时；如果相同则不用更新和起局，否则要更新frame的值并要重新起局；
	 * 5：为分，需要更新frame的值，不需要起局；
	 * 当双击叶子全部更新年月日时，或从干支切换到年月日时，也需要触发所有的年月日时，真是麻烦
	 */
	public void reDo(int type) {
		if(frame == null) return;
		
		int iyear = model.getSelectYear(); 
		int ihour = Integer.valueOf(this.hf.getText());
		int iminute = Integer.valueOf(this.mf.getText());
		int iday = this.model.getSelectDay();
		int imonth = this.model.getSelectMonth()+1; 
		
		//System.out.println("type="+type+";iyear="+iyear+";imonth="+imonth+";iday="+iday+";ihour="+ihour+";frame.year="+frame.getYear()+";frame.month="+frame.getMonth()+";frame.day="+frame.getDay()+";frame.hour="+frame.getHour());
		//如果是阴历，不管是否是，一律按阳历录入
		if(type==5) {  //如果为分钟，则直接更新值后返回
			frame.setMinute(iminute);
			return;
		}else if(type==4 && frame.getHour()==ihour) { //小时相同则不用更新和起局，否则都要
			return;
		}else if(type==3 && frame.getDay()==iday) { //日子相同则不用更新和起局，否则都要
			return;
		}else if(type==2 && frame.getMonth()==imonth) { //月份相同则不用更新和起局，否则都要
			//if(frame.getYear()*frame.getMonth()*frame.getDay()==0) return;
			return;
		}else if(type==1 && frame.getYear()==iyear) { //年份相同则不用更新和起局，否则都要
			return;
		}
		if(iyear<org.boc.db.Calendar.IYEAR || iyear >org.boc.db.Calendar.MAXYEAR) {
			Messages.info("目前支持年份在"+org.boc.db.Calendar.IYEAR+"~"+org.boc.db.Calendar.MAXYEAR+"之间！");
			return;
		}
		frame.setInputParameter(iyear, imonth, iday, ihour, iminute, true, false);
		frame.setInputGanzi(0, 0, 0, 0, 0, 0, 0, 0);
		frame.pan();
	}
		
	public void setParent(JPanel frame) {
		this.frame = (LiuYaoFrame)frame;
	}
	
	public void updateFrameganzi() {
		int ing = ng.getSelectedIndex();
    int iyg = yg.getSelectedIndex();
    int irg = rg.getSelectedIndex();
    int isg = sg.getSelectedIndex();
    int inz = ing%2==1 ? (nz.getSelectedIndex()-1)*2+1 : nz.getSelectedIndex()*2;
    int iyz = iyg%2==1 ? (yz.getSelectedIndex()-1)*2+1 : (yz.getSelectedIndex()*2);
    int irz = irg%2==1 ? (rz.getSelectedIndex()-1)*2+1 : (rz.getSelectedIndex()*2);
    int isz = isg%2==1 ? (sz.getSelectedIndex()-1)*2+1 : (sz.getSelectedIndex()*2);
    if(frame==null) return;
    frame.setInputGanzi(ing, inz,	iyg, iyz,	irg, irz,	isg, isz);
    frame.setInputParameter(0, 0, 0, 0, 0, false, false);
	}
	
	public static void main(String[] args) {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		LyCalendarForm form = new LyCalendarForm();
		JFrame frame = new JFrame();
		frame.getContentPane().add(form);
		frame.setSize(500,500);
		frame.setVisible(true);
	}
}