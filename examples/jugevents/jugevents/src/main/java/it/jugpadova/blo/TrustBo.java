/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.dao.ReliabilityRequestDao;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.ReliabilityRequest;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * General porpouse BO for reliability services.
 * 
 * @author Enrico Giurin
 * 
 */
public class TrustBo {
	/**
	 * min value for threshold access.
	 */
	public static final double MIN_THRESHOLD_ACCESS = 0d;

	/**
	 * Max value for threshold access
	 */
	public static final double MAX_THRESHOLD_ACCESS = 1d;

	private static final Logger logger = Logger.getLogger(TrustBo.class);

	private double thresholdAccess;

	private VelocityEngine velocityEngine;

	private JavaMailSender mailSender;

	private String adminMailJE;

	private String internalMail;

	private Daos daos;

	public double getThresholdAccess() {
		return thresholdAccess;
	}

	public void setThresholdAccess(double thresholdAccess) {
		this.thresholdAccess = thresholdAccess;
	}

	/**
	 * Returns true if jugger is reliable according to jugevents policies.
	 * 
	 * @param jugger
	 * @return
	 */
	public boolean isJuggerReliable(Jugger jugger) {
		// NOTE: we can change here Policy to grant reliablility, we
		// can also decide to grant a special ROLE to jugger, without using the
		// attribute reliability
		double reliability = jugger.getReliability();
		if (reliability < MIN_THRESHOLD_ACCESS
				|| reliability > MAX_THRESHOLD_ACCESS) {
			throw new IllegalArgumentException("reliability: " + reliability
					+ " is out of range");
		}
		if (thresholdAccess < MIN_THRESHOLD_ACCESS
				|| thresholdAccess > MAX_THRESHOLD_ACCESS) {
			throw new IllegalArgumentException("thresholdAccess: "
					+ thresholdAccess + " is out of range");
		}
		if (jugger.getReliability() >= thresholdAccess)
			return true;

		return false;
	}

	/**
	 * Business method to require Reliability.
	 * 
	 * @param jug
	 */
	// Metodo da chiamare all' interno di un contesto transazionale
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	void requireReliability(long juggerId, String motivation) {
		JuggerDao jdao = daos.getJuggerDao();
		ReliabilityRequestDao rrdao = daos.getReliabilityRequestDao();

		ReliabilityRequest rr = new ReliabilityRequest();
		rr.setDateRequest(new Date(System.currentTimeMillis()));
		rr.setMotivation(motivation);
		rr.setStatus(ReliabilityRequest.RELIABILITY_REQUIRED);
		rrdao.create(rr);
		Jugger ej = jdao.read(juggerId);
		ej.setReliabilityRequest(rr);
		jdao.update(ej);

		// send mail to admin-jugevents
		sendEmail(ej, "", "A jugger has required reliability",
				"it/jugpadova/request-reliability2admin.vm", internalMail,
				adminMailJE, motivation);
		logger.info("Jugger " + ej.getUser().getUsername()
				+ " has completed wth success request of reliability");
	}

	@Transactional
	public void requireReliabilityOnExistingJugger(long juggerId,
			String motivation) {
		requireReliability(juggerId, motivation);
	}

	private void sendEmail(final Jugger jugger, final String baseUrl,
			final String subject, final String template, final String sender,
			final String mailTo, final String motivation) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@SuppressWarnings(value = "unchecked")
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(mailTo);
				message.setFrom(sender);
				message.setSubject(subject);
				Map model = new HashMap();
				model.put("jugger", jugger);
				model.put("baseUrl", baseUrl);
				model.put("motivation", motivation);

				model.put("username", URLEncoder.encode(jugger.getUser()
						.getUsername(), "UTF-8"));
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, template, model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
	}

	public String getAdminMailJE() {
		return adminMailJE;
	}

	public void setAdminMailJE(String adminMailJE) {
		this.adminMailJE = adminMailJE;
	}

	public String getInternalMail() {
		return internalMail;
	}

	public void setInternalMail(String internalMail) {
		this.internalMail = internalMail;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public Daos getDaos() {
		return daos;
	}

	public void setDaos(Daos daos) {
		this.daos = daos;
	}

}
