package it.jugpadova.po;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@NamedQuery(
    name="Event.findCurrentEvents",
    query="from Event e where e.startDate >= current_date()"
)
public class Event extends EntityBase {
    private String title;
    private Date startDate;
    private String startTime;
    private Date endDate;
    private String endTime;
    private String location;
    private String description;
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
            this.participants = new LinkedList();
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
