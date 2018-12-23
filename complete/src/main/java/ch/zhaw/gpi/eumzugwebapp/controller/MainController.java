package ch.zhaw.gpi.eumzugwebapp.controller;

import java.util.List;

import ch.zhaw.gpi.eumzugwebapp.resources.Municipality;
import ch.zhaw.gpi.eumzugwebapp.resources.Document;
import ch.zhaw.gpi.eumzugwebapp.resources.Person;
import ch.zhaw.gpi.eumzugwebapp.resources.Status;
import ch.zhaw.gpi.eumzugwebapp.resources.TransactionLog;
import ch.zhaw.gpi.eumzugwebapp.services.EUmzugClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    
    //BITTE KOMMENTIEREN
    @Autowired
    private EUmzugClientService eUmzugClientService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }
// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------DOKUMENTE---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//

     /**
     * Dokumentenliste
     * Alle Dokumente werden geholt und Liste wird ins Model gespeichert.
     * @param model Wird auomatisch von Spring mitgegeben
     * @return Gibt die aktuelle Seite zurück
     */
    @RequestMapping(value = {"/indexDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String getDocumentList(Model model) {

        List<Document> documentListe = eUmzugClientService.getDocumentList();//Dokumente von eUmzugService werden geholt und in "documentListe" gespeichert.
        model.addAttribute("documentListe", documentListe);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "indexDocument";//Gibt die View zurück die geladen werden soll
    }

    
    /***
     * Dokument hinzufügen
     * 
     * @param model Wird automatisch von Spring mitgegeben
     * @return Gibt View zurück die geladen werden soll
     */

    @RequestMapping(value = {"/", "/addDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showAddDocumentPage(Model model) {

        Document document = new Document();//Erstellung von leerem Dokument
        model.addAttribute("document", document);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "addDocument";//Gibt View zurück die geladen werden soll
    }

    /***
     * Dokument speichern
     * Speichert das Dokument wenn in der View "addDocument" ein Post ausgeführt wird
     * @param model Wird automatisch von Spring mitgegeben
     * @param document Wird aus dem Model vom Spring Framework ausgelesen
     * @return Weiterleitung auf die Seite "indexDocument"
     */
    @RequestMapping(value = {"/addDocument"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String saveDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.addDocument(document);//Speichert das Dokument über den EUmzugClientService

        return "redirect:/indexDocument";//Weiterleitung auf die Seite "indexDocument"

    }

     /***
     * Dokument anzeigen vor Lösung
     * Bereitet das Model für die Seite auf wo die Dokumente gelöscht werden können
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "deletDocument"
     */

    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showDeleteDocumentPage(Model model) {

        Document document = new Document();//Erstellung eines leeren Dokuments 
        model.addAttribute("document", document);//Setzten des Dokumens auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "deleteDocument";//Laden der View "deletDocument"
    }
    
    /***
     * Doument löschen
     * Löscht Doument beim Absenden des Formulars
     * @param model Wird automatisch von Spring mitgegeben
     * @param id ID des Dokuments
     * @return Weiterleitung auf View "IndexDocument"
     */

    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String deleteDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.deleteDocument(document.getDocumentId());//Löscht das Dokument im Backend

        return "redirect:/indexDocument";//Weiterleitung auf View "IndexDocument"

    }

    /**
     * BITTE TITEL UND ZWECK KOMMENTIEREN 
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "getDocumentByName"
     */
    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showGetDocumentByNamePage(Model model) {

        Document document = new Document();//Erstellung von leerem Dokument
        model.addAttribute("document", document);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getDocumentByName"; //Laden der View "getDocumentByName"
    }

    /**
     * BITTE TITEL UND ZWECK KOMMENTIEREN 
     * @param model
     * @param document
     * @return 
     */
    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String getDocumentByName(Model model, @ModelAttribute("document") Document document) {

        Document newDocument = eUmzugClientService.getDocumentByName(document.getName());//BITTE KOMMENTIEREN
        model.addAttribute("document", newDocument);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getDocumentByName";//Laden der View "getDocumentByName"
    }

     /***
     * Anzeige umbennater Dokumente
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "renameDocument"
     */
    @RequestMapping(value = {"/", "/renameDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showRenameDocumentPage(Model model) {

        Document document = new Document();//Erstellung leeres Dokument
        model.addAttribute("document", document);//Setzten des Dokumens auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "renameDocument";//Laden der View "renameDocument"
    }

     /*** 
     * Dokument Umbennenung
     * Bennt das Dokument um, wenn das Formular "RenameDocument" abgesendet wird
     * @param model Wird automatisch von Spring mitgegeben
     * @param Id ID des Dokuement , welche von View gesetzt wird
     * @param name Name des Dokuments, welches von der View gesetzt wird
     * @return Weiterleitung zur View "IndexDocument"
     */
    @RequestMapping(value = {"/renameDocument"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String renameDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.renameDocument(document.getDocumentId(), document.getName());//Umbennenung im Backend

        return "redirect:/indexDocument";//Weiterleitung zur View "IndexDocument"

    }

// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------GEMEINDEN---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    //GEMEINDEN
    /**
     * Liste der Gemeinden
     * Alle Gemeinden werden geholt und Liste wird ins Model gespeichert.
     * @param model Wird auomatisch von Spring mitgegeben
     * @return Gibt die aktuelle Seite zurück
     */

    @RequestMapping(value = {"/indexMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String getMunicipalityList(Model model) {

        List<Municipality> municipalityListe = eUmzugClientService.getMunicipalityList();        //Gemeinden von EUmzugClientService werden geholt und in "Liste" gespeichert.
        model.addAttribute("municipalityListe", municipalityListe); //Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "indexMunicipality";//Gibt die View zurück die geladen werden soll
    }

    /**
     * Gemeinde hinzufügen
     * @param model Wird automatisch von Spring mitgegeben
     * @return Gibt View zurück die geladen werden soll
     */
    @RequestMapping(value = {"/", "/addMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showAddMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();//Erstellung von leerem Dokument, welches and Model bereitgestellt wird, damit es die View verarbeiten kann
        model.addAttribute("municipality", municipality);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "addMunicipality";//Gibt View zurück die geladen werden soll
    }

    /**
     * Gemeinde speichern
     * Speichert die Gemeinde, wenn auf der Seite ein Post ausgeführt wird
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung auf die Seite "indexMuniciplaity"
     */
    @RequestMapping(value = {"/addMunicipality"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String saveMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.addMunicipality(municipality);//Speichert das Dokument über den EUmzugClientService

        return "redirect:/indexMunicipality";//Weiterleitung auf die Seite "indexMunicipality"
    }

    /**
     * Gemeinde anzeigen vor Löschung
     * Bereitet das Model für die Seite auf wo die Gemeinden gelöscht werden können
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "deletMunicipality"
     */
    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showDeleteMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();//Erstellung eines leeren Dokuments
        model.addAttribute("municipality", municipality);//Setzten des Dokumens auf das Model, damit beispielsweise die View darauf zugreifen kann

        
        return "deleteMunicipality";//Weiterleiten auf die angegebene View
    }

    /**
     * Gemeinde löschen
     * Löscht Gemeinde beim Absenden des Formulars
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return //Weiterleiten auf die angegebene View
     */
    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String deleteMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.deleteMunicipality(municipality.getMunicipalityId());//Löscht das Dokument im Backend

        return "redirect:/indexMunicipality";//Weiterleiten auf die angegebene View
    }

    /**
     * BITTE KOMMENTIEREN mit ZWECK UND TITEL
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View
     */
    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showGetMunicipalityByNamePage(Model model) {

        Municipality municipality = new Municipality();//Erstellung von leeren Gemeinde Attriut
        model.addAttribute("municipality", municipality);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getMunicipalityByName";//Laden der View
    }

    /**
     * BITTE ZWECK UND TITEL KOMMENTIEREN
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Laden der View
     */
    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String getMunicipalityByName(Model model, @ModelAttribute("municipality") Municipality municipality) {

        Municipality newMunicipality = eUmzugClientService.getMunicipalityByName(municipality.getMunicipalityName());//BITTE KOMMENTIEREN
        model.addAttribute("municipality", newMunicipality);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getMunicipalityByName";//Laden der View
    }

    /**
     * Anzeige unbennanter Dokumente
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der angegebenen View
     */
    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showRenameMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();//Erstellung leerer Gemeinde
        model.addAttribute("municipality", municipality);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "renameMunicipality"; //Laden der angegebenen View
    }

    /**
     * Umbennenung Gemeinde
     * Bennent die Gemeinde um, wenn das Formular "renameMunicipality" abgesendet wird
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung zur View "IndexMunicipality"
     */
    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String renameMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.renameMunicipality(municipality.getMunicipalityId(), municipality.getMunicipalityName()); //Umbennenung im Backend

        return "redirect:/indexMunicipality";//Weiterleitung zur View "IndexMunicipality"

    }

    /**
     * Umzugsgebühr anzeigen
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der angegebenen View "newFee"
     */
    @RequestMapping(value = {"/newFee"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showNewFeePage(Model model) {

        Municipality getFeeMove = new Municipality();//Erstellung leerer Gemeinde
        model.addAttribute("municipality", getFeeMove);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "newFee";
    }

    /**
     * Neue Umzugsgebühr
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung zur angegebenen View
     */
    @RequestMapping(value = {"/newFee"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String newFee(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.newFee(municipality.getMunicipalityId(), municipality.getFeeMove());//Setzten der Umzugsgebühr im Backend

        return "redirect:/indexMunicipality";//Weiterleitung zur angegebenen View

    }

    /**
     * Zuzugsgebühr anzeigen
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der angegebenen View "newFeeIn"
     */
    @RequestMapping(value = {"/newFeeIn"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showNewFeeInPage(Model model) {

        Municipality getFeeMoveIn = new Municipality();//Erstellung leerer Gemeinde
        model.addAttribute("municipality", getFeeMoveIn);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "newFeeIn";//Laden der angegebenen View "newFeeIn"
    }

    /**
     * Neue Zuzugsgebühr
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung zur angegebenen View
     */
    @RequestMapping(value = {"/newFeeIn"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String newFeeIn(Model model,
            @ModelAttribute("municipality") Municipality municipality
    ) {

        eUmzugClientService.newFeeIn(municipality.getMunicipalityId(), municipality.getFeeMoveIn());//Setzten der Zuzugsgebühr im Backend

        return "redirect:/indexMunicipality";//Weiterleitung zur angegebenen View
    }

    /**
     * Wegzugsgebühr anzeigen
     * @param model Wird automatisch von Spring mitgegeben
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showNewFeeOutPage(Model model) {

        Municipality getFeeMoveOut = new Municipality();//Erstellung leerer Gemeinde
        model.addAttribute("municipality", getFeeMoveOut);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "newFeeOut";//Laden der angegebenen View "newFeeIn"
    }

    /**
     * Neue Wegzugsgebühr
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String newFeeOut(Model model,
            @ModelAttribute("municipality") Municipality municipality
    ) {

        eUmzugClientService.newFeeOut(municipality.getMunicipalityId(), municipality.getFeeMoveOut());//Setzten der Wegzugsgebühr im Backend

        return "redirect:/indexMunicipality";//Weiterleitung zur angegeben View

    }

// ----------------------------------------------------------------------------------------------------------//
// --------------------------------------------TransactionLog------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
     /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/indexTransactionLog"}, method = RequestMethod.GET)
    public String getTransactionLog(Model model
    ) {
        return "indexTransactionLog";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/getPersonListForStatus"}, method = RequestMethod.GET)
    public String showGetPersonListForStatusPage(Model model) {

        Status status = new Status();
        model.addAttribute("status", status);

        return "getPersonListForStatus";
    }

    /**
     * 
     * @param model
     * @param status
     * @return 
     */
    @RequestMapping(value = {"/getPersonListForStatus"}, method = RequestMethod.POST)
    public String getPersonListForStatus(Model model, @ModelAttribute("status") Status status) {

        List<Person> personenListe = eUmzugClientService.getPersonListForStatus(status.getName());
        model.addAttribute("personenListe", personenListe);

        return "getPersonListForStatus";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/getCurrentStatusForPerson"}, method = RequestMethod.GET)
    public String showGetCurrentStatusForPersonPage(Model model) {

        Person person = new Person();
        model.addAttribute("person", person);

        TransactionLog transactionLog = new TransactionLog();
        model.addAttribute("transactionLog", transactionLog);

        return "getCurrentStatusForPerson";
    }

    /**
     * 
     * @param model
     * @param person
     * @return 
     */
    @RequestMapping(value = {"/getCurrentStatusForPerson"}, method = RequestMethod.POST)
    public String getCurrentStatusForPerson(Model model, @ModelAttribute("person") Person person) {

        TransactionLog transactionLog = eUmzugClientService.getCurrentStatusForPerson(person.getLocalPersonId());
        model.addAttribute("transactionLog", transactionLog);

        return "getCurrentStatusForPerson";
    }

}
