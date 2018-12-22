package ch.zhaw.gpi.eumzugwebapp.resources;

import java.io.Serializable;

/**
 * Enitity-Klasse für Kombination aus Gemeinde und einem Dokument
 * 
 * Da weitere Attribute vorhanden sind, kann nicht @ManyToMany genutzt werden
 *
 * @author scep
 */
public class MunicipalityDocumentRelation implements Serializable {

    // Automatisch gesetzte Id
    private Long municipalityDocumentId;

    // Hochladen-Bedingung Zivilstand (nur wenn verheiratet)
    private Boolean marriageCondition;

    // Hochladen-Bedingung Elternschaft (nur mit Kindern)
    private Boolean childrenCondition;

    // Hochladen-Bedingung Ausländerstatus (nur wenn Ausländer)
    private Boolean strangerCondition;
    
    // Referenz auf ein Dokument (wird in Datenbank über Foreign Key implementiert)
    private Document documentEntity;
    
    // GETTER UND SETTER
    public Document getDocumentEntity() {
        return documentEntity;
    }
    
    public void setDocumentEntity(Document documentEntity) {
        this.documentEntity = documentEntity;
    }

    public Boolean isStrangerCondition() {
        return strangerCondition;
    }

    public void setStrangerCondition(Boolean strangerCondition) {
        this.strangerCondition = strangerCondition;
    }

    public Boolean isChildrenCondition() {
        return childrenCondition;
    }

    public void setChildrenCondition(Boolean childrenCondition) {
        this.childrenCondition = childrenCondition;
    }

    public Boolean isMarriageCondition() {
        return marriageCondition;
    }

    public void setMarriageCondition(Boolean marriageCondition) {
        this.marriageCondition = marriageCondition;
    }

    public Long getMunicipalityDocumentId() {
        return municipalityDocumentId;
    }

    public void setMunicipalityDocumentId(Long municipalityDocumentId) {
        this.municipalityDocumentId = municipalityDocumentId;
    }

    
}
