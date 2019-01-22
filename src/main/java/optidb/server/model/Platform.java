package optidb.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Platform {
    private final String name;
    private final String currentVersion;

    public Platform(String name) {
        this.name = name;
        this.currentVersion = this.version();
    }

    public String getName() {
        return name;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String version(){
        try {
            // Création de la commande
            Process process = Runtime.getRuntime().exec("mvn -v");
            // On récup le résultat

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line = reader.readLine();

            int exitVal = process.waitFor();
            if (exitVal != 0) {
                System.out.println("ERROR : Read version");
            }
            return line;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
