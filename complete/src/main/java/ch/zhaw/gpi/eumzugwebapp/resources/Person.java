package ch.zhaw.gpi.eumzugwebapp.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * Enitity-Klasse für Stammdaten zu einer Person
 * 
 * @author VZa02
 */
public class Person implements Serializable{

    // Manuell gesetzte Personenidentifikation
    private String localPersonId;

    // AHV-Nummer
    private Long vn;
    
    // Vorname
    private String firstName;

    // Nachname
    private String officialName;
    
    // Geschlecht (1=männlich, 2,= weiblich, 3=unbestimmt)
    private int sex;

    // Geburtsdatum
    private Date dateOfBirth;
    
    // GETTER und SETTER
    public String getLocalPersonId() {
        return localPersonId;
    }

    public void setLocalPersonId(String localPersonId) {
        this.localPersonId = localPersonId;
    }

    public Long getVn() {
        return vn;
    }

    public void setVn(Long vn) {
        this.vn = vn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    
    
}
