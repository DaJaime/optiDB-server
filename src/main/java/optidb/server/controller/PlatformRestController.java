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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Stream;


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
                r = mysql.test(name, nbCol, nbLine);
                this.jsonCreate(r);
                return r;
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


    @RequestMapping(value = "/historique", method = RequestMethod.GET)
    public ArrayList historiqueList()
    {
        ArrayList ls = new ArrayList<>();
        ls.add("s");
        return ls;
    }


    private void jsonCreate (Resultat res)
    {
        JSONObject obj = new JSONObject();
        JSONArray listeInsert = new JSONArray();
        try
        {
            for(int i=0;i< res.getListeInsert().size();i++)
            {
                listeInsert.put(res.getListeInsert().get(i));
            }
            obj.put("platformName", res.getPlatformName());
            obj.put("nbCol", res.getNbCol());
            obj.put("nbLine", res.getNbLine());
            obj.put("tempsCreate", res.getTempsCreate());
            obj.put("listeInsert", listeInsert);
            obj.put("tempsUpdate", res.getTempsUpdate());
            obj.put("tempsAlter", res.getTempsAlter());
            obj.put("tempsDelete", res.getTempsDelete());
            obj.put("tempsSelectAll", res.getTempsSelectAll());
            obj.put("tempsSelect", res.getTempsSelect());
            obj.put("tempsDrop", res.getTempsDrop());
        }
        catch (Exception e)
        {
            myLog.warning(e.toString());
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        try (FileWriter file = new FileWriter( "/home/vagrant/media/"+dateFormat.format(date)+".json"))
        {
            file.write(obj.toString());
            file.flush();
        }
        catch (Exception e)
        {
            myLog.warning(e.toString());
        }
    }
}
