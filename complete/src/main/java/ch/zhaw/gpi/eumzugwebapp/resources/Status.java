
package ch.zhaw.gpi.eumzugwebapp.resources;

import java.io.Serializable;

/**
 * Enitity-Klasse für Status zu einem Transaktions-Log-Eintrag der Umzugsplattform
 * 
 * @author scep
 */

public class Status implements Serializable {
    // Automatisch generierte Id
    private Long id;
    
    // Name -> Gemäss TransactionLog min 1 -> max 100
    private String name;
    
    // Deutscher Text (nicht NotNull fals keine Übersetzung vorhanden)
    private String germanText;
    
    // Englischer Text (nicht NotNull fals keine übersetzung vorhanden)
    private String englishText;
 
    
    //GETTER UND SETTER

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGermanText() {
        return germanText;
    }

    public void setGermanText(String germanText) {
        this.germanText = germanText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }
    
}
