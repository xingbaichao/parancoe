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
package it.jugpadova.controllers;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Guid;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.WireFeedOutput;
import org.parancoe.web.BaseMultiActionController;
import it.jugpadova.Daos;
import it.jugpadova.Blos;
import it.jugpadova.bean.EventSearch;
import it.jugpadova.bean.Registration;
import it.jugpadova.blo.EventBo;
import it.jugpadova.exception.ParancoeAccessDeniedException;
import it.jugpadova.po.Event;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.Participant;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public abstract class EventController extends BaseMultiActionController {

    private static Logger logger =
            Logger.getLogger(EventController.class);

    public ModelAndView delete(HttpServletRequest req,
            HttpServletResponse res) {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Event event = dao().getEventDao().read(id);
            if (event == null) {
                throw new IllegalArgumentException("No event with id " + id);
            }
            blo().getEventBo().checkUserAuthorization(event);
            dao().getEventDao().delete(event);
            logger.info("User " + blo().getServicesBo().authenticatedUsername() +
                    " deleted event with id=" + id);
        } catch (ParancoeAccessDeniedException pade) {
            throw pade;
        } catch (Exception e) {
            return genericError(e);
        }
        return new ModelAndView("redirect:search.form");
    }

    public ModelAndView show(HttpServletRequest req,
            HttpServletResponse res) {
        ModelAndView mv =
                new ModelAndView("event/show");
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Event event = blo().getEventBo().retrieveEvent(id);
            if (event == null) {
                throw new IllegalArgumentException("No event with id " + id);
            }
            mv.addObject("event", event);
        } catch (Exception e) {
            return genericError(e);
        }
        return mv;
    }

    public ModelAndView participants(HttpServletRequest req,
            HttpServletResponse res) {
        ModelAndView mv =
                new ModelAndView("event/participants");
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Event event = blo().getEventBo().retrieveEvent(id);
            if (event == null) {
                throw new IllegalArgumentException("No event with id " + id);
            }
            blo().getEventBo().checkUserAuthorization(event);
            List<Participant> participants =
                    dao().getParticipantDao().
                    findConfirmedParticipantsByEventId(event.getId());
            mv.addObject("event", event);
            mv.addObject("participants", participants);
            Registration registration = new Registration();
            registration.setEvent(event);
            registration.setParticipant(new Participant());
            mv.addObject("registration", registration);
        } catch (ParancoeAccessDeniedException pade) {
            throw pade;
        } catch (Exception e) {
            return genericError(e);
        }
        return mv;
    }

    public ModelAndView rss(HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        try {
            EventSearch eventSearch = new EventSearch();
            eventSearch.setContinent(req.getParameter("continent"));
            eventSearch.setCountry(req.getParameter("country"));
            eventSearch.setJugName(req.getParameter("jugName"));
            eventSearch.setPastEvents(Boolean.parseBoolean(req.getParameter("pastEvents")));
            eventSearch.setOrderByDate(req.getParameter("order"));
            List<Event> events = blo().getEventBo().search(eventSearch);
            Channel channel = new Channel("rss_2.0");
            channel.setTitle("JUG Event news");
            channel.setDescription("JUG Events news always updated");
            StringBuffer channelLink = req.getRequestURL();
            if (req.getQueryString() != null) {
                channelLink.append('?').append(req.getQueryString());
            }
            channel.setLink(channelLink.toString());
            channel.setEncoding("UTF-8");
            Date now = new Date();
            channel.setLastBuildDate(now);
            channel.setPubDate(now);
            List<Item> items =
                    new LinkedList<Item>();
            for (Event event : events) {
                Item item = new Item();
                Guid guid = new Guid();
                guid.setValue("http://" + req.getServerName() + ":" +
                        req.getServerPort() + req.getContextPath() +
                        "/event/show.html?id=" + event.getId());
                guid.setPermaLink(true);
                item.setGuid(guid);
                Jugger jugger = event.getOwner();
                item.setAuthor(jugger.getFirstName() + " " +
                        jugger.getLastName());
                item.setTitle(event.getTitle());
                item.setExpirationDate(event.getEndDate() != null
                        ? event.getEndDate() : event.getStartDate());
                item.setPubDate(event.getStartDate());
                Description description =
                        new Description();
                description.setValue(event.getFilteredDescription());
                description.setType("text/html");
                item.setDescription(description);
                items.add(item);
            }
            channel.setItems(items);
            // flush it in the res
            WireFeedOutput wfo = new WireFeedOutput();
            res.setHeader("Cache-Control", "no-store");
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            res.setContentType("text/xml");
            ServletOutputStream resOutputStream = res.getOutputStream();
            wfo.output(channel,
                    new OutputStreamWriter(resOutputStream, "UTF-8"));
            resOutputStream.flush();
            resOutputStream.close();
        } catch (Exception exception) {
            logger.error("Error producing RSS", exception);
            throw exception;
        }
        return null;
    }

    public ModelAndView badge(HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        try {
            String locale = req.getParameter("lang");
            if (StringUtils.isBlank(locale)) {
                locale = (String) req.getAttribute("lang");
            }
            if (StringUtils.isBlank(locale)) {
                locale = "en";
            }
            java.text.DateFormat dateFormat =
                    java.text.DateFormat.getDateInstance(java.text.DateFormat.DEFAULT,
                    new Locale(locale));
            java.lang.String baseUrl =
                    "http://" + req.getServerName() + ":" + req.getServerPort() +
                    req.getContextPath();
            it.jugpadova.bean.EventSearch eventSearch =
                    new it.jugpadova.bean.EventSearch();
            eventSearch.setContinent(req.getParameter("continent"));
            eventSearch.setCountry(req.getParameter("country"));
            eventSearch.setJugName(req.getParameter("jugName"));
            eventSearch.setPastEvents(java.lang.Boolean.parseBoolean(req.getParameter("pastEvents")));
            eventSearch.setOrderByDate(req.getParameter("order"));
            java.util.List<it.jugpadova.po.Event> events =
                    blo().getEventBo().search(eventSearch);
            boolean showDescription =
                    java.lang.Boolean.parseBoolean(req.getParameter("jeb_showDescription"));
            String badgeStyle = req.getParameter("jeb_style");
            EventBo eventBo = blo().getEventBo();
            String result =
                    eventBo.getBadgeCode(eventBo.getBadgeHtmlCode(events,
                    dateFormat, baseUrl, showDescription, badgeStyle));
            // flush it in the res
            res.setHeader("Cache-Control", "no-store");
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            res.setContentType("text/javascript");
            ServletOutputStream resOutputStream = res.getOutputStream();
            Writer writer =
                    new OutputStreamWriter(resOutputStream, "UTF-8");
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (Exception exception) {
            logger.error("Error producing badge", exception);
            throw exception;
        }
        return null;
    }

    public Logger getLogger() {
        return logger;
    }

    protected abstract Daos dao();

    protected abstract Blos blo();
}
