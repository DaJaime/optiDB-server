package optidb.server.controller;

import optidb.server.model.Platform;
import optidb.server.model.Resultat;
import optidb.server.model.SqlTest;
import optidb.server.platformConnect.MariadbConnect;
import optidb.server.platformConnect.MysqlConnect;
import optidb.server.platformConnect.PostgresConnect;
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
    public Resultat platformVersion(@RequestParam(value="name", defaultValue="Inconu") String name,
                                    @RequestParam(value="col", defaultValue="0") int nbCol,
                                    @RequestParam(value="line", defaultValue="0") int nbLine,
                                    @RequestParam(value="cle", defaultValue="0") int cle) {
        name = name.toLowerCase();
        SqlTest sqlTest = new SqlTest();
        switch (name) {
            case "mysql":
                MysqlConnect mysql = new MysqlConnect();
                return sqlTest.test(mysql,name, nbCol, nbLine, cle);
            case "postgres":
                PostgresConnect postgres = new PostgresConnect();
                return sqlTest.test(postgres,name, nbCol, nbLine, cle);
            case "mariadb":
                MariadbConnect mariadb = new MariadbConnect();
                return sqlTest.test(mariadb,name, nbCol, nbLine, cle);
            default:
                break;
        }
        ArrayList listeInsert = new ArrayList();
        return new Resultat(name, nbCol, nbLine, 0, listeInsert,0,0, 0,0, 0, 0);
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
