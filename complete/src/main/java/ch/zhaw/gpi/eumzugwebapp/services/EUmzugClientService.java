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
 * Klasse zust�ndig f�r die Kommunikation mit der EUmzug-Applikation. Sprich
 * Klasse ruft EUmzug Service via REST template auf. Anschliessend wird
 * Kartenelement aufgerufen und an CheckBaseInsuranceService �bergeben. Zudem
 * Kann �ber PUT eine Adressänderung vorgenommen werden.
 *
 * @author VZa02
 */
/**
 * Annotation "@Service" f�r dependency incection in Spring, damit die Java
 * Klasse nicht als Spring Bean in der XML Konfiguration angegeben werden muss.
 * Sondern, sie wird durch die Annotation automatisch als Spring Bean erkannt,
 * w�hrend dem "Class-Path" Scanning.
 */
@Service
public class EUmzugClientService {

    //Rest Template
    private final RestTemplate restTemplate;

    // Endpoint f�r Veka Service -> In application.properties
    @Value(value = "${eUmzugClientService.endpoint}")
    private String eUmzugClientServiceEndpoint;

    //Konstruktor
    public EUmzugClientService() {
        restTemplate = new RestTemplate();
    }

    //DOKUMENTE
    /**
     * Methode gibt eine Liste von allen Dokumenten zur�ck.
     *
     * @return documentlists (List<Document>), bei einem
     * HttpClientErrorException wird NULL zuru�ckgeben
     */
    public List<Document> getDocumentList() {
        try {
            //Einen REST Request absetzten und fordert die Ressource "Dokumente" 
            //an, gleichzeitig wird der Response in die Variable dokumentListe gespeichert
            ResponseEntity<List<Document>> documentListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/dokumente", HttpMethod.GET, null, new ParameterizedTypeReference<List<Document>>() {
            });

