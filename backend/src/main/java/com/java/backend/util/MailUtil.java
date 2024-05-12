package com.java.backend.util;

import jakarta.mail.MessagingException;


public interface MailUtil {
	void sendTokenMail(String to, String subject, String name, String token)
			throws MessagingException;
}
