package com.email;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/* 
 * Source : 
	1. https://www.tutorialspoint.com/javamail_api/javamail_api_send_email_with_attachment.htm
	2. https://www.baeldung.com/java-email 
*/

/**
 * Helper to send Email using Gmail Service 
 * @author chetan bommu
 *
 */
public class EmailHelper {
	private static final Logger LOG = LoggerFactory.getLogger(EmailHelper.class);
	
	// Gmail credentials of the from USER
	private static final String FROM_USER = "testingapplication45@gmail.com";
	private static final String FROM_USER_PASSWORD = "TESTappn@45";
	
	// SMTP Properties
	private static final String HOST = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";
	
	public static void main(String[] args) {
		//Recipients File, Subject, Message body file, Attachments
		sendEmail("D://recipients.txt","Mail Application Testing","D://message.txt","D://here.txt");
	}
	
	/**
	 * Method to send Email
	 * @param recipients - List of recipients in String format
	 */
	public static void sendEmail(final String recipientsFilePath, final String subject,
			final String messageFilepath, final String attachmentsFilePath) {
		try {
			MimeMessage message = new MimeMessage(getSession());
			message.setFrom(new InternetAddress(FROM_USER));
			List<String> recipientsList = Files.readAllLines(Paths.get(recipientsFilePath));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(createRecipientsList(recipientsList)));
			//message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(recipients));
			message.setSubject(subject);
			// Mail Message part
			String content = new String(Files.readAllBytes(Paths.get(messageFilepath)));
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			BodyPart attachmentPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentsFilePath);
			attachmentPart.setDataHandler(new DataHandler(source));
			//Overriding Attachment name
			attachmentPart.setFileName("attachment.txt");
			multipart.addBodyPart(attachmentPart);
			
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Mail Sent Successfully.");
		} catch (Exception e) {
			LOG.info("Failed to send Mail. Exception is : " , e);
		}
	}
	
	/**
	 * Method to create a session
	 * @return Session
	 */
	private static Session getSession() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.socketFactory.port", SMTP_PORT);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.checkserveridentity", true);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		
		//create session and provide authentication
		return Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(FROM_USER, FROM_USER_PASSWORD);
			}
		});
	}
	
	/**
	 * Method to convert List of Recipients(users) into a single String
	 * @param users - List of Recipients
	 * @return String having list of recipients separated by comma.
	 */
	private static String createRecipientsList(List<String> users) {
		String recipients = "";
		for(String user : users) {
			recipients += user + "," ;
		}
		if(recipients.charAt(recipients.length()-1) == ',') {
			recipients = recipients.substring(0, recipients.length()-1);
		}
		return recipients;
	}
}
