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
 * @ClassName ������SendMail
 * @Description ����˵����
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************ 
 * @date �������ڣ�2011-3-18
 * @author �����ˣ����п�
 * @version �汾�ţ�V1.0
 *          <p>
 *          �޶���¼*************************************
 * 
 *          2011-3-18 ���п� �������๦�ܡ�
 * 
 * 
 *          </p>
 */
public class SendMail {
	private MimeMessage mimeMsg; // MIME�ʼ�����
	private Session session; // �ʼ��Ự����
	private Properties props; // ϵͳ����
	private boolean needAuth = false; // smtp�Ƿ���Ҫ��֤
	private String username = "bosssoft"; // smtp��֤�û���������
	private String password = "bs@)!!)#!*";
	private Multipart mp; // Multipart����,�ʼ�����,����,���������ݾ���ӵ����к�������MimeMessage����
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
		// System.out.println("����ϵͳ���ԣ�mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // ���ϵͳ���Զ���
		props.put("mail.smtp.host", hostName); // ����SMTP����

	}

	// ����˿�
	public void setSmtpPort(String port) {
		if (props == null)
			props = System.getProperties(); // ���ϵͳ���Զ���
		props.put("mail.smtp.port", port); // ����SMTP�����˿�
	}

	/**
	 * 
	 * @return boolean
	 */

	public boolean createMimeMessage() {
		try {
			// System.out.println("׼����ȡ�ʼ��Ự����");
			session = Session.getDefaultInstance(props, null); // ����ʼ��Ự����
		} catch (Exception e) {
			System.err.println("��ȡ�ʼ��Ự����ʱ��������" + e);
			return false;
		}
		// System.out.println("׼������MIME�ʼ�����");
		try {
			mimeMsg = new MimeMessage(session); // ����MIME�ʼ�����
			mp = new MimeMultipart();
			return true;
		}

		catch (Exception e) {
			System.err.println("����MIME�ʼ�����ʧ�ܣ�" + e);
			return false;
		}

	}

	/**
	 * 
	 * @param need
	 *            boolean
	 */

	public void setNeedAuth(boolean need) {
		// System.out.println("����smtp�����֤��mail.smtp.auth = " + need);
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
		// System.out.println("�����ʼ����⣡");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			System.err.println("�����ʼ����ⷢ������");
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
			System.err.println("�����ʼ�����ʱ��������" + e);
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
		// System.out.println("�����ʼ�������" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		}

		catch (Exception e) {
			System.err.println("�����ʼ�������" + filename + "��������" + e);
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
		// System.out.println("���÷����ˣ�");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // ���÷�����
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
			// System.out.println("���ڷ����ʼ�....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg, mimeMsg
					.getRecipients(Message.RecipientType.TO));
			// transport.send(mimeMsg);
//			 System.out.println("�����ʼ��ɹ���");
			transport.close();
			return true;
		} catch (Exception e) {
			System.err.println("�ʼ�����ʧ�ܣ�" + e);
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
		String mailbody = "����test2��<br/>&nbsp;&nbsp;&nbsp;&nbsp;���ļ����˻���ַ��"
				+ "<a href='"
				+ config.get("basePath")
				+ "buzz/pay/usercentermgr/activeUser.do?AID=123'>����</a>"
				+ "<br/>"
				+ config.get("basePath")
				+ "buzz/pay/usercentermgr/activeUser.do?AID=123<br/>&nbsp;&nbsp;&nbsp;&nbsp;���ʼ���ϵͳ�Զ�����������ظ���"
				+ "<p align='right'>" + config.get("appName") + "<br/>"
				+ Tools.getDate() + "</p>" + "<address>���ݲ�˼����������޹�˾</address>";

		themail.setBody(mailbody);
//		themail.setTo("lilei8888@163.com");
		themail.setTo("lilei8106@sina.com");
		boolean b = themail.sendout();
		System.out.println(b);
		// String
		// str="{\"data\":\"\\u003cresultCode\\u003e001\\u003c/resultCode\\u003e\\u003caccount\\u003e003\\u003c/account\\u003e\\u003cdate\\u003e20110330\\u003c/date\\u003e\\u003cbankCode\\u003e003\\u003c/bankCode\\u003e\\u003crecordCnt\\u003e6\\u003c/recordCnt\\u003e\\u003camount\\u003e80.0\u003c/amount\\u003e\"}";
		// System.out.println(EscapeUnescape.unescape(str));

		// String host = "192.168.10.191"; // ָ����smtp�������������ľ�����IP
		/*
		 * String host = "192.168.10.37"; // ����smtp������ //String host =
		 * "smtp.163.com"; // 163��smtp������ String from = "bosssoft@bosssoft.com";
		 * // �ʼ������˵��ʼ���ַ // String to = "crb@lixiaobo.com"; // �����ʼ������˵��ʼ���ַ
		 * String to = "lilei8888@163.com"; // �����ʼ������˵��ʼ���ַ final String
		 * username = "bosssoft"; // �����˵��ʼ��ʻ� final String password = "123"; //
		 * �����˵��ʼ�����
		 * 
		 * // ����Properties ���� Properties props = System.getProperties();
		 * 
		 * // ���smtp���������� props.put("mail.smtp.host", host);
		 * props.put("mail.smtp.auth", "true");
		 * 
		 * // �����ʼ��Ự Session session = Session.getDefaultInstance(props, new
		 * Authenticator() {
		 * 
		 * @Override public PasswordAuthentication getPasswordAuthentication() {
		 * return new PasswordAuthentication(username, password); } });
		 * 
		 * try { // �����ʼ���Ϣ MimeMessage message = new MimeMessage(session);
		 * message.setFrom(new InternetAddress(from));
		 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(
		 * to)); // message.setSubject(transferChinese("�����Լ����ʼ���������"));
		 * message.setSubject("���ԣ�I hava my own mail server"); message
		 * .setText("���ԣ�From now, you have your own mail server, congratulation!"
		 * ); // ������Ϣ session.getTransport("smtp").send(message); //
		 * Transport.send(message); //Ҳ������������Transport������
		 * System.out.println("SendMail Process Over!"); } catch
		 * (MessagingException e) { e.printStackTrace(); }
		 */
	}
}
