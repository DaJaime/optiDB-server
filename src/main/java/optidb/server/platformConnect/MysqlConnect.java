package optidb.server.platformConnect;

import optidb.server.model.Resultat;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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

    private long createTable(Connection cx, int nbCol){
        StringBuilder sb = new StringBuilder();
        // On crer autant de collones que demandé
        sb.append("CREATE TABLE table_test (id INTEGER(10)");
        if(nbCol>1) {
            for (int i = 1; i < nbCol; i++) {
                sb.append(", col").append(i).append(" VARCHAR(100)");
            }
        }
        sb.append(")");
        // Execution
        long debut = System.currentTimeMillis();
        executeUpdate(cx, sb.toString());
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private long insertOneLine(Connection cx, int nbCol){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO table_test values('1'");
        if(nbCol>1) {
            for (int i = 1; i < nbCol; i++) {
                sb.append(",'info").append(i).append("'");
            }
        }
        sb.append(")");
        System.out.println(sb.toString());
        // Execution
        long debut = System.currentTimeMillis();
        executeUpdate(cx, sb.toString());
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private ArrayList insertAllLine(Connection cx, int nbCol, int nbLine){
        ArrayList listeInsert = new ArrayList();
        for (int y = 1; y<nbLine+1;y++) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO table_test values('").append(y).append("'");
            if (nbCol > 1) {
                for (int i = 1; i < nbCol; i++) {
                    sb.append(",'info").append(i).append("'");
                }
            }
            sb.append(")");
            //On ajoute le temps à la liste tout les 10% pour voir l'évolution
            double prct = ((100.0 * (double) y) / (double) nbLine);
            if(prct % 10 == 0) {
                long debut = System.currentTimeMillis();
                executeUpdate(cx, sb.toString());
                long fin = System.currentTimeMillis();
                listeInsert.add(fin - debut);
            }
        }

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return listeInsert;
    }

    private long updateOneLine(Connection cx){
        String requete = "UPDATE table_test SET id = '2' WHERE id=1";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private long alterTable(Connection cx){
        String requete = "ALTER TABLE table_test ADD newCol VARCHAR(255)";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private long deleteOneLine(Connection cx){
        String requete = "DELETE FROM table_test WHERE id = '1'";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private long selectOneLine(Connection cx){
        String requete = "SELECT * FROM table_test WHERE id = '1'";
        long debut = System.currentTimeMillis();
        executeQuery(cx, requete);
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private long selectAll(Connection cx){
        String requete = "SELECT * FROM table_test";
        long debut = System.currentTimeMillis();
        executeQuery(cx, requete);
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
        return fin - debut;
    }

    private long dropTable(Connection cx){
        String requete = "DROP TABLE table_test";
        long debut = System.currentTimeMillis();
        executeUpdate(cx, requete);
        long fin = System.currentTimeMillis();

        // On attend une demi seconde
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
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

    public Resultat test(String nameBD, int nbCol, int nbLine){
        // Init
        this.dockerRun();
        Connection cx = this.connect();

        //On commence les tests
        // Create
        long tempsCreate = this.createTable(cx, nbCol);
        // Isert
        ArrayList listeInsert = this.insertAllLine(cx, nbCol, nbLine);
        // Update
        long tempsUpdate = this.updateOneLine(cx);
        // Select
        long tempsSelectOne = this.selectOneLine(cx);
        // Select All table
        long tempsSelectAll = this.selectAll(cx);
        // Alter
        long tempsAlter = this.alterTable(cx);
        // Delete
        long tempsDelete = this.deleteOneLine(cx);
        // Drop
        long tempsDrop = this.dropTable(cx);

        //On ferme la connection
        this.dockerClose(cx);

        // on récup le résultat
        return new Resultat(nameBD, nbCol, nbLine, tempsCreate, listeInsert, tempsUpdate, tempsSelectOne, tempsSelectAll, tempsAlter, tempsDelete ,tempsDrop);
    }


}
