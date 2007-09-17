package it.jugpadova.bean;

import it.jugpadova.po.Event;
import java.util.Date;

/**
 * A message in the news and upcomings list.
 *
 * @author Lucio Benfante
 */
public class NewsMessage {

    public static final String TYPE_NEWS = "NEWS";
    public static final String TYPE_NEW_EVENT = "NEW_EVENT";
    public static final String TYPE_UPCOMING_EVENT = "UPCOMING_EVENT";
    private String type;
    private Date date;
    private String message;
    private Event event;
    private String baseUrl;

    public NewsMessage() {
    }

    public NewsMessage(String type, Date date, String message, String baseUrl) {
        this.type = type;
        this.date = date;
        this.message = message;
        this.baseUrl = baseUrl;
    }

    public NewsMessage(String type, Date date, Event event, String baseUrl) {
        this.type = type;
        this.date = date;
        this.event = event;
        this.baseUrl = baseUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Object[] getArguments() {
        if (TYPE_UPCOMING_EVENT.equals(this.type)) {
            return new Object[]{event.getTitle(), event.getOwner().getJug().getName(), event.getOwner().getJug().getWebSite(), getEventUrl(baseUrl, event)};
        }
        return new Object[]{};
    }
    
    private String getEventUrl(String baseUrl, Event event) {
        return baseUrl+"/event/show.html?id="+event.getId();
    }
    
}
