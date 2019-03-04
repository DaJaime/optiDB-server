package optidb.server.model;

import optidb.server.platformConnect.InterfaceConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SqlTest {
    private static Logger myLog = Logger.getLogger("WarningLogging");

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

    private long createTable(Connection cx, int nbCol, int cle){
        StringBuilder sb = new StringBuilder();
        // On crer autant de collones que demandé
        if(cle == 0) {
            sb.append("CREATE TABLE table_test (id INT");
        }
        else {
            sb.append("CREATE TABLE table_test (id INT PRIMARY KEY NOT NULL");
        }
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
            if(nbLine>=10){
                int interval = nbLine/10;
                if(y % interval == 0) {
                    long debut = System.currentTimeMillis();
                    executeUpdate(cx, sb.toString());
                    long fin = System.currentTimeMillis();
                    listeInsert.add(fin - debut);
                }
                else{
                    executeUpdate(cx, sb.toString());
                }
            }
            else{
                executeUpdate(cx, sb.toString());
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

    private long updateOneLine(Connection cx, int nbLine){
        String requete = "UPDATE table_test SET id = '"+nbLine+1+"' WHERE id=1";
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

    public Resultat test(InterfaceConnect platform, String nameBD, int nbCol, int nbLine, int cle){
        // Init
        platform.dockerRun();
        Connection cx = platform.connect();

        //On commence les tests
        // Create
        long tempsCreate = this.createTable(cx, nbCol, cle);
        // Isert
        ArrayList listeInsert = this.insertAllLine(cx, nbCol, nbLine);
        // Update
        long tempsUpdate = this.updateOneLine(cx, nbLine);
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
        platform.dockerClose(cx);

        // on récup le résultat
        return new Resultat(nameBD, nbCol, nbLine, tempsCreate, listeInsert, tempsUpdate, tempsSelectOne,
                tempsSelectAll, tempsAlter, tempsDelete ,tempsDrop);
    }
}
