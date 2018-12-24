package ch.zhaw.gpi.eumzugwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse für die Webapplikation. Basis ist das SpringBoot-Framework.
 * 
 * @author VZa02
 */
@SpringBootApplication
public class Application {

    /**
     * Haupt-Methode, welche beim Run-Befehl eine
     * Webapplikation erstellt.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
