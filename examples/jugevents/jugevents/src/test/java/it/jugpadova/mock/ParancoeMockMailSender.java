/**
 * 
 */
package it.jugpadova.mock;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * Mock implementation of JavaMailSender for test porpouse.
 * 
 * @author Enrico Giurin
 * 
 */
public class ParancoeMockMailSender extends JavaMailSenderImpl {
	private static final Logger logger = Logger
			.getLogger(ParancoeMockMailSender.class);

	@Override
	public void send(MimeMessagePreparator arg0) throws MailException {
		logger.info("Called send() ");
	}

}
