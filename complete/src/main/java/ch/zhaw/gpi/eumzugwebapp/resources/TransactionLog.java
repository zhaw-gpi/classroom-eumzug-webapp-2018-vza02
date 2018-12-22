package ch.zhaw.gpi.eumzugwebapp.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * Enitity-Klasse für Bewegungsdaten zu einem Transaktions-Log-Eintrag der Umzugsplattform
 * 
 * @author scep
 */
public class TransactionLog implements Serializable {

    // Automatisch generierte Id
    private Long logId;
    
    // Zeitstempel
    private Date logTimeStamp;
    
    // Status-Eintrag
    private Status status;
    
    // Beziehung zu einer Person
    private Person person;

    // GETTER und SETTER
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Date getLogTimeStamp() {
        return logTimeStamp;
    }

    public void setLogTimeStamp(Date logTimeStamp) {
        this.logTimeStamp = logTimeStamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
}
