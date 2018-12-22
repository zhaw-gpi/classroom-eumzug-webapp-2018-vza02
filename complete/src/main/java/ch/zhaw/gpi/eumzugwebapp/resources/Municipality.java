package ch.zhaw.gpi.eumzugwebapp.resources;

import java.io.Serializable;
import java.util.List;

/**
 * Enitity-Klasse für Stammdaten zu einer politischen Gemeinde
 * 
 * @author scep
 */
public class Municipality implements Serializable {

    // Manuell gesetzte Id (BFS-Nummer)
    private int municipalityId;
    
    // Offizieller Name
    private String municipalityName;
    
    // Umzugsgebühr
    private int feeMove;
    
    // Wegzugsgebühr
    private int feeMoveOut;

    // Zuzugsgebühr
    private int feeMoveIn;
    
    // Liste benötigter Dokumente inkl. Hochlad-Bedingungen
    private List<MunicipalityDocumentRelation> municipalityDocumentRelationEntities;
    
    // GETTER UND SETTER
    public int getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(int municipalityId) {
        this.municipalityId = municipalityId;
    }
    
    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }    

    public int getFeeMove() {
        return feeMove;
    }

    public void setFeeMove(int feeMove) {
        this.feeMove = feeMove;
    }    

    public int getFeeMoveOut() {
        return this.feeMoveOut;
    }

    public void setFeeMoveOut(int feeMoveOut) {
        this.feeMoveOut = feeMoveOut;
    }

    public int getFeeMoveIn() {
        return this.feeMoveIn;
    }

    public void setFeeMoveIn(int feeMoveIn) {
        this.feeMoveIn = feeMoveIn;
    }
    
    public List<MunicipalityDocumentRelation> getMunicipalityDocumentRelationEntities() {
        return municipalityDocumentRelationEntities;
    }
        
    public void setMunicipalityDocumentRelationEntities(List<MunicipalityDocumentRelation> municipalityDocumentRelationEntities) {
        this.municipalityDocumentRelationEntities = municipalityDocumentRelationEntities;
    }
    
    // Hinzufügen von einer neuen MunicipalityDocumentRelationEntity
    public void addMunicipalityDocumentRelationEntity(MunicipalityDocumentRelation municipalityDocumentRelationEntity) {
        this.municipalityDocumentRelationEntities.add(municipalityDocumentRelationEntity);
    }
    
    // Entfernen einer MunicipalityDocumentRelationEntity
    public void removeMunicipalityDocumentRelationEntity(MunicipalityDocumentRelation municipalityDocumentRelationEntity) {
        this.municipalityDocumentRelationEntities.remove(municipalityDocumentRelationEntity);
    }
    
}
