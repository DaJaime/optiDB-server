package optidb.server.platformConnect;

import optidb.server.model.Resultat;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class MysqlConnect {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    private void dockerRun () {
        try {
            Process process = Runtime.getRuntime().exec("docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mysql:8.0.14");
            process.waitFor();
            Thread.sleep(20000);
        } catch (IOException e) {
            myLog.warning(e.toString());

        } catch (InterruptedException e) {
            myLog.warning(e.toString());
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
        Statement stmt = null;
        try {
            stmt = cx.createStatement();
            stmt.executeUpdate(requete);
        } catch (SQLException e) {
            myLog.warning(e.toString());
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                myLog.warning(e.toString());
            }
        }
    }

    private void executeQuery (Connection cx, String requete){
        Statement stmt = null;
        try {
            stmt = cx.createStatement();
            ResultSet rs = stmt.executeQuery(requete);
            rs.next();
        } catch (SQLException e) {
            myLog.warning(e.toString());
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                myLog.warning(e.toString());
            }
        }
    }

    private long createTable(Connection cx){
        String requete = "CREATE TABLE table_test (id INTEGER(10), nom VARCHAR(100), prenom VARCHAR(100))";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    private long insertOneLine(Connection cx){
        String requete = "INSERT INTO table_test values('1','toto','tata')";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;

    }

    private long updateOneLine(Connection cx){
        String requete = "UPDATE table_test SET nom = 'titi' WHERE id=1";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    private long alterTable(Connection cx){
        String requete = "ALTER TABLE table_test ADD adresse VARCHAR(255)";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    private long deleteOneLine(Connection cx){
        String requete = "DELETE FROM table_test WHERE id = '1'";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    private long selectOneLine(Connection cx){
        String requete = "SELECT * FROM table_test WHERE id = '1'";
        long debut = System.currentTimeMillis();
        executeQuery(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    private long dropTable(Connection cx){
        String requete = "DROP TABLE table_test";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();
        return fin - debut;
    }

    private void dockerClose(Connection cx) {
        try {
            if (cx != null) cx.close();
            Process processClose = Runtime.getRuntime().exec("docker stop mysql");
            processClose.waitFor();
            Thread.sleep(4000);
            Process processRm = Runtime.getRuntime().exec("docker rm mysql");
            processRm.waitFor();
            Thread.sleep(4000);
        } catch (IOException e) {
            myLog.warning(e.toString());
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            System.out.println("Close connection");
            myLog.warning(e.toString());
        }
    }

    public Resultat test(String nameBD){
        // Init
        this.dockerRun();

        Connection cx = this.connect();
        //On commence les tests
        // Create
        long tempsCreate = this.createTable(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        // Isert
        long tempsInsert = this.insertOneLine(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        // Update
        long tempsUpdate = updateOneLine(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        // Select
        long tempsSelect = selectOneLine(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }

        // Alter
        long tempsAlter = alterTable(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }

        // Delete
        long tempsDelete = this.deleteOneLine(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }

        // Drop
        long tempsDrop = this.dropTable(cx);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }

        //On ferme la connection
        this.dockerClose(cx);
        // on récup le résultat
        return new Resultat(nameBD, tempsCreate, tempsInsert, tempsUpdate, tempsSelect, tempsAlter, tempsDelete ,tempsDrop);
    }


}
