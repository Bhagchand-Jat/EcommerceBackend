/**
 * 
 */
package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * 
 */
@Service
public class EmailService {
	@Autowired
	private JavaMailSender emailSender;

	public void sendEmail(String recipientEmail, String body, String subject) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		message.setContent(buildEmailContent(body), "text/html");
		message.setSubject(subject);
		message.setFrom(new InternetAddress("your_email"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		emailSender.send(message);
	}

	private String buildEmailContent(String otp) {
		// Create HTML content with the OTP (replace with your desired template)
		return "Your one-time password (OTP) is: <b>" + otp + "</b>";
	}
}
