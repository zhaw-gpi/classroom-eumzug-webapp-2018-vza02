package ch.zhaw.gpi.eumzugwebapp.controller;

import java.util.List;
 
import ch.zhaw.gpi.eumzugwebapp.form.PersonForm;
import ch.zhaw.gpi.eumzugwebapp.resources.Municipality;
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
    
    //private static List<Person> persons = new ArrayList<Person>();
 
    static {
       // persons.add(new Person("Bill", "Gates"));
        //persons.add(new Person("Steve", "Jobs"));
    }

 
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
 
        model.addAttribute("message", "TEST");
 
        return "index";
    }
 
    //GEMEINDEN
    
    @RequestMapping(value = { "/getMunicipalityList" }, method = RequestMethod.GET)
    public String getMunicipalityList(Model model) {
 
        List<Municipality> municipalityListe = eUmzugClientService.getMunicipalityList();
        model.addAttribute("municipalityListe", eUmzugClientService);
 
        return "getMunicipalityList";
    }
 
    @RequestMapping(value = { "/", "/addMunicipality" }, method = RequestMethod.GET)
    public String showAddMunicipalityPage(Model model) {
 
        Municipality municipality = new Municipality(); 
        model.addAttribute("municipality", municipality);
 
        return "addMunicipality";
    }
 
    @RequestMapping(value = { "/addMunicipality" }, method = RequestMethod.POST)
    public String saveMunicipality(Model model, @ModelAttribute("municipality") Municipality municipality) {
 
        //String firstName = personForm.getFirstName();
        //String lastName = personForm.getLastName();
        
        Municipality gemeinde = new Municipality();
        
        gemeinde.setMunicipalityDocumentRelationEntities(null);
 
        eUmzugClientService.addMunicipality(gemeinde);

        return "redirect:/getMunicipalityList";
        
    }
 
}