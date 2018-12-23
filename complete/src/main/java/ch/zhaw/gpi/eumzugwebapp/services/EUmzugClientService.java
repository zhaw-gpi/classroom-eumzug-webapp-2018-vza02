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
/**
 * Annotation "@Service" für dependency incection in Spring, damit die Java
 * Klasse nicht als Spring Bean in der XML Konfiguration angegeben werden muss.
 * Sondern, sie wird durch die Annotation automatisch als Spring Bean erkannt,
 * während dem "Class-Path" Scanning.
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
     *Methode gibt eine Liste von allen Dokumenten zurück.  
     * @return documentlists (List<Document>), bei einem HttpClientErrorException wird NULL zuruückgeben
     */
    public List<Document> getDocumentList() {
        try {
            //Einen REST Request absetzten und fordert die Ressource "Dokumente" 
            //an, gleichzeitig wird der Rsponse in die Variable dokumentListe gespeichert
            ResponseEntity<List<Document>> documentListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/dokumente", HttpMethod.GET, null,              new ParameterizedTypeReference<List<Document>>() {
            });

            //Gibt die documentlists (List<Document>) aus dem Response als 
            //Rückgabewert der Funktion aus.
            return documentListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben 
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     *Methode gibt eine Dokument zurück, von einem spezifischen Namen
     * @param name Der Name des Dokuments das zurück gegeben werden sollte
     * @return Das Dokument welches über den Param "name" gesucht
     * wird, bei einer httpClientErrorEception wird der Wert NULL zurückgegeben
     */
    public Document getDocumentByName(String name) {
        try {
            //Holt über einen REST Request das über den Namen gesuchte Dokument
            Document document = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/dokumente/{name}", // Abklären mit Marvin was 
                    Document.class, //Gib Typ von Dokeumnt Class zuruück
                    name //Parameter "name" welcher beim Funktionsaufruf angegeben wird, wird an den Restrequest weitergegeben,
                    //damit der Restservice nach dem Dokument mit diesem Namen suchen kann.
            );
            //Gibt das Dokument zurück
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
        restTemplate.put(eUmzugClientServiceEndpoint + "/dokumente/{Id}/{name}", "", Id, name);
    }

    /**
     * sendet über eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
     *
     * @param Id
     */
    public void deleteDocument(int Id) {
        restTemplate.delete(eUmzugClientServiceEndpoint + "/dokumente/{Id}", Id);
    }
    
   

    //TRANSACTIONLOGS 
    /**
     *Diese Methode gibt eine Liste von Personen zurück (List<Person>), welche 
     * einen TransactionLog Eintrag mit dem angegeben Status haben.
     * @param statusName Der Status nachdem im TransactionLog gefiltert wird.
     * @return Liste von Personen, bei der HTTP ERROR wird NULL zurückgegeben 
     * oder wenn kein Eintrag gefunden wird.
     */
    public List<Person> getPersonListForStatus(String statusName) {
        try {
            //Erstellt ein HttpEntity für einen Request mit dem angegebenen Body "statusName" und
            //ohne Header.
            HttpEntity<String> request = new HttpEntity<>(statusName);

            //Führt einen REST Request aus und holt sich alle Personen wo es 
            //einen TransactionLog Eintrag mit dem Status aus dem Request gibt.
            ResponseEntity<List<Person>> personListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/transactionlog/status/{statusName}", HttpMethod.GET, request, new ParameterizedTypeReference<List<Person>>() {
            });
            
            //Gibt die Liste der Personen (List<Person>) aus dem Response als 
            //Rückgabewert der Funktion aus.
            return personListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     *Gibt den TransactionLog einer angegeben Person zurück.
     * @param localPersonId Die ID der Person für wen der TransactionLog zurückgegeben werden sollte.
     * @return TransactionLog, bei der HTTP ERROR wird NULL zurückgegeben 
     * oder wenn kein Eintrag gefunden wird.
     */
    public TransactionLog getCurrentStatusForPerson(String localPersonId) {
        try {
            //Führt einen REST Request aus und holt sich den TransactionLog einer Person
            TransactionLog transactionLog = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/transactionlog/person/{localPersonId}", //API
                    TransactionLog.class, //Gibt resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    localPersonId //Person nach der gesucht werden sollte
            );
            //Gibt den TransactionLog zurück
            return transactionLog;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    //GEMEINDEN
     /**
     *Methode gibt eine Liste von allen Gemeinden zurück.  
     * @return documentlists (List<Municipality>), bei einem HttpClientErrorException wird NULL zurückgeben
     */
    public List<Municipality> getMunicipalityList() {
        try {
            //Einen REST Request absetzten und fordert die Ressource "Gemeinde" 
            //an, gleichzeitig wird der Rsponse in die Variable dokumentListe gespeichert
            ResponseEntity<List<Municipality>> municipalityListe = restTemplate.exchange(eUmzugClientServiceEndpoint + "/gemeinden", HttpMethod.GET, null, new ParameterizedTypeReference<List<Municipality>>() {
            });

            //Gibt die Liste von Gemeinden (List<Municiplaity>) aus dem Response als 
            //Rückgabewert der Funktion aus.
            return municipalityListe.getBody();
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     *Methode gibt eine Dokument zurück, von einem spezifischen Namen
     * @param gemeindeName Der Name der Gemeinde das zurück gegeben werden sollte
     * @return Die Gemeinde welches über den Param "gemeindeName" gesucht
     * wird, bei einer httpClientErrorEception wird der Wert NULL zurückgegeben
     */
    public Municipality getMunicipalityByName(String gemeindeName) {
        try {
            //Holt über einen REST Request die über den Namen gesuchte Gemeinde
            Municipality municipality = restTemplate.getForObject(eUmzugClientServiceEndpoint + "/gemeinden/{gemeindeName}",
                    Municipality.class, //Gibt resultatklasse an in welcher das Ergebnis  deserialisert werden soll
                    gemeindeName //Parameter "gemeindeName" welcher beim Funktionsaufruf angegeben wird, wird an den Restrequest weitergegeben,
                    //damit der Restservice nach dem Dokument mit diesem Namen suchen kann.
            );
            //Gibt die Gemeinde zurück
            return municipality;
        } catch (HttpClientErrorException httpClientErrorException) { //httpClientErrorEception beinhaltet HTTP ERROR Meldung wird aber gemäss Anforderung nicht zurückgegeben
            return null;    //Bei HTTP ERROR wird null zurückgegeben
        }

    }

    /**
     * //Erstellt die neue Gemeinde via Put.
     *
     * @param gemeinde
     */
    public void addMunicipality(Municipality gemeinde) {
        //Sendet über eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/gemeinde", gemeinde);
    }

    /**
     * Bennent die Gemeinde um via Put.
     *
     * @param Id
     * @param name
     */
    public void renameMunicipality(int Id, String name) {
        //sendet über eine PUT-Anfrage an eumzugapi/v1//dokumente/{Id}/{name}
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{Id}/{name}", "", Id, name);
    }

    /**
     *Löscht die Gemeinde mit der angegbenen ID.
     * @param Id
     */
    public void deleteMunicipality(int Id) {
        //sendet über eine PUT-Anfrage an eumzugapi/v1//gemeinde/{Id}/{name}
      //  restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{Id}","", Id);
        restTemplate.delete(eUmzugClientServiceEndpoint + "/gemeinden/{Id}", Id);
    }

    /**
     * Erstellt neue Gebühr für eine Gemeinde über die angegebene ID und der angegebenen Gebühr
     * @param id Das ist die ID der Gemeinde
     * @param gebuehr Der Wert der Gebühr
     */
    public void newFee(int id, int gebuehr) {
        //Sendet über eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/move/{Gebuehr}", "",id, gebuehr);
    }

    /**
     * Zugugsgebühr wird erstellt über die angegebene ID und der angegebenen Gebühr
     * @param id Das ist die ID der Gemeinde
     * @param gebuehr Der Wert der Geführ
     */
    public void newFeeIn(int id, int gebuehr) {
        //Sendet über eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/movein/{Gebuehr}", "", id, gebuehr);
    }

    /**
     * Wegzugsgebühr wird erstellt über die angegebene ID und der angegebenen Gebühr
     * @param id Das ist die ID der Gemeinde
     * @param gebuehr Der Wert der Gebühr
     */
    public void newFeeOut(int id, int gebuehr) {
        //Sendet über eine PUT-Anfrage an eumzugapi/v1/gemeinde/gemeinde Dabei wird keine Antwort der API-Methode ausgelesen. 
        restTemplate.put(eUmzugClientServiceEndpoint + "/gemeinden/{id}/moveout/{Gebuehr}", "", id, gebuehr);
    }
}
