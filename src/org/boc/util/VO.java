package org.boc.util;

public class VO implements java.io.Serializable{
  private int ng;  //输入的时间、当前时间、八字的年干
  private int nz;  //输入的时间、当前时间、八字的年支
  private int yg;  //输入的时间、当前时间、八字的的月干
  private int yz;
  private int rg;
  private int rz;
  private int sg;
  private int sz;
  private int year;  //年份
  private int month;
  private int day;
  private int hour;
  private int minute;
  private int second;
  private boolean isBoy;  //是否男命
  private boolean isYin;  //是否阴历
  private boolean isYun;  //是否闰月
  private String rowId;   //文件ID号
  private String fileId;  //树上节点名
  private String root;    //树的根节点名
  private String parent;  //
  private String ycsj;    //预测时间
  private String memo;    //备注
  private String dw;      //大门方位
  private String zw;      //座向
  private String addr;    //地址
  private String addr2;
  private String code;    //邮编
  private String tel;
  private String phone;
  private String email;
  private String qq;
  private String msn;
  private String qt1;   //其它1
  private String qt2;
  private int isheng;   //省
  private int ishi;    //市
  private int iqt1;    //其它1
  private int iqt2;
  private boolean bqt1;
  private boolean bqt2;
  private String name;  //姓名 

  public VO() {
  }

  public VO(int year, int month, int day, int hour, int minute,
            boolean isBoy, boolean isYin, boolean isYun,
            int isheng, int ishi) {
    this.year=year;
    this.month=month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.isBoy = isBoy;
    this.isYin = isYin;
    this.isYun = isYun;
    this.isheng=isheng;
    this.ishi = ishi;
  }

  public VO(int ng, int nz, int yg, int yz, int rg, int rz, int sg, int sz,
            boolean isBoy, boolean isYin, boolean isYun,
            int isheng, int ishi) {
    this.ng=ng;
    this.nz=nz;
    this.yg = yg;
    this.yz = yz;
    this.rg = rg;
    this.rz = rz;
    this.sg = sg;
    this.sz = sz;
    this.isBoy = isBoy;
    this.isYin = isYin;
    this.isYun = isYun;
    this.isheng=isheng;
    this.ishi = ishi;
  }
  public int getNg() {
    return ng;
  }
  public void setNg(int ng) {
    this.ng = ng;
  }
  public int getNz() {
    return nz;
  }
  public void setNz(int nz) {
    this.nz = nz;
  }
  public int getYg() {
    return yg;
  }
  public void setYg(int yg) {
    this.yg = yg;
  }
  public int getYz() {
    return yz;
  }
  public void setYz(int yz) {
    this.yz = yz;
  }
  public int getRg() {
    return rg;
  }
  public void setRg(int rg) {
    this.rg = rg;
  }
  public int getRz() {
    return rz;
  }
  public void setRz(int rz) {
    this.rz = rz;
  }
  public int getSg() {
    return sg;
  }
  public void setSg(int sg) {
    this.sg = sg;
  }
  public int getSz() {
    return sz;
  }
  public void setSz(int sz) {
    this.sz = sz;
  }
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getMonth() {
    return month;
  }
  public void setMonth(int month) {
    this.month = month;
  }
  public int getDay() {
    return day;
  }
  public void setDay(int day) {
    this.day = day;
  }
  public int getHour() {
    return hour;
  }
  public void setHour(int hour) {
    this.hour = hour;
  }
  public int getMinute() {
    return minute;
  }
  public void setMinute(int minute) {
    this.minute = minute;
  }
  public int getSecond() {
    return second;
  }
  public void setSecond(int second) {
    this.second = second;
  }
  public boolean isIsBoy() {
    return isBoy;
  }
  public void setIsBoy(boolean isBoy) {
    this.isBoy = isBoy;
  }
  public boolean isIsYin() {
    return isYin;
  }
  public void setIsYin(boolean isYin) {
    this.isYin = isYin;
  }
  public boolean isIsYun() {
    return isYun;
  }
  public void setIsYun(boolean isYun) {
    this.isYun = isYun;
  }

  public String getRowId() {
    return rowId;
  }
  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getFileId() {
    return fileId;
  }
  public void setFileId(String fileId) {
    this.fileId = fileId;
  }
  public String getRoot() {
    return root;
  }
  public void setRoot(String root) {
    this.root = root;
  }
  public String getParent() {
    return parent;
  }
  public void setParent(String parent) {
    this.parent = parent;
  }
  public String getYcsj() {
    return ycsj;
  }
  public void setYcsj(String ycsj) {
    this.ycsj = ycsj;
  }
  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }
  public int getIsheng() {
    return isheng;
  }
  public void setIsheng(int isheng) {
    this.isheng = isheng;
  }
  public int getIshi() {
    return ishi;
  }
  public void setIshi(int ishi) {
    this.ishi = ishi;
  }

  public String getDw() {
    return dw;
  }
  public void setDw(String dw) {
    this.dw = dw;
  }
  public String getZw() {
    return zw;
  }
  public void setZw(String zw) {
    this.zw = zw;
  }
  public String getAddr() {
    return addr;
  }
  public void setAddr(String addr) {
    this.addr = addr;
  }
  public String getAddr2() {
    return addr2;
  }
  public void setAddr2(String addr2) {
    this.addr2 = addr2;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getQq() {
    return qq;
  }
  public void setQq(String qq) {
    this.qq = qq;
  }
  public String getMsn() {
    return msn;
  }
  public void setMsn(String msn) {
    this.msn = msn;
  }
  public String getQt1() {
    return qt1;
  }
  public void setQt1(String qt1) {
    this.qt1 = qt1;
  }
  public String getQt2() {
    return qt2;
  }
  public void setQt2(String qt2) {
    this.qt2 = qt2;
  }
  public int getIqt1() {
    return iqt1;
  }
  public void setIqt1(int iqt1) {
    this.iqt1 = iqt1;
  }
  public int getIqt2() {
    return iqt2;
  }
  public void setIqt2(int iqt2) {
    this.iqt2 = iqt2;
  }
  public boolean isBqt1() {
    return bqt1;
  }
  public void setBqt1(boolean bqt1) {
    this.bqt1 = bqt1;
  }
  public boolean isBqt2() {
    return bqt2;
  }
  public void setBqt2(boolean bqt2) {
    this.bqt2 = bqt2;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

}
