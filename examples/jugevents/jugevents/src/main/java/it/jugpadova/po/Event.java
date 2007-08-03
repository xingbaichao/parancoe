package it.jugpadova.po;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * An event.
 *
 * @author Lucio Benfante
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name="Event.findCurrentEvents",
        query="from Event e where e.startDate >= current_date()"
    ),
    @NamedQuery(
        name="Event.findEventByPartialLocation",
        query="from Event e where lower(e.location) like lower(?) order by e.location"
    ),
    @NamedQuery(
        name="Event.findEventByPartialLocationAndOwner",
        query="from Event e where lower(e.location) like lower(?) and e.owner.username = ? order by e.location"
    )
})
public class Event extends EntityBase {
    private String title;
    private Date startDate;
    private String startTime;
    private Date endDate;
    private String endTime;
    private String location;
    private String directions;
    private String description;
    private String filter = "Textile";
    private Jugger owner;

    @ManyToOne
    public Jugger getOwner() {
        return owner;
    }

    public void setOwner(Jugger owner) {
        this.owner = owner;
    }

    @Column(length=1024)
    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    private List<Participant> participants;
    
    /** Creates a new instance of Event */
    public Event() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(length=4096)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade={CascadeType.ALL})
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        if (this.participants == null) {
            this.participants = new LinkedList<Participant>();
        }
        this.participants.add(participant);
    }
    
    @Transient
    public int getNumberOfParticipants() {
        int result = 0;
        if (getParticipants() != null) {
            result = getParticipants().size();
        }
        return result;
    }
}
