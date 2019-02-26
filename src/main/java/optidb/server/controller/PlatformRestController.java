package optidb.server.controller;

import optidb.server.model.Platform;
import optidb.server.model.Resultat;
import optidb.server.platformConnect.MysqlConnect;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;


@RestController
public class PlatformRestController {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    @RequestMapping(value = "/platform", method = RequestMethod.GET)
    public Resultat platformVersion(@RequestParam(value="name", defaultValue="Inconu") String name, @RequestParam(value="col", defaultValue="0") int nbCol, @RequestParam(value="line", defaultValue="0") int nbLine) {
        name = name.toLowerCase();
        Resultat r;
        switch (name) {
            case "mysql":
                MysqlConnect mysql = new MysqlConnect();
                System.out.println(mysql.test(name, nbCol, nbLine));
                r = mysql.test(name, nbCol, nbLine);
                this.jsonCreate(r);
                return mysql.test(name, nbCol, nbLine);
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


    private void jsonCreate (Resultat res)
    {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        try
        {
            list.put(3);
            obj.put("messages", list);
            obj.put("name", "mkyong.com");
            obj.put("age", new Integer(100));
        }
        catch (Exception e)
        {
            System.out.println(e.toString() + "Erreur json");
        }


        try (FileWriter file = new FileWriter("test.json")) {

            file.write(obj.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);
    }
}
