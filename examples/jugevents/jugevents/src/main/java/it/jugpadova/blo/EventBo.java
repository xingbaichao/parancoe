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
import it.jugpadova.bean.EventSearch;
import it.jugpadova.dao.EventDao;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.dao.ParticipantDao;
import org.springframework.transaction.annotation.Transactional;
import it.jugpadova.po.Event;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.Participant;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.acegisecurity.Authentication;
import org.acegisecurity.providers.encoding.MessageDigestPasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.proxy.scriptaculous.Effect;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EventBo {

    private static final Logger logger =
            Logger.getLogger(EventBo.class);
    private Daos daos;
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String confirmationSenderEmailAddress;

    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
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

    public String getConfirmationSenderEmailAddress() {
        return confirmationSenderEmailAddress;
    }

    public void setConfirmationSenderEmailAddress(String confirmationSenderEmailAddress) {
        this.confirmationSenderEmailAddress = confirmationSenderEmailAddress;
    }

    @Transactional(readOnly = true)
    public List<Event> retrieveEvents() {
        List<Event> events = getDaos().getEventDao().findCurrentEvents();
        for (Event event : events) {
            event.getParticipants().size();
        }
        return events;
    }

    @Transactional(readOnly = true)
    public List<Event> search(EventSearch eventSearch) {
        List<Event> events = new LinkedList<Event>();
        try {
            DetachedCriteria eventCriteria =
                    DetachedCriteria.forClass(Event.class);
            if (StringUtils.isNotBlank(eventSearch.getJugName()) ||
                    StringUtils.isNotBlank(eventSearch.getCountry()) ||
                    StringUtils.isNotBlank(eventSearch.getContinent())) {
                DetachedCriteria ownerCriteria =
                        eventCriteria.createCriteria("owner");
                if (StringUtils.isNotBlank(eventSearch.getJugName())) {
                    ownerCriteria.add(Restrictions.ilike("jugName",
                            eventSearch.getJugName(),
                            MatchMode.ANYWHERE));
                }
                if (StringUtils.isNotBlank(eventSearch.getCountry()) ||
                        StringUtils.isNotBlank(eventSearch.getContinent())) {
                    DetachedCriteria countryCriteria =
                            ownerCriteria.createCriteria("country");
                    if (StringUtils.isNotBlank(eventSearch.getCountry())) {
                        countryCriteria.add(Restrictions.or(Restrictions.ilike("englishName",
                                eventSearch.getCountry(),
                                MatchMode.ANYWHERE),
                                Restrictions.ilike("localName",
                                eventSearch.getCountry(),
                                MatchMode.ANYWHERE)));
                    }
                    if (StringUtils.isNotBlank(eventSearch.getContinent())) {
                        DetachedCriteria continentCriteria =
                                countryCriteria.createCriteria("continent");
                        continentCriteria.add(Restrictions.ilike("name",
                                eventSearch.getContinent(),
                                MatchMode.ANYWHERE));
                    }
                }
            }
            if (!eventSearch.isPastEvents()) {
                eventCriteria.add(Restrictions.ge("startDate",
                        new Date()));
            }
            eventCriteria.addOrder(Order.asc("startDate"));
            events = daos.getEventDao().searchByCriteria(eventCriteria);
            for (Event event : events) {
                event.getParticipants().size();
            }
        } catch (Exception e) {
            logger.error("Error searching events", e);
        }

        return events;
    }

    @Transactional
    public void save(Event event) {
        if (event.getOwner() == null) {
            Jugger jugger = getCurrentJugger();
            event.setOwner(jugger);
        }
        EventDao eventDao = getDaos().getEventDao();
        eventDao.createOrUpdate(event);
    }

    private Jugger getCurrentJugger() {
        Jugger result = null;
        Authentication authentication =
                org.acegisecurity.context.SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            JuggerDao juggerDao = getDaos().getJuggerDao();
            List<Jugger> juggers = juggerDao.searchByUsername(name);
            if (juggers.size() > 0) {
                result = juggerDao.searchByUsername(name).get(0);
                if (juggers.size() > 1) {
                    logger.warn("MOre than a JUG with the '" + name +
                            "' username");
                }
            } else {
                logger.error("No jugger with the '" + name + "' username");
            }
        }
        return result;
    }

    @Transactional
    public void register(Event event, Participant participant,
            String baseUrl) {
        EventDao eventDao = getDaos().getEventDao();
        event = eventDao.read(event.getId());
        participant.setConfirmed(Boolean.FALSE);
        participant.setConfirmationCode(generateConfirmationCode(event,
                participant));
        participant.setEvent(event);
        getDaos().getParticipantDao().createOrUpdate(participant);
        event.addParticipant(participant);
        eventDao.createOrUpdate(event);
        sendConfirmationEmail(event, participant, baseUrl);
    }

    @Transactional
    public void refreshRegistration(Event event,
            Participant participant, String baseUrl) {
        participant.setConfirmed(Boolean.FALSE);
        participant.setConfirmationCode(generateConfirmationCode(event,
                participant));
        participant.setEvent(event);
        getDaos().getParticipantDao().createOrUpdate(participant);
        sendConfirmationEmail(event, participant, baseUrl);
    }

    private String generateConfirmationCode(Event event,
            Participant participant) {
        return new MessageDigestPasswordEncoder("MD5", true).encodePassword(event.getTitle() +
                participant.getFirstName() + participant.getLastName() +
                participant.getEmail(), new Date());
    }

    private void sendConfirmationEmail(final Event event,
            final Participant participant, final String baseUrl) {
        MimeMessagePreparator preparator =
                new MimeMessagePreparator() {

            @SuppressWarnings(value = "unchecked")
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message =
                        new MimeMessageHelper(mimeMessage);
                message.setTo(participant.getEmail());
                message.setFrom(confirmationSenderEmailAddress);
                message.setSubject("Please confirm event registration");
                Map model = new HashMap();
                model.put("participant", participant);
                model.put("event", event);
                model.put("baseUrl", baseUrl);
                model.put("confirmationCode",
                        URLEncoder.encode(participant.getConfirmationCode(),
                        "UTF-8"));
                model.put("email",
                        URLEncoder.encode(participant.getEmail(), "UTF-8"));
                String text =
                        VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "it/jugpadova/registration-confirmation.vm", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

    @Transactional(readOnly = true)
    public Event retrieveEvent(Long id) {
        Event event = getDaos().getEventDao().read(id);
        event.getParticipants().size();
        return event;
    }

    @Transactional(readOnly = true)
    public List findPartialLocation(String partialLocation,
            String username) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialLocation) &&
                !StringUtils.isBlank(username)) {
            try {
                List<Event> events =
                        getDaos().getEventDao().
                        findEventByPartialLocationAndOwner("%" + partialLocation +
                        "%", username);
                Iterator<Event> itEvents = events.iterator();
                while (itEvents.hasNext()) {
                    Event event = itEvents.next();
                    result.add(event.getLocation() + "<div class=\"informal\">" +
                            event.getDirections() + "</div>" +
                            "<div class=\"informal hidden\">" + event.getId() +
                            "</div>");
                }
            } catch (Exception e) {
                logger.error("Error completing the location", e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public void copyDirectionsFromEvent(long eventId) {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        Util util = new Util(session);
        Event event = daos.getEventDao().read(Long.valueOf(eventId));
        if (event != null) {
            util.setValue("location", event.getLocation());
            util.setValue("directions", event.getDirections());
            util.setValue("directionsPreview",
                    FilterBo.filterText(event.getDirections(), event.getFilter(),
                    false));
            Effect effect =
                    new Effect(session);
            effect.highlight("location");
            effect.highlight("directions");
            effect.highlight("directionsPreview");
        }
    }

    @Transactional
    public Participant confirmParticipant(String email,
            String confirmationCode) {
        ParticipantDao dao = daos.getParticipantDao();
        List<Participant> participants =
                dao.findByEmailAndConfirmationCodeAndConfirmed(email,
                confirmationCode, Boolean.FALSE);
        if (participants != null && participants.size() > 0) {
            Participant p = participants.get(0);
            p.setConfirmed(Boolean.TRUE);
            return p;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public void updateBadgePanel(String continent, String country,
            String jugName, String pastEvents,
            String jebShowDescription, String badgeStyle, String locale) {
        WebContext wctx = WebContextFactory.get();
        HttpServletRequest req = wctx.getHttpServletRequest();
        ScriptSession session = wctx.getScriptSession();
        Util util = new Util(session);
        java.text.DateFormat dateFormat =
                java.text.DateFormat.getDateInstance(java.text.DateFormat.DEFAULT,
                new Locale(locale));
        java.lang.String baseUrl =
                "http://" + req.getServerName() + ":" + req.getServerPort() +
                req.getContextPath();
        it.jugpadova.bean.EventSearch eventSearch =
                new it.jugpadova.bean.EventSearch();
        eventSearch.setContinent(continent);
        eventSearch.setCountry(country);
        eventSearch.setJugName(jugName);
        eventSearch.setPastEvents(java.lang.Boolean.parseBoolean(pastEvents));
        java.util.List<it.jugpadova.po.Event> events = this.search(eventSearch);
        boolean showDescription =
                java.lang.Boolean.parseBoolean(jebShowDescription);
        util.setValue("badgeCode",
                this.getBadgePageCode(baseUrl, continent, country, jugName,
                pastEvents, jebShowDescription, badgeStyle));
        util.setValue("badgePreview",
                this.getBadgeHtmlCode(events, dateFormat, baseUrl,
                showDescription, badgeStyle));
    }

    public String getBadgeCode(String badgeHtmlCode) {
        java.lang.StringBuffer result = new java.lang.StringBuffer();
        result.append("var badge=\'\';\n");
        result.append("badge += '").append(javascriptize(badgeHtmlCode)).
                append("';\n");
        result.append("document.write(badge);");
        return result.toString();
    }

    public String getBadgePageCode(String baseUrl, String continent,
            String country, String jugName, String pastEvents,
            String jebShowDescription, String badgeStyle) {
        StringBuffer result = new StringBuffer();
        result.append("<script type=\"text/javascript\" src=\"").append(baseUrl).
                append("/event/badge.html");
        try {
            if (StringUtils.isNotBlank(continent) ||
                    StringUtils.isNotBlank(country) ||
                    StringUtils.isNotBlank(jugName) ||
                    StringUtils.isNotBlank(pastEvents) ||
                    StringUtils.isNotBlank(jebShowDescription) ||
                    StringUtils.isNotBlank(badgeStyle)) {
                result.append('?');
                boolean first = true;
                if (StringUtils.isNotBlank(continent)) {
                    if (!first) {
                        result.append('&');
                    }
                    result.append("continent=").
                            append(URLEncoder.encode(continent, "UTF-8"));
                    first = false;
                }
                if (StringUtils.isNotBlank(country)) {
                    if (!first) {
                        result.append('&');
                    }
                    result.append("country=").
                            append(URLEncoder.encode(country, "UTF-8"));
                    first = false;
                }
                if (StringUtils.isNotBlank(jugName)) {
                    if (!first) {
                        result.append('&');
                    }
                    result.append("jugName=").
                            append(URLEncoder.encode(jugName, "UTF-8"));
                    first = false;
                }
                if (StringUtils.isNotBlank(pastEvents)) {
                    if (!first) {
                        result.append('&');
                    }
                    result.append("pastEvents=").
                            append(URLEncoder.encode(pastEvents, "UTF-8"));
                    first = false;
                }
                if (StringUtils.isNotBlank(jebShowDescription)) {
                    if (!first) {
                        result.append('&');
                    }
                    result.append("jeb_showDescription=").
                            append(URLEncoder.encode(jebShowDescription, "UTF-8"));
                    first = false;
                }
                if (StringUtils.isNotBlank(badgeStyle)) {
                    if (!first) {
                        result.append('&');
                    }
                    result.append("jeb_style=").
                            append(URLEncoder.encode(badgeStyle, "UTF-8"));
                    first = false;
                }
            }
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            logger.error("Error encoding URL parameters",
                    unsupportedEncodingException);
        }
        result.append("\"><!--\n");
        result.append("// --></script>");
        return result.toString();
    }

    public String getBadgeHtmlCode(List<Event> events,
            DateFormat dateFormat, String baseUrl, boolean showDescription, String badgeStyle) {
        StringBuffer result = new StringBuffer();
        if ("simple".equals(badgeStyle)) {
            result.append("<style type=\"text/css\"><!--\n");
            result.append(".jeb_event_odd {background-color: rgb(90%, 90%, 90%); padding: 4px;}\n");
            result.append(".jeb_event_even {background-color: white; padding: 4px;}\n");
            result.append(".jeb_date {font-weight: bold;}\n");
            result.append(".jeb_title {margin-left: 15px;}\n");
            result.append(".jeb_description {margin-left: 15px;}\n");
            result.append("--></style>\n");
        }
        boolean isOdd = false;
        for (it.jugpadova.po.Event event : events) {
            result.append("<div class=\"jeb_event_").
                    append(isOdd ? "odd" : "even").append("\">");
            result.append("<div class=\"jeb_date\"><span class=\"jeb_text\">").
                    append(dateFormat.format(event.getStartDate())).
                    append("</span></div>");
            result.append("<div class=\"jeb_title\"><span class=\"jeb_text\"><a href=\"").
                    append(baseUrl).append("/event/show.html?id=").
                    append(event.getId()).append("\">").append(event.getTitle()).
                    append("</a></span></div>");
            if (showDescription) {
                result.append("<div class=\"jeb_description\"><span class=\"jeb_text\">").
                        append(event.getFilteredDescription()).
                        append("</span></div>");
            }
            result.append("</div>");
            isOdd = !isOdd;
        }
        return result.toString();
    }

    private String javascriptize(String s) {
        return s.replaceAll("\'", "\'").replaceAll("\n", "\n");
    }
}
