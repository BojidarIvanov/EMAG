package util;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

	public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML)
			throws MessagingException {

		// 1 - get a mail session
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", "smtp.gmail.com");
		props.put("mail.smtps.port", 465);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtps.starttls.enable", "true");
		props.put("mail.smtps.debug", "true");

		props.put("mail.smtps.quitwait", "false");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		// 2 - create a message
		Message message = new MimeMessage(session);
		message.setSubject(subject);
		if (bodyIsHTML) {
			message.setContent(body, "text/html");
		} else {
			message.setText(body);
		}

		// 3 - address the message
		Address fromAddress = new InternetAddress(from);
		Address toAddress = new InternetAddress(to);
		message.setFrom(fromAddress);
		message.setRecipient(Message.RecipientType.TO, toAddress);

		// 4 - send the message
		javax.mail.Transport transport = session.getTransport();
		transport.connect("pechorinnd@gmail.com", "Koparan25");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public static void main(String[] args) {
		try {
			sendMail("pechorinnd@gmail.com", "Emag", "success is just around the corner - я сега да видим",
					"Дали е проблем кирилицата.", false);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}