            //Gibt die documentlists (List<Document>) aus dem Response als 
            //R�ckgabewert der Funktion aus.
            return documentListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gem�ss Anforderung nicht zur�ckgegeben 
            return null;    //Bei HTTP ERROR wird null zur�ckgegeben
        }

    }

    /**
     * Methode gibt eine Dokument zur�ck, von einem spezifischen Namen
     *
     * @param name Der Name des Dokuments das zur�ck gegeben werden sollte
     * @return Das Dokument welches �ber den Param "name" gesucht wird, bei
     * einer httpClientErrorEception wird der Wert NULL zur�ckgegeben
     */
    public Document getDocumentByName(String name) {
        try {
            //Holt ber einen REST Request das �ber den Namen gesuchte Dokument
            Document document = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/dokumente/{name}", // Abkl�ren mit Marvin was 
                    Document.class, //Gib Typ von Dokument Class zur�ck
                    name //Parameter "name" welcher beim Funktionsaufruf angegeben wird, wird an den Rest-Request weitergegeben,
            //damit der Restservice nach dem Dokument mit diesem Namen suchen kann.
            );
            //Gibt das Dokument zur�ck
            return document;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gem�ss Anforderung nicht zur�ckgegeben
            return null;    //Bei HTTP ERROR wird null zur�ckgegeben
        }

    }

    /**
     * sendet �ber eine PUT-Anfrage an eumzugapi/v1/dokumente/dokument
     *
     * @param document
     */
    public void addDocument(Document document) {
        //das neue Dokument als RequestBody (dokument). Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/dokumente/dokument", document);
    }

    /**
     * sendet �ber eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     * @param name
     */
    public void renameDocument(int Id, String name) {
        restTemplate.put(eUmzugClientServiceEndpoint + "/dokumente/{Id}/{name}", "", Id, name);
    }

    /**
     * sendet �ber eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     */
    public void deleteDocument(int Id) {
        restTemplate.delete(eUmzugClientServiceEndpoint + "/dokumente/{Id}", Id);
    }

    //TRANSACTIONLOGS 
    /**
     * Diese Methode gibt eine Liste von Personen zur�ck (List<Person>), welche
     * einen TransactionLog Eintrag mit dem angegeben Status haben.
     *
     * @param statusName Der Status nachdem im TransactionLog gefiltert wird.
     * @return Liste von Personen, bei der HTTP ERROR wird NULL zur�ckgegeben
     * oder wenn kein Eintrag gefunden wird.
     */
    public List<Person> getPersonListForStatus(String statusName) {
        try {
                     
            //F�hrt einen REST Request aus und holt sich alle Personen wo es 
            //einen TransactionLog Eintrag mit dem Status aus dem Request gibt.
            ResponseEntity<List<Person>> personListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/transactionlog/status/{statusName}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
            }, statusName);

            //Gibt die Liste der Personen (List<Person>) aus dem Response als 
            //R�ckgabewert der Funktion aus.
            return personListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gem�ss Anforderung nicht zur�ckgegeben
            return null;    //Bei HTTP ERROR wird null zur�ckgegeben
        }

    }

    /**
     * Gibt den TransactionLog einer angegeben Person zur�ck.
     *
     * @param localPersonId Die ID der Person f�r wen der TransactionLog
     * zur�ckgegeben werden sollte.
     * @return TransactionLog, bei der HTTP ERROR wird NULL zur�ckgegeben oder
     * wenn kein Eintrag gefunden wird.
     */
    public TransactionLog getCurrentStatusForPerson(String localPersonId) {
        try {
            //F�hrt einen REST Request aus und holt sich den TransactionLog einer Person
            TransactionLog transactionLog = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/transactionlog/person/{localPersonId}", //API
                    TransactionLog.class, //Gibt Resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    localPersonId //PersonenID nach der gesucht werden sollte
            );
            //Gibt den TransactionLog zurück
            return transactionLog;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gem�ss Anforderung nicht zur�ckgegeben
            return null;    //Bei HTTP ERROR wird null zur�ckgegeben
        }

    }

    //GEMEINDEN
    /**
     * Methode gibt eine Liste von allen Gemeinden zur�ck.
     *
     * @return documentlists (List<Municipality>), bei einem
     * HttpClientErrorException wird NULL zurückgeben
     */
    public List<Municipality> getMunicipalityList() {
        try {
            //Einen REST Request absetzten und fordert die Ressource "Gemeinde" 
            //an, gleichzeitig wird der Rsponse in die Variable dokumentListe gespeichert
            ResponseEntity<List<Municipality>> municipalityListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/gemeinden", HttpMethod.GET, null, new ParameterizedTypeReference<List<Municipality>>() {
            });

            //Gibt die Liste von Gemeinden (List<Municiplaity>) aus dem Response als 
            //R�ckgabewert der Funktion aus.
            return municipalityListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zur�ckgegeben
            return null;    //Bei HTTP ERROR wird null zur�ckgegeben
        }

    }

    /**
     * Methode gibt eine Dokument zur�ck, von einem spezifischen Namen
     *
     * @param gemeindeName Der Name der Gemeinde das zurück gegeben werden
     * sollte
     * @return Die Gemeinde welches über den Param "gemeindeName" gesucht wird,
     * bei einer httpClientErrorEception wird der Wert NULL zurückgegeben
     */
    public Municipality getMunicipalityByName(String gemeindeName) {
        try {
            //Holt �ber einen REST Request die �ber den Namen gesuchte Gemeinde
            Municipality municipality = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/gemeinden/{gemeindeName}",
                    Municipality.class, //Gibt Resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    gemeindeName //Parameter "gemeindeName" welcher beim Funktionsaufruf angegeben wird, wird an den Restrequest weitergegeben,
            //damit der Restservice nach dem Dokument mit diesem Namen suchen kann.
            );
            //Gibt die Gemeinde zur�ck
            return municipality;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gem�ss Anforderung nicht zur�ckgegeben
            return null;    //Bei HTTP ERROR wird null zur�ckgegeben
        }

    }

    /**
     * //Erstellt die neue Gemeinde via Put.
     *
     * @param gemeinde
     */
    public void addMunicipality(Municipality gemeinde) {
        //Sendet �ber eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/gemeinde", gemeinde);
    }

    /**
     * Bennent die Gemeinde um via Put.
     *
     * @param Id
     * @param name
     */
    public void renameMunicipality(int Id, String name) {
        //sendet �ber eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{Id}/{name}", "", Id, name);
    }

    /**
     * L�scht die Gemeinde mit der angegbenen ID.
     *
     * @param Id
     */
    public void deleteMunicipality(int Id) {
        //sendet �ber eine PUT-Anfrage an eumzugapi/v1//gemeinde/{Id}/{name}
        restTemplate.delete(eUmzugClientServiceEndpoint + "/gemeinden/{Id}", Id);
    }

    /**
     * Erstellt neue Geb�hr f�r eine Gemeinde �ber die angegebene ID und der
     * angegebenen Geb�hr
     *
     * @param id Das ist die ID der Gemeinde
     * @param gebuehr Der Wert der Geb�hr
     */
    public void newFee(int id, int gebuehr) {
        //Sendet �ber eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/move/{Gebuehr}", "", id, gebuehr);
    }

    /**
     * Zugugsgeb�hr wird erstellt �ber die angegebene ID und der angegebenen
     * Geb�hr
     *
     * @param id Das ist die ID der Gemeinde
     * @param gebuehr Der Wert der Gef�hr
     */
    public void newFeeIn(int id, int gebuehr) {
        //Sendet �ber eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/movein/{Gebuehr}", "", id, gebuehr);
    }

    /**
     * Wegzugsgeb�hr wird erstellt �ber die angegebene ID und der angegebenen
     * Geb�hr
     *
     * @param id Das ist die ID der Gemeinde
     * @param gebuehr Der Wert der Geb�hr
     */
    public void newFeeOut(int id, int gebuehr) {
        //Sendet �ber eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/moveout/{Gebuehr}", "", id, gebuehr);
    }
}
