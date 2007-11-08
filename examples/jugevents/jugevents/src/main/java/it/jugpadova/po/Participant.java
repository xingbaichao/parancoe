package it.jugpadova.po;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.parancoe.persistence.po.hibernate.EntityBase;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * The participant of an event.
 *
 * @author lucio
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Participant.findParticipantByEmailAndEventId",
        query="from Participant p where p.email = ? and p.event.id = ?"),
    @NamedQuery(name="Participant.findConfirmedParticipantsByEventId",
        query="from Participant p where p.event.id = ? and p.confirmed = true order by p.lastName, p.firstName")
})
public class Participant extends EntityBase {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    private String confirmationCode;
    private Boolean confirmed;
    private Event event;
    private Date creationDate;
    
    /** Creates a new instance of Participant */
    public Participant() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @ManyToOne
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }        

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
}
