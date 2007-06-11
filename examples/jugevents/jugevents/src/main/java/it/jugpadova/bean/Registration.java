package it.jugpadova.bean;

import it.jugpadova.po.Event;
import it.jugpadova.po.Participant;
import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;

/**
 *
 * @author lucio
 */
@Expression(value="a + b == sum", errorCode="wrongSum", args="a,b,sum")
public class Registration {
    private Event event;
    @CascadeValidation
    private Participant participant;
    private int a, b, sum;
    
    public Registration() {
    }
    
    public Event getEvent() {
        return event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }
    
    public Participant getParticipant() {
        return participant;
    }
    
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
    
    public int getA() {
        return a;
    }
    
    public void setA(int a) {
        this.a = a;
    }
    
    public int getB() {
        return b;
    }
    
    public void setB(int b) {
        this.b = b;
    }
    
    public int getSum() {
        return sum;
    }
    
    public void setSum(int sum) {
        this.sum = sum;
    }
    
}
