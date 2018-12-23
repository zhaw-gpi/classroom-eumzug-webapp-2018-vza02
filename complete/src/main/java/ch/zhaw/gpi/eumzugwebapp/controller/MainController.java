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

    @Autowired
    private EUmzugClientService eUmzugClientService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }
// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------DOKUMENTE---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//

    //Dokumentenliste
    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/indexDocument"}, method = RequestMethod.GET)
    public String getDocumentList(Model model) {

        List<Document> documentListe = eUmzugClientService.getDocumentList();
        model.addAttribute("documentListe", documentListe);

        return "indexDocument";
    }

    //Dokument hinzufügen
    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/", "/addDocument"}, method = RequestMethod.GET)
    public String showAddDocumentPage(Model model) {

        Document document = new Document();
        model.addAttribute("document", document);

        return "addDocument";
    }

    /**
     * 
     * @param model
     * @param document
     * @return 
     */
    @RequestMapping(value = {"/addDocument"}, method = RequestMethod.POST)
    public String saveDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.addDocument(document);

        return "redirect:/indexDocument";

    }

    //Dokument löschen
    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/", "/deleteDocument"}, method = RequestMethod.GET)
    public String showDeleteDocumentPage(Model model) {

        Document document = new Document();
        model.addAttribute("document", document);

        return "deleteDocument";
    }

    /**
     * 
     * @param model
     * @param document
     * @return 
     */
    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.POST)
    public String deleteDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.deleteDocument(document.getDocumentId());

        return "redirect:/indexDocument";

    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.GET)
    public String showGetDocumentByNamePage(Model model) {

        Document document = new Document();
        model.addAttribute("document", document);

        return "getDocumentByName";
    }

    /**
     * 
     * @param model
     * @param document
     * @return 
     */
    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.POST)
    public String getDocumentByName(Model model, @ModelAttribute("document") Document document) {

        Document newDocument = eUmzugClientService.getDocumentByName(document.getName());
        model.addAttribute("document", newDocument);

        return "getDocumentByName";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/", "/renameDocument"}, method = RequestMethod.GET)
    public String showRenameDocumentPage(Model model) {

        Document document = new Document();
        model.addAttribute("document", document);

        return "renameDocument";
    }

    /**
     * 
     * @param model
     * @param document
     * @return 
     */
    @RequestMapping(value = {"/renameDocument"}, method = RequestMethod.POST)
    public String renameDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.renameDocument(document.getDocumentId(), document.getName());

        return "redirect:/indexDocument";

    }

// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------GEMEINDEN---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    //GEMEINDEN
    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/indexMunicipality"}, method = RequestMethod.GET)
    public String getMunicipalityList(Model model) {

        List<Municipality> municipalityListe = eUmzugClientService.getMunicipalityList();
        model.addAttribute("municipalityListe", municipalityListe);

        return "indexMunicipality";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/", "/addMunicipality"}, method = RequestMethod.GET)
    public String showAddMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();
        model.addAttribute("municipality", municipality);

        return "addMunicipality";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/addMunicipality"}, method = RequestMethod.POST)
    public String saveMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.addMunicipality(municipality);

        return "redirect:/indexMunicipality";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.GET)
    public String showDeleteMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();
        model.addAttribute("municipality", municipality);

        return "deleteMunicipality";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.POST)
    public String deleteMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.deleteMunicipality(municipality.getMunicipalityId());

        return "redirect:/indexMunicipality";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.GET)
    public String showGetMunicipalityByNamePage(Model model) {

        Municipality municipality = new Municipality();
        model.addAttribute("municipality", municipality);

        return "getMunicipalityByName";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.POST)
    public String getMunicipalityByName(Model model, @ModelAttribute("municipality") Municipality municipality) {

        Municipality newMunicipality = eUmzugClientService.getMunicipalityByName(municipality.getMunicipalityName());
        model.addAttribute("municipality", newMunicipality);

        return "getMunicipalityByName";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.GET)
    public String showRenameMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();
        model.addAttribute("municipality", municipality);

        return "renameMunicipality";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.POST)
    public String renameMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.renameMunicipality(municipality.getMunicipalityId(), municipality.getMunicipalityName());

        return "redirect:/indexMunicipality";

    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/newFee"}, method = RequestMethod.GET)
    public String showNewFeePage(Model model) {

        Municipality getFeeMove = new Municipality();
        model.addAttribute("municipality", getFeeMove);

        return "newFee";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/newFee"}, method = RequestMethod.POST)
    public String newFee(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.newFee(municipality.getMunicipalityId(), municipality.getFeeMove());

        return "redirect:/indexMunicipality";

    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/newFeeIn"}, method = RequestMethod.GET)
    public String showNewFeeInPage(Model model) {

        Municipality getFeeMoveIn = new Municipality();
        model.addAttribute("municipality", getFeeMoveIn);

        return "newFeeIn";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/newFeeIn"}, method = RequestMethod.POST)
    public String newFeeIn(Model model,
            @ModelAttribute("municipality") Municipality municipality
    ) {

        eUmzugClientService.newFeeIn(municipality.getMunicipalityId(), municipality.getFeeMoveIn());

        return "redirect:/indexMunicipality";
    }

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.GET)
    public String showNewFeeOutPage(Model model) {

        Municipality getFeeMoveOut = new Municipality();
        model.addAttribute("municipality", getFeeMoveOut);

        return "newFeeOut";
    }

    /**
     * 
     * @param model
     * @param municipality
     * @return 
     */
    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.POST)
    public String newFeeOut(Model model,
            @ModelAttribute("municipality") Municipality municipality
    ) {

        eUmzugClientService.newFeeOut(municipality.getMunicipalityId(), municipality.getFeeMoveOut());

        return "redirect:/indexMunicipality";

    }

// ----------------------------------------------------------------------------------------------------------//
// --------------------------------------------TransactionLog------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    //Dokumentenliste
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
