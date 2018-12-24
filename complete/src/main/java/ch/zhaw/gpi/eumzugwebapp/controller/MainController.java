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

/**
 * Die Klasse ist für alle Abfragen zuständig. Sie leitet den jeweiligen Link
 * weiter und füllt die htmls mit Inhalt. Es gibt pro HTML eine GET-Methode mit
 * dem Namen show.....Page, welche das HTML mit leeren Inhalt füllt z.B. einem
 * Objekt. Ausserdem gibt es eine POST Methode zu jedem HTML file, welche
 * aufgerufen wird, wenn auf dem HTML ein Button geklickt wird. Die Methoden
 * rufen über den eUmzugClientService die API von der EUmzug Plattform auf.
 *
 * @author VZa02
 */
@Controller
public class MainController {

    // Entsprechende Bean wird instanziert, Referenz ist vorhanden
    @Autowired
    private EUmzugClientService eUmzugClientService;

    /**
     * Startseite der Webapp
     *
     * @param model
     * @return Gibt View zurück die geladen werden soll
     */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------DOKUMENTE---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    /**
     * Dokumentenliste. Alle Dokumente werden geholt und Liste wird ins Model
     * gespeichert.
     *
     * @param model Wird auomatisch von Spring mitgegeben
     * @return Gibt die aktuelle Seite zurück
     */
    @RequestMapping(value = {"/indexDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String getDocumentList(Model model) {

        List<Document> documentListe = eUmzugClientService.getDocumentList();//Dokumente von eUmzugService werden geholt und in "documentListe" gespeichert.
        model.addAttribute("documentListe", documentListe);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "indexDocument";//Gibt die View zurück die geladen werden soll
    }

    /**
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

    /**
     * Dokument speichern Speichert das Dokument wenn in der View "addDocument"
     * ein Post ausgeführt wird
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param document Wird aus dem Model vom Spring Framework ausgelesen
     * @return Weiterleitung auf die Seite "indexDocument"
     */
    @RequestMapping(value = {"/addDocument"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Formulars
    public String saveDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.addDocument(document);//Speichert das Dokument über den EUmzugClientService

        return "redirect:/indexDocument";//Weiterleitung auf die Seite "indexDocument"

    }

    /**
     * Dokument anzeigen vor Lösung Bereitet das Model für die Seite auf wo die
     * Dokumente gelöscht werden können
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "deletDocument"
     */
    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showDeleteDocumentPage(Model model) {

        Document document = new Document();//Instanzierung eines leeren Dokuments 
        model.addAttribute("document", document);//Setzten des Dokumens auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "deleteDocument";//Laden der View "deletDocument"
    }

    /**
     * Doument löschen. Löscht Doument beim Absenden des Formulars
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param document Das Dokument mit Inhalt von der ID
     * @return Weiterleitung auf View "IndexDocument"
     */
    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String deleteDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.deleteDocument(document.getDocumentId());//Löscht das Dokument im Backend über die Dokument-ID

        return "redirect:/indexDocument";//Weiterleitung auf View "IndexDocument"

    }

    /**
     * Gibt die Anfangsview zurück, um ein Dokument nach seinem Namen zu suchen
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "getDocumentByName"
     */
    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showGetDocumentByNamePage(Model model) {

        Document document = new Document();//Instanzieren von leerem Dokument
        model.addAttribute("document", document);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getDocumentByName"; //Laden der View "getDocumentByName"
    }

    /**
     * Sucht ein Dokument anhand seines Namens
     *
     * @param model
     * @param document
     * @return
     */
    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String getDocumentByName(Model model, @ModelAttribute("document") Document document) {

        Document newDocument = eUmzugClientService.getDocumentByName(document.getName());//Sucht ein Dokument anhand seines Namens
        model.addAttribute("document", newDocument);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getDocumentByName";//Laden der View "getDocumentByName"
    }

    /**
     * Anzeige um Dokumente umzubenennen
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "renameDocument"
     */
    @RequestMapping(value = {"/", "/renameDocument"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showRenameDocumentPage(Model model) {

        Document document = new Document();//Instanzierung von leerem Dokument
        model.addAttribute("document", document);//Setzten des Dokumens auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "renameDocument";//Laden der View "renameDocument"
    }

    /**
     * Dokument Umbennenung Benennt das Dokument um, wenn das Formular
     * "RenameDocument" abgesendet wird
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param document Dokument, welches eine ID und einen Namen beinhaltet
     * @return Weiterleitung zur View "IndexDocument"
     */
    @RequestMapping(value = {"/renameDocument"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String renameDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.renameDocument(document.getDocumentId(), document.getName());//Umbennenung im Backend über die ID und den neuen Namen

        return "redirect:/indexDocument";//Weiterleitung zur View "IndexDocument"
    }

// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------GEMEINDEN---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    /**
     * Liste der Gemeinden. Alle Gemeinden werden geholt und Liste wird ins
     * Model gespeichert.
     *
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
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Gibt View zurück die geladen werden soll
     */
    @RequestMapping(value = {"/", "/addMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showAddMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();//Instanzierung von leerer Gemeinde, welches Model bereitgestellt wird, damit es die View verarbeiten kann
        model.addAttribute("municipality", municipality);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "addMunicipality";//Gibt View zurück die geladen werden soll
    }

    /**
     * Gemeinde speichern Speichert die Gemeinde, wenn auf der Seite ein Post
     * ausgeführt wird
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung auf die Seite "indexMuniciplaity"
     */
    @RequestMapping(value = {"/addMunicipality"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Formulars
    public String saveMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.addMunicipality(municipality);//Speichert die Gemeinde über den EUmzugClientService

        return "redirect:/indexMunicipality";//Weiterleitung auf die Seite "indexMunicipality"
    }

    /**
     * Gemeinde anzeigen vor Löschung Bereitet das Model für die Seite auf wo
     * die Gemeinden gelöscht werden können
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View "deletMunicipality"
     */
    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showDeleteMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();//Instanzierung einer leeren Gemeinde
        model.addAttribute("municipality", municipality);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "deleteMunicipality";//Weiterleiten auf die angegebene View
    }

    /**
     * Gemeinde löschen. Löscht Gemeinde beim Absenden des Formulars
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return //Weiterleiten auf die angegebene View
     */
    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Formulars
    public String deleteMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.deleteMunicipality(municipality.getMunicipalityId());//Löscht die Gemeinde im Backend über die ID

        return "redirect:/indexMunicipality";//Weiterleiten auf die angegebene View
    }

    /**
     * Anzeige um Gemeinden umzubenennen
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der View
     */
    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showGetMunicipalityByNamePage(Model model) {

        Municipality municipality = new Municipality();//Instanzierung von leeren Gemeinde Objekt
        model.addAttribute("municipality", municipality);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getMunicipalityByName";//Laden der View
    }

    /**
     * Sucht eine Gemeinde anhand des Namens
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Laden der View
     */
    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String getMunicipalityByName(Model model, @ModelAttribute("municipality") Municipality municipality) {

        Municipality newMunicipality = eUmzugClientService.getMunicipalityByName(municipality.getMunicipalityName()); //Sucht eine Gemeinde anhand des Namens
        model.addAttribute("municipality", newMunicipality);//Das Model wird mit Attributen befüllt damit die View auf diese Attribute zugreifen kann

        return "getMunicipalityByName";//Laden der View
    }

    /**
     * Anzeige um Gemeinden umzubennennen
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Laden der angegebenen View
     */
    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showRenameMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();//Instanzierung leerer Gemeinde
        model.addAttribute("municipality", municipality);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "renameMunicipality"; //Laden der angegebenen View
    }

    /**
     * Umbennenung Gemeinde. Bennent die Gemeinde um, wenn das Formular
     * "renameMunicipality" abgesendet wird
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung zur View "IndexMunicipality"
     */
    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String renameMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.renameMunicipality(municipality.getMunicipalityId(), municipality.getMunicipalityName()); //Umbennenung im Backend über die ID und des neuen Namens

        return "redirect:/indexMunicipality";//Weiterleitung zur View "IndexMunicipality"

    }

    /**
     * Umzugsgebühr anzeigen
     *
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
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung zur angegebenen View
     */
    @RequestMapping(value = {"/newFee"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String newFee(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.newFee(municipality.getMunicipalityId(), municipality.getFeeMove());//Setzten der Umzugsgebühr im Backend über die ID und der neuen Gebühr

        return "redirect:/indexMunicipality";//Weiterleitung zur angegebenen View

    }

    /**
     * Zuzugsgebühr anzeigen
     *
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
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality Wird automatisch von Spring mitgegeben
     * @return Weiterleitung zur angegebenen View
     */
    @RequestMapping(value = {"/newFeeIn"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String newFeeIn(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.newFeeIn(municipality.getMunicipalityId(), municipality.getFeeMoveIn());//Setzten der Zuzugsgebühr im Backend über die ID und der neuen Gebühr

        return "redirect:/indexMunicipality";//Weiterleitung zur angegebenen View
    }

    /**
     * Wegzugsgebühr anzeigen
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.GET)//Mappt die Funktion mit dem URL Aufruf
    public String showNewFeeOutPage(Model model) {

        Municipality getFeeMoveOut = new Municipality();//Instanzierung leerer Gemeinde
        model.addAttribute("municipality", getFeeMoveOut);//Setzten der Gemeinde auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "newFeeOut";//Laden der angegebenen View "newFeeIn"
    }

    /**
     * Neue Wegzugsgebühr
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param municipality
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.POST)//Mappt die Funktion mit dem Absenden eines Forulars
    public String newFeeOut(Model model,
            @ModelAttribute("municipality") Municipality municipality
    ) {

        eUmzugClientService.newFeeOut(municipality.getMunicipalityId(), municipality.getFeeMoveOut());//Setzten der Wegzugsgebühr im Backend über die ID und der neuen Gebühr

        return "redirect:/indexMunicipality";//Weiterleitung zur angegeben View

    }

// ----------------------------------------------------------------------------------------------------------//
// --------------------------------------------TransactionLog------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    /**
     * Leitet auf die Übersichtsseite der TransactionLogs
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/indexTransactionLog"}, method = RequestMethod.GET)
    public String getTransactionLog(Model model) {
        return "indexTransactionLog"; //Laden der angegebenen View "indexTransactionLog"
    }

    /**
     * Leitet auf die View, um eine PersonenListe anhand eines Statuses zu
     * finden
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/getPersonListForStatus"}, method = RequestMethod.GET)
    public String showGetPersonListForStatusPage(Model model) {

        Status status = new Status(); //Instanzierung eines neuen Objekts
        model.addAttribute("status", status);//Setzten des Status auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "getPersonListForStatus"; //Laden der angegebenen View "getPersonListForStatus"
    }

    /**
     * Sucht die PersonenListe, welche einen gewissen Status haben
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param status
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/getPersonListForStatus"}, method = RequestMethod.POST)
    public String getPersonListForStatus(Model model, @ModelAttribute("status") Status status) {

        List<Person> personenListe = eUmzugClientService.getPersonListForStatus(status.getName()); //PersonenListe anhand des Statuses um EUmzug auslesen
        model.addAttribute("personenListe", personenListe); // Liste der View mitgeben

        return "getPersonListForStatus"; //Laden der angegebenen View "getPersonListForStatus"
    }

    /**
     * Leitet auf die View, um einen aktuellen Status für eine Person zu suchen
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/getCurrentStatusForPerson"}, method = RequestMethod.GET)
    public String showGetCurrentStatusForPersonPage(Model model) {

        Person person = new Person(); //Instanzierung eines neuen Objekts
        model.addAttribute("person", person); // Hinzufügen einer Person, damit beispielsweise die View darauf zugreifen kann

        TransactionLog transactionLog = new TransactionLog(); //Instanzierung eines neuen Objekts
        Status status = new Status(); //Instanzierung eines neuen Objekts
        transactionLog.setStatus(status); // Status setzen
        transactionLog.setPerson(person); // Person setzen
        model.addAttribute("transactionLog", transactionLog); // Hinzufügen des Transaction Log, damit beispielsweise die View darauf zugreifen kann

        return "getCurrentStatusForPerson"; //Laden der angegebenen View "getCurrentStatusForPerson"
    }

    /**
     * Sucht den aktuellen Status für eine Person
     *
     * @param model Wird automatisch von Spring mitgegeben
     * @param person
     * @return Anzeige der angegebenen View
     */
    @RequestMapping(value = {"/getCurrentStatusForPerson"}, method = RequestMethod.POST)
    public String getCurrentStatusForPerson(Model model, @ModelAttribute("person") Person person) {

        TransactionLog transactionLog = eUmzugClientService.getCurrentStatusForPerson(person.getLocalPersonId()); //Sucht den aktuellen Status für eine Person
        model.addAttribute("transactionLog", transactionLog); //Setzten des TransactionLogs auf das Model, damit beispielsweise die View darauf zugreifen kann

        return "getCurrentStatusForPerson"; //Laden der angegebenen View "getCurrentStatusForPerson"
    }

}
