package optidb.server.controller;

import optidb.server.model.Platform;
import optidb.server.model.Resultat;
import optidb.server.platformConnect.MysqlConnect;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;


@RestController
public class PlatformRestController {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    @RequestMapping(value = "/platform", method = RequestMethod.GET)
    public Resultat platformVersion(@RequestParam(value="name", defaultValue="Inconu") String name) {
        name = name.toLowerCase();
        switch (name) {
            case "mysql":
                MysqlConnect mysql = new MysqlConnect();
                return mysql.test(name);
            default:
                break;
        }
        return new Resultat(name, 0, 0,0);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ArrayList <Platform> platformList() {
        ArrayList<Platform> liste = new ArrayList<>();
        String ligne = "";
        try
        {
            // Création de la commande
            Process process = Runtime.getRuntime().exec("docker images");
            // On récup le résultat
            BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream()));
            // On passe la première ligne
            ligne = reader.readLine();
            // On lis les autres lignes, un crée une Platform par ligne que l'on ajoute à la liste des Platform
            while ((ligne = reader.readLine()) != null) {
                String [] tab = ligne.split(" ");
                liste.add(new Platform(tab[0], tab[15]));
            }
            process.waitFor();
        }
        catch (IOException e)
        {
            myLog.warning(e.toString());
            return null;
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
            return null;
        }

        return liste;
    }

}
