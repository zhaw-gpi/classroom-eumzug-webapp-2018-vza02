package ch.zhaw.gpi.eumzugwebapp.resources;

import java.io.Serializable;

/**
 * Enitity-Klasse für Stammdaten zu einem Dokument
 *
 * @author VZa02
 */
public class Document implements Serializable {

    // Manuell gesetzte Id
    private int documentId;

    // Bezeichnung des Dokuments
    private String name;
    
    // GETTER UND SETTER
    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
