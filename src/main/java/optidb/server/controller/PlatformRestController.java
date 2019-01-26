package optidb.server.controller;

import optidb.server.model.Platform;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
public class PlatformRestController {

    @RequestMapping("/platform")
    public Platform platformVersion(@RequestParam(value="name", defaultValue="Inconu") String name) {
        name = name.toLowerCase();
        return new Platform(name, "0");
    }

    @RequestMapping("/list")
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
                System.out.println(ligne);
                String [] tab = ligne.split(" ");
                liste.add(new Platform(tab[0], tab[15]));
            }
            process.waitFor();
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

        return liste;
    }

}
