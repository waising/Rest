package com.bs.pub.util;

import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @ClassName 类名：SendMail
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************ 
 * @date 创建日期：2011-3-18
 * @author 创建人：李中俊
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2011-3-18 李中俊 创建该类功能。
 * 
 * 
 *          </p>
 */
public class SendMail {
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	private boolean needAuth = false; // smtp是否需要认证
	private String username = "bosssoft"; // smtp认证用户名和密码
	private String password = "bs@)!!)#!*";
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	private String port = "25";

	public SendMail() {
		setSmtpHost("smtp.yeah.net");
		createMimeMessage();
		this.setFrom("bosssoft@yeah.net");
	}

	public SendMail(String smtp) {
		setSmtpHost(smtp);
		setSmtpPort(port);
		createMimeMessage();
	}

	/**
	 * 
	 * @param hostName
	 *            String
	 */

	public void setSmtpHost(String hostName) {
		// System.out.println("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机

	}

	// 邮箱端口
	public void setSmtpPort(String port) {
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.port", port); // 设置SMTP主机端口
	}

	/**
	 * 
	 * @return boolean
	 */

	public boolean createMimeMessage() {
		try {
			// System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			System.err.println("获取邮件会话对象时发生错误！" + e);
			return false;
		}
		// System.out.println("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();
			return true;
		}

		catch (Exception e) {
			System.err.println("创建MIME邮件对象失败！" + e);
			return false;
		}

	}

	/**
	 * 
	 * @param need
	 *            boolean
	 */

	public void setNeedAuth(boolean need) {
		// System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}

	}

	/**
	 * 
	 * @param name
	 *            String
	 * 
	 * @param pass
	 *            String
	 */

	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;

	}

	/**
	 * 
	 * @param mailSubject
	 *            String
	 * 
	 * @return boolean
	 */

	public boolean setSubject(String mailSubject) {
		// System.out.println("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			System.err.println("设置邮件主题发生错误！");
			return false;
		}

	}

	/**
	 * 
	 * @param mailBody
	 *            String
	 */

	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=GBK");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			System.err.println("设置邮件正文时发生错误！" + e);
			return false;
		}

	}

	/**
	 * 
	 * @param name
	 *            String
	 * 
	 * @param pass
	 *            String
	 */

	public boolean addFileAffix(String filename) {
		// System.out.println("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		}

		catch (Exception e) {
			System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
	}

	/**
	 * 
	 * @param name
	 *            String
	 * 
	 * @param pass
	 *            String
	 */

	public boolean setFrom(String from) {
		// System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param name
	 *            String
	 * 
	 * @param pass
	 *            String
	 */

	public boolean setTo(String to) {

		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param name
	 *            String
	 * 
	 * @param pass
	 *            String
	 */

	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param name
	 *            String
	 * 
	 * @param pass
	 *            String
	 */

	public boolean sendout() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			// System.out.println("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg, mimeMsg
					.getRecipients(Message.RecipientType.TO));
			// transport.send(mimeMsg);
//			 System.out.println("发送邮件成功！");
			transport.close();
			return true;
		} catch (Exception e) {
			System.err.println("邮件发送失败！" + e);
			return false;
		}

	}

	/**
	 * 
	 * Just do it as this
	 */

	public static void main(String[] args) {
		SendMail themail = new SendMail();
		Map<String, String> config = ReadConfig.getConfig();
		themail.setFrom("bosssoft@bosssoft.vicp.cc");
		themail.setSmtpHost("192.168.10.36");
		themail.setNamePass("bosssoft", "bosssoft2");
		themail.setNeedAuth(true);
		String mailbody = "您好test2：<br/>&nbsp;&nbsp;&nbsp;&nbsp;您的激活账户地址："
				+ "<a href='"
				+ config.get("basePath")
				+ "buzz/pay/usercentermgr/activeUser.do?AID=123'>激活</a>"
				+ "<br/>"
				+ config.get("basePath")
				+ "buzz/pay/usercentermgr/activeUser.do?AID=123<br/>&nbsp;&nbsp;&nbsp;&nbsp;此邮件由系统自动发出，请勿回复。"
				+ "<p align='right'>" + config.get("appName") + "<br/>"
				+ Tools.getDate() + "</p>" + "<address>福州博思软件开发有限公司</address>";

		themail.setBody(mailbody);
//		themail.setTo("lilei8888@163.com");
		themail.setTo("lilei8106@sina.com");
		boolean b = themail.sendout();
		System.out.println(b);
		// String
		// str="{\"data\":\"\\u003cresultCode\\u003e001\\u003c/resultCode\\u003e\\u003caccount\\u003e003\\u003c/account\\u003e\\u003cdate\\u003e20110330\\u003c/date\\u003e\\u003cbankCode\\u003e003\\u003c/bankCode\\u003e\\u003crecordCnt\\u003e6\\u003c/recordCnt\\u003e\\u003camount\\u003e80.0\u003c/amount\\u003e\"}";
		// System.out.println(EscapeUnescape.unescape(str));

		// String host = "192.168.10.191"; // 指定的smtp服务器，本机的局域网IP
		/*
		 * String host = "192.168.10.37"; // 本机smtp服务器 //String host =
		 * "smtp.163.com"; // 163的smtp服务器 String from = "bosssoft@bosssoft.com";
		 * // 邮件发送人的邮件地址 // String to = "crb@lixiaobo.com"; // 内网邮件接收人的邮件地址
		 * String to = "lilei8888@163.com"; // 外网邮件接收人的邮件地址 final String
		 * username = "bosssoft"; // 发件人的邮件帐户 final String password = "123"; //
		 * 发件人的邮件密码
		 * 
		 * // 创建Properties 对象 Properties props = System.getProperties();
		 * 
		 * // 添加smtp服务器属性 props.put("mail.smtp.host", host);
		 * props.put("mail.smtp.auth", "true");
		 * 
		 * // 创建邮件会话 Session session = Session.getDefaultInstance(props, new
		 * Authenticator() {
		 * 
		 * @Override public PasswordAuthentication getPasswordAuthentication() {
		 * return new PasswordAuthentication(username, password); } });
		 * 
		 * try { // 定义邮件信息 MimeMessage message = new MimeMessage(session);
		 * message.setFrom(new InternetAddress(from));
		 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(
		 * to)); // message.setSubject(transferChinese("我有自己的邮件服务器了"));
		 * message.setSubject("测试，I hava my own mail server"); message
		 * .setText("测试，From now, you have your own mail server, congratulation!"
		 * ); // 发送消息 session.getTransport("smtp").send(message); //
		 * Transport.send(message); //也可以这样创建Transport对象发送
		 * System.out.println("SendMail Process Over!"); } catch
		 * (MessagingException e) { e.printStackTrace(); }
		 */
	}
}
