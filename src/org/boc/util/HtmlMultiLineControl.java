package org.boc.util;

public class HtmlMultiLineControl {

	private String strDest;
	// 上文
	private final String getUp(int fontSize) {
		return "<html><bgcolor color=#000080><font size="+fontSize+" color=#0000cd ><B>" ;
	}
	private final String getUp() {
		return getUp(5);
	}
	// 下文
	private final String getDown() {
		return "</B></font></bgcolor></html>";
	}

	public String CovertDestionString(String strDest) {
		if(strDest==null || (strDest!=null && strDest.trim().equalsIgnoreCase("null"))) return null;
		strDest = strDest.replaceAll("\r\n", "<BR>");
		this.strDest = getUp() + strDest + getDown();
		return this.strDest;
	}
	public String CovertDestionString(String strDest,int fontSize) {
		if(strDest==null || (strDest!=null && strDest.trim().equalsIgnoreCase("null"))) return null;
		strDest = strDest.replaceAll("\r\n", "<BR>");
		this.strDest = getUp(fontSize) + strDest + getDown();
		return this.strDest;
	}
}
