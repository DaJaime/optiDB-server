package optidb.server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public String version()
    {
        Logger logger = Logger.getLogger("logger");
        try
        {
            // Création de la commande
            Process process = Runtime.getRuntime().exec("mvn -v");
            // On récup le résultat
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            int exitVal = process.waitFor();
            if (exitVal != 0)
            {
                logger.log(Level.INFO,"ERROR : Read version");
            }
            return line;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return null;
        }
    }


}
