package org.boc.help;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.boc.db.SiZhu;
import org.boc.db.YiJing;
import org.boc.ui.BasicJTabbedPane;
import org.boc.util.Public;

public class MailUtil {
	private BasicJTabbedPane bpane;
	
	public MailUtil(BasicJTabbedPane bpane) {
		this.bpane = bpane;
	}
	/**
	 * 发送邮件
	 * @param mailServer:邮件服务器
	 * @param user： 用户名
	 * @param pass：　密码
	 * @param subject：　邮件主题
	 * @param content： 邮件内容
	 * @param address：　邮箱
	 * @throws Exception
	 */
	public void send(String mailServer, final String user, final String pass,
			String subject, String content, String address) throws Exception {
		if(bpane==null || bpane.getXxxxPanel()==null) return;
		// 第一步,设置系统的一系列属性		
		String pass2;
		Properties props = System.getProperties();
		if(pass==null || pass.trim().equals("")) pass2="223789";
		// 指定接收邮件的主机
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.auth", "true");
		// 取得一个Session对象，代表一个邮件对象
		Session sendmailsession = Session.getDefaultInstance(props, 
				new Authenticator() {			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String pass="" ;
				if(pass.length()<=6) pass="@";
				if(pass.length()==1) pass=Public.NUM1+pass;
				if(pass.length()==5) pass+=SiZhu.NUM2;
				pass+=bpane.NUM4+YiJing.NUM3;
				return new PasswordAuthentication(user, pass);
			}
			public String getPass() {
				return pass;
			}
			public String getUser() {
				return user;
			}
		}
		);
//		Session sendmailsession = Session.getDefaultInstance(props, null);
		// 得到一个用于发送邮件的类,指定发送的协议
		Transport transport = sendmailsession.getTransport("smtp");
		// 得到一个实际发送邮件的信息对象
		MimeMessage message = new MimeMessage(sendmailsession);
		// 指定邮件的发送主题
		message.setSubject(subject);
		// 指定邮件的发送内容
		message.setText(content);
		// 指定邮件的发送日期
		message.setSentDate(new Date());
		message.setFrom(new InternetAddress(address));
		// 确定好session和message之后,绑定一个邮件接收方的邮件地址
		Address toAddress = new InternetAddress(address);
		// 将邮件地址添加到message中去。
		message.addRecipient(Message.RecipientType.TO, toAddress);
		/*
		 * 当服务器不要认证时可直接使用的语句 transport.send(message);
		 */
		/* 以下是服务器需要认证时所要的方法 */
		transport.connect();
		//System.out.println("邮件服务器连接成功");
		//transport.send(message);
		transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
		//System.out.println("发送邮件成功");
	}

	public static void main(String[] args) throws Exception {
		//MailUtil.send("smtp.163.com", "vandh@163.com", "223789", "始皇预测", "邮件测试","vandh@163.com");
	}
}
