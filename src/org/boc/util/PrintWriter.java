package org.boc.util;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class PrintWriter {
	public static final int SMALL = 12;  //小号字体
	public static final int MID = 16;    //中号字体
	public static final int BIG = 21;    //大号字体
	public static final int BRED = 11;
	public static final int MRED = 12;
	public static final int SRED = 13;
	
	public static final int BWHITE = 21;
	public static final int MWHITE = 22;
	public static final int SWHITE = 23;
	
	public static final int BBLACK = 31;
	public static final int MBLACK = 32;
	public static final int SBLACK = 33;
	
	public static final int BBLUE = 41;
	public static final int MBLUE = 42;
	public static final int SBLUE = 43;
	
	public static final int BPINK = 51;
	public static final int MPINK = 52;
	public static final int SPINK = 53;

	SimpleAttributeSet smallblack;  //小黑色字体
	SimpleAttributeSet midblack;    //中黑色字体
	SimpleAttributeSet bigblack;    //大黑色字体加粗
	
	SimpleAttributeSet smallred;    //小红色字体
	SimpleAttributeSet midred;      //中红色字体加粗
	SimpleAttributeSet bigred;      //大红色字体加粗
	
	SimpleAttributeSet smallwhite;  //小白色字体
	SimpleAttributeSet bigwhite;   	//大白色字体
	
	SimpleAttributeSet smallblue;   //小蓝色字体
	SimpleAttributeSet midblue;   	//中蓝色字体
	SimpleAttributeSet bigblue;   	//大蓝色字体
	
	SimpleAttributeSet smallpink;   //水红色小字体
	SimpleAttributeSet midpink;   //水红色中字体
	SimpleAttributeSet bigpink;   //水红色大字体
	
	Document doc ;					 //显示结构的文档
	
	public PrintWriter() {		
		smallblack = new SimpleAttributeSet();//MAGENTA ORANGE
		StyleConstants.setForeground(smallblack, Color.BLACK);
		StyleConstants.setFontSize(smallblack, SMALL);
		
		midblack = new SimpleAttributeSet();
		StyleConstants.setForeground(midblack, Color.BLACK);
		StyleConstants.setBold(midblack, true);
		StyleConstants.setFontSize(midblack, MID);
		
		bigblack = new SimpleAttributeSet();
		StyleConstants.setForeground(bigblack, Color.BLACK);
		StyleConstants.setBold(bigblack, true);
		StyleConstants.setFontSize(bigblack, BIG);
		
		smallred = new SimpleAttributeSet();
		StyleConstants.setForeground(smallred, Color.RED);  			
		StyleConstants.setFontSize(smallred, SMALL);
		
		midred = new SimpleAttributeSet();
		StyleConstants.setForeground(midred, Color.RED);
		StyleConstants.setBold(midred, true);
		StyleConstants.setFontSize(midred, MID);
		
		bigred = new SimpleAttributeSet();
		StyleConstants.setForeground(bigred, Color.RED);
		StyleConstants.setBold(bigred, true);
		StyleConstants.setFontSize(bigred, BIG);
		
		smallpink = new SimpleAttributeSet();
		StyleConstants.setForeground(smallpink, Color.MAGENTA);  
		StyleConstants.setFontSize(smallpink, SMALL);
		
		midpink = new SimpleAttributeSet();
		StyleConstants.setForeground(midpink, Color.MAGENTA);  
		StyleConstants.setBold(midpink, true);
		StyleConstants.setFontSize(midpink, MID);
		
		bigpink = new SimpleAttributeSet();
		StyleConstants.setForeground(bigpink, Color.MAGENTA);  
		StyleConstants.setFontSize(bigpink, BIG);
	
		smallblue = new SimpleAttributeSet();
		StyleConstants.setForeground(smallblue, Color.BLUE);  		
		StyleConstants.setFontSize(smallblue, SMALL);
		
		bigblue = new SimpleAttributeSet();
		StyleConstants.setForeground(bigblue, Color.BLUE);  
		StyleConstants.setBold(bigblue, true);
		StyleConstants.setFontSize(bigblue, BIG);
		
		midblue = new SimpleAttributeSet();
		StyleConstants.setForeground(midblue, Color.BLUE);  
		StyleConstants.setBold(midblue, true);
		StyleConstants.setFontSize(midblue, MID);
		
		smallwhite = new SimpleAttributeSet();
		StyleConstants.setForeground(smallwhite, Color.WHITE);  
		StyleConstants.setFontSize(smallwhite, SMALL);
		
		bigwhite = new SimpleAttributeSet();
		StyleConstants.setForeground(bigwhite, Color.WHITE);
		StyleConstants.setBold(bigwhite, true);
		StyleConstants.setFontSize(bigwhite, BIG);		
	}
	
	public void setDocument(Document doc ) {
		this.doc = doc;
	}
		
	public void println(String str,int type, boolean isbold) throws BadLocationException{
		print(str,type, isbold);
		newLine();
	}
	public void println(String str,int type) throws BadLocationException{
		println(str, type, true);
	}
	//不传默认是加粗的
	public void print(String str,int type) throws BadLocationException{
		this.print(str, type,true);
	}
	public void print(String str,int type,boolean isbold) throws BadLocationException{
		SimpleAttributeSet s = null;
		switch(type) {
			case BRED:
				s = bigred;				
				break;
			case BBLUE:
				s=bigblue;
				break;
			case BBLACK:
				s = bigblack;
				break;
			case BWHITE:
				s = bigwhite;
				break;
			case BPINK:
				s = bigpink;
				break;
			case MRED:
				s = midred;
				break;
			case MBLUE:
				s=midblue;
				break;
			case MBLACK:
				s = midblack;
				break;
			case MWHITE:
				//s = midwhite;
				break;
			case MPINK:
				s = midpink;
				break;
			case SRED:
				s = smallred;
				break;
			case SBLUE:
				s=smallblue;
				break;
			case SBLACK:
				s = smallblack;
				break;
			case SWHITE:
				s = smallwhite;
				break;
			case SPINK:
				s = smallpink;
				break;
			default:
					break;
		}
		StyleConstants.setBold(s, isbold);
		doc.insertString(doc.getLength(), str, s);
	}
	//白色
	public void bwhite(String str) throws BadLocationException {
		bwhite(doc.getLength(), str);
	}
	private void bwhite(int len, String str) throws BadLocationException{
		doc.insertString(len, str, bigwhite);
	}
	public void swhite(String str) throws BadLocationException {
		swhite(doc.getLength(), str);
	}
	private void swhite(int len, String str) throws BadLocationException{
		doc.insertString(len, str, smallwhite);
	}
	//水红色
	public void spink(String str) throws BadLocationException {
		spink(doc.getLength(), str);
	}
	private void spink(int len, String str) throws BadLocationException{
		doc.insertString(len, str, smallpink);
	}
	public void bpink(String str) throws BadLocationException {
		bpink(doc.getLength(), str);
	}
	public void mpink(String str) throws BadLocationException {
		mpink(doc.getLength(), str);
	}
	private void bpink(int len, String str) throws BadLocationException{
		doc.insertString(len, str, bigpink);
	}
	private void mpink(int len, String str) throws BadLocationException{
		doc.insertString(len, str, midpink);
	}
	
	//蓝色
	public void sblue(String str) throws BadLocationException {
		sblue(doc.getLength(), str);
	}
	private void sblue(int len, String str) throws BadLocationException{
		doc.insertString(len, str, smallblue);
	}
	public void mblue(String str) throws BadLocationException {
		mblue(doc.getLength(), str);
	}
	private void mblue(int len, String str) throws BadLocationException{
		doc.insertString(len, str, midblue);
	}
	public void bblue(String str) throws BadLocationException {
		bblue(doc.getLength(), str);
	}
	private void bblue(int len, String str) throws BadLocationException{
		doc.insertString(len, str, bigblue);
	}
	public void bblack(String str) throws BadLocationException {
		bblack(doc.getLength(), str);
	}
	private void bblack(int len, String str) throws BadLocationException{
		doc.insertString(len, str, bigblack);
	}
	public void sblack(String str) throws BadLocationException {
		sblack(doc.getLength(), str);
	}
	private void sblack(int len, String str) throws BadLocationException{
		doc.insertString(len, str, smallblack);
	}
	public void mblack(String str) throws BadLocationException {
		mblack(doc.getLength(), str);
	}
	private void mblack(int len, String str) throws BadLocationException{
		doc.insertString(len, str, midblack);
	}
	public void bred(String str) throws BadLocationException {
		bred(doc.getLength(), str);
	}
	private void bred(int len, String str) throws BadLocationException{
		doc.insertString(len, str, bigred);
	}
	public void mred(String str) throws BadLocationException {
		mred(doc.getLength(), str);
	}
	private void mred(int len, String str) throws BadLocationException{
		doc.insertString(len, str, midred);
	}
	public void sred(String str) throws BadLocationException {
		sred(doc.getLength(), str);
	}
	private void sred(int len, String str) throws BadLocationException{
		doc.insertString(len, str, smallred);
	}
	public void newLine() throws BadLocationException {
		doc.insertString(doc.getLength(), "\r\n", null);
	}
}