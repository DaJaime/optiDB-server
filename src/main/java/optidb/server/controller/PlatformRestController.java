package optidb.server.controller;

import optidb.server.model.Platform;
import optidb.server.model.Resultat;
import optidb.server.model.SqlTest;
import optidb.server.platformConnect.MariadbConnect;
import optidb.server.platformConnect.MysqlConnect;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import optidb.server.platformConnect.PostgresConnect;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        Resultat r;
        switch (name) {
            case "mysql":
                MysqlConnect mysql = new MysqlConnect();
                r = sqlTest.test(mysql,name, nbCol, nbLine, cle);
                this.jsonCreate(r);
                return r;
            case "postgres":
                PostgresConnect postgres = new PostgresConnect();
                r = sqlTest.test(postgres,name, nbCol, nbLine, cle);
                this.jsonCreate(r);
                return r;
            case "mariadb":
                MariadbConnect mariadb = new MariadbConnect();
                r = sqlTest.test(mariadb,name, nbCol, nbLine, cle);
                this.jsonCreate(r);
                return r;
            default:
                break;
        }
        ArrayList listeInsert = new ArrayList();
        return new Resultat(name, nbCol, nbLine, 0, listeInsert,0,0, 0,0, 0, 0);
    }


    @RequestMapping(value = "/historique", method = RequestMethod.GET)
    public Resultat historiqueJson(@RequestParam(value="name", defaultValue="Inconu") String name)
    {
        Resultat r = null;
        JSONParser parser = new JSONParser();
        try
        {
            Object obj = parser.parse(new FileReader("/vagrant/media/"+name));
            JSONObject jsonObject = (JSONObject) obj;

            String platformName = (String) jsonObject.get("platformName");
            Long nbCol = (Long) jsonObject.get("nbCol");
            Long nbLigne = (Long) jsonObject.get("nbLine");
            Long delete = (Long) jsonObject.get("tempsDelete");
            Long alter = (Long) jsonObject.get("tempsAlter");
            Long create = (Long) jsonObject.get("tempsCreate");
            Long select = (Long) jsonObject.get("tempsSelect");
            Long drop = (Long) jsonObject.get("tempsDrop");
            Long update = (Long) jsonObject.get("tempsUpdate");
            Long selectAll = (Long) jsonObject.get("tempsSelectAll");
            ArrayList listinsert = (ArrayList) jsonObject.get("listeInsert");
            r = new Resultat(platformName,nbCol.intValue(),nbLigne.intValue(),create,listinsert,update,select,selectAll,alter,delete,drop);
        }
        catch (IOException e)
        {
            myLog.warning(e.toString());
        }
        catch (ParseException e)
        {
            myLog.warning(e.toString());
        }
        return r;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ArrayList <Platform> platformList()
    {
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
                List<String> elements = new ArrayList<>();
                for(int i = 0;i<tab.length;i++) {
                    if(tab[i].length()>0) {
                        elements.add(tab[i]);
                    }
                }
                Platform obj = new Platform(elements.get(0), elements.get(1));
                if(tab[0].matches("mysql")) {
                    obj.setDescription("MySQL est un serveur de bases de données relationnelles Open Source.\n \n Un serveur de bases de données stocke les données dans des tables séparées plutôt que de tout rassembler dans une seule table. Cela améliore la rapidité et la souplesse de l'ensemble. Les tables sont reliées par des relations définies, qui rendent possible la combinaison de données entre plusieurs tables durant une requête. Le SQL dans 'MySQL' signifie 'Structured Query Language' : le langage standard pour les traitements de bases de données.");
                    obj.setTypeModel("Relationnel");obj.setLogo("https://upload.wikimedia.org/wikipedia/fr/thumb/6/62/MySQL.svg/220px-MySQL.svg.png");
                    obj.setWebsite("https://www.mysql.com/fr/");obj.setDeveloper("Oracle");obj.setInitialRelease("1995");
                    obj.setLicense("Open Source - GPL v2");obj.setRequetage("SQL"); obj.setName("MySQL");
                }
                if(tab[0].matches("postgres")) {
                    obj.setDescription("PostgreSQL est un Système de Gestion de Base de Données (SBGD) libre disponible sous licence BSD. Ce système multi-plateforme est largement connu et réputé à travers le monde, notamment pour son comportement stable et pour être très respectueux des normes ANSI SQL. Ce projet libre n’est pas géré par une entreprise mais par une communauté de développeurs.\\nLa première version de PostgreSQL date de 1996. A l’origine il s’agit de la base de données Ingres, développé par Michael Stonebraker, qui a été reprogrammé de zéro en 1985 sous le nom Postgres (post-ingres). SQL a été ajouté à Postgres en 1995 sous le nom de projet Postgres95 et le nom à évolué en PostgreSQL en 1996.");
                    obj.setTypeModel("Relationnel");obj.setLogo("http://www.impulsmap.fr/wp-content/uploads/2016/07/LogoPostgreSql100reel.png");
                    obj.setWebsite("https://www.postgresql.org/");obj.setDeveloper("PostgreSQL United States");obj.setInitialRelease("1989");
                    obj.setLicense("License BSD");obj.setRequetage("SQL"); obj.setName("PostGreSQL");
                }
                if(tab[0].matches("mariadb")) {
                    obj.setDescription("MariaDB est un Système de Gestion de Base de Données (SGBD) disponible sous licence GPL. Ce système est un fork de MySQL, ce qui signifie que c’est un nouveau logiciel créé à partir du code source de MySQL.\\n\\nLe projet a été lancé par l’entreprise Monty Program AB et sa maintenance est assurée par la fondation MariaDB. Cette fondation est une organisation à but non lucrative qui permet de s’assurer que le projet restera libre et qu’il y a une protection légale autour du projet.\\nSachant que MySQL a fini par devenir un projet de l’entreprise Oracle, l’informaticien Michael Widenius qui est le principal développeur de MySQL décide de créer MariaDB dans le but de remplacer MySQL et d’assurer une interropérabilité avec celui-ci.");
                    obj.setTypeModel("Relationnel");obj.setLogo("https://michauko.org/blog/wp-content/uploads/2016/02/logo-Mariadb.png");
                    obj.setWebsite("https://mariadb.org/");obj.setDeveloper("MariaDB Foundation");obj.setInitialRelease("2009");
                    obj.setLicense("Open Source - GPL v2");obj.setRequetage("SQL"); obj.setName("MariaDB");
                }
                if(tab[0].matches("sqlite3")) {
                    obj.setDescription("SQLite est un système de base de données qui a la particularité de fonctionner sans serveur, on dit aussi \\\"standalone\\\" ou \\\"base de données embarquée\\\". On peut l'utiliser avec beaucoup de langages : PHP, Python, C# (.NET), Java, C/C++, Delphi, Ruby...\\nL'intérêt c'est que c'est très léger et rapide à mettre en place, on peut s'en servir aussi bien pour stocker des données dans une vraie base de données sur une application pour smartphone (iPhone ou Android), pour une application Windows, ou sur un serveur web.\\nUne base de données SQLite est bien plus performante et facile à utiliser que de stocker les données dans des fichiers XML ou binaires.");
                    obj.setTypeModel("Relationnel");obj.setLogo("https://sql.sh/wp-content/uploads/2014/04/sqlite-sgbd-500px.png");
                    obj.setWebsite("https://www.sqlite.org/index.html");obj.setDeveloper("Dwayne Richard Hipp");obj.setInitialRelease("2000");
                    obj.setLicense("Domaine public");obj.setRequetage("SQL"); obj.setName("SQLite");
                }
                liste.add(obj);
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


    @RequestMapping(value = "/media", method = RequestMethod.GET)
    public ArrayList<String> historiqueList()
    {
        ArrayList<String> ls = new ArrayList<>();
        File directory = new File("/vagrant/media");
        File[] fList = directory.listFiles();
        if(fList != null)
        {
            for (File file : fList)
            {
                ls.add(file.getName());
            }
        }
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
        try (FileWriter file = new FileWriter( "/vagrant/media/"+res.getPlatformName()+dateFormat.format(date)+".json"))
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
