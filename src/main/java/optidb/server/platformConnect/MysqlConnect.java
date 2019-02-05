package optidb.server.platformConnect;

import optidb.server.model.Resultat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnect {

    private void dockerRun () {
        try {
            Process process = Runtime.getRuntime().exec("docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mysql:8.0.14");
            process.waitFor();
            Thread.sleep(16000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private Connection connect () {
        String driver = "com.mysql.cj.jdbc.Driver";
        Connection cx = null;
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/test","root","pass");
        }
        catch (ClassNotFoundException e) { // classe du pilote introuvable
            System.out.println(e.getMessage());
        }
        catch (SQLException e) { // accès à la base refusé
            System.out.println("SQL : " + e.getMessage());
        }
        return cx;
    }

    private void executeUpdate (Connection cx, String requete){
        try {
            Statement stmt = cx.createStatement();
            stmt.executeUpdate(requete);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(Connection cx){
        String requete = "CREATE TABLE table_test (id INTEGER(10), nom VARCHAR(100), prenom VARCHAR(100))";
        executeUpdate(cx, requete);
    }

    private void insertOneLine(Connection cx){
        String requete = "INSERT INTO table_test values('1','toto','tata')";
        executeUpdate(cx, requete);

    }

    private void deleteOneLine(Connection cx){
        String requete = "DELETE FROM table_test WHERE id = '1'";
        executeUpdate(cx, requete);
    }

    private void dockerClose(Connection cx) {
        try {
            if (cx != null) cx.close();
            Process processClose = Runtime.getRuntime().exec("docker stop mysql");
            processClose.waitFor();
            Thread.sleep(4000);
            Process processRm = Runtime.getRuntime().exec("docker rm mysql");
            processRm.waitFor();
            Thread.sleep(8000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            System.out.println("Close connection");
            e.printStackTrace();
        }
    }

    public Resultat test(String nameBD){
        // Init
        this.dockerRun();
        Connection cx = this.connect();
        //On commence les tests
        long debut = System.currentTimeMillis();
        this.createTable(cx);
        long fin = System.currentTimeMillis();
        long tempsCreate= fin - debut;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        // Isert
        debut = System.currentTimeMillis();
        this.insertOneLine(cx);
        fin = System.currentTimeMillis();
        long tempsInsert = fin - debut;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        // Delete
        debut = System.currentTimeMillis();
        this.deleteOneLine(cx);
        fin = System.currentTimeMillis();
        long tempsDelete = fin - debut;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        //On ferme la connection
        this.dockerClose(cx);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        // on récup le résultat
        return new Resultat(nameBD, tempsCreate, tempsInsert, tempsDelete);
    }


}
