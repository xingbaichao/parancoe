package it.jugpadova.bean;

/**
 * Bean for the event search form.
 * 
 * @author Lucio Benfante
 */
public class EventSearch {

    private String continent;
    private String country;
    private String jugName;
    private boolean pastEvents;
    private String orderByDate="asc";
    
    public EventSearch() {
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getJugName() {
        return jugName;
    }

    public void setJugName(String jugName) {
        this.jugName = jugName;
    }

    public boolean isPastEvents() {
        return pastEvents;
    }

    public void setPastEvents(boolean pastEvents) {
        this.pastEvents = pastEvents;
    }

    public String getOrderByDate() {
        return orderByDate;
    }

    public void setOrderByDate(String orderByDate) {
        this.orderByDate = orderByDate;
    }
            
}
