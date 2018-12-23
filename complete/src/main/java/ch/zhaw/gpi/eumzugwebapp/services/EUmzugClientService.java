package ch.zhaw.gpi.eumzugwebapp.services;

import ch.zhaw.gpi.eumzugwebapp.resources.Document;
import ch.zhaw.gpi.eumzugwebapp.resources.Municipality;
import ch.zhaw.gpi.eumzugwebapp.resources.Person;
import ch.zhaw.gpi.eumzugwebapp.resources.TransactionLog;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Klasse zuständig für die Kommunikation mit der EUmzug-Applikation. Sprich
 * Klasse ruft EUmzug Service via REST template auf. Anschliessend wird
 * Kartenelement aufgerufen und an CheckBaseInsuranceService übergeben. Zudem
 * Kann über PUT eine Adressänderung vorgenommen werden.
 *
 * @author VZa02
 */
@Service
public class EUmzugClientService {

    //Rest Template
    private final RestTemplate restTemplate;

    // Endpoint für Veka Service -> In application.properties
    @Value(value = "${eUmzugClientService.endpoint}")
    private String eUmzugClientServiceEndpoint;

    //Konstruktor
    public EUmzugClientService() {
        restTemplate = new RestTemplate();
    }

    //DOKUMENTE
    /**
     *
     * @return
     */
    public List<Document> getDocumentList() {
        try {
            ResponseEntity<List<Document>> documentListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/dokumente", HttpMethod.GET, null, new ParameterizedTypeReference<List<Document>>() {
            });

            //Gibt die Karte zurück
            return documentListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     *
     * @param name
     * @return
     */
    public Document getDocumentByName(String name) {
        try {
            Document document = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/dokumente/{name}", //API Link für Karte
                    Document.class, //Gibt resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    name
            );
            //Gibt die Karte zurück
            return document;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1/dokumente/dokument
     *
     * @param document
     */
    public void addDocument(Document document) {
        //das neue Dokument als RequestBody (dokument). Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/dokumente/dokument", document);
    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     * @param name
     */
    public void renameDocument(int Id, String name) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/dokumente/{Id}/{name}", Id, name);
    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     */
    public void deleteDocument(int Id) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/dokumente/{Id}", Id);
    }

    //TRANSACTIONLOGS 
    /**
     *
     * @param statusName
     * @return
     */
    public List<Person> getPersonListForStatus(String statusName) {
        try {
            HttpEntity<String> request = new HttpEntity<>(statusName);

            ResponseEntity<List<Person>> personListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/transactionlog/status/{statusName}", HttpMethod.GET, request, new ParameterizedTypeReference<List<Person>>() {
            });

            return personListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     *
     * @param localPersonId
     * @return
     */
    public TransactionLog getCurrentStatusForPerson(String localPersonId) {
        try {
            TransactionLog transactionLog = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/transactionlog/person/{localPersonId}", //API Link für Karte
                    TransactionLog.class, //Gibt resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    localPersonId
            );
            //Gibt die Karte zurück
            return transactionLog;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    //GEMEINDEN
    /**
     *
     * @return
     */
    public List<Municipality> getMunicipalityList() {
        try {
            ResponseEntity<List<Municipality>> municipalityListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/gemeinden", HttpMethod.GET, null, new ParameterizedTypeReference<List<Municipality>>() {
            });

            //Gibt die Karte zurück
            return municipalityListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     *
     * @param gemeindeName
     * @return
     */
    public Municipality getMunicipalityByName(String gemeindeName) {
        try {
            Municipality municipality = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/gemeinden/{gemeindeName}", //API Link für Karte
                    Municipality.class, //Gibt resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    gemeindeName
            );
            //Gibt die Karte zurück
            return municipality;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1/dokumente/dokument
     *
     * @param gemeinde
     */
    public void addMunicipality(Municipality gemeinde) {
        //das neue Dokument als RequestBody (dokument). Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/gemeinde", gemeinde);
    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     * @param name
     */
    public void renameMunicipality(int Id, String name) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{Id}/{name}", Id, name);
    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     */
    public void deleteMunicipality(int Id) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{Id}", Id);
    }

    /**
     *
     * @param id
     * @param gebuehr
     */
    public void newFee(int id, int gebuehr) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/move/{Gebuehr}", id, gebuehr);
    }

    /**
     *
     * @param id
     * @param gebuehr
     */
    public void newFeeIn(int id, int gebuehr) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/movein/{Gebuehr}", id, gebuehr);
    }

    /**
     *
     * @param id
     * @param gebuehr
     */
    public void newFeeOut(int id, int gebuehr) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/moveout/{Gebuehr}", id, gebuehr);
    }
}
