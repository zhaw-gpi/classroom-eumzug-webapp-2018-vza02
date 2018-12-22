package ch.zhaw.gpi.eumzugwebapp.controller;

import java.util.List;

import ch.zhaw.gpi.eumzugwebapp.resources.Municipality;
import ch.zhaw.gpi.eumzugwebapp.resources.Document;
import ch.zhaw.gpi.eumzugwebapp.services.EUmzugClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @RequestMapping(value = {"/indexDocument"}, method = RequestMethod.GET)
    public String getDocumentList(Model model) {

        List<Document> documentListe = eUmzugClientService.getDocumentList();
        model.addAttribute("documentListe", documentListe);

        return "indexDocument";
    }

    //Dokument hinzufügen
    @RequestMapping(value = {"/", "/addDocument"}, method = RequestMethod.GET)
    public String showAddDocumentPage(Model model) {

        Document document = new Document();
        model.addAttribute("document", document);

        return "addDocument";
    }

    @RequestMapping(value = {"/addDocument"}, method = RequestMethod.POST)
    public String saveDocument(Model model, @ModelAttribute("document") Document document) {

        eUmzugClientService.addDocument(document);

        return "redirect:/getDocumentList";

    }

    @RequestMapping(value = {"/deleteDocument"}, method = RequestMethod.POST)
    public String deleteDocument(Model model, @ModelAttribute("id") int id) {

        eUmzugClientService.deleteDocument(id);

        return "redirect:/getDocumentList";

    }

    @RequestMapping(value = {"/getDocumentByName"}, method = RequestMethod.GET)
    public String getDocumentByName(Model model, @ModelAttribute("document") String name) {

        eUmzugClientService.getDocumentByName(name);

        return "getDocumentList";
    }

    @RequestMapping(value = {"/renameDocument"}, method = RequestMethod.POST)
    public String renameDocument(Model model, @ModelAttribute("id") int Id, @ModelAttribute("name") String name) {

        eUmzugClientService.renameDocument(Id, name);

        return "redirect:/getDocumentList";

    }

// ----------------------------------------------------------------------------------------------------------//
// ----------------------------------------------GEMEINDEN---------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    //GEMEINDEN
    @RequestMapping(value = {"/indexMunicipality"}, method = RequestMethod.GET)
    public String getMunicipalityList(Model model) {

        List<Municipality> municipalityListe = eUmzugClientService.getMunicipalityList();
        model.addAttribute("municipalityListe", municipalityListe);

        return "indexMunicipality";
    }

    @RequestMapping(value = {"/", "/addMunicipality"}, method = RequestMethod.GET)
    public String showAddMunicipalityPage(Model model) {

        Municipality municipality = new Municipality();
        model.addAttribute("municipality", municipality);

        return "addMunicipality";
    }

    @RequestMapping(value = {"/addMunicipality"}, method = RequestMethod.POST)
    public String saveMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {

        eUmzugClientService.addMunicipality(municipality);

        return "redirect:/indexMunicipality";
    }

    @RequestMapping(value = {"/deleteMunicipality"}, method = RequestMethod.POST)
    public String deleteMunicipality(Model model, @ModelAttribute("id") int Id) {

        eUmzugClientService.deleteMunicipality(Id);

        return "redirect:/indexMunicipality";

    }

    @RequestMapping(value = {"/getMunicipalityByName"}, method = RequestMethod.GET)
    public String getMunicipalityByName(Model model, @ModelAttribute("municipality") String name) {

        eUmzugClientService.getMunicipalityByName(name);

        return "getDocumentList";
    }

    @RequestMapping(value = {"/renameMunicipality"}, method = RequestMethod.POST)
    public String renameMunicipality(Model model, @ModelAttribute("id") int Id, @ModelAttribute("name") String name) {

        eUmzugClientService.renameMunicipality(Id, name);

        return "redirect:/getDocumentList";

    }

    @RequestMapping(value = {"/newFee"}, method = RequestMethod.POST)
    public String newFee(Model model, @ModelAttribute("id") int Id, @ModelAttribute("gebuehr") int gebuehr) {

        eUmzugClientService.newFee(Id, gebuehr);

        return "redirect:/getDocumentList";

    }

    @RequestMapping(value = {"/newFeeIn"}, method = RequestMethod.POST)
    public String newFeeIn(Model model, @ModelAttribute("id") int Id, @ModelAttribute("gebuehr") int gebuehr) {

        eUmzugClientService.newFeeIn(Id, gebuehr);

        return "redirect:/getDocumentList";

    }

    @RequestMapping(value = {"/newFeeOut"}, method = RequestMethod.POST)
    public String newFeeOut(Model model, @ModelAttribute("id") int Id, @ModelAttribute("gebuehr") int gebuehr) {

        eUmzugClientService.newFeeOut(Id, gebuehr);

        return "redirect:/getDocumentList";

    }

// ----------------------------------------------------------------------------------------------------------//
// --------------------------------------------TransactionLog------------------------------------------------//
// ----------------------------------------------------------------------------------------------------------//
    @RequestMapping(value = {"/getPersonListForStatus"}, method = RequestMethod.GET)
    public String getPersonListForStatus(Model model, @ModelAttribute("localPersonId") String localPersonId) {

        eUmzugClientService.getPersonListForStatus(localPersonId);

        return "getPersonListForStatus";
    }

    @RequestMapping(value = {"/getCurrentStatusForPerson"}, method = RequestMethod.GET)
    public String getCurrentStatusForPerson(Model model, @ModelAttribute("localPersonId") String localPersonId) {

        eUmzugClientService.getCurrentStatusForPerson(localPersonId);

        return "getCurrentStatusForPerson";
    }

}
