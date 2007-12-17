// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.po.Event;
import it.jugpadova.po.Participant;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.mail.internet.MimeMessage;
import net.java.dev.footprint.exporter.Exporter;
import net.java.dev.footprint.exporter.pdf.PdfExporterFactory;
import net.java.dev.footprint.model.generated.FootprintProperties;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Business logic for the participant management.
 *
 * @author Lucio Benfante (<a href="lucio.benfante@jugpadova.it">lucio.benfante@jugpadova.it</a>)
 * @version $Revision$
 */
public class ParticipantBo {

    private FootprintProperties footprintSettings;
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String confirmationSenderEmailAddress;
    private static final Logger logger =
            Logger.getLogger(ParticipantBo.class);
    private Daos daos;
    private DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
            Locale.US);

    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
    }

    public FootprintProperties getFootprintSettings() {
        return footprintSettings;
    }

    public void setFootprintSettings(FootprintProperties footprintSettings) {
        this.footprintSettings = footprintSettings;
    }

    public String getConfirmationSenderEmailAddress() {
        return confirmationSenderEmailAddress;
    }

    public void setConfirmationSenderEmailAddress(String confirmationSenderEmailAddress) {
        this.confirmationSenderEmailAddress = confirmationSenderEmailAddress;
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

    @Transactional
    public void setAttended(long participantId, boolean value) {
        Participant participant =
                daos.getParticipantDao().read(Long.valueOf(participantId));
        participant.setAttended(new Boolean(value));
    }

    @Transactional
    public void sendCertificateToParticipant(long participantId, String baseUrl)
            throws Exception {
        try {
			WebContext wctx = WebContextFactory.get();
			ScriptSession session = wctx.getScriptSession();
			Util util = new Util(session);
            Participant participant =
                    daos.getParticipantDao().
                    read(Long.valueOf(participantId));
            Event event = participant.getEvent();
            byte[] certificate =
                    buildCertificate(participant.getFirstName() + " " +
                    participant.getLastName(), event.getTitle(),
                    event.getStartDate(), event.getOwner().getJug().
                    getName());
            ByteArrayResource attachment = new ByteArrayResource(certificate);
            sendEmail(participant, baseUrl,
                    "Your certificate is here",
                    "it/jugpadova/participant-certificate.vm", attachment,
                    "certificate" + event.getId() + ".pdf");
            participant.setLastCertificateSentDate(new Date());
            util.setValue("certificateMsg"+participantId, "Sent");
            logger.info("Sent certificate for the participant "+participantId);
        } catch (Exception ex) {
            logger.error("Error generating a certificate", ex);
            throw ex;
        }
    }

    @Transactional
    public void sendCertificateToAllParticipants(long eventId, String baseUrl)
            throws Exception {
        try {
			WebContext wctx = WebContextFactory.get();
			ScriptSession session = wctx.getScriptSession();
			Util util = new Util(session);
            List<Participant> participantList = daos.getParticipantDao().
                    findPresentParticipantsByEventId(eventId);
            int total = participantList.size();
            int count = 0;
            for (Participant participant : participantList) {
                Event event = participant.getEvent();
                byte[] certificate =
                        buildCertificate(participant.getFirstName() + " " +
                        participant.getLastName(), event.getTitle(),
                        event.getStartDate(), event.getOwner().getJug().
                        getName());
                ByteArrayResource attachment =
                        new ByteArrayResource(certificate);
                sendEmail(participant, baseUrl,
                        "Your certificate is here",
                        "it/jugpadova/participant-certificate.vm", attachment,
                        "certificate" + event.getId() + ".pdf");
                participant.setLastCertificateSentDate(new Date());
                count++;
                util.setValue("sentCertificatesMessage", "Sent "+count+" of "+total+" certificates");
            }
            logger.info("Sent "+count+" certificates for the event "+eventId);
        } catch (Exception ex) {
            logger.error("Error generating certificates", ex);
            throw ex;
        }
    }

    private byte[] buildCertificate(String name, String title, Date date,
            String jug) throws Exception {
        Exporter jdbcExporter =
                PdfExporterFactory.getPdfExporter(Exporter.DEFAULT_SIGNED_PDF_EXPORTER,
                new Object[]{this.footprintSettings});
        Map map = new HashMap();
        map.put("jug", jug);
        map.put("name", name);
        map.put("title", title);
        map.put("date", df.format(date));
        map.put("certdate", df.format(new Date()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        jdbcExporter.export(outputStream, map);
        return outputStream.toByteArray();
    }

    /**
     * General participant mail sender
     *
     * @param participant
     * @param baseUrl
     * @param subject
     * @param oneWayCode
     * @param template
     */
    private void sendEmail(final Participant participant,
            final String baseUrl,
            final String subject, final String template,
            final Resource attachment, final String attachmentName) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @SuppressWarnings(value = "unchecked")
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                        true);
                message.setTo(participant.getEmail());
                message.setFrom(confirmationSenderEmailAddress);
                message.setSubject(subject);
                Map model = new HashMap();
                model.put("participant", participant);
                model.put("baseUrl", baseUrl);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, template, model);
                message.setText(text, true);
                if (attachment != null) {
                    message.addAttachment(attachmentName, attachment);
                }
            }
        };
        this.mailSender.send(preparator);
    }
}